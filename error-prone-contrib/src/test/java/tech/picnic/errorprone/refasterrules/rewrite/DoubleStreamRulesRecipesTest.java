package tech.picnic.errorprone.refasterrules.rewrite;

import org.junit.jupiter.api.Test;
import org.openrewrite.Recipe;
import org.openrewrite.java.JavaParser;
import org.openrewrite.test.RewriteTest;

import static org.openrewrite.java.Assertions.java;

final class DoubleStreamRulesRecipesTest implements RewriteTest {

    @Test
    void testConcatOneDoubleStreamRecipe() {
        Recipe recipe = RewriteUtils.loadRecipe("DoubleStreamRulesRecipes.ConcatOneDoubleStreamRecipe");
        rewriteRun(
                spec -> spec.recipe(recipe)
                        .expectedCyclesThatMakeChanges(1).cycles(1)
                        .parser(JavaParser.fromJavaVersion().classpath(JavaParser.runtimeClasspath())),
                java(
                        """
                                import com.google.common.collect.Streams;
                                
                                import java.util.stream.DoubleStream;
                                
                                class Test {
                                    DoubleStream test() {
                                        return Streams.concat(DoubleStream.of(1));
                                    }
                                }
                                """,
                        """
                                import java.util.stream.DoubleStream;
                                
                                class Test {
                                    DoubleStream test() {
                                        return DoubleStream.of(1);
                                    }
                                }
                                """
                ));
    }

    @Test
    void testConcatTwoDoubleStreamsRecipe() {
        Recipe recipe = RewriteUtils.loadRecipe("DoubleStreamRulesRecipes.ConcatTwoDoubleStreamsRecipe");
        rewriteRun(
                spec -> spec.recipe(recipe)
                        .expectedCyclesThatMakeChanges(1).cycles(1)
                        .parser(JavaParser.fromJavaVersion().classpath(JavaParser.runtimeClasspath())),
                java(
                        """
                                import com.google.common.collect.Streams;
                                
                                import java.util.stream.DoubleStream;
                                
                                class Test {
                                    DoubleStream test() {
                                        return Streams.concat(DoubleStream.of(1), DoubleStream.of(2));
                                    }
                                }
                                """,
                        """
                                import java.util.stream.DoubleStream;
                                
                                class Test {
                                    DoubleStream test() {
                                        return DoubleStream.concat(DoubleStream.of(1), DoubleStream.of(2));
                                    }
                                }
                                """
                ));
    }

    @Test
    void testDoubleStreamAllMatchRecipe() {
        Recipe recipe = RewriteUtils.loadRecipe("DoubleStreamRulesRecipes.DoubleStreamAllMatchRecipe");
        rewriteRun(
                spec -> spec.recipe(recipe)
                        .expectedCyclesThatMakeChanges(1).cycles(1)
                        .parser(JavaParser.fromJavaVersion().classpath(JavaParser.runtimeClasspath())),
                java(
                        """
                                import java.util.function.DoublePredicate;
                                import java.util.stream.DoubleStream;
                                
                                class Test {
                                    boolean test() {
                                        DoublePredicate pred = i -> i > 0;
                                        return DoubleStream.of(1).noneMatch(pred.negate());
                                    }
                                }
                                """,
                        """
                                import java.util.function.DoublePredicate;
                                import java.util.stream.DoubleStream;
                                
                                class Test {
                                    boolean test() {
                                        DoublePredicate pred = i -> i > 0;
                                        return DoubleStream.of(1).allMatch(pred);
                                    }
                                }
                                """
                ));
    }

    @Test
    void testDoubleStreamAllMatch2Recipe() {
        Recipe recipe = RewriteUtils.loadRecipe("DoubleStreamRulesRecipes.DoubleStreamAllMatch2Recipe");
        rewriteRun(
                spec -> spec.recipe(recipe)
                        .expectedCyclesThatMakeChanges(1).cycles(1)
                        .parser(JavaParser.fromJavaVersion().classpath(JavaParser.runtimeClasspath())),
                java(
                        """
                                import java.util.stream.DoubleStream;
                                
                                class Test {
                                    boolean test() {
                                        return DoubleStream.of(1).noneMatch(n -> !(n > 1));
                                    }
                                }
                                """,
                        """
                                import java.util.stream.DoubleStream;
                                
                                class Test {
                                    boolean test() {
                                        return DoubleStream.of(1).allMatch(n -> n > 1);
                                    }
                                }
                                """
                ));
    }

