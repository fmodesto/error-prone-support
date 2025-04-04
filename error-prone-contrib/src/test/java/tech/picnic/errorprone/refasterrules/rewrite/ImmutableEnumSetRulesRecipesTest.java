package tech.picnic.errorprone.refasterrules.rewrite;

import org.junit.jupiter.api.Test;
import org.openrewrite.Recipe;
import org.openrewrite.java.JavaParser;
import org.openrewrite.test.RewriteTest;

import static org.openrewrite.java.Assertions.java;

final class ImmutableEnumSetRulesRecipesTest implements RewriteTest {

    @Test
    void testSetsImmutableEnumSet1Recipe() {
        Recipe recipe = RewriteUtils.loadRecipe("ImmutableEnumSetRulesRecipes.SetsImmutableEnumSet1Recipe");
        rewriteRun(
                spec -> spec.recipe(recipe)
                        .expectedCyclesThatMakeChanges(1).cycles(1)
                        .parser(JavaParser.fromJavaVersion().classpath(JavaParser.runtimeClasspath())),
                java(
                        """
                                import com.google.common.collect.ImmutableSet;
                                
                                import java.math.RoundingMode;
                                import java.util.EnumSet;
                                
                                class Test {
                                    ImmutableSet<ImmutableSet<RoundingMode>> test() {
                                        return ImmutableSet.of(ImmutableSet.of(RoundingMode.UP), ImmutableSet.copyOf(EnumSet.of(RoundingMode.UP)));
                                    }
                                }
                                """,
                        """
                                import com.google.common.collect.ImmutableSet;
                                import com.google.common.collect.Sets;
                                
                                import java.math.RoundingMode;
                                
                                class Test {
                                    ImmutableSet<ImmutableSet<RoundingMode>> test() {
                                        return ImmutableSet.of(Sets.immutableEnumSet(RoundingMode.UP), Sets.immutableEnumSet(RoundingMode.UP));
                                    }
                                }
                                """
                ));
    }

    @Test
    void testSetsImmutableEnumSet2Recipe() {
        Recipe recipe = RewriteUtils.loadRecipe("ImmutableEnumSetRulesRecipes.SetsImmutableEnumSet2Recipe");
        rewriteRun(
                spec -> spec.recipe(recipe)
                        .expectedCyclesThatMakeChanges(1).cycles(1)
                        .parser(JavaParser.fromJavaVersion().classpath(JavaParser.runtimeClasspath())),
                java(
                        """
                                import com.google.common.collect.ImmutableSet;
                                
                                import java.math.RoundingMode;
                                import java.util.EnumSet;
                                
                                class Test {
                                    ImmutableSet<ImmutableSet<RoundingMode>> test() {
                                        return ImmutableSet.of(ImmutableSet.of(RoundingMode.UP, RoundingMode.DOWN), ImmutableSet.copyOf(EnumSet.of(RoundingMode.UP, RoundingMode.DOWN)));
                                    }
                                }
                                """,
                        """
                                import com.google.common.collect.ImmutableSet;
                                import com.google.common.collect.Sets;
                                
                                import java.math.RoundingMode;
                                
                                class Test {
                                    ImmutableSet<ImmutableSet<RoundingMode>> test() {
                                        return ImmutableSet.of(Sets.immutableEnumSet(RoundingMode.UP, RoundingMode.DOWN), Sets.immutableEnumSet(RoundingMode.UP, RoundingMode.DOWN));
                                    }
                                }
                                """
                ));
    }

