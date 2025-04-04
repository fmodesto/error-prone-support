package tech.picnic.errorprone.refasterrules.rewrite;

import org.junit.jupiter.api.Test;
import org.openrewrite.Recipe;
import org.openrewrite.java.JavaParser;
import org.openrewrite.test.RewriteTest;

import static org.openrewrite.java.Assertions.java;

final class OptionalRulesRecipesTest implements RewriteTest {

    @Test
    void testFilterOuterOptionalAfterFlatMapRecipe() {
        Recipe recipe = RewriteUtils.loadRecipe("OptionalRulesRecipes.FilterOuterOptionalAfterFlatMapRecipe");
        rewriteRun(
                spec -> spec.recipe(recipe)
                        .expectedCyclesThatMakeChanges(1).cycles(1)
                        .parser(JavaParser.fromJavaVersion().classpath(JavaParser.runtimeClasspath())),
                java(
                        """
                                import java.util.Optional;
                                
                                class Test {
                                    Optional<Integer> test() {
                                        return Optional.of("foo").flatMap(v -> Optional.of(v.length()).filter(len -> len > 0));
                                    }
                                }
                                """,
                        """
                                import java.util.Optional;
                                
                                class Test {
                                    Optional<Integer> test() {
                                        return Optional.of("foo").flatMap(v -> Optional.of(v.length())).filter(len -> len > 0);
                                    }
                                }
                                """
                ));
    }

    @Test
    void testFlatMapOuterOptionalAfterFlatMapRecipe() {
        Recipe recipe = RewriteUtils.loadRecipe("OptionalRulesRecipes.FlatMapOuterOptionalAfterFlatMapRecipe");
        rewriteRun(
                spec -> spec.recipe(recipe)
                        .expectedCyclesThatMakeChanges(1).cycles(1)
                        .parser(JavaParser.fromJavaVersion().classpath(JavaParser.runtimeClasspath())),
                java(
                        """
                                import java.util.Optional;
                                import java.util.function.Function;
                                
                                class Test {
                                    Optional<Integer> test() {
                                        return Optional.of("foo").flatMap(v -> Optional.of(v.length()).flatMap(Optional::of));
                                    }
                                }
                                """,
                        """
                                import java.util.Optional;
                                import java.util.function.Function;
                                
                                class Test {
                                    Optional<Integer> test() {
                                        return Optional.of("foo").flatMap(v -> Optional.of(v.length())).flatMap(Optional::of);
                                    }
                                }
                                """
                ));
    }

    @Test
    void testFlatMapToOptionalRecipe() {
        Recipe recipe = RewriteUtils.loadRecipe("OptionalRulesRecipes.FlatMapToOptionalRecipe");
        rewriteRun(
                spec -> spec.recipe(recipe)
                        .expectedCyclesThatMakeChanges(1).cycles(1)
                        .parser(JavaParser.fromJavaVersion().classpath(JavaParser.runtimeClasspath())),
                java(
                        """
                                import java.util.Optional;
                                
                                class Test {
                                    Optional<String> test() {
                                        return Optional.of(1).map(n -> Optional.of(String.valueOf(n)).orElseThrow());
                                    }
                                }
                                """,
                        """
                                import java.util.Optional;
                                
                                class Test {
                                    Optional<String> test() {
                                        return Optional.of(1).flatMap(n -> Optional.of(String.valueOf(n)));
                                    }
                                }
                                """
                ));
    }

    @Test
    void testMapOptionalToBooleanRecipe() {
        Recipe recipe = RewriteUtils.loadRecipe("OptionalRulesRecipes.MapOptionalToBooleanRecipe");
        rewriteRun(
                spec -> spec.recipe(recipe)
                        .expectedCyclesThatMakeChanges(1).cycles(1)
                        .parser(JavaParser.fromJavaVersion().classpath(JavaParser.runtimeClasspath())),
                java(
                        """
                                import com.google.common.collect.ImmutableSet;
                                
                                import java.util.Optional;
                                import java.util.function.Function;
                                
                                class Test {
                                    ImmutableSet<Boolean> test() {
                                        return ImmutableSet.of(Optional.of("foo").map(String::isEmpty).orElse(false), Optional.of("bar").map(s -> s.isEmpty()).orElse(Boolean.FALSE));
                                    }
                                }
                                """,
                        """
                                import com.google.common.collect.ImmutableSet;
                                
                                import java.util.Optional;
                                
                                class Test {
                                    ImmutableSet<Boolean> test() {
                                        return ImmutableSet.of(Optional.of("foo").filter(String::isEmpty).isPresent(), Optional.of("bar").filter(s -> s.isEmpty()).isPresent());
                                    }
                                }
                                """
                ));
    }

