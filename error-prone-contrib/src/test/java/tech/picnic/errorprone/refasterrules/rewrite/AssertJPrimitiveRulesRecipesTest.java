package tech.picnic.errorprone.refasterrules.rewrite;

import org.junit.jupiter.api.Test;
import org.openrewrite.Recipe;
import org.openrewrite.java.JavaParser;
import org.openrewrite.test.RewriteTest;

import static org.openrewrite.java.Assertions.java;

final class AssertJPrimitiveRulesRecipesTest implements RewriteTest {

    @Test
    void testAssertThatIsEqualToRecipe() {
        Recipe recipe = RewriteUtils.loadRecipe("AssertJPrimitiveRulesRecipes.AssertThatIsEqualToRecipe");
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
                                    @SuppressWarnings("SimplifyBooleanExpression")
                                    ImmutableSet<AbstractAssert<?, ?>> test() {
                                        return ImmutableSet.of(Assertions.assertThat(true == false).isTrue(), Assertions.assertThat(true != false).isFalse(), Assertions.assertThat((byte) 1 == (byte) 2).isTrue(), Assertions.assertThat((byte) 1 != (byte) 2).isFalse(), Assertions.assertThat((char) 1 == (char) 2).isTrue(), Assertions.assertThat((char) 1 != (char) 2).isFalse(), Assertions.assertThat((short) 1 == (short) 2).isTrue(), Assertions.assertThat((short) 1 != (short) 2).isFalse(), Assertions.assertThat(1 == 2).isTrue(), Assertions.assertThat(1 != 2).isFalse(), Assertions.assertThat(1L == 2L).isTrue(), Assertions.assertThat(1L != 2L).isFalse(), Assertions.assertThat(1F == 2F).isTrue(), Assertions.assertThat(1F != 2F).isFalse(), Assertions.assertThat(1.0 == 2.0).isTrue(), Assertions.assertThat(1.0 != 2.0).isFalse());
                                    }
                                }
                                """,
                        """
                                import com.google.common.collect.ImmutableSet;
                                import org.assertj.core.api.AbstractAssert;
                                import org.assertj.core.api.Assertions;
                                
