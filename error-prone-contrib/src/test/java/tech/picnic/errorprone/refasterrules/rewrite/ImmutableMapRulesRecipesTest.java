package tech.picnic.errorprone.refasterrules.rewrite;

import org.junit.jupiter.api.Test;
import org.openrewrite.Recipe;
import org.openrewrite.java.JavaParser;
import org.openrewrite.test.RewriteTest;

import static org.openrewrite.java.Assertions.java;

final class ImmutableMapRulesRecipesTest implements RewriteTest {

    @Test
    void testEntryIterableToImmutableMapRecipe() {
        Recipe recipe = RewriteUtils.loadRecipe("ImmutableMapRulesRecipes.EntryIterableToImmutableMapRecipe");
        rewriteRun(
                spec -> spec.recipe(recipe)
                        .expectedCyclesThatMakeChanges(1).cycles(1)
                        .parser(JavaParser.fromJavaVersion().classpath(JavaParser.runtimeClasspath())),
                java(
                        """
                                import com.google.common.collect.ImmutableMap;
                                import com.google.common.collect.ImmutableSet;
                                import com.google.common.collect.Iterables;
                                import com.google.common.collect.Streams;
                                
                                import java.util.Map;
                                import java.util.function.Function;
                                
                                class Test {
                                    ImmutableSet<Map<String, Integer>> test() {
                                        return ImmutableSet.of(ImmutableMap.copyOf(ImmutableMap.of("foo", 1).entrySet()), ImmutableMap.<String, Integer>builder().putAll(ImmutableMap.of("foo", 1)).buildOrThrow(), Map.copyOf(ImmutableMap.of("foo", 1)), ImmutableMap.<String, Integer>builder().putAll(ImmutableMap.of("foo", 1).entrySet()).buildOrThrow(), ImmutableMap.of("foo", 1).entrySet().stream().collect(ImmutableMap.toImmutableMap(Map.Entry::getKey, Map.Entry::getValue)), Streams.stream(Iterables.cycle(Map.entry("foo", 1))).collect(ImmutableMap.toImmutableMap(Map.Entry::getKey, Map.Entry::getValue)));
                                    }
                                }
                                """,
                        """
                                import com.google.common.collect.ImmutableMap;
                                import com.google.common.collect.ImmutableSet;
                                import com.google.common.collect.Iterables;
                                
                                import java.util.Map;
                                
                                class Test {
                                    ImmutableSet<Map<String, Integer>> test() {
                                        return ImmutableSet.of(ImmutableMap.copyOf(ImmutableMap.of("foo", 1)), ImmutableMap.copyOf(ImmutableMap.of("foo", 1)), ImmutableMap.copyOf(ImmutableMap.of("foo", 1)), ImmutableMap.copyOf(ImmutableMap.of("foo", 1).entrySet()), ImmutableMap.copyOf(ImmutableMap.of("foo", 1).entrySet()), ImmutableMap.copyOf(Iterables.cycle(Map.entry("foo", 1))));
                                    }
                                }
                                """
                ));
    }

    @Test
    void testEntryToImmutableMapRecipe() {
        Recipe recipe = RewriteUtils.loadRecipe("ImmutableMapRulesRecipes.EntryToImmutableMapRecipe");
        rewriteRun(
                spec -> spec.recipe(recipe)
                        .expectedCyclesThatMakeChanges(1).cycles(1)
                        .parser(JavaParser.fromJavaVersion().classpath(JavaParser.runtimeClasspath())),
                java(
                        """
                                import com.google.common.collect.ImmutableMap;
                                import com.google.common.collect.ImmutableSet;
                                
                                import java.util.Map;
                                import java.util.function.Function;
                                import java.util.stream.Stream;
                                
                                class Test {
                                    ImmutableSet<ImmutableMap<String, Integer>> test() {
                                        return ImmutableSet.of(ImmutableMap.<String, Integer>builder().put(Map.entry("foo", 1)).buildOrThrow(), Stream.of(Map.entry("foo", 1)).collect(ImmutableMap.toImmutableMap(Map.Entry::getKey, Map.Entry::getValue)));
                                    }
                                }
                                """,
                        """
                                import com.google.common.collect.ImmutableMap;
                                import com.google.common.collect.ImmutableSet;
                                
                                import java.util.Map;
                                
                                class Test {
                                    ImmutableSet<ImmutableMap<String, Integer>> test() {
                                        return ImmutableSet.of(ImmutableMap.of(Map.entry("foo", 1).getKey(), Map.entry("foo", 1).getValue()), ImmutableMap.of(Map.entry("foo", 1).getKey(), Map.entry("foo", 1).getValue()));
                                    }
                                }
                                """
                ));
    }

