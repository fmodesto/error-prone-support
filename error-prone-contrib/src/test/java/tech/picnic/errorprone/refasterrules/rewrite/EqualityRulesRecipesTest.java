package tech.picnic.errorprone.refasterrules.rewrite;

import org.junit.jupiter.api.Test;
import org.openrewrite.Recipe;
import org.openrewrite.java.JavaParser;
import org.openrewrite.test.RewriteTest;

import static org.openrewrite.java.Assertions.java;

final class EqualityRulesRecipesTest implements RewriteTest {

    @Test
    void testDoubleNegationRecipe() {
        Recipe recipe = RewriteUtils.loadRecipe("EqualityRulesRecipes.DoubleNegationRecipe");
        rewriteRun(
                spec -> spec.recipe(recipe)
                        .expectedCyclesThatMakeChanges(1).cycles(1)
                        .parser(JavaParser.fromJavaVersion().classpath(JavaParser.runtimeClasspath())),
                java(
                        """
                                class Test {
                                    boolean test() {
                                        return !!Boolean.TRUE;
                                    }
                                }
                                """,
                        """
                                class Test {
                                    boolean test() {
                                        return Boolean.TRUE;
                                    }
                                }
                                """
                ));
    }

    @Test
    void testEnumReferenceEqualityRecipe() {
        Recipe recipe = RewriteUtils.loadRecipe("EqualityRulesRecipes.EnumReferenceEqualityRecipe");
        rewriteRun(
                spec -> spec.recipe(recipe)
                        .expectedCyclesThatMakeChanges(1).cycles(1)
                        .parser(JavaParser.fromJavaVersion().classpath(JavaParser.runtimeClasspath())),
                java(
                        """
                                import com.google.common.collect.ImmutableSet;
                                
                                import java.math.RoundingMode;
                                import java.util.Objects;
                                
                                class Test {
                                    ImmutableSet<Boolean> test() {
                                        return ImmutableSet.of(RoundingMode.UP.equals(RoundingMode.DOWN), Objects.equals(RoundingMode.UP, RoundingMode.DOWN), RoundingMode.UP.ordinal() == RoundingMode.DOWN.ordinal(), !RoundingMode.UP.equals(RoundingMode.DOWN), !Objects.equals(RoundingMode.UP, RoundingMode.DOWN), RoundingMode.UP.ordinal() != RoundingMode.DOWN.ordinal());
                                    }
                                }
                                """,
                        """
                                import com.google.common.collect.ImmutableSet;
                                
                                import java.math.RoundingMode;
                                
                                class Test {
                                    ImmutableSet<Boolean> test() {
                                        return ImmutableSet.of(RoundingMode.UP == RoundingMode.DOWN, RoundingMode.UP == RoundingMode.DOWN, RoundingMode.UP == RoundingMode.DOWN, RoundingMode.UP != RoundingMode.DOWN, RoundingMode.UP != RoundingMode.DOWN, RoundingMode.UP != RoundingMode.DOWN);
                                    }
                                }
                                """
                ));
    }

    @Test
    void testEnumReferenceEqualityLambdaRecipe() {
        Recipe recipe = RewriteUtils.loadRecipe("EqualityRulesRecipes.EnumReferenceEqualityLambdaRecipe");
        rewriteRun(
                spec -> spec.recipe(recipe)
                        .expectedCyclesThatMakeChanges(1).cycles(1)
                        .parser(JavaParser.fromJavaVersion().classpath(JavaParser.runtimeClasspath())),
                java(
                        """
                                import com.google.common.collect.ImmutableSet;
                                
                                import java.math.RoundingMode;
                                import java.util.function.Predicate;
                                
                                class Test {
                                    ImmutableSet<Predicate<RoundingMode>> test() {
                                        return ImmutableSet.of(Predicate.isEqual(RoundingMode.DOWN), RoundingMode.UP::equals);
                                    }
                                }
                                """,
                        """
                                import com.google.common.collect.ImmutableSet;
                                
                                import java.math.RoundingMode;
                                import java.util.function.Predicate;
                                
                                class Test {
                                    ImmutableSet<Predicate<RoundingMode>> test() {
                                        return ImmutableSet.of(v -> v == RoundingMode.DOWN, v -> v == RoundingMode.UP);
                                    }
                                }
                                """
                ));
    }

    @Test
    void testEqualsRecipe() {
        Recipe recipe = RewriteUtils.loadRecipe("EqualityRulesRecipes.EqualsRecipe");
        rewriteRun(
                spec -> spec.recipe(recipe)
                        .expectedCyclesThatMakeChanges(1).cycles(1)
                        .parser(JavaParser.fromJavaVersion().classpath(JavaParser.runtimeClasspath())),
                java(
                        """
                                import com.google.common.collect.ImmutableSet;
                                
                                import java.util.Optional;
                                
                                class Test {
                                    ImmutableSet<Boolean> test() {
                                        return ImmutableSet.of(Optional.of("foo").equals(Optional.of("bar")), Optional.of("baz").equals(Optional.ofNullable("qux")), Optional.ofNullable("quux").equals(Optional.of("quuz")));
                                    }
                                }
                                """,
                        """
                                import com.google.common.collect.ImmutableSet;
                                
                                class Test {
                                    ImmutableSet<Boolean> test() {
                                        return ImmutableSet.of("foo".equals("bar"), "baz".equals("qux"), "quuz".equals("quux"));
                                    }
                                }
                                """
                ));
    }

