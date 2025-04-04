package tech.picnic.errorprone.refasterrules.rewrite;

import org.junit.jupiter.api.Test;
import org.openrewrite.Recipe;
import org.openrewrite.java.JavaParser;
import org.openrewrite.test.RewriteTest;

import static org.openrewrite.java.Assertions.java;

final class IntStreamRulesRecipesTest implements RewriteTest {

    @Test
    void testConcatOneIntStreamRecipe() {
        Recipe recipe = RewriteUtils.loadRecipe("IntStreamRulesRecipes.ConcatOneIntStreamRecipe");
        rewriteRun(
                spec -> spec.recipe(recipe)
                        .expectedCyclesThatMakeChanges(1).cycles(1)
                        .parser(JavaParser.fromJavaVersion().classpath(JavaParser.runtimeClasspath())),
                java(
                        """
                                import com.google.common.collect.Streams;
                                
                                import java.util.stream.IntStream;
                                
                                class Test {
                                    IntStream test() {
                                        return Streams.concat(IntStream.of(1));
                                    }
                                }
                                """,
                        """
                                import java.util.stream.IntStream;
                                
                                class Test {
                                    IntStream test() {
                                        return IntStream.of(1);
                                    }
                                }
                                """
                ));
    }

    @Test
    void testConcatTwoIntStreamsRecipe() {
        Recipe recipe = RewriteUtils.loadRecipe("IntStreamRulesRecipes.ConcatTwoIntStreamsRecipe");
        rewriteRun(
                spec -> spec.recipe(recipe)
                        .expectedCyclesThatMakeChanges(1).cycles(1)
                        .parser(JavaParser.fromJavaVersion().classpath(JavaParser.runtimeClasspath())),
                java(
                        """
                                import com.google.common.collect.Streams;
                                
                                import java.util.stream.IntStream;
                                
                                class Test {
                                    IntStream test() {
                                        return Streams.concat(IntStream.of(1), IntStream.of(2));
                                    }
                                }
                                """,
                        """
                                import java.util.stream.IntStream;
                                
                                class Test {
                                    IntStream test() {
                                        return IntStream.concat(IntStream.of(1), IntStream.of(2));
                                    }
                                }
                                """
                ));
    }

    @Test
    void testFilterOuterIntStreamAfterFlatMapRecipe() {
        Recipe recipe = RewriteUtils.loadRecipe("IntStreamRulesRecipes.FilterOuterIntStreamAfterFlatMapRecipe");
        rewriteRun(
                spec -> spec.recipe(recipe)
                        .expectedCyclesThatMakeChanges(1).cycles(1)
                        .parser(JavaParser.fromJavaVersion().classpath(JavaParser.runtimeClasspath())),
                java(
                        """
                                import java.util.stream.IntStream;
                                
                                class Test {
                                    IntStream test() {
                                        return IntStream.of(1).flatMap(v -> IntStream.of(v * v).filter(n -> n > 1));
                                    }
                                }
                                """,
                        """
                                import java.util.stream.IntStream;
                                
                                class Test {
                                    IntStream test() {
                                        return IntStream.of(1).flatMap(v -> IntStream.of(v * v)).filter(n -> n > 1);
                                    }
                                }
                                """
                ));
    }

    @Test
    void testFilterOuterStreamAfterFlatMapToIntRecipe() {
        Recipe recipe = RewriteUtils.loadRecipe("IntStreamRulesRecipes.FilterOuterStreamAfterFlatMapToIntRecipe");
        rewriteRun(
                spec -> spec.recipe(recipe)
                        .expectedCyclesThatMakeChanges(1).cycles(1)
                        .parser(JavaParser.fromJavaVersion().classpath(JavaParser.runtimeClasspath())),
                java(
                        """
                                import java.util.stream.IntStream;
                                import java.util.stream.Stream;
                                
                                class Test {
                                    IntStream test() {
                                        return Stream.of(1).flatMapToInt(v -> IntStream.of(v * v).filter(n -> n > 1));
                                    }
                                }
                                """,
                        """
                                import java.util.stream.IntStream;
                                import java.util.stream.Stream;
                                
                                class Test {
                                    IntStream test() {
                                        return Stream.of(1).flatMapToInt(v -> IntStream.of(v * v)).filter(n -> n > 1);
                                    }
                                }
                                """
                ));
    }