    @Test
    void testDoubleStreamAnyMatchRecipe() {
        Recipe recipe = RewriteUtils.loadRecipe("DoubleStreamRulesRecipes.DoubleStreamAnyMatchRecipe");
        rewriteRun(
                spec -> spec.recipe(recipe)
                        .expectedCyclesThatMakeChanges(1).cycles(1)
                        .parser(JavaParser.fromJavaVersion().classpath(JavaParser.runtimeClasspath())),
                java(
                        """
                                import com.google.common.collect.ImmutableSet;
                                
                                import java.util.stream.DoubleStream;
                                
                                class Test {
                                    ImmutableSet<Boolean> test() {
                                        return ImmutableSet.of(!DoubleStream.of(1).noneMatch(n -> n > 1), DoubleStream.of(2).filter(n -> n > 2).findAny().isPresent());
                                    }
                                }
                                """,
                        """
                                import com.google.common.collect.ImmutableSet;
                                
                                import java.util.stream.DoubleStream;
                                
                                class Test {
                                    ImmutableSet<Boolean> test() {
                                        return ImmutableSet.of(DoubleStream.of(1).anyMatch(n -> n > 1), DoubleStream.of(2).anyMatch(n -> n > 2));
                                    }
                                }
                                """
                ));
    }

    @Test
    void testDoubleStreamFilterSortedRecipe() {
        Recipe recipe = RewriteUtils.loadRecipe("DoubleStreamRulesRecipes.DoubleStreamFilterSortedRecipe");
        rewriteRun(
                spec -> spec.recipe(recipe)
                        .expectedCyclesThatMakeChanges(1).cycles(1)
                        .parser(JavaParser.fromJavaVersion().classpath(JavaParser.runtimeClasspath())),
                java(
                        """
                                import java.util.stream.DoubleStream;
                                
                                class Test {
                                    DoubleStream test() {
                                        return DoubleStream.of(1, 4, 3, 2).sorted().filter(d -> d % 2 == 0);
                                    }
                                }
                                """,
                        """
                                import java.util.stream.DoubleStream;
                                
                                class Test {
                                    DoubleStream test() {
                                        return DoubleStream.of(1, 4, 3, 2).filter(d -> d % 2 == 0).sorted();
                                    }
                                }
                                """
                ));
    }

    @Test
    void testDoubleStreamIsEmptyRecipe() {
        Recipe recipe = RewriteUtils.loadRecipe("DoubleStreamRulesRecipes.DoubleStreamIsEmptyRecipe");
        rewriteRun(
                spec -> spec.recipe(recipe)
                        .expectedCyclesThatMakeChanges(1).cycles(1)
                        .parser(JavaParser.fromJavaVersion().classpath(JavaParser.runtimeClasspath())),
                java(
                        """
                                import com.google.common.collect.ImmutableSet;
                                
                                import java.util.stream.DoubleStream;
                                
                                class Test {
                                    ImmutableSet<Boolean> test() {
                                        return ImmutableSet.of(DoubleStream.of(1).count() == 0, DoubleStream.of(2).count() <= 0, DoubleStream.of(3).count() < 1, DoubleStream.of(4).findFirst().isEmpty());
                                    }
                                }
                                """,
                        """
                                import com.google.common.collect.ImmutableSet;
                                
                                import java.util.stream.DoubleStream;
                                
                                class Test {
                                    ImmutableSet<Boolean> test() {
                                        return ImmutableSet.of(DoubleStream.of(1).findAny().isEmpty(), DoubleStream.of(2).findAny().isEmpty(), DoubleStream.of(3).findAny().isEmpty(), DoubleStream.of(4).findAny().isEmpty());
                                    }
                                }
                                """
                ));
    }

