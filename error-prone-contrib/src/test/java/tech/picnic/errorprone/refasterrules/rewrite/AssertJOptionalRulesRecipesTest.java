package tech.picnic.errorprone.refasterrules.rewrite;

import org.junit.jupiter.api.Test;
import org.openrewrite.Recipe;
import org.openrewrite.java.JavaParser;
import org.openrewrite.test.RewriteTest;

import static org.openrewrite.java.Assertions.java;

final class AssertJOptionalRulesRecipesTest implements RewriteTest {

    @Test
    void testAbstractOptionalAssertContainsSameRecipe() {
        Recipe recipe = RewriteUtils.loadRecipe("AssertJOptionalRulesRecipes.AbstractOptionalAssertContainsSameRecipe");
        rewriteRun(
                spec -> spec.recipe(recipe)
                        .expectedCyclesThatMakeChanges(1).cycles(1)
                        .parser(JavaParser.fromJavaVersion().classpath(JavaParser.runtimeClasspath())),
                java(
                        """
                                import com.google.common.collect.ImmutableSet;
                                import org.assertj.core.api.AbstractAssert;
                                import org.assertj.core.api.Assertions;
                                
                                import java.util.Optional;
                                
                                class Test {
                                    ImmutableSet<AbstractAssert<?, ?>> test() {
                                        return ImmutableSet.of(Assertions.assertThat(Optional.of(1)).get().isSameAs(1), Assertions.assertThat(Optional.of(2)).isPresent().isSameAs(2));
                                    }
                                }
                                """,
                        """
                                import com.google.common.collect.ImmutableSet;
                                import org.assertj.core.api.AbstractAssert;
                                import org.assertj.core.api.Assertions;
                                
                                import java.util.Optional;
                                
                                class Test {
                                    ImmutableSet<AbstractAssert<?, ?>> test() {
                                        return ImmutableSet.of(Assertions.assertThat(Optional.of(1)).containsSame(1), Assertions.assertThat(Optional.of(2)).containsSame(2));
                                    }
                                }
                                """
                ));
    }

    @Test
    void testAbstractOptionalAssertHasValueRecipe() {
        Recipe recipe = RewriteUtils.loadRecipe("AssertJOptionalRulesRecipes.AbstractOptionalAssertHasValueRecipe");
        rewriteRun(
                spec -> spec.recipe(recipe)
                        .expectedCyclesThatMakeChanges(1).cycles(1)
                        .parser(JavaParser.fromJavaVersion().classpath(JavaParser.runtimeClasspath())),
                java(
                        """
                                import com.google.common.collect.ImmutableSet;
                                import org.assertj.core.api.AbstractAssert;
                                import org.assertj.core.api.Assertions;
                                
                                import java.util.Optional;
                                
                                class Test {
                                    ImmutableSet<AbstractAssert<?, ?>> test() {
                                        return ImmutableSet.of(Assertions.assertThat(Optional.of(1)).get().isEqualTo(1), Assertions.assertThat(Optional.of(2)).isEqualTo(Optional.of(2)), Assertions.assertThat(Optional.of(3)).contains(3), Assertions.assertThat(Optional.of(4)).isPresent().hasValue(4));
                                    }
                                }
                                """,
                        """
                                import com.google.common.collect.ImmutableSet;
                                import org.assertj.core.api.AbstractAssert;
                                import org.assertj.core.api.Assertions;
                                
                                import java.util.Optional;
                                
                                class Test {
                                    ImmutableSet<AbstractAssert<?, ?>> test() {
                                        return ImmutableSet.of(Assertions.assertThat(Optional.of(1)).hasValue(1), Assertions.assertThat(Optional.of(2)).hasValue(2), Assertions.assertThat(Optional.of(3)).hasValue(3), Assertions.assertThat(Optional.of(4)).hasValue(4));
                                    }
                                }
                                """
                ));
    }

