package tech.picnic.errorprone.refasterrules.rewrite;

import org.junit.jupiter.api.Test;
import org.openrewrite.Recipe;
import org.openrewrite.java.JavaParser;
import org.openrewrite.test.RewriteTest;

import static org.openrewrite.java.Assertions.java;

final class ImmutableSortedSetRulesRecipesTest implements RewriteTest {

    @Test
    void testEmptyImmutableSortedSetRecipe() {
        Recipe recipe = RewriteUtils.loadRecipe("ImmutableSortedSetRulesRecipes.EmptyImmutableSortedSetRecipe");
        rewriteRun(
                spec -> spec.recipe(recipe)
                        .expectedCyclesThatMakeChanges(1).cycles(1)
                        .parser(JavaParser.fromJavaVersion().classpath(JavaParser.runtimeClasspath())),
                java(
                        """
                                import com.google.common.collect.ImmutableSet;
                                import com.google.common.collect.ImmutableSortedSet;
                                
                                import java.util.Comparator;
                                import java.util.stream.Stream;
                                
                                class Test {
                                    ImmutableSet<ImmutableSortedSet<Integer>> test() {
                                        return ImmutableSet.of(ImmutableSortedSet.<Integer>naturalOrder().build(), Stream.<Integer>empty().collect(ImmutableSortedSet.toImmutableSortedSet(Comparator.naturalOrder())));
                                    }
                                }
                                """,
                        """
                                import com.google.common.collect.ImmutableSet;
                                import com.google.common.collect.ImmutableSortedSet;
                                
                                class Test {
                                    ImmutableSet<ImmutableSortedSet<Integer>> test() {
                                        return ImmutableSet.of(ImmutableSortedSet.of(), ImmutableSortedSet.of());
                                    }
                                }
                                """
                ));
    }

    @Test
    void testImmutableSortedSetBuilderRecipe() {
        Recipe recipe = RewriteUtils.loadRecipe("ImmutableSortedSetRulesRecipes.ImmutableSortedSetBuilderRecipe");
        rewriteRun(
                spec -> spec.recipe(recipe)
                        .expectedCyclesThatMakeChanges(1).cycles(1)
                        .parser(JavaParser.fromJavaVersion().classpath(JavaParser.runtimeClasspath())),
                java(
                        """
                                import com.google.common.collect.ImmutableSortedSet;
                                
                                import java.util.Comparator;
                                
                                class Test {
                                    ImmutableSortedSet.Builder<String> test() {
                                        return new ImmutableSortedSet.Builder<>(Comparator.comparingInt(String::length));
                                    }
                                }
                                """,
                        """
                                import com.google.common.collect.ImmutableSortedSet;
                                
                                import java.util.Comparator;
                                
                                class Test {
                                    ImmutableSortedSet.Builder<String> test() {
                                        return ImmutableSortedSet.orderedBy(Comparator.comparingInt(String::length));
                                    }
                                }
                                """
                ));
    }

    @Test
    void testImmutableSortedSetNaturalOrderBuilderRecipe() {
        Recipe recipe = RewriteUtils.loadRecipe("ImmutableSortedSetRulesRecipes.ImmutableSortedSetNaturalOrderBuilderRecipe");
        rewriteRun(
                spec -> spec.recipe(recipe)
                        .expectedCyclesThatMakeChanges(1).cycles(1)
                        .parser(JavaParser.fromJavaVersion().classpath(JavaParser.runtimeClasspath())),
                java(
                        """
                                import com.google.common.collect.ImmutableSortedSet;
                                
                                import java.util.Comparator;
                                
                                class Test {
                                    ImmutableSortedSet.Builder<String> test() {
                                        return ImmutableSortedSet.orderedBy(Comparator.<String>naturalOrder());
                                    }
                                }
                                """,
                        """
                                import com.google.common.collect.ImmutableSortedSet;
                                
                                class Test {
                                    ImmutableSortedSet.Builder<String> test() {
                                        return ImmutableSortedSet.naturalOrder();
                                    }
                                }
                                """
                ));
    }

    @Test
    void testImmutableSortedSetReverseOrderBuilderRecipe() {
        Recipe recipe = RewriteUtils.loadRecipe("ImmutableSortedSetRulesRecipes.ImmutableSortedSetReverseOrderBuilderRecipe");
        rewriteRun(
                spec -> spec.recipe(recipe)
                        .expectedCyclesThatMakeChanges(1).cycles(1)
                        .parser(JavaParser.fromJavaVersion().classpath(JavaParser.runtimeClasspath())),
                java(
                        """
                                import com.google.common.collect.ImmutableSortedSet;
                                
                                import java.util.Comparator;
                                
                                class Test {
                                    ImmutableSortedSet.Builder<String> test() {
                                        return ImmutableSortedSet.orderedBy(Comparator.<String>reverseOrder());
                                    }
                                }
                                """,
                        """
                                import com.google.common.collect.ImmutableSortedSet;
                                
                                class Test {
                                    ImmutableSortedSet.Builder<String> test() {
                                        return ImmutableSortedSet.reverseOrder();
                                    }
                                }
                                """
                ));
    }

