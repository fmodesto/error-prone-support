package tech.picnic.errorprone.refasterrules.rewrite;

import org.junit.jupiter.api.Test;
import org.openrewrite.Recipe;
import org.openrewrite.java.JavaParser;
import org.openrewrite.test.RewriteTest;

import static org.openrewrite.java.Assertions.java;

final class ReactorRulesRecipesTest implements RewriteTest {

    @Test
    void testConcatMapIterableIdentityRecipe() {
        Recipe recipe = RewriteUtils.loadRecipe("ReactorRulesRecipes.ConcatMapIterableIdentityRecipe");
        rewriteRun(
                spec -> spec.recipe(recipe)
                        .expectedCyclesThatMakeChanges(1).cycles(1)
                        .parser(JavaParser.fromJavaVersion().classpath(JavaParser.runtimeClasspath())),
                java(
                        """
                                import com.google.common.collect.ImmutableList;
                                import com.google.common.collect.ImmutableSet;
                                import reactor.core.publisher.Flux;
                                
                                import java.util.function.Function;
                                
                                class Test {
                                    ImmutableSet<Flux<String>> test() {
                                        return ImmutableSet.of(Flux.just(ImmutableList.of("foo")).concatMap(list -> Flux.fromIterable(list)), Flux.just(ImmutableList.of("bar")).concatMap(Flux::fromIterable));
                                    }
                                }
                                """,
                        """
                                import com.google.common.collect.ImmutableList;
                                import com.google.common.collect.ImmutableSet;
                                import reactor.core.publisher.Flux;
                                
                                import java.util.function.Function;
                                
                                class Test {
                                    ImmutableSet<Flux<String>> test() {
                                        return ImmutableSet.of(Flux.just(ImmutableList.of("foo")).concatMapIterable(Function.identity()), Flux.just(ImmutableList.of("bar")).concatMapIterable(Function.identity()));
                                    }
                                }
                                """
                ));
    }

    @Test
    void testConcatMapIterableIdentityWithPrefetchRecipe() {
        Recipe recipe = RewriteUtils.loadRecipe("ReactorRulesRecipes.ConcatMapIterableIdentityWithPrefetchRecipe");
        rewriteRun(
                spec -> spec.recipe(recipe)
                        .expectedCyclesThatMakeChanges(1).cycles(1)
                        .parser(JavaParser.fromJavaVersion().classpath(JavaParser.runtimeClasspath())),
                java(
                        """
                                import com.google.common.collect.ImmutableList;
                                import com.google.common.collect.ImmutableSet;
                                import reactor.core.publisher.Flux;
                                
                                import java.util.function.Function;
                                
                                class Test {
                                    ImmutableSet<Flux<String>> test() {
                                        return ImmutableSet.of(Flux.just(ImmutableList.of("foo")).concatMap(list -> Flux.fromIterable(list), 1), Flux.just(ImmutableList.of("bar")).concatMap(Flux::fromIterable, 2));
                                    }
                                }
                                """,
                        """
                                import com.google.common.collect.ImmutableList;
                                import com.google.common.collect.ImmutableSet;
                                import reactor.core.publisher.Flux;
                                
                                import java.util.function.Function;
                                
                                class Test {
                                    ImmutableSet<Flux<String>> test() {
                                        return ImmutableSet.of(Flux.just(ImmutableList.of("foo")).concatMapIterable(Function.identity(), 1), Flux.just(ImmutableList.of("bar")).concatMapIterable(Function.identity(), 2));
                                    }
                                }
                                """
                ));
    }

    @Test
    void testContextEmptyRecipe() {
        Recipe recipe = RewriteUtils.loadRecipe("ReactorRulesRecipes.ContextEmptyRecipe");
        rewriteRun(
                spec -> spec.recipe(recipe)
                        .expectedCyclesThatMakeChanges(1).cycles(1)
                        .parser(JavaParser.fromJavaVersion().classpath(JavaParser.runtimeClasspath())),
                java(
                        """
                                import com.google.common.collect.ImmutableMap;
                                import com.google.common.collect.ImmutableSet;
                                import reactor.util.context.Context;
                                
                                class Test {
                                    ImmutableSet<Context> test() {
                                        return ImmutableSet.of(Context.of(ImmutableMap.of()), Context.of(ImmutableMap.of(1, 2)));
                                    }
                                }
                                """,
                        """
                                import com.google.common.collect.ImmutableMap;
                                import com.google.common.collect.ImmutableSet;
                                import reactor.util.context.Context;
                                
                                class Test {
                                    ImmutableSet<Context> test() {
                                        return ImmutableSet.of(Context.empty(), Context.of(ImmutableMap.of(1, 2)));
                                    }
                                }
                                """
                ));
    }

    @Test
    void testFluxAsStepVerifierExpectNextRecipe() {
        Recipe recipe = RewriteUtils.loadRecipe("ReactorRulesRecipes.FluxAsStepVerifierExpectNextRecipe");
        rewriteRun(
                spec -> spec.recipe(recipe)
                        .expectedCyclesThatMakeChanges(1).cycles(1)
                        .parser(JavaParser.fromJavaVersion().classpath(JavaParser.runtimeClasspath())),
                java(
                        """
                                import com.google.common.collect.ImmutableList;
                                import org.assertj.core.api.Assertions;
                                import reactor.core.publisher.Flux;
                                import reactor.core.publisher.Mono;
                                import reactor.test.StepVerifier;
                                
                                import java.util.function.Function;
                                
                                class Test {
                                    StepVerifier.Step<?> test() {
                                        return Flux.just(1).collect(ImmutableList.toImmutableList()).as(StepVerifier::create).assertNext(list -> Assertions.assertThat(list).containsExactly(2));
                                    }
                                }
                                """,
                        """
                                import reactor.core.publisher.Flux;
                                import reactor.test.StepVerifier;
                                
                                import java.util.function.Function;
                                
                                class Test {
                                    StepVerifier.Step<?> test() {
                                        return Flux.just(1).as(StepVerifier::create).expectNext(2);
                                    }
                                }
                                """
                ));
    }

    @Test
    void testFluxCastRecipe() {
        Recipe recipe = RewriteUtils.loadRecipe("ReactorRulesRecipes.FluxCastRecipe");
        rewriteRun(
                spec -> spec.recipe(recipe)
                        .expectedCyclesThatMakeChanges(1).cycles(1)
                        .parser(JavaParser.fromJavaVersion().classpath(JavaParser.runtimeClasspath())),
                java(
                        """
                                import reactor.core.publisher.Flux;
                                
                                import java.util.function.Function;
                                
                                class Test {
                                    Flux<Number> test() {
                                        return Flux.just(1).map(Number.class::cast);
                                    }
                                }
                                """,
                        """
                                import reactor.core.publisher.Flux;
                                
                                class Test {
                                    Flux<Number> test() {
                                        return Flux.just(1).cast(Number.class);
                                    }
                                }
                                """
                ));
    }

    @Test
    void testFluxCollectToImmutableListRecipe() {
        Recipe recipe = RewriteUtils.loadRecipe("ReactorRulesRecipes.FluxCollectToImmutableListRecipe");
        rewriteRun(
                spec -> spec.recipe(recipe)
                        .expectedCyclesThatMakeChanges(1).cycles(1)
                        .parser(JavaParser.fromJavaVersion().classpath(JavaParser.runtimeClasspath())),
                java(
                        """
                                import reactor.core.publisher.Flux;
                                import reactor.core.publisher.Mono;
                                
                                import java.util.List;
                                
                                class Test {
                                    Mono<List<Integer>> test() {
                                        return Flux.just(1).collectList();
                                    }
                                }
                                """,
                        """
                                import com.google.common.collect.ImmutableList;
                                import reactor.core.publisher.Flux;
                                import reactor.core.publisher.Mono;
                                
                                import java.util.List;
                                
                                class Test {
                                    Mono<List<Integer>> test() {
                                        return Flux.just(1).collect(ImmutableList.toImmutableList());
                                    }
                                }
                                """
                ));
    }

    @Test
    void testFluxCollectToImmutableSetRecipe() {
        Recipe recipe = RewriteUtils.loadRecipe("ReactorRulesRecipes.FluxCollectToImmutableSetRecipe");
        rewriteRun(
                spec -> spec.recipe(recipe)
                        .expectedCyclesThatMakeChanges(1).cycles(1)
                        .parser(JavaParser.fromJavaVersion().classpath(JavaParser.runtimeClasspath())),
                java(
                        """
                                import com.google.common.collect.ImmutableList;
                                import com.google.common.collect.ImmutableSet;
                                import reactor.core.publisher.Flux;
                                import reactor.core.publisher.Mono;
                                
                                import java.util.function.Function;
                                
                                class Test {
                                    Mono<ImmutableSet<Integer>> test() {
                                        return Flux.just(1).collect(ImmutableList.toImmutableList()).map(ImmutableSet::copyOf);
                                    }
                                }
                                """,
                        """
                                import com.google.common.collect.ImmutableSet;
                                import reactor.core.publisher.Flux;
                                import reactor.core.publisher.Mono;
                                
                                class Test {
                                    Mono<ImmutableSet<Integer>> test() {
                                        return Flux.just(1).collect(ImmutableSet.toImmutableSet());
                                    }
                                }
                                """
                ));
    }

    @Test
    void testFluxConcatMapRecipe() {
        Recipe recipe = RewriteUtils.loadRecipe("ReactorRulesRecipes.FluxConcatMapRecipe");
        rewriteRun(
                spec -> spec.recipe(recipe)
                        .expectedCyclesThatMakeChanges(1).cycles(1)
                        .parser(JavaParser.fromJavaVersion().classpath(JavaParser.runtimeClasspath())),
                java(
                        """
                                import com.google.common.collect.ImmutableSet;
                                import reactor.core.publisher.Flux;
                                import reactor.core.publisher.Mono;
                                
                                import java.util.function.Function;
                                
                                class Test {
                                    ImmutableSet<Flux<Integer>> test() {
                                        return ImmutableSet.of(Flux.just(1).concatMap(Mono::just, 0), Flux.just(2).flatMap(Mono::just, 1), Flux.just(3).flatMapSequential(Mono::just, 1), Flux.just(4).map(Mono::just).concatMap(Function.identity()), Flux.just(5).map(Mono::just).concatMap(v -> v), Flux.just(6).map(Mono::just).concatMap(v -> Mono.empty()));
                                    }
                                }
                                """,
                        """
                                import com.google.common.collect.ImmutableSet;
                                import reactor.core.publisher.Flux;
                                import reactor.core.publisher.Mono;
                                
                                import java.util.function.Function;
                                
                                class Test {
                                    ImmutableSet<Flux<Integer>> test() {
                                        return ImmutableSet.of(Flux.just(1).concatMap(Mono::just), Flux.just(2).concatMap(Mono::just), Flux.just(3).concatMap(Mono::just), Flux.just(4).concatMap(Mono::just), Flux.just(5).concatMap(Mono::just), Flux.just(6).map(Mono::just).concatMap(v -> Mono.empty()));
                                    }
                                }
                                """
                ));
    }

    @Test
    void testFluxConcatMapIterableRecipe() {
        Recipe recipe = RewriteUtils.loadRecipe("ReactorRulesRecipes.FluxConcatMapIterableRecipe");
        rewriteRun(
                spec -> spec.recipe(recipe)
                        .expectedCyclesThatMakeChanges(1).cycles(1)
                        .parser(JavaParser.fromJavaVersion().classpath(JavaParser.runtimeClasspath())),
                java(
                        """
                                import com.google.common.collect.ImmutableList;
                                import com.google.common.collect.ImmutableSet;
                                import reactor.core.publisher.Flux;
                                
                                import java.util.function.Function;
                                
                                class Test {
                                    ImmutableSet<Flux<Integer>> test() {
                                        return ImmutableSet.of(Flux.just(1).flatMapIterable(ImmutableList::of), Flux.just(2).map(ImmutableList::of).concatMapIterable(Function.identity()), Flux.just(3).map(ImmutableList::of).concatMapIterable(v -> v), Flux.just(4).map(ImmutableList::of).concatMapIterable(v -> ImmutableSet.of()));
                                    }
                                }
                                """,
                        """
                                import com.google.common.collect.ImmutableList;
                                import com.google.common.collect.ImmutableSet;
                                import reactor.core.publisher.Flux;
                                
                                import java.util.function.Function;
                                
                                class Test {
                                    ImmutableSet<Flux<Integer>> test() {
                                        return ImmutableSet.of(Flux.just(1).concatMapIterable(ImmutableList::of), Flux.just(2).concatMapIterable(ImmutableList::of), Flux.just(3).concatMapIterable(ImmutableList::of), Flux.just(4).map(ImmutableList::of).concatMapIterable(v -> ImmutableSet.of()));
                                    }
                                }
                                """
                ));
    }

    @Test
    void testFluxConcatMapIterableWithPrefetchRecipe() {
        Recipe recipe = RewriteUtils.loadRecipe("ReactorRulesRecipes.FluxConcatMapIterableWithPrefetchRecipe");
        rewriteRun(
                spec -> spec.recipe(recipe)
                        .expectedCyclesThatMakeChanges(1).cycles(1)
                        .parser(JavaParser.fromJavaVersion().classpath(JavaParser.runtimeClasspath())),
                java(
                        """
                                import com.google.common.collect.ImmutableList;
                                import com.google.common.collect.ImmutableSet;
                                import reactor.core.publisher.Flux;
                                
                                import java.util.function.Function;
                                
                                class Test {
                                    ImmutableSet<Flux<Integer>> test() {
                                        return ImmutableSet.of(Flux.just(1).flatMapIterable(ImmutableList::of, 5), Flux.just(2).map(ImmutableList::of).concatMapIterable(Function.identity(), 5), Flux.just(3).map(ImmutableList::of).concatMapIterable(v -> v, 5), Flux.just(4).map(ImmutableList::of).concatMapIterable(v -> ImmutableSet.of(), 5));
                                    }
                                }
                                """,
                        """
                                import com.google.common.collect.ImmutableList;
                                import com.google.common.collect.ImmutableSet;
                                import reactor.core.publisher.Flux;
                                
                                import java.util.function.Function;
                                
                                class Test {
                                    ImmutableSet<Flux<Integer>> test() {
                                        return ImmutableSet.of(Flux.just(1).concatMapIterable(ImmutableList::of, 5), Flux.just(2).concatMapIterable(ImmutableList::of, 5), Flux.just(3).concatMapIterable(ImmutableList::of, 5), Flux.just(4).map(ImmutableList::of).concatMapIterable(v -> ImmutableSet.of(), 5));
                                    }
                                }
                                """
                ));
    }

    @Test
    void testFluxConcatMapWithPrefetchRecipe() {
        Recipe recipe = RewriteUtils.loadRecipe("ReactorRulesRecipes.FluxConcatMapWithPrefetchRecipe");
        rewriteRun(
                spec -> spec.recipe(recipe)
                        .expectedCyclesThatMakeChanges(1).cycles(1)
                        .parser(JavaParser.fromJavaVersion().classpath(JavaParser.runtimeClasspath())),
                java(
                        """
                                import com.google.common.collect.ImmutableSet;
                                import reactor.core.publisher.Flux;
                                import reactor.core.publisher.Mono;
                                
                                import java.util.function.Function;
                                
                                class Test {
                                    ImmutableSet<Flux<Integer>> test() {
                                        return ImmutableSet.of(Flux.just(1).flatMap(Mono::just, 1, 3), Flux.just(2).flatMapSequential(Mono::just, 1, 4), Flux.just(3).map(Mono::just).concatMap(Function.identity(), 5), Flux.just(4).map(Mono::just).concatMap(v -> v, 6), Flux.just(5).map(Mono::just).concatMap(v -> Mono.empty(), 7));
                                    }
                                }
                                """,
                        """
                                import com.google.common.collect.ImmutableSet;
                                import reactor.core.publisher.Flux;
                                import reactor.core.publisher.Mono;
                                
                                import java.util.function.Function;
                                
                                class Test {
                                    ImmutableSet<Flux<Integer>> test() {
                                        return ImmutableSet.of(Flux.just(1).concatMap(Mono::just, 3), Flux.just(2).concatMap(Mono::just, 4), Flux.just(3).concatMap(Mono::just, 5), Flux.just(4).concatMap(Mono::just, 6), Flux.just(5).map(Mono::just).concatMap(v -> Mono.empty(), 7));
                                    }
                                }
                                """
                ));
    }

    @Test
    void testFluxCountMapMathToIntExactRecipe() {
        Recipe recipe = RewriteUtils.loadRecipe("ReactorRulesRecipes.FluxCountMapMathToIntExactRecipe");
        rewriteRun(
                spec -> spec.recipe(recipe)
                        .expectedCyclesThatMakeChanges(1).cycles(1)
                        .parser(JavaParser.fromJavaVersion().classpath(JavaParser.runtimeClasspath())),
                java(
                        """
                                import com.google.common.collect.ImmutableCollection;
                                import com.google.common.collect.ImmutableList;
                                import com.google.common.collect.ImmutableSet;
                                import reactor.core.publisher.Flux;
                                import reactor.core.publisher.Mono;
                                
                                import java.util.ArrayList;
                                import java.util.Collection;
                                import java.util.List;
                                import java.util.function.Function;
                                import java.util.function.Supplier;
                                import java.util.stream.Collectors;
                                
                                class Test {
                                    ImmutableSet<Mono<Integer>> test() {
                                        return ImmutableSet.of(Flux.just(1).collect(ImmutableList.toImmutableList()).map(Collection::size), Flux.just(2).collect(ImmutableList.toImmutableList()).map(List::size), Flux.just(3).collect(ImmutableList.toImmutableList()).map(ImmutableCollection::size), Flux.just(4).collect(ImmutableList.toImmutableList()).map(ImmutableList::size), Flux.just(5).collect(Collectors.toCollection(ArrayList::new)).map(Collection::size), Flux.just(6).collect(Collectors.toCollection(ArrayList::new)).map(List::size));
                                    }
                                }
                                """,
                        """
                                import com.google.common.collect.ImmutableSet;
                                import reactor.core.publisher.Flux;
                                import reactor.core.publisher.Mono;
                                
                                import java.util.function.Function;
                                
                                class Test {
                                    ImmutableSet<Mono<Integer>> test() {
                                        return ImmutableSet.of(Flux.just(1).count().map(Math::toIntExact), Flux.just(2).count().map(Math::toIntExact), Flux.just(3).count().map(Math::toIntExact), Flux.just(4).count().map(Math::toIntExact), Flux.just(5).count().map(Math::toIntExact), Flux.just(6).count().map(Math::toIntExact));
                                    }
                                }
                                """
                ));
    }

