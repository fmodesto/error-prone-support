package tech.picnic.errorprone.refasterrules.rewrite;

import org.junit.jupiter.api.Test;
import org.openrewrite.Recipe;
import org.openrewrite.java.JavaParser;
import org.openrewrite.test.RewriteTest;

import static org.openrewrite.java.Assertions.java;

final class ComparatorRulesRecipesTest implements RewriteTest {

    @Test
    void testCollectionsMaxRecipe() {
        Recipe recipe = RewriteUtils.loadRecipe("ComparatorRulesRecipes.CollectionsMaxRecipe");
        rewriteRun(
                spec -> spec.recipe(recipe)
                        .expectedCyclesThatMakeChanges(1).cycles(1)
                        .parser(JavaParser.fromJavaVersion().classpath(JavaParser.runtimeClasspath())),
                java(
                        """
                                import com.google.common.collect.ImmutableList;
                                import com.google.common.collect.ImmutableSet;
                                
                                import java.util.Collections;
                                import java.util.Comparator;
                                
                                class Test {
                                    ImmutableSet<String> test() {
                                        return ImmutableSet.of(Collections.max(ImmutableList.of("foo"), Comparator.naturalOrder()), Collections.min(ImmutableList.of("bar"), Comparator.reverseOrder()));
                                    }
                                }
                                """,
                        """
                                import com.google.common.collect.ImmutableList;
                                import com.google.common.collect.ImmutableSet;
                                
                                import java.util.Collections;
                                
                                class Test {
                                    ImmutableSet<String> test() {
                                        return ImmutableSet.of(Collections.max(ImmutableList.of("foo")), Collections.max(ImmutableList.of("bar")));
                                    }
                                }
                                """
                ));
    }

    @Test
    void testCollectionsMaxWithComparatorRecipe() {
        Recipe recipe = RewriteUtils.loadRecipe("ComparatorRulesRecipes.CollectionsMaxWithComparatorRecipe");
        rewriteRun(
                spec -> spec.recipe(recipe)
                        .expectedCyclesThatMakeChanges(1).cycles(1)
                        .parser(JavaParser.fromJavaVersion().classpath(JavaParser.runtimeClasspath())),
                java(
                        """
                                import com.google.common.collect.ImmutableSet;
                                
                                import java.util.Comparator;
                                
                                class Test {
                                    String test() {
                                        return ImmutableSet.of("foo", "bar").stream().max(Comparator.naturalOrder()).orElseThrow();
                                    }
                                }
                                """,
                        """
                                import com.google.common.collect.ImmutableSet;
                                
                                import java.util.Collections;
                                import java.util.Comparator;
                                
                                class Test {
                                    String test() {
                                        return Collections.max(ImmutableSet.of("foo", "bar"), Comparator.naturalOrder());
                                    }
                                }
                                """
                ));
    }

    @Test
    void testCollectionsMinRecipe() {
        Recipe recipe = RewriteUtils.loadRecipe("ComparatorRulesRecipes.CollectionsMinRecipe");
        rewriteRun(
                spec -> spec.recipe(recipe)
                        .expectedCyclesThatMakeChanges(1).cycles(1)
                        .parser(JavaParser.fromJavaVersion().classpath(JavaParser.runtimeClasspath())),
                java(
                        """
                                import com.google.common.collect.ImmutableList;
                                import com.google.common.collect.ImmutableSet;
                                
                                import java.util.Collections;
                                import java.util.Comparator;
                                
                                class Test {
                                    ImmutableSet<String> test() {
                                        return ImmutableSet.of(Collections.min(ImmutableList.of("foo"), Comparator.naturalOrder()), Collections.max(ImmutableList.of("bar"), Comparator.reverseOrder()));
                                    }
                                }
                                """,
                        """
                                import com.google.common.collect.ImmutableList;
                                import com.google.common.collect.ImmutableSet;
                                
                                import java.util.Collections;
                                
                                class Test {
                                    ImmutableSet<String> test() {
                                        return ImmutableSet.of(Collections.min(ImmutableList.of("foo")), Collections.min(ImmutableList.of("bar")));
                                    }
                                }
                                """
                ));
    }

    @Test
    void testCollectionsMinWithComparatorRecipe() {
        Recipe recipe = RewriteUtils.loadRecipe("ComparatorRulesRecipes.CollectionsMinWithComparatorRecipe");
        rewriteRun(
                spec -> spec.recipe(recipe)
                        .expectedCyclesThatMakeChanges(1).cycles(1)
                        .parser(JavaParser.fromJavaVersion().classpath(JavaParser.runtimeClasspath())),
                java(
                        """
                                import com.google.common.collect.ImmutableSet;
                                
                                import java.util.Comparator;
                                
                                class Test {
                                    String test() {
                                        return ImmutableSet.of("foo", "bar").stream().min(Comparator.naturalOrder()).orElseThrow();
                                    }
                                }
                                """,
                        """
                                import com.google.common.collect.ImmutableSet;
                                
                                import java.util.Collections;
                                import java.util.Comparator;
                                
                                class Test {
                                    String test() {
                                        return Collections.min(ImmutableSet.of("foo", "bar"), Comparator.naturalOrder());
                                    }
                                }
                                """
                ));
    }

