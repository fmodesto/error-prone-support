package tech.picnic.errorprone.bugpatterns;

import com.google.errorprone.BugCheckerRefactoringTestHelper;
import com.google.errorprone.BugCheckerRefactoringTestHelper.TestMode;
import com.google.errorprone.CompilationTestHelper;
import org.junit.jupiter.api.Test;

final class ExplicitArgumentEnumerationTest {
  @Test
  void identification() {
    CompilationTestHelper.newInstance(ExplicitArgumentEnumeration.class, getClass())
        .addSourceLines(
            "A.java",
            "import static org.assertj.core.api.Assertions.assertThat;",
            "",
            "import com.google.common.collect.ImmutableList;",
            "import com.google.errorprone.CompilationTestHelper;",
            "import com.google.errorprone.bugpatterns.BugChecker;",
            "import org.jooq.impl.DSL;",
            "import reactor.core.publisher.Flux;",
            "import reactor.test.StepVerifier;",
            "",
            "class A {",
            "  // BUG: Diagnostic contains:",
            "  private final int value = unaryMethod(ImmutableList.of(1, 2));",
            "",
            "  void m() {",
            "    ImmutableList<String> list = ImmutableList.of();",
            "    assertThat(ImmutableList.of()).containsAnyElementsOf(list);",
            "",
            "    ImmutableList.<ImmutableList<String>>builder().add(ImmutableList.of());",
            "",
            "    DSL.row(ImmutableList.of(1, 2));",
            "",
            "    // BUG: Diagnostic contains:",
            "    unaryMethod(ImmutableList.of(1, 2));",
            "    unaryMethodWithLessVisibleOverload(ImmutableList.of(1, 2));",
            "    binaryMethod(ImmutableList.of(1, 2), 3);",
            "",
            "    ImmutableList.builder()",
            "        // BUG: Diagnostic contains:",
            "        .addAll(ImmutableList.of())",
            "        // BUG: Diagnostic contains:",
            "        .addAll(ImmutableList.copyOf(new String[0]))",
            "        .addAll(ImmutableList.copyOf(ImmutableList.of()))",
            "        .build();",
            "",
            "    assertThat(ImmutableList.of(1))",
            "        // BUG: Diagnostic contains:",
            "        .containsAnyElementsOf(ImmutableList.of(1))",
            "        // BUG: Diagnostic contains:",
            "        .isSubsetOf(ImmutableList.of(1));",
            "",
            "    Flux.just(1, 2)",
            "        .as(StepVerifier::create)",
            "        // BUG: Diagnostic contains:",
            "        .expectNextSequence(ImmutableList.of(1, 2))",
            "        .verifyComplete();",
            "",
            "    CompilationTestHelper.newInstance(BugChecker.class, getClass())",
            "        // BUG: Diagnostic contains:",
            "        .setArgs(ImmutableList.of(\"foo\"))",
            "        .withClasspath();",
            "  }",
            "",
            "  private int unaryMethod(ImmutableList<Integer> args) {",
            "    return 0;",
            "  }",
            "",
            "  private int unaryMethod(Integer... args) {",
            "    return unaryMethod(ImmutableList.copyOf(args));",
            "  }",
            "",
            "  void unaryMethodWithLessVisibleOverload(ImmutableList<Integer> args) {}",
            "",
            "  private void unaryMethodWithLessVisibleOverload(Integer... args) {",
            "    unaryMethodWithLessVisibleOverload(ImmutableList.copyOf(args));",
            "  }",
            "",
            "  private void binaryMethod(ImmutableList<Integer> args, int extraArg) {}",
            "",
            "  private void binaryMethod(Integer... args) {",
            "    binaryMethod(ImmutableList.copyOf(args), 0);",
            "  }",
            "}")
        .doTest();
  }

