package tech.picnic.errorprone.refasterrules.rewrite;

import org.junit.jupiter.api.Test;
import org.openrewrite.Recipe;
import org.openrewrite.java.JavaParser;
import org.openrewrite.test.RewriteTest;

import static org.openrewrite.java.Assertions.java;

final class AssertJFloatRulesRecipesTest implements RewriteTest {

    @Test
    void testAbstractFloatAssertIsCloseToWithOffsetRecipe() {
        Recipe recipe = RewriteUtils.loadRecipe("AssertJFloatRulesRecipes.AbstractFloatAssertIsCloseToWithOffsetRecipe");
        rewriteRun(
                spec -> spec.recipe(recipe)
                        .expectedCyclesThatMakeChanges(1).cycles(1)
                        .parser(JavaParser.fromJavaVersion().classpath(JavaParser.runtimeClasspath())),
                java(
                        """
                                import com.google.common.collect.ImmutableSet;
                                import org.assertj.core.api.AbstractFloatAssert;
                                import org.assertj.core.api.Assertions;
                                import org.assertj.core.data.Offset;
                                
                                class Test {
                                    ImmutableSet<AbstractFloatAssert<?>> test() {
                                        return ImmutableSet.of(Assertions.assertThat(0F).isEqualTo(1, Offset.offset(0F)), Assertions.assertThat(0F).isEqualTo(Float.valueOf(1), Offset.offset(0F)));
                                    }
                                }
                                """,
                        """
                                import com.google.common.collect.ImmutableSet;
                                import org.assertj.core.api.AbstractFloatAssert;
                                import org.assertj.core.api.Assertions;
                                import org.assertj.core.data.Offset;
                                
                                class Test {
                                    ImmutableSet<AbstractFloatAssert<?>> test() {
                                        return ImmutableSet.of(Assertions.assertThat(0F).isCloseTo(1, Offset.offset(0F)), Assertions.assertThat(0F).isCloseTo(Float.valueOf(1), Offset.offset(0F)));
                                    }
                                }
                                """
                ));
    }

    @Test
    void testAbstractFloatAssertIsEqualToRecipe() {
        Recipe recipe = RewriteUtils.loadRecipe("AssertJFloatRulesRecipes.AbstractFloatAssertIsEqualToRecipe");
        rewriteRun(
                spec -> spec.recipe(recipe)
                        .expectedCyclesThatMakeChanges(1).cycles(1)
                        .parser(JavaParser.fromJavaVersion().classpath(JavaParser.runtimeClasspath())),
                java(
                        """
                                import com.google.common.collect.ImmutableSet;
                                import org.assertj.core.api.AbstractFloatAssert;
                                import org.assertj.core.api.Assertions;
                                import org.assertj.core.data.Offset;
                                import org.assertj.core.data.Percentage;
                                
                                class Test {
                                    ImmutableSet<AbstractFloatAssert<?>> test() {
                                        return ImmutableSet.of(Assertions.assertThat(0F).isCloseTo(1, Offset.offset(0F)), Assertions.assertThat(0F).isCloseTo(1, Percentage.withPercentage(0)));
                                    }
                                }
                                """,
                        """
                                import com.google.common.collect.ImmutableSet;
                                import org.assertj.core.api.AbstractFloatAssert;
                                import org.assertj.core.api.Assertions;
                                
                                class Test {
                                    ImmutableSet<AbstractFloatAssert<?>> test() {
                                        return ImmutableSet.of(Assertions.assertThat(0F).isEqualTo(1), Assertions.assertThat(0F).isEqualTo(1));
                                    }
                                }
                                """
                ));
    }

