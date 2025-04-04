package tech.picnic.errorprone.refasterrules.rewrite;

import org.junit.jupiter.api.Test;
import org.openrewrite.Recipe;
import org.openrewrite.java.JavaParser;
import org.openrewrite.test.RewriteTest;

import static org.openrewrite.java.Assertions.java;

final class RxJava2AdapterRulesRecipesTest implements RewriteTest {

    @Test
    void testCompletableToMonoRecipe() {
        Recipe recipe = RewriteUtils.loadRecipe("RxJava2AdapterRulesRecipes.CompletableToMonoRecipe");
        rewriteRun(
                spec -> spec.recipe(recipe)
                        .expectedCyclesThatMakeChanges(1).cycles(1)
                        .parser(JavaParser.fromJavaVersion().classpath(JavaParser.runtimeClasspath())),
                java(
                        """
                                import com.google.common.collect.ImmutableSet;
                                import io.reactivex.Completable;
                                import reactor.adapter.rxjava.RxJava2Adapter;
                                import reactor.core.publisher.Mono;
                                
                                class Test {
                                    ImmutableSet<Mono<Void>> test() {
                                        return ImmutableSet.of(RxJava2Adapter.completableToMono(Completable.complete()), Completable.complete().to(RxJava2Adapter::completableToMono));
                                    }
                                }
                                """,
                        """
                                import com.google.common.collect.ImmutableSet;
                                import io.reactivex.Completable;
                                import reactor.adapter.rxjava.RxJava2Adapter;
                                import reactor.core.publisher.Mono;
                                
                                class Test {
                                    ImmutableSet<Mono<Void>> test() {
                                        return ImmutableSet.of(Completable.complete().as(RxJava2Adapter::completableToMono), Completable.complete().as(RxJava2Adapter::completableToMono));
                                    }
                                }
                                """
                ));
    }

    @Test
    void testFlowableToFluxRecipe() {
        Recipe recipe = RewriteUtils.loadRecipe("RxJava2AdapterRulesRecipes.FlowableToFluxRecipe");
        rewriteRun(
                spec -> spec.recipe(recipe)
                        .expectedCyclesThatMakeChanges(1).cycles(1)
                        .parser(JavaParser.fromJavaVersion().classpath(JavaParser.runtimeClasspath())),
                java(
                        """
                                import com.google.common.collect.ImmutableSet;
                                import io.reactivex.Flowable;
                                import reactor.adapter.rxjava.RxJava2Adapter;
                                import reactor.core.publisher.Flux;
                                
                                class Test {
                                    ImmutableSet<Flux<Integer>> test() {
                                        return ImmutableSet.of(Flux.from(Flowable.just(1)), Flowable.just(2).to(Flux::from), Flowable.just(3).as(Flux::from), RxJava2Adapter.flowableToFlux(Flowable.just(4)), Flowable.just(5).to(RxJava2Adapter::flowableToFlux));
                                    }
                                }
                                """,
                        """
                                import com.google.common.collect.ImmutableSet;
                                import io.reactivex.Flowable;
                                import reactor.adapter.rxjava.RxJava2Adapter;
                                import reactor.core.publisher.Flux;
                                
                                class Test {
                                    ImmutableSet<Flux<Integer>> test() {
                                        return ImmutableSet.of(Flowable.just(1).as(RxJava2Adapter::flowableToFlux), Flowable.just(2).as(RxJava2Adapter::flowableToFlux), Flowable.just(3).as(RxJava2Adapter::flowableToFlux), Flowable.just(4).as(RxJava2Adapter::flowableToFlux), Flowable.just(5).as(RxJava2Adapter::flowableToFlux));
                                    }
                                }
                                """
                ));
    }

