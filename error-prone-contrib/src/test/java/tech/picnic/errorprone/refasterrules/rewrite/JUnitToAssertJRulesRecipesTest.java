package tech.picnic.errorprone.refasterrules.rewrite;

import org.junit.jupiter.api.Test;
import org.openrewrite.Recipe;
import org.openrewrite.java.JavaParser;
import org.openrewrite.test.RewriteTest;

import static org.openrewrite.java.Assertions.java;

final class JUnitToAssertJRulesRecipesTest implements RewriteTest {

    @Test
    void testAssertThatBooleanArrayContainsExactlyRecipe() {
        Recipe recipe = RewriteUtils.loadRecipe("JUnitToAssertJRulesRecipes.AssertThatBooleanArrayContainsExactlyRecipe");
        rewriteRun(
                spec -> spec.recipe(recipe)
                        .expectedCyclesThatMakeChanges(1).cycles(1)
                        .parser(JavaParser.fromJavaVersion().classpath(JavaParser.runtimeClasspath())),
                java(
                        """
                                import org.junit.jupiter.api.Assertions;
                                
                                class Test {
                                    void test() {
                                        Assertions.assertArrayEquals(new boolean[]{true}, new boolean[]{false});
                                    }
                                }
                                """,
                        """
                                import org.assertj.core.api.Assertions;
                                
                                class Test {
                                    void test() {
                                        Assertions.assertThat(new boolean[]{false}).containsExactly(new boolean[]{true});
                                    }
                                }
                                """
                ));
    }

    @Test
    void testAssertThatBooleanArrayWithFailMessageContainsExactlyRecipe() {
        Recipe recipe = RewriteUtils.loadRecipe("JUnitToAssertJRulesRecipes.AssertThatBooleanArrayWithFailMessageContainsExactlyRecipe");
        rewriteRun(
                spec -> spec.recipe(recipe)
                        .expectedCyclesThatMakeChanges(1).cycles(1)
                        .parser(JavaParser.fromJavaVersion().classpath(JavaParser.runtimeClasspath())),
                java(
                        """
                                import org.junit.jupiter.api.Assertions;
                                
                                class Test {
                                    void test() {
                                        Assertions.assertArrayEquals(new boolean[]{true}, new boolean[]{false}, "foo");
                                    }
                                }
                                """,
                        """
                                import org.assertj.core.api.Assertions;
                                
                                class Test {
                                    void test() {
                                        Assertions.assertThat(new boolean[]{false}).withFailMessage("foo").containsExactly(new boolean[]{true});
                                    }
                                }
                                """
                ));
    }

    @Test
    void testAssertThatBooleanArrayWithFailMessageSupplierContainsExactlyRecipe() {
        Recipe recipe = RewriteUtils.loadRecipe("JUnitToAssertJRulesRecipes.AssertThatBooleanArrayWithFailMessageSupplierContainsExactlyRecipe");
        rewriteRun(
                spec -> spec.recipe(recipe)
                        .expectedCyclesThatMakeChanges(1).cycles(1)
                        .parser(JavaParser.fromJavaVersion().classpath(JavaParser.runtimeClasspath())),
                java(
                        """
                                import org.junit.jupiter.api.Assertions;
                                
                                class Test {
                                    void test() {
                                        Assertions.assertArrayEquals(new boolean[]{true}, new boolean[]{false}, () -> "foo");
                                    }
                                }
                                """,
                        """
                                import org.assertj.core.api.Assertions;
                                
                                class Test {
                                    void test() {
                                        Assertions.assertThat(new boolean[]{false}).withFailMessage(() -> "foo").containsExactly(new boolean[]{true});
                                    }
                                }
                                """
                ));
    }

    @Test
    void testAssertThatByteArrayContainsExactlyRecipe() {
        Recipe recipe = RewriteUtils.loadRecipe("JUnitToAssertJRulesRecipes.AssertThatByteArrayContainsExactlyRecipe");
        rewriteRun(
                spec -> spec.recipe(recipe)
                        .expectedCyclesThatMakeChanges(1).cycles(1)
                        .parser(JavaParser.fromJavaVersion().classpath(JavaParser.runtimeClasspath())),
                java(
                        """
                                import org.junit.jupiter.api.Assertions;
                                
                                class Test {
                                    void test() {
                                        Assertions.assertArrayEquals(new byte[]{1}, new byte[]{2});
                                    }
                                }
                                """,
                        """
                                import org.assertj.core.api.Assertions;
                                
                                class Test {
                                    void test() {
                                        Assertions.assertThat(new byte[]{2}).containsExactly(new byte[]{1});
                                    }
                                }
                                """
                ));
    }

    @Test
    void testAssertThatByteArrayWithFailMessageContainsExactlyRecipe() {
        Recipe recipe = RewriteUtils.loadRecipe("JUnitToAssertJRulesRecipes.AssertThatByteArrayWithFailMessageContainsExactlyRecipe");
        rewriteRun(
                spec -> spec.recipe(recipe)
                        .expectedCyclesThatMakeChanges(1).cycles(1)
                        .parser(JavaParser.fromJavaVersion().classpath(JavaParser.runtimeClasspath())),
                java(
                        """
                                import org.junit.jupiter.api.Assertions;
                                
                                class Test {
                                    void test() {
                                        Assertions.assertArrayEquals(new byte[]{1}, new byte[]{2}, "foo");
                                    }
                                }
                                """,
                        """
                                import org.assertj.core.api.Assertions;
                                
                                class Test {
                                    void test() {
                                        Assertions.assertThat(new byte[]{2}).withFailMessage("foo").containsExactly(new byte[]{1});
                                    }
                                }
                                """
                ));
    }

    @Test
    void testAssertThatByteArrayWithFailMessageSupplierContainsExactlyRecipe() {
        Recipe recipe = RewriteUtils.loadRecipe("JUnitToAssertJRulesRecipes.AssertThatByteArrayWithFailMessageSupplierContainsExactlyRecipe");
        rewriteRun(
                spec -> spec.recipe(recipe)
                        .expectedCyclesThatMakeChanges(1).cycles(1)
                        .parser(JavaParser.fromJavaVersion().classpath(JavaParser.runtimeClasspath())),
                java(
                        """
                                import org.junit.jupiter.api.Assertions;
                                
                                class Test {
                                    void test() {
                                        Assertions.assertArrayEquals(new byte[]{1}, new byte[]{2}, () -> "foo");
                                    }
                                }
                                """,
                        """
                                import org.assertj.core.api.Assertions;
                                
                                class Test {
                                    void test() {
                                        Assertions.assertThat(new byte[]{2}).withFailMessage(() -> "foo").containsExactly(new byte[]{1});
                                    }
                                }
                                """
                ));
    }

    @Test
    void testAssertThatCharArrayContainsExactlyRecipe() {
        Recipe recipe = RewriteUtils.loadRecipe("JUnitToAssertJRulesRecipes.AssertThatCharArrayContainsExactlyRecipe");
        rewriteRun(
                spec -> spec.recipe(recipe)
                        .expectedCyclesThatMakeChanges(1).cycles(1)
                        .parser(JavaParser.fromJavaVersion().classpath(JavaParser.runtimeClasspath())),
                java(
                        """
                                import org.junit.jupiter.api.Assertions;
                                
                                class Test {
                                    void test() {
                                        Assertions.assertArrayEquals(new char[]{'a'}, new char[]{'b'});
                                    }
                                }
                                """,
                        """
                                import org.assertj.core.api.Assertions;
                                
                                class Test {
                                    void test() {
                                        Assertions.assertThat(new char[]{'b'}).containsExactly(new char[]{'a'});
                                    }
                                }
                                """
                ));
    }

    @Test
    void testAssertThatCharArrayWithFailMessageContainsExactlyRecipe() {
        Recipe recipe = RewriteUtils.loadRecipe("JUnitToAssertJRulesRecipes.AssertThatCharArrayWithFailMessageContainsExactlyRecipe");
        rewriteRun(
                spec -> spec.recipe(recipe)
                        .expectedCyclesThatMakeChanges(1).cycles(1)
                        .parser(JavaParser.fromJavaVersion().classpath(JavaParser.runtimeClasspath())),
                java(
                        """
                                import org.junit.jupiter.api.Assertions;
                                
                                class Test {
                                    void test() {
                                        Assertions.assertArrayEquals(new char[]{'a'}, new char[]{'b'}, "foo");
                                    }
                                }
                                """,
                        """
                                import org.assertj.core.api.Assertions;
                                
                                class Test {
                                    void test() {
                                        Assertions.assertThat(new char[]{'b'}).withFailMessage("foo").containsExactly(new char[]{'a'});
                                    }
                                }
                                """
                ));
    }

