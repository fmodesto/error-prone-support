package tech.picnic.errorprone.refasterrules.rewrite;

import org.junit.jupiter.api.Test;
import org.openrewrite.Recipe;
import org.openrewrite.java.JavaParser;
import org.openrewrite.test.RewriteTest;

import static org.openrewrite.java.Assertions.java;

final class AssertJEnumerableRulesRecipesTest implements RewriteTest {

    @Test
    void testEnumerableAssertHasSameSizeAsRecipe() {
        Recipe recipe = RewriteUtils.loadRecipe("AssertJEnumerableRulesRecipes.EnumerableAssertHasSameSizeAsRecipe");
        rewriteRun(
                spec -> spec.recipe(recipe)
                        .expectedCyclesThatMakeChanges(1).cycles(1)
                        .parser(JavaParser.fromJavaVersion().classpath(JavaParser.runtimeClasspath())),
                java(
                        """
                                import com.google.common.collect.ImmutableSet;
                                import com.google.common.collect.Iterables;
                                import org.assertj.core.api.Assertions;
                                import org.assertj.core.api.EnumerableAssert;
                                
                                class Test {
                                    ImmutableSet<EnumerableAssert<?, Integer>> test() {
                                        return ImmutableSet.of(Assertions.assertThat(ImmutableSet.of(1)).hasSize(Iterables.size(ImmutableSet.of(2))), Assertions.assertThat(ImmutableSet.of(3)).hasSize(ImmutableSet.of(4).size()), Assertions.assertThat(ImmutableSet.of(5)).hasSize(new Integer[0].length));
                                    }
                                }
                                """,
                        """
                                import com.google.common.collect.ImmutableSet;
                                import org.assertj.core.api.Assertions;
                                import org.assertj.core.api.EnumerableAssert;
                                
                                class Test {
                                    ImmutableSet<EnumerableAssert<?, Integer>> test() {
                                        return ImmutableSet.of(Assertions.assertThat(ImmutableSet.of(1)).hasSameSizeAs(ImmutableSet.of(2)), Assertions.assertThat(ImmutableSet.of(3)).hasSameSizeAs(ImmutableSet.of(4)), Assertions.assertThat(ImmutableSet.of(5)).hasSameSizeAs(new Integer[0]));
                                    }
                                }
                                """
                ));
    }

    @Test
    void testEnumerableAssertHasSizeRecipe() {
        Recipe recipe = RewriteUtils.loadRecipe("AssertJEnumerableRulesRecipes.EnumerableAssertHasSizeRecipe");
        rewriteRun(
                spec -> spec.recipe(recipe)
                        .expectedCyclesThatMakeChanges(1).cycles(1)
                        .parser(JavaParser.fromJavaVersion().classpath(JavaParser.runtimeClasspath())),
                java(
                        """
                                import com.google.common.collect.ImmutableSet;
                                import org.assertj.core.api.AbstractAssert;
                                import org.assertj.core.api.Assertions;
                                
                                class Test {
                                    ImmutableSet<AbstractAssert<?, ?>> test() {
                                        return ImmutableSet.of(Assertions.assertThat(ImmutableSet.of(1)).size().isEqualTo(2).returnToIterable(), Assertions.assertThat(ImmutableSet.of(3)).size().isEqualTo(4));
                                    }
                                }
                                """,
                        """
                                import com.google.common.collect.ImmutableSet;
                                import org.assertj.core.api.AbstractAssert;
                                import org.assertj.core.api.Assertions;
                                
                                class Test {
                                    ImmutableSet<AbstractAssert<?, ?>> test() {
                                        return ImmutableSet.of(Assertions.assertThat(ImmutableSet.of(1)).hasSize(2), Assertions.assertThat(ImmutableSet.of(3)).hasSize(4));
                                    }
                                }
                                """
                ));
    }

