package tech.picnic.errorprone.refasterrules.rewrite;

import org.junit.jupiter.api.Test;
import org.openrewrite.Recipe;
import org.openrewrite.java.JavaParser;
import org.openrewrite.test.RewriteTest;

import static org.openrewrite.java.Assertions.java;

final class CollectionRulesRecipesTest implements RewriteTest {

    @Test
    void testArraysAsListRecipe() {
        Recipe recipe = RewriteUtils.loadRecipe("CollectionRulesRecipes.ArraysAsListRecipe");
        rewriteRun(
                spec -> spec.recipe(recipe)
                        .expectedCyclesThatMakeChanges(1).cycles(1)
                        .parser(JavaParser.fromJavaVersion().classpath(JavaParser.runtimeClasspath())),
                java(
                        """
                                import java.util.Arrays;
                                import java.util.List;
                                
                                class Test {
                                    List<String> test() {
                                        return Arrays.stream(new String[0]).toList();
                                    }
                                }
                                """,
                        """
                                import java.util.Arrays;
                                import java.util.List;
                                
                                class Test {
                                    List<String> test() {
                                        return Arrays.asList(new String[0]);
                                    }
                                }
                                """
                ));
    }

    @Test
    void testCollectionAddAllToCollectionBlockRecipe() {
        Recipe recipe = RewriteUtils.loadRecipe("CollectionRulesRecipes.CollectionAddAllToCollectionBlockRecipe");
        rewriteRun(
                spec -> spec.recipe(recipe)
                        .expectedCyclesThatMakeChanges(1).cycles(1)
                        .parser(JavaParser.fromJavaVersion().classpath(JavaParser.runtimeClasspath())),
                java(
                        """
                                import com.google.common.collect.ImmutableSet;
                                
                                import java.util.ArrayList;
                                
                                class Test {
                                    void test() {
                                        ImmutableSet.of("foo").forEach(new ArrayList<>()::add);
                                        for (Number element : ImmutableSet.of(1)) {
                                            new ArrayList<Number>().add(element);
                                        }
                                        for (Integer element : ImmutableSet.of(2)) {
                                            new ArrayList<Number>().add(element);
                                        }
                                    }
                                }
                                """,
                        """
                                import com.google.common.collect.ImmutableSet;
                                
                                import java.util.ArrayList;
                                
                                class Test {
                                    void test() {
                                        new ArrayList<>().addAll(ImmutableSet.of("foo"));
                                        new ArrayList<Number>().addAll(ImmutableSet.of(1));
                                        new ArrayList<Number>().addAll(ImmutableSet.of(2));
                                    }
                                }
                                """
                ));
    }

    @Test
    void testCollectionAddAllToCollectionExpressionRecipe() {
        Recipe recipe = RewriteUtils.loadRecipe("CollectionRulesRecipes.CollectionAddAllToCollectionExpressionRecipe");
        rewriteRun(
                spec -> spec.recipe(recipe)
                        .expectedCyclesThatMakeChanges(1).cycles(1)
                        .parser(JavaParser.fromJavaVersion().classpath(JavaParser.runtimeClasspath())),
                java(
                        """
                                import com.google.common.collect.ImmutableSet;
                                import com.google.common.collect.Iterables;
                                
                                import java.util.ArrayList;
                                
                                class Test {
                                    boolean test() {
                                        return Iterables.addAll(new ArrayList<>(), ImmutableSet.of("foo"));
                                    }
                                }
                                """,
                        """
                                import com.google.common.collect.ImmutableSet;
                                
                                import java.util.ArrayList;
                                
                                class Test {
                                    boolean test() {
                                        return new ArrayList<>().addAll(ImmutableSet.of("foo"));
                                    }
                                }
                                """
                ));
    }