    @Test
    void testCollectionsSortRecipe() {
        Recipe recipe = RewriteUtils.loadRecipe("ComparatorRulesRecipes.CollectionsSortRecipe");
        rewriteRun(
                spec -> spec.recipe(recipe)
                        .expectedCyclesThatMakeChanges(1).cycles(1)
                        .parser(JavaParser.fromJavaVersion().classpath(JavaParser.runtimeClasspath())),
                java(
                        """
                                import com.google.common.collect.ImmutableList;
                                
                                import java.util.Collections;
                                import java.util.Comparator;
                                
                                class Test {
                                    void test() {
                                        Collections.sort(ImmutableList.of("foo", "bar"), Comparator.naturalOrder());
                                    }
                                }
                                """,
                        """
                                import com.google.common.collect.ImmutableList;
                                
                                import java.util.Collections;
                                
                                class Test {
                                    void test() {
                                        Collections.sort(ImmutableList.of("foo", "bar"));
                                    }
                                }
                                """
                ));
    }

    @Test
    void testComparatorsMaxRecipe() {
        Recipe recipe = RewriteUtils.loadRecipe("ComparatorRulesRecipes.ComparatorsMaxRecipe");
        rewriteRun(
                spec -> spec.recipe(recipe)
                        .expectedCyclesThatMakeChanges(1).cycles(1)
                        .parser(JavaParser.fromJavaVersion().classpath(JavaParser.runtimeClasspath())),
                java(
                        """
                                import java.util.Comparator;
                                import java.util.function.BinaryOperator;
                                
                                class Test {
                                    BinaryOperator<String> test() {
                                        return BinaryOperator.maxBy(Comparator.naturalOrder());
                                    }
                                }
                                """,
                        """
                                import com.google.common.collect.Comparators;
                                
                                import java.util.function.BinaryOperator;
                                
                                class Test {
                                    BinaryOperator<String> test() {
                                        return Comparators::max;
                                    }
                                }
                                """
                ));
    }

    @Test
    void testComparatorsMinRecipe() {
        Recipe recipe = RewriteUtils.loadRecipe("ComparatorRulesRecipes.ComparatorsMinRecipe");
        rewriteRun(
                spec -> spec.recipe(recipe)
                        .expectedCyclesThatMakeChanges(1).cycles(1)
                        .parser(JavaParser.fromJavaVersion().classpath(JavaParser.runtimeClasspath())),
                java(
                        """
                                import java.util.Comparator;
                                import java.util.function.BinaryOperator;
                                
                                class Test {
                                    BinaryOperator<String> test() {
                                        return BinaryOperator.minBy(Comparator.naturalOrder());
                                    }
                                }
                                """,
                        """
                                import com.google.common.collect.Comparators;
                                
                                import java.util.function.BinaryOperator;
                                
                                class Test {
                                    BinaryOperator<String> test() {
                                        return Comparators::min;
                                    }
                                }
                                """
                ));
    }

    @Test
    void testCompareToRecipe() {
        Recipe recipe = RewriteUtils.loadRecipe("ComparatorRulesRecipes.CompareToRecipe");
        rewriteRun(
                spec -> spec.recipe(recipe)
                        .expectedCyclesThatMakeChanges(1).cycles(1)
                        .parser(JavaParser.fromJavaVersion().classpath(JavaParser.runtimeClasspath())),
                java(
                        """
                                import com.google.common.collect.ImmutableSet;
                                
                                import java.util.Comparator;
                                
                                class Test {
                                    ImmutableSet<Integer> test() {
                                        return ImmutableSet.of(Comparator.<String>naturalOrder().compare("foo", "bar"), Comparator.<String>reverseOrder().compare("baz", "qux"));
                                    }
                                }
                                """,
                        """
                                import com.google.common.collect.ImmutableSet;
                                
                                class Test {
                                    ImmutableSet<Integer> test() {
                                        return ImmutableSet.of("foo".compareTo("bar"), "qux".compareTo("baz"));
                                    }
                                }
                                """
                ));
    }

    @Test
    void testComparingEnumRecipe() {
        Recipe recipe = RewriteUtils.loadRecipe("ComparatorRulesRecipes.ComparingEnumRecipe");
        rewriteRun(
                spec -> spec.recipe(recipe)
                        .expectedCyclesThatMakeChanges(1).cycles(1)
                        .parser(JavaParser.fromJavaVersion().classpath(JavaParser.runtimeClasspath())),
                java(
                        """
                                import java.math.RoundingMode;
                                import java.util.Comparator;
                                
                                class Test {
                                    Comparator<String> test() {
                                        return Comparator.comparingInt(s -> RoundingMode.valueOf(s).ordinal());
                                    }
                                }
                                """,
                        """
                                import java.math.RoundingMode;
                                import java.util.Comparator;
                                
                                class Test {
                                    Comparator<String> test() {
                                        return Comparator.comparing(s -> RoundingMode.valueOf(s));
                                    }
                                }
                                """
                ));
    }