    @Test
    void testEnumerableAssertHasSizeBetweenRecipe() {
        Recipe recipe = RewriteUtils.loadRecipe("AssertJEnumerableRulesRecipes.EnumerableAssertHasSizeBetweenRecipe");
        rewriteRun(
                spec -> spec.recipe(recipe)
                        .expectedCyclesThatMakeChanges(1).cycles(1)
                        .parser(JavaParser.fromJavaVersion().classpath(JavaParser.runtimeClasspath())),
                java(
                        """
                                import com.google.common.collect.ImmutableSet;
                                import org.assertj.core.api.AbstractAssert;
                                import org.assertj.core.api.Assertions;
                                
                                class Test {
                                    ImmutableSet<AbstractAssert<?, ?>> test() {
                                        return ImmutableSet.of(Assertions.assertThat(ImmutableSet.of(1)).size().isBetween(2, 3).returnToIterable(), Assertions.assertThat(ImmutableSet.of(4)).size().isBetween(5, 6));
                                    }
                                }
                                """,
                        """
                                import com.google.common.collect.ImmutableSet;
                                import org.assertj.core.api.AbstractAssert;
                                import org.assertj.core.api.Assertions;
                                
                                class Test {
                                    ImmutableSet<AbstractAssert<?, ?>> test() {
                                        return ImmutableSet.of(Assertions.assertThat(ImmutableSet.of(1)).hasSizeBetween(2, 3), Assertions.assertThat(ImmutableSet.of(4)).hasSizeBetween(5, 6));
                                    }
                                }
                                """
                ));
    }

    @Test
    void testEnumerableAssertHasSizeGreaterThanRecipe() {
        Recipe recipe = RewriteUtils.loadRecipe("AssertJEnumerableRulesRecipes.EnumerableAssertHasSizeGreaterThanRecipe");
        rewriteRun(
                spec -> spec.recipe(recipe)
                        .expectedCyclesThatMakeChanges(1).cycles(1)
                        .parser(JavaParser.fromJavaVersion().classpath(JavaParser.runtimeClasspath())),
                java(
                        """
                                import com.google.common.collect.ImmutableSet;
                                import org.assertj.core.api.AbstractAssert;
                                import org.assertj.core.api.Assertions;
                                
                                class Test {
                                    ImmutableSet<AbstractAssert<?, ?>> test() {
                                        return ImmutableSet.of(Assertions.assertThat(ImmutableSet.of(1)).size().isGreaterThan(2).returnToIterable(), Assertions.assertThat(ImmutableSet.of(3)).size().isGreaterThan(4));
                                    }
                                }
                                """,
                        """
                                import com.google.common.collect.ImmutableSet;
                                import org.assertj.core.api.AbstractAssert;
                                import org.assertj.core.api.Assertions;
                                
                                class Test {
                                    ImmutableSet<AbstractAssert<?, ?>> test() {
                                        return ImmutableSet.of(Assertions.assertThat(ImmutableSet.of(1)).hasSizeGreaterThan(2), Assertions.assertThat(ImmutableSet.of(3)).hasSizeGreaterThan(4));
                                    }
                                }
                                """
                ));
    }

    @Test
    void testEnumerableAssertHasSizeGreaterThanOrEqualToRecipe() {
        Recipe recipe = RewriteUtils.loadRecipe("AssertJEnumerableRulesRecipes.EnumerableAssertHasSizeGreaterThanOrEqualToRecipe");
        rewriteRun(
                spec -> spec.recipe(recipe)
                        .expectedCyclesThatMakeChanges(1).cycles(1)
                        .parser(JavaParser.fromJavaVersion().classpath(JavaParser.runtimeClasspath())),
                java(
                        """
                                import com.google.common.collect.ImmutableSet;
                                import org.assertj.core.api.AbstractAssert;
                                import org.assertj.core.api.Assertions;
                                
                                class Test {
                                    ImmutableSet<AbstractAssert<?, ?>> test() {
                                        return ImmutableSet.of(Assertions.assertThat(ImmutableSet.of(1)).size().isGreaterThanOrEqualTo(2).returnToIterable(), Assertions.assertThat(ImmutableSet.of(3)).size().isGreaterThanOrEqualTo(4));
                                    }
                                }
                                """,
                        """
                                import com.google.common.collect.ImmutableSet;
                                import org.assertj.core.api.AbstractAssert;
                                import org.assertj.core.api.Assertions;
                                
                                class Test {
                                    ImmutableSet<AbstractAssert<?, ?>> test() {
                                        return ImmutableSet.of(Assertions.assertThat(ImmutableSet.of(1)).hasSizeGreaterThanOrEqualTo(2), Assertions.assertThat(ImmutableSet.of(3)).hasSizeGreaterThanOrEqualTo(4));
                                    }
                                }
                                """
                ));
    }

