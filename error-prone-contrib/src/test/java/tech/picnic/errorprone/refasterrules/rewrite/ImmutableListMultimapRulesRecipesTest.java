package tech.picnic.errorprone.refasterrules.rewrite;

import org.junit.jupiter.api.Test;
import org.openrewrite.Recipe;
import org.openrewrite.java.JavaParser;
import org.openrewrite.test.RewriteTest;

import static org.openrewrite.java.Assertions.java;

final class ImmutableListMultimapRulesRecipesTest implements RewriteTest {

    @Test
    void testEmptyImmutableListMultimapRecipe() {
        Recipe recipe = RewriteUtils.loadRecipe("ImmutableListMultimapRulesRecipes.EmptyImmutableListMultimapRecipe");
        rewriteRun(
                spec -> spec.recipe(recipe)
                        .expectedCyclesThatMakeChanges(1).cycles(1)
                        .parser(JavaParser.fromJavaVersion().classpath(JavaParser.runtimeClasspath())),
                java(
                        """
                                import com.google.common.collect.ImmutableListMultimap;
                                import com.google.common.collect.ImmutableMultimap;
                                import com.google.common.collect.ImmutableSet;
                                
                                class Test {
                                    ImmutableSet<ImmutableMultimap<String, Integer>> test() {
                                        return ImmutableSet.of(ImmutableListMultimap.<String, Integer>builder().build(), ImmutableMultimap.of());
                                    }
                                }
                                """,
                        """
                                import com.google.common.collect.ImmutableListMultimap;
                                import com.google.common.collect.ImmutableMultimap;
                                import com.google.common.collect.ImmutableSet;
                                
                                class Test {
                                    ImmutableSet<ImmutableMultimap<String, Integer>> test() {
                                        return ImmutableSet.of(ImmutableListMultimap.of(), ImmutableListMultimap.of());
                                    }
                                }
                                """
                ));
    }

    @Test
    void testEntryToImmutableListMultimapRecipe() {
        Recipe recipe = RewriteUtils.loadRecipe("ImmutableListMultimapRulesRecipes.EntryToImmutableListMultimapRecipe");
        rewriteRun(
                spec -> spec.recipe(recipe)
                        .expectedCyclesThatMakeChanges(1).cycles(1)
                        .parser(JavaParser.fromJavaVersion().classpath(JavaParser.runtimeClasspath())),
                java(
                        """
                                import com.google.common.collect.ImmutableList;
                                import com.google.common.collect.ImmutableListMultimap;
                                import com.google.common.collect.ImmutableMultimap;
                                
                                import java.util.Map;
                                import java.util.function.Function;
                                import java.util.stream.Stream;
                                
                                class Test {
                                    ImmutableList<ImmutableMultimap<String, Integer>> test() {
                                        return ImmutableList.of(ImmutableListMultimap.<String, Integer>builder().put(Map.entry("foo", 1)).build(), Stream.of(Map.entry("foo", 1)).collect(ImmutableListMultimap.toImmutableListMultimap(Map.Entry::getKey, Map.Entry::getValue)));
                                    }
                                }
                                """,
                        """
                                import com.google.common.collect.ImmutableList;
                                import com.google.common.collect.ImmutableListMultimap;
                                import com.google.common.collect.ImmutableMultimap;
                                
                                import java.util.Map;
                                
                                class Test {
                                    ImmutableList<ImmutableMultimap<String, Integer>> test() {
                                        return ImmutableList.of(ImmutableListMultimap.of(Map.entry("foo", 1).getKey(), Map.entry("foo", 1).getValue()), ImmutableListMultimap.of(Map.entry("foo", 1).getKey(), Map.entry("foo", 1).getValue()));
                                    }
                                }
                                """
                ));
    }

    @Test
    void testImmutableListMultimapBuilderRecipe() {
        Recipe recipe = RewriteUtils.loadRecipe("ImmutableListMultimapRulesRecipes.ImmutableListMultimapBuilderRecipe");
        rewriteRun(
                spec -> spec.recipe(recipe)
                        .expectedCyclesThatMakeChanges(1).cycles(1)
                        .parser(JavaParser.fromJavaVersion().classpath(JavaParser.runtimeClasspath())),
                java(
                        """
                                import com.google.common.collect.ImmutableListMultimap;
                                import com.google.common.collect.ImmutableMultimap;
                                import com.google.common.collect.ImmutableSet;
                                
                                class Test {
                                    ImmutableSet<ImmutableMultimap.Builder<String, Integer>> test() {
                                        return ImmutableSet.of(new ImmutableListMultimap.Builder<>(), new ImmutableMultimap.Builder<>(), ImmutableMultimap.builder());
                                    }
                                }
                                """,
                        """
                                import com.google.common.collect.ImmutableListMultimap;
                                import com.google.common.collect.ImmutableMultimap;
                                import com.google.common.collect.ImmutableSet;
                                
                                class Test {
                                    ImmutableSet<ImmutableMultimap.Builder<String, Integer>> test() {
                                        return ImmutableSet.of(ImmutableListMultimap.builder(), ImmutableListMultimap.builder(), ImmutableListMultimap.builder());
                                    }
                                }
                                """
                ));
    }

