package tech.picnic.errorprone.refasterrules.rewrite;

import org.junit.jupiter.api.Test;
import org.openrewrite.Recipe;
import org.openrewrite.java.JavaParser;
import org.openrewrite.test.RewriteTest;

import static org.openrewrite.java.Assertions.java;

final class BigDecimalRulesRecipesTest implements RewriteTest {

    @Test
    void testBigDecimalOneRecipe() {
        Recipe recipe = RewriteUtils.loadRecipe("BigDecimalRulesRecipes.BigDecimalOneRecipe");
        rewriteRun(
                spec -> spec.recipe(recipe)
                        .expectedCyclesThatMakeChanges(1).cycles(1)
                        .parser(JavaParser.fromJavaVersion().classpath(JavaParser.runtimeClasspath())),
                java(
                        """
                                import com.google.common.collect.ImmutableSet;
                                
                                import java.math.BigDecimal;
                                
                                class Test {
                                    ImmutableSet<BigDecimal> test() {
                                        return ImmutableSet.of(BigDecimal.valueOf(1), BigDecimal.valueOf(1L), new BigDecimal("1"));
                                    }
                                }
                                """,
                        """
                                import com.google.common.collect.ImmutableSet;
                                
                                import java.math.BigDecimal;
                                
                                class Test {
                                    ImmutableSet<BigDecimal> test() {
                                        return ImmutableSet.of(BigDecimal.ONE, BigDecimal.ONE, BigDecimal.ONE);
                                    }
                                }
                                """
                ));
    }

    @Test
    void testBigDecimalSignumIsNegativeRecipe() {
        Recipe recipe = RewriteUtils.loadRecipe("BigDecimalRulesRecipes.BigDecimalSignumIsNegativeRecipe");
        rewriteRun(
                spec -> spec.recipe(recipe)
                        .expectedCyclesThatMakeChanges(1).cycles(1)
                        .parser(JavaParser.fromJavaVersion().classpath(JavaParser.runtimeClasspath())),
                java(
                        """
                                import com.google.common.collect.ImmutableSet;
                                
                                import java.math.BigDecimal;
                                
                                class Test {
                                    ImmutableSet<Boolean> test() {
                                        return ImmutableSet.of(BigDecimal.valueOf(1).compareTo(BigDecimal.ZERO) < 0, BigDecimal.ZERO.compareTo(BigDecimal.valueOf(2)) > 0, BigDecimal.valueOf(3).signum() < 0, BigDecimal.valueOf(4).signum() <= -1, BigDecimal.valueOf(5).compareTo(BigDecimal.ZERO) >= 0, BigDecimal.ZERO.compareTo(BigDecimal.valueOf(6)) <= 0, BigDecimal.valueOf(7).signum() >= 0, BigDecimal.valueOf(8).signum() > -1);
                                    }
                                }
                                """,
                        """
                                import com.google.common.collect.ImmutableSet;
                                
                                import java.math.BigDecimal;
                                
                                class Test {
                                    ImmutableSet<Boolean> test() {
                                        return ImmutableSet.of(BigDecimal.valueOf(1).signum() == -1, BigDecimal.valueOf(2).signum() == -1, BigDecimal.valueOf(3).signum() == -1, BigDecimal.valueOf(4).signum() == -1, BigDecimal.valueOf(5).signum() != -1, BigDecimal.valueOf(6).signum() != -1, BigDecimal.valueOf(7).signum() != -1, BigDecimal.valueOf(8).signum() != -1);
                                    }
                                }
                                """
                ));
    }

    @Test
    void testBigDecimalSignumIsPositiveRecipe() {
        Recipe recipe = RewriteUtils.loadRecipe("BigDecimalRulesRecipes.BigDecimalSignumIsPositiveRecipe");
        rewriteRun(
                spec -> spec.recipe(recipe)
                        .expectedCyclesThatMakeChanges(1).cycles(1)
                        .parser(JavaParser.fromJavaVersion().classpath(JavaParser.runtimeClasspath())),
                java(
                        """
                                import com.google.common.collect.ImmutableSet;
                                
                                import java.math.BigDecimal;
                                
                                class Test {
                                    ImmutableSet<Boolean> test() {
                                        return ImmutableSet.of(BigDecimal.valueOf(1).compareTo(BigDecimal.ZERO) > 0, BigDecimal.ZERO.compareTo(BigDecimal.valueOf(2)) < 0, BigDecimal.valueOf(3).signum() > 0, BigDecimal.valueOf(4).signum() >= 1, BigDecimal.valueOf(5).compareTo(BigDecimal.ZERO) <= 0, BigDecimal.ZERO.compareTo(BigDecimal.valueOf(6)) >= 0, BigDecimal.valueOf(7).signum() <= 0, BigDecimal.valueOf(8).signum() < 1);
                                    }
                                }
                                """,
                        """
                                import com.google.common.collect.ImmutableSet;
                                
                                import java.math.BigDecimal;
                                
                                class Test {
                                    ImmutableSet<Boolean> test() {
                                        return ImmutableSet.of(BigDecimal.valueOf(1).signum() == 1, BigDecimal.valueOf(2).signum() == 1, BigDecimal.valueOf(3).signum() == 1, BigDecimal.valueOf(4).signum() == 1, BigDecimal.valueOf(5).signum() != 1, BigDecimal.valueOf(6).signum() != 1, BigDecimal.valueOf(7).signum() != 1, BigDecimal.valueOf(8).signum() != 1);
                                    }
                                }
                                """
                ));
    }

