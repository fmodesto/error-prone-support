package tech.picnic.errorprone.refasterrules.rewrite;

import org.junit.jupiter.api.Test;
import org.openrewrite.Recipe;
import org.openrewrite.java.JavaParser;
import org.openrewrite.test.RewriteTest;

import static org.openrewrite.java.Assertions.java;

final class AssortedRulesRecipesTest implements RewriteTest {

    @Test
    void testCheckIndexRecipe() {
        Recipe recipe = RewriteUtils.loadRecipe("AssortedRulesRecipes.CheckIndexRecipe");
        rewriteRun(
                spec -> spec.recipe(recipe)
                        .expectedCyclesThatMakeChanges(1).cycles(1)
                        .parser(JavaParser.fromJavaVersion().classpath(JavaParser.runtimeClasspath())),
                java(
                        """
                                import com.google.common.base.Preconditions;
                                
                                class Test {
                                    int test() {
                                        return Preconditions.checkElementIndex(0, 1);
                                    }
                                }
                                """,
                        """
                                import java.util.Objects;
                                
                                class Test {
                                    int test() {
                                        return Objects.checkIndex(0, 1);
                                    }
                                }
                                """
                ));
    }

    @Test
    void testCheckIndexConditionalRecipe() {
        Recipe recipe = RewriteUtils.loadRecipe("AssortedRulesRecipes.CheckIndexConditionalRecipe");
        rewriteRun(
                spec -> spec.recipe(recipe)
                        .expectedCyclesThatMakeChanges(1).cycles(1)
                        .parser(JavaParser.fromJavaVersion().classpath(JavaParser.runtimeClasspath())),
                java(
                        """
                                class Test {
                                    void test() {
                                        if (1 < 0 || 1 >= 2) {
                                            throw new IndexOutOfBoundsException();
                                        }
                                    }
                                }
                                """,
                        """
                                import java.util.Objects;
                                
                                class Test {
                                    void test() {
                                        Objects.checkIndex(1, 2);
                                    }
                                }
                                """
                ));
    }

    @Test
    void testDisjointCollectionsRecipe() {
        Recipe recipe = RewriteUtils.loadRecipe("AssortedRulesRecipes.DisjointCollectionsRecipe");
        rewriteRun(
                spec -> spec.recipe(recipe)
                        .expectedCyclesThatMakeChanges(1).cycles(1)
                        .parser(JavaParser.fromJavaVersion().classpath(JavaParser.runtimeClasspath())),
                java(
                        """
                                import com.google.common.collect.ImmutableList;
                                import com.google.common.collect.ImmutableSet;
                                
                                import java.util.Collections;
                                import java.util.HashSet;
                                
                                class Test {
                                    ImmutableSet<Boolean> test() {
                                        return ImmutableSet.of(Collections.disjoint(ImmutableSet.copyOf(ImmutableList.of(1)), ImmutableList.of(2)), Collections.disjoint(new HashSet<>(ImmutableList.of(3)), ImmutableList.of(4)), Collections.disjoint(ImmutableList.of(5), ImmutableSet.copyOf(ImmutableList.of(6))), Collections.disjoint(ImmutableList.of(7), new HashSet<>(ImmutableList.of(8))));
                                    }
                                }
                                """,
                        """
                                import com.google.common.collect.ImmutableList;
                                import com.google.common.collect.ImmutableSet;
                                
                                import java.util.Collections;
                                
                                class Test {
                                    ImmutableSet<Boolean> test() {
                                        return ImmutableSet.of(Collections.disjoint(ImmutableList.of(1), ImmutableList.of(2)), Collections.disjoint(ImmutableList.of(3), ImmutableList.of(4)), Collections.disjoint(ImmutableList.of(5), ImmutableList.of(6)), Collections.disjoint(ImmutableList.of(7), ImmutableList.of(8)));
                                    }
                                }
                                """
                ));
    }

    @Test
    void testDisjointSetsRecipe() {
        Recipe recipe = RewriteUtils.loadRecipe("AssortedRulesRecipes.DisjointSetsRecipe");
        rewriteRun(
                spec -> spec.recipe(recipe)
                        .expectedCyclesThatMakeChanges(1).cycles(1)
                        .parser(JavaParser.fromJavaVersion().classpath(JavaParser.runtimeClasspath())),
                java(
                        """
                                import com.google.common.collect.ImmutableSet;
                                import com.google.common.collect.Sets;
                                
                                class Test {
                                    ImmutableSet<Boolean> test() {
                                        return ImmutableSet.of(Sets.intersection(ImmutableSet.of(1), ImmutableSet.of(2)).isEmpty(), ImmutableSet.of(3).stream().noneMatch(ImmutableSet.of(4)::contains));
                                    }
                                }
                                """,
                        """
                                import com.google.common.collect.ImmutableSet;
                                
                                import java.util.Collections;
                                
                                class Test {
                                    ImmutableSet<Boolean> test() {
                                        return ImmutableSet.of(Collections.disjoint(ImmutableSet.of(1), ImmutableSet.of(2)), Collections.disjoint(ImmutableSet.of(3), ImmutableSet.of(4)));
                                    }
                                }
                                """
                ));
    }

    @Test
    void testIterableIsEmptyRecipe() {
        Recipe recipe = RewriteUtils.loadRecipe("AssortedRulesRecipes.IterableIsEmptyRecipe");
        rewriteRun(
                spec -> spec.recipe(recipe)
                        .expectedCyclesThatMakeChanges(1).cycles(1)
                        .parser(JavaParser.fromJavaVersion().classpath(JavaParser.runtimeClasspath())),
                java(
                        """
                                import com.google.common.collect.ImmutableList;
                                
                                class Test {
                                    boolean test() {
                                        return !ImmutableList.of().iterator().hasNext();
                                    }
                                }
                                """,
                        """
                                import com.google.common.collect.ImmutableList;
                                import com.google.common.collect.Iterables;
                                
                                class Test {
                                    boolean test() {
                                        return Iterables.isEmpty(ImmutableList.of());
                                    }
                                }
                                """
                ));
    }