    @Test
    void testIndexIterableToImmutableListMultimapRecipe() {
        Recipe recipe = RewriteUtils.loadRecipe("ImmutableListMultimapRulesRecipes.IndexIterableToImmutableListMultimapRecipe");
        rewriteRun(
                spec -> spec.recipe(recipe)
                        .expectedCyclesThatMakeChanges(1).cycles(1)
                        .parser(JavaParser.fromJavaVersion().classpath(JavaParser.runtimeClasspath())),
                java(
                        """
                                import com.google.common.collect.ImmutableList;
                                import com.google.common.collect.ImmutableListMultimap;
                                import com.google.common.collect.ImmutableSet;
                                import com.google.common.collect.Streams;
                                
                                import java.util.function.Function;
                                
                                class Test {
                                    ImmutableSet<ImmutableListMultimap<Integer, Integer>> test() {
                                        return ImmutableSet.of(Streams.stream(ImmutableList.of(1).iterator()).collect(ImmutableListMultimap.toImmutableListMultimap(n -> n * 2, Function.identity())), Streams.stream(ImmutableList.of(2).iterator()).collect(ImmutableListMultimap.toImmutableListMultimap(n -> n * 2, v -> v)), Streams.stream(ImmutableList.of(3).iterator()).collect(ImmutableListMultimap.toImmutableListMultimap(n -> n * 2, v -> 0)), Streams.stream(ImmutableList.of(4)::iterator).collect(ImmutableListMultimap.toImmutableListMultimap(Integer::valueOf, Function.identity())), Streams.stream(ImmutableList.of(5)::iterator).collect(ImmutableListMultimap.toImmutableListMultimap(Integer::valueOf, v -> v)), Streams.stream(ImmutableList.of(6)::iterator).collect(ImmutableListMultimap.toImmutableListMultimap(Integer::valueOf, v -> 0)), ImmutableList.of(7).stream().collect(ImmutableListMultimap.toImmutableListMultimap(n -> n.intValue(), Function.identity())), ImmutableList.of(8).stream().collect(ImmutableListMultimap.toImmutableListMultimap(n -> n.intValue(), v -> v)), ImmutableList.of(9).stream().collect(ImmutableListMultimap.toImmutableListMultimap(n -> n.intValue(), v -> 0)));
                                    }
                                }
                                """,
                        """
                                import com.google.common.collect.*;
                                
                                import java.util.function.Function;
                                
                                class Test {
                                    ImmutableSet<ImmutableListMultimap<Integer, Integer>> test() {
                                        return ImmutableSet.of(Multimaps.index(ImmutableList.of(1).iterator(), n -> n * 2), Multimaps.index(ImmutableList.of(2).iterator(), n -> n * 2), Streams.stream(ImmutableList.of(3).iterator()).collect(ImmutableListMultimap.toImmutableListMultimap(n -> n * 2, v -> 0)), Multimaps.index(ImmutableList.of(4)::iterator, Integer::valueOf), Multimaps.index(ImmutableList.of(5)::iterator, Integer::valueOf), Streams.stream(ImmutableList.of(6)::iterator).collect(ImmutableListMultimap.toImmutableListMultimap(Integer::valueOf, v -> 0)), Multimaps.index(ImmutableList.of(7), n -> n.intValue()), Multimaps.index(ImmutableList.of(8), n -> n.intValue()), ImmutableList.of(9).stream().collect(ImmutableListMultimap.toImmutableListMultimap(n -> n.intValue(), v -> 0)));
                                    }
                                }
                                """
                ));
    }