    @Test
    void testCollectionContainsRecipe() {
        Recipe recipe = RewriteUtils.loadRecipe("CollectionRulesRecipes.CollectionContainsRecipe");
        rewriteRun(
                spec -> spec.recipe(recipe)
                        .expectedCyclesThatMakeChanges(1).cycles(1)
                        .parser(JavaParser.fromJavaVersion().classpath(JavaParser.runtimeClasspath())),
                java(
                        """
                                import com.google.common.collect.ImmutableSet;
                                
                                class Test {
                                    boolean test() {
                                        return ImmutableSet.of("foo").stream().anyMatch("bar"::equals);
                                    }
                                }
                                """,
                        """
                                import com.google.common.collect.ImmutableSet;
                                
                                class Test {
                                    boolean test() {
                                        return ImmutableSet.of("foo").contains("bar");
                                    }
                                }
                                """
                ));
    }

    @Test
    void testCollectionForEachRecipe() {
        Recipe recipe = RewriteUtils.loadRecipe("CollectionRulesRecipes.CollectionForEachRecipe");
        rewriteRun(
                spec -> spec.recipe(recipe)
                        .expectedCyclesThatMakeChanges(1).cycles(1)
                        .parser(JavaParser.fromJavaVersion().classpath(JavaParser.runtimeClasspath())),
                java(
                        """
                                import com.google.common.collect.ImmutableSet;
                                
                                class Test {
                                    void test() {
                                        ImmutableSet.of(1).stream().forEach(String::valueOf);
                                    }
                                }
                                """,
                        """
                                import com.google.common.collect.ImmutableSet;
                                
                                class Test {
                                    void test() {
                                        ImmutableSet.of(1).forEach(String::valueOf);
                                    }
                                }
                                """
                ));
    }

    @Test
    void testCollectionIsEmptyRecipe() {
        Recipe recipe = RewriteUtils.loadRecipe("CollectionRulesRecipes.CollectionIsEmptyRecipe");
        rewriteRun(
                spec -> spec.recipe(recipe)
                        .expectedCyclesThatMakeChanges(1).cycles(1)
                        .parser(JavaParser.fromJavaVersion().classpath(JavaParser.runtimeClasspath())),
                java(
                        """
                                import com.google.common.collect.ImmutableSet;
                                import com.google.common.collect.Iterables;
                                
                                class Test {
                                    ImmutableSet<Boolean> test() {
                                        return ImmutableSet.of(ImmutableSet.of(1).size() == 0, ImmutableSet.of(2).size() <= 0, ImmutableSet.of(3).size() < 1, ImmutableSet.of(4).size() != 0, ImmutableSet.of(5).size() > 0, ImmutableSet.of(6).size() >= 1, Iterables.isEmpty(ImmutableSet.of(7)), ImmutableSet.of(8).stream().findAny().isEmpty(), ImmutableSet.of(9).stream().findFirst().isEmpty(), ImmutableSet.of(10).asList().isEmpty());
                                    }
                                }
                                """,
                        """
                                import com.google.common.collect.ImmutableSet;
                                
                                class Test {
                                    ImmutableSet<Boolean> test() {
                                        return ImmutableSet.of(ImmutableSet.of(1).isEmpty(), ImmutableSet.of(2).isEmpty(), ImmutableSet.of(3).isEmpty(), !ImmutableSet.of(4).isEmpty(), !ImmutableSet.of(5).isEmpty(), !ImmutableSet.of(6).isEmpty(), ImmutableSet.of(7).isEmpty(), ImmutableSet.of(8).isEmpty(), ImmutableSet.of(9).isEmpty(), ImmutableSet.of(10).isEmpty());
                                    }
                                }
                                """
                ));
    }

