package tech.picnic.errorprone.refasterrules.rewrite;

import org.junit.jupiter.api.Test;
import org.openrewrite.Recipe;
import org.openrewrite.java.JavaParser;
import org.openrewrite.test.RewriteTest;

import static org.openrewrite.java.Assertions.java;

final class ImmutableSortedMapRulesRecipesTest implements RewriteTest {

    @Test
    void testEmptyImmutableSortedMapRecipe() {
        Recipe recipe = RewriteUtils.loadRecipe("ImmutableSortedMapRulesRecipes.EmptyImmutableSortedMapRecipe");
        rewriteRun(
                spec -> spec.recipe(recipe)
                        .expectedCyclesThatMakeChanges(1).cycles(1)
                        .parser(JavaParser.fromJavaVersion().classpath(JavaParser.runtimeClasspath())),
                java(
                        """
                                import com.google.common.collect.ImmutableSortedMap;
                                
                                class Test {
                                    ImmutableSortedMap<String, Integer> test() {
                                        return ImmutableSortedMap.<String, Integer>naturalOrder().buildOrThrow();
                                    }
                                }
                                """,
                        """
                                import com.google.common.collect.ImmutableSortedMap;
                                
                                class Test {
                                    ImmutableSortedMap<String, Integer> test() {
                                        return ImmutableSortedMap.of();
                                    }
                                }
                                """
                ));
    }

    @Test
    void testEntryToImmutableSortedMapRecipe() {
        Recipe recipe = RewriteUtils.loadRecipe("ImmutableSortedMapRulesRecipes.EntryToImmutableSortedMapRecipe");
        rewriteRun(
                spec -> spec.recipe(recipe)
                        .expectedCyclesThatMakeChanges(1).cycles(1)
                        .parser(JavaParser.fromJavaVersion().classpath(JavaParser.runtimeClasspath())),
                java(
                        """
                                import com.google.common.collect.ImmutableSet;
                                import com.google.common.collect.ImmutableSortedMap;
                                
                                import java.util.Comparator;
                                import java.util.Map;
                                import java.util.stream.Stream;
                                
                                class Test {
                                    ImmutableSet<ImmutableSortedMap<String, Integer>> test() {
                                        return ImmutableSet.of(ImmutableSortedMap.<String, Integer>naturalOrder().put(Map.entry("foo", 1)).buildOrThrow(), Stream.of(Map.entry("foo", 1)).collect(ImmutableSortedMap.toImmutableSortedMap(Comparator.naturalOrder(), Map.Entry::getKey, Map.Entry::getValue)));
                                    }
                                }
                                """,
                        """
                                import com.google.common.collect.ImmutableSet;
                                import com.google.common.collect.ImmutableSortedMap;
                                
                                import java.util.Map;
                                
                                class Test {
                                    ImmutableSet<ImmutableSortedMap<String, Integer>> test() {
                                        return ImmutableSet.of(ImmutableSortedMap.of(Map.entry("foo", 1).getKey(), Map.entry("foo", 1).getValue()), ImmutableSortedMap.of(Map.entry("foo", 1).getKey(), Map.entry("foo", 1).getValue()));
                                    }
                                }
                                """
                ));
    }

    @Test
    void testImmutableSortedMapBuilderRecipe() {
        Recipe recipe = RewriteUtils.loadRecipe("ImmutableSortedMapRulesRecipes.ImmutableSortedMapBuilderRecipe");
        rewriteRun(
                spec -> spec.recipe(recipe)
                        .expectedCyclesThatMakeChanges(1).cycles(1)
                        .parser(JavaParser.fromJavaVersion().classpath(JavaParser.runtimeClasspath())),
                java(
                        """
                                import com.google.common.collect.ImmutableSortedMap;
                                
                                import java.util.Comparator;
                                
                                class Test {
                                    ImmutableSortedMap.Builder<String, Integer> test() {
                                        return new ImmutableSortedMap.Builder<>(Comparator.comparingInt(String::length));
                                    }
                                }
                                """,
                        """
                                import com.google.common.collect.ImmutableSortedMap;
                                
                                import java.util.Comparator;
                                
                                class Test {
                                    ImmutableSortedMap.Builder<String, Integer> test() {
                                        return ImmutableSortedMap.orderedBy(Comparator.comparingInt(String::length));
                                    }
                                }
                                """
                ));
    }

    @Test
    void testImmutableSortedMapNaturalOrderBuilderRecipe() {
        Recipe recipe = RewriteUtils.loadRecipe("ImmutableSortedMapRulesRecipes.ImmutableSortedMapNaturalOrderBuilderRecipe");
        rewriteRun(
                spec -> spec.recipe(recipe)
                        .expectedCyclesThatMakeChanges(1).cycles(1)
                        .parser(JavaParser.fromJavaVersion().classpath(JavaParser.runtimeClasspath())),
                java(
                        """
                                import com.google.common.collect.ImmutableSortedMap;
                                
                                import java.util.Comparator;
                                
                                class Test {
                                    ImmutableSortedMap.Builder<String, Integer> test() {
                                        return ImmutableSortedMap.orderedBy(Comparator.<String>naturalOrder());
                                    }
                                }
                                """,
                        """
                                import com.google.common.collect.ImmutableSortedMap;
                                
                                class Test {
                                    ImmutableSortedMap.Builder<String, Integer> test() {
                                        return ImmutableSortedMap.naturalOrder();
                                    }
                                }
                                """
                ));
    }

