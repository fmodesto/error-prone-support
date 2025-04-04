package tech.picnic.errorprone.refasterrules.rewrite;

import org.junit.jupiter.api.Test;
import org.openrewrite.Recipe;
import org.openrewrite.java.JavaParser;
import org.openrewrite.test.RewriteTest;

import static org.openrewrite.java.Assertions.java;

final class AssertJMapRulesRecipesTest implements RewriteTest {

    @Test
    void testAbstractMapAssertContainsExactlyEntriesOfRecipe() {
        Recipe recipe = RewriteUtils.loadRecipe("AssertJMapRulesRecipes.AbstractMapAssertContainsExactlyEntriesOfRecipe");
        rewriteRun(
                spec -> spec.recipe(recipe)
                        .expectedCyclesThatMakeChanges(1).cycles(1)
                        .parser(JavaParser.fromJavaVersion().classpath(JavaParser.runtimeClasspath())),
                java(
                        """
                                import com.google.common.collect.ImmutableMap;
                                import org.assertj.core.api.Assertions;
                                import org.assertj.core.api.MapAssert;
                                
                                class Test {
                                    MapAssert<Integer, Integer> test() {
                                        return Assertions.assertThat(ImmutableMap.of(1, 2)).containsExactlyInAnyOrderEntriesOf(ImmutableMap.of(1, 2));
                                    }
                                }
                                """,
                        """
                                import com.google.common.collect.ImmutableMap;
                                import org.assertj.core.api.Assertions;
                                import org.assertj.core.api.MapAssert;
                                
                                class Test {
                                    MapAssert<Integer, Integer> test() {
                                        return Assertions.assertThat(ImmutableMap.of(1, 2)).containsExactlyEntriesOf(ImmutableMap.of(1, 2));
                                    }
                                }
                                """
                ));
    }

    @Test
    void testAbstractMapAssertContainsExactlyInAnyOrderEntriesOfRecipe() {
        Recipe recipe = RewriteUtils.loadRecipe("AssertJMapRulesRecipes.AbstractMapAssertContainsExactlyInAnyOrderEntriesOfRecipe");
        rewriteRun(
                spec -> spec.recipe(recipe)
                        .expectedCyclesThatMakeChanges(1).cycles(1)
                        .parser(JavaParser.fromJavaVersion().classpath(JavaParser.runtimeClasspath())),
                java(
                        """
                                import com.google.common.collect.ImmutableMap;
                                import org.assertj.core.api.Assertions;
                                import org.assertj.core.api.MapAssert;
                                
                                class Test {
                                    MapAssert<Integer, Integer> test() {
                                        return Assertions.assertThat(ImmutableMap.of(1, 2, 3, 4)).isEqualTo(ImmutableMap.of(1, 2, 3, 4));
                                    }
                                }
                                """,
                        """
                                import com.google.common.collect.ImmutableMap;
                                import org.assertj.core.api.Assertions;
                                import org.assertj.core.api.MapAssert;
                                
                                class Test {
                                    MapAssert<Integer, Integer> test() {
                                        return Assertions.assertThat(ImmutableMap.of(1, 2, 3, 4)).containsExactlyInAnyOrderEntriesOf(ImmutableMap.of(1, 2, 3, 4));
                                    }
                                }
                                """
                ));
    }

    @Test
    void testAbstractMapAssertHasSameSizeAsRecipe() {
        Recipe recipe = RewriteUtils.loadRecipe("AssertJMapRulesRecipes.AbstractMapAssertHasSameSizeAsRecipe");
        rewriteRun(
                spec -> spec.recipe(recipe)
                        .expectedCyclesThatMakeChanges(1).cycles(1)
                        .parser(JavaParser.fromJavaVersion().classpath(JavaParser.runtimeClasspath())),
                java(
                        """
                                import com.google.common.collect.ImmutableMap;
                                import org.assertj.core.api.Assertions;
                                import org.assertj.core.api.MapAssert;
                                
                                class Test {
                                    MapAssert<Integer, Integer> test() {
                                        return Assertions.assertThat(ImmutableMap.of(1, 2)).hasSize(ImmutableMap.of(3, 4).size());
                                    }
                                }
                                """,
                        """
                                import com.google.common.collect.ImmutableMap;
                                import org.assertj.core.api.Assertions;
                                import org.assertj.core.api.MapAssert;
                                
                                class Test {
                                    MapAssert<Integer, Integer> test() {
                                        return Assertions.assertThat(ImmutableMap.of(1, 2)).hasSameSizeAs(ImmutableMap.of(3, 4));
                                    }
                                }
                                """
                ));
    }

