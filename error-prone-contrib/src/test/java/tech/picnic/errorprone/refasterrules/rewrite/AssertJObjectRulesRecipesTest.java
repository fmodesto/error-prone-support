package tech.picnic.errorprone.refasterrules.rewrite;

import org.junit.jupiter.api.Test;
import org.openrewrite.Recipe;
import org.openrewrite.java.JavaParser;
import org.openrewrite.test.RewriteTest;

import static org.openrewrite.java.Assertions.java;

final class AssertJObjectRulesRecipesTest implements RewriteTest {

    @Test
    void testAssertThatHasToStringRecipe() {
        Recipe recipe = RewriteUtils.loadRecipe("AssertJObjectRulesRecipes.AssertThatHasToStringRecipe");
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
                                        return Assertions.assertThat(new Object().toString()).isEqualTo("foo");
                                    }
                                }
                                """,
                        """
                                import org.assertj.core.api.AbstractAssert;
                                import org.assertj.core.api.Assertions;
                                
                                class Test {
                                    AbstractAssert<?, ?> test() {
                                        return Assertions.assertThat(new Object()).hasToString("foo");
                                    }
                                }
                                """
                ));
    }

    @Test
    void testAssertThatIsInstanceOfRecipe() {
        Recipe recipe = RewriteUtils.loadRecipe("AssertJObjectRulesRecipes.AssertThatIsInstanceOfRecipe");
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
                                        return Assertions.assertThat("foo" instanceof String).isTrue();
                                    }
                                }
                                """,
                        """
                                import org.assertj.core.api.AbstractAssert;
                                import org.assertj.core.api.Assertions;
                                
                                class Test {
                                    AbstractAssert<?, ?> test() {
                                        return Assertions.assertThat("foo").isInstanceOf(String.class);
                                    }
                                }
                                """
                ));
    }

    @Test
    void testAssertThatIsIsEqualToRecipe() {
        Recipe recipe = RewriteUtils.loadRecipe("AssertJObjectRulesRecipes.AssertThatIsIsEqualToRecipe");
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
                                        return Assertions.assertThat("foo".equals("bar")).isTrue();
                                    }
                                }
                                """,
                        """
                                import org.assertj.core.api.AbstractAssert;
                                import org.assertj.core.api.Assertions;
                                
                                class Test {
                                    AbstractAssert<?, ?> test() {
                                        return Assertions.assertThat("foo").isEqualTo("bar");
                                    }
                                }
                                """
                ));
    }

    @Test
    void testAssertThatIsIsNotEqualToRecipe() {
        Recipe recipe = RewriteUtils.loadRecipe("AssertJObjectRulesRecipes.AssertThatIsIsNotEqualToRecipe");
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
                                        return Assertions.assertThat("foo".equals("bar")).isFalse();
                                    }
                                }
                                """,
                        """
                                import org.assertj.core.api.AbstractAssert;
                                import org.assertj.core.api.Assertions;
                                
                                class Test {
                                    AbstractAssert<?, ?> test() {
                                        return Assertions.assertThat("foo").isNotEqualTo("bar");
                                    }
                                }
                                """
                ));
    }

    @Test
    void testAssertThatIsNotInstanceOfRecipe() {
        Recipe recipe = RewriteUtils.loadRecipe("AssertJObjectRulesRecipes.AssertThatIsNotInstanceOfRecipe");
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
                                        return Assertions.assertThat("foo" instanceof String).isFalse();
                                    }
                                }
                                """,
                        """
                                import org.assertj.core.api.AbstractAssert;
                                import org.assertj.core.api.Assertions;
                                
                                class Test {
                                    AbstractAssert<?, ?> test() {
                                        return Assertions.assertThat("foo").isNotInstanceOf(String.class);
                                    }
                                }
                                """
                ));
    }
}
