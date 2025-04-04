package tech.picnic.errorprone.refasterrules.rewrite;

import org.junit.jupiter.api.Test;
import org.openrewrite.Recipe;
import org.openrewrite.java.JavaParser;
import org.openrewrite.test.RewriteTest;

import static org.openrewrite.java.Assertions.java;

final class AssertJBigDecimalRulesRecipesTest implements RewriteTest {

    @Test
    void testAbstractBigDecimalAssertIsEqualByComparingToRecipe() {
        Recipe recipe = RewriteUtils.loadRecipe("AssertJBigDecimalRulesRecipes.AbstractBigDecimalAssertIsEqualByComparingToRecipe");
        rewriteRun(
                spec -> spec.recipe(recipe)
                        .expectedCyclesThatMakeChanges(1).cycles(1)
                        .parser(JavaParser.fromJavaVersion().classpath(JavaParser.runtimeClasspath())),
                java(
                        """
                                import com.google.common.collect.ImmutableSet;
                                import org.assertj.core.api.AbstractBigDecimalAssert;
                                import org.assertj.core.api.Assertions;
                                import org.assertj.core.data.Offset;
                                import org.assertj.core.data.Percentage;
                                
                                import java.math.BigDecimal;
                                
                                class Test {
                                    ImmutableSet<AbstractBigDecimalAssert<?>> test() {
                                        return ImmutableSet.of(Assertions.assertThat(BigDecimal.ZERO).isCloseTo(BigDecimal.ONE, Offset.offset(BigDecimal.ZERO)), Assertions.assertThat(BigDecimal.ZERO).isCloseTo(BigDecimal.ONE, Percentage.withPercentage(0)));
                                    }
                                }
                                """,
                        """
                                import com.google.common.collect.ImmutableSet;
                                import org.assertj.core.api.AbstractBigDecimalAssert;
                                import org.assertj.core.api.Assertions;
                                
                                import java.math.BigDecimal;
                                
                                class Test {
                                    ImmutableSet<AbstractBigDecimalAssert<?>> test() {
                                        return ImmutableSet.of(Assertions.assertThat(BigDecimal.ZERO).isEqualByComparingTo(BigDecimal.ONE), Assertions.assertThat(BigDecimal.ZERO).isEqualByComparingTo(BigDecimal.ONE));
                                    }
                                }
                                """
                ));
    }

    @Test
    void testAbstractBigDecimalAssertIsNotEqualByComparingToRecipe() {
        Recipe recipe = RewriteUtils.loadRecipe("AssertJBigDecimalRulesRecipes.AbstractBigDecimalAssertIsNotEqualByComparingToRecipe");
        rewriteRun(
                spec -> spec.recipe(recipe)
                        .expectedCyclesThatMakeChanges(1).cycles(1)
                        .parser(JavaParser.fromJavaVersion().classpath(JavaParser.runtimeClasspath())),
                java(
                        """
                                import com.google.common.collect.ImmutableSet;
                                import org.assertj.core.api.AbstractBigDecimalAssert;
                                import org.assertj.core.api.Assertions;
                                import org.assertj.core.data.Offset;
                                import org.assertj.core.data.Percentage;
                                
                                import java.math.BigDecimal;
                                
                                class Test {
                                    ImmutableSet<AbstractBigDecimalAssert<?>> test() {
                                        return ImmutableSet.of(Assertions.assertThat(BigDecimal.ZERO).isNotCloseTo(BigDecimal.ONE, Offset.offset(BigDecimal.ZERO)), Assertions.assertThat(BigDecimal.ZERO).isNotCloseTo(BigDecimal.ONE, Percentage.withPercentage(0)));
                                    }
                                }
                                """,
                        """
                                import com.google.common.collect.ImmutableSet;
                                import org.assertj.core.api.AbstractBigDecimalAssert;
                                import org.assertj.core.api.Assertions;
                                
                                import java.math.BigDecimal;
                                
                                class Test {
                                    ImmutableSet<AbstractBigDecimalAssert<?>> test() {
                                        return ImmutableSet.of(Assertions.assertThat(BigDecimal.ZERO).isNotEqualByComparingTo(BigDecimal.ONE), Assertions.assertThat(BigDecimal.ZERO).isNotEqualByComparingTo(BigDecimal.ONE));
                                    }
                                }
                                """
                ));
    }
}
