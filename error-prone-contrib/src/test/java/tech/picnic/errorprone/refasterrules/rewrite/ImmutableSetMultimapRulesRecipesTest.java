package tech.picnic.errorprone.refasterrules.rewrite;

import org.junit.jupiter.api.Test;
import org.openrewrite.Recipe;
import org.openrewrite.java.JavaParser;
import org.openrewrite.test.RewriteTest;

import static org.openrewrite.java.Assertions.java;

final class ImmutableSetMultimapRulesRecipesTest implements RewriteTest {

    @Test
    void testEmptyImmutableSetMultimapRecipe() {
        Recipe recipe = RewriteUtils.loadRecipe("ImmutableSetMultimapRulesRecipes.EmptyImmutableSetMultimapRecipe");
        rewriteRun(
                spec -> spec.recipe(recipe)
                        .expectedCyclesThatMakeChanges(1).cycles(1)
                        .parser(JavaParser.fromJavaVersion().classpath(JavaParser.runtimeClasspath())),
                java(
                        """
                                import com.google.common.collect.ImmutableSetMultimap;
                                
                                class Test {
                                    ImmutableSetMultimap<String, Integer> test() {
                                        return ImmutableSetMultimap.<String, Integer>builder().build();
                                    }
                                }
                                """,
                        """
                                import com.google.common.collect.ImmutableSetMultimap;
                                
                                class Test {
                                    ImmutableSetMultimap<String, Integer> test() {
                                        return ImmutableSetMultimap.of();
                                    }
                                }
                                """
                ));
    }

    @Test
    void testEntryToImmutableSetMultimapRecipe() {
        Recipe recipe = RewriteUtils.loadRecipe("ImmutableSetMultimapRulesRecipes.EntryToImmutableSetMultimapRecipe");
        rewriteRun(
                spec -> spec.recipe(recipe)
                        .expectedCyclesThatMakeChanges(1).cycles(1)
                        .parser(JavaParser.fromJavaVersion().classpath(JavaParser.runtimeClasspath())),
                java(
                        """
                                import com.google.common.collect.ImmutableSet;
                                import com.google.common.collect.ImmutableSetMultimap;
                                
                                import java.util.Map;
                                import java.util.stream.Stream;
                                
                                class Test {
                                    ImmutableSet<ImmutableSetMultimap<String, Integer>> test() {
                                        return ImmutableSet.of(ImmutableSetMultimap.<String, Integer>builder().put(Map.entry("foo", 1)).build(), Stream.of(Map.entry("foo", 1)).collect(ImmutableSetMultimap.toImmutableSetMultimap(Map.Entry::getKey, Map.Entry::getValue)));
                                    }
                                }
                                """,
                        """
                                import com.google.common.collect.ImmutableSet;
                                import com.google.common.collect.ImmutableSetMultimap;
                                
                                import java.util.Map;
                                
                                class Test {
                                    ImmutableSet<ImmutableSetMultimap<String, Integer>> test() {
                                        return ImmutableSet.of(ImmutableSetMultimap.of(Map.entry("foo", 1).getKey(), Map.entry("foo", 1).getValue()), ImmutableSetMultimap.of(Map.entry("foo", 1).getKey(), Map.entry("foo", 1).getValue()));
                                    }
                                }
                                """
                ));
    }

    @Test
    void testImmutableSetMultimapBuilderRecipe() {
        Recipe recipe = RewriteUtils.loadRecipe("ImmutableSetMultimapRulesRecipes.ImmutableSetMultimapBuilderRecipe");
        rewriteRun(
                spec -> spec.recipe(recipe)
                        .expectedCyclesThatMakeChanges(1).cycles(1)
                        .parser(JavaParser.fromJavaVersion().classpath(JavaParser.runtimeClasspath())),
                java(
                        """
                                import com.google.common.collect.ImmutableSetMultimap;
                                
                                class Test {
                                    ImmutableSetMultimap.Builder<String, Integer> test() {
                                        return new ImmutableSetMultimap.Builder<>();
                                    }
                                }
                                """,
                        """
                                import com.google.common.collect.ImmutableSetMultimap;
                                
                                class Test {
                                    ImmutableSetMultimap.Builder<String, Integer> test() {
                                        return ImmutableSetMultimap.builder();
                                    }
                                }
                                """
                ));
    }

