package tech.picnic.errorprone.refasterrules.rewrite;

import org.junit.jupiter.api.Test;
import org.openrewrite.Recipe;
import org.openrewrite.java.JavaParser;
import org.openrewrite.test.RewriteTest;

import static org.openrewrite.java.Assertions.java;

final class AssertJStringRulesRecipesTest implements RewriteTest {

    @Test
    void testAbstractStringAssertStringIsEmptyRecipe() {
        Recipe recipe = RewriteUtils.loadRecipe("AssertJStringRulesRecipes.AbstractStringAssertStringIsEmptyRecipe");
        rewriteRun(
                spec -> spec.recipe(recipe)
                        .expectedCyclesThatMakeChanges(1).cycles(1)
                        .parser(JavaParser.fromJavaVersion().classpath(JavaParser.runtimeClasspath())),
                java(
                        """
                                import org.assertj.core.api.Assertions;
                                
                                class Test {
                                    void test() {
                                        Assertions.assertThat("foo").isEqualTo("");
                                    }
                                }
                                """,
                        """
                                import org.assertj.core.api.Assertions;
                                
                                class Test {
                                    void test() {
                                        Assertions.assertThat("foo").isEmpty();
                                    }
                                }
                                """
                ));
    }

    @Test
    void testAbstractStringAssertStringIsNotEmptyRecipe() {
        Recipe recipe = RewriteUtils.loadRecipe("AssertJStringRulesRecipes.AbstractStringAssertStringIsNotEmptyRecipe");
        rewriteRun(
                spec -> spec.recipe(recipe)
                        .expectedCyclesThatMakeChanges(1).cycles(1)
                        .parser(JavaParser.fromJavaVersion().classpath(JavaParser.runtimeClasspath())),
                java(
                        """
                                import org.assertj.core.api.AbstractStringAssert;
                                import org.assertj.core.api.Assertions;
                                
                                class Test {
                                    AbstractStringAssert<?> test() {
                                        return Assertions.assertThat("foo").isNotEqualTo("");
                                    }
                                }
                                """,
                        """
                                import org.assertj.core.api.AbstractStringAssert;
                                import org.assertj.core.api.Assertions;
                                
                                class Test {
                                    AbstractStringAssert<?> test() {
                                        return Assertions.assertThat("foo").isNotEmpty();
                                    }
                                }
                                """
                ));
    }

    @Test
    void testAssertThatDoesNotMatchRecipe() {
        Recipe recipe = RewriteUtils.loadRecipe("AssertJStringRulesRecipes.AssertThatDoesNotMatchRecipe");
        rewriteRun(
                spec -> spec.recipe(recipe)
                        .expectedCyclesThatMakeChanges(1).cycles(1)
                        .parser(JavaParser.fromJavaVersion().classpath(JavaParser.runtimeClasspath())),
                java(
                        """
                                import org.assertj.core.api.AbstractAssert;
                                import org.assertj.core.api.Assertions;
                                
                                class Test {
                                    AbstractAssert<?, ?> test() {
                                        return Assertions.assertThat("foo".matches(".*")).isFalse();
                                    }
                                }
                                """,
                        """
                                import org.assertj.core.api.AbstractAssert;
                                import org.assertj.core.api.Assertions;
                                
                                class Test {
                                    AbstractAssert<?, ?> test() {
                                        return Assertions.assertThat("foo").doesNotMatch(".*");
                                    }
                                }
                                """
                ));
    }

    @Test
    void testAssertThatMatchesRecipe() {
        Recipe recipe = RewriteUtils.loadRecipe("AssertJStringRulesRecipes.AssertThatMatchesRecipe");
        rewriteRun(
                spec -> spec.recipe(recipe)
                        .expectedCyclesThatMakeChanges(1).cycles(1)
                        .parser(JavaParser.fromJavaVersion().classpath(JavaParser.runtimeClasspath())),
                java(
                        """
                                import org.assertj.core.api.AbstractAssert;
                                import org.assertj.core.api.Assertions;
                                
                                class Test {
                                    AbstractAssert<?, ?> test() {
                                        return Assertions.assertThat("foo".matches(".*")).isTrue();
                                    }
                                }
                                """,
                        """
                                import org.assertj.core.api.AbstractAssert;
                                import org.assertj.core.api.Assertions;
                                
                                class Test {
                                    AbstractAssert<?, ?> test() {
                                        return Assertions.assertThat("foo").matches(".*");
                                    }
                                }
                                """
                ));
    }

