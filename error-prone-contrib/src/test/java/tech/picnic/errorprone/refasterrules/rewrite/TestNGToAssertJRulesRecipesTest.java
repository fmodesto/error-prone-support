package tech.picnic.errorprone.refasterrules.rewrite;

import org.junit.jupiter.api.Test;
import org.openrewrite.Recipe;
import org.openrewrite.java.JavaParser;
import org.openrewrite.test.RewriteTest;

import static org.openrewrite.java.Assertions.java;

final class TestNGToAssertJRulesRecipesTest implements RewriteTest {

    @Test
    void testAssertEqualRecipe() {
        Recipe recipe = RewriteUtils.loadRecipe("TestNGToAssertJRulesRecipes.AssertEqualRecipe");
        rewriteRun(
                spec -> spec.recipe(recipe)
                        .expectedCyclesThatMakeChanges(1).cycles(1)
                        .parser(JavaParser.fromJavaVersion().classpath(JavaParser.runtimeClasspath())),
                java(
                        """
                                import com.google.common.collect.ImmutableMap;
                                import org.testng.Assert;
                                
                                class Test {
                                    void test() {
                                        Assert.assertEquals(true, false);
                                        Assert.assertEquals(true, Boolean.FALSE);
                                        Assert.assertEquals(Boolean.TRUE, false);
                                        Assert.assertEquals(Boolean.TRUE, Boolean.FALSE);
                                        Assert.assertEquals((byte) 0, (byte) 1);
                                        Assert.assertEquals((byte) 0, Byte.decode("1"));
                                        Assert.assertEquals(Byte.decode("0"), (byte) 1);
                                        Assert.assertEquals(Byte.decode("0"), Byte.decode("1"));
                                        Assert.assertEquals('a', 'b');
                                        Assert.assertEquals('a', Character.valueOf('b'));
                                        Assert.assertEquals(Character.valueOf('a'), 'b');
                                        Assert.assertEquals(Character.valueOf('a'), Character.valueOf('b'));
                                        Assert.assertEquals((short) 0, (short) 1);
                                        Assert.assertEquals((short) 0, Short.decode("1"));
                                        Assert.assertEquals(Short.decode("0"), (short) 1);
                                        Assert.assertEquals(Short.decode("0"), Short.decode("1"));
                                        Assert.assertEquals(0, 1);
                                        Assert.assertEquals(0, Integer.valueOf(1));
                                        Assert.assertEquals(Integer.valueOf(0), 1);
                                        Assert.assertEquals(Integer.valueOf(0), Integer.valueOf(1));
                                        Assert.assertEquals(0L, 1L);
                                        Assert.assertEquals(0L, Long.valueOf(1));
                                        Assert.assertEquals(Long.valueOf(0), 1L);
                                        Assert.assertEquals(Long.valueOf(0), Long.valueOf(1));
                                        Assert.assertEquals(0.0F, 1.0F);
                                        Assert.assertEquals(0.0F, Float.valueOf(1.0F));
                                        Assert.assertEquals(Float.valueOf(0.0F), 1.0F);
                                        Assert.assertEquals(Float.valueOf(0.0F), Float.valueOf(1.0F));
                                        Assert.assertEquals(0.0, 1.0);
                                        Assert.assertEquals(0.0, Double.valueOf(1.0));
                                        Assert.assertEquals(Double.valueOf(0.0), 1.0);
                                        Assert.assertEquals(Double.valueOf(0.0), Double.valueOf(1.0));
                                        Assert.assertEquals(new Object(), new StringBuilder());
                                        Assert.assertEquals("actual", "expected");
                                        Assert.assertEquals(ImmutableMap.of(), ImmutableMap.of(1, 2));
                                    }
                                }
                                """,
                        """
                                import com.google.common.collect.ImmutableMap;
                                import org.assertj.core.api.Assertions;
                                
                                class Test {
                                    void test() {
                                        Assertions.assertThat(true).isEqualTo(false);
                                        Assertions.assertThat(true).isEqualTo(Boolean.FALSE);
                                        Assertions.assertThat(Boolean.TRUE).isEqualTo(false);
                                        Assertions.assertThat(Boolean.TRUE).isEqualTo(Boolean.FALSE);
                                        Assertions.assertThat((byte) 0).isEqualTo((byte) 1);
                                        Assertions.assertThat((byte) 0).isEqualTo(Byte.decode("1"));
                                        Assertions.assertThat(Byte.decode("0")).isEqualTo((byte) 1);
                                        Assertions.assertThat(Byte.decode("0")).isEqualTo(Byte.decode("1"));
                                        Assertions.assertThat('a').isEqualTo('b');
                                        Assertions.assertThat('a').isEqualTo(Character.valueOf('b'));
                                        Assertions.assertThat(Character.valueOf('a')).isEqualTo('b');
                                        Assertions.assertThat(Character.valueOf('a')).isEqualTo(Character.valueOf('b'));
                                        Assertions.assertThat((short) 0).isEqualTo((short) 1);
                                        Assertions.assertThat((short) 0).isEqualTo(Short.decode("1"));
                                        Assertions.assertThat(Short.decode("0")).isEqualTo((short) 1);
                                        Assertions.assertThat(Short.decode("0")).isEqualTo(Short.decode("1"));
                                        Assertions.assertThat(0).isEqualTo(1);
                                        Assertions.assertThat(0).isEqualTo(Integer.valueOf(1));
                                        Assertions.assertThat(Integer.valueOf(0)).isEqualTo(1);
                                        Assertions.assertThat(Integer.valueOf(0)).isEqualTo(Integer.valueOf(1));
                                        Assertions.assertThat(0L).isEqualTo(1L);
                                        Assertions.assertThat(0L).isEqualTo(Long.valueOf(1));
                                        Assertions.assertThat(Long.valueOf(0)).isEqualTo(1L);
                                        Assertions.assertThat(Long.valueOf(0)).isEqualTo(Long.valueOf(1));
                                        Assertions.assertThat(0.0F).isEqualTo(1.0F);
                                        Assertions.assertThat(0.0F).isEqualTo(Float.valueOf(1.0F));
                                        Assertions.assertThat(Float.valueOf(0.0F)).isEqualTo(1.0F);
                                        Assertions.assertThat(Float.valueOf(0.0F)).isEqualTo(Float.valueOf(1.0F));
                                        Assertions.assertThat(0.0).isEqualTo(1.0);
                                        Assertions.assertThat(0.0).isEqualTo(Double.valueOf(1.0));
                                        Assertions.assertThat(Double.valueOf(0.0)).isEqualTo(1.0);
                                        Assertions.assertThat(Double.valueOf(0.0)).isEqualTo(Double.valueOf(1.0));
                                        Assertions.assertThat(new Object()).isEqualTo(new StringBuilder());
                                        Assertions.assertThat("actual").isEqualTo("expected");
                                        Assertions.assertThat(ImmutableMap.of()).isEqualTo(ImmutableMap.of(1, 2));
                                    }
                                }
                                """
                ));
    }

