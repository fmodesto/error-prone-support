package tech.picnic.errorprone.refasterrules.rewrite;

import org.junit.jupiter.api.Test;
import org.openrewrite.Recipe;
import org.openrewrite.java.JavaParser;
import org.openrewrite.test.RewriteTest;

import static org.openrewrite.java.Assertions.java;

final class ImmutableSetRulesRecipesTest implements RewriteTest {

    @Test
    void testImmutableSetBuilderRecipe() {
        Recipe recipe = RewriteUtils.loadRecipe("ImmutableSetRulesRecipes.ImmutableSetBuilderRecipe");
        rewriteRun(
                spec -> spec.recipe(recipe)
                        .expectedCyclesThatMakeChanges(1).cycles(1)
                        .parser(JavaParser.fromJavaVersion().classpath(JavaParser.runtimeClasspath())),
                java(
                        """
                                import com.google.common.collect.ImmutableSet;
                                
                                class Test {
                                    ImmutableSet.Builder<String> test() {
                                        return new ImmutableSet.Builder<>();
                                    }
                                }
                                """,
                        """
                                import com.google.common.collect.ImmutableSet;
                                
                                class Test {
                                    ImmutableSet.Builder<String> test() {
                                        return ImmutableSet.builder();
                                    }
                                }
                                """
                ));
    }

    @Test
    void testImmutableSetCopyOfSetViewRecipe() {
        Recipe recipe = RewriteUtils.loadRecipe("ImmutableSetRulesRecipes.ImmutableSetCopyOfSetViewRecipe");
        rewriteRun(
                spec -> spec.recipe(recipe)
                        .expectedCyclesThatMakeChanges(1).cycles(1)
                        .parser(JavaParser.fromJavaVersion().classpath(JavaParser.runtimeClasspath())),
                java(
                        """
                                import com.google.common.collect.ImmutableSet;
                                import com.google.common.collect.Sets;
                                
                                class Test {
                                    ImmutableSet<Integer> test() {
                                        return ImmutableSet.copyOf(Sets.difference(ImmutableSet.of(1), ImmutableSet.of(2)));
                                    }
                                }
                                """,
                        """
                                import com.google.common.collect.ImmutableSet;
                                import com.google.common.collect.Sets;
                                
                                class Test {
                                    ImmutableSet<Integer> test() {
                                        return Sets.difference(ImmutableSet.of(1), ImmutableSet.of(2)).immutableCopy();
                                    }
                                }
                                """
                ));
    }

    @Test
    void testImmutableSetOfRecipe() {
        Recipe recipe = RewriteUtils.loadRecipe("ImmutableSetRulesRecipes.ImmutableSetOfRecipe");
        rewriteRun(
                spec -> spec.recipe(recipe)
                        .expectedCyclesThatMakeChanges(1).cycles(1)
                        .parser(JavaParser.fromJavaVersion().classpath(JavaParser.runtimeClasspath())),
                java(
                        """
                                import com.google.common.collect.ImmutableSet;
                                
                                import java.util.Collections;
                                import java.util.Set;
                                import java.util.stream.Stream;
                                
                                class Test {
                                    ImmutableSet<Set<Integer>> test() {
                                        return ImmutableSet.of(ImmutableSet.<Integer>builder().build(), Stream.<Integer>empty().collect(ImmutableSet.toImmutableSet()), Collections.<Integer>emptySet(), Set.<Integer>of());
                                    }
                                }
                                """,
                        """
                                import com.google.common.collect.ImmutableSet;
                                
                                import java.util.Set;
                                
                                class Test {
                                    ImmutableSet<Set<Integer>> test() {
                                        return ImmutableSet.of(ImmutableSet.of(), ImmutableSet.of(), ImmutableSet.of(), ImmutableSet.of());
                                    }
                                }
                                """
                ));
    }

    @Test
    void testImmutableSetOf1Recipe() {
        Recipe recipe = RewriteUtils.loadRecipe("ImmutableSetRulesRecipes.ImmutableSetOf1Recipe");
        rewriteRun(
                spec -> spec.recipe(recipe)
                        .expectedCyclesThatMakeChanges(1).cycles(1)
                        .parser(JavaParser.fromJavaVersion().classpath(JavaParser.runtimeClasspath())),
                java(
                        """
                                import com.google.common.collect.ImmutableSet;
                                
                                import java.util.Collections;
                                import java.util.Set;
                                
                                class Test {
                                    ImmutableSet<Set<Integer>> test() {
                                        return ImmutableSet.of(ImmutableSet.<Integer>builder().add(1).build(), Collections.singleton(1), Set.of(1));
                                    }
                                }
                                """,
                        """
                                import com.google.common.collect.ImmutableSet;
                                
                                import java.util.Set;
                                
                                class Test {
                                    ImmutableSet<Set<Integer>> test() {
                                        return ImmutableSet.of(ImmutableSet.of(1), ImmutableSet.of(1), ImmutableSet.of(1));
                                    }
                                }
                                """
                ));
    }

