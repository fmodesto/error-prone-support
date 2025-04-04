package tech.picnic.errorprone.refasterrules.rewrite;

import org.junit.jupiter.api.Test;
import org.openrewrite.Recipe;
import org.openrewrite.java.JavaParser;
import org.openrewrite.test.RewriteTest;

import static org.openrewrite.java.Assertions.java;

final class SuggestedFixRulesRecipesTest implements RewriteTest {

    @Test
    void testSuggestedFixDeleteRecipe() {
        Recipe recipe = RewriteUtils.loadRecipe("SuggestedFixRulesRecipes.SuggestedFixDeleteRecipe");
        rewriteRun(
                spec -> spec.recipe(recipe)
                        .expectedCyclesThatMakeChanges(1).cycles(1)
                        .parser(JavaParser.fromJavaVersion().classpath(JavaParser.runtimeClasspath())),
                java(
                        """
                                import com.google.errorprone.fixes.SuggestedFix;
                                
                                class Test {
                                    SuggestedFix test() {
                                        return SuggestedFix.builder().delete(null).build();
                                    }
                                }
                                """,
                        """
                                import com.google.errorprone.fixes.SuggestedFix;
                                
                                class Test {
                                    SuggestedFix test() {
                                        return SuggestedFix.delete(null);
                                    }
                                }
                                """
                ));
    }

    @Test
    void testSuggestedFixPostfixWithRecipe() {
        Recipe recipe = RewriteUtils.loadRecipe("SuggestedFixRulesRecipes.SuggestedFixPostfixWithRecipe");
        rewriteRun(
                spec -> spec.recipe(recipe)
                        .expectedCyclesThatMakeChanges(1).cycles(1)
                        .parser(JavaParser.fromJavaVersion().classpath(JavaParser.runtimeClasspath())),
                java(
                        """
                                import com.google.errorprone.fixes.SuggestedFix;
                                
                                class Test {
                                    SuggestedFix test() {
                                        return SuggestedFix.builder().postfixWith(null, "foo").build();
                                    }
                                }
                                """,
                        """
                                import com.google.errorprone.fixes.SuggestedFix;
                                
                                class Test {
                                    SuggestedFix test() {
                                        return SuggestedFix.postfixWith(null, "foo");
                                    }
                                }
                                """
                ));
    }

    @Test
    void testSuggestedFixPrefixWithRecipe() {
        Recipe recipe = RewriteUtils.loadRecipe("SuggestedFixRulesRecipes.SuggestedFixPrefixWithRecipe");
        rewriteRun(
                spec -> spec.recipe(recipe)
                        .expectedCyclesThatMakeChanges(1).cycles(1)
                        .parser(JavaParser.fromJavaVersion().classpath(JavaParser.runtimeClasspath())),
                java(
                        """
                                import com.google.errorprone.fixes.SuggestedFix;
                                
                                class Test {
                                    SuggestedFix test() {
                                        return SuggestedFix.builder().prefixWith(null, "foo").build();
                                    }
                                }
                                """,
                        """
                                import com.google.errorprone.fixes.SuggestedFix;
                                
                                class Test {
                                    SuggestedFix test() {
                                        return SuggestedFix.prefixWith(null, "foo");
                                    }
                                }
                                """
                ));
    }

    @Test
    void testSuggestedFixReplaceStartEndRecipe() {
        Recipe recipe = RewriteUtils.loadRecipe("SuggestedFixRulesRecipes.SuggestedFixReplaceStartEndRecipe");
        rewriteRun(
                spec -> spec.recipe(recipe)
                        .expectedCyclesThatMakeChanges(1).cycles(1)
                        .parser(JavaParser.fromJavaVersion().classpath(JavaParser.runtimeClasspath())),
                java(
                        """
                                import com.google.errorprone.fixes.SuggestedFix;
                                
                                class Test {
                                    SuggestedFix test() {
                                        return SuggestedFix.builder().replace(1, 2, "foo").build();
                                    }
                                }
                                """,
                        """
                                import com.google.errorprone.fixes.SuggestedFix;
                                
                                class Test {
                                    SuggestedFix test() {
                                        return SuggestedFix.replace(1, 2, "foo");
                                    }
                                }
                                """
                ));
    }

    @Test
    void testSuggestedFixReplaceTreeRecipe() {
        Recipe recipe = RewriteUtils.loadRecipe("SuggestedFixRulesRecipes.SuggestedFixReplaceTreeRecipe");
        rewriteRun(
                spec -> spec.recipe(recipe)
                        .expectedCyclesThatMakeChanges(1).cycles(1)
                        .parser(JavaParser.fromJavaVersion().classpath(JavaParser.runtimeClasspath())),
                java(
                        """
                                import com.google.errorprone.fixes.SuggestedFix;
                                
                                class Test {
                                    SuggestedFix test() {
                                        return SuggestedFix.builder().replace(null, "foo").build();
                                    }
                                }
                                """,
                        """
                                import com.google.errorprone.fixes.SuggestedFix;
                                
                                class Test {
                                    SuggestedFix test() {
                                        return SuggestedFix.replace(null, "foo");
                                    }
                                }
                                """
                ));
    }

    @Test
    void testSuggestedFixReplaceTreeStartEndRecipe() {
        Recipe recipe = RewriteUtils.loadRecipe("SuggestedFixRulesRecipes.SuggestedFixReplaceTreeStartEndRecipe");
        rewriteRun(
                spec -> spec.recipe(recipe)
                        .expectedCyclesThatMakeChanges(1).cycles(1)
                        .parser(JavaParser.fromJavaVersion().classpath(JavaParser.runtimeClasspath())),
                java(
                        """
                                import com.google.errorprone.fixes.SuggestedFix;
                                
                                class Test {
                                    SuggestedFix test() {
                                        return SuggestedFix.builder().replace(null, "foo", 1, 2).build();
                                    }
                                }
                                """,
                        """
                                import com.google.errorprone.fixes.SuggestedFix;
                                
                                class Test {
                                    SuggestedFix test() {
                                        return SuggestedFix.replace(null, "foo", 1, 2);
                                    }
                                }
                                """
                ));
    }

    @Test
    void testSuggestedFixSwapRecipe() {
        Recipe recipe = RewriteUtils.loadRecipe("SuggestedFixRulesRecipes.SuggestedFixSwapRecipe");
        rewriteRun(
                spec -> spec.recipe(recipe)
                        .expectedCyclesThatMakeChanges(1).cycles(1)
                        .parser(JavaParser.fromJavaVersion().classpath(JavaParser.runtimeClasspath())),
                java(
                        """
                                import com.google.errorprone.VisitorState;
                                import com.google.errorprone.fixes.SuggestedFix;
                                import com.sun.source.tree.ExpressionTree;
                                import com.sun.source.tree.Tree;
                                
                                class Test {
                                    SuggestedFix test() {
                                        return SuggestedFix.builder().swap((Tree) null, (ExpressionTree) null, (VisitorState) null).build();
                                    }
                                }
                                """,
                        """
                                import com.google.errorprone.VisitorState;
                                import com.google.errorprone.fixes.SuggestedFix;
                                import com.sun.source.tree.ExpressionTree;
                                import com.sun.source.tree.Tree;
                                
                                class Test {
                                    SuggestedFix test() {
                                        return SuggestedFix.swap((Tree) null, (ExpressionTree) null, (VisitorState) null);
                                    }
                                }
                                """
                ));
    }
}