    @Test
    void testMapOuterOptionalAfterFlatMapRecipe() {
        Recipe recipe = RewriteUtils.loadRecipe("OptionalRulesRecipes.MapOuterOptionalAfterFlatMapRecipe");
        rewriteRun(
                spec -> spec.recipe(recipe)
                        .expectedCyclesThatMakeChanges(1).cycles(1)
                        .parser(JavaParser.fromJavaVersion().classpath(JavaParser.runtimeClasspath())),
                java(
                        """
                                import java.util.Optional;
                                
                                class Test {
                                    Optional<Integer> test() {
                                        return Optional.of("foo").flatMap(v -> Optional.of(v.length()).map(len -> len * 0));
                                    }
                                }
                                """,
                        """
                                import java.util.Optional;
                                
                                class Test {
                                    Optional<Integer> test() {
                                        return Optional.of("foo").flatMap(v -> Optional.of(v.length())).map(len -> len * 0);
                                    }
                                }
                                """
                ));
    }

    @Test
    void testMapToNullableRecipe() {
        Recipe recipe = RewriteUtils.loadRecipe("OptionalRulesRecipes.MapToNullableRecipe");
        rewriteRun(
                spec -> spec.recipe(recipe)
                        .expectedCyclesThatMakeChanges(1).cycles(1)
                        .parser(JavaParser.fromJavaVersion().classpath(JavaParser.runtimeClasspath())),
                java(
                        """
                                import com.google.common.collect.ImmutableSet;
                                
                                import java.util.Optional;
                                
                                class Test {
                                    ImmutableSet<Optional<String>> test() {
                                        return ImmutableSet.of(Optional.of(1).flatMap(n -> Optional.of(String.valueOf(n))), Optional.of(2).flatMap(n -> Optional.ofNullable(String.valueOf(n))));
                                    }
                                }
                                """,
                        """
                                import com.google.common.collect.ImmutableSet;
                                
                                import java.util.Optional;
                                
                                class Test {
                                    ImmutableSet<Optional<String>> test() {
                                        return ImmutableSet.of(Optional.of(1).map(n -> String.valueOf(n)), Optional.of(2).map(n -> String.valueOf(n)));
                                    }
                                }
                                """
                ));
    }

    @Test
    void testOptionalEmptyRecipe() {
        Recipe recipe = RewriteUtils.loadRecipe("OptionalRulesRecipes.OptionalEmptyRecipe");
        rewriteRun(
                spec -> spec.recipe(recipe)
                        .expectedCyclesThatMakeChanges(1).cycles(1)
                        .parser(JavaParser.fromJavaVersion().classpath(JavaParser.runtimeClasspath())),
                java(
                        """
                                import java.util.Optional;
                                
                                class Test {
                                    Optional<String> test() {
                                        return Optional.ofNullable(null);
                                    }
                                }
                                """,
                        """
                                import java.util.Optional;
                                
                                class Test {
                                    Optional<String> test() {
                                        return Optional.empty();
                                    }
                                }
                                """
                ));
    }

