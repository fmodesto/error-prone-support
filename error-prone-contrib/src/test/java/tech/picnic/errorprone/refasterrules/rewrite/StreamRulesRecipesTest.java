package tech.picnic.errorprone.refasterrules.rewrite;

import org.junit.jupiter.api.Test;
import org.openrewrite.Recipe;
import org.openrewrite.java.JavaParser;
import org.openrewrite.test.RewriteTest;

import static org.openrewrite.java.Assertions.java;

final class StreamRulesRecipesTest implements RewriteTest {

    @Test
    void testConcatOneStreamRecipe() {
        Recipe recipe = RewriteUtils.loadRecipe("StreamRulesRecipes.ConcatOneStreamRecipe");
        rewriteRun(
                spec -> spec.recipe(recipe)
                        .expectedCyclesThatMakeChanges(1).cycles(1)
                        .parser(JavaParser.fromJavaVersion().classpath(JavaParser.runtimeClasspath())),
                java(
                        """
                                import com.google.common.collect.Streams;
                                
                                import java.util.stream.Stream;
                                
                                class Test {
                                    Stream<Integer> test() {
                                        return Streams.concat(Stream.of(1));
                                    }
                                }
                                """,
                        """
                                import java.util.stream.Stream;
                                
                                class Test {
                                    Stream<Integer> test() {
                                        return Stream.of(1);
                                    }
                                }
                                """
                ));
    }

    @Test
    void testConcatTwoStreamsRecipe() {
        Recipe recipe = RewriteUtils.loadRecipe("StreamRulesRecipes.ConcatTwoStreamsRecipe");
        rewriteRun(
                spec -> spec.recipe(recipe)
                        .expectedCyclesThatMakeChanges(1).cycles(1)
                        .parser(JavaParser.fromJavaVersion().classpath(JavaParser.runtimeClasspath())),
                java(
                        """
                                import com.google.common.collect.Streams;
                                
                                import java.util.stream.Stream;
                                
                                class Test {
                                    Stream<Integer> test() {
                                        return Streams.concat(Stream.of(1), Stream.of(2));
                                    }
                                }
                                """,
                        """
                                import java.util.stream.Stream;
                                
                                class Test {
                                    Stream<Integer> test() {
                                        return Stream.concat(Stream.of(1), Stream.of(2));
                                    }
                                }
                                """
                ));
    }

    @Test
    void testEmptyStreamRecipe() {
        Recipe recipe = RewriteUtils.loadRecipe("StreamRulesRecipes.EmptyStreamRecipe");
        rewriteRun(
                spec -> spec.recipe(recipe)
                        .expectedCyclesThatMakeChanges(1).cycles(1)
                        .parser(JavaParser.fromJavaVersion().classpath(JavaParser.runtimeClasspath())),
                java(
                        """
                                import java.util.stream.Stream;
                                
                                class Test {
                                    Stream<String> test() {
                                        return Stream.of();
                                    }
                                }
                                """,
                        """
                                import java.util.stream.Stream;
                                
                                class Test {
                                    Stream<String> test() {
                                        return Stream.empty();
                                    }
                                }
                                """
                ));
    }

    @Test
    void testFilterOuterStreamAfterFlatMapRecipe() {
        Recipe recipe = RewriteUtils.loadRecipe("StreamRulesRecipes.FilterOuterStreamAfterFlatMapRecipe");
        rewriteRun(
                spec -> spec.recipe(recipe)
                        .expectedCyclesThatMakeChanges(1).cycles(1)
                        .parser(JavaParser.fromJavaVersion().classpath(JavaParser.runtimeClasspath())),
                java(
                        """
                                import java.util.stream.Stream;
                                
                                class Test {
                                    Stream<Integer> test() {
                                        return Stream.of("foo").flatMap(v -> Stream.of(v.length()).filter(len -> len > 0));
                                    }
                                }
                                """,
                        """
                                import java.util.stream.Stream;
                                
                                class Test {
                                    Stream<Integer> test() {
                                        return Stream.of("foo").flatMap(v -> Stream.of(v.length())).filter(len -> len > 0);
                                    }
                                }
                                """
                ));
    }

    @Test
    void testFlatMapOuterStreamAfterFlatMapRecipe() {
        Recipe recipe = RewriteUtils.loadRecipe("StreamRulesRecipes.FlatMapOuterStreamAfterFlatMapRecipe");
        rewriteRun(
                spec -> spec.recipe(recipe)
                        .expectedCyclesThatMakeChanges(1).cycles(1)
                        .parser(JavaParser.fromJavaVersion().classpath(JavaParser.runtimeClasspath())),
                java(
                        """
                                import java.util.function.Function;
                                import java.util.stream.Stream;
                                
                                class Test {
                                    Stream<Integer> test() {
                                        return Stream.of("foo").flatMap(v -> Stream.of(v.length()).flatMap(Stream::of));
                                    }
                                }
                                """,
                        """
                                import java.util.function.Function;
                                import java.util.stream.Stream;
                                
                                class Test {
                                    Stream<Integer> test() {
                                        return Stream.of("foo").flatMap(v -> Stream.of(v.length())).flatMap(Stream::of);
                                    }
                                }
                                """
                ));
    }

    @Test
    void testJoiningRecipe() {
        Recipe recipe = RewriteUtils.loadRecipe("StreamRulesRecipes.JoiningRecipe");
        rewriteRun(
                spec -> spec.recipe(recipe)
                        .expectedCyclesThatMakeChanges(1).cycles(1)
                        .parser(JavaParser.fromJavaVersion().classpath(JavaParser.runtimeClasspath())),
                java(
                        """
                                import java.util.stream.Collectors;
                                import java.util.stream.Stream;
                                
                                class Test {
                                    String test() {
                                        return Stream.of("foo").collect(Collectors.joining(""));
                                    }
                                }
                                """,
                        """
                                import java.util.stream.Collectors;
                                import java.util.stream.Stream;
                                
                                class Test {
                                    String test() {
                                        return Stream.of("foo").collect(Collectors.joining());
                                    }
                                }
                                """
                ));
    }

    @Test
    void testMapOuterStreamAfterFlatMapRecipe() {
        Recipe recipe = RewriteUtils.loadRecipe("StreamRulesRecipes.MapOuterStreamAfterFlatMapRecipe");
        rewriteRun(
                spec -> spec.recipe(recipe)
                        .expectedCyclesThatMakeChanges(1).cycles(1)
                        .parser(JavaParser.fromJavaVersion().classpath(JavaParser.runtimeClasspath())),
                java(
                        """
                                import java.util.stream.Stream;
                                
                                class Test {
                                    Stream<Integer> test() {
                                        return Stream.of("foo").flatMap(v -> Stream.of(v.length()).map(len -> len * 0));
                                    }
                                }
                                """,
                        """
                                import java.util.stream.Stream;
                                
                                class Test {
                                    Stream<Integer> test() {
                                        return Stream.of("foo").flatMap(v -> Stream.of(v.length())).map(len -> len * 0);
                                    }
                                }
                                """
                ));
    }

