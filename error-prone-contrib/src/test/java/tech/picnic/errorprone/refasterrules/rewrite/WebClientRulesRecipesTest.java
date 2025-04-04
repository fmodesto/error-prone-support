package tech.picnic.errorprone.refasterrules.rewrite;

import org.junit.jupiter.api.Test;
import org.openrewrite.Recipe;
import org.openrewrite.java.JavaParser;
import org.openrewrite.test.RewriteTest;

import static org.openrewrite.java.Assertions.java;

final class WebClientRulesRecipesTest implements RewriteTest {

    @Test
    void testBodyValueRecipe() {
        Recipe recipe = RewriteUtils.loadRecipe("WebClientRulesRecipes.BodyValueRecipe");
        rewriteRun(
                spec -> spec.recipe(recipe)
                        .expectedCyclesThatMakeChanges(1).cycles(1)
                        .parser(JavaParser.fromJavaVersion().classpath(JavaParser.runtimeClasspath())),
                java(
                        """
                                import com.google.common.collect.ImmutableSet;
                                import org.springframework.test.web.reactive.server.WebTestClient;
                                import org.springframework.web.reactive.function.BodyInserters;
                                import org.springframework.web.reactive.function.client.WebClient;
                                
                                class Test {
                                    ImmutableSet<?> test() {
                                        return ImmutableSet.of(WebClient.create().post().body(BodyInserters.fromValue("bar")), WebTestClient.bindToServer().build().post().body(BodyInserters.fromValue("bar")));
                                    }
                                }
                                """,
                        """
                                import com.google.common.collect.ImmutableSet;
                                import org.springframework.test.web.reactive.server.WebTestClient;
                                import org.springframework.web.reactive.function.client.WebClient;
                                
                                class Test {
                                    ImmutableSet<?> test() {
                                        return ImmutableSet.of(WebClient.create().post().bodyValue("bar"), WebTestClient.bindToServer().build().post().bodyValue("bar"));
                                    }
                                }
                                """
                ));
    }

    @Test
    void testRequestHeadersUriSpecUriRecipe() {
        Recipe recipe = RewriteUtils.loadRecipe("WebClientRulesRecipes.RequestHeadersUriSpecUriRecipe");
        rewriteRun(
                spec -> spec.recipe(recipe)
                        .expectedCyclesThatMakeChanges(1).cycles(1)
                        .parser(JavaParser.fromJavaVersion().classpath(JavaParser.runtimeClasspath())),
                java(
                        """
                                import com.google.common.collect.ImmutableSet;
                                import org.springframework.test.web.reactive.server.WebTestClient;
                                import org.springframework.web.reactive.function.client.WebClient;
                                
                                class Test {
                                    ImmutableSet<?> test() {
                                        return ImmutableSet.of(WebClient.create("foo").get().uri(uriBuilder -> uriBuilder.path("/bar").build()), WebClient.create("bar").post().uri(uriBuilder -> uriBuilder.path("/bar/{baz}").build("quux")), WebTestClient.bindToServer().build().get().uri(uriBuilder -> uriBuilder.path("/baz").build()), WebTestClient.bindToServer().build().post().uri(uriBuilder -> uriBuilder.path("/qux/{quux}/{quuz}").build("corge", "grault")));
                                    }
                                }
                                """,
                        """
                                import com.google.common.collect.ImmutableSet;
                                import org.springframework.test.web.reactive.server.WebTestClient;
                                import org.springframework.web.reactive.function.client.WebClient;
                                
                                class Test {
                                    ImmutableSet<?> test() {
                                        return ImmutableSet.of(WebClient.create("foo").get().uri("/bar"), WebClient.create("bar").post().uri("/bar/{baz}", "quux"), WebTestClient.bindToServer().build().get().uri("/baz"), WebTestClient.bindToServer().build().post().uri("/qux/{quux}/{quuz}", "corge", "grault"));
                                    }
                                }
                                """
                ));
    }

