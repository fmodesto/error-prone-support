package tech.picnic.errorprone.refasterrules.rewrite;

import org.junit.jupiter.api.Test;
import org.openrewrite.Recipe;
import org.openrewrite.java.JavaParser;
import org.openrewrite.test.RewriteTest;

import static org.openrewrite.java.Assertions.java;

final class AssertJBigIntegerRulesRecipesTest implements RewriteTest {

    @Test
    void testAbstractBigIntegerAssertIsEqualToRecipe() {
        Recipe recipe = RewriteUtils.loadRecipe("AssertJBigIntegerRulesRecipes.AbstractBigIntegerAssertIsEqualToRecipe");
        rewriteRun(
                spec -> spec.recipe(recipe)
                        .expectedCyclesThatMakeChanges(1).cycles(1)
                        .parser(JavaParser.fromJavaVersion().classpath(JavaParser.runtimeClasspath())),
                java(
                        """
                                import com.google.common.collect.ImmutableSet;
                                import org.assertj.core.api.AbstractBigIntegerAssert;
                                import org.assertj.core.api.Assertions;
                                import org.assertj.core.data.Offset;
                                import org.assertj.core.data.Percentage;
                                
                                import java.math.BigInteger;
                                
                                class Test {
                                    ImmutableSet<AbstractBigIntegerAssert<?>> test() {
                                        return ImmutableSet.of(Assertions.assertThat(BigInteger.ZERO).isCloseTo(BigInteger.ONE, Offset.offset(BigInteger.ZERO)), Assertions.assertThat(BigInteger.ZERO).isCloseTo(BigInteger.ONE, Percentage.withPercentage(0)));
                                    }
                                }
                                """,
                        """
                                import com.google.common.collect.ImmutableSet;
                                import org.assertj.core.api.AbstractBigIntegerAssert;
                                import org.assertj.core.api.Assertions;
                                
                                import java.math.BigInteger;
                                
                                class Test {
                                    ImmutableSet<AbstractBigIntegerAssert<?>> test() {
                                        return ImmutableSet.of(Assertions.assertThat(BigInteger.ZERO).isEqualTo(BigInteger.ONE), Assertions.assertThat(BigInteger.ZERO).isEqualTo(BigInteger.ONE));
                                    }
                                }
                                """
                ));
    }

    @Test
    void testAbstractBigIntegerAssertIsNotEqualToRecipe() {
        Recipe recipe = RewriteUtils.loadRecipe("AssertJBigIntegerRulesRecipes.AbstractBigIntegerAssertIsNotEqualToRecipe");
        rewriteRun(
                spec -> spec.recipe(recipe)
                        .expectedCyclesThatMakeChanges(1).cycles(1)
                        .parser(JavaParser.fromJavaVersion().classpath(JavaParser.runtimeClasspath())),
                java(
                        """
                                import com.google.common.collect.ImmutableSet;
                                import org.assertj.core.api.AbstractBigIntegerAssert;
                                import org.assertj.core.api.Assertions;
                                import org.assertj.core.data.Offset;
                                import org.assertj.core.data.Percentage;
                                
                                import java.math.BigInteger;
                                
                                class Test {
                                    ImmutableSet<AbstractBigIntegerAssert<?>> test() {
                                        return ImmutableSet.of(Assertions.assertThat(BigInteger.ZERO).isNotCloseTo(BigInteger.ONE, Offset.offset(BigInteger.ZERO)), Assertions.assertThat(BigInteger.ZERO).isNotCloseTo(BigInteger.ONE, Percentage.withPercentage(0)));
                                    }
                                }
                                """,
                        """
                                import com.google.common.collect.ImmutableSet;
                                import org.assertj.core.api.AbstractBigIntegerAssert;
                                import org.assertj.core.api.Assertions;
                                
                                import java.math.BigInteger;
                                
                                class Test {
                                    ImmutableSet<AbstractBigIntegerAssert<?>> test() {
                                        return ImmutableSet.of(Assertions.assertThat(BigInteger.ZERO).isNotEqualTo(BigInteger.ONE), Assertions.assertThat(BigInteger.ZERO).isNotEqualTo(BigInteger.ONE));
                                    }
                                }
                                """
                ));
    }