    @Test
    void testIterableToImmutableListMultimapRecipe() {
        Recipe recipe = RewriteUtils.loadRecipe("ImmutableListMultimapRulesRecipes.IterableToImmutableListMultimapRecipe");
        rewriteRun(
                spec -> spec.recipe(recipe)
                        .expectedCyclesThatMakeChanges(1).cycles(1)
                        .parser(JavaParser.fromJavaVersion().classpath(JavaParser.runtimeClasspath())),
                java(
                        """
                                import com.google.common.collect.*;
                                
                                import java.util.Map;
                                import java.util.function.Function;
                                
                                class Test {
                                    ImmutableList<ImmutableMultimap<String, Integer>> test() {
                                        return ImmutableList.of(ImmutableListMultimap.copyOf(ImmutableListMultimap.of("foo", 1).entries()), ImmutableListMultimap.<String, Integer>builder().putAll(ImmutableListMultimap.of("foo", 1)).build(), ImmutableListMultimap.<String, Integer>builder().putAll(ImmutableListMultimap.of("foo", 1).entries()).build(), ImmutableListMultimap.of("foo", 1).entries().stream().collect(ImmutableListMultimap.toImmutableListMultimap(Map.Entry::getKey, Map.Entry::getValue)), Streams.stream(Iterables.cycle(Map.entry("foo", 1))).collect(ImmutableListMultimap.toImmutableListMultimap(Map.Entry::getKey, Map.Entry::getValue)), ImmutableMultimap.copyOf(ImmutableListMultimap.of("foo", 1)), ImmutableMultimap.copyOf(ImmutableListMultimap.of("foo", 1).entries()), ImmutableMultimap.copyOf(Iterables.cycle(Map.entry("foo", 1))));
                                    }
                                }
                                """,
                        """
                                import com.google.common.collect.ImmutableList;
                                import com.google.common.collect.ImmutableListMultimap;
                                import com.google.common.collect.ImmutableMultimap;
                                import com.google.common.collect.Iterables;
                                
                                import java.util.Map;
                                
                                class Test {
                                    ImmutableList<ImmutableMultimap<String, Integer>> test() {
                                        return ImmutableList.of(ImmutableListMultimap.copyOf(ImmutableListMultimap.of("foo", 1)), ImmutableListMultimap.copyOf(ImmutableListMultimap.of("foo", 1)), ImmutableListMultimap.copyOf(ImmutableListMultimap.of("foo", 1).entries()), ImmutableListMultimap.copyOf(ImmutableListMultimap.of("foo", 1).entries()), ImmutableListMultimap.copyOf(Iterables.cycle(Map.entry("foo", 1))), ImmutableListMultimap.copyOf(ImmutableListMultimap.of("foo", 1)), ImmutableListMultimap.copyOf(ImmutableListMultimap.of("foo", 1)), ImmutableListMultimap.copyOf(Iterables.cycle(Map.entry("foo", 1))));
                                    }
                                }
                                """
                ));
    }

    @Test
    void testPairToImmutableListMultimapRecipe() {
        Recipe recipe = RewriteUtils.loadRecipe("ImmutableListMultimapRulesRecipes.PairToImmutableListMultimapRecipe");
        rewriteRun(
                spec -> spec.recipe(recipe)
                        .expectedCyclesThatMakeChanges(1).cycles(1)
                        .parser(JavaParser.fromJavaVersion().classpath(JavaParser.runtimeClasspath())),
                java(
                        """
                                import com.google.common.collect.ImmutableListMultimap;
                                import com.google.common.collect.ImmutableMultimap;
                                import com.google.common.collect.ImmutableSet;
                                
                                class Test {
                                    ImmutableSet<ImmutableMultimap<String, Integer>> test() {
                                        return ImmutableSet.of(ImmutableListMultimap.<String, Integer>builder().put("foo", 1).build(), ImmutableMultimap.of("bar", 2));
                                    }
                                }
                                """,
                        """
                                import com.google.common.collect.ImmutableListMultimap;
                                import com.google.common.collect.ImmutableMultimap;
                                import com.google.common.collect.ImmutableSet;
                                
                                class Test {
                                    ImmutableSet<ImmutableMultimap<String, Integer>> test() {
                                        return ImmutableSet.of(ImmutableListMultimap.of("foo", 1), ImmutableListMultimap.of("bar", 2));
                                    }
                                }
                                """
                ));
    }

    @Test
    void testStreamOfMapEntriesToImmutableListMultimapRecipe() {
        Recipe recipe = RewriteUtils.loadRecipe("ImmutableListMultimapRulesRecipes.StreamOfMapEntriesToImmutableListMultimapRecipe");
        rewriteRun(
                spec -> spec.recipe(recipe)
                        .expectedCyclesThatMakeChanges(1).cycles(1)
                        .parser(JavaParser.fromJavaVersion().classpath(JavaParser.runtimeClasspath())),
                java(
                        """
                                import com.google.common.collect.ImmutableListMultimap;
                                
                                import java.util.Map;
                                import java.util.function.Function;
                                import java.util.stream.Stream;
                                
                                class Test {
                                    ImmutableListMultimap<Integer, String> test() {
                                        return Stream.of(1, 2, 3).map(n -> Map.entry(n, n.toString())).collect(ImmutableListMultimap.toImmutableListMultimap(Map.Entry::getKey, Map.Entry::getValue));
                                    }
                                }
                                """,
                        """
                                import com.google.common.collect.ImmutableListMultimap;
                                
                                import java.util.stream.Stream;
                                
                                class Test {
                                    ImmutableListMultimap<Integer, String> test() {
                                        return Stream.of(1, 2, 3).collect(ImmutableListMultimap.toImmutableListMultimap(n -> n, n -> n.toString()));
                                    }
                                }
                                """
                ));
    }

