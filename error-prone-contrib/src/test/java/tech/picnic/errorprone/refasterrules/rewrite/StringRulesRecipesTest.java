package tech.picnic.errorprone.refasterrules.rewrite;

import org.junit.jupiter.api.Test;
import org.openrewrite.Recipe;
import org.openrewrite.java.JavaParser;
import org.openrewrite.test.RewriteTest;

import static org.openrewrite.java.Assertions.java;

final class StringRulesRecipesTest implements RewriteTest {

    @Test
    void testFilterEmptyStringRecipe() {
        Recipe recipe = RewriteUtils.loadRecipe("StringRulesRecipes.FilterEmptyStringRecipe");
        rewriteRun(
                spec -> spec.recipe(recipe)
                        .expectedCyclesThatMakeChanges(1).cycles(1)
                        .parser(JavaParser.fromJavaVersion().classpath(JavaParser.runtimeClasspath())),
                java(
                        """
                                import com.google.common.base.Strings;
                                
                                import java.util.Optional;
                                import java.util.function.Function;
                                
                                class Test {
                                    Optional<String> test() {
                                        return Optional.of("foo").map(Strings::emptyToNull);
                                    }
                                }
                                """,
                        """
                                import java.util.Optional;
                                import java.util.function.Predicate;
                                
                                class Test {
                                    Optional<String> test() {
                                        return Optional.of("foo").filter(Predicate.not(String::isEmpty));
                                    }
                                }
                                """
                ));
    }

    @Test
    void testJoinStringsRecipe() {
        Recipe recipe = RewriteUtils.loadRecipe("StringRulesRecipes.JoinStringsRecipe");
        rewriteRun(
                spec -> spec.recipe(recipe)
                        .expectedCyclesThatMakeChanges(1).cycles(1)
                        .parser(JavaParser.fromJavaVersion().classpath(JavaParser.runtimeClasspath())),
                java(
                        """
                                import com.google.common.base.Joiner;
                                import com.google.common.collect.ImmutableList;
                                import com.google.common.collect.ImmutableSet;
                                import com.google.common.collect.Iterables;
                                import com.google.common.collect.Streams;
                                
                                import java.util.Arrays;
                                import java.util.stream.Collectors;
                                
                                class Test {
                                    ImmutableSet<String> test() {
                                        return ImmutableSet.of(Joiner.on("a").join(new String[]{"foo", "bar"}), Joiner.on("b").join(new CharSequence[]{"foo", "bar"}), Arrays.stream(new String[]{"foo", "bar"}).collect(Collectors.joining("c")), Joiner.on("d").join(ImmutableList.of("foo", "bar")), Streams.stream(Iterables.cycle(ImmutableList.of("foo", "bar"))).collect(Collectors.joining("e")), ImmutableList.of("foo", "bar").stream().collect(Collectors.joining("f")));
                                    }
                                }
                                """,
                        """
                                import com.google.common.collect.ImmutableList;
                                import com.google.common.collect.ImmutableSet;
                                import com.google.common.collect.Iterables;
                                
                                class Test {
                                    ImmutableSet<String> test() {
                                        return ImmutableSet.of(String.join("a", new String[]{"foo", "bar"}), String.join("b", new CharSequence[]{"foo", "bar"}), String.join("c", new String[]{"foo", "bar"}), String.join("d", ImmutableList.of("foo", "bar")), String.join("e", Iterables.cycle(ImmutableList.of("foo", "bar"))), String.join("f", ImmutableList.of("foo", "bar")));
                                    }
                                }
                                """
                ));
    }

