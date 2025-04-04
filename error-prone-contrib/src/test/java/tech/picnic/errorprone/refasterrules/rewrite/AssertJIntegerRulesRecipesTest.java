package tech.picnic.errorprone.refasterrules.rewrite;

import org.junit.jupiter.api.Test;
import org.openrewrite.Recipe;
import org.openrewrite.java.JavaParser;
import org.openrewrite.test.RewriteTest;

import static org.openrewrite.java.Assertions.java;

final class AssertJIntegerRulesRecipesTest implements RewriteTest {

    @Test
    void testAbstractIntegerAssertIsEqualToRecipe() {
        Recipe recipe = RewriteUtils.loadRecipe("AssertJIntegerRulesRecipes.AbstractIntegerAssertIsEqualToRecipe");
        rewriteRun(
                spec -> spec.recipe(recipe)
                        .expectedCyclesThatMakeChanges(1).cycles(1)
                        .parser(JavaParser.fromJavaVersion().classpath(JavaParser.runtimeClasspath())),
                java(
                        """
                                import com.google.common.collect.ImmutableSet;
                                import org.assertj.core.api.AbstractIntegerAssert;
                                import org.assertj.core.api.Assertions;
                                import org.assertj.core.data.Offset;
                                import org.assertj.core.data.Percentage;
                                
                                class Test {
                                    ImmutableSet<AbstractIntegerAssert<?>> test() {
                                        return ImmutableSet.of(Assertions.assertThat(0).isCloseTo(1, Offset.offset(0)), Assertions.assertThat(0).isCloseTo(1, Percentage.withPercentage(0)));
                                    }
                                }
                                """,
                        """
                                import com.google.common.collect.ImmutableSet;
                                import org.assertj.core.api.AbstractIntegerAssert;
                                import org.assertj.core.api.Assertions;
                                
                                class Test {
                                    ImmutableSet<AbstractIntegerAssert<?>> test() {
                                        return ImmutableSet.of(Assertions.assertThat(0).isEqualTo(1), Assertions.assertThat(0).isEqualTo(1));
                                    }
                                }
                                """
                ));
    }

    @Test
    void testAbstractIntegerAssertIsNotEqualToRecipe() {
        Recipe recipe = RewriteUtils.loadRecipe("AssertJIntegerRulesRecipes.AbstractIntegerAssertIsNotEqualToRecipe");
        rewriteRun(
                spec -> spec.recipe(recipe)
                        .expectedCyclesThatMakeChanges(1).cycles(1)
                        .parser(JavaParser.fromJavaVersion().classpath(JavaParser.runtimeClasspath())),
                java(
                        """
                                import com.google.common.collect.ImmutableSet;
                                import org.assertj.core.api.AbstractIntegerAssert;
                                import org.assertj.core.api.Assertions;
                                import org.assertj.core.data.Offset;
                                import org.assertj.core.data.Percentage;
                                
                                class Test {
                                    ImmutableSet<AbstractIntegerAssert<?>> test() {
                                        return ImmutableSet.of(Assertions.assertThat(0).isNotCloseTo(1, Offset.offset(0)), Assertions.assertThat(0).isNotCloseTo(1, Percentage.withPercentage(0)));
                                    }
                                }
                                """,
                        """
                                import com.google.common.collect.ImmutableSet;
                                import org.assertj.core.api.AbstractIntegerAssert;
                                import org.assertj.core.api.Assertions;
                                
                                class Test {
                                    ImmutableSet<AbstractIntegerAssert<?>> test() {
                                        return ImmutableSet.of(Assertions.assertThat(0).isNotEqualTo(1), Assertions.assertThat(0).isNotEqualTo(1));
                                    }
                                }
                                """
                ));
    }

    @Test
    void testAbstractIntegerAssertIsNotZeroRecipe() {
        Recipe recipe = RewriteUtils.loadRecipe("AssertJIntegerRulesRecipes.AbstractIntegerAssertIsNotZeroRecipe");
        rewriteRun(
                spec -> spec.recipe(recipe)
                        .expectedCyclesThatMakeChanges(1).cycles(1)
                        .parser(JavaParser.fromJavaVersion().classpath(JavaParser.runtimeClasspath())),
                java(
                        """
                                import org.assertj.core.api.AbstractIntegerAssert;
                                import org.assertj.core.api.Assertions;
                                
                                class Test {
                                    AbstractIntegerAssert<?> test() {
                                        return Assertions.assertThat(0).isNotZero();
                                    }
                                }
                                """,
                        """
                                import org.assertj.core.api.AbstractIntegerAssert;
                                import org.assertj.core.api.Assertions;
                                
                                class Test {
                                    AbstractIntegerAssert<?> test() {
                                        return Assertions.assertThat(0).isNotEqualTo(0);
                                    }
                                }
                                """
                ));
    }

    @Test
    void testAbstractIntegerAssertIsOneRecipe() {
        Recipe recipe = RewriteUtils.loadRecipe("AssertJIntegerRulesRecipes.AbstractIntegerAssertIsOneRecipe");
        rewriteRun(
                spec -> spec.recipe(recipe)
                        .expectedCyclesThatMakeChanges(1).cycles(1)
                        .parser(JavaParser.fromJavaVersion().classpath(JavaParser.runtimeClasspath())),
                java(
                        """
                                import org.assertj.core.api.AbstractIntegerAssert;
                                import org.assertj.core.api.Assertions;
                                
                                class Test {
                                    AbstractIntegerAssert<?> test() {
                                        return Assertions.assertThat(0).isOne();
                                    }
                                }
                                """,
                        """
                                import org.assertj.core.api.AbstractIntegerAssert;
                                import org.assertj.core.api.Assertions;
                                
                                class Test {
                                    AbstractIntegerAssert<?> test() {
                                        return Assertions.assertThat(0).isEqualTo(1);
                                    }
                                }
                                """
                ));
    }

    @Test
    void testAbstractIntegerAssertIsZeroRecipe() {
        Recipe recipe = RewriteUtils.loadRecipe("AssertJIntegerRulesRecipes.AbstractIntegerAssertIsZeroRecipe");
        rewriteRun(
                spec -> spec.recipe(recipe)
                        .expectedCyclesThatMakeChanges(1).cycles(1)
                        .parser(JavaParser.fromJavaVersion().classpath(JavaParser.runtimeClasspath())),
                java(
                        """
                                import org.assertj.core.api.AbstractIntegerAssert;
                                import org.assertj.core.api.Assertions;
                                
                                class Test {
                                    AbstractIntegerAssert<?> test() {
                                        return Assertions.assertThat(0).isZero();
                                    }
                                }
                                """,
                        """
                                import org.assertj.core.api.AbstractIntegerAssert;
                                import org.assertj.core.api.Assertions;
                                
                                class Test {
                                    AbstractIntegerAssert<?> test() {
                                        return Assertions.assertThat(0).isEqualTo(0);
                                    }
                                }
                                """
                ));
    }
}
