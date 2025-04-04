package tech.picnic.errorprone.refasterrules.rewrite;

import org.junit.jupiter.api.Test;
import org.openrewrite.Recipe;
import org.openrewrite.java.JavaParser;
import org.openrewrite.test.RewriteTest;

import static org.openrewrite.java.Assertions.java;

final class ImmutableSortedMultisetRulesRecipesTest implements RewriteTest {

    @Test
    void testEmptyImmutableSortedMultisetRecipe() {
        Recipe recipe = RewriteUtils.loadRecipe("ImmutableSortedMultisetRulesRecipes.EmptyImmutableSortedMultisetRecipe");
        rewriteRun(
                spec -> spec.recipe(recipe)
                        .expectedCyclesThatMakeChanges(1).cycles(1)
                        .parser(JavaParser.fromJavaVersion().classpath(JavaParser.runtimeClasspath())),
                java(
                        """
                                import com.google.common.collect.ImmutableMultiset;
                                import com.google.common.collect.ImmutableSortedMultiset;
                                
                                import java.util.Comparator;
                                import java.util.stream.Stream;
                                
                                class Test {
                                    ImmutableMultiset<ImmutableSortedMultiset<Integer>> test() {
                                        return ImmutableMultiset.of(ImmutableSortedMultiset.<Integer>naturalOrder().build(), Stream.<Integer>empty().collect(ImmutableSortedMultiset.toImmutableSortedMultiset(Comparator.naturalOrder())));
                                    }
                                }
                                """,
                        """
                                import com.google.common.collect.ImmutableMultiset;
                                import com.google.common.collect.ImmutableSortedMultiset;
                                
                                class Test {
                                    ImmutableMultiset<ImmutableSortedMultiset<Integer>> test() {
                                        return ImmutableMultiset.of(ImmutableSortedMultiset.of(), ImmutableSortedMultiset.of());
                                    }
                                }
                                """
                ));
    }

    @Test
    void testImmutableSortedMultisetBuilderRecipe() {
        Recipe recipe = RewriteUtils.loadRecipe("ImmutableSortedMultisetRulesRecipes.ImmutableSortedMultisetBuilderRecipe");
        rewriteRun(
                spec -> spec.recipe(recipe)
                        .expectedCyclesThatMakeChanges(1).cycles(1)
                        .parser(JavaParser.fromJavaVersion().classpath(JavaParser.runtimeClasspath())),
                java(
                        """
                                import com.google.common.collect.ImmutableSortedMultiset;
                                
                                import java.util.Comparator;
                                
                                class Test {
                                    ImmutableSortedMultiset.Builder<String> test() {
                                        return new ImmutableSortedMultiset.Builder<>(Comparator.comparingInt(String::length));
                                    }
                                }
                                """,
                        """
                                import com.google.common.collect.ImmutableSortedMultiset;
                                
                                import java.util.Comparator;
                                
                                class Test {
                                    ImmutableSortedMultiset.Builder<String> test() {
                                        return ImmutableSortedMultiset.orderedBy(Comparator.comparingInt(String::length));
                                    }
                                }
                                """
                ));
    }

    @Test
    void testImmutableSortedMultisetNaturalOrderBuilderRecipe() {
        Recipe recipe = RewriteUtils.loadRecipe("ImmutableSortedMultisetRulesRecipes.ImmutableSortedMultisetNaturalOrderBuilderRecipe");
        rewriteRun(
                spec -> spec.recipe(recipe)
                        .expectedCyclesThatMakeChanges(1).cycles(1)
                        .parser(JavaParser.fromJavaVersion().classpath(JavaParser.runtimeClasspath())),
                java(
                        """
                                import com.google.common.collect.ImmutableSortedMultiset;
                                
                                import java.util.Comparator;
                                
                                class Test {
                                    ImmutableSortedMultiset.Builder<String> test() {
                                        return ImmutableSortedMultiset.orderedBy(Comparator.<String>naturalOrder());
                                    }
                                }
                                """,
                        """
                                import com.google.common.collect.ImmutableSortedMultiset;
                                
                                class Test {
                                    ImmutableSortedMultiset.Builder<String> test() {
                                        return ImmutableSortedMultiset.naturalOrder();
                                    }
                                }
                                """
                ));
    }

    @Test
    void testImmutableSortedMultisetReverseOrderBuilderRecipe() {
        Recipe recipe = RewriteUtils.loadRecipe("ImmutableSortedMultisetRulesRecipes.ImmutableSortedMultisetReverseOrderBuilderRecipe");
        rewriteRun(
                spec -> spec.recipe(recipe)
                        .expectedCyclesThatMakeChanges(1).cycles(1)
                        .parser(JavaParser.fromJavaVersion().classpath(JavaParser.runtimeClasspath())),
                java(
                        """
                                import com.google.common.collect.ImmutableSortedMultiset;
                                
                                import java.util.Comparator;
                                
                                class Test {
                                    ImmutableSortedMultiset.Builder<String> test() {
                                        return ImmutableSortedMultiset.orderedBy(Comparator.<String>reverseOrder());
                                    }
                                }
                                """,
                        """
                                import com.google.common.collect.ImmutableSortedMultiset;
                                
                                class Test {
                                    ImmutableSortedMultiset.Builder<String> test() {
                                        return ImmutableSortedMultiset.reverseOrder();
                                    }
                                }
                                """
                ));
    }