    @Test
    void testImmutableMapBuilderRecipe() {
        Recipe recipe = RewriteUtils.loadRecipe("ImmutableMapRulesRecipes.ImmutableMapBuilderRecipe");
        rewriteRun(
                spec -> spec.recipe(recipe)
                        .expectedCyclesThatMakeChanges(1).cycles(1)
                        .parser(JavaParser.fromJavaVersion().classpath(JavaParser.runtimeClasspath())),
                java(
                        """
                                import com.google.common.collect.ImmutableMap;
                                
                                class Test {
                                    ImmutableMap.Builder<String, Integer> test() {
                                        return new ImmutableMap.Builder<>();
                                    }
                                }
                                """,
                        """
                                import com.google.common.collect.ImmutableMap;
                                
                                class Test {
                                    ImmutableMap.Builder<String, Integer> test() {
                                        return ImmutableMap.builder();
                                    }
                                }
                                """
                ));
    }

    @Test
    void testImmutableMapBuilderBuildOrThrowRecipe() {
        Recipe recipe = RewriteUtils.loadRecipe("ImmutableMapRulesRecipes.ImmutableMapBuilderBuildOrThrowRecipe");
        rewriteRun(
                spec -> spec.recipe(recipe)
                        .expectedCyclesThatMakeChanges(1).cycles(1)
                        .parser(JavaParser.fromJavaVersion().classpath(JavaParser.runtimeClasspath())),
                java(
                        """
                                import com.google.common.collect.ImmutableMap;
                                
                                class Test {
                                    ImmutableMap<Object, Object> test() {
                                        return ImmutableMap.builder().build();
                                    }
                                }
                                """,
                        """
                                import com.google.common.collect.ImmutableMap;
                                
                                class Test {
                                    ImmutableMap<Object, Object> test() {
                                        return ImmutableMap.builder().buildOrThrow();
                                    }
                                }
                                """
                ));
    }

    @Test
    void testImmutableMapCopyOfMapsFilterKeysRecipe() {
        Recipe recipe = RewriteUtils.loadRecipe("ImmutableMapRulesRecipes.ImmutableMapCopyOfMapsFilterKeysRecipe");
        rewriteRun(
                spec -> spec.recipe(recipe)
                        .expectedCyclesThatMakeChanges(1).cycles(1)
                        .parser(JavaParser.fromJavaVersion().classpath(JavaParser.runtimeClasspath())),
                java(
                        """
                                import com.google.common.collect.ImmutableMap;
                                
                                import java.util.Map;
                                import java.util.function.Function;
                                
                                class Test {
                                    ImmutableMap<String, Integer> test() {
                                        return ImmutableMap.of("foo", 1).entrySet().stream().filter(entry -> entry.getKey().length() > 1).collect(ImmutableMap.toImmutableMap(Map.Entry::getKey, Map.Entry::getValue));
                                    }
                                }
                                """,
                        """
                                import com.google.common.collect.ImmutableMap;
                                import com.google.common.collect.Maps;
                                
                                class Test {
                                    ImmutableMap<String, Integer> test() {
                                        return ImmutableMap.copyOf(Maps.filterKeys(ImmutableMap.of("foo", 1), k -> k.length() > 1));
                                    }
                                }
                                """
                ));
    }