    @Test
    void testCustomComparatorRecipe() {
        Recipe recipe = RewriteUtils.loadRecipe("ComparatorRulesRecipes.CustomComparatorRecipe");
        rewriteRun(
                spec -> spec.recipe(recipe)
                        .expectedCyclesThatMakeChanges(1).cycles(1)
                        .parser(JavaParser.fromJavaVersion().classpath(JavaParser.runtimeClasspath())),
                java(
                        """
                                import com.google.common.collect.ImmutableSet;
                                
                                import java.util.Comparator;
                                import java.util.function.Function;
                                
                                class Test {
                                    ImmutableSet<Comparator<String>> test() {
                                        return ImmutableSet.of(Comparator.comparing(Function.identity(), Comparator.comparingInt(String::length)), Comparator.comparing(s -> s, Comparator.comparingInt(String::length)), Comparator.comparing(s -> "foo", Comparator.comparingInt(String::length)));
                                    }
                                }
                                """,
                        """
                                import com.google.common.collect.ImmutableSet;
                                
                                import java.util.Comparator;
                                
                                class Test {
                                    ImmutableSet<Comparator<String>> test() {
                                        return ImmutableSet.of(Comparator.comparingInt(String::length), Comparator.comparingInt(String::length), Comparator.comparing(s -> "foo", Comparator.comparingInt(String::length)));
                                    }
                                }
                                """
                ));
    }

    @Test
    void testIsLessThanRecipe() {
        Recipe recipe = RewriteUtils.loadRecipe("ComparatorRulesRecipes.IsLessThanRecipe");
        rewriteRun(
                spec -> spec.recipe(recipe)
                        .expectedCyclesThatMakeChanges(1).cycles(1)
                        .parser(JavaParser.fromJavaVersion().classpath(JavaParser.runtimeClasspath())),
                java(
                        """
                                import com.google.common.collect.ImmutableSet;
                                
                                import java.math.RoundingMode;
                                
                                class Test {
                                    ImmutableSet<Boolean> test() {
                                        return ImmutableSet.of(RoundingMode.UP.ordinal() < RoundingMode.DOWN.ordinal(), RoundingMode.UP.ordinal() >= RoundingMode.DOWN.ordinal());
                                    }
                                }
                                """,
                        """
                                import com.google.common.collect.ImmutableSet;
                                
                                import java.math.RoundingMode;
                                
                                class Test {
                                    ImmutableSet<Boolean> test() {
                                        return ImmutableSet.of(RoundingMode.UP.compareTo(RoundingMode.DOWN) < 0, RoundingMode.UP.compareTo(RoundingMode.DOWN) >= 0);
                                    }
                                }
                                """
                ));
    }

    @Test
    void testIsLessThanOrEqualToRecipe() {
        Recipe recipe = RewriteUtils.loadRecipe("ComparatorRulesRecipes.IsLessThanOrEqualToRecipe");
        rewriteRun(
                spec -> spec.recipe(recipe)
                        .expectedCyclesThatMakeChanges(1).cycles(1)
                        .parser(JavaParser.fromJavaVersion().classpath(JavaParser.runtimeClasspath())),
                java(
                        """
                                import com.google.common.collect.ImmutableSet;
                                
                                import java.math.RoundingMode;
                                
                                class Test {
                                    ImmutableSet<Boolean> test() {
                                        return ImmutableSet.of(RoundingMode.UP.ordinal() <= RoundingMode.DOWN.ordinal(), RoundingMode.UP.ordinal() > RoundingMode.DOWN.ordinal());
                                    }
                                }
                                """,
                        """
                                import com.google.common.collect.ImmutableSet;
                                
                                import java.math.RoundingMode;
                                
                                class Test {
                                    ImmutableSet<Boolean> test() {
                                        return ImmutableSet.of(RoundingMode.UP.compareTo(RoundingMode.DOWN) <= 0, RoundingMode.UP.compareTo(RoundingMode.DOWN) > 0);
                                    }
                                }
                                """
                ));
    }

    @Test
    void testMaxByNaturalOrderRecipe() {
        Recipe recipe = RewriteUtils.loadRecipe("ComparatorRulesRecipes.MaxByNaturalOrderRecipe");
        rewriteRun(
                spec -> spec.recipe(recipe)
                        .expectedCyclesThatMakeChanges(1).cycles(1)
                        .parser(JavaParser.fromJavaVersion().classpath(JavaParser.runtimeClasspath())),
                java(
                        """
                                import java.util.Comparator;
                                import java.util.Optional;
                                import java.util.stream.Collector;
                                import java.util.stream.Collectors;
                                
                                class Test {
                                    Collector<Integer, ?, Optional<Integer>> test() {
                                        return Collectors.minBy(Comparator.reverseOrder());
                                    }
                                }
                                """,
                        """
                                import java.util.Comparator;
                                import java.util.Optional;
                                import java.util.stream.Collector;
                                import java.util.stream.Collectors;
                                
                                class Test {
                                    Collector<Integer, ?, Optional<Integer>> test() {
                                        return Collectors.maxBy(Comparator.naturalOrder());
                                    }
                                }
                                """
                ));
    }