    @Test
    void testSetsImmutableEnumSet3Recipe() {
        Recipe recipe = RewriteUtils.loadRecipe("ImmutableEnumSetRulesRecipes.SetsImmutableEnumSet3Recipe");
        rewriteRun(
                spec -> spec.recipe(recipe)
                        .expectedCyclesThatMakeChanges(1).cycles(1)
                        .parser(JavaParser.fromJavaVersion().classpath(JavaParser.runtimeClasspath())),
                java(
                        """
                                import com.google.common.collect.ImmutableSet;
                                
                                import java.math.RoundingMode;
                                import java.util.EnumSet;
                                
                                class Test {
                                    ImmutableSet<ImmutableSet<RoundingMode>> test() {
                                        return ImmutableSet.of(ImmutableSet.of(RoundingMode.UP, RoundingMode.DOWN, RoundingMode.CEILING), ImmutableSet.copyOf(EnumSet.of(RoundingMode.UP, RoundingMode.DOWN, RoundingMode.CEILING)));
                                    }
                                }
                                """,
                        """
                                import com.google.common.collect.ImmutableSet;
                                import com.google.common.collect.Sets;
                                
                                import java.math.RoundingMode;
                                
                                class Test {
                                    ImmutableSet<ImmutableSet<RoundingMode>> test() {
                                        return ImmutableSet.of(Sets.immutableEnumSet(RoundingMode.UP, RoundingMode.DOWN, RoundingMode.CEILING), Sets.immutableEnumSet(RoundingMode.UP, RoundingMode.DOWN, RoundingMode.CEILING));
                                    }
                                }
                                """
                ));
    }

    @Test
    void testSetsImmutableEnumSet4Recipe() {
        Recipe recipe = RewriteUtils.loadRecipe("ImmutableEnumSetRulesRecipes.SetsImmutableEnumSet4Recipe");
        rewriteRun(
                spec -> spec.recipe(recipe)
                        .expectedCyclesThatMakeChanges(1).cycles(1)
                        .parser(JavaParser.fromJavaVersion().classpath(JavaParser.runtimeClasspath())),
                java(
                        """
                                import com.google.common.collect.ImmutableSet;
                                
                                import java.math.RoundingMode;
                                import java.util.EnumSet;
                                
                                class Test {
                                    ImmutableSet<ImmutableSet<RoundingMode>> test() {
                                        return ImmutableSet.of(ImmutableSet.of(RoundingMode.UP, RoundingMode.DOWN, RoundingMode.CEILING, RoundingMode.FLOOR), ImmutableSet.copyOf(EnumSet.of(RoundingMode.UP, RoundingMode.DOWN, RoundingMode.CEILING, RoundingMode.FLOOR)));
                                    }
                                }
                                """,
                        """
                                import com.google.common.collect.ImmutableSet;
                                import com.google.common.collect.Sets;
                                
                                import java.math.RoundingMode;
                                
                                class Test {
                                    ImmutableSet<ImmutableSet<RoundingMode>> test() {
                                        return ImmutableSet.of(Sets.immutableEnumSet(RoundingMode.UP, RoundingMode.DOWN, RoundingMode.CEILING, RoundingMode.FLOOR), Sets.immutableEnumSet(RoundingMode.UP, RoundingMode.DOWN, RoundingMode.CEILING, RoundingMode.FLOOR));
                                    }
                                }
                                """
                ));
    }

    @Test
    void testSetsImmutableEnumSet5Recipe() {
        Recipe recipe = RewriteUtils.loadRecipe("ImmutableEnumSetRulesRecipes.SetsImmutableEnumSet5Recipe");
        rewriteRun(
                spec -> spec.recipe(recipe)
                        .expectedCyclesThatMakeChanges(1).cycles(1)
                        .parser(JavaParser.fromJavaVersion().classpath(JavaParser.runtimeClasspath())),
                java(
                        """
                                import com.google.common.collect.ImmutableSet;
                                
                                import java.math.RoundingMode;
                                import java.util.EnumSet;
                                
                                class Test {
                                    ImmutableSet<ImmutableSet<RoundingMode>> test() {
                                        return ImmutableSet.of(ImmutableSet.of(RoundingMode.UP, RoundingMode.DOWN, RoundingMode.CEILING, RoundingMode.FLOOR, RoundingMode.UNNECESSARY), ImmutableSet.copyOf(EnumSet.of(RoundingMode.UP, RoundingMode.DOWN, RoundingMode.CEILING, RoundingMode.FLOOR, RoundingMode.UNNECESSARY)));
                                    }
                                }
                                """,
                        """
                                import com.google.common.collect.ImmutableSet;
                                import com.google.common.collect.Sets;
                                
                                import java.math.RoundingMode;
                                
                                class Test {
                                    ImmutableSet<ImmutableSet<RoundingMode>> test() {
                                        return ImmutableSet.of(Sets.immutableEnumSet(RoundingMode.UP, RoundingMode.DOWN, RoundingMode.CEILING, RoundingMode.FLOOR, RoundingMode.UNNECESSARY), Sets.immutableEnumSet(RoundingMode.UP, RoundingMode.DOWN, RoundingMode.CEILING, RoundingMode.FLOOR, RoundingMode.UNNECESSARY));
                                    }
                                }
                                """
                ));
    }