    @Test
    void testFluxDefaultIfEmptyRecipe() {
        Recipe recipe = RewriteUtils.loadRecipe("ReactorRulesRecipes.FluxDefaultIfEmptyRecipe");
        rewriteRun(
                spec -> spec.recipe(recipe)
                        .expectedCyclesThatMakeChanges(1).cycles(1)
                        .parser(JavaParser.fromJavaVersion().classpath(JavaParser.runtimeClasspath())),
                java(
                        """
                                import com.google.common.collect.ImmutableSet;
                                import reactor.core.publisher.Flux;
                                import reactor.core.publisher.Mono;
                                
                                class Test {
                                    ImmutableSet<Flux<String>> test() {
                                        return ImmutableSet.of(Flux.just("foo").switchIfEmpty(Mono.just("bar")), Flux.just("baz").switchIfEmpty(Flux.just("qux")));
                                    }
                                }
                                """,
                        """
                                import com.google.common.collect.ImmutableSet;
                                import reactor.core.publisher.Flux;
                                
                                class Test {
                                    ImmutableSet<Flux<String>> test() {
                                        return ImmutableSet.of(Flux.just("foo").defaultIfEmpty("bar"), Flux.just("baz").defaultIfEmpty("qux"));
                                    }
                                }
                                """
                ));
    }

    @Test
    void testFluxDeferredErrorRecipe() {
        Recipe recipe = RewriteUtils.loadRecipe("ReactorRulesRecipes.FluxDeferredErrorRecipe");
        rewriteRun(
                spec -> spec.recipe(recipe)
                        .expectedCyclesThatMakeChanges(1).cycles(1)
                        .parser(JavaParser.fromJavaVersion().classpath(JavaParser.runtimeClasspath())),
                java(
                        """
                                import reactor.core.publisher.Flux;
                                
                                class Test {
                                    Flux<Void> test() {
                                        return Flux.defer(() -> Flux.error(new IllegalStateException()));
                                    }
                                }
                                """,
                        """
                                import reactor.core.publisher.Flux;
                                
                                class Test {
                                    Flux<Void> test() {
                                        return Flux.error(() -> new IllegalStateException());
                                    }
                                }
                                """
                ));
    }

    @Test
    void testFluxDoOnErrorRecipe() {
        Recipe recipe = RewriteUtils.loadRecipe("ReactorRulesRecipes.FluxDoOnErrorRecipe");
        rewriteRun(
                spec -> spec.recipe(recipe)
                        .expectedCyclesThatMakeChanges(1).cycles(1)
                        .parser(JavaParser.fromJavaVersion().classpath(JavaParser.runtimeClasspath())),
                java(
                        """
                                import reactor.core.publisher.Flux;
                                
                                class Test {
                                    Flux<Integer> test() {
                                        return Flux.just(1).doOnError(IllegalArgumentException.class::isInstance, e -> {
                                        });
                                    }
                                }
                                """,
                        """
                                import reactor.core.publisher.Flux;
                                
                                class Test {
                                    Flux<Integer> test() {
                                        return Flux.just(1).doOnError(IllegalArgumentException.class, e -> {
                                        });
                                    }
                                }
                                """
                ));
    }

    @Test
    void testFluxEmptyRecipe() {
        Recipe recipe = RewriteUtils.loadRecipe("ReactorRulesRecipes.FluxEmptyRecipe");
        rewriteRun(
                spec -> spec.recipe(recipe)
                        .expectedCyclesThatMakeChanges(1).cycles(1)
                        .parser(JavaParser.fromJavaVersion().classpath(JavaParser.runtimeClasspath())),
                java(
                        """
                                import com.google.common.collect.ImmutableSet;
                                import reactor.core.publisher.Flux;
                                
                                class Test {
                                    ImmutableSet<Flux<?>> test() {
                                        return ImmutableSet.of(Flux.concat(), Flux.concatDelayError(), Flux.firstWithSignal(), Flux.just(), Flux.merge(), Flux.merge(1), Flux.mergeComparing((a, b) -> 0), Flux.mergeComparing(1, (a, b) -> 0), Flux.mergeComparingDelayError(1, (a, b) -> 0), Flux.mergeDelayError(1), Flux.mergePriority((a, b) -> 0), Flux.mergePriority(1, (a, b) -> 0), Flux.mergePriorityDelayError(1, (a, b) -> 0), Flux.mergeSequential(), Flux.mergeSequential(1), Flux.mergeSequentialDelayError(1), Flux.zip(v -> v), Flux.zip(v -> v, 1), Flux.combineLatest(v -> v), Flux.combineLatest(v -> v, 1), Flux.mergeComparing(), Flux.mergePriority(), Flux.range(0, 0));
                                    }
                                }
                                """,
                        """
                                import com.google.common.collect.ImmutableSet;
                                import reactor.core.publisher.Flux;
                                
                                class Test {
                                    ImmutableSet<Flux<?>> test() {
                                        return ImmutableSet.of(Flux.empty(), Flux.empty(), Flux.empty(), Flux.empty(), Flux.empty(), Flux.empty(), Flux.empty(), Flux.empty(), Flux.empty(), Flux.empty(), Flux.empty(), Flux.empty(), Flux.empty(), Flux.empty(), Flux.empty(), Flux.empty(), Flux.empty(), Flux.empty(), Flux.empty(), Flux.empty(), Flux.empty(), Flux.empty(), Flux.empty());
                                    }
                                }
                                """
                ));
    }

    @Test
    void testFluxErrorSupplierRecipe() {
        Recipe recipe = RewriteUtils.loadRecipe("ReactorRulesRecipes.FluxErrorSupplierRecipe");
        rewriteRun(
                spec -> spec.recipe(recipe)
                        .expectedCyclesThatMakeChanges(1).cycles(1)
                        .parser(JavaParser.fromJavaVersion().classpath(JavaParser.runtimeClasspath())),
                java(
                        """
                                import reactor.core.publisher.Flux;
                                
                                import java.util.function.Supplier;
                                
                                class Test {
                                    Flux<Void> test() {
                                        return Flux.error(() -> ((Supplier<RuntimeException>) null).get());
                                    }
                                }
                                """,
                        """
                                import reactor.core.publisher.Flux;
                                
                                import java.util.function.Supplier;
                                
                                class Test {
                                    Flux<Void> test() {
                                        return Flux.error(((Supplier<RuntimeException>) null));
                                    }
                                }
                                """
                ));
    }

    @Test
    void testFluxFilterSortRecipe() {
        Recipe recipe = RewriteUtils.loadRecipe("ReactorRulesRecipes.FluxFilterSortRecipe");
        rewriteRun(
                spec -> spec.recipe(recipe)
                        .expectedCyclesThatMakeChanges(1).cycles(1)
                        .parser(JavaParser.fromJavaVersion().classpath(JavaParser.runtimeClasspath())),
                java(
                        """
                                import reactor.core.publisher.Flux;
                                
                                class Test {
                                    Flux<Integer> test() {
                                        return Flux.just(1, 4, 3, 2).sort().filter(i -> i % 2 == 0);
                                    }
                                }
                                """,
                        """
                                import reactor.core.publisher.Flux;
                                
                                class Test {
                                    Flux<Integer> test() {
                                        return Flux.just(1, 4, 3, 2).filter(i -> i % 2 == 0).sort();
                                    }
                                }
                                """
                ));
    }

    @Test
    void testFluxFilterSortWithComparatorRecipe() {
        Recipe recipe = RewriteUtils.loadRecipe("ReactorRulesRecipes.FluxFilterSortWithComparatorRecipe");
        rewriteRun(
                spec -> spec.recipe(recipe)
                        .expectedCyclesThatMakeChanges(1).cycles(1)
                        .parser(JavaParser.fromJavaVersion().classpath(JavaParser.runtimeClasspath())),
                java(
                        """
                                import reactor.core.publisher.Flux;
                                
                                import java.util.Comparator;
                                
                                class Test {
                                    Flux<Integer> test() {
                                        return Flux.just(1, 4, 3, 2).sort(Comparator.reverseOrder()).filter(i -> i % 2 == 0);
                                    }
                                }
                                """,
                        """
                                import reactor.core.publisher.Flux;
                                
                                import java.util.Comparator;
                                
                                class Test {
                                    Flux<Integer> test() {
                                        return Flux.just(1, 4, 3, 2).filter(i -> i % 2 == 0).sort(Comparator.reverseOrder());
                                    }
                                }
                                """
                ));
    }

    @Test
    void testFluxFromIterableRecipe() {
        Recipe recipe = RewriteUtils.loadRecipe("ReactorRulesRecipes.FluxFromIterableRecipe");
        rewriteRun(
                spec -> spec.recipe(recipe)
                        .expectedCyclesThatMakeChanges(1).cycles(1)
                        .parser(JavaParser.fromJavaVersion().classpath(JavaParser.runtimeClasspath())),
                java(
                        """
                                import com.google.common.collect.ImmutableList;
                                import com.google.common.collect.ImmutableSet;
                                import reactor.core.publisher.Flux;
                                
                                import java.util.function.Supplier;
                                import java.util.stream.Stream;
                                
                                class Test {
                                    ImmutableSet<Flux<String>> test() {
                                        return ImmutableSet.of(Flux.fromStream(ImmutableList.of("foo")::stream), Flux.fromStream(() -> ImmutableList.of("bar").stream()));
                                    }
                                }
                                """,
                        """
                                import com.google.common.collect.ImmutableList;
                                import com.google.common.collect.ImmutableSet;
                                import reactor.core.publisher.Flux;
                                
                                class Test {
                                    ImmutableSet<Flux<String>> test() {
                                        return ImmutableSet.of(Flux.fromIterable(ImmutableList.of("foo")), Flux.fromIterable(ImmutableList.of("bar")));
                                    }
                                }
                                """
                ));
    }

    @Test
    void testFluxFromStreamSupplierRecipe() {
        Recipe recipe = RewriteUtils.loadRecipe("ReactorRulesRecipes.FluxFromStreamSupplierRecipe");
        rewriteRun(
                spec -> spec.recipe(recipe)
                        .expectedCyclesThatMakeChanges(1).cycles(1)
                        .parser(JavaParser.fromJavaVersion().classpath(JavaParser.runtimeClasspath())),
                java(
                        """
                                import reactor.core.publisher.Flux;
                                
                                import java.util.stream.Stream;
                                
                                class Test {
                                    Flux<Integer> test() {
                                        return Flux.fromStream(Stream.of(1));
                                    }
                                }
                                """,
                        """
                                import reactor.core.publisher.Flux;
                                
                                import java.util.stream.Stream;
                                
                                class Test {
                                    Flux<Integer> test() {
                                        return Flux.fromStream(() -> Stream.of(1));
                                    }
                                }
                                """
                ));
    }

    @Test
    void testFluxJustRecipe() {
        Recipe recipe = RewriteUtils.loadRecipe("ReactorRulesRecipes.FluxJustRecipe");
        rewriteRun(
                spec -> spec.recipe(recipe)
                        .expectedCyclesThatMakeChanges(1).cycles(1)
                        .parser(JavaParser.fromJavaVersion().classpath(JavaParser.runtimeClasspath())),
                java(
                        """
                                import com.google.common.collect.ImmutableList;
                                import com.google.common.collect.ImmutableSet;
                                import reactor.core.publisher.Flux;
                                import reactor.core.publisher.Mono;
                                
                                class Test {
                                    ImmutableSet<Flux<Integer>> test() {
                                        return ImmutableSet.of(Flux.range(0, 1), Mono.just(2).flux(), Mono.just(3).repeat().take(1), Flux.fromIterable(ImmutableList.of(4)), Flux.fromIterable(ImmutableSet.of(5)));
                                    }
                                }
                                """,
                        """
                                import com.google.common.collect.ImmutableSet;
                                import reactor.core.publisher.Flux;
                                
                                class Test {
                                    ImmutableSet<Flux<Integer>> test() {
                                        return ImmutableSet.of(Flux.just(0), Flux.just(2), Flux.just(3), Flux.just(4), Flux.just(5));
                                    }
                                }
                                """
                ));
    }

    @Test
    void testFluxMapRecipe() {
        Recipe recipe = RewriteUtils.loadRecipe("ReactorRulesRecipes.FluxMapRecipe");
        rewriteRun(
                spec -> spec.recipe(recipe)
                        .expectedCyclesThatMakeChanges(1).cycles(1)
                        .parser(JavaParser.fromJavaVersion().classpath(JavaParser.runtimeClasspath())),
                java(
                        """
                                import com.google.common.collect.ImmutableSet;
                                import reactor.core.publisher.Flux;
                                import reactor.core.publisher.Mono;
                                
                                class Test {
                                    ImmutableSet<Flux<Integer>> test() {
                                        return ImmutableSet.of(Flux.just(1).concatMap(n -> Mono.just(n)), Flux.just(1).concatMap(n -> Flux.just(n * 2)), Flux.just(1).concatMap(n -> Mono.just(n), 3), Flux.just(1).concatMap(n -> Flux.just(n * 2), 3), Flux.just(1).concatMapDelayError(n -> Mono.just(n)), Flux.just(1).concatMapDelayError(n -> Flux.just(n * 2)), Flux.just(1).concatMapDelayError(n -> Mono.just(n), 3), Flux.just(1).concatMapDelayError(n -> Flux.just(n * 2), 3), Flux.just(1).flatMap(n -> Mono.just(n), 3), Flux.just(1).flatMap(n -> Flux.just(n * 2), 3), Flux.just(1).flatMap(n -> Mono.just(n), 3, 4), Flux.just(1).flatMap(n -> Flux.just(n * 2), 3, 4), Flux.just(1).flatMapDelayError(n -> Mono.just(n), 3, 4), Flux.just(1).flatMapDelayError(n -> Flux.just(n * 2), 3, 4), Flux.just(1).flatMapSequential(n -> Mono.just(n), 3), Flux.just(1).flatMapSequential(n -> Flux.just(n * 2), 3), Flux.just(1).flatMapSequential(n -> Mono.just(n), 3, 4), Flux.just(1).flatMapSequential(n -> Flux.just(n * 2), 3, 4), Flux.just(1).flatMapSequentialDelayError(n -> Mono.just(n), 3, 4), Flux.just(1).flatMapSequentialDelayError(n -> Flux.just(n * 2), 3, 4), Flux.just(1).switchMap(n -> Mono.just(n)), Flux.just(1).switchMap(n -> Flux.just(n * 2)));
                                    }
                                }
                                """,
                        """
                                import com.google.common.collect.ImmutableSet;
                                import reactor.core.publisher.Flux;
                                
                                class Test {
                                    ImmutableSet<Flux<Integer>> test() {
                                        return ImmutableSet.of(Flux.just(1).map(n -> n), Flux.just(1).map(n -> n * 2), Flux.just(1).map(n -> n), Flux.just(1).map(n -> n * 2), Flux.just(1).map(n -> n), Flux.just(1).map(n -> n * 2), Flux.just(1).map(n -> n), Flux.just(1).map(n -> n * 2), Flux.just(1).map(n -> n), Flux.just(1).map(n -> n * 2), Flux.just(1).map(n -> n), Flux.just(1).map(n -> n * 2), Flux.just(1).map(n -> n), Flux.just(1).map(n -> n * 2), Flux.just(1).map(n -> n), Flux.just(1).map(n -> n * 2), Flux.just(1).map(n -> n), Flux.just(1).map(n -> n * 2), Flux.just(1).map(n -> n), Flux.just(1).map(n -> n * 2), Flux.just(1).map(n -> n), Flux.just(1).map(n -> n * 2));
                                    }
                                }
                                """
                ));
    }

    @Test
    void testFluxMapNotNullRecipe() {
        Recipe recipe = RewriteUtils.loadRecipe("ReactorRulesRecipes.FluxMapNotNullRecipe");
        rewriteRun(
                spec -> spec.recipe(recipe)
                        .expectedCyclesThatMakeChanges(1).cycles(1)
                        .parser(JavaParser.fromJavaVersion().classpath(JavaParser.runtimeClasspath())),
                java(
                        """
                                import com.google.common.collect.ImmutableSet;
                                import reactor.core.publisher.Flux;
                                import reactor.core.publisher.Mono;
                                
                                class Test {
                                    ImmutableSet<Flux<Integer>> test() {
                                        return ImmutableSet.of(Flux.just(1).concatMap(n -> Mono.justOrEmpty(n)), Flux.just(1).concatMap(n -> Mono.fromSupplier(() -> n * 2)), Flux.just(1).concatMap(n -> Mono.justOrEmpty(n), 3), Flux.just(1).concatMap(n -> Mono.fromSupplier(() -> n * 2), 3), Flux.just(1).concatMapDelayError(n -> Mono.justOrEmpty(n)), Flux.just(1).concatMapDelayError(n -> Mono.fromSupplier(() -> n * 2)), Flux.just(1).concatMapDelayError(n -> Mono.justOrEmpty(n), 3), Flux.just(1).concatMapDelayError(n -> Mono.fromSupplier(() -> n * 2), 3), Flux.just(1).flatMap(n -> Mono.justOrEmpty(n), 3), Flux.just(1).flatMap(n -> Mono.fromSupplier(() -> n * 2), 3), Flux.just(1).flatMap(n -> Mono.justOrEmpty(n), 3, 4), Flux.just(1).flatMap(n -> Mono.fromSupplier(() -> n * 2), 3, 4), Flux.just(1).flatMapDelayError(n -> Mono.justOrEmpty(n), 3, 4), Flux.just(1).flatMapDelayError(n -> Mono.fromSupplier(() -> n * 2), 3, 4), Flux.just(1).flatMapSequential(n -> Mono.justOrEmpty(n), 3), Flux.just(1).flatMapSequential(n -> Mono.fromSupplier(() -> n * 2), 3), Flux.just(1).flatMapSequential(n -> Mono.justOrEmpty(n), 3, 4), Flux.just(1).flatMapSequential(n -> Mono.fromSupplier(() -> n * 2), 3, 4), Flux.just(1).flatMapSequentialDelayError(n -> Mono.justOrEmpty(n), 3, 4), Flux.just(1).flatMapSequentialDelayError(n -> Mono.fromSupplier(() -> n * 2), 3, 4), Flux.just(1).switchMap(n -> Mono.justOrEmpty(n)), Flux.just(1).switchMap(n -> Mono.fromSupplier(() -> n * 2)));
                                    }
                                }
                                """,
                        """
                                import com.google.common.collect.ImmutableSet;
                                import reactor.core.publisher.Flux;
                                
                                class Test {
                                    ImmutableSet<Flux<Integer>> test() {
                                        return ImmutableSet.of(Flux.just(1).mapNotNull(n -> n), Flux.just(1).mapNotNull(n -> n * 2), Flux.just(1).mapNotNull(n -> n), Flux.just(1).mapNotNull(n -> n * 2), Flux.just(1).mapNotNull(n -> n), Flux.just(1).mapNotNull(n -> n * 2), Flux.just(1).mapNotNull(n -> n), Flux.just(1).mapNotNull(n -> n * 2), Flux.just(1).mapNotNull(n -> n), Flux.just(1).mapNotNull(n -> n * 2), Flux.just(1).mapNotNull(n -> n), Flux.just(1).mapNotNull(n -> n * 2), Flux.just(1).mapNotNull(n -> n), Flux.just(1).mapNotNull(n -> n * 2), Flux.just(1).mapNotNull(n -> n), Flux.just(1).mapNotNull(n -> n * 2), Flux.just(1).mapNotNull(n -> n), Flux.just(1).mapNotNull(n -> n * 2), Flux.just(1).mapNotNull(n -> n), Flux.just(1).mapNotNull(n -> n * 2), Flux.just(1).mapNotNull(n -> n), Flux.just(1).mapNotNull(n -> n * 2));
                                    }
                                }
                                """
                ));
    }