    @Test
    void testTransformMultimapValuesToImmutableListMultimapRecipe() {
        Recipe recipe = RewriteUtils.loadRecipe("ImmutableListMultimapRulesRecipes.TransformMultimapValuesToImmutableListMultimapRecipe");
        rewriteRun(
                spec -> spec.recipe(recipe)
                        .expectedCyclesThatMakeChanges(1).cycles(1)
                        .parser(JavaParser.fromJavaVersion().classpath(JavaParser.runtimeClasspath())),
                java(
                        """
                                import com.google.common.collect.ImmutableListMultimap;
                                
                                import java.util.Map;
                                import java.util.function.Function;
                                
                                class Test {
                                    ImmutableListMultimap<String, Integer> test() {
                                        return ImmutableListMultimap.of("foo", 1L).entries().stream().collect(ImmutableListMultimap.toImmutableListMultimap(Map.Entry::getKey, e -> Math.toIntExact(e.getValue())));
                                    }
                                }
                                """,
                        """
                                import com.google.common.collect.ImmutableListMultimap;
                                import com.google.common.collect.Multimaps;
                                
                                class Test {
                                    ImmutableListMultimap<String, Integer> test() {
                                        return ImmutableListMultimap.copyOf(Multimaps.transformValues(ImmutableListMultimap.of("foo", 1L), v -> Math.toIntExact(v)));
                                    }
                                }
                                """
                ));
    }

    @Test
    void testTransformMultimapValuesToImmutableListMultimap2Recipe() {
        Recipe recipe = RewriteUtils.loadRecipe("ImmutableListMultimapRulesRecipes.TransformMultimapValuesToImmutableListMultimap2Recipe");
        rewriteRun(
                spec -> spec.recipe(recipe)
                        .expectedCyclesThatMakeChanges(1).cycles(1)
                        .parser(JavaParser.fromJavaVersion().classpath(JavaParser.runtimeClasspath())),
                java(
                        """
                                import com.google.common.collect.*;
                                
                                import java.util.Map;
                                import java.util.function.Function;
                                
                                class Test {
                                    ImmutableSet<ImmutableListMultimap<String, Integer>> test() {
                                        return ImmutableSet.of(ImmutableSetMultimap.of("foo", 1L).asMap().entrySet().stream().collect(ImmutableListMultimap.flatteningToImmutableListMultimap(Map.Entry::getKey, e -> e.getValue().stream().map(Math::toIntExact))), Multimaps.asMap((Multimap<String, Long>) ImmutableSetMultimap.of("bar", 2L)).entrySet().stream().collect(ImmutableListMultimap.flatteningToImmutableListMultimap(Map.Entry::getKey, e -> e.getValue().stream().map(n -> Math.toIntExact(n)))), Multimaps.asMap(ImmutableListMultimap.of("baz", 3L)).entrySet().stream().collect(ImmutableListMultimap.flatteningToImmutableListMultimap(Map.Entry::getKey, e -> e.getValue().stream().map(Math::toIntExact))), Multimaps.asMap(ImmutableSetMultimap.of("qux", 4L)).entrySet().stream().collect(ImmutableListMultimap.flatteningToImmutableListMultimap(Map.Entry::getKey, e -> e.getValue().stream().map(n -> Math.toIntExact(n)))), Multimaps.asMap(TreeMultimap.<String, Long>create()).entrySet().stream().collect(ImmutableListMultimap.flatteningToImmutableListMultimap(Map.Entry::getKey, e -> e.getValue().stream().map(Math::toIntExact))));
                                    }
                                }
                                """,
                        """
                                import com.google.common.collect.*;
                                
                                class Test {
                                    ImmutableSet<ImmutableListMultimap<String, Integer>> test() {
                                        return ImmutableSet.of(ImmutableListMultimap.copyOf(Multimaps.transformValues(ImmutableSetMultimap.of("foo", 1L), Math::toIntExact)), ImmutableListMultimap.copyOf(Multimaps.transformValues((Multimap<String, Long>) ImmutableSetMultimap.of("bar", 2L), n -> Math.toIntExact(n))), ImmutableListMultimap.copyOf(Multimaps.transformValues(ImmutableListMultimap.of("baz", 3L), Math::toIntExact)), ImmutableListMultimap.copyOf(Multimaps.transformValues(ImmutableSetMultimap.of("qux", 4L), n -> Math.toIntExact(n))), ImmutableListMultimap.copyOf(Multimaps.transformValues(TreeMultimap.<String, Long>create(), Math::toIntExact)));
                                    }
                                }
                                """
                ));
    }
}
