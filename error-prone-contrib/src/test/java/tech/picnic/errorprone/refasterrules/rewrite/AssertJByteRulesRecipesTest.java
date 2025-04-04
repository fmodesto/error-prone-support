package tech.picnic.errorprone.refasterrules.rewrite;

import org.junit.jupiter.api.Test;
import org.openrewrite.Recipe;
import org.openrewrite.java.JavaParser;
import org.openrewrite.test.RewriteTest;

import static org.openrewrite.java.Assertions.java;

final class AssertJByteRulesRecipesTest implements RewriteTest {

    @Test
    void testAbstractByteAssertIsEqualToRecipe() {
        Recipe recipe = RewriteUtils.loadRecipe("AssertJByteRulesRecipes.AbstractByteAssertIsEqualToRecipe");
        rewriteRun(
                spec -> spec.recipe(recipe)
                        .expectedCyclesThatMakeChanges(1).cycles(1)
                        .parser(JavaParser.fromJavaVersion().classpath(JavaParser.runtimeClasspath())),
                java(
                        """
                                import com.google.common.collect.ImmutableSet;
                                import org.assertj.core.api.AbstractByteAssert;
                                import org.assertj.core.api.Assertions;
                                import org.assertj.core.data.Offset;
                                import org.assertj.core.data.Percentage;
                                
                                class Test {
                                    ImmutableSet<AbstractByteAssert<?>> test() {
                                        return ImmutableSet.of(Assertions.assertThat((byte) 0).isCloseTo((byte) 1, Offset.offset((byte) 0)), Assertions.assertThat((byte) 0).isCloseTo((byte) 1, Percentage.withPercentage(0)));
                                    }
                                }
                                """,
                        """
                                import com.google.common.collect.ImmutableSet;
                                import org.assertj.core.api.AbstractByteAssert;
                                import org.assertj.core.api.Assertions;
                                
                                class Test {
                                    ImmutableSet<AbstractByteAssert<?>> test() {
                                        return ImmutableSet.of(Assertions.assertThat((byte) 0).isEqualTo((byte) 1), Assertions.assertThat((byte) 0).isEqualTo((byte) 1));
                                    }
                                }
                                """
                ));
    }

    @Test
    void testAbstractByteAssertIsNotEqualToRecipe() {
        Recipe recipe = RewriteUtils.loadRecipe("AssertJByteRulesRecipes.AbstractByteAssertIsNotEqualToRecipe");
        rewriteRun(
                spec -> spec.recipe(recipe)
                        .expectedCyclesThatMakeChanges(1).cycles(1)
                        .parser(JavaParser.fromJavaVersion().classpath(JavaParser.runtimeClasspath())),
                java(
                        """
                                import com.google.common.collect.ImmutableSet;
                                import org.assertj.core.api.AbstractByteAssert;
                                import org.assertj.core.api.Assertions;
                                import org.assertj.core.data.Offset;
                                import org.assertj.core.data.Percentage;
                                
                                class Test {
                                    ImmutableSet<AbstractByteAssert<?>> test() {
                                        return ImmutableSet.of(Assertions.assertThat((byte) 0).isNotCloseTo((byte) 1, Offset.offset((byte) 0)), Assertions.assertThat((byte) 0).isNotCloseTo((byte) 1, Percentage.withPercentage(0)));
                                    }
                                }
                                """,
                        """
                                import com.google.common.collect.ImmutableSet;
                                import org.assertj.core.api.AbstractByteAssert;
                                import org.assertj.core.api.Assertions;
                                
                                class Test {
                                    ImmutableSet<AbstractByteAssert<?>> test() {
                                        return ImmutableSet.of(Assertions.assertThat((byte) 0).isNotEqualTo((byte) 1), Assertions.assertThat((byte) 0).isNotEqualTo((byte) 1));
                                    }
                                }
                                """
                ));
    }

    @Test
    void testAbstractByteAssertIsNotZeroRecipe() {
        Recipe recipe = RewriteUtils.loadRecipe("AssertJByteRulesRecipes.AbstractByteAssertIsNotZeroRecipe");
        rewriteRun(
                spec -> spec.recipe(recipe)
                        .expectedCyclesThatMakeChanges(1).cycles(1)
                        .parser(JavaParser.fromJavaVersion().classpath(JavaParser.runtimeClasspath())),
                java(
                        """
                                import org.assertj.core.api.AbstractByteAssert;
                                import org.assertj.core.api.Assertions;
                                
                                class Test {
                                    AbstractByteAssert<?> test() {
                                        return Assertions.assertThat((byte) 0).isNotZero();
                                    }
                                }
                                """,
                        """
                                import org.assertj.core.api.AbstractByteAssert;
                                import org.assertj.core.api.Assertions;
                                
                                class Test {
                                    AbstractByteAssert<?> test() {
                                        return Assertions.assertThat((byte) 0).isNotEqualTo((byte) 0);
                                    }
                                }
                                """
                ));
    }

    @Test
    void testAbstractByteAssertIsOneRecipe() {
        Recipe recipe = RewriteUtils.loadRecipe("AssertJByteRulesRecipes.AbstractByteAssertIsOneRecipe");
        rewriteRun(
                spec -> spec.recipe(recipe)
                        .expectedCyclesThatMakeChanges(1).cycles(1)
                        .parser(JavaParser.fromJavaVersion().classpath(JavaParser.runtimeClasspath())),
                java(
                        """
                                import org.assertj.core.api.AbstractByteAssert;
                                import org.assertj.core.api.Assertions;
                                
                                class Test {
                                    AbstractByteAssert<?> test() {
                                        return Assertions.assertThat((byte) 0).isOne();
                                    }
                                }
                                """,
                        """
                                import org.assertj.core.api.AbstractByteAssert;
                                import org.assertj.core.api.Assertions;
                                
                                class Test {
                                    AbstractByteAssert<?> test() {
                                        return Assertions.assertThat((byte) 0).isEqualTo((byte) 1);
                                    }
                                }
                                """
                ));
    }

    @Test
    void testAbstractByteAssertIsZeroRecipe() {
        Recipe recipe = RewriteUtils.loadRecipe("AssertJByteRulesRecipes.AbstractByteAssertIsZeroRecipe");
        rewriteRun(
                spec -> spec.recipe(recipe)
                        .expectedCyclesThatMakeChanges(1).cycles(1)
                        .parser(JavaParser.fromJavaVersion().classpath(JavaParser.runtimeClasspath())),
                java(
                        """
                                import org.assertj.core.api.AbstractByteAssert;
                                import org.assertj.core.api.Assertions;
                                
                                class Test {
                                    AbstractByteAssert<?> test() {
                                        return Assertions.assertThat((byte) 0).isZero();
                                    }
                                }
                                """,
                        """
                                import org.assertj.core.api.AbstractByteAssert;
                                import org.assertj.core.api.Assertions;
                                
                                class Test {
                                    AbstractByteAssert<?> test() {
                                        return Assertions.assertThat((byte) 0).isEqualTo((byte) 0);
                                    }
                                }
                                """
                ));
    }
}