    @Test
    void testFluxMapNotNullOrElseRecipe() {
        Recipe recipe = RewriteUtils.loadRecipe("ReactorRulesRecipes.FluxMapNotNullOrElseRecipe");
        rewriteRun(
                spec -> spec.recipe(recipe)
                        .expectedCyclesThatMakeChanges(1).cycles(1)
                        .parser(JavaParser.fromJavaVersion().classpath(JavaParser.runtimeClasspath())),
                java(
                        """
                                import reactor.core.publisher.Flux;
                                
                                import java.util.Optional;
                                import java.util.function.Function;
                                
                                class Test {
                                    Flux<Integer> test() {
                                        return Flux.just(Optional.of(1)).filter(Optional::isPresent).map(Optional::orElseThrow);
                                    }
                                }
                                """,
                        """
                                import reactor.core.publisher.Flux;
                                
                                import java.util.Optional;
                                
                                class Test {
                                    Flux<Integer> test() {
                                        return Flux.just(Optional.of(1)).mapNotNull(x -> x.orElse(null));
                                    }
                                }
                                """
                ));
    }

    @Test
    void testFluxMapNotNullTransformationOrElseRecipe() {
        Recipe recipe = RewriteUtils.loadRecipe("ReactorRulesRecipes.FluxMapNotNullTransformationOrElseRecipe");
        rewriteRun(
                spec -> spec.recipe(recipe)
                        .expectedCyclesThatMakeChanges(1).cycles(1)
                        .parser(JavaParser.fromJavaVersion().classpath(JavaParser.runtimeClasspath())),
                java(
                        """
                                import reactor.core.publisher.Flux;
                                
                                import java.util.Optional;
                                
                                class Test {
                                    Flux<String> test() {
                                        return Flux.just(1).map(x -> Optional.of(x.toString())).mapNotNull(x -> x.orElse(null));
                                    }
                                }
                                """,
                        """
                                import reactor.core.publisher.Flux;
                                
                                import java.util.Optional;
                                
                                class Test {
                                    Flux<String> test() {
                                        return Flux.just(1).mapNotNull(x -> Optional.of(x.toString()).orElse(null));
                                    }
                                }
                                """
                ));
    }

    @Test
    void testFluxOfTypeRecipe() {
        Recipe recipe = RewriteUtils.loadRecipe("ReactorRulesRecipes.FluxOfTypeRecipe");
        rewriteRun(
                spec -> spec.recipe(recipe)
                        .expectedCyclesThatMakeChanges(1).cycles(1)
                        .parser(JavaParser.fromJavaVersion().classpath(JavaParser.runtimeClasspath())),
                java(
                        """
                                import reactor.core.publisher.Flux;
                                
                                class Test {
                                    Flux<Number> test() {
                                        return Flux.just(1).filter(Number.class::isInstance).cast(Number.class);
                                    }
                                }
                                """,
                        """
                                import reactor.core.publisher.Flux;
                                
                                class Test {
                                    Flux<Number> test() {
                                        return Flux.just(1).ofType(Number.class);
                                    }
                                }
                                """
                ));
    }

    @Test
    void testFluxOnErrorCompleteRecipe() {
        Recipe recipe = RewriteUtils.loadRecipe("ReactorRulesRecipes.FluxOnErrorCompleteRecipe");
        rewriteRun(
                spec -> spec.recipe(recipe)
                        .expectedCyclesThatMakeChanges(1).cycles(1)
                        .parser(JavaParser.fromJavaVersion().classpath(JavaParser.runtimeClasspath())),
                java(
                        """
                                import com.google.common.collect.ImmutableSet;
                                import reactor.core.publisher.Flux;
                                import reactor.core.publisher.Mono;
                                
                                class Test {
                                    ImmutableSet<Flux<Integer>> test() {
                                        return ImmutableSet.of(Flux.just(1).onErrorResume(e -> Mono.empty()), Flux.just(2).onErrorResume(e -> Flux.empty()));
                                    }
                                }
                                """,
                        """
                                import com.google.common.collect.ImmutableSet;
                                import reactor.core.publisher.Flux;
                                
                                class Test {
                                    ImmutableSet<Flux<Integer>> test() {
                                        return ImmutableSet.of(Flux.just(1).onErrorComplete(), Flux.just(2).onErrorComplete());
                                    }
                                }
                                """
                ));
    }

    @Test
    void testFluxOnErrorCompleteClassRecipe() {
        Recipe recipe = RewriteUtils.loadRecipe("ReactorRulesRecipes.FluxOnErrorCompleteClassRecipe");
        rewriteRun(
                spec -> spec.recipe(recipe)
                        .expectedCyclesThatMakeChanges(1).cycles(1)
                        .parser(JavaParser.fromJavaVersion().classpath(JavaParser.runtimeClasspath())),
                java(
                        """
                                import com.google.common.collect.ImmutableSet;
                                import reactor.core.publisher.Flux;
                                import reactor.core.publisher.Mono;
                                
                                class Test {
                                    ImmutableSet<Flux<Integer>> test() {
                                        return ImmutableSet.of(Flux.just(1).onErrorComplete(IllegalArgumentException.class::isInstance), Flux.just(2).onErrorResume(IllegalStateException.class, e -> Mono.empty()), Flux.just(3).onErrorResume(AssertionError.class, e -> Flux.empty()));
                                    }
                                }
                                """,
                        """
                                import com.google.common.collect.ImmutableSet;
                                import reactor.core.publisher.Flux;
                                
                                class Test {
                                    ImmutableSet<Flux<Integer>> test() {
                                        return ImmutableSet.of(Flux.just(1).onErrorComplete(IllegalArgumentException.class), Flux.just(2).onErrorComplete(IllegalStateException.class), Flux.just(3).onErrorComplete(AssertionError.class));
                                    }
                                }
                                """
                ));
    }

    @Test
    void testFluxOnErrorCompletePredicateRecipe() {
        Recipe recipe = RewriteUtils.loadRecipe("ReactorRulesRecipes.FluxOnErrorCompletePredicateRecipe");
        rewriteRun(
                spec -> spec.recipe(recipe)
                        .expectedCyclesThatMakeChanges(1).cycles(1)
                        .parser(JavaParser.fromJavaVersion().classpath(JavaParser.runtimeClasspath())),
                java(
                        """
                                import com.google.common.collect.ImmutableSet;
                                import reactor.core.publisher.Flux;
                                import reactor.core.publisher.Mono;
                                
                                class Test {
                                    ImmutableSet<Flux<Integer>> test() {
                                        return ImmutableSet.of(Flux.just(1).onErrorResume(e -> e.getCause() == null, e -> Mono.empty()), Flux.just(2).onErrorResume(e -> e.getCause() != null, e -> Flux.empty()));
                                    }
                                }
                                """,
                        """
                                import com.google.common.collect.ImmutableSet;
                                import reactor.core.publisher.Flux;
                                
                                class Test {
                                    ImmutableSet<Flux<Integer>> test() {
                                        return ImmutableSet.of(Flux.just(1).onErrorComplete(e -> e.getCause() == null), Flux.just(2).onErrorComplete(e -> e.getCause() != null));
                                    }
                                }
                                """
                ));
    }

    @Test
    void testFluxOnErrorContinueRecipe() {
        Recipe recipe = RewriteUtils.loadRecipe("ReactorRulesRecipes.FluxOnErrorContinueRecipe");
        rewriteRun(
                spec -> spec.recipe(recipe)
                        .expectedCyclesThatMakeChanges(1).cycles(1)
                        .parser(JavaParser.fromJavaVersion().classpath(JavaParser.runtimeClasspath())),
                java(
                        """
                                import reactor.core.publisher.Flux;
                                
                                class Test {
                                    Flux<Integer> test() {
                                        return Flux.just(1).onErrorContinue(IllegalArgumentException.class::isInstance, (e, v) -> {
                                        });
                                    }
                                }
                                """,
                        """
                                import reactor.core.publisher.Flux;
                                
                                class Test {
                                    Flux<Integer> test() {
                                        return Flux.just(1).onErrorContinue(IllegalArgumentException.class, (e, v) -> {
                                        });
                                    }
                                }
                                """
                ));
    }

    @Test
    void testFluxOnErrorMapRecipe() {
        Recipe recipe = RewriteUtils.loadRecipe("ReactorRulesRecipes.FluxOnErrorMapRecipe");
        rewriteRun(
                spec -> spec.recipe(recipe)
                        .expectedCyclesThatMakeChanges(1).cycles(1)
                        .parser(JavaParser.fromJavaVersion().classpath(JavaParser.runtimeClasspath())),
                java(
                        """
                                import reactor.core.publisher.Flux;
                                
                                class Test {
                                    Flux<Integer> test() {
                                        return Flux.just(1).onErrorMap(IllegalArgumentException.class::isInstance, e -> e);
                                    }
                                }
                                """,
                        """
                                import reactor.core.publisher.Flux;
                                
                                class Test {
                                    Flux<Integer> test() {
                                        return Flux.just(1).onErrorMap(IllegalArgumentException.class, e -> e);
                                    }
                                }
                                """
                ));
    }

    @Test
    void testFluxOnErrorResumeRecipe() {
        Recipe recipe = RewriteUtils.loadRecipe("ReactorRulesRecipes.FluxOnErrorResumeRecipe");
        rewriteRun(
                spec -> spec.recipe(recipe)
                        .expectedCyclesThatMakeChanges(1).cycles(1)
                        .parser(JavaParser.fromJavaVersion().classpath(JavaParser.runtimeClasspath())),
                java(
                        """
                                import reactor.core.publisher.Flux;
                                
                                class Test {
                                    Flux<Integer> test() {
                                        return Flux.just(1).onErrorResume(IllegalArgumentException.class::isInstance, e -> Flux.just(2));
                                    }
                                }
                                """,
                        """
                                import reactor.core.publisher.Flux;
                                
                                class Test {
                                    Flux<Integer> test() {
                                        return Flux.just(1).onErrorResume(IllegalArgumentException.class, e -> Flux.just(2));
                                    }
                                }
                                """
                ));
    }

    @Test
    void testFluxOnErrorReturnRecipe() {
        Recipe recipe = RewriteUtils.loadRecipe("ReactorRulesRecipes.FluxOnErrorReturnRecipe");
        rewriteRun(
                spec -> spec.recipe(recipe)
                        .expectedCyclesThatMakeChanges(1).cycles(1)
                        .parser(JavaParser.fromJavaVersion().classpath(JavaParser.runtimeClasspath())),
                java(
                        """
                                import reactor.core.publisher.Flux;
                                
                                class Test {
                                    Flux<Integer> test() {
                                        return Flux.just(1).onErrorReturn(IllegalArgumentException.class::isInstance, 2);
                                    }
                                }
                                """,
                        """
                                import reactor.core.publisher.Flux;
                                
                                class Test {
                                    Flux<Integer> test() {
                                        return Flux.just(1).onErrorReturn(IllegalArgumentException.class, 2);
                                    }
                                }
                                """
                ));
    }

    @Test
    void testFluxSortRecipe() {
        Recipe recipe = RewriteUtils.loadRecipe("ReactorRulesRecipes.FluxSortRecipe");
        rewriteRun(
                spec -> spec.recipe(recipe)
                        .expectedCyclesThatMakeChanges(1).cycles(1)
                        .parser(JavaParser.fromJavaVersion().classpath(JavaParser.runtimeClasspath())),
                java(
                        """
                                import reactor.core.publisher.Flux;
                                
                                import java.util.Comparator;
                                
                                class Test {
                                    Flux<Integer> test() {
                                        return Flux.just(1).sort(Comparator.naturalOrder());
                                    }
                                }
                                """,
                        """
                                import reactor.core.publisher.Flux;
                                
                                class Test {
                                    Flux<Integer> test() {
                                        return Flux.just(1).sort();
                                    }
                                }
                                """
                ));
    }

    @Test
    void testFluxSwitchIfEmptyOfEmptyPublisherRecipe() {
        Recipe recipe = RewriteUtils.loadRecipe("ReactorRulesRecipes.FluxSwitchIfEmptyOfEmptyPublisherRecipe");
        rewriteRun(
                spec -> spec.recipe(recipe)
                        .expectedCyclesThatMakeChanges(1).cycles(1)
                        .parser(JavaParser.fromJavaVersion().classpath(JavaParser.runtimeClasspath())),
                java(
                        """
                                import com.google.common.collect.ImmutableSet;
                                import reactor.core.publisher.Flux;
                                import reactor.core.publisher.Mono;
                                
                                class Test {
                                    ImmutableSet<Flux<Integer>> test() {
                                        return ImmutableSet.of(Flux.just(1).switchIfEmpty(Mono.empty()), Flux.just(2).switchIfEmpty(Flux.empty()));
                                    }
                                }
                                """,
                        """
                                import com.google.common.collect.ImmutableSet;
                                import reactor.core.publisher.Flux;
                                
                                class Test {
                                    ImmutableSet<Flux<Integer>> test() {
                                        return ImmutableSet.of(Flux.just(1), Flux.just(2));
                                    }
                                }
                                """
                ));
    }

    @Test
    void testFluxTakeRecipe() {
        Recipe recipe = RewriteUtils.loadRecipe("ReactorRulesRecipes.FluxTakeRecipe");
        rewriteRun(
                spec -> spec.recipe(recipe)
                        .expectedCyclesThatMakeChanges(1).cycles(1)
                        .parser(JavaParser.fromJavaVersion().classpath(JavaParser.runtimeClasspath())),
                java(
                        """
                                import reactor.core.publisher.Flux;
                                
                                class Test {
                                    Flux<Integer> test() {
                                        return Flux.just(1, 2, 3).take(1, true);
                                    }
                                }
                                """,
                        """
                                import reactor.core.publisher.Flux;
                                
                                class Test {
                                    Flux<Integer> test() {
                                        return Flux.just(1, 2, 3).take(1);
                                    }
                                }
                                """
                ));
    }

    @Test
    void testFluxTakeWhileRecipe() {
        Recipe recipe = RewriteUtils.loadRecipe("ReactorRulesRecipes.FluxTakeWhileRecipe");
        rewriteRun(
                spec -> spec.recipe(recipe)
                        .expectedCyclesThatMakeChanges(1).cycles(1)
                        .parser(JavaParser.fromJavaVersion().classpath(JavaParser.runtimeClasspath())),
                java(
                        """
                                import reactor.core.publisher.Flux;
                                
                                class Test {
                                    Flux<Integer> test() {
                                        return Flux.just(1, 2, 3).takeWhile(i -> i % 2 == 0).filter(i -> i % 2 == 0);
                                    }
                                }
                                """,
                        """
                                import reactor.core.publisher.Flux;
                                
                                class Test {
                                    Flux<Integer> test() {
                                        return Flux.just(1, 2, 3).takeWhile(i -> i % 2 == 0);
                                    }
                                }
                                """
                ));
    }

    @Test
    void testFluxThenRecipe() {
        Recipe recipe = RewriteUtils.loadRecipe("ReactorRulesRecipes.FluxThenRecipe");
        rewriteRun(
                spec -> spec.recipe(recipe)
                        .expectedCyclesThatMakeChanges(1).cycles(1)
                        .parser(JavaParser.fromJavaVersion().classpath(JavaParser.runtimeClasspath())),
                java(
                        """
                                import com.google.common.collect.ImmutableSet;
                                import reactor.core.publisher.Flux;
                                import reactor.core.publisher.Mono;
                                
                                class Test {
                                    ImmutableSet<Mono<Void>> test() {
                                        return ImmutableSet.of(Flux.just("foo").ignoreElements().then(), Flux.<Void>empty().ignoreElements());
                                    }
                                }
                                """,
                        """
                                import com.google.common.collect.ImmutableSet;
                                import reactor.core.publisher.Flux;
                                import reactor.core.publisher.Mono;
                                
                                class Test {
                                    ImmutableSet<Mono<Void>> test() {
                                        return ImmutableSet.of(Flux.just("foo").then(), Flux.<Void>empty().then());
                                    }
                                }
                                """
                ));
    }

    @Test
    void testFluxThenEmptyRecipe() {
        Recipe recipe = RewriteUtils.loadRecipe("ReactorRulesRecipes.FluxThenEmptyRecipe");
        rewriteRun(
                spec -> spec.recipe(recipe)
                        .expectedCyclesThatMakeChanges(1).cycles(1)
                        .parser(JavaParser.fromJavaVersion().classpath(JavaParser.runtimeClasspath())),
                java(
                        """
                                import reactor.core.publisher.Flux;
                                import reactor.core.publisher.Mono;
                                
                                class Test {
                                    Mono<Void> test() {
                                        return Flux.just("foo").ignoreElements().thenEmpty(Mono.empty());
                                    }
                                }
                                """,
                        """
                                import reactor.core.publisher.Flux;
                                import reactor.core.publisher.Mono;
                                
                                class Test {
                                    Mono<Void> test() {
                                        return Flux.just("foo").thenEmpty(Mono.empty());
                                    }
                                }
                                """
                ));
    }

    @Test
    void testFluxThenManyRecipe() {
        Recipe recipe = RewriteUtils.loadRecipe("ReactorRulesRecipes.FluxThenManyRecipe");
        rewriteRun(
                spec -> spec.recipe(recipe)
                        .expectedCyclesThatMakeChanges(1).cycles(1)
                        .parser(JavaParser.fromJavaVersion().classpath(JavaParser.runtimeClasspath())),
                java(
                        """
                                import reactor.core.publisher.Flux;
                                
                                class Test {
                                    Flux<String> test() {
                                        return Flux.just("foo").ignoreElements().thenMany(Flux.just("bar"));
                                    }
                                }
                                """,
                        """
                                import reactor.core.publisher.Flux;
                                
                                class Test {
                                    Flux<String> test() {
                                        return Flux.just("foo").thenMany(Flux.just("bar"));
                                    }
                                }
                                """
                ));
    }