    @Test
    void testDoubleStreamIsNotEmptyRecipe() {
        Recipe recipe = RewriteUtils.loadRecipe("DoubleStreamRulesRecipes.DoubleStreamIsNotEmptyRecipe");
        rewriteRun(
                spec -> spec.recipe(recipe)
                        .expectedCyclesThatMakeChanges(1).cycles(1)
                        .parser(JavaParser.fromJavaVersion().classpath(JavaParser.runtimeClasspath())),
                java(
                        """
                                import com.google.common.collect.ImmutableSet;
                                
                                import java.util.stream.DoubleStream;
                                
                                class Test {
                                    ImmutableSet<Boolean> test() {
                                        return ImmutableSet.of(DoubleStream.of(1).count() != 0, DoubleStream.of(2).count() > 0, DoubleStream.of(3).count() >= 1, DoubleStream.of(4).findFirst().isPresent());
                                    }
                                }
                                """,
                        """
                                import com.google.common.collect.ImmutableSet;
                                
                                import java.util.stream.DoubleStream;
                                
                                class Test {
                                    ImmutableSet<Boolean> test() {
                                        return ImmutableSet.of(DoubleStream.of(1).findAny().isPresent(), DoubleStream.of(2).findAny().isPresent(), DoubleStream.of(3).findAny().isPresent(), DoubleStream.of(4).findAny().isPresent());
                                    }
                                }
                                """
                ));
    }

    @Test
    void testDoubleStreamMinRecipe() {
        Recipe recipe = RewriteUtils.loadRecipe("DoubleStreamRulesRecipes.DoubleStreamMinRecipe");
        rewriteRun(
                spec -> spec.recipe(recipe)
                        .expectedCyclesThatMakeChanges(1).cycles(1)
                        .parser(JavaParser.fromJavaVersion().classpath(JavaParser.runtimeClasspath())),
                java(
                        """
                                import java.util.OptionalDouble;
                                import java.util.stream.DoubleStream;
                                
                                class Test {
                                    OptionalDouble test() {
                                        return DoubleStream.of(1).sorted().findFirst();
                                    }
                                }
                                """,
                        """
                                import java.util.OptionalDouble;
                                import java.util.stream.DoubleStream;
                                
                                class Test {
                                    OptionalDouble test() {
                                        return DoubleStream.of(1).min();
                                    }
                                }
                                """
                ));
    }

    @Test
    void testDoubleStreamNoneMatchRecipe() {
        Recipe recipe = RewriteUtils.loadRecipe("DoubleStreamRulesRecipes.DoubleStreamNoneMatchRecipe");
        rewriteRun(
                spec -> spec.recipe(recipe)
                        .expectedCyclesThatMakeChanges(1).cycles(1)
                        .parser(JavaParser.fromJavaVersion().classpath(JavaParser.runtimeClasspath())),
                java(
                        """
                                import com.google.common.collect.ImmutableSet;
                                
                                import java.util.function.DoublePredicate;
                                import java.util.stream.DoubleStream;
                                
                                class Test {
                                    ImmutableSet<Boolean> test() {
                                        DoublePredicate pred = i -> i > 0;
                                        return ImmutableSet.of(!DoubleStream.of(1).anyMatch(n -> n > 1), DoubleStream.of(2).allMatch(pred.negate()), DoubleStream.of(3).filter(pred).findAny().isEmpty());
                                    }
                                }
                                """,
                        """
                                import com.google.common.collect.ImmutableSet;
                                
                                import java.util.function.DoublePredicate;
                                import java.util.stream.DoubleStream;
                                
                                class Test {
                                    ImmutableSet<Boolean> test() {
                                        DoublePredicate pred = i -> i > 0;
                                        return ImmutableSet.of(DoubleStream.of(1).noneMatch(n -> n > 1), DoubleStream.of(2).noneMatch(pred), DoubleStream.of(3).noneMatch(pred));
                                    }
                                }
                                """
                ));
    }

