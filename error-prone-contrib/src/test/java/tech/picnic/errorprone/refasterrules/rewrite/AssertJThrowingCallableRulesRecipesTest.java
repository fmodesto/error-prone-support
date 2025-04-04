package tech.picnic.errorprone.refasterrules.rewrite;

import org.junit.jupiter.api.Test;
import org.openrewrite.Recipe;
import org.openrewrite.java.JavaParser;
import org.openrewrite.test.RewriteTest;

import static org.openrewrite.java.Assertions.java;

final class AssertJThrowingCallableRulesRecipesTest implements RewriteTest {

    @Test
    void testAbstractThrowableAssertHasMessageRecipe() {
        Recipe recipe = RewriteUtils.loadRecipe("AssertJThrowingCallableRulesRecipes.AbstractThrowableAssertHasMessageRecipe");
        rewriteRun(
                spec -> spec.recipe(recipe)
                        .expectedCyclesThatMakeChanges(1).cycles(1)
                        .parser(JavaParser.fromJavaVersion().classpath(JavaParser.runtimeClasspath())),
                java(
                        """
                                import com.google.common.collect.ImmutableSet;
                                import org.assertj.core.api.AbstractThrowableAssert;
                                import org.assertj.core.api.Assertions;
                                
                                class Test {
                                    ImmutableSet<AbstractThrowableAssert<?, ? extends Throwable>>test() {
                                        return ImmutableSet.of(Assertions.assertThatThrownBy(() -> {
                                        }).isInstanceOf(IllegalArgumentException.class).hasMessage(String.format("foo %s", "bar")), Assertions.assertThatThrownBy(() -> {
                                        }).isInstanceOf(IllegalArgumentException.class).hasMessage(String.format("foo %s %s", "bar", 1)));
                                    }
                                }
                                """,
                        """
                                import com.google.common.collect.ImmutableSet;
                                import org.assertj.core.api.AbstractThrowableAssert;
                                import org.assertj.core.api.Assertions;
                                
                                class Test {
                                    ImmutableSet<AbstractThrowableAssert<?, ? extends Throwable>>test() {
                                        return ImmutableSet.of(Assertions.assertThatThrownBy(() -> {
                                        }).isInstanceOf(IllegalArgumentException.class).hasMessage("foo %s", "bar"), Assertions.assertThatThrownBy(() -> {
                                        }).isInstanceOf(IllegalArgumentException.class).hasMessage("foo %s %s", "bar", 1));
                                    }
                                }
                                """
                ));
    }

    @Test
    void testAbstractThrowableAssertWithFailMessageRecipe() {
        Recipe recipe = RewriteUtils.loadRecipe("AssertJThrowingCallableRulesRecipes.AbstractThrowableAssertWithFailMessageRecipe");
        rewriteRun(
                spec -> spec.recipe(recipe)
                        .expectedCyclesThatMakeChanges(1).cycles(1)
                        .parser(JavaParser.fromJavaVersion().classpath(JavaParser.runtimeClasspath())),
                java(
                        """
                                import com.google.common.collect.ImmutableSet;
                                import org.assertj.core.api.AbstractThrowableAssert;
                                import org.assertj.core.api.Assertions;
                                
                                class Test {
                                    ImmutableSet<AbstractThrowableAssert<?, ? extends Throwable>>test() {
                                        return ImmutableSet.of(Assertions.assertThatThrownBy(() -> {
                                        }).isInstanceOf(IllegalArgumentException.class).withFailMessage(String.format("foo %s", "bar")), Assertions.assertThatThrownBy(() -> {
                                        }).isInstanceOf(IllegalArgumentException.class).withFailMessage(String.format("foo %s %s", "bar", 1)));
                                    }
                                }
                                """,
                        """
                                import com.google.common.collect.ImmutableSet;
                                import org.assertj.core.api.AbstractThrowableAssert;
                                import org.assertj.core.api.Assertions;
                                
                                class Test {
                                    ImmutableSet<AbstractThrowableAssert<?, ? extends Throwable>>test() {
                                        return ImmutableSet.of(Assertions.assertThatThrownBy(() -> {
                                        }).isInstanceOf(IllegalArgumentException.class).withFailMessage("foo %s", "bar"), Assertions.assertThatThrownBy(() -> {
                                        }).isInstanceOf(IllegalArgumentException.class).withFailMessage("foo %s %s", "bar", 1));
                                    }
                                }
                                """
                ));
    }

    @Test
    void testAssertThatThrownByAsInstanceOfThrowableRecipe() {
        Recipe recipe = RewriteUtils.loadRecipe("AssertJThrowingCallableRulesRecipes.AssertThatThrownByAsInstanceOfThrowableRecipe");
        rewriteRun(
                spec -> spec.recipe(recipe)
                        .expectedCyclesThatMakeChanges(1).cycles(1)
                        .parser(JavaParser.fromJavaVersion().classpath(JavaParser.runtimeClasspath())),
                java(
                        """
                                import org.assertj.core.api.AbstractObjectAssert;
                                import org.assertj.core.api.Assertions;
                                
                                class Test {
                                    AbstractObjectAssert<?, ?> test() {
                                        return Assertions.assertThatExceptionOfType(IllegalArgumentException.class).isThrownBy(() -> {
                                        });
                                    }
                                }
                                """,
                        """
                                import org.assertj.core.api.AbstractObjectAssert;
                                import org.assertj.core.api.Assertions;
                                import org.assertj.core.api.InstanceOfAssertFactories;
                                
                                class Test {
                                    AbstractObjectAssert<?, ?> test() {
                                        return Assertions.assertThatThrownBy(() -> {
                                        }).asInstanceOf(InstanceOfAssertFactories.throwable(IllegalArgumentException.class));
                                    }
                                }
                                """
                ));
    }

    @Test
    void testAssertThatThrownByHasMessageRecipe() {
        Recipe recipe = RewriteUtils.loadRecipe("AssertJThrowingCallableRulesRecipes.AssertThatThrownByHasMessageRecipe");
        rewriteRun(
                spec -> spec.recipe(recipe)
                        .expectedCyclesThatMakeChanges(1).cycles(1)
                        .parser(JavaParser.fromJavaVersion().classpath(JavaParser.runtimeClasspath())),
                java(
                        """
                                import org.assertj.core.api.AbstractObjectAssert;
                                import org.assertj.core.api.Assertions;
                                
                                class Test {
                                    AbstractObjectAssert<?, ?> test() {
                                        return Assertions.assertThatExceptionOfType(IllegalArgumentException.class).isThrownBy(() -> {
                                        }).withMessage("foo");
                                    }
                                }
                                """,
                        """
                                import org.assertj.core.api.AbstractObjectAssert;
                                import org.assertj.core.api.Assertions;
                                
                                class Test {
                                    AbstractObjectAssert<?, ?> test() {
                                        return Assertions.assertThatThrownBy(() -> {
                                        }).isInstanceOf(IllegalArgumentException.class).hasMessage("foo");
                                    }
                                }
                                """
                ));
    }

