package tech.picnic.errorprone.refasterrules.rewrite;

import org.junit.jupiter.api.Test;
import org.openrewrite.Recipe;
import org.openrewrite.java.JavaParser;
import org.openrewrite.test.RewriteTest;

import static org.openrewrite.java.Assertions.java;

final class FileRulesRecipesTest implements RewriteTest {

    @Test
    void testFileMkDirsFileExistsRecipe() {
        Recipe recipe = RewriteUtils.loadRecipe("FileRulesRecipes.FileMkDirsFileExistsRecipe");
        rewriteRun(
                spec -> spec.recipe(recipe)
                        .expectedCyclesThatMakeChanges(1).cycles(1)
                        .parser(JavaParser.fromJavaVersion().classpath(JavaParser.runtimeClasspath())),
                java(
                        """
                                import com.google.common.collect.ImmutableSet;
                                
                                import java.io.File;
                                
                                class Test {
                                    ImmutableSet<Boolean> test() {
                                        return ImmutableSet.of(new File("foo").exists() || new File("foo").mkdirs(), !new File("bar").exists() && !new File("bar").mkdirs());
                                    }
                                }
                                """,
                        """
                                import com.google.common.collect.ImmutableSet;
                                
                                import java.io.File;
                                
                                class Test {
                                    ImmutableSet<Boolean> test() {
                                        return ImmutableSet.of(new File("foo").mkdirs() || new File("foo").exists(), !new File("bar").mkdirs() && !new File("bar").exists());
                                    }
                                }
                                """
                ));
    }

    @Test
    void testFilesCreateTempFileInCustomDirectoryToFileRecipe() {
        Recipe recipe = RewriteUtils.loadRecipe("FileRulesRecipes.FilesCreateTempFileInCustomDirectoryToFileRecipe");
        rewriteRun(
                spec -> spec.recipe(recipe)
                        .expectedCyclesThatMakeChanges(1).cycles(1)
                        .parser(JavaParser.fromJavaVersion().classpath(JavaParser.runtimeClasspath())),
                java(
                        """
                                import java.io.File;
                                import java.io.IOException;
                                
                                class Test {
                                    File test() throws IOException {
                                        return File.createTempFile("foo", "bar", new File("baz"));
                                    }
                                }
                                """,
                        """
                                import java.io.File;
                                import java.io.IOException;
                                import java.nio.file.Files;
                                
                                class Test {
                                    File test() throws IOException {
                                        return Files.createTempFile(new File("baz").toPath(), "foo", "bar").toFile();
                                    }
                                }
                                """
                ));
    }

    @Test
    void testFilesCreateTempFileToFileRecipe() {
        Recipe recipe = RewriteUtils.loadRecipe("FileRulesRecipes.FilesCreateTempFileToFileRecipe");
        rewriteRun(
                spec -> spec.recipe(recipe)
                        .expectedCyclesThatMakeChanges(1).cycles(1)
                        .parser(JavaParser.fromJavaVersion().classpath(JavaParser.runtimeClasspath())),
                java(
                        """
                                import com.google.common.collect.ImmutableSet;
                                
                                import java.io.File;
                                import java.io.IOException;
                                
                                class Test {
                                    ImmutableSet<File> test() throws IOException {
                                        return ImmutableSet.of(File.createTempFile("foo", "bar"), File.createTempFile("baz", "qux", null));
                                    }
                                }
                                """,
                        """
                                import com.google.common.collect.ImmutableSet;
                                
                                import java.io.File;
                                import java.io.IOException;
                                import java.nio.file.Files;
                                
                                class Test {
                                    ImmutableSet<File> test() throws IOException {
                                        return ImmutableSet.of(Files.createTempFile("foo", "bar").toFile(), Files.createTempFile("baz", "qux").toFile());
                                    }
                                }
                                """
                ));
    }

    @Test
    void testFilesReadStringRecipe() {
        Recipe recipe = RewriteUtils.loadRecipe("FileRulesRecipes.FilesReadStringRecipe");
        rewriteRun(
                spec -> spec.recipe(recipe)
                        .expectedCyclesThatMakeChanges(1).cycles(1)
                        .parser(JavaParser.fromJavaVersion().classpath(JavaParser.runtimeClasspath())),
                java(
                        """
                                import java.io.IOException;
                                import java.nio.charset.StandardCharsets;
                                import java.nio.file.Files;
                                import java.nio.file.Paths;
                                
                                class Test {
                                    String test() throws IOException {
                                        return Files.readString(Paths.get("foo"), StandardCharsets.UTF_8);
                                    }
                                }
                                """,
                        """
                                import java.io.IOException;
                                import java.nio.file.Files;
                                import java.nio.file.Paths;
                                
                                class Test {
                                    String test() throws IOException {
                                        return Files.readString(Paths.get("foo"));
                                    }
                                }
                                """
                ));
    }