    @Test
    void testBigDecimalSignumIsZeroRecipe() {
        Recipe recipe = RewriteUtils.loadRecipe("BigDecimalRulesRecipes.BigDecimalSignumIsZeroRecipe");
        rewriteRun(
                spec -> spec.recipe(recipe)
                        .expectedCyclesThatMakeChanges(1).cycles(1)
                        .parser(JavaParser.fromJavaVersion().classpath(JavaParser.runtimeClasspath())),
                java(
                        """
                                import com.google.common.collect.ImmutableSet;
                                
                                import java.math.BigDecimal;
                                
                                class Test {
                                    ImmutableSet<Boolean> test() {
                                        return ImmutableSet.of(BigDecimal.valueOf(1).compareTo(BigDecimal.ZERO) == 0, BigDecimal.ZERO.compareTo(BigDecimal.valueOf(2)) == 0, BigDecimal.valueOf(3).compareTo(BigDecimal.ZERO) != 0, BigDecimal.ZERO.compareTo(BigDecimal.valueOf(4)) != 0);
                                    }
                                }
                                """,
                        """
                                import com.google.common.collect.ImmutableSet;
                                
                                import java.math.BigDecimal;
                                
                                class Test {
                                    ImmutableSet<Boolean> test() {
                                        return ImmutableSet.of(BigDecimal.valueOf(1).signum() == 0, BigDecimal.valueOf(2).signum() == 0, BigDecimal.valueOf(3).signum() != 0, BigDecimal.valueOf(4).signum() != 0);
                                    }
                                }
                                """
                ));
    }

    @Test
    void testBigDecimalTenRecipe() {
        Recipe recipe = RewriteUtils.loadRecipe("BigDecimalRulesRecipes.BigDecimalTenRecipe");
        rewriteRun(
                spec -> spec.recipe(recipe)
                        .expectedCyclesThatMakeChanges(1).cycles(1)
                        .parser(JavaParser.fromJavaVersion().classpath(JavaParser.runtimeClasspath())),
                java(
                        """
                                import com.google.common.collect.ImmutableSet;
                                
                                import java.math.BigDecimal;
                                
                                class Test {
                                    ImmutableSet<BigDecimal> test() {
                                        return ImmutableSet.of(BigDecimal.valueOf(10), BigDecimal.valueOf(10L), new BigDecimal("10"));
                                    }
                                }
                                """,
                        """
                                import com.google.common.collect.ImmutableSet;
                                
                                import java.math.BigDecimal;
                                
                                class Test {
                                    ImmutableSet<BigDecimal> test() {
                                        return ImmutableSet.of(BigDecimal.TEN, BigDecimal.TEN, BigDecimal.TEN);
                                    }
                                }
                                """
                ));
    }

    @Test
    void testBigDecimalValueOfRecipe() {
        Recipe recipe = RewriteUtils.loadRecipe("BigDecimalRulesRecipes.BigDecimalValueOfRecipe");
        rewriteRun(
                spec -> spec.recipe(recipe)
                        .expectedCyclesThatMakeChanges(1).cycles(1)
                        .parser(JavaParser.fromJavaVersion().classpath(JavaParser.runtimeClasspath())),
                java(
                        """
                                import com.google.common.collect.ImmutableSet;
                                
                                import java.math.BigDecimal;
                                
                                class Test {
                                    ImmutableSet<BigDecimal> test() {
                                        return ImmutableSet.of(new BigDecimal(2), new BigDecimal(2L), new BigDecimal(2.0));
                                    }
                                }
                                """,
                        """
                                import com.google.common.collect.ImmutableSet;
                                
                                import java.math.BigDecimal;
                                
                                class Test {
                                    ImmutableSet<BigDecimal> test() {
                                        return ImmutableSet.of(BigDecimal.valueOf(2), BigDecimal.valueOf(2L), BigDecimal.valueOf(2.0));
                                    }
                                }
                                """
                ));
    }

    @Test
    void testBigDecimalZeroRecipe() {
        Recipe recipe = RewriteUtils.loadRecipe("BigDecimalRulesRecipes.BigDecimalZeroRecipe");
        rewriteRun(
                spec -> spec.recipe(recipe)
                        .expectedCyclesThatMakeChanges(1).cycles(1)
                        .parser(JavaParser.fromJavaVersion().classpath(JavaParser.runtimeClasspath())),
                java(
                        """
                                import com.google.common.collect.ImmutableSet;
                                
                                import java.math.BigDecimal;
                                
                                class Test {
                                    ImmutableSet<BigDecimal> test() {
                                        return ImmutableSet.of(BigDecimal.valueOf(0), BigDecimal.valueOf(0L), new BigDecimal("0"));
                                    }
                                }
                                """,
                        """
                                import com.google.common.collect.ImmutableSet;
                                
                                import java.math.BigDecimal;
                                
                                class Test {
                                    ImmutableSet<BigDecimal> test() {
                                        return ImmutableSet.of(BigDecimal.ZERO, BigDecimal.ZERO, BigDecimal.ZERO);
                                    }
                                }
                                """
                ));
    }
}
