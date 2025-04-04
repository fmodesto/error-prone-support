package tech.picnic.errorprone.refasterrules.rewrite;

import org.junit.jupiter.api.Test;
import org.openrewrite.Recipe;
import org.openrewrite.java.JavaParser;
import org.openrewrite.test.RewriteTest;

import static org.openrewrite.java.Assertions.java;

final class MockitoRulesRecipesTest implements RewriteTest {

    @Test
    void testInvocationOnMockGetArgumentsRecipe() {
        Recipe recipe = RewriteUtils.loadRecipe("MockitoRulesRecipes.InvocationOnMockGetArgumentsRecipe");
        rewriteRun(
                spec -> spec.recipe(recipe)
                        .expectedCyclesThatMakeChanges(1).cycles(1)
                        .parser(JavaParser.fromJavaVersion().classpath(JavaParser.runtimeClasspath())),
                java(
                        """
                                import org.mockito.invocation.InvocationOnMock;
                                
                                class Test {
                                    Object test() {
                                        return ((InvocationOnMock) null).getArguments()[0];
                                    }
                                }
                                """,
                        """
                                import org.mockito.invocation.InvocationOnMock;
                                
                                class Test {
                                    Object test() {
                                        return ((InvocationOnMock) null).getArgument(0);
                                    }
                                }
                                """
                ));
    }

    @Test
    void testInvocationOnMockGetArgumentsWithTypeParameterRecipe() {
        Recipe recipe = RewriteUtils.loadRecipe("MockitoRulesRecipes.InvocationOnMockGetArgumentsWithTypeParameterRecipe");
        rewriteRun(
                spec -> spec.recipe(recipe)
                        .expectedCyclesThatMakeChanges(1).cycles(1)
                        .parser(JavaParser.fromJavaVersion().classpath(JavaParser.runtimeClasspath())),
                java(
                        """
                                import com.google.common.collect.ImmutableSet;
                                import org.mockito.invocation.InvocationOnMock;
                                
                                class Test {
                                    ImmutableSet<Number> test() {
                                        return ImmutableSet.of(((InvocationOnMock) null).getArgument(0, Integer.class), (Double) ((InvocationOnMock) null).getArgument(1));
                                    }
                                }
                                """,
                        """
                                import com.google.common.collect.ImmutableSet;
                                import org.mockito.invocation.InvocationOnMock;
                                
                                class Test {
                                    ImmutableSet<Number> test() {
                                        return ImmutableSet.of(((InvocationOnMock) null).<Integer>getArgument(0), ((InvocationOnMock) null).<Double>getArgument(1));
                                    }
                                }
                                """
                ));
    }

    @Test
    void testNeverRecipe() {
        Recipe recipe = RewriteUtils.loadRecipe("MockitoRulesRecipes.NeverRecipe");
        rewriteRun(
                spec -> spec.recipe(recipe)
                        .expectedCyclesThatMakeChanges(1).cycles(1)
                        .parser(JavaParser.fromJavaVersion().classpath(JavaParser.runtimeClasspath())),
                java(
                        """
                                import org.mockito.Mockito;
                                import org.mockito.verification.VerificationMode;
                                
                                class Test {
                                    VerificationMode test() {
                                        return Mockito.times(0);
                                    }
                                }
                                """,
                        """
                                import org.mockito.Mockito;
                                import org.mockito.verification.VerificationMode;
                                
                                class Test {
                                    VerificationMode test() {
                                        return Mockito.never();
                                    }
                                }
                                """
                ));
    }

    @Test
    void testVerifyOnceRecipe() {
        Recipe recipe = RewriteUtils.loadRecipe("MockitoRulesRecipes.VerifyOnceRecipe");
        rewriteRun(
                spec -> spec.recipe(recipe)
                        .expectedCyclesThatMakeChanges(1).cycles(1)
                        .parser(JavaParser.fromJavaVersion().classpath(JavaParser.runtimeClasspath())),
                java(
                        """
                                import org.mockito.Mockito;
                                
                                class Test {
                                    Object test() {
                                        return Mockito.verify(Mockito.mock(Object.class), Mockito.times(1));
                                    }
                                }
                                """,
                        """
                                import org.mockito.Mockito;
                                
                                class Test {
                                    Object test() {
                                        return Mockito.verify(Mockito.mock(Object.class));
                                    }
                                }
                                """
                ));
    }
}