    @Test
    void testEnumerableAssertHasSizeLessThanRecipe() {
        Recipe recipe = RewriteUtils.loadRecipe("AssertJEnumerableRulesRecipes.EnumerableAssertHasSizeLessThanRecipe");
        rewriteRun(
                spec -> spec.recipe(recipe)
                        .expectedCyclesThatMakeChanges(1).cycles(1)
                        .parser(JavaParser.fromJavaVersion().classpath(JavaParser.runtimeClasspath())),
                java(
                        """
                                import com.google.common.collect.ImmutableSet;
                                import org.assertj.core.api.AbstractAssert;
                                import org.assertj.core.api.Assertions;
                                
                                class Test {
                                    ImmutableSet<AbstractAssert<?, ?>> test() {
                                        return ImmutableSet.of(Assertions.assertThat(ImmutableSet.of(1)).size().isLessThan(2).returnToIterable(), Assertions.assertThat(ImmutableSet.of(3)).size().isLessThan(4));
                                    }
                                }
                                """,
                        """
                                import com.google.common.collect.ImmutableSet;
                                import org.assertj.core.api.AbstractAssert;
                                import org.assertj.core.api.Assertions;
                                
                                class Test {
                                    ImmutableSet<AbstractAssert<?, ?>> test() {
                                        return ImmutableSet.of(Assertions.assertThat(ImmutableSet.of(1)).hasSizeLessThan(2), Assertions.assertThat(ImmutableSet.of(3)).hasSizeLessThan(4));
                                    }
                                }
                                """
                ));
    }

    @Test
    void testEnumerableAssertHasSizeLessThanOrEqualToRecipe() {
        Recipe recipe = RewriteUtils.loadRecipe("AssertJEnumerableRulesRecipes.EnumerableAssertHasSizeLessThanOrEqualToRecipe");
        rewriteRun(
                spec -> spec.recipe(recipe)
                        .expectedCyclesThatMakeChanges(1).cycles(1)
                        .parser(JavaParser.fromJavaVersion().classpath(JavaParser.runtimeClasspath())),
                java(
                        """
                                import com.google.common.collect.ImmutableSet;
                                import org.assertj.core.api.AbstractAssert;
                                import org.assertj.core.api.Assertions;
                                
                                class Test {
                                    ImmutableSet<AbstractAssert<?, ?>> test() {
                                        return ImmutableSet.of(Assertions.assertThat(ImmutableSet.of(1)).size().isLessThanOrEqualTo(2).returnToIterable(), Assertions.assertThat(ImmutableSet.of(3)).size().isLessThanOrEqualTo(4));
                                    }
                                }
                                """,
                        """
                                import com.google.common.collect.ImmutableSet;
                                import org.assertj.core.api.AbstractAssert;
                                import org.assertj.core.api.Assertions;
                                
                                class Test {
                                    ImmutableSet<AbstractAssert<?, ?>> test() {
                                        return ImmutableSet.of(Assertions.assertThat(ImmutableSet.of(1)).hasSizeLessThanOrEqualTo(2), Assertions.assertThat(ImmutableSet.of(3)).hasSizeLessThanOrEqualTo(4));
                                    }
                                }
                                """
                ));
    }