    @Test
    void testIterableToImmutableSortedMultisetRecipe() {
        Recipe recipe = RewriteUtils.loadRecipe("ImmutableSortedMultisetRulesRecipes.IterableToImmutableSortedMultisetRecipe");
        rewriteRun(
                spec -> spec.recipe(recipe)
                        .expectedCyclesThatMakeChanges(1).cycles(1)
                        .parser(JavaParser.fromJavaVersion().classpath(JavaParser.runtimeClasspath())),
                java(
                        """
                                import com.google.common.collect.ImmutableList;
                                import com.google.common.collect.ImmutableMultiset;
                                import com.google.common.collect.ImmutableSortedMultiset;
                                import com.google.common.collect.Streams;
                                
                                import java.util.Arrays;
                                import java.util.Comparator;
                                
                                class Test {
                                    @SuppressWarnings("unchecked")
                                    ImmutableMultiset<ImmutableSortedMultiset<Integer>> test() {
                                        return ImmutableMultiset.of(ImmutableSortedMultiset.copyOf(Comparator.naturalOrder(), ImmutableList.of(1)), ImmutableSortedMultiset.copyOf(Comparator.naturalOrder(), ImmutableList.of(2).iterator()), ImmutableList.of(3).stream().collect(ImmutableSortedMultiset.toImmutableSortedMultiset(Comparator.naturalOrder())), Streams.stream(ImmutableList.of(4)::iterator).collect(ImmutableSortedMultiset.toImmutableSortedMultiset(Comparator.naturalOrder())), Streams.stream(ImmutableList.of(5).iterator()).collect(ImmutableSortedMultiset.toImmutableSortedMultiset(Comparator.naturalOrder())), ImmutableSortedMultiset.<Integer>naturalOrder().addAll(ImmutableMultiset.of(6)).build(), ImmutableSortedMultiset.<Integer>naturalOrder().addAll(ImmutableMultiset.of(7)::iterator).build(), ImmutableSortedMultiset.<Integer>naturalOrder().addAll(ImmutableMultiset.of(8).iterator()).build(), ImmutableSortedMultiset.<Integer>naturalOrder().add(new Integer[]{9}).build(), Arrays.stream(new Integer[]{10}).collect(ImmutableSortedMultiset.toImmutableSortedMultiset(Comparator.naturalOrder())));
                                    }
                                }
                                """,
                        """
                                import com.google.common.collect.ImmutableList;
                                import com.google.common.collect.ImmutableMultiset;
                                import com.google.common.collect.ImmutableSortedMultiset;
                                
                                class Test {
                                    @SuppressWarnings("unchecked")
                                    ImmutableMultiset<ImmutableSortedMultiset<Integer>> test() {
                                        return ImmutableMultiset.of(ImmutableSortedMultiset.copyOf(ImmutableList.of(1)), ImmutableSortedMultiset.copyOf(ImmutableList.of(2).iterator()), ImmutableSortedMultiset.copyOf(ImmutableList.of(3)), ImmutableSortedMultiset.copyOf(ImmutableList.of(4)::iterator), ImmutableSortedMultiset.copyOf(ImmutableList.of(5).iterator()), ImmutableSortedMultiset.copyOf(ImmutableMultiset.of(6)), ImmutableSortedMultiset.copyOf(ImmutableMultiset.of(7)::iterator), ImmutableSortedMultiset.copyOf(ImmutableMultiset.of(8).iterator()), ImmutableSortedMultiset.copyOf(new Integer[]{9}), ImmutableSortedMultiset.copyOf(new Integer[]{10}));
                                    }
                                }
                                """
                ));
    }

    @Test
    void testStreamToImmutableSortedMultisetRecipe() {
        Recipe recipe = RewriteUtils.loadRecipe("ImmutableSortedMultisetRulesRecipes.StreamToImmutableSortedMultisetRecipe");
        rewriteRun(
                spec -> spec.recipe(recipe)
                        .expectedCyclesThatMakeChanges(1).cycles(1)
                        .parser(JavaParser.fromJavaVersion().classpath(JavaParser.runtimeClasspath())),
                java(
                        """
                                import com.google.common.collect.ImmutableSortedMultiset;
                                
                                import java.util.stream.Stream;
                                
                                class Test {
                                    ImmutableSortedMultiset<Integer> test() {
                                        return ImmutableSortedMultiset.copyOf(Stream.of(1).iterator());
                                    }
                                }
                                """,
                        """
                                import com.google.common.collect.ImmutableSortedMultiset;
                                
                                import java.util.Comparator;
                                import java.util.stream.Stream;
                                
                                class Test {
                                    ImmutableSortedMultiset<Integer> test() {
                                        return Stream.of(1).collect(ImmutableSortedMultiset.toImmutableSortedMultiset(Comparator.naturalOrder()));
                                    }
                                }
                                """
                ));
    }
}
