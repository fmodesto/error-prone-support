package tech.picnic.errorprone.refasterrules.rewrite;

import java.util.List;

import org.junit.jupiter.api.Assumptions;
import org.openrewrite.Recipe;
import org.openrewrite.config.CompositeRecipe;
import org.openrewrite.java.OrderImports;
import org.openrewrite.java.RemoveUnusedImports;
import org.openrewrite.java.ShortenFullyQualifiedTypeReferences;
import org.openrewrite.java.format.AutoFormat;

public class RewriteUtils {
    public static Recipe loadRecipe(String name) {
        try {
            Class<?> recipeClass = Class.forName("tech.picnic.errorprone.refasterrules." + name.replace('.', '$'));
            Recipe recipe = (Recipe) recipeClass.getConstructor().newInstance();
            return new CompositeRecipe(List.of(recipe, new RemoveUnusedImports(), new ShortenFullyQualifiedTypeReferences(),
                    new OrderImports(true), new AutoFormat()));
        } catch (Exception e) {
            Assumptions.abort("Recipe " + name + " doesn't exist. " + e.getMessage());
            throw new RuntimeException(e);
        }
    }
}
