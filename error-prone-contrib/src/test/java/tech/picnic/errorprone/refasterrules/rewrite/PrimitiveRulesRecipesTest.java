package tech.picnic.errorprone.refasterrules.rewrite;

import org.junit.jupiter.api.Test;
import org.openrewrite.Recipe;
import org.openrewrite.java.JavaParser;
import org.openrewrite.test.RewriteTest;

import static org.openrewrite.java.Assertions.java;

final class PrimitiveRulesRecipesTest implements RewriteTest {

    @Test
    void testArraysCompareUnsignedBytesRecipe() {
        Recipe recipe = RewriteUtils.loadRecipe("PrimitiveRulesRecipes.ArraysCompareUnsignedBytesRecipe");
        rewriteRun(
                spec -> spec.recipe(recipe)
                        .expectedCyclesThatMakeChanges(1).cycles(1)
                        .parser(JavaParser.fromJavaVersion().classpath(JavaParser.runtimeClasspath())),
                java(
                        """
                                import com.google.common.primitives.UnsignedBytes;
                                
                                import java.util.Comparator;
                                
                                class Test {
                                    Comparator<byte[]> test() {
                                        return UnsignedBytes.lexicographicalComparator();
                                    }
                                }
                                """,
                        """
                                import java.util.Arrays;
                                import java.util.Comparator;
                                
                                class Test {
                                    Comparator<byte[]> test() {
                                        return Arrays::compareUnsigned;
                                    }
                                }
                                """
                ));
    }

    @Test
    void testArraysCompareUnsignedIntsRecipe() {
        Recipe recipe = RewriteUtils.loadRecipe("PrimitiveRulesRecipes.ArraysCompareUnsignedIntsRecipe");
        rewriteRun(
                spec -> spec.recipe(recipe)
                        .expectedCyclesThatMakeChanges(1).cycles(1)
                        .parser(JavaParser.fromJavaVersion().classpath(JavaParser.runtimeClasspath())),
                java(
                        """
                                import com.google.common.primitives.UnsignedInts;
                                
                                import java.util.Comparator;
                                
                                class Test {
                                    Comparator<int[]> test() {
                                        return UnsignedInts.lexicographicalComparator();
                                    }
                                }
                                """,
                        """
                                import java.util.Arrays;
                                import java.util.Comparator;
                                
                                class Test {
                                    Comparator<int[]> test() {
                                        return Arrays::compareUnsigned;
                                    }
                                }
                                """
                ));
    }

    @Test
    void testArraysCompareUnsignedLongsRecipe() {
        Recipe recipe = RewriteUtils.loadRecipe("PrimitiveRulesRecipes.ArraysCompareUnsignedLongsRecipe");
        rewriteRun(
                spec -> spec.recipe(recipe)
                        .expectedCyclesThatMakeChanges(1).cycles(1)
                        .parser(JavaParser.fromJavaVersion().classpath(JavaParser.runtimeClasspath())),
                java(
                        """
                                import com.google.common.primitives.UnsignedLongs;
                                
                                import java.util.Comparator;
                                
                                class Test {
                                    Comparator<long[]> test() {
                                        return UnsignedLongs.lexicographicalComparator();
                                    }
                                }
                                """,
                        """
                                import java.util.Arrays;
                                import java.util.Comparator;
                                
                                class Test {
                                    Comparator<long[]> test() {
                                        return Arrays::compareUnsigned;
                                    }
                                }
                                """
                ));
    }

    @Test
    void testBooleanHashCodeRecipe() {
        Recipe recipe = RewriteUtils.loadRecipe("PrimitiveRulesRecipes.BooleanHashCodeRecipe");
        rewriteRun(
                spec -> spec.recipe(recipe)
                        .expectedCyclesThatMakeChanges(1).cycles(1)
                        .parser(JavaParser.fromJavaVersion().classpath(JavaParser.runtimeClasspath())),
                java(
                        """
                                import com.google.common.primitives.Booleans;
                                
                                class Test {
                                    int test() {
                                        return Booleans.hashCode(true);
                                    }
                                }
                                """,
                        """
                                class Test {
                                    int test() {
                                        return Boolean.hashCode(true);
                                    }
                                }
                                """
                ));
    }

    @Test
    void testByteHashCodeRecipe() {
        Recipe recipe = RewriteUtils.loadRecipe("PrimitiveRulesRecipes.ByteHashCodeRecipe");
        rewriteRun(
                spec -> spec.recipe(recipe)
                        .expectedCyclesThatMakeChanges(1).cycles(1)
                        .parser(JavaParser.fromJavaVersion().classpath(JavaParser.runtimeClasspath())),
                java(
                        """
                                import com.google.common.primitives.Bytes;
                                
                                class Test {
                                    int test() {
                                        return Bytes.hashCode((byte) 1);
                                    }
                                }
                                """,
                        """
                                class Test {
                                    int test() {
                                        return Byte.hashCode((byte) 1);
                                    }
                                }
                                """
                ));
    }