    @Test
    void testImmutableMapCopyOfMapsFilterValuesRecipe() {
        Recipe recipe = RewriteUtils.loadRecipe("ImmutableMapRulesRecipes.ImmutableMapCopyOfMapsFilterValuesRecipe");
        rewriteRun(
                spec -> spec.recipe(recipe)
                        .expectedCyclesThatMakeChanges(1).cycles(1)
                        .parser(JavaParser.fromJavaVersion().classpath(JavaParser.runtimeClasspath())),
                java(
                        """
                                import com.google.common.collect.ImmutableMap;
                                
                                import java.util.Map;
                                import java.util.function.Function;
                                
                                class Test {
                                    ImmutableMap<String, Integer> test() {
                                        return ImmutableMap.of("foo", 1).entrySet().stream().filter(entry -> entry.getValue() > 0).collect(ImmutableMap.toImmutableMap(Map.Entry::getKey, Map.Entry::getValue));
                                    }
                                }
                                """,
                        """
                                import com.google.common.collect.ImmutableMap;
                                import com.google.common.collect.Maps;
                                
                                class Test {
                                    ImmutableMap<String, Integer> test() {
                                        return ImmutableMap.copyOf(Maps.filterValues(ImmutableMap.of("foo", 1), v -> v > 0));
                                    }
                                }
                                """
                ));
    }

    @Test
    void testImmutableMapOfRecipe() {
        Recipe recipe = RewriteUtils.loadRecipe("ImmutableMapRulesRecipes.ImmutableMapOfRecipe");
        rewriteRun(
                spec -> spec.recipe(recipe)
                        .expectedCyclesThatMakeChanges(1).cycles(1)
                        .parser(JavaParser.fromJavaVersion().classpath(JavaParser.runtimeClasspath())),
                java(
                        """
                                import com.google.common.collect.ImmutableMap;
                                import com.google.common.collect.ImmutableSet;
                                
                                import java.util.Collections;
                                import java.util.Map;
                                
                                class Test {
                                    ImmutableSet<Map<String, String>> test() {
                                        return ImmutableSet.of(ImmutableMap.<String, String>builder().buildOrThrow(), ImmutableMap.ofEntries(), Collections.<String, String>emptyMap(), Map.<String, String>of());
                                    }
                                }
                                """,
                        """
                                import com.google.common.collect.ImmutableMap;
                                import com.google.common.collect.ImmutableSet;
                                
                                import java.util.Map;
                                
                                class Test {
                                    ImmutableSet<Map<String, String>> test() {
                                        return ImmutableSet.of(ImmutableMap.of(), ImmutableMap.of(), ImmutableMap.of(), ImmutableMap.of());
                                    }
                                }
                                """
                ));
    }

    @Test
    void testImmutableMapOf1Recipe() {
        Recipe recipe = RewriteUtils.loadRecipe("ImmutableMapRulesRecipes.ImmutableMapOf1Recipe");
        rewriteRun(
                spec -> spec.recipe(recipe)
                        .expectedCyclesThatMakeChanges(1).cycles(1)
                        .parser(JavaParser.fromJavaVersion().classpath(JavaParser.runtimeClasspath())),
                java(
                        """
                                import com.google.common.collect.ImmutableMap;
                                import com.google.common.collect.ImmutableSet;
                                
                                import java.util.Collections;
                                import java.util.Map;
                                
                                class Test {
                                    ImmutableSet<Map<String, String>> test() {
                                        return ImmutableSet.of(ImmutableMap.<String, String>builder().put("k1", "v1").buildOrThrow(), ImmutableMap.ofEntries(Map.entry("k1", "v1")), Collections.singletonMap("k1", "v1"), Map.of("k1", "v1"));
                                    }
                                }
                                """,
                        """
                                import com.google.common.collect.ImmutableMap;
                                import com.google.common.collect.ImmutableSet;
                                
                                import java.util.Map;
                                
                                class Test {
                                    ImmutableSet<Map<String, String>> test() {
                                        return ImmutableSet.of(ImmutableMap.of("k1", "v1"), ImmutableMap.of("k1", "v1"), ImmutableMap.of("k1", "v1"), ImmutableMap.of("k1", "v1"));
                                    }
                                }
                                """
                ));
    }

