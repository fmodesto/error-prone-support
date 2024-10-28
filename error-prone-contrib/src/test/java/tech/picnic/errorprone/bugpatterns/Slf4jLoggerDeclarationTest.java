package tech.picnic.errorprone.bugpatterns;

import com.google.common.collect.ImmutableList;
import com.google.errorprone.BugCheckerRefactoringTestHelper;
import com.google.errorprone.BugCheckerRefactoringTestHelper.TestMode;
import com.google.errorprone.CompilationTestHelper;
import org.junit.jupiter.api.Test;

final class Slf4jLoggerDeclarationTest {
  @Test
  void identification() {
    CompilationTestHelper.newInstance(Slf4jLoggerDeclaration.class, getClass())
        .addSourceLines(
            "A.java",
            "import static java.lang.Class.forName;",
            "",
            "import org.slf4j.Logger;",
            "import org.slf4j.LoggerFactory;",
            "",
            "class A {",
            "  private static final long serialVersionUID = 1L;",
            "  private static final Logger LOG = LoggerFactory.getLogger(A.class);",
            "",
            "  abstract static class DynamicLogger {",
            "    private final Logger log = LoggerFactory.getLogger(getClass());",
            "  }",
            "",
            "  abstract static class DynamicLoggerWithExplicitThis {",
            "    private final Logger log = LoggerFactory.getLogger(this.getClass());",
            "  }",
            "",
            "  static final class StaticLogger {",
            "    private static final Logger LOG = LoggerFactory.getLogger(StaticLogger.class);",
            "  }",
            "",
            "  static final class StaticLoggerWithCustomIdentifier {",
            "    private static final Logger LOG = LoggerFactory.getLogger(\"custom-identifier\");",
            "  }",
            "",
            "  interface StaticLoggerForInterface {",
            "    Logger LOG = LoggerFactory.getLogger(StaticLoggerForInterface.class);",
            "  }",
            "",
            "  abstract static class DynamicLoggerForWrongTypeWithoutReceiver {",
            "    // BUG: Diagnostic contains:",
            "    private final Logger log = LoggerFactory.getLogger(forName(\"A.class\"));",
            "",
            "    DynamicLoggerForWrongTypeWithoutReceiver() throws ClassNotFoundException {}",
            "  }",
            "",
            "  abstract static class DynamicLoggerForWrongTypeWithoutSymbol {",
            "    // BUG: Diagnostic contains:",
            "    private final Logger log = LoggerFactory.getLogger(\"foo\".getClass());",
            "  }",
            "",
            "  abstract static class DynamicLoggerForWrongTypeWithSymbol {",
            "    // BUG: Diagnostic contains:",
            "    private final Logger log = LoggerFactory.getLogger(new A().getClass());",
            "  }",
            "",
            "  static final class NonAbstractDynamicLogger {",
            "    // BUG: Diagnostic contains:",
            "    private final Logger log = LoggerFactory.getLogger(getClass());",
            "  }",
            "",
            "  abstract static class DynamicLoggerWithMissingModifier {",
            "    // BUG: Diagnostic contains:",
            "    final Logger log = LoggerFactory.getLogger(getClass());",
            "  }",
            "",
            "  abstract static class DynamicLoggerWithExcessModifier {",
            "    // BUG: Diagnostic contains:",
            "    private final transient Logger log = LoggerFactory.getLogger(getClass());",
            "  }",
            "",
            "  abstract static class MisnamedDynamicLogger {",
            "    // BUG: Diagnostic contains:",
            "    private final Logger LOG = LoggerFactory.getLogger(getClass());",
            "  }",
            "",
            "  static final class StaticLoggerWithMissingModifier {",
            "    // BUG: Diagnostic contains:",
            "    static final Logger LOG = LoggerFactory.getLogger(StaticLoggerWithMissingModifier.class);",
            "  }",
            "",
            "  static final class StaticLoggerWithExcessModifier {",
            "    // BUG: Diagnostic contains:",
            "    private static final transient Logger LOG =",
            "        LoggerFactory.getLogger(StaticLoggerWithExcessModifier.class);",
            "  }",
            "",
            "  static final class MisnamedStaticLogger {",
            "    // BUG: Diagnostic contains:",
            "    private static final Logger log = LoggerFactory.getLogger(MisnamedStaticLogger.class);",
            "  }",
            "",
            "  static final class StaticLoggerWithIncorrectIdentifier {",
            "    // BUG: Diagnostic contains:",
            "    private static final Logger LOG = LoggerFactory.getLogger(A.class);",
            "  }",
            "",
            "  static final class StaticLoggerWithCustomIdentifierAndMissingModifier {",
            "    // BUG: Diagnostic contains:",
            "    static final Logger LOG = LoggerFactory.getLogger(\"custom-identifier\");",
            "  }",
            "",
            "  static final class StaticLoggerWithCustomIdentifierAndExcessModifier {",
            "    // BUG: Diagnostic contains:",
            "    private static final transient Logger LOG = LoggerFactory.getLogger(\"custom-identifier\");",
            "  }",
            "",
            "  static final class MisnamedStaticLoggerWithCustomIdentifier {",
            "    // BUG: Diagnostic contains:",
            "    private static final Logger log = LoggerFactory.getLogger(\"custom-identifier\");",
            "  }",
            "",
            "  interface StaticLoggerForInterfaceWithExcessModifier {",
            "    // BUG: Diagnostic contains:",
            "    static Logger LOG = LoggerFactory.getLogger(StaticLoggerForInterfaceWithExcessModifier.class);",
            "  }",
            "",
            "  interface MisnamedStaticLoggerForInterface {",
            "    // BUG: Diagnostic contains:",
            "    Logger log = LoggerFactory.getLogger(MisnamedStaticLoggerForInterface.class);",
            "  }",
            "",
            "  interface StaticLoggerForInterfaceWithIncorrectIdentifier {",
            "    // BUG: Diagnostic contains:",
            "    Logger LOG = LoggerFactory.getLogger(A.class);",
            "  }",
            "}")
        .doTest();
  }

