package tech.picnic.errorprone.refasterrules.rewrite;

import org.junit.jupiter.api.Test;
import org.openrewrite.Recipe;
import org.openrewrite.java.JavaParser;
import org.openrewrite.test.RewriteTest;

import static org.openrewrite.java.Assertions.java;

final class AssertJIterableRulesRecipesTest implements RewriteTest {

    @Test
    void testAssertThatIterableHasOneElementEqualToRecipe() {
        Recipe recipe = RewriteUtils.loadRecipe("AssertJIterableRulesRecipes.AssertThatIterableHasOneElementEqualToRecipe");
        rewriteRun(
                spec -> spec.recipe(recipe)
                        .expectedCyclesThatMakeChanges(1).cycles(1)
                        .parser(JavaParser.fromJavaVersion().classpath(JavaParser.runtimeClasspath())),
                java(
                        """
                                import com.google.common.collect.ImmutableSet;
                                import com.google.common.collect.Iterables;
                                import org.assertj.core.api.AbstractAssert;
                                import org.assertj.core.api.Assertions;
                                
                                class Test {
                                    AbstractAssert<?, ?> test() {
                                        return Assertions.assertThat(Iterables.getOnlyElement(ImmutableSet.of(new Object()))).isEqualTo("foo");
                                    }
                                }
                                """,
                        """
                                import com.google.common.collect.ImmutableSet;
                                import org.assertj.core.api.AbstractAssert;
                                import org.assertj.core.api.Assertions;
                                
                                class Test {
                                    AbstractAssert<?, ?> test() {
                                        return Assertions.assertThat(ImmutableSet.of(new Object())).containsExactly("foo");
                                    }
                                }
                                """
                ));
    }

    @Test
    void testAssertThatIterableIsEmptyRecipe() {
        Recipe recipe = RewriteUtils.loadRecipe("AssertJIterableRulesRecipes.AssertThatIterableIsEmptyRecipe");
        rewriteRun(
                spec -> spec.recipe(recipe)
                        .expectedCyclesThatMakeChanges(1).cycles(1)
                        .parser(JavaParser.fromJavaVersion().classpath(JavaParser.runtimeClasspath())),
                java(
                        """
                                import com.google.common.collect.ImmutableSet;
                                import org.assertj.core.api.Assertions;
                                
                                class Test {
                                    void test() {
                                        Assertions.assertThat(ImmutableSet.of(1).iterator()).isExhausted();
                                        Assertions.assertThat(ImmutableSet.of(2).isEmpty()).isTrue();
                                    }
                                }
                                """,
                        """
                                import com.google.common.collect.ImmutableSet;
                                import org.assertj.core.api.Assertions;
                                
                                class Test {
                                    void test() {
                                        Assertions.assertThat(ImmutableSet.of(1)).isEmpty();
                                        Assertions.assertThat(ImmutableSet.of(2)).isEmpty();
                                    }
                                }
                                """
                ));
    }

    @Test
    void testAssertThatIterableIsNotEmptyRecipe() {
        Recipe recipe = RewriteUtils.loadRecipe("AssertJIterableRulesRecipes.AssertThatIterableIsNotEmptyRecipe");
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
                                        return ImmutableSet.of(Assertions.assertThat(ImmutableSet.of(1).iterator()).hasNext(), Assertions.assertThat(ImmutableSet.of(2).isEmpty()).isFalse());
                                    }
                                }
                                """,
                        """
                                import com.google.common.collect.ImmutableSet;
                                import org.assertj.core.api.AbstractAssert;
                                import org.assertj.core.api.Assertions;
                                
                                class Test {
                                    ImmutableSet<AbstractAssert<?, ?>> test() {
                                        return ImmutableSet.of(Assertions.assertThat(ImmutableSet.of(1)).isNotEmpty(), Assertions.assertThat(ImmutableSet.of(2)).isNotEmpty());
                                    }
                                }
                                """
                ));
    }

    @Test
    void testAssertThatIterableSizeRecipe() {
        Recipe recipe = RewriteUtils.loadRecipe("AssertJIterableRulesRecipes.AssertThatIterableSizeRecipe");
        rewriteRun(
                spec -> spec.recipe(recipe)
                        .expectedCyclesThatMakeChanges(1).cycles(1)
                        .parser(JavaParser.fromJavaVersion().classpath(JavaParser.runtimeClasspath())),
                java(
                        """
                                import com.google.common.collect.ImmutableSet;
                                import com.google.common.collect.Iterables;
                                import org.assertj.core.api.AbstractIntegerAssert;
                                import org.assertj.core.api.Assertions;
                                
                                class Test {
                                    ImmutableSet<AbstractIntegerAssert<?>> test() {
                                        return ImmutableSet.of(Assertions.assertThat(Iterables.size(ImmutableSet.of(1))), Assertions.assertThat(ImmutableSet.of(2).size()));
                                    }
                                }
                                """,
                        """
                                import com.google.common.collect.ImmutableSet;
                                import org.assertj.core.api.AbstractIntegerAssert;
                                import org.assertj.core.api.Assertions;
                                
                                class Test {
                                    ImmutableSet<AbstractIntegerAssert<?>> test() {
                                        return ImmutableSet.of(Assertions.assertThat(ImmutableSet.of(1)).size(), Assertions.assertThat(ImmutableSet.of(2)).size());
                                    }
                                }
                                """
                ));
    }
}
