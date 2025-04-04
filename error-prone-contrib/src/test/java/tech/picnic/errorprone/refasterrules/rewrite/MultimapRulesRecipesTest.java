package tech.picnic.errorprone.refasterrules.rewrite;

import org.junit.jupiter.api.Test;
import org.openrewrite.Recipe;
import org.openrewrite.java.JavaParser;
import org.openrewrite.test.RewriteTest;

import static org.openrewrite.java.Assertions.java;

final class MultimapRulesRecipesTest implements RewriteTest {

    @Test
    void testMultimapContainsKeyRecipe() {
        Recipe recipe = RewriteUtils.loadRecipe("MultimapRulesRecipes.MultimapContainsKeyRecipe");
        rewriteRun(
                spec -> spec.recipe(recipe)
                        .expectedCyclesThatMakeChanges(1).cycles(1)
                        .parser(JavaParser.fromJavaVersion().classpath(JavaParser.runtimeClasspath())),
                java(
                        """
                                import com.google.common.collect.ImmutableSet;
                                import com.google.common.collect.ImmutableSetMultimap;
                                
                                class Test {
                                    ImmutableSet<Boolean> test() {
                                        return ImmutableSet.of(ImmutableSetMultimap.of("foo", 1).keySet().contains("bar"), ImmutableSetMultimap.of("baz", 1).keys().contains("qux"));
                                    }
                                }
                                """,
                        """
                                import com.google.common.collect.ImmutableSet;
                                import com.google.common.collect.ImmutableSetMultimap;
                                
                                class Test {
                                    ImmutableSet<Boolean> test() {
                                        return ImmutableSet.of(ImmutableSetMultimap.of("foo", 1).containsKey("bar"), ImmutableSetMultimap.of("baz", 1).containsKey("qux"));
                                    }
                                }
                                """
                ));
    }

    @Test
    void testMultimapContainsValueRecipe() {
        Recipe recipe = RewriteUtils.loadRecipe("MultimapRulesRecipes.MultimapContainsValueRecipe");
        rewriteRun(
                spec -> spec.recipe(recipe)
                        .expectedCyclesThatMakeChanges(1).cycles(1)
                        .parser(JavaParser.fromJavaVersion().classpath(JavaParser.runtimeClasspath())),
                java(
                        """
                                import com.google.common.collect.ImmutableSetMultimap;
                                
                                class Test {
                                    boolean test() {
                                        return ImmutableSetMultimap.of("foo", 1).values().contains(2);
                                    }
                                }
                                """,
                        """
                                import com.google.common.collect.ImmutableSetMultimap;
                                
                                class Test {
                                    boolean test() {
                                        return ImmutableSetMultimap.of("foo", 1).containsValue(2);
                                    }
                                }
                                """
                ));
    }

    @Test
    void testMultimapGetRecipe() {
        Recipe recipe = RewriteUtils.loadRecipe("MultimapRulesRecipes.MultimapGetRecipe");
        rewriteRun(
                spec -> spec.recipe(recipe)
                        .expectedCyclesThatMakeChanges(1).cycles(1)
                        .parser(JavaParser.fromJavaVersion().classpath(JavaParser.runtimeClasspath())),
                java(
                        """
                                import com.google.common.collect.ImmutableSet;
                                import com.google.common.collect.ImmutableSetMultimap;
                                import com.google.common.collect.Multimap;
                                import com.google.common.collect.Multimaps;
                                
                                import java.util.Collection;
                                
                                class Test {
                                    ImmutableSet<Collection<Integer>> test() {
                                        return ImmutableSet.of(ImmutableSetMultimap.of(1, 2).asMap().get(1), Multimaps.asMap((Multimap<Integer, Integer>) ImmutableSetMultimap.of(1, 2)).get(1));
                                    }
                                }
                                """,
                        """
                                import com.google.common.collect.ImmutableSet;
                                import com.google.common.collect.ImmutableSetMultimap;
                                import com.google.common.collect.Multimap;
                                
                                import java.util.Collection;
                                
                                class Test {
                                    ImmutableSet<Collection<Integer>> test() {
                                        return ImmutableSet.of(ImmutableSetMultimap.of(1, 2).get(1), ((Multimap<Integer, Integer>) ImmutableSetMultimap.of(1, 2)).get(1));
                                    }
                                }
                                """
                ));
    }

