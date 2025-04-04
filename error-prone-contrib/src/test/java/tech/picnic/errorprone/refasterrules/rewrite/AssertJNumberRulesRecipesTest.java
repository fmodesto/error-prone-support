package tech.picnic.errorprone.refasterrules.rewrite;

import org.junit.jupiter.api.Test;
import org.openrewrite.Recipe;
import org.openrewrite.java.JavaParser;
import org.openrewrite.test.RewriteTest;

import static org.openrewrite.java.Assertions.java;

final class AssertJNumberRulesRecipesTest implements RewriteTest {

    @Test
    void testAssertThatIsEvenRecipe() {
        Recipe recipe = RewriteUtils.loadRecipe("AssertJNumberRulesRecipes.AssertThatIsEvenRecipe");
        rewriteRun(
                spec -> spec.recipe(recipe)
                        .expectedCyclesThatMakeChanges(1).cycles(1)
                        .parser(JavaParser.fromJavaVersion().classpath(JavaParser.runtimeClasspath())),
                java(
                        """
                                import com.google.common.collect.ImmutableSet;
                                import org.assertj.core.api.Assertions;
                                import org.assertj.core.api.NumberAssert;
                                
                                class Test {
                                    ImmutableSet<NumberAssert<?, ?>> test() {
                                        return ImmutableSet.of(Assertions.assertThat((byte) 1 % 2).isEqualTo(0), Assertions.assertThat(Byte.valueOf((byte) 1) % 2).isEqualTo(0), Assertions.assertThat((char) 1 % 2).isEqualTo(0), Assertions.assertThat(Character.valueOf((char) 1) % 2).isEqualTo(0), Assertions.assertThat((short) 1 % 2).isEqualTo(0), Assertions.assertThat(Short.valueOf((short) 1) % 2).isEqualTo(0), Assertions.assertThat(1 % 2).isEqualTo(0), Assertions.assertThat(Integer.valueOf(1) % 2).isEqualTo(0), Assertions.assertThat(1L % 2).isEqualTo(0), Assertions.assertThat(Long.valueOf(1) % 2).isEqualTo(0));
                                    }
                                }
                                """,
                        """
                                import com.google.common.collect.ImmutableSet;
                                import org.assertj.core.api.Assertions;
                                import org.assertj.core.api.NumberAssert;
                                
                                class Test {
                                    ImmutableSet<NumberAssert<?, ?>> test() {
                                        return ImmutableSet.of(Assertions.assertThat((byte) 1).isEven(), Assertions.assertThat(Byte.valueOf((byte) 1)).isEven(), Assertions.assertThat((char) 1 % 2).isEqualTo(0), Assertions.assertThat(Character.valueOf((char) 1) % 2).isEqualTo(0), Assertions.assertThat((short) 1).isEven(), Assertions.assertThat(Short.valueOf((short) 1)).isEven(), Assertions.assertThat(1).isEven(), Assertions.assertThat(Integer.valueOf(1)).isEven(), Assertions.assertThat(1L).isEven(), Assertions.assertThat(Long.valueOf(1)).isEven());
                                    }
                                }
                                """
                ));
    }

    @Test
    void testAssertThatIsOddRecipe() {
        Recipe recipe = RewriteUtils.loadRecipe("AssertJNumberRulesRecipes.AssertThatIsOddRecipe");
        rewriteRun(
                spec -> spec.recipe(recipe)
                        .expectedCyclesThatMakeChanges(1).cycles(1)
                        .parser(JavaParser.fromJavaVersion().classpath(JavaParser.runtimeClasspath())),
                java(
                        """
                                import com.google.common.collect.ImmutableSet;
                                import org.assertj.core.api.Assertions;
                                import org.assertj.core.api.NumberAssert;
                                
                                class Test {
                                    ImmutableSet<NumberAssert<?, ?>> test() {
                                        return ImmutableSet.of(Assertions.assertThat((byte) 1 % 2).isEqualTo(1), Assertions.assertThat(Byte.valueOf((byte) 1) % 2).isEqualTo(1), Assertions.assertThat((char) 1 % 2).isEqualTo(1), Assertions.assertThat(Character.valueOf((char) 1) % 2).isEqualTo(1), Assertions.assertThat((short) 1 % 2).isEqualTo(1), Assertions.assertThat(Short.valueOf((short) 1) % 2).isEqualTo(1), Assertions.assertThat(1 % 2).isEqualTo(1), Assertions.assertThat(Integer.valueOf(1) % 2).isEqualTo(1), Assertions.assertThat(1L % 2).isEqualTo(1), Assertions.assertThat(Long.valueOf(1) % 2).isEqualTo(1));
                                    }
                                }
                                """,
                        """
                                import com.google.common.collect.ImmutableSet;
                                import org.assertj.core.api.Assertions;
                                import org.assertj.core.api.NumberAssert;
                                
                                class Test {
                                    ImmutableSet<NumberAssert<?, ?>> test() {
                                        return ImmutableSet.of(Assertions.assertThat((byte) 1).isOdd(), Assertions.assertThat(Byte.valueOf((byte) 1)).isOdd(), Assertions.assertThat((char) 1 % 2).isEqualTo(1), Assertions.assertThat(Character.valueOf((char) 1) % 2).isEqualTo(1), Assertions.assertThat((short) 1).isOdd(), Assertions.assertThat(Short.valueOf((short) 1)).isOdd(), Assertions.assertThat(1).isOdd(), Assertions.assertThat(Integer.valueOf(1)).isOdd(), Assertions.assertThat(1L).isOdd(), Assertions.assertThat(Long.valueOf(1)).isOdd());
                                    }
                                }
                                """
                ));
    }

