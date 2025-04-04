package tech.picnic.errorprone.refasterrules.rewrite;

import org.junit.jupiter.api.Test;
import org.openrewrite.Recipe;
import org.openrewrite.java.JavaParser;
import org.openrewrite.test.RewriteTest;

import static org.openrewrite.java.Assertions.java;

final class MapRulesRecipesTest implements RewriteTest {

    @Test
    void testCreateEnumMapRecipe() {
        Recipe recipe = RewriteUtils.loadRecipe("MapRulesRecipes.CreateEnumMapRecipe");
        rewriteRun(
                spec -> spec.recipe(recipe)
                        .expectedCyclesThatMakeChanges(1).cycles(1)
                        .parser(JavaParser.fromJavaVersion().classpath(JavaParser.runtimeClasspath())),
                java(
                        """
                                import java.math.RoundingMode;
                                import java.util.HashMap;
                                import java.util.Map;
                                
                                class Test {
                                    Map<RoundingMode, String> test() {
                                        return new HashMap<>();
                                    }
                                }
                                """,
                        """
                                import java.math.RoundingMode;
                                import java.util.EnumMap;
                                import java.util.Map;
                                
                                class Test {
                                    Map<RoundingMode, String> test() {
                                        return new EnumMap<>(RoundingMode.class);
                                    }
                                }
                                """
                ));
    }

    @Test
    void testMapContainsKeyRecipe() {
        Recipe recipe = RewriteUtils.loadRecipe("MapRulesRecipes.MapContainsKeyRecipe");
        rewriteRun(
                spec -> spec.recipe(recipe)
                        .expectedCyclesThatMakeChanges(1).cycles(1)
                        .parser(JavaParser.fromJavaVersion().classpath(JavaParser.runtimeClasspath())),
                java(
                        """
                                import com.google.common.collect.ImmutableMap;
                                
                                class Test {
                                    boolean test() {
                                        return ImmutableMap.of("foo", 1).keySet().contains("bar");
                                    }
                                }
                                """,
                        """
                                import com.google.common.collect.ImmutableMap;
                                
                                class Test {
                                    boolean test() {
                                        return ImmutableMap.of("foo", 1).containsKey("bar");
                                    }
                                }
                                """
                ));
    }

    @Test
    void testMapContainsValueRecipe() {
        Recipe recipe = RewriteUtils.loadRecipe("MapRulesRecipes.MapContainsValueRecipe");
        rewriteRun(
                spec -> spec.recipe(recipe)
                        .expectedCyclesThatMakeChanges(1).cycles(1)
                        .parser(JavaParser.fromJavaVersion().classpath(JavaParser.runtimeClasspath())),
                java(
                        """
                                import com.google.common.collect.ImmutableMap;
                                
                                class Test {
                                    boolean test() {
                                        return ImmutableMap.of("foo", 1).values().contains(2);
                                    }
                                }
                                """,
                        """
                                import com.google.common.collect.ImmutableMap;
                                
                                class Test {
                                    boolean test() {
                                        return ImmutableMap.of("foo", 1).containsValue(2);
                                    }
                                }
                                """
                ));
    }

    @Test
    void testMapGetOrDefaultRecipe() {
        Recipe recipe = RewriteUtils.loadRecipe("MapRulesRecipes.MapGetOrDefaultRecipe");
        rewriteRun(
                spec -> spec.recipe(recipe)
                        .expectedCyclesThatMakeChanges(1).cycles(1)
                        .parser(JavaParser.fromJavaVersion().classpath(JavaParser.runtimeClasspath())),
                java(
                        """
                                import com.google.common.collect.ImmutableMap;
                                
                                import java.util.Objects;
                                
                                class Test {
                                    String test() {
                                        return Objects.requireNonNullElse(ImmutableMap.of(1, "foo").get("bar"), "baz");
                                    }
                                }
                                """,
                        """
                                import com.google.common.collect.ImmutableMap;
                                
                                class Test {
                                    String test() {
                                        return ImmutableMap.of(1, "foo").getOrDefault("bar", "baz");
                                    }
                                }
                                """
                ));
    }

    @Test
    void testMapGetOrNullRecipe() {
        Recipe recipe = RewriteUtils.loadRecipe("MapRulesRecipes.MapGetOrNullRecipe");
        rewriteRun(
                spec -> spec.recipe(recipe)
                        .expectedCyclesThatMakeChanges(1).cycles(1)
                        .parser(JavaParser.fromJavaVersion().classpath(JavaParser.runtimeClasspath())),
                java(
                        """
                                import com.google.common.collect.ImmutableMap;
                                
                                class Test {
                                    String test() {
                                        return ImmutableMap.of(1, "foo").getOrDefault("bar", null);
                                    }
                                }
                                """,
                        """
                                import com.google.common.collect.ImmutableMap;
                                
                                class Test {
                                    String test() {
                                        return ImmutableMap.of(1, "foo").get("bar");
                                    }
                                }
                                """
                ));
    }