    @Test
    void testMultimapIsEmptyRecipe() {
        Recipe recipe = RewriteUtils.loadRecipe("MultimapRulesRecipes.MultimapIsEmptyRecipe");
        rewriteRun(
                spec -> spec.recipe(recipe)
                        .expectedCyclesThatMakeChanges(1).cycles(1)
                        .parser(JavaParser.fromJavaVersion().classpath(JavaParser.runtimeClasspath())),
                java(
                        """
                                import com.google.common.collect.ImmutableSet;
                                import com.google.common.collect.ImmutableSetMultimap;
                                
                                class Test {
                                    ImmutableSet<Boolean> test() {
                                        return ImmutableSet.of(ImmutableSetMultimap.of("foo", 1).keySet().isEmpty(), ImmutableSetMultimap.of("bar", 2).keys().isEmpty(), ImmutableSetMultimap.of("baz", 3).values().isEmpty(), ImmutableSetMultimap.of("qux", 54).entries().isEmpty());
                                    }
                                }
                                """,
                        """
                                import com.google.common.collect.ImmutableSet;
                                import com.google.common.collect.ImmutableSetMultimap;
                                
                                class Test {
                                    ImmutableSet<Boolean> test() {
                                        return ImmutableSet.of(ImmutableSetMultimap.of("foo", 1).isEmpty(), ImmutableSetMultimap.of("bar", 2).isEmpty(), ImmutableSetMultimap.of("baz", 3).isEmpty(), ImmutableSetMultimap.of("qux", 54).isEmpty());
                                    }
                                }
                                """
                ));
    }

    @Test
    void testMultimapKeySetRecipe() {
        Recipe recipe = RewriteUtils.loadRecipe("MultimapRulesRecipes.MultimapKeySetRecipe");
        rewriteRun(
                spec -> spec.recipe(recipe)
                        .expectedCyclesThatMakeChanges(1).cycles(1)
                        .parser(JavaParser.fromJavaVersion().classpath(JavaParser.runtimeClasspath())),
                java(
                        """
                                import com.google.common.collect.ImmutableSetMultimap;
                                
                                import java.util.Set;
                                
                                class Test {
                                    Set<String> test() {
                                        return ImmutableSetMultimap.of("foo", "bar").asMap().keySet();
                                    }
                                }
                                """,
                        """
                                import com.google.common.collect.ImmutableSetMultimap;
                                
                                import java.util.Set;
                                
                                class Test {
                                    Set<String> test() {
                                        return ImmutableSetMultimap.of("foo", "bar").keySet();
                                    }
                                }
                                """
                ));
    }

    @Test
    void testMultimapKeysStreamRecipe() {
        Recipe recipe = RewriteUtils.loadRecipe("MultimapRulesRecipes.MultimapKeysStreamRecipe");
        rewriteRun(
                spec -> spec.recipe(recipe)
                        .expectedCyclesThatMakeChanges(1).cycles(1)
                        .parser(JavaParser.fromJavaVersion().classpath(JavaParser.runtimeClasspath())),
                java(
                        """
                                import com.google.common.collect.ImmutableSetMultimap;
                                
                                import java.util.Map;
                                import java.util.stream.Stream;
                                
                                class Test {
                                    Stream<String> test() {
                                        return ImmutableSetMultimap.of("foo", 1).entries().stream().map(Map.Entry::getKey);
                                    }
                                }
                                """,
                        """
                                import com.google.common.collect.ImmutableSetMultimap;
                                
                                import java.util.stream.Stream;
                                
                                class Test {
                                    Stream<String> test() {
                                        return ImmutableSetMultimap.of("foo", 1).keys().stream();
                                    }
                                }
                                """
                ));
    }

    @Test
    void testMultimapSizeRecipe() {
        Recipe recipe = RewriteUtils.loadRecipe("MultimapRulesRecipes.MultimapSizeRecipe");
        rewriteRun(
                spec -> spec.recipe(recipe)
                        .expectedCyclesThatMakeChanges(1).cycles(1)
                        .parser(JavaParser.fromJavaVersion().classpath(JavaParser.runtimeClasspath())),
                java(
                        """
                                import com.google.common.collect.ImmutableSetMultimap;
                                
                                class Test {
                                    int test() {
                                        return ImmutableSetMultimap.of().values().size();
                                    }
                                }
                                """,
                        """
                                import com.google.common.collect.ImmutableSetMultimap;
                                
                                class Test {
                                    int test() {
                                        return ImmutableSetMultimap.of().size();
                                    }
                                }
                                """
                ));
    }

    @Test
    void testMultimapValuesStreamRecipe() {
        Recipe recipe = RewriteUtils.loadRecipe("MultimapRulesRecipes.MultimapValuesStreamRecipe");
        rewriteRun(
                spec -> spec.recipe(recipe)
                        .expectedCyclesThatMakeChanges(1).cycles(1)
                        .parser(JavaParser.fromJavaVersion().classpath(JavaParser.runtimeClasspath())),
                java(
                        """
                                import com.google.common.collect.ImmutableSetMultimap;
                                
                                import java.util.Map;
                                import java.util.stream.Stream;
                                
                                class Test {
                                    Stream<Integer> test() {
                                        return ImmutableSetMultimap.of("foo", 1).entries().stream().map(Map.Entry::getValue);
                                    }
                                }
                                """,
                        """
                                import com.google.common.collect.ImmutableSetMultimap;
                                
                                import java.util.stream.Stream;
                                
                                class Test {
                                    Stream<Integer> test() {
                                        return ImmutableSetMultimap.of("foo", 1).values().stream();
                                    }
                                }
                                """
                ));
    }
}