    @Test
    void testOptionalEqualsOptionalRecipe() {
        Recipe recipe = RewriteUtils.loadRecipe("OptionalRulesRecipes.OptionalEqualsOptionalRecipe");
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
                                        return ImmutableSet.of(Optional.of("foo").filter("bar"::equals).isPresent(), Optional.of("baz").stream().anyMatch("qux"::equals));
                                    }
                                }
                                """,
                        """
                                import com.google.common.collect.ImmutableSet;
                                
                                import java.util.Optional;
                                
                                class Test {
                                    ImmutableSet<Boolean> test() {
                                        return ImmutableSet.of(Optional.of("foo").equals(Optional.of("bar")), Optional.of("baz").equals(Optional.of("qux")));
                                    }
                                }
                                """
                ));
    }

    @Test
    void testOptionalFilterRecipe() {
        Recipe recipe = RewriteUtils.loadRecipe("OptionalRulesRecipes.OptionalFilterRecipe");
        rewriteRun(
                spec -> spec.recipe(recipe)
                        .expectedCyclesThatMakeChanges(1).cycles(1)
                        .parser(JavaParser.fromJavaVersion().classpath(JavaParser.runtimeClasspath())),
                java(
                        """
                                import com.google.common.collect.ImmutableSet;
                                
                                import java.util.Optional;
                                
                                class Test {
                                    ImmutableSet<Optional<String>> test() {
                                        return ImmutableSet.of(Optional.of("foo").stream().filter(String::isEmpty).findFirst(), Optional.of("bar").stream().filter(String::isEmpty).findAny());
                                    }
                                }
                                """,
                        """
                                import com.google.common.collect.ImmutableSet;
                                
                                import java.util.Optional;
                                
                                class Test {
                                    ImmutableSet<Optional<String>> test() {
                                        return ImmutableSet.of(Optional.of("foo").filter(String::isEmpty), Optional.of("bar").filter(String::isEmpty));
                                    }
                                }
                                """
                ));
    }

    @Test
    void testOptionalFirstIteratorElementRecipe() {
        Recipe recipe = RewriteUtils.loadRecipe("OptionalRulesRecipes.OptionalFirstIteratorElementRecipe");
        rewriteRun(
                spec -> spec.recipe(recipe)
                        .expectedCyclesThatMakeChanges(1).cycles(1)
                        .parser(JavaParser.fromJavaVersion().classpath(JavaParser.runtimeClasspath())),
                java(
                        """
                                import com.google.common.collect.ImmutableSet;
                                
                                import java.util.Optional;
                                
                                class Test {
                                    ImmutableSet<Optional<String>> test() {
                                        return ImmutableSet.of(ImmutableSet.of("foo").iterator().hasNext() ? Optional.of(ImmutableSet.of("foo").iterator().next()) : Optional.empty(), !ImmutableSet.of("foo").iterator().hasNext() ? Optional.empty() : Optional.of(ImmutableSet.of("foo").iterator().next()));
                                    }
                                }
                                """,
                        """
                                import com.google.common.collect.ImmutableSet;
                                import com.google.common.collect.Streams;
                                
                                import java.util.Optional;
                                
                                class Test {
                                    ImmutableSet<Optional<String>> test() {
                                        return ImmutableSet.of(Streams.stream(ImmutableSet.of("foo").iterator()).findFirst(), Streams.stream(ImmutableSet.of("foo").iterator()).findFirst());
                                    }
                                }
                                """
                ));
    }

    @Test
    void testOptionalIdentityRecipe() {
        Recipe recipe = RewriteUtils.loadRecipe("OptionalRulesRecipes.OptionalIdentityRecipe");
        rewriteRun(
                spec -> spec.recipe(recipe)
                        .expectedCyclesThatMakeChanges(1).cycles(1)
                        .parser(JavaParser.fromJavaVersion().classpath(JavaParser.runtimeClasspath())),
                java(
                        """
                                import com.google.common.collect.ImmutableSet;
                                
                                import java.util.Optional;
                                import java.util.function.Function;
                                
                                class Test {
                                    ImmutableSet<Optional<String>> test() {
                                        return ImmutableSet.of(Optional.of("foo").or(() -> Optional.empty()), Optional.of("bar").or(Optional::empty), Optional.of("baz").map(Optional::of).orElseGet(() -> Optional.empty()), Optional.of("qux").map(Optional::of).orElseGet(Optional::empty), Optional.of("quux").stream().findFirst(), Optional.of("quuz").stream().findAny(), Optional.of("corge").stream().min(String::compareTo), Optional.of("grault").stream().max(String::compareTo));
                                    }
                                }
                                """,
                        """
                                import com.google.common.collect.ImmutableSet;
                                
                                import java.util.Optional;
                                
                                class Test {
                                    ImmutableSet<Optional<String>> test() {
                                        return ImmutableSet.of(Optional.of("foo"), Optional.of("bar"), Optional.of("baz"), Optional.of("qux"), Optional.of("quux"), Optional.of("quuz"), Optional.of("corge"), Optional.of("grault"));
                                    }
                                }
                                """
                ));
    }

    @Test
    void testOptionalIsEmptyRecipe() {
        Recipe recipe = RewriteUtils.loadRecipe("OptionalRulesRecipes.OptionalIsEmptyRecipe");
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
                                        return ImmutableSet.of(!Optional.empty().isPresent(), !Optional.of("foo").isPresent());
                                    }
                                }
                                """,
                        """
                                import com.google.common.collect.ImmutableSet;
                                
                                import java.util.Optional;
                                
                                class Test {
                                    ImmutableSet<Boolean> test() {
                                        return ImmutableSet.of(Optional.empty().isEmpty(), Optional.of("foo").isEmpty());
                                    }
                                }
                                """
                ));
    }

    @Test
    void testOptionalIsPresentRecipe() {
        Recipe recipe = RewriteUtils.loadRecipe("OptionalRulesRecipes.OptionalIsPresentRecipe");
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
                                        return ImmutableSet.of(!Optional.empty().isEmpty(), !Optional.of("foo").isEmpty());
                                    }
                                }
                                """,
                        """
                                import com.google.common.collect.ImmutableSet;
                                
                                import java.util.Optional;
                                
                                class Test {
                                    ImmutableSet<Boolean> test() {
                                        return ImmutableSet.of(Optional.empty().isPresent(), Optional.of("foo").isPresent());
                                    }
                                }
                                """
                ));
    }

    @Test
    void testOptionalMapRecipe() {
        Recipe recipe = RewriteUtils.loadRecipe("OptionalRulesRecipes.OptionalMapRecipe");
        rewriteRun(
                spec -> spec.recipe(recipe)
                        .expectedCyclesThatMakeChanges(1).cycles(1)
                        .parser(JavaParser.fromJavaVersion().classpath(JavaParser.runtimeClasspath())),
                java(
                        """
                                import java.util.Optional;
                                import java.util.function.Function;
                                
                                class Test {
                                    Optional<String> test() {
                                        return Optional.of(1).stream().map(String::valueOf).findAny();
                                    }
                                }
                                """,
                        """
                                import java.util.Optional;
                                import java.util.function.Function;
                                
                                class Test {
                                    Optional<String> test() {
                                        return Optional.of(1).map(String::valueOf);
                                    }
                                }
                                """
                ));
    }

    @Test
    void testOptionalOfNullableRecipe() {
        Recipe recipe = RewriteUtils.loadRecipe("OptionalRulesRecipes.OptionalOfNullableRecipe");
        rewriteRun(
                spec -> spec.recipe(recipe)
                        .expectedCyclesThatMakeChanges(1).cycles(1)
                        .parser(JavaParser.fromJavaVersion().classpath(JavaParser.runtimeClasspath())),
                java(
                        """
                                import com.google.common.collect.ImmutableSet;
                                
                                import java.util.Optional;
                                
                                class Test {
                                    ImmutableSet<Optional<String>> test() {
                                        return ImmutableSet.of(toString() == null ? Optional.empty() : Optional.of(toString()), toString() != null ? Optional.of(toString()) : Optional.empty());
                                    }
                                }
                                """,
                        """
                                import com.google.common.collect.ImmutableSet;
                                
                                import java.util.Optional;
                                
                                class Test {
                                    ImmutableSet<Optional<String>> test() {
                                        return ImmutableSet.of(Optional.ofNullable(toString()), Optional.ofNullable(toString()));
                                    }
                                }
                                """
                ));
    }

    @Test
    void testOptionalOrElseRecipe() {
        Recipe recipe = RewriteUtils.loadRecipe("OptionalRulesRecipes.OptionalOrElseRecipe");
        rewriteRun(
                spec -> spec.recipe(recipe)
                        .expectedCyclesThatMakeChanges(1).cycles(1)
                        .parser(JavaParser.fromJavaVersion().classpath(JavaParser.runtimeClasspath())),
                java(
                        """
                                import com.google.common.collect.ImmutableSet;
                                
                                import java.util.Optional;
                                
                                class Test {
                                    ImmutableSet<String> test() {
                                        return ImmutableSet.of(Optional.of("foo").orElseGet(() -> "bar"), Optional.of("baz").orElseGet(() -> toString()));
                                    }
                                }
                                """,
                        """
                                import com.google.common.collect.ImmutableSet;
                                
                                import java.util.Optional;
                                
                                class Test {
                                    ImmutableSet<String> test() {
                                        return ImmutableSet.of(Optional.of("foo").orElse("bar"), Optional.of("baz").orElseGet(() -> toString()));
                                    }
                                }
                                """
                ));
    }

    @Test
    void testOptionalOrElseThrowRecipe() {
        Recipe recipe = RewriteUtils.loadRecipe("OptionalRulesRecipes.OptionalOrElseThrowRecipe");
        rewriteRun(
                spec -> spec.recipe(recipe)
                        .expectedCyclesThatMakeChanges(1).cycles(1)
                        .parser(JavaParser.fromJavaVersion().classpath(JavaParser.runtimeClasspath())),
                java(
                        """
                                import java.util.Optional;
                                
                                class Test {
                                    String test() {
                                        return Optional.of("foo").get();
                                    }
                                }
                                """,
                        """
                                import java.util.Optional;
                                
                                class Test {
                                    String test() {
                                        return Optional.of("foo").orElseThrow();
                                    }
                                }
                                """
                ));
    }

    @Test
    void testOptionalOrElseThrowMethodReferenceRecipe() {
        Recipe recipe = RewriteUtils.loadRecipe("OptionalRulesRecipes.OptionalOrElseThrowMethodReferenceRecipe");
        rewriteRun(
                spec -> spec.recipe(recipe)
                        .expectedCyclesThatMakeChanges(1).cycles(1)
                        .parser(JavaParser.fromJavaVersion().classpath(JavaParser.runtimeClasspath())),
                java(
                        """
                                import java.util.Optional;
                                import java.util.function.Function;
                                
                                class Test {
                                    Function<Optional<Integer>, Integer> test() {
                                        return Optional::get;
                                    }
                                }
                                """,
                        """
                                import java.util.Optional;
                                import java.util.function.Function;
                                
                                class Test {
                                    Function<Optional<Integer>, Integer> test() {
                                        return Optional::orElseThrow;
                                    }
                                }
                                """
                ));
    }

    @Test
    void testOptionalOrOtherOptionalRecipe() {
        Recipe recipe = RewriteUtils.loadRecipe("OptionalRulesRecipes.OptionalOrOtherOptionalRecipe");
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
                                    ImmutableSet<Optional<String>> test() {
                                        return ImmutableSet.of(Optional.of("foo").map(Optional::of).orElse(Optional.of("bar")), Optional.of("baz").map(Optional::of).orElseGet(() -> Optional.of("qux")), Stream.of(Optional.of("quux"), Optional.of("quuz")).flatMap(Optional::stream).findFirst(), Optional.of("corge").isPresent() ? Optional.of("corge") : Optional.of("grault"));
                                    }
                                }
                                """,
                        """
                                import com.google.common.collect.ImmutableSet;
                                
                                import java.util.Optional;
                                
                                class Test {
                                    ImmutableSet<Optional<String>> test() {
                                        return ImmutableSet.of(Optional.of("foo").or(() -> Optional.of("bar")), Optional.of("baz").or(() -> Optional.of("qux")), Optional.of("quux").or(() -> Optional.of("quuz")), Optional.of("corge").or(() -> Optional.of("grault")));
                                    }
                                }
                                """
                ));
    }

    @Test
    void testOptionalStreamRecipe() {
        Recipe recipe = RewriteUtils.loadRecipe("OptionalRulesRecipes.OptionalStreamRecipe");
        rewriteRun(
                spec -> spec.recipe(recipe)
                        .expectedCyclesThatMakeChanges(1).cycles(1)
                        .parser(JavaParser.fromJavaVersion().classpath(JavaParser.runtimeClasspath())),
                java(
                        """
                                import java.util.Optional;
                                import java.util.function.Function;
                                import java.util.stream.Stream;
                                
                                class Test {
                                    Stream<String> test() {
                                        return Optional.of("foo").map(Stream::of).orElseGet(Stream::empty);
                                    }
                                }
                                """,
                        """
                                import java.util.Optional;
                                import java.util.stream.Stream;
                                
                                class Test {
                                    Stream<String> test() {
                                        return Optional.of("foo").stream();
                                    }
                                }
                                """
                ));
    }

    @Test
    void testOrOrElseThrowRecipe() {
        Recipe recipe = RewriteUtils.loadRecipe("OptionalRulesRecipes.OrOrElseThrowRecipe");
        rewriteRun(
                spec -> spec.recipe(recipe)
                        .expectedCyclesThatMakeChanges(1).cycles(1)
                        .parser(JavaParser.fromJavaVersion().classpath(JavaParser.runtimeClasspath())),
                java(
                        """
                                import java.util.Optional;
                                
                                class Test {
                                    String test() {
                                        return Optional.of("foo").orElseGet(() -> Optional.of("bar").orElseThrow());
                                    }
                                }
                                """,
                        """
                                import java.util.Optional;
                                
                                class Test {
                                    String test() {
                                        return Optional.of("foo").or(() -> Optional.of("bar")).orElseThrow();
                                    }
                                }
                                """
                ));
    }

    @Test
    void testStreamFlatMapOptionalRecipe() {
        Recipe recipe = RewriteUtils.loadRecipe("OptionalRulesRecipes.StreamFlatMapOptionalRecipe");
        rewriteRun(
                spec -> spec.recipe(recipe)
                        .expectedCyclesThatMakeChanges(1).cycles(1)
                        .parser(JavaParser.fromJavaVersion().classpath(JavaParser.runtimeClasspath())),
                java(
                        """
                                import com.google.common.collect.ImmutableSet;
                                import com.google.common.collect.Streams;
                                
                                import java.util.Optional;
                                import java.util.function.Function;
                                import java.util.stream.Stream;
                                
                                class Test {
                                    ImmutableSet<Object> test() {
                                        return ImmutableSet.of(Stream.of(Optional.empty()).filter(Optional::isPresent).map(Optional::orElseThrow), Stream.of(Optional.of("foo")).flatMap(Streams::stream));
                                    }
                                }
                                """,
                        """
                                import com.google.common.collect.ImmutableSet;
                                
                                import java.util.Optional;
                                import java.util.function.Function;
                                import java.util.stream.Stream;
                                
                                class Test {
                                    ImmutableSet<Object> test() {
                                        return ImmutableSet.of(Stream.of(Optional.empty()).flatMap(Optional::stream), Stream.of(Optional.of("foo")).flatMap(Optional::stream));
                                    }
                                }
                                """
                ));
    }

    @Test
    void testStreamMapToOptionalGetRecipe() {
        Recipe recipe = RewriteUtils.loadRecipe("OptionalRulesRecipes.StreamMapToOptionalGetRecipe");
        rewriteRun(
                spec -> spec.recipe(recipe)
                        .expectedCyclesThatMakeChanges(1).cycles(1)
                        .parser(JavaParser.fromJavaVersion().classpath(JavaParser.runtimeClasspath())),
                java(
                        """
                                import java.util.Optional;
                                import java.util.stream.Stream;
                                
                                class Test {
                                    Stream<String> test() {
                                        return Stream.of(1).map(n -> Optional.of(String.valueOf(n)).orElseThrow());
                                    }
                                }
                                """,
                        """
                                import java.util.Optional;
                                import java.util.stream.Stream;
                                
                                class Test {
                                    Stream<String> test() {
                                        return Stream.of(1).flatMap(n -> Optional.of(String.valueOf(n)).stream());
                                    }
                                }
                                """
                ));
    }

    @Test
    void testTernaryOperatorOptionalNegativeFilteringRecipe() {
        Recipe recipe = RewriteUtils.loadRecipe("OptionalRulesRecipes.TernaryOperatorOptionalNegativeFilteringRecipe");
        rewriteRun(
                spec -> spec.recipe(recipe)
                        .expectedCyclesThatMakeChanges(1).cycles(1)
                        .parser(JavaParser.fromJavaVersion().classpath(JavaParser.runtimeClasspath())),
                java(
                        """
                                import java.util.Optional;
                                
                                class Test {
                                    Optional<String> test() {
                                        return "foo".length() > 5 ? Optional.empty() : Optional.of("foo");
                                    }
                                }
                                """,
                        """
                                import java.util.Optional;
                                
                                class Test {
                                    Optional<String> test() {
                                        return /* Or Optional.ofNullable (can't auto-infer). */ Optional.of("foo").filter(v -> v.length() <= 5);
                                    }
                                }
                                """
                ));
    }

    @Test
    void testTernaryOperatorOptionalPositiveFilteringRecipe() {
        Recipe recipe = RewriteUtils.loadRecipe("OptionalRulesRecipes.TernaryOperatorOptionalPositiveFilteringRecipe");
        rewriteRun(
                spec -> spec.recipe(recipe)
                        .expectedCyclesThatMakeChanges(1).cycles(1)
                        .parser(JavaParser.fromJavaVersion().classpath(JavaParser.runtimeClasspath())),
                java(
                        """
                                import java.util.Optional;
                                
                                class Test {
                                    Optional<String> test() {
                                        return "foo".length() > 5 ? Optional.of("foo") : Optional.empty();
                                    }
                                }
                                """,
                        """
                                import java.util.Optional;
                                
                                class Test {
                                    Optional<String> test() {
                                        return /* Or Optional.ofNullable (can't auto-infer). */ Optional.of("foo").filter(v -> v.length() > 5);
                                    }
                                }
                                """
                ));
    }
}