    @Test
    void testMaxOfArrayRecipe() {
        Recipe recipe = RewriteUtils.loadRecipe("ComparatorRulesRecipes.MaxOfArrayRecipe");
        rewriteRun(
                spec -> spec.recipe(recipe)
                        .expectedCyclesThatMakeChanges(1).cycles(1)
                        .parser(JavaParser.fromJavaVersion().classpath(JavaParser.runtimeClasspath())),
                java(
                        """
                                import java.util.Arrays;
                                import java.util.Comparator;
                                
                                class Test {
                                    String test() {
                                        return Arrays.stream(new String[0]).max(Comparator.naturalOrder()).orElseThrow();
                                    }
                                }
                                """,
                        """
                                import java.util.Arrays;
                                import java.util.Collections;
                                import java.util.Comparator;
                                
                                class Test {
                                    String test() {
                                        return Collections.max(Arrays.asList(new String[0]), Comparator.naturalOrder());
                                    }
                                }
                                """
                ));
    }

    @Test
    void testMaxOfPairCustomOrderRecipe() {
        Recipe recipe = RewriteUtils.loadRecipe("ComparatorRulesRecipes.MaxOfPairCustomOrderRecipe");
        rewriteRun(
                spec -> spec.recipe(recipe)
                        .expectedCyclesThatMakeChanges(1).cycles(1)
                        .parser(JavaParser.fromJavaVersion().classpath(JavaParser.runtimeClasspath())),
                java(
                        """
                                import com.google.common.collect.ImmutableList;
                                import com.google.common.collect.ImmutableSet;
                                
                                import java.util.Arrays;
                                import java.util.Collections;
                                import java.util.Comparator;
                                
                                class Test {
                                    ImmutableSet<Object> test() {
                                        return ImmutableSet.of(Comparator.comparingInt(String::length).compare("a", "b") >= 0 ? "a" : "b", Comparator.comparingInt(String::length).compare("a", "b") < 0 ? "b" : "a", Comparator.comparingInt(String::length).compare("a", "b") > 0 ? "a" : "b", Comparator.comparingInt(String::length).compare("a", "b") <= 0 ? "b" : "a", Collections.max(Arrays.asList("a", "b"), (a, b) -> -1), Collections.max(ImmutableList.of("a", "b"), (a, b) -> 0), Collections.max(ImmutableSet.of("a", "b"), (a, b) -> 1));
                                    }
                                }
                                """,
                        """
                                import com.google.common.collect.Comparators;
                                import com.google.common.collect.ImmutableSet;
                                
                                import java.util.Comparator;
                                
                                class Test {
                                    ImmutableSet<Object> test() {
                                        return ImmutableSet.of(Comparators.max("a", "b", Comparator.comparingInt(String::length)), Comparators.max("a", "b", Comparator.comparingInt(String::length)), Comparators.max("b", "a", Comparator.comparingInt(String::length)), Comparators.max("b", "a", Comparator.comparingInt(String::length)), Comparators.max("a", "b", (a, b) -> -1), Comparators.max("a", "b", (a, b) -> 0), Comparators.max("a", "b", (a, b) -> 1));
                                    }
                                }
                                """
                ));
    }

    @Test
    void testMaxOfPairNaturalOrderRecipe() {
        Recipe recipe = RewriteUtils.loadRecipe("ComparatorRulesRecipes.MaxOfPairNaturalOrderRecipe");
        rewriteRun(
                spec -> spec.recipe(recipe)
                        .expectedCyclesThatMakeChanges(1).cycles(1)
                        .parser(JavaParser.fromJavaVersion().classpath(JavaParser.runtimeClasspath())),
                java(
                        """
                                import com.google.common.collect.Comparators;
                                import com.google.common.collect.ImmutableList;
                                import com.google.common.collect.ImmutableSet;
                                
                                import java.util.Arrays;
                                import java.util.Collections;
                                import java.util.Comparator;
                                
                                class Test {
                                    ImmutableSet<String> test() {
                                        return ImmutableSet.of("a".compareTo("b") >= 0 ? "a" : "b", "a".compareTo("b") < 0 ? "b" : "a", "a".compareTo("b") > 0 ? "a" : "b", "a".compareTo("b") <= 0 ? "b" : "a", Comparators.max("a", "b", Comparator.naturalOrder()), Comparators.min("a", "b", Comparator.reverseOrder()), Collections.max(Arrays.asList("a", "b")), Collections.max(ImmutableList.of("a", "b")), Collections.max(ImmutableSet.of("a", "b")));
                                    }
                                }
                                """,
                        """
                                import com.google.common.collect.Comparators;
                                import com.google.common.collect.ImmutableSet;
                                
                                class Test {
                                    ImmutableSet<String> test() {
                                        return ImmutableSet.of(Comparators.max("a", "b"), Comparators.max("a", "b"), Comparators.max("b", "a"), Comparators.max("b", "a"), Comparators.max("a", "b"), Comparators.max("a", "b"), Comparators.max("a", "b"), Comparators.max("a", "b"), Comparators.max("a", "b"));
                                    }
                                }
                                """
                ));
    }