    @Test
    void testStreamAllMatchRecipe() {
        Recipe recipe = RewriteUtils.loadRecipe("StreamRulesRecipes.StreamAllMatchRecipe");
        rewriteRun(
                spec -> spec.recipe(recipe)
                        .expectedCyclesThatMakeChanges(1).cycles(1)
                        .parser(JavaParser.fromJavaVersion().classpath(JavaParser.runtimeClasspath())),
                java(
                        """
                                import com.google.common.collect.ImmutableSet;
                                
                                import java.util.function.Function;
                                import java.util.function.Predicate;
                                import java.util.stream.Stream;
                                
                                class Test {
                                    ImmutableSet<Boolean> test() {
                                        Predicate<String> pred = String::isBlank;
                                        Function<String, Boolean> toBooleanFunction = Boolean::valueOf;
                                        return ImmutableSet.of(Stream.of("foo").noneMatch(Predicate.not(String::isBlank)), Stream.of("bar").noneMatch(pred.negate()), Stream.of("baz").map(s -> s.isBlank()).allMatch(Boolean::booleanValue), Stream.of("qux").map(Boolean::valueOf).allMatch(r -> r), Stream.of("quux").map(toBooleanFunction).anyMatch(Boolean::booleanValue));
                                    }
                                }
                                """,
                        """
                                import com.google.common.collect.ImmutableSet;
                                
                                import java.util.function.Function;
                                import java.util.function.Predicate;
                                import java.util.stream.Stream;
                                
                                class Test {
                                    ImmutableSet<Boolean> test() {
                                        Predicate<String> pred = String::isBlank;
                                        Function<String, Boolean> toBooleanFunction = Boolean::valueOf;
                                        return ImmutableSet.of(Stream.of("foo").allMatch(String::isBlank), Stream.of("bar").allMatch(pred), Stream.of("baz").allMatch(s -> s.isBlank()), Stream.of("qux").allMatch(Boolean::valueOf), Stream.of("quux").map(toBooleanFunction).anyMatch(Boolean::booleanValue));
                                    }
                                }
                                """
                ));
    }

    @Test
    void testStreamAllMatch2Recipe() {
        Recipe recipe = RewriteUtils.loadRecipe("StreamRulesRecipes.StreamAllMatch2Recipe");
        rewriteRun(
                spec -> spec.recipe(recipe)
                        .expectedCyclesThatMakeChanges(1).cycles(1)
                        .parser(JavaParser.fromJavaVersion().classpath(JavaParser.runtimeClasspath())),
                java(
                        """
                                import java.util.stream.Stream;
                                
                                class Test {
                                    boolean test() {
                                        return Stream.of("foo").noneMatch(s -> !s.isBlank());
                                    }
                                }
                                """,
                        """
                                import java.util.stream.Stream;
                                
                                class Test {
                                    boolean test() {
                                        return Stream.of("foo").allMatch(s -> s.isBlank());
                                    }
                                }
                                """
                ));
    }

    @Test
    void testStreamAnyMatchRecipe() {
        Recipe recipe = RewriteUtils.loadRecipe("StreamRulesRecipes.StreamAnyMatchRecipe");
        rewriteRun(
                spec -> spec.recipe(recipe)
                        .expectedCyclesThatMakeChanges(1).cycles(1)
                        .parser(JavaParser.fromJavaVersion().classpath(JavaParser.runtimeClasspath())),
                java(
                        """
                                import com.google.common.collect.ImmutableSet;
                                
                                import java.util.function.Function;
                                import java.util.function.Predicate;
                                import java.util.stream.Stream;
                                
                                class Test {
                                    ImmutableSet<Boolean> test() {
                                        Function<String, Boolean> toBooleanFunction = Boolean::valueOf;
                                        return ImmutableSet.of(!Stream.of("foo").noneMatch(s -> s.length() > 1), Stream.of("bar").filter(String::isEmpty).findAny().isPresent(), Stream.of("baz").map(s -> s.isBlank()).anyMatch(Boolean::booleanValue), Stream.of("qux").map(Boolean::valueOf).anyMatch(r -> r), Stream.of("quux").map(toBooleanFunction).anyMatch(Boolean::booleanValue));
                                    }
                                }
                                """,
                        """
                                import com.google.common.collect.ImmutableSet;
                                
                                import java.util.function.Function;
                                import java.util.function.Predicate;
                                import java.util.stream.Stream;
                                
                                class Test {
                                    ImmutableSet<Boolean> test() {
                                        Function<String, Boolean> toBooleanFunction = Boolean::valueOf;
                                        return ImmutableSet.of(Stream.of("foo").anyMatch(s -> s.length() > 1), Stream.of("bar").anyMatch(String::isEmpty), Stream.of("baz").anyMatch(s -> s.isBlank()), Stream.of("qux").anyMatch(Boolean::valueOf), Stream.of("quux").map(toBooleanFunction).anyMatch(Boolean::booleanValue));
                                    }
                                }
                                """
                ));
    }

    @Test
    void testStreamCountRecipe() {
        Recipe recipe = RewriteUtils.loadRecipe("StreamRulesRecipes.StreamCountRecipe");
        rewriteRun(
                spec -> spec.recipe(recipe)
                        .expectedCyclesThatMakeChanges(1).cycles(1)
                        .parser(JavaParser.fromJavaVersion().classpath(JavaParser.runtimeClasspath())),
                java(
                        """
                                import java.util.stream.Collectors;
                                import java.util.stream.Stream;
                                
                                class Test {
                                    Long test() {
                                        return Stream.of(1).collect(Collectors.counting());
                                    }
                                }
                                """,
                        """
                                import java.util.stream.Stream;
                                
                                class Test {
                                    Long test() {
                                        return Stream.of(1).count();
                                    }
                                }
                                """
                ));
    }

    @Test
    void testStreamFilterCollectRecipe() {
        Recipe recipe = RewriteUtils.loadRecipe("StreamRulesRecipes.StreamFilterCollectRecipe");
        rewriteRun(
                spec -> spec.recipe(recipe)
                        .expectedCyclesThatMakeChanges(1).cycles(1)
                        .parser(JavaParser.fromJavaVersion().classpath(JavaParser.runtimeClasspath())),
                java(
                        """
                                import com.google.common.collect.ImmutableSet;
                                
                                import java.util.function.Predicate;
                                import java.util.stream.Collectors;
                                import java.util.stream.Stream;
                                
                                class Test {
                                    ImmutableSet<String> test() {
                                        return Stream.of("1").collect(Collectors.filtering(String::isEmpty, ImmutableSet.toImmutableSet()));
                                    }
                                }
                                """,
                        """
                                import com.google.common.collect.ImmutableSet;
                                
                                import java.util.function.Predicate;
                                import java.util.stream.Stream;
                                
                                class Test {
                                    ImmutableSet<String> test() {
                                        return Stream.of("1").filter(String::isEmpty).collect(ImmutableSet.toImmutableSet());
                                    }
                                }
                                """
                ));
    }