    @Test
    void testImmutableMapOf2Recipe() {
        Recipe recipe = RewriteUtils.loadRecipe("ImmutableMapRulesRecipes.ImmutableMapOf2Recipe");
        rewriteRun(
                spec -> spec.recipe(recipe)
                        .expectedCyclesThatMakeChanges(1).cycles(1)
                        .parser(JavaParser.fromJavaVersion().classpath(JavaParser.runtimeClasspath())),
                java(
                        """
                                import com.google.common.collect.ImmutableMap;
                                import com.google.common.collect.ImmutableSet;
                                
                                import java.util.Map;
                                
                                class Test {
                                    ImmutableSet<Map<String, String>> test() {
                                        return ImmutableSet.of(ImmutableMap.ofEntries(Map.entry("k1", "v1"), Map.entry("k2", "v2")), Map.of("k1", "v1", "k2", "v2"));
                                    }
                                }
                                """,
                        """
                                import com.google.common.collect.ImmutableMap;
                                import com.google.common.collect.ImmutableSet;
                                
                                import java.util.Map;
                                
                                class Test {
                                    ImmutableSet<Map<String, String>> test() {
                                        return ImmutableSet.of(ImmutableMap.of("k1", "v1", "k2", "v2"), ImmutableMap.of("k1", "v1", "k2", "v2"));
                                    }
                                }
                                """
                ));
    }

    @Test
    void testImmutableMapOf3Recipe() {
        Recipe recipe = RewriteUtils.loadRecipe("ImmutableMapRulesRecipes.ImmutableMapOf3Recipe");
        rewriteRun(
                spec -> spec.recipe(recipe)
                        .expectedCyclesThatMakeChanges(1).cycles(1)
                        .parser(JavaParser.fromJavaVersion().classpath(JavaParser.runtimeClasspath())),
                java(
                        """
                                import com.google.common.collect.ImmutableMap;
                                import com.google.common.collect.ImmutableSet;
                                
                                import java.util.Map;
                                
                                class Test {
                                    ImmutableSet<Map<String, String>> test() {
                                        return ImmutableSet.of(ImmutableMap.ofEntries(Map.entry("k1", "v1"), Map.entry("k2", "v2"), Map.entry("k3", "v3")), Map.of("k1", "v1", "k2", "v2", "k3", "v3"));
                                    }
                                }
                                """,
                        """
                                import com.google.common.collect.ImmutableMap;
                                import com.google.common.collect.ImmutableSet;
                                
                                import java.util.Map;
                                
                                class Test {
                                    ImmutableSet<Map<String, String>> test() {
                                        return ImmutableSet.of(ImmutableMap.of("k1", "v1", "k2", "v2", "k3", "v3"), ImmutableMap.of("k1", "v1", "k2", "v2", "k3", "v3"));
                                    }
                                }
                                """
                ));
    }

    @Test
    void testImmutableMapOf4Recipe() {
        Recipe recipe = RewriteUtils.loadRecipe("ImmutableMapRulesRecipes.ImmutableMapOf4Recipe");
        rewriteRun(
                spec -> spec.recipe(recipe)
                        .expectedCyclesThatMakeChanges(1).cycles(1)
                        .parser(JavaParser.fromJavaVersion().classpath(JavaParser.runtimeClasspath())),
                java(
                        """
                                import com.google.common.collect.ImmutableMap;
                                import com.google.common.collect.ImmutableSet;
                                
                                import java.util.Map;
                                
                                class Test {
                                    ImmutableSet<Map<String, String>> test() {
                                        return ImmutableSet.of(ImmutableMap.ofEntries(Map.entry("k1", "v1"), Map.entry("k2", "v2"), Map.entry("k3", "v3"), Map.entry("k4", "v4")), Map.of("k1", "v1", "k2", "v2", "k3", "v3", "k4", "v4"));
                                    }
                                }
                                """,
                        """
                                import com.google.common.collect.ImmutableMap;
                                import com.google.common.collect.ImmutableSet;
                                
                                import java.util.Map;
                                
                                class Test {
                                    ImmutableSet<Map<String, String>> test() {
                                        return ImmutableSet.of(ImmutableMap.of("k1", "v1", "k2", "v2", "k3", "v3", "k4", "v4"), ImmutableMap.of("k1", "v1", "k2", "v2", "k3", "v3", "k4", "v4"));
                                    }
                                }
                                """
                ));
    }