    @Test
    void testFluxToFlowableRecipe() {
        Recipe recipe = RewriteUtils.loadRecipe("RxJava2AdapterRulesRecipes.FluxToFlowableRecipe");
        rewriteRun(
                spec -> spec.recipe(recipe)
                        .expectedCyclesThatMakeChanges(1).cycles(1)
                        .parser(JavaParser.fromJavaVersion().classpath(JavaParser.runtimeClasspath())),
                java(
                        """
                                import com.google.common.collect.ImmutableSet;
                                import io.reactivex.Flowable;
                                import reactor.adapter.rxjava.RxJava2Adapter;
                                import reactor.core.publisher.Flux;
                                
                                class Test {
                                    ImmutableSet<Flowable<String>> test() {
                                        return ImmutableSet.of(Flowable.fromPublisher(Flux.just("foo")), Flux.just("bar").as(Flowable::fromPublisher), RxJava2Adapter.fluxToFlowable(Flux.just("baz")));
                                    }
                                }
                                """,
                        """
                                import com.google.common.collect.ImmutableSet;
                                import io.reactivex.Flowable;
                                import reactor.adapter.rxjava.RxJava2Adapter;
                                import reactor.core.publisher.Flux;
                                
                                class Test {
                                    ImmutableSet<Flowable<String>> test() {
                                        return ImmutableSet.of(Flux.just("foo").as(RxJava2Adapter::fluxToFlowable), Flux.just("bar").as(RxJava2Adapter::fluxToFlowable), Flux.just("baz").as(RxJava2Adapter::fluxToFlowable));
                                    }
                                }
                                """
                ));
    }

    @Test
    void testFluxToObservableRecipe() {
        Recipe recipe = RewriteUtils.loadRecipe("RxJava2AdapterRulesRecipes.FluxToObservableRecipe");
        rewriteRun(
                spec -> spec.recipe(recipe)
                        .expectedCyclesThatMakeChanges(1).cycles(1)
                        .parser(JavaParser.fromJavaVersion().classpath(JavaParser.runtimeClasspath())),
                java(
                        """
                                import com.google.common.collect.ImmutableSet;
                                import io.reactivex.Observable;
                                import reactor.adapter.rxjava.RxJava2Adapter;
                                import reactor.core.publisher.Flux;
                                
                                class Test {
                                    ImmutableSet<Observable<Integer>> test() {
                                        return ImmutableSet.of(Observable.fromPublisher(Flux.just(1)), Flux.just(2).as(Observable::fromPublisher), RxJava2Adapter.fluxToObservable(Flux.just(3)));
                                    }
                                }
                                """,
                        """
                                import com.google.common.collect.ImmutableSet;
                                import io.reactivex.Observable;
                                import reactor.adapter.rxjava.RxJava2Adapter;
                                import reactor.core.publisher.Flux;
                                
                                class Test {
                                    ImmutableSet<Observable<Integer>> test() {
                                        return ImmutableSet.of(Flux.just(1).as(RxJava2Adapter::fluxToObservable), Flux.just(2).as(RxJava2Adapter::fluxToObservable), Flux.just(3).as(RxJava2Adapter::fluxToObservable));
                                    }
                                }
                                """
                ));
    }

    @Test
    void testMaybeToMonoRecipe() {
        Recipe recipe = RewriteUtils.loadRecipe("RxJava2AdapterRulesRecipes.MaybeToMonoRecipe");
        rewriteRun(
                spec -> spec.recipe(recipe)
                        .expectedCyclesThatMakeChanges(1).cycles(1)
                        .parser(JavaParser.fromJavaVersion().classpath(JavaParser.runtimeClasspath())),
                java(
                        """
                                import com.google.common.collect.ImmutableSet;
                                import io.reactivex.Maybe;
                                import reactor.adapter.rxjava.RxJava2Adapter;
                                import reactor.core.publisher.Mono;
                                
                                class Test {
                                    ImmutableSet<Mono<String>> test() {
                                        return ImmutableSet.of(RxJava2Adapter.maybeToMono(Maybe.just("foo")), Maybe.just("bar").to(RxJava2Adapter::maybeToMono));
                                    }
                                }
                                """,
                        """
                                import com.google.common.collect.ImmutableSet;
                                import io.reactivex.Maybe;
                                import reactor.adapter.rxjava.RxJava2Adapter;
                                import reactor.core.publisher.Mono;
                                
                                class Test {
                                    ImmutableSet<Mono<String>> test() {
                                        return ImmutableSet.of(Maybe.just("foo").as(RxJava2Adapter::maybeToMono), Maybe.just("bar").as(RxJava2Adapter::maybeToMono));
                                    }
                                }
                                """
                ));
    }