    @Test
    void testAbstractMapAssertIsEmptyRecipe() {
        Recipe recipe = RewriteUtils.loadRecipe("AssertJMapRulesRecipes.AbstractMapAssertIsEmptyRecipe");
        rewriteRun(
                spec -> spec.recipe(recipe)
                        .expectedCyclesThatMakeChanges(1).cycles(1)
                        .parser(JavaParser.fromJavaVersion().classpath(JavaParser.runtimeClasspath())),
                java(
                        """
                                import com.google.common.collect.ImmutableList;
                                import com.google.common.collect.ImmutableMap;
                                import org.assertj.core.api.Assertions;
                                
                                class Test {
                                    void test() {
                                        Assertions.assertThat(ImmutableMap.of(1, 0)).containsExactlyEntriesOf(ImmutableMap.of());
                                        Assertions.assertThat(ImmutableMap.of(2, 0)).containsExactlyEntriesOf(ImmutableMap.of(1, 2));
                                        Assertions.assertThat(ImmutableMap.of(3, 0)).containsExactlyInAnyOrderEntriesOf(ImmutableMap.of());
                                        Assertions.assertThat(ImmutableMap.of(4, 0)).containsExactlyInAnyOrderEntriesOf(ImmutableMap.of(1, 2, 3, 4));
                                        Assertions.assertThat(ImmutableMap.of(5, 0)).hasSameSizeAs(ImmutableMap.of());
                                        Assertions.assertThat(ImmutableMap.of(6, 0)).hasSameSizeAs(ImmutableMap.of(1, 2));
                                        Assertions.assertThat(ImmutableMap.of(7, 0)).isEqualTo(ImmutableMap.of());
                                        Assertions.assertThat(ImmutableMap.of(8, 0)).isEqualTo(ImmutableMap.of("foo", "bar"));
                                        Assertions.assertThat(ImmutableMap.of(9, 0)).containsOnlyKeys(ImmutableList.of());
                                        Assertions.assertThat(ImmutableMap.of(10, 0)).containsOnlyKeys(ImmutableList.of(1));
                                        Assertions.assertThat(ImmutableMap.of(11, 0)).containsExactly();
                                        Assertions.assertThat(ImmutableMap.of(12, 0)).containsOnly();
                                        Assertions.assertThat(ImmutableMap.of(13, 0)).containsOnlyKeys();
                                    }
                                }
                                """,
                        """
                                import com.google.common.collect.ImmutableList;
                                import com.google.common.collect.ImmutableMap;
                                import org.assertj.core.api.Assertions;
                                
                                class Test {
                                    void test() {
                                        Assertions.assertThat(ImmutableMap.of(1, 0)).isEmpty();
                                        Assertions.assertThat(ImmutableMap.of(2, 0)).containsExactlyEntriesOf(ImmutableMap.of(1, 2));
                                        Assertions.assertThat(ImmutableMap.of(3, 0)).isEmpty();
                                        Assertions.assertThat(ImmutableMap.of(4, 0)).containsExactlyInAnyOrderEntriesOf(ImmutableMap.of(1, 2, 3, 4));
                                        Assertions.assertThat(ImmutableMap.of(5, 0)).isEmpty();
                                        Assertions.assertThat(ImmutableMap.of(6, 0)).hasSameSizeAs(ImmutableMap.of(1, 2));
                                        Assertions.assertThat(ImmutableMap.of(7, 0)).isEmpty();
                                        Assertions.assertThat(ImmutableMap.of(8, 0)).isEqualTo(ImmutableMap.of("foo", "bar"));
                                        Assertions.assertThat(ImmutableMap.of(9, 0)).isEmpty();
                                        Assertions.assertThat(ImmutableMap.of(10, 0)).containsOnlyKeys(ImmutableList.of(1));
                                        Assertions.assertThat(ImmutableMap.of(11, 0)).isEmpty();
                                        Assertions.assertThat(ImmutableMap.of(12, 0)).isEmpty();
                                        Assertions.assertThat(ImmutableMap.of(13, 0)).isEmpty();
                                    }
                                }
                                """
                ));
    }