    @Test
    void testStreamFilterSortedRecipe() {
        Recipe recipe = RewriteUtils.loadRecipe("StreamRulesRecipes.StreamFilterSortedRecipe");
        rewriteRun(
                spec -> spec.recipe(recipe)
                        .expectedCyclesThatMakeChanges(1).cycles(1)
                        .parser(JavaParser.fromJavaVersion().classpath(JavaParser.runtimeClasspath())),
                java(
                        """
                                import java.util.stream.Stream;
                                
                                class Test {
                                    Stream<Integer> test() {
                                        return Stream.of(1, 4, 3, 2).sorted().filter(i -> i % 2 == 0);
                                    }
                                }
                                """,
                        """
                                import java.util.stream.Stream;
                                
                                class Test {
                                    Stream<Integer> test() {
                                        return Stream.of(1, 4, 3, 2).filter(i -> i % 2 == 0).sorted();
                                    }
                                }
                                """
                ));
    }

    @Test
    void testStreamFilterSortedWithComparatorRecipe() {
        Recipe recipe = RewriteUtils.loadRecipe("StreamRulesRecipes.StreamFilterSortedWithComparatorRecipe");
        rewriteRun(
                spec -> spec.recipe(recipe)
                        .expectedCyclesThatMakeChanges(1).cycles(1)
                        .parser(JavaParser.fromJavaVersion().classpath(JavaParser.runtimeClasspath())),
                java(
                        """
                                import java.util.Comparator;
                                import java.util.stream.Stream;
                                
                                class Test {
                                    Stream<Integer> test() {
                                        return Stream.of(1, 4, 3, 2).sorted(Comparator.reverseOrder()).filter(i -> i % 2 == 0);
                                    }
                                }
                                """,
                        """
                                import java.util.Comparator;
                                import java.util.stream.Stream;
                                
                                class Test {
                                    Stream<Integer> test() {
                                        return Stream.of(1, 4, 3, 2).filter(i -> i % 2 == 0).sorted(Comparator.reverseOrder());
                                    }
                                }
                                """
                ));
    }

    @Test
    void testStreamFindAnyIsEmptyRecipe() {
        Recipe recipe = RewriteUtils.loadRecipe("StreamRulesRecipes.StreamFindAnyIsEmptyRecipe");
        rewriteRun(
                spec -> spec.recipe(recipe)
                        .expectedCyclesThatMakeChanges(1).cycles(1)
                        .parser(JavaParser.fromJavaVersion().classpath(JavaParser.runtimeClasspath())),
                java(
                        """
                                import com.google.common.collect.ImmutableList;
                                import com.google.common.collect.ImmutableMap;
                                import com.google.common.collect.ImmutableSet;
                                
                                import java.util.List;
                                import java.util.Map;
                                import java.util.function.Function;
                                import java.util.stream.Collectors;
                                import java.util.stream.Stream;
                                
                                class Test {
                                    ImmutableSet<Boolean> test() {
                                        return ImmutableSet.of(Stream.of(1).count() == 0, Stream.of(2).count() <= 0, Stream.of(3).count() < 1, Stream.of(4).findFirst().isEmpty(), Stream.of(5).collect(ImmutableSet.toImmutableSet()).isEmpty(), Stream.of(6).collect(Collectors.collectingAndThen(ImmutableList.toImmutableList(), List::isEmpty)), Stream.of(7).collect(Collectors.collectingAndThen(ImmutableList.toImmutableList(), ImmutableList::isEmpty)), Stream.of(8).collect(Collectors.collectingAndThen(ImmutableMap.toImmutableMap(k -> k, v -> v), Map::isEmpty)), Stream.of(9).collect(Collectors.collectingAndThen(ImmutableMap.toImmutableMap(k -> k, v -> v), ImmutableMap::isEmpty)), Stream.of(10).count() != 0, Stream.of(11).count() > 0, Stream.of(12).count() >= 1);
                                    }
                                }
                                """,
                        """
                                import com.google.common.collect.ImmutableSet;
                                
                                import java.util.stream.Stream;
                                
                                class Test {
                                    ImmutableSet<Boolean> test() {
                                        return ImmutableSet.of(Stream.of(1).findAny().isEmpty(), Stream.of(2).findAny().isEmpty(), Stream.of(3).findAny().isEmpty(), Stream.of(4).findAny().isEmpty(), Stream.of(5).findAny().isEmpty(), Stream.of(6).findAny().isEmpty(), Stream.of(7).findAny().isEmpty(), Stream.of(8).findAny().isEmpty(), Stream.of(9).findAny().isEmpty(), !Stream.of(10).findAny().isEmpty(), !Stream.of(11).findAny().isEmpty(), !Stream.of(12).findAny().isEmpty());
                                    }
                                }
                                """
                ));
    }

    @Test
    void testStreamFindAnyIsPresentRecipe() {
        Recipe recipe = RewriteUtils.loadRecipe("StreamRulesRecipes.StreamFindAnyIsPresentRecipe");
        rewriteRun(
                spec -> spec.recipe(recipe)
                        .expectedCyclesThatMakeChanges(1).cycles(1)
                        .parser(JavaParser.fromJavaVersion().classpath(JavaParser.runtimeClasspath())),
                java(
                        """
                                import java.util.stream.Stream;
                                
                                class Test {
                                    boolean test() {
                                        return Stream.of(1).findFirst().isPresent();
                                    }
                                }
                                """,
                        """
                                import java.util.stream.Stream;
                                
                                class Test {
                                    boolean test() {
                                        return Stream.of(1).findAny().isPresent();
                                    }
                                }
                                """
                ));
    }

    @Test
    void testStreamFlatMapCollectRecipe() {
        Recipe recipe = RewriteUtils.loadRecipe("StreamRulesRecipes.StreamFlatMapCollectRecipe");
        rewriteRun(
                spec -> spec.recipe(recipe)
                        .expectedCyclesThatMakeChanges(1).cycles(1)
                        .parser(JavaParser.fromJavaVersion().classpath(JavaParser.runtimeClasspath())),
                java(
                        """
                                import com.google.common.collect.ImmutableSet;
                                
                                import java.util.stream.Collectors;
                                import java.util.stream.Stream;
                                
                                class Test {
                                    ImmutableSet<Integer> test() {
                                        return Stream.of(1).collect(Collectors.flatMapping(n -> Stream.of(n, n), ImmutableSet.toImmutableSet()));
                                    }
                                }
                                """,
                        """
                                import com.google.common.collect.ImmutableSet;
                                
                                import java.util.stream.Stream;
                                
                                class Test {
                                    ImmutableSet<Integer> test() {
                                        return Stream.of(1).flatMap(n -> Stream.of(n, n)).collect(ImmutableSet.toImmutableSet());
                                    }
                                }
                                """
                ));
    }