    @Test
    void testCollectionIteratorRecipe() {
        Recipe recipe = RewriteUtils.loadRecipe("CollectionRulesRecipes.CollectionIteratorRecipe");
        rewriteRun(
                spec -> spec.recipe(recipe)
                        .expectedCyclesThatMakeChanges(1).cycles(1)
                        .parser(JavaParser.fromJavaVersion().classpath(JavaParser.runtimeClasspath())),
                java(
                        """
                                import com.google.common.collect.ImmutableSet;
                                
                                import java.util.Iterator;
                                
                                class Test {
                                    ImmutableSet<Iterator<Integer>> test() {
                                        return ImmutableSet.of(ImmutableSet.of(1).stream().iterator(), ImmutableSet.of(2).asList().iterator());
                                    }
                                }
                                """,
                        """
                                import com.google.common.collect.ImmutableSet;
                                
                                import java.util.Iterator;
                                
                                class Test {
                                    ImmutableSet<Iterator<Integer>> test() {
                                        return ImmutableSet.of(ImmutableSet.of(1).iterator(), ImmutableSet.of(2).iterator());
                                    }
                                }
                                """
                ));
    }

    @Test
    void testCollectionRemoveAllFromCollectionBlockRecipe() {
        Recipe recipe = RewriteUtils.loadRecipe("CollectionRulesRecipes.CollectionRemoveAllFromCollectionBlockRecipe");
        rewriteRun(
                spec -> spec.recipe(recipe)
                        .expectedCyclesThatMakeChanges(1).cycles(1)
                        .parser(JavaParser.fromJavaVersion().classpath(JavaParser.runtimeClasspath())),
                java(
                        """
                                import com.google.common.collect.ImmutableList;
                                import com.google.common.collect.ImmutableSet;
                                
                                import java.util.HashSet;
                                
                                class Test {
                                    void test() {
                                        ImmutableSet.of("foo").forEach(new HashSet<>()::remove);
                                        for (Number element : ImmutableList.of(1)) {
                                            new HashSet<Number>().remove(element);
                                        }
                                        for (Integer element : ImmutableSet.of(2)) {
                                            new HashSet<Number>().remove(element);
                                        }
                                    }
                                }
                                """,
                        """
                                import com.google.common.collect.ImmutableList;
                                import com.google.common.collect.ImmutableSet;
                                
                                import java.util.HashSet;
                                
                                class Test {
                                    void test() {
                                        new HashSet<>().removeAll(ImmutableSet.of("foo"));
                                        new HashSet<Number>().removeAll(ImmutableList.of(1));
                                        new HashSet<Number>().removeAll(ImmutableSet.of(2));
                                    }
                                }
                                """
                ));
    }

    @Test
    void testCollectionRemoveAllFromCollectionExpressionRecipe() {
        Recipe recipe = RewriteUtils.loadRecipe("CollectionRulesRecipes.CollectionRemoveAllFromCollectionExpressionRecipe");
        rewriteRun(
                spec -> spec.recipe(recipe)
                        .expectedCyclesThatMakeChanges(1).cycles(1)
                        .parser(JavaParser.fromJavaVersion().classpath(JavaParser.runtimeClasspath())),
                java(
                        """
                                import com.google.common.collect.ImmutableSet;
                                import com.google.common.collect.Iterables;
                                
                                import java.util.ArrayList;
                                
                                class Test {
                                    boolean test() {
                                        return Iterables.removeAll(new ArrayList<>(), ImmutableSet.of("foo"));
                                    }
                                }
                                """,
                        """
                                import com.google.common.collect.ImmutableSet;
                                
                                import java.util.ArrayList;
                                
                                class Test {
                                    boolean test() {
                                        return new ArrayList<>().removeAll(ImmutableSet.of("foo"));
                                    }
                                }
                                """
                ));
    }

