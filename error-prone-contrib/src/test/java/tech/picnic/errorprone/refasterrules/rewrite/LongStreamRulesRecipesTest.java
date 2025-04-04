package tech.picnic.errorprone.refasterrules.rewrite;

import org.junit.jupiter.api.Test;
import org.openrewrite.Recipe;
import org.openrewrite.java.JavaParser;
import org.openrewrite.test.RewriteTest;

import static org.openrewrite.java.Assertions.java;

final class LongStreamRulesRecipesTest implements RewriteTest {

    @Test
    void testConcatOneLongStreamRecipe() {
        Recipe recipe = RewriteUtils.loadRecipe("LongStreamRulesRecipes.ConcatOneLongStreamRecipe");
        rewriteRun(
                spec -> spec.recipe(recipe)
                        .expectedCyclesThatMakeChanges(1).cycles(1)
                        .parser(JavaParser.fromJavaVersion().classpath(JavaParser.runtimeClasspath())),
                java(
                        """
                                import com.google.common.collect.Streams;
                                
                                import java.util.stream.LongStream;
                                
                                class Test {
                                    LongStream test() {
                                        return Streams.concat(LongStream.of(1));
                                    }
                                }
                                """,
                        """
                                import java.util.stream.LongStream;
                                
                                class Test {
                                    LongStream test() {
                                        return LongStream.of(1);
                                    }
                                }
                                """
                ));
    }

    @Test
    void testConcatTwoLongStreamsRecipe() {
        Recipe recipe = RewriteUtils.loadRecipe("LongStreamRulesRecipes.ConcatTwoLongStreamsRecipe");
        rewriteRun(
                spec -> spec.recipe(recipe)
                        .expectedCyclesThatMakeChanges(1).cycles(1)
                        .parser(JavaParser.fromJavaVersion().classpath(JavaParser.runtimeClasspath())),
                java(
                        """
                                import com.google.common.collect.Streams;
                                
                                import java.util.stream.LongStream;
                                
                                class Test {
                                    LongStream test() {
                                        return Streams.concat(LongStream.of(1), LongStream.of(2));
                                    }
                                }
                                """,
                        """
                                import java.util.stream.LongStream;
                                
                                class Test {
                                    LongStream test() {
                                        return LongStream.concat(LongStream.of(1), LongStream.of(2));
                                    }
                                }
                                """
                ));
    }

    @Test
    void testFilterOuterLongStreamAfterFlatMapRecipe() {
        Recipe recipe = RewriteUtils.loadRecipe("LongStreamRulesRecipes.FilterOuterLongStreamAfterFlatMapRecipe");
        rewriteRun(
                spec -> spec.recipe(recipe)
                        .expectedCyclesThatMakeChanges(1).cycles(1)
                        .parser(JavaParser.fromJavaVersion().classpath(JavaParser.runtimeClasspath())),
                java(
                        """
                                import java.util.stream.LongStream;
                                
                                class Test {
                                    LongStream test() {
                                        return LongStream.of(1).flatMap(v -> LongStream.of(v * v).filter(n -> n > 1));
                                    }
                                }
                                """,
                        """
                                import java.util.stream.LongStream;
                                
                                class Test {
                                    LongStream test() {
                                        return LongStream.of(1).flatMap(v -> LongStream.of(v * v)).filter(n -> n > 1);
                                    }
                                }
                                """
                ));
    }

    @Test
    void testFilterOuterStreamAfterFlatMapToLongRecipe() {
        Recipe recipe = RewriteUtils.loadRecipe("LongStreamRulesRecipes.FilterOuterStreamAfterFlatMapToLongRecipe");
        rewriteRun(
                spec -> spec.recipe(recipe)
                        .expectedCyclesThatMakeChanges(1).cycles(1)
                        .parser(JavaParser.fromJavaVersion().classpath(JavaParser.runtimeClasspath())),
                java(
                        """
                                import java.util.stream.LongStream;
                                import java.util.stream.Stream;
                                
                                class Test {
                                    LongStream test() {
                                        return Stream.of(1).flatMapToLong(v -> LongStream.of(v * v).filter(n -> n > 1));
                                    }
                                }
                                """,
                        """
                                import java.util.stream.LongStream;
                                import java.util.stream.Stream;
                                
                                class Test {
                                    LongStream test() {
                                        return Stream.of(1).flatMapToLong(v -> LongStream.of(v * v)).filter(n -> n > 1);
                                    }
                                }
                                """
                ));
    }