    @Test
    void testWebClientGetRecipe() {
        Recipe recipe = RewriteUtils.loadRecipe("WebClientRulesRecipes.WebClientGetRecipe");
        rewriteRun(
                spec -> spec.recipe(recipe)
                        .expectedCyclesThatMakeChanges(1).cycles(1)
                        .parser(JavaParser.fromJavaVersion().classpath(JavaParser.runtimeClasspath())),
                java(
                        """
                                import com.google.common.collect.ImmutableSet;
                                import org.springframework.http.HttpMethod;
                                import org.springframework.test.web.reactive.server.WebTestClient;
                                import org.springframework.web.reactive.function.client.WebClient;
                                
                                class Test {
                                    ImmutableSet<?> test() {
                                        return ImmutableSet.of(WebClient.create().method(HttpMethod.GET), WebTestClient.bindToServer().build().method(HttpMethod.GET));
                                    }
                                }
                                """,
                        """
                                import com.google.common.collect.ImmutableSet;
                                import org.springframework.test.web.reactive.server.WebTestClient;
                                import org.springframework.web.reactive.function.client.WebClient;
                                
                                class Test {
                                    ImmutableSet<?> test() {
                                        return ImmutableSet.of(WebClient.create().get(), WebTestClient.bindToServer().build().get());
                                    }
                                }
                                """
                ));
    }

    @Test
    void testWebClientHeadRecipe() {
        Recipe recipe = RewriteUtils.loadRecipe("WebClientRulesRecipes.WebClientHeadRecipe");
        rewriteRun(
                spec -> spec.recipe(recipe)
                        .expectedCyclesThatMakeChanges(1).cycles(1)
                        .parser(JavaParser.fromJavaVersion().classpath(JavaParser.runtimeClasspath())),
                java(
                        """
                                import com.google.common.collect.ImmutableSet;
                                import org.springframework.http.HttpMethod;
                                import org.springframework.test.web.reactive.server.WebTestClient;
                                import org.springframework.web.reactive.function.client.WebClient;
                                
                                class Test {
                                    ImmutableSet<?> test() {
                                        return ImmutableSet.of(WebClient.create().method(HttpMethod.HEAD), WebTestClient.bindToServer().build().method(HttpMethod.HEAD));
                                    }
                                }
                                """,
                        """
                                import com.google.common.collect.ImmutableSet;
                                import org.springframework.test.web.reactive.server.WebTestClient;
                                import org.springframework.web.reactive.function.client.WebClient;
                                
                                class Test {
                                    ImmutableSet<?> test() {
                                        return ImmutableSet.of(WebClient.create().head(), WebTestClient.bindToServer().build().head());
                                    }
                                }
                                """
                ));
    }

    @Test
    void testWebClientOptionsRecipe() {
        Recipe recipe = RewriteUtils.loadRecipe("WebClientRulesRecipes.WebClientOptionsRecipe");
        rewriteRun(
                spec -> spec.recipe(recipe)
                        .expectedCyclesThatMakeChanges(1).cycles(1)
                        .parser(JavaParser.fromJavaVersion().classpath(JavaParser.runtimeClasspath())),
                java(
                        """
                                import com.google.common.collect.ImmutableSet;
                                import org.springframework.http.HttpMethod;
                                import org.springframework.test.web.reactive.server.WebTestClient;
                                import org.springframework.web.reactive.function.client.WebClient;
                                
                                class Test {
                                    ImmutableSet<?> test() {
                                        return ImmutableSet.of(WebClient.create().method(HttpMethod.OPTIONS), WebTestClient.bindToServer().build().method(HttpMethod.OPTIONS));
                                    }
                                }
                                """,
                        """
                                import com.google.common.collect.ImmutableSet;
                                import org.springframework.test.web.reactive.server.WebTestClient;
                                import org.springframework.web.reactive.function.client.WebClient;
                                
                                class Test {
                                    ImmutableSet<?> test() {
                                        return ImmutableSet.of(WebClient.create().options(), WebTestClient.bindToServer().build().options());
                                    }
                                }
                                """
                ));
    }