    @Test
    void testAssertEqualArrayIterationOrderRecipe() {
        Recipe recipe = RewriteUtils.loadRecipe("TestNGToAssertJRulesRecipes.AssertEqualArrayIterationOrderRecipe");
        rewriteRun(
                spec -> spec.recipe(recipe)
                        .expectedCyclesThatMakeChanges(1).cycles(1)
                        .parser(JavaParser.fromJavaVersion().classpath(JavaParser.runtimeClasspath())),
                java(
                        """
                                import org.testng.Assert;
                                
                                class Test {
                                    void test() {
                                        Assert.assertEquals(new boolean[0], new boolean[0]);
                                        Assert.assertEquals(new byte[0], new byte[0]);
                                        Assert.assertEquals(new char[0], new char[0]);
                                        Assert.assertEquals(new short[0], new short[0]);
                                        Assert.assertEquals(new int[0], new int[0]);
                                        Assert.assertEquals(new long[0], new long[0]);
                                        Assert.assertEquals(new float[0], new float[0]);
                                        Assert.assertEquals(new double[0], new double[0]);
                                        Assert.assertEquals(new Object[0], new Object[0]);
                                    }
                                }
                                """,
                        """
                                import org.assertj.core.api.Assertions;
                                
                                class Test {
                                    void test() {
                                        Assertions.assertThat(new boolean[0]).containsExactly(new boolean[0]);
                                        Assertions.assertThat(new byte[0]).containsExactly(new byte[0]);
                                        Assertions.assertThat(new char[0]).containsExactly(new char[0]);
                                        Assertions.assertThat(new short[0]).containsExactly(new short[0]);
                                        Assertions.assertThat(new int[0]).containsExactly(new int[0]);
                                        Assertions.assertThat(new long[0]).containsExactly(new long[0]);
                                        Assertions.assertThat(new float[0]).containsExactly(new float[0]);
                                        Assertions.assertThat(new double[0]).containsExactly(new double[0]);
                                        Assertions.assertThat(new Object[0]).containsExactly(new Object[0]);
                                    }
                                }
                                """
                ));
    }

    @Test
    void testAssertEqualArrayIterationOrderWithMessageRecipe() {
        Recipe recipe = RewriteUtils.loadRecipe("TestNGToAssertJRulesRecipes.AssertEqualArrayIterationOrderWithMessageRecipe");
        rewriteRun(
                spec -> spec.recipe(recipe)
                        .expectedCyclesThatMakeChanges(1).cycles(1)
                        .parser(JavaParser.fromJavaVersion().classpath(JavaParser.runtimeClasspath())),
                java(
                        """
                                import org.testng.Assert;
                                
                                class Test {
                                    void test() {
                                        Assert.assertEquals(new boolean[0], new boolean[0], "foo");
                                        Assert.assertEquals(new byte[0], new byte[0], "bar");
                                        Assert.assertEquals(new char[0], new char[0], "baz");
                                        Assert.assertEquals(new short[0], new short[0], "qux");
                                        Assert.assertEquals(new int[0], new int[0], "quux");
                                        Assert.assertEquals(new long[0], new long[0], "quuz");
                                        Assert.assertEquals(new float[0], new float[0], "corge");
                                        Assert.assertEquals(new double[0], new double[0], "grault");
                                        Assert.assertEquals(new Object[0], new Object[0], "garply");
                                    }
                                }
                                """,
                        """
                                import org.assertj.core.api.Assertions;
                                
                                class Test {
                                    void test() {
                                        Assertions.assertThat(new boolean[0]).withFailMessage("foo").containsExactly(new boolean[0]);
                                        Assertions.assertThat(new byte[0]).withFailMessage("bar").containsExactly(new byte[0]);
                                        Assertions.assertThat(new char[0]).withFailMessage("baz").containsExactly(new char[0]);
                                        Assertions.assertThat(new short[0]).withFailMessage("qux").containsExactly(new short[0]);
                                        Assertions.assertThat(new int[0]).withFailMessage("quux").containsExactly(new int[0]);
                                        Assertions.assertThat(new long[0]).withFailMessage("quuz").containsExactly(new long[0]);
                                        Assertions.assertThat(new float[0]).withFailMessage("corge").containsExactly(new float[0]);
                                        Assertions.assertThat(new double[0]).withFailMessage("grault").containsExactly(new double[0]);
                                        Assertions.assertThat(new Object[0]).withFailMessage("garply").containsExactly(new Object[0]);
                                    }
                                }
                                """
                ));
    }

    @Test
    void testAssertEqualArraysIrrespectiveOfOrderRecipe() {
        Recipe recipe = RewriteUtils.loadRecipe("TestNGToAssertJRulesRecipes.AssertEqualArraysIrrespectiveOfOrderRecipe");
        rewriteRun(
                spec -> spec.recipe(recipe)
                        .expectedCyclesThatMakeChanges(1).cycles(1)
                        .parser(JavaParser.fromJavaVersion().classpath(JavaParser.runtimeClasspath())),
                java(
                        """
                                import org.testng.Assert;
                                
                                class Test {
                                    void test() {
                                        Assert.assertEqualsNoOrder(new Object[0], new Object[0]);
                                    }
                                }
                                """,
                        """
                                import org.assertj.core.api.Assertions;
                                
                                class Test {
                                    void test() {
                                        Assertions.assertThat(new Object[0]).containsExactlyInAnyOrder(new Object[0]);
                                    }
                                }
                                """
                ));
    }

    @Test
    void testAssertEqualArraysIrrespectiveOfOrderWithMessageRecipe() {
        Recipe recipe = RewriteUtils.loadRecipe("TestNGToAssertJRulesRecipes.AssertEqualArraysIrrespectiveOfOrderWithMessageRecipe");
        rewriteRun(
                spec -> spec.recipe(recipe)
                        .expectedCyclesThatMakeChanges(1).cycles(1)
                        .parser(JavaParser.fromJavaVersion().classpath(JavaParser.runtimeClasspath())),
                java(
                        """
                                import org.testng.Assert;
                                
                                class Test {
                                    void test() {
                                        Assert.assertEqualsNoOrder(new Object[0], new Object[0], "foo");
                                    }
                                }
                                """,
                        """
                                import org.assertj.core.api.Assertions;
                                
                                class Test {
                                    void test() {
                                        Assertions.assertThat(new Object[0]).withFailMessage("foo").containsExactlyInAnyOrder(new Object[0]);
                                    }
                                }
                                """
                ));
    }

    @Test
    void testAssertEqualDoubleArraysWithDeltaRecipe() {
        Recipe recipe = RewriteUtils.loadRecipe("TestNGToAssertJRulesRecipes.AssertEqualDoubleArraysWithDeltaRecipe");
        rewriteRun(
                spec -> spec.recipe(recipe)
                        .expectedCyclesThatMakeChanges(1).cycles(1)
                        .parser(JavaParser.fromJavaVersion().classpath(JavaParser.runtimeClasspath())),
                java(
                        """
                                import org.testng.Assert;
                                
                                class Test {
                                    void test() {
                                        Assert.assertEquals(new double[0], new double[0], 0.0);
                                    }
                                }
                                """,
                        """
                                import org.assertj.core.api.Assertions;
                                import org.assertj.core.data.Offset;
                                
                                class Test {
                                    void test() {
                                        Assertions.assertThat(new double[0]).containsExactly(new double[0], Offset.offset(0.0));
                                    }
                                }
                                """
                ));
    }

    @Test
    void testAssertEqualDoubleArraysWithDeltaWithMessageRecipe() {
        Recipe recipe = RewriteUtils.loadRecipe("TestNGToAssertJRulesRecipes.AssertEqualDoubleArraysWithDeltaWithMessageRecipe");
        rewriteRun(
                spec -> spec.recipe(recipe)
                        .expectedCyclesThatMakeChanges(1).cycles(1)
                        .parser(JavaParser.fromJavaVersion().classpath(JavaParser.runtimeClasspath())),
                java(
                        """
                                import org.testng.Assert;
                                
                                class Test {
                                    void test() {
                                        Assert.assertEquals(new double[0], new double[0], 0.0, "foo");
                                    }
                                }
                                """,
                        """
                                import org.assertj.core.api.Assertions;
                                import org.assertj.core.data.Offset;
                                
                                class Test {
                                    void test() {
                                        Assertions.assertThat(new double[0]).withFailMessage("foo").containsExactly(new double[0], Offset.offset(0.0));
                                    }
                                }
                                """
                ));
    }

