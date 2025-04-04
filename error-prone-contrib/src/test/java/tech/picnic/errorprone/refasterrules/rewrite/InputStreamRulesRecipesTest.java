package tech.picnic.errorprone.refasterrules.rewrite;

import org.junit.jupiter.api.Test;
import org.openrewrite.Recipe;
import org.openrewrite.java.JavaParser;
import org.openrewrite.test.RewriteTest;

import static org.openrewrite.java.Assertions.java;

final class InputStreamRulesRecipesTest implements RewriteTest {

    @Test
    void testInputStreamReadAllBytesRecipe() {
        Recipe recipe = RewriteUtils.loadRecipe("InputStreamRulesRecipes.InputStreamReadAllBytesRecipe");
        rewriteRun(
                spec -> spec.recipe(recipe)
                        .expectedCyclesThatMakeChanges(1).cycles(1)
                        .parser(JavaParser.fromJavaVersion().classpath(JavaParser.runtimeClasspath())),
                java(
                        """
                                import com.google.common.io.ByteStreams;
                                
                                import java.io.ByteArrayInputStream;
                                import java.io.IOException;
                                
                                class Test {
                                    byte[] test() throws IOException {
                                        return ByteStreams.toByteArray(new ByteArrayInputStream(new byte[0]));
                                    }
                                }
                                """,
                        """
                                import java.io.ByteArrayInputStream;
                                import java.io.IOException;
                                
                                class Test {
                                    byte[] test() throws IOException {
                                        return new ByteArrayInputStream(new byte[0]).readAllBytes();
                                    }
                                }
                                """
                ));
    }

    @Test
    void testInputStreamReadNBytesRecipe() {
        Recipe recipe = RewriteUtils.loadRecipe("InputStreamRulesRecipes.InputStreamReadNBytesRecipe");
        rewriteRun(
                spec -> spec.recipe(recipe)
                        .expectedCyclesThatMakeChanges(1).cycles(1)
                        .parser(JavaParser.fromJavaVersion().classpath(JavaParser.runtimeClasspath())),
                java(
                        """
                                import com.google.common.io.ByteStreams;
                                
                                import java.io.ByteArrayInputStream;
                                import java.io.IOException;
                                
                                class Test {
                                    byte[] test() throws IOException {
                                        return ByteStreams.limit(new ByteArrayInputStream(new byte[0]), 0).readAllBytes();
                                    }
                                }
                                """,
                        """
                                import java.io.ByteArrayInputStream;
                                import java.io.IOException;
                                
                                class Test {
                                    byte[] test() throws IOException {
                                        return new ByteArrayInputStream(new byte[0]).readNBytes(0);
                                    }
                                }
                                """
                ));
    }

    @Test
    void testInputStreamSkipNBytesRecipe() {
        Recipe recipe = RewriteUtils.loadRecipe("InputStreamRulesRecipes.InputStreamSkipNBytesRecipe");
        rewriteRun(
                spec -> spec.recipe(recipe)
                        .expectedCyclesThatMakeChanges(1).cycles(1)
                        .parser(JavaParser.fromJavaVersion().classpath(JavaParser.runtimeClasspath())),
                java(
                        """
                                import com.google.common.io.ByteStreams;
                                
                                import java.io.ByteArrayInputStream;
                                import java.io.IOException;
                                
                                class Test {
                                    void test() throws IOException {
                                        ByteStreams.skipFully(new ByteArrayInputStream(new byte[0]), 0);
                                    }
                                }
                                """,
                        """
                                import java.io.ByteArrayInputStream;
                                import java.io.IOException;
                                
                                class Test {
                                    void test() throws IOException {
                                        new ByteArrayInputStream(new byte[0]).skipNBytes(0);
                                    }
                                }
                                """
                ));
    }

    @Test
    void testInputStreamTransferToRecipe() {
        Recipe recipe = RewriteUtils.loadRecipe("InputStreamRulesRecipes.InputStreamTransferToRecipe");
        rewriteRun(
                spec -> spec.recipe(recipe)
                        .expectedCyclesThatMakeChanges(1).cycles(1)
                        .parser(JavaParser.fromJavaVersion().classpath(JavaParser.runtimeClasspath())),
                java(
                        """
                                import com.google.common.io.ByteStreams;
                                
                                import java.io.ByteArrayInputStream;
                                import java.io.ByteArrayOutputStream;
                                import java.io.IOException;
                                
                                class Test {
                                    long test() throws IOException {
                                        return ByteStreams.copy(new ByteArrayInputStream(new byte[0]), new ByteArrayOutputStream());
                                    }
                                }
                                """,
                        """
                                import java.io.ByteArrayInputStream;
                                import java.io.ByteArrayOutputStream;
                                import java.io.IOException;
                                
                                class Test {
                                    long test() throws IOException {
                                        return new ByteArrayInputStream(new byte[0]).transferTo(new ByteArrayOutputStream());
                                    }
                                }
                                """
                ));
    }
}