    @Test
    void testIterableToImmutableSetMultimapRecipe() {
        Recipe recipe = RewriteUtils.loadRecipe("ImmutableSetMultimapRulesRecipes.IterableToImmutableSetMultimapRecipe");
        rewriteRun(
                spec -> spec.recipe(recipe)
                        .expectedCyclesThatMakeChanges(1).cycles(1)
                        .parser(JavaParser.fromJavaVersion().classpath(JavaParser.runtimeClasspath())),
                java(
                        """
                                import com.google.common.collect.ImmutableSet;
                                import com.google.common.collect.ImmutableSetMultimap;
                                import com.google.common.collect.Iterables;
                                import com.google.common.collect.Streams;
                                
                                import java.util.Map;
                                
                                class Test {
                                    ImmutableSet<ImmutableSetMultimap<String, Integer>> test() {
                                        return ImmutableSet.of(ImmutableSetMultimap.copyOf(ImmutableSetMultimap.of("foo", 1).entries()), ImmutableSetMultimap.<String, Integer>builder().putAll(ImmutableSetMultimap.of("foo", 1)).build(), ImmutableSetMultimap.<String, Integer>builder().putAll(ImmutableSetMultimap.of("foo", 1).entries()).build(), ImmutableSetMultimap.of("foo", 1).entries().stream().collect(ImmutableSetMultimap.toImmutableSetMultimap(Map.Entry::getKey, Map.Entry::getValue)), Streams.stream(Iterables.cycle(Map.entry("foo", 1))).collect(ImmutableSetMultimap.toImmutableSetMultimap(Map.Entry::getKey, Map.Entry::getValue)));
                                    }
                                }
                                """,
                        """
                                import com.google.common.collect.ImmutableSet;
                                import com.google.common.collect.ImmutableSetMultimap;
                                import com.google.common.collect.Iterables;
                                
                                import java.util.Map;
                                
                                class Test {
                                    ImmutableSet<ImmutableSetMultimap<String, Integer>> test() {
                                        return ImmutableSet.of(ImmutableSetMultimap.copyOf(ImmutableSetMultimap.of("foo", 1)), ImmutableSetMultimap.copyOf(ImmutableSetMultimap.of("foo", 1)), ImmutableSetMultimap.copyOf(ImmutableSetMultimap.of("foo", 1).entries()), ImmutableSetMultimap.copyOf(ImmutableSetMultimap.of("foo", 1).entries()), ImmutableSetMultimap.copyOf(Iterables.cycle(Map.entry("foo", 1))));
                                    }
                                }
                                """
                ));
    }

    @Test
    void testPairToImmutableSetMultimapRecipe() {
        Recipe recipe = RewriteUtils.loadRecipe("ImmutableSetMultimapRulesRecipes.PairToImmutableSetMultimapRecipe");
        rewriteRun(
                spec -> spec.recipe(recipe)
                        .expectedCyclesThatMakeChanges(1).cycles(1)
                        .parser(JavaParser.fromJavaVersion().classpath(JavaParser.runtimeClasspath())),
                java(
                        """
                                import com.google.common.collect.ImmutableSetMultimap;
                                
                                class Test {
                                    ImmutableSetMultimap<String, Integer> test() {
                                        return ImmutableSetMultimap.<String, Integer>builder().put("foo", 1).build();
                                    }
                                }
                                """,
                        """
                                import com.google.common.collect.ImmutableSetMultimap;
                                
                                class Test {
                                    ImmutableSetMultimap<String, Integer> test() {
                                        return ImmutableSetMultimap.of("foo", 1);
                                    }
                                }
                                """
                ));
    }

    @Test
    void testStreamOfMapEntriesToImmutableSetMultimapRecipe() {
        Recipe recipe = RewriteUtils.loadRecipe("ImmutableSetMultimapRulesRecipes.StreamOfMapEntriesToImmutableSetMultimapRecipe");
        rewriteRun(
                spec -> spec.recipe(recipe)
                        .expectedCyclesThatMakeChanges(1).cycles(1)
                        .parser(JavaParser.fromJavaVersion().classpath(JavaParser.runtimeClasspath())),
                java(
                        """
                                import com.google.common.collect.ImmutableSetMultimap;
                                
                                import java.util.Map;
                                import java.util.stream.Stream;
                                
                                class Test {
                                    ImmutableSetMultimap<Integer, String> test() {
                                        return Stream.of(1, 2, 3).map(n -> Map.entry(n, n.toString())).collect(ImmutableSetMultimap.toImmutableSetMultimap(Map.Entry::getKey, Map.Entry::getValue));
                                    }
                                }
                                """,
                        """
                                import com.google.common.collect.ImmutableSetMultimap;
                                
                                import java.util.stream.Stream;
                                
                                class Test {
                                    ImmutableSetMultimap<Integer, String> test() {
                                        return Stream.of(1, 2, 3).collect(ImmutableSetMultimap.toImmutableSetMultimap(n -> n, n -> n.toString()));
                                    }
                                }
                                """
                ));
    }

