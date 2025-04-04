package tech.picnic.errorprone.refasterrules.rewrite;

import org.junit.jupiter.api.Test;
import org.openrewrite.Recipe;
import org.openrewrite.java.JavaParser;
import org.openrewrite.test.RewriteTest;

import static org.openrewrite.java.Assertions.java;

final class JUnitRulesRecipesTest implements RewriteTest {

    @Test
    void testArgumentsEnumerationRecipe() {
        Recipe recipe = RewriteUtils.loadRecipe("JUnitRulesRecipes.ArgumentsEnumerationRecipe");
        rewriteRun(
                spec -> spec.recipe(recipe)
                        .expectedCyclesThatMakeChanges(1).cycles(1)
                        .parser(JavaParser.fromJavaVersion().classpath(JavaParser.runtimeClasspath())),
                java(
                        """
                                import com.google.common.collect.ImmutableSet;
                                import org.junit.jupiter.params.provider.Arguments;
                                
                                class Test {
                                    ImmutableSet<Arguments> test() {
                                        return ImmutableSet.of(Arguments.of("foo"), Arguments.of(1, "foo", 2, "bar"), Arguments.of(new Object()));
                                    }
                                }
                                """,
                        """
                                import com.google.common.collect.ImmutableSet;
                                import org.junit.jupiter.params.provider.Arguments;
                                
                                class Test {
                                    ImmutableSet<Arguments> test() {
                                        return ImmutableSet.of(Arguments.arguments("foo"), Arguments.arguments(1, "foo", 2, "bar"), Arguments.arguments(new Object()));
                                    }
                                }
                                """
                ));
    }
}