    @Test
    void testAssertThatThrownByHasMessageContainingRecipe() {
        Recipe recipe = RewriteUtils.loadRecipe("AssertJThrowingCallableRulesRecipes.AssertThatThrownByHasMessageContainingRecipe");
        rewriteRun(
                spec -> spec.recipe(recipe)
                        .expectedCyclesThatMakeChanges(1).cycles(1)
                        .parser(JavaParser.fromJavaVersion().classpath(JavaParser.runtimeClasspath())),
                java(
                        """
                                import org.assertj.core.api.AbstractObjectAssert;
                                import org.assertj.core.api.Assertions;
                                
                                class Test {
                                    AbstractObjectAssert<?, ?> test() {
                                        return Assertions.assertThatExceptionOfType(IllegalArgumentException.class).isThrownBy(() -> {
                                        }).withMessageContaining("foo");
                                    }
                                }
                                """,
                        """
                                import org.assertj.core.api.AbstractObjectAssert;
                                import org.assertj.core.api.Assertions;
                                
                                class Test {
                                    AbstractObjectAssert<?, ?> test() {
                                        return Assertions.assertThatThrownBy(() -> {
                                        }).isInstanceOf(IllegalArgumentException.class).hasMessageContaining("foo");
                                    }
                                }
                                """
                ));
    }

    @Test
    void testAssertThatThrownByHasMessageNotContainingRecipe() {
        Recipe recipe = RewriteUtils.loadRecipe("AssertJThrowingCallableRulesRecipes.AssertThatThrownByHasMessageNotContainingRecipe");
        rewriteRun(
                spec -> spec.recipe(recipe)
                        .expectedCyclesThatMakeChanges(1).cycles(1)
                        .parser(JavaParser.fromJavaVersion().classpath(JavaParser.runtimeClasspath())),
                java(
                        """
                                import org.assertj.core.api.AbstractObjectAssert;
                                import org.assertj.core.api.Assertions;
                                
                                class Test {
                                    AbstractObjectAssert<?, ?> test() {
                                        return Assertions.assertThatExceptionOfType(IllegalArgumentException.class).isThrownBy(() -> {
                                        }).withMessageNotContaining("foo");
                                    }
                                }
                                """,
                        """
                                import org.assertj.core.api.AbstractObjectAssert;
                                import org.assertj.core.api.Assertions;
                                
                                class Test {
                                    AbstractObjectAssert<?, ?> test() {
                                        return Assertions.assertThatThrownBy(() -> {
                                        }).isInstanceOf(IllegalArgumentException.class).hasMessageNotContaining("foo");
                                    }
                                }
                                """
                ));
    }

    @Test
    void testAssertThatThrownByHasMessageParametersRecipe() {
        Recipe recipe = RewriteUtils.loadRecipe("AssertJThrowingCallableRulesRecipes.AssertThatThrownByHasMessageParametersRecipe");
        rewriteRun(
                spec -> spec.recipe(recipe)
                        .expectedCyclesThatMakeChanges(1).cycles(1)
                        .parser(JavaParser.fromJavaVersion().classpath(JavaParser.runtimeClasspath())),
                java(
                        """
                                import org.assertj.core.api.AbstractObjectAssert;
                                import org.assertj.core.api.Assertions;
                                
                                class Test {
                                    AbstractObjectAssert<?, ?> test() {
                                        return Assertions.assertThatExceptionOfType(IllegalArgumentException.class).isThrownBy(() -> {
                                        }).withMessage("foo %s", "bar");
                                    }
                                }
                                """,
                        """
                                import org.assertj.core.api.AbstractObjectAssert;
                                import org.assertj.core.api.Assertions;
                                
                                class Test {
                                    AbstractObjectAssert<?, ?> test() {
                                        return Assertions.assertThatThrownBy(() -> {
                                        }).isInstanceOf(IllegalArgumentException.class).hasMessage("foo %s", "bar");
                                    }
                                }
                                """
                ));
    }

    @Test
    void testAssertThatThrownByHasMessageStartingWithRecipe() {
        Recipe recipe = RewriteUtils.loadRecipe("AssertJThrowingCallableRulesRecipes.AssertThatThrownByHasMessageStartingWithRecipe");
        rewriteRun(
                spec -> spec.recipe(recipe)
                        .expectedCyclesThatMakeChanges(1).cycles(1)
                        .parser(JavaParser.fromJavaVersion().classpath(JavaParser.runtimeClasspath())),
                java(
                        """
                                import org.assertj.core.api.AbstractObjectAssert;
                                import org.assertj.core.api.Assertions;
                                
                                class Test {
                                    AbstractObjectAssert<?, ?> test() {
                                        return Assertions.assertThatExceptionOfType(IllegalArgumentException.class).isThrownBy(() -> {
                                        }).withMessageStartingWith("foo");
                                    }
                                }
                                """,
                        """
                                import org.assertj.core.api.AbstractObjectAssert;
                                import org.assertj.core.api.Assertions;
                                
                                class Test {
                                    AbstractObjectAssert<?, ?> test() {
                                        return Assertions.assertThatThrownBy(() -> {
                                        }).isInstanceOf(IllegalArgumentException.class).hasMessageStartingWith("foo");
                                    }
                                }
                                """
                ));
    }

    @Test
    void testAssertThatThrownByIOExceptionRecipe() {
        Recipe recipe = RewriteUtils.loadRecipe("AssertJThrowingCallableRulesRecipes.AssertThatThrownByIOExceptionRecipe");
        rewriteRun(
                spec -> spec.recipe(recipe)
                        .expectedCyclesThatMakeChanges(1).cycles(1)
                        .parser(JavaParser.fromJavaVersion().classpath(JavaParser.runtimeClasspath())),
                java(
                        """
                                import org.assertj.core.api.AbstractObjectAssert;
                                import org.assertj.core.api.Assertions;
                                
                                class Test {
                                    AbstractObjectAssert<?, ?> test() {
                                        return Assertions.assertThatIOException().isThrownBy(() -> {
                                        });
                                    }
                                }
                                """,
                        """
                                import org.assertj.core.api.AbstractObjectAssert;
                                import org.assertj.core.api.Assertions;
                                
                                import java.io.IOException;
                                
                                class Test {
                                    AbstractObjectAssert<?, ?> test() {
                                        return Assertions.assertThatThrownBy(() -> {
                                        }).isInstanceOf(IOException.class);
                                    }
                                }
                                """
                ));
    }

