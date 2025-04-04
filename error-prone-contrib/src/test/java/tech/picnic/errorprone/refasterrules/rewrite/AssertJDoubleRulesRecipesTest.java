package tech.picnic.errorprone.refasterrules.rewrite;

import org.junit.jupiter.api.Test;
import org.openrewrite.Recipe;
import org.openrewrite.java.JavaParser;
import org.openrewrite.test.RewriteTest;

import static org.openrewrite.java.Assertions.java;

final class AssertJDoubleRulesRecipesTest implements RewriteTest {

    @Test
    void testAbstractDoubleAssertIsCloseToWithOffsetRecipe() {
        Recipe recipe = RewriteUtils.loadRecipe("AssertJDoubleRulesRecipes.AbstractDoubleAssertIsCloseToWithOffsetRecipe");
        rewriteRun(
                spec -> spec.recipe(recipe)
                        .expectedCyclesThatMakeChanges(1).cycles(1)
                        .parser(JavaParser.fromJavaVersion().classpath(JavaParser.runtimeClasspath())),
                java(
                        """
                                import com.google.common.collect.ImmutableSet;
                                import org.assertj.core.api.AbstractDoubleAssert;
                                import org.assertj.core.api.Assertions;
                                import org.assertj.core.data.Offset;
                                
                                class Test {
                                    ImmutableSet<AbstractDoubleAssert<?>> test() {
                                        return ImmutableSet.of(Assertions.assertThat(0.0).isEqualTo(1, Offset.offset(0.0)), Assertions.assertThat(0.0).isEqualTo(Double.valueOf(1), Offset.offset(0.0)));
                                    }
                                }
                                """,
                        """
                                import com.google.common.collect.ImmutableSet;
                                import org.assertj.core.api.AbstractDoubleAssert;
                                import org.assertj.core.api.Assertions;
                                import org.assertj.core.data.Offset;
                                
                                class Test {
                                    ImmutableSet<AbstractDoubleAssert<?>> test() {
                                        return ImmutableSet.of(Assertions.assertThat(0.0).isCloseTo(1, Offset.offset(0.0)), Assertions.assertThat(0.0).isCloseTo(Double.valueOf(1), Offset.offset(0.0)));
                                    }
                                }
                                """
                ));
    }

    @Test
    void testAbstractDoubleAssertIsEqualToRecipe() {
        Recipe recipe = RewriteUtils.loadRecipe("AssertJDoubleRulesRecipes.AbstractDoubleAssertIsEqualToRecipe");
        rewriteRun(
                spec -> spec.recipe(recipe)
                        .expectedCyclesThatMakeChanges(1).cycles(1)
                        .parser(JavaParser.fromJavaVersion().classpath(JavaParser.runtimeClasspath())),
                java(
                        """
                                import com.google.common.collect.ImmutableSet;
                                import org.assertj.core.api.AbstractDoubleAssert;
                                import org.assertj.core.api.Assertions;
                                import org.assertj.core.data.Offset;
                                import org.assertj.core.data.Percentage;
                                
                                class Test {
                                    ImmutableSet<AbstractDoubleAssert<?>> test() {
                                        return ImmutableSet.of(Assertions.assertThat(0.0).isCloseTo(1, Offset.offset(0.0)), Assertions.assertThat(0.0).isCloseTo(1, Percentage.withPercentage(0)));
                                    }
                                }
                                """,
                        """
                                import com.google.common.collect.ImmutableSet;
                                import org.assertj.core.api.AbstractDoubleAssert;
                                import org.assertj.core.api.Assertions;
                                
                                class Test {
                                    ImmutableSet<AbstractDoubleAssert<?>> test() {
                                        return ImmutableSet.of(Assertions.assertThat(0.0).isEqualTo(1), Assertions.assertThat(0.0).isEqualTo(1));
                                    }
                                }
                                """
                ));
    }