    @Test
    void testAssertEqualDoublesWithDeltaRecipe() {
        Recipe recipe = RewriteUtils.loadRecipe("TestNGToAssertJRulesRecipes.AssertEqualDoublesWithDeltaRecipe");
        rewriteRun(
                spec -> spec.recipe(recipe)
                        .expectedCyclesThatMakeChanges(1).cycles(1)
                        .parser(JavaParser.fromJavaVersion().classpath(JavaParser.runtimeClasspath())),
                java(
                        """
                                import org.testng.Assert;
                                
                                class Test {
                                    void test() {
                                        Assert.assertEquals(0.0, 0.0, 0.0);
                                    }
                                }
                                """,
                        """
                                import org.assertj.core.api.Assertions;
                                import org.assertj.core.data.Offset;
                                
                                class Test {
                                    void test() {
                                        Assertions.assertThat(0.0).isCloseTo(0.0, Offset.offset(0.0));
                                    }
                                }
                                """
                ));
    }

    @Test
    void testAssertEqualDoublesWithDeltaWithMessageRecipe() {
        Recipe recipe = RewriteUtils.loadRecipe("TestNGToAssertJRulesRecipes.AssertEqualDoublesWithDeltaWithMessageRecipe");
        rewriteRun(
                spec -> spec.recipe(recipe)
                        .expectedCyclesThatMakeChanges(1).cycles(1)
                        .parser(JavaParser.fromJavaVersion().classpath(JavaParser.runtimeClasspath())),
                java(
                        """
                                import org.testng.Assert;
                                
                                class Test {
                                    void test() {
                                        Assert.assertEquals(0.0, 0.0, 0.0, "foo");
                                    }
                                }
                                """,
                        """
                                import org.assertj.core.api.Assertions;
                                import org.assertj.core.data.Offset;
                                
                                class Test {
                                    void test() {
                                        Assertions.assertThat(0.0).withFailMessage("foo").isCloseTo(0.0, Offset.offset(0.0));
                                    }
                                }
                                """
                ));
    }

    @Test
    void testAssertEqualFloatArraysWithDeltaRecipe() {
        Recipe recipe = RewriteUtils.loadRecipe("TestNGToAssertJRulesRecipes.AssertEqualFloatArraysWithDeltaRecipe");
        rewriteRun(
                spec -> spec.recipe(recipe)
                        .expectedCyclesThatMakeChanges(1).cycles(1)
                        .parser(JavaParser.fromJavaVersion().classpath(JavaParser.runtimeClasspath())),
                java(
                        """
                                import org.testng.Assert;
                                
                                class Test {
                                    void test() {
                                        Assert.assertEquals(new float[0], new float[0], 0.0F);
                                    }
                                }
                                """,
                        """
                                import org.assertj.core.api.Assertions;
                                import org.assertj.core.data.Offset;
                                
                                class Test {
                                    void test() {
                                        Assertions.assertThat(new float[0]).containsExactly(new float[0], Offset.offset(0.0F));
                                    }
                                }
                                """
                ));
    }

    @Test
    void testAssertEqualFloatArraysWithDeltaWithMessageRecipe() {
        Recipe recipe = RewriteUtils.loadRecipe("TestNGToAssertJRulesRecipes.AssertEqualFloatArraysWithDeltaWithMessageRecipe");
        rewriteRun(
                spec -> spec.recipe(recipe)
                        .expectedCyclesThatMakeChanges(1).cycles(1)
                        .parser(JavaParser.fromJavaVersion().classpath(JavaParser.runtimeClasspath())),
                java(
                        """
                                import org.testng.Assert;
                                
                                class Test {
                                    void test() {
                                        Assert.assertEquals(new float[0], new float[0], 0.0F, "foo");
                                    }
                                }
                                """,
                        """
                                import org.assertj.core.api.Assertions;
                                import org.assertj.core.data.Offset;
                                
                                class Test {
                                    void test() {
                                        Assertions.assertThat(new float[0]).withFailMessage("foo").containsExactly(new float[0], Offset.offset(0.0F));
                                    }
                                }
                                """
                ));
    }

    @Test
    void testAssertEqualFloatsWithDeltaRecipe() {
        Recipe recipe = RewriteUtils.loadRecipe("TestNGToAssertJRulesRecipes.AssertEqualFloatsWithDeltaRecipe");
        rewriteRun(
                spec -> spec.recipe(recipe)
                        .expectedCyclesThatMakeChanges(1).cycles(1)
                        .parser(JavaParser.fromJavaVersion().classpath(JavaParser.runtimeClasspath())),
                java(
                        """
                                import org.testng.Assert;
                                
                                class Test {
                                    void test() {
                                        Assert.assertEquals(0.0F, 0.0F, 0.0F);
                                    }
                                }
                                """,
                        """
                                import org.assertj.core.api.Assertions;
                                import org.assertj.core.data.Offset;
                                
                                class Test {
                                    void test() {
                                        Assertions.assertThat(0.0F).isCloseTo(0.0F, Offset.offset(0.0F));
                                    }
                                }
                                """
                ));
    }

    @Test
    void testAssertEqualFloatsWithDeltaWithMessageRecipe() {
        Recipe recipe = RewriteUtils.loadRecipe("TestNGToAssertJRulesRecipes.AssertEqualFloatsWithDeltaWithMessageRecipe");
        rewriteRun(
                spec -> spec.recipe(recipe)
                        .expectedCyclesThatMakeChanges(1).cycles(1)
                        .parser(JavaParser.fromJavaVersion().classpath(JavaParser.runtimeClasspath())),
                java(
                        """
                                import org.testng.Assert;
                                
                                class Test {
                                    void test() {
                                        Assert.assertEquals(0.0F, 0.0F, 0.0F, "foo");
                                    }
                                }
                                """,
                        """
                                import org.assertj.core.api.Assertions;
                                import org.assertj.core.data.Offset;
                                
                                class Test {
                                    void test() {
                                        Assertions.assertThat(0.0F).withFailMessage("foo").isCloseTo(0.0F, Offset.offset(0.0F));
                                    }
                                }
                                """
                ));
    }

    @Test
    void testAssertEqualIterableIterationOrderRecipe() {
        Recipe recipe = RewriteUtils.loadRecipe("TestNGToAssertJRulesRecipes.AssertEqualIterableIterationOrderRecipe");
        rewriteRun(
                spec -> spec.recipe(recipe)
                        .expectedCyclesThatMakeChanges(1).cycles(1)
                        .parser(JavaParser.fromJavaVersion().classpath(JavaParser.runtimeClasspath())),
                java(
                        """
                                import com.google.common.collect.Iterables;
                                import org.testng.Assert;
                                
                                import java.util.ArrayList;
                                import java.util.Collections;
                                
                                class Test {
                                    void test() {
                                        Assert.assertEquals(Iterables.unmodifiableIterable(new ArrayList<>()), Iterables.unmodifiableIterable(new ArrayList<>()));
                                        Assert.assertEquals(Collections.synchronizedCollection(new ArrayList<>()), Collections.synchronizedCollection(new ArrayList<>()));
                                    }
                                }
                                """,
                        """
                                import com.google.common.collect.Iterables;
                                import org.assertj.core.api.Assertions;
                                
                                import java.util.ArrayList;
                                import java.util.Collections;
                                
                                class Test {
                                    void test() {
                                        Assertions.assertThat(Iterables.unmodifiableIterable(new ArrayList<>())).containsExactlyElementsOf(Iterables.unmodifiableIterable(new ArrayList<>()));
                                        Assertions.assertThat(Collections.synchronizedCollection(new ArrayList<>())).containsExactlyElementsOf(Collections.synchronizedCollection(new ArrayList<>()));
                                    }
                                }
                                """
                ));
    }

