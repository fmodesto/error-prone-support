package tech.picnic.errorprone.refasterrules.rewrite;

import org.junit.jupiter.api.Test;
import org.openrewrite.Recipe;
import org.openrewrite.java.JavaParser;
import org.openrewrite.test.RewriteTest;

import static org.openrewrite.java.Assertions.java;

final class ClassRulesRecipesTest implements RewriteTest {

    @Test
    void testClassIsInstanceRecipe() {
        Recipe recipe = RewriteUtils.loadRecipe("ClassRulesRecipes.ClassIsInstanceRecipe");
        rewriteRun(
                spec -> spec.recipe(recipe)
                        .expectedCyclesThatMakeChanges(1).cycles(1)
                        .parser(JavaParser.fromJavaVersion().classpath(JavaParser.runtimeClasspath())),
                java(
                        """
                                class Test {
                                    boolean test() {
                                        return CharSequence.class.isAssignableFrom("foo".getClass());
                                    }
                                }
                                """,
                        """
                                class Test {
                                    boolean test() {
                                        return CharSequence.class.isInstance("foo");
                                    }
                                }
                                """
                ));
    }

    @Test
    void testClassLiteralIsInstancePredicateRecipe() {
        Recipe recipe = RewriteUtils.loadRecipe("ClassRulesRecipes.ClassLiteralIsInstancePredicateRecipe");
        rewriteRun(
                spec -> spec.recipe(recipe)
                        .expectedCyclesThatMakeChanges(1).cycles(1)
                        .parser(JavaParser.fromJavaVersion().classpath(JavaParser.runtimeClasspath())),
                java(
                        """
                                import java.util.function.Predicate;
                                
                                class Test {
                                    Predicate<String> test() {
                                        return s -> s instanceof CharSequence;
                                    }
                                }
                                """,
                        """
                                import java.util.function.Predicate;
                                
                                class Test {
                                    Predicate<String> test() {
                                        return CharSequence.class::isInstance;
                                    }
                                }
                                """
                ));
    }

    @Test
    void testClassReferenceCastRecipe() {
        Recipe recipe = RewriteUtils.loadRecipe("ClassRulesRecipes.ClassReferenceCastRecipe");
        rewriteRun(
                spec -> spec.recipe(recipe)
                        .expectedCyclesThatMakeChanges(1).cycles(1)
                        .parser(JavaParser.fromJavaVersion().classpath(JavaParser.runtimeClasspath())),
                java(
                        """
                                import java.util.function.Function;
                                
                                class Test {
                                    Function<Number, Integer> test() {
                                        return i -> Integer.class.cast(i);
                                    }
                                }
                                """,
                        """
                                import java.util.function.Function;
                                
                                class Test {
                                    Function<Number, Integer> test() {
                                        return Integer.class::cast;
                                    }
                                }
                                """
                ));
    }

    @Test
    void testClassReferenceIsInstancePredicateRecipe() {
        Recipe recipe = RewriteUtils.loadRecipe("ClassRulesRecipes.ClassReferenceIsInstancePredicateRecipe");
        rewriteRun(
                spec -> spec.recipe(recipe)
                        .expectedCyclesThatMakeChanges(1).cycles(1)
                        .parser(JavaParser.fromJavaVersion().classpath(JavaParser.runtimeClasspath())),
                java(
                        """
                                import java.util.function.Predicate;
                                
                                class Test {
                                    Predicate<String> test() {
                                        Class<?> clazz = CharSequence.class;
                                        return s -> clazz.isInstance(s);
                                    }
                                }
                                """,
                        """
                                import java.util.function.Predicate;
                                
                                class Test {
                                    Predicate<String> test() {
                                        Class<?> clazz = CharSequence.class;
                                        return clazz::isInstance;
                                    }
                                }
                                """
                ));
    }

    @Test
    void testInstanceofRecipe() {
        Recipe recipe = RewriteUtils.loadRecipe("ClassRulesRecipes.InstanceofRecipe");
        rewriteRun(
                spec -> spec.recipe(recipe)
                        .expectedCyclesThatMakeChanges(1).cycles(1)
                        .parser(JavaParser.fromJavaVersion().classpath(JavaParser.runtimeClasspath())),
                java(
                        """
                                import com.google.common.collect.ImmutableSet;
                                
                                class Test {
                                    ImmutableSet<Boolean> test() {
                                        Class<?> clazz = CharSequence.class;
                                        return ImmutableSet.of(CharSequence.class.isInstance("foo"), clazz.isInstance("bar"));
                                    }
                                }
                                """,
                        """
                                import com.google.common.collect.ImmutableSet;
                                
                                class Test {
                                    ImmutableSet<Boolean> test() {
                                        Class<?> clazz = CharSequence.class;
                                        return ImmutableSet.of("foo" instanceof CharSequence, clazz.isInstance("bar"));
                                    }
                                }
                                """
                ));
    }
}