    @Test
    void testFluxThenMonoRecipe() {
        Recipe recipe = RewriteUtils.loadRecipe("ReactorRulesRecipes.FluxThenMonoRecipe");
        rewriteRun(
                spec -> spec.recipe(recipe)
                        .expectedCyclesThatMakeChanges(1).cycles(1)
                        .parser(JavaParser.fromJavaVersion().classpath(JavaParser.runtimeClasspath())),
                java(
                        """
                                import com.google.common.collect.ImmutableSet;
                                import reactor.core.publisher.Flux;
                                import reactor.core.publisher.Mono;
                                
                                class Test {
                                    ImmutableSet<Mono<?>> test() {
                                        return ImmutableSet.of(Flux.just("foo").ignoreElements().then(Mono.just("bar")), Flux.just("baz").thenEmpty(Mono.<Void>empty()));
                                    }
                                }
                                """,
                        """
                                import com.google.common.collect.ImmutableSet;
                                import reactor.core.publisher.Flux;
                                import reactor.core.publisher.Mono;
                                
                                class Test {
                                    ImmutableSet<Mono<?>> test() {
                                        return ImmutableSet.of(Flux.just("foo").then(Mono.just("bar")), Flux.just("baz").then(Mono.<Void>empty()));
                                    }
                                }
                                """
                ));
    }

    @Test
    void testFluxTransformMaxRecipe() {
        Recipe recipe = RewriteUtils.loadRecipe("ReactorRulesRecipes.FluxTransformMaxRecipe");
        rewriteRun(
                spec -> spec.recipe(recipe)
                        .expectedCyclesThatMakeChanges(1).cycles(1)
                        .parser(JavaParser.fromJavaVersion().classpath(JavaParser.runtimeClasspath())),
                java(
                        """
                                import reactor.core.publisher.Flux;
                                import reactor.core.publisher.Mono;
                                
                                class Test {
                                    Mono<Integer> test() {
                                        return Flux.just(1).sort().last();
                                    }
                                }
                                """,
                        """
                                import reactor.core.publisher.Flux;
                                import reactor.core.publisher.Mono;
                                import reactor.math.MathFlux;
                                
                                import java.util.function.Function;
                                
                                class Test {
                                    Mono<Integer> test() {
                                        return Flux.just(1).transform(MathFlux::max).singleOrEmpty();
                                    }
                                }
                                """
                ));
    }

    @Test
    void testFluxTransformMaxWithComparatorRecipe() {
        Recipe recipe = RewriteUtils.loadRecipe("ReactorRulesRecipes.FluxTransformMaxWithComparatorRecipe");
        rewriteRun(
                spec -> spec.recipe(recipe)
                        .expectedCyclesThatMakeChanges(1).cycles(1)
                        .parser(JavaParser.fromJavaVersion().classpath(JavaParser.runtimeClasspath())),
                java(
                        """
                                import com.google.common.collect.ImmutableSet;
                                import reactor.core.publisher.Flux;
                                import reactor.core.publisher.Mono;
                                
                                import java.util.Comparator;
                                import java.util.Optional;
                                import java.util.function.Function;
                                import java.util.stream.Collectors;
                                
                                class Test {
                                    ImmutableSet<Mono<Integer>> test() {
                                        return ImmutableSet.of(Flux.just(1).sort(Comparator.reverseOrder()).last(), Flux.just(2).collect(Collectors.maxBy(Comparator.reverseOrder())).flatMap(Mono::justOrEmpty));
                                    }
                                }
                                """,
                        """
                                import com.google.common.collect.ImmutableSet;
                                import reactor.core.publisher.Flux;
                                import reactor.core.publisher.Mono;
                                import reactor.math.MathFlux;
                                
                                import java.util.Comparator;
                                
                                class Test {
                                    ImmutableSet<Mono<Integer>> test() {
                                        return ImmutableSet.of(Flux.just(1).transform(f -> MathFlux.max(f, Comparator.reverseOrder())).singleOrEmpty(), Flux.just(2).transform(f -> MathFlux.max(f, Comparator.reverseOrder())).singleOrEmpty());
                                    }
                                }
                                """
                ));
    }

    @Test
    void testFluxTransformMinRecipe() {
        Recipe recipe = RewriteUtils.loadRecipe("ReactorRulesRecipes.FluxTransformMinRecipe");
        rewriteRun(
                spec -> spec.recipe(recipe)
                        .expectedCyclesThatMakeChanges(1).cycles(1)
                        .parser(JavaParser.fromJavaVersion().classpath(JavaParser.runtimeClasspath())),
                java(
                        """
                                import reactor.core.publisher.Flux;
                                import reactor.core.publisher.Mono;
                                
                                class Test {
                                    Mono<Integer> test() {
                                        return Flux.just(1).sort().next();
                                    }
                                }
                                """,
                        """
                                import reactor.core.publisher.Flux;
                                import reactor.core.publisher.Mono;
                                import reactor.math.MathFlux;
                                
                                import java.util.function.Function;
                                
                                class Test {
                                    Mono<Integer> test() {
                                        return Flux.just(1).transform(MathFlux::min).singleOrEmpty();
                                    }
                                }
                                """
                ));
    }

    @Test
    void testFluxTransformMinWithComparatorRecipe() {
        Recipe recipe = RewriteUtils.loadRecipe("ReactorRulesRecipes.FluxTransformMinWithComparatorRecipe");
        rewriteRun(
                spec -> spec.recipe(recipe)
                        .expectedCyclesThatMakeChanges(1).cycles(1)
                        .parser(JavaParser.fromJavaVersion().classpath(JavaParser.runtimeClasspath())),
                java(
                        """
                                import com.google.common.collect.ImmutableSet;
                                import reactor.core.publisher.Flux;
                                import reactor.core.publisher.Mono;
                                
                                import java.util.Comparator;
                                import java.util.Optional;
                                import java.util.function.Function;
                                import java.util.stream.Collectors;
                                
                                class Test {
                                    ImmutableSet<Mono<Integer>> test() {
                                        return ImmutableSet.of(Flux.just(1).sort(Comparator.reverseOrder()).next(), Flux.just(2).collect(Collectors.minBy(Comparator.reverseOrder())).flatMap(Mono::justOrEmpty));
                                    }
                                }
                                """,
                        """
                                import com.google.common.collect.ImmutableSet;
                                import reactor.core.publisher.Flux;
                                import reactor.core.publisher.Mono;
                                import reactor.math.MathFlux;
                                
                                import java.util.Comparator;
                                
                                class Test {
                                    ImmutableSet<Mono<Integer>> test() {
                                        return ImmutableSet.of(Flux.just(1).transform(f -> MathFlux.min(f, Comparator.reverseOrder())).singleOrEmpty(), Flux.just(2).transform(f -> MathFlux.min(f, Comparator.reverseOrder())).singleOrEmpty());
                                    }
                                }
                                """
                ));
    }

    @Test
    void testFluxZipRecipe() {
        Recipe recipe = RewriteUtils.loadRecipe("ReactorRulesRecipes.FluxZipRecipe");
        rewriteRun(
                spec -> spec.recipe(recipe)
                        .expectedCyclesThatMakeChanges(1).cycles(1)
                        .parser(JavaParser.fromJavaVersion().classpath(JavaParser.runtimeClasspath())),
                java(
                        """
                                import reactor.core.publisher.Flux;
                                import reactor.util.function.Tuple2;
                                
                                class Test {
                                    Flux<Tuple2<String, Integer>> test() {
                                        return Flux.just("foo", "bar").zipWith(Flux.just(1, 2));
                                    }
                                }
                                """,
                        """
                                import reactor.core.publisher.Flux;
                                import reactor.util.function.Tuple2;
                                
                                class Test {
                                    Flux<Tuple2<String, Integer>> test() {
                                        return Flux.zip(Flux.just("foo", "bar"), Flux.just(1, 2));
                                    }
                                }
                                """
                ));
    }

    @Test
    void testFluxZipWithCombinatorRecipe() {
        Recipe recipe = RewriteUtils.loadRecipe("ReactorRulesRecipes.FluxZipWithCombinatorRecipe");
        rewriteRun(
                spec -> spec.recipe(recipe)
                        .expectedCyclesThatMakeChanges(1).cycles(1)
                        .parser(JavaParser.fromJavaVersion().classpath(JavaParser.runtimeClasspath())),
                java(
                        """
                                import reactor.core.publisher.Flux;
                                
                                class Test {
                                    Flux<String> test() {
                                        return Flux.just("foo", "bar").zipWith(Flux.just(1, 2), String::repeat);
                                    }
                                }
                                """,
                        """
                                import reactor.core.publisher.Flux;
                                import reactor.function.TupleUtils;
                                
                                class Test {
                                    Flux<String> test() {
                                        return Flux.zip(Flux.just("foo", "bar"), Flux.just(1, 2)).map(TupleUtils.function(String::repeat));
                                    }
                                }
                                """
                ));
    }

    @Test
    void testFluxZipWithIterableRecipe() {
        Recipe recipe = RewriteUtils.loadRecipe("ReactorRulesRecipes.FluxZipWithIterableRecipe");
        rewriteRun(
                spec -> spec.recipe(recipe)
                        .expectedCyclesThatMakeChanges(1).cycles(1)
                        .parser(JavaParser.fromJavaVersion().classpath(JavaParser.runtimeClasspath())),
                java(
                        """
                                import com.google.common.collect.ImmutableSet;
                                import reactor.core.publisher.Flux;
                                import reactor.util.function.Tuple2;
                                
                                class Test {
                                    Flux<Tuple2<String, Integer>> test() {
                                        return Flux.zip(Flux.just("foo", "bar"), Flux.fromIterable(ImmutableSet.of(1, 2)));
                                    }
                                }
                                """,
                        """
                                import com.google.common.collect.ImmutableSet;
                                import reactor.core.publisher.Flux;
                                import reactor.util.function.Tuple2;
                                
                                class Test {
                                    Flux<Tuple2<String, Integer>> test() {
                                        return Flux.just("foo", "bar").zipWithIterable(ImmutableSet.of(1, 2));
                                    }
                                }
                                """
                ));
    }

    @Test
    void testFluxZipWithIterableBiFunctionRecipe() {
        Recipe recipe = RewriteUtils.loadRecipe("ReactorRulesRecipes.FluxZipWithIterableBiFunctionRecipe");
        rewriteRun(
                spec -> spec.recipe(recipe)
                        .expectedCyclesThatMakeChanges(1).cycles(1)
                        .parser(JavaParser.fromJavaVersion().classpath(JavaParser.runtimeClasspath())),
                java(
                        """
                                import com.google.common.collect.ImmutableSet;
                                import reactor.core.publisher.Flux;
                                
                                class Test {
                                    Flux<String> test() {
                                        return Flux.just("foo", "bar").zipWith(Flux.fromIterable(ImmutableSet.of(1, 2)), String::repeat);
                                    }
                                }
                                """,
                        """
                                import com.google.common.collect.ImmutableSet;
                                import reactor.core.publisher.Flux;
                                
                                class Test {
                                    Flux<String> test() {
                                        return Flux.just("foo", "bar").zipWithIterable(ImmutableSet.of(1, 2), String::repeat);
                                    }
                                }
                                """
                ));
    }

    @Test
    void testFluxZipWithIterableMapFunctionRecipe() {
        Recipe recipe = RewriteUtils.loadRecipe("ReactorRulesRecipes.FluxZipWithIterableMapFunctionRecipe");
        rewriteRun(
                spec -> spec.recipe(recipe)
                        .expectedCyclesThatMakeChanges(1).cycles(1)
                        .parser(JavaParser.fromJavaVersion().classpath(JavaParser.runtimeClasspath())),
                java(
                        """
                                import com.google.common.collect.ImmutableSet;
                                import reactor.core.publisher.Flux;
                                
                                class Test {
                                    Flux<String> test() {
                                        return Flux.just("foo", "bar").zipWithIterable(ImmutableSet.of(1, 2), String::repeat);
                                    }
                                }
                                """,
                        """
                                import com.google.common.collect.ImmutableSet;
                                import reactor.core.publisher.Flux;
                                import reactor.function.TupleUtils;
                                
                                class Test {
                                    Flux<String> test() {
                                        return Flux.just("foo", "bar").zipWithIterable(ImmutableSet.of(1, 2)).map(TupleUtils.function(String::repeat));
                                    }
                                }
                                """
                ));
    }

    @Test
    void testMathFluxMaxRecipe() {
        Recipe recipe = RewriteUtils.loadRecipe("ReactorRulesRecipes.MathFluxMaxRecipe");
        rewriteRun(
                spec -> spec.recipe(recipe)
                        .expectedCyclesThatMakeChanges(1).cycles(1)
                        .parser(JavaParser.fromJavaVersion().classpath(JavaParser.runtimeClasspath())),
                java(
                        """
                                import com.google.common.collect.ImmutableSet;
                                import reactor.core.publisher.Flux;
                                import reactor.core.publisher.Mono;
                                import reactor.math.MathFlux;
                                
                                import java.util.Comparator;
                                
                                class Test {
                                    ImmutableSet<Mono<Integer>> test() {
                                        return ImmutableSet.of(MathFlux.min(Flux.just(1), Comparator.reverseOrder()), MathFlux.max(Flux.just(2), Comparator.naturalOrder()));
                                    }
                                }
                                """,
                        """
                                import com.google.common.collect.ImmutableSet;
                                import reactor.core.publisher.Flux;
                                import reactor.core.publisher.Mono;
                                import reactor.math.MathFlux;
                                
                                class Test {
                                    ImmutableSet<Mono<Integer>> test() {
                                        return ImmutableSet.of(MathFlux.max(Flux.just(1)), MathFlux.max(Flux.just(2)));
                                    }
                                }
                                """
                ));
    }

    @Test
    void testMathFluxMinRecipe() {
        Recipe recipe = RewriteUtils.loadRecipe("ReactorRulesRecipes.MathFluxMinRecipe");
        rewriteRun(
                spec -> spec.recipe(recipe)
                        .expectedCyclesThatMakeChanges(1).cycles(1)
                        .parser(JavaParser.fromJavaVersion().classpath(JavaParser.runtimeClasspath())),
                java(
                        """
                                import com.google.common.collect.ImmutableSet;
                                import reactor.core.publisher.Flux;
                                import reactor.core.publisher.Mono;
                                import reactor.math.MathFlux;
                                
                                import java.util.Comparator;
                                
                                class Test {
                                    ImmutableSet<Mono<Integer>> test() {
                                        return ImmutableSet.of(MathFlux.min(Flux.just(1), Comparator.naturalOrder()), MathFlux.max(Flux.just(2), Comparator.reverseOrder()));
                                    }
                                }
                                """,
                        """
                                import com.google.common.collect.ImmutableSet;
                                import reactor.core.publisher.Flux;
                                import reactor.core.publisher.Mono;
                                import reactor.math.MathFlux;
                                
                                class Test {
                                    ImmutableSet<Mono<Integer>> test() {
                                        return ImmutableSet.of(MathFlux.min(Flux.just(1)), MathFlux.min(Flux.just(2)));
                                    }
                                }
                                """
                ));
    }

    @Test
    void testMonoCastRecipe() {
        Recipe recipe = RewriteUtils.loadRecipe("ReactorRulesRecipes.MonoCastRecipe");
        rewriteRun(
                spec -> spec.recipe(recipe)
                        .expectedCyclesThatMakeChanges(1).cycles(1)
                        .parser(JavaParser.fromJavaVersion().classpath(JavaParser.runtimeClasspath())),
                java(
                        """
                                import reactor.core.publisher.Mono;
                                
                                import java.util.function.Function;
                                
                                class Test {
                                    Mono<Number> test() {
                                        return Mono.just(1).map(Number.class::cast);
                                    }
                                }
                                """,
                        """
                                import reactor.core.publisher.Mono;
                                
                                class Test {
                                    Mono<Number> test() {
                                        return Mono.just(1).cast(Number.class);
                                    }
                                }
                                """
                ));
    }

    @Test
    void testMonoDefaultIfEmptyRecipe() {
        Recipe recipe = RewriteUtils.loadRecipe("ReactorRulesRecipes.MonoDefaultIfEmptyRecipe");
        rewriteRun(
                spec -> spec.recipe(recipe)
                        .expectedCyclesThatMakeChanges(1).cycles(1)
                        .parser(JavaParser.fromJavaVersion().classpath(JavaParser.runtimeClasspath())),
                java(
                        """
                                import reactor.core.publisher.Mono;
                                
                                class Test {
                                    Mono<String> test() {
                                        return Mono.just("foo").switchIfEmpty(Mono.just("bar"));
                                    }
                                }
                                """,
                        """
                                import reactor.core.publisher.Mono;
                                
                                class Test {
                                    Mono<String> test() {
                                        return Mono.just("foo").defaultIfEmpty("bar");
                                    }
                                }
                                """
                ));
    }

    @Test
    void testMonoDeferMonoJustOrEmptyRecipe() {
        Recipe recipe = RewriteUtils.loadRecipe("ReactorRulesRecipes.MonoDeferMonoJustOrEmptyRecipe");
        rewriteRun(
                spec -> spec.recipe(recipe)
                        .expectedCyclesThatMakeChanges(1).cycles(1)
                        .parser(JavaParser.fromJavaVersion().classpath(JavaParser.runtimeClasspath())),
                java(
                        """
                                import com.google.common.collect.ImmutableSet;
                                import reactor.core.publisher.Mono;
                                
                                import java.util.Optional;
                                
                                class Test {
                                    ImmutableSet<Mono<Integer>> test() {
                                        return ImmutableSet.of(Mono.fromCallable(() -> Optional.of(1).orElse(null)), Mono.fromSupplier(() -> Optional.of(2).orElse(null)));
                                    }
                                }
                                """,
                        """
                                import com.google.common.collect.ImmutableSet;
                                import reactor.core.publisher.Mono;
                                
                                import java.util.Optional;
                                
                                class Test {
                                    ImmutableSet<Mono<Integer>> test() {
                                        return ImmutableSet.of(Mono.defer(() -> Mono.justOrEmpty(Optional.of(1))), Mono.defer(() -> Mono.justOrEmpty(Optional.of(2))));
                                    }
                                }
                                """
                ));
    }

    @Test
    void testMonoDeferredErrorRecipe() {
        Recipe recipe = RewriteUtils.loadRecipe("ReactorRulesRecipes.MonoDeferredErrorRecipe");
        rewriteRun(
                spec -> spec.recipe(recipe)
                        .expectedCyclesThatMakeChanges(1).cycles(1)
                        .parser(JavaParser.fromJavaVersion().classpath(JavaParser.runtimeClasspath())),
                java(
                        """
                                import reactor.core.publisher.Mono;
                                
                                class Test {
                                    Mono<Void> test() {
                                        return Mono.defer(() -> Mono.error(new IllegalStateException()));
                                    }
                                }
                                """,
                        """
                                import reactor.core.publisher.Mono;
                                
                                class Test {
                                    Mono<Void> test() {
                                        return Mono.error(() -> new IllegalStateException());
                                    }
                                }
                                """
                ));
    }