    @Test
    void testMonoToCompletableRecipe() {
        Recipe recipe = RewriteUtils.loadRecipe("RxJava2AdapterRulesRecipes.MonoToCompletableRecipe");
        rewriteRun(
                spec -> spec.recipe(recipe)
                        .expectedCyclesThatMakeChanges(1).cycles(1)
                        .parser(JavaParser.fromJavaVersion().classpath(JavaParser.runtimeClasspath())),
                java(
                        """
                                import com.google.common.collect.ImmutableSet;
                                import io.reactivex.Completable;
                                import reactor.adapter.rxjava.RxJava2Adapter;
                                import reactor.core.publisher.Mono;
                                
                                class Test {
                                    ImmutableSet<Completable> test() {
                                        return ImmutableSet.of(Completable.fromPublisher(Mono.empty()), Mono.empty().as(Completable::fromPublisher), RxJava2Adapter.monoToCompletable(Mono.empty()));
                                    }
                                }
                                """,
                        """
                                import com.google.common.collect.ImmutableSet;
                                import io.reactivex.Completable;
                                import reactor.adapter.rxjava.RxJava2Adapter;
                                import reactor.core.publisher.Mono;
                                
                                class Test {
                                    ImmutableSet<Completable> test() {
                                        return ImmutableSet.of(Mono.empty().as(RxJava2Adapter::monoToCompletable), Mono.empty().as(RxJava2Adapter::monoToCompletable), Mono.empty().as(RxJava2Adapter::monoToCompletable));
                                    }
                                }
                                """
                ));
    }

    @Test
    void testMonoToFlowableRecipe() {
        Recipe recipe = RewriteUtils.loadRecipe("RxJava2AdapterRulesRecipes.MonoToFlowableRecipe");
        rewriteRun(
                spec -> spec.recipe(recipe)
                        .expectedCyclesThatMakeChanges(1).cycles(1)
                        .parser(JavaParser.fromJavaVersion().classpath(JavaParser.runtimeClasspath())),
                java(
                        """
                                import com.google.common.collect.ImmutableSet;
                                import io.reactivex.Flowable;
                                import reactor.adapter.rxjava.RxJava2Adapter;
                                import reactor.core.publisher.Mono;
                                
                                class Test {
                                    ImmutableSet<Flowable<Integer>> test() {
                                        return ImmutableSet.of(Flowable.fromPublisher(Mono.just(1)), Mono.just(2).as(Flowable::fromPublisher), RxJava2Adapter.monoToFlowable(Mono.just(3)));
                                    }
                                }
                                """,
                        """
                                import com.google.common.collect.ImmutableSet;
                                import io.reactivex.Flowable;
                                import reactor.adapter.rxjava.RxJava2Adapter;
                                import reactor.core.publisher.Mono;
                                
                                class Test {
                                    ImmutableSet<Flowable<Integer>> test() {
                                        return ImmutableSet.of(Mono.just(1).as(RxJava2Adapter::monoToFlowable), Mono.just(2).as(RxJava2Adapter::monoToFlowable), Mono.just(3).as(RxJava2Adapter::monoToFlowable));
                                    }
                                }
                                """
                ));
    }

    @Test
    void testMonoToMaybeRecipe() {
        Recipe recipe = RewriteUtils.loadRecipe("RxJava2AdapterRulesRecipes.MonoToMaybeRecipe");
        rewriteRun(
                spec -> spec.recipe(recipe)
                        .expectedCyclesThatMakeChanges(1).cycles(1)
                        .parser(JavaParser.fromJavaVersion().classpath(JavaParser.runtimeClasspath())),
                java(
                        """
                                import io.reactivex.Maybe;
                                import reactor.adapter.rxjava.RxJava2Adapter;
                                import reactor.core.publisher.Mono;
                                
                                class Test {
                                    Maybe<String> test() {
                                        return RxJava2Adapter.monoToMaybe(Mono.just("foo"));
                                    }
                                }
                                """,
                        """
                                import io.reactivex.Maybe;
                                import reactor.adapter.rxjava.RxJava2Adapter;
                                import reactor.core.publisher.Mono;
                                
                                class Test {
                                    Maybe<String> test() {
                                        return Mono.just("foo").as(RxJava2Adapter::monoToMaybe);
                                    }
                                }
                                """
                ));
    }