    @Test
    void testAssertThatThrownByIOExceptionHasMessageRecipe() {
        Recipe recipe = RewriteUtils.loadRecipe("AssertJThrowingCallableRulesRecipes.AssertThatThrownByIOExceptionHasMessageRecipe");
        rewriteRun(
                spec -> spec.recipe(recipe)
                        .expectedCyclesThatMakeChanges(1).cycles(1)
                        .parser(JavaParser.fromJavaVersion().classpath(JavaParser.runtimeClasspath())),
                java(
                        """
                                import org.assertj.core.api.AbstractObjectAssert;
                                import org.assertj.core.api.Assertions;
                                
                                class Test {
                                    AbstractObjectAssert<?, ?> test() {
                                        return Assertions.assertThatIOException().isThrownBy(() -> {
                                        }).withMessage("foo");
                                    }
                                }
                                """,
                        """
                                import org.assertj.core.api.AbstractObjectAssert;
                                import org.assertj.core.api.Assertions;
                                
                                import java.io.IOException;
                                
                                class Test {
                                    AbstractObjectAssert<?, ?> test() {
                                        return Assertions.assertThatThrownBy(() -> {
                                        }).isInstanceOf(IOException.class).hasMessage("foo");
                                    }
                                }
                                """
                ));
    }

    @Test
    void testAssertThatThrownByIOExceptionHasMessageContainingRecipe() {
        Recipe recipe = RewriteUtils.loadRecipe("AssertJThrowingCallableRulesRecipes.AssertThatThrownByIOExceptionHasMessageContainingRecipe");
        rewriteRun(
                spec -> spec.recipe(recipe)
                        .expectedCyclesThatMakeChanges(1).cycles(1)
                        .parser(JavaParser.fromJavaVersion().classpath(JavaParser.runtimeClasspath())),
                java(
                        """
                                import org.assertj.core.api.AbstractObjectAssert;
                                import org.assertj.core.api.Assertions;
                                
                                class Test {
                                    AbstractObjectAssert<?, ?> test() {
                                        return Assertions.assertThatIOException().isThrownBy(() -> {
                                        }).withMessageContaining("foo");
                                    }
                                }
                                """,
                        """
                                import org.assertj.core.api.AbstractObjectAssert;
                                import org.assertj.core.api.Assertions;
                                
                                import java.io.IOException;
                                
                                class Test {
                                    AbstractObjectAssert<?, ?> test() {
                                        return Assertions.assertThatThrownBy(() -> {
                                        }).isInstanceOf(IOException.class).hasMessageContaining("foo");
                                    }
                                }
                                """
                ));
    }

    @Test
    void testAssertThatThrownByIOExceptionHasMessageNotContainingRecipe() {
        Recipe recipe = RewriteUtils.loadRecipe("AssertJThrowingCallableRulesRecipes.AssertThatThrownByIOExceptionHasMessageNotContainingRecipe");
        rewriteRun(
                spec -> spec.recipe(recipe)
                        .expectedCyclesThatMakeChanges(1).cycles(1)
                        .parser(JavaParser.fromJavaVersion().classpath(JavaParser.runtimeClasspath())),
                java(
                        """
                                import org.assertj.core.api.AbstractObjectAssert;
                                import org.assertj.core.api.Assertions;
                                
                                class Test {
                                    AbstractObjectAssert<?, ?> test() {
                                        return Assertions.assertThatIOException().isThrownBy(() -> {
                                        }).withMessageNotContaining("foo");
                                    }
                                }
                                """,
                        """
                                import org.assertj.core.api.AbstractObjectAssert;
                                import org.assertj.core.api.Assertions;
                                
                                import java.io.IOException;
                                
                                class Test {
                                    AbstractObjectAssert<?, ?> test() {
                                        return Assertions.assertThatThrownBy(() -> {
                                        }).isInstanceOf(IOException.class).hasMessageNotContaining("foo");
                                    }
                                }
                                """
                ));
    }

    @Test
    void testAssertThatThrownByIOExceptionHasMessageParametersRecipe() {
        Recipe recipe = RewriteUtils.loadRecipe("AssertJThrowingCallableRulesRecipes.AssertThatThrownByIOExceptionHasMessageParametersRecipe");
        rewriteRun(
                spec -> spec.recipe(recipe)
                        .expectedCyclesThatMakeChanges(1).cycles(1)
                        .parser(JavaParser.fromJavaVersion().classpath(JavaParser.runtimeClasspath())),
                java(
                        """
                                import org.assertj.core.api.AbstractObjectAssert;
                                import org.assertj.core.api.Assertions;
                                
                                class Test {
                                    AbstractObjectAssert<?, ?> test() {
                                        return Assertions.assertThatIOException().isThrownBy(() -> {
                                        }).withMessage("foo %s", "bar");
                                    }
                                }
                                """,
                        """
                                import org.assertj.core.api.AbstractObjectAssert;
                                import org.assertj.core.api.Assertions;
                                
                                import java.io.IOException;
                                
                                class Test {
                                    AbstractObjectAssert<?, ?> test() {
                                        return Assertions.assertThatThrownBy(() -> {
                                        }).isInstanceOf(IOException.class).hasMessage("foo %s", "bar");
                                    }
                                }
                                """
                ));
    }

