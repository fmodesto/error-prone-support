package tech.picnic.errorprone.refasterrules.rewrite;

import org.junit.jupiter.api.Test;
import org.openrewrite.Recipe;
import org.openrewrite.java.JavaParser;
import org.openrewrite.test.RewriteTest;

import static org.openrewrite.java.Assertions.java;

final class AssertJIteratorRulesRecipesTest implements RewriteTest {

    @Test
    void testAssertThatHasNextRecipe() {
        Recipe recipe = RewriteUtils.loadRecipe("AssertJIteratorRulesRecipes.AssertThatHasNextRecipe");
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
                                    AbstractAssert<?, ?> test() {
                                        return Assertions.assertThat(ImmutableSet.of().iterator().hasNext()).isTrue();
                                    }
                                }
                                """,
                        """
                                import com.google.common.collect.ImmutableSet;
                                import org.assertj.core.api.AbstractAssert;
                                import org.assertj.core.api.Assertions;
                                
                                class Test {
                                    AbstractAssert<?, ?> test() {
                                        return Assertions.assertThat(ImmutableSet.of().iterator()).hasNext();
                                    }
                                }
                                """
                ));
    }

    @Test
    void testAssertThatIsExhaustedRecipe() {
        Recipe recipe = RewriteUtils.loadRecipe("AssertJIteratorRulesRecipes.AssertThatIsExhaustedRecipe");
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
                                    AbstractAssert<?, ?> test() {
                                        return Assertions.assertThat(ImmutableSet.of().iterator().hasNext()).isFalse();
                                    }
                                }
                                """,
                        """
                                import com.google.common.collect.ImmutableSet;
                                import org.assertj.core.api.AbstractAssert;
                                import org.assertj.core.api.Assertions;
                                
                                class Test {
                                    AbstractAssert<?, ?> test() {
                                        return Assertions.assertThat(ImmutableSet.of().iterator()).isExhausted();
                                    }
                                }
                                """
                ));
    }
}
