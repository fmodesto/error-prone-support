package tech.picnic.errorprone.refasterrules.rewrite;

import org.junit.jupiter.api.Test;
import org.openrewrite.Recipe;
import org.openrewrite.java.JavaParser;
import org.openrewrite.test.RewriteTest;

import static org.openrewrite.java.Assertions.java;

final class PreconditionsRulesRecipesTest implements RewriteTest {

    @Test
    void testCheckArgumentRecipe() {
        Recipe recipe = RewriteUtils.loadRecipe("PreconditionsRulesRecipes.CheckArgumentRecipe");
        rewriteRun(
                spec -> spec.recipe(recipe)
                        .expectedCyclesThatMakeChanges(1).cycles(1)
                        .parser(JavaParser.fromJavaVersion().classpath(JavaParser.runtimeClasspath())),
                java(
                        """
                                class Test {
                                    void test() {
                                        if ("foo".isEmpty()) {
                                            throw new IllegalArgumentException();
                                        }
                                    }
                                }
                                """,
                        """
                                import com.google.common.base.Preconditions;
                                
                                class Test {
                                    void test() {
                                        Preconditions.checkArgument(!"foo".isEmpty());
                                    }
                                }
                                """
                ));
    }

    @Test
    void testCheckArgumentWithMessageRecipe() {
        Recipe recipe = RewriteUtils.loadRecipe("PreconditionsRulesRecipes.CheckArgumentWithMessageRecipe");
        rewriteRun(
                spec -> spec.recipe(recipe)
                        .expectedCyclesThatMakeChanges(1).cycles(1)
                        .parser(JavaParser.fromJavaVersion().classpath(JavaParser.runtimeClasspath())),
                java(
                        """
                                class Test {
                                    void test() {
                                        if ("foo".isEmpty()) {
                                            throw new IllegalArgumentException("The string is empty");
                                        }
                                    }
                                }
                                """,
                        """
                                import com.google.common.base.Preconditions;
                                
                                class Test {
                                    void test() {
                                        Preconditions.checkArgument(!"foo".isEmpty(), "The string is empty");
                                    }
                                }
                                """
                ));
    }

    @Test
    void testCheckElementIndexWithMessageRecipe() {
        Recipe recipe = RewriteUtils.loadRecipe("PreconditionsRulesRecipes.CheckElementIndexWithMessageRecipe");
        rewriteRun(
                spec -> spec.recipe(recipe)
                        .expectedCyclesThatMakeChanges(1).cycles(1)
                        .parser(JavaParser.fromJavaVersion().classpath(JavaParser.runtimeClasspath())),
                java(
                        """
                                class Test {
                                    void test() {
                                        if (1 < 0 || 1 >= 2) {
                                            throw new IndexOutOfBoundsException("My index");
                                        }
                                    }
                                }
                                """,
                        """
                                import com.google.common.base.Preconditions;
                                
                                class Test {
                                    void test() {
                                        Preconditions.checkElementIndex(1, 2, "My index");
                                    }
                                }
                                """
                ));
    }

    @Test
    void testCheckPositionIndexRecipe() {
        Recipe recipe = RewriteUtils.loadRecipe("PreconditionsRulesRecipes.CheckPositionIndexRecipe");
        rewriteRun(
                spec -> spec.recipe(recipe)
                        .expectedCyclesThatMakeChanges(1).cycles(1)
                        .parser(JavaParser.fromJavaVersion().classpath(JavaParser.runtimeClasspath())),
                java(
                        """
                                class Test {
                                    void test() {
                                        if (1 < 0 || 1 > 2) {
                                            throw new IndexOutOfBoundsException();
                                        }
                                    }
                                }
                                """,
                        """
                                import com.google.common.base.Preconditions;
                                
                                class Test {
                                    void test() {
                                        Preconditions.checkPositionIndex(1, 2);
                                    }
                                }
                                """
                ));
    }

    @Test
    void testCheckPositionIndexWithMessageRecipe() {
        Recipe recipe = RewriteUtils.loadRecipe("PreconditionsRulesRecipes.CheckPositionIndexWithMessageRecipe");
        rewriteRun(
                spec -> spec.recipe(recipe)
                        .expectedCyclesThatMakeChanges(1).cycles(1)
                        .parser(JavaParser.fromJavaVersion().classpath(JavaParser.runtimeClasspath())),
                java(
                        """
                                class Test {
                                    void test() {
                                        if (1 < 0 || 1 > 2) {
                                            throw new IndexOutOfBoundsException("My position");
                                        }
                                    }
                                }
                                """,
                        """
                                import com.google.common.base.Preconditions;
                                
                                class Test {
                                    void test() {
                                        Preconditions.checkPositionIndex(1, 2, "My position");
                                    }
                                }
                                """
                ));
    }