    @Test
    void testAssertThatThrownByIOExceptionHasMessageStartingWithRecipe() {
        Recipe recipe = RewriteUtils.loadRecipe("AssertJThrowingCallableRulesRecipes.AssertThatThrownByIOExceptionHasMessageStartingWithRecipe");
        rewriteRun(
                spec -> spec.recipe(recipe)
                        .expectedCyclesThatMakeChanges(1).cycles(1)
                        .parser(JavaParser.fromJavaVersion().classpath(JavaParser.runtimeClasspath())),
                java(
                        """
                                import org.assertj.core.api.AbstractObjectAssert;
                                import org.assertj.core.api.Assertions;
                                
                                class Test {
                                    AbstractObjectAssert<?, ?> test() {
                                        return Assertions.assertThatIOException().isThrownBy(() -> {
                                        }).withMessageStartingWith("foo");
                                    }
                                }
                                """,
                        """
                                import org.assertj.core.api.AbstractObjectAssert;
                                import org.assertj.core.api.Assertions;
                                
                                import java.io.IOException;
                                
                                class Test {
                                    AbstractObjectAssert<?, ?> test() {
                                        return Assertions.assertThatThrownBy(() -> {
                                        }).isInstanceOf(IOException.class).hasMessageStartingWith("foo");
                                    }
                                }
                                """
                ));
    }

    @Test
    void testAssertThatThrownByIOExceptionRootCauseHasMessageRecipe() {
        Recipe recipe = RewriteUtils.loadRecipe("AssertJThrowingCallableRulesRecipes.AssertThatThrownByIOExceptionRootCauseHasMessageRecipe");
        rewriteRun(
                spec -> spec.recipe(recipe)
                        .expectedCyclesThatMakeChanges(1).cycles(1)
                        .parser(JavaParser.fromJavaVersion().classpath(JavaParser.runtimeClasspath())),
                java(
                        """
                                import org.assertj.core.api.AbstractObjectAssert;
                                import org.assertj.core.api.Assertions;
                                
                                class Test {
                                    AbstractObjectAssert<?, ?> test() {
                                        return Assertions.assertThatIOException().isThrownBy(() -> {
                                        }).havingRootCause().withMessage("foo");
                                    }
                                }
                                """,
                        """
                                import org.assertj.core.api.AbstractObjectAssert;
                                import org.assertj.core.api.Assertions;
                                
                                import java.io.IOException;
                                
                                class Test {
                                    AbstractObjectAssert<?, ?> test() {
                                        return Assertions.assertThatThrownBy(() -> {
                                        }).isInstanceOf(IOException.class).rootCause().hasMessage("foo");
                                    }
                                }
                                """
                ));
    }

    @Test
    void testAssertThatThrownByIllegalArgumentExceptionRecipe() {
        Recipe recipe = RewriteUtils.loadRecipe("AssertJThrowingCallableRulesRecipes.AssertThatThrownByIllegalArgumentExceptionRecipe");
        rewriteRun(
                spec -> spec.recipe(recipe)
                        .expectedCyclesThatMakeChanges(1).cycles(1)
                        .parser(JavaParser.fromJavaVersion().classpath(JavaParser.runtimeClasspath())),
                java(
                        """
                                import org.assertj.core.api.AbstractObjectAssert;
                                import org.assertj.core.api.Assertions;
                                
                                class Test {
                                    AbstractObjectAssert<?, ?> test() {
                                        return Assertions.assertThatIllegalArgumentException().isThrownBy(() -> {
                                        });
                                    }
                                }
                                """,
                        """
                                import org.assertj.core.api.AbstractObjectAssert;
                                import org.assertj.core.api.Assertions;
                                
                                class Test {
                                    AbstractObjectAssert<?, ?> test() {
                                        return Assertions.assertThatThrownBy(() -> {
                                        }).isInstanceOf(IllegalArgumentException.class);
                                    }
                                }
                                """
                ));
    }

    @Test
    void testAssertThatThrownByIllegalArgumentExceptionHasMessageRecipe() {
        Recipe recipe = RewriteUtils.loadRecipe("AssertJThrowingCallableRulesRecipes.AssertThatThrownByIllegalArgumentExceptionHasMessageRecipe");
        rewriteRun(
                spec -> spec.recipe(recipe)
                        .expectedCyclesThatMakeChanges(1).cycles(1)
                        .parser(JavaParser.fromJavaVersion().classpath(JavaParser.runtimeClasspath())),
                java(
                        """
                                import org.assertj.core.api.AbstractObjectAssert;
                                import org.assertj.core.api.Assertions;
                                
                                class Test {
                                    AbstractObjectAssert<?, ?> test() {
                                        return Assertions.assertThatIllegalArgumentException().isThrownBy(() -> {
                                        }).withMessage("foo");
                                    }
                                }
                                """,
                        """
                                import org.assertj.core.api.AbstractObjectAssert;
                                import org.assertj.core.api.Assertions;
                                
                                class Test {
                                    AbstractObjectAssert<?, ?> test() {
                                        return Assertions.assertThatThrownBy(() -> {
                                        }).isInstanceOf(IllegalArgumentException.class).hasMessage("foo");
                                    }
                                }
                                """
                ));
    }

    @Test
    void testAssertThatThrownByIllegalArgumentExceptionHasMessageContainingRecipe() {
        Recipe recipe = RewriteUtils.loadRecipe("AssertJThrowingCallableRulesRecipes.AssertThatThrownByIllegalArgumentExceptionHasMessageContainingRecipe");
        rewriteRun(
                spec -> spec.recipe(recipe)
                        .expectedCyclesThatMakeChanges(1).cycles(1)
                        .parser(JavaParser.fromJavaVersion().classpath(JavaParser.runtimeClasspath())),
                java(
                        """
                                import org.assertj.core.api.AbstractObjectAssert;
                                import org.assertj.core.api.Assertions;
                                
                                class Test {
                                    AbstractObjectAssert<?, ?> test() {
                                        return Assertions.assertThatIllegalArgumentException().isThrownBy(() -> {
                                        }).withMessageContaining("foo");
                                    }
                                }
                                """,
                        """
                                import org.assertj.core.api.AbstractObjectAssert;
                                import org.assertj.core.api.Assertions;
                                
                                class Test {
                                    AbstractObjectAssert<?, ?> test() {
                                        return Assertions.assertThatThrownBy(() -> {
                                        }).isInstanceOf(IllegalArgumentException.class).hasMessageContaining("foo");
                                    }
                                }
                                """
                ));
    }