    @Test
    void testStreamIterateRecipe() {
        Recipe recipe = RewriteUtils.loadRecipe("StreamRulesRecipes.StreamIterateRecipe");
        rewriteRun(
                spec -> spec.recipe(recipe)
                        .expectedCyclesThatMakeChanges(1).cycles(1)
                        .parser(JavaParser.fromJavaVersion().classpath(JavaParser.runtimeClasspath())),
                java(
                        """
                                import java.util.stream.Stream;
                                
                                class Test {
                                    Stream<Integer> test() {
                                        return Stream.iterate(0, i -> i + 1).takeWhile(i -> i < 10);
                                    }
                                }
                                """,
                        """
                                import java.util.stream.Stream;
                                
                                class Test {
                                    Stream<Integer> test() {
                                        return Stream.iterate(0, i -> i < 10, i -> i + 1);
                                    }
                                }
                                """
                ));
    }

    @Test
    void testStreamMapCollectRecipe() {
        Recipe recipe = RewriteUtils.loadRecipe("StreamRulesRecipes.StreamMapCollectRecipe");
        rewriteRun(
                spec -> spec.recipe(recipe)
                        .expectedCyclesThatMakeChanges(1).cycles(1)
                        .parser(JavaParser.fromJavaVersion().classpath(JavaParser.runtimeClasspath())),
                java(
                        """
                                import com.google.common.collect.ImmutableSet;
                                
                                import java.util.function.Function;
                                import java.util.stream.Collectors;
                                import java.util.stream.Stream;
                                
                                class Test {
                                    ImmutableSet<Integer> test() {
                                        return Stream.of("1").collect(Collectors.mapping(Integer::parseInt, ImmutableSet.toImmutableSet()));
                                    }
                                }
                                """,
                        """
                                import com.google.common.collect.ImmutableSet;
                                
                                import java.util.function.Function;
                                import java.util.stream.Stream;
                                
                                class Test {
                                    ImmutableSet<Integer> test() {
                                        return Stream.of("1").map(Integer::parseInt).collect(ImmutableSet.toImmutableSet());
                                    }
                                }
                                """
                ));
    }

    @Test
    void testStreamMapFilterRecipe() {
        Recipe recipe = RewriteUtils.loadRecipe("StreamRulesRecipes.StreamMapFilterRecipe");
        rewriteRun(
                spec -> spec.recipe(recipe)
                        .expectedCyclesThatMakeChanges(1).cycles(1)
                        .parser(JavaParser.fromJavaVersion().classpath(JavaParser.runtimeClasspath())),
                java(
                        """
                                import com.google.common.collect.ImmutableMap;
                                
                                import java.util.function.Function;
                                import java.util.function.Predicate;
                                import java.util.stream.Stream;
                                
                                class Test {
                                    Stream<Integer> test() {
                                        return Stream.of("foo").filter(ImmutableMap.of(1, 2)::containsKey).map(ImmutableMap.of(1, 2)::get);
                                    }
                                }
                                """,
                        """
                                import com.google.common.collect.ImmutableMap;
                                
                                import java.util.Objects;
                                import java.util.function.Function;
                                import java.util.function.Predicate;
                                import java.util.stream.Stream;
                                
                                class Test {
                                    Stream<Integer> test() {
                                        return Stream.of("foo").map(ImmutableMap.of(1, 2)::get).filter(Objects::nonNull);
                                    }
                                }
                                """
                ));
    }

    @Test
    void testStreamMapFirstRecipe() {
        Recipe recipe = RewriteUtils.loadRecipe("StreamRulesRecipes.StreamMapFirstRecipe");
        rewriteRun(
                spec -> spec.recipe(recipe)
                        .expectedCyclesThatMakeChanges(1).cycles(1)
                        .parser(JavaParser.fromJavaVersion().classpath(JavaParser.runtimeClasspath())),
                java(
                        """
                                import com.google.common.collect.ImmutableSet;
                                
                                import java.util.Optional;
                                import java.util.function.Function;
                                import java.util.stream.Stream;
                                
                                class Test {
                                    ImmutableSet<Optional<Integer>> test() {
                                        return ImmutableSet.of(Stream.of("foo").map(s -> s.length()).findFirst(), Stream.of("bar").map(String::length).findFirst());
                                    }
                                }
                                """,
                        """
                                import com.google.common.collect.ImmutableSet;
                                
                                import java.util.Optional;
                                import java.util.function.Function;
                                import java.util.stream.Stream;
                                
                                class Test {
                                    ImmutableSet<Optional<Integer>> test() {
                                        return ImmutableSet.of(Stream.of("foo").findFirst().map(s -> s.length()), Stream.of("bar").findFirst().map(String::length));
                                    }
                                }
                                """
                ));
    }

    @Test
    void testStreamMapToDoubleSumRecipe() {
        Recipe recipe = RewriteUtils.loadRecipe("StreamRulesRecipes.StreamMapToDoubleSumRecipe");
        rewriteRun(
                spec -> spec.recipe(recipe)
                        .expectedCyclesThatMakeChanges(1).cycles(1)
                        .parser(JavaParser.fromJavaVersion().classpath(JavaParser.runtimeClasspath())),
                java(
                        """
                                import com.google.common.collect.ImmutableSet;
                                
                                import java.util.function.Function;
                                import java.util.stream.Collectors;
                                import java.util.stream.Stream;
                                
                                class Test {
                                    ImmutableSet<Double> test() {
                                        Function<String, Double> parseDoubleFunction = Double::parseDouble;
                                        return ImmutableSet.of(Stream.of("1").collect(Collectors.summingDouble(Double::parseDouble)), Stream.of(2).map(i -> i * 2.0).reduce(0.0, Double::sum), Stream.of("3").map(Double::parseDouble).reduce(0.0, Double::sum), Stream.of("4").map(parseDoubleFunction).reduce(0.0, Double::sum));
                                    }
                                }
                                """,
                        """
                                import com.google.common.collect.ImmutableSet;
                                
                                import java.util.function.Function;
                                import java.util.stream.Stream;
                                
                                class Test {
                                    ImmutableSet<Double> test() {
                                        Function<String, Double> parseDoubleFunction = Double::parseDouble;
                                        return ImmutableSet.of(Stream.of("1").mapToDouble(Double::parseDouble).sum(), Stream.of(2).mapToDouble(i -> i * 2.0).sum(), Stream.of("3").mapToDouble(Double::parseDouble).sum(), Stream.of("4").map(parseDoubleFunction).reduce(0.0, Double::sum));
                                    }
                                }
                                """
                ));
    }