    @Test
    void testNumberAssertIsNegativeRecipe() {
        Recipe recipe = RewriteUtils.loadRecipe("AssertJNumberRulesRecipes.NumberAssertIsNegativeRecipe");
        rewriteRun(
                spec -> spec.recipe(recipe)
                        .expectedCyclesThatMakeChanges(1).cycles(1)
                        .parser(JavaParser.fromJavaVersion().classpath(JavaParser.runtimeClasspath())),
                java(
                        """
                                import com.google.common.collect.ImmutableSet;
                                import org.assertj.core.api.Assertions;
                                import org.assertj.core.api.NumberAssert;
                                
                                import java.math.BigDecimal;
                                import java.math.BigInteger;
                                
                                class Test {
                                    ImmutableSet<NumberAssert<?, ?>> test() {
                                        return ImmutableSet.of(Assertions.assertThat((byte) 0).isLessThan((byte) 0), Assertions.assertThat((byte) 0).isLessThanOrEqualTo((byte) -1), Assertions.assertThat((short) 0).isLessThan((short) 0), Assertions.assertThat((short) 0).isLessThanOrEqualTo((short) -1), Assertions.assertThat(0).isLessThan(0), Assertions.assertThat(0).isLessThanOrEqualTo(-1), Assertions.assertThat(0L).isLessThan(0), Assertions.assertThat(0L).isLessThanOrEqualTo(-1), Assertions.assertThat(0.0F).isLessThan(0), Assertions.assertThat(0.0).isLessThan(0), Assertions.assertThat(BigInteger.ZERO).isLessThan(BigInteger.ZERO), Assertions.assertThat(BigInteger.ZERO).isLessThanOrEqualTo(BigInteger.valueOf(-1)), Assertions.assertThat(BigDecimal.ZERO).isLessThan(BigDecimal.ZERO));
                                    }
                                }
                                """,
                        """
                                import com.google.common.collect.ImmutableSet;
                                import org.assertj.core.api.Assertions;
                                import org.assertj.core.api.NumberAssert;
                                
                                import java.math.BigDecimal;
                                import java.math.BigInteger;
                                
                                class Test {
                                    ImmutableSet<NumberAssert<?, ?>> test() {
                                        return ImmutableSet.of(Assertions.assertThat((byte) 0).isNegative(), Assertions.assertThat((byte) 0).isNegative(), Assertions.assertThat((short) 0).isNegative(), Assertions.assertThat((short) 0).isNegative(), Assertions.assertThat(0).isNegative(), Assertions.assertThat(0).isNegative(), Assertions.assertThat(0L).isNegative(), Assertions.assertThat(0L).isNegative(), Assertions.assertThat(0.0F).isNegative(), Assertions.assertThat(0.0).isNegative(), Assertions.assertThat(BigInteger.ZERO).isNegative(), Assertions.assertThat(BigInteger.ZERO).isNegative(), Assertions.assertThat(BigDecimal.ZERO).isNegative());
                                    }
                                }
                                """
                ));
    }

