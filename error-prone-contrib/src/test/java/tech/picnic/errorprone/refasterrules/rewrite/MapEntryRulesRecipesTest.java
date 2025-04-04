package tech.picnic.errorprone.refasterrules.rewrite;

import org.junit.jupiter.api.Test;
import org.openrewrite.Recipe;
import org.openrewrite.java.JavaParser;
import org.openrewrite.test.RewriteTest;

import static org.openrewrite.java.Assertions.java;

final class MapEntryRulesRecipesTest implements RewriteTest {

    @Test
    void testMapEntryRecipe() {
        Recipe recipe = RewriteUtils.loadRecipe("MapEntryRulesRecipes.MapEntryRecipe");
        rewriteRun(
                spec -> spec.recipe(recipe)
                        .expectedCyclesThatMakeChanges(1).cycles(1)
                        .parser(JavaParser.fromJavaVersion().classpath(JavaParser.runtimeClasspath())),
                java(
                        """
                                import com.google.common.collect.ImmutableSet;
                                import com.google.common.collect.Maps;
                                
                                import java.util.AbstractMap;
                                import java.util.Map;
                                
                                class Test {
                                    ImmutableSet<Map.Entry<String, Integer>> test() {
                                        return ImmutableSet.of(Maps.immutableEntry("foo", 1), new AbstractMap.SimpleImmutableEntry<>("bar", 2));
                                    }
                                }
                                """,
                        """
                                import com.google.common.collect.ImmutableSet;
                                
                                import java.util.Map;
                                
                                class Test {
                                    ImmutableSet<Map.Entry<String, Integer>> test() {
                                        return ImmutableSet.of(Map.entry("foo", 1), Map.entry("bar", 2));
                                    }
                                }
                                """
                ));
    }

    @Test
    void testMapEntryComparingByKeyRecipe() {
        Recipe recipe = RewriteUtils.loadRecipe("MapEntryRulesRecipes.MapEntryComparingByKeyRecipe");
        rewriteRun(
                spec -> spec.recipe(recipe)
                        .expectedCyclesThatMakeChanges(1).cycles(1)
                        .parser(JavaParser.fromJavaVersion().classpath(JavaParser.runtimeClasspath())),
                java(
                        """
                                import com.google.common.collect.ImmutableSet;
                                
                                import java.util.Comparator;
                                import java.util.Map;
                                
                                class Test {
                                    ImmutableSet<Comparator<Map.Entry<Integer, String>>> test() {
                                        return ImmutableSet.of(Comparator.comparing(Map.Entry::getKey), Map.Entry.comparingByKey(Comparator.naturalOrder()));
                                    }
                                }
                                """,
                        """
                                import com.google.common.collect.ImmutableSet;
                                
                                import java.util.Comparator;
                                import java.util.Map;
                                
                                class Test {
                                    ImmutableSet<Comparator<Map.Entry<Integer, String>>> test() {
                                        return ImmutableSet.of(Map.Entry.comparingByKey(), Map.Entry.comparingByKey());
                                    }
                                }
                                """
                ));
    }

    @Test
    void testMapEntryComparingByKeyWithCustomComparatorRecipe() {
        Recipe recipe = RewriteUtils.loadRecipe("MapEntryRulesRecipes.MapEntryComparingByKeyWithCustomComparatorRecipe");
        rewriteRun(
                spec -> spec.recipe(recipe)
                        .expectedCyclesThatMakeChanges(1).cycles(1)
                        .parser(JavaParser.fromJavaVersion().classpath(JavaParser.runtimeClasspath())),
                java(
                        """
                                import java.util.Comparator;
                                import java.util.Map;
                                
                                class Test {
                                    Comparator<Map.Entry<Integer, String>> test() {
                                        return Comparator.comparing(Map.Entry::getKey, Comparator.comparingInt(i -> i * 2));
                                    }
                                }
                                """,
                        """
                                import java.util.Comparator;
                                import java.util.Map;
                                
                                class Test {
                                    Comparator<Map.Entry<Integer, String>> test() {
                                        return Map.Entry.comparingByKey(Comparator.comparingInt(i -> i * 2));
                                    }
                                }
                                """
                ));
    }

    @Test
    void testMapEntryComparingByValueRecipe() {
        Recipe recipe = RewriteUtils.loadRecipe("MapEntryRulesRecipes.MapEntryComparingByValueRecipe");
        rewriteRun(
                spec -> spec.recipe(recipe)
                        .expectedCyclesThatMakeChanges(1).cycles(1)
                        .parser(JavaParser.fromJavaVersion().classpath(JavaParser.runtimeClasspath())),
                java(
                        """
                                import com.google.common.collect.ImmutableSet;
                                
                                import java.util.Comparator;
                                import java.util.Map;
                                
                                class Test {
                                    ImmutableSet<Comparator<Map.Entry<Integer, String>>> test() {
                                        return ImmutableSet.of(Comparator.comparing(Map.Entry::getValue), Map.Entry.comparingByValue(Comparator.naturalOrder()));
                                    }
                                }
                                """,
                        """
                                import com.google.common.collect.ImmutableSet;
                                
                                import java.util.Comparator;
                                import java.util.Map;
                                
                                class Test {
                                    ImmutableSet<Comparator<Map.Entry<Integer, String>>> test() {
                                        return ImmutableSet.of(Map.Entry.comparingByValue(), Map.Entry.comparingByValue());
                                    }
                                }
                                """
                ));
    }

    @Test
    void testMapEntryComparingByValueWithCustomComparatorRecipe() {
        Recipe recipe = RewriteUtils.loadRecipe("MapEntryRulesRecipes.MapEntryComparingByValueWithCustomComparatorRecipe");
        rewriteRun(
                spec -> spec.recipe(recipe)
                        .expectedCyclesThatMakeChanges(1).cycles(1)
                        .parser(JavaParser.fromJavaVersion().classpath(JavaParser.runtimeClasspath())),
                java(
                        """
                                import java.util.Comparator;
                                import java.util.Map;
                                
                                class Test {
                                    Comparator<Map.Entry<Integer, String>> test() {
                                        return Comparator.comparing(Map.Entry::getValue, Comparator.comparingInt(String::length));
                                    }
                                }
                                """,
                        """
                                import java.util.Comparator;
                                import java.util.Map;
                                
                                class Test {
                                    Comparator<Map.Entry<Integer, String>> test() {
                                        return Map.Entry.comparingByValue(Comparator.comparingInt(String::length));
                                    }
                                }
                                """
                ));
    }
}