    @Test
    void testAbstractBigIntegerAssertIsNotZeroRecipe() {
        Recipe recipe = RewriteUtils.loadRecipe("AssertJBigIntegerRulesRecipes.AbstractBigIntegerAssertIsNotZeroRecipe");
        rewriteRun(
                spec -> spec.recipe(recipe)
                        .expectedCyclesThatMakeChanges(1).cycles(1)
                        .parser(JavaParser.fromJavaVersion().classpath(JavaParser.runtimeClasspath())),
                java(
                        """
                                import com.google.common.collect.ImmutableSet;
                                import org.assertj.core.api.AbstractBigIntegerAssert;
                                import org.assertj.core.api.Assertions;
                                
                                import java.math.BigInteger;
                                
                                class Test {
                                    ImmutableSet<AbstractBigIntegerAssert<?>> test() {
                                        return ImmutableSet.of(Assertions.assertThat(BigInteger.ZERO).isNotZero(), Assertions.assertThat(BigInteger.ZERO).isNotEqualTo(0L), Assertions.assertThat(BigInteger.ZERO).isNotEqualTo(BigInteger.ZERO));
                                    }
                                }
                                """,
                        """
                                import com.google.common.collect.ImmutableSet;
                                import org.assertj.core.api.AbstractBigIntegerAssert;
                                import org.assertj.core.api.Assertions;
                                
                                import java.math.BigInteger;
                                
                                class Test {
                                    ImmutableSet<AbstractBigIntegerAssert<?>> test() {
                                        return ImmutableSet.of(Assertions.assertThat(BigInteger.ZERO).isNotEqualTo(0), Assertions.assertThat(BigInteger.ZERO).isNotEqualTo(0), Assertions.assertThat(BigInteger.ZERO).isNotEqualTo(0));
                                    }
                                }
                                """
                ));
    }

    @Test
    void testAbstractBigIntegerAssertIsOneRecipe() {
        Recipe recipe = RewriteUtils.loadRecipe("AssertJBigIntegerRulesRecipes.AbstractBigIntegerAssertIsOneRecipe");
        rewriteRun(
                spec -> spec.recipe(recipe)
                        .expectedCyclesThatMakeChanges(1).cycles(1)
                        .parser(JavaParser.fromJavaVersion().classpath(JavaParser.runtimeClasspath())),
                java(
                        """
                                import com.google.common.collect.ImmutableSet;
                                import org.assertj.core.api.AbstractBigIntegerAssert;
                                import org.assertj.core.api.Assertions;
                                
                                import java.math.BigInteger;
                                
                                class Test {
                                    ImmutableSet<AbstractBigIntegerAssert<?>> test() {
                                        return ImmutableSet.of(Assertions.assertThat(BigInteger.ZERO).isOne(), Assertions.assertThat(BigInteger.ZERO).isEqualTo(1L), Assertions.assertThat(BigInteger.ZERO).isEqualTo(BigInteger.ONE));
                                    }
                                }
                                """,
                        """
                                import com.google.common.collect.ImmutableSet;
                                import org.assertj.core.api.AbstractBigIntegerAssert;
                                import org.assertj.core.api.Assertions;
                                
                                import java.math.BigInteger;
                                
                                class Test {
                                    ImmutableSet<AbstractBigIntegerAssert<?>> test() {
                                        return ImmutableSet.of(Assertions.assertThat(BigInteger.ZERO).isEqualTo(1), Assertions.assertThat(BigInteger.ZERO).isEqualTo(1), Assertions.assertThat(BigInteger.ZERO).isEqualTo(1));
                                    }
                                }
                                """
                ));
    }

    @Test
    void testAbstractBigIntegerAssertIsZeroRecipe() {
        Recipe recipe = RewriteUtils.loadRecipe("AssertJBigIntegerRulesRecipes.AbstractBigIntegerAssertIsZeroRecipe");
        rewriteRun(
                spec -> spec.recipe(recipe)
                        .expectedCyclesThatMakeChanges(1).cycles(1)
                        .parser(JavaParser.fromJavaVersion().classpath(JavaParser.runtimeClasspath())),
                java(
                        """
                                import com.google.common.collect.ImmutableSet;
                                import org.assertj.core.api.AbstractBigIntegerAssert;
                                import org.assertj.core.api.Assertions;
                                
                                import java.math.BigInteger;
                                
                                class Test {
                                    ImmutableSet<AbstractBigIntegerAssert<?>> test() {
                                        return ImmutableSet.of(Assertions.assertThat(BigInteger.ZERO).isZero(), Assertions.assertThat(BigInteger.ZERO).isEqualTo(0L), Assertions.assertThat(BigInteger.ZERO).isEqualTo(BigInteger.ZERO));
                                    }
                                }
                                """,
                        """
                                import com.google.common.collect.ImmutableSet;
                                import org.assertj.core.api.AbstractBigIntegerAssert;
                                import org.assertj.core.api.Assertions;
                                
                                import java.math.BigInteger;
                                
                                class Test {
                                    ImmutableSet<AbstractBigIntegerAssert<?>> test() {
                                        return ImmutableSet.of(Assertions.assertThat(BigInteger.ZERO).isEqualTo(0), Assertions.assertThat(BigInteger.ZERO).isEqualTo(0), Assertions.assertThat(BigInteger.ZERO).isEqualTo(0));
                                    }
                                }
                                """
                ));
    }
}
