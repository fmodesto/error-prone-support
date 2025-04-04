package tech.picnic.errorprone.refasterrules.rewrite;

import org.junit.jupiter.api.Test;
import org.openrewrite.Recipe;
import org.openrewrite.java.JavaParser;
import org.openrewrite.test.RewriteTest;

import static org.openrewrite.java.Assertions.java;

final class ImmutableListRulesRecipesTest implements RewriteTest {

    @Test
    void testImmutableListBuilderRecipe() {
        Recipe recipe = RewriteUtils.loadRecipe("ImmutableListRulesRecipes.ImmutableListBuilderRecipe");
        rewriteRun(
                spec -> spec.recipe(recipe)
                        .expectedCyclesThatMakeChanges(1).cycles(1)
                        .parser(JavaParser.fromJavaVersion().classpath(JavaParser.runtimeClasspath())),
                java(
                        """
                                import com.google.common.collect.ImmutableList;
                                
                                class Test {
                                    ImmutableList.Builder<String> test() {
                                        return new ImmutableList.Builder<>();
                                    }
                                }
                                """,
                        """
                                import com.google.common.collect.ImmutableList;
                                
                                class Test {
                                    ImmutableList.Builder<String> test() {
                                        return ImmutableList.builder();
                                    }
                                }
                                """
                ));
    }

    @Test
    void testImmutableListOfRecipe() {
        Recipe recipe = RewriteUtils.loadRecipe("ImmutableListRulesRecipes.ImmutableListOfRecipe");
        rewriteRun(
                spec -> spec.recipe(recipe)
                        .expectedCyclesThatMakeChanges(1).cycles(1)
                        .parser(JavaParser.fromJavaVersion().classpath(JavaParser.runtimeClasspath())),
                java(
                        """
                                import com.google.common.collect.ImmutableList;
                                import com.google.common.collect.ImmutableSet;
                                
                                import java.util.Collections;
                                import java.util.List;
                                import java.util.stream.Stream;
                                
                                class Test {
                                    ImmutableSet<List<Integer>> test() {
                                        return ImmutableSet.of(ImmutableList.<Integer>builder().build(), Stream.<Integer>empty().collect(ImmutableList.toImmutableList()), Collections.<Integer>emptyList(), List.<Integer>of());
                                    }
                                }
                                """,
                        """
                                import com.google.common.collect.ImmutableList;
                                import com.google.common.collect.ImmutableSet;
                                
                                import java.util.List;
                                
                                class Test {
                                    ImmutableSet<List<Integer>> test() {
                                        return ImmutableSet.of(ImmutableList.of(), ImmutableList.of(), ImmutableList.of(), ImmutableList.of());
                                    }
                                }
                                """
                ));
    }

    @Test
    void testImmutableListOf1Recipe() {
        Recipe recipe = RewriteUtils.loadRecipe("ImmutableListRulesRecipes.ImmutableListOf1Recipe");
        rewriteRun(
                spec -> spec.recipe(recipe)
                        .expectedCyclesThatMakeChanges(1).cycles(1)
                        .parser(JavaParser.fromJavaVersion().classpath(JavaParser.runtimeClasspath())),
                java(
                        """
                                import com.google.common.collect.ImmutableSet;
                                
                                import java.util.Collections;
                                import java.util.List;
                                
                                class Test {
                                    ImmutableSet<List<Integer>> test() {
                                        return ImmutableSet.of(Collections.singletonList(1), List.of(1));
                                    }
                                }
                                """,
                        """
                                import com.google.common.collect.ImmutableList;
                                import com.google.common.collect.ImmutableSet;
                                
                                import java.util.List;
                                
                                class Test {
                                    ImmutableSet<List<Integer>> test() {
                                        return ImmutableSet.of(ImmutableList.of(1), ImmutableList.of(1));
                                    }
                                }
                                """
                ));
    }

    @Test
    void testImmutableListOf2Recipe() {
        Recipe recipe = RewriteUtils.loadRecipe("ImmutableListRulesRecipes.ImmutableListOf2Recipe");
        rewriteRun(
                spec -> spec.recipe(recipe)
                        .expectedCyclesThatMakeChanges(1).cycles(1)
                        .parser(JavaParser.fromJavaVersion().classpath(JavaParser.runtimeClasspath())),
                java(
                        """
                                import java.util.List;
                                
                                class Test {
                                    List<Integer> test() {
                                        return List.of(1, 2);
                                    }
                                }
                                """,
                        """
                                import com.google.common.collect.ImmutableList;
                                
                                import java.util.List;
                                
                                class Test {
                                    List<Integer> test() {
                                        return ImmutableList.of(1, 2);
                                    }
                                }
                                """
                ));
    }