    @Test
    void testFlatMapOuterIntStreamAfterFlatMapRecipe() {
        Recipe recipe = RewriteUtils.loadRecipe("IntStreamRulesRecipes.FlatMapOuterIntStreamAfterFlatMapRecipe");
        rewriteRun(
                spec -> spec.recipe(recipe)
                        .expectedCyclesThatMakeChanges(1).cycles(1)
                        .parser(JavaParser.fromJavaVersion().classpath(JavaParser.runtimeClasspath())),
                java(
                        """
                                import java.util.stream.IntStream;
                                
                                class Test {
                                    IntStream test() {
                                        return IntStream.of(1).flatMap(v -> IntStream.of(v * v).flatMap(IntStream::of));
                                    }
                                }
                                """,
                        """
                                import java.util.stream.IntStream;
                                
                                class Test {
                                    IntStream test() {
                                        return IntStream.of(1).flatMap(v -> IntStream.of(v * v)).flatMap(IntStream::of);
                                    }
                                }
                                """
                ));
    }

    @Test
    void testFlatMapOuterStreamAfterFlatMapToIntRecipe() {
        Recipe recipe = RewriteUtils.loadRecipe("IntStreamRulesRecipes.FlatMapOuterStreamAfterFlatMapToIntRecipe");
        rewriteRun(
                spec -> spec.recipe(recipe)
                        .expectedCyclesThatMakeChanges(1).cycles(1)
                        .parser(JavaParser.fromJavaVersion().classpath(JavaParser.runtimeClasspath())),
                java(
                        """
                                import java.util.stream.IntStream;
                                import java.util.stream.Stream;
                                
                                class Test {
                                    IntStream test() {
                                        return Stream.of(1).flatMapToInt(v -> IntStream.of(v * v).flatMap(IntStream::of));
                                    }
                                }
                                """,
                        """
                                import java.util.stream.IntStream;
                                import java.util.stream.Stream;
                                
                                class Test {
                                    IntStream test() {
                                        return Stream.of(1).flatMapToInt(v -> IntStream.of(v * v)).flatMap(IntStream::of);
                                    }
                                }
                                """
                ));
    }

    @Test
    void testIntStreamAllMatchRecipe() {
        Recipe recipe = RewriteUtils.loadRecipe("IntStreamRulesRecipes.IntStreamAllMatchRecipe");
        rewriteRun(
                spec -> spec.recipe(recipe)
                        .expectedCyclesThatMakeChanges(1).cycles(1)
                        .parser(JavaParser.fromJavaVersion().classpath(JavaParser.runtimeClasspath())),
                java(
                        """
                                import java.util.function.IntPredicate;
                                import java.util.stream.IntStream;
                                
                                class Test {
                                    boolean test() {
                                        IntPredicate pred = i -> i > 0;
                                        return IntStream.of(1).noneMatch(pred.negate());
                                    }
                                }
                                """,
                        """
                                import java.util.function.IntPredicate;
                                import java.util.stream.IntStream;
                                
                                class Test {
                                    boolean test() {
                                        IntPredicate pred = i -> i > 0;
                                        return IntStream.of(1).allMatch(pred);
                                    }
                                }
                                """
                ));
    }

    @Test
    void testIntStreamAllMatch2Recipe() {
        Recipe recipe = RewriteUtils.loadRecipe("IntStreamRulesRecipes.IntStreamAllMatch2Recipe");
        rewriteRun(
                spec -> spec.recipe(recipe)
                        .expectedCyclesThatMakeChanges(1).cycles(1)
                        .parser(JavaParser.fromJavaVersion().classpath(JavaParser.runtimeClasspath())),
                java(
                        """
                                import java.util.stream.IntStream;
                                
                                class Test {
                                    boolean test() {
                                        return IntStream.of(1).noneMatch(n -> !(n > 1));
                                    }
                                }
                                """,
                        """
                                import java.util.stream.IntStream;
                                
                                class Test {
                                    boolean test() {
                                        return IntStream.of(1).allMatch(n -> n > 1);
                                    }
                                }
                                """
                ));
    }

