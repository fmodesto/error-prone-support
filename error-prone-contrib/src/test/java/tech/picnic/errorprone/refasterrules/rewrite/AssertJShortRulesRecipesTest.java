package tech.picnic.errorprone.refasterrules.rewrite;

import org.junit.jupiter.api.Test;
import org.openrewrite.Recipe;
import org.openrewrite.java.JavaParser;
import org.openrewrite.test.RewriteTest;

import static org.openrewrite.java.Assertions.java;

final class AssertJShortRulesRecipesTest implements RewriteTest {

    @Test
    void testAbstractShortAssertIsEqualToRecipe() {
        Recipe recipe = RewriteUtils.loadRecipe("AssertJShortRulesRecipes.AbstractShortAssertIsEqualToRecipe");
        rewriteRun(
                spec -> spec.recipe(recipe)
                        .expectedCyclesThatMakeChanges(1).cycles(1)
                        .parser(JavaParser.fromJavaVersion().classpath(JavaParser.runtimeClasspath())),
                java(
                        """
                                import com.google.common.collect.ImmutableSet;
                                import org.assertj.core.api.AbstractShortAssert;
                                import org.assertj.core.api.Assertions;
                                import org.assertj.core.data.Offset;
                                import org.assertj.core.data.Percentage;
                                
                                class Test {
                                    ImmutableSet<AbstractShortAssert<?>> test() {
                                        return ImmutableSet.of(Assertions.assertThat((short) 0).isCloseTo((short) 1, Offset.offset((short) 0)), Assertions.assertThat((short) 0).isCloseTo((short) 1, Percentage.withPercentage(0)));
                                    }
                                }
                                """,
                        """
                                import com.google.common.collect.ImmutableSet;
                                import org.assertj.core.api.AbstractShortAssert;
                                import org.assertj.core.api.Assertions;
                                
                                class Test {
                                    ImmutableSet<AbstractShortAssert<?>> test() {
                                        return ImmutableSet.of(Assertions.assertThat((short) 0).isEqualTo((short) 1), Assertions.assertThat((short) 0).isEqualTo((short) 1));
                                    }
                                }
                                """
                ));
    }

    @Test
    void testAbstractShortAssertIsNotEqualToRecipe() {
        Recipe recipe = RewriteUtils.loadRecipe("AssertJShortRulesRecipes.AbstractShortAssertIsNotEqualToRecipe");
        rewriteRun(
                spec -> spec.recipe(recipe)
                        .expectedCyclesThatMakeChanges(1).cycles(1)
                        .parser(JavaParser.fromJavaVersion().classpath(JavaParser.runtimeClasspath())),
                java(
                        """
                                import com.google.common.collect.ImmutableSet;
                                import org.assertj.core.api.AbstractShortAssert;
                                import org.assertj.core.api.Assertions;
                                import org.assertj.core.data.Offset;
                                import org.assertj.core.data.Percentage;
                                
                                class Test {
                                    ImmutableSet<AbstractShortAssert<?>> test() {
                                        return ImmutableSet.of(Assertions.assertThat((short) 0).isNotCloseTo((short) 1, Offset.offset((short) 0)), Assertions.assertThat((short) 0).isNotCloseTo((short) 1, Percentage.withPercentage(0)));
                                    }
                                }
                                """,
                        """
                                import com.google.common.collect.ImmutableSet;
                                import org.assertj.core.api.AbstractShortAssert;
                                import org.assertj.core.api.Assertions;
                                
                                class Test {
                                    ImmutableSet<AbstractShortAssert<?>> test() {
                                        return ImmutableSet.of(Assertions.assertThat((short) 0).isNotEqualTo((short) 1), Assertions.assertThat((short) 0).isNotEqualTo((short) 1));
                                    }
                                }
                                """
                ));
    }

    @Test
    void testAbstractShortAssertIsNotZeroRecipe() {
        Recipe recipe = RewriteUtils.loadRecipe("AssertJShortRulesRecipes.AbstractShortAssertIsNotZeroRecipe");
        rewriteRun(
                spec -> spec.recipe(recipe)
                        .expectedCyclesThatMakeChanges(1).cycles(1)
                        .parser(JavaParser.fromJavaVersion().classpath(JavaParser.runtimeClasspath())),
                java(
                        """
                                import org.assertj.core.api.AbstractShortAssert;
                                import org.assertj.core.api.Assertions;
                                
                                class Test {
                                    AbstractShortAssert<?> test() {
                                        return Assertions.assertThat((short) 0).isNotZero();
                                    }
                                }
                                """,
                        """
                                import org.assertj.core.api.AbstractShortAssert;
                                import org.assertj.core.api.Assertions;
                                
                                class Test {
                                    AbstractShortAssert<?> test() {
                                        return Assertions.assertThat((short) 0).isNotEqualTo((short) 0);
                                    }
                                }
                                """
                ));
    }

    @Test
    void testAbstractShortAssertIsOneRecipe() {
        Recipe recipe = RewriteUtils.loadRecipe("AssertJShortRulesRecipes.AbstractShortAssertIsOneRecipe");
        rewriteRun(
                spec -> spec.recipe(recipe)
                        .expectedCyclesThatMakeChanges(1).cycles(1)
                        .parser(JavaParser.fromJavaVersion().classpath(JavaParser.runtimeClasspath())),
                java(
                        """
                                import org.assertj.core.api.AbstractShortAssert;
                                import org.assertj.core.api.Assertions;
                                
                                class Test {
                                    AbstractShortAssert<?> test() {
                                        return Assertions.assertThat((short) 0).isOne();
                                    }
                                }
                                """,
                        """
                                import org.assertj.core.api.AbstractShortAssert;
                                import org.assertj.core.api.Assertions;
                                
                                class Test {
                                    AbstractShortAssert<?> test() {
                                        return Assertions.assertThat((short) 0).isEqualTo((short) 1);
                                    }
                                }
                                """
                ));
    }

    @Test
    void testAbstractShortAssertIsZeroRecipe() {
        Recipe recipe = RewriteUtils.loadRecipe("AssertJShortRulesRecipes.AbstractShortAssertIsZeroRecipe");
        rewriteRun(
                spec -> spec.recipe(recipe)
                        .expectedCyclesThatMakeChanges(1).cycles(1)
                        .parser(JavaParser.fromJavaVersion().classpath(JavaParser.runtimeClasspath())),
                java(
                        """
                                import org.assertj.core.api.AbstractShortAssert;
                                import org.assertj.core.api.Assertions;
                                
                                class Test {
                                    AbstractShortAssert<?> test() {
                                        return Assertions.assertThat((short) 0).isZero();
                                    }
                                }
                                """,
                        """
                                import org.assertj.core.api.AbstractShortAssert;
                                import org.assertj.core.api.Assertions;
                                
                                class Test {
                                    AbstractShortAssert<?> test() {
                                        return Assertions.assertThat((short) 0).isEqualTo((short) 0);
                                    }
                                }
                                """
                ));
    }
}
