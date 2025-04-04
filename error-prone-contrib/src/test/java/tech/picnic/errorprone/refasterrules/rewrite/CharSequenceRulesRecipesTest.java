package tech.picnic.errorprone.refasterrules.rewrite;

import org.junit.jupiter.api.Test;
import org.openrewrite.Recipe;
import org.openrewrite.java.JavaParser;
import org.openrewrite.test.RewriteTest;

import static org.openrewrite.java.Assertions.java;

final class CharSequenceRulesRecipesTest implements RewriteTest {

    @Test
    void testCharSequenceIsEmptyRecipe() {
        Recipe recipe = RewriteUtils.loadRecipe("CharSequenceRulesRecipes.CharSequenceIsEmptyRecipe");
        rewriteRun(
                spec -> spec.recipe(recipe)
                        .expectedCyclesThatMakeChanges(1).cycles(1)
                        .parser(JavaParser.fromJavaVersion().classpath(JavaParser.runtimeClasspath())),
                java(
                        """
                                import com.google.common.collect.ImmutableSet;
                                
                                class Test {
                                    ImmutableSet<Boolean> test() {
                                        return ImmutableSet.of(new StringBuilder("foo").length() == 0, new StringBuilder("bar").length() <= 0, new StringBuilder("baz").length() < 1, new StringBuilder("qux").length() != 0, new StringBuilder("quux").length() > 0, new StringBuilder("corge").length() >= 1);
                                    }
                                }
                                """,
                        """
                                import com.google.common.collect.ImmutableSet;
                                
                                class Test {
                                    ImmutableSet<Boolean> test() {
                                        return ImmutableSet.of(new StringBuilder("foo").isEmpty(), new StringBuilder("bar").isEmpty(), new StringBuilder("baz").isEmpty(), !new StringBuilder("qux").isEmpty(), !new StringBuilder("quux").isEmpty(), !new StringBuilder("corge").isEmpty());
                                    }
                                }
                                """
                ));
    }
}