    @Test
    void testFlatMapOuterLongStreamAfterFlatMapRecipe() {
        Recipe recipe = RewriteUtils.loadRecipe("LongStreamRulesRecipes.FlatMapOuterLongStreamAfterFlatMapRecipe");
        rewriteRun(
                spec -> spec.recipe(recipe)
                        .expectedCyclesThatMakeChanges(1).cycles(1)
                        .parser(JavaParser.fromJavaVersion().classpath(JavaParser.runtimeClasspath())),
                java(
                        """
                                import java.util.stream.LongStream;
                                
                                class Test {
                                    LongStream test() {
                                        return LongStream.of(1).flatMap(v -> LongStream.of(v * v).flatMap(LongStream::of));
                                    }
                                }
                                """,
                        """
                                import java.util.stream.LongStream;
                                
                                class Test {
                                    LongStream test() {
                                        return LongStream.of(1).flatMap(v -> LongStream.of(v * v)).flatMap(LongStream::of);
                                    }
                                }
                                """
                ));
    }

    @Test
    void testFlatMapOuterStreamAfterFlatMapToLongRecipe() {
        Recipe recipe = RewriteUtils.loadRecipe("LongStreamRulesRecipes.FlatMapOuterStreamAfterFlatMapToLongRecipe");
        rewriteRun(
                spec -> spec.recipe(recipe)
                        .expectedCyclesThatMakeChanges(1).cycles(1)
                        .parser(JavaParser.fromJavaVersion().classpath(JavaParser.runtimeClasspath())),
                java(
                        """
                                import java.util.stream.LongStream;
                                import java.util.stream.Stream;
                                
                                class Test {
                                    LongStream test() {
                                        return Stream.of(1).flatMapToLong(v -> LongStream.of(v * v).flatMap(LongStream::of));
                                    }
                                }
                                """,
                        """
                                import java.util.stream.LongStream;
                                import java.util.stream.Stream;
                                
                                class Test {
                                    LongStream test() {
                                        return Stream.of(1).flatMapToLong(v -> LongStream.of(v * v)).flatMap(LongStream::of);
                                    }
                                }
                                """
                ));
    }

    @Test
    void testLongStreamAllMatchRecipe() {
        Recipe recipe = RewriteUtils.loadRecipe("LongStreamRulesRecipes.LongStreamAllMatchRecipe");
        rewriteRun(
                spec -> spec.recipe(recipe)
                        .expectedCyclesThatMakeChanges(1).cycles(1)
                        .parser(JavaParser.fromJavaVersion().classpath(JavaParser.runtimeClasspath())),
                java(
                        """
                                import java.util.function.LongPredicate;
                                import java.util.stream.LongStream;
                                
                                class Test {
                                    boolean test() {
                                        LongPredicate pred = i -> i > 0;
                                        return LongStream.of(1).noneMatch(pred.negate());
                                    }
                                }
                                """,
                        """
                                import java.util.function.LongPredicate;
                                import java.util.stream.LongStream;
                                
                                class Test {
                                    boolean test() {
                                        LongPredicate pred = i -> i > 0;
                                        return LongStream.of(1).allMatch(pred);
                                    }
                                }
                                """
                ));
    }

    @Test
    void testLongStreamAllMatch2Recipe() {
        Recipe recipe = RewriteUtils.loadRecipe("LongStreamRulesRecipes.LongStreamAllMatch2Recipe");
        rewriteRun(
                spec -> spec.recipe(recipe)
                        .expectedCyclesThatMakeChanges(1).cycles(1)
                        .parser(JavaParser.fromJavaVersion().classpath(JavaParser.runtimeClasspath())),
                java(
                        """
                                import java.util.stream.LongStream;
                                
                                class Test {
                                    boolean test() {
                                        return LongStream.of(1).noneMatch(n -> !(n > 1));
                                    }
                                }
                                """,
                        """
                                import java.util.stream.LongStream;
                                
                                class Test {
                                    boolean test() {
                                        return LongStream.of(1).allMatch(n -> n > 1);
                                    }
                                }
                                """
                ));
    }