    @Test
    void testMapIsEmptyRecipe() {
        Recipe recipe = RewriteUtils.loadRecipe("MapRulesRecipes.MapIsEmptyRecipe");
        rewriteRun(
                spec -> spec.recipe(recipe)
                        .expectedCyclesThatMakeChanges(1).cycles(1)
                        .parser(JavaParser.fromJavaVersion().classpath(JavaParser.runtimeClasspath())),
                java(
                        """
                                import com.google.common.collect.ImmutableMap;
                                import com.google.common.collect.ImmutableSet;
                                
                                class Test {
                                    ImmutableSet<Boolean> test() {
                                        return ImmutableSet.of(ImmutableMap.of("foo", 1).keySet().isEmpty(), ImmutableMap.of("bar", 2).values().isEmpty(), ImmutableMap.of("baz", 3).entrySet().isEmpty());
                                    }
                                }
                                """,
                        """
                                import com.google.common.collect.ImmutableMap;
                                import com.google.common.collect.ImmutableSet;
                                
                                class Test {
                                    ImmutableSet<Boolean> test() {
                                        return ImmutableSet.of(ImmutableMap.of("foo", 1).isEmpty(), ImmutableMap.of("bar", 2).isEmpty(), ImmutableMap.of("baz", 3).isEmpty());
                                    }
                                }
                                """
                ));
    }

    @Test
    void testMapKeyStreamRecipe() {
        Recipe recipe = RewriteUtils.loadRecipe("MapRulesRecipes.MapKeyStreamRecipe");
        rewriteRun(
                spec -> spec.recipe(recipe)
                        .expectedCyclesThatMakeChanges(1).cycles(1)
                        .parser(JavaParser.fromJavaVersion().classpath(JavaParser.runtimeClasspath())),
                java(
                        """
                                import com.google.common.collect.ImmutableMap;
                                
                                import java.util.Map;
                                import java.util.stream.Stream;
                                
                                class Test {
                                    Stream<String> test() {
                                        return ImmutableMap.of("foo", 1).entrySet().stream().map(Map.Entry::getKey);
                                    }
                                }
                                """,
                        """
                                import com.google.common.collect.ImmutableMap;
                                
                                import java.util.stream.Stream;
                                
                                class Test {
                                    Stream<String> test() {
                                        return ImmutableMap.of("foo", 1).keySet().stream();
                                    }
                                }
                                """
                ));
    }

    @Test
    void testMapSizeRecipe() {
        Recipe recipe = RewriteUtils.loadRecipe("MapRulesRecipes.MapSizeRecipe");
        rewriteRun(
                spec -> spec.recipe(recipe)
                        .expectedCyclesThatMakeChanges(1).cycles(1)
                        .parser(JavaParser.fromJavaVersion().classpath(JavaParser.runtimeClasspath())),
                java(
                        """
                                import com.google.common.collect.ImmutableMap;
                                import com.google.common.collect.ImmutableSet;
                                
                                class Test {
                                    ImmutableSet<Integer> test() {
                                        return ImmutableSet.of(ImmutableMap.of("foo", 1).keySet().size(), ImmutableMap.of("bar", 2).values().size(), ImmutableMap.of("baz", 3).entrySet().size());
                                    }
                                }
                                """,
                        """
                                import com.google.common.collect.ImmutableMap;
                                import com.google.common.collect.ImmutableSet;
                                
                                class Test {
                                    ImmutableSet<Integer> test() {
                                        return ImmutableSet.of(ImmutableMap.of("foo", 1).size(), ImmutableMap.of("bar", 2).size(), ImmutableMap.of("baz", 3).size());
                                    }
                                }
                                """
                ));
    }

    @Test
    void testMapValueStreamRecipe() {
        Recipe recipe = RewriteUtils.loadRecipe("MapRulesRecipes.MapValueStreamRecipe");
        rewriteRun(
                spec -> spec.recipe(recipe)
                        .expectedCyclesThatMakeChanges(1).cycles(1)
                        .parser(JavaParser.fromJavaVersion().classpath(JavaParser.runtimeClasspath())),
                java(
                        """
                                import com.google.common.collect.ImmutableMap;
                                
                                import java.util.Map;
                                import java.util.stream.Stream;
                                
                                class Test {
                                    Stream<Integer> test() {
                                        return ImmutableMap.of("foo", 1).entrySet().stream().map(Map.Entry::getValue);
                                    }
                                }
                                """,
                        """
                                import com.google.common.collect.ImmutableMap;
                                
                                import java.util.stream.Stream;
                                
                                class Test {
                                    Stream<Integer> test() {
                                        return ImmutableMap.of("foo", 1).values().stream();
                                    }
                                }
                                """
                ));
    }
}