    @Test
    void testStreamMapToDoubleSummaryStatisticsRecipe() {
        Recipe recipe = RewriteUtils.loadRecipe("StreamRulesRecipes.StreamMapToDoubleSummaryStatisticsRecipe");
        rewriteRun(
                spec -> spec.recipe(recipe)
                        .expectedCyclesThatMakeChanges(1).cycles(1)
                        .parser(JavaParser.fromJavaVersion().classpath(JavaParser.runtimeClasspath())),
                java(
                        """
                                import java.util.DoubleSummaryStatistics;
                                import java.util.stream.Collectors;
                                import java.util.stream.Stream;
                                
                                class Test {
                                    DoubleSummaryStatistics test() {
                                        return Stream.of("1").collect(Collectors.summarizingDouble(Double::parseDouble));
                                    }
                                }
                                """,
                        """
                                import java.util.DoubleSummaryStatistics;
                                import java.util.stream.Stream;
                                
                                class Test {
                                    DoubleSummaryStatistics test() {
                                        return Stream.of("1").mapToDouble(Double::parseDouble).summaryStatistics();
                                    }
                                }
                                """
                ));
    }

    @Test
    void testStreamMapToIntSumRecipe() {
        Recipe recipe = RewriteUtils.loadRecipe("StreamRulesRecipes.StreamMapToIntSumRecipe");
        rewriteRun(
                spec -> spec.recipe(recipe)
                        .expectedCyclesThatMakeChanges(1).cycles(1)
                        .parser(JavaParser.fromJavaVersion().classpath(JavaParser.runtimeClasspath())),
                java(
                        """
                                import com.google.common.collect.ImmutableSet;
                                
                                import java.util.function.Function;
                                import java.util.stream.Collectors;
                                import java.util.stream.Stream;
                                
                                class Test {
                                    ImmutableSet<Integer> test() {
                                        Function<String, Integer> parseIntFunction = Integer::parseInt;
                                        return ImmutableSet.of(Stream.of("1").collect(Collectors.summingInt(Integer::parseInt)), Stream.of(2).map(i -> i * 2).reduce(0, Integer::sum), Stream.of("3").map(Integer::parseInt).reduce(0, Integer::sum), Stream.of("4").map(parseIntFunction).reduce(0, Integer::sum));
                                    }
                                }
                                """,
                        """
                                import com.google.common.collect.ImmutableSet;
                                
                                import java.util.function.Function;
                                import java.util.stream.Stream;
                                
                                class Test {
                                    ImmutableSet<Integer> test() {
                                        Function<String, Integer> parseIntFunction = Integer::parseInt;
                                        return ImmutableSet.of(Stream.of("1").mapToInt(Integer::parseInt).sum(), Stream.of(2).mapToInt(i -> i * 2).sum(), Stream.of("3").mapToInt(Integer::parseInt).sum(), Stream.of("4").map(parseIntFunction).reduce(0, Integer::sum));
                                    }
                                }
                                """
                ));
    }

    @Test
    void testStreamMapToIntSummaryStatisticsRecipe() {
        Recipe recipe = RewriteUtils.loadRecipe("StreamRulesRecipes.StreamMapToIntSummaryStatisticsRecipe");
        rewriteRun(
                spec -> spec.recipe(recipe)
                        .expectedCyclesThatMakeChanges(1).cycles(1)
                        .parser(JavaParser.fromJavaVersion().classpath(JavaParser.runtimeClasspath())),
                java(
                        """
                                import java.util.IntSummaryStatistics;
                                import java.util.stream.Collectors;
                                import java.util.stream.Stream;
                                
                                class Test {
                                    IntSummaryStatistics test() {
                                        return Stream.of("1").collect(Collectors.summarizingInt(Integer::parseInt));
                                    }
                                }
                                """,
                        """
                                import java.util.IntSummaryStatistics;
                                import java.util.stream.Stream;
                                
                                class Test {
                                    IntSummaryStatistics test() {
                                        return Stream.of("1").mapToInt(Integer::parseInt).summaryStatistics();
                                    }
                                }
                                """
                ));
    }

    @Test
    void testStreamMapToLongSumRecipe() {
        Recipe recipe = RewriteUtils.loadRecipe("StreamRulesRecipes.StreamMapToLongSumRecipe");
        rewriteRun(
                spec -> spec.recipe(recipe)
                        .expectedCyclesThatMakeChanges(1).cycles(1)
                        .parser(JavaParser.fromJavaVersion().classpath(JavaParser.runtimeClasspath())),
                java(
                        """
                                import com.google.common.collect.ImmutableSet;
                                
                                import java.util.function.Function;
                                import java.util.stream.Collectors;
                                import java.util.stream.Stream;
                                
                                class Test {
                                    ImmutableSet<Long> test() {
                                        Function<String, Long> parseLongFunction = Long::parseLong;
                                        return ImmutableSet.of(Stream.of("1").collect(Collectors.summingLong(Long::parseLong)), Stream.of(2).map(i -> i * 2L).reduce(0L, Long::sum), Stream.of("3").map(Long::parseLong).reduce(0L, Long::sum), Stream.of("4").map(parseLongFunction).reduce(0L, Long::sum));
                                    }
                                }
                                """,
                        """
                                import com.google.common.collect.ImmutableSet;
                                
                                import java.util.function.Function;
                                import java.util.stream.Stream;
                                
                                class Test {
                                    ImmutableSet<Long> test() {
                                        Function<String, Long> parseLongFunction = Long::parseLong;
                                        return ImmutableSet.of(Stream.of("1").mapToLong(Long::parseLong).sum(), Stream.of(2).mapToLong(i -> i * 2L).sum(), Stream.of("3").mapToLong(Long::parseLong).sum(), Stream.of("4").map(parseLongFunction).reduce(0L, Long::sum));
                                    }
                                }
                                """
                ));
    }

    @Test
    void testStreamMapToLongSummaryStatisticsRecipe() {
        Recipe recipe = RewriteUtils.loadRecipe("StreamRulesRecipes.StreamMapToLongSummaryStatisticsRecipe");
        rewriteRun(
                spec -> spec.recipe(recipe)
                        .expectedCyclesThatMakeChanges(1).cycles(1)
                        .parser(JavaParser.fromJavaVersion().classpath(JavaParser.runtimeClasspath())),
                java(
                        """
                                import java.util.LongSummaryStatistics;
                                import java.util.stream.Collectors;
                                import java.util.stream.Stream;
                                
                                class Test {
                                    LongSummaryStatistics test() {
                                        return Stream.of("1").collect(Collectors.summarizingLong(Long::parseLong));
                                    }
                                }
                                """,
                        """
                                import java.util.LongSummaryStatistics;
                                import java.util.stream.Stream;
                                
                                class Test {
                                    LongSummaryStatistics test() {
                                        return Stream.of("1").mapToLong(Long::parseLong).summaryStatistics();
                                    }
                                }
                                """
                ));
    }