    @Test
    void testCollectionSizeRecipe() {
        Recipe recipe = RewriteUtils.loadRecipe("CollectionRulesRecipes.CollectionSizeRecipe");
        rewriteRun(
                spec -> spec.recipe(recipe)
                        .expectedCyclesThatMakeChanges(1).cycles(1)
                        .parser(JavaParser.fromJavaVersion().classpath(JavaParser.runtimeClasspath())),
                java(
                        """
                                import com.google.common.collect.ImmutableSet;
                                import com.google.common.collect.Iterables;
                                
                                class Test {
                                    ImmutableSet<Integer> test() {
                                        return ImmutableSet.of(Iterables.size(ImmutableSet.of(1)), ImmutableSet.of(2).asList().size());
                                    }
                                }
                                """,
                        """
                                import com.google.common.collect.ImmutableSet;
                                
                                class Test {
                                    ImmutableSet<Integer> test() {
                                        return ImmutableSet.of(ImmutableSet.of(1).size(), ImmutableSet.of(2).size());
                                    }
                                }
                                """
                ));
    }

    @Test
    void testCollectionToArrayRecipe() {
        Recipe recipe = RewriteUtils.loadRecipe("CollectionRulesRecipes.CollectionToArrayRecipe");
        rewriteRun(
                spec -> spec.recipe(recipe)
                        .expectedCyclesThatMakeChanges(1).cycles(1)
                        .parser(JavaParser.fromJavaVersion().classpath(JavaParser.runtimeClasspath())),
                java(
                        """
                                import com.google.common.collect.ImmutableSet;
                                
                                class Test {
                                    ImmutableSet<Object[]> test() {
                                        return ImmutableSet.of(ImmutableSet.of(1).toArray(new Object[1]), ImmutableSet.of(2).toArray(Object[]::new), ImmutableSet.of(3).asList().toArray());
                                    }
                                }
                                """,
                        """
                                import com.google.common.collect.ImmutableSet;
                                
                                class Test {
                                    ImmutableSet<Object[]> test() {
                                        return ImmutableSet.of(ImmutableSet.of(1).toArray(), ImmutableSet.of(2).toArray(), ImmutableSet.of(3).toArray());
                                    }
                                }
                                """
                ));
    }

    @Test
    void testImmutableCollectionAsListRecipe() {
        Recipe recipe = RewriteUtils.loadRecipe("CollectionRulesRecipes.ImmutableCollectionAsListRecipe");
        rewriteRun(
                spec -> spec.recipe(recipe)
                        .expectedCyclesThatMakeChanges(1).cycles(1)
                        .parser(JavaParser.fromJavaVersion().classpath(JavaParser.runtimeClasspath())),
                java(
                        """
                                import com.google.common.collect.ImmutableList;
                                import com.google.common.collect.ImmutableSet;
                                
                                class Test {
                                    ImmutableList<Integer> test() {
                                        return ImmutableList.copyOf(ImmutableSet.of(1));
                                    }
                                }
                                """,
                        """
                                import com.google.common.collect.ImmutableList;
                                import com.google.common.collect.ImmutableSet;
                                
                                class Test {
                                    ImmutableList<Integer> test() {
                                        return ImmutableSet.of(1).asList();
                                    }
                                }
                                """
                ));
    }

    @Test
    void testImmutableCollectionContainsRecipe() {
        Recipe recipe = RewriteUtils.loadRecipe("CollectionRulesRecipes.ImmutableCollectionContainsRecipe");
        rewriteRun(
                spec -> spec.recipe(recipe)
                        .expectedCyclesThatMakeChanges(1).cycles(1)
                        .parser(JavaParser.fromJavaVersion().classpath(JavaParser.runtimeClasspath())),
                java(
                        """
                                import com.google.common.collect.ImmutableSet;
                                
                                class Test {
                                    boolean test() {
                                        return ImmutableSet.of(1).asList().contains("foo");
                                    }
                                }
                                """,
                        """
                                import com.google.common.collect.ImmutableSet;
                                
                                class Test {
                                    boolean test() {
                                        return ImmutableSet.of(1).contains("foo");
                                    }
                                }
                                """
                ));
    }