    @Test
    void testImmutableSetOf2Recipe() {
        Recipe recipe = RewriteUtils.loadRecipe("ImmutableSetRulesRecipes.ImmutableSetOf2Recipe");
        rewriteRun(
                spec -> spec.recipe(recipe)
                        .expectedCyclesThatMakeChanges(1).cycles(1)
                        .parser(JavaParser.fromJavaVersion().classpath(JavaParser.runtimeClasspath())),
                java(
                        """
                                import java.util.Set;
                                
                                class Test {
                                    Set<Integer> test() {
                                        return Set.of(1, 2);
                                    }
                                }
                                """,
                        """
                                import com.google.common.collect.ImmutableSet;
                                
                                import java.util.Set;
                                
                                class Test {
                                    Set<Integer> test() {
                                        return ImmutableSet.of(1, 2);
                                    }
                                }
                                """
                ));
    }

    @Test
    void testImmutableSetOf3Recipe() {
        Recipe recipe = RewriteUtils.loadRecipe("ImmutableSetRulesRecipes.ImmutableSetOf3Recipe");
        rewriteRun(
                spec -> spec.recipe(recipe)
                        .expectedCyclesThatMakeChanges(1).cycles(1)
                        .parser(JavaParser.fromJavaVersion().classpath(JavaParser.runtimeClasspath())),
                java(
                        """
                                import java.util.Set;
                                
                                class Test {
                                    Set<Integer> test() {
                                        return Set.of(1, 2, 3);
                                    }
                                }
                                """,
                        """
                                import com.google.common.collect.ImmutableSet;
                                
                                import java.util.Set;
                                
                                class Test {
                                    Set<Integer> test() {
                                        return ImmutableSet.of(1, 2, 3);
                                    }
                                }
                                """
                ));
    }

    @Test
    void testImmutableSetOf4Recipe() {
        Recipe recipe = RewriteUtils.loadRecipe("ImmutableSetRulesRecipes.ImmutableSetOf4Recipe");
        rewriteRun(
                spec -> spec.recipe(recipe)
                        .expectedCyclesThatMakeChanges(1).cycles(1)
                        .parser(JavaParser.fromJavaVersion().classpath(JavaParser.runtimeClasspath())),
                java(
                        """
                                import java.util.Set;
                                
                                class Test {
                                    Set<Integer> test() {
                                        return Set.of(1, 2, 3, 4);
                                    }
                                }
                                """,
                        """
                                import com.google.common.collect.ImmutableSet;
                                
                                import java.util.Set;
                                
                                class Test {
                                    Set<Integer> test() {
                                        return ImmutableSet.of(1, 2, 3, 4);
                                    }
                                }
                                """
                ));
    }

    @Test
    void testImmutableSetOf5Recipe() {
        Recipe recipe = RewriteUtils.loadRecipe("ImmutableSetRulesRecipes.ImmutableSetOf5Recipe");
        rewriteRun(
                spec -> spec.recipe(recipe)
                        .expectedCyclesThatMakeChanges(1).cycles(1)
                        .parser(JavaParser.fromJavaVersion().classpath(JavaParser.runtimeClasspath())),
                java(
                        """
                                import java.util.Set;
                                
                                class Test {
                                    Set<Integer> test() {
                                        return Set.of(1, 2, 3, 4, 5);
                                    }
                                }
                                """,
                        """
                                import com.google.common.collect.ImmutableSet;
                                
                                import java.util.Set;
                                
                                class Test {
                                    Set<Integer> test() {
                                        return ImmutableSet.of(1, 2, 3, 4, 5);
                                    }
                                }
                                """
                ));
    }