    @Test
    void testCharacterBytesRecipe() {
        Recipe recipe = RewriteUtils.loadRecipe("PrimitiveRulesRecipes.CharacterBytesRecipe");
        rewriteRun(
                spec -> spec.recipe(recipe)
                        .expectedCyclesThatMakeChanges(1).cycles(1)
                        .parser(JavaParser.fromJavaVersion().classpath(JavaParser.runtimeClasspath())),
                java(
                        """
                                import com.google.common.primitives.Chars;
                                
                                class Test {
                                    int test() {
                                        return Chars.BYTES;
                                    }
                                }
                                """,
                        """
                                class Test {
                                    int test() {
                                        return Character.BYTES;
                                    }
                                }
                                """
                ));
    }

    @Test
    void testCharacterHashCodeRecipe() {
        Recipe recipe = RewriteUtils.loadRecipe("PrimitiveRulesRecipes.CharacterHashCodeRecipe");
        rewriteRun(
                spec -> spec.recipe(recipe)
                        .expectedCyclesThatMakeChanges(1).cycles(1)
                        .parser(JavaParser.fromJavaVersion().classpath(JavaParser.runtimeClasspath())),
                java(
                        """
                                import com.google.common.primitives.Chars;
                                
                                class Test {
                                    int test() {
                                        return Chars.hashCode('a');
                                    }
                                }
                                """,
                        """
                                class Test {
                                    int test() {
                                        return Character.hashCode('a');
                                    }
                                }
                                """
                ));
    }

    @Test
    void testDoubleBytesRecipe() {
        Recipe recipe = RewriteUtils.loadRecipe("PrimitiveRulesRecipes.DoubleBytesRecipe");
        rewriteRun(
                spec -> spec.recipe(recipe)
                        .expectedCyclesThatMakeChanges(1).cycles(1)
                        .parser(JavaParser.fromJavaVersion().classpath(JavaParser.runtimeClasspath())),
                java(
                        """
                                import com.google.common.primitives.Doubles;
                                
                                class Test {
                                    int test() {
                                        return Doubles.BYTES;
                                    }
                                }
                                """,
                        """
                                class Test {
                                    int test() {
                                        return Double.BYTES;
                                    }
                                }
                                """
                ));
    }

    @Test
    void testDoubleHashCodeRecipe() {
        Recipe recipe = RewriteUtils.loadRecipe("PrimitiveRulesRecipes.DoubleHashCodeRecipe");
        rewriteRun(
                spec -> spec.recipe(recipe)
                        .expectedCyclesThatMakeChanges(1).cycles(1)
                        .parser(JavaParser.fromJavaVersion().classpath(JavaParser.runtimeClasspath())),
                java(
                        """
                                import com.google.common.primitives.Doubles;
                                
                                class Test {
                                    int test() {
                                        return Doubles.hashCode(1);
                                    }
                                }
                                """,
                        """
                                class Test {
                                    int test() {
                                        return Double.hashCode(1);
                                    }
                                }
                                """
                ));
    }

    @Test
    void testDoubleIsFiniteRecipe() {
        Recipe recipe = RewriteUtils.loadRecipe("PrimitiveRulesRecipes.DoubleIsFiniteRecipe");
        rewriteRun(
                spec -> spec.recipe(recipe)
                        .expectedCyclesThatMakeChanges(1).cycles(1)
                        .parser(JavaParser.fromJavaVersion().classpath(JavaParser.runtimeClasspath())),
                java(
                        """
                                import com.google.common.primitives.Doubles;
                                
                                class Test {
                                    boolean test() {
                                        return Doubles.isFinite(1);
                                    }
                                }
                                """,
                        """
                                class Test {
                                    boolean test() {
                                        return Double.isFinite(1);
                                    }
                                }
                                """
                ));
    }

    @Test
    void testFloatBytesRecipe() {
        Recipe recipe = RewriteUtils.loadRecipe("PrimitiveRulesRecipes.FloatBytesRecipe");
        rewriteRun(
                spec -> spec.recipe(recipe)
                        .expectedCyclesThatMakeChanges(1).cycles(1)
                        .parser(JavaParser.fromJavaVersion().classpath(JavaParser.runtimeClasspath())),
                java(
                        """
                                import com.google.common.primitives.Floats;
                                
                                class Test {
                                    int test() {
                                        return Floats.BYTES;
                                    }
                                }
                                """,
                        """
                                class Test {
                                    int test() {
                                        return Float.BYTES;
                                    }
                                }
                                """
                ));
    }