    @Test
    void testIntStreamAnyMatchRecipe() {
        Recipe recipe = RewriteUtils.loadRecipe("IntStreamRulesRecipes.IntStreamAnyMatchRecipe");
        rewriteRun(
                spec -> spec.recipe(recipe)
                        .expectedCyclesThatMakeChanges(1).cycles(1)
                        .parser(JavaParser.fromJavaVersion().classpath(JavaParser.runtimeClasspath())),
                java(
                        """
                                import com.google.common.collect.ImmutableSet;
                                
                                import java.util.stream.IntStream;
                                
                                class Test {
                                    ImmutableSet<Boolean> test() {
                                        return ImmutableSet.of(!IntStream.of(1).noneMatch(n -> n > 1), IntStream.of(2).filter(n -> n > 2).findAny().isPresent());
                                    }
                                }
                                """,
                        """
                                import com.google.common.collect.ImmutableSet;
                                
                                import java.util.stream.IntStream;
                                
                                class Test {
                                    ImmutableSet<Boolean> test() {
                                        return ImmutableSet.of(IntStream.of(1).anyMatch(n -> n > 1), IntStream.of(2).anyMatch(n -> n > 2));
                                    }
                                }
                                """
                ));
    }

    @Test
    void testIntStreamClosedOpenRangeRecipe() {
        Recipe recipe = RewriteUtils.loadRecipe("IntStreamRulesRecipes.IntStreamClosedOpenRangeRecipe");
        rewriteRun(
                spec -> spec.recipe(recipe)
                        .expectedCyclesThatMakeChanges(1).cycles(1)
                        .parser(JavaParser.fromJavaVersion().classpath(JavaParser.runtimeClasspath())),
                java(
                        """
                                import java.util.stream.IntStream;
                                
                                class Test {
                                    IntStream test() {
                                        return IntStream.rangeClosed(0, 42 - 1);
                                    }
                                }
                                """,
                        """
                                import java.util.stream.IntStream;
                                
                                class Test {
                                    IntStream test() {
                                        return IntStream.range(0, 42);
                                    }
                                }
                                """
                ));
    }

    @Test
    void testIntStreamFilterSortedRecipe() {
        Recipe recipe = RewriteUtils.loadRecipe("IntStreamRulesRecipes.IntStreamFilterSortedRecipe");
        rewriteRun(
                spec -> spec.recipe(recipe)
                        .expectedCyclesThatMakeChanges(1).cycles(1)
                        .parser(JavaParser.fromJavaVersion().classpath(JavaParser.runtimeClasspath())),
                java(
                        """
                                import java.util.stream.IntStream;
                                
                                class Test {
                                    IntStream test() {
                                        return IntStream.of(1, 4, 3, 2).sorted().filter(i -> i % 2 == 0);
                                    }
                                }
                                """,
                        """
                                import java.util.stream.IntStream;
                                
                                class Test {
                                    IntStream test() {
                                        return IntStream.of(1, 4, 3, 2).filter(i -> i % 2 == 0).sorted();
                                    }
                                }
                                """
                ));
    }

    @Test
    void testIntStreamIsEmptyRecipe() {
        Recipe recipe = RewriteUtils.loadRecipe("IntStreamRulesRecipes.IntStreamIsEmptyRecipe");
        rewriteRun(
                spec -> spec.recipe(recipe)
                        .expectedCyclesThatMakeChanges(1).cycles(1)
                        .parser(JavaParser.fromJavaVersion().classpath(JavaParser.runtimeClasspath())),
                java(
                        """
                                import com.google.common.collect.ImmutableSet;
                                
                                import java.util.stream.IntStream;
                                
                                class Test {
                                    ImmutableSet<Boolean> test() {
                                        return ImmutableSet.of(IntStream.of(1).count() == 0, IntStream.of(2).count() <= 0, IntStream.of(3).count() < 1, IntStream.of(4).findFirst().isEmpty());
                                    }
                                }
                                """,
                        """
                                import com.google.common.collect.ImmutableSet;
                                
                                import java.util.stream.IntStream;
                                
                                class Test {
                                    ImmutableSet<Boolean> test() {
                                        return ImmutableSet.of(IntStream.of(1).findAny().isEmpty(), IntStream.of(2).findAny().isEmpty(), IntStream.of(3).findAny().isEmpty(), IntStream.of(4).findAny().isEmpty());
                                    }
                                }
                                """
                ));
    }