    @Test
    void testMaxOfVarargsRecipe() {
        Recipe recipe = RewriteUtils.loadRecipe("ComparatorRulesRecipes.MaxOfVarargsRecipe");
        rewriteRun(
                spec -> spec.recipe(recipe)
                        .expectedCyclesThatMakeChanges(1).cycles(1)
                        .parser(JavaParser.fromJavaVersion().classpath(JavaParser.runtimeClasspath())),
                java(
                        """
                                import java.util.Comparator;
                                import java.util.stream.Stream;
                                
                                class Test {
                                    int test() {
                                        return Stream.of(1, 2).max(Comparator.naturalOrder()).orElseThrow();
                                    }
                                }
                                """,
                        """
                                import java.util.Arrays;
                                import java.util.Collections;
                                import java.util.Comparator;
                                
                                class Test {
                                    int test() {
                                        return Collections.max(Arrays.asList(1, 2), Comparator.naturalOrder());
                                    }
                                }
                                """
                ));
    }

    @Test
    void testMinByNaturalOrderRecipe() {
        Recipe recipe = RewriteUtils.loadRecipe("ComparatorRulesRecipes.MinByNaturalOrderRecipe");
        rewriteRun(
                spec -> spec.recipe(recipe)
                        .expectedCyclesThatMakeChanges(1).cycles(1)
                        .parser(JavaParser.fromJavaVersion().classpath(JavaParser.runtimeClasspath())),
                java(
                        """
                                import java.util.Comparator;
                                import java.util.Optional;
                                import java.util.stream.Collector;
                                import java.util.stream.Collectors;
                                
                                class Test {
                                    Collector<Integer, ?, Optional<Integer>> test() {
                                        return Collectors.maxBy(Comparator.reverseOrder());
                                    }
                                }
                                """,
                        """
                                import java.util.Comparator;
                                import java.util.Optional;
                                import java.util.stream.Collector;
                                import java.util.stream.Collectors;
                                
                                class Test {
                                    Collector<Integer, ?, Optional<Integer>> test() {
                                        return Collectors.minBy(Comparator.naturalOrder());
                                    }
                                }
                                """
                ));
    }

    @Test
    void testMinOfArrayRecipe() {
        Recipe recipe = RewriteUtils.loadRecipe("ComparatorRulesRecipes.MinOfArrayRecipe");
        rewriteRun(
                spec -> spec.recipe(recipe)
                        .expectedCyclesThatMakeChanges(1).cycles(1)
                        .parser(JavaParser.fromJavaVersion().classpath(JavaParser.runtimeClasspath())),
                java(
                        """
                                import java.util.Arrays;
                                import java.util.Comparator;
                                
                                class Test {
                                    String test() {
                                        return Arrays.stream(new String[0]).min(Comparator.naturalOrder()).orElseThrow();
                                    }
                                }
                                """,
                        """
                                import java.util.Arrays;
                                import java.util.Collections;
                                import java.util.Comparator;
                                
                                class Test {
                                    String test() {
                                        return Collections.min(Arrays.asList(new String[0]), Comparator.naturalOrder());
                                    }
                                }
                                """
                ));
    }

    @Test
    void testMinOfPairCustomOrderRecipe() {
        Recipe recipe = RewriteUtils.loadRecipe("ComparatorRulesRecipes.MinOfPairCustomOrderRecipe");
        rewriteRun(
                spec -> spec.recipe(recipe)
                        .expectedCyclesThatMakeChanges(1).cycles(1)
                        .parser(JavaParser.fromJavaVersion().classpath(JavaParser.runtimeClasspath())),
                java(
                        """
                                import com.google.common.collect.ImmutableList;
                                import com.google.common.collect.ImmutableSet;
                                
                                import java.util.Arrays;
                                import java.util.Collections;
                                import java.util.Comparator;
                                
                                class Test {
                                    ImmutableSet<Object> test() {
                                        return ImmutableSet.of(Comparator.comparingInt(String::length).compare("a", "b") <= 0 ? "a" : "b", Comparator.comparingInt(String::length).compare("a", "b") > 0 ? "b" : "a", Comparator.comparingInt(String::length).compare("a", "b") < 0 ? "a" : "b", Comparator.comparingInt(String::length).compare("a", "b") >= 0 ? "b" : "a", Collections.min(Arrays.asList("a", "b"), (a, b) -> -1), Collections.min(ImmutableList.of("a", "b"), (a, b) -> 0), Collections.min(ImmutableSet.of("a", "b"), (a, b) -> 1));
                                    }
                                }
                                """,
                        """
                                import com.google.common.collect.Comparators;
                                import com.google.common.collect.ImmutableSet;
                                
                                import java.util.Comparator;
                                
                                class Test {
                                    ImmutableSet<Object> test() {
                                        return ImmutableSet.of(Comparators.min("a", "b", Comparator.comparingInt(String::length)), Comparators.min("a", "b", Comparator.comparingInt(String::length)), Comparators.min("b", "a", Comparator.comparingInt(String::length)), Comparators.min("b", "a", Comparator.comparingInt(String::length)), Comparators.min("a", "b", (a, b) -> -1), Comparators.min("a", "b", (a, b) -> 0), Comparators.min("a", "b", (a, b) -> 1));
                                    }
                                }
                                """
                ));
    }