  @Test
  void replacement() {
    BugCheckerRefactoringTestHelper.newInstance(Slf4jLoggerDeclaration.class, getClass())
        .addInputLines(
            "A.java",
            "import org.slf4j.Logger;",
            "import org.slf4j.LoggerFactory;",
            "",
            "class A {",
            "  static Logger foo = LoggerFactory.getLogger(Logger.class);",
            "",
            "  abstract static class DynamicLogger {",
            "    transient Logger BAR = LoggerFactory.getLogger(getClass());",
            "  }",
            "",
            "  static final class StaticLogger {",
            "    transient Logger baz = LoggerFactory.getLogger(LoggerFactory.class);",
            "  }",
            "",
            "  static final class StaticLoggerWithCustomIdentifier {",
            "    transient Logger qux = LoggerFactory.getLogger(\"custom-identifier\");",
            "  }",
            "",
            "  interface StaticLoggerForInterface {",
            "    public static final Logger quux = LoggerFactory.getLogger(A.class);",
            "  }",
            "}")
        .addOutputLines(
            "A.java",
            "import org.slf4j.Logger;",
            "import org.slf4j.LoggerFactory;",
            "",
            "class A {",
            "  private static final Logger LOG = LoggerFactory.getLogger(A.class);",
            "",
            "  abstract static class DynamicLogger {",
            "    private final Logger log = LoggerFactory.getLogger(getClass());",
            "  }",
            "",
            "  static final class StaticLogger {",
            "    private static final Logger LOG = LoggerFactory.getLogger(StaticLogger.class);",
            "  }",
            "",
            "  static final class StaticLoggerWithCustomIdentifier {",
            "    private static final Logger LOG = LoggerFactory.getLogger(\"custom-identifier\");",
            "  }",
            "",
            "  interface StaticLoggerForInterface {",
            "    Logger LOG = LoggerFactory.getLogger(StaticLoggerForInterface.class);",
            "  }",
            "}")
        .doTest(TestMode.TEXT_MATCH);
  }

  @Test
  void replacementWithCustomLoggerName() {
    BugCheckerRefactoringTestHelper.newInstance(Slf4jLoggerDeclaration.class, getClass())
        .setArgs(ImmutableList.of("-XepOpt:Slf4jLogDeclaration:CanonicalStaticLoggerName=FOO_BAR"))
        .addInputLines(
            "A.java",
            "import org.slf4j.Logger;",
            "import org.slf4j.LoggerFactory;",
            "",
            "class A {",
            "  transient Logger LOG = LoggerFactory.getLogger(Logger.class);",
            "",
            "  abstract static class DynamicLogger {",
            "    transient Logger log = LoggerFactory.getLogger(getClass());",
            "  }",
            "}")
        .addOutputLines(
            "A.java",
            "import org.slf4j.Logger;",
            "import org.slf4j.LoggerFactory;",
            "",
            "class A {",
            "  private static final Logger FOO_BAR = LoggerFactory.getLogger(A.class);",
            "",
            "  abstract static class DynamicLogger {",
            "    private final Logger fooBar = LoggerFactory.getLogger(getClass());",
            "  }",
            "}")
        .doTest(TestMode.TEXT_MATCH);
  }
}