    @Test
    void testIterableToImmutableSetRecipe() {
        Recipe recipe = RewriteUtils.loadRecipe("ImmutableSetRulesRecipes.IterableToImmutableSetRecipe");
        rewriteRun(
                spec -> spec.recipe(recipe)
                        .expectedCyclesThatMakeChanges(1).cycles(1)
                        .parser(JavaParser.fromJavaVersion().classpath(JavaParser.runtimeClasspath())),
                java(
                        """
                                import com.google.common.collect.ImmutableList;
                                import com.google.common.collect.ImmutableSet;
                                import com.google.common.collect.Streams;
                                
                                import java.util.Arrays;
                                
                                class Test {
                                    ImmutableSet<ImmutableSet<Integer>> test() {
                                        return ImmutableSet.of(ImmutableList.of(1).stream().collect(ImmutableSet.toImmutableSet()), Streams.stream(ImmutableList.of(2)::iterator).collect(ImmutableSet.toImmutableSet()), Streams.stream(ImmutableList.of(3).iterator()).collect(ImmutableSet.toImmutableSet()), ImmutableSet.<Integer>builder().addAll(ImmutableSet.of(4)).build(), ImmutableSet.<Integer>builder().addAll(ImmutableSet.of(5)::iterator).build(), ImmutableSet.<Integer>builder().addAll(ImmutableSet.of(6).iterator()).build(), ImmutableSet.<Integer>builder().add(new Integer[]{7}).build(), Arrays.stream(new Integer[]{8}).collect(ImmutableSet.toImmutableSet()));
                                    }
                                }
                                """,
                        """
                                import com.google.common.collect.ImmutableList;
                                import com.google.common.collect.ImmutableSet;
                                
                                class Test {
                                    ImmutableSet<ImmutableSet<Integer>> test() {
                                        return ImmutableSet.of(ImmutableSet.copyOf(ImmutableList.of(1)), ImmutableSet.copyOf(ImmutableList.of(2)::iterator), ImmutableSet.copyOf(ImmutableList.of(3).iterator()), ImmutableSet.copyOf(ImmutableSet.of(4)), ImmutableSet.copyOf(ImmutableSet.of(5)::iterator), ImmutableSet.copyOf(ImmutableSet.of(6).iterator()), ImmutableSet.copyOf(new Integer[]{7}), ImmutableSet.copyOf(new Integer[]{8}));
                                    }
                                }
                                """
                ));
    }

    @Test
    void testSetsDifferenceRecipe() {
        Recipe recipe = RewriteUtils.loadRecipe("ImmutableSetRulesRecipes.SetsDifferenceRecipe");
        rewriteRun(
                spec -> spec.recipe(recipe)
                        .expectedCyclesThatMakeChanges(1).cycles(1)
                        .parser(JavaParser.fromJavaVersion().classpath(JavaParser.runtimeClasspath())),
                java(
                        """
                                import com.google.common.collect.ImmutableSet;
                                
                                import java.util.function.Predicate;
                                
                                class Test {
                                    ImmutableSet<ImmutableSet<Integer>> test() {
                                        return ImmutableSet.of(ImmutableSet.of(1).stream().filter(Predicate.not(ImmutableSet.of(2)::contains)).collect(ImmutableSet.toImmutableSet()), ImmutableSet.of(3).stream().filter(v -> !ImmutableSet.of(4).contains(v)).collect(ImmutableSet.toImmutableSet()));
                                    }
                                }
                                """,
                        """
                                import com.google.common.collect.ImmutableSet;
                                import com.google.common.collect.Sets;
                                
                                class Test {
                                    ImmutableSet<ImmutableSet<Integer>> test() {
                                        return ImmutableSet.of(Sets.difference(ImmutableSet.of(1), ImmutableSet.of(2)).immutableCopy(), Sets.difference(ImmutableSet.of(3), ImmutableSet.of(4)).immutableCopy());
                                    }
                                }
                                """
                ));
    }