    @Test
    void testImmutableListOf3Recipe() {
        Recipe recipe = RewriteUtils.loadRecipe("ImmutableListRulesRecipes.ImmutableListOf3Recipe");
        rewriteRun(
                spec -> spec.recipe(recipe)
                        .expectedCyclesThatMakeChanges(1).cycles(1)
                        .parser(JavaParser.fromJavaVersion().classpath(JavaParser.runtimeClasspath())),
                java(
                        """
                                import java.util.List;
                                
                                class Test {
                                    List<Integer> test() {
                                        return List.of(1, 2, 3);
                                    }
                                }
                                """,
                        """
                                import com.google.common.collect.ImmutableList;
                                
                                import java.util.List;
                                
                                class Test {
                                    List<Integer> test() {
                                        return ImmutableList.of(1, 2, 3);
                                    }
                                }
                                """
                ));
    }

    @Test
    void testImmutableListOf4Recipe() {
        Recipe recipe = RewriteUtils.loadRecipe("ImmutableListRulesRecipes.ImmutableListOf4Recipe");
        rewriteRun(
                spec -> spec.recipe(recipe)
                        .expectedCyclesThatMakeChanges(1).cycles(1)
                        .parser(JavaParser.fromJavaVersion().classpath(JavaParser.runtimeClasspath())),
                java(
                        """
                                import java.util.List;
                                
                                class Test {
                                    List<Integer> test() {
                                        return List.of(1, 2, 3, 4);
                                    }
                                }
                                """,
                        """
                                import com.google.common.collect.ImmutableList;
                                
                                import java.util.List;
                                
                                class Test {
                                    List<Integer> test() {
                                        return ImmutableList.of(1, 2, 3, 4);
                                    }
                                }
                                """
                ));
    }

    @Test
    void testImmutableListOf5Recipe() {
        Recipe recipe = RewriteUtils.loadRecipe("ImmutableListRulesRecipes.ImmutableListOf5Recipe");
        rewriteRun(
                spec -> spec.recipe(recipe)
                        .expectedCyclesThatMakeChanges(1).cycles(1)
                        .parser(JavaParser.fromJavaVersion().classpath(JavaParser.runtimeClasspath())),
                java(
                        """
                                import java.util.List;
                                
                                class Test {
                                    List<Integer> test() {
                                        return List.of(1, 2, 3, 4, 5);
                                    }
                                }
                                """,
                        """
                                import com.google.common.collect.ImmutableList;
                                
                                import java.util.List;
                                
                                class Test {
                                    List<Integer> test() {
                                        return ImmutableList.of(1, 2, 3, 4, 5);
                                    }
                                }
                                """
                ));
    }

    @Test
    void testImmutableListSortedCopyOfRecipe() {
        Recipe recipe = RewriteUtils.loadRecipe("ImmutableListRulesRecipes.ImmutableListSortedCopyOfRecipe");
        rewriteRun(
                spec -> spec.recipe(recipe)
                        .expectedCyclesThatMakeChanges(1).cycles(1)
                        .parser(JavaParser.fromJavaVersion().classpath(JavaParser.runtimeClasspath())),
                java(
                        """
                                import com.google.common.collect.ImmutableList;
                                import com.google.common.collect.ImmutableSet;
                                import com.google.common.collect.Streams;
                                
                                import java.util.Comparator;
                                
                                class Test {
                                    ImmutableSet<ImmutableList<Integer>> test() {
                                        return ImmutableSet.of(ImmutableList.sortedCopyOf(Comparator.naturalOrder(), ImmutableSet.of(1)), ImmutableSet.of(2).stream().sorted().collect(ImmutableList.toImmutableList()), Streams.stream(ImmutableSet.of(3)::iterator).sorted().collect(ImmutableList.toImmutableList()));
                                    }
                                }
                                """,
                        """
                                import com.google.common.collect.ImmutableList;
                                import com.google.common.collect.ImmutableSet;
                                
                                class Test {
                                    ImmutableSet<ImmutableList<Integer>> test() {
                                        return ImmutableSet.of(ImmutableList.sortedCopyOf(ImmutableSet.of(1)), ImmutableList.sortedCopyOf(ImmutableSet.of(2)), ImmutableList.sortedCopyOf(ImmutableSet.of(3)::iterator));
                                    }
                                }
                                """
                ));
    }