    @Test
    void testAssertThatPathContentRecipe() {
        Recipe recipe = RewriteUtils.loadRecipe("AssertJStringRulesRecipes.AssertThatPathContentRecipe");
        rewriteRun(
                spec -> spec.recipe(recipe)
                        .expectedCyclesThatMakeChanges(1).cycles(1)
                        .parser(JavaParser.fromJavaVersion().classpath(JavaParser.runtimeClasspath())),
                java(
                        """
                                import org.assertj.core.api.AbstractStringAssert;
                                import org.assertj.core.api.Assertions;
                                
                                import java.io.IOException;
                                import java.nio.charset.Charset;
                                import java.nio.file.Files;
                                import java.nio.file.Paths;
                                
                                class Test {
                                    AbstractStringAssert<?> test() throws IOException {
                                        return Assertions.assertThat(Files.readString(Paths.get(""), Charset.defaultCharset()));
                                    }
                                }
                                """,
                        """
                                import org.assertj.core.api.AbstractStringAssert;
                                import org.assertj.core.api.Assertions;
                                
                                import java.io.IOException;
                                import java.nio.charset.Charset;
                                import java.nio.file.Paths;
                                
                                class Test {
                                    AbstractStringAssert<?> test() throws IOException {
                                        return Assertions.assertThat(Paths.get("")).content(Charset.defaultCharset());
                                    }
                                }
                                """
                ));
    }

    @Test
    void testAssertThatPathContentUtf8Recipe() {
        Recipe recipe = RewriteUtils.loadRecipe("AssertJStringRulesRecipes.AssertThatPathContentUtf8Recipe");
        rewriteRun(
                spec -> spec.recipe(recipe)
                        .expectedCyclesThatMakeChanges(1).cycles(1)
                        .parser(JavaParser.fromJavaVersion().classpath(JavaParser.runtimeClasspath())),
                java(
                        """
                                import org.assertj.core.api.AbstractStringAssert;
                                import org.assertj.core.api.Assertions;
                                
                                import java.io.IOException;
                                import java.nio.file.Files;
                                import java.nio.file.Paths;
                                
                                class Test {
                                    AbstractStringAssert<?> test() throws IOException {
                                        return Assertions.assertThat(Files.readString(Paths.get("")));
                                    }
                                }
                                """,
                        """
                                import org.assertj.core.api.AbstractStringAssert;
                                import org.assertj.core.api.Assertions;
                                
                                import java.io.IOException;
                                import java.nio.charset.Charset;
                                import java.nio.charset.StandardCharsets;
                                import java.nio.file.Paths;
                                
                                class Test {
                                    AbstractStringAssert<?> test() throws IOException {
                                        return Assertions.assertThat(Paths.get("")).content(StandardCharsets.UTF_8);
                                    }
                                }
                                """
                ));
    }

    @Test
    void testAssertThatStringContainsRecipe() {
        Recipe recipe = RewriteUtils.loadRecipe("AssertJStringRulesRecipes.AssertThatStringContainsRecipe");
        rewriteRun(
                spec -> spec.recipe(recipe)
                        .expectedCyclesThatMakeChanges(1).cycles(1)
                        .parser(JavaParser.fromJavaVersion().classpath(JavaParser.runtimeClasspath())),
                java(
                        """
                                import org.assertj.core.api.AbstractAssert;
                                import org.assertj.core.api.Assertions;
                                
                                class Test {
                                    AbstractAssert<?, ?> test() {
                                        return Assertions.assertThat("foo".contains("bar")).isTrue();
                                    }
                                }
                                """,
                        """
                                import org.assertj.core.api.AbstractAssert;
                                import org.assertj.core.api.Assertions;
                                
                                class Test {
                                    AbstractAssert<?, ?> test() {
                                        return Assertions.assertThat("foo").contains("bar");
                                    }
                                }
                                """
                ));
    }

    @Test
    void testAssertThatStringDoesNotContainRecipe() {
        Recipe recipe = RewriteUtils.loadRecipe("AssertJStringRulesRecipes.AssertThatStringDoesNotContainRecipe");
        rewriteRun(
                spec -> spec.recipe(recipe)
                        .expectedCyclesThatMakeChanges(1).cycles(1)
                        .parser(JavaParser.fromJavaVersion().classpath(JavaParser.runtimeClasspath())),
                java(
                        """
                                import org.assertj.core.api.AbstractAssert;
                                import org.assertj.core.api.Assertions;
                                
                                class Test {
                                    AbstractAssert<?, ?> test() {
                                        return Assertions.assertThat("foo".contains("bar")).isFalse();
                                    }
                                }
                                """,
                        """
                                import org.assertj.core.api.AbstractAssert;
                                import org.assertj.core.api.Assertions;
                                
                                class Test {
                                    AbstractAssert<?, ?> test() {
                                        return Assertions.assertThat("foo").doesNotContain("bar");
                                    }
                                }
                                """
                ));
    }
}
