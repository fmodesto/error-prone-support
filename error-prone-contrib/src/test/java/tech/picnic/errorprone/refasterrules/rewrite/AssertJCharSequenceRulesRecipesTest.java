package tech.picnic.errorprone.refasterrules.rewrite;

import org.junit.jupiter.api.Test;
import org.openrewrite.Recipe;
import org.openrewrite.java.JavaParser;
import org.openrewrite.test.RewriteTest;

import static org.openrewrite.java.Assertions.java;

final class AssertJCharSequenceRulesRecipesTest implements RewriteTest {

    @Test
    void testAssertThatCharSequenceHasSizeRecipe() {
        Recipe recipe = RewriteUtils.loadRecipe("AssertJCharSequenceRulesRecipes.AssertThatCharSequenceHasSizeRecipe");
        rewriteRun(
                spec -> spec.recipe(recipe)
                        .expectedCyclesThatMakeChanges(1).cycles(1)
                        .parser(JavaParser.fromJavaVersion().classpath(JavaParser.runtimeClasspath())),
                java(
                        """
                                import org.assertj.core.api.AbstractAssert;
                                import org.assertj.core.api.Assertions;
                                
                                class Test {
                                    AbstractAssert<?, ?> test() {
                                        return Assertions.assertThat("foo".length()).isEqualTo(3);
                                    }
                                }
                                """,
                        """
                                import org.assertj.core.api.AbstractAssert;
                                import org.assertj.core.api.Assertions;
                                
                                class Test {
                                    AbstractAssert<?, ?> test() {
                                        return Assertions.assertThat("foo").hasSize(3);
                                    }
                                }
                                """
                ));
    }

    @Test
    void testAssertThatCharSequenceIsEmptyRecipe() {
        Recipe recipe = RewriteUtils.loadRecipe("AssertJCharSequenceRulesRecipes.AssertThatCharSequenceIsEmptyRecipe");
        rewriteRun(
                spec -> spec.recipe(recipe)
                        .expectedCyclesThatMakeChanges(1).cycles(1)
                        .parser(JavaParser.fromJavaVersion().classpath(JavaParser.runtimeClasspath())),
                java(
                        """
                                import org.assertj.core.api.Assertions;
                                
                                class Test {
                                    void test() {
                                        Assertions.assertThat("foo".isEmpty()).isTrue();
                                        Assertions.assertThat("bar".length()).isEqualTo(0L);
                                        Assertions.assertThat("baz".length()).isNotPositive();
                                    }
                                }
                                """,
                        """
                                import org.assertj.core.api.Assertions;
                                
                                class Test {
                                    void test() {
                                        Assertions.assertThat("foo").isEmpty();
                                        Assertions.assertThat("bar").isEmpty();
                                        Assertions.assertThat("baz").isEmpty();
                                    }
                                }
                                """
                ));
    }

    @Test
    void testAssertThatCharSequenceIsNotEmptyRecipe() {
        Recipe recipe = RewriteUtils.loadRecipe("AssertJCharSequenceRulesRecipes.AssertThatCharSequenceIsNotEmptyRecipe");
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
                                        return ImmutableSet.of(Assertions.assertThat("foo".isEmpty()).isFalse(), Assertions.assertThat("bar".length()).isNotEqualTo(0), Assertions.assertThat("baz".length()).isPositive());
                                    }
                                }
                                """,
                        """
                                import com.google.common.collect.ImmutableSet;
                                import org.assertj.core.api.AbstractAssert;
                                import org.assertj.core.api.Assertions;
                                
                                class Test {
                                    ImmutableSet<AbstractAssert<?, ?>> test() {
                                        return ImmutableSet.of(Assertions.assertThat("foo").isNotEmpty(), Assertions.assertThat("bar").isNotEmpty(), Assertions.assertThat("baz").isNotEmpty());
                                    }
                                }
                                """
                ));
    }
}