    @Test
    void testIntStreamIsNotEmptyRecipe() {
        Recipe recipe = RewriteUtils.loadRecipe("IntStreamRulesRecipes.IntStreamIsNotEmptyRecipe");
        rewriteRun(
                spec -> spec.recipe(recipe)
                        .expectedCyclesThatMakeChanges(1).cycles(1)
                        .parser(JavaParser.fromJavaVersion().classpath(JavaParser.runtimeClasspath())),
                java(
                        """
                                import com.google.common.collect.ImmutableSet;
                                
                                import java.util.stream.IntStream;
                                
                                class Test {
                                    ImmutableSet<Boolean> test() {
                                        return ImmutableSet.of(IntStream.of(1).count() != 0, IntStream.of(2).count() > 0, IntStream.of(3).count() >= 1, IntStream.of(4).findFirst().isPresent());
                                    }
                                }
                                """,
                        """
                                import com.google.common.collect.ImmutableSet;
                                
                                import java.util.stream.IntStream;
                                
                                class Test {
                                    ImmutableSet<Boolean> test() {
                                        return ImmutableSet.of(IntStream.of(1).findAny().isPresent(), IntStream.of(2).findAny().isPresent(), IntStream.of(3).findAny().isPresent(), IntStream.of(4).findAny().isPresent());
                                    }
                                }
                                """
                ));
    }

    @Test
    void testIntStreamMinRecipe() {
        Recipe recipe = RewriteUtils.loadRecipe("IntStreamRulesRecipes.IntStreamMinRecipe");
        rewriteRun(
                spec -> spec.recipe(recipe)
                        .expectedCyclesThatMakeChanges(1).cycles(1)
                        .parser(JavaParser.fromJavaVersion().classpath(JavaParser.runtimeClasspath())),
                java(
                        """
                                import java.util.OptionalInt;
                                import java.util.stream.IntStream;
                                
                                class Test {
                                    OptionalInt test() {
                                        return IntStream.of(1).sorted().findFirst();
                                    }
                                }
                                """,
                        """
                                import java.util.OptionalInt;
                                import java.util.stream.IntStream;
                                
                                class Test {
                                    OptionalInt test() {
                                        return IntStream.of(1).min();
                                    }
                                }
                                """
                ));
    }

    @Test
    void testIntStreamNoneMatchRecipe() {
        Recipe recipe = RewriteUtils.loadRecipe("IntStreamRulesRecipes.IntStreamNoneMatchRecipe");
        rewriteRun(
                spec -> spec.recipe(recipe)
                        .expectedCyclesThatMakeChanges(1).cycles(1)
                        .parser(JavaParser.fromJavaVersion().classpath(JavaParser.runtimeClasspath())),
                java(
                        """
                                import com.google.common.collect.ImmutableSet;
                                
                                import java.util.function.IntPredicate;
                                import java.util.stream.IntStream;
                                
                                class Test {
                                    ImmutableSet<Boolean> test() {
                                        IntPredicate pred = i -> i > 0;
                                        return ImmutableSet.of(!IntStream.of(1).anyMatch(n -> n > 1), IntStream.of(2).allMatch(pred.negate()), IntStream.of(3).filter(pred).findAny().isEmpty());
                                    }
                                }
                                """,
                        """
                                import com.google.common.collect.ImmutableSet;
                                
                                import java.util.function.IntPredicate;
                                import java.util.stream.IntStream;
                                
                                class Test {
                                    ImmutableSet<Boolean> test() {
                                        IntPredicate pred = i -> i > 0;
                                        return ImmutableSet.of(IntStream.of(1).noneMatch(n -> n > 1), IntStream.of(2).noneMatch(pred), IntStream.of(3).noneMatch(pred));
                                    }
                                }
                                """
                ));
    }