    @Test
    void testFloatHashCodeRecipe() {
        Recipe recipe = RewriteUtils.loadRecipe("PrimitiveRulesRecipes.FloatHashCodeRecipe");
        rewriteRun(
                spec -> spec.recipe(recipe)
                        .expectedCyclesThatMakeChanges(1).cycles(1)
                        .parser(JavaParser.fromJavaVersion().classpath(JavaParser.runtimeClasspath())),
                java(
                        """
                                import com.google.common.primitives.Floats;
                                
                                class Test {
                                    int test() {
                                        return Floats.hashCode(1);
                                    }
                                }
                                """,
                        """
                                class Test {
                                    int test() {
                                        return Float.hashCode(1);
                                    }
                                }
                                """
                ));
    }

    @Test
    void testFloatIsFiniteRecipe() {
        Recipe recipe = RewriteUtils.loadRecipe("PrimitiveRulesRecipes.FloatIsFiniteRecipe");
        rewriteRun(
                spec -> spec.recipe(recipe)
                        .expectedCyclesThatMakeChanges(1).cycles(1)
                        .parser(JavaParser.fromJavaVersion().classpath(JavaParser.runtimeClasspath())),
                java(
                        """
                                import com.google.common.primitives.Floats;
                                
                                class Test {
                                    boolean test() {
                                        return Floats.isFinite(1);
                                    }
                                }
                                """,
                        """
                                class Test {
                                    boolean test() {
                                        return Float.isFinite(1);
                                    }
                                }
                                """
                ));
    }

    @Test
    void testGreaterThanRecipe() {
        Recipe recipe = RewriteUtils.loadRecipe("PrimitiveRulesRecipes.GreaterThanRecipe");
        rewriteRun(
                spec -> spec.recipe(recipe)
                        .expectedCyclesThatMakeChanges(1).cycles(1)
                        .parser(JavaParser.fromJavaVersion().classpath(JavaParser.runtimeClasspath())),
                java(
                        """
                                import com.google.common.collect.ImmutableSet;
                                
                                class Test {
                                    ImmutableSet<Boolean> test() {
                                        return ImmutableSet.of(!((byte) 3 <= (byte) 4), !((char) 3 <= (char) 4), !((short) 3 <= (short) 4), !(3 <= 4), !(3L <= 4L), !(3F <= 4F), !(3.0 <= 4.0));
                                    }
                                }
                                """,
                        """
                                import com.google.common.collect.ImmutableSet;
                                
                                class Test {
                                    ImmutableSet<Boolean> test() {
                                        return ImmutableSet.of((byte) 3 > (byte) 4, (char) 3 > (char) 4, (short) 3 > (short) 4, 3 > 4, 3L > 4L, 3F > 4F, 3.0 > 4.0);
                                    }
                                }
                                """
                ));
    }

    @Test
    void testGreaterThanOrEqualToRecipe() {
        Recipe recipe = RewriteUtils.loadRecipe("PrimitiveRulesRecipes.GreaterThanOrEqualToRecipe");
        rewriteRun(
                spec -> spec.recipe(recipe)
                        .expectedCyclesThatMakeChanges(1).cycles(1)
                        .parser(JavaParser.fromJavaVersion().classpath(JavaParser.runtimeClasspath())),
                java(
                        """
                                import com.google.common.collect.ImmutableSet;
                                
                                class Test {
                                    ImmutableSet<Boolean> test() {
                                        return ImmutableSet.of(!((byte) 3 < (byte) 4), !((char) 3 < (char) 4), !((short) 3 < (short) 4), !(3 < 4), !(3L < 4L), !(3F < 4F), !(3.0 < 4.0));
                                    }
                                }
                                """,
                        """
                                import com.google.common.collect.ImmutableSet;
                                
                                class Test {
                                    ImmutableSet<Boolean> test() {
                                        return ImmutableSet.of((byte) 3 >= (byte) 4, (char) 3 >= (char) 4, (short) 3 >= (short) 4, 3 >= 4, 3L >= 4L, 3F >= 4F, 3.0 >= 4.0);
                                    }
                                }
                                """
                ));
    }

    @Test
    void testIntegerBytesRecipe() {
        Recipe recipe = RewriteUtils.loadRecipe("PrimitiveRulesRecipes.IntegerBytesRecipe");
        rewriteRun(
                spec -> spec.recipe(recipe)
                        .expectedCyclesThatMakeChanges(1).cycles(1)
                        .parser(JavaParser.fromJavaVersion().classpath(JavaParser.runtimeClasspath())),
                java(
                        """
                                import com.google.common.primitives.Ints;
                                
                                class Test {
                                    int test() {
                                        return Ints.BYTES;
                                    }
                                }
                                """,
                        """
                                class Test {
                                    int test() {
                                        return Integer.BYTES;
                                    }
                                }
                                """
                ));
    }