    @Test
    void testAbstractMapAssertIsNotEmptyRecipe() {
        Recipe recipe = RewriteUtils.loadRecipe("AssertJMapRulesRecipes.AbstractMapAssertIsNotEmptyRecipe");
        rewriteRun(
                spec -> spec.recipe(recipe)
                        .expectedCyclesThatMakeChanges(1).cycles(1)
                        .parser(JavaParser.fromJavaVersion().classpath(JavaParser.runtimeClasspath())),
                java(
                        """
                                import com.google.common.collect.ImmutableMap;
                                import com.google.common.collect.ImmutableSet;
                                import org.assertj.core.api.Assertions;
                                import org.assertj.core.api.MapAssert;
                                
                                class Test {
                                    ImmutableSet<MapAssert<Integer, Integer>> test() {
                                        return ImmutableSet.of(Assertions.assertThat(ImmutableMap.of(1, 0)).isNotEqualTo(ImmutableMap.of()), Assertions.assertThat(ImmutableMap.of(2, 0)).isNotEqualTo(ImmutableMap.of("foo", "bar")));
                                    }
                                }
                                """,
                        """
                                import com.google.common.collect.ImmutableMap;
                                import com.google.common.collect.ImmutableSet;
                                import org.assertj.core.api.Assertions;
                                import org.assertj.core.api.MapAssert;
                                
                                class Test {
                                    ImmutableSet<MapAssert<Integer, Integer>> test() {
                                        return ImmutableSet.of(Assertions.assertThat(ImmutableMap.of(1, 0)).isNotEmpty(), Assertions.assertThat(ImmutableMap.of(2, 0)).isNotEqualTo(ImmutableMap.of("foo", "bar")));
                                    }
                                }
                                """
                ));
    }

    @Test
    void testAssertThatMapContainsEntryRecipe() {
        Recipe recipe = RewriteUtils.loadRecipe("AssertJMapRulesRecipes.AssertThatMapContainsEntryRecipe");
        rewriteRun(
                spec -> spec.recipe(recipe)
                        .expectedCyclesThatMakeChanges(1).cycles(1)
                        .parser(JavaParser.fromJavaVersion().classpath(JavaParser.runtimeClasspath())),
                java(
                        """
                                import com.google.common.collect.ImmutableMap;
                                import org.assertj.core.api.AbstractObjectAssert;
                                import org.assertj.core.api.Assertions;
                                
                                class Test {
                                    AbstractObjectAssert<?, ?> test() {
                                        return Assertions.assertThat(ImmutableMap.of(1, 2).get(1)).isEqualTo(2);
                                    }
                                }
                                """,
                        """
                                import com.google.common.collect.ImmutableMap;
                                import org.assertj.core.api.AbstractObjectAssert;
                                import org.assertj.core.api.Assertions;
                                
                                class Test {
                                    AbstractObjectAssert<?, ?> test() {
                                        return Assertions.assertThat(ImmutableMap.of(1, 2).get(1)).isEqualTo(2);
                                    }
                                }
                                """
                ));
    }