    @Test
    void testSetsImmutableEnumSet6Recipe() {
        Recipe recipe = RewriteUtils.loadRecipe("ImmutableEnumSetRulesRecipes.SetsImmutableEnumSet6Recipe");
        rewriteRun(
                spec -> spec.recipe(recipe)
                        .expectedCyclesThatMakeChanges(1).cycles(1)
                        .parser(JavaParser.fromJavaVersion().classpath(JavaParser.runtimeClasspath())),
                java(
                        """
                                import com.google.common.collect.ImmutableSet;
                                
                                import java.math.RoundingMode;
                                
                                class Test {
                                    ImmutableSet<RoundingMode> test() {
                                        return ImmutableSet.of(RoundingMode.UP, RoundingMode.DOWN, RoundingMode.CEILING, RoundingMode.FLOOR, RoundingMode.UNNECESSARY, RoundingMode.HALF_EVEN);
                                    }
                                }
                                """,
                        """
                                import com.google.common.collect.ImmutableSet;
                                import com.google.common.collect.Sets;
                                
                                import java.math.RoundingMode;
                                
                                class Test {
                                    ImmutableSet<RoundingMode> test() {
                                        return Sets.immutableEnumSet(RoundingMode.UP, RoundingMode.DOWN, RoundingMode.CEILING, RoundingMode.FLOOR, RoundingMode.UNNECESSARY, RoundingMode.HALF_EVEN);
                                    }
                                }
                                """
                ));
    }

    @Test
    void testSetsImmutableEnumSetArraysAsListRecipe() {
        Recipe recipe = RewriteUtils.loadRecipe("ImmutableEnumSetRulesRecipes.SetsImmutableEnumSetArraysAsListRecipe");
        rewriteRun(
                spec -> spec.recipe(recipe)
                        .expectedCyclesThatMakeChanges(1).cycles(1)
                        .parser(JavaParser.fromJavaVersion().classpath(JavaParser.runtimeClasspath())),
                java(
                        """
                                import com.google.common.collect.ImmutableSet;
                                
                                import java.math.RoundingMode;
                                
                                class Test {
                                    ImmutableSet<RoundingMode> test() {
                                        return ImmutableSet.copyOf(RoundingMode.values());
                                    }
                                }
                                """,
                        """
                                import com.google.common.collect.ImmutableSet;
                                import com.google.common.collect.Sets;
                                
                                import java.math.RoundingMode;
                                import java.util.Arrays;
                                
                                class Test {
                                    ImmutableSet<RoundingMode> test() {
                                        return Sets.immutableEnumSet(Arrays.asList(RoundingMode.values()));
                                    }
                                }
                                """
                ));
    }