    @Test
    void testMonoDoOnErrorRecipe() {
        Recipe recipe = RewriteUtils.loadRecipe("ReactorRulesRecipes.MonoDoOnErrorRecipe");
        rewriteRun(
                spec -> spec.recipe(recipe)
                        .expectedCyclesThatMakeChanges(1).cycles(1)
                        .parser(JavaParser.fromJavaVersion().classpath(JavaParser.runtimeClasspath())),
                java(
                        """
                                import reactor.core.publisher.Mono;
                                
                                class Test {
                                    Mono<Integer> test() {
                                        return Mono.just(1).doOnError(IllegalArgumentException.class::isInstance, e -> {
                                        });
                                    }
                                }
                                """,
                        """
                                import reactor.core.publisher.Mono;
                                
                                class Test {
                                    Mono<Integer> test() {
                                        return Mono.just(1).doOnError(IllegalArgumentException.class, e -> {
                                        });
                                    }
                                }
                                """
                ));
    }

    @Test
    void testMonoEmptyRecipe() {
        Recipe recipe = RewriteUtils.loadRecipe("ReactorRulesRecipes.MonoEmptyRecipe");
        rewriteRun(
                spec -> spec.recipe(recipe)
                        .expectedCyclesThatMakeChanges(1).cycles(1)
                        .parser(JavaParser.fromJavaVersion().classpath(JavaParser.runtimeClasspath())),
                java(
                        """
                                import com.google.common.collect.ImmutableSet;
                                import reactor.core.publisher.Mono;
                                
                                import java.util.Optional;
                                
                                class Test {
                                    ImmutableSet<Mono<String>> test() {
                                        return ImmutableSet.of(Mono.justOrEmpty(null), Mono.justOrEmpty(Optional.empty()));
                                    }
                                }
                                """,
                        """
                                import com.google.common.collect.ImmutableSet;
                                import reactor.core.publisher.Mono;
                                
                                class Test {
                                    ImmutableSet<Mono<String>> test() {
                                        return ImmutableSet.of(Mono.empty(), Mono.empty());
                                    }
                                }
                                """
                ));
    }

    @Test
    void testMonoErrorSupplierRecipe() {
        Recipe recipe = RewriteUtils.loadRecipe("ReactorRulesRecipes.MonoErrorSupplierRecipe");
        rewriteRun(
                spec -> spec.recipe(recipe)
                        .expectedCyclesThatMakeChanges(1).cycles(1)
                        .parser(JavaParser.fromJavaVersion().classpath(JavaParser.runtimeClasspath())),
                java(
                        """
                                import reactor.core.publisher.Mono;
                                
                                import java.util.function.Supplier;
                                
                                class Test {
                                    Mono<Void> test() {
                                        return Mono.error(() -> ((Supplier<RuntimeException>) null).get());
                                    }
                                }
                                """,
                        """
                                import reactor.core.publisher.Mono;
                                
                                import java.util.function.Supplier;
                                
                                class Test {
                                    Mono<Void> test() {
                                        return Mono.error(((Supplier<RuntimeException>) null));
                                    }
                                }
                                """
                ));
    }

    @Test
    void testMonoFlatMapRecipe() {
        Recipe recipe = RewriteUtils.loadRecipe("ReactorRulesRecipes.MonoFlatMapRecipe");
        rewriteRun(
                spec -> spec.recipe(recipe)
                        .expectedCyclesThatMakeChanges(1).cycles(1)
                        .parser(JavaParser.fromJavaVersion().classpath(JavaParser.runtimeClasspath())),
                java(
                        """
                                import com.google.common.collect.ImmutableSet;
                                import reactor.core.publisher.Mono;
                                
                                import java.util.function.Function;
                                
                                class Test {
                                    ImmutableSet<Mono<String>> test() {
                                        return ImmutableSet.of(Mono.just("foo").map(Mono::just).flatMap(Function.identity()), Mono.just("bar").map(Mono::just).flatMap(v -> v), Mono.just("baz").map(Mono::just).flatMap(v -> Mono.empty()));
                                    }
                                }
                                """,
                        """
                                import com.google.common.collect.ImmutableSet;
                                import reactor.core.publisher.Mono;
                                
                                import java.util.function.Function;
                                
                                class Test {
                                    ImmutableSet<Mono<String>> test() {
                                        return ImmutableSet.of(Mono.just("foo").flatMap(Mono::just), Mono.just("bar").flatMap(Mono::just), Mono.just("baz").map(Mono::just).flatMap(v -> Mono.empty()));
                                    }
                                }
                                """
                ));
    }

    @Test
    void testMonoFlatMapIterableRecipe() {
        Recipe recipe = RewriteUtils.loadRecipe("ReactorRulesRecipes.MonoFlatMapIterableRecipe");
        rewriteRun(
                spec -> spec.recipe(recipe)
                        .expectedCyclesThatMakeChanges(1).cycles(1)
                        .parser(JavaParser.fromJavaVersion().classpath(JavaParser.runtimeClasspath())),
                java(
                        """
                                import com.google.common.collect.ImmutableSet;
                                import reactor.core.publisher.Flux;
                                import reactor.core.publisher.Mono;
                                
                                import java.util.function.Function;
                                
                                class Test {
                                    ImmutableSet<Flux<Integer>> test() {
                                        return ImmutableSet.of(Mono.just(1).map(ImmutableSet::of).flatMapMany(Flux::fromIterable), Mono.just(2).map(ImmutableSet::of).flatMapIterable(Function.identity()), Mono.just(3).map(ImmutableSet::of).flatMapIterable(v -> v), Mono.just(4).map(ImmutableSet::of).flatMapIterable(v -> ImmutableSet.of()), Mono.just(5).flux().concatMapIterable(ImmutableSet::of));
                                    }
                                }
                                """,
                        """
                                import com.google.common.collect.ImmutableSet;
                                import reactor.core.publisher.Flux;
                                import reactor.core.publisher.Mono;
                                
                                import java.util.function.Function;
                                
                                class Test {
                                    ImmutableSet<Flux<Integer>> test() {
                                        return ImmutableSet.of(Mono.just(1).flatMapIterable(ImmutableSet::of), Mono.just(2).flatMapIterable(ImmutableSet::of), Mono.just(3).flatMapIterable(ImmutableSet::of), Mono.just(4).map(ImmutableSet::of).flatMapIterable(v -> ImmutableSet.of()), Mono.just(5).flatMapIterable(ImmutableSet::of));
                                    }
                                }
                                """
                ));
    }

    @Test
    void testMonoFlatMapIterableIdentityRecipe() {
        Recipe recipe = RewriteUtils.loadRecipe("ReactorRulesRecipes.MonoFlatMapIterableIdentityRecipe");
        rewriteRun(
                spec -> spec.recipe(recipe)
                        .expectedCyclesThatMakeChanges(1).cycles(1)
                        .parser(JavaParser.fromJavaVersion().classpath(JavaParser.runtimeClasspath())),
                java(
                        """
                                import com.google.common.collect.ImmutableSet;
                                import reactor.core.publisher.Flux;
                                import reactor.core.publisher.Mono;
                                
                                import java.util.function.Function;
                                
                                class Test {
                                    Flux<Integer> test() {
                                        return Mono.just(ImmutableSet.of(1)).flatMapMany(Flux::fromIterable);
                                    }
                                }
                                """,
                        """
                                import com.google.common.collect.ImmutableSet;
                                import reactor.core.publisher.Flux;
                                import reactor.core.publisher.Mono;
                                
                                import java.util.function.Function;
                                
                                class Test {
                                    Flux<Integer> test() {
                                        return Mono.just(ImmutableSet.of(1)).flatMapIterable(Function.identity());
                                    }
                                }
                                """
                ));
    }

    @Test
    void testMonoFlatMapManyRecipe() {
        Recipe recipe = RewriteUtils.loadRecipe("ReactorRulesRecipes.MonoFlatMapManyRecipe");
        rewriteRun(
                spec -> spec.recipe(recipe)
                        .expectedCyclesThatMakeChanges(1).cycles(1)
                        .parser(JavaParser.fromJavaVersion().classpath(JavaParser.runtimeClasspath())),
                java(
                        """
                                import com.google.common.collect.ImmutableSet;
                                import reactor.core.publisher.Flux;
                                import reactor.core.publisher.Mono;
                                
                                import java.util.function.Function;
                                
                                class Test {
                                    ImmutableSet<Flux<Integer>> test() {
                                        return ImmutableSet.of(Mono.just(1).map(Mono::just).flatMapMany(Function.identity()), Mono.just(2).map(Mono::just).flatMapMany(v -> v), Mono.just(3).map(Mono::just).flatMapMany(v -> Flux.empty()), Mono.just(4).flux().concatMap(Mono::just), Mono.just(5).flux().concatMap(Mono::just, 2), Mono.just(6).flux().concatMapDelayError(Mono::just), Mono.just(7).flux().concatMapDelayError(Mono::just, 2), Mono.just(8).flux().concatMapDelayError(Mono::just, false, 2), Mono.just(9).flux().flatMap(Mono::just, 2), Mono.just(10).flux().flatMap(Mono::just, 2, 3), Mono.just(11).flux().flatMapDelayError(Mono::just, 2, 3), Mono.just(12).flux().flatMapSequential(Mono::just, 2), Mono.just(13).flux().flatMapSequential(Mono::just, 2, 3), Mono.just(14).flux().flatMapSequentialDelayError(Mono::just, 2, 3), Mono.just(15).flux().switchMap(Mono::just));
                                    }
                                }
                                """,
                        """
                                import com.google.common.collect.ImmutableSet;
                                import reactor.core.publisher.Flux;
                                import reactor.core.publisher.Mono;
                                
                                import java.util.function.Function;
                                
                                class Test {
                                    ImmutableSet<Flux<Integer>> test() {
                                        return ImmutableSet.of(Mono.just(1).flatMapMany(Mono::just), Mono.just(2).flatMapMany(Mono::just), Mono.just(3).map(Mono::just).flatMapMany(v -> Flux.empty()), Mono.just(4).flatMapMany(Mono::just), Mono.just(5).flatMapMany(Mono::just), Mono.just(6).flatMapMany(Mono::just), Mono.just(7).flatMapMany(Mono::just), Mono.just(8).flatMapMany(Mono::just), Mono.just(9).flatMapMany(Mono::just), Mono.just(10).flatMapMany(Mono::just), Mono.just(11).flatMapMany(Mono::just), Mono.just(12).flatMapMany(Mono::just), Mono.just(13).flatMapMany(Mono::just), Mono.just(14).flatMapMany(Mono::just), Mono.just(15).flatMapMany(Mono::just));
                                    }
                                }
                                """
                ));
    }

    @Test
    void testMonoFlatMapToFluxRecipe() {
        Recipe recipe = RewriteUtils.loadRecipe("ReactorRulesRecipes.MonoFlatMapToFluxRecipe");
        rewriteRun(
                spec -> spec.recipe(recipe)
                        .expectedCyclesThatMakeChanges(1).cycles(1)
                        .parser(JavaParser.fromJavaVersion().classpath(JavaParser.runtimeClasspath())),
                java(
                        """
                                import reactor.core.publisher.Flux;
                                import reactor.core.publisher.Mono;
                                
                                class Test {
                                    Flux<String> test() {
                                        return Mono.just("foo").flatMapMany(s -> Mono.fromSupplier(() -> s + s));
                                    }
                                }
                                """,
                        """
                                import reactor.core.publisher.Flux;
                                import reactor.core.publisher.Mono;
                                
                                class Test {
                                    Flux<String> test() {
                                        return Mono.just("foo").flatMap(s -> Mono.fromSupplier(() -> s + s)).flux();
                                    }
                                }
                                """
                ));
    }

    @Test
    void testMonoFluxRecipe() {
        Recipe recipe = RewriteUtils.loadRecipe("ReactorRulesRecipes.MonoFluxRecipe");
        rewriteRun(
                spec -> spec.recipe(recipe)
                        .expectedCyclesThatMakeChanges(1).cycles(1)
                        .parser(JavaParser.fromJavaVersion().classpath(JavaParser.runtimeClasspath())),
                java(
                        """
                                import com.google.common.collect.ImmutableSet;
                                import reactor.core.publisher.Flux;
                                import reactor.core.publisher.Mono;
                                
                                import java.util.function.Function;
                                
                                class Test {
                                    ImmutableSet<Flux<String>> test() {
                                        return ImmutableSet.of(Mono.just("foo").flatMapMany(Mono::just), Mono.just("bar").flatMapMany(Flux::just), Flux.concat(Mono.just("baz")));
                                    }
                                }
                                """,
                        """
                                import com.google.common.collect.ImmutableSet;
                                import reactor.core.publisher.Flux;
                                import reactor.core.publisher.Mono;
                                
                                class Test {
                                    ImmutableSet<Flux<String>> test() {
                                        return ImmutableSet.of(Mono.just("foo").flux(), Mono.just("bar").flux(), Mono.just("baz").flux());
                                    }
                                }
                                """
                ));
    }

    @Test
    void testMonoFromFutureAsyncLoadingCacheGetRecipe() {
        Recipe recipe = RewriteUtils.loadRecipe("ReactorRulesRecipes.MonoFromFutureAsyncLoadingCacheGetRecipe");
        rewriteRun(
                spec -> spec.recipe(recipe)
                        .expectedCyclesThatMakeChanges(1).cycles(1)
                        .parser(JavaParser.fromJavaVersion().classpath(JavaParser.runtimeClasspath())),
                java(
                        """
                                import com.github.benmanes.caffeine.cache.AsyncLoadingCache;
                                import reactor.core.publisher.Mono;
                                
                                class Test {
                                    Mono<String> test() {
                                        return Mono.fromFuture(() -> ((AsyncLoadingCache<Integer, String>) null).get(0));
                                    }
                                }
                                """,
                        """
                                import com.github.benmanes.caffeine.cache.AsyncLoadingCache;
                                import reactor.core.publisher.Mono;
                                
                                class Test {
                                    Mono<String> test() {
                                        return Mono.fromFuture(() -> ((AsyncLoadingCache<Integer, String>) null).get(0), true);
                                    }
                                }
                                """
                ));
    }

    @Test
    void testMonoFromFutureAsyncLoadingCacheGetAllRecipe() {
        Recipe recipe = RewriteUtils.loadRecipe("ReactorRulesRecipes.MonoFromFutureAsyncLoadingCacheGetAllRecipe");
        rewriteRun(
                spec -> spec.recipe(recipe)
                        .expectedCyclesThatMakeChanges(1).cycles(1)
                        .parser(JavaParser.fromJavaVersion().classpath(JavaParser.runtimeClasspath())),
                java(
                        """
                                import com.github.benmanes.caffeine.cache.AsyncLoadingCache;
                                import com.google.common.collect.ImmutableSet;
                                import reactor.core.publisher.Mono;
                                
                                import java.util.Map;
                                
                                class Test {
                                    Mono<Map<Integer, String>> test() {
                                        return Mono.fromFuture(() -> ((AsyncLoadingCache<Integer, String>) null).getAll(ImmutableSet.of()));
                                    }
                                }
                                """,
                        """
                                import com.github.benmanes.caffeine.cache.AsyncLoadingCache;
                                import com.google.common.collect.ImmutableSet;
                                import reactor.core.publisher.Mono;
                                
                                import java.util.Map;
                                
                                class Test {
                                    Mono<Map<Integer, String>> test() {
                                        return Mono.fromFuture(() -> ((AsyncLoadingCache<Integer, String>) null).getAll(ImmutableSet.of()), true);
                                    }
                                }
                                """
                ));
    }

    @Test
    void testMonoFromFutureSupplierRecipe() {
        Recipe recipe = RewriteUtils.loadRecipe("ReactorRulesRecipes.MonoFromFutureSupplierRecipe");
        rewriteRun(
                spec -> spec.recipe(recipe)
                        .expectedCyclesThatMakeChanges(1).cycles(1)
                        .parser(JavaParser.fromJavaVersion().classpath(JavaParser.runtimeClasspath())),
                java(
                        """
                                import reactor.core.publisher.Mono;
                                
                                import java.util.concurrent.CompletableFuture;
                                
                                class Test {
                                    Mono<Void> test() {
                                        return Mono.fromFuture(CompletableFuture.completedFuture(null));
                                    }
                                }
                                """,
                        """
                                import reactor.core.publisher.Mono;
                                
                                import java.util.concurrent.CompletableFuture;
                                
                                class Test {
                                    Mono<Void> test() {
                                        return Mono.fromFuture(() -> CompletableFuture.completedFuture(null));
                                    }
                                }
                                """
                ));
    }

    @Test
    void testMonoFromFutureSupplierBooleanRecipe() {
        Recipe recipe = RewriteUtils.loadRecipe("ReactorRulesRecipes.MonoFromFutureSupplierBooleanRecipe");
        rewriteRun(
                spec -> spec.recipe(recipe)
                        .expectedCyclesThatMakeChanges(1).cycles(1)
                        .parser(JavaParser.fromJavaVersion().classpath(JavaParser.runtimeClasspath())),
                java(
                        """
                                import reactor.core.publisher.Mono;
                                
                                import java.util.concurrent.CompletableFuture;
                                
                                class Test {
                                    Mono<Void> test() {
                                        return Mono.fromFuture(CompletableFuture.completedFuture(null), true);
                                    }
                                }
                                """,
                        """
                                import reactor.core.publisher.Mono;
                                
                                import java.util.concurrent.CompletableFuture;
                                
                                class Test {
                                    Mono<Void> test() {
                                        return Mono.fromFuture(() -> CompletableFuture.completedFuture(null), true);
                                    }
                                }
                                """
                ));
    }

    @Test
    void testMonoFromOptionalSwitchIfEmptyRecipe() {
        Recipe recipe = RewriteUtils.loadRecipe("ReactorRulesRecipes.MonoFromOptionalSwitchIfEmptyRecipe");
        rewriteRun(
                spec -> spec.recipe(recipe)
                        .expectedCyclesThatMakeChanges(1).cycles(1)
                        .parser(JavaParser.fromJavaVersion().classpath(JavaParser.runtimeClasspath())),
                java(
                        """
                                import reactor.core.publisher.Mono;
                                
                                import java.util.Optional;
                                import java.util.function.Function;
                                
                                class Test {
                                    Mono<Integer> test() {
                                        return Optional.of(1).map(Mono::just).orElse(Mono.just(2));
                                    }
                                }
                                """,
                        """
                                import reactor.core.publisher.Mono;
                                
                                import java.util.Optional;
                                
                                class Test {
                                    Mono<Integer> test() {
                                        return Mono.justOrEmpty(Optional.of(1)).switchIfEmpty(Mono.just(2));
                                    }
                                }
                                """
                ));
    }