    @Test
    void testImmutableMapOf5Recipe() {
        Recipe recipe = RewriteUtils.loadRecipe("ImmutableMapRulesRecipes.ImmutableMapOf5Recipe");
        rewriteRun(
                spec -> spec.recipe(recipe)
                        .expectedCyclesThatMakeChanges(1).cycles(1)
                        .parser(JavaParser.fromJavaVersion().classpath(JavaParser.runtimeClasspath())),
                java(
                        """
                                import com.google.common.collect.ImmutableMap;
                                import com.google.common.collect.ImmutableSet;
                                
                                import java.util.Map;
                                
                                class Test {
                                    ImmutableSet<Map<String, String>> test() {
                                        return ImmutableSet.of(ImmutableMap.ofEntries(Map.entry("k1", "v1"), Map.entry("k2", "v2"), Map.entry("k3", "v3"), Map.entry("k4", "v4"), Map.entry("k5", "v5")), Map.of("k1", "v1", "k2", "v2", "k3", "v3", "k4", "v4", "k5", "v5"));
                                    }
                                }
                                """,
                        """
                                import com.google.common.collect.ImmutableMap;
                                import com.google.common.collect.ImmutableSet;
                                
                                import java.util.Map;
                                
                                class Test {
                                    ImmutableSet<Map<String, String>> test() {
                                        return ImmutableSet.of(ImmutableMap.of("k1", "v1", "k2", "v2", "k3", "v3", "k4", "v4", "k5", "v5"), ImmutableMap.of("k1", "v1", "k2", "v2", "k3", "v3", "k4", "v4", "k5", "v5"));
                                    }
                                }
                                """
                ));
    }

    @Test
    void testImmutableMapOfEntriesRecipe() {
        Recipe recipe = RewriteUtils.loadRecipe("ImmutableMapRulesRecipes.ImmutableMapOfEntriesRecipe");
        rewriteRun(
                spec -> spec.recipe(recipe)
                        .expectedCyclesThatMakeChanges(1).cycles(1)
                        .parser(JavaParser.fromJavaVersion().classpath(JavaParser.runtimeClasspath())),
                java(
                        """
                                import com.google.common.collect.ImmutableSet;
                                
                                import java.util.Map;
                                
                                class Test {
                                    ImmutableSet<Map<String, Integer>> test() {
                                        return ImmutableSet.of(Map.ofEntries(), Map.ofEntries(Map.entry("foo", 1)), Map.ofEntries(Map.entry("bar", 2), Map.entry("baz", 3)));
                                    }
                                }
                                """,
                        """
                                import com.google.common.collect.ImmutableMap;
                                import com.google.common.collect.ImmutableSet;
                                
                                import java.util.Map;
                                
                                class Test {
                                    ImmutableSet<Map<String, Integer>> test() {
                                        return ImmutableSet.of(ImmutableMap.ofEntries(), ImmutableMap.ofEntries(Map.entry("foo", 1)), ImmutableMap.ofEntries(Map.entry("bar", 2), Map.entry("baz", 3)));
                                    }
                                }
                                """
                ));
    }