    @Test
    void testMinOfPairNaturalOrderRecipe() {
        Recipe recipe = RewriteUtils.loadRecipe("ComparatorRulesRecipes.MinOfPairNaturalOrderRecipe");
        rewriteRun(
                spec -> spec.recipe(recipe)
                        .expectedCyclesThatMakeChanges(1).cycles(1)
                        .parser(JavaParser.fromJavaVersion().classpath(JavaParser.runtimeClasspath())),
                java(
                        """
                                import com.google.common.collect.Comparators;
                                import com.google.common.collect.ImmutableList;
                                import com.google.common.collect.ImmutableSet;
                                
                                import java.util.Arrays;
                                import java.util.Collections;
                                import java.util.Comparator;
                                
                                class Test {
                                    ImmutableSet<String> test() {
                                        return ImmutableSet.of("a".compareTo("b") <= 0 ? "a" : "b", "a".compareTo("b") > 0 ? "b" : "a", "a".compareTo("b") < 0 ? "a" : "b", "a".compareTo("b") >= 0 ? "b" : "a", Comparators.min("a", "b", Comparator.naturalOrder()), Comparators.max("a", "b", Comparator.reverseOrder()), Collections.min(Arrays.asList("a", "b")), Collections.min(ImmutableList.of("a", "b")), Collections.min(ImmutableSet.of("a", "b")));
                                    }
                                }
                                """,
                        """
                                import com.google.common.collect.Comparators;
                                import com.google.common.collect.ImmutableSet;
                                
                                class Test {
                                    ImmutableSet<String> test() {
                                        return ImmutableSet.of(Comparators.min("a", "b"), Comparators.min("a", "b"), Comparators.min("b", "a"), Comparators.min("b", "a"), Comparators.min("a", "b"), Comparators.min("a", "b"), Comparators.min("a", "b"), Comparators.min("a", "b"), Comparators.min("a", "b"));
                                    }
                                }
                                """
                ));
    }

    @Test
    void testMinOfVarargsRecipe() {
        Recipe recipe = RewriteUtils.loadRecipe("ComparatorRulesRecipes.MinOfVarargsRecipe");
        rewriteRun(
                spec -> spec.recipe(recipe)
                        .expectedCyclesThatMakeChanges(1).cycles(1)
                        .parser(JavaParser.fromJavaVersion().classpath(JavaParser.runtimeClasspath())),
                java(
                        """
                                import java.util.Comparator;
                                import java.util.stream.Stream;
                                
                                class Test {
                                    int test() {
                                        return Stream.of(1, 2).min(Comparator.naturalOrder()).orElseThrow();
                                    }
                                }
                                """,
                        """
                                import java.util.Arrays;
                                import java.util.Collections;
                                import java.util.Comparator;
                                
                                class Test {
                                    int test() {
                                        return Collections.min(Arrays.asList(1, 2), Comparator.naturalOrder());
                                    }
                                }
                                """
                ));
    }

    @Test
    void testNaturalOrderRecipe() {
        Recipe recipe = RewriteUtils.loadRecipe("ComparatorRulesRecipes.NaturalOrderRecipe");
        rewriteRun(
                spec -> spec.recipe(recipe)
                        .expectedCyclesThatMakeChanges(1).cycles(1)
                        .parser(JavaParser.fromJavaVersion().classpath(JavaParser.runtimeClasspath())),
                java(
                        """
                                import com.google.common.collect.ImmutableSet;
                                
                                import java.util.Collections;
                                import java.util.Comparator;
                                import java.util.function.Function;
                                
                                class Test {
                                    ImmutableSet<Comparator<String>> test() {
                                        return ImmutableSet.of(String::compareTo, Comparator.comparing(Function.identity()), Comparator.comparing(s -> s), Comparator.comparing(s -> 0), Collections.<String>reverseOrder(Comparator.reverseOrder()), Comparator.<String>reverseOrder().reversed());
                                    }
                                }
                                """,
                        """
                                import com.google.common.collect.ImmutableSet;
                                
                                import java.util.Comparator;
                                
                                class Test {
                                    ImmutableSet<Comparator<String>> test() {
                                        return ImmutableSet.of(Comparator.naturalOrder(), Comparator.naturalOrder(), Comparator.naturalOrder(), Comparator.comparing(s -> 0), Comparator.naturalOrder(), Comparator.naturalOrder());
                                    }
                                }
                                """
                ));
    }