    @Test
    void testAssertThatMapContainsKeyRecipe() {
        Recipe recipe = RewriteUtils.loadRecipe("AssertJMapRulesRecipes.AssertThatMapContainsKeyRecipe");
        rewriteRun(
                spec -> spec.recipe(recipe)
                        .expectedCyclesThatMakeChanges(1).cycles(1)
                        .parser(JavaParser.fromJavaVersion().classpath(JavaParser.runtimeClasspath())),
                java(
                        """
                                import com.google.common.collect.ImmutableMap;
                                import org.assertj.core.api.AbstractAssert;
                                import org.assertj.core.api.Assertions;
                                
                                class Test {
                                    AbstractAssert<?, ?> test() {
                                        return Assertions.assertThat(ImmutableMap.of(1, 2).containsKey(3)).isTrue();
                                    }
                                }
                                """,
                        """
                                import com.google.common.collect.ImmutableMap;
                                import org.assertj.core.api.AbstractAssert;
                                import org.assertj.core.api.Assertions;
                                
                                class Test {
                                    AbstractAssert<?, ?> test() {
                                        return Assertions.assertThat(ImmutableMap.of(1, 2)).containsKey(3);
                                    }
                                }
                                """
                ));
    }

    @Test
    void testAssertThatMapContainsOnlyKeysRecipe() {
        Recipe recipe = RewriteUtils.loadRecipe("AssertJMapRulesRecipes.AssertThatMapContainsOnlyKeysRecipe");
        rewriteRun(
                spec -> spec.recipe(recipe)
                        .expectedCyclesThatMakeChanges(1).cycles(1)
                        .parser(JavaParser.fromJavaVersion().classpath(JavaParser.runtimeClasspath())),
                java(
                        """
                                import com.google.common.collect.ImmutableMap;
                                import com.google.common.collect.ImmutableSet;
                                import org.assertj.core.api.AbstractAssert;
                                import org.assertj.core.api.Assertions;
                                
                                class Test {
                                    AbstractAssert<?, ?> test() {
                                        return Assertions.assertThat(ImmutableMap.of(1, 2).keySet()).hasSameElementsAs(ImmutableSet.of(3));
                                    }
                                }
                                """,
                        """
                                import com.google.common.collect.ImmutableMap;
                                import com.google.common.collect.ImmutableSet;
                                import org.assertj.core.api.AbstractAssert;
                                import org.assertj.core.api.Assertions;
                                
                                class Test {
                                    AbstractAssert<?, ?> test() {
                                        return Assertions.assertThat(ImmutableMap.of(1, 2)).containsOnlyKeys(ImmutableSet.of(3));
                                    }
                                }
                                """
                ));
    }

    @Test
    void testAssertThatMapContainsValueRecipe() {
        Recipe recipe = RewriteUtils.loadRecipe("AssertJMapRulesRecipes.AssertThatMapContainsValueRecipe");
        rewriteRun(
                spec -> spec.recipe(recipe)
                        .expectedCyclesThatMakeChanges(1).cycles(1)
                        .parser(JavaParser.fromJavaVersion().classpath(JavaParser.runtimeClasspath())),
                java(
                        """
                                import com.google.common.collect.ImmutableMap;
                                import org.assertj.core.api.AbstractAssert;
                                import org.assertj.core.api.Assertions;
                                
                                class Test {
                                    AbstractAssert<?, ?> test() {
                                        return Assertions.assertThat(ImmutableMap.of(1, 2).containsValue(3)).isTrue();
                                    }
                                }
                                """,
                        """
                                import com.google.common.collect.ImmutableMap;
                                import org.assertj.core.api.AbstractAssert;
                                import org.assertj.core.api.Assertions;
                                
                                class Test {
                                    AbstractAssert<?, ?> test() {
                                        return Assertions.assertThat(ImmutableMap.of(1, 2)).containsValue(3);
                                    }
                                }
                                """
                ));
    }

    @Test
    void testAssertThatMapDoesNotContainKeyRecipe() {
        Recipe recipe = RewriteUtils.loadRecipe("AssertJMapRulesRecipes.AssertThatMapDoesNotContainKeyRecipe");
        rewriteRun(
                spec -> spec.recipe(recipe)
                        .expectedCyclesThatMakeChanges(1).cycles(1)
                        .parser(JavaParser.fromJavaVersion().classpath(JavaParser.runtimeClasspath())),
                java(
                        """
                                import com.google.common.collect.ImmutableMap;
                                import org.assertj.core.api.AbstractAssert;
                                import org.assertj.core.api.Assertions;
                                
                                class Test {
                                    AbstractAssert<?, ?> test() {
                                        return Assertions.assertThat(ImmutableMap.of(1, 2).containsKey(3)).isFalse();
                                    }
                                }
                                """,
                        """
                                import com.google.common.collect.ImmutableMap;
                                import org.assertj.core.api.AbstractAssert;
                                import org.assertj.core.api.Assertions;
                                
                                class Test {
                                    AbstractAssert<?, ?> test() {
                                        return Assertions.assertThat(ImmutableMap.of(1, 2)).doesNotContainKey(3);
                                    }
                                }
                                """
                ));
    }

