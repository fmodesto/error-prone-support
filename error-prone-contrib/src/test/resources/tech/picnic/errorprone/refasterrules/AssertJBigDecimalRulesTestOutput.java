package tech.picnic.errorprone.refasterrules;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.data.Offset.offset;
import static org.assertj.core.data.Percentage.withPercentage;

import java.util.function.Function;
import com.google.common.collect.ImmutableSet;
import java.math.BigDecimal;
import org.assertj.core.api.AbstractBigDecimalAssert;
import tech.picnic.errorprone.refaster.test.RefasterRuleCollectionTestCase;

final class AssertJBigDecimalRulesTest implements RefasterRuleCollectionTestCase {
  @Override
  public ImmutableSet<Object> elidedTypesAndStaticImports() {
    return ImmutableSet.of(offset(0), withPercentage(0));
  }

  ImmutableSet<Function<Object, String>> testRefToLambda() {
    return ImmutableSet.of(
            e -> e.toString());
  }

  ImmutableSet<Function<Object, String>> testLambdaToRef() {
    return ImmutableSet.of(
            Object::toString);
  }

  ImmutableSet<AbstractBigDecimalAssert<?>> testAbstractBigDecimalAssertIsEqualByComparingTo() {
    return ImmutableSet.of(
        assertThat(BigDecimal.ZERO).isEqualByComparingTo(BigDecimal.ONE),
        assertThat(BigDecimal.ZERO).isEqualByComparingTo(BigDecimal.ONE));
  }

  ImmutableSet<AbstractBigDecimalAssert<?>> testAbstractBigDecimalAssertIsNotEqualByComparingTo() {
    return ImmutableSet.of(
        assertThat(BigDecimal.ZERO).isNotEqualByComparingTo(BigDecimal.ONE),
        assertThat(BigDecimal.ZERO).isNotEqualByComparingTo(BigDecimal.ONE));
  }
}