    @Test
    void testLongStreamAnyMatchRecipe() {
        Recipe recipe = RewriteUtils.loadRecipe("LongStreamRulesRecipes.LongStreamAnyMatchRecipe");
        rewriteRun(
                spec -> spec.recipe(recipe)
                        .expectedCyclesThatMakeChanges(1).cycles(1)
                        .parser(JavaParser.fromJavaVersion().classpath(JavaParser.runtimeClasspath())),
                java(
                        """
                                import com.google.common.collect.ImmutableSet;
                                
                                import java.util.stream.LongStream;
                                
                                class Test {
                                    ImmutableSet<Boolean> test() {
                                        return ImmutableSet.of(!LongStream.of(1).noneMatch(n -> n > 1), LongStream.of(2).filter(n -> n > 2).findAny().isPresent());
                                    }
                                }
                                """,
                        """
                                import com.google.common.collect.ImmutableSet;
                                
                                import java.util.stream.LongStream;
                                
                                class Test {
                                    ImmutableSet<Boolean> test() {
                                        return ImmutableSet.of(LongStream.of(1).anyMatch(n -> n > 1), LongStream.of(2).anyMatch(n -> n > 2));
                                    }
                                }
                                """
                ));
    }

    @Test
    void testLongStreamClosedOpenRangeRecipe() {
        Recipe recipe = RewriteUtils.loadRecipe("LongStreamRulesRecipes.LongStreamClosedOpenRangeRecipe");
        rewriteRun(
                spec -> spec.recipe(recipe)
                        .expectedCyclesThatMakeChanges(1).cycles(1)
                        .parser(JavaParser.fromJavaVersion().classpath(JavaParser.runtimeClasspath())),
                java(
                        """
                                import java.util.stream.LongStream;
                                
                                class Test {
                                    LongStream test() {
                                        return LongStream.rangeClosed(0, 42 - 1);
                                    }
                                }
                                """,
                        """
                                import java.util.stream.LongStream;
                                
                                class Test {
                                    LongStream test() {
                                        return LongStream.range(0, 42);
                                    }
                                }
                                """
                ));
    }

    @Test
    void testLongStreamFilterSortedRecipe() {
        Recipe recipe = RewriteUtils.loadRecipe("LongStreamRulesRecipes.LongStreamFilterSortedRecipe");
        rewriteRun(
                spec -> spec.recipe(recipe)
                        .expectedCyclesThatMakeChanges(1).cycles(1)
                        .parser(JavaParser.fromJavaVersion().classpath(JavaParser.runtimeClasspath())),
                java(
                        """
                                import java.util.stream.LongStream;
                                
                                class Test {
                                    LongStream test() {
                                        return LongStream.of(1, 4, 3, 2).sorted().filter(l -> l % 2 == 0);
                                    }
                                }
                                """,
                        """
                                import java.util.stream.LongStream;
                                
                                class Test {
                                    LongStream test() {
                                        return LongStream.of(1, 4, 3, 2).filter(l -> l % 2 == 0).sorted();
                                    }
                                }
                                """
                ));
    }

    @Test
    void testLongStreamIsEmptyRecipe() {
        Recipe recipe = RewriteUtils.loadRecipe("LongStreamRulesRecipes.LongStreamIsEmptyRecipe");
        rewriteRun(
                spec -> spec.recipe(recipe)
                        .expectedCyclesThatMakeChanges(1).cycles(1)
                        .parser(JavaParser.fromJavaVersion().classpath(JavaParser.runtimeClasspath())),
                java(
                        """
                                import com.google.common.collect.ImmutableSet;
                                
                                import java.util.stream.LongStream;
                                
                                class Test {
                                    ImmutableSet<Boolean> test() {
                                        return ImmutableSet.of(LongStream.of(1).count() == 0, LongStream.of(2).count() <= 0, LongStream.of(3).count() < 1, LongStream.of(4).findFirst().isEmpty());
                                    }
                                }
                                """,
                        """
                                import com.google.common.collect.ImmutableSet;
                                
                                import java.util.stream.LongStream;
                                
                                class Test {
                                    ImmutableSet<Boolean> test() {
                                        return ImmutableSet.of(LongStream.of(1).findAny().isEmpty(), LongStream.of(2).findAny().isEmpty(), LongStream.of(3).findAny().isEmpty(), LongStream.of(4).findAny().isEmpty());
                                    }
                                }
                                """
                ));
    }