    @Test
    void testIntegerCompareUnsignedRecipe() {
        Recipe recipe = RewriteUtils.loadRecipe("PrimitiveRulesRecipes.IntegerCompareUnsignedRecipe");
        rewriteRun(
                spec -> spec.recipe(recipe)
                        .expectedCyclesThatMakeChanges(1).cycles(1)
                        .parser(JavaParser.fromJavaVersion().classpath(JavaParser.runtimeClasspath())),
                java(
                        """
                                import com.google.common.primitives.UnsignedInts;
                                
                                class Test {
                                    int test() {
                                        return UnsignedInts.compare(1, 2);
                                    }
                                }
                                """,
                        """
                                class Test {
                                    int test() {
                                        return Integer.compareUnsigned(1, 2);
                                    }
                                }
                                """
                ));
    }

    @Test
    void testIntegerDivideUnsignedRecipe() {
        Recipe recipe = RewriteUtils.loadRecipe("PrimitiveRulesRecipes.IntegerDivideUnsignedRecipe");
        rewriteRun(
                spec -> spec.recipe(recipe)
                        .expectedCyclesThatMakeChanges(1).cycles(1)
                        .parser(JavaParser.fromJavaVersion().classpath(JavaParser.runtimeClasspath())),
                java(
                        """
                                import com.google.common.primitives.UnsignedInts;
                                
                                class Test {
                                    int test() {
                                        return UnsignedInts.divide(1, 2);
                                    }
                                }
                                """,
                        """
                                class Test {
                                    int test() {
                                        return Integer.divideUnsigned(1, 2);
                                    }
                                }
                                """
                ));
    }

    @Test
    void testIntegerHashCodeRecipe() {
        Recipe recipe = RewriteUtils.loadRecipe("PrimitiveRulesRecipes.IntegerHashCodeRecipe");
        rewriteRun(
                spec -> spec.recipe(recipe)
                        .expectedCyclesThatMakeChanges(1).cycles(1)
                        .parser(JavaParser.fromJavaVersion().classpath(JavaParser.runtimeClasspath())),
                java(
                        """
                                import com.google.common.primitives.Ints;
                                
                                class Test {
                                    int test() {
                                        return Ints.hashCode(1);
                                    }
                                }
                                """,
                        """
                                class Test {
                                    int test() {
                                        return Integer.hashCode(1);
                                    }
                                }
                                """
                ));
    }

    @Test
    void testIntegerParseUnsignedIntRecipe() {
        Recipe recipe = RewriteUtils.loadRecipe("PrimitiveRulesRecipes.IntegerParseUnsignedIntRecipe");
        rewriteRun(
                spec -> spec.recipe(recipe)
                        .expectedCyclesThatMakeChanges(1).cycles(1)
                        .parser(JavaParser.fromJavaVersion().classpath(JavaParser.runtimeClasspath())),
                java(
                        """
                                import com.google.common.collect.ImmutableSet;
                                import com.google.common.primitives.UnsignedInts;
                                
                                class Test {
                                    ImmutableSet<Integer> test() {
                                        return ImmutableSet.of(UnsignedInts.parseUnsignedInt("1"), Integer.parseUnsignedInt("2", 10));
                                    }
                                }
                                """,
                        """
                                import com.google.common.collect.ImmutableSet;
                                
                                class Test {
                                    ImmutableSet<Integer> test() {
                                        return ImmutableSet.of(Integer.parseUnsignedInt("1"), Integer.parseUnsignedInt("2"));
                                    }
                                }
                                """
                ));
    }

    @Test
    void testIntegerParseUnsignedIntWithRadixRecipe() {
        Recipe recipe = RewriteUtils.loadRecipe("PrimitiveRulesRecipes.IntegerParseUnsignedIntWithRadixRecipe");
        rewriteRun(
                spec -> spec.recipe(recipe)
                        .expectedCyclesThatMakeChanges(1).cycles(1)
                        .parser(JavaParser.fromJavaVersion().classpath(JavaParser.runtimeClasspath())),
                java(
                        """
                                import com.google.common.primitives.UnsignedInts;
                                
                                class Test {
                                    int test() {
                                        return UnsignedInts.parseUnsignedInt("1", 2);
                                    }
                                }
                                """,
                        """
                                class Test {
                                    int test() {
                                        return Integer.parseUnsignedInt("1", 2);
                                    }
                                }
                                """
                ));
    }

    @Test
    void testIntegerRemainderUnsignedRecipe() {
        Recipe recipe = RewriteUtils.loadRecipe("PrimitiveRulesRecipes.IntegerRemainderUnsignedRecipe");
        rewriteRun(
                spec -> spec.recipe(recipe)
                        .expectedCyclesThatMakeChanges(1).cycles(1)
                        .parser(JavaParser.fromJavaVersion().classpath(JavaParser.runtimeClasspath())),
                java(
                        """
                                import com.google.common.primitives.UnsignedInts;
                                
                                class Test {
                                    int test() {
                                        return UnsignedInts.remainder(1, 2);
                                    }
                                }
                                """,
                        """
                                class Test {
                                    int test() {
                                        return Integer.remainderUnsigned(1, 2);
                                    }
                                }
                                """
                ));
    }