    @Test
    void testDoubleStreamNoneMatch2Recipe() {
        Recipe recipe = RewriteUtils.loadRecipe("DoubleStreamRulesRecipes.DoubleStreamNoneMatch2Recipe");
        rewriteRun(
                spec -> spec.recipe(recipe)
                        .expectedCyclesThatMakeChanges(1).cycles(1)
                        .parser(JavaParser.fromJavaVersion().classpath(JavaParser.runtimeClasspath())),
                java(
                        """
                                import java.util.stream.DoubleStream;
                                
                                class Test {
                                    boolean test() {
                                        return DoubleStream.of(1).allMatch(n -> !(n > 1));
                                    }
                                }
                                """,
                        """
                                import java.util.stream.DoubleStream;
                                
                                class Test {
                                    boolean test() {
                                        return DoubleStream.of(1).noneMatch(n -> n > 1);
                                    }
                                }
                                """
                ));
    }

    @Test
    void testDoubleStreamTakeWhileRecipe() {
        Recipe recipe = RewriteUtils.loadRecipe("DoubleStreamRulesRecipes.DoubleStreamTakeWhileRecipe");
        rewriteRun(
                spec -> spec.recipe(recipe)
                        .expectedCyclesThatMakeChanges(1).cycles(1)
                        .parser(JavaParser.fromJavaVersion().classpath(JavaParser.runtimeClasspath())),
                java(
                        """
                                import java.util.stream.DoubleStream;
                                
                                class Test {
                                    DoubleStream test() {
                                        return DoubleStream.of(1, 2, 3).takeWhile(i -> i < 2).filter(i -> i < 2);
                                    }
                                }
                                """,
                        """
                                import java.util.stream.DoubleStream;
                                
                                class Test {
                                    DoubleStream test() {
                                        return DoubleStream.of(1, 2, 3).takeWhile(i -> i < 2);
                                    }
                                }
                                """
                ));
    }

    @Test
    void testFilterOuterDoubleStreamAfterFlatMapRecipe() {
        Recipe recipe = RewriteUtils.loadRecipe("DoubleStreamRulesRecipes.FilterOuterDoubleStreamAfterFlatMapRecipe");
        rewriteRun(
                spec -> spec.recipe(recipe)
                        .expectedCyclesThatMakeChanges(1).cycles(1)
                        .parser(JavaParser.fromJavaVersion().classpath(JavaParser.runtimeClasspath())),
                java(
                        """
                                import java.util.stream.DoubleStream;
                                
                                class Test {
                                    DoubleStream test() {
                                        return DoubleStream.of(1).flatMap(v -> DoubleStream.of(v * v).filter(n -> n > 1));
                                    }
                                }
                                """,
                        """
                                import java.util.stream.DoubleStream;
                                
                                class Test {
                                    DoubleStream test() {
                                        return DoubleStream.of(1).flatMap(v -> DoubleStream.of(v * v)).filter(n -> n > 1);
                                    }
                                }
                                """
                ));
    }

    @Test
    void testFilterOuterStreamAfterFlatMapToDoubleRecipe() {
        Recipe recipe = RewriteUtils.loadRecipe("DoubleStreamRulesRecipes.FilterOuterStreamAfterFlatMapToDoubleRecipe");
        rewriteRun(
                spec -> spec.recipe(recipe)
                        .expectedCyclesThatMakeChanges(1).cycles(1)
                        .parser(JavaParser.fromJavaVersion().classpath(JavaParser.runtimeClasspath())),
                java(
                        """
                                import java.util.stream.DoubleStream;
                                import java.util.stream.Stream;
                                
                                class Test {
                                    DoubleStream test() {
                                        return Stream.of(1).flatMapToDouble(v -> DoubleStream.of(v * v).filter(n -> n > 1));
                                    }
                                }
                                """,
                        """
                                import java.util.stream.DoubleStream;
                                import java.util.stream.Stream;
                                
                                class Test {
                                    DoubleStream test() {
                                        return Stream.of(1).flatMapToDouble(v -> DoubleStream.of(v * v)).filter(n -> n > 1);
                                    }
                                }
                                """
                ));
    }