    @Test
    void testNewStringFromCharArrayRecipe() {
        Recipe recipe = RewriteUtils.loadRecipe("StringRulesRecipes.NewStringFromCharArrayRecipe");
        rewriteRun(
                spec -> spec.recipe(recipe)
                        .expectedCyclesThatMakeChanges(1).cycles(1)
                        .parser(JavaParser.fromJavaVersion().classpath(JavaParser.runtimeClasspath())),
                java(
                        """
                                import com.google.common.collect.ImmutableSet;
                                
                                class Test {
                                    ImmutableSet<String> test() {
                                        return ImmutableSet.of(String.valueOf(new char[]{'f', 'o', 'o'}), new String(new char[]{'b', 'a', 'r'}, 0, new char[]{'b', 'a', 'r'}.length));
                                    }
                                }
                                """,
                        """
                                import com.google.common.collect.ImmutableSet;
                                
                                class Test {
                                    ImmutableSet<String> test() {
                                        return ImmutableSet.of(new String(new char[]{'f', 'o', 'o'}), new String(new char[]{'b', 'a', 'r'}));
                                    }
                                }
                                """
                ));
    }

    @Test
    void testNewStringFromCharArraySubSequenceRecipe() {
        Recipe recipe = RewriteUtils.loadRecipe("StringRulesRecipes.NewStringFromCharArraySubSequenceRecipe");
        rewriteRun(
                spec -> spec.recipe(recipe)
                        .expectedCyclesThatMakeChanges(1).cycles(1)
                        .parser(JavaParser.fromJavaVersion().classpath(JavaParser.runtimeClasspath())),
                java(
                        """
                                import com.google.common.collect.ImmutableSet;
                                
                                class Test {
                                    ImmutableSet<String> test() {
                                        return ImmutableSet.of(String.valueOf(new char[]{'f', 'o', 'o'}, 0, 1), String.copyValueOf(new char[]{'b', 'a', 'r'}, 2, 3));
                                    }
                                }
                                """,
                        """
                                import com.google.common.collect.ImmutableSet;
                                
                                class Test {
                                    ImmutableSet<String> test() {
                                        return ImmutableSet.of(new String(new char[]{'f', 'o', 'o'}, 0, 1), new String(new char[]{'b', 'a', 'r'}, 2, 3));
                                    }
                                }
                                """
                ));
    }

    @Test
    void testOptionalNonEmptyStringRecipe() {
        Recipe recipe = RewriteUtils.loadRecipe("StringRulesRecipes.OptionalNonEmptyStringRecipe");
        rewriteRun(
                spec -> spec.recipe(recipe)
                        .expectedCyclesThatMakeChanges(1).cycles(1)
                        .parser(JavaParser.fromJavaVersion().classpath(JavaParser.runtimeClasspath())),
                java(
                        """
                                import com.google.common.base.Strings;
                                import com.google.common.collect.ImmutableSet;
                                
                                import java.util.Optional;
                                
                                class Test {
                                    ImmutableSet<Optional<String>> test() {
                                        return ImmutableSet.of(Strings.isNullOrEmpty(toString()) ? Optional.empty() : Optional.of(toString()), Strings.isNullOrEmpty(toString()) ? Optional.empty() : Optional.ofNullable(toString()), !Strings.isNullOrEmpty(toString()) ? Optional.of(toString()) : Optional.empty(), !Strings.isNullOrEmpty(toString()) ? Optional.ofNullable(toString()) : Optional.empty());
                                    }
                                }
                                """,
                        """
                                import com.google.common.collect.ImmutableSet;
                                
                                import java.util.Optional;
                                import java.util.function.Predicate;
                                
                                class Test {
                                    ImmutableSet<Optional<String>> test() {
                                        return ImmutableSet.of(Optional.ofNullable(toString()).filter(Predicate.not(String::isEmpty)), Optional.ofNullable(toString()).filter(Predicate.not(String::isEmpty)), Optional.ofNullable(toString()).filter(Predicate.not(String::isEmpty)), Optional.ofNullable(toString()).filter(Predicate.not(String::isEmpty)));
                                    }
                                }
                                """
                ));
    }

    @Test
    void testStringIndexOfCharRecipe() {
        Recipe recipe = RewriteUtils.loadRecipe("StringRulesRecipes.StringIndexOfCharRecipe");
        rewriteRun(
                spec -> spec.recipe(recipe)
                        .expectedCyclesThatMakeChanges(1).cycles(1)
                        .parser(JavaParser.fromJavaVersion().classpath(JavaParser.runtimeClasspath())),
                java(
                        """
                                class Test {
                                    int test() {
                                        return "foo".substring(1).indexOf('a');
                                    }
                                }
                                """,
                        """
                                class Test {
                                    int test() {
                                        return Math.max(-1, "foo".indexOf('a', 1) - 1);
                                    }
                                }
                                """
                ));
    }