    @Test
    void testNumberAssertIsNotNegativeRecipe() {
        Recipe recipe = RewriteUtils.loadRecipe("AssertJNumberRulesRecipes.NumberAssertIsNotNegativeRecipe");
        rewriteRun(
                spec -> spec.recipe(recipe)
                        .expectedCyclesThatMakeChanges(1).cycles(1)
                        .parser(JavaParser.fromJavaVersion().classpath(JavaParser.runtimeClasspath())),
                java(
                        """
                                import com.google.common.collect.ImmutableSet;
                                import org.assertj.core.api.Assertions;
                                import org.assertj.core.api.NumberAssert;
                                
                                import java.math.BigDecimal;
                                import java.math.BigInteger;
                                
                                class Test {
                                    ImmutableSet<NumberAssert<?, ?>> test() {
                                        return ImmutableSet.of(Assertions.assertThat((byte) 0).isGreaterThanOrEqualTo((byte) 0), Assertions.assertThat((byte) 0).isGreaterThan((byte) -1), Assertions.assertThat((short) 0).isGreaterThanOrEqualTo((short) 0), Assertions.assertThat((short) 0).isGreaterThan((short) -1), Assertions.assertThat(0).isGreaterThanOrEqualTo(0), Assertions.assertThat(0).isGreaterThan(-1), Assertions.assertThat(0L).isGreaterThanOrEqualTo(0), Assertions.assertThat(0L).isGreaterThan(-1), Assertions.assertThat(0.0F).isGreaterThanOrEqualTo(0), Assertions.assertThat(0.0).isGreaterThanOrEqualTo(0), Assertions.assertThat(BigInteger.ZERO).isGreaterThanOrEqualTo(BigInteger.ZERO), Assertions.assertThat(BigInteger.ZERO).isGreaterThan(BigInteger.valueOf(-1)), Assertions.assertThat(BigDecimal.ZERO).isGreaterThanOrEqualTo(BigDecimal.ZERO));
                                    }
                                }
                                """,
                        """
                                import com.google.common.collect.ImmutableSet;
                                import org.assertj.core.api.Assertions;
                                import org.assertj.core.api.NumberAssert;
                                
                                import java.math.BigDecimal;
                                import java.math.BigInteger;
                                
                                class Test {
                                    ImmutableSet<NumberAssert<?, ?>> test() {
                                        return ImmutableSet.of(Assertions.assertThat((byte) 0).isNotNegative(), Assertions.assertThat((byte) 0).isNotNegative(), Assertions.assertThat((short) 0).isNotNegative(), Assertions.assertThat((short) 0).isNotNegative(), Assertions.assertThat(0).isNotNegative(), Assertions.assertThat(0).isNotNegative(), Assertions.assertThat(0L).isNotNegative(), Assertions.assertThat(0L).isNotNegative(), Assertions.assertThat(0.0F).isNotNegative(), Assertions.assertThat(0.0).isNotNegative(), Assertions.assertThat(BigInteger.ZERO).isNotNegative(), Assertions.assertThat(BigInteger.ZERO).isNotNegative(), Assertions.assertThat(BigDecimal.ZERO).isNotNegative());
                                    }
                                }
                                """
                ));
    }

    @Test
    void testNumberAssertIsNotPositiveRecipe() {
        Recipe recipe = RewriteUtils.loadRecipe("AssertJNumberRulesRecipes.NumberAssertIsNotPositiveRecipe");
        rewriteRun(
                spec -> spec.recipe(recipe)
                        .expectedCyclesThatMakeChanges(1).cycles(1)
                        .parser(JavaParser.fromJavaVersion().classpath(JavaParser.runtimeClasspath())),
                java(
                        """
                                import com.google.common.collect.ImmutableSet;
                                import org.assertj.core.api.Assertions;
                                import org.assertj.core.api.NumberAssert;
                                
                                import java.math.BigDecimal;
                                import java.math.BigInteger;
                                
                                class Test {
                                    ImmutableSet<NumberAssert<?, ?>> test() {
                                        return ImmutableSet.of(Assertions.assertThat((byte) 0).isLessThanOrEqualTo((byte) 0), Assertions.assertThat((byte) 0).isLessThan((byte) 1), Assertions.assertThat((short) 0).isLessThanOrEqualTo((short) 0), Assertions.assertThat((short) 0).isLessThan((short) 1), Assertions.assertThat(0).isLessThanOrEqualTo(0), Assertions.assertThat(0).isLessThan(1), Assertions.assertThat(0L).isLessThanOrEqualTo(0), Assertions.assertThat(0L).isLessThan(1), Assertions.assertThat(0.0F).isLessThanOrEqualTo(0), Assertions.assertThat(0.0).isLessThanOrEqualTo(0), Assertions.assertThat(BigInteger.ZERO).isLessThanOrEqualTo(BigInteger.ZERO), Assertions.assertThat(BigInteger.ZERO).isLessThan(BigInteger.valueOf(1)), Assertions.assertThat(BigDecimal.ZERO).isLessThanOrEqualTo(BigDecimal.ZERO));
                                    }
                                }
                                """,
                        """
                                import com.google.common.collect.ImmutableSet;
                                import org.assertj.core.api.Assertions;
                                import org.assertj.core.api.NumberAssert;
                                
                                import java.math.BigDecimal;
                                import java.math.BigInteger;
                                
                                class Test {
                                    ImmutableSet<NumberAssert<?, ?>> test() {
                                        return ImmutableSet.of(Assertions.assertThat((byte) 0).isNotPositive(), Assertions.assertThat((byte) 0).isNotPositive(), Assertions.assertThat((short) 0).isNotPositive(), Assertions.assertThat((short) 0).isNotPositive(), Assertions.assertThat(0).isNotPositive(), Assertions.assertThat(0).isNotPositive(), Assertions.assertThat(0L).isNotPositive(), Assertions.assertThat(0L).isNotPositive(), Assertions.assertThat(0.0F).isNotPositive(), Assertions.assertThat(0.0).isNotPositive(), Assertions.assertThat(BigInteger.ZERO).isNotPositive(), Assertions.assertThat(BigInteger.ZERO).isNotPositive(), Assertions.assertThat(BigDecimal.ZERO).isNotPositive());
                                    }
                                }
                                """
                ));
    }

