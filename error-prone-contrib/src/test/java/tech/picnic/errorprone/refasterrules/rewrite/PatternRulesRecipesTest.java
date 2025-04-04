package tech.picnic.errorprone.refasterrules.rewrite;

import org.junit.jupiter.api.Test;
import org.openrewrite.Recipe;
import org.openrewrite.java.JavaParser;
import org.openrewrite.test.RewriteTest;

import static org.openrewrite.java.Assertions.java;

final class PatternRulesRecipesTest implements RewriteTest {

    @Test
    void testPatternAsPredicateRecipe() {
        Recipe recipe = RewriteUtils.loadRecipe("PatternRulesRecipes.PatternAsPredicateRecipe");
        rewriteRun(
                spec -> spec.recipe(recipe)
                        .expectedCyclesThatMakeChanges(1).cycles(1)
                        .parser(JavaParser.fromJavaVersion().classpath(JavaParser.runtimeClasspath())),
                java(
                        """
                                import com.google.common.base.Predicates;
                                
                                import java.util.function.Predicate;
                                import java.util.regex.Pattern;
                                
                                class Test {
                                    Predicate<?> test() {
                                        return Predicates.contains(Pattern.compile("foo"));
                                    }
                                }
                                """,
                        """
                                import java.util.function.Predicate;
                                import java.util.regex.Pattern;
                                
                                class Test {
                                    Predicate<?> test() {
                                        return Pattern.compile("foo").asPredicate();
                                    }
                                }
                                """
                ));
    }

    @Test
    void testPatternCompileAsPredicateRecipe() {
        Recipe recipe = RewriteUtils.loadRecipe("PatternRulesRecipes.PatternCompileAsPredicateRecipe");
        rewriteRun(
                spec -> spec.recipe(recipe)
                        .expectedCyclesThatMakeChanges(1).cycles(1)
                        .parser(JavaParser.fromJavaVersion().classpath(JavaParser.runtimeClasspath())),
                java(
                        """
                                import com.google.common.base.Predicates;
                                
                                import java.util.function.Predicate;
                                
                                class Test {
                                    Predicate<?> test() {
                                        return Predicates.containsPattern("foo");
                                    }
                                }
                                """,
                        """
                                import java.util.function.Predicate;
                                import java.util.regex.Pattern;
                                
                                class Test {
                                    Predicate<?> test() {
                                        return Pattern.compile("foo").asPredicate();
                                    }
                                }
                                """
                ));
    }
}