    @Test
    void testSetsImmutableEnumSetIterableRecipe() {
        Recipe recipe = RewriteUtils.loadRecipe("ImmutableEnumSetRulesRecipes.SetsImmutableEnumSetIterableRecipe");
        rewriteRun(
                spec -> spec.recipe(recipe)
                        .expectedCyclesThatMakeChanges(1).cycles(1)
                        .parser(JavaParser.fromJavaVersion().classpath(JavaParser.runtimeClasspath())),
                java(
                        """
                                import com.google.common.collect.ImmutableSet;
                                import com.google.common.collect.Iterables;
                                
                                import java.math.RoundingMode;
                                import java.util.EnumSet;
                                
                                class Test {
                                    ImmutableSet<ImmutableSet<RoundingMode>> test() {
                                        return ImmutableSet.of(ImmutableSet.copyOf(Iterables.cycle(RoundingMode.UP)), ImmutableSet.copyOf(EnumSet.allOf(RoundingMode.class)));
                                    }
                                }
                                """,
                        """
                                import com.google.common.collect.ImmutableSet;
                                import com.google.common.collect.Iterables;
                                import com.google.common.collect.Sets;
                                
                                import java.math.RoundingMode;
                                import java.util.EnumSet;
                                
                                class Test {
                                    ImmutableSet<ImmutableSet<RoundingMode>> test() {
                                        return ImmutableSet.of(Sets.immutableEnumSet(Iterables.cycle(RoundingMode.UP)), Sets.immutableEnumSet(EnumSet.allOf(RoundingMode.class)));
                                    }
                                }
                                """
                ));
    }

    @Test
    void testSetsImmutableEnumSetVarArgsRecipe() {
        Recipe recipe = RewriteUtils.loadRecipe("ImmutableEnumSetRulesRecipes.SetsImmutableEnumSetVarArgsRecipe");
        rewriteRun(
                spec -> spec.recipe(recipe)
                        .expectedCyclesThatMakeChanges(1).cycles(1)
                        .parser(JavaParser.fromJavaVersion().classpath(JavaParser.runtimeClasspath())),
                java(
                        """
                                import com.google.common.collect.ImmutableSet;
                                
                                import java.math.RoundingMode;
                                import java.util.EnumSet;
                                
                                class Test {
                                    ImmutableSet<RoundingMode> test() {
                                        return ImmutableSet.copyOf(EnumSet.of(RoundingMode.UP, RoundingMode.DOWN, RoundingMode.CEILING, RoundingMode.FLOOR, RoundingMode.UNNECESSARY, RoundingMode.HALF_EVEN));
                                    }
                                }
                                """,
                        """
                                import com.google.common.collect.ImmutableSet;
                                import com.google.common.collect.Sets;
                                
                                import java.math.RoundingMode;
                                
                                class Test {
                                    ImmutableSet<RoundingMode> test() {
                                        return Sets.immutableEnumSet(RoundingMode.UP, RoundingMode.DOWN, RoundingMode.CEILING, RoundingMode.FLOOR, RoundingMode.UNNECESSARY, RoundingMode.HALF_EVEN);
                                    }
                                }
                                """
                ));
    }

    @Test
    void testStreamToImmutableEnumSetRecipe() {
        Recipe recipe = RewriteUtils.loadRecipe("ImmutableEnumSetRulesRecipes.StreamToImmutableEnumSetRecipe");
        rewriteRun(
                spec -> spec.recipe(recipe)
                        .expectedCyclesThatMakeChanges(1).cycles(1)
                        .parser(JavaParser.fromJavaVersion().classpath(JavaParser.runtimeClasspath())),
                java(
                        """
                                import com.google.common.collect.BoundType;
                                import com.google.common.collect.ImmutableSet;
                                
                                import java.util.stream.Stream;
                                
                                class Test {
                                    ImmutableSet<BoundType> test() {
                                        return Stream.of(BoundType.OPEN).collect(ImmutableSet.toImmutableSet());
                                    }
                                }
                                """,
                        """
                                import com.google.common.collect.BoundType;
                                import com.google.common.collect.ImmutableSet;
                                import com.google.common.collect.Sets;
                                
                                import java.util.stream.Stream;
                                
                                class Test {
                                    ImmutableSet<BoundType> test() {
                                        return Stream.of(BoundType.OPEN).collect(Sets.toImmutableEnumSet());
                                    }
                                }
                                """
                ));
    }
}