    @Test
    void testAssertThatCharArrayWithFailMessageSupplierContainsExactlyRecipe() {
        Recipe recipe = RewriteUtils.loadRecipe("JUnitToAssertJRulesRecipes.AssertThatCharArrayWithFailMessageSupplierContainsExactlyRecipe");
        rewriteRun(
                spec -> spec.recipe(recipe)
                        .expectedCyclesThatMakeChanges(1).cycles(1)
                        .parser(JavaParser.fromJavaVersion().classpath(JavaParser.runtimeClasspath())),
                java(
                        """
                                import org.junit.jupiter.api.Assertions;
                                
                                class Test {
                                    void test() {
                                        Assertions.assertArrayEquals(new char[]{'a'}, new char[]{'b'}, () -> "foo");
                                    }
                                }
                                """,
                        """
                                import org.assertj.core.api.Assertions;
                                
                                class Test {
                                    void test() {
                                        Assertions.assertThat(new char[]{'b'}).withFailMessage(() -> "foo").containsExactly(new char[]{'a'});
                                    }
                                }
                                """
                ));
    }

    @Test
    void testAssertThatCodeDoesNotThrowAnyExceptionRecipe() {
        Recipe recipe = RewriteUtils.loadRecipe("JUnitToAssertJRulesRecipes.AssertThatCodeDoesNotThrowAnyExceptionRecipe");
        rewriteRun(
                spec -> spec.recipe(recipe)
                        .expectedCyclesThatMakeChanges(1).cycles(1)
                        .parser(JavaParser.fromJavaVersion().classpath(JavaParser.runtimeClasspath())),
                java(
                        """
                                import org.junit.jupiter.api.Assertions;
                                
                                class Test {
                                    void test() {
                                        Assertions.assertDoesNotThrow(() -> {
                                        });
                                        Assertions.assertDoesNotThrow(() -> toString());
                                    }
                                }
                                """,
                        """
                                import org.assertj.core.api.Assertions;
                                
                                class Test {
                                    void test() {
                                        Assertions.assertThatCode(() -> {
                                        }).doesNotThrowAnyException();
                                        Assertions.assertThatCode(() -> toString()).doesNotThrowAnyException();
                                    }
                                }
                                """
                ));
    }

    @Test
    void testAssertThatCodeWithFailMessageStringDoesNotThrowAnyExceptionRecipe() {
        Recipe recipe = RewriteUtils.loadRecipe("JUnitToAssertJRulesRecipes.AssertThatCodeWithFailMessageStringDoesNotThrowAnyExceptionRecipe");
        rewriteRun(
                spec -> spec.recipe(recipe)
                        .expectedCyclesThatMakeChanges(1).cycles(1)
                        .parser(JavaParser.fromJavaVersion().classpath(JavaParser.runtimeClasspath())),
                java(
                        """
                                import org.junit.jupiter.api.Assertions;
                                
                                class Test {
                                    void test() {
                                        Assertions.assertDoesNotThrow(() -> {
                                        }, "foo");
                                        Assertions.assertDoesNotThrow(() -> toString(), "bar");
                                    }
                                }
                                """,
                        """
                                import org.assertj.core.api.Assertions;
                                
                                class Test {
                                    void test() {
                                        Assertions.assertThatCode(() -> {
                                        }).withFailMessage("foo").doesNotThrowAnyException();
                                        Assertions.assertThatCode(() -> toString()).withFailMessage("bar").doesNotThrowAnyException();
                                    }
                                }
                                """
                ));
    }

    @Test
    void testAssertThatCodeWithFailMessageSupplierDoesNotThrowAnyExceptionRecipe() {
        Recipe recipe = RewriteUtils.loadRecipe("JUnitToAssertJRulesRecipes.AssertThatCodeWithFailMessageSupplierDoesNotThrowAnyExceptionRecipe");
        rewriteRun(
                spec -> spec.recipe(recipe)
                        .expectedCyclesThatMakeChanges(1).cycles(1)
                        .parser(JavaParser.fromJavaVersion().classpath(JavaParser.runtimeClasspath())),
                java(
                        """
                                import org.junit.jupiter.api.Assertions;
                                
                                class Test {
                                    void test() {
                                        Assertions.assertDoesNotThrow(() -> {
                                        }, () -> "foo");
                                        Assertions.assertDoesNotThrow(() -> toString(), () -> "bar");
                                    }
                                }
                                """,
                        """
                                import org.assertj.core.api.Assertions;
                                
                                class Test {
                                    void test() {
                                        Assertions.assertThatCode(() -> {
                                        }).withFailMessage(() -> "foo").doesNotThrowAnyException();
                                        Assertions.assertThatCode(() -> toString()).withFailMessage(() -> "bar").doesNotThrowAnyException();
                                    }
                                }
                                """
                ));
    }

    @Test
    void testAssertThatDoubleArrayContainsExactlyRecipe() {
        Recipe recipe = RewriteUtils.loadRecipe("JUnitToAssertJRulesRecipes.AssertThatDoubleArrayContainsExactlyRecipe");
        rewriteRun(
                spec -> spec.recipe(recipe)
                        .expectedCyclesThatMakeChanges(1).cycles(1)
                        .parser(JavaParser.fromJavaVersion().classpath(JavaParser.runtimeClasspath())),
                java(
                        """
                                import org.junit.jupiter.api.Assertions;
                                
                                class Test {
                                    void test() {
                                        Assertions.assertArrayEquals(new double[]{1.0}, new double[]{2.0});
                                    }
                                }
                                """,
                        """
                                import org.assertj.core.api.Assertions;
                                
                                class Test {
                                    void test() {
                                        Assertions.assertThat(new double[]{2.0}).containsExactly(new double[]{1.0});
                                    }
                                }
                                """
                ));
    }

    @Test
    void testAssertThatDoubleArrayContainsExactlyWithOffsetRecipe() {
        Recipe recipe = RewriteUtils.loadRecipe("JUnitToAssertJRulesRecipes.AssertThatDoubleArrayContainsExactlyWithOffsetRecipe");
        rewriteRun(
                spec -> spec.recipe(recipe)
                        .expectedCyclesThatMakeChanges(1).cycles(1)
                        .parser(JavaParser.fromJavaVersion().classpath(JavaParser.runtimeClasspath())),
                java(
                        """
                                import org.junit.jupiter.api.Assertions;
                                
                                class Test {
                                    void test() {
                                        Assertions.assertArrayEquals(new double[]{1.0}, new double[]{2.0}, 0.1);
                                    }
                                }
                                """,
                        """
                                import org.assertj.core.api.Assertions;
                                
                                class Test {
                                    void test() {
                                        Assertions.assertThat(new double[]{2.0}).containsExactly(new double[]{1.0}, Assertions.offset(0.1));
                                    }
                                }
                                """
                ));
    }

    @Test
    void testAssertThatDoubleArrayWithFailMessageContainsExactlyRecipe() {
        Recipe recipe = RewriteUtils.loadRecipe("JUnitToAssertJRulesRecipes.AssertThatDoubleArrayWithFailMessageContainsExactlyRecipe");
        rewriteRun(
                spec -> spec.recipe(recipe)
                        .expectedCyclesThatMakeChanges(1).cycles(1)
                        .parser(JavaParser.fromJavaVersion().classpath(JavaParser.runtimeClasspath())),
                java(
                        """
                                import org.junit.jupiter.api.Assertions;
                                
                                class Test {
                                    void test() {
                                        Assertions.assertArrayEquals(new double[]{1.0}, new double[]{2.0}, "foo");
                                    }
                                }
                                """,
                        """
                                import org.assertj.core.api.Assertions;
                                
                                class Test {
                                    void test() {
                                        Assertions.assertThat(new double[]{2.0}).withFailMessage("foo").containsExactly(new double[]{1.0});
                                    }
                                }
                                """
                ));
    }