    @Test
    void testAssertThatThrownByIllegalArgumentExceptionHasMessageNotContainingAnyRecipe() {
        Recipe recipe = RewriteUtils.loadRecipe("AssertJThrowingCallableRulesRecipes.AssertThatThrownByIllegalArgumentExceptionHasMessageNotContainingAnyRecipe");
        rewriteRun(
                spec -> spec.recipe(recipe)
                        .expectedCyclesThatMakeChanges(1).cycles(1)
                        .parser(JavaParser.fromJavaVersion().classpath(JavaParser.runtimeClasspath())),
                java(
                        """
                                import org.assertj.core.api.AbstractObjectAssert;
                                import org.assertj.core.api.Assertions;
                                
                                class Test {
                                    AbstractObjectAssert<?, ?>test() {
                                        return Assertions.assertThatIllegalArgumentException().isThrownBy(() -> {
                                        }).withMessageNotContainingAny("foo", "bar");
                                    }
                                }
                                """,
                        """
                                import org.assertj.core.api.AbstractObjectAssert;
                                import org.assertj.core.api.Assertions;
                                
                                class Test {
                                    AbstractObjectAssert<?, ?>test() {
                                        return Assertions.assertThatThrownBy(() -> {
                                        }).isInstanceOf(IllegalArgumentException.class).hasMessageNotContainingAny("foo", "bar");
                                    }
                                }
                                """
                ));
    }

    @Test
    void testAssertThatThrownByIllegalArgumentExceptionHasMessageParametersRecipe() {
        Recipe recipe = RewriteUtils.loadRecipe("AssertJThrowingCallableRulesRecipes.AssertThatThrownByIllegalArgumentExceptionHasMessageParametersRecipe");
        rewriteRun(
                spec -> spec.recipe(recipe)
                        .expectedCyclesThatMakeChanges(1).cycles(1)
                        .parser(JavaParser.fromJavaVersion().classpath(JavaParser.runtimeClasspath())),
                java(
                        """
                                import org.assertj.core.api.AbstractObjectAssert;
                                import org.assertj.core.api.Assertions;
                                
                                class Test {
                                    AbstractObjectAssert<?, ?> test() {
                                        return Assertions.assertThatIllegalArgumentException().isThrownBy(() -> {
                                        }).withMessage("foo %s", "bar");
                                    }
                                }
                                """,
                        """
                                import org.assertj.core.api.AbstractObjectAssert;
                                import org.assertj.core.api.Assertions;
                                
                                class Test {
                                    AbstractObjectAssert<?, ?> test() {
                                        return Assertions.assertThatThrownBy(() -> {
                                        }).isInstanceOf(IllegalArgumentException.class).hasMessage("foo %s", "bar");
                                    }
                                }
                                """
                ));
    }

    @Test
    void testAssertThatThrownByIllegalArgumentExceptionHasMessageStartingWithRecipe() {
        Recipe recipe = RewriteUtils.loadRecipe("AssertJThrowingCallableRulesRecipes.AssertThatThrownByIllegalArgumentExceptionHasMessageStartingWithRecipe");
        rewriteRun(
                spec -> spec.recipe(recipe)
                        .expectedCyclesThatMakeChanges(1).cycles(1)
                        .parser(JavaParser.fromJavaVersion().classpath(JavaParser.runtimeClasspath())),
                java(
                        """
                                import org.assertj.core.api.AbstractObjectAssert;
                                import org.assertj.core.api.Assertions;
                                
                                class Test {
                                    AbstractObjectAssert<?, ?>test() {
                                        return Assertions.assertThatIllegalArgumentException().isThrownBy(() -> {
                                        }).withMessageStartingWith("foo");
                                    }
                                }
                                """,
                        """
                                import org.assertj.core.api.AbstractObjectAssert;
                                import org.assertj.core.api.Assertions;
                                
                                class Test {
                                    AbstractObjectAssert<?, ?>test() {
                                        return Assertions.assertThatThrownBy(() -> {
                                        }).isInstanceOf(IllegalArgumentException.class).hasMessageStartingWith("foo");
                                    }
                                }
                                """
                ));
    }

    @Test
    void testAssertThatThrownByIllegalArgumentExceptionRootCauseHasMessageRecipe() {
        Recipe recipe = RewriteUtils.loadRecipe("AssertJThrowingCallableRulesRecipes.AssertThatThrownByIllegalArgumentExceptionRootCauseHasMessageRecipe");
        rewriteRun(
                spec -> spec.recipe(recipe)
                        .expectedCyclesThatMakeChanges(1).cycles(1)
                        .parser(JavaParser.fromJavaVersion().classpath(JavaParser.runtimeClasspath())),
                java(
                        """
                                import org.assertj.core.api.AbstractObjectAssert;
                                import org.assertj.core.api.Assertions;
                                
                                class Test {
                                    AbstractObjectAssert<?, ?> test() {
                                        return Assertions.assertThatIllegalArgumentException().isThrownBy(() -> {
                                        }).havingRootCause().withMessage("foo");
                                    }
                                }
                                """,
                        """
                                import org.assertj.core.api.AbstractObjectAssert;
                                import org.assertj.core.api.Assertions;
                                
                                class Test {
                                    AbstractObjectAssert<?, ?> test() {
                                        return Assertions.assertThatThrownBy(() -> {
                                        }).isInstanceOf(IllegalArgumentException.class).rootCause().hasMessage("foo");
                                    }
                                }
                                """
                ));
    }

    @Test
    void testAssertThatThrownByIllegalStateExceptionRecipe() {
        Recipe recipe = RewriteUtils.loadRecipe("AssertJThrowingCallableRulesRecipes.AssertThatThrownByIllegalStateExceptionRecipe");
        rewriteRun(
                spec -> spec.recipe(recipe)
                        .expectedCyclesThatMakeChanges(1).cycles(1)
                        .parser(JavaParser.fromJavaVersion().classpath(JavaParser.runtimeClasspath())),
                java(
                        """
                                import org.assertj.core.api.AbstractObjectAssert;
                                import org.assertj.core.api.Assertions;
                                
                                class Test {
                                    AbstractObjectAssert<?, ?> test() {
                                        return Assertions.assertThatIllegalStateException().isThrownBy(() -> {
                                        });
                                    }
                                }
                                """,
                        """
                                import org.assertj.core.api.AbstractObjectAssert;
                                import org.assertj.core.api.Assertions;
                                
                                class Test {
                                    AbstractObjectAssert<?, ?> test() {
                                        return Assertions.assertThatThrownBy(() -> {
                                        }).isInstanceOf(IllegalStateException.class);
                                    }
                                }
                                """
                ));
    }