    @Test
    void testAbstractOptionalAssertIsEmptyRecipe() {
        Recipe recipe = RewriteUtils.loadRecipe("AssertJOptionalRulesRecipes.AbstractOptionalAssertIsEmptyRecipe");
        rewriteRun(
                spec -> spec.recipe(recipe)
                        .expectedCyclesThatMakeChanges(1).cycles(1)
                        .parser(JavaParser.fromJavaVersion().classpath(JavaParser.runtimeClasspath())),
                java(
                        """
                                import com.google.common.collect.ImmutableSet;
                                import org.assertj.core.api.Assertions;
                                import org.assertj.core.api.OptionalAssert;
                                
                                import java.util.Optional;
                                
                                class Test {
                                    ImmutableSet<OptionalAssert<Integer>> test() {
                                        return ImmutableSet.of(Assertions.assertThat(Optional.of(1)).isNotPresent(), Assertions.assertThat(Optional.of(2)).isEqualTo(Optional.empty()));
                                    }
                                }
                                """,
                        """
                                import com.google.common.collect.ImmutableSet;
                                import org.assertj.core.api.Assertions;
                                import org.assertj.core.api.OptionalAssert;
                                
                                import java.util.Optional;
                                
                                class Test {
                                    ImmutableSet<OptionalAssert<Integer>> test() {
                                        return ImmutableSet.of(Assertions.assertThat(Optional.of(1)).isEmpty(), Assertions.assertThat(Optional.of(2)).isEmpty());
                                    }
                                }
                                """
                ));
    }

    @Test
    void testAbstractOptionalAssertIsPresentRecipe() {
        Recipe recipe = RewriteUtils.loadRecipe("AssertJOptionalRulesRecipes.AbstractOptionalAssertIsPresentRecipe");
        rewriteRun(
                spec -> spec.recipe(recipe)
                        .expectedCyclesThatMakeChanges(1).cycles(1)
                        .parser(JavaParser.fromJavaVersion().classpath(JavaParser.runtimeClasspath())),
                java(
                        """
                                import com.google.common.collect.ImmutableSet;
                                import org.assertj.core.api.Assertions;
                                import org.assertj.core.api.OptionalAssert;
                                
                                import java.util.Optional;
                                
                                class Test {
                                    ImmutableSet<OptionalAssert<Integer>> test() {
                                        return ImmutableSet.of(Assertions.assertThat(Optional.of(1)).isNotEmpty(), Assertions.assertThat(Optional.of(2)).isNotEqualTo(Optional.empty()));
                                    }
                                }
                                """,
                        """
                                import com.google.common.collect.ImmutableSet;
                                import org.assertj.core.api.Assertions;
                                import org.assertj.core.api.OptionalAssert;
                                
                                import java.util.Optional;
                                
                                class Test {
                                    ImmutableSet<OptionalAssert<Integer>> test() {
                                        return ImmutableSet.of(Assertions.assertThat(Optional.of(1)).isPresent(), Assertions.assertThat(Optional.of(2)).isPresent());
                                    }
                                }
                                """
                ));
    }

    @Test
    void testAssertThatOptionalRecipe() {
        Recipe recipe = RewriteUtils.loadRecipe("AssertJOptionalRulesRecipes.AssertThatOptionalRecipe");
        rewriteRun(
                spec -> spec.recipe(recipe)
                        .expectedCyclesThatMakeChanges(1).cycles(1)
                        .parser(JavaParser.fromJavaVersion().classpath(JavaParser.runtimeClasspath())),
                java(
                        """
                                import org.assertj.core.api.AbstractAssert;
                                import org.assertj.core.api.Assertions;
                                
                                import java.util.Optional;
                                
                                class Test {
                                    AbstractAssert<?, ?> test() {
                                        return Assertions.assertThat(Optional.of(new Object()).orElseThrow());
                                    }
                                }
                                """,
                        """
                                import org.assertj.core.api.AbstractAssert;
                                import org.assertj.core.api.Assertions;
                                
                                import java.util.Optional;
                                
                                class Test {
                                    AbstractAssert<?, ?> test() {
                                        return Assertions.assertThat(Optional.of(new Object())).get();
                                    }
                                }
                                """
                ));
    }

