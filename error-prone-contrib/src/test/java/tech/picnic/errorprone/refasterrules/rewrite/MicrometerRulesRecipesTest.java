package tech.picnic.errorprone.refasterrules.rewrite;

import org.junit.jupiter.api.Test;
import org.openrewrite.Recipe;
import org.openrewrite.java.JavaParser;
import org.openrewrite.test.RewriteTest;

import static org.openrewrite.java.Assertions.java;

final class MicrometerRulesRecipesTest implements RewriteTest {

    @Test
    void testTagsOf1Recipe() {
        Recipe recipe = RewriteUtils.loadRecipe("MicrometerRulesRecipes.TagsOf1Recipe");
        rewriteRun(
                spec -> spec.recipe(recipe)
                        .expectedCyclesThatMakeChanges(1).cycles(1)
                        .parser(JavaParser.fromJavaVersion().classpath(JavaParser.runtimeClasspath())),
                java(
                        """
                                import com.google.common.collect.ImmutableList;
                                import com.google.common.collect.ImmutableSet;
                                import io.micrometer.core.instrument.Tag;
                                
                                class Test {
                                    ImmutableSet<Iterable<Tag>> test() {
                                        return ImmutableSet.of(ImmutableSet.of(Tag.of("foo", "v1")), ImmutableList.of(Tag.of("bar", "v2")));
                                    }
                                }
                                """,
                        """
                                import com.google.common.collect.ImmutableSet;
                                import io.micrometer.core.instrument.Tag;
                                import io.micrometer.core.instrument.Tags;
                                
                                class Test {
                                    ImmutableSet<Iterable<Tag>> test() {
                                        return ImmutableSet.of(Tags.of(Tag.of("foo", "v1")), Tags.of(Tag.of("bar", "v2")));
                                    }
                                }
                                """
                ));
    }

    @Test
    void testTagsOf2Recipe() {
        Recipe recipe = RewriteUtils.loadRecipe("MicrometerRulesRecipes.TagsOf2Recipe");
        rewriteRun(
                spec -> spec.recipe(recipe)
                        .expectedCyclesThatMakeChanges(1).cycles(1)
                        .parser(JavaParser.fromJavaVersion().classpath(JavaParser.runtimeClasspath())),
                java(
                        """
                                import com.google.common.collect.ImmutableList;
                                import com.google.common.collect.ImmutableSet;
                                import io.micrometer.core.instrument.Tag;
                                
                                class Test {
                                    ImmutableSet<Iterable<Tag>> test() {
                                        return ImmutableSet.of(ImmutableSet.of(Tag.of("foo", "v1"), Tag.of("bar", "v2")), ImmutableList.of(Tag.of("baz", "v3"), Tag.of("qux", "v4")));
                                    }
                                }
                                """,
                        """
                                import com.google.common.collect.ImmutableSet;
                                import io.micrometer.core.instrument.Tag;
                                import io.micrometer.core.instrument.Tags;
                                
                                class Test {
                                    ImmutableSet<Iterable<Tag>> test() {
                                        return ImmutableSet.of(Tags.of(Tag.of("foo", "v1"), Tag.of("bar", "v2")), Tags.of(Tag.of("baz", "v3"), Tag.of("qux", "v4")));
                                    }
                                }
                                """
                ));
    }

    @Test
    void testTagsOf3Recipe() {
        Recipe recipe = RewriteUtils.loadRecipe("MicrometerRulesRecipes.TagsOf3Recipe");
        rewriteRun(
                spec -> spec.recipe(recipe)
                        .expectedCyclesThatMakeChanges(1).cycles(1)
                        .parser(JavaParser.fromJavaVersion().classpath(JavaParser.runtimeClasspath())),
                java(
                        """
                                import com.google.common.collect.ImmutableList;
                                import com.google.common.collect.ImmutableSet;
                                import io.micrometer.core.instrument.Tag;
                                
                                class Test {
                                    ImmutableSet<Iterable<Tag>> test() {
                                        return ImmutableSet.of(ImmutableSet.of(Tag.of("foo", "v1"), Tag.of("bar", "v2"), Tag.of("baz", "v3")), ImmutableList.of(Tag.of("qux", "v4"), Tag.of("quux", "v5"), Tag.of("corge", "v6")));
                                    }
                                }
                                """,
                        """
                                import com.google.common.collect.ImmutableSet;
                                import io.micrometer.core.instrument.Tag;
                                import io.micrometer.core.instrument.Tags;
                                
                                class Test {
                                    ImmutableSet<Iterable<Tag>> test() {
                                        return ImmutableSet.of(Tags.of(Tag.of("foo", "v1"), Tag.of("bar", "v2"), Tag.of("baz", "v3")), Tags.of(Tag.of("qux", "v4"), Tag.of("quux", "v5"), Tag.of("corge", "v6")));
                                    }
                                }
                                """
                ));
    }

