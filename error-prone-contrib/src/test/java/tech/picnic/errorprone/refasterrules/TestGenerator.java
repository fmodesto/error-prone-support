package tech.picnic.errorprone.refasterrules;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.openrewrite.ExecutionContext;
import org.openrewrite.InMemoryExecutionContext;
import org.openrewrite.java.*;
import org.openrewrite.java.format.AutoFormat;
import org.openrewrite.java.tree.J;
import org.openrewrite.java.tree.Space;
import org.openrewrite.test.RewriteTest;
import org.openrewrite.test.SourceSpecs;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

import static org.openrewrite.java.Assertions.java;

public class TestGenerator implements RewriteTest {

    static String CLASS = """
      package tech.picnic.errorprone.refasterrules.rewrite;
      
      import org.junit.jupiter.api.Test;
      import org.openrewrite.Recipe;
      import org.openrewrite.java.JavaParser;
      import org.openrewrite.test.RewriteTest;
      
      import static org.openrewrite.java.Assertions.java;
      
      final class {{RECIPES}}RecipesTest implements RewriteTest {

      {{TEST}}
      }
      """;

    static String TEST = """
          @Test
          void test{{RECIPE}}Recipe() {
              Recipe recipe = RewriteUtils.loadRecipe("{{RECIPES}}Recipes.{{RECIPE}}Recipe");
              rewriteRun(
                spec -> spec.recipe(recipe)
                  .expectedCyclesThatMakeChanges(1).cycles(1)
                  .parser(JavaParser.fromJavaVersion().classpath(JavaParser.runtimeClasspath())),
                java(
      ""\"
      {{BEFORE}}
      ""\",
      ""\"
      {{AFTER}}
      ""\"
              ));
          }
      """;

    record Code(String recipe, String code, String className) {}

    @Test
    @Disabled("Manual run")
    void generateCode() throws IOException {
        Map<String, List<Code>> before = cleanupCode(extractMethods("Input.java"));
        Map<String, List<Code>> after = cleanupCode(extractMethods("Output.java"));

        for (var recipes : before.keySet()) {
            var methods = new ArrayList<String>();
            for (var code : before.get(recipes).stream().sorted(Comparator.comparing(Code::recipe)).toList()) {
                var recipe = code.recipe();
                var beforeCode = code.code();
                var afterCode = after.get(recipes).stream()
                  .filter(c -> c.recipe().equals(recipe))
                  .findFirst()
                  .orElseThrow().code();
                methods.add(TEST
                  .replace("{{RECIPES}}", recipes)
                  .replace("{{RECIPE}}", recipe)
                  .replace("{{BEFORE}}", beforeCode)
                  .replace("{{AFTER}}", afterCode));
            }
            var testClass = CLASS
              .replace("{{RECIPES}}", recipes)
              .replace("{{TEST}}", methods.stream().collect(Collectors.joining("\n")).trim());
            Path dest = Paths.get("src/test/java/tech/picnic/errorprone/refasterrules/rewrite/" + recipes + "RecipesTest.java");
            Files.createDirectories(dest.getParent());
            Files.write(dest, testClass.getBytes());
        }
    }