    @Test
    void testImmutableSortedMapReverseOrderBuilderRecipe() {
        Recipe recipe = RewriteUtils.loadRecipe("ImmutableSortedMapRulesRecipes.ImmutableSortedMapReverseOrderBuilderRecipe");
        rewriteRun(
                spec -> spec.recipe(recipe)
                        .expectedCyclesThatMakeChanges(1).cycles(1)
                        .parser(JavaParser.fromJavaVersion().classpath(JavaParser.runtimeClasspath())),
                java(
                        """
                                import com.google.common.collect.ImmutableSortedMap;
                                
                                import java.util.Comparator;
                                
                                class Test {
                                    ImmutableSortedMap.Builder<String, Integer> test() {
                                        return ImmutableSortedMap.orderedBy(Comparator.<String>reverseOrder());
                                    }
                                }
                                """,
                        """
                                import com.google.common.collect.ImmutableSortedMap;
                                
                                class Test {
                                    ImmutableSortedMap.Builder<String, Integer> test() {
                                        return ImmutableSortedMap.reverseOrder();
                                    }
                                }
                                """
                ));
    }

    @Test
    void testIterableToImmutableSortedMapRecipe() {
        Recipe recipe = RewriteUtils.loadRecipe("ImmutableSortedMapRulesRecipes.IterableToImmutableSortedMapRecipe");
        rewriteRun(
                spec -> spec.recipe(recipe)
                        .expectedCyclesThatMakeChanges(1).cycles(1)
                        .parser(JavaParser.fromJavaVersion().classpath(JavaParser.runtimeClasspath())),
                java(
                        """
                                import com.google.common.collect.ImmutableSet;
                                import com.google.common.collect.ImmutableSortedMap;
                                import com.google.common.collect.Iterables;
                                import com.google.common.collect.Streams;
                                
                                import java.util.Comparator;
                                import java.util.Map;
                                
                                class Test {
                                    ImmutableSet<ImmutableSortedMap<String, Integer>> test() {
                                        return ImmutableSet.of(ImmutableSortedMap.copyOf(ImmutableSortedMap.of("foo", 1), Comparator.naturalOrder()), ImmutableSortedMap.copyOf(ImmutableSortedMap.of("foo", 1).entrySet()), ImmutableSortedMap.<String, Integer>naturalOrder().putAll(ImmutableSortedMap.of("foo", 1)).buildOrThrow(), ImmutableSortedMap.<String, Integer>naturalOrder().putAll(ImmutableSortedMap.of("foo", 1).entrySet()).buildOrThrow(), ImmutableSortedMap.of("foo", 1).entrySet().stream().collect(ImmutableSortedMap.toImmutableSortedMap(Comparator.naturalOrder(), Map.Entry::getKey, Map.Entry::getValue)), Streams.stream(Iterables.cycle(Map.entry("foo", 1))).collect(ImmutableSortedMap.toImmutableSortedMap(Comparator.naturalOrder(), Map.Entry::getKey, Map.Entry::getValue)));
                                    }
                                }
                                """,
                        """
                                import com.google.common.collect.ImmutableSet;
                                import com.google.common.collect.ImmutableSortedMap;
                                import com.google.common.collect.Iterables;
                                
                                import java.util.Map;
                                
                                class Test {
                                    ImmutableSet<ImmutableSortedMap<String, Integer>> test() {
                                        return ImmutableSet.of(ImmutableSortedMap.copyOf(ImmutableSortedMap.of("foo", 1)), ImmutableSortedMap.copyOf(ImmutableSortedMap.of("foo", 1)), ImmutableSortedMap.copyOf(ImmutableSortedMap.of("foo", 1)), ImmutableSortedMap.copyOf(ImmutableSortedMap.of("foo", 1).entrySet()), ImmutableSortedMap.copyOf(ImmutableSortedMap.of("foo", 1).entrySet()), ImmutableSortedMap.copyOf(Iterables.cycle(Map.entry("foo", 1))));
                                    }
                                }
                                """
                ));
    }

    @Test
    void testPairToImmutableSortedMapRecipe() {
        Recipe recipe = RewriteUtils.loadRecipe("ImmutableSortedMapRulesRecipes.PairToImmutableSortedMapRecipe");
        rewriteRun(
                spec -> spec.recipe(recipe)
                        .expectedCyclesThatMakeChanges(1).cycles(1)
                        .parser(JavaParser.fromJavaVersion().classpath(JavaParser.runtimeClasspath())),
                java(
                        """
                                import com.google.common.collect.ImmutableSortedMap;
                                
                                class Test {
                                    ImmutableSortedMap<String, Integer> test() {
                                        return ImmutableSortedMap.<String, Integer>naturalOrder().put("foo", 1).buildOrThrow();
                                    }
                                }
                                """,
                        """
                                import com.google.common.collect.ImmutableSortedMap;
                                
                                class Test {
                                    ImmutableSortedMap<String, Integer> test() {
                                        return ImmutableSortedMap.of("foo", 1);
                                    }
                                }
                                """
                ));
    }
}