    @Test
    void testAssertThatMapDoesNotContainValueRecipe() {
        Recipe recipe = RewriteUtils.loadRecipe("AssertJMapRulesRecipes.AssertThatMapDoesNotContainValueRecipe");
        rewriteRun(
                spec -> spec.recipe(recipe)
                        .expectedCyclesThatMakeChanges(1).cycles(1)
                        .parser(JavaParser.fromJavaVersion().classpath(JavaParser.runtimeClasspath())),
                java(
                        """
                                import com.google.common.collect.ImmutableMap;
                                import org.assertj.core.api.AbstractAssert;
                                import org.assertj.core.api.Assertions;
                                
                                class Test {
                                    AbstractAssert<?, ?> test() {
                                        return Assertions.assertThat(ImmutableMap.of(1, 2).containsValue(3)).isFalse();
                                    }
                                }
                                """,
                        """
                                import com.google.common.collect.ImmutableMap;
                                import org.assertj.core.api.AbstractAssert;
                                import org.assertj.core.api.Assertions;
                                
                                class Test {
                                    AbstractAssert<?, ?> test() {
                                        return Assertions.assertThat(ImmutableMap.of(1, 2)).doesNotContainValue(3);
                                    }
                                }
                                """
                ));
    }

    @Test
    void testAssertThatMapHasSizeRecipe() {
        Recipe recipe = RewriteUtils.loadRecipe("AssertJMapRulesRecipes.AssertThatMapHasSizeRecipe");
        rewriteRun(
                spec -> spec.recipe(recipe)
                        .expectedCyclesThatMakeChanges(1).cycles(1)
                        .parser(JavaParser.fromJavaVersion().classpath(JavaParser.runtimeClasspath())),
                java(
                        """
                                import com.google.common.collect.ImmutableMap;
                                import com.google.common.collect.ImmutableSet;
                                import org.assertj.core.api.AbstractAssert;
                                import org.assertj.core.api.Assertions;
                                
                                class Test {
                                    ImmutableSet<AbstractAssert<?, ?>> test() {
                                        return ImmutableSet.of(Assertions.assertThat(ImmutableMap.of(1, 2).size()).isEqualTo(1), Assertions.assertThat(ImmutableMap.of(3, 4).keySet()).hasSize(1), Assertions.assertThat(ImmutableMap.of(5, 6).values()).hasSize(1), Assertions.assertThat(ImmutableMap.of(7, 8).entrySet()).hasSize(1));
                                    }
                                }
                                """,
                        """
                                import com.google.common.collect.ImmutableMap;
                                import com.google.common.collect.ImmutableSet;
                                import org.assertj.core.api.AbstractAssert;
                                import org.assertj.core.api.Assertions;
                                
                                class Test {
                                    ImmutableSet<AbstractAssert<?, ?>> test() {
                                        return ImmutableSet.of(Assertions.assertThat(ImmutableMap.of(1, 2)).hasSize(1), Assertions.assertThat(ImmutableMap.of(3, 4)).hasSize(1), Assertions.assertThat(ImmutableMap.of(5, 6)).hasSize(1), Assertions.assertThat(ImmutableMap.of(7, 8)).hasSize(1));
                                    }
                                }
                                """
                ));
    }