    @Test
    void testAssertThatThrownByIllegalStateExceptionHasMessageRecipe() {
        Recipe recipe = RewriteUtils.loadRecipe("AssertJThrowingCallableRulesRecipes.AssertThatThrownByIllegalStateExceptionHasMessageRecipe");
        rewriteRun(
                spec -> spec.recipe(recipe)
                        .expectedCyclesThatMakeChanges(1).cycles(1)
                        .parser(JavaParser.fromJavaVersion().classpath(JavaParser.runtimeClasspath())),
                java(
                        """
                                import org.assertj.core.api.AbstractObjectAssert;
                                import org.assertj.core.api.Assertions;
                                
                                class Test {
                                    AbstractObjectAssert<?, ?> test() {
                                        return Assertions.assertThatIllegalStateException().isThrownBy(() -> {
                                        }).withMessage("foo");
                                    }
                                }
                                """,
                        """
                                import org.assertj.core.api.AbstractObjectAssert;
                                import org.assertj.core.api.Assertions;
                                
                                class Test {
                                    AbstractObjectAssert<?, ?> test() {
                                        return Assertions.assertThatThrownBy(() -> {
                                        }).isInstanceOf(IllegalStateException.class).hasMessage("foo");
                                    }
                                }
                                """
                ));
    }

    @Test
    void testAssertThatThrownByIllegalStateExceptionHasMessageContainingRecipe() {
        Recipe recipe = RewriteUtils.loadRecipe("AssertJThrowingCallableRulesRecipes.AssertThatThrownByIllegalStateExceptionHasMessageContainingRecipe");
        rewriteRun(
                spec -> spec.recipe(recipe)
                        .expectedCyclesThatMakeChanges(1).cycles(1)
                        .parser(JavaParser.fromJavaVersion().classpath(JavaParser.runtimeClasspath())),
                java(
                        """
                                import org.assertj.core.api.AbstractObjectAssert;
                                import org.assertj.core.api.Assertions;
                                
                                class Test {
                                    AbstractObjectAssert<?, ?> test() {
                                        return Assertions.assertThatIllegalStateException().isThrownBy(() -> {
                                        }).withMessageContaining("foo");
                                    }
                                }
                                """,
                        """
                                import org.assertj.core.api.AbstractObjectAssert;
                                import org.assertj.core.api.Assertions;
                                
                                class Test {
                                    AbstractObjectAssert<?, ?> test() {
                                        return Assertions.assertThatThrownBy(() -> {
                                        }).isInstanceOf(IllegalStateException.class).hasMessageContaining("foo");
                                    }
                                }
                                """
                ));
    }

    @Test
    void testAssertThatThrownByIllegalStateExceptionHasMessageNotContainingRecipe() {
        Recipe recipe = RewriteUtils.loadRecipe("AssertJThrowingCallableRulesRecipes.AssertThatThrownByIllegalStateExceptionHasMessageNotContainingRecipe");
        rewriteRun(
                spec -> spec.recipe(recipe)
                        .expectedCyclesThatMakeChanges(1).cycles(1)
                        .parser(JavaParser.fromJavaVersion().classpath(JavaParser.runtimeClasspath())),
                java(
                        """
                                import org.assertj.core.api.AbstractObjectAssert;
                                import org.assertj.core.api.Assertions;
                                
                                class Test {
                                    AbstractObjectAssert<?, ?> test() {
                                        return Assertions.assertThatIllegalStateException().isThrownBy(() -> {
                                        }).withMessageNotContaining("foo");
                                    }
                                }
                                """,
                        """
                                import org.assertj.core.api.AbstractObjectAssert;
                                import org.assertj.core.api.Assertions;
                                
                                class Test {
                                    AbstractObjectAssert<?, ?> test() {
                                        return Assertions.assertThatThrownBy(() -> {
                                        }).isInstanceOf(IllegalStateException.class).hasMessageNotContaining("foo");
                                    }
                                }
                                """
                ));
    }

    @Test
    void testAssertThatThrownByIllegalStateExceptionHasMessageParametersRecipe() {
        Recipe recipe = RewriteUtils.loadRecipe("AssertJThrowingCallableRulesRecipes.AssertThatThrownByIllegalStateExceptionHasMessageParametersRecipe");
        rewriteRun(
                spec -> spec.recipe(recipe)
                        .expectedCyclesThatMakeChanges(1).cycles(1)
                        .parser(JavaParser.fromJavaVersion().classpath(JavaParser.runtimeClasspath())),
                java(
                        """
                                import org.assertj.core.api.AbstractObjectAssert;
                                import org.assertj.core.api.Assertions;
                                
                                class Test {
                                    AbstractObjectAssert<?, ?> test() {
                                        return Assertions.assertThatIllegalStateException().isThrownBy(() -> {
                                        }).withMessage("foo %s", "bar");
                                    }
                                }
                                """,
                        """
                                import org.assertj.core.api.AbstractObjectAssert;
                                import org.assertj.core.api.Assertions;
                                
                                class Test {
                                    AbstractObjectAssert<?, ?> test() {
                                        return Assertions.assertThatThrownBy(() -> {
                                        }).isInstanceOf(IllegalStateException.class).hasMessage("foo %s", "bar");
                                    }
                                }
                                """
                ));
    }

    @Test
    void testAssertThatThrownByIllegalStateExceptionHasMessageStartingWithRecipe() {
        Recipe recipe = RewriteUtils.loadRecipe("AssertJThrowingCallableRulesRecipes.AssertThatThrownByIllegalStateExceptionHasMessageStartingWithRecipe");
        rewriteRun(
                spec -> spec.recipe(recipe)
                        .expectedCyclesThatMakeChanges(1).cycles(1)
                        .parser(JavaParser.fromJavaVersion().classpath(JavaParser.runtimeClasspath())),
                java(
                        """
                                import org.assertj.core.api.AbstractObjectAssert;
                                import org.assertj.core.api.Assertions;
                                
                                class Test {
                                    AbstractObjectAssert<?, ?> test() {
                                        return Assertions.assertThatIllegalStateException().isThrownBy(() -> {
                                        }).withMessageStartingWith("foo");
                                    }
                                }
                                """,
                        """
                                import org.assertj.core.api.AbstractObjectAssert;
                                import org.assertj.core.api.Assertions;
                                
                                class Test {
                                    AbstractObjectAssert<?, ?> test() {
                                        return Assertions.assertThatThrownBy(() -> {
                                        }).isInstanceOf(IllegalStateException.class).hasMessageStartingWith("foo");
                                    }
                                }
                                """
                ));
    }