                                class Test {
                                    @SuppressWarnings("SimplifyBooleanExpression")
                                    ImmutableSet<AbstractAssert<?, ?>> test() {
                                        return ImmutableSet.of(Assertions.assertThat(true).isEqualTo(false), Assertions.assertThat(true).isEqualTo(false), Assertions.assertThat((byte) 1).isEqualTo((byte) 2), Assertions.assertThat((byte) 1).isEqualTo((byte) 2), Assertions.assertThat((char) 1).isEqualTo((char) 2), Assertions.assertThat((char) 1).isEqualTo((char) 2), Assertions.assertThat((short) 1).isEqualTo((short) 2), Assertions.assertThat((short) 1).isEqualTo((short) 2), Assertions.assertThat(1).isEqualTo(2), Assertions.assertThat(1).isEqualTo(2), Assertions.assertThat(1L).isEqualTo(2L), Assertions.assertThat(1L).isEqualTo(2L), Assertions.assertThat(1F).isEqualTo(2F), Assertions.assertThat(1F).isEqualTo(2F), Assertions.assertThat(1.0).isEqualTo(2.0), Assertions.assertThat(1.0).isEqualTo(2.0));
                                    }
                                }
                                """
                ));
    }

    @Test
    void testAssertThatIsGreaterThanRecipe() {
        Recipe recipe = RewriteUtils.loadRecipe("AssertJPrimitiveRulesRecipes.AssertThatIsGreaterThanRecipe");
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
                                        return ImmutableSet.of(Assertions.assertThat((byte) 1 > (byte) 2).isTrue(), Assertions.assertThat((byte) 1 <= (byte) 2).isFalse(), Assertions.assertThat((char) 1 > (char) 2).isTrue(), Assertions.assertThat((char) 1 <= (char) 2).isFalse(), Assertions.assertThat((short) 1 > (short) 2).isTrue(), Assertions.assertThat((short) 1 <= (short) 2).isFalse(), Assertions.assertThat(1 > 2).isTrue(), Assertions.assertThat(1 <= 2).isFalse(), Assertions.assertThat(1L > 2L).isTrue(), Assertions.assertThat(1L <= 2L).isFalse(), Assertions.assertThat(1F > 2F).isTrue(), Assertions.assertThat(1F <= 2F).isFalse(), Assertions.assertThat(1.0 > 2.0).isTrue(), Assertions.assertThat(1.0 <= 2.0).isFalse());
                                    }
                                }
                                """,
                        """
                                import com.google.common.collect.ImmutableSet;
                                import org.assertj.core.api.AbstractAssert;
                                import org.assertj.core.api.Assertions;
                                
                                class Test {
                                    ImmutableSet<AbstractAssert<?, ?>> test() {
                                        return ImmutableSet.of(Assertions.assertThat((byte) 1).isGreaterThan((byte) 2), Assertions.assertThat((byte) 1).isGreaterThan((byte) 2), Assertions.assertThat((char) 1).isGreaterThan((char) 2), Assertions.assertThat((char) 1).isGreaterThan((char) 2), Assertions.assertThat((short) 1).isGreaterThan((short) 2), Assertions.assertThat((short) 1).isGreaterThan((short) 2), Assertions.assertThat(1).isGreaterThan(2), Assertions.assertThat(1).isGreaterThan(2), Assertions.assertThat(1L).isGreaterThan(2L), Assertions.assertThat(1L).isGreaterThan(2L), Assertions.assertThat(1F).isGreaterThan(2F), Assertions.assertThat(1F).isGreaterThan(2F), Assertions.assertThat(1.0).isGreaterThan(2.0), Assertions.assertThat(1.0).isGreaterThan(2.0));
                                    }
                                }
                                """
                ));
    }

    @Test
    void testAssertThatIsGreaterThanOrEqualToRecipe() {
        Recipe recipe = RewriteUtils.loadRecipe("AssertJPrimitiveRulesRecipes.AssertThatIsGreaterThanOrEqualToRecipe");
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
                                        return ImmutableSet.of(Assertions.assertThat((byte) 1 >= (byte) 2).isTrue(), Assertions.assertThat((byte) 1 < (byte) 2).isFalse(), Assertions.assertThat((char) 1 >= (char) 2).isTrue(), Assertions.assertThat((char) 1 < (char) 2).isFalse(), Assertions.assertThat((short) 1 >= (short) 2).isTrue(), Assertions.assertThat((short) 1 < (short) 2).isFalse(), Assertions.assertThat(1 >= 2).isTrue(), Assertions.assertThat(1 < 2).isFalse(), Assertions.assertThat(1L >= 2L).isTrue(), Assertions.assertThat(1L < 2L).isFalse(), Assertions.assertThat(1F >= 2F).isTrue(), Assertions.assertThat(1F < 2F).isFalse(), Assertions.assertThat(1.0 >= 2.0).isTrue(), Assertions.assertThat(1.0 < 2.0).isFalse());
                                    }
                                }
                                """,
                        """
                                import com.google.common.collect.ImmutableSet;
                                import org.assertj.core.api.AbstractAssert;
                                import org.assertj.core.api.Assertions;
                                
                                class Test {
                                    ImmutableSet<AbstractAssert<?, ?>> test() {
                                        return ImmutableSet.of(Assertions.assertThat((byte) 1).isGreaterThanOrEqualTo((byte) 2), Assertions.assertThat((byte) 1).isGreaterThanOrEqualTo((byte) 2), Assertions.assertThat((char) 1).isGreaterThanOrEqualTo((char) 2), Assertions.assertThat((char) 1).isGreaterThanOrEqualTo((char) 2), Assertions.assertThat((short) 1).isGreaterThanOrEqualTo((short) 2), Assertions.assertThat((short) 1).isGreaterThanOrEqualTo((short) 2), Assertions.assertThat(1).isGreaterThanOrEqualTo(2), Assertions.assertThat(1).isGreaterThanOrEqualTo(2), Assertions.assertThat(1L).isGreaterThanOrEqualTo(2L), Assertions.assertThat(1L).isGreaterThanOrEqualTo(2L), Assertions.assertThat(1F).isGreaterThanOrEqualTo(2F), Assertions.assertThat(1F).isGreaterThanOrEqualTo(2F), Assertions.assertThat(1.0).isGreaterThanOrEqualTo(2.0), Assertions.assertThat(1.0).isGreaterThanOrEqualTo(2.0));
                                    }
                                }
                                """
                ));
    }

    @Test
    void testAssertThatIsLessThanRecipe() {
        Recipe recipe = RewriteUtils.loadRecipe("AssertJPrimitiveRulesRecipes.AssertThatIsLessThanRecipe");
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
                                        return ImmutableSet.of(Assertions.assertThat((byte) 1 < (byte) 2).isTrue(), Assertions.assertThat((byte) 1 >= (byte) 2).isFalse(), Assertions.assertThat((char) 1 < (char) 2).isTrue(), Assertions.assertThat((char) 1 >= (char) 2).isFalse(), Assertions.assertThat((short) 1 < (short) 2).isTrue(), Assertions.assertThat((short) 1 >= (short) 2).isFalse(), Assertions.assertThat(1 < 2).isTrue(), Assertions.assertThat(1 >= 2).isFalse(), Assertions.assertThat(1L < 2L).isTrue(), Assertions.assertThat(1L >= 2L).isFalse(), Assertions.assertThat(1F < 2F).isTrue(), Assertions.assertThat(1F >= 2F).isFalse(), Assertions.assertThat(1.0 < 2.0).isTrue(), Assertions.assertThat(1.0 >= 2.0).isFalse());
                                    }
                                }
                                """,
                        """
                                import com.google.common.collect.ImmutableSet;
                                import org.assertj.core.api.AbstractAssert;
                                import org.assertj.core.api.Assertions;
                                
                                class Test {
                                    ImmutableSet<AbstractAssert<?, ?>> test() {
                                        return ImmutableSet.of(Assertions.assertThat((byte) 1).isLessThan((byte) 2), Assertions.assertThat((byte) 1).isLessThan((byte) 2), Assertions.assertThat((char) 1).isLessThan((char) 2), Assertions.assertThat((char) 1).isLessThan((char) 2), Assertions.assertThat((short) 1).isLessThan((short) 2), Assertions.assertThat((short) 1).isLessThan((short) 2), Assertions.assertThat(1).isLessThan(2), Assertions.assertThat(1).isLessThan(2), Assertions.assertThat(1L).isLessThan(2L), Assertions.assertThat(1L).isLessThan(2L), Assertions.assertThat(1F).isLessThan(2F), Assertions.assertThat(1F).isLessThan(2F), Assertions.assertThat(1.0).isLessThan(2.0), Assertions.assertThat(1.0).isLessThan(2.0));
                                    }
                                }
                                """
                ));
    }

    @Test
    void testAssertThatIsLessThanOrEqualToRecipe() {
        Recipe recipe = RewriteUtils.loadRecipe("AssertJPrimitiveRulesRecipes.AssertThatIsLessThanOrEqualToRecipe");
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
                                        return ImmutableSet.of(Assertions.assertThat((byte) 1 <= (byte) 2).isTrue(), Assertions.assertThat((byte) 1 > (byte) 2).isFalse(), Assertions.assertThat((char) 1 <= (char) 2).isTrue(), Assertions.assertThat((char) 1 > (char) 2).isFalse(), Assertions.assertThat((short) 1 <= (short) 2).isTrue(), Assertions.assertThat((short) 1 > (short) 2).isFalse(), Assertions.assertThat(1 <= 2).isTrue(), Assertions.assertThat(1 > 2).isFalse(), Assertions.assertThat(1L <= 2L).isTrue(), Assertions.assertThat(1L > 2L).isFalse(), Assertions.assertThat(1F <= 2F).isTrue(), Assertions.assertThat(1F > 2F).isFalse(), Assertions.assertThat(1.0 <= 2.0).isTrue(), Assertions.assertThat(1.0 > 2.0).isFalse());
                                    }
                                }
                                """,
                        """
                                import com.google.common.collect.ImmutableSet;
                                import org.assertj.core.api.AbstractAssert;
                                import org.assertj.core.api.Assertions;
                                
                                class Test {
                                    ImmutableSet<AbstractAssert<?, ?>> test() {
                                        return ImmutableSet.of(Assertions.assertThat((byte) 1).isLessThanOrEqualTo((byte) 2), Assertions.assertThat((byte) 1).isLessThanOrEqualTo((byte) 2), Assertions.assertThat((char) 1).isLessThanOrEqualTo((char) 2), Assertions.assertThat((char) 1).isLessThanOrEqualTo((char) 2), Assertions.assertThat((short) 1).isLessThanOrEqualTo((short) 2), Assertions.assertThat((short) 1).isLessThanOrEqualTo((short) 2), Assertions.assertThat(1).isLessThanOrEqualTo(2), Assertions.assertThat(1).isLessThanOrEqualTo(2), Assertions.assertThat(1L).isLessThanOrEqualTo(2L), Assertions.assertThat(1L).isLessThanOrEqualTo(2L), Assertions.assertThat(1F).isLessThanOrEqualTo(2F), Assertions.assertThat(1F).isLessThanOrEqualTo(2F), Assertions.assertThat(1.0).isLessThanOrEqualTo(2.0), Assertions.assertThat(1.0).isLessThanOrEqualTo(2.0));
                                    }
                                }
                                """
                ));
    }

    @Test
    void testAssertThatIsNotEqualToRecipe() {
        Recipe recipe = RewriteUtils.loadRecipe("AssertJPrimitiveRulesRecipes.AssertThatIsNotEqualToRecipe");
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
                                    @SuppressWarnings("SimplifyBooleanExpression")
                                    ImmutableSet<AbstractAssert<?, ?>> test() {
                                        return ImmutableSet.of(Assertions.assertThat(true != false).isTrue(), Assertions.assertThat(true == false).isFalse(), Assertions.assertThat((byte) 1 != (byte) 2).isTrue(), Assertions.assertThat((byte) 1 == (byte) 2).isFalse(), Assertions.assertThat((char) 1 != (char) 2).isTrue(), Assertions.assertThat((char) 1 == (char) 2).isFalse(), Assertions.assertThat((short) 1 != (short) 2).isTrue(), Assertions.assertThat((short) 1 == (short) 2).isFalse(), Assertions.assertThat(1 != 2).isTrue(), Assertions.assertThat(1 == 2).isFalse(), Assertions.assertThat(1L != 2L).isTrue(), Assertions.assertThat(1L == 2L).isFalse(), Assertions.assertThat(1F != 2F).isTrue(), Assertions.assertThat(1F == 2F).isFalse(), Assertions.assertThat(1.0 != 2.0).isTrue(), Assertions.assertThat(1.0 == 2.0).isFalse());
                                    }
                                }
                                """,
                        """
                                import com.google.common.collect.ImmutableSet;
                                import org.assertj.core.api.AbstractAssert;
                                import org.assertj.core.api.Assertions;
                                
                                class Test {
                                    @SuppressWarnings("SimplifyBooleanExpression")
                                    ImmutableSet<AbstractAssert<?, ?>> test() {
                                        return ImmutableSet.of(Assertions.assertThat(true).isNotEqualTo(false), Assertions.assertThat(true).isNotEqualTo(false), Assertions.assertThat((byte) 1).isNotEqualTo((byte) 2), Assertions.assertThat((byte) 1).isNotEqualTo((byte) 2), Assertions.assertThat((char) 1).isNotEqualTo((char) 2), Assertions.assertThat((char) 1).isNotEqualTo((char) 2), Assertions.assertThat((short) 1).isNotEqualTo((short) 2), Assertions.assertThat((short) 1).isNotEqualTo((short) 2), Assertions.assertThat(1).isNotEqualTo(2), Assertions.assertThat(1).isNotEqualTo(2), Assertions.assertThat(1L).isNotEqualTo(2L), Assertions.assertThat(1L).isNotEqualTo(2L), Assertions.assertThat(1F).isNotEqualTo(2F), Assertions.assertThat(1F).isNotEqualTo(2F), Assertions.assertThat(1.0).isNotEqualTo(2.0), Assertions.assertThat(1.0).isNotEqualTo(2.0));
                                    }
                                }
                                """
                ));
    }
}