    @Test
    void testMonoFromSupplierRecipe() {
        Recipe recipe = RewriteUtils.loadRecipe("ReactorRulesRecipes.MonoFromSupplierRecipe");
        rewriteRun(
                spec -> spec.recipe(recipe)
                        .expectedCyclesThatMakeChanges(1).cycles(1)
                        .parser(JavaParser.fromJavaVersion().classpath(JavaParser.runtimeClasspath())),
                java(
                        """
                                import com.google.common.collect.ImmutableSet;
                                import reactor.core.publisher.Mono;
                                
                                import java.util.concurrent.Callable;
                                
                                class Test {
                                    ImmutableSet<Mono<?>> test() {
                                        return ImmutableSet.of(Mono.fromCallable((Callable<?>) null), Mono.fromCallable(() -> getClass().getDeclaredConstructor()), Mono.fromCallable(() -> toString()), Mono.fromCallable(getClass()::getDeclaredConstructor), Mono.fromCallable(this::toString));
                                    }
                                }
                                """,
                        """
                                import com.google.common.collect.ImmutableSet;
                                import reactor.core.publisher.Mono;
                                
                                import java.util.concurrent.Callable;
                                import java.util.function.Supplier;
                                
                                class Test {
                                    ImmutableSet<Mono<?>> test() {
                                        return ImmutableSet.of(Mono.fromCallable((Callable<?>) null), Mono.fromCallable(() -> getClass().getDeclaredConstructor()), Mono.fromSupplier(() -> toString()), Mono.fromCallable(getClass()::getDeclaredConstructor), Mono.fromSupplier(this::toString));
                                    }
                                }
                                """
                ));
    }

    @Test
    void testMonoIdentityRecipe() {
        Recipe recipe = RewriteUtils.loadRecipe("ReactorRulesRecipes.MonoIdentityRecipe");
        rewriteRun(
                spec -> spec.recipe(recipe)
                        .expectedCyclesThatMakeChanges(1).cycles(1)
                        .parser(JavaParser.fromJavaVersion().classpath(JavaParser.runtimeClasspath())),
                java(
                        """
                                import com.google.common.collect.ImmutableList;
                                import com.google.common.collect.ImmutableSet;
                                import reactor.core.publisher.Mono;
                                
                                import java.util.function.Function;
                                
                                class Test {
                                    ImmutableSet<Mono<?>> test() {
                                        return ImmutableSet.of(Mono.just(1).switchIfEmpty(Mono.empty()), Mono.just(2).flux().next(), Mono.just(3).flux().singleOrEmpty(), Mono.<Void>empty().ignoreElement(), Mono.<Void>empty().then(), Mono.<ImmutableList<String>>empty().map(ImmutableList::copyOf));
                                    }
                                }
                                """,
                        """
                                import com.google.common.collect.ImmutableList;
                                import com.google.common.collect.ImmutableSet;
                                import reactor.core.publisher.Mono;
                                
                                class Test {
                                    ImmutableSet<Mono<?>> test() {
                                        return ImmutableSet.of(Mono.just(1), Mono.just(2), Mono.just(3), Mono.<Void>empty(), Mono.<Void>empty(), Mono.<ImmutableList<String>>empty());
                                    }
                                }
                                """
                ));
    }

    @Test
    void testMonoJustRecipe() {
        Recipe recipe = RewriteUtils.loadRecipe("ReactorRulesRecipes.MonoJustRecipe");
        rewriteRun(
                spec -> spec.recipe(recipe)
                        .expectedCyclesThatMakeChanges(1).cycles(1)
                        .parser(JavaParser.fromJavaVersion().classpath(JavaParser.runtimeClasspath())),
                java(
                        """
                                import reactor.core.publisher.Mono;
                                
                                import java.util.Optional;
                                
                                class Test {
                                    Mono<Integer> test() {
                                        return Mono.justOrEmpty(Optional.of(1));
                                    }
                                }
                                """,
                        """
                                import reactor.core.publisher.Mono;
                                
                                class Test {
                                    Mono<Integer> test() {
                                        return Mono.just(1);
                                    }
                                }
                                """
                ));
    }

    @Test
    void testMonoJustOrEmptyObjectRecipe() {
        Recipe recipe = RewriteUtils.loadRecipe("ReactorRulesRecipes.MonoJustOrEmptyObjectRecipe");
        rewriteRun(
                spec -> spec.recipe(recipe)
                        .expectedCyclesThatMakeChanges(1).cycles(1)
                        .parser(JavaParser.fromJavaVersion().classpath(JavaParser.runtimeClasspath())),
                java(
                        """
                                import reactor.core.publisher.Mono;
                                
                                import java.util.Optional;
                                
                                class Test {
                                    Mono<Integer> test() {
                                        return Mono.justOrEmpty(Optional.ofNullable(1));
                                    }
                                }
                                """,
                        """
                                import reactor.core.publisher.Mono;
                                
                                class Test {
                                    Mono<Integer> test() {
                                        return Mono.justOrEmpty(1);
                                    }
                                }
                                """
                ));
    }

    @Test
    void testMonoJustOrEmptyOptionalRecipe() {
        Recipe recipe = RewriteUtils.loadRecipe("ReactorRulesRecipes.MonoJustOrEmptyOptionalRecipe");
        rewriteRun(
                spec -> spec.recipe(recipe)
                        .expectedCyclesThatMakeChanges(1).cycles(1)
                        .parser(JavaParser.fromJavaVersion().classpath(JavaParser.runtimeClasspath())),
                java(
                        """
                                import reactor.core.publisher.Mono;
                                
                                import java.util.Optional;
                                import java.util.function.Function;
                                
                                class Test {
                                    Mono<Integer> test() {
                                        return Mono.just(Optional.of(1)).filter(Optional::isPresent).map(Optional::orElseThrow);
                                    }
                                }
                                """,
                        """
                                import reactor.core.publisher.Mono;
                                
                                import java.util.Optional;
                                
                                class Test {
                                    Mono<Integer> test() {
                                        return Mono.justOrEmpty(Optional.of(1));
                                    }
                                }
                                """
                ));
    }

    @Test
    void testMonoMapRecipe() {
        Recipe recipe = RewriteUtils.loadRecipe("ReactorRulesRecipes.MonoMapRecipe");
        rewriteRun(
                spec -> spec.recipe(recipe)
                        .expectedCyclesThatMakeChanges(1).cycles(1)
                        .parser(JavaParser.fromJavaVersion().classpath(JavaParser.runtimeClasspath())),
                java(
                        """
                                import com.google.common.collect.ImmutableSet;
                                import reactor.core.publisher.Mono;
                                
                                class Test {
                                    ImmutableSet<Mono<String>> test() {
                                        return ImmutableSet.of(Mono.just("foo").flatMap(s -> Mono.just(s)), Mono.just("bar").flatMap(s -> Mono.just(s.substring(1))));
                                    }
                                }
                                """,
                        """
                                import com.google.common.collect.ImmutableSet;
                                import reactor.core.publisher.Mono;
                                
                                class Test {
                                    ImmutableSet<Mono<String>> test() {
                                        return ImmutableSet.of(Mono.just("foo").map(s -> s), Mono.just("bar").map(s -> s.substring(1)));
                                    }
                                }
                                """
                ));
    }

    @Test
    void testMonoMapNotNullRecipe() {
        Recipe recipe = RewriteUtils.loadRecipe("ReactorRulesRecipes.MonoMapNotNullRecipe");
        rewriteRun(
                spec -> spec.recipe(recipe)
                        .expectedCyclesThatMakeChanges(1).cycles(1)
                        .parser(JavaParser.fromJavaVersion().classpath(JavaParser.runtimeClasspath())),
                java(
                        """
                                import com.google.common.collect.ImmutableSet;
                                import reactor.core.publisher.Mono;
                                
                                class Test {
                                    ImmutableSet<Mono<String>> test() {
                                        return ImmutableSet.of(Mono.just("foo").flatMap(s -> Mono.justOrEmpty(s)), Mono.just("bar").flatMap(s -> Mono.fromSupplier(() -> s.substring(1))));
                                    }
                                }
                                """,
                        """
                                import com.google.common.collect.ImmutableSet;
                                import reactor.core.publisher.Mono;
                                
                                class Test {
                                    ImmutableSet<Mono<String>> test() {
                                        return ImmutableSet.of(Mono.just("foo").mapNotNull(s -> s), Mono.just("bar").mapNotNull(s -> s.substring(1)));
                                    }
                                }
                                """
                ));
    }

    @Test
    void testMonoOfTypeRecipe() {
        Recipe recipe = RewriteUtils.loadRecipe("ReactorRulesRecipes.MonoOfTypeRecipe");
        rewriteRun(
                spec -> spec.recipe(recipe)
                        .expectedCyclesThatMakeChanges(1).cycles(1)
                        .parser(JavaParser.fromJavaVersion().classpath(JavaParser.runtimeClasspath())),
                java(
                        """
                                import reactor.core.publisher.Mono;
                                
                                class Test {
                                    Mono<Number> test() {
                                        return Mono.just(1).filter(Number.class::isInstance).cast(Number.class);
                                    }
                                }
                                """,
                        """
                                import reactor.core.publisher.Mono;
                                
                                class Test {
                                    Mono<Number> test() {
                                        return Mono.just(1).ofType(Number.class);
                                    }
                                }
                                """
                ));
    }

    @Test
    void testMonoOnErrorCompleteRecipe() {
        Recipe recipe = RewriteUtils.loadRecipe("ReactorRulesRecipes.MonoOnErrorCompleteRecipe");
        rewriteRun(
                spec -> spec.recipe(recipe)
                        .expectedCyclesThatMakeChanges(1).cycles(1)
                        .parser(JavaParser.fromJavaVersion().classpath(JavaParser.runtimeClasspath())),
                java(
                        """
                                import reactor.core.publisher.Mono;
                                
                                class Test {
                                    Mono<Integer> test() {
                                        return Mono.just(1).onErrorResume(e -> Mono.empty());
                                    }
                                }
                                """,
                        """
                                import reactor.core.publisher.Mono;
                                
                                class Test {
                                    Mono<Integer> test() {
                                        return Mono.just(1).onErrorComplete();
                                    }
                                }
                                """
                ));
    }

    @Test
    void testMonoOnErrorCompleteClassRecipe() {
        Recipe recipe = RewriteUtils.loadRecipe("ReactorRulesRecipes.MonoOnErrorCompleteClassRecipe");
        rewriteRun(
                spec -> spec.recipe(recipe)
                        .expectedCyclesThatMakeChanges(1).cycles(1)
                        .parser(JavaParser.fromJavaVersion().classpath(JavaParser.runtimeClasspath())),
                java(
                        """
                                import com.google.common.collect.ImmutableSet;
                                import reactor.core.publisher.Mono;
                                
                                class Test {
                                    ImmutableSet<Mono<Integer>> test() {
                                        return ImmutableSet.of(Mono.just(1).onErrorComplete(IllegalArgumentException.class::isInstance), Mono.just(2).onErrorResume(IllegalStateException.class, e -> Mono.empty()));
                                    }
                                }
                                """,
                        """
                                import com.google.common.collect.ImmutableSet;
                                import reactor.core.publisher.Mono;
                                
                                class Test {
                                    ImmutableSet<Mono<Integer>> test() {
                                        return ImmutableSet.of(Mono.just(1).onErrorComplete(IllegalArgumentException.class), Mono.just(2).onErrorComplete(IllegalStateException.class));
                                    }
                                }
                                """
                ));
    }

    @Test
    void testMonoOnErrorCompletePredicateRecipe() {
        Recipe recipe = RewriteUtils.loadRecipe("ReactorRulesRecipes.MonoOnErrorCompletePredicateRecipe");
        rewriteRun(
                spec -> spec.recipe(recipe)
                        .expectedCyclesThatMakeChanges(1).cycles(1)
                        .parser(JavaParser.fromJavaVersion().classpath(JavaParser.runtimeClasspath())),
                java(
                        """
                                import reactor.core.publisher.Mono;
                                
                                class Test {
                                    Mono<Integer> test() {
                                        return Mono.just(1).onErrorResume(e -> e.getCause() == null, e -> Mono.empty());
                                    }
                                }
                                """,
                        """
                                import reactor.core.publisher.Mono;
                                
                                class Test {
                                    Mono<Integer> test() {
                                        return Mono.just(1).onErrorComplete(e -> e.getCause() == null);
                                    }
                                }
                                """
                ));
    }

    @Test
    void testMonoOnErrorContinueRecipe() {
        Recipe recipe = RewriteUtils.loadRecipe("ReactorRulesRecipes.MonoOnErrorContinueRecipe");
        rewriteRun(
                spec -> spec.recipe(recipe)
                        .expectedCyclesThatMakeChanges(1).cycles(1)
                        .parser(JavaParser.fromJavaVersion().classpath(JavaParser.runtimeClasspath())),
                java(
                        """
                                import reactor.core.publisher.Mono;
                                
                                class Test {
                                    Mono<Integer> test() {
                                        return Mono.just(1).onErrorContinue(IllegalArgumentException.class::isInstance, (e, v) -> {
                                        });
                                    }
                                }
                                """,
                        """
                                import reactor.core.publisher.Mono;
                                
                                class Test {
                                    Mono<Integer> test() {
                                        return Mono.just(1).onErrorContinue(IllegalArgumentException.class, (e, v) -> {
                                        });
                                    }
                                }
                                """
                ));
    }

    @Test
    void testMonoOnErrorMapRecipe() {
        Recipe recipe = RewriteUtils.loadRecipe("ReactorRulesRecipes.MonoOnErrorMapRecipe");
        rewriteRun(
                spec -> spec.recipe(recipe)
                        .expectedCyclesThatMakeChanges(1).cycles(1)
                        .parser(JavaParser.fromJavaVersion().classpath(JavaParser.runtimeClasspath())),
                java(
                        """
                                import reactor.core.publisher.Mono;
                                
                                class Test {
                                    Mono<Integer> test() {
                                        return Mono.just(1).onErrorMap(IllegalArgumentException.class::isInstance, e -> e);
                                    }
                                }
                                """,
                        """
                                import reactor.core.publisher.Mono;
                                
                                class Test {
                                    Mono<Integer> test() {
                                        return Mono.just(1).onErrorMap(IllegalArgumentException.class, e -> e);
                                    }
                                }
                                """
                ));
    }

    @Test
    void testMonoOnErrorResumeRecipe() {
        Recipe recipe = RewriteUtils.loadRecipe("ReactorRulesRecipes.MonoOnErrorResumeRecipe");
        rewriteRun(
                spec -> spec.recipe(recipe)
                        .expectedCyclesThatMakeChanges(1).cycles(1)
                        .parser(JavaParser.fromJavaVersion().classpath(JavaParser.runtimeClasspath())),
                java(
                        """
                                import reactor.core.publisher.Mono;
                                
                                class Test {
                                    Mono<Integer> test() {
                                        return Mono.just(1).onErrorResume(IllegalArgumentException.class::isInstance, e -> Mono.just(2));
                                    }
                                }
                                """,
                        """
                                import reactor.core.publisher.Mono;
                                
                                class Test {
                                    Mono<Integer> test() {
                                        return Mono.just(1).onErrorResume(IllegalArgumentException.class, e -> Mono.just(2));
                                    }
                                }
                                """
                ));
    }

    @Test
    void testMonoOnErrorReturnRecipe() {
        Recipe recipe = RewriteUtils.loadRecipe("ReactorRulesRecipes.MonoOnErrorReturnRecipe");
        rewriteRun(
                spec -> spec.recipe(recipe)
                        .expectedCyclesThatMakeChanges(1).cycles(1)
                        .parser(JavaParser.fromJavaVersion().classpath(JavaParser.runtimeClasspath())),
                java(
                        """
                                import reactor.core.publisher.Mono;
                                
                                class Test {
                                    Mono<Integer> test() {
                                        return Mono.just(1).onErrorReturn(IllegalArgumentException.class::isInstance, 2);
                                    }
                                }
                                """,
                        """
                                import reactor.core.publisher.Mono;
                                
                                class Test {
                                    Mono<Integer> test() {
                                        return Mono.just(1).onErrorReturn(IllegalArgumentException.class, 2);
                                    }
                                }
                                """
                ));
    }

    @Test
    void testMonoSingleRecipe() {
        Recipe recipe = RewriteUtils.loadRecipe("ReactorRulesRecipes.MonoSingleRecipe");
        rewriteRun(
                spec -> spec.recipe(recipe)
                        .expectedCyclesThatMakeChanges(1).cycles(1)
                        .parser(JavaParser.fromJavaVersion().classpath(JavaParser.runtimeClasspath())),
                java(
                        """
                                import reactor.core.publisher.Mono;
                                
                                class Test {
                                    Mono<Integer> test() {
                                        return Mono.just(1).flux().single();
                                    }
                                }
                                """,
                        """
                                import reactor.core.publisher.Mono;
                                
                                class Test {
                                    Mono<Integer> test() {
                                        return Mono.just(1).single();
                                    }
                                }
                                """
                ));
    }

    @Test
    void testMonoSingleOptionalRecipe() {
        Recipe recipe = RewriteUtils.loadRecipe("ReactorRulesRecipes.MonoSingleOptionalRecipe");
        rewriteRun(
                spec -> spec.recipe(recipe)
                        .expectedCyclesThatMakeChanges(1).cycles(1)
                        .parser(JavaParser.fromJavaVersion().classpath(JavaParser.runtimeClasspath())),
                java(
                        """
                                import com.google.common.collect.ImmutableSet;
                                import com.google.common.collect.MoreCollectors;
                                import reactor.core.publisher.Mono;
                                
                                import java.util.Optional;
                                import java.util.function.Function;
                                
                                class Test {
                                    ImmutableSet<Mono<Optional<String>>> test() {
                                        return ImmutableSet.of(Mono.just("foo").flux().collect(MoreCollectors.toOptional()), Mono.just("bar").map(Optional::of), Mono.just("baz").singleOptional().defaultIfEmpty(Optional.empty()), Mono.just("quux").singleOptional().switchIfEmpty(Mono.just(Optional.empty())), Mono.just("quuz").transform(Mono::singleOptional));
                                    }
                                }
                                """,
                        """
                                import com.google.common.collect.ImmutableSet;
                                import reactor.core.publisher.Mono;
                                
                                import java.util.Optional;
                                
                                class Test {
                                    ImmutableSet<Mono<Optional<String>>> test() {
                                        return ImmutableSet.of(Mono.just("foo").singleOptional(), Mono.just("bar").singleOptional(), Mono.just("baz").singleOptional(), Mono.just("quux").singleOptional(), Mono.just("quuz").singleOptional());
                                    }
                                }
                                """
                ));
    }

    @Test
    void testMonoThenRecipe() {
        Recipe recipe = RewriteUtils.loadRecipe("ReactorRulesRecipes.MonoThenRecipe");
        rewriteRun(
                spec -> spec.recipe(recipe)
                        .expectedCyclesThatMakeChanges(1).cycles(1)
                        .parser(JavaParser.fromJavaVersion().classpath(JavaParser.runtimeClasspath())),
                java(
                        """
                                import com.google.common.collect.ImmutableSet;
                                import reactor.core.publisher.Mono;
                                
                                class Test {
                                    ImmutableSet<Mono<Void>> test() {
                                        return ImmutableSet.of(Mono.just("foo").ignoreElement().then(), Mono.just("bar").flux().then(), Mono.when(Mono.just("baz")), Mono.whenDelayError(Mono.just("qux")));
                                    }
                                }
                                """,
                        """
                                import com.google.common.collect.ImmutableSet;
                                import reactor.core.publisher.Mono;
                                
                                class Test {
                                    ImmutableSet<Mono<Void>> test() {
                                        return ImmutableSet.of(Mono.just("foo").then(), Mono.just("bar").then(), Mono.just("baz").then(), Mono.just("qux").then());
                                    }
                                }
                                """
                ));
    }