    @Test
    void testStringIndexOfStringRecipe() {
        Recipe recipe = RewriteUtils.loadRecipe("StringRulesRecipes.StringIndexOfStringRecipe");
        rewriteRun(
                spec -> spec.recipe(recipe)
                        .expectedCyclesThatMakeChanges(1).cycles(1)
                        .parser(JavaParser.fromJavaVersion().classpath(JavaParser.runtimeClasspath())),
                java(
                        """
                                class Test {
                                    int test() {
                                        return "foo".substring(1).indexOf("bar");
                                    }
                                }
                                """,
                        """
                                class Test {
                                    int test() {
                                        return Math.max(-1, "foo".indexOf("bar", 1) - 1);
                                    }
                                }
                                """
                ));
    }

    @Test
    void testStringIsEmptyRecipe() {
        Recipe recipe = RewriteUtils.loadRecipe("StringRulesRecipes.StringIsEmptyRecipe");
        rewriteRun(
                spec -> spec.recipe(recipe)
                        .expectedCyclesThatMakeChanges(1).cycles(1)
                        .parser(JavaParser.fromJavaVersion().classpath(JavaParser.runtimeClasspath())),
                java(
                        """
                                import com.google.common.collect.ImmutableSet;
                                
                                class Test {
                                    ImmutableSet<Boolean> test() {
                                        return ImmutableSet.of("foo".length() == 0, "bar".length() <= 0, "baz".length() < 1, "qux".length() != 0, "quux".length() > 0, "corge".length() >= 1);
                                    }
                                }
                                """,
                        """
                                import com.google.common.collect.ImmutableSet;
                                
                                class Test {
                                    ImmutableSet<Boolean> test() {
                                        return ImmutableSet.of("foo".isEmpty(), "bar".isEmpty(), "baz".isEmpty(), !"qux".isEmpty(), !"quux".isEmpty(), !"corge".isEmpty());
                                    }
                                }
                                """
                ));
    }

    @Test
    void testStringIsEmptyPredicateRecipe() {
        Recipe recipe = RewriteUtils.loadRecipe("StringRulesRecipes.StringIsEmptyPredicateRecipe");
        rewriteRun(
                spec -> spec.recipe(recipe)
                        .expectedCyclesThatMakeChanges(1).cycles(1)
                        .parser(JavaParser.fromJavaVersion().classpath(JavaParser.runtimeClasspath())),
                java(
                        """
                                import java.util.stream.Stream;
                                
                                class Test {
                                    boolean test() {
                                        return Stream.of("foo").anyMatch(s -> s.isEmpty());
                                    }
                                }
                                """,
                        """
                                import java.util.function.Predicate;
                                import java.util.stream.Stream;
                                
                                class Test {
                                    boolean test() {
                                        return Stream.of("foo").anyMatch(String::isEmpty);
                                    }
                                }
                                """
                ));
    }

    @Test
    void testStringIsNotEmptyPredicateRecipe() {
        Recipe recipe = RewriteUtils.loadRecipe("StringRulesRecipes.StringIsNotEmptyPredicateRecipe");
        rewriteRun(
                spec -> spec.recipe(recipe)
                        .expectedCyclesThatMakeChanges(1).cycles(1)
                        .parser(JavaParser.fromJavaVersion().classpath(JavaParser.runtimeClasspath())),
                java(
                        """
                                import java.util.stream.Stream;
                                
                                class Test {
                                    boolean test() {
                                        return Stream.of("foo").anyMatch(s -> !s.isEmpty());
                                    }
                                }
                                """,
                        """
                                import java.util.function.Predicate;
                                import java.util.stream.Stream;
                                
                                class Test {
                                    boolean test() {
                                        return Stream.of("foo").anyMatch(Predicate.not(String::isEmpty));
                                    }
                                }
                                """
                ));
    }