    @Test
    void testIndexIterableToImmutableMapRecipe() {
        Recipe recipe = RewriteUtils.loadRecipe("ImmutableMapRulesRecipes.IndexIterableToImmutableMapRecipe");
        rewriteRun(
                spec -> spec.recipe(recipe)
                        .expectedCyclesThatMakeChanges(1).cycles(1)
                        .parser(JavaParser.fromJavaVersion().classpath(JavaParser.runtimeClasspath())),
                java(
                        """
                                import com.google.common.collect.ImmutableList;
                                import com.google.common.collect.ImmutableMap;
                                import com.google.common.collect.ImmutableSet;
                                import com.google.common.collect.Streams;
                                
                                import java.util.function.Function;
                                
                                class Test {
                                    ImmutableSet<ImmutableMap<Integer, Integer>> test() {
                                        return ImmutableSet.of(ImmutableList.of(1).stream().collect(ImmutableMap.toImmutableMap(n -> n * 2, Function.identity())), ImmutableList.of(2).stream().collect(ImmutableMap.toImmutableMap(n -> n * 2, v -> v)), ImmutableList.of(3).stream().collect(ImmutableMap.toImmutableMap(n -> n * 2, v -> 0)), Streams.stream(ImmutableList.of(4)::iterator).collect(ImmutableMap.toImmutableMap(Integer::valueOf, Function.identity())), Streams.stream(ImmutableList.of(5)::iterator).collect(ImmutableMap.toImmutableMap(Integer::valueOf, v -> v)), Streams.stream(ImmutableList.of(6)::iterator).collect(ImmutableMap.toImmutableMap(Integer::valueOf, v -> 0)), Streams.stream(ImmutableList.of(7).iterator()).collect(ImmutableMap.toImmutableMap(n -> n.intValue(), Function.identity())), Streams.stream(ImmutableList.of(8).iterator()).collect(ImmutableMap.toImmutableMap(n -> n.intValue(), v -> v)), Streams.stream(ImmutableList.of(9).iterator()).collect(ImmutableMap.toImmutableMap(n -> n.intValue(), v -> 0)));
                                    }
                                }
                                """,
                        """
                                import com.google.common.collect.*;
                                
                                import java.util.function.Function;
                                
                                class Test {
                                    ImmutableSet<ImmutableMap<Integer, Integer>> test() {
                                        return ImmutableSet.of(Maps.uniqueIndex(ImmutableList.of(1), n -> n * 2), Maps.uniqueIndex(ImmutableList.of(2), n -> n * 2), ImmutableList.of(3).stream().collect(ImmutableMap.toImmutableMap(n -> n * 2, v -> 0)), Maps.uniqueIndex(ImmutableList.of(4)::iterator, Integer::valueOf), Maps.uniqueIndex(ImmutableList.of(5)::iterator, Integer::valueOf), Streams.stream(ImmutableList.of(6)::iterator).collect(ImmutableMap.toImmutableMap(Integer::valueOf, v -> 0)), Maps.uniqueIndex(ImmutableList.of(7).iterator(), n -> n.intValue()), Maps.uniqueIndex(ImmutableList.of(8).iterator(), n -> n.intValue()), Streams.stream(ImmutableList.of(9).iterator()).collect(ImmutableMap.toImmutableMap(n -> n.intValue(), v -> 0)));
                                    }
                                }
                                """
                ));
    }

    @Test
    void testIterableToImmutableMapRecipe() {
        Recipe recipe = RewriteUtils.loadRecipe("ImmutableMapRulesRecipes.IterableToImmutableMapRecipe");
        rewriteRun(
                spec -> spec.recipe(recipe)
                        .expectedCyclesThatMakeChanges(1).cycles(1)
                        .parser(JavaParser.fromJavaVersion().classpath(JavaParser.runtimeClasspath())),
                java(
                        """
                                import com.google.common.collect.*;
                                
                                import java.util.function.Function;
                                
                                class Test {
                                    ImmutableSet<ImmutableMap<Integer, Integer>> test() {
                                        return ImmutableSet.of(ImmutableList.of(1).stream().collect(ImmutableMap.toImmutableMap(Function.identity(), n -> n * 2)), ImmutableList.of(2).stream().collect(ImmutableMap.toImmutableMap(k -> k, n -> n * 2)), ImmutableList.of(3).stream().collect(ImmutableMap.toImmutableMap(k -> 0, n -> n * 2)), Streams.stream(ImmutableList.of(4)::iterator).collect(ImmutableMap.toImmutableMap(Function.identity(), Integer::valueOf)), Streams.stream(ImmutableList.of(5)::iterator).collect(ImmutableMap.toImmutableMap(k -> k, Integer::valueOf)), Streams.stream(ImmutableList.of(6)::iterator).collect(ImmutableMap.toImmutableMap(k -> 0, Integer::valueOf)), Streams.stream(ImmutableList.of(7).iterator()).collect(ImmutableMap.toImmutableMap(Function.identity(), n -> n.intValue())), Streams.stream(ImmutableList.of(8).iterator()).collect(ImmutableMap.toImmutableMap(k -> k, n -> n.intValue())), Streams.stream(ImmutableList.of(9).iterator()).collect(ImmutableMap.toImmutableMap(k -> 0, n -> n.intValue())), ImmutableMap.copyOf(Maps.asMap(ImmutableSet.of(10), Integer::valueOf)));
                                    }
                                }
                                """,
                        """
                                import com.google.common.collect.*;
                                
                                import java.util.function.Function;
                                
                                class Test {
                                    ImmutableSet<ImmutableMap<Integer, Integer>> test() {
                                        return ImmutableSet.of(Maps.toMap(ImmutableList.of(1), n -> n * 2), Maps.toMap(ImmutableList.of(2), n -> n * 2), ImmutableList.of(3).stream().collect(ImmutableMap.toImmutableMap(k -> 0, n -> n * 2)), Maps.toMap(ImmutableList.of(4)::iterator, Integer::valueOf), Maps.toMap(ImmutableList.of(5)::iterator, Integer::valueOf), Streams.stream(ImmutableList.of(6)::iterator).collect(ImmutableMap.toImmutableMap(k -> 0, Integer::valueOf)), Maps.toMap(ImmutableList.of(7).iterator(), n -> n.intValue()), Maps.toMap(ImmutableList.of(8).iterator(), n -> n.intValue()), Streams.stream(ImmutableList.of(9).iterator()).collect(ImmutableMap.toImmutableMap(k -> 0, n -> n.intValue())), Maps.toMap(ImmutableSet.of(10), Integer::valueOf));
                                    }
                                }
                                """
                ));
    }