    @Test
    void testAssertEqualIterableIterationOrderWithMessageRecipe() {
        Recipe recipe = RewriteUtils.loadRecipe("TestNGToAssertJRulesRecipes.AssertEqualIterableIterationOrderWithMessageRecipe");
        rewriteRun(
                spec -> spec.recipe(recipe)
                        .expectedCyclesThatMakeChanges(1).cycles(1)
                        .parser(JavaParser.fromJavaVersion().classpath(JavaParser.runtimeClasspath())),
                java(
                        """
                                import com.google.common.collect.Iterables;
                                import org.testng.Assert;
                                
                                import java.util.ArrayList;
                                import java.util.Collections;
                                
                                class Test {
                                    void test() {
                                        Assert.assertEquals(Iterables.unmodifiableIterable(new ArrayList<>()), Iterables.unmodifiableIterable(new ArrayList<>()), "foo");
                                        Assert.assertEquals(Collections.synchronizedCollection(new ArrayList<>()), Collections.synchronizedCollection(new ArrayList<>()), "bar");
                                    }
                                }
                                """,
                        """
                                import com.google.common.collect.Iterables;
                                import org.assertj.core.api.Assertions;
                                
                                import java.util.ArrayList;
                                import java.util.Collections;
                                
                                class Test {
                                    void test() {
                                        Assertions.assertThat(Iterables.unmodifiableIterable(new ArrayList<>())).withFailMessage("foo").containsExactlyElementsOf(Iterables.unmodifiableIterable(new ArrayList<>()));
                                        Assertions.assertThat(Collections.synchronizedCollection(new ArrayList<>())).withFailMessage("bar").containsExactlyElementsOf(Collections.synchronizedCollection(new ArrayList<>()));
                                    }
                                }
                                """
                ));
    }

    @Test
    void testAssertEqualIteratorIterationOrderRecipe() {
        Recipe recipe = RewriteUtils.loadRecipe("TestNGToAssertJRulesRecipes.AssertEqualIteratorIterationOrderRecipe");
        rewriteRun(
                spec -> spec.recipe(recipe)
                        .expectedCyclesThatMakeChanges(1).cycles(1)
                        .parser(JavaParser.fromJavaVersion().classpath(JavaParser.runtimeClasspath())),
                java(
                        """
                                import com.google.common.collect.Iterators;
                                import org.testng.Assert;
                                
                                import java.util.ArrayList;
                                
                                class Test {
                                    void test() {
                                        Assert.assertEquals(Iterators.unmodifiableIterator(new ArrayList<>().iterator()), Iterators.unmodifiableIterator(new ArrayList<>().iterator()));
                                    }
                                }
                                """,
                        """
                                import com.google.common.collect.ImmutableList;
                                import com.google.common.collect.Iterators;
                                import org.assertj.core.api.Assertions;
                                
                                import java.util.ArrayList;
                                
                                class Test {
                                    void test() {
                                        Assertions.assertThat(Iterators.unmodifiableIterator(new ArrayList<>().iterator())).toIterable().containsExactlyElementsOf(ImmutableList.copyOf(Iterators.unmodifiableIterator(new ArrayList<>().iterator())));
                                    }
                                }
                                """
                ));
    }

    @Test
    void testAssertEqualIteratorIterationOrderWithMessageRecipe() {
        Recipe recipe = RewriteUtils.loadRecipe("TestNGToAssertJRulesRecipes.AssertEqualIteratorIterationOrderWithMessageRecipe");
        rewriteRun(
                spec -> spec.recipe(recipe)
                        .expectedCyclesThatMakeChanges(1).cycles(1)
                        .parser(JavaParser.fromJavaVersion().classpath(JavaParser.runtimeClasspath())),
                java(
                        """
                                import com.google.common.collect.Iterators;
                                import org.testng.Assert;
                                
                                import java.util.ArrayList;
                                
                                class Test {
                                    void test() {
                                        Assert.assertEquals(Iterators.unmodifiableIterator(new ArrayList<>().iterator()), Iterators.unmodifiableIterator(new ArrayList<>().iterator()), "foo");
                                    }
                                }
                                """,
                        """
                                import com.google.common.collect.ImmutableList;
                                import com.google.common.collect.Iterators;
                                import org.assertj.core.api.Assertions;
                                
                                import java.util.ArrayList;
                                
                                class Test {
                                    void test() {
                                        Assertions.assertThat(Iterators.unmodifiableIterator(new ArrayList<>().iterator())).toIterable().withFailMessage("foo").containsExactlyElementsOf(ImmutableList.copyOf(Iterators.unmodifiableIterator(new ArrayList<>().iterator())));
                                    }
                                }
                                """
                ));
    }

    @Test
    void testAssertEqualSetsRecipe() {
        Recipe recipe = RewriteUtils.loadRecipe("TestNGToAssertJRulesRecipes.AssertEqualSetsRecipe");
        rewriteRun(
                spec -> spec.recipe(recipe)
                        .expectedCyclesThatMakeChanges(1).cycles(1)
                        .parser(JavaParser.fromJavaVersion().classpath(JavaParser.runtimeClasspath())),
                java(
                        """
                                import com.google.common.collect.ImmutableSet;
                                import org.testng.Assert;
                                
                                class Test {
                                    void test() {
                                        Assert.assertEquals(ImmutableSet.of(), ImmutableSet.of());
                                    }
                                }
                                """,
                        """
                                import com.google.common.collect.ImmutableSet;
                                import org.assertj.core.api.Assertions;
                                
                                class Test {
                                    void test() {
                                        Assertions.assertThat(ImmutableSet.of()).hasSameElementsAs(ImmutableSet.of());
                                    }
                                }
                                """
                ));
    }

    @Test
    void testAssertEqualSetsWithMessageRecipe() {
        Recipe recipe = RewriteUtils.loadRecipe("TestNGToAssertJRulesRecipes.AssertEqualSetsWithMessageRecipe");
        rewriteRun(
                spec -> spec.recipe(recipe)
                        .expectedCyclesThatMakeChanges(1).cycles(1)
                        .parser(JavaParser.fromJavaVersion().classpath(JavaParser.runtimeClasspath())),
                java(
                        """
                                import com.google.common.collect.ImmutableSet;
                                import org.testng.Assert;
                                
                                class Test {
                                    void test() {
                                        Assert.assertEquals(ImmutableSet.of(), ImmutableSet.of(), "foo");
                                    }
                                }
                                """,
                        """
                                import com.google.common.collect.ImmutableSet;
                                import org.assertj.core.api.Assertions;
                                
                                class Test {
                                    void test() {
                                        Assertions.assertThat(ImmutableSet.of()).withFailMessage("foo").hasSameElementsAs(ImmutableSet.of());
                                    }
                                }
                                """
                ));
    }