    @Test
    void testAssertThatThrownByIllegalStateExceptionRootCauseHasMessageRecipe() {
        Recipe recipe = RewriteUtils.loadRecipe("AssertJThrowingCallableRulesRecipes.AssertThatThrownByIllegalStateExceptionRootCauseHasMessageRecipe");
        rewriteRun(
                spec -> spec.recipe(recipe)
                        .expectedCyclesThatMakeChanges(1).cycles(1)
                        .parser(JavaParser.fromJavaVersion().classpath(JavaParser.runtimeClasspath())),
                java(
                        """
                                import org.assertj.core.api.AbstractObjectAssert;
                                import org.assertj.core.api.Assertions;
                                
                                class Test {
                                    AbstractObjectAssert<?, ?> test() {
                                        return Assertions.assertThatIllegalStateException().isThrownBy(() -> {
                                        }).havingRootCause().withMessage("foo");
                                    }
                                }
                                """,
                        """
                                import org.assertj.core.api.AbstractObjectAssert;
                                import org.assertj.core.api.Assertions;
                                
                                class Test {
                                    AbstractObjectAssert<?, ?> test() {
                                        return Assertions.assertThatThrownBy(() -> {
                                        }).isInstanceOf(IllegalStateException.class).rootCause().hasMessage("foo");
                                    }
                                }
                                """
                ));
    }

    @Test
    void testAssertThatThrownByIsInstanceOfRecipe() {
        Recipe recipe = RewriteUtils.loadRecipe("AssertJThrowingCallableRulesRecipes.AssertThatThrownByIsInstanceOfRecipe");
        rewriteRun(
                spec -> spec.recipe(recipe)
                        .expectedCyclesThatMakeChanges(1).cycles(1)
                        .parser(JavaParser.fromJavaVersion().classpath(JavaParser.runtimeClasspath())),
                java(
                        """
                                import org.assertj.core.api.Assertions;
                                import org.assertj.core.api.InstanceOfAssertFactories;
                                
                                class Test {
                                    void test() {
                                        Assertions.assertThatThrownBy(() -> {
                                        }).asInstanceOf(InstanceOfAssertFactories.throwable(IllegalArgumentException.class));
                                        Assertions.assertThatThrownBy(() -> {
                                        }).asInstanceOf(InstanceOfAssertFactories.type(IllegalArgumentException.class));
                                    }
                                }
                                """,
                        """
                                import org.assertj.core.api.Assertions;
                                
                                class Test {
                                    void test() {
                                        Assertions.assertThatThrownBy(() -> {
                                        }).isInstanceOf(IllegalArgumentException.class);
                                        Assertions.assertThatThrownBy(() -> {
                                        }).isInstanceOf(IllegalArgumentException.class);
                                    }
                                }
                                """
                ));
    }

    @Test
    void testAssertThatThrownByNullPointerExceptionRecipe() {
        Recipe recipe = RewriteUtils.loadRecipe("AssertJThrowingCallableRulesRecipes.AssertThatThrownByNullPointerExceptionRecipe");
        rewriteRun(
                spec -> spec.recipe(recipe)
                        .expectedCyclesThatMakeChanges(1).cycles(1)
                        .parser(JavaParser.fromJavaVersion().classpath(JavaParser.runtimeClasspath())),
                java(
                        """
                                import org.assertj.core.api.AbstractObjectAssert;
                                import org.assertj.core.api.Assertions;
                                
                                class Test {
                                    AbstractObjectAssert<?, ?> test() {
                                        return Assertions.assertThatNullPointerException().isThrownBy(() -> {
                                        });
                                    }
                                }
                                """,
                        """
                                import org.assertj.core.api.AbstractObjectAssert;
                                import org.assertj.core.api.Assertions;
                                
                                class Test {
                                    AbstractObjectAssert<?, ?> test() {
                                        return Assertions.assertThatThrownBy(() -> {
                                        }).isInstanceOf(NullPointerException.class);
                                    }
                                }
                                """
                ));
    }

    @Test
    void testAssertThatThrownByNullPointerExceptionHasMessageRecipe() {
        Recipe recipe = RewriteUtils.loadRecipe("AssertJThrowingCallableRulesRecipes.AssertThatThrownByNullPointerExceptionHasMessageRecipe");
        rewriteRun(
                spec -> spec.recipe(recipe)
                        .expectedCyclesThatMakeChanges(1).cycles(1)
                        .parser(JavaParser.fromJavaVersion().classpath(JavaParser.runtimeClasspath())),
                java(
                        """
                                import org.assertj.core.api.AbstractObjectAssert;
                                import org.assertj.core.api.Assertions;
                                
                                class Test {
                                    AbstractObjectAssert<?, ?> test() {
                                        return Assertions.assertThatNullPointerException().isThrownBy(() -> {
                                        }).withMessage("foo");
                                    }
                                }
                                """,
                        """
                                import org.assertj.core.api.AbstractObjectAssert;
                                import org.assertj.core.api.Assertions;
                                
                                class Test {
                                    AbstractObjectAssert<?, ?> test() {
                                        return Assertions.assertThatThrownBy(() -> {
                                        }).isInstanceOf(NullPointerException.class).hasMessage("foo");
                                    }
                                }
                                """
                ));
    }

    @Test
    void testAssertThatThrownByNullPointerExceptionHasMessageContainingRecipe() {
        Recipe recipe = RewriteUtils.loadRecipe("AssertJThrowingCallableRulesRecipes.AssertThatThrownByNullPointerExceptionHasMessageContainingRecipe");
        rewriteRun(
                spec -> spec.recipe(recipe)
                        .expectedCyclesThatMakeChanges(1).cycles(1)
                        .parser(JavaParser.fromJavaVersion().classpath(JavaParser.runtimeClasspath())),
                java(
                        """
                                import org.assertj.core.api.AbstractObjectAssert;
                                import org.assertj.core.api.Assertions;
                                
                                class Test {
                                    AbstractObjectAssert<?, ?> test() {
                                        return Assertions.assertThatNullPointerException().isThrownBy(() -> {
                                        }).withMessageContaining("foo");
                                    }
                                }
                                """,
                        """
                                import org.assertj.core.api.AbstractObjectAssert;
                                import org.assertj.core.api.Assertions;
                                
                                class Test {
                                    AbstractObjectAssert<?, ?> test() {
                                        return Assertions.assertThatThrownBy(() -> {
                                        }).isInstanceOf(NullPointerException.class).hasMessageContaining("foo");
                                    }
                                }
                                """
                ));
    }