    @Test
    void testMonoToSingleRecipe() {
        Recipe recipe = RewriteUtils.loadRecipe("RxJava2AdapterRulesRecipes.MonoToSingleRecipe");
        rewriteRun(
                spec -> spec.recipe(recipe)
                        .expectedCyclesThatMakeChanges(1).cycles(1)
                        .parser(JavaParser.fromJavaVersion().classpath(JavaParser.runtimeClasspath())),
                java(
                        """
                                import com.google.common.collect.ImmutableSet;
                                import io.reactivex.Single;
                                import reactor.adapter.rxjava.RxJava2Adapter;
                                import reactor.core.publisher.Mono;
                                
                                class Test {
                                    ImmutableSet<Single<Integer>> test() {
                                        return ImmutableSet.of(Single.fromPublisher(Mono.just(1)), Mono.just(2).as(Single::fromPublisher), RxJava2Adapter.monoToSingle(Mono.just(3)));
                                    }
                                }
                                """,
                        """
                                import com.google.common.collect.ImmutableSet;
                                import io.reactivex.Single;
                                import reactor.adapter.rxjava.RxJava2Adapter;
                                import reactor.core.publisher.Mono;
                                
                                class Test {
                                    ImmutableSet<Single<Integer>> test() {
                                        return ImmutableSet.of(Mono.just(1).as(RxJava2Adapter::monoToSingle), Mono.just(2).as(RxJava2Adapter::monoToSingle), Mono.just(3).as(RxJava2Adapter::monoToSingle));
                                    }
                                }
                                """
                ));
    }

    @Test
    void testObservableToFluxRecipe() {
        Recipe recipe = RewriteUtils.loadRecipe("RxJava2AdapterRulesRecipes.ObservableToFluxRecipe");
        rewriteRun(
                spec -> spec.recipe(recipe)
                        .expectedCyclesThatMakeChanges(1).cycles(1)
                        .parser(JavaParser.fromJavaVersion().classpath(JavaParser.runtimeClasspath())),
                java(
                        """
                                import com.google.common.collect.ImmutableSet;
                                import io.reactivex.BackpressureStrategy;
                                import io.reactivex.Observable;
                                import reactor.adapter.rxjava.RxJava2Adapter;
                                import reactor.core.publisher.Flux;
                                
                                class Test {
                                    ImmutableSet<Flux<String>> test() {
                                        return ImmutableSet.of(RxJava2Adapter.observableToFlux(Observable.just("foo"), BackpressureStrategy.BUFFER), Observable.just("bar").as(obs -> RxJava2Adapter.observableToFlux(obs, BackpressureStrategy.DROP)), Observable.just("baz").to(obs -> RxJava2Adapter.observableToFlux(obs, BackpressureStrategy.ERROR)));
                                    }
                                }
                                """,
                        """
                                import com.google.common.collect.ImmutableSet;
                                import io.reactivex.BackpressureStrategy;
                                import io.reactivex.Observable;
                                import reactor.adapter.rxjava.RxJava2Adapter;
                                import reactor.core.publisher.Flux;
                                
                                class Test {
                                    ImmutableSet<Flux<String>> test() {
                                        return ImmutableSet.of(Observable.just("foo").toFlowable(BackpressureStrategy.BUFFER).as(RxJava2Adapter::flowableToFlux), Observable.just("bar").toFlowable(BackpressureStrategy.DROP).as(RxJava2Adapter::flowableToFlux), Observable.just("baz").toFlowable(BackpressureStrategy.ERROR).as(RxJava2Adapter::flowableToFlux));
                                    }
                                }
                                """
                ));
    }

    @Test
    void testSingleToMonoRecipe() {
        Recipe recipe = RewriteUtils.loadRecipe("RxJava2AdapterRulesRecipes.SingleToMonoRecipe");
        rewriteRun(
                spec -> spec.recipe(recipe)
                        .expectedCyclesThatMakeChanges(1).cycles(1)
                        .parser(JavaParser.fromJavaVersion().classpath(JavaParser.runtimeClasspath())),
                java(
                        """
                                import com.google.common.collect.ImmutableSet;
                                import io.reactivex.Single;
                                import reactor.adapter.rxjava.RxJava2Adapter;
                                import reactor.core.publisher.Mono;
                                
                                class Test {
                                    ImmutableSet<Mono<Integer>> test() {
                                        return ImmutableSet.of(RxJava2Adapter.singleToMono(Single.just(1)), Single.just(2).to(RxJava2Adapter::singleToMono));
                                    }
                                }
                                """,
                        """
                                import com.google.common.collect.ImmutableSet;
                                import io.reactivex.Single;
                                import reactor.adapter.rxjava.RxJava2Adapter;
                                import reactor.core.publisher.Mono;
                                
                                class Test {
                                    ImmutableSet<Mono<Integer>> test() {
                                        return ImmutableSet.of(Single.just(1).as(RxJava2Adapter::singleToMono), Single.just(2).as(RxJava2Adapter::singleToMono));
                                    }
                                }
                                """
                ));
    }
}