    @Test
    void testFilesReadStringWithCharsetRecipe() {
        Recipe recipe = RewriteUtils.loadRecipe("FileRulesRecipes.FilesReadStringWithCharsetRecipe");
        rewriteRun(
                spec -> spec.recipe(recipe)
                        .expectedCyclesThatMakeChanges(1).cycles(1)
                        .parser(JavaParser.fromJavaVersion().classpath(JavaParser.runtimeClasspath())),
                java(
                        """
                                import java.io.IOException;
                                import java.nio.charset.StandardCharsets;
                                import java.nio.file.Files;
                                import java.nio.file.Paths;
                                
                                class Test {
                                    String test() throws IOException {
                                        return new String(Files.readAllBytes(Paths.get("foo")), StandardCharsets.ISO_8859_1);
                                    }
                                }
                                """,
                        """
                                import java.io.IOException;
                                import java.nio.charset.StandardCharsets;
                                import java.nio.file.Files;
                                import java.nio.file.Paths;
                                
                                class Test {
                                    String test() throws IOException {
                                        return Files.readString(Paths.get("foo"), StandardCharsets.ISO_8859_1);
                                    }
                                }
                                """
                ));
    }

    @Test
    void testPathInstanceRecipe() {
        Recipe recipe = RewriteUtils.loadRecipe("FileRulesRecipes.PathInstanceRecipe");
        rewriteRun(
                spec -> spec.recipe(recipe)
                        .expectedCyclesThatMakeChanges(1).cycles(1)
                        .parser(JavaParser.fromJavaVersion().classpath(JavaParser.runtimeClasspath())),
                java(
                        """
                                import java.nio.file.Path;
                                
                                class Test {
                                    Path test() {
                                        return Path.of("foo").toFile().toPath();
                                    }
                                }
                                """,
                        """
                                import java.nio.file.Path;
                                
                                class Test {
                                    Path test() {
                                        return Path.of("foo");
                                    }
                                }
                                """
                ));
    }

    @Test
    void testPathOfStringRecipe() {
        Recipe recipe = RewriteUtils.loadRecipe("FileRulesRecipes.PathOfStringRecipe");
        rewriteRun(
                spec -> spec.recipe(recipe)
                        .expectedCyclesThatMakeChanges(1).cycles(1)
                        .parser(JavaParser.fromJavaVersion().classpath(JavaParser.runtimeClasspath())),
                java(
                        """
                                import com.google.common.collect.ImmutableSet;
                                
                                import java.nio.file.Path;
                                import java.nio.file.Paths;
                                
                                class Test {
                                    ImmutableSet<Path> test() {
                                        return ImmutableSet.of(Paths.get("foo"), Paths.get("bar", "baz", "qux"));
                                    }
                                }
                                """,
                        """
                                import com.google.common.collect.ImmutableSet;
                                
                                import java.nio.file.Path;
                                
                                class Test {
                                    ImmutableSet<Path> test() {
                                        return ImmutableSet.of(Path.of("foo"), Path.of("bar", "baz", "qux"));
                                    }
                                }
                                """
                ));
    }

    @Test
    void testPathOfUriRecipe() {
        Recipe recipe = RewriteUtils.loadRecipe("FileRulesRecipes.PathOfUriRecipe");
        rewriteRun(
                spec -> spec.recipe(recipe)
                        .expectedCyclesThatMakeChanges(1).cycles(1)
                        .parser(JavaParser.fromJavaVersion().classpath(JavaParser.runtimeClasspath())),
                java(
                        """
                                import java.net.URI;
                                import java.nio.file.Path;
                                import java.nio.file.Paths;
                                
                                class Test {
                                    Path test() {
                                        return Paths.get(URI.create("foo"));
                                    }
                                }
                                """,
                        """
                                import java.net.URI;
                                import java.nio.file.Path;
                                
                                class Test {
                                    Path test() {
                                        return Path.of(URI.create("foo"));
                                    }
                                }
                                """
                ));
    }

    @Test
    void testPathToFileMkDirsFilesExistsRecipe() {
        Recipe recipe = RewriteUtils.loadRecipe("FileRulesRecipes.PathToFileMkDirsFilesExistsRecipe");
        rewriteRun(
                spec -> spec.recipe(recipe)
                        .expectedCyclesThatMakeChanges(1).cycles(1)
                        .parser(JavaParser.fromJavaVersion().classpath(JavaParser.runtimeClasspath())),
                java(
                        """
                                import com.google.common.collect.ImmutableSet;
                                
                                import java.nio.file.Files;
                                import java.nio.file.Path;
                                
                                class Test {
                                    ImmutableSet<Boolean> test() {
                                        return ImmutableSet.of(Files.exists(Path.of("foo")) || Path.of("foo").toFile().mkdirs(), !Files.exists(Path.of("bar")) && !Path.of("bar").toFile().mkdirs());
                                    }
                                }
                                """,
                        """
                                import com.google.common.collect.ImmutableSet;
                                
                                import java.nio.file.Files;
                                import java.nio.file.Path;
                                
                                class Test {
                                    ImmutableSet<Boolean> test() {
                                        return ImmutableSet.of(Path.of("foo").toFile().mkdirs() || Files.exists(Path.of("foo")), !Path.of("bar").toFile().mkdirs() && !Files.exists(Path.of("bar")));
                                    }
                                }
                                """
                ));
    }
}