    @Test
    void testIntegerSignumIsNegativeRecipe() {
        Recipe recipe = RewriteUtils.loadRecipe("PrimitiveRulesRecipes.IntegerSignumIsNegativeRecipe");
        rewriteRun(
                spec -> spec.recipe(recipe)
                        .expectedCyclesThatMakeChanges(1).cycles(1)
                        .parser(JavaParser.fromJavaVersion().classpath(JavaParser.runtimeClasspath())),
                java(
                        """
                                import com.google.common.collect.ImmutableSet;
                                
                                class Test {
                                    ImmutableSet<Boolean> test() {
                                        return ImmutableSet.of(Integer.signum(1) < 0, Integer.signum(2) <= -1, Integer.signum(3) >= 0, Integer.signum(4) > -1);
                                    }
                                }
                                """,
                        """
                                import com.google.common.collect.ImmutableSet;
                                
                                class Test {
                                    ImmutableSet<Boolean> test() {
                                        return ImmutableSet.of(Integer.signum(1) == -1, Integer.signum(2) == -1, Integer.signum(3) != -1, Integer.signum(4) != -1);
                                    }
                                }
                                """
                ));
    }

    @Test
    void testIntegerSignumIsPositiveRecipe() {
        Recipe recipe = RewriteUtils.loadRecipe("PrimitiveRulesRecipes.IntegerSignumIsPositiveRecipe");
        rewriteRun(
                spec -> spec.recipe(recipe)
                        .expectedCyclesThatMakeChanges(1).cycles(1)
                        .parser(JavaParser.fromJavaVersion().classpath(JavaParser.runtimeClasspath())),
                java(
                        """
                                import com.google.common.collect.ImmutableSet;
                                
                                class Test {
                                    ImmutableSet<Boolean> test() {
                                        return ImmutableSet.of(Integer.signum(1) > 0, Integer.signum(2) >= 1, Integer.signum(3) <= 0, Integer.signum(4) < 1);
                                    }
                                }
                                """,
                        """
                                import com.google.common.collect.ImmutableSet;
                                
                                class Test {
                                    ImmutableSet<Boolean> test() {
                                        return ImmutableSet.of(Integer.signum(1) == 1, Integer.signum(2) == 1, Integer.signum(3) != 1, Integer.signum(4) != 1);
                                    }
                                }
                                """
                ));
    }

    @Test
    void testIntegerToUnsignedStringRecipe() {
        Recipe recipe = RewriteUtils.loadRecipe("PrimitiveRulesRecipes.IntegerToUnsignedStringRecipe");
        rewriteRun(
                spec -> spec.recipe(recipe)
                        .expectedCyclesThatMakeChanges(1).cycles(1)
                        .parser(JavaParser.fromJavaVersion().classpath(JavaParser.runtimeClasspath())),
                java(
                        """
                                import com.google.common.collect.ImmutableSet;
                                import com.google.common.primitives.UnsignedInts;
                                
                                class Test {
                                    ImmutableSet<String> test() {
                                        return ImmutableSet.of(UnsignedInts.toString(1), Integer.toUnsignedString(2, 10));
                                    }
                                }
                                """,
                        """
                                import com.google.common.collect.ImmutableSet;
                                
                                class Test {
                                    ImmutableSet<String> test() {
                                        return ImmutableSet.of(Integer.toUnsignedString(1), Integer.toUnsignedString(2));
                                    }
                                }
                                """
                ));
    }

    @Test
    void testIntegerToUnsignedStringWithRadixRecipe() {
        Recipe recipe = RewriteUtils.loadRecipe("PrimitiveRulesRecipes.IntegerToUnsignedStringWithRadixRecipe");
        rewriteRun(
                spec -> spec.recipe(recipe)
                        .expectedCyclesThatMakeChanges(1).cycles(1)
                        .parser(JavaParser.fromJavaVersion().classpath(JavaParser.runtimeClasspath())),
                java(
                        """
                                import com.google.common.primitives.UnsignedInts;
                                
                                class Test {
                                    String test() {
                                        return UnsignedInts.toString(1, 2);
                                    }
                                }
                                """,
                        """
                                class Test {
                                    String test() {
                                        return Integer.toUnsignedString(1, 2);
                                    }
                                }
                                """
                ));
    }