  @Test
  void replacement() {
    BugCheckerRefactoringTestHelper.newInstance(ExplicitArgumentEnumeration.class, getClass())
        .addInputLines(
            "A.java",
            "import static org.assertj.core.api.Assertions.assertThat;",
            "",
            "import com.google.common.collect.ImmutableList;",
            "import com.google.common.collect.ImmutableMultiset;",
            "import com.google.common.collect.ImmutableSet;",
            "import com.google.errorprone.BugCheckerRefactoringTestHelper;",
            "import com.google.errorprone.CompilationTestHelper;",
            "import com.google.errorprone.bugpatterns.BugChecker;",
            "import java.util.Arrays;",
            "import java.util.List;",
            "import java.util.Set;",
            "import reactor.core.publisher.Flux;",
            "import reactor.test.StepVerifier;",
            "",
            "class A {",
            "  void m() {",
            "    ImmutableList.builder().addAll(ImmutableList.of()).build();",
            "",
            "    assertThat(ImmutableList.of()).containsAnyElementsOf(ImmutableMultiset.of());",
            "    assertThat(ImmutableList.of()).containsAll(ImmutableSet.of());",
            "    assertThat(ImmutableList.of()).containsExactlyElementsOf(List.of());",
            "    assertThat(ImmutableList.of()).containsExactlyInAnyOrderElementsOf(Set.of());",
            "    assertThat(ImmutableList.of()).containsSequence(Arrays.asList());",
            "    assertThat(ImmutableList.of()).containsSubsequence(ImmutableList.of(1));",
            "    assertThat(ImmutableList.of()).doesNotContainAnyElementsOf(ImmutableMultiset.of(2));",
            "    assertThat(ImmutableList.of()).doesNotContainSequence(ImmutableSet.of(3));",
            "    assertThat(ImmutableList.of()).doesNotContainSubsequence(List.of(4));",
            "    assertThat(ImmutableList.of()).hasSameElementsAs(Set.of(5));",
            "    assertThat(ImmutableList.of()).isSubsetOf(Arrays.asList(6));",
            "",
            "    Flux.empty()",
            "        .as(StepVerifier::create)",
            "        .expectNextSequence(ImmutableList.of(1, 2))",
            "        .verifyComplete();",
            "",
            "    BugCheckerRefactoringTestHelper.newInstance(BugChecker.class, getClass())",
            "        .setArgs(ImmutableList.of(\"foo\", \"bar\"));",
            "    CompilationTestHelper.newInstance(BugChecker.class, getClass())",
            "        .setArgs(ImmutableList.of(\"foo\", \"bar\"));",
            "  }",
            "}")
        .addOutputLines(
            "A.java",
            "import static org.assertj.core.api.Assertions.assertThat;",
            "",
            "import com.google.common.collect.ImmutableList;",
            "import com.google.common.collect.ImmutableMultiset;",
            "import com.google.common.collect.ImmutableSet;",
            "import com.google.errorprone.BugCheckerRefactoringTestHelper;",
            "import com.google.errorprone.CompilationTestHelper;",
            "import com.google.errorprone.bugpatterns.BugChecker;",
            "import java.util.Arrays;",
            "import java.util.List;",
            "import java.util.Set;",
            "import reactor.core.publisher.Flux;",
            "import reactor.test.StepVerifier;",
            "",
            "class A {",
            "  void m() {",
            "    ImmutableList.builder().add().build();",
            "",
            "    assertThat(ImmutableList.of()).containsAnyOf();",
            "    assertThat(ImmutableList.of()).contains();",
            "    assertThat(ImmutableList.of()).containsExactly();",
            "    assertThat(ImmutableList.of()).containsExactlyInAnyOrder();",
            "    assertThat(ImmutableList.of()).containsSequence();",
            "    assertThat(ImmutableList.of()).containsSubsequence(1);",
            "    assertThat(ImmutableList.of()).doesNotContain(2);",
            "    assertThat(ImmutableList.of()).doesNotContainSequence(3);",
            "    assertThat(ImmutableList.of()).doesNotContainSubsequence(4);",
            "    assertThat(ImmutableList.of()).containsOnly(5);",
            "    assertThat(ImmutableList.of()).isSubsetOf(6);",
            "",
            "    Flux.empty().as(StepVerifier::create).expectNext(1, 2).verifyComplete();",
            "",
            "    BugCheckerRefactoringTestHelper.newInstance(BugChecker.class, getClass()).setArgs(\"foo\", \"bar\");",
            "    CompilationTestHelper.newInstance(BugChecker.class, getClass()).setArgs(\"foo\", \"bar\");",
            "  }",
            "}")
        .doTest(TestMode.TEXT_MATCH);
  }
}