    @Test
    void testReverseOrderRecipe() {
        Recipe recipe = RewriteUtils.loadRecipe("ComparatorRulesRecipes.ReverseOrderRecipe");
        rewriteRun(
                spec -> spec.recipe(recipe)
                        .expectedCyclesThatMakeChanges(1).cycles(1)
                        .parser(JavaParser.fromJavaVersion().classpath(JavaParser.runtimeClasspath())),
                java(
                        """
                                import com.google.common.collect.ImmutableSet;
                                
                                import java.util.Collections;
                                import java.util.Comparator;
                                
                                class Test {
                                    ImmutableSet<Comparator<String>> test() {
                                        return ImmutableSet.of(Collections.reverseOrder(), Collections.<String>reverseOrder(Comparator.naturalOrder()), Comparator.<String>naturalOrder().reversed());
                                    }
                                }
                                """,
                        """
                                import com.google.common.collect.ImmutableSet;
                                
                                import java.util.Comparator;
                                
                                class Test {
                                    ImmutableSet<Comparator<String>> test() {
                                        return ImmutableSet.of(Comparator.reverseOrder(), Comparator.reverseOrder(), Comparator.reverseOrder());
                                    }
                                }
                                """
                ));
    }

    @Test
    void testThenComparingRecipe() {
        Recipe recipe = RewriteUtils.loadRecipe("ComparatorRulesRecipes.ThenComparingRecipe");
        rewriteRun(
                spec -> spec.recipe(recipe)
                        .expectedCyclesThatMakeChanges(1).cycles(1)
                        .parser(JavaParser.fromJavaVersion().classpath(JavaParser.runtimeClasspath())),
                java(
                        """
                                import java.util.Comparator;
                                import java.util.function.Function;
                                
                                class Test {
                                    Comparator<String> test() {
                                        return Comparator.<String>naturalOrder().thenComparing(Comparator.comparing(String::isEmpty));
                                    }
                                }
                                """,
                        """
                                import java.util.Comparator;
                                import java.util.function.Function;
                                
                                class Test {
                                    Comparator<String> test() {
                                        return Comparator.<String>naturalOrder().thenComparing(String::isEmpty);
                                    }
                                }
                                """
                ));
    }

    @Test
    void testThenComparingCustomRecipe() {
        Recipe recipe = RewriteUtils.loadRecipe("ComparatorRulesRecipes.ThenComparingCustomRecipe");
        rewriteRun(
                spec -> spec.recipe(recipe)
                        .expectedCyclesThatMakeChanges(1).cycles(1)
                        .parser(JavaParser.fromJavaVersion().classpath(JavaParser.runtimeClasspath())),
                java(
                        """
                                import java.util.Comparator;
                                import java.util.function.Function;
                                
                                class Test {
                                    Comparator<String> test() {
                                        return Comparator.<String>naturalOrder().thenComparing(Comparator.comparing(String::isEmpty, Comparator.reverseOrder()));
                                    }
                                }
                                """,
                        """
                                import java.util.Comparator;
                                import java.util.function.Function;
                                
                                class Test {
                                    Comparator<String> test() {
                                        return Comparator.<String>naturalOrder().thenComparing(String::isEmpty, Comparator.reverseOrder());
                                    }
                                }
                                """
                ));
    }

    @Test
    void testThenComparingCustomReversedRecipe() {
        Recipe recipe = RewriteUtils.loadRecipe("ComparatorRulesRecipes.ThenComparingCustomReversedRecipe");
        rewriteRun(
                spec -> spec.recipe(recipe)
                        .expectedCyclesThatMakeChanges(1).cycles(1)
                        .parser(JavaParser.fromJavaVersion().classpath(JavaParser.runtimeClasspath())),
                java(
                        """
                                import java.util.Comparator;
                                import java.util.function.Function;
                                
                                class Test {
                                    Comparator<String> test() {
                                        return Comparator.<String>naturalOrder().thenComparing(Comparator.comparing(String::isEmpty, Comparator.<Boolean>reverseOrder()).reversed());
                                    }
                                }
                                """,
                        """
                                import java.util.Comparator;
                                import java.util.function.Function;
                                
                                class Test {
                                    Comparator<String> test() {
                                        return Comparator.<String>naturalOrder().thenComparing(String::isEmpty, Comparator.<Boolean>reverseOrder().reversed());
                                    }
                                }
                                """
                ));
    }

