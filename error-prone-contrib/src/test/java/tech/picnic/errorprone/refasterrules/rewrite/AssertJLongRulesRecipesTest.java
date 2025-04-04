package tech.picnic.errorprone.refasterrules.rewrite;

import org.junit.jupiter.api.Test;
import org.openrewrite.Recipe;
import org.openrewrite.java.JavaParser;
import org.openrewrite.test.RewriteTest;

import static org.openrewrite.java.Assertions.java;

final class AssertJLongRulesRecipesTest implements RewriteTest {

    @Test
    void testAbstractLongAssertIsEqualToRecipe() {
        Recipe recipe = RewriteUtils.loadRecipe("AssertJLongRulesRecipes.AbstractLongAssertIsEqualToRecipe");
        rewriteRun(
                spec -> spec.recipe(recipe)
                        .expectedCyclesThatMakeChanges(1).cycles(1)
                        .parser(JavaParser.fromJavaVersion().classpath(JavaParser.runtimeClasspath())),
                java(
                        """
                                import com.google.common.collect.ImmutableSet;
                                import org.assertj.core.api.AbstractLongAssert;
                                import org.assertj.core.api.Assertions;
                                import org.assertj.core.data.Offset;
                                import org.assertj.core.data.Percentage;
                                
                                class Test {
                                    ImmutableSet<AbstractLongAssert<?>> test() {
                                        return ImmutableSet.of(Assertions.assertThat(0L).isCloseTo(1, Offset.offset(0L)), Assertions.assertThat(0L).isCloseTo(1, Percentage.withPercentage(0)));
                                    }
                                }
                                """,
                        """
                                import com.google.common.collect.ImmutableSet;
                                import org.assertj.core.api.AbstractLongAssert;
                                import org.assertj.core.api.Assertions;
                                
                                class Test {
                                    ImmutableSet<AbstractLongAssert<?>> test() {
                                        return ImmutableSet.of(Assertions.assertThat(0L).isEqualTo(1), Assertions.assertThat(0L).isEqualTo(1));
                                    }
                                }
                                """
                ));
    }

    @Test
    void testAbstractLongAssertIsNotEqualToRecipe() {
        Recipe recipe = RewriteUtils.loadRecipe("AssertJLongRulesRecipes.AbstractLongAssertIsNotEqualToRecipe");
        rewriteRun(
                spec -> spec.recipe(recipe)
                        .expectedCyclesThatMakeChanges(1).cycles(1)
                        .parser(JavaParser.fromJavaVersion().classpath(JavaParser.runtimeClasspath())),
                java(
                        """
                                import com.google.common.collect.ImmutableSet;
                                import org.assertj.core.api.AbstractLongAssert;
                                import org.assertj.core.api.Assertions;
                                import org.assertj.core.data.Offset;
                                import org.assertj.core.data.Percentage;
                                
                                class Test {
                                    ImmutableSet<AbstractLongAssert<?>> test() {
                                        return ImmutableSet.of(Assertions.assertThat(0L).isNotCloseTo(1, Offset.offset(0L)), Assertions.assertThat(0L).isNotCloseTo(1, Percentage.withPercentage(0)));
                                    }
                                }
                                """,
                        """
                                import com.google.common.collect.ImmutableSet;
                                import org.assertj.core.api.AbstractLongAssert;
                                import org.assertj.core.api.Assertions;
                                
                                class Test {
                                    ImmutableSet<AbstractLongAssert<?>> test() {
                                        return ImmutableSet.of(Assertions.assertThat(0L).isNotEqualTo(1), Assertions.assertThat(0L).isNotEqualTo(1));
                                    }
                                }
                                """
                ));
    }

    @Test
    void testAbstractLongAssertIsNotZeroRecipe() {
        Recipe recipe = RewriteUtils.loadRecipe("AssertJLongRulesRecipes.AbstractLongAssertIsNotZeroRecipe");
        rewriteRun(
                spec -> spec.recipe(recipe)
                        .expectedCyclesThatMakeChanges(1).cycles(1)
                        .parser(JavaParser.fromJavaVersion().classpath(JavaParser.runtimeClasspath())),
                java(
                        """
                                import org.assertj.core.api.AbstractLongAssert;
                                import org.assertj.core.api.Assertions;
                                
                                class Test {
                                    AbstractLongAssert<?> test() {
                                        return Assertions.assertThat(0L).isNotZero();
                                    }
                                }
                                """,
                        """
                                import org.assertj.core.api.AbstractLongAssert;
                                import org.assertj.core.api.Assertions;
                                
                                class Test {
                                    AbstractLongAssert<?> test() {
                                        return Assertions.assertThat(0L).isNotEqualTo(0);
                                    }
                                }
                                """
                ));
    }

    @Test
    void testAbstractLongAssertIsOneRecipe() {
        Recipe recipe = RewriteUtils.loadRecipe("AssertJLongRulesRecipes.AbstractLongAssertIsOneRecipe");
        rewriteRun(
                spec -> spec.recipe(recipe)
                        .expectedCyclesThatMakeChanges(1).cycles(1)
                        .parser(JavaParser.fromJavaVersion().classpath(JavaParser.runtimeClasspath())),
                java(
                        """
                                import org.assertj.core.api.AbstractLongAssert;
                                import org.assertj.core.api.Assertions;
                                
                                class Test {
                                    AbstractLongAssert<?> test() {
                                        return Assertions.assertThat(0L).isOne();
                                    }
                                }
                                """,
                        """
                                import org.assertj.core.api.AbstractLongAssert;
                                import org.assertj.core.api.Assertions;
                                
                                class Test {
                                    AbstractLongAssert<?> test() {
                                        return Assertions.assertThat(0L).isEqualTo(1);
                                    }
                                }
                                """
                ));
    }

    @Test
    void testAbstractLongAssertIsZeroRecipe() {
        Recipe recipe = RewriteUtils.loadRecipe("AssertJLongRulesRecipes.AbstractLongAssertIsZeroRecipe");
        rewriteRun(
                spec -> spec.recipe(recipe)
                        .expectedCyclesThatMakeChanges(1).cycles(1)
                        .parser(JavaParser.fromJavaVersion().classpath(JavaParser.runtimeClasspath())),
                java(
                        """
                                import org.assertj.core.api.AbstractLongAssert;
                                import org.assertj.core.api.Assertions;
                                
                                class Test {
                                    AbstractLongAssert<?> test() {
                                        return Assertions.assertThat(0L).isZero();
                                    }
                                }
                                """,
                        """
                                import org.assertj.core.api.AbstractLongAssert;
                                import org.assertj.core.api.Assertions;
                                
                                class Test {
                                    AbstractLongAssert<?> test() {
                                        return Assertions.assertThat(0L).isEqualTo(0);
                                    }
                                }
                                """
                ));
    }
}
