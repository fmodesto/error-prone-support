package tech.picnic.errorprone.refasterrules.rewrite;

import org.junit.jupiter.api.Test;
import org.openrewrite.Recipe;
import org.openrewrite.java.JavaParser;
import org.openrewrite.test.RewriteTest;

import static org.openrewrite.java.Assertions.java;

final class AssertJComparableRulesRecipesTest implements RewriteTest {

    @Test
    void testAssertThatIsEqualByComparingToRecipe() {
        Recipe recipe = RewriteUtils.loadRecipe("AssertJComparableRulesRecipes.AssertThatIsEqualByComparingToRecipe");
        rewriteRun(
                spec -> spec.recipe(recipe)
                        .expectedCyclesThatMakeChanges(1).cycles(1)
                        .parser(JavaParser.fromJavaVersion().classpath(JavaParser.runtimeClasspath())),
                java(
                        """
                                import org.assertj.core.api.AbstractComparableAssert;
                                import org.assertj.core.api.Assertions;
                                
                                import java.math.BigDecimal;
                                
                                class Test {
                                    AbstractComparableAssert<?, ?> test() {
                                        return Assertions.assertThat(BigDecimal.ZERO.compareTo(BigDecimal.ONE)).isEqualTo(0);
                                    }
                                }
                                """,
                        """
                                import org.assertj.core.api.AbstractComparableAssert;
                                import org.assertj.core.api.Assertions;
                                
                                import java.math.BigDecimal;
                                
                                class Test {
                                    AbstractComparableAssert<?, ?> test() {
                                        return Assertions.assertThat(BigDecimal.ZERO).isEqualByComparingTo(BigDecimal.ONE);
                                    }
                                }
                                """
                ));
    }

    @Test
    void testAssertThatIsGreaterThanRecipe() {
        Recipe recipe = RewriteUtils.loadRecipe("AssertJComparableRulesRecipes.AssertThatIsGreaterThanRecipe");
        rewriteRun(
                spec -> spec.recipe(recipe)
                        .expectedCyclesThatMakeChanges(1).cycles(1)
                        .parser(JavaParser.fromJavaVersion().classpath(JavaParser.runtimeClasspath())),
                java(
                        """
                                import org.assertj.core.api.AbstractComparableAssert;
                                import org.assertj.core.api.Assertions;
                                
                                import java.math.BigDecimal;
                                
                                class Test {
                                    AbstractComparableAssert<?, ?> test() {
                                        return Assertions.assertThat(BigDecimal.ZERO.compareTo(BigDecimal.ONE)).isPositive();
                                    }
                                }
                                """,
                        """
                                import org.assertj.core.api.AbstractComparableAssert;
                                import org.assertj.core.api.Assertions;
                                
                                import java.math.BigDecimal;
                                
                                class Test {
                                    AbstractComparableAssert<?, ?> test() {
                                        return Assertions.assertThat(BigDecimal.ZERO).isGreaterThan(BigDecimal.ONE);
                                    }
                                }
                                """
                ));
    }

    @Test
    void testAssertThatIsGreaterThanOrEqualToRecipe() {
        Recipe recipe = RewriteUtils.loadRecipe("AssertJComparableRulesRecipes.AssertThatIsGreaterThanOrEqualToRecipe");
        rewriteRun(
                spec -> spec.recipe(recipe)
                        .expectedCyclesThatMakeChanges(1).cycles(1)
                        .parser(JavaParser.fromJavaVersion().classpath(JavaParser.runtimeClasspath())),
                java(
                        """
                                import org.assertj.core.api.AbstractComparableAssert;
                                import org.assertj.core.api.Assertions;
                                
                                import java.math.BigDecimal;
                                
                                class Test {
                                    AbstractComparableAssert<?, ?> test() {
                                        return Assertions.assertThat(BigDecimal.ZERO.compareTo(BigDecimal.ONE)).isNotNegative();
                                    }
                                }
                                """,
                        """
                                import org.assertj.core.api.AbstractComparableAssert;
                                import org.assertj.core.api.Assertions;
                                
                                import java.math.BigDecimal;
                                
                                class Test {
                                    AbstractComparableAssert<?, ?> test() {
                                        return Assertions.assertThat(BigDecimal.ZERO).isGreaterThanOrEqualTo(BigDecimal.ONE);
                                    }
                                }
                                """
                ));
    }