    @Test
    void testAssertEqualWithMessageRecipe() {
        Recipe recipe = RewriteUtils.loadRecipe("TestNGToAssertJRulesRecipes.AssertEqualWithMessageRecipe");
        rewriteRun(
                spec -> spec.recipe(recipe)
                        .expectedCyclesThatMakeChanges(1).cycles(1)
                        .parser(JavaParser.fromJavaVersion().classpath(JavaParser.runtimeClasspath())),
                java(
                        """
                                import com.google.common.collect.ImmutableMap;
                                import org.testng.Assert;
                                
                                class Test {
                                    void test() {
                                        Assert.assertEquals(true, false, "foo");
                                        Assert.assertEquals(true, Boolean.FALSE, "bar");
                                        Assert.assertEquals(Boolean.TRUE, false, "baz");
                                        Assert.assertEquals(Boolean.TRUE, Boolean.FALSE, "qux");
                                        Assert.assertEquals((byte) 0, (byte) 1, "quux");
                                        Assert.assertEquals((byte) 0, Byte.decode("1"), "corge");
                                        Assert.assertEquals(Byte.decode("0"), (byte) 1, "grault");
                                        Assert.assertEquals(Byte.decode("0"), Byte.decode("1"), "garply");
                                        Assert.assertEquals('a', 'b', "waldo");
                                        Assert.assertEquals('a', Character.valueOf('b'), "fred");
                                        Assert.assertEquals(Character.valueOf('a'), 'b', "plugh");
                                        Assert.assertEquals(Character.valueOf('a'), Character.valueOf('b'), "xyzzy");
                                        Assert.assertEquals((short) 0, (short) 1, "thud");
                                        Assert.assertEquals((short) 0, Short.decode("1"), "foo");
                                        Assert.assertEquals(Short.decode("0"), (short) 1, "bar");
                                        Assert.assertEquals(Short.decode("0"), Short.decode("1"), "baz");
                                        Assert.assertEquals(0, 1, "qux");
                                        Assert.assertEquals(0, Integer.valueOf(1), "quux");
                                        Assert.assertEquals(Integer.valueOf(0), 1, "corge");
                                        Assert.assertEquals(Integer.valueOf(0), Integer.valueOf(1), "grault");
                                        Assert.assertEquals(0L, 1L, "garply");
                                        Assert.assertEquals(0L, Long.valueOf(1), "waldo");
                                        Assert.assertEquals(Long.valueOf(0), 1L, "fred");
                                        Assert.assertEquals(Long.valueOf(0), Long.valueOf(1), "plugh");
                                        Assert.assertEquals(0.0F, 1.0F, "xyzzy");
                                        Assert.assertEquals(0.0F, Float.valueOf(1.0F), "thud");
                                        Assert.assertEquals(Float.valueOf(0.0F), 1.0F, "foo");
                                        Assert.assertEquals(Float.valueOf(0.0F), Float.valueOf(1.0F), "bar");
                                        Assert.assertEquals(0.0, 1.0, "baz");
                                        Assert.assertEquals(0.0, Double.valueOf(1.0), "qux");
                                        Assert.assertEquals(Double.valueOf(0.0), 1.0, "quux");
                                        Assert.assertEquals(Double.valueOf(0.0), Double.valueOf(1.0), "corge");
                                        Assert.assertEquals(new Object(), new StringBuilder(), "grault");
                                        Assert.assertEquals("actual", "expected", "garply");
                                        Assert.assertEquals(ImmutableMap.of(), ImmutableMap.of(1, 2), "waldo");
                                    }
                                }
                                """,
                        """
                                import com.google.common.collect.ImmutableMap;
                                import org.assertj.core.api.Assertions;
                                
                                class Test {
                                    void test() {
                                        Assertions.assertThat(true).withFailMessage("foo").isEqualTo(false);
                                        Assertions.assertThat(true).withFailMessage("bar").isEqualTo(Boolean.FALSE);
                                        Assertions.assertThat(Boolean.TRUE).withFailMessage("baz").isEqualTo(false);
                                        Assertions.assertThat(Boolean.TRUE).withFailMessage("qux").isEqualTo(Boolean.FALSE);
                                        Assertions.assertThat((byte) 0).withFailMessage("quux").isEqualTo((byte) 1);
                                        Assertions.assertThat((byte) 0).withFailMessage("corge").isEqualTo(Byte.decode("1"));
                                        Assertions.assertThat(Byte.decode("0")).withFailMessage("grault").isEqualTo((byte) 1);
                                        Assertions.assertThat(Byte.decode("0")).withFailMessage("garply").isEqualTo(Byte.decode("1"));
                                        Assertions.assertThat('a').withFailMessage("waldo").isEqualTo('b');
                                        Assertions.assertThat('a').withFailMessage("fred").isEqualTo(Character.valueOf('b'));
                                        Assertions.assertThat(Character.valueOf('a')).withFailMessage("plugh").isEqualTo('b');
                                        Assertions.assertThat(Character.valueOf('a')).withFailMessage("xyzzy").isEqualTo(Character.valueOf('b'));
                                        Assertions.assertThat((short) 0).withFailMessage("thud").isEqualTo((short) 1);
                                        Assertions.assertThat((short) 0).withFailMessage("foo").isEqualTo(Short.decode("1"));
                                        Assertions.assertThat(Short.decode("0")).withFailMessage("bar").isEqualTo((short) 1);
                                        Assertions.assertThat(Short.decode("0")).withFailMessage("baz").isEqualTo(Short.decode("1"));
                                        Assertions.assertThat(0).withFailMessage("qux").isEqualTo(1);
                                        Assertions.assertThat(0).withFailMessage("quux").isEqualTo(Integer.valueOf(1));
                                        Assertions.assertThat(Integer.valueOf(0)).withFailMessage("corge").isEqualTo(1);
                                        Assertions.assertThat(Integer.valueOf(0)).withFailMessage("grault").isEqualTo(Integer.valueOf(1));
                                        Assertions.assertThat(0L).withFailMessage("garply").isEqualTo(1L);
                                        Assertions.assertThat(0L).withFailMessage("waldo").isEqualTo(Long.valueOf(1));
                                        Assertions.assertThat(Long.valueOf(0)).withFailMessage("fred").isEqualTo(1L);
                                        Assertions.assertThat(Long.valueOf(0)).withFailMessage("plugh").isEqualTo(Long.valueOf(1));
                                        Assertions.assertThat(0.0F).withFailMessage("xyzzy").isEqualTo(1.0F);
                                        Assertions.assertThat(0.0F).withFailMessage("thud").isEqualTo(Float.valueOf(1.0F));
                                        Assertions.assertThat(Float.valueOf(0.0F)).withFailMessage("foo").isEqualTo(1.0F);
                                        Assertions.assertThat(Float.valueOf(0.0F)).withFailMessage("bar").isEqualTo(Float.valueOf(1.0F));
                                        Assertions.assertThat(0.0).withFailMessage("baz").isEqualTo(1.0);
                                        Assertions.assertThat(0.0).withFailMessage("qux").isEqualTo(Double.valueOf(1.0));
                                        Assertions.assertThat(Double.valueOf(0.0)).withFailMessage("quux").isEqualTo(1.0);
                                        Assertions.assertThat(Double.valueOf(0.0)).withFailMessage("corge").isEqualTo(Double.valueOf(1.0));
                                        Assertions.assertThat(new Object()).withFailMessage("grault").isEqualTo(new StringBuilder());
                                        Assertions.assertThat("actual").withFailMessage("garply").isEqualTo("expected");
                                        Assertions.assertThat(ImmutableMap.of()).withFailMessage("waldo").isEqualTo(ImmutableMap.of(1, 2));
                                    }
                                }
                                """
                ));
    }