    @Test
    void testIterableToImmutableSortedSetRecipe() {
        Recipe recipe = RewriteUtils.loadRecipe("ImmutableSortedSetRulesRecipes.IterableToImmutableSortedSetRecipe");
        rewriteRun(
                spec -> spec.recipe(recipe)
                        .expectedCyclesThatMakeChanges(1).cycles(1)
                        .parser(JavaParser.fromJavaVersion().classpath(JavaParser.runtimeClasspath())),
                java(
                        """
                                import com.google.common.collect.ImmutableList;
                                import com.google.common.collect.ImmutableSet;
                                import com.google.common.collect.ImmutableSortedSet;
                                import com.google.common.collect.Streams;
                                
                                import java.util.Arrays;
                                import java.util.Comparator;
                                
                                class Test {
                                    ImmutableSet<ImmutableSortedSet<Integer>> test() {
                                        return ImmutableSet.of(ImmutableSortedSet.copyOf(Comparator.naturalOrder(), ImmutableList.of(1)), ImmutableSortedSet.copyOf(Comparator.naturalOrder(), ImmutableList.of(2).iterator()), ImmutableList.of(3).stream().collect(ImmutableSortedSet.toImmutableSortedSet(Comparator.naturalOrder())), Streams.stream(ImmutableList.of(4)::iterator).collect(ImmutableSortedSet.toImmutableSortedSet(Comparator.naturalOrder())), Streams.stream(ImmutableList.of(5).iterator()).collect(ImmutableSortedSet.toImmutableSortedSet(Comparator.naturalOrder())), ImmutableSortedSet.<Integer>naturalOrder().addAll(ImmutableSet.of(6)).build(), ImmutableSortedSet.<Integer>naturalOrder().addAll(ImmutableSet.of(7)::iterator).build(), ImmutableSortedSet.<Integer>naturalOrder().addAll(ImmutableSet.of(8).iterator()).build(), ImmutableSortedSet.<Integer>naturalOrder().add(new Integer[]{9}).build(), Arrays.stream(new Integer[]{10}).collect(ImmutableSortedSet.toImmutableSortedSet(Comparator.naturalOrder())));
                                    }
                                }
                                """,
                        """
                                import com.google.common.collect.ImmutableList;
                                import com.google.common.collect.ImmutableSet;
                                import com.google.common.collect.ImmutableSortedSet;
                                
                                import java.util.Comparator;
                                
                                class Test {
                                    ImmutableSet<ImmutableSortedSet<Integer>> test() {
                                        return ImmutableSet.of(ImmutableSortedSet.copyOf(Comparator.naturalOrder(), ImmutableList.of(1)), ImmutableSortedSet.copyOf(ImmutableList.of(2).iterator()), ImmutableSortedSet.copyOf(ImmutableList.of(3)), ImmutableSortedSet.copyOf(ImmutableList.of(4)::iterator), ImmutableSortedSet.copyOf(ImmutableList.of(5).iterator()), ImmutableSortedSet.copyOf(ImmutableSet.of(6)), ImmutableSortedSet.copyOf(ImmutableSet.of(7)::iterator), ImmutableSortedSet.copyOf(ImmutableSet.of(8).iterator()), ImmutableSortedSet.copyOf(new Integer[]{9}), ImmutableSortedSet.copyOf(new Integer[]{10}));
                                    }
                                }
                                """
                ));
    }

    @Test
    void testStreamToImmutableSortedSetRecipe() {
        Recipe recipe = RewriteUtils.loadRecipe("ImmutableSortedSetRulesRecipes.StreamToImmutableSortedSetRecipe");
        rewriteRun(
                spec -> spec.recipe(recipe)
                        .expectedCyclesThatMakeChanges(1).cycles(1)
                        .parser(JavaParser.fromJavaVersion().classpath(JavaParser.runtimeClasspath())),
                java(
                        """
                                import com.google.common.collect.ImmutableSortedSet;
                                
                                import java.util.stream.Stream;
                                
                                class Test {
                                    ImmutableSortedSet<Integer> test() {
                                        return ImmutableSortedSet.copyOf(Stream.of(1).iterator());
                                    }
                                }
                                """,
                        """
                                import com.google.common.collect.ImmutableSortedSet;
                                
                                import java.util.Comparator;
                                import java.util.stream.Stream;
                                
                                class Test {
                                    ImmutableSortedSet<Integer> test() {
                                        return Stream.of(1).collect(ImmutableSortedSet.toImmutableSortedSet(Comparator.naturalOrder()));
                                    }
                                }
                                """
                ));
    }
}