    @Test
    void testLessThanRecipe() {
        Recipe recipe = RewriteUtils.loadRecipe("PrimitiveRulesRecipes.LessThanRecipe");
        rewriteRun(
                spec -> spec.recipe(recipe)
                        .expectedCyclesThatMakeChanges(1).cycles(1)
                        .parser(JavaParser.fromJavaVersion().classpath(JavaParser.runtimeClasspath())),
                java(
                        """
                                import com.google.common.collect.ImmutableSet;
                                
                                class Test {
                                    ImmutableSet<Boolean> test() {
                                        return ImmutableSet.of(!((byte) 3 >= (byte) 4), !((char) 3 >= (char) 4), !((short) 3 >= (short) 4), !(3 >= 4), !(3L >= 4L), !(3F >= 4F), !(3.0 >= 4.0));
                                    }
                                }
                                """,
                        """
                                import com.google.common.collect.ImmutableSet;
                                
                                class Test {
                                    ImmutableSet<Boolean> test() {
                                        return ImmutableSet.of((byte) 3 < (byte) 4, (char) 3 < (char) 4, (short) 3 < (short) 4, 3 < 4, 3L < 4L, 3F < 4F, 3.0 < 4.0);
                                    }
                                }
                                """
                ));
    }

    @Test
    void testLessThanOrEqualToRecipe() {
        Recipe recipe = RewriteUtils.loadRecipe("PrimitiveRulesRecipes.LessThanOrEqualToRecipe");
        rewriteRun(
                spec -> spec.recipe(recipe)
                        .expectedCyclesThatMakeChanges(1).cycles(1)
                        .parser(JavaParser.fromJavaVersion().classpath(JavaParser.runtimeClasspath())),
                java(
                        """
                                import com.google.common.collect.ImmutableSet;
                                
                                class Test {
                                    ImmutableSet<Boolean> test() {
                                        return ImmutableSet.of(!((byte) 3 > (byte) 4), !((char) 3 > (char) 4), !((short) 3 > (short) 4), !(3 > 4), !(3L > 4L), !(3F > 4F), !(3.0 > 4.0));
                                    }
                                }
                                """,
                        """
                                import com.google.common.collect.ImmutableSet;
                                
                                class Test {
                                    ImmutableSet<Boolean> test() {
                                        return ImmutableSet.of((byte) 3 <= (byte) 4, (char) 3 <= (char) 4, (short) 3 <= (short) 4, 3 <= 4, 3L <= 4L, 3F <= 4F, 3.0 <= 4.0);
                                    }
                                }
                                """
                ));
    }

    @Test
    void testLongBytesRecipe() {
        Recipe recipe = RewriteUtils.loadRecipe("PrimitiveRulesRecipes.LongBytesRecipe");
        rewriteRun(
                spec -> spec.recipe(recipe)
                        .expectedCyclesThatMakeChanges(1).cycles(1)
                        .parser(JavaParser.fromJavaVersion().classpath(JavaParser.runtimeClasspath())),
                java(
                        """
                                import com.google.common.primitives.Longs;
                                
                                class Test {
                                    int test() {
                                        return Longs.BYTES;
                                    }
                                }
                                """,
                        """
                                class Test {
                                    int test() {
                                        return Long.BYTES;
                                    }
                                }
                                """
                ));
    }

    @Test
    void testLongCompareUnsignedRecipe() {
        Recipe recipe = RewriteUtils.loadRecipe("PrimitiveRulesRecipes.LongCompareUnsignedRecipe");
        rewriteRun(
                spec -> spec.recipe(recipe)
                        .expectedCyclesThatMakeChanges(1).cycles(1)
                        .parser(JavaParser.fromJavaVersion().classpath(JavaParser.runtimeClasspath())),
                java(
                        """
                                import com.google.common.primitives.UnsignedLongs;
                                
                                class Test {
                                    long test() {
                                        return UnsignedLongs.compare(1, 2);
                                    }
                                }
                                """,
                        """
                                class Test {
                                    long test() {
                                        return Long.compareUnsigned(1, 2);
                                    }
                                }
                                """
                ));
    }

    @Test
    void testLongDivideUnsignedRecipe() {
        Recipe recipe = RewriteUtils.loadRecipe("PrimitiveRulesRecipes.LongDivideUnsignedRecipe");
        rewriteRun(
                spec -> spec.recipe(recipe)
                        .expectedCyclesThatMakeChanges(1).cycles(1)
                        .parser(JavaParser.fromJavaVersion().classpath(JavaParser.runtimeClasspath())),
                java(
                        """
                                import com.google.common.primitives.UnsignedLongs;
                                
                                class Test {
                                    long test() {
                                        return UnsignedLongs.divide(1, 2);
                                    }
                                }
                                """,
                        """
                                class Test {
                                    long test() {
                                        return Long.divideUnsigned(1, 2);
                                    }
                                }
                                """
                ));
    }

    @Test
    void testLongHashCodeRecipe() {
        Recipe recipe = RewriteUtils.loadRecipe("PrimitiveRulesRecipes.LongHashCodeRecipe");
        rewriteRun(
                spec -> spec.recipe(recipe)
                        .expectedCyclesThatMakeChanges(1).cycles(1)
                        .parser(JavaParser.fromJavaVersion().classpath(JavaParser.runtimeClasspath())),
                java(
                        """
                                import com.google.common.primitives.Longs;
                                
                                class Test {
                                    int test() {
                                        return Longs.hashCode(1);
                                    }
                                }
                                """,
                        """
                                class Test {
                                    int test() {
                                        return Long.hashCode(1);
                                    }
                                }
                                """
                ));
    }