    @Test
    void testSetsDifferenceMapRecipe() {
        Recipe recipe = RewriteUtils.loadRecipe("ImmutableSetRulesRecipes.SetsDifferenceMapRecipe");
        rewriteRun(
                spec -> spec.recipe(recipe)
                        .expectedCyclesThatMakeChanges(1).cycles(1)
                        .parser(JavaParser.fromJavaVersion().classpath(JavaParser.runtimeClasspath())),
                java(
                        """
                                import com.google.common.collect.ImmutableMap;
                                import com.google.common.collect.ImmutableSet;
                                
                                import java.util.function.Predicate;
                                
                                class Test {
                                    ImmutableSet<ImmutableSet<Integer>> test() {
                                        return ImmutableSet.of(ImmutableSet.of(1).stream().filter(Predicate.not(ImmutableMap.of(2, 3)::containsKey)).collect(ImmutableSet.toImmutableSet()), ImmutableSet.of(4).stream().filter(v -> !ImmutableMap.of(5, 6).containsKey(v)).collect(ImmutableSet.toImmutableSet()));
                                    }
                                }
                                """,
                        """
                                import com.google.common.collect.ImmutableMap;
                                import com.google.common.collect.ImmutableSet;
                                import com.google.common.collect.Sets;
                                
                                class Test {
                                    ImmutableSet<ImmutableSet<Integer>> test() {
                                        return ImmutableSet.of(Sets.difference(ImmutableSet.of(1), ImmutableMap.of(2, 3).keySet()).immutableCopy(), Sets.difference(ImmutableSet.of(4), ImmutableMap.of(5, 6).keySet()).immutableCopy());
                                    }
                                }
                                """
                ));
    }

    @Test
    void testSetsDifferenceMultimapRecipe() {
        Recipe recipe = RewriteUtils.loadRecipe("ImmutableSetRulesRecipes.SetsDifferenceMultimapRecipe");
        rewriteRun(
                spec -> spec.recipe(recipe)
                        .expectedCyclesThatMakeChanges(1).cycles(1)
                        .parser(JavaParser.fromJavaVersion().classpath(JavaParser.runtimeClasspath())),
                java(
                        """
                                import com.google.common.collect.ImmutableSet;
                                import com.google.common.collect.ImmutableSetMultimap;
                                
                                import java.util.function.Predicate;
                                
                                class Test {
                                    ImmutableSet<ImmutableSet<Integer>> test() {
                                        return ImmutableSet.of(ImmutableSet.of(1).stream().filter(Predicate.not(ImmutableSetMultimap.of(2, 3)::containsKey)).collect(ImmutableSet.toImmutableSet()), ImmutableSet.of(4).stream().filter(v -> !ImmutableSetMultimap.of(5, 6).containsKey(v)).collect(ImmutableSet.toImmutableSet()));
                                    }
                                }
                                """,
                        """
                                import com.google.common.collect.ImmutableSet;
                                import com.google.common.collect.ImmutableSetMultimap;
                                import com.google.common.collect.Sets;
                                
                                class Test {
                                    ImmutableSet<ImmutableSet<Integer>> test() {
                                        return ImmutableSet.of(Sets.difference(ImmutableSet.of(1), ImmutableSetMultimap.of(2, 3).keySet()).immutableCopy(), Sets.difference(ImmutableSet.of(4), ImmutableSetMultimap.of(5, 6).keySet()).immutableCopy());
                                    }
                                }
                                """
                ));
    }

    @Test
    void testSetsIntersectionRecipe() {
        Recipe recipe = RewriteUtils.loadRecipe("ImmutableSetRulesRecipes.SetsIntersectionRecipe");
        rewriteRun(
                spec -> spec.recipe(recipe)
                        .expectedCyclesThatMakeChanges(1).cycles(1)
                        .parser(JavaParser.fromJavaVersion().classpath(JavaParser.runtimeClasspath())),
                java(
                        """
                                import com.google.common.collect.ImmutableSet;
                                
                                import java.util.function.Predicate;
                                
                                class Test {
                                    ImmutableSet<Integer> test() {
                                        return ImmutableSet.of(1).stream().filter(ImmutableSet.of(2)::contains).collect(ImmutableSet.toImmutableSet());
                                    }
                                }
                                """,
                        """
                                import com.google.common.collect.ImmutableSet;
                                import com.google.common.collect.Sets;
                                
                                class Test {
                                    ImmutableSet<Integer> test() {
                                        return Sets.intersection(ImmutableSet.of(1), ImmutableSet.of(2)).immutableCopy();
                                    }
                                }
                                """
                ));
    }