    @Test
    void testTagsOf4Recipe() {
        Recipe recipe = RewriteUtils.loadRecipe("MicrometerRulesRecipes.TagsOf4Recipe");
        rewriteRun(
                spec -> spec.recipe(recipe)
                        .expectedCyclesThatMakeChanges(1).cycles(1)
                        .parser(JavaParser.fromJavaVersion().classpath(JavaParser.runtimeClasspath())),
                java(
                        """
                                import com.google.common.collect.ImmutableList;
                                import com.google.common.collect.ImmutableSet;
                                import io.micrometer.core.instrument.Tag;
                                
                                class Test {
                                    ImmutableSet<Iterable<Tag>> test() {
                                        return ImmutableSet.of(ImmutableSet.of(Tag.of("foo", "v1"), Tag.of("bar", "v2"), Tag.of("baz", "v3"), Tag.of("qux", "v4")), ImmutableList.of(Tag.of("quux", "v5"), Tag.of("corge", "v6"), Tag.of("grault", "v7"), Tag.of("garply", "v8")));
                                    }
                                }
                                """,
                        """
                                import com.google.common.collect.ImmutableSet;
                                import io.micrometer.core.instrument.Tag;
                                import io.micrometer.core.instrument.Tags;
                                
                                class Test {
                                    ImmutableSet<Iterable<Tag>> test() {
                                        return ImmutableSet.of(Tags.of(Tag.of("foo", "v1"), Tag.of("bar", "v2"), Tag.of("baz", "v3"), Tag.of("qux", "v4")), Tags.of(Tag.of("quux", "v5"), Tag.of("corge", "v6"), Tag.of("grault", "v7"), Tag.of("garply", "v8")));
                                    }
                                }
                                """
                ));
    }

    @Test
    void testTagsOf5Recipe() {
        Recipe recipe = RewriteUtils.loadRecipe("MicrometerRulesRecipes.TagsOf5Recipe");
        rewriteRun(
                spec -> spec.recipe(recipe)
                        .expectedCyclesThatMakeChanges(1).cycles(1)
                        .parser(JavaParser.fromJavaVersion().classpath(JavaParser.runtimeClasspath())),
                java(
                        """
                                import com.google.common.collect.ImmutableList;
                                import com.google.common.collect.ImmutableSet;
                                import io.micrometer.core.instrument.Tag;
                                
                                class Test {
                                    ImmutableSet<Iterable<Tag>> test() {
                                        return ImmutableSet.of(ImmutableSet.of(Tag.of("foo", "v1"), Tag.of("bar", "v2"), Tag.of("baz", "v3"), Tag.of("qux", "v4"), Tag.of("quux", "v5")), ImmutableList.of(Tag.of("corge", "v6"), Tag.of("grault", "v7"), Tag.of("garply", "v8"), Tag.of("waldo", "v9"), Tag.of("fred", "v10")));
                                    }
                                }
                                """,
                        """
                                import com.google.common.collect.ImmutableSet;
                                import io.micrometer.core.instrument.Tag;
                                import io.micrometer.core.instrument.Tags;
                                
                                class Test {
                                    ImmutableSet<Iterable<Tag>> test() {
                                        return ImmutableSet.of(Tags.of(Tag.of("foo", "v1"), Tag.of("bar", "v2"), Tag.of("baz", "v3"), Tag.of("qux", "v4"), Tag.of("quux", "v5")), Tags.of(Tag.of("corge", "v6"), Tag.of("grault", "v7"), Tag.of("garply", "v8"), Tag.of("waldo", "v9"), Tag.of("fred", "v10")));
                                    }
                                }
                                """
                ));
    }
}