    @Test
    void testAssertFalseRecipe() {
        Recipe recipe = RewriteUtils.loadRecipe("TestNGToAssertJRulesRecipes.AssertFalseRecipe");
        rewriteRun(
                spec -> spec.recipe(recipe)
                        .expectedCyclesThatMakeChanges(1).cycles(1)
                        .parser(JavaParser.fromJavaVersion().classpath(JavaParser.runtimeClasspath())),
                java(
                        """
                                import org.testng.Assert;
                                
                                class Test {
                                    void test() {
                                        Assert.assertFalse(true);
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
    void testAssertFalseWithMessageRecipe() {
        Recipe recipe = RewriteUtils.loadRecipe("TestNGToAssertJRulesRecipes.AssertFalseWithMessageRecipe");
        rewriteRun(
                spec -> spec.recipe(recipe)
                        .expectedCyclesThatMakeChanges(1).cycles(1)
                        .parser(JavaParser.fromJavaVersion().classpath(JavaParser.runtimeClasspath())),
                java(
                        """
                                import org.testng.Assert;
                                
                                class Test {
                                    void test() {
                                        Assert.assertFalse(true, "message");
                                    }
                                }
                                """,
                        """
                                import org.assertj.core.api.Assertions;
                                
                                class Test {
                                    void test() {
                                        Assertions.assertThat(true).withFailMessage("message").isFalse();
                                    }
                                }
                                """
                ));
    }

    @Test
    void testAssertNotNullRecipe() {
        Recipe recipe = RewriteUtils.loadRecipe("TestNGToAssertJRulesRecipes.AssertNotNullRecipe");
        rewriteRun(
                spec -> spec.recipe(recipe)
                        .expectedCyclesThatMakeChanges(1).cycles(1)
                        .parser(JavaParser.fromJavaVersion().classpath(JavaParser.runtimeClasspath())),
                java(
                        """
                                import org.testng.Assert;
                                
                                class Test {
                                    void test() {
                                        Assert.assertNotNull(new Object());
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
    void testAssertNotNullWithMessageRecipe() {
        Recipe recipe = RewriteUtils.loadRecipe("TestNGToAssertJRulesRecipes.AssertNotNullWithMessageRecipe");
        rewriteRun(
                spec -> spec.recipe(recipe)
                        .expectedCyclesThatMakeChanges(1).cycles(1)
                        .parser(JavaParser.fromJavaVersion().classpath(JavaParser.runtimeClasspath())),
                java(
                        """
                                import org.testng.Assert;
                                
                                class Test {
                                    void test() {
                                        Assert.assertNotNull(new Object(), "foo");
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
    void testAssertNotSameRecipe() {
        Recipe recipe = RewriteUtils.loadRecipe("TestNGToAssertJRulesRecipes.AssertNotSameRecipe");
        rewriteRun(
                spec -> spec.recipe(recipe)
                        .expectedCyclesThatMakeChanges(1).cycles(1)
                        .parser(JavaParser.fromJavaVersion().classpath(JavaParser.runtimeClasspath())),
                java(
                        """
                                import org.testng.Assert;
                                
                                class Test {
                                    void test() {
                                        Assert.assertNotSame(new Object(), new Object());
                                    }
                                }
                                """,
                        """
                                import org.assertj.core.api.Assertions;
                                
                                class Test {
                                    void test() {
                                        Assertions.assertThat(new Object()).isNotSameAs(new Object());
                                    }
                                }
                                """
                ));
    }

    @Test
    void testAssertNotSameWithMessageRecipe() {
        Recipe recipe = RewriteUtils.loadRecipe("TestNGToAssertJRulesRecipes.AssertNotSameWithMessageRecipe");
        rewriteRun(
                spec -> spec.recipe(recipe)
                        .expectedCyclesThatMakeChanges(1).cycles(1)
                        .parser(JavaParser.fromJavaVersion().classpath(JavaParser.runtimeClasspath())),
                java(
                        """
                                import org.testng.Assert;
                                
                                class Test {
                                    void test() {
                                        Assert.assertNotSame(new Object(), new Object(), "foo");
                                    }
                                }
                                """,
                        """
                                import org.assertj.core.api.Assertions;
                                
                                class Test {
                                    void test() {
                                        Assertions.assertThat(new Object()).withFailMessage("foo").isNotSameAs(new Object());
                                    }
                                }
                                """
                ));
    }

    @Test
    void testAssertNullRecipe() {
        Recipe recipe = RewriteUtils.loadRecipe("TestNGToAssertJRulesRecipes.AssertNullRecipe");
        rewriteRun(
                spec -> spec.recipe(recipe)
                        .expectedCyclesThatMakeChanges(1).cycles(1)
                        .parser(JavaParser.fromJavaVersion().classpath(JavaParser.runtimeClasspath())),
                java(
                        """
                                import org.testng.Assert;
                                
                                class Test {
                                    void test() {
                                        Assert.assertNull(new Object());
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
    void testAssertNullWithMessageRecipe() {
        Recipe recipe = RewriteUtils.loadRecipe("TestNGToAssertJRulesRecipes.AssertNullWithMessageRecipe");
        rewriteRun(
                spec -> spec.recipe(recipe)
                        .expectedCyclesThatMakeChanges(1).cycles(1)
                        .parser(JavaParser.fromJavaVersion().classpath(JavaParser.runtimeClasspath())),
                java(
                        """
                                import org.testng.Assert;
                                
                                class Test {
                                    void test() {
                                        Assert.assertNull(new Object(), "foo");
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
    void testAssertSameRecipe() {
        Recipe recipe = RewriteUtils.loadRecipe("TestNGToAssertJRulesRecipes.AssertSameRecipe");
        rewriteRun(
                spec -> spec.recipe(recipe)
                        .expectedCyclesThatMakeChanges(1).cycles(1)
                        .parser(JavaParser.fromJavaVersion().classpath(JavaParser.runtimeClasspath())),
                java(
                        """
                                import org.testng.Assert;
                                
                                class Test {
                                    void test() {
                                        Assert.assertSame(new Object(), new Object());
                                    }
                                }
                                """,
                        """
                                import org.assertj.core.api.Assertions;
                                
                                class Test {
                                    void test() {
                                        Assertions.assertThat(new Object()).isSameAs(new Object());
                                    }
                                }
                                """
                ));
    }

    @Test
    void testAssertSameWithMessageRecipe() {
        Recipe recipe = RewriteUtils.loadRecipe("TestNGToAssertJRulesRecipes.AssertSameWithMessageRecipe");
        rewriteRun(
                spec -> spec.recipe(recipe)
                        .expectedCyclesThatMakeChanges(1).cycles(1)
                        .parser(JavaParser.fromJavaVersion().classpath(JavaParser.runtimeClasspath())),
                java(
                        """
                                import org.testng.Assert;
                                
                                class Test {
                                    void test() {
                                        Assert.assertSame(new Object(), new Object(), "foo");
                                    }
                                }
                                """,
                        """
                                import org.assertj.core.api.Assertions;
                                
                                class Test {
                                    void test() {
                                        Assertions.assertThat(new Object()).withFailMessage("foo").isSameAs(new Object());
                                    }
                                }
                                """
                ));
    }

    @Test
    void testAssertThrowsRecipe() {
        Recipe recipe = RewriteUtils.loadRecipe("TestNGToAssertJRulesRecipes.AssertThrowsRecipe");
        rewriteRun(
                spec -> spec.recipe(recipe)
                        .expectedCyclesThatMakeChanges(1).cycles(1)
                        .parser(JavaParser.fromJavaVersion().classpath(JavaParser.runtimeClasspath())),
                java(
                        """
                                import org.testng.Assert;
                                
                                class Test {
                                    void test() {
                                        Assert.assertThrows(() -> {
                                        });
                                    }
                                }
                                """,
                        """
                                import org.assertj.core.api.Assertions;
                                
                                class Test {
                                    void test() {
                                        Assertions.assertThatThrownBy(() -> {
                                        });
                                    }
                                }
                                """
                ));
    }

    @Test
    void testAssertThrowsWithTypeRecipe() {
        Recipe recipe = RewriteUtils.loadRecipe("TestNGToAssertJRulesRecipes.AssertThrowsWithTypeRecipe");
        rewriteRun(
                spec -> spec.recipe(recipe)
                        .expectedCyclesThatMakeChanges(1).cycles(1)
                        .parser(JavaParser.fromJavaVersion().classpath(JavaParser.runtimeClasspath())),
                java(
                        """
                                import org.testng.Assert;
                                
                                class Test {
                                    void test() {
                                        Assert.assertThrows(IllegalStateException.class, () -> {
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
    void testAssertTrueRecipe() {
        Recipe recipe = RewriteUtils.loadRecipe("TestNGToAssertJRulesRecipes.AssertTrueRecipe");
        rewriteRun(
                spec -> spec.recipe(recipe)
                        .expectedCyclesThatMakeChanges(1).cycles(1)
                        .parser(JavaParser.fromJavaVersion().classpath(JavaParser.runtimeClasspath())),
                java(
                        """
                                import org.testng.Assert;
                                
                                class Test {
                                    void test() {
                                        Assert.assertTrue(true);
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
    void testAssertTrueWithMessageRecipe() {
        Recipe recipe = RewriteUtils.loadRecipe("TestNGToAssertJRulesRecipes.AssertTrueWithMessageRecipe");
        rewriteRun(
                spec -> spec.recipe(recipe)
                        .expectedCyclesThatMakeChanges(1).cycles(1)
                        .parser(JavaParser.fromJavaVersion().classpath(JavaParser.runtimeClasspath())),
                java(
                        """
                                import org.testng.Assert;
                                
                                class Test {
                                    void test() {
                                        Assert.assertTrue(true, "foo");
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
    void testAssertUnequalRecipe() {
        Recipe recipe = RewriteUtils.loadRecipe("TestNGToAssertJRulesRecipes.AssertUnequalRecipe");
        rewriteRun(
                spec -> spec.recipe(recipe)
                        .expectedCyclesThatMakeChanges(1).cycles(1)
                        .parser(JavaParser.fromJavaVersion().classpath(JavaParser.runtimeClasspath())),
                java(
                        """
                                import com.google.common.collect.ImmutableMap;
                                import com.google.common.collect.ImmutableSet;
                                import org.testng.Assert;
                                
                                class Test {
                                    void test() {
                                        Assert.assertNotEquals(true, true);
                                        Assert.assertNotEquals((byte) 0, (byte) 0);
                                        Assert.assertNotEquals((char) 0, (char) 0);
                                        Assert.assertNotEquals((short) 0, (short) 0);
                                        Assert.assertNotEquals(0, 0);
                                        Assert.assertNotEquals(0L, 0L);
                                        Assert.assertNotEquals(0.0F, 0.0F);
                                        Assert.assertNotEquals(0.0, 0.0);
                                        Assert.assertNotEquals(new Object(), new Object());
                                        Assert.assertNotEquals("actual", "expected");
                                        Assert.assertNotEquals(ImmutableSet.of(), ImmutableSet.of());
                                        Assert.assertNotEquals(ImmutableMap.of(), ImmutableMap.of());
                                    }
                                }
                                """,
                        """
                                import com.google.common.collect.ImmutableMap;
                                import com.google.common.collect.ImmutableSet;
                                import org.assertj.core.api.Assertions;
                                
                                class Test {
                                    void test() {
                                        Assertions.assertThat(true).isNotEqualTo(true);
                                        Assertions.assertThat((byte) 0).isNotEqualTo((byte) 0);
                                        Assertions.assertThat((char) 0).isNotEqualTo((char) 0);
                                        Assertions.assertThat((short) 0).isNotEqualTo((short) 0);
                                        Assertions.assertThat(0).isNotEqualTo(0);
                                        Assertions.assertThat(0L).isNotEqualTo(0L);
                                        Assertions.assertThat(0.0F).isNotEqualTo(0.0F);
                                        Assertions.assertThat(0.0).isNotEqualTo(0.0);
                                        Assertions.assertThat(new Object()).isNotEqualTo(new Object());
                                        Assertions.assertThat("actual").isNotEqualTo("expected");
                                        Assertions.assertThat(ImmutableSet.of()).isNotEqualTo(ImmutableSet.of());
                                        Assertions.assertThat(ImmutableMap.of()).isNotEqualTo(ImmutableMap.of());
                                    }
                                }
                                """
                ));
    }

    @Test
    void testAssertUnequalDoublesWithDeltaRecipe() {
        Recipe recipe = RewriteUtils.loadRecipe("TestNGToAssertJRulesRecipes.AssertUnequalDoublesWithDeltaRecipe");
        rewriteRun(
                spec -> spec.recipe(recipe)
                        .expectedCyclesThatMakeChanges(1).cycles(1)
                        .parser(JavaParser.fromJavaVersion().classpath(JavaParser.runtimeClasspath())),
                java(
                        """
                                import org.testng.Assert;
                                
                                class Test {
                                    void test() {
                                        Assert.assertNotEquals(0.0, 0.0, 0.0);
                                    }
                                }
                                """,
                        """
                                import org.assertj.core.api.Assertions;
                                import org.assertj.core.data.Offset;
                                
                                class Test {
                                    void test() {
                                        Assertions.assertThat(0.0).isNotCloseTo(0.0, Offset.offset(0.0));
                                    }
                                }
                                """
                ));
    }

    @Test
    void testAssertUnequalDoublesWithDeltaWithMessageRecipe() {
        Recipe recipe = RewriteUtils.loadRecipe("TestNGToAssertJRulesRecipes.AssertUnequalDoublesWithDeltaWithMessageRecipe");
        rewriteRun(
                spec -> spec.recipe(recipe)
                        .expectedCyclesThatMakeChanges(1).cycles(1)
                        .parser(JavaParser.fromJavaVersion().classpath(JavaParser.runtimeClasspath())),
                java(
                        """
                                import org.testng.Assert;
                                
                                class Test {
                                    void test() {
                                        Assert.assertNotEquals(0.0, 0.0, 0.0, "foo");
                                    }
                                }
                                """,
                        """
                                import org.assertj.core.api.Assertions;
                                import org.assertj.core.data.Offset;
                                
                                class Test {
                                    void test() {
                                        Assertions.assertThat(0.0).withFailMessage("foo").isNotCloseTo(0.0, Offset.offset(0.0));
                                    }
                                }
                                """
                ));
    }

    @Test
    void testAssertUnequalFloatsWithDeltaRecipe() {
        Recipe recipe = RewriteUtils.loadRecipe("TestNGToAssertJRulesRecipes.AssertUnequalFloatsWithDeltaRecipe");
        rewriteRun(
                spec -> spec.recipe(recipe)
                        .expectedCyclesThatMakeChanges(1).cycles(1)
                        .parser(JavaParser.fromJavaVersion().classpath(JavaParser.runtimeClasspath())),
                java(
                        """
                                import org.testng.Assert;
                                
                                class Test {
                                    void test() {
                                        Assert.assertNotEquals(0.0F, 0.0F, 0.0F);
                                    }
                                }
                                """,
                        """
                                import org.assertj.core.api.Assertions;
                                import org.assertj.core.data.Offset;
                                
                                class Test {
                                    void test() {
                                        Assertions.assertThat(0.0F).isNotCloseTo(0.0F, Offset.offset(0.0F));
                                    }
                                }
                                """
                ));
    }

    @Test
    void testAssertUnequalFloatsWithDeltaWithMessageRecipe() {
        Recipe recipe = RewriteUtils.loadRecipe("TestNGToAssertJRulesRecipes.AssertUnequalFloatsWithDeltaWithMessageRecipe");
        rewriteRun(
                spec -> spec.recipe(recipe)
                        .expectedCyclesThatMakeChanges(1).cycles(1)
                        .parser(JavaParser.fromJavaVersion().classpath(JavaParser.runtimeClasspath())),
                java(
                        """
                                import org.testng.Assert;
                                
                                class Test {
                                    void test() {
                                        Assert.assertNotEquals(0.0F, 0.0F, 0.0F, "foo");
                                    }
                                }
                                """,
                        """
                                import org.assertj.core.api.Assertions;
                                import org.assertj.core.data.Offset;
                                
                                class Test {
                                    void test() {
                                        Assertions.assertThat(0.0F).withFailMessage("foo").isNotCloseTo(0.0F, Offset.offset(0.0F));
                                    }
                                }
                                """
                ));
    }

    @Test
    void testAssertUnequalWithMessageRecipe() {
        Recipe recipe = RewriteUtils.loadRecipe("TestNGToAssertJRulesRecipes.AssertUnequalWithMessageRecipe");
        rewriteRun(
                spec -> spec.recipe(recipe)
                        .expectedCyclesThatMakeChanges(1).cycles(1)
                        .parser(JavaParser.fromJavaVersion().classpath(JavaParser.runtimeClasspath())),
                java(
                        """
                                import com.google.common.collect.ImmutableMap;
                                import com.google.common.collect.ImmutableSet;
                                import org.testng.Assert;
                                
                                class Test {
                                    void test() {
                                        Assert.assertNotEquals(true, true, "foo");
                                        Assert.assertNotEquals((byte) 0, (byte) 0, "bar");
                                        Assert.assertNotEquals((char) 0, (char) 0, "baz");
                                        Assert.assertNotEquals((short) 0, (short) 0, "qux");
                                        Assert.assertNotEquals(0, 0, "quux");
                                        Assert.assertNotEquals(0L, 0L, "quuz");
                                        Assert.assertNotEquals(0.0F, 0.0F, "corge");
                                        Assert.assertNotEquals(0.0, 0.0, "grault");
                                        Assert.assertNotEquals(new Object(), new Object(), "garply");
                                        Assert.assertNotEquals("actual", "expected", "waldo");
                                        Assert.assertNotEquals(ImmutableSet.of(), ImmutableSet.of(), "fred");
                                        Assert.assertNotEquals(ImmutableMap.of(), ImmutableMap.of(), "plugh");
                                    }
                                }
                                """,
                        """
                                import com.google.common.collect.ImmutableMap;
                                import com.google.common.collect.ImmutableSet;
                                import org.assertj.core.api.Assertions;
                                
                                class Test {
                                    void test() {
                                        Assertions.assertThat(true).withFailMessage("foo").isNotEqualTo(true);
                                        Assertions.assertThat((byte) 0).withFailMessage("bar").isNotEqualTo((byte) 0);
                                        Assertions.assertThat((char) 0).withFailMessage("baz").isNotEqualTo((char) 0);
                                        Assertions.assertThat((short) 0).withFailMessage("qux").isNotEqualTo((short) 0);
                                        Assertions.assertThat(0).withFailMessage("quux").isNotEqualTo(0);
                                        Assertions.assertThat(0L).withFailMessage("quuz").isNotEqualTo(0L);
                                        Assertions.assertThat(0.0F).withFailMessage("corge").isNotEqualTo(0.0F);
                                        Assertions.assertThat(0.0).withFailMessage("grault").isNotEqualTo(0.0);
                                        Assertions.assertThat(new Object()).withFailMessage("garply").isNotEqualTo(new Object());
                                        Assertions.assertThat("actual").withFailMessage("waldo").isNotEqualTo("expected");
                                        Assertions.assertThat(ImmutableSet.of()).withFailMessage("fred").isNotEqualTo(ImmutableSet.of());
                                        Assertions.assertThat(ImmutableMap.of()).withFailMessage("plugh").isNotEqualTo(ImmutableMap.of());
                                    }
                                }
                                """
                ));
    }

    @Test
    void testFailRecipe() {
        Recipe recipe = RewriteUtils.loadRecipe("TestNGToAssertJRulesRecipes.FailRecipe");
        rewriteRun(
                spec -> spec.recipe(recipe)
                        .expectedCyclesThatMakeChanges(1).cycles(1)
                        .parser(JavaParser.fromJavaVersion().classpath(JavaParser.runtimeClasspath())),
                java(
                        """
                                import org.testng.Assert;
                                
                                class Test {
                                    void test() {
                                        Assert.fail();
                                    }
                                }
                                """,
                        """
                                import org.assertj.core.api.Assertions;
                                
                                class Test {
                                    void test() {
                                        Assertions.fail();
                                    }
                                }
                                """
                ));
    }

    @Test
    void testFailWithMessageRecipe() {
        Recipe recipe = RewriteUtils.loadRecipe("TestNGToAssertJRulesRecipes.FailWithMessageRecipe");
        rewriteRun(
                spec -> spec.recipe(recipe)
                        .expectedCyclesThatMakeChanges(1).cycles(1)
                        .parser(JavaParser.fromJavaVersion().classpath(JavaParser.runtimeClasspath())),
                java(
                        """
                                import org.testng.Assert;
                                
                                class Test {
                                    void test() {
                                        Assert.fail("foo");
                                    }
                                }
                                """,
                        """
                                import org.assertj.core.api.Assertions;
                                
                                class Test {
                                    void test() {
                                        Assertions.fail("foo");
                                    }
                                }
                                """
                ));
    }

    @Test
    void testFailWithMessageAndThrowableRecipe() {
        Recipe recipe = RewriteUtils.loadRecipe("TestNGToAssertJRulesRecipes.FailWithMessageAndThrowableRecipe");
        rewriteRun(
                spec -> spec.recipe(recipe)
                        .expectedCyclesThatMakeChanges(1).cycles(1)
                        .parser(JavaParser.fromJavaVersion().classpath(JavaParser.runtimeClasspath())),
                java(
                        """
                                import org.testng.Assert;
                                
                                class Test {
                                    void test() {
                                        Assert.fail("foo", new IllegalStateException());
                                    }
                                }
                                """,
                        """
                                import org.assertj.core.api.Assertions;
                                
                                class Test {
                                    void test() {
                                        Assertions.fail("foo", new IllegalStateException());
                                    }
                                }
                                """
                ));
    }
}