    @Test
    void testImmutableCollectionParallelStreamRecipe() {
        Recipe recipe = RewriteUtils.loadRecipe("CollectionRulesRecipes.ImmutableCollectionParallelStreamRecipe");
        rewriteRun(
                spec -> spec.recipe(recipe)
                        .expectedCyclesThatMakeChanges(1).cycles(1)
                        .parser(JavaParser.fromJavaVersion().classpath(JavaParser.runtimeClasspath())),
                java(
                        """
                                import com.google.common.collect.ImmutableSet;
                                
                                import java.util.stream.Stream;
                                
                                class Test {
                                    Stream<Integer> test() {
                                        return ImmutableSet.of(1).asList().parallelStream();
                                    }
                                }
                                """,
                        """
                                import com.google.common.collect.ImmutableSet;
                                
                                import java.util.stream.Stream;
                                
                                class Test {
                                    Stream<Integer> test() {
                                        return ImmutableSet.of(1).parallelStream();
                                    }
                                }
                                """
                ));
    }

    @Test
    void testImmutableCollectionStreamRecipe() {
        Recipe recipe = RewriteUtils.loadRecipe("CollectionRulesRecipes.ImmutableCollectionStreamRecipe");
        rewriteRun(
                spec -> spec.recipe(recipe)
                        .expectedCyclesThatMakeChanges(1).cycles(1)
                        .parser(JavaParser.fromJavaVersion().classpath(JavaParser.runtimeClasspath())),
                java(
                        """
                                import com.google.common.collect.ImmutableSet;
                                
                                import java.util.stream.Stream;
                                
                                class Test {
                                    Stream<Integer> test() {
                                        return ImmutableSet.of(1).asList().stream();
                                    }
                                }
                                """,
                        """
                                import com.google.common.collect.ImmutableSet;
                                
                                import java.util.stream.Stream;
                                
                                class Test {
                                    Stream<Integer> test() {
                                        return ImmutableSet.of(1).stream();
                                    }
                                }
                                """
                ));
    }

    @Test
    void testImmutableCollectionToArrayWithArrayRecipe() {
        Recipe recipe = RewriteUtils.loadRecipe("CollectionRulesRecipes.ImmutableCollectionToArrayWithArrayRecipe");
        rewriteRun(
                spec -> spec.recipe(recipe)
                        .expectedCyclesThatMakeChanges(1).cycles(1)
                        .parser(JavaParser.fromJavaVersion().classpath(JavaParser.runtimeClasspath())),
                java(
                        """
                                import com.google.common.collect.ImmutableSet;
                                
                                class Test {
                                    Integer[] test() {
                                        return ImmutableSet.of(1).asList().toArray(new Integer[0]);
                                    }
                                }
                                """,
                        """
                                import com.google.common.collect.ImmutableSet;
                                
                                class Test {
                                    Integer[] test() {
                                        return ImmutableSet.of(1).toArray(new Integer[0]);
                                    }
                                }
                                """
                ));
    }

    @Test
    void testImmutableCollectionToArrayWithGeneratorRecipe() {
        Recipe recipe = RewriteUtils.loadRecipe("CollectionRulesRecipes.ImmutableCollectionToArrayWithGeneratorRecipe");
        rewriteRun(
                spec -> spec.recipe(recipe)
                        .expectedCyclesThatMakeChanges(1).cycles(1)
                        .parser(JavaParser.fromJavaVersion().classpath(JavaParser.runtimeClasspath())),
                java(
                        """
                                import com.google.common.collect.ImmutableSet;
                                
                                class Test {
                                    Integer[] test() {
                                        return ImmutableSet.of(1).asList().toArray(Integer[]::new);
                                    }
                                }
                                """,
                        """
                                import com.google.common.collect.ImmutableSet;
                                
                                class Test {
                                    Integer[] test() {
                                        return ImmutableSet.of(1).toArray(Integer[]::new);
                                    }
                                }
                                """
                ));
    }