    @Test
    void testAssertThatDoubleArrayWithFailMessageContainsExactlyWithOffsetRecipe() {
        Recipe recipe = RewriteUtils.loadRecipe("JUnitToAssertJRulesRecipes.AssertThatDoubleArrayWithFailMessageContainsExactlyWithOffsetRecipe");
        rewriteRun(
                spec -> spec.recipe(recipe)
                        .expectedCyclesThatMakeChanges(1).cycles(1)
                        .parser(JavaParser.fromJavaVersion().classpath(JavaParser.runtimeClasspath())),
                java(
                        """
                                import org.junit.jupiter.api.Assertions;
                                
                                class Test {
                                    void test() {
                                        Assertions.assertArrayEquals(new double[]{1.0}, new double[]{2.0}, 0.1, "foo");
                                    }
                                }
                                """,
                        """
                                import org.assertj.core.api.Assertions;
                                
                                class Test {
                                    void test() {
                                        Assertions.assertThat(new double[]{2.0}).withFailMessage("foo").containsExactly(new double[]{1.0}, Assertions.offset(0.1));
                                    }
                                }
                                """
                ));
    }

    @Test
    void testAssertThatDoubleArrayWithFailMessageSupplierContainsExactlyRecipe() {
        Recipe recipe = RewriteUtils.loadRecipe("JUnitToAssertJRulesRecipes.AssertThatDoubleArrayWithFailMessageSupplierContainsExactlyRecipe");
        rewriteRun(
                spec -> spec.recipe(recipe)
                        .expectedCyclesThatMakeChanges(1).cycles(1)
                        .parser(JavaParser.fromJavaVersion().classpath(JavaParser.runtimeClasspath())),
                java(
                        """
                                import org.junit.jupiter.api.Assertions;
                                
                                class Test {
                                    void test() {
                                        Assertions.assertArrayEquals(new double[]{1.0}, new double[]{2.0}, () -> "foo");
                                    }
                                }
                                """,
                        """
                                import org.assertj.core.api.Assertions;
                                
                                class Test {
                                    void test() {
                                        Assertions.assertThat(new double[]{2.0}).withFailMessage(() -> "foo").containsExactly(new double[]{1.0});
                                    }
                                }
                                """
                ));
    }

    @Test
    void testAssertThatDoubleArrayWithFailMessageSupplierContainsExactlyWithOffsetRecipe() {
        Recipe recipe = RewriteUtils.loadRecipe("JUnitToAssertJRulesRecipes.AssertThatDoubleArrayWithFailMessageSupplierContainsExactlyWithOffsetRecipe");
        rewriteRun(
                spec -> spec.recipe(recipe)
                        .expectedCyclesThatMakeChanges(1).cycles(1)
                        .parser(JavaParser.fromJavaVersion().classpath(JavaParser.runtimeClasspath())),
                java(
                        """
                                import org.junit.jupiter.api.Assertions;
                                
                                class Test {
                                    void test() {
                                        Assertions.assertArrayEquals(new double[]{1.0}, new double[]{2.0}, 0.1, () -> "foo");
                                    }
                                }
                                """,
                        """
                                import org.assertj.core.api.Assertions;
                                
                                class Test {
                                    void test() {
                                        Assertions.assertThat(new double[]{2.0}).withFailMessage(() -> "foo").containsExactly(new double[]{1.0}, Assertions.offset(0.1));
                                    }
                                }
                                """
                ));
    }

    @Test
    void testAssertThatFloatArrayContainsExactlyRecipe() {
        Recipe recipe = RewriteUtils.loadRecipe("JUnitToAssertJRulesRecipes.AssertThatFloatArrayContainsExactlyRecipe");
        rewriteRun(
                spec -> spec.recipe(recipe)
                        .expectedCyclesThatMakeChanges(1).cycles(1)
                        .parser(JavaParser.fromJavaVersion().classpath(JavaParser.runtimeClasspath())),
                java(
                        """
                                import org.junit.jupiter.api.Assertions;
                                
                                class Test {
                                    void test() {
                                        Assertions.assertArrayEquals(new float[]{1.0F}, new float[]{2.0F});
                                    }
                                }
                                """,
                        """
                                import org.assertj.core.api.Assertions;
                                
                                class Test {
                                    void test() {
                                        Assertions.assertThat(new float[]{2.0F}).containsExactly(new float[]{1.0F});
                                    }
                                }
                                """
                ));
    }

    @Test
    void testAssertThatFloatArrayContainsExactlyWithOffsetRecipe() {
        Recipe recipe = RewriteUtils.loadRecipe("JUnitToAssertJRulesRecipes.AssertThatFloatArrayContainsExactlyWithOffsetRecipe");
        rewriteRun(
                spec -> spec.recipe(recipe)
                        .expectedCyclesThatMakeChanges(1).cycles(1)
                        .parser(JavaParser.fromJavaVersion().classpath(JavaParser.runtimeClasspath())),
                java(
                        """
                                import org.junit.jupiter.api.Assertions;
                                
                                class Test {
                                    void test() {
                                        Assertions.assertArrayEquals(new float[]{1.0F}, new float[]{2.0F}, 0.1f);
                                    }
                                }
                                """,
                        """
                                import org.assertj.core.api.Assertions;
                                
                                class Test {
                                    void test() {
                                        Assertions.assertThat(new float[]{2.0F}).containsExactly(new float[]{1.0F}, Assertions.offset(0.1f));
                                    }
                                }
                                """
                ));
    }

    @Test
    void testAssertThatFloatArrayWithFailMessageContainsExactlyRecipe() {
        Recipe recipe = RewriteUtils.loadRecipe("JUnitToAssertJRulesRecipes.AssertThatFloatArrayWithFailMessageContainsExactlyRecipe");
        rewriteRun(
                spec -> spec.recipe(recipe)
                        .expectedCyclesThatMakeChanges(1).cycles(1)
                        .parser(JavaParser.fromJavaVersion().classpath(JavaParser.runtimeClasspath())),
                java(
                        """
                                import org.junit.jupiter.api.Assertions;
                                
                                class Test {
                                    void test() {
                                        Assertions.assertArrayEquals(new float[]{1.0F}, new float[]{2.0F}, "foo");
                                    }
                                }
                                """,
                        """
                                import org.assertj.core.api.Assertions;
                                
                                class Test {
                                    void test() {
                                        Assertions.assertThat(new float[]{2.0F}).withFailMessage("foo").containsExactly(new float[]{1.0F});
                                    }
                                }
                                """
                ));
    }

    @Test
    void testAssertThatFloatArrayWithFailMessageContainsExactlyWithOffsetRecipe() {
        Recipe recipe = RewriteUtils.loadRecipe("JUnitToAssertJRulesRecipes.AssertThatFloatArrayWithFailMessageContainsExactlyWithOffsetRecipe");
        rewriteRun(
                spec -> spec.recipe(recipe)
                        .expectedCyclesThatMakeChanges(1).cycles(1)
                        .parser(JavaParser.fromJavaVersion().classpath(JavaParser.runtimeClasspath())),
                java(
                        """
                                import org.junit.jupiter.api.Assertions;
                                
                                class Test {
                                    void test() {
                                        Assertions.assertArrayEquals(new float[]{1.0F}, new float[]{2.0F}, 0.1f, "foo");
                                    }
                                }
                                """,
                        """
                                import org.assertj.core.api.Assertions;
                                
                                class Test {
                                    void test() {
                                        Assertions.assertThat(new float[]{2.0F}).withFailMessage("foo").containsExactly(new float[]{1.0F}, Assertions.offset(0.1f));
                                    }
                                }
                                """
                ));
    }

    @Test
    void testAssertThatFloatArrayWithFailMessageSupplierContainsExactlyRecipe() {
        Recipe recipe = RewriteUtils.loadRecipe("JUnitToAssertJRulesRecipes.AssertThatFloatArrayWithFailMessageSupplierContainsExactlyRecipe");
        rewriteRun(
                spec -> spec.recipe(recipe)
                        .expectedCyclesThatMakeChanges(1).cycles(1)
                        .parser(JavaParser.fromJavaVersion().classpath(JavaParser.runtimeClasspath())),
                java(
                        """
                                import org.junit.jupiter.api.Assertions;
                                
                                class Test {
                                    void test() {
                                        Assertions.assertArrayEquals(new float[]{1.0F}, new float[]{2.0F}, () -> "foo");
                                    }
                                }
                                """,
                        """
                                import org.assertj.core.api.Assertions;
                                
                                class Test {
                                    void test() {
                                        Assertions.assertThat(new float[]{2.0F}).withFailMessage(() -> "foo").containsExactly(new float[]{1.0F});
                                    }
                                }
                                """
                ));
    }