    @Test
    void testLongStreamIsNotEmptyRecipe() {
        Recipe recipe = RewriteUtils.loadRecipe("LongStreamRulesRecipes.LongStreamIsNotEmptyRecipe");
        rewriteRun(
                spec -> spec.recipe(recipe)
                        .expectedCyclesThatMakeChanges(1).cycles(1)
                        .parser(JavaParser.fromJavaVersion().classpath(JavaParser.runtimeClasspath())),
                java(
                        """
                                import com.google.common.collect.ImmutableSet;
                                
                                import java.util.stream.LongStream;
                                
                                class Test {
                                    ImmutableSet<Boolean> test() {
                                        return ImmutableSet.of(LongStream.of(1).count() != 0, LongStream.of(2).count() > 0, LongStream.of(3).count() >= 1, LongStream.of(4).findFirst().isPresent());
                                    }
                                }
                                """,
                        """
                                import com.google.common.collect.ImmutableSet;
                                
                                import java.util.stream.LongStream;
                                
                                class Test {
                                    ImmutableSet<Boolean> test() {
                                        return ImmutableSet.of(LongStream.of(1).findAny().isPresent(), LongStream.of(2).findAny().isPresent(), LongStream.of(3).findAny().isPresent(), LongStream.of(4).findAny().isPresent());
                                    }
                                }
                                """
                ));
    }

    @Test
    void testLongStreamMinRecipe() {
        Recipe recipe = RewriteUtils.loadRecipe("LongStreamRulesRecipes.LongStreamMinRecipe");
        rewriteRun(
                spec -> spec.recipe(recipe)
                        .expectedCyclesThatMakeChanges(1).cycles(1)
                        .parser(JavaParser.fromJavaVersion().classpath(JavaParser.runtimeClasspath())),
                java(
                        """
                                import java.util.OptionalLong;
                                import java.util.stream.LongStream;
                                
                                class Test {
                                    OptionalLong test() {
                                        return LongStream.of(1).sorted().findFirst();
                                    }
                                }
                                """,
                        """
                                import java.util.OptionalLong;
                                import java.util.stream.LongStream;
                                
                                class Test {
                                    OptionalLong test() {
                                        return LongStream.of(1).min();
                                    }
                                }
                                """
                ));
    }

    @Test
    void testLongStreamNoneMatchRecipe() {
        Recipe recipe = RewriteUtils.loadRecipe("LongStreamRulesRecipes.LongStreamNoneMatchRecipe");
        rewriteRun(
                spec -> spec.recipe(recipe)
                        .expectedCyclesThatMakeChanges(1).cycles(1)
                        .parser(JavaParser.fromJavaVersion().classpath(JavaParser.runtimeClasspath())),
                java(
                        """
                                import com.google.common.collect.ImmutableSet;
                                
                                import java.util.function.LongPredicate;
                                import java.util.stream.LongStream;
                                
                                class Test {
                                    ImmutableSet<Boolean> test() {
                                        LongPredicate pred = i -> i > 0;
                                        return ImmutableSet.of(!LongStream.of(1).anyMatch(n -> n > 1), LongStream.of(2).allMatch(pred.negate()), LongStream.of(3).filter(pred).findAny().isEmpty());
                                    }
                                }
                                """,
                        """
                                import com.google.common.collect.ImmutableSet;
                                
                                import java.util.function.LongPredicate;
                                import java.util.stream.LongStream;
                                
                                class Test {
                                    ImmutableSet<Boolean> test() {
                                        LongPredicate pred = i -> i > 0;
                                        return ImmutableSet.of(LongStream.of(1).noneMatch(n -> n > 1), LongStream.of(2).noneMatch(pred), LongStream.of(3).noneMatch(pred));
                                    }
                                }
                                """
                ));
    }