    @Test
    void testIteratorGetNextOrDefaultRecipe() {
        Recipe recipe = RewriteUtils.loadRecipe("AssortedRulesRecipes.IteratorGetNextOrDefaultRecipe");
        rewriteRun(
                spec -> spec.recipe(recipe)
                        .expectedCyclesThatMakeChanges(1).cycles(1)
                        .parser(JavaParser.fromJavaVersion().classpath(JavaParser.runtimeClasspath())),
                java(
                        """
                                import com.google.common.collect.ImmutableList;
                                import com.google.common.collect.ImmutableSet;
                                import com.google.common.collect.Streams;
                                
                                class Test {
                                    ImmutableSet<String> test() {
                                        return ImmutableSet.of(ImmutableList.of("a").iterator().hasNext() ? ImmutableList.of("a").iterator().next() : "foo", Streams.stream(ImmutableList.of("b").iterator()).findFirst().orElse("bar"), Streams.stream(ImmutableList.of("c").iterator()).findAny().orElse("baz"));
                                    }
                                }
                                """,
                        """
                                import com.google.common.collect.ImmutableList;
                                import com.google.common.collect.ImmutableSet;
                                import com.google.common.collect.Iterators;
                                
                                class Test {
                                    ImmutableSet<String> test() {
                                        return ImmutableSet.of(Iterators.getNext(ImmutableList.of("a").iterator(), "foo"), Iterators.getNext(ImmutableList.of("b").iterator(), "bar"), Iterators.getNext(ImmutableList.of("c").iterator(), "baz"));
                                    }
                                }
                                """
                ));
    }

    @Test
    void testLogicalImplicationRecipe() {
        Recipe recipe = RewriteUtils.loadRecipe("AssortedRulesRecipes.LogicalImplicationRecipe");
        rewriteRun(
                spec -> spec.recipe(recipe)
                        .expectedCyclesThatMakeChanges(1).cycles(1)
                        .parser(JavaParser.fromJavaVersion().classpath(JavaParser.runtimeClasspath())),
                java(
                        """
                                import com.google.common.collect.ImmutableSet;
                                
                                class Test {
                                    ImmutableSet<Boolean> test() {
                                        return ImmutableSet.of(toString().isEmpty() || (!toString().isEmpty() && Boolean.TRUE), !toString().isEmpty() || (toString().isEmpty() && Boolean.TRUE), 3 < 4 || (3 >= 4 && Boolean.TRUE), 3 >= 4 || (3 < 4 && Boolean.TRUE));
                                    }
                                }
                                """,
                        """
                                import com.google.common.collect.ImmutableSet;
                                
                                class Test {
                                    ImmutableSet<Boolean> test() {
                                        return ImmutableSet.of(toString().isEmpty() || Boolean.TRUE, !toString().isEmpty() || (toString().isEmpty() && Boolean.TRUE), 3 < 4 || (3 >= 4 && Boolean.TRUE), 3 >= 4 || (3 < 4 && Boolean.TRUE));
                                    }
                                }
                                """
                ));
    }

    @Test
    void testSplitToStreamRecipe() {
        Recipe recipe = RewriteUtils.loadRecipe("AssortedRulesRecipes.SplitToStreamRecipe");
        rewriteRun(
                spec -> spec.recipe(recipe)
                        .expectedCyclesThatMakeChanges(1).cycles(1)
                        .parser(JavaParser.fromJavaVersion().classpath(JavaParser.runtimeClasspath())),
                java(
                        """
                                import com.google.common.base.Splitter;
                                import com.google.common.collect.ImmutableSet;
                                import com.google.common.collect.Streams;
                                
                                import java.util.stream.Stream;
                                
                                class Test {
                                    ImmutableSet<Stream<String>> test() {
                                        return ImmutableSet.of(Streams.stream(Splitter.on(':').split("foo")), Splitter.on(',').splitToList(new StringBuilder("bar")).stream());
                                    }
                                }
                                """,
                        """
                                import com.google.common.base.Splitter;
                                import com.google.common.collect.ImmutableSet;
                                
                                import java.util.stream.Stream;
                                
                                class Test {
                                    ImmutableSet<Stream<String>> test() {
                                        return ImmutableSet.of(Splitter.on(':').splitToStream("foo"), Splitter.on(',').splitToStream(new StringBuilder("bar")));
                                    }
                                }
                                """
                ));
    }

    @Test
    void testUnboundedSingleElementStreamRecipe() {
        Recipe recipe = RewriteUtils.loadRecipe("AssortedRulesRecipes.UnboundedSingleElementStreamRecipe");
        rewriteRun(
                spec -> spec.recipe(recipe)
                        .expectedCyclesThatMakeChanges(1).cycles(1)
                        .parser(JavaParser.fromJavaVersion().classpath(JavaParser.runtimeClasspath())),
                java(
                        """
                                import com.google.common.collect.Iterables;
                                import com.google.common.collect.Streams;
                                
                                import java.util.stream.Stream;
                                
                                class Test {
                                    Stream<String> test() {
                                        return Streams.stream(Iterables.cycle("foo"));
                                    }
                                }
                                """,
                        """
                                import java.util.stream.Stream;
                                
                                class Test {
                                    Stream<String> test() {
                                        return Stream.generate(() -> "foo");
                                    }
                                }
                                """
                ));
    }
}