    @Test
    void testFlatMapOuterDoubleStreamAfterFlatMapRecipe() {
        Recipe recipe = RewriteUtils.loadRecipe("DoubleStreamRulesRecipes.FlatMapOuterDoubleStreamAfterFlatMapRecipe");
        rewriteRun(
                spec -> spec.recipe(recipe)
                        .expectedCyclesThatMakeChanges(1).cycles(1)
                        .parser(JavaParser.fromJavaVersion().classpath(JavaParser.runtimeClasspath())),
                java(
                        """
                                import java.util.stream.DoubleStream;
                                
                                class Test {
                                    DoubleStream test() {
                                        return DoubleStream.of(1).flatMap(v -> DoubleStream.of(v * v).flatMap(DoubleStream::of));
                                    }
                                }
                                """,
                        """
                                import java.util.stream.DoubleStream;
                                
                                class Test {
                                    DoubleStream test() {
                                        return DoubleStream.of(1).flatMap(v -> DoubleStream.of(v * v)).flatMap(DoubleStream::of);
                                    }
                                }
                                """
                ));
    }

    @Test
    void testFlatMapOuterStreamAfterFlatMapToDoubleRecipe() {
        Recipe recipe = RewriteUtils.loadRecipe("DoubleStreamRulesRecipes.FlatMapOuterStreamAfterFlatMapToDoubleRecipe");
        rewriteRun(
                spec -> spec.recipe(recipe)
                        .expectedCyclesThatMakeChanges(1).cycles(1)
                        .parser(JavaParser.fromJavaVersion().classpath(JavaParser.runtimeClasspath())),
                java(
                        """
                                import java.util.stream.DoubleStream;
                                import java.util.stream.Stream;
                                
                                class Test {
                                    DoubleStream test() {
                                        return Stream.of(1).flatMapToDouble(v -> DoubleStream.of(v * v).flatMap(DoubleStream::of));
                                    }
                                }
                                """,
                        """
                                import java.util.stream.DoubleStream;
                                import java.util.stream.Stream;
                                
                                class Test {
                                    DoubleStream test() {
                                        return Stream.of(1).flatMapToDouble(v -> DoubleStream.of(v * v)).flatMap(DoubleStream::of);
                                    }
                                }
                                """
                ));
    }

    @Test
    void testMapOuterDoubleStreamAfterFlatMapRecipe() {
        Recipe recipe = RewriteUtils.loadRecipe("DoubleStreamRulesRecipes.MapOuterDoubleStreamAfterFlatMapRecipe");
        rewriteRun(
                spec -> spec.recipe(recipe)
                        .expectedCyclesThatMakeChanges(1).cycles(1)
                        .parser(JavaParser.fromJavaVersion().classpath(JavaParser.runtimeClasspath())),
                java(
                        """
                                import java.util.stream.DoubleStream;
                                
                                class Test {
                                    DoubleStream test() {
                                        return DoubleStream.of(1).flatMap(v -> DoubleStream.of(v * v).map(n -> n * 1));
                                    }
                                }
                                """,
                        """
                                import java.util.stream.DoubleStream;
                                
                                class Test {
                                    DoubleStream test() {
                                        return DoubleStream.of(1).flatMap(v -> DoubleStream.of(v * v)).map(n -> n * 1);
                                    }
                                }
                                """
                ));
    }

    @Test
    void testMapOuterStreamAfterFlatMapToDoubleRecipe() {
        Recipe recipe = RewriteUtils.loadRecipe("DoubleStreamRulesRecipes.MapOuterStreamAfterFlatMapToDoubleRecipe");
        rewriteRun(
                spec -> spec.recipe(recipe)
                        .expectedCyclesThatMakeChanges(1).cycles(1)
                        .parser(JavaParser.fromJavaVersion().classpath(JavaParser.runtimeClasspath())),
                java(
                        """
                                import java.util.stream.DoubleStream;
                                import java.util.stream.Stream;
                                
                                class Test {
                                    DoubleStream test() {
                                        return Stream.of(1).flatMapToDouble(v -> DoubleStream.of(v * v).map(n -> n * 1));
                                    }
                                }
                                """,
                        """
                                import java.util.stream.DoubleStream;
                                import java.util.stream.Stream;
                                
                                class Test {
                                    DoubleStream test() {
                                        return Stream.of(1).flatMapToDouble(v -> DoubleStream.of(v * v)).map(n -> n * 1);
                                    }
                                }
                                """
                ));
    }
}