    @Test
    void testMonoThenEmptyRecipe() {
        Recipe recipe = RewriteUtils.loadRecipe("ReactorRulesRecipes.MonoThenEmptyRecipe");
        rewriteRun(
                spec -> spec.recipe(recipe)
                        .expectedCyclesThatMakeChanges(1).cycles(1)
                        .parser(JavaParser.fromJavaVersion().classpath(JavaParser.runtimeClasspath())),
                java(
                        """
                                import reactor.core.publisher.Mono;
                                
                                class Test {
                                    Mono<Void> test() {
                                        return Mono.just("foo").ignoreElement().thenEmpty(Mono.empty());
                                    }
                                }
                                """,
                        """
                                import reactor.core.publisher.Mono;
                                
                                class Test {
                                    Mono<Void> test() {
                                        return Mono.just("foo").thenEmpty(Mono.empty());
                                    }
                                }
                                """
                ));
    }

    @Test
    void testMonoThenManyRecipe() {
        Recipe recipe = RewriteUtils.loadRecipe("ReactorRulesRecipes.MonoThenManyRecipe");
        rewriteRun(
                spec -> spec.recipe(recipe)
                        .expectedCyclesThatMakeChanges(1).cycles(1)
                        .parser(JavaParser.fromJavaVersion().classpath(JavaParser.runtimeClasspath())),
                java(
                        """
                                import com.google.common.collect.ImmutableSet;
                                import reactor.core.publisher.Flux;
                                import reactor.core.publisher.Mono;
                                
                                class Test {
                                    ImmutableSet<Flux<String>> test() {
                                        return ImmutableSet.of(Mono.just("foo").ignoreElement().thenMany(Flux.just("bar")), Mono.just("baz").ignoreElement().thenMany(Flux.just("qux")));
                                    }
                                }
                                """,
                        """
                                import com.google.common.collect.ImmutableSet;
                                import reactor.core.publisher.Flux;
                                import reactor.core.publisher.Mono;
                                
                                class Test {
                                    ImmutableSet<Flux<String>> test() {
                                        return ImmutableSet.of(Mono.just("foo").thenMany(Flux.just("bar")), Mono.just("baz").thenMany(Flux.just("qux")));
                                    }
                                }
                                """
                ));
    }

    @Test
    void testMonoThenMonoRecipe() {
        Recipe recipe = RewriteUtils.loadRecipe("ReactorRulesRecipes.MonoThenMonoRecipe");
        rewriteRun(
                spec -> spec.recipe(recipe)
                        .expectedCyclesThatMakeChanges(1).cycles(1)
                        .parser(JavaParser.fromJavaVersion().classpath(JavaParser.runtimeClasspath())),
                java(
                        """
                                import com.google.common.collect.ImmutableSet;
                                import reactor.core.publisher.Mono;
                                
                                class Test {
                                    ImmutableSet<Mono<?>> test() {
                                        return ImmutableSet.of(Mono.just("foo").ignoreElement().then(Mono.just("bar")), Mono.just("baz").flux().then(Mono.just("qux")), Mono.just("quux").thenEmpty(Mono.<Void>empty()));
                                    }
                                }
                                """,
                        """
                                import com.google.common.collect.ImmutableSet;
                                import reactor.core.publisher.Mono;
                                
                                class Test {
                                    ImmutableSet<Mono<?>> test() {
                                        return ImmutableSet.of(Mono.just("foo").then(Mono.just("bar")), Mono.just("baz").then(Mono.just("qux")), Mono.just("quux").then(Mono.<Void>empty()));
                                    }
                                }
                                """
                ));
    }

    @Test
    void testMonoThenMonoFluxRecipe() {
        Recipe recipe = RewriteUtils.loadRecipe("ReactorRulesRecipes.MonoThenMonoFluxRecipe");
        rewriteRun(
                spec -> spec.recipe(recipe)
                        .expectedCyclesThatMakeChanges(1).cycles(1)
                        .parser(JavaParser.fromJavaVersion().classpath(JavaParser.runtimeClasspath())),
                java(
                        """
                                import reactor.core.publisher.Flux;
                                import reactor.core.publisher.Mono;
                                
                                class Test {
                                    Flux<String> test() {
                                        return Mono.just("foo").thenMany(Mono.just("bar"));
                                    }
                                }
                                """,
                        """
                                import reactor.core.publisher.Flux;
                                import reactor.core.publisher.Mono;
                                
                                class Test {
                                    Flux<String> test() {
                                        return Mono.just("foo").then(Mono.just("bar")).flux();
                                    }
                                }
                                """
                ));
    }

    @Test
    void testMonoThenReturnRecipe() {
        Recipe recipe = RewriteUtils.loadRecipe("ReactorRulesRecipes.MonoThenReturnRecipe");
        rewriteRun(
                spec -> spec.recipe(recipe)
                        .expectedCyclesThatMakeChanges(1).cycles(1)
                        .parser(JavaParser.fromJavaVersion().classpath(JavaParser.runtimeClasspath())),
                java(
                        """
                                import com.google.common.collect.ImmutableSet;
                                import reactor.core.publisher.Mono;
                                
                                class Test {
                                    ImmutableSet<Mono<String>> test() {
                                        return ImmutableSet.of(Mono.just(1).ignoreElement().thenReturn("foo"), Mono.just(2).then().thenReturn("bar"), Mono.just(3).then(Mono.just("baz")));
                                    }
                                }
                                """,
                        """
                                import com.google.common.collect.ImmutableSet;
                                import reactor.core.publisher.Mono;
                                
                                class Test {
                                    ImmutableSet<Mono<String>> test() {
                                        return ImmutableSet.of(Mono.just(1).thenReturn("foo"), Mono.just(2).thenReturn("bar"), Mono.just(3).thenReturn("baz"));
                                    }
                                }
                                """
                ));
    }

    @Test
    void testMonoZipRecipe() {
        Recipe recipe = RewriteUtils.loadRecipe("ReactorRulesRecipes.MonoZipRecipe");
        rewriteRun(
                spec -> spec.recipe(recipe)
                        .expectedCyclesThatMakeChanges(1).cycles(1)
                        .parser(JavaParser.fromJavaVersion().classpath(JavaParser.runtimeClasspath())),
                java(
                        """
                                import reactor.core.publisher.Mono;
                                import reactor.util.function.Tuple2;
                                
                                class Test {
                                    Mono<Tuple2<String, Integer>> test() {
                                        return Mono.just("foo").zipWith(Mono.just(1));
                                    }
                                }
                                """,
                        """
                                import reactor.core.publisher.Mono;
                                import reactor.util.function.Tuple2;
                                
                                class Test {
                                    Mono<Tuple2<String, Integer>> test() {
                                        return Mono.zip(Mono.just("foo"), Mono.just(1));
                                    }
                                }
                                """
                ));
    }

    @Test
    void testMonoZipWithCombinatorRecipe() {
        Recipe recipe = RewriteUtils.loadRecipe("ReactorRulesRecipes.MonoZipWithCombinatorRecipe");
        rewriteRun(
                spec -> spec.recipe(recipe)
                        .expectedCyclesThatMakeChanges(1).cycles(1)
                        .parser(JavaParser.fromJavaVersion().classpath(JavaParser.runtimeClasspath())),
                java(
                        """
                                import reactor.core.publisher.Mono;
                                
                                class Test {
                                    Mono<String> test() {
                                        return Mono.just("foo").zipWith(Mono.just(1), String::repeat);
                                    }
                                }
                                """,
                        """
                                import reactor.core.publisher.Mono;
                                import reactor.function.TupleUtils;
                                
                                class Test {
                                    Mono<String> test() {
                                        return Mono.zip(Mono.just("foo"), Mono.just(1)).map(TupleUtils.function(String::repeat));
                                    }
                                }
                                """
                ));
    }

    @Test
    void testOptionalMapMonoJustRecipe() {
        Recipe recipe = RewriteUtils.loadRecipe("ReactorRulesRecipes.OptionalMapMonoJustRecipe");
        rewriteRun(
                spec -> spec.recipe(recipe)
                        .expectedCyclesThatMakeChanges(1).cycles(1)
                        .parser(JavaParser.fromJavaVersion().classpath(JavaParser.runtimeClasspath())),
                java(
                        """
                                import reactor.core.publisher.Mono;
                                
                                import java.util.Optional;
                                import java.util.function.Function;
                                
                                class Test {
                                    Optional<Mono<String>> test() {
                                        return Optional.of("foo").map(Mono::justOrEmpty);
                                    }
                                }
                                """,
                        """
                                import reactor.core.publisher.Mono;
                                
                                import java.util.Optional;
                                import java.util.function.Function;
                                
                                class Test {
                                    Optional<Mono<String>> test() {
                                        return Optional.of("foo").map(Mono::just);
                                    }
                                }
                                """
                ));
    }

    @Test
    void testPublisherProbeAssertWasCancelledRecipe() {
        Recipe recipe = RewriteUtils.loadRecipe("ReactorRulesRecipes.PublisherProbeAssertWasCancelledRecipe");
        rewriteRun(
                spec -> spec.recipe(recipe)
                        .expectedCyclesThatMakeChanges(1).cycles(1)
                        .parser(JavaParser.fromJavaVersion().classpath(JavaParser.runtimeClasspath())),
                java(
                        """
                                import org.assertj.core.api.Assertions;
                                import reactor.test.publisher.PublisherProbe;
                                
                                class Test {
                                    void test() {
                                        Assertions.assertThat(PublisherProbe.empty().wasCancelled()).isTrue();
                                    }
                                }
                                """,
                        """
                                import reactor.test.publisher.PublisherProbe;
                                
                                class Test {
                                    void test() {
                                        PublisherProbe.empty().assertWasCancelled();
                                    }
                                }
                                """
                ));
    }

    @Test
    void testPublisherProbeAssertWasNotCancelledRecipe() {
        Recipe recipe = RewriteUtils.loadRecipe("ReactorRulesRecipes.PublisherProbeAssertWasNotCancelledRecipe");
        rewriteRun(
                spec -> spec.recipe(recipe)
                        .expectedCyclesThatMakeChanges(1).cycles(1)
                        .parser(JavaParser.fromJavaVersion().classpath(JavaParser.runtimeClasspath())),
                java(
                        """
                                import org.assertj.core.api.Assertions;
                                import reactor.test.publisher.PublisherProbe;
                                
                                class Test {
                                    void test() {
                                        Assertions.assertThat(PublisherProbe.empty().wasCancelled()).isFalse();
                                    }
                                }
                                """,
                        """
                                import reactor.test.publisher.PublisherProbe;
                                
                                class Test {
                                    void test() {
                                        PublisherProbe.empty().assertWasNotCancelled();
                                    }
                                }
                                """
                ));
    }

    @Test
    void testPublisherProbeAssertWasNotRequestedRecipe() {
        Recipe recipe = RewriteUtils.loadRecipe("ReactorRulesRecipes.PublisherProbeAssertWasNotRequestedRecipe");
        rewriteRun(
                spec -> spec.recipe(recipe)
                        .expectedCyclesThatMakeChanges(1).cycles(1)
                        .parser(JavaParser.fromJavaVersion().classpath(JavaParser.runtimeClasspath())),
                java(
                        """
                                import org.assertj.core.api.Assertions;
                                import reactor.test.publisher.PublisherProbe;
                                
                                class Test {
                                    void test() {
                                        Assertions.assertThat(PublisherProbe.empty().wasRequested()).isFalse();
                                    }
                                }
                                """,
                        """
                                import reactor.test.publisher.PublisherProbe;
                                
                                class Test {
                                    void test() {
                                        PublisherProbe.empty().assertWasNotRequested();
                                    }
                                }
                                """
                ));
    }

    @Test
    void testPublisherProbeAssertWasNotSubscribedRecipe() {
        Recipe recipe = RewriteUtils.loadRecipe("ReactorRulesRecipes.PublisherProbeAssertWasNotSubscribedRecipe");
        rewriteRun(
                spec -> spec.recipe(recipe)
                        .expectedCyclesThatMakeChanges(1).cycles(1)
                        .parser(JavaParser.fromJavaVersion().classpath(JavaParser.runtimeClasspath())),
                java(
                        """
                                import org.assertj.core.api.Assertions;
                                import reactor.core.publisher.Mono;
                                import reactor.test.publisher.PublisherProbe;
                                
                                class Test {
                                    void test() {
                                        Assertions.assertThat(PublisherProbe.of(Mono.just(1)).wasSubscribed()).isFalse();
                                        Assertions.assertThat(PublisherProbe.of(Mono.just(2)).subscribeCount()).isEqualTo(0);
                                        Assertions.assertThat(PublisherProbe.of(Mono.just(3)).subscribeCount()).isNotPositive();
                                    }
                                }
                                """,
                        """
                                import reactor.core.publisher.Mono;
                                import reactor.test.publisher.PublisherProbe;
                                
                                class Test {
                                    void test() {
                                        PublisherProbe.of(Mono.just(1)).assertWasNotSubscribed();
                                        PublisherProbe.of(Mono.just(2)).assertWasNotSubscribed();
                                        PublisherProbe.of(Mono.just(3)).assertWasNotSubscribed();
                                    }
                                }
                                """
                ));
    }

    @Test
    void testPublisherProbeAssertWasRequestedRecipe() {
        Recipe recipe = RewriteUtils.loadRecipe("ReactorRulesRecipes.PublisherProbeAssertWasRequestedRecipe");
        rewriteRun(
                spec -> spec.recipe(recipe)
                        .expectedCyclesThatMakeChanges(1).cycles(1)
                        .parser(JavaParser.fromJavaVersion().classpath(JavaParser.runtimeClasspath())),
                java(
                        """
                                import org.assertj.core.api.Assertions;
                                import reactor.test.publisher.PublisherProbe;
                                
                                class Test {
                                    void test() {
                                        Assertions.assertThat(PublisherProbe.empty().wasRequested()).isTrue();
                                    }
                                }
                                """,
                        """
                                import reactor.test.publisher.PublisherProbe;
                                
                                class Test {
                                    void test() {
                                        PublisherProbe.empty().assertWasRequested();
                                    }
                                }
                                """
                ));
    }

    @Test
    void testPublisherProbeAssertWasSubscribedRecipe() {
        Recipe recipe = RewriteUtils.loadRecipe("ReactorRulesRecipes.PublisherProbeAssertWasSubscribedRecipe");
        rewriteRun(
                spec -> spec.recipe(recipe)
                        .expectedCyclesThatMakeChanges(1).cycles(1)
                        .parser(JavaParser.fromJavaVersion().classpath(JavaParser.runtimeClasspath())),
                java(
                        """
                                import org.assertj.core.api.Assertions;
                                import reactor.core.publisher.Mono;
                                import reactor.test.publisher.PublisherProbe;
                                
                                class Test {
                                    void test() {
                                        Assertions.assertThat(PublisherProbe.of(Mono.just(1)).wasSubscribed()).isTrue();
                                        Assertions.assertThat(PublisherProbe.of(Mono.just(2)).subscribeCount()).isNotNegative();
                                        Assertions.assertThat(PublisherProbe.of(Mono.just(3)).subscribeCount()).isNotEqualTo(0);
                                        Assertions.assertThat(PublisherProbe.of(Mono.just(4)).subscribeCount()).isPositive();
                                    }
                                }
                                """,
                        """
                                import reactor.core.publisher.Mono;
                                import reactor.test.publisher.PublisherProbe;
                                
                                class Test {
                                    void test() {
                                        PublisherProbe.of(Mono.just(1)).assertWasSubscribed();
                                        PublisherProbe.of(Mono.just(2)).assertWasSubscribed();
                                        PublisherProbe.of(Mono.just(3)).assertWasSubscribed();
                                        PublisherProbe.of(Mono.just(4)).assertWasSubscribed();
                                    }
                                }
                                """
                ));
    }

    @Test
    void testPublisherProbeEmptyRecipe() {
        Recipe recipe = RewriteUtils.loadRecipe("ReactorRulesRecipes.PublisherProbeEmptyRecipe");
        rewriteRun(
                spec -> spec.recipe(recipe)
                        .expectedCyclesThatMakeChanges(1).cycles(1)
                        .parser(JavaParser.fromJavaVersion().classpath(JavaParser.runtimeClasspath())),
                java(
                        """
                                import com.google.common.collect.ImmutableSet;
                                import reactor.core.publisher.Flux;
                                import reactor.core.publisher.Mono;
                                import reactor.test.publisher.PublisherProbe;
                                
                                class Test {
                                    ImmutableSet<PublisherProbe<Void>> test() {
                                        return ImmutableSet.of(PublisherProbe.of(Mono.empty()), PublisherProbe.of(Flux.empty()));
                                    }
                                }
                                """,
                        """
                                import com.google.common.collect.ImmutableSet;
                                import reactor.test.publisher.PublisherProbe;
                                
                                class Test {
                                    ImmutableSet<PublisherProbe<Void>> test() {
                                        return ImmutableSet.of(PublisherProbe.empty(), PublisherProbe.empty());
                                    }
                                }
                                """
                ));
    }

    @Test
    void testStepVerifierFromFluxRecipe() {
        Recipe recipe = RewriteUtils.loadRecipe("ReactorRulesRecipes.StepVerifierFromFluxRecipe");
        rewriteRun(
                spec -> spec.recipe(recipe)
                        .expectedCyclesThatMakeChanges(1).cycles(1)
                        .parser(JavaParser.fromJavaVersion().classpath(JavaParser.runtimeClasspath())),
                java(
                        """
                                import reactor.core.publisher.Flux;
                                import reactor.test.StepVerifier;
                                
                                class Test {
                                    StepVerifier.FirstStep<Integer> test() {
                                        return StepVerifier.create(Flux.just(1));
                                    }
                                }
                                """,
                        """
                                import reactor.core.publisher.Flux;
                                import reactor.test.StepVerifier;
                                
                                import java.util.function.Function;
                                
                                class Test {
                                    StepVerifier.FirstStep<Integer> test() {
                                        return Flux.just(1).as(StepVerifier::create);
                                    }
                                }
                                """
                ));
    }