    @Test
    void testNumberAssertIsPositiveRecipe() {
        Recipe recipe = RewriteUtils.loadRecipe("AssertJNumberRulesRecipes.NumberAssertIsPositiveRecipe");
        rewriteRun(
                spec -> spec.recipe(recipe)
                        .expectedCyclesThatMakeChanges(1).cycles(1)
                        .parser(JavaParser.fromJavaVersion().classpath(JavaParser.runtimeClasspath())),
                java(
                        """
                                import com.google.common.collect.ImmutableSet;
                                import org.assertj.core.api.Assertions;
                                import org.assertj.core.api.NumberAssert;
                                
                                import java.math.BigDecimal;
                                import java.math.BigInteger;
                                
                                class Test {
                                    ImmutableSet<NumberAssert<?, ?>> test() {
                                        return ImmutableSet.of(Assertions.assertThat((byte) 0).isGreaterThan((byte) 0), Assertions.assertThat((byte) 0).isGreaterThanOrEqualTo((byte) 1), Assertions.assertThat((short) 0).isGreaterThan((short) 0), Assertions.assertThat((short) 0).isGreaterThanOrEqualTo((short) 1), Assertions.assertThat(0).isGreaterThan(0), Assertions.assertThat(0).isGreaterThanOrEqualTo(1), Assertions.assertThat(0L).isGreaterThan(0), Assertions.assertThat(0L).isGreaterThanOrEqualTo(1), Assertions.assertThat(0.0F).isGreaterThan(0), Assertions.assertThat(0.0).isGreaterThan(0), Assertions.assertThat(BigInteger.ZERO).isGreaterThan(BigInteger.ZERO), Assertions.assertThat(BigInteger.ZERO).isGreaterThanOrEqualTo(BigInteger.valueOf(1)), Assertions.assertThat(BigDecimal.ZERO).isGreaterThan(BigDecimal.ZERO));
                                    }
                                }
                                """,
                        """
                                import com.google.common.collect.ImmutableSet;
                                import org.assertj.core.api.Assertions;
                                import org.assertj.core.api.NumberAssert;
                                
                                import java.math.BigDecimal;
                                import java.math.BigInteger;
                                
                                class Test {
                                    ImmutableSet<NumberAssert<?, ?>> test() {
                                        return ImmutableSet.of(Assertions.assertThat((byte) 0).isPositive(), Assertions.assertThat((byte) 0).isPositive(), Assertions.assertThat((short) 0).isPositive(), Assertions.assertThat((short) 0).isPositive(), Assertions.assertThat(0).isPositive(), Assertions.assertThat(0).isPositive(), Assertions.assertThat(0L).isPositive(), Assertions.assertThat(0L).isPositive(), Assertions.assertThat(0.0F).isPositive(), Assertions.assertThat(0.0).isPositive(), Assertions.assertThat(BigInteger.ZERO).isPositive(), Assertions.assertThat(BigInteger.ZERO).isPositive(), Assertions.assertThat(BigDecimal.ZERO).isPositive());
                                    }
                                }
                                """
                ));
    }
}