    @Test
    void testAssertThatFloatArrayWithFailMessageSupplierContainsExactlyWithOffsetRecipe() {
        Recipe recipe = RewriteUtils.loadRecipe("JUnitToAssertJRulesRecipes.AssertThatFloatArrayWithFailMessageSupplierContainsExactlyWithOffsetRecipe");
        rewriteRun(
                spec -> spec.recipe(recipe)
                        .expectedCyclesThatMakeChanges(1).cycles(1)
                        .parser(JavaParser.fromJavaVersion().classpath(JavaParser.runtimeClasspath())),
                java(
                        """
                                import org.junit.jupiter.api.Assertions;
                                
                                class Test {
                                    void test() {
                                        Assertions.assertArrayEquals(new float[]{1.0F}, new float[]{2.0F}, 0.1f, () -> "foo");
                                    }
                                }
                                """,
                        """
                                import org.assertj.core.api.Assertions;
                                
                                class Test {
                                    void test() {
                                        Assertions.assertThat(new float[]{2.0F}).withFailMessage(() -> "foo").containsExactly(new float[]{1.0F}, Assertions.offset(0.1f));
                                    }
                                }
                                """
                ));
    }

    @Test
    void testAssertThatIntArrayContainsExactlyRecipe() {
        Recipe recipe = RewriteUtils.loadRecipe("JUnitToAssertJRulesRecipes.AssertThatIntArrayContainsExactlyRecipe");
        rewriteRun(
                spec -> spec.recipe(recipe)
                        .expectedCyclesThatMakeChanges(1).cycles(1)
                        .parser(JavaParser.fromJavaVersion().classpath(JavaParser.runtimeClasspath())),
                java(
                        """
                                import org.junit.jupiter.api.Assertions;
                                
                                class Test {
                                    void test() {
                                        Assertions.assertArrayEquals(new int[]{1}, new int[]{2});
                                    }
                                }
                                """,
                        """
                                import org.assertj.core.api.Assertions;
                                
                                class Test {
                                    void test() {
                                        Assertions.assertThat(new int[]{2}).containsExactly(new int[]{1});
                                    }
                                }
                                """
                ));
    }

    @Test
    void testAssertThatIntArrayWithFailMessageContainsExactlyRecipe() {
        Recipe recipe = RewriteUtils.loadRecipe("JUnitToAssertJRulesRecipes.AssertThatIntArrayWithFailMessageContainsExactlyRecipe");
        rewriteRun(
                spec -> spec.recipe(recipe)
                        .expectedCyclesThatMakeChanges(1).cycles(1)
                        .parser(JavaParser.fromJavaVersion().classpath(JavaParser.runtimeClasspath())),
                java(
                        """
                                import org.junit.jupiter.api.Assertions;
                                
                                class Test {
                                    void test() {
                                        Assertions.assertArrayEquals(new int[]{1}, new int[]{2}, "foo");
                                    }
                                }
                                """,
                        """
                                import org.assertj.core.api.Assertions;
                                
                                class Test {
                                    void test() {
                                        Assertions.assertThat(new int[]{2}).withFailMessage("foo").containsExactly(new int[]{1});
                                    }
                                }
                                """
                ));
    }

    @Test
    void testAssertThatIntArrayWithFailMessageSupplierContainsExactlyRecipe() {
        Recipe recipe = RewriteUtils.loadRecipe("JUnitToAssertJRulesRecipes.AssertThatIntArrayWithFailMessageSupplierContainsExactlyRecipe");
        rewriteRun(
                spec -> spec.recipe(recipe)
                        .expectedCyclesThatMakeChanges(1).cycles(1)
                        .parser(JavaParser.fromJavaVersion().classpath(JavaParser.runtimeClasspath())),
                java(
                        """
                                import org.junit.jupiter.api.Assertions;
                                
                                class Test {
                                    void test() {
                                        Assertions.assertArrayEquals(new int[]{1}, new int[]{2}, () -> "foo");
                                    }
                                }
                                """,
                        """
                                import org.assertj.core.api.Assertions;
                                
                                class Test {
                                    void test() {
                                        Assertions.assertThat(new int[]{2}).withFailMessage(() -> "foo").containsExactly(new int[]{1});
                                    }
                                }
                                """
                ));
    }

    @Test
    void testAssertThatIsFalseRecipe() {
        Recipe recipe = RewriteUtils.loadRecipe("JUnitToAssertJRulesRecipes.AssertThatIsFalseRecipe");
        rewriteRun(
                spec -> spec.recipe(recipe)
                        .expectedCyclesThatMakeChanges(1).cycles(1)
                        .parser(JavaParser.fromJavaVersion().classpath(JavaParser.runtimeClasspath())),
                java(
                        """
                                import org.junit.jupiter.api.Assertions;
                                
                                class Test {
                                    void test() {
                                        Assertions.assertFalse(true);
                                    }
                                }
                                """,
                        """
                                import org.assertj.core.api.Assertions;
                                
                                class Test {
                                    void test() {
                                        Assertions.assertThat(true).isFalse();
                                    }
                                }
                                """
                ));
    }

    @Test
    void testAssertThatIsInstanceOfRecipe() {
        Recipe recipe = RewriteUtils.loadRecipe("JUnitToAssertJRulesRecipes.AssertThatIsInstanceOfRecipe");
        rewriteRun(
                spec -> spec.recipe(recipe)
                        .expectedCyclesThatMakeChanges(1).cycles(1)
                        .parser(JavaParser.fromJavaVersion().classpath(JavaParser.runtimeClasspath())),
                java(
                        """
                                import org.junit.jupiter.api.Assertions;
                                
                                class Test {
                                    void test() {
                                        Assertions.assertInstanceOf(Object.class, new Object());
                                    }
                                }
                                """,
                        """
                                import org.assertj.core.api.Assertions;
                                
                                class Test {
                                    void test() {
                                        Assertions.assertThat(new Object()).isInstanceOf(Object.class);
                                    }
                                }
                                """
                ));
    }

    @Test
    void testAssertThatIsNotNullRecipe() {
        Recipe recipe = RewriteUtils.loadRecipe("JUnitToAssertJRulesRecipes.AssertThatIsNotNullRecipe");
        rewriteRun(
                spec -> spec.recipe(recipe)
                        .expectedCyclesThatMakeChanges(1).cycles(1)
                        .parser(JavaParser.fromJavaVersion().classpath(JavaParser.runtimeClasspath())),
                java(
                        """
                                import org.junit.jupiter.api.Assertions;
                                
                                class Test {
                                    void test() {
                                        Assertions.assertNotNull(new Object());
                                    }
                                }
                                """,
                        """
                                import org.assertj.core.api.Assertions;
                                
                                class Test {
                                    void test() {
                                        Assertions.assertThat(new Object()).isNotNull();
                                    }
                                }
                                """
                ));
    }

    @Test
    void testAssertThatIsNotSameAsRecipe() {
        Recipe recipe = RewriteUtils.loadRecipe("JUnitToAssertJRulesRecipes.AssertThatIsNotSameAsRecipe");
        rewriteRun(
                spec -> spec.recipe(recipe)
                        .expectedCyclesThatMakeChanges(1).cycles(1)
                        .parser(JavaParser.fromJavaVersion().classpath(JavaParser.runtimeClasspath())),
                java(
                        """
                                import org.junit.jupiter.api.Assertions;
                                
                                class Test {
                                    void test() {
                                        Assertions.assertNotSame("foo", "bar");
                                    }
                                }
                                """,
                        """
                                import org.assertj.core.api.Assertions;
                                
                                class Test {
                                    void test() {
                                        Assertions.assertThat("bar").isNotSameAs("foo");
                                    }
                                }
                                """
                ));
    }

    @Test
    void testAssertThatIsNullRecipe() {
        Recipe recipe = RewriteUtils.loadRecipe("JUnitToAssertJRulesRecipes.AssertThatIsNullRecipe");
        rewriteRun(
                spec -> spec.recipe(recipe)
                        .expectedCyclesThatMakeChanges(1).cycles(1)
                        .parser(JavaParser.fromJavaVersion().classpath(JavaParser.runtimeClasspath())),
                java(
                        """
                                import org.junit.jupiter.api.Assertions;
                                
                                class Test {
                                    void test() {
                                        Assertions.assertNull(new Object());
                                    }
                                }
                                """,
                        """
                                import org.assertj.core.api.Assertions;
                                
                                class Test {
                                    void test() {
                                        Assertions.assertThat(new Object()).isNull();
                                    }
                                }
                                """
                ));
    }