    @Test
    void testStreamMaxRecipe() {
        Recipe recipe = RewriteUtils.loadRecipe("StreamRulesRecipes.StreamMaxRecipe");
        rewriteRun(
                spec -> spec.recipe(recipe)
                        .expectedCyclesThatMakeChanges(1).cycles(1)
                        .parser(JavaParser.fromJavaVersion().classpath(JavaParser.runtimeClasspath())),
                java(
                        """
                                import com.google.common.collect.ImmutableSet;
                                import com.google.common.collect.Streams;
                                
                                import java.util.Comparator;
                                import java.util.Optional;
                                import java.util.stream.Collectors;
                                import java.util.stream.Stream;
                                
                                class Test {
                                    ImmutableSet<Optional<String>> test() {
                                        return ImmutableSet.of(Stream.of("foo").min(Comparator.comparingInt(String::length).reversed()), Streams.findLast(Stream.of("bar").sorted(Comparator.comparingInt(String::length))), Stream.of("baz").collect(Collectors.maxBy(Comparator.comparingInt(String::length))));
                                    }
                                }
                                """,
                        """
                                import com.google.common.collect.ImmutableSet;
                                
                                import java.util.Comparator;
                                import java.util.Optional;
                                import java.util.stream.Stream;
                                
                                class Test {
                                    ImmutableSet<Optional<String>> test() {
                                        return ImmutableSet.of(Stream.of("foo").max(Comparator.comparingInt(String::length)), Stream.of("bar").max(Comparator.comparingInt(String::length)), Stream.of("baz").max(Comparator.comparingInt(String::length)));
                                    }
                                }
                                """
                ));
    }

    @Test
    void testStreamMaxNaturalOrderRecipe() {
        Recipe recipe = RewriteUtils.loadRecipe("StreamRulesRecipes.StreamMaxNaturalOrderRecipe");
        rewriteRun(
                spec -> spec.recipe(recipe)
                        .expectedCyclesThatMakeChanges(1).cycles(1)
                        .parser(JavaParser.fromJavaVersion().classpath(JavaParser.runtimeClasspath())),
                java(
                        """
                                import com.google.common.collect.ImmutableSet;
                                import com.google.common.collect.Streams;
                                
                                import java.util.Comparator;
                                import java.util.Optional;
                                import java.util.stream.Stream;
                                
                                class Test {
                                    ImmutableSet<Optional<String>> test() {
                                        return ImmutableSet.of(Stream.of("foo").min(Comparator.reverseOrder()), Streams.findLast(Stream.of("bar").sorted()));
                                    }
                                }
                                """,
                        """
                                import com.google.common.collect.ImmutableSet;
                                
                                import java.util.Comparator;
                                import java.util.Optional;
                                import java.util.stream.Stream;
                                
                                class Test {
                                    ImmutableSet<Optional<String>> test() {
                                        return ImmutableSet.of(Stream.of("foo").max(Comparator.naturalOrder()), Stream.of("bar").max(Comparator.naturalOrder()));
                                    }
                                }
                                """
                ));
    }

    @Test
    void testStreamMinRecipe() {
        Recipe recipe = RewriteUtils.loadRecipe("StreamRulesRecipes.StreamMinRecipe");
        rewriteRun(
                spec -> spec.recipe(recipe)
                        .expectedCyclesThatMakeChanges(1).cycles(1)
                        .parser(JavaParser.fromJavaVersion().classpath(JavaParser.runtimeClasspath())),
                java(
                        """
                                import com.google.common.collect.ImmutableSet;
                                
                                import java.util.Comparator;
                                import java.util.Optional;
                                import java.util.stream.Collectors;
                                import java.util.stream.Stream;
                                
                                class Test {
                                    ImmutableSet<Optional<String>> test() {
                                        return ImmutableSet.of(Stream.of("foo").max(Comparator.comparingInt(String::length).reversed()), Stream.of("bar").sorted(Comparator.comparingInt(String::length)).findFirst(), Stream.of("baz").collect(Collectors.minBy(Comparator.comparingInt(String::length))));
                                    }
                                }
                                """,
                        """
                                import com.google.common.collect.ImmutableSet;
                                
                                import java.util.Comparator;
                                import java.util.Optional;
                                import java.util.stream.Stream;
                                
                                class Test {
                                    ImmutableSet<Optional<String>> test() {
                                        return ImmutableSet.of(Stream.of("foo").min(Comparator.comparingInt(String::length)), Stream.of("bar").min(Comparator.comparingInt(String::length)), Stream.of("baz").min(Comparator.comparingInt(String::length)));
                                    }
                                }
                                """
                ));
    }

    @Test
    void testStreamMinNaturalOrderRecipe() {
        Recipe recipe = RewriteUtils.loadRecipe("StreamRulesRecipes.StreamMinNaturalOrderRecipe");
        rewriteRun(
                spec -> spec.recipe(recipe)
                        .expectedCyclesThatMakeChanges(1).cycles(1)
                        .parser(JavaParser.fromJavaVersion().classpath(JavaParser.runtimeClasspath())),
                java(
                        """
                                import com.google.common.collect.ImmutableSet;
                                
                                import java.util.Comparator;
                                import java.util.Optional;
                                import java.util.stream.Stream;
                                
                                class Test {
                                    ImmutableSet<Optional<String>> test() {
                                        return ImmutableSet.of(Stream.of("foo").max(Comparator.reverseOrder()), Stream.of("bar").sorted().findFirst());
                                    }
                                }
                                """,
                        """
                                import com.google.common.collect.ImmutableSet;
                                
                                import java.util.Comparator;
                                import java.util.Optional;
                                import java.util.stream.Stream;
                                
                                class Test {
                                    ImmutableSet<Optional<String>> test() {
                                        return ImmutableSet.of(Stream.of("foo").min(Comparator.naturalOrder()), Stream.of("bar").min(Comparator.naturalOrder()));
                                    }
                                }
                                """
                ));
    }