    @Test
    void testStringIsNullOrEmptyRecipe() {
        Recipe recipe = RewriteUtils.loadRecipe("StringRulesRecipes.StringIsNullOrEmptyRecipe");
        rewriteRun(
                spec -> spec.recipe(recipe)
                        .expectedCyclesThatMakeChanges(1).cycles(1)
                        .parser(JavaParser.fromJavaVersion().classpath(JavaParser.runtimeClasspath())),
                java(
                        """
                                import com.google.common.collect.ImmutableSet;
                                
                                class Test {
                                    ImmutableSet<Boolean> test() {
                                        return ImmutableSet.of(getClass().getName() == null || getClass().getName().isEmpty(), getClass().getName() != null && !getClass().getName().isEmpty());
                                    }
                                }
                                """,
                        """
                                import com.google.common.base.Strings;
                                import com.google.common.collect.ImmutableSet;
                                
                                class Test {
                                    ImmutableSet<Boolean> test() {
                                        return ImmutableSet.of(Strings.isNullOrEmpty(getClass().getName()), !Strings.isNullOrEmpty(getClass().getName()));
                                    }
                                }
                                """
                ));
    }

    @Test
    void testStringLastIndexOfCharRecipe() {
        Recipe recipe = RewriteUtils.loadRecipe("StringRulesRecipes.StringLastIndexOfCharRecipe");
        rewriteRun(
                spec -> spec.recipe(recipe)
                        .expectedCyclesThatMakeChanges(1).cycles(1)
                        .parser(JavaParser.fromJavaVersion().classpath(JavaParser.runtimeClasspath())),
                java(
                        """
                                class Test {
                                    int test() {
                                        return "foo".substring(1).lastIndexOf('a');
                                    }
                                }
                                """,
                        """
                                class Test {
                                    int test() {
                                        return Math.max(-1, "foo".lastIndexOf('a') - 1);
                                    }
                                }
                                """
                ));
    }

    @Test
    void testStringLastIndexOfCharWithIndexRecipe() {
        Recipe recipe = RewriteUtils.loadRecipe("StringRulesRecipes.StringLastIndexOfCharWithIndexRecipe");
        rewriteRun(
                spec -> spec.recipe(recipe)
                        .expectedCyclesThatMakeChanges(1).cycles(1)
                        .parser(JavaParser.fromJavaVersion().classpath(JavaParser.runtimeClasspath())),
                java(
                        """
                                class Test {
                                    int test() {
                                        return "foo".substring(0, 2).lastIndexOf('a');
                                    }
                                }
                                """,
                        """
                                class Test {
                                    int test() {
                                        return "foo".lastIndexOf('a', 2 - 1);
                                    }
                                }
                                """
                ));
    }

    @Test
    void testStringLastIndexOfStringRecipe() {
        Recipe recipe = RewriteUtils.loadRecipe("StringRulesRecipes.StringLastIndexOfStringRecipe");
        rewriteRun(
                spec -> spec.recipe(recipe)
                        .expectedCyclesThatMakeChanges(1).cycles(1)
                        .parser(JavaParser.fromJavaVersion().classpath(JavaParser.runtimeClasspath())),
                java(
                        """
                                class Test {
                                    int test() {
                                        return "foo".substring(1).lastIndexOf("bar");
                                    }
                                }
                                """,
                        """
                                class Test {
                                    int test() {
                                        return Math.max(-1, "foo".lastIndexOf("bar") - 1);
                                    }
                                }
                                """
                ));
    }

    @Test
    void testStringLastIndexOfStringWithIndexRecipe() {
        Recipe recipe = RewriteUtils.loadRecipe("StringRulesRecipes.StringLastIndexOfStringWithIndexRecipe");
        rewriteRun(
                spec -> spec.recipe(recipe)
                        .expectedCyclesThatMakeChanges(1).cycles(1)
                        .parser(JavaParser.fromJavaVersion().classpath(JavaParser.runtimeClasspath())),
                java(
                        """
                                class Test {
                                    int test() {
                                        return "foo".substring(0, 2).lastIndexOf("bar");
                                    }
                                }
                                """,
                        """
                                class Test {
                                    int test() {
                                        return "foo".lastIndexOf("bar", 2 - 1);
                                    }
                                }
                                """
                ));
    }