    @Test
    void testAssertThatIsSameAsRecipe() {
        Recipe recipe = RewriteUtils.loadRecipe("JUnitToAssertJRulesRecipes.AssertThatIsSameAsRecipe");
        rewriteRun(
                spec -> spec.recipe(recipe)
                        .expectedCyclesThatMakeChanges(1).cycles(1)
                        .parser(JavaParser.fromJavaVersion().classpath(JavaParser.runtimeClasspath())),
                java(
                        """
                                import org.junit.jupiter.api.Assertions;
                                
                                class Test {
                                    void test() {
                                        Assertions.assertSame("foo", "bar");
                                    }
                                }
                                """,
                        """
                                import org.assertj.core.api.Assertions;
                                
                                class Test {
                                    void test() {
                                        Assertions.assertThat("bar").isSameAs("foo");
                                    }
                                }
                                """
                ));
    }

    @Test
    void testAssertThatIsTrueRecipe() {
        Recipe recipe = RewriteUtils.loadRecipe("JUnitToAssertJRulesRecipes.AssertThatIsTrueRecipe");
        rewriteRun(
                spec -> spec.recipe(recipe)
                        .expectedCyclesThatMakeChanges(1).cycles(1)
                        .parser(JavaParser.fromJavaVersion().classpath(JavaParser.runtimeClasspath())),
                java(
                        """
                                import org.junit.jupiter.api.Assertions;
                                
                                class Test {
                                    void test() {
                                        Assertions.assertTrue(true);
                                    }
                                }
                                """,
                        """
                                import org.assertj.core.api.Assertions;
                                
                                class Test {
                                    void test() {
                                        Assertions.assertThat(true).isTrue();
                                    }
                                }
                                """
                ));
    }

    @Test
    void testAssertThatLongArrayContainsExactlyRecipe() {
        Recipe recipe = RewriteUtils.loadRecipe("JUnitToAssertJRulesRecipes.AssertThatLongArrayContainsExactlyRecipe");
        rewriteRun(
                spec -> spec.recipe(recipe)
                        .expectedCyclesThatMakeChanges(1).cycles(1)
                        .parser(JavaParser.fromJavaVersion().classpath(JavaParser.runtimeClasspath())),
                java(
                        """
                                import org.junit.jupiter.api.Assertions;
                                
                                class Test {
                                    void test() {
                                        Assertions.assertArrayEquals(new long[]{1L}, new long[]{2L});
                                    }
                                }
                                """,
                        """
                                import org.assertj.core.api.Assertions;
                                
                                class Test {
                                    void test() {
                                        Assertions.assertThat(new long[]{2L}).containsExactly(new long[]{1L});
                                    }
                                }
                                """
                ));
    }

    @Test
    void testAssertThatLongArrayWithFailMessageContainsExactlyRecipe() {
        Recipe recipe = RewriteUtils.loadRecipe("JUnitToAssertJRulesRecipes.AssertThatLongArrayWithFailMessageContainsExactlyRecipe");
        rewriteRun(
                spec -> spec.recipe(recipe)
                        .expectedCyclesThatMakeChanges(1).cycles(1)
                        .parser(JavaParser.fromJavaVersion().classpath(JavaParser.runtimeClasspath())),
                java(
                        """
                                import org.junit.jupiter.api.Assertions;
                                
                                class Test {
                                    void test() {
                                        Assertions.assertArrayEquals(new long[]{1L}, new long[]{2L}, "foo");
                                    }
                                }
                                """,
                        """
                                import org.assertj.core.api.Assertions;
                                
                                class Test {
                                    void test() {
                                        Assertions.assertThat(new long[]{2L}).withFailMessage("foo").containsExactly(new long[]{1L});
                                    }
                                }
                                """
                ));
    }

    @Test
    void testAssertThatLongArrayWithFailMessageSupplierContainsExactlyRecipe() {
        Recipe recipe = RewriteUtils.loadRecipe("JUnitToAssertJRulesRecipes.AssertThatLongArrayWithFailMessageSupplierContainsExactlyRecipe");
        rewriteRun(
                spec -> spec.recipe(recipe)
                        .expectedCyclesThatMakeChanges(1).cycles(1)
                        .parser(JavaParser.fromJavaVersion().classpath(JavaParser.runtimeClasspath())),
                java(
                        """
                                import org.junit.jupiter.api.Assertions;
                                
                                class Test {
                                    void test() {
                                        Assertions.assertArrayEquals(new long[]{1L}, new long[]{2L}, () -> "foo");
                                    }
                                }
                                """,
                        """
                                import org.assertj.core.api.Assertions;
                                
                                class Test {
                                    void test() {
                                        Assertions.assertThat(new long[]{2L}).withFailMessage(() -> "foo").containsExactly(new long[]{1L});
                                    }
                                }
                                """
                ));
    }

    @Test
    void testAssertThatObjectArrayContainsExactlyRecipe() {
        Recipe recipe = RewriteUtils.loadRecipe("JUnitToAssertJRulesRecipes.AssertThatObjectArrayContainsExactlyRecipe");
        rewriteRun(
                spec -> spec.recipe(recipe)
                        .expectedCyclesThatMakeChanges(1).cycles(1)
                        .parser(JavaParser.fromJavaVersion().classpath(JavaParser.runtimeClasspath())),
                java(
                        """
                                import org.junit.jupiter.api.Assertions;
                                
                                class Test {
                                    void test() {
                                        Assertions.assertArrayEquals(new Object[]{"foo"}, new Object[]{"bar"});
                                    }
                                }
                                """,
                        """
                                import org.assertj.core.api.Assertions;
                                
                                class Test {
                                    void test() {
                                        Assertions.assertThat(new Object[]{"bar"}).containsExactly(new Object[]{"foo"});
                                    }
                                }
                                """
                ));
    }

    @Test
    void testAssertThatObjectArrayWithFailMessageContainsExactlyRecipe() {
        Recipe recipe = RewriteUtils.loadRecipe("JUnitToAssertJRulesRecipes.AssertThatObjectArrayWithFailMessageContainsExactlyRecipe");
        rewriteRun(
                spec -> spec.recipe(recipe)
                        .expectedCyclesThatMakeChanges(1).cycles(1)
                        .parser(JavaParser.fromJavaVersion().classpath(JavaParser.runtimeClasspath())),
                java(
                        """
                                import org.junit.jupiter.api.Assertions;
                                
                                class Test {
                                    void test() {
                                        Assertions.assertArrayEquals(new Object[]{"foo"}, new Object[]{"bar"}, "foo");
                                    }
                                }
                                """,
                        """
                                import org.assertj.core.api.Assertions;
                                
                                class Test {
                                    void test() {
                                        Assertions.assertThat(new Object[]{"bar"}).withFailMessage("foo").containsExactly(new Object[]{"foo"});
                                    }
                                }
                                """
                ));
    }

    @Test
    void testAssertThatObjectArrayWithFailMessageSupplierContainsExactlyRecipe() {
        Recipe recipe = RewriteUtils.loadRecipe("JUnitToAssertJRulesRecipes.AssertThatObjectArrayWithFailMessageSupplierContainsExactlyRecipe");
        rewriteRun(
                spec -> spec.recipe(recipe)
                        .expectedCyclesThatMakeChanges(1).cycles(1)
                        .parser(JavaParser.fromJavaVersion().classpath(JavaParser.runtimeClasspath())),
                java(
                        """
                                import org.junit.jupiter.api.Assertions;
                                
                                class Test {
                                    void test() {
                                        Assertions.assertArrayEquals(new Object[]{"foo"}, new Object[]{"bar"}, () -> "foo");
                                    }
                                }
                                """,
                        """
                                import org.assertj.core.api.Assertions;
                                
                                class Test {
                                    void test() {
                                        Assertions.assertThat(new Object[]{"bar"}).withFailMessage(() -> "foo").containsExactly(new Object[]{"foo"});
                                    }
                                }
                                """
                ));
    }

    @Test
    void testAssertThatShortArrayContainsExactlyRecipe() {
        Recipe recipe = RewriteUtils.loadRecipe("JUnitToAssertJRulesRecipes.AssertThatShortArrayContainsExactlyRecipe");
        rewriteRun(
                spec -> spec.recipe(recipe)
                        .expectedCyclesThatMakeChanges(1).cycles(1)
                        .parser(JavaParser.fromJavaVersion().classpath(JavaParser.runtimeClasspath())),
                java(
                        """
                                import org.junit.jupiter.api.Assertions;
                                
                                class Test {
                                    void test() {
                                        Assertions.assertArrayEquals(new short[]{1}, new short[]{2});
                                    }
                                }
                                """,
                        """
                                import org.assertj.core.api.Assertions;
                                
                                class Test {
                                    void test() {
                                        Assertions.assertThat(new short[]{2}).containsExactly(new short[]{1});
                                    }
                                }
                                """
                ));
    }

