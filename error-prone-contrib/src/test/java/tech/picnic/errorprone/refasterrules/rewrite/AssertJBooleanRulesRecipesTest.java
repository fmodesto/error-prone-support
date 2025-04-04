package tech.picnic.errorprone.refasterrules.rewrite;

import org.junit.jupiter.api.Test;
import org.openrewrite.Recipe;
import org.openrewrite.java.JavaParser;
import org.openrewrite.test.RewriteTest;

import static org.openrewrite.java.Assertions.java;

final class AssertJBooleanRulesRecipesTest implements RewriteTest {

    @Test
    void testAbstractBooleanAssertIsEqualToRecipe() {
        Recipe recipe = RewriteUtils.loadRecipe("AssertJBooleanRulesRecipes.AbstractBooleanAssertIsEqualToRecipe");
        rewriteRun(
                spec -> spec.recipe(recipe)
                        .expectedCyclesThatMakeChanges(1).cycles(1)
                        .parser(JavaParser.fromJavaVersion().classpath(JavaParser.runtimeClasspath())),
                java(
                        """
                                import org.assertj.core.api.AbstractBooleanAssert;
                                import org.assertj.core.api.Assertions;
                                
                                class Test {
                                    AbstractBooleanAssert<?> test() {
                                        return Assertions.assertThat(true).isNotEqualTo(!Boolean.FALSE);
                                    }
                                }
                                """,
                        """
                                import org.assertj.core.api.AbstractBooleanAssert;
                                import org.assertj.core.api.Assertions;
                                
                                class Test {
                                    AbstractBooleanAssert<?> test() {
                                        return Assertions.assertThat(true).isEqualTo(Boolean.FALSE);
                                    }
                                }
                                """
                ));
    }

    @Test
    void testAbstractBooleanAssertIsFalseRecipe() {
        Recipe recipe = RewriteUtils.loadRecipe("AssertJBooleanRulesRecipes.AbstractBooleanAssertIsFalseRecipe");
        rewriteRun(
                spec -> spec.recipe(recipe)
                        .expectedCyclesThatMakeChanges(1).cycles(1)
                        .parser(JavaParser.fromJavaVersion().classpath(JavaParser.runtimeClasspath())),
                java(
                        """
                                import com.google.common.collect.ImmutableSet;
                                import org.assertj.core.api.AbstractBooleanAssert;
                                import org.assertj.core.api.Assertions;
                                
                                class Test {
                                    ImmutableSet<AbstractBooleanAssert<?>> test() {
                                        return ImmutableSet.of(Assertions.assertThat(true).isEqualTo(false), Assertions.assertThat(true).isEqualTo(Boolean.FALSE), Assertions.assertThat(true).isNotEqualTo(true), Assertions.assertThat(true).isNotEqualTo(Boolean.TRUE));
                                    }
                                }
                                """,
                        """
                                import com.google.common.collect.ImmutableSet;
                                import org.assertj.core.api.AbstractBooleanAssert;
                                import org.assertj.core.api.Assertions;
                                
                                class Test {
                                    ImmutableSet<AbstractBooleanAssert<?>> test() {
                                        return ImmutableSet.of(Assertions.assertThat(true).isFalse(), Assertions.assertThat(true).isFalse(), Assertions.assertThat(true).isFalse(), Assertions.assertThat(true).isFalse());
                                    }
                                }
                                """
                ));
    }

    @Test
    void testAbstractBooleanAssertIsNotEqualToRecipe() {
        Recipe recipe = RewriteUtils.loadRecipe("AssertJBooleanRulesRecipes.AbstractBooleanAssertIsNotEqualToRecipe");
        rewriteRun(
                spec -> spec.recipe(recipe)
                        .expectedCyclesThatMakeChanges(1).cycles(1)
                        .parser(JavaParser.fromJavaVersion().classpath(JavaParser.runtimeClasspath())),
                java(
                        """
                                import org.assertj.core.api.AbstractBooleanAssert;
                                import org.assertj.core.api.Assertions;
                                
                                class Test {
                                    AbstractBooleanAssert<?> test() {
                                        return Assertions.assertThat(true).isEqualTo(!Boolean.FALSE);
                                    }
                                }
                                """,
                        """
                                import org.assertj.core.api.AbstractBooleanAssert;
                                import org.assertj.core.api.Assertions;
                                
                                class Test {
                                    AbstractBooleanAssert<?> test() {
                                        return Assertions.assertThat(true).isNotEqualTo(Boolean.FALSE);
                                    }
                                }
                                """
                ));
    }