    @Test
    void testImmutableCollectionToStringRecipe() {
        Recipe recipe = RewriteUtils.loadRecipe("CollectionRulesRecipes.ImmutableCollectionToStringRecipe");
        rewriteRun(
                spec -> spec.recipe(recipe)
                        .expectedCyclesThatMakeChanges(1).cycles(1)
                        .parser(JavaParser.fromJavaVersion().classpath(JavaParser.runtimeClasspath())),
                java(
                        """
                                import com.google.common.collect.ImmutableSet;
                                
                                class Test {
                                    String test() {
                                        return ImmutableSet.of(1).asList().toString();
                                    }
                                }
                                """,
                        """
                                import com.google.common.collect.ImmutableSet;
                                
                                class Test {
                                    String test() {
                                        return ImmutableSet.of(1).toString();
                                    }
                                }
                                """
                ));
    }

    @Test
    void testNewArrayListFromCollectionRecipe() {
        Recipe recipe = RewriteUtils.loadRecipe("CollectionRulesRecipes.NewArrayListFromCollectionRecipe");
        rewriteRun(
                spec -> spec.recipe(recipe)
                        .expectedCyclesThatMakeChanges(1).cycles(1)
                        .parser(JavaParser.fromJavaVersion().classpath(JavaParser.runtimeClasspath())),
                java(
                        """
                                import com.google.common.collect.ImmutableList;
                                import com.google.common.collect.Lists;
                                
                                import java.util.ArrayList;
                                
                                class Test {
                                    ArrayList<String> test() {
                                        return Lists.newArrayList(ImmutableList.of("foo"));
                                    }
                                }
                                """,
                        """
                                import com.google.common.collect.ImmutableList;
                                
                                import java.util.ArrayList;
                                
                                class Test {
                                    ArrayList<String> test() {
                                        return new ArrayList<>(ImmutableList.of("foo"));
                                    }
                                }
                                """
                ));
    }

    @Test
    void testOptionalFirstCollectionElementRecipe() {
        Recipe recipe = RewriteUtils.loadRecipe("CollectionRulesRecipes.OptionalFirstCollectionElementRecipe");
        rewriteRun(
                spec -> spec.recipe(recipe)
                        .expectedCyclesThatMakeChanges(1).cycles(1)
                        .parser(JavaParser.fromJavaVersion().classpath(JavaParser.runtimeClasspath())),
                java(
                        """
                                import com.google.common.collect.ImmutableList;
                                import com.google.common.collect.ImmutableSet;
                                import com.google.common.collect.ImmutableSortedSet;
                                
                                import java.util.Optional;
                                
                                class Test {
                                    ImmutableSet<Optional<Integer>> test() {
                                        return ImmutableSet.of(ImmutableSet.of(0).stream().findAny(), ImmutableSet.of(1).isEmpty() ? Optional.empty() : Optional.of(ImmutableSet.of(1).iterator().next()), ImmutableList.of(2).isEmpty() ? Optional.empty() : Optional.of(ImmutableList.of(2).get(0)), ImmutableSortedSet.of(3).isEmpty() ? Optional.empty() : Optional.of(ImmutableSortedSet.of(3).first()), !ImmutableSet.of(1).isEmpty() ? Optional.of(ImmutableSet.of(1).iterator().next()) : Optional.empty(), !ImmutableList.of(2).isEmpty() ? Optional.of(ImmutableList.of(2).get(0)) : Optional.empty(), !ImmutableSortedSet.of(3).isEmpty() ? Optional.of(ImmutableSortedSet.of(3).first()) : Optional.empty());
                                    }
                                }
                                """,
                        """
                                import com.google.common.collect.ImmutableList;
                                import com.google.common.collect.ImmutableSet;
                                import com.google.common.collect.ImmutableSortedSet;
                                
                                import java.util.Optional;
                                
                                class Test {
                                    ImmutableSet<Optional<Integer>> test() {
                                        return ImmutableSet.of(ImmutableSet.of(0).stream().findFirst(), ImmutableSet.of(1).stream().findFirst(), ImmutableList.of(2).stream().findFirst(), ImmutableSortedSet.of(3).stream().findFirst(), ImmutableSet.of(1).stream().findFirst(), ImmutableList.of(2).stream().findFirst(), ImmutableSortedSet.of(3).stream().findFirst());
                                    }
                                }
                                """
                ));
    }