    @Test
    void testImmutableListSortedCopyOfWithCustomComparatorRecipe() {
        Recipe recipe = RewriteUtils.loadRecipe("ImmutableListRulesRecipes.ImmutableListSortedCopyOfWithCustomComparatorRecipe");
        rewriteRun(
                spec -> spec.recipe(recipe)
                        .expectedCyclesThatMakeChanges(1).cycles(1)
                        .parser(JavaParser.fromJavaVersion().classpath(JavaParser.runtimeClasspath())),
                java(
                        """
                                import com.google.common.collect.ImmutableList;
                                import com.google.common.collect.ImmutableSet;
                                import com.google.common.collect.Streams;
                                
                                import java.util.Comparator;
                                
                                class Test {
                                    ImmutableSet<ImmutableList<String>> test() {
                                        return ImmutableSet.of(ImmutableSet.of("foo").stream().sorted(Comparator.comparing(String::length)).collect(ImmutableList.toImmutableList()), Streams.stream(ImmutableSet.of("bar")::iterator).sorted(Comparator.comparing(String::isEmpty)).collect(ImmutableList.toImmutableList()));
                                    }
                                }
                                """,
                        """
                                import com.google.common.collect.ImmutableList;
                                import com.google.common.collect.ImmutableSet;
                                
                                import java.util.Comparator;
                                
                                class Test {
                                    ImmutableSet<ImmutableList<String>> test() {
                                        return ImmutableSet.of(ImmutableList.sortedCopyOf(Comparator.comparing(String::length), ImmutableSet.of("foo")), ImmutableList.sortedCopyOf(Comparator.comparing(String::isEmpty), ImmutableSet.of("bar")::iterator));
                                    }
                                }
                                """
                ));
    }

    @Test
    void testIterableToImmutableListRecipe() {
        Recipe recipe = RewriteUtils.loadRecipe("ImmutableListRulesRecipes.IterableToImmutableListRecipe");
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
                                    ImmutableSet<ImmutableList<Integer>> test() {
                                        return ImmutableSet.of(ImmutableList.of(1).stream().collect(ImmutableList.toImmutableList()), Streams.stream(ImmutableList.of(2)::iterator).collect(ImmutableList.toImmutableList()), Streams.stream(ImmutableList.of(3).iterator()).collect(ImmutableList.toImmutableList()), ImmutableList.<Integer>builder().addAll(ImmutableList.of(4)).build(), ImmutableList.<Integer>builder().addAll(ImmutableList.of(5)::iterator).build(), ImmutableList.<Integer>builder().addAll(ImmutableList.of(6).iterator()).build(), ImmutableList.<Integer>builder().add(new Integer[]{7}).build(), Arrays.stream(new Integer[]{8}).collect(ImmutableList.toImmutableList()));
                                    }
                                }
                                """,
                        """
                                import com.google.common.collect.ImmutableList;
                                import com.google.common.collect.ImmutableSet;
                                
                                class Test {
                                    ImmutableSet<ImmutableList<Integer>> test() {
                                        return ImmutableSet.of(ImmutableList.copyOf(ImmutableList.of(1)), ImmutableList.copyOf(ImmutableList.of(2)::iterator), ImmutableList.copyOf(ImmutableList.of(3).iterator()), ImmutableList.copyOf(ImmutableList.of(4)), ImmutableList.copyOf(ImmutableList.of(5)::iterator), ImmutableList.copyOf(ImmutableList.of(6).iterator()), ImmutableList.copyOf(new Integer[]{7}), ImmutableList.copyOf(new Integer[]{8}));
                                    }
                                }
                                """
                ));
    }

    @Test
    void testStreamToDistinctImmutableListRecipe() {
        Recipe recipe = RewriteUtils.loadRecipe("ImmutableListRulesRecipes.StreamToDistinctImmutableListRecipe");
        rewriteRun(
                spec -> spec.recipe(recipe)
                        .expectedCyclesThatMakeChanges(1).cycles(1)
                        .parser(JavaParser.fromJavaVersion().classpath(JavaParser.runtimeClasspath())),
                java(
                        """
                                import com.google.common.collect.ImmutableList;
                                
                                import java.util.stream.Stream;
                                
                                class Test {
                                    ImmutableList<Integer> test() {
                                        return Stream.of(1).distinct().collect(ImmutableList.toImmutableList());
                                    }
                                }
                                """,
                        """
                                import com.google.common.collect.ImmutableList;
                                import com.google.common.collect.ImmutableSet;
                                
                                import java.util.stream.Stream;
                                
                                class Test {
                                    ImmutableList<Integer> test() {
                                        return Stream.of(1).collect(ImmutableSet.toImmutableSet()).asList();
                                    }
                                }
                                """
                ));
    }

    @Test
    void testStreamToImmutableListRecipe() {
        Recipe recipe = RewriteUtils.loadRecipe("ImmutableListRulesRecipes.StreamToImmutableListRecipe");
        rewriteRun(
                spec -> spec.recipe(recipe)
                        .expectedCyclesThatMakeChanges(1).cycles(1)
                        .parser(JavaParser.fromJavaVersion().classpath(JavaParser.runtimeClasspath())),
                java(
                        """
                                import com.google.common.collect.ImmutableList;
                                
                                import java.util.stream.Stream;
                                
                                class Test {
                                    ImmutableList<Integer> test() {
                                        return ImmutableList.copyOf(Stream.of(1).iterator());
                                    }
                                }
                                """,
                        """
                                import com.google.common.collect.ImmutableList;
                                
                                import java.util.stream.Stream;
                                
                                class Test {
                                    ImmutableList<Integer> test() {
                                        return Stream.of(1).collect(ImmutableList.toImmutableList());
                                    }
                                }
                                """
                ));
    }
}