    @Test
    void testWebClientPatchRecipe() {
        Recipe recipe = RewriteUtils.loadRecipe("WebClientRulesRecipes.WebClientPatchRecipe");
        rewriteRun(
                spec -> spec.recipe(recipe)
                        .expectedCyclesThatMakeChanges(1).cycles(1)
                        .parser(JavaParser.fromJavaVersion().classpath(JavaParser.runtimeClasspath())),
                java(
                        """
                                import com.google.common.collect.ImmutableSet;
                                import org.springframework.http.HttpMethod;
                                import org.springframework.test.web.reactive.server.WebTestClient;
                                import org.springframework.web.reactive.function.client.WebClient;
                                
                                class Test {
                                    ImmutableSet<?> test() {
                                        return ImmutableSet.of(WebClient.create().method(HttpMethod.PATCH), WebTestClient.bindToServer().build().method(HttpMethod.PATCH));
                                    }
                                }
                                """,
                        """
                                import com.google.common.collect.ImmutableSet;
                                import org.springframework.test.web.reactive.server.WebTestClient;
                                import org.springframework.web.reactive.function.client.WebClient;
                                
                                class Test {
                                    ImmutableSet<?> test() {
                                        return ImmutableSet.of(WebClient.create().patch(), WebTestClient.bindToServer().build().patch());
                                    }
                                }
                                """
                ));
    }

    @Test
    void testWebClientPostRecipe() {
        Recipe recipe = RewriteUtils.loadRecipe("WebClientRulesRecipes.WebClientPostRecipe");
        rewriteRun(
                spec -> spec.recipe(recipe)
                        .expectedCyclesThatMakeChanges(1).cycles(1)
                        .parser(JavaParser.fromJavaVersion().classpath(JavaParser.runtimeClasspath())),
                java(
                        """
                                import com.google.common.collect.ImmutableSet;
                                import org.springframework.http.HttpMethod;
                                import org.springframework.test.web.reactive.server.WebTestClient;
                                import org.springframework.web.reactive.function.client.WebClient;
                                
                                class Test {
                                    ImmutableSet<?> test() {
                                        return ImmutableSet.of(WebClient.create().method(HttpMethod.POST), WebTestClient.bindToServer().build().method(HttpMethod.POST));
                                    }
                                }
                                """,
                        """
                                import com.google.common.collect.ImmutableSet;
                                import org.springframework.test.web.reactive.server.WebTestClient;
                                import org.springframework.web.reactive.function.client.WebClient;
                                
                                class Test {
                                    ImmutableSet<?> test() {
                                        return ImmutableSet.of(WebClient.create().post(), WebTestClient.bindToServer().build().post());
                                    }
                                }
                                """
                ));
    }

    @Test
    void testWebClientPutRecipe() {
        Recipe recipe = RewriteUtils.loadRecipe("WebClientRulesRecipes.WebClientPutRecipe");
        rewriteRun(
                spec -> spec.recipe(recipe)
                        .expectedCyclesThatMakeChanges(1).cycles(1)
                        .parser(JavaParser.fromJavaVersion().classpath(JavaParser.runtimeClasspath())),
                java(
                        """
                                import com.google.common.collect.ImmutableSet;
                                import org.springframework.http.HttpMethod;
                                import org.springframework.test.web.reactive.server.WebTestClient;
                                import org.springframework.web.reactive.function.client.WebClient;
                                
                                class Test {
                                    ImmutableSet<?> test() {
                                        return ImmutableSet.of(WebClient.create().method(HttpMethod.PUT), WebTestClient.bindToServer().build().method(HttpMethod.PUT));
                                    }
                                }
                                """,
                        """
                                import com.google.common.collect.ImmutableSet;
                                import org.springframework.test.web.reactive.server.WebTestClient;
                                import org.springframework.web.reactive.function.client.WebClient;
                                
                                class Test {
                                    ImmutableSet<?> test() {
                                        return ImmutableSet.of(WebClient.create().put(), WebTestClient.bindToServer().build().put());
                                    }
                                }
                                """
                ));
    }
}