    @Test
    void testStreamNoneMatchRecipe() {
        Recipe recipe = RewriteUtils.loadRecipe("StreamRulesRecipes.StreamNoneMatchRecipe");
        rewriteRun(
                spec -> spec.recipe(recipe)
                        .expectedCyclesThatMakeChanges(1).cycles(1)
                        .parser(JavaParser.fromJavaVersion().classpath(JavaParser.runtimeClasspath())),
                java(
                        """
                                import com.google.common.collect.ImmutableSet;
                                
                                import java.util.function.Function;
                                import java.util.function.Predicate;
                                import java.util.stream.Stream;
                                
                                class Test {
                                    ImmutableSet<Boolean> test() {
                                        Predicate<String> pred = String::isBlank;
                                        Function<String, Boolean> toBooleanFunction = Boolean::valueOf;
                                        return ImmutableSet.of(!Stream.of("foo").anyMatch(s -> s.length() > 1), Stream.of("bar").allMatch(Predicate.not(String::isBlank)), Stream.of("baz").allMatch(pred.negate()), Stream.of("qux").filter(String::isEmpty).findAny().isEmpty(), Stream.of("quux").map(s -> s.isBlank()).noneMatch(Boolean::booleanValue), Stream.of("quuz").map(Boolean::valueOf).noneMatch(r -> r), Stream.of("corge").map(toBooleanFunction).noneMatch(Boolean::booleanValue));
                                    }
                                }
                                """,
                        """
                                import com.google.common.collect.ImmutableSet;
                                
                                import java.util.function.Function;
                                import java.util.function.Predicate;
                                import java.util.stream.Stream;
                                
                                class Test {
                                    ImmutableSet<Boolean> test() {
                                        Predicate<String> pred = String::isBlank;
                                        Function<String, Boolean> toBooleanFunction = Boolean::valueOf;
                                        return ImmutableSet.of(Stream.of("foo").noneMatch(s -> s.length() > 1), Stream.of("bar").noneMatch(String::isBlank), Stream.of("baz").noneMatch(pred), Stream.of("qux").noneMatch(String::isEmpty), Stream.of("quux").noneMatch(s -> s.isBlank()), Stream.of("quuz").noneMatch(Boolean::valueOf), Stream.of("corge").map(toBooleanFunction).noneMatch(Boolean::booleanValue));
                                    }
                                }
                                """
                ));
    }

    @Test
    void testStreamNoneMatch2Recipe() {
        Recipe recipe = RewriteUtils.loadRecipe("StreamRulesRecipes.StreamNoneMatch2Recipe");
        rewriteRun(
                spec -> spec.recipe(recipe)
                        .expectedCyclesThatMakeChanges(1).cycles(1)
                        .parser(JavaParser.fromJavaVersion().classpath(JavaParser.runtimeClasspath())),
                java(
                        """
                                import com.google.common.collect.ImmutableSet;
                                
                                import java.util.stream.Stream;
                                
                                class Test {
                                    ImmutableSet<Boolean> test() {
                                        return ImmutableSet.of(Stream.of("foo").allMatch(s -> !s.isBlank()), Stream.of(Boolean.TRUE).allMatch(b -> !b));
                                    }
                                }
                                """,
                        """
                                import com.google.common.collect.ImmutableSet;
                                
                                import java.util.stream.Stream;
                                
                                class Test {
                                    ImmutableSet<Boolean> test() {
                                        return ImmutableSet.of(Stream.of("foo").noneMatch(s -> s.isBlank()), Stream.of(Boolean.TRUE).noneMatch(b -> b));
                                    }
                                }
                                """
                ));
    }

    @Test
    void testStreamOf1Recipe() {
        Recipe recipe = RewriteUtils.loadRecipe("StreamRulesRecipes.StreamOf1Recipe");
        rewriteRun(
                spec -> spec.recipe(recipe)
                        .expectedCyclesThatMakeChanges(1).cycles(1)
                        .parser(JavaParser.fromJavaVersion().classpath(JavaParser.runtimeClasspath())),
                java(
                        """
                                import com.google.common.collect.ImmutableList;
                                
                                import java.util.stream.Stream;
                                
                                class Test {
                                    Stream<Integer> test() {
                                        return ImmutableList.of(1).stream();
                                    }
                                }
                                """,
                        """
                                import java.util.stream.Stream;
                                
                                class Test {
                                    Stream<Integer> test() {
                                        return Stream.of(1);
                                    }
                                }
                                """
                ));
    }

    @Test
    void testStreamOf2Recipe() {
        Recipe recipe = RewriteUtils.loadRecipe("StreamRulesRecipes.StreamOf2Recipe");
        rewriteRun(
                spec -> spec.recipe(recipe)
                        .expectedCyclesThatMakeChanges(1).cycles(1)
                        .parser(JavaParser.fromJavaVersion().classpath(JavaParser.runtimeClasspath())),
                java(
                        """
                                import com.google.common.collect.ImmutableList;
                                
                                import java.util.stream.Stream;
                                
                                class Test {
                                    Stream<Integer> test() {
                                        return ImmutableList.of(1, 2).stream();
                                    }
                                }
                                """,
                        """
                                import java.util.stream.Stream;
                                
                                class Test {
                                    Stream<Integer> test() {
                                        return Stream.of(1, 2);
                                    }
                                }
                                """
                ));
    }

    @Test
    void testStreamOf3Recipe() {
        Recipe recipe = RewriteUtils.loadRecipe("StreamRulesRecipes.StreamOf3Recipe");
        rewriteRun(
                spec -> spec.recipe(recipe)
                        .expectedCyclesThatMakeChanges(1).cycles(1)
                        .parser(JavaParser.fromJavaVersion().classpath(JavaParser.runtimeClasspath())),
                java(
                        """
                                import com.google.common.collect.ImmutableList;
                                
                                import java.util.stream.Stream;
                                
                                class Test {
                                    Stream<Integer> test() {
                                        return ImmutableList.of(1, 2, 3).stream();
                                    }
                                }
                                """,
                        """
                                import java.util.stream.Stream;
                                
                                class Test {
                                    Stream<Integer> test() {
                                        return Stream.of(1, 2, 3);
                                    }
                                }
                                """
                ));
    }

    @Test
    void testStreamOf4Recipe() {
        Recipe recipe = RewriteUtils.loadRecipe("StreamRulesRecipes.StreamOf4Recipe");
        rewriteRun(
                spec -> spec.recipe(recipe)
                        .expectedCyclesThatMakeChanges(1).cycles(1)
                        .parser(JavaParser.fromJavaVersion().classpath(JavaParser.runtimeClasspath())),
                java(
                        """
                                import com.google.common.collect.ImmutableList;
                                
                                import java.util.stream.Stream;
                                
                                class Test {
                                    Stream<Integer> test() {
                                        return ImmutableList.of(1, 2, 3, 4).stream();
                                    }
                                }
                                """,
                        """
                                import java.util.stream.Stream;
                                
                                class Test {
                                    Stream<Integer> test() {
                                        return Stream.of(1, 2, 3, 4);
                                    }
                                }
                                """
                ));
    }

    @Test
    void testStreamOf5Recipe() {
        Recipe recipe = RewriteUtils.loadRecipe("StreamRulesRecipes.StreamOf5Recipe");
        rewriteRun(
                spec -> spec.recipe(recipe)
                        .expectedCyclesThatMakeChanges(1).cycles(1)
                        .parser(JavaParser.fromJavaVersion().classpath(JavaParser.runtimeClasspath())),
                java(
                        """
                                import com.google.common.collect.ImmutableList;
                                
                                import java.util.stream.Stream;
                                
                                class Test {
                                    Stream<Integer> test() {
                                        return ImmutableList.of(1, 2, 3, 4, 5).stream();
                                    }
                                }
                                """,
                        """
                                import java.util.stream.Stream;
                                
                                class Test {
                                    Stream<Integer> test() {
                                        return Stream.of(1, 2, 3, 4, 5);
                                    }
                                }
                                """
                ));
    }