    @Test
    void testStreamOfMapEntriesToImmutableMapRecipe() {
        Recipe recipe = RewriteUtils.loadRecipe("ImmutableMapRulesRecipes.StreamOfMapEntriesToImmutableMapRecipe");
        rewriteRun(
                spec -> spec.recipe(recipe)
                        .expectedCyclesThatMakeChanges(1).cycles(1)
                        .parser(JavaParser.fromJavaVersion().classpath(JavaParser.runtimeClasspath())),
                java(
                        """
                                import com.google.common.collect.ImmutableMap;
                                
                                import java.util.Map;
                                import java.util.function.Function;
                                import java.util.stream.Stream;
                                
                                class Test {
                                    ImmutableMap<Integer, String> test() {
                                        return Stream.of(1, 2, 3).map(n -> Map.entry(n, n.toString())).collect(ImmutableMap.toImmutableMap(Map.Entry::getKey, Map.Entry::getValue));
                                    }
                                }
                                """,
                        """
                                import com.google.common.collect.ImmutableMap;
                                
                                import java.util.stream.Stream;
                                
                                class Test {
                                    ImmutableMap<Integer, String> test() {
                                        return Stream.of(1, 2, 3).collect(ImmutableMap.toImmutableMap(n -> n, n -> n.toString()));
                                    }
                                }
                                """
                ));
    }

    @Test
    void testTransformMapValuesToImmutableMapRecipe() {
        Recipe recipe = RewriteUtils.loadRecipe("ImmutableMapRulesRecipes.TransformMapValuesToImmutableMapRecipe");
        rewriteRun(
                spec -> spec.recipe(recipe)
                        .expectedCyclesThatMakeChanges(1).cycles(1)
                        .parser(JavaParser.fromJavaVersion().classpath(JavaParser.runtimeClasspath())),
                java(
                        """
                                import com.google.common.collect.ImmutableMap;
                                import com.google.common.collect.ImmutableSet;
                                import com.google.common.collect.Maps;
                                
                                import java.util.Map;
                                import java.util.function.Function;
                                
                                class Test {
                                    ImmutableSet<ImmutableMap<String, Integer>> test() {
                                        return ImmutableSet.of(ImmutableMap.of("foo", 1L).entrySet().stream().collect(ImmutableMap.toImmutableMap(Map.Entry::getKey, e -> Math.toIntExact(e.getValue()))), Maps.toMap(ImmutableMap.of("bar", 2L).keySet(), k -> Math.toIntExact(ImmutableMap.of("bar", 2L).get(k))));
                                    }
                                }
                                """,
                        """
                                import com.google.common.collect.ImmutableMap;
                                import com.google.common.collect.ImmutableSet;
                                import com.google.common.collect.Maps;
                                
                                class Test {
                                    ImmutableSet<ImmutableMap<String, Integer>> test() {
                                        return ImmutableSet.of(ImmutableMap.copyOf(Maps.transformValues(ImmutableMap.of("foo", 1L), v -> Math.toIntExact(v))), ImmutableMap.copyOf(Maps.transformValues(ImmutableMap.of("bar", 2L), v -> Math.toIntExact(v))));
                                    }
                                }
                                """
                ));
    }
}