    @Test
    void testLongStreamNoneMatch2Recipe() {
        Recipe recipe = RewriteUtils.loadRecipe("LongStreamRulesRecipes.LongStreamNoneMatch2Recipe");
        rewriteRun(
                spec -> spec.recipe(recipe)
                        .expectedCyclesThatMakeChanges(1).cycles(1)
                        .parser(JavaParser.fromJavaVersion().classpath(JavaParser.runtimeClasspath())),
                java(
                        """
                                import java.util.stream.LongStream;
                                
                                class Test {
                                    boolean test() {
                                        return LongStream.of(1).allMatch(n -> !(n > 1));
                                    }
                                }
                                """,
                        """
                                import java.util.stream.LongStream;
                                
                                class Test {
                                    boolean test() {
                                        return LongStream.of(1).noneMatch(n -> n > 1);
                                    }
                                }
                                """
                ));
    }

    @Test
    void testLongStreamTakeWhileRecipe() {
        Recipe recipe = RewriteUtils.loadRecipe("LongStreamRulesRecipes.LongStreamTakeWhileRecipe");
        rewriteRun(
                spec -> spec.recipe(recipe)
                        .expectedCyclesThatMakeChanges(1).cycles(1)
                        .parser(JavaParser.fromJavaVersion().classpath(JavaParser.runtimeClasspath())),
                java(
                        """
                                import java.util.stream.LongStream;
                                
                                class Test {
                                    LongStream test() {
                                        return LongStream.of(1, 2, 3).takeWhile(i -> i < 2).filter(i -> i < 2);
                                    }
                                }
                                """,
                        """
                                import java.util.stream.LongStream;
                                
                                class Test {
                                    LongStream test() {
                                        return LongStream.of(1, 2, 3).takeWhile(i -> i < 2);
                                    }
                                }
                                """
                ));
    }

    @Test
    void testMapOuterLongStreamAfterFlatMapRecipe() {
        Recipe recipe = RewriteUtils.loadRecipe("LongStreamRulesRecipes.MapOuterLongStreamAfterFlatMapRecipe");
        rewriteRun(
                spec -> spec.recipe(recipe)
                        .expectedCyclesThatMakeChanges(1).cycles(1)
                        .parser(JavaParser.fromJavaVersion().classpath(JavaParser.runtimeClasspath())),
                java(
                        """
                                import java.util.stream.LongStream;
                                
                                class Test {
                                    LongStream test() {
                                        return LongStream.of(1).flatMap(v -> LongStream.of(v * v).map(n -> n * 1));
                                    }
                                }
                                """,
                        """
                                import java.util.stream.LongStream;
                                
                                class Test {
                                    LongStream test() {
                                        return LongStream.of(1).flatMap(v -> LongStream.of(v * v)).map(n -> n * 1);
                                    }
                                }
                                """
                ));
    }

    @Test
    void testMapOuterStreamAfterFlatMapToLongRecipe() {
        Recipe recipe = RewriteUtils.loadRecipe("LongStreamRulesRecipes.MapOuterStreamAfterFlatMapToLongRecipe");
        rewriteRun(
                spec -> spec.recipe(recipe)
                        .expectedCyclesThatMakeChanges(1).cycles(1)
                        .parser(JavaParser.fromJavaVersion().classpath(JavaParser.runtimeClasspath())),
                java(
                        """
                                import java.util.stream.LongStream;
                                import java.util.stream.Stream;
                                
                                class Test {
                                    LongStream test() {
                                        return Stream.of(1).flatMapToLong(v -> LongStream.of(v * v).map(n -> n * 1));
                                    }
                                }
                                """,
                        """
                                import java.util.stream.LongStream;
                                import java.util.stream.Stream;
                                
                                class Test {
                                    LongStream test() {
                                        return Stream.of(1).flatMapToLong(v -> LongStream.of(v * v)).map(n -> n * 1);
                                    }
                                }
                                """
                ));
    }
}