    @Test
    void testStringStartsWithRecipe() {
        Recipe recipe = RewriteUtils.loadRecipe("StringRulesRecipes.StringStartsWithRecipe");
        rewriteRun(
                spec -> spec.recipe(recipe)
                        .expectedCyclesThatMakeChanges(1).cycles(1)
                        .parser(JavaParser.fromJavaVersion().classpath(JavaParser.runtimeClasspath())),
                java(
                        """
                                class Test {
                                    boolean test() {
                                        return "foo".substring(1).startsWith("bar");
                                    }
                                }
                                """,
                        """
                                class Test {
                                    boolean test() {
                                        return "foo".startsWith("bar", 1);
                                    }
                                }
                                """
                ));
    }

    @Test
    void testStringValueOfRecipe() {
        Recipe recipe = RewriteUtils.loadRecipe("StringRulesRecipes.StringValueOfRecipe");
        rewriteRun(
                spec -> spec.recipe(recipe)
                        .expectedCyclesThatMakeChanges(1).cycles(1)
                        .parser(JavaParser.fromJavaVersion().classpath(JavaParser.runtimeClasspath())),
                java(
                        """
                                import java.util.Objects;
                                
                                class Test {
                                    String test() {
                                        return Objects.toString("foo");
                                    }
                                }
                                """,
                        """
                                class Test {
                                    String test() {
                                        return String.valueOf("foo");
                                    }
                                }
                                """
                ));
    }

    @Test
    void testStringValueOfMethodReferenceRecipe() {
        Recipe recipe = RewriteUtils.loadRecipe("StringRulesRecipes.StringValueOfMethodReferenceRecipe");
        rewriteRun(
                spec -> spec.recipe(recipe)
                        .expectedCyclesThatMakeChanges(1).cycles(1)
                        .parser(JavaParser.fromJavaVersion().classpath(JavaParser.runtimeClasspath())),
                java(
                        """
                                import java.util.Objects;
                                import java.util.function.Function;
                                
                                class Test {
                                    Function<Object, String> test() {
                                        return Objects::toString;
                                    }
                                }
                                """,
                        """
                                import java.util.function.Function;
                                
                                class Test {
                                    Function<Object, String> test() {
                                        return String::valueOf;
                                    }
                                }
                                """
                ));
    }

    @Test
    void testSubstringRemainderRecipe() {
        Recipe recipe = RewriteUtils.loadRecipe("StringRulesRecipes.SubstringRemainderRecipe");
        rewriteRun(
                spec -> spec.recipe(recipe)
                        .expectedCyclesThatMakeChanges(1).cycles(1)
                        .parser(JavaParser.fromJavaVersion().classpath(JavaParser.runtimeClasspath())),
                java(
                        """
                                class Test {
                                    String test() {
                                        return "foo".substring(1, "foo".length());
                                    }
                                }
                                """,
                        """
                                class Test {
                                    String test() {
                                        return "foo".substring(1);
                                    }
                                }
                                """
                ));
    }

    @Test
    void testUtf8EncodedLengthRecipe() {
        Recipe recipe = RewriteUtils.loadRecipe("StringRulesRecipes.Utf8EncodedLengthRecipe");
        rewriteRun(
                spec -> spec.recipe(recipe)
                        .expectedCyclesThatMakeChanges(1).cycles(1)
                        .parser(JavaParser.fromJavaVersion().classpath(JavaParser.runtimeClasspath())),
                java(
                        """
                                import java.nio.charset.StandardCharsets;
                                
                                class Test {
                                    int test() {
                                        return "foo".getBytes(StandardCharsets.UTF_8).length;
                                    }
                                }
                                """,
                        """
                                import com.google.common.base.Utf8;
                                
                                class Test {
                                    int test() {
                                        return Utf8.encodedLength("foo");
                                    }
                                }
                                """
                ));
    }
}