    @Test
    void testAssertThatThrownByNullPointerExceptionHasMessageNotContainingRecipe() {
        Recipe recipe = RewriteUtils.loadRecipe("AssertJThrowingCallableRulesRecipes.AssertThatThrownByNullPointerExceptionHasMessageNotContainingRecipe");
        rewriteRun(
                spec -> spec.recipe(recipe)
                        .expectedCyclesThatMakeChanges(1).cycles(1)
                        .parser(JavaParser.fromJavaVersion().classpath(JavaParser.runtimeClasspath())),
                java(
                        """
                                import org.assertj.core.api.AbstractObjectAssert;
                                import org.assertj.core.api.Assertions;
                                
                                class Test {
                                    AbstractObjectAssert<?, ?> test() {
                                        return Assertions.assertThatNullPointerException().isThrownBy(() -> {
                                        }).withMessageNotContaining("foo");
                                    }
                                }
                                """,
                        """
                                import org.assertj.core.api.AbstractObjectAssert;
                                import org.assertj.core.api.Assertions;
                                
                                class Test {
                                    AbstractObjectAssert<?, ?> test() {
                                        return Assertions.assertThatThrownBy(() -> {
                                        }).isInstanceOf(NullPointerException.class).hasMessageNotContaining("foo");
                                    }
                                }
                                """
                ));
    }

    @Test
    void testAssertThatThrownByNullPointerExceptionHasMessageParametersRecipe() {
        Recipe recipe = RewriteUtils.loadRecipe("AssertJThrowingCallableRulesRecipes.AssertThatThrownByNullPointerExceptionHasMessageParametersRecipe");
        rewriteRun(
                spec -> spec.recipe(recipe)
                        .expectedCyclesThatMakeChanges(1).cycles(1)
                        .parser(JavaParser.fromJavaVersion().classpath(JavaParser.runtimeClasspath())),
                java(
                        """
                                import org.assertj.core.api.AbstractObjectAssert;
                                import org.assertj.core.api.Assertions;
                                
                                class Test {
                                    AbstractObjectAssert<?, ?> test() {
                                        return Assertions.assertThatNullPointerException().isThrownBy(() -> {
                                        }).withMessage("foo %s", "bar");
                                    }
                                }
                                """,
                        """
                                import org.assertj.core.api.AbstractObjectAssert;
                                import org.assertj.core.api.Assertions;
                                
                                class Test {
                                    AbstractObjectAssert<?, ?> test() {
                                        return Assertions.assertThatThrownBy(() -> {
                                        }).isInstanceOf(NullPointerException.class).hasMessage("foo %s", "bar");
                                    }
                                }
                                """
                ));
    }

    @Test
    void testAssertThatThrownByNullPointerExceptionHasMessageStartingWithRecipe() {
        Recipe recipe = RewriteUtils.loadRecipe("AssertJThrowingCallableRulesRecipes.AssertThatThrownByNullPointerExceptionHasMessageStartingWithRecipe");
        rewriteRun(
                spec -> spec.recipe(recipe)
                        .expectedCyclesThatMakeChanges(1).cycles(1)
                        .parser(JavaParser.fromJavaVersion().classpath(JavaParser.runtimeClasspath())),
                java(
                        """
                                import org.assertj.core.api.AbstractObjectAssert;
                                import org.assertj.core.api.Assertions;
                                
                                class Test {
                                    AbstractObjectAssert<?, ?> test() {
                                        return Assertions.assertThatNullPointerException().isThrownBy(() -> {
                                        }).withMessageStartingWith("foo");
                                    }
                                }
                                """,
                        """
                                import org.assertj.core.api.AbstractObjectAssert;
                                import org.assertj.core.api.Assertions;
                                
                                class Test {
                                    AbstractObjectAssert<?, ?> test() {
                                        return Assertions.assertThatThrownBy(() -> {
                                        }).isInstanceOf(NullPointerException.class).hasMessageStartingWith("foo");
                                    }
                                }
                                """
                ));
    }

    @Test
    void testAssertThatThrownByNullPointerExceptionRootCauseHasMessageRecipe() {
        Recipe recipe = RewriteUtils.loadRecipe("AssertJThrowingCallableRulesRecipes.AssertThatThrownByNullPointerExceptionRootCauseHasMessageRecipe");
        rewriteRun(
                spec -> spec.recipe(recipe)
                        .expectedCyclesThatMakeChanges(1).cycles(1)
                        .parser(JavaParser.fromJavaVersion().classpath(JavaParser.runtimeClasspath())),
                java(
                        """
                                import org.assertj.core.api.AbstractObjectAssert;
                                import org.assertj.core.api.Assertions;
                                
                                class Test {
                                    AbstractObjectAssert<?, ?> test() {
                                        return Assertions.assertThatNullPointerException().isThrownBy(() -> {
                                        }).havingRootCause().withMessage("foo");
                                    }
                                }
                                """,
                        """
                                import org.assertj.core.api.AbstractObjectAssert;
                                import org.assertj.core.api.Assertions;
                                
                                class Test {
                                    AbstractObjectAssert<?, ?> test() {
                                        return Assertions.assertThatThrownBy(() -> {
                                        }).isInstanceOf(NullPointerException.class).rootCause().hasMessage("foo");
                                    }
                                }
                                """
                ));
    }

    @Test
    void testAssertThatThrownByRootCauseHasMessageRecipe() {
        Recipe recipe = RewriteUtils.loadRecipe("AssertJThrowingCallableRulesRecipes.AssertThatThrownByRootCauseHasMessageRecipe");
        rewriteRun(
                spec -> spec.recipe(recipe)
                        .expectedCyclesThatMakeChanges(1).cycles(1)
                        .parser(JavaParser.fromJavaVersion().classpath(JavaParser.runtimeClasspath())),
                java(
                        """
                                import org.assertj.core.api.AbstractObjectAssert;
                                import org.assertj.core.api.Assertions;
                                
                                class Test {
                                    AbstractObjectAssert<?, ?> test() {
                                        return Assertions.assertThatExceptionOfType(IllegalArgumentException.class).isThrownBy(() -> {
                                        }).havingRootCause().withMessage("foo");
                                    }
                                }
                                """,
                        """
                                import org.assertj.core.api.AbstractObjectAssert;
                                import org.assertj.core.api.Assertions;
                                
                                class Test {
                                    AbstractObjectAssert<?, ?> test() {
                                        return Assertions.assertThatThrownBy(() -> {
                                        }).isInstanceOf(IllegalArgumentException.class).rootCause().hasMessage("foo");
                                    }
                                }
                                """
                ));
    }
}