    @Test
    void testOptionalFirstQueueElementRecipe() {
        Recipe recipe = RewriteUtils.loadRecipe("CollectionRulesRecipes.OptionalFirstQueueElementRecipe");
        rewriteRun(
                spec -> spec.recipe(recipe)
                        .expectedCyclesThatMakeChanges(1).cycles(1)
                        .parser(JavaParser.fromJavaVersion().classpath(JavaParser.runtimeClasspath())),
                java(
                        """
                                import com.google.common.collect.ImmutableSet;
                                
                                import java.util.LinkedList;
                                import java.util.Optional;
                                
                                class Test {
                                    ImmutableSet<Optional<String>> test() {
                                        return ImmutableSet.of(new LinkedList<String>().stream().findFirst(), new LinkedList<String>().isEmpty() ? Optional.empty() : Optional.of(new LinkedList<String>().peek()), new LinkedList<String>().isEmpty() ? Optional.empty() : Optional.ofNullable(new LinkedList<String>().peek()), !new LinkedList<String>().isEmpty() ? Optional.of(new LinkedList<String>().peek()) : Optional.empty(), !new LinkedList<String>().isEmpty() ? Optional.ofNullable(new LinkedList<String>().peek()) : Optional.empty());
                                    }
                                }
                                """,
                        """
                                import com.google.common.collect.ImmutableSet;
                                
                                import java.util.LinkedList;
                                import java.util.Optional;
                                
                                class Test {
                                    ImmutableSet<Optional<String>> test() {
                                        return ImmutableSet.of(Optional.ofNullable(new LinkedList<String>().peek()), Optional.ofNullable(new LinkedList<String>().peek()), Optional.ofNullable(new LinkedList<String>().peek()), Optional.ofNullable(new LinkedList<String>().peek()), Optional.ofNullable(new LinkedList<String>().peek()));
                                    }
                                }
                                """
                ));
    }

    @Test
    void testRemoveOptionalFirstNavigableSetElementRecipe() {
        Recipe recipe = RewriteUtils.loadRecipe("CollectionRulesRecipes.RemoveOptionalFirstNavigableSetElementRecipe");
        rewriteRun(
                spec -> spec.recipe(recipe)
                        .expectedCyclesThatMakeChanges(1).cycles(1)
                        .parser(JavaParser.fromJavaVersion().classpath(JavaParser.runtimeClasspath())),
                java(
                        """
                                import com.google.common.collect.ImmutableSet;
                                
                                import java.util.Optional;
                                import java.util.TreeSet;
                                
                                class Test {
                                    ImmutableSet<Optional<String>> test() {
                                        return ImmutableSet.of(new TreeSet<String>().isEmpty() ? Optional.empty() : Optional.of(new TreeSet<String>().pollFirst()), new TreeSet<String>().isEmpty() ? Optional.empty() : Optional.ofNullable(new TreeSet<String>().pollFirst()), !new TreeSet<String>().isEmpty() ? Optional.of(new TreeSet<String>().pollFirst()) : Optional.empty(), !new TreeSet<String>().isEmpty() ? Optional.ofNullable(new TreeSet<String>().pollFirst()) : Optional.empty());
                                    }
                                }
                                """,
                        """
                                import com.google.common.collect.ImmutableSet;
                                
                                import java.util.Optional;
                                import java.util.TreeSet;
                                
                                class Test {
                                    ImmutableSet<Optional<String>> test() {
                                        return ImmutableSet.of(Optional.ofNullable(new TreeSet<String>().pollFirst()), Optional.ofNullable(new TreeSet<String>().pollFirst()), Optional.ofNullable(new TreeSet<String>().pollFirst()), Optional.ofNullable(new TreeSet<String>().pollFirst()));
                                    }
                                }
                                """
                ));
    }