    @Test
    void testThenComparingDoubleRecipe() {
        Recipe recipe = RewriteUtils.loadRecipe("ComparatorRulesRecipes.ThenComparingDoubleRecipe");
        rewriteRun(
                spec -> spec.recipe(recipe)
                        .expectedCyclesThatMakeChanges(1).cycles(1)
                        .parser(JavaParser.fromJavaVersion().classpath(JavaParser.runtimeClasspath())),
                java(
                        """
                                import java.util.Comparator;
                                
                                class Test {
                                    Comparator<Integer> test() {
                                        return Comparator.<Integer>naturalOrder().thenComparing(Comparator.comparingDouble(Integer::doubleValue));
                                    }
                                }
                                """,
                        """
                                import java.util.Comparator;
                                
                                class Test {
                                    Comparator<Integer> test() {
                                        return Comparator.<Integer>naturalOrder().thenComparingDouble(Integer::doubleValue);
                                    }
                                }
                                """
                ));
    }

    @Test
    void testThenComparingIntRecipe() {
        Recipe recipe = RewriteUtils.loadRecipe("ComparatorRulesRecipes.ThenComparingIntRecipe");
        rewriteRun(
                spec -> spec.recipe(recipe)
                        .expectedCyclesThatMakeChanges(1).cycles(1)
                        .parser(JavaParser.fromJavaVersion().classpath(JavaParser.runtimeClasspath())),
                java(
                        """
                                import java.util.Comparator;
                                
                                class Test {
                                    Comparator<Integer> test() {
                                        return Comparator.<Integer>naturalOrder().thenComparing(Comparator.comparingInt(Integer::intValue));
                                    }
                                }
                                """,
                        """
                                import java.util.Comparator;
                                
                                class Test {
                                    Comparator<Integer> test() {
                                        return Comparator.<Integer>naturalOrder().thenComparingInt(Integer::intValue);
                                    }
                                }
                                """
                ));
    }

    @Test
    void testThenComparingLongRecipe() {
        Recipe recipe = RewriteUtils.loadRecipe("ComparatorRulesRecipes.ThenComparingLongRecipe");
        rewriteRun(
                spec -> spec.recipe(recipe)
                        .expectedCyclesThatMakeChanges(1).cycles(1)
                        .parser(JavaParser.fromJavaVersion().classpath(JavaParser.runtimeClasspath())),
                java(
                        """
                                import java.util.Comparator;
                                
                                class Test {
                                    Comparator<Integer> test() {
                                        return Comparator.<Integer>naturalOrder().thenComparing(Comparator.comparingLong(Integer::longValue));
                                    }
                                }
                                """,
                        """
                                import java.util.Comparator;
                                
                                class Test {
                                    Comparator<Integer> test() {
                                        return Comparator.<Integer>naturalOrder().thenComparingLong(Integer::longValue);
                                    }
                                }
                                """
                ));
    }

    @Test
    void testThenComparingNaturalOrderRecipe() {
        Recipe recipe = RewriteUtils.loadRecipe("ComparatorRulesRecipes.ThenComparingNaturalOrderRecipe");
        rewriteRun(
                spec -> spec.recipe(recipe)
                        .expectedCyclesThatMakeChanges(1).cycles(1)
                        .parser(JavaParser.fromJavaVersion().classpath(JavaParser.runtimeClasspath())),
                java(
                        """
                                import com.google.common.collect.ImmutableSet;
                                
                                import java.util.Comparator;
                                import java.util.function.Function;
                                
                                class Test {
                                    ImmutableSet<Comparator<String>> test() {
                                        return ImmutableSet.of(Comparator.<String>naturalOrder().thenComparing(Function.identity()), Comparator.<String>naturalOrder().thenComparing(s -> s), Comparator.<String>naturalOrder().thenComparing(s -> 0));
                                    }
                                }
                                """,
                        """
                                import com.google.common.collect.ImmutableSet;
                                
                                import java.util.Comparator;
                                
                                class Test {
                                    ImmutableSet<Comparator<String>> test() {
                                        return ImmutableSet.of(Comparator.<String>naturalOrder().thenComparing(Comparator.naturalOrder()), Comparator.<String>naturalOrder().thenComparing(Comparator.naturalOrder()), Comparator.<String>naturalOrder().thenComparing(s -> 0));
                                    }
                                }
                                """
                ));
    }

    @Test
    void testThenComparingReversedRecipe() {
        Recipe recipe = RewriteUtils.loadRecipe("ComparatorRulesRecipes.ThenComparingReversedRecipe");
        rewriteRun(
                spec -> spec.recipe(recipe)
                        .expectedCyclesThatMakeChanges(1).cycles(1)
                        .parser(JavaParser.fromJavaVersion().classpath(JavaParser.runtimeClasspath())),
                java(
                        """
                                import java.util.Comparator;
                                import java.util.function.Function;
                                
                                class Test {
                                    Comparator<String> test() {
                                        return Comparator.<String>naturalOrder().thenComparing(Comparator.comparing(String::isEmpty).reversed());
                                    }
                                }
                                """,
                        """
                                import java.util.Comparator;
                                import java.util.function.Function;
                                
                                class Test {
                                    Comparator<String> test() {
                                        return Comparator.<String>naturalOrder().thenComparing(String::isEmpty, Comparator.reverseOrder());
                                    }
                                }
                                """
                ));
    }
}