    @Test
    void testAssertThatIsLessThanRecipe() {
        Recipe recipe = RewriteUtils.loadRecipe("AssertJComparableRulesRecipes.AssertThatIsLessThanRecipe");
        rewriteRun(
                spec -> spec.recipe(recipe)
                        .expectedCyclesThatMakeChanges(1).cycles(1)
                        .parser(JavaParser.fromJavaVersion().classpath(JavaParser.runtimeClasspath())),
                java(
                        """
                                import org.assertj.core.api.AbstractComparableAssert;
                                import org.assertj.core.api.Assertions;
                                
                                import java.math.BigDecimal;
                                
                                class Test {
                                    AbstractComparableAssert<?, ?> test() {
                                        return Assertions.assertThat(BigDecimal.ZERO.compareTo(BigDecimal.ONE)).isNegative();
                                    }
                                }
                                """,
                        """
                                import org.assertj.core.api.AbstractComparableAssert;
                                import org.assertj.core.api.Assertions;
                                
                                import java.math.BigDecimal;
                                
                                class Test {
                                    AbstractComparableAssert<?, ?> test() {
                                        return Assertions.assertThat(BigDecimal.ZERO).isLessThan(BigDecimal.ONE);
                                    }
                                }
                                """
                ));
    }

    @Test
    void testAssertThatIsLessThanOrEqualToRecipe() {
        Recipe recipe = RewriteUtils.loadRecipe("AssertJComparableRulesRecipes.AssertThatIsLessThanOrEqualToRecipe");
        rewriteRun(
                spec -> spec.recipe(recipe)
                        .expectedCyclesThatMakeChanges(1).cycles(1)
                        .parser(JavaParser.fromJavaVersion().classpath(JavaParser.runtimeClasspath())),
                java(
                        """
                                import org.assertj.core.api.AbstractComparableAssert;
                                import org.assertj.core.api.Assertions;
                                
                                import java.math.BigDecimal;
                                
                                class Test {
                                    AbstractComparableAssert<?, ?> test() {
                                        return Assertions.assertThat(BigDecimal.ZERO.compareTo(BigDecimal.ONE)).isNotPositive();
                                    }
                                }
                                """,
                        """
                                import org.assertj.core.api.AbstractComparableAssert;
                                import org.assertj.core.api.Assertions;
                                
                                import java.math.BigDecimal;
                                
                                class Test {
                                    AbstractComparableAssert<?, ?> test() {
                                        return Assertions.assertThat(BigDecimal.ZERO).isLessThanOrEqualTo(BigDecimal.ONE);
                                    }
                                }
                                """
                ));
    }

    @Test
    void testAssertThatIsNotEqualByComparingToRecipe() {
        Recipe recipe = RewriteUtils.loadRecipe("AssertJComparableRulesRecipes.AssertThatIsNotEqualByComparingToRecipe");
        rewriteRun(
                spec -> spec.recipe(recipe)
                        .expectedCyclesThatMakeChanges(1).cycles(1)
                        .parser(JavaParser.fromJavaVersion().classpath(JavaParser.runtimeClasspath())),
                java(
                        """
                                import org.assertj.core.api.AbstractComparableAssert;
                                import org.assertj.core.api.Assertions;
                                
                                import java.math.BigDecimal;
                                
                                class Test {
                                    AbstractComparableAssert<?, ?> test() {
                                        return Assertions.assertThat(BigDecimal.ZERO.compareTo(BigDecimal.ONE)).isNotEqualTo(0);
                                    }
                                }
                                """,
                        """
                                import org.assertj.core.api.AbstractComparableAssert;
                                import org.assertj.core.api.Assertions;
                                
                                import java.math.BigDecimal;
                                
                                class Test {
                                    AbstractComparableAssert<?, ?> test() {
                                        return Assertions.assertThat(BigDecimal.ZERO).isNotEqualByComparingTo(BigDecimal.ONE);
                                    }
                                }
                                """
                ));
    }
}