    @Test
    void testSetsIntersectionMapRecipe() {
        Recipe recipe = RewriteUtils.loadRecipe("ImmutableSetRulesRecipes.SetsIntersectionMapRecipe");
        rewriteRun(
                spec -> spec.recipe(recipe)
                        .expectedCyclesThatMakeChanges(1).cycles(1)
                        .parser(JavaParser.fromJavaVersion().classpath(JavaParser.runtimeClasspath())),
                java(
                        """
                                import com.google.common.collect.ImmutableMap;
                                import com.google.common.collect.ImmutableSet;
                                
                                import java.util.function.Predicate;
                                
                                class Test {
                                    ImmutableSet<Integer> test() {
                                        return ImmutableSet.of(1).stream().filter(ImmutableMap.of(2, 3)::containsKey).collect(ImmutableSet.toImmutableSet());
                                    }
                                }
                                """,
                        """
                                import com.google.common.collect.ImmutableMap;
                                import com.google.common.collect.ImmutableSet;
                                import com.google.common.collect.Sets;
                                
                                class Test {
                                    ImmutableSet<Integer> test() {
                                        return Sets.intersection(ImmutableSet.of(1), ImmutableMap.of(2, 3).keySet()).immutableCopy();
                                    }
                                }
                                """
                ));
    }

    @Test
    void testSetsIntersectionMultimapRecipe() {
        Recipe recipe = RewriteUtils.loadRecipe("ImmutableSetRulesRecipes.SetsIntersectionMultimapRecipe");
        rewriteRun(
                spec -> spec.recipe(recipe)
                        .expectedCyclesThatMakeChanges(1).cycles(1)
                        .parser(JavaParser.fromJavaVersion().classpath(JavaParser.runtimeClasspath())),
                java(
                        """
                                import com.google.common.collect.ImmutableSet;
                                import com.google.common.collect.ImmutableSetMultimap;
                                
                                import java.util.function.Predicate;
                                
                                class Test {
                                    ImmutableSet<Integer> test() {
                                        return ImmutableSet.of(1).stream().filter(ImmutableSetMultimap.of(2, 3)::containsKey).collect(ImmutableSet.toImmutableSet());
                                    }
                                }
                                """,
                        """
                                import com.google.common.collect.ImmutableSet;
                                import com.google.common.collect.ImmutableSetMultimap;
                                import com.google.common.collect.Sets;
                                
                                class Test {
                                    ImmutableSet<Integer> test() {
                                        return Sets.intersection(ImmutableSet.of(1), ImmutableSetMultimap.of(2, 3).keySet()).immutableCopy();
                                    }
                                }
                                """
                ));
    }

    @Test
    void testSetsUnionRecipe() {
        Recipe recipe = RewriteUtils.loadRecipe("ImmutableSetRulesRecipes.SetsUnionRecipe");
        rewriteRun(
                spec -> spec.recipe(recipe)
                        .expectedCyclesThatMakeChanges(1).cycles(1)
                        .parser(JavaParser.fromJavaVersion().classpath(JavaParser.runtimeClasspath())),
                java(
                        """
                                import com.google.common.collect.ImmutableSet;
                                
                                import java.util.stream.Stream;
                                
                                class Test {
                                    ImmutableSet<Integer> test() {
                                        return Stream.concat(ImmutableSet.of(1).stream(), ImmutableSet.of(2).stream()).collect(ImmutableSet.toImmutableSet());
                                    }
                                }
                                """,
                        """
                                import com.google.common.collect.ImmutableSet;
                                import com.google.common.collect.Sets;
                                
                                class Test {
                                    ImmutableSet<Integer> test() {
                                        return Sets.union(ImmutableSet.of(1), ImmutableSet.of(2)).immutableCopy();
                                    }
                                }
                                """
                ));
    }

    @Test
    void testStreamToImmutableSetRecipe() {
        Recipe recipe = RewriteUtils.loadRecipe("ImmutableSetRulesRecipes.StreamToImmutableSetRecipe");
        rewriteRun(
                spec -> spec.recipe(recipe)
                        .expectedCyclesThatMakeChanges(1).cycles(1)
                        .parser(JavaParser.fromJavaVersion().classpath(JavaParser.runtimeClasspath())),
                java(
                        """
                                import com.google.common.collect.ImmutableSet;
                                
                                import java.util.stream.Stream;
                                
                                class Test {
                                    ImmutableSet<ImmutableSet<Integer>> test() {
                                        return ImmutableSet.of(ImmutableSet.copyOf(Stream.of(1).iterator()), Stream.of(2).distinct().collect(ImmutableSet.toImmutableSet()));
                                    }
                                }
                                """,
                        """
                                import com.google.common.collect.ImmutableSet;
                                
                                import java.util.stream.Stream;
                                
                                class Test {
                                    ImmutableSet<ImmutableSet<Integer>> test() {
                                        return ImmutableSet.of(Stream.of(1).collect(ImmutableSet.toImmutableSet()), Stream.of(2).collect(ImmutableSet.toImmutableSet()));
                                    }
                                }
                                """
                ));
    }
}