    @Test
    void testStreamOfArrayRecipe() {
        Recipe recipe = RewriteUtils.loadRecipe("StreamRulesRecipes.StreamOfArrayRecipe");
        rewriteRun(
                spec -> spec.recipe(recipe)
                        .expectedCyclesThatMakeChanges(1).cycles(1)
                        .parser(JavaParser.fromJavaVersion().classpath(JavaParser.runtimeClasspath())),
                java(
                        """
                                import java.util.stream.Stream;
                                
                                class Test {
                                    Stream<String> test() {
                                        return Stream.of(new String[]{"foo", "bar"});
                                    }
                                }
                                """,
                        """
                                import java.util.Arrays;
                                import java.util.stream.Stream;
                                
                                class Test {
                                    Stream<String> test() {
                                        return Arrays.stream(new String[]{"foo", "bar"});
                                    }
                                }
                                """
                ));
    }

    @Test
    void testStreamOfNullableRecipe() {
        Recipe recipe = RewriteUtils.loadRecipe("StreamRulesRecipes.StreamOfNullableRecipe");
        rewriteRun(
                spec -> spec.recipe(recipe)
                        .expectedCyclesThatMakeChanges(1).cycles(1)
                        .parser(JavaParser.fromJavaVersion().classpath(JavaParser.runtimeClasspath())),
                java(
                        """
                                import com.google.common.collect.ImmutableSet;
                                
                                import java.util.Objects;
                                import java.util.Optional;
                                import java.util.function.Predicate;
                                import java.util.stream.Stream;
                                
                                class Test {
                                    ImmutableSet<Stream<String>> test() {
                                        return ImmutableSet.of(Stream.of("a").filter(Objects::nonNull), Optional.ofNullable("b").stream());
                                    }
                                }
                                """,
                        """
                                import com.google.common.collect.ImmutableSet;
                                
                                import java.util.stream.Stream;
                                
                                class Test {
                                    ImmutableSet<Stream<String>> test() {
                                        return ImmutableSet.of(Stream.ofNullable("a"), Stream.ofNullable("b"));
                                    }
                                }
                                """
                ));
    }

    @Test
    void testStreamReduceRecipe() {
        Recipe recipe = RewriteUtils.loadRecipe("StreamRulesRecipes.StreamReduceRecipe");
        rewriteRun(
                spec -> spec.recipe(recipe)
                        .expectedCyclesThatMakeChanges(1).cycles(1)
                        .parser(JavaParser.fromJavaVersion().classpath(JavaParser.runtimeClasspath())),
                java(
                        """
                                import java.util.Optional;
                                import java.util.stream.Collectors;
                                import java.util.stream.Stream;
                                
                                class Test {
                                    Optional<Integer> test() {
                                        return Stream.of(1).collect(Collectors.reducing(Integer::sum));
                                    }
                                }
                                """,
                        """
                                import java.util.Optional;
                                import java.util.stream.Stream;
                                
                                class Test {
                                    Optional<Integer> test() {
                                        return Stream.of(1).reduce(Integer::sum);
                                    }
                                }
                                """
                ));
    }

    @Test
    void testStreamReduceWithIdentityRecipe() {
        Recipe recipe = RewriteUtils.loadRecipe("StreamRulesRecipes.StreamReduceWithIdentityRecipe");
        rewriteRun(
                spec -> spec.recipe(recipe)
                        .expectedCyclesThatMakeChanges(1).cycles(1)
                        .parser(JavaParser.fromJavaVersion().classpath(JavaParser.runtimeClasspath())),
                java(
                        """
                                import java.util.stream.Collectors;
                                import java.util.stream.Stream;
                                
                                class Test {
                                    Integer test() {
                                        return Stream.of(1).collect(Collectors.reducing(0, Integer::sum));
                                    }
                                }
                                """,
                        """
                                import java.util.stream.Stream;
                                
                                class Test {
                                    Integer test() {
                                        return Stream.of(1).reduce(0, Integer::sum);
                                    }
                                }
                                """
                ));
    }

    @Test
    void testStreamTakeWhileRecipe() {
        Recipe recipe = RewriteUtils.loadRecipe("StreamRulesRecipes.StreamTakeWhileRecipe");
        rewriteRun(
                spec -> spec.recipe(recipe)
                        .expectedCyclesThatMakeChanges(1).cycles(1)
                        .parser(JavaParser.fromJavaVersion().classpath(JavaParser.runtimeClasspath())),
                java(
                        """
                                import java.util.stream.Stream;
                                
                                class Test {
                                    Stream<Integer> test() {
                                        return Stream.of(1, 2, 3).takeWhile(i -> i < 2).filter(i -> i < 2);
                                    }
                                }
                                """,
                        """
                                import java.util.stream.Stream;
                                
                                class Test {
                                    Stream<Integer> test() {
                                        return Stream.of(1, 2, 3).takeWhile(i -> i < 2);
                                    }
                                }
                                """
                ));
    }

    @Test
    void testStreamsConcatRecipe() {
        Recipe recipe = RewriteUtils.loadRecipe("StreamRulesRecipes.StreamsConcatRecipe");
        rewriteRun(
                spec -> spec.recipe(recipe)
                        .expectedCyclesThatMakeChanges(1).cycles(1)
                        .parser(JavaParser.fromJavaVersion().classpath(JavaParser.runtimeClasspath())),
                java(
                        """
                                import com.google.common.collect.ImmutableSet;
                                
                                import java.util.function.Function;
                                import java.util.stream.Stream;
                                
                                class Test {
                                    ImmutableSet<Stream<Integer>> test() {
                                        return ImmutableSet.of(Stream.of(Stream.of(1), Stream.of(2)).flatMap(Function.identity()), Stream.of(Stream.of(3), Stream.of(4)).flatMap(v -> v), Stream.of(Stream.of(5), Stream.of(6)).flatMap(v -> Stream.empty()));
                                    }
                                }
                                """,
                        """
                                import com.google.common.collect.ImmutableSet;
                                import com.google.common.collect.Streams;
                                
                                import java.util.stream.Stream;
                                
                                class Test {
                                    ImmutableSet<Stream<Integer>> test() {
                                        return ImmutableSet.of(Streams.concat(Stream.of(1), Stream.of(2)), Streams.concat(Stream.of(3), Stream.of(4)), Stream.of(Stream.of(5), Stream.of(6)).flatMap(v -> Stream.empty()));
                                    }
                                }
                                """
                ));
    }
}