    @Test
    void testAbstractDoubleAssertIsNotEqualToRecipe() {
        Recipe recipe = RewriteUtils.loadRecipe("AssertJDoubleRulesRecipes.AbstractDoubleAssertIsNotEqualToRecipe");
        rewriteRun(
                spec -> spec.recipe(recipe)
                        .expectedCyclesThatMakeChanges(1).cycles(1)
                        .parser(JavaParser.fromJavaVersion().classpath(JavaParser.runtimeClasspath())),
                java(
                        """
                                import com.google.common.collect.ImmutableSet;
                                import org.assertj.core.api.AbstractDoubleAssert;
                                import org.assertj.core.api.Assertions;
                                import org.assertj.core.data.Offset;
                                import org.assertj.core.data.Percentage;
                                
                                class Test {
                                    ImmutableSet<AbstractDoubleAssert<?>> test() {
                                        return ImmutableSet.of(Assertions.assertThat(0.0).isNotCloseTo(1, Offset.offset(0.0)), Assertions.assertThat(0.0).isNotCloseTo(1, Percentage.withPercentage(0)));
                                    }
                                }
                                """,
                        """
                                import com.google.common.collect.ImmutableSet;
                                import org.assertj.core.api.AbstractDoubleAssert;
                                import org.assertj.core.api.Assertions;
                                
                                class Test {
                                    ImmutableSet<AbstractDoubleAssert<?>> test() {
                                        return ImmutableSet.of(Assertions.assertThat(0.0).isNotEqualTo(1), Assertions.assertThat(0.0).isNotEqualTo(1));
                                    }
                                }
                                """
                ));
    }

    @Test
    void testAbstractDoubleAssertIsNotZeroRecipe() {
        Recipe recipe = RewriteUtils.loadRecipe("AssertJDoubleRulesRecipes.AbstractDoubleAssertIsNotZeroRecipe");
        rewriteRun(
                spec -> spec.recipe(recipe)
                        .expectedCyclesThatMakeChanges(1).cycles(1)
                        .parser(JavaParser.fromJavaVersion().classpath(JavaParser.runtimeClasspath())),
                java(
                        """
                                import org.assertj.core.api.AbstractDoubleAssert;
                                import org.assertj.core.api.Assertions;
                                
                                class Test {
                                    AbstractDoubleAssert<?> test() {
                                        return Assertions.assertThat(0.0).isNotZero();
                                    }
                                }
                                """,
                        """
                                import org.assertj.core.api.AbstractDoubleAssert;
                                import org.assertj.core.api.Assertions;
                                
                                class Test {
                                    AbstractDoubleAssert<?> test() {
                                        return Assertions.assertThat(0.0).isNotEqualTo(0);
                                    }
                                }
                                """
                ));
    }

    @Test
    void testAbstractDoubleAssertIsOneRecipe() {
        Recipe recipe = RewriteUtils.loadRecipe("AssertJDoubleRulesRecipes.AbstractDoubleAssertIsOneRecipe");
        rewriteRun(
                spec -> spec.recipe(recipe)
                        .expectedCyclesThatMakeChanges(1).cycles(1)
                        .parser(JavaParser.fromJavaVersion().classpath(JavaParser.runtimeClasspath())),
                java(
                        """
                                import org.assertj.core.api.AbstractDoubleAssert;
                                import org.assertj.core.api.Assertions;
                                
                                class Test {
                                    AbstractDoubleAssert<?> test() {
                                        return Assertions.assertThat(0.0).isOne();
                                    }
                                }
                                """,
                        """
                                import org.assertj.core.api.AbstractDoubleAssert;
                                import org.assertj.core.api.Assertions;
                                
                                class Test {
                                    AbstractDoubleAssert<?> test() {
                                        return Assertions.assertThat(0.0).isEqualTo(1);
                                    }
                                }
                                """
                ));
    }

    @Test
    void testAbstractDoubleAssertIsZeroRecipe() {
        Recipe recipe = RewriteUtils.loadRecipe("AssertJDoubleRulesRecipes.AbstractDoubleAssertIsZeroRecipe");
        rewriteRun(
                spec -> spec.recipe(recipe)
                        .expectedCyclesThatMakeChanges(1).cycles(1)
                        .parser(JavaParser.fromJavaVersion().classpath(JavaParser.runtimeClasspath())),
                java(
                        """
                                import org.assertj.core.api.AbstractDoubleAssert;
                                import org.assertj.core.api.Assertions;
                                
                                class Test {
                                    AbstractDoubleAssert<?> test() {
                                        return Assertions.assertThat(0.0).isZero();
                                    }
                                }
                                """,
                        """
                                import org.assertj.core.api.AbstractDoubleAssert;
                                import org.assertj.core.api.Assertions;
                                
                                class Test {
                                    AbstractDoubleAssert<?> test() {
                                        return Assertions.assertThat(0.0).isEqualTo(0);
                                    }
                                }
                                """
                ));
    }
}