    @Test
    void testAssertThatMapIsEmptyRecipe() {
        Recipe recipe = RewriteUtils.loadRecipe("AssertJMapRulesRecipes.AssertThatMapIsEmptyRecipe");
        rewriteRun(
                spec -> spec.recipe(recipe)
                        .expectedCyclesThatMakeChanges(1).cycles(1)
                        .parser(JavaParser.fromJavaVersion().classpath(JavaParser.runtimeClasspath())),
                java(
                        """
                                import com.google.common.collect.ImmutableMap;
                                import org.assertj.core.api.Assertions;
                                
                                class Test {
                                    void test() {
                                        Assertions.assertThat(ImmutableMap.of(1, 0)).hasSize(0);
                                        Assertions.assertThat(ImmutableMap.of(2, 0).isEmpty()).isTrue();
                                        Assertions.assertThat(ImmutableMap.of(3, 0).size()).isEqualTo(0L);
                                        Assertions.assertThat(ImmutableMap.of(4, 0).size()).isNotPositive();
                                        Assertions.assertThat(ImmutableMap.of(5, 0).keySet()).isEmpty();
                                        Assertions.assertThat(ImmutableMap.of(6, 0).values()).isEmpty();
                                        Assertions.assertThat(ImmutableMap.of(7, 0).entrySet()).isEmpty();
                                    }
                                }
                                """,
                        """
                                import com.google.common.collect.ImmutableMap;
                                import org.assertj.core.api.Assertions;
                                
                                class Test {
                                    void test() {
                                        Assertions.assertThat(ImmutableMap.of(1, 0)).isEmpty();
                                        Assertions.assertThat(ImmutableMap.of(2, 0)).isEmpty();
                                        Assertions.assertThat(ImmutableMap.of(3, 0)).isEmpty();
                                        Assertions.assertThat(ImmutableMap.of(4, 0)).isEmpty();
                                        Assertions.assertThat(ImmutableMap.of(5, 0)).isEmpty();
                                        Assertions.assertThat(ImmutableMap.of(6, 0)).isEmpty();
                                        Assertions.assertThat(ImmutableMap.of(7, 0)).isEmpty();
                                    }
                                }
                                """
                ));
    }

    @Test
    void testAssertThatMapIsNotEmptyRecipe() {
        Recipe recipe = RewriteUtils.loadRecipe("AssertJMapRulesRecipes.AssertThatMapIsNotEmptyRecipe");
        rewriteRun(
                spec -> spec.recipe(recipe)
                        .expectedCyclesThatMakeChanges(1).cycles(1)
                        .parser(JavaParser.fromJavaVersion().classpath(JavaParser.runtimeClasspath())),
                java(
                        """
                                import com.google.common.collect.ImmutableMap;
                                import com.google.common.collect.ImmutableSet;
                                import org.assertj.core.api.AbstractAssert;
                                import org.assertj.core.api.Assertions;
                                
                                class Test {
                                    ImmutableSet<AbstractAssert<?, ?>> test() {
                                        return ImmutableSet.of(Assertions.assertThat(ImmutableMap.of(1, 0).isEmpty()).isFalse(), Assertions.assertThat(ImmutableMap.of(2, 0).size()).isNotEqualTo(0), Assertions.assertThat(ImmutableMap.of(3, 0).size()).isPositive(), Assertions.assertThat(ImmutableMap.of(4, 0).keySet()).isNotEmpty(), Assertions.assertThat(ImmutableMap.of(5, 0).values()).isNotEmpty(), Assertions.assertThat(ImmutableMap.of(6, 0).entrySet()).isNotEmpty());
                                    }
                                }
                                """,
                        """
                                import com.google.common.collect.ImmutableMap;
                                import com.google.common.collect.ImmutableSet;
                                import org.assertj.core.api.AbstractAssert;
                                import org.assertj.core.api.Assertions;
                                
                                class Test {
                                    ImmutableSet<AbstractAssert<?, ?>> test() {
                                        return ImmutableSet.of(Assertions.assertThat(ImmutableMap.of(1, 0)).isNotEmpty(), Assertions.assertThat(ImmutableMap.of(2, 0)).isNotEmpty(), Assertions.assertThat(ImmutableMap.of(3, 0)).isNotEmpty(), Assertions.assertThat(ImmutableMap.of(4, 0)).isNotEmpty(), Assertions.assertThat(ImmutableMap.of(5, 0)).isNotEmpty(), Assertions.assertThat(ImmutableMap.of(6, 0)).isNotEmpty());
                                    }
                                }
                                """
                ));
    }
}
