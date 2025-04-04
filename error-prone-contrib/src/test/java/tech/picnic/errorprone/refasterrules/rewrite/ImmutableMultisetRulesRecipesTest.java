package tech.picnic.errorprone.refasterrules.rewrite;

import org.junit.jupiter.api.Test;
import org.openrewrite.Recipe;
import org.openrewrite.java.JavaParser;
import org.openrewrite.test.RewriteTest;

import static org.openrewrite.java.Assertions.java;

final class ImmutableMultisetRulesRecipesTest implements RewriteTest {

    @Test
    void testEmptyImmutableMultisetRecipe() {
        Recipe recipe = RewriteUtils.loadRecipe("ImmutableMultisetRulesRecipes.EmptyImmutableMultisetRecipe");
        rewriteRun(
                spec -> spec.recipe(recipe)
                        .expectedCyclesThatMakeChanges(1).cycles(1)
                        .parser(JavaParser.fromJavaVersion().classpath(JavaParser.runtimeClasspath())),
                java(
                        """
                                import com.google.common.collect.ImmutableMultiset;
                                
                                import java.util.stream.Stream;
                                
                                class Test {
                                    ImmutableMultiset<ImmutableMultiset<Integer>> test() {
                                        return ImmutableMultiset.of(ImmutableMultiset.<Integer>builder().build(), Stream.<Integer>empty().collect(ImmutableMultiset.toImmutableMultiset()));
                                    }
                                }
                                """,
                        """
                                import com.google.common.collect.ImmutableMultiset;
                                
                                class Test {
                                    ImmutableMultiset<ImmutableMultiset<Integer>> test() {
                                        return ImmutableMultiset.of(ImmutableMultiset.of(), ImmutableMultiset.of());
                                    }
                                }
                                """
                ));
    }

    @Test
    void testImmutableMultisetBuilderRecipe() {
        Recipe recipe = RewriteUtils.loadRecipe("ImmutableMultisetRulesRecipes.ImmutableMultisetBuilderRecipe");
        rewriteRun(
                spec -> spec.recipe(recipe)
                        .expectedCyclesThatMakeChanges(1).cycles(1)
                        .parser(JavaParser.fromJavaVersion().classpath(JavaParser.runtimeClasspath())),
                java(
                        """
                                import com.google.common.collect.ImmutableMultiset;
                                
                                class Test {
                                    ImmutableMultiset.Builder<String> test() {
                                        return new ImmutableMultiset.Builder<>();
                                    }
                                }
                                """,
                        """
                                import com.google.common.collect.ImmutableMultiset;
                                
                                class Test {
                                    ImmutableMultiset.Builder<String> test() {
                                        return ImmutableMultiset.builder();
                                    }
                                }
                                """
                ));
    }

    @Test
    void testIterableToImmutableMultisetRecipe() {
        Recipe recipe = RewriteUtils.loadRecipe("ImmutableMultisetRulesRecipes.IterableToImmutableMultisetRecipe");
        rewriteRun(
                spec -> spec.recipe(recipe)
                        .expectedCyclesThatMakeChanges(1).cycles(1)
                        .parser(JavaParser.fromJavaVersion().classpath(JavaParser.runtimeClasspath())),
                java(
                        """
                                import com.google.common.collect.ImmutableList;
                                import com.google.common.collect.ImmutableMultiset;
                                import com.google.common.collect.Streams;
                                
                                import java.util.Arrays;
                                
                                class Test {
                                    @SuppressWarnings("unchecked")
                                    ImmutableMultiset<ImmutableMultiset<Integer>> test() {
                                        return ImmutableMultiset.of(ImmutableList.of(1).stream().collect(ImmutableMultiset.toImmutableMultiset()), Streams.stream(ImmutableList.of(2)::iterator).collect(ImmutableMultiset.toImmutableMultiset()), Streams.stream(ImmutableList.of(3).iterator()).collect(ImmutableMultiset.toImmutableMultiset()), ImmutableMultiset.<Integer>builder().addAll(ImmutableMultiset.of(4)).build(), ImmutableMultiset.<Integer>builder().addAll(ImmutableMultiset.of(5)::iterator).build(), ImmutableMultiset.<Integer>builder().addAll(ImmutableMultiset.of(6).iterator()).build(), ImmutableMultiset.<Integer>builder().add(new Integer[]{7}).build(), Arrays.stream(new Integer[]{8}).collect(ImmutableMultiset.toImmutableMultiset()));
                                    }
                                }
                                """,
                        """
                                import com.google.common.collect.ImmutableList;
                                import com.google.common.collect.ImmutableMultiset;
                                
                                class Test {
                                    @SuppressWarnings("unchecked")
                                    ImmutableMultiset<ImmutableMultiset<Integer>> test() {
                                        return ImmutableMultiset.of(ImmutableMultiset.copyOf(ImmutableList.of(1)), ImmutableMultiset.copyOf(ImmutableList.of(2)::iterator), ImmutableMultiset.copyOf(ImmutableList.of(3).iterator()), ImmutableMultiset.copyOf(ImmutableMultiset.of(4)), ImmutableMultiset.copyOf(ImmutableMultiset.of(5)::iterator), ImmutableMultiset.copyOf(ImmutableMultiset.of(6).iterator()), ImmutableMultiset.copyOf(new Integer[]{7}), ImmutableMultiset.copyOf(new Integer[]{8}));
                                    }
                                }
                                """
                ));
    }

    @Test
    void testStreamToImmutableMultisetRecipe() {
        Recipe recipe = RewriteUtils.loadRecipe("ImmutableMultisetRulesRecipes.StreamToImmutableMultisetRecipe");
        rewriteRun(
                spec -> spec.recipe(recipe)
                        .expectedCyclesThatMakeChanges(1).cycles(1)
                        .parser(JavaParser.fromJavaVersion().classpath(JavaParser.runtimeClasspath())),
                java(
                        """
                                import com.google.common.collect.ImmutableMultiset;
                                
                                import java.util.stream.Stream;
                                
                                class Test {
                                    ImmutableMultiset<Integer> test() {
                                        return ImmutableMultiset.copyOf(Stream.of(1).iterator());
                                    }
                                }
                                """,
                        """
                                import com.google.common.collect.ImmutableMultiset;
                                
                                import java.util.stream.Stream;
                                
                                class Test {
                                    ImmutableMultiset<Integer> test() {
                                        return Stream.of(1).collect(ImmutableMultiset.toImmutableMultiset());
                                    }
                                }
                                """
                ));
    }
}