    @Test
    void testStepVerifierFromMonoRecipe() {
        Recipe recipe = RewriteUtils.loadRecipe("ReactorRulesRecipes.StepVerifierFromMonoRecipe");
        rewriteRun(
                spec -> spec.recipe(recipe)
                        .expectedCyclesThatMakeChanges(1).cycles(1)
                        .parser(JavaParser.fromJavaVersion().classpath(JavaParser.runtimeClasspath())),
                java(
                        """
                                import com.google.common.collect.ImmutableSet;
                                import reactor.core.publisher.Flux;
                                import reactor.core.publisher.Mono;
                                import reactor.test.StepVerifier;
                                
                                import java.util.function.Function;
                                
                                class Test {
                                    ImmutableSet<StepVerifier.FirstStep<Integer>> test() {
                                        return ImmutableSet.of(StepVerifier.create(Mono.just(1)), Mono.just(2).flux().as(StepVerifier::create));
                                    }
                                }
                                """,
                        """
                                import com.google.common.collect.ImmutableSet;
                                import reactor.core.publisher.Mono;
                                import reactor.test.StepVerifier;
                                
                                import java.util.function.Function;
                                
                                class Test {
                                    ImmutableSet<StepVerifier.FirstStep<Integer>> test() {
                                        return ImmutableSet.of(Mono.just(1).as(StepVerifier::create), Mono.just(2).as(StepVerifier::create));
                                    }
                                }
                                """
                ));
    }

    @Test
    void testStepVerifierLastStepVerifyCompleteRecipe() {
        Recipe recipe = RewriteUtils.loadRecipe("ReactorRulesRecipes.StepVerifierLastStepVerifyCompleteRecipe");
        rewriteRun(
                spec -> spec.recipe(recipe)
                        .expectedCyclesThatMakeChanges(1).cycles(1)
                        .parser(JavaParser.fromJavaVersion().classpath(JavaParser.runtimeClasspath())),
                java(
                        """
                                import reactor.core.publisher.Mono;
                                import reactor.test.StepVerifier;
                                
                                import java.time.Duration;
                                import java.util.function.Function;
                                
                                class Test {
                                    Duration test() {
                                        return Mono.empty().as(StepVerifier::create).expectComplete().verify();
                                    }
                                }
                                """,
                        """
                                import reactor.core.publisher.Mono;
                                import reactor.test.StepVerifier;
                                
                                import java.time.Duration;
                                import java.util.function.Function;
                                
                                class Test {
                                    Duration test() {
                                        return Mono.empty().as(StepVerifier::create).verifyComplete();
                                    }
                                }
                                """
                ));
    }

    @Test
    void testStepVerifierLastStepVerifyErrorRecipe() {
        Recipe recipe = RewriteUtils.loadRecipe("ReactorRulesRecipes.StepVerifierLastStepVerifyErrorRecipe");
        rewriteRun(
                spec -> spec.recipe(recipe)
                        .expectedCyclesThatMakeChanges(1).cycles(1)
                        .parser(JavaParser.fromJavaVersion().classpath(JavaParser.runtimeClasspath())),
                java(
                        """
                                import reactor.core.publisher.Mono;
                                import reactor.test.StepVerifier;
                                
                                import java.time.Duration;
                                import java.util.function.Function;
                                
                                class Test {
                                    Duration test() {
                                        return Mono.empty().as(StepVerifier::create).expectError().verify();
                                    }
                                }
                                """,
                        """
                                import reactor.core.publisher.Mono;
                                import reactor.test.StepVerifier;
                                
                                import java.time.Duration;
                                import java.util.function.Function;
                                
                                class Test {
                                    Duration test() {
                                        return Mono.empty().as(StepVerifier::create).verifyError();
                                    }
                                }
                                """
                ));
    }

    @Test
    void testStepVerifierLastStepVerifyErrorClassRecipe() {
        Recipe recipe = RewriteUtils.loadRecipe("ReactorRulesRecipes.StepVerifierLastStepVerifyErrorClassRecipe");
        rewriteRun(
                spec -> spec.recipe(recipe)
                        .expectedCyclesThatMakeChanges(1).cycles(1)
                        .parser(JavaParser.fromJavaVersion().classpath(JavaParser.runtimeClasspath())),
                java(
                        """
                                import com.google.common.collect.ImmutableSet;
                                import org.assertj.core.api.Assertions;
                                import reactor.core.publisher.Mono;
                                import reactor.test.StepVerifier;
                                
                                import java.time.Duration;
                                import java.util.function.Function;
                                
                                class Test {
                                    ImmutableSet<Duration> test() {
                                        return ImmutableSet.of(Mono.empty().as(StepVerifier::create).expectError(IllegalArgumentException.class).verify(), Mono.empty().as(StepVerifier::create).verifyErrorMatches(IllegalStateException.class::isInstance), Mono.empty().as(StepVerifier::create).verifyErrorSatisfies(t -> Assertions.assertThat(t).isInstanceOf(AssertionError.class)));
                                    }
                                }
                                """,
                        """
                                import com.google.common.collect.ImmutableSet;
                                import reactor.core.publisher.Mono;
                                import reactor.test.StepVerifier;
                                
                                import java.time.Duration;
                                import java.util.function.Function;
                                
                                class Test {
                                    ImmutableSet<Duration> test() {
                                        return ImmutableSet.of(Mono.empty().as(StepVerifier::create).verifyError(IllegalArgumentException.class), Mono.empty().as(StepVerifier::create).verifyError(IllegalStateException.class), Mono.empty().as(StepVerifier::create).verifyError(AssertionError.class));
                                    }
                                }
                                """
                ));
    }

    @Test
    void testStepVerifierLastStepVerifyErrorMatchesRecipe() {
        Recipe recipe = RewriteUtils.loadRecipe("ReactorRulesRecipes.StepVerifierLastStepVerifyErrorMatchesRecipe");
        rewriteRun(
                spec -> spec.recipe(recipe)
                        .expectedCyclesThatMakeChanges(1).cycles(1)
                        .parser(JavaParser.fromJavaVersion().classpath(JavaParser.runtimeClasspath())),
                java(
                        """
                                import com.google.common.collect.ImmutableSet;
                                import reactor.core.publisher.Mono;
                                import reactor.test.StepVerifier;
                                
                                import java.util.function.Function;
                                
                                class Test {
                                    ImmutableSet<?> test() {
                                        return ImmutableSet.of(Mono.empty().as(StepVerifier::create).expectErrorMatches(IllegalArgumentException.class::equals).verify(), Mono.empty().as(StepVerifier::create).expectError().verifyThenAssertThat().hasOperatorErrorMatching(IllegalStateException.class::equals));
                                    }
                                }
                                """,
                        """
                                import com.google.common.collect.ImmutableSet;
                                import reactor.core.publisher.Mono;
                                import reactor.test.StepVerifier;
                                
                                import java.util.function.Function;
                                
                                class Test {
                                    ImmutableSet<?> test() {
                                        return ImmutableSet.of(Mono.empty().as(StepVerifier::create).verifyErrorMatches(IllegalArgumentException.class::equals), Mono.empty().as(StepVerifier::create).verifyErrorMatches(IllegalStateException.class::equals));
                                    }
                                }
                                """
                ));
    }

    @Test
    void testStepVerifierLastStepVerifyErrorMessageRecipe() {
        Recipe recipe = RewriteUtils.loadRecipe("ReactorRulesRecipes.StepVerifierLastStepVerifyErrorMessageRecipe");
        rewriteRun(
                spec -> spec.recipe(recipe)
                        .expectedCyclesThatMakeChanges(1).cycles(1)
                        .parser(JavaParser.fromJavaVersion().classpath(JavaParser.runtimeClasspath())),
                java(
                        """
                                import reactor.core.publisher.Mono;
                                import reactor.test.StepVerifier;
                                
                                import java.time.Duration;
                                import java.util.function.Function;
                                
                                class Test {
                                    Duration test() {
                                        return Mono.empty().as(StepVerifier::create).expectErrorMessage("foo").verify();
                                    }
                                }
                                """,
                        """
                                import reactor.core.publisher.Mono;
                                import reactor.test.StepVerifier;
                                
                                import java.time.Duration;
                                import java.util.function.Function;
                                
                                class Test {
                                    Duration test() {
                                        return Mono.empty().as(StepVerifier::create).verifyErrorMessage("foo");
                                    }
                                }
                                """
                ));
    }

    @Test
    void testStepVerifierLastStepVerifyErrorSatisfiesRecipe() {
        Recipe recipe = RewriteUtils.loadRecipe("ReactorRulesRecipes.StepVerifierLastStepVerifyErrorSatisfiesRecipe");
        rewriteRun(
                spec -> spec.recipe(recipe)
                        .expectedCyclesThatMakeChanges(1).cycles(1)
                        .parser(JavaParser.fromJavaVersion().classpath(JavaParser.runtimeClasspath())),
                java(
                        """
                                import reactor.core.publisher.Mono;
                                import reactor.test.StepVerifier;
                                
                                import java.time.Duration;
                                import java.util.function.Function;
                                
                                class Test {
                                    Duration test() {
                                        return Mono.empty().as(StepVerifier::create).expectErrorSatisfies(t -> {
                                        }).verify();
                                    }
                                }
                                """,
                        """
                                import reactor.core.publisher.Mono;
                                import reactor.test.StepVerifier;
                                
                                import java.time.Duration;
                                import java.util.function.Function;
                                
                                class Test {
                                    Duration test() {
                                        return Mono.empty().as(StepVerifier::create).verifyErrorSatisfies(t -> {
                                        });
                                    }
                                }
                                """
                ));
    }

    @Test
    void testStepVerifierLastStepVerifyErrorSatisfiesAssertJRecipe() {
        Recipe recipe = RewriteUtils.loadRecipe("ReactorRulesRecipes.StepVerifierLastStepVerifyErrorSatisfiesAssertJRecipe");
        rewriteRun(
                spec -> spec.recipe(recipe)
                        .expectedCyclesThatMakeChanges(1).cycles(1)
                        .parser(JavaParser.fromJavaVersion().classpath(JavaParser.runtimeClasspath())),
                java(
                        """
                                import com.google.common.collect.ImmutableSet;
                                import reactor.core.publisher.Mono;
                                import reactor.test.StepVerifier;
                                
                                import java.util.function.Function;
                                
                                class Test {
                                    ImmutableSet<?> test() {
                                        return ImmutableSet.of(Mono.empty().as(StepVerifier::create).expectError().verifyThenAssertThat().hasOperatorErrorOfType(IllegalArgumentException.class).hasOperatorErrorWithMessage("foo"), Mono.empty().as(StepVerifier::create).expectError(IllegalStateException.class).verifyThenAssertThat().hasOperatorErrorWithMessage("bar"), Mono.empty().as(StepVerifier::create).expectErrorMessage("baz").verifyThenAssertThat().hasOperatorErrorOfType(AssertionError.class));
                                    }
                                }
                                """,
                        """
                                import com.google.common.collect.ImmutableSet;
                                import org.assertj.core.api.Assertions;
                                import reactor.core.publisher.Mono;
                                import reactor.test.StepVerifier;
                                
                                import java.util.function.Function;
                                
                                class Test {
                                    ImmutableSet<?> test() {
                                        return ImmutableSet.of(Mono.empty().as(StepVerifier::create).verifyErrorSatisfies(t -> Assertions.assertThat(t).isInstanceOf(IllegalArgumentException.class).hasMessage("foo")), Mono.empty().as(StepVerifier::create).verifyErrorSatisfies(t -> Assertions.assertThat(t).isInstanceOf(IllegalStateException.class).hasMessage("bar")), Mono.empty().as(StepVerifier::create).verifyErrorSatisfies(t -> Assertions.assertThat(t).isInstanceOf(AssertionError.class).hasMessage("baz")));
                                    }
                                }
                                """
                ));
    }

    @Test
    void testStepVerifierLastStepVerifyTimeoutRecipe() {
        Recipe recipe = RewriteUtils.loadRecipe("ReactorRulesRecipes.StepVerifierLastStepVerifyTimeoutRecipe");
        rewriteRun(
                spec -> spec.recipe(recipe)
                        .expectedCyclesThatMakeChanges(1).cycles(1)
                        .parser(JavaParser.fromJavaVersion().classpath(JavaParser.runtimeClasspath())),
                java(
                        """
                                import reactor.core.publisher.Mono;
                                import reactor.test.StepVerifier;
                                
                                import java.time.Duration;
                                import java.util.function.Function;
                                
                                class Test {
                                    Duration test() {
                                        return Mono.empty().as(StepVerifier::create).expectTimeout(Duration.ZERO).verify();
                                    }
                                }
                                """,
                        """
                                import reactor.core.publisher.Mono;
                                import reactor.test.StepVerifier;
                                
                                import java.time.Duration;
                                import java.util.function.Function;
                                
                                class Test {
                                    Duration test() {
                                        return Mono.empty().as(StepVerifier::create).verifyTimeout(Duration.ZERO);
                                    }
                                }
                                """
                ));
    }

    @Test
    void testStepVerifierStepExpectNextRecipe() {
        Recipe recipe = RewriteUtils.loadRecipe("ReactorRulesRecipes.StepVerifierStepExpectNextRecipe");
        rewriteRun(
                spec -> spec.recipe(recipe)
                        .expectedCyclesThatMakeChanges(1).cycles(1)
                        .parser(JavaParser.fromJavaVersion().classpath(JavaParser.runtimeClasspath())),
                java(
                        """
                                import com.google.common.collect.ImmutableSet;
                                import reactor.core.publisher.Mono;
                                import reactor.test.StepVerifier;
                                
                                import java.util.function.Function;
                                
                                class Test {
                                    ImmutableSet<StepVerifier.Step<String>> test() {
                                        return ImmutableSet.of(Mono.just("foo").as(StepVerifier::create).expectNextMatches(s -> s.equals("bar")), Mono.just("baz").as(StepVerifier::create).expectNextMatches("qux"::equals));
                                    }
                                }
                                """,
                        """
                                import com.google.common.collect.ImmutableSet;
                                import reactor.core.publisher.Mono;
                                import reactor.test.StepVerifier;
                                
                                import java.util.function.Function;
                                
                                class Test {
                                    ImmutableSet<StepVerifier.Step<String>> test() {
                                        return ImmutableSet.of(Mono.just("foo").as(StepVerifier::create).expectNext("bar"), Mono.just("baz").as(StepVerifier::create).expectNext("qux"));
                                    }
                                }
                                """
                ));
    }

    @Test
    void testStepVerifierStepIdentityRecipe() {
        Recipe recipe = RewriteUtils.loadRecipe("ReactorRulesRecipes.StepVerifierStepIdentityRecipe");
        rewriteRun(
                spec -> spec.recipe(recipe)
                        .expectedCyclesThatMakeChanges(1).cycles(1)
                        .parser(JavaParser.fromJavaVersion().classpath(JavaParser.runtimeClasspath())),
                java(
                        """
                                import com.google.common.collect.ImmutableList;
                                import com.google.common.collect.ImmutableSet;
                                import reactor.core.publisher.Mono;
                                import reactor.test.StepVerifier;
                                
                                import java.util.function.Function;
                                
                                class Test {
                                    ImmutableSet<StepVerifier.Step<Integer>> test() {
                                        return ImmutableSet.of(Mono.just(1).as(StepVerifier::create).expectNext(), Mono.just(2).as(StepVerifier::create).expectNextCount(0L), Mono.just(3).as(StepVerifier::create).expectNextSequence(ImmutableList.of()), Mono.just(4).as(StepVerifier::create).expectNextSequence(ImmutableList.of(5)));
                                    }
                                }
                                """,
                        """
                                import com.google.common.collect.ImmutableList;
                                import com.google.common.collect.ImmutableSet;
                                import reactor.core.publisher.Mono;
                                import reactor.test.StepVerifier;
                                
                                import java.util.function.Function;
                                
                                class Test {
                                    ImmutableSet<StepVerifier.Step<Integer>> test() {
                                        return ImmutableSet.of(Mono.just(1).as(StepVerifier::create), Mono.just(2).as(StepVerifier::create), Mono.just(3).as(StepVerifier::create), Mono.just(4).as(StepVerifier::create).expectNextSequence(ImmutableList.of(5)));
                                    }
                                }
                                """
                ));
    }

    @Test
    void testStepVerifierVerifyRecipe() {
        Recipe recipe = RewriteUtils.loadRecipe("ReactorRulesRecipes.StepVerifierVerifyRecipe");
        rewriteRun(
                spec -> spec.recipe(recipe)
                        .expectedCyclesThatMakeChanges(1).cycles(1)
                        .parser(JavaParser.fromJavaVersion().classpath(JavaParser.runtimeClasspath())),
                java(
                        """
                                import reactor.core.publisher.Mono;
                                import reactor.test.StepVerifier;
                                
                                import java.util.function.Function;
                                
                                class Test {
                                    Object test() {
                                        return Mono.empty().as(StepVerifier::create).expectError().verifyThenAssertThat();
                                    }
                                }
                                """,
                        """
                                import reactor.core.publisher.Mono;
                                import reactor.test.StepVerifier;
                                
                                import java.util.function.Function;
                                
                                class Test {
                                    Object test() {
                                        return Mono.empty().as(StepVerifier::create).expectError().verify();
                                    }
                                }
                                """
                ));
    }

    @Test
    void testStepVerifierVerifyDurationRecipe() {
        Recipe recipe = RewriteUtils.loadRecipe("ReactorRulesRecipes.StepVerifierVerifyDurationRecipe");
        rewriteRun(
                spec -> spec.recipe(recipe)
                        .expectedCyclesThatMakeChanges(1).cycles(1)
                        .parser(JavaParser.fromJavaVersion().classpath(JavaParser.runtimeClasspath())),
                java(
                        """
                                import reactor.core.publisher.Mono;
                                import reactor.test.StepVerifier;
                                
                                import java.time.Duration;
                                import java.util.function.Function;
                                
                                class Test {
                                    Object test() {
                                        return Mono.empty().as(StepVerifier::create).expectError().verifyThenAssertThat(Duration.ZERO);
                                    }
                                }
                                """,
                        """
                                import reactor.core.publisher.Mono;
                                import reactor.test.StepVerifier;
                                
                                import java.time.Duration;
                                import java.util.function.Function;
                                
                                class Test {
                                    Object test() {
                                        return Mono.empty().as(StepVerifier::create).expectError().verify(Duration.ZERO);
                                    }
                                }
                                """
                ));
    }

    @Test
    void testStepVerifierVerifyLaterRecipe() {
        Recipe recipe = RewriteUtils.loadRecipe("ReactorRulesRecipes.StepVerifierVerifyLaterRecipe");
        rewriteRun(
                spec -> spec.recipe(recipe)
                        .expectedCyclesThatMakeChanges(1).cycles(1)
                        .parser(JavaParser.fromJavaVersion().classpath(JavaParser.runtimeClasspath())),
                java(
                        """
                                import reactor.core.publisher.Mono;
                                import reactor.test.StepVerifier;
                                
                                import java.util.function.Function;
                                
                                class Test {
                                    StepVerifier test() {
                                        return Mono.empty().as(StepVerifier::create).expectError().verifyLater().verifyLater();
                                    }
                                }
                                """,
                        """
                                import reactor.core.publisher.Mono;
                                import reactor.test.StepVerifier;
                                
                                import java.util.function.Function;
                                
                                class Test {
                                    StepVerifier test() {
                                        return Mono.empty().as(StepVerifier::create).expectError().verifyLater();
                                    }
                                }
                                """
                ));
    }
}