    @Test
    void testAbstractFloatAssertIsNotEqualToRecipe() {
        Recipe recipe = RewriteUtils.loadRecipe("AssertJFloatRulesRecipes.AbstractFloatAssertIsNotEqualToRecipe");
        rewriteRun(
                spec -> spec.recipe(recipe)
                        .expectedCyclesThatMakeChanges(1).cycles(1)
                        .parser(JavaParser.fromJavaVersion().classpath(JavaParser.runtimeClasspath())),
                java(
                        """
                                import com.google.common.collect.ImmutableSet;
                                import org.assertj.core.api.AbstractFloatAssert;
                                import org.assertj.core.api.Assertions;
                                import org.assertj.core.data.Offset;
                                import org.assertj.core.data.Percentage;
                                
                                class Test {
                                    ImmutableSet<AbstractFloatAssert<?>> test() {
                                        return ImmutableSet.of(Assertions.assertThat(0F).isNotCloseTo(1, Offset.offset(0F)), Assertions.assertThat(0F).isNotCloseTo(1, Percentage.withPercentage(0)));
                                    }
                                }
                                """,
                        """
                                import com.google.common.collect.ImmutableSet;
                                import org.assertj.core.api.AbstractFloatAssert;
                                import org.assertj.core.api.Assertions;
                                
                                class Test {
                                    ImmutableSet<AbstractFloatAssert<?>> test() {
                                        return ImmutableSet.of(Assertions.assertThat(0F).isNotEqualTo(1), Assertions.assertThat(0F).isNotEqualTo(1));
                                    }
                                }
                                """
                ));
    }

    @Test
    void testAbstractFloatAssertIsNotZeroRecipe() {
        Recipe recipe = RewriteUtils.loadRecipe("AssertJFloatRulesRecipes.AbstractFloatAssertIsNotZeroRecipe");
        rewriteRun(
                spec -> spec.recipe(recipe)
                        .expectedCyclesThatMakeChanges(1).cycles(1)
                        .parser(JavaParser.fromJavaVersion().classpath(JavaParser.runtimeClasspath())),
                java(
                        """
                                import org.assertj.core.api.AbstractFloatAssert;
                                import org.assertj.core.api.Assertions;
                                
                                class Test {
                                    AbstractFloatAssert<?> test() {
                                        return Assertions.assertThat(0F).isNotZero();
                                    }
                                }
                                """,
                        """
                                import org.assertj.core.api.AbstractFloatAssert;
                                import org.assertj.core.api.Assertions;
                                
                                class Test {
                                    AbstractFloatAssert<?> test() {
                                        return Assertions.assertThat(0F).isNotEqualTo(0);
                                    }
                                }
                                """
                ));
    }

    @Test
    void testAbstractFloatAssertIsOneRecipe() {
        Recipe recipe = RewriteUtils.loadRecipe("AssertJFloatRulesRecipes.AbstractFloatAssertIsOneRecipe");
        rewriteRun(
                spec -> spec.recipe(recipe)
                        .expectedCyclesThatMakeChanges(1).cycles(1)
                        .parser(JavaParser.fromJavaVersion().classpath(JavaParser.runtimeClasspath())),
                java(
                        """
                                import org.assertj.core.api.AbstractFloatAssert;
                                import org.assertj.core.api.Assertions;
                                
                                class Test {
                                    AbstractFloatAssert<?> test() {
                                        return Assertions.assertThat(0F).isOne();
                                    }
                                }
                                """,
                        """
                                import org.assertj.core.api.AbstractFloatAssert;
                                import org.assertj.core.api.Assertions;
                                
                                class Test {
                                    AbstractFloatAssert<?> test() {
                                        return Assertions.assertThat(0F).isEqualTo(1);
                                    }
                                }
                                """
                ));
    }

    @Test
    void testAbstractFloatAssertIsZeroRecipe() {
        Recipe recipe = RewriteUtils.loadRecipe("AssertJFloatRulesRecipes.AbstractFloatAssertIsZeroRecipe");
        rewriteRun(
                spec -> spec.recipe(recipe)
                        .expectedCyclesThatMakeChanges(1).cycles(1)
                        .parser(JavaParser.fromJavaVersion().classpath(JavaParser.runtimeClasspath())),
                java(
                        """
                                import org.assertj.core.api.AbstractFloatAssert;
                                import org.assertj.core.api.Assertions;
                                
                                class Test {
                                    AbstractFloatAssert<?> test() {
                                        return Assertions.assertThat(0F).isZero();
                                    }
                                }
                                """,
                        """
                                import org.assertj.core.api.AbstractFloatAssert;
                                import org.assertj.core.api.Assertions;
                                
                                class Test {
                                    AbstractFloatAssert<?> test() {
                                        return Assertions.assertThat(0F).isEqualTo(0);
                                    }
                                }
                                """
                ));
    }
}