    @Test
    void testIntStreamNoneMatch2Recipe() {
        Recipe recipe = RewriteUtils.loadRecipe("IntStreamRulesRecipes.IntStreamNoneMatch2Recipe");
        rewriteRun(
                spec -> spec.recipe(recipe)
                        .expectedCyclesThatMakeChanges(1).cycles(1)
                        .parser(JavaParser.fromJavaVersion().classpath(JavaParser.runtimeClasspath())),
                java(
                        """
                                import java.util.stream.IntStream;
                                
                                class Test {
                                    boolean test() {
                                        return IntStream.of(1).allMatch(n -> !(n > 1));
                                    }
                                }
                                """,
                        """
                                import java.util.stream.IntStream;
                                
                                class Test {
                                    boolean test() {
                                        return IntStream.of(1).noneMatch(n -> n > 1);
                                    }
                                }
                                """
                ));
    }

    @Test
    void testIntStreamTakeWhileRecipe() {
        Recipe recipe = RewriteUtils.loadRecipe("IntStreamRulesRecipes.IntStreamTakeWhileRecipe");
        rewriteRun(
                spec -> spec.recipe(recipe)
                        .expectedCyclesThatMakeChanges(1).cycles(1)
                        .parser(JavaParser.fromJavaVersion().classpath(JavaParser.runtimeClasspath())),
                java(
                        """
                                import java.util.stream.IntStream;
                                
                                class Test {
                                    IntStream test() {
                                        return IntStream.of(1, 2, 3).takeWhile(i -> i < 2).filter(i -> i < 2);
                                    }
                                }
                                """,
                        """
                                import java.util.stream.IntStream;
                                
                                class Test {
                                    IntStream test() {
                                        return IntStream.of(1, 2, 3).takeWhile(i -> i < 2);
                                    }
                                }
                                """
                ));
    }

    @Test
    void testMapOuterIntStreamAfterFlatMapRecipe() {
        Recipe recipe = RewriteUtils.loadRecipe("IntStreamRulesRecipes.MapOuterIntStreamAfterFlatMapRecipe");
        rewriteRun(
                spec -> spec.recipe(recipe)
                        .expectedCyclesThatMakeChanges(1).cycles(1)
                        .parser(JavaParser.fromJavaVersion().classpath(JavaParser.runtimeClasspath())),
                java(
                        """
                                import java.util.stream.IntStream;
                                
                                class Test {
                                    IntStream test() {
                                        return IntStream.of(1).flatMap(v -> IntStream.of(v * v).map(n -> n * 1));
                                    }
                                }
                                """,
                        """
                                import java.util.stream.IntStream;
                                
                                class Test {
                                    IntStream test() {
                                        return IntStream.of(1).flatMap(v -> IntStream.of(v * v)).map(n -> n * 1);
                                    }
                                }
                                """
                ));
    }

    @Test
    void testMapOuterStreamAfterFlatMapToIntRecipe() {
        Recipe recipe = RewriteUtils.loadRecipe("IntStreamRulesRecipes.MapOuterStreamAfterFlatMapToIntRecipe");
        rewriteRun(
                spec -> spec.recipe(recipe)
                        .expectedCyclesThatMakeChanges(1).cycles(1)
                        .parser(JavaParser.fromJavaVersion().classpath(JavaParser.runtimeClasspath())),
                java(
                        """
                                import java.util.stream.IntStream;
                                import java.util.stream.Stream;
                                
                                class Test {
                                    IntStream test() {
                                        return Stream.of(1).flatMapToInt(v -> IntStream.of(v * v).map(n -> n * 1));
                                    }
                                }
                                """,
                        """
                                import java.util.stream.IntStream;
                                import java.util.stream.Stream;
                                
                                class Test {
                                    IntStream test() {
                                        return Stream.of(1).flatMapToInt(v -> IntStream.of(v * v)).map(n -> n * 1);
                                    }
                                }
                                """
                ));
    }
}