    @Test
    void testAbstractBooleanAssertIsTrueRecipe() {
        Recipe recipe = RewriteUtils.loadRecipe("AssertJBooleanRulesRecipes.AbstractBooleanAssertIsTrueRecipe");
        rewriteRun(
                spec -> spec.recipe(recipe)
                        .expectedCyclesThatMakeChanges(1).cycles(1)
                        .parser(JavaParser.fromJavaVersion().classpath(JavaParser.runtimeClasspath())),
                java(
                        """
                                import com.google.common.collect.ImmutableSet;
                                import org.assertj.core.api.AbstractBooleanAssert;
                                import org.assertj.core.api.Assertions;
                                
                                class Test {
                                    ImmutableSet<AbstractBooleanAssert<?>> test() {
                                        return ImmutableSet.of(Assertions.assertThat(true).isEqualTo(true), Assertions.assertThat(true).isEqualTo(Boolean.TRUE), Assertions.assertThat(true).isNotEqualTo(false), Assertions.assertThat(true).isNotEqualTo(Boolean.FALSE));
                                    }
                                }
                                """,
                        """
                                import com.google.common.collect.ImmutableSet;
                                import org.assertj.core.api.AbstractBooleanAssert;
                                import org.assertj.core.api.Assertions;
                                
                                class Test {
                                    ImmutableSet<AbstractBooleanAssert<?>> test() {
                                        return ImmutableSet.of(Assertions.assertThat(true).isTrue(), Assertions.assertThat(true).isTrue(), Assertions.assertThat(true).isTrue(), Assertions.assertThat(true).isTrue());
                                    }
                                }
                                """
                ));
    }

    @Test
    void testAssertThatBooleanIsFalseRecipe() {
        Recipe recipe = RewriteUtils.loadRecipe("AssertJBooleanRulesRecipes.AssertThatBooleanIsFalseRecipe");
        rewriteRun(
                spec -> spec.recipe(recipe)
                        .expectedCyclesThatMakeChanges(1).cycles(1)
                        .parser(JavaParser.fromJavaVersion().classpath(JavaParser.runtimeClasspath())),
                java(
                        """
                                import org.assertj.core.api.AbstractBooleanAssert;
                                import org.assertj.core.api.Assertions;
                                
                                class Test {
                                    AbstractBooleanAssert<?> test() {
                                        return Assertions.assertThat(!Boolean.TRUE).isTrue();
                                    }
                                }
                                """,
                        """
                                import org.assertj.core.api.AbstractBooleanAssert;
                                import org.assertj.core.api.Assertions;
                                
                                class Test {
                                    AbstractBooleanAssert<?> test() {
                                        return Assertions.assertThat(Boolean.TRUE).isFalse();
                                    }
                                }
                                """
                ));
    }

    @Test
    void testAssertThatBooleanIsTrueRecipe() {
        Recipe recipe = RewriteUtils.loadRecipe("AssertJBooleanRulesRecipes.AssertThatBooleanIsTrueRecipe");
        rewriteRun(
                spec -> spec.recipe(recipe)
                        .expectedCyclesThatMakeChanges(1).cycles(1)
                        .parser(JavaParser.fromJavaVersion().classpath(JavaParser.runtimeClasspath())),
                java(
                        """
                                import org.assertj.core.api.AbstractBooleanAssert;
                                import org.assertj.core.api.Assertions;
                                
                                class Test {
                                    AbstractBooleanAssert<?> test() {
                                        return Assertions.assertThat(!Boolean.TRUE).isFalse();
                                    }
                                }
                                """,
                        """
                                import org.assertj.core.api.AbstractBooleanAssert;
                                import org.assertj.core.api.Assertions;
                                
                                class Test {
                                    AbstractBooleanAssert<?> test() {
                                        return Assertions.assertThat(Boolean.TRUE).isTrue();
                                    }
                                }
                                """
                ));
    }
}