    @Test
    void testEnumerableAssertIsEmptyRecipe() {
        Recipe recipe = RewriteUtils.loadRecipe("AssertJEnumerableRulesRecipes.EnumerableAssertIsEmptyRecipe");
        rewriteRun(
                spec -> spec.recipe(recipe)
                        .expectedCyclesThatMakeChanges(1).cycles(1)
                        .parser(JavaParser.fromJavaVersion().classpath(JavaParser.runtimeClasspath())),
                java(
                        """
                                import com.google.common.collect.ImmutableSet;
                                import org.assertj.core.api.Assertions;
                                
                                class Test {
                                    void test() {
                                        Assertions.assertThat(ImmutableSet.of(1)).hasSize(0);
                                        Assertions.assertThat(ImmutableSet.of(2)).hasSizeLessThanOrEqualTo(0);
                                        Assertions.assertThat(ImmutableSet.of(3)).hasSizeLessThan(1);
                                        Assertions.assertThat(ImmutableSet.of(4)).size().isNotPositive();
                                    }
                                }
                                """,
                        """
                                import com.google.common.collect.ImmutableSet;
                                import org.assertj.core.api.Assertions;
                                
                                class Test {
                                    void test() {
                                        Assertions.assertThat(ImmutableSet.of(1)).isEmpty();
                                        Assertions.assertThat(ImmutableSet.of(2)).isEmpty();
                                        Assertions.assertThat(ImmutableSet.of(3)).isEmpty();
                                        Assertions.assertThat(ImmutableSet.of(4)).isEmpty();
                                    }
                                }
                                """
                ));
    }

    @Test
    void testEnumerableAssertIsNotEmptyRecipe() {
        Recipe recipe = RewriteUtils.loadRecipe("AssertJEnumerableRulesRecipes.EnumerableAssertIsNotEmptyRecipe");
        rewriteRun(
                spec -> spec.recipe(recipe)
                        .expectedCyclesThatMakeChanges(1).cycles(1)
                        .parser(JavaParser.fromJavaVersion().classpath(JavaParser.runtimeClasspath())),
                java(
                        """
                                import com.google.common.collect.ImmutableSet;
                                import org.assertj.core.api.AbstractAssert;
                                import org.assertj.core.api.Assertions;
                                
                                class Test {
                                    ImmutableSet<AbstractAssert<?, ?>> test() {
                                        return ImmutableSet.of(Assertions.assertThat(ImmutableSet.of(1)).hasSizeGreaterThan(0), Assertions.assertThat(ImmutableSet.of(2)).hasSizeGreaterThanOrEqualTo(1), Assertions.assertThat(ImmutableSet.of(3)).size().isNotEqualTo(0).returnToIterable(), Assertions.assertThat(ImmutableSet.of(4)).size().isPositive().returnToIterable(), Assertions.assertThat(ImmutableSet.of(5)).size().isNotEqualTo(0), Assertions.assertThat(ImmutableSet.of(6)).size().isPositive());
                                    }
                                }
                                """,
                        """
                                import com.google.common.collect.ImmutableSet;
                                import org.assertj.core.api.AbstractAssert;
                                import org.assertj.core.api.Assertions;
                                
                                class Test {
                                    ImmutableSet<AbstractAssert<?, ?>> test() {
                                        return ImmutableSet.of(Assertions.assertThat(ImmutableSet.of(1)).isNotEmpty(), Assertions.assertThat(ImmutableSet.of(2)).isNotEmpty(), Assertions.assertThat(ImmutableSet.of(3)).isNotEmpty(), Assertions.assertThat(ImmutableSet.of(4)).isNotEmpty(), Assertions.assertThat(ImmutableSet.of(5)).isNotEmpty(), Assertions.assertThat(ImmutableSet.of(6)).isNotEmpty());
                                    }
                                }
                                """
                ));
    }
}
