package tech.picnic.errorprone.refasterrules.rewrite;

import org.junit.jupiter.api.Test;
import org.openrewrite.Recipe;
import org.openrewrite.java.JavaParser;
import org.openrewrite.test.RewriteTest;

import static org.openrewrite.java.Assertions.java;

final class ImmutableTableRulesRecipesTest implements RewriteTest {

    @Test
    void testCellToImmutableTableRecipe() {
        Recipe recipe = RewriteUtils.loadRecipe("ImmutableTableRulesRecipes.CellToImmutableTableRecipe");
        rewriteRun(
                spec -> spec.recipe(recipe)
                        .expectedCyclesThatMakeChanges(1).cycles(1)
                        .parser(JavaParser.fromJavaVersion().classpath(JavaParser.runtimeClasspath())),
                java(
                        """
                                import com.google.common.collect.ImmutableSet;
                                import com.google.common.collect.ImmutableTable;
                                import com.google.common.collect.Table;
                                import com.google.common.collect.Tables;
                                
                                import java.util.stream.Stream;
                                
                                class Test {
                                    ImmutableSet<ImmutableTable<String, Integer, String>> test() {
                                        return ImmutableSet.of(ImmutableTable.<String, Integer, String>builder().put(Tables.immutableCell("foo", 1, "bar")).buildOrThrow(), Stream.of(Tables.immutableCell("baz", 2, "qux")).collect(ImmutableTable.toImmutableTable(Table.Cell::getRowKey, Table.Cell::getColumnKey, Table.Cell::getValue)));
                                    }
                                }
                                """,
                        """
                                import com.google.common.collect.ImmutableSet;
                                import com.google.common.collect.ImmutableTable;
                                import com.google.common.collect.Tables;
                                
                                class Test {
                                    ImmutableSet<ImmutableTable<String, Integer, String>> test() {
                                        return ImmutableSet.of(ImmutableTable.of(Tables.immutableCell("foo", 1, "bar").getRowKey(), Tables.immutableCell("foo", 1, "bar").getColumnKey(), Tables.immutableCell("foo", 1, "bar").getValue()), ImmutableTable.of(Tables.immutableCell("baz", 2, "qux").getRowKey(), Tables.immutableCell("baz", 2, "qux").getColumnKey(), Tables.immutableCell("baz", 2, "qux").getValue()));
                                    }
                                }
                                """
                ));
    }

    @Test
    void testImmutableTableBuilderRecipe() {
        Recipe recipe = RewriteUtils.loadRecipe("ImmutableTableRulesRecipes.ImmutableTableBuilderRecipe");
        rewriteRun(
                spec -> spec.recipe(recipe)
                        .expectedCyclesThatMakeChanges(1).cycles(1)
                        .parser(JavaParser.fromJavaVersion().classpath(JavaParser.runtimeClasspath())),
                java(
                        """
                                import com.google.common.collect.ImmutableTable;
                                
                                class Test {
                                    ImmutableTable.Builder<String, Integer, String> test() {
                                        return new ImmutableTable.Builder<>();
                                    }
                                }
                                """,
                        """
                                import com.google.common.collect.ImmutableTable;
                                
                                class Test {
                                    ImmutableTable.Builder<String, Integer, String> test() {
                                        return ImmutableTable.builder();
                                    }
                                }
                                """
                ));
    }

    @Test
    void testImmutableTableBuilderBuildOrThrowRecipe() {
        Recipe recipe = RewriteUtils.loadRecipe("ImmutableTableRulesRecipes.ImmutableTableBuilderBuildOrThrowRecipe");
        rewriteRun(
                spec -> spec.recipe(recipe)
                        .expectedCyclesThatMakeChanges(1).cycles(1)
                        .parser(JavaParser.fromJavaVersion().classpath(JavaParser.runtimeClasspath())),
                java(
                        """
                                import com.google.common.collect.ImmutableTable;
                                
                                class Test {
                                    ImmutableTable<Object, Object, Object> test() {
                                        return ImmutableTable.builder().build();
                                    }
                                }
                                """,
                        """
                                import com.google.common.collect.ImmutableTable;
                                
                                class Test {
                                    ImmutableTable<Object, Object, Object> test() {
                                        return ImmutableTable.builder().buildOrThrow();
                                    }
                                }
                                """
                ));
    }

    @Test
    void testImmutableTableOfRecipe() {
        Recipe recipe = RewriteUtils.loadRecipe("ImmutableTableRulesRecipes.ImmutableTableOfRecipe");
        rewriteRun(
                spec -> spec.recipe(recipe)
                        .expectedCyclesThatMakeChanges(1).cycles(1)
                        .parser(JavaParser.fromJavaVersion().classpath(JavaParser.runtimeClasspath())),
                java(
                        """
                                import com.google.common.collect.ImmutableTable;
                                
                                class Test {
                                    ImmutableTable<String, String, String> test() {
                                        return ImmutableTable.<String, String, String>builder().buildOrThrow();
                                    }
                                }
                                """,
                        """
                                import com.google.common.collect.ImmutableTable;
                                
                                class Test {
                                    ImmutableTable<String, String, String> test() {
                                        return ImmutableTable.of();
                                    }
                                }
                                """
                ));
    }

    @Test
    void testStreamOfCellsToImmutableTableRecipe() {
        Recipe recipe = RewriteUtils.loadRecipe("ImmutableTableRulesRecipes.StreamOfCellsToImmutableTableRecipe");
        rewriteRun(
                spec -> spec.recipe(recipe)
                        .expectedCyclesThatMakeChanges(1).cycles(1)
                        .parser(JavaParser.fromJavaVersion().classpath(JavaParser.runtimeClasspath())),
                java(
                        """
                                import com.google.common.collect.ImmutableTable;
                                import com.google.common.collect.Table;
                                import com.google.common.collect.Tables;
                                
                                import java.util.stream.Stream;
                                
                                class Test {
                                    ImmutableTable<Integer, String, Integer> test() {
                                        return Stream.of(1, 2, 3).map(n -> Tables.immutableCell(n, n.toString(), n * 2)).collect(ImmutableTable.toImmutableTable(Table.Cell::getRowKey, Table.Cell::getColumnKey, Table.Cell::getValue));
                                    }
                                }
                                """,
                        """
                                import com.google.common.collect.ImmutableTable;
                                
                                import java.util.stream.Stream;
                                
                                class Test {
                                    ImmutableTable<Integer, String, Integer> test() {
                                        return Stream.of(1, 2, 3).collect(ImmutableTable.toImmutableTable(n -> n, n -> n.toString(), n -> n * 2));
                                    }
                                }
                                """
                ));
    }
}