    @Test
    void testAssertThatShortArrayWithFailMessageContainsExactlyRecipe() {
        Recipe recipe = RewriteUtils.loadRecipe("JUnitToAssertJRulesRecipes.AssertThatShortArrayWithFailMessageContainsExactlyRecipe");
        rewriteRun(
                spec -> spec.recipe(recipe)
                        .expectedCyclesThatMakeChanges(1).cycles(1)
                        .parser(JavaParser.fromJavaVersion().classpath(JavaParser.runtimeClasspath())),
                java(
                        """
                                import org.junit.jupiter.api.Assertions;
                                
                                class Test {
                                    void test() {
                                        Assertions.assertArrayEquals(new short[]{1}, new short[]{2}, "foo");
                                    }
                                }
                                """,
                        """
                                import org.assertj.core.api.Assertions;
                                
                                class Test {
                                    void test() {
                                        Assertions.assertThat(new short[]{2}).withFailMessage("foo").containsExactly(new short[]{1});
                                    }
                                }
                                """
                ));
    }

    @Test
    void testAssertThatShortArrayWithFailMessageSupplierContainsExactlyRecipe() {
        Recipe recipe = RewriteUtils.loadRecipe("JUnitToAssertJRulesRecipes.AssertThatShortArrayWithFailMessageSupplierContainsExactlyRecipe");
        rewriteRun(
                spec -> spec.recipe(recipe)
                        .expectedCyclesThatMakeChanges(1).cycles(1)
                        .parser(JavaParser.fromJavaVersion().classpath(JavaParser.runtimeClasspath())),
                java(
                        """
                                import org.junit.jupiter.api.Assertions;
                                
                                class Test {
                                    void test() {
                                        Assertions.assertArrayEquals(new short[]{1}, new short[]{2}, () -> "foo");
                                    }
                                }
                                """,
                        """
                                import org.assertj.core.api.Assertions;
                                
                                class Test {
                                    void test() {
                                        Assertions.assertThat(new short[]{2}).withFailMessage(() -> "foo").containsExactly(new short[]{1});
                                    }
                                }
                                """
                ));
    }

    @Test
    void testAssertThatThrownByIsExactlyInstanceOfRecipe() {
        Recipe recipe = RewriteUtils.loadRecipe("JUnitToAssertJRulesRecipes.AssertThatThrownByIsExactlyInstanceOfRecipe");
        rewriteRun(
                spec -> spec.recipe(recipe)
                        .expectedCyclesThatMakeChanges(1).cycles(1)
                        .parser(JavaParser.fromJavaVersion().classpath(JavaParser.runtimeClasspath())),
                java(
                        """
                                import org.junit.jupiter.api.Assertions;
                                
                                class Test {
                                    void test() {
                                        Assertions.assertThrowsExactly(IllegalStateException.class, () -> {
                                        });
                                    }
                                }
                                """,
                        """
                                import org.assertj.core.api.Assertions;
                                
                                class Test {
                                    void test() {
                                        Assertions.assertThatThrownBy(() -> {
                                        }).isExactlyInstanceOf(IllegalStateException.class);
                                    }
                                }
                                """
                ));
    }

    @Test
    void testAssertThatThrownByIsInstanceOfRecipe() {
        Recipe recipe = RewriteUtils.loadRecipe("JUnitToAssertJRulesRecipes.AssertThatThrownByIsInstanceOfRecipe");
        rewriteRun(
                spec -> spec.recipe(recipe)
                        .expectedCyclesThatMakeChanges(1).cycles(1)
                        .parser(JavaParser.fromJavaVersion().classpath(JavaParser.runtimeClasspath())),
                java(
                        """
                                import org.junit.jupiter.api.Assertions;
                                
                                class Test {
                                    void test() {
                                        Assertions.assertThrows(IllegalStateException.class, () -> {
                                        });
                                    }
                                }
                                """,
                        """
                                import org.assertj.core.api.Assertions;
                                
                                class Test {
                                    void test() {
                                        Assertions.assertThatThrownBy(() -> {
                                        }).isInstanceOf(IllegalStateException.class);
                                    }
                                }
                                """
                ));
    }

    @Test
    void testAssertThatThrownByWithFailMessageStringIsExactlyInstanceOfRecipe() {
        Recipe recipe = RewriteUtils.loadRecipe("JUnitToAssertJRulesRecipes.AssertThatThrownByWithFailMessageStringIsExactlyInstanceOfRecipe");
        rewriteRun(
                spec -> spec.recipe(recipe)
                        .expectedCyclesThatMakeChanges(1).cycles(1)
                        .parser(JavaParser.fromJavaVersion().classpath(JavaParser.runtimeClasspath())),
                java(
                        """
                                import org.junit.jupiter.api.Assertions;
                                
                                class Test {
                                    void test() {
                                        Assertions.assertThrowsExactly(IllegalStateException.class, () -> {
                                        }, "foo");
                                    }
                                }
                                """,
                        """
                                import org.assertj.core.api.Assertions;
                                
                                class Test {
                                    void test() {
                                        Assertions.assertThatThrownBy(() -> {
                                        }).withFailMessage("foo").isExactlyInstanceOf(IllegalStateException.class);
                                    }
                                }
                                """
                ));
    }

    @Test
    void testAssertThatThrownByWithFailMessageStringIsInstanceOfRecipe() {
        Recipe recipe = RewriteUtils.loadRecipe("JUnitToAssertJRulesRecipes.AssertThatThrownByWithFailMessageStringIsInstanceOfRecipe");
        rewriteRun(
                spec -> spec.recipe(recipe)
                        .expectedCyclesThatMakeChanges(1).cycles(1)
                        .parser(JavaParser.fromJavaVersion().classpath(JavaParser.runtimeClasspath())),
                java(
                        """
                                import org.junit.jupiter.api.Assertions;
                                
                                class Test {
                                    void test() {
                                        Assertions.assertThrows(IllegalStateException.class, () -> {
                                        }, "foo");
                                    }
                                }
                                """,
                        """
                                import org.assertj.core.api.Assertions;
                                
                                class Test {
                                    void test() {
                                        Assertions.assertThatThrownBy(() -> {
                                        }).withFailMessage("foo").isInstanceOf(IllegalStateException.class);
                                    }
                                }
                                """
                ));
    }

    @Test
    void testAssertThatThrownByWithFailMessageSupplierIsExactlyInstanceOfRecipe() {
        Recipe recipe = RewriteUtils.loadRecipe("JUnitToAssertJRulesRecipes.AssertThatThrownByWithFailMessageSupplierIsExactlyInstanceOfRecipe");
        rewriteRun(
                spec -> spec.recipe(recipe)
                        .expectedCyclesThatMakeChanges(1).cycles(1)
                        .parser(JavaParser.fromJavaVersion().classpath(JavaParser.runtimeClasspath())),
                java(
                        """
                                import org.junit.jupiter.api.Assertions;
                                
                                class Test {
                                    void test() {
                                        Assertions.assertThrowsExactly(IllegalStateException.class, () -> {
                                        }, () -> "foo");
                                    }
                                }
                                """,
                        """
                                import org.assertj.core.api.Assertions;
                                
                                class Test {
                                    void test() {
                                        Assertions.assertThatThrownBy(() -> {
                                        }).withFailMessage(() -> "foo").isExactlyInstanceOf(IllegalStateException.class);
                                    }
                                }
                                """
                ));
    }

    @Test
    void testAssertThatThrownByWithFailMessageSupplierIsInstanceOfRecipe() {
        Recipe recipe = RewriteUtils.loadRecipe("JUnitToAssertJRulesRecipes.AssertThatThrownByWithFailMessageSupplierIsInstanceOfRecipe");
        rewriteRun(
                spec -> spec.recipe(recipe)
                        .expectedCyclesThatMakeChanges(1).cycles(1)
                        .parser(JavaParser.fromJavaVersion().classpath(JavaParser.runtimeClasspath())),
                java(
                        """
                                import org.junit.jupiter.api.Assertions;
                                
                                class Test {
                                    void test() {
                                        Assertions.assertThrows(IllegalStateException.class, () -> {
                                        }, () -> "foo");
                                    }
                                }
                                """,
                        """
                                import org.assertj.core.api.Assertions;
                                
                                class Test {
                                    void test() {
                                        Assertions.assertThatThrownBy(() -> {
                                        }).withFailMessage(() -> "foo").isInstanceOf(IllegalStateException.class);
                                    }
                                }
                                """
                ));
    }