    @Test
    void testAssertThatOptionalHasValueMatchingRecipe() {
        Recipe recipe = RewriteUtils.loadRecipe("AssertJOptionalRulesRecipes.AssertThatOptionalHasValueMatchingRecipe");
        rewriteRun(
                spec -> spec.recipe(recipe)
                        .expectedCyclesThatMakeChanges(1).cycles(1)
                        .parser(JavaParser.fromJavaVersion().classpath(JavaParser.runtimeClasspath())),
                java(
                        """
                                import org.assertj.core.api.AbstractAssert;
                                import org.assertj.core.api.Assertions;
                                
                                import java.util.Optional;
                                
                                class Test {
                                    AbstractAssert<?, ?> test() {
                                        return Assertions.assertThat(Optional.of("foo").filter(String::isEmpty)).isPresent();
                                    }
                                }
                                """,
                        """
                                import org.assertj.core.api.AbstractAssert;
                                import org.assertj.core.api.Assertions;
                                
                                import java.util.Optional;
                                
                                class Test {
                                    AbstractAssert<?, ?> test() {
                                        return Assertions.assertThat(Optional.of("foo")).get().matches(String::isEmpty);
                                    }
                                }
                                """
                ));
    }

    @Test
    void testAssertThatOptionalIsEmptyRecipe() {
        Recipe recipe = RewriteUtils.loadRecipe("AssertJOptionalRulesRecipes.AssertThatOptionalIsEmptyRecipe");
        rewriteRun(
                spec -> spec.recipe(recipe)
                        .expectedCyclesThatMakeChanges(1).cycles(1)
                        .parser(JavaParser.fromJavaVersion().classpath(JavaParser.runtimeClasspath())),
                java(
                        """
                                import com.google.common.collect.ImmutableSet;
                                import org.assertj.core.api.AbstractAssert;
                                import org.assertj.core.api.Assertions;
                                
                                import java.util.Optional;
                                
                                class Test {
                                    ImmutableSet<AbstractAssert<?, ?>> test() {
                                        return ImmutableSet.of(Assertions.assertThat(Optional.of(1).isEmpty()).isTrue(), Assertions.assertThat(Optional.of(2).isPresent()).isFalse());
                                    }
                                }
                                """,
                        """
                                import com.google.common.collect.ImmutableSet;
                                import org.assertj.core.api.AbstractAssert;
                                import org.assertj.core.api.Assertions;
                                
                                import java.util.Optional;
                                
                                class Test {
                                    ImmutableSet<AbstractAssert<?, ?>> test() {
                                        return ImmutableSet.of(Assertions.assertThat(Optional.of(1)).isEmpty(), Assertions.assertThat(Optional.of(2)).isEmpty());
                                    }
                                }
                                """
                ));
    }

    @Test
    void testAssertThatOptionalIsPresentRecipe() {
        Recipe recipe = RewriteUtils.loadRecipe("AssertJOptionalRulesRecipes.AssertThatOptionalIsPresentRecipe");
        rewriteRun(
                spec -> spec.recipe(recipe)
                        .expectedCyclesThatMakeChanges(1).cycles(1)
                        .parser(JavaParser.fromJavaVersion().classpath(JavaParser.runtimeClasspath())),
                java(
                        """
                                import com.google.common.collect.ImmutableSet;
                                import org.assertj.core.api.AbstractAssert;
                                import org.assertj.core.api.Assertions;
                                
                                import java.util.Optional;
                                
                                class Test {
                                    ImmutableSet<AbstractAssert<?, ?>> test() {
                                        return ImmutableSet.of(Assertions.assertThat(Optional.of(1).isPresent()).isTrue(), Assertions.assertThat(Optional.of(2).isEmpty()).isFalse());
                                    }
                                }
                                """,
                        """
                                import com.google.common.collect.ImmutableSet;
                                import org.assertj.core.api.AbstractAssert;
                                import org.assertj.core.api.Assertions;
                                
                                import java.util.Optional;
                                
                                class Test {
                                    ImmutableSet<AbstractAssert<?, ?>> test() {
                                        return ImmutableSet.of(Assertions.assertThat(Optional.of(1)).isPresent(), Assertions.assertThat(Optional.of(2)).isPresent());
                                    }
                                }
                                """
                ));
    }
}