    @Test
    void testTransformMultimapValuesToImmutableSetMultimapRecipe() {
        Recipe recipe = RewriteUtils.loadRecipe("ImmutableSetMultimapRulesRecipes.TransformMultimapValuesToImmutableSetMultimapRecipe");
        rewriteRun(
                spec -> spec.recipe(recipe)
                        .expectedCyclesThatMakeChanges(1).cycles(1)
                        .parser(JavaParser.fromJavaVersion().classpath(JavaParser.runtimeClasspath())),
                java(
                        """
                                import com.google.common.collect.ImmutableSetMultimap;
                                
                                import java.util.Map;
                                
                                class Test {
                                    ImmutableSetMultimap<String, Integer> test() {
                                        return ImmutableSetMultimap.of("foo", 1L).entries().stream().collect(ImmutableSetMultimap.toImmutableSetMultimap(Map.Entry::getKey, e -> Math.toIntExact(e.getValue())));
                                    }
                                }
                                """,
                        """
                                import com.google.common.collect.ImmutableSetMultimap;
                                import com.google.common.collect.Multimaps;
                                
                                class Test {
                                    ImmutableSetMultimap<String, Integer> test() {
                                        return ImmutableSetMultimap.copyOf(Multimaps.transformValues(ImmutableSetMultimap.of("foo", 1L), e -> Math.toIntExact(e)));
                                    }
                                }
                                """
                ));
    }

    @Test
    void testTransformMultimapValuesToImmutableSetMultimap2Recipe() {
        Recipe recipe = RewriteUtils.loadRecipe("ImmutableSetMultimapRulesRecipes.TransformMultimapValuesToImmutableSetMultimap2Recipe");
        rewriteRun(
                spec -> spec.recipe(recipe)
                        .expectedCyclesThatMakeChanges(1).cycles(1)
                        .parser(JavaParser.fromJavaVersion().classpath(JavaParser.runtimeClasspath())),
                java(
                        """
                                import com.google.common.collect.*;
                                
                                import java.util.Map;
                                
                                class Test {
                                    ImmutableSet<ImmutableSetMultimap<String, Integer>>test() {
                                        return ImmutableSet.of(ImmutableSetMultimap.of("foo", 1L).asMap().entrySet().stream().collect(ImmutableSetMultimap.flatteningToImmutableSetMultimap(Map.Entry::getKey, e -> e.getValue().stream().map(Math::toIntExact))), Multimaps.asMap((Multimap<String, Long>) ImmutableSetMultimap.of("bar", 2L)).entrySet().stream().collect(ImmutableSetMultimap.flatteningToImmutableSetMultimap(Map.Entry::getKey, e -> e.getValue().stream().map(n -> Math.toIntExact(n)))), Multimaps.asMap(ImmutableListMultimap.of("baz", 3L)).entrySet().stream().collect(ImmutableSetMultimap.flatteningToImmutableSetMultimap(Map.Entry::getKey, e -> e.getValue().stream().map(Math::toIntExact))), Multimaps.asMap(ImmutableSetMultimap.of("qux", 4L)).entrySet().stream().collect(ImmutableSetMultimap.flatteningToImmutableSetMultimap(Map.Entry::getKey, e -> e.getValue().stream().map(n -> Math.toIntExact(n)))), Multimaps.asMap(TreeMultimap.<String, Long>create()).entrySet().stream().collect(ImmutableSetMultimap.flatteningToImmutableSetMultimap(Map.Entry::getKey, e -> e.getValue().stream().map(Math::toIntExact))));
                                    }
                                }
                                """,
                        """
                                import com.google.common.collect.*;
                                
                                class Test {
                                    ImmutableSet<ImmutableSetMultimap<String, Integer>>test() {
                                        return ImmutableSet.of(ImmutableSetMultimap.copyOf(Multimaps.transformValues(ImmutableSetMultimap.of("foo", 1L), Math::toIntExact)), ImmutableSetMultimap.copyOf(Multimaps.transformValues((Multimap<String, Long>) ImmutableSetMultimap.of("bar", 2L), n -> Math.toIntExact(n))), ImmutableSetMultimap.copyOf(Multimaps.transformValues(ImmutableListMultimap.of("baz", 3L), Math::toIntExact)), ImmutableSetMultimap.copyOf(Multimaps.transformValues(ImmutableSetMultimap.of("qux", 4L), n -> Math.toIntExact(n))), ImmutableSetMultimap.copyOf(Multimaps.transformValues(TreeMultimap.<String, Long>create(), Math::toIntExact)));
                                    }
                                }
                                """
                ));
    }
}