    @Test
    void testAssertThatWithFailMessageStringIsFalseRecipe() {
        Recipe recipe = RewriteUtils.loadRecipe("JUnitToAssertJRulesRecipes.AssertThatWithFailMessageStringIsFalseRecipe");
        rewriteRun(
                spec -> spec.recipe(recipe)
                        .expectedCyclesThatMakeChanges(1).cycles(1)
                        .parser(JavaParser.fromJavaVersion().classpath(JavaParser.runtimeClasspath())),
                java(
                        """
                                import org.junit.jupiter.api.Assertions;
                                
                                class Test {
                                    void test() {
                                        Assertions.assertFalse(true, "foo");
                                    }
                                }
                                """,
                        """
                                import org.assertj.core.api.Assertions;
                                
                                class Test {
                                    void test() {
                                        Assertions.assertThat(true).withFailMessage("foo").isFalse();
                                    }
                                }
                                """
                ));
    }

    @Test
    void testAssertThatWithFailMessageStringIsInstanceOfRecipe() {
        Recipe recipe = RewriteUtils.loadRecipe("JUnitToAssertJRulesRecipes.AssertThatWithFailMessageStringIsInstanceOfRecipe");
        rewriteRun(
                spec -> spec.recipe(recipe)
                        .expectedCyclesThatMakeChanges(1).cycles(1)
                        .parser(JavaParser.fromJavaVersion().classpath(JavaParser.runtimeClasspath())),
                java(
                        """
                                import org.junit.jupiter.api.Assertions;
                                
                                class Test {
                                    void test() {
                                        Assertions.assertInstanceOf(Object.class, new Object(), "foo");
                                    }
                                }
                                """,
                        """
                                import org.assertj.core.api.Assertions;
                                
                                class Test {
                                    void test() {
                                        Assertions.assertThat(new Object()).withFailMessage("foo").isInstanceOf(Object.class);
                                    }
                                }
                                """
                ));
    }

    @Test
    void testAssertThatWithFailMessageStringIsNotNullRecipe() {
        Recipe recipe = RewriteUtils.loadRecipe("JUnitToAssertJRulesRecipes.AssertThatWithFailMessageStringIsNotNullRecipe");
        rewriteRun(
                spec -> spec.recipe(recipe)
                        .expectedCyclesThatMakeChanges(1).cycles(1)
                        .parser(JavaParser.fromJavaVersion().classpath(JavaParser.runtimeClasspath())),
                java(
                        """
                                import org.junit.jupiter.api.Assertions;
                                
                                class Test {
                                    void test() {
                                        Assertions.assertNotNull(new Object(), "foo");
                                    }
                                }
                                """,
                        """
                                import org.assertj.core.api.Assertions;
                                
                                class Test {
                                    void test() {
                                        Assertions.assertThat(new Object()).withFailMessage("foo").isNotNull();
                                    }
                                }
                                """
                ));
    }

    @Test
    void testAssertThatWithFailMessageStringIsNotSameAsRecipe() {
        Recipe recipe = RewriteUtils.loadRecipe("JUnitToAssertJRulesRecipes.AssertThatWithFailMessageStringIsNotSameAsRecipe");
        rewriteRun(
                spec -> spec.recipe(recipe)
                        .expectedCyclesThatMakeChanges(1).cycles(1)
                        .parser(JavaParser.fromJavaVersion().classpath(JavaParser.runtimeClasspath())),
                java(
                        """
                                import org.junit.jupiter.api.Assertions;
                                
                                class Test {
                                    void test() {
                                        Assertions.assertNotSame("foo", "bar", "baz");
                                    }
                                }
                                """,
                        """
                                import org.assertj.core.api.Assertions;
                                
                                class Test {
                                    void test() {
                                        Assertions.assertThat("bar").withFailMessage("baz").isNotSameAs("foo");
                                    }
                                }
                                """
                ));
    }

    @Test
    void testAssertThatWithFailMessageStringIsNullRecipe() {
        Recipe recipe = RewriteUtils.loadRecipe("JUnitToAssertJRulesRecipes.AssertThatWithFailMessageStringIsNullRecipe");
        rewriteRun(
                spec -> spec.recipe(recipe)
                        .expectedCyclesThatMakeChanges(1).cycles(1)
                        .parser(JavaParser.fromJavaVersion().classpath(JavaParser.runtimeClasspath())),
                java(
                        """
                                import org.junit.jupiter.api.Assertions;
                                
                                class Test {
                                    void test() {
                                        Assertions.assertNull(new Object(), "foo");
                                    }
                                }
                                """,
                        """
                                import org.assertj.core.api.Assertions;
                                
                                class Test {
                                    void test() {
                                        Assertions.assertThat(new Object()).withFailMessage("foo").isNull();
                                    }
                                }
                                """
                ));
    }

    @Test
    void testAssertThatWithFailMessageStringIsSameAsRecipe() {
        Recipe recipe = RewriteUtils.loadRecipe("JUnitToAssertJRulesRecipes.AssertThatWithFailMessageStringIsSameAsRecipe");
        rewriteRun(
                spec -> spec.recipe(recipe)
                        .expectedCyclesThatMakeChanges(1).cycles(1)
                        .parser(JavaParser.fromJavaVersion().classpath(JavaParser.runtimeClasspath())),
                java(
                        """
                                import org.junit.jupiter.api.Assertions;
                                
                                class Test {
                                    void test() {
                                        Assertions.assertSame("foo", "bar", "baz");
                                    }
                                }
                                """,
                        """
                                import org.assertj.core.api.Assertions;
                                
                                class Test {
                                    void test() {
                                        Assertions.assertThat("bar").withFailMessage("baz").isSameAs("foo");
                                    }
                                }
                                """
                ));
    }

    @Test
    void testAssertThatWithFailMessageStringIsTrueRecipe() {
        Recipe recipe = RewriteUtils.loadRecipe("JUnitToAssertJRulesRecipes.AssertThatWithFailMessageStringIsTrueRecipe");
        rewriteRun(
                spec -> spec.recipe(recipe)
                        .expectedCyclesThatMakeChanges(1).cycles(1)
                        .parser(JavaParser.fromJavaVersion().classpath(JavaParser.runtimeClasspath())),
                java(
                        """
                                import org.junit.jupiter.api.Assertions;
                                
                                class Test {
                                    void test() {
                                        Assertions.assertTrue(true, "foo");
                                    }
                                }
                                """,
                        """
                                import org.assertj.core.api.Assertions;
                                
                                class Test {
                                    void test() {
                                        Assertions.assertThat(true).withFailMessage("foo").isTrue();
                                    }
                                }
                                """
                ));
    }

    @Test
    void testAssertThatWithFailMessageSupplierIsFalseRecipe() {
        Recipe recipe = RewriteUtils.loadRecipe("JUnitToAssertJRulesRecipes.AssertThatWithFailMessageSupplierIsFalseRecipe");
        rewriteRun(
                spec -> spec.recipe(recipe)
                        .expectedCyclesThatMakeChanges(1).cycles(1)
                        .parser(JavaParser.fromJavaVersion().classpath(JavaParser.runtimeClasspath())),
                java(
                        """
                                import org.junit.jupiter.api.Assertions;
                                
                                class Test {
                                    void test() {
                                        Assertions.assertFalse(true, () -> "foo");
                                    }
                                }
                                """,
                        """
                                import org.assertj.core.api.Assertions;
                                
                                class Test {
                                    void test() {
                                        Assertions.assertThat(true).withFailMessage(() -> "foo").isFalse();
                                    }
                                }
                                """
                ));
    }

    @Test
    void testAssertThatWithFailMessageSupplierIsInstanceOfRecipe() {
        Recipe recipe = RewriteUtils.loadRecipe("JUnitToAssertJRulesRecipes.AssertThatWithFailMessageSupplierIsInstanceOfRecipe");
        rewriteRun(
                spec -> spec.recipe(recipe)
                        .expectedCyclesThatMakeChanges(1).cycles(1)
                        .parser(JavaParser.fromJavaVersion().classpath(JavaParser.runtimeClasspath())),
                java(
                        """
                                import org.junit.jupiter.api.Assertions;
                                
                                class Test {
                                    void test() {
                                        Assertions.assertInstanceOf(Object.class, new Object(), () -> "foo");
                                    }
                                }
                                """,
                        """
                                import org.assertj.core.api.Assertions;
                                
                                class Test {
                                    void test() {
                                        Assertions.assertThat(new Object()).withFailMessage(() -> "foo").isInstanceOf(Object.class);
                                    }
                                }
                                """
                ));
    }

