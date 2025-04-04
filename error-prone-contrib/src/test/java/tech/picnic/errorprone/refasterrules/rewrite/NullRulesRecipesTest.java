package tech.picnic.errorprone.refasterrules.rewrite;

import org.junit.jupiter.api.Test;
import org.openrewrite.Recipe;
import org.openrewrite.java.JavaParser;
import org.openrewrite.test.RewriteTest;

import static org.openrewrite.java.Assertions.java;

final class NullRulesRecipesTest implements RewriteTest {

    @Test
    void testIsNotNullRecipe() {
        Recipe recipe = RewriteUtils.loadRecipe("NullRulesRecipes.IsNotNullRecipe");
        rewriteRun(
                spec -> spec.recipe(recipe)
                        .expectedCyclesThatMakeChanges(1).cycles(1)
                        .parser(JavaParser.fromJavaVersion().classpath(JavaParser.runtimeClasspath())),
                java(
                        """
                                import com.google.common.collect.ImmutableSet;
                                
                                import java.util.Objects;
                                
                                class Test {
                                    ImmutableSet<Boolean> test() {
                                        return ImmutableSet.of(null != "foo", Objects.nonNull("bar"));
                                    }
                                }
                                """,
                        """
                                import com.google.common.collect.ImmutableSet;
                                
                                class Test {
                                    ImmutableSet<Boolean> test() {
                                        return ImmutableSet.of("foo" != null, "bar" != null);
                                    }
                                }
                                """
                ));
    }

    @Test
    void testIsNullRecipe() {
        Recipe recipe = RewriteUtils.loadRecipe("NullRulesRecipes.IsNullRecipe");
        rewriteRun(
                spec -> spec.recipe(recipe)
                        .expectedCyclesThatMakeChanges(1).cycles(1)
                        .parser(JavaParser.fromJavaVersion().classpath(JavaParser.runtimeClasspath())),
                java(
                        """
                                import com.google.common.collect.ImmutableSet;
                                
                                import java.util.Objects;
                                
                                class Test {
                                    ImmutableSet<Boolean> test() {
                                        return ImmutableSet.of(null == "foo", Objects.isNull("bar"));
                                    }
                                }
                                """,
                        """
                                import com.google.common.collect.ImmutableSet;
                                
                                class Test {
                                    ImmutableSet<Boolean> test() {
                                        return ImmutableSet.of("foo" == null, "bar" == null);
                                    }
                                }
                                """
                ));
    }

    @Test
    void testIsNullFunctionRecipe() {
        Recipe recipe = RewriteUtils.loadRecipe("NullRulesRecipes.IsNullFunctionRecipe");
        rewriteRun(
                spec -> spec.recipe(recipe)
                        .expectedCyclesThatMakeChanges(1).cycles(1)
                        .parser(JavaParser.fromJavaVersion().classpath(JavaParser.runtimeClasspath())),
                java(
                        """
                                import com.google.common.collect.ImmutableSet;
                                
                                import java.util.Objects;
                                import java.util.function.Predicate;
                                
                                class Test {
                                    ImmutableSet<Predicate<String>> test() {
                                        return ImmutableSet.of(s -> s == null, Predicate.not(Objects::nonNull));
                                    }
                                }
                                """,
                        """
                                import com.google.common.collect.ImmutableSet;
                                
                                import java.util.Objects;
                                import java.util.function.Predicate;
                                
                                class Test {
                                    ImmutableSet<Predicate<String>> test() {
                                        return ImmutableSet.of(Objects::isNull, Objects::isNull);
                                    }
                                }
                                """
                ));
    }

    @Test
    void testNonNullFunctionRecipe() {
        Recipe recipe = RewriteUtils.loadRecipe("NullRulesRecipes.NonNullFunctionRecipe");
        rewriteRun(
                spec -> spec.recipe(recipe)
                        .expectedCyclesThatMakeChanges(1).cycles(1)
                        .parser(JavaParser.fromJavaVersion().classpath(JavaParser.runtimeClasspath())),
                java(
                        """
                                import com.google.common.collect.ImmutableSet;
                                
                                import java.util.Objects;
                                import java.util.function.Predicate;
                                
                                class Test {
                                    ImmutableSet<Predicate<String>> test() {
                                        return ImmutableSet.of(s -> s != null, Predicate.not(Objects::isNull));
                                    }
                                }
                                """,
                        """
                                import com.google.common.collect.ImmutableSet;
                                
                                import java.util.Objects;
                                import java.util.function.Predicate;
                                
                                class Test {
                                    ImmutableSet<Predicate<String>> test() {
                                        return ImmutableSet.of(Objects::nonNull, Objects::nonNull);
                                    }
                                }
                                """
                ));
    }

    @Test
    void testRequireNonNullElseRecipe() {
        Recipe recipe = RewriteUtils.loadRecipe("NullRulesRecipes.RequireNonNullElseRecipe");
        rewriteRun(
                spec -> spec.recipe(recipe)
                        .expectedCyclesThatMakeChanges(1).cycles(1)
                        .parser(JavaParser.fromJavaVersion().classpath(JavaParser.runtimeClasspath())),
                java(
                        """
                                import com.google.common.base.MoreObjects;
                                import com.google.common.collect.ImmutableSet;
                                
                                import java.util.Optional;
                                
                                class Test {
                                    ImmutableSet<String> test() {
                                        return ImmutableSet.of(MoreObjects.firstNonNull("foo", "bar"), Optional.ofNullable("baz").orElse("qux"));
                                    }
                                }
                                """,
                        """
                                import com.google.common.collect.ImmutableSet;
                                
                                import java.util.Objects;
                                
                                class Test {
                                    ImmutableSet<String> test() {
                                        return ImmutableSet.of(Objects.requireNonNullElse("foo", "bar"), Objects.requireNonNullElse("baz", "qux"));
                                    }
                                }
                                """
                ));
    }

    @Test
    void testRequireNonNullElseGetRecipe() {
        Recipe recipe = RewriteUtils.loadRecipe("NullRulesRecipes.RequireNonNullElseGetRecipe");
        rewriteRun(
                spec -> spec.recipe(recipe)
                        .expectedCyclesThatMakeChanges(1).cycles(1)
                        .parser(JavaParser.fromJavaVersion().classpath(JavaParser.runtimeClasspath())),
                java(
                        """
                                import java.util.Optional;
                                
                                class Test {
                                    String test() {
                                        return Optional.ofNullable("foo").orElseGet(() -> "bar");
                                    }
                                }
                                """,
                        """
                                import java.util.Objects;
                                
                                class Test {
                                    String test() {
                                        return Objects.requireNonNullElseGet("foo", () -> "bar");
                                    }
                                }
                                """
                ));
    }
}