    @Test
    void testRemoveOptionalFirstQueueElementRecipe() {
        Recipe recipe = RewriteUtils.loadRecipe("CollectionRulesRecipes.RemoveOptionalFirstQueueElementRecipe");
        rewriteRun(
                spec -> spec.recipe(recipe)
                        .expectedCyclesThatMakeChanges(1).cycles(1)
                        .parser(JavaParser.fromJavaVersion().classpath(JavaParser.runtimeClasspath())),
                java(
                        """
                                import com.google.common.collect.ImmutableSet;
                                
                                import java.util.LinkedList;
                                import java.util.Optional;
                                
                                class Test {
                                    ImmutableSet<Optional<String>> test() {
                                        return ImmutableSet.of(new LinkedList<String>().isEmpty() ? Optional.empty() : Optional.of(new LinkedList<String>().poll()), new LinkedList<String>().isEmpty() ? Optional.empty() : Optional.of(new LinkedList<String>().remove()), new LinkedList<String>().isEmpty() ? Optional.empty() : Optional.ofNullable(new LinkedList<String>().poll()), new LinkedList<String>().isEmpty() ? Optional.empty() : Optional.ofNullable(new LinkedList<String>().remove()), !new LinkedList<String>().isEmpty() ? Optional.of(new LinkedList<String>().poll()) : Optional.empty(), !new LinkedList<String>().isEmpty() ? Optional.of(new LinkedList<String>().remove()) : Optional.empty(), !new LinkedList<String>().isEmpty() ? Optional.ofNullable(new LinkedList<String>().poll()) : Optional.empty(), !new LinkedList<String>().isEmpty() ? Optional.ofNullable(new LinkedList<String>().remove()) : Optional.empty());
                                    }
                                }
                                """,
                        """
                                import com.google.common.collect.ImmutableSet;
                                
                                import java.util.LinkedList;
                                import java.util.Optional;
                                
                                class Test {
                                    ImmutableSet<Optional<String>> test() {
                                        return ImmutableSet.of(Optional.ofNullable(new LinkedList<String>().poll()), Optional.ofNullable(new LinkedList<String>().poll()), Optional.ofNullable(new LinkedList<String>().poll()), Optional.ofNullable(new LinkedList<String>().poll()), Optional.ofNullable(new LinkedList<String>().poll()), Optional.ofNullable(new LinkedList<String>().poll()), Optional.ofNullable(new LinkedList<String>().poll()), Optional.ofNullable(new LinkedList<String>().poll()));
                                    }
                                }
                                """
                ));
    }

    @Test
    void testSetStreamRecipe() {
        Recipe recipe = RewriteUtils.loadRecipe("CollectionRulesRecipes.SetStreamRecipe");
        rewriteRun(
                spec -> spec.recipe(recipe)
                        .expectedCyclesThatMakeChanges(1).cycles(1)
                        .parser(JavaParser.fromJavaVersion().classpath(JavaParser.runtimeClasspath())),
                java(
                        """
                                import com.google.common.collect.ImmutableSet;
                                
                                import java.util.stream.Stream;
                                
                                class Test {
                                    Stream<Integer> test() {
                                        return ImmutableSet.of(1).stream().distinct();
                                    }
                                }
                                """,
                        """
                                import com.google.common.collect.ImmutableSet;
                                
                                import java.util.stream.Stream;
                                
                                class Test {
                                    Stream<Integer> test() {
                                        return ImmutableSet.of(1).stream();
                                    }
                                }
                                """
                ));
    }
}