    @Test
    void testEqualsPredicateRecipe() {
        Recipe recipe = RewriteUtils.loadRecipe("EqualityRulesRecipes.EqualsPredicateRecipe");
        rewriteRun(
                spec -> spec.recipe(recipe)
                        .expectedCyclesThatMakeChanges(1).cycles(1)
                        .parser(JavaParser.fromJavaVersion().classpath(JavaParser.runtimeClasspath())),
                java(
                        """
                                import java.util.stream.Stream;
                                
                                class Test {
                                    boolean test() {
                                        return Stream.of("foo").anyMatch(s -> "bar".equals(s));
                                    }
                                }
                                """,
                        """
                                import java.util.function.Predicate;
                                import java.util.stream.Stream;
                                
                                class Test {
                                    boolean test() {
                                        return Stream.of("foo").anyMatch("bar"::equals);
                                    }
                                }
                                """
                ));
    }

    @Test
    void testIndirectDoubleNegationRecipe() {
        Recipe recipe = RewriteUtils.loadRecipe("EqualityRulesRecipes.IndirectDoubleNegationRecipe");
        rewriteRun(
                spec -> spec.recipe(recipe)
                        .expectedCyclesThatMakeChanges(1).cycles(1)
                        .parser(JavaParser.fromJavaVersion().classpath(JavaParser.runtimeClasspath())),
                java(
                        """
                                import com.google.common.collect.BoundType;
                                import com.google.common.collect.ImmutableSet;
                                
                                class Test {
                                    @SuppressWarnings("SimplifyBooleanExpression")
                                    ImmutableSet<Boolean> test() {
                                        return ImmutableSet.of(true ? false : !false, !(true != false), !((byte) 3 != (byte) 4), !((char) 3 != (char) 4), !((short) 3 != (short) 4), !(3 != 4), !(3L != 4L), !(3F != 4F), !(3.0 != 4.0), !(BoundType.OPEN != BoundType.CLOSED));
                                    }
                                }
                                """,
                        """
                                import com.google.common.collect.BoundType;
                                import com.google.common.collect.ImmutableSet;
                                
                                class Test {
                                    @SuppressWarnings("SimplifyBooleanExpression")
                                    ImmutableSet<Boolean> test() {
                                        return ImmutableSet.of(true == false, true == false, (byte) 3 == (byte) 4, (char) 3 == (char) 4, (short) 3 == (short) 4, 3 == 4, 3L == 4L, 3F == 4F, 3.0 == 4.0, BoundType.OPEN == BoundType.CLOSED);
                                    }
                                }
                                """
                ));
    }

    @Test
    void testNegationRecipe() {
        Recipe recipe = RewriteUtils.loadRecipe("EqualityRulesRecipes.NegationRecipe");
        rewriteRun(
                spec -> spec.recipe(recipe)
                        .expectedCyclesThatMakeChanges(1).cycles(1)
                        .parser(JavaParser.fromJavaVersion().classpath(JavaParser.runtimeClasspath())),
                java(
                        """
                                import com.google.common.collect.BoundType;
                                import com.google.common.collect.ImmutableSet;
                                
                                class Test {
                                    @SuppressWarnings("SimplifyBooleanExpression")
                                    ImmutableSet<Boolean> test() {
                                        return ImmutableSet.of(true ? !false : false, !(true == false), !((byte) 3 == (byte) 4), !((char) 3 == (char) 4), !((short) 3 == (short) 4), !(3 == 4), !(3L == 4L), !(3F == 4F), !(3.0 == 4.0), !(BoundType.OPEN == BoundType.CLOSED));
                                    }
                                }
                                """,
                        """
                                import com.google.common.collect.BoundType;
                                import com.google.common.collect.ImmutableSet;
                                
                                class Test {
                                    @SuppressWarnings("SimplifyBooleanExpression")
                                    ImmutableSet<Boolean> test() {
                                        return ImmutableSet.of(true != false, true != false, (byte) 3 != (byte) 4, (char) 3 != (char) 4, (short) 3 != (short) 4, 3 != 4, 3L != 4L, 3F != 4F, 3.0 != 4.0, BoundType.OPEN != BoundType.CLOSED);
                                    }
                                }
                                """
                ));
    }

    @Test
    void testObjectsEqualsRecipe() {
        Recipe recipe = RewriteUtils.loadRecipe("EqualityRulesRecipes.ObjectsEqualsRecipe");
        rewriteRun(
                spec -> spec.recipe(recipe)
                        .expectedCyclesThatMakeChanges(1).cycles(1)
                        .parser(JavaParser.fromJavaVersion().classpath(JavaParser.runtimeClasspath())),
                java(
                        """
                                import java.util.Optional;
                                
                                class Test {
                                    boolean test() {
                                        return Optional.ofNullable("foo").equals(Optional.ofNullable("bar"));
                                    }
                                }
                                """,
                        """
                                import java.util.Objects;
                                
                                class Test {
                                    boolean test() {
                                        return Objects.equals("foo", "bar");
                                    }
                                }
                                """
                ));
    }

    @Test
    void testPredicateLambdaRecipe() {
        Recipe recipe = RewriteUtils.loadRecipe("EqualityRulesRecipes.PredicateLambdaRecipe");
        rewriteRun(
                spec -> spec.recipe(recipe)
                        .expectedCyclesThatMakeChanges(1).cycles(1)
                        .parser(JavaParser.fromJavaVersion().classpath(JavaParser.runtimeClasspath())),
                java(
                        """
                                import java.util.function.Predicate;
                                
                                class Test {
                                    Predicate<String> test() {
                                        return Predicate.not(v -> v.isEmpty());
                                    }
                                }
                                """,
                        """
                                import java.util.function.Predicate;
                                
                                class Test {
                                    Predicate<String> test() {
                                        return v -> !v.isEmpty();
                                    }
                                }
                                """
                ));
    }
}