    @Test
    void testCheckStateRecipe() {
        Recipe recipe = RewriteUtils.loadRecipe("PreconditionsRulesRecipes.CheckStateRecipe");
        rewriteRun(
                spec -> spec.recipe(recipe)
                        .expectedCyclesThatMakeChanges(1).cycles(1)
                        .parser(JavaParser.fromJavaVersion().classpath(JavaParser.runtimeClasspath())),
                java(
                        """
                                class Test {
                                    void test() {
                                        if ("foo".isEmpty()) {
                                            throw new IllegalStateException();
                                        }
                                    }
                                }
                                """,
                        """
                                import com.google.common.base.Preconditions;
                                
                                class Test {
                                    void test() {
                                        Preconditions.checkState(!"foo".isEmpty());
                                    }
                                }
                                """
                ));
    }

    @Test
    void testCheckStateWithMessageRecipe() {
        Recipe recipe = RewriteUtils.loadRecipe("PreconditionsRulesRecipes.CheckStateWithMessageRecipe");
        rewriteRun(
                spec -> spec.recipe(recipe)
                        .expectedCyclesThatMakeChanges(1).cycles(1)
                        .parser(JavaParser.fromJavaVersion().classpath(JavaParser.runtimeClasspath())),
                java(
                        """
                                class Test {
                                    void test() {
                                        if ("foo".isEmpty()) {
                                            throw new IllegalStateException("The string is empty");
                                        }
                                    }
                                }
                                """,
                        """
                                import com.google.common.base.Preconditions;
                                
                                class Test {
                                    void test() {
                                        Preconditions.checkState(!"foo".isEmpty(), "The string is empty");
                                    }
                                }
                                """
                ));
    }

    @Test
    void testRequireNonNullRecipe() {
        Recipe recipe = RewriteUtils.loadRecipe("PreconditionsRulesRecipes.RequireNonNullRecipe");
        rewriteRun(
                spec -> spec.recipe(recipe)
                        .expectedCyclesThatMakeChanges(1).cycles(1)
                        .parser(JavaParser.fromJavaVersion().classpath(JavaParser.runtimeClasspath())),
                java(
                        """
                                import com.google.common.base.Preconditions;
                                
                                class Test {
                                    String test() {
                                        return Preconditions.checkNotNull("foo");
                                    }
                                }
                                """,
                        """
                                import java.util.Objects;
                                
                                class Test {
                                    String test() {
                                        return Objects.requireNonNull("foo");
                                    }
                                }
                                """
                ));
    }

    @Test
    void testRequireNonNullStatementRecipe() {
        Recipe recipe = RewriteUtils.loadRecipe("PreconditionsRulesRecipes.RequireNonNullStatementRecipe");
        rewriteRun(
                spec -> spec.recipe(recipe)
                        .expectedCyclesThatMakeChanges(1).cycles(1)
                        .parser(JavaParser.fromJavaVersion().classpath(JavaParser.runtimeClasspath())),
                java(
                        """
                                class Test {
                                    void test() {
                                        if ("foo" == null) {
                                            throw new NullPointerException();
                                        }
                                    }
                                }
                                """,
                        """
                                import java.util.Objects;
                                
                                class Test {
                                    void test() {
                                        Objects.requireNonNull("foo");
                                    }
                                }
                                """
                ));
    }

    @Test
    void testRequireNonNullWithMessageRecipe() {
        Recipe recipe = RewriteUtils.loadRecipe("PreconditionsRulesRecipes.RequireNonNullWithMessageRecipe");
        rewriteRun(
                spec -> spec.recipe(recipe)
                        .expectedCyclesThatMakeChanges(1).cycles(1)
                        .parser(JavaParser.fromJavaVersion().classpath(JavaParser.runtimeClasspath())),
                java(
                        """
                                import com.google.common.base.Preconditions;
                                
                                class Test {
                                    String test() {
                                        return Preconditions.checkNotNull("foo", "The string is null");
                                    }
                                }
                                """,
                        """
                                import java.util.Objects;
                                
                                class Test {
                                    String test() {
                                        return Objects.requireNonNull("foo", "The string is null");
                                    }
                                }
                                """
                ));
    }

    @Test
    void testRequireNonNullWithMessageStatementRecipe() {
        Recipe recipe = RewriteUtils.loadRecipe("PreconditionsRulesRecipes.RequireNonNullWithMessageStatementRecipe");
        rewriteRun(
                spec -> spec.recipe(recipe)
                        .expectedCyclesThatMakeChanges(1).cycles(1)
                        .parser(JavaParser.fromJavaVersion().classpath(JavaParser.runtimeClasspath())),
                java(
                        """
                                class Test {
                                    void test() {
                                        if ("foo" == null) {
                                            throw new NullPointerException("The string is null");
                                        }
                                    }
                                }
                                """,
                        """
                                import java.util.Objects;
                                
                                class Test {
                                    void test() {
                                        Objects.requireNonNull("foo", "The string is null");
                                    }
                                }
                                """
                ));
    }
}