    private Map<String, List<Code>> extractMethods(String filter) throws IOException {
        var ai = new AtomicInteger(0);
        Map<String, List<Code>> methods = new HashMap<>();
        var files = Files.list(Paths.get("src/test/resources/tech/picnic/errorprone/refasterrules"))
          .filter(e -> e.toString().endsWith(filter))
          .filter(e -> !e.toString().contains("BugCheckerRulesTest"))
          .map(e -> getSource(e))
          .toArray(SourceSpecs[]::new);

        rewriteRun(
          spec -> spec
            .parser(JavaParser.fromJavaVersion().classpath(JavaParser.runtimeClasspath()))
            .recipe(RewriteTest.toRecipe(() -> new JavaVisitor<>() {
                @Override
                public J visitMethodDeclaration(J.MethodDeclaration method, ExecutionContext executionContext) {
                    if (method.getName().getSimpleName().startsWith("test")) {
                        J.CompilationUnit cu = getCursor().dropParentUntil(e -> e instanceof J.CompilationUnit).getValue();
                        var imports = cu.getImports().stream()
                          .map(e -> e + ";")
                          .filter(e -> !e.contains("RefasterRuleCollectionTestCase"))
                          .collect(Collectors.joining("\n")).trim();

                        J.ClassDeclaration cd = getCursor().dropParentUntil(e -> e instanceof J.ClassDeclaration).getValue();
                        String name = cd.getSimpleName().substring(0, cd.getSimpleName().length() - 4);

                        var test = method.withName(method.getName().withSimpleName("test")).printTrimmed(new JavaPrinter<>()).trim().lines()
                          .map(e -> "    " + e)
                          .collect(Collectors.joining("\n"));

                        if (name.contains("TimeRules") && test.contains("ZONED_DATE_TIME")) {
                            test = "    static final ZonedDateTime ZONED_DATE_TIME = Instant.EPOCH.atZone(ZoneOffset.UTC);\n\n" + test;
                        }

                        var testClass = "Test" + ai.incrementAndGet();
                        var generated = """
                          {{IMPORTS}}
                          
                          class {{CLASS}} {
                          {{TEST}}
                          }
                          """.replace("{{IMPORTS}}", imports).replace("{{TEST}}", test).replace("{{CLASS}}", testClass);

                        methods.computeIfAbsent(name, e -> new ArrayList<>()).add(new Code(method.getName().getSimpleName().substring(4), generated, testClass));
                    }
                    return super.visitMethodDeclaration(method, executionContext);
                }
            })),
          files
        );
        return methods;
    }

    private HashMap<String, List<Code>> cleanupCode(Map<String, List<Code>> before) {
        var parser = JavaParser.fromJavaVersion()
          .classpath(JavaParser.runtimeClasspath())
          .build();
        var srcs = before.values().stream()
          .flatMap(Collection::stream)
          .map(Code::code)
          .toArray(String[]::new);
        Map<String, String> cleaned = new HashMap<>();
        parser.parse(srcs).forEach(cu -> {
            var cn = cu.getSourcePath().toString().substring(0, cu.getSourcePath().toString().lastIndexOf("."));
            var ast = new RemoveUnusedImports().getVisitor().visit(cu, new InMemoryExecutionContext());
            ast = new ShortenFullyQualifiedTypeReferences().getVisitor().visit(ast, new InMemoryExecutionContext());
            ast = new JavaIsoVisitor<>() {
                @Override
                public Space visitSpace(Space space, Space.Location loc, Object o) {
                    if (space.getWhitespace().contains("\n") && getCursor().firstEnclosing(J.MethodDeclaration.class) != null) {
                        return Space.EMPTY;
                    }
                    return super.visitSpace(space, loc, o);
                }
            }.visit(ast, new InMemoryExecutionContext());
            ast = new OrderImports(true).getVisitor().visit(ast, new InMemoryExecutionContext());
            ast = new AutoFormat().getVisitor().visit(ast, new InMemoryExecutionContext());
            cleaned.put(cn, ast.printTrimmed(new JavaPrinter<>()).trim().replace(";import", ";\nimport"));
        });
        var copy = new HashMap<String, List<Code>>();
        for (var e : before.entrySet()) {
            var cleanedCodes = new ArrayList<Code>();
            for (var code : e.getValue()) {
                var src = cleaned.get(code.className()).replace("class " + code.className, "class Test");
                cleanedCodes.add(new Code(code.recipe(), src, code.className()));
            }
            copy.put(e.getKey(), cleanedCodes);
        }
        return copy;
    }

    private static SourceSpecs getSource(Path e) {
        try {
            return java(Files.readString(e));
        } catch (IOException ex) {
            throw new RuntimeException(ex);
        }
    }
}