    @Test
    void testAssertThatWithFailMessageSupplierIsNotNullRecipe() {
        Recipe recipe = RewriteUtils.loadRecipe("JUnitToAssertJRulesRecipes.AssertThatWithFailMessageSupplierIsNotNullRecipe");
        rewriteRun(
                spec -> spec.recipe(recipe)
                        .expectedCyclesThatMakeChanges(1).cycles(1)
                        .parser(JavaParser.fromJavaVersion().classpath(JavaParser.runtimeClasspath())),
                java(
                        """
                                import org.junit.jupiter.api.Assertions;
                                
                                class Test {
                                    void test() {
                                        Assertions.assertNotNull(new Object(), () -> "foo");
                                    }
                                }
                                """,
                        """
                                import org.assertj.core.api.Assertions;
                                
                                class Test {
                                    void test() {
                                        Assertions.assertThat(new Object()).withFailMessage(() -> "foo").isNotNull();
                                    }
                                }
                                """
                ));
    }

    @Test
    void testAssertThatWithFailMessageSupplierIsNotSameAsRecipe() {
        Recipe recipe = RewriteUtils.loadRecipe("JUnitToAssertJRulesRecipes.AssertThatWithFailMessageSupplierIsNotSameAsRecipe");
        rewriteRun(
                spec -> spec.recipe(recipe)
                        .expectedCyclesThatMakeChanges(1).cycles(1)
                        .parser(JavaParser.fromJavaVersion().classpath(JavaParser.runtimeClasspath())),
                java(
                        """
                                import org.junit.jupiter.api.Assertions;
                                
                                class Test {
                                    void test() {
                                        Assertions.assertNotSame("foo", "bar", () -> "baz");
                                    }
                                }
                                """,
                        """
                                import org.assertj.core.api.Assertions;
                                
                                class Test {
                                    void test() {
                                        Assertions.assertThat("bar").withFailMessage(() -> "baz").isNotSameAs("foo");
                                    }
                                }
                                """
                ));
    }

    @Test
    void testAssertThatWithFailMessageSupplierIsNullRecipe() {
        Recipe recipe = RewriteUtils.loadRecipe("JUnitToAssertJRulesRecipes.AssertThatWithFailMessageSupplierIsNullRecipe");
        rewriteRun(
                spec -> spec.recipe(recipe)
                        .expectedCyclesThatMakeChanges(1).cycles(1)
                        .parser(JavaParser.fromJavaVersion().classpath(JavaParser.runtimeClasspath())),
                java(
                        """
                                import org.junit.jupiter.api.Assertions;
                                
                                class Test {
                                    void test() {
                                        Assertions.assertNull(new Object(), () -> "foo");
                                    }
                                }
                                """,
                        """
                                import org.assertj.core.api.Assertions;
                                
                                class Test {
                                    void test() {
                                        Assertions.assertThat(new Object()).withFailMessage(() -> "foo").isNull();
                                    }
                                }
                                """
                ));
    }

    @Test
    void testAssertThatWithFailMessageSupplierIsSameAsRecipe() {
        Recipe recipe = RewriteUtils.loadRecipe("JUnitToAssertJRulesRecipes.AssertThatWithFailMessageSupplierIsSameAsRecipe");
        rewriteRun(
                spec -> spec.recipe(recipe)
                        .expectedCyclesThatMakeChanges(1).cycles(1)
                        .parser(JavaParser.fromJavaVersion().classpath(JavaParser.runtimeClasspath())),
                java(
                        """
                                import org.junit.jupiter.api.Assertions;
                                
                                class Test {
                                    void test() {
                                        Assertions.assertSame("foo", "bar", () -> "baz");
                                    }
                                }
                                """,
                        """
                                import org.assertj.core.api.Assertions;
                                
                                class Test {
                                    void test() {
                                        Assertions.assertThat("bar").withFailMessage(() -> "baz").isSameAs("foo");
                                    }
                                }
                                """
                ));
    }

    @Test
    void testAssertThatWithFailMessageSupplierIsTrueRecipe() {
        Recipe recipe = RewriteUtils.loadRecipe("JUnitToAssertJRulesRecipes.AssertThatWithFailMessageSupplierIsTrueRecipe");
        rewriteRun(
                spec -> spec.recipe(recipe)
                        .expectedCyclesThatMakeChanges(1).cycles(1)
                        .parser(JavaParser.fromJavaVersion().classpath(JavaParser.runtimeClasspath())),
                java(
                        """
                                import org.junit.jupiter.api.Assertions;
                                
                                class Test {
                                    void test() {
                                        Assertions.assertTrue(true, () -> "foo");
                                    }
                                }
                                """,
                        """
                                import org.assertj.core.api.Assertions;
                                
                                class Test {
                                    void test() {
                                        Assertions.assertThat(true).withFailMessage(() -> "foo").isTrue();
                                    }
                                }
                                """
                ));
    }

    @Test
    void testFailRecipe() {
        Recipe recipe = RewriteUtils.loadRecipe("JUnitToAssertJRulesRecipes.FailRecipe");
        rewriteRun(
                spec -> spec.recipe(recipe)
                        .expectedCyclesThatMakeChanges(1).cycles(1)
                        .parser(JavaParser.fromJavaVersion().classpath(JavaParser.runtimeClasspath())),
                java(
                        """
                                import org.junit.jupiter.api.Assertions;
                                
                                class Test {
                                    Object test() {
                                        return Assertions.fail();
                                    }
                                }
                                """,
                        """
                                import org.assertj.core.api.Assertions;
                                
                                class Test {
                                    Object test() {
                                        return Assertions.fail();
                                    }
                                }
                                """
                ));
    }

    @Test
    void testFailWithMessageRecipe() {
        Recipe recipe = RewriteUtils.loadRecipe("JUnitToAssertJRulesRecipes.FailWithMessageRecipe");
        rewriteRun(
                spec -> spec.recipe(recipe)
                        .expectedCyclesThatMakeChanges(1).cycles(1)
                        .parser(JavaParser.fromJavaVersion().classpath(JavaParser.runtimeClasspath())),
                java(
                        """
                                import org.junit.jupiter.api.Assertions;
                                
                                class Test {
                                    Object test() {
                                        return Assertions.fail("foo");
                                    }
                                }
                                """,
                        """
                                import org.assertj.core.api.Assertions;
                                
                                class Test {
                                    Object test() {
                                        return Assertions.fail("foo");
                                    }
                                }
                                """
                ));
    }

    @Test
    void testFailWithMessageAndThrowableRecipe() {
        Recipe recipe = RewriteUtils.loadRecipe("JUnitToAssertJRulesRecipes.FailWithMessageAndThrowableRecipe");
        rewriteRun(
                spec -> spec.recipe(recipe)
                        .expectedCyclesThatMakeChanges(1).cycles(1)
                        .parser(JavaParser.fromJavaVersion().classpath(JavaParser.runtimeClasspath())),
                java(
                        """
                                import org.junit.jupiter.api.Assertions;
                                
                                class Test {
                                    Object test() {
                                        return Assertions.fail("foo", new IllegalStateException());
                                    }
                                }
                                """,
                        """
                                import org.assertj.core.api.Assertions;
                                
                                class Test {
                                    Object test() {
                                        return Assertions.fail("foo", new IllegalStateException());
                                    }
                                }
                                """
                ));
    }

    @Test
    void testFailWithThrowableRecipe() {
        Recipe recipe = RewriteUtils.loadRecipe("JUnitToAssertJRulesRecipes.FailWithThrowableRecipe");
        rewriteRun(
                spec -> spec.recipe(recipe)
                        .expectedCyclesThatMakeChanges(1).cycles(1)
                        .parser(JavaParser.fromJavaVersion().classpath(JavaParser.runtimeClasspath())),
                java(
                        """
                                import org.junit.jupiter.api.Assertions;
                                
                                class Test {
                                    Object test() {
                                        return Assertions.fail(new IllegalStateException());
                                    }
                                }
                                """,
                        """
                                import org.assertj.core.api.Assertions;
                                
                                class Test {
                                    Object test() {
                                        return Assertions.fail(new IllegalStateException());
                                    }
                                }
                                """
                ));
    }
}