    @Test
    void testLongParseUnsignedLongRecipe() {
        Recipe recipe = RewriteUtils.loadRecipe("PrimitiveRulesRecipes.LongParseUnsignedLongRecipe");
        rewriteRun(
                spec -> spec.recipe(recipe)
                        .expectedCyclesThatMakeChanges(1).cycles(1)
                        .parser(JavaParser.fromJavaVersion().classpath(JavaParser.runtimeClasspath())),
                java(
                        """
                                import com.google.common.collect.ImmutableSet;
                                import com.google.common.primitives.UnsignedLongs;
                                
                                class Test {
                                    ImmutableSet<Long> test() {
                                        return ImmutableSet.of(UnsignedLongs.parseUnsignedLong("1"), Long.parseUnsignedLong("2", 10));
                                    }
                                }
                                """,
                        """
                                import com.google.common.collect.ImmutableSet;
                                
                                class Test {
                                    ImmutableSet<Long> test() {
                                        return ImmutableSet.of(Long.parseUnsignedLong("1"), Long.parseUnsignedLong("2"));
                                    }
                                }
                                """
                ));
    }

    @Test
    void testLongParseUnsignedLongWithRadixRecipe() {
        Recipe recipe = RewriteUtils.loadRecipe("PrimitiveRulesRecipes.LongParseUnsignedLongWithRadixRecipe");
        rewriteRun(
                spec -> spec.recipe(recipe)
                        .expectedCyclesThatMakeChanges(1).cycles(1)
                        .parser(JavaParser.fromJavaVersion().classpath(JavaParser.runtimeClasspath())),
                java(
                        """
                                import com.google.common.primitives.UnsignedLongs;
                                
                                class Test {
                                    long test() {
                                        return UnsignedLongs.parseUnsignedLong("1", 2);
                                    }
                                }
                                """,
                        """
                                class Test {
                                    long test() {
                                        return Long.parseUnsignedLong("1", 2);
                                    }
                                }
                                """
                ));
    }

    @Test
    void testLongRemainderUnsignedRecipe() {
        Recipe recipe = RewriteUtils.loadRecipe("PrimitiveRulesRecipes.LongRemainderUnsignedRecipe");
        rewriteRun(
                spec -> spec.recipe(recipe)
                        .expectedCyclesThatMakeChanges(1).cycles(1)
                        .parser(JavaParser.fromJavaVersion().classpath(JavaParser.runtimeClasspath())),
                java(
                        """
                                import com.google.common.primitives.UnsignedLongs;
                                
                                class Test {
                                    long test() {
                                        return UnsignedLongs.remainder(1, 2);
                                    }
                                }
                                """,
                        """
                                class Test {
                                    long test() {
                                        return Long.remainderUnsigned(1, 2);
                                    }
                                }
                                """
                ));
    }

    @Test
    void testLongSignumIsNegativeRecipe() {
        Recipe recipe = RewriteUtils.loadRecipe("PrimitiveRulesRecipes.LongSignumIsNegativeRecipe");
        rewriteRun(
                spec -> spec.recipe(recipe)
                        .expectedCyclesThatMakeChanges(1).cycles(1)
                        .parser(JavaParser.fromJavaVersion().classpath(JavaParser.runtimeClasspath())),
                java(
                        """
                                import com.google.common.collect.ImmutableSet;
                                
                                class Test {
                                    ImmutableSet<Boolean> test() {
                                        return ImmutableSet.of(Long.signum(1L) < 0, Long.signum(2L) <= -1, Long.signum(3L) >= 0, Long.signum(4L) > -1);
                                    }
                                }
                                """,
                        """
                                import com.google.common.collect.ImmutableSet;
                                
                                class Test {
                                    ImmutableSet<Boolean> test() {
                                        return ImmutableSet.of(Long.signum(1L) == -1, Long.signum(2L) == -1, Long.signum(3L) != -1, Long.signum(4L) != -1);
                                    }
                                }
                                """
                ));
    }

    @Test
    void testLongSignumIsPositiveRecipe() {
        Recipe recipe = RewriteUtils.loadRecipe("PrimitiveRulesRecipes.LongSignumIsPositiveRecipe");
        rewriteRun(
                spec -> spec.recipe(recipe)
                        .expectedCyclesThatMakeChanges(1).cycles(1)
                        .parser(JavaParser.fromJavaVersion().classpath(JavaParser.runtimeClasspath())),
                java(
                        """
                                import com.google.common.collect.ImmutableSet;
                                
                                class Test {
                                    ImmutableSet<Boolean> test() {
                                        return ImmutableSet.of(Long.signum(1L) > 0, Long.signum(2L) >= 1, Long.signum(3L) <= 0, Long.signum(4L) < 1);
                                    }
                                }
                                """,
                        """
                                import com.google.common.collect.ImmutableSet;
                                
                                class Test {
                                    ImmutableSet<Boolean> test() {
                                        return ImmutableSet.of(Long.signum(1L) == 1, Long.signum(2L) == 1, Long.signum(3L) != 1, Long.signum(4L) != 1);
                                    }
                                }
                                """
                ));
    }

    @Test
    void testLongToIntExactRecipe() {
        Recipe recipe = RewriteUtils.loadRecipe("PrimitiveRulesRecipes.LongToIntExactRecipe");
        rewriteRun(
                spec -> spec.recipe(recipe)
                        .expectedCyclesThatMakeChanges(1).cycles(1)
                        .parser(JavaParser.fromJavaVersion().classpath(JavaParser.runtimeClasspath())),
                java(
                        """
                                import com.google.common.primitives.Ints;
                                
                                class Test {
                                    int test() {
                                        return Ints.checkedCast(Long.MAX_VALUE);
                                    }
                                }
                                """,
                        """
                                class Test {
                                    int test() {
                                        return Math.toIntExact(Long.MAX_VALUE);
                                    }
                                }
                                """
                ));
    }

    @Test
    void testLongToUnsignedStringRecipe() {
        Recipe recipe = RewriteUtils.loadRecipe("PrimitiveRulesRecipes.LongToUnsignedStringRecipe");
        rewriteRun(
                spec -> spec.recipe(recipe)
                        .expectedCyclesThatMakeChanges(1).cycles(1)
                        .parser(JavaParser.fromJavaVersion().classpath(JavaParser.runtimeClasspath())),
                java(
                        """
                                import com.google.common.collect.ImmutableSet;
                                import com.google.common.primitives.UnsignedLongs;
                                
                                class Test {
                                    ImmutableSet<String> test() {
                                        return ImmutableSet.of(UnsignedLongs.toString(1), Long.toUnsignedString(2, 10));
                                    }
                                }
                                """,
                        """
                                import com.google.common.collect.ImmutableSet;
                                
                                class Test {
                                    ImmutableSet<String> test() {
                                        return ImmutableSet.of(Long.toUnsignedString(1), Long.toUnsignedString(2));
                                    }
                                }
                                """
                ));
    }

    @Test
    void testLongToUnsignedStringWithRadixRecipe() {
        Recipe recipe = RewriteUtils.loadRecipe("PrimitiveRulesRecipes.LongToUnsignedStringWithRadixRecipe");
        rewriteRun(
                spec -> spec.recipe(recipe)
                        .expectedCyclesThatMakeChanges(1).cycles(1)
                        .parser(JavaParser.fromJavaVersion().classpath(JavaParser.runtimeClasspath())),
                java(
                        """
                                import com.google.common.primitives.UnsignedLongs;
                                
                                class Test {
                                    String test() {
                                        return UnsignedLongs.toString(1, 2);
                                    }
                                }
                                """,
                        """
                                class Test {
                                    String test() {
                                        return Long.toUnsignedString(1, 2);
                                    }
                                }
                                """
                ));
    }

    @Test
    void testShortBytesRecipe() {
        Recipe recipe = RewriteUtils.loadRecipe("PrimitiveRulesRecipes.ShortBytesRecipe");
        rewriteRun(
                spec -> spec.recipe(recipe)
                        .expectedCyclesThatMakeChanges(1).cycles(1)
                        .parser(JavaParser.fromJavaVersion().classpath(JavaParser.runtimeClasspath())),
                java(
                        """
                                import com.google.common.primitives.Shorts;
                                
                                class Test {
                                    int test() {
                                        return Shorts.BYTES;
                                    }
                                }
                                """,
                        """
                                class Test {
                                    int test() {
                                        return Short.BYTES;
                                    }
                                }
                                """
                ));
    }

    @Test
    void testShortHashCodeRecipe() {
        Recipe recipe = RewriteUtils.loadRecipe("PrimitiveRulesRecipes.ShortHashCodeRecipe");
        rewriteRun(
                spec -> spec.recipe(recipe)
                        .expectedCyclesThatMakeChanges(1).cycles(1)
                        .parser(JavaParser.fromJavaVersion().classpath(JavaParser.runtimeClasspath())),
                java(
                        """
                                import com.google.common.primitives.Shorts;
                                
                                class Test {
                                    int test() {
                                        return Shorts.hashCode((short) 1);
                                    }
                                }
                                """,
                        """
                                class Test {
                                    int test() {
                                        return Short.hashCode((short) 1);
                                    }
                                }
                                """
                ));
    }
}
