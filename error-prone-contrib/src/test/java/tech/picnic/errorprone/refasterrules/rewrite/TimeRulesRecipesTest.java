package tech.picnic.errorprone.refasterrules.rewrite;

import org.junit.jupiter.api.Test;
import org.openrewrite.Recipe;
import org.openrewrite.java.JavaParser;
import org.openrewrite.test.RewriteTest;

import static org.openrewrite.java.Assertions.java;

final class TimeRulesRecipesTest implements RewriteTest {

    @Test
    void testChronoLocalDateIsAfterRecipe() {
        Recipe recipe = RewriteUtils.loadRecipe("TimeRulesRecipes.ChronoLocalDateIsAfterRecipe");
        rewriteRun(
                spec -> spec.recipe(recipe)
                        .expectedCyclesThatMakeChanges(1).cycles(1)
                        .parser(JavaParser.fromJavaVersion().classpath(JavaParser.runtimeClasspath())),
                java(
                        """
                                import com.google.common.collect.ImmutableSet;
                                
                                import java.time.LocalDate;
                                
                                class Test {
                                    ImmutableSet<Boolean> test() {
                                        return ImmutableSet.of(LocalDate.MIN.compareTo(LocalDate.MAX) > 0, LocalDate.MIN.compareTo(LocalDate.MAX) <= 0);
                                    }
                                }
                                """,
                        """
                                import com.google.common.collect.ImmutableSet;
                                
                                import java.time.LocalDate;
                                
                                class Test {
                                    ImmutableSet<Boolean> test() {
                                        return ImmutableSet.of(LocalDate.MIN.isAfter(LocalDate.MAX), !LocalDate.MIN.isAfter(LocalDate.MAX));
                                    }
                                }
                                """
                ));
    }

    @Test
    void testChronoLocalDateIsBeforeRecipe() {
        Recipe recipe = RewriteUtils.loadRecipe("TimeRulesRecipes.ChronoLocalDateIsBeforeRecipe");
        rewriteRun(
                spec -> spec.recipe(recipe)
                        .expectedCyclesThatMakeChanges(1).cycles(1)
                        .parser(JavaParser.fromJavaVersion().classpath(JavaParser.runtimeClasspath())),
                java(
                        """
                                import com.google.common.collect.ImmutableSet;
                                
                                import java.time.LocalDate;
                                
                                class Test {
                                    ImmutableSet<Boolean> test() {
                                        return ImmutableSet.of(LocalDate.MIN.compareTo(LocalDate.MAX) < 0, LocalDate.MIN.compareTo(LocalDate.MAX) >= 0);
                                    }
                                }
                                """,
                        """
                                import com.google.common.collect.ImmutableSet;
                                
                                import java.time.LocalDate;
                                
                                class Test {
                                    ImmutableSet<Boolean> test() {
                                        return ImmutableSet.of(LocalDate.MIN.isBefore(LocalDate.MAX), !LocalDate.MIN.isBefore(LocalDate.MAX));
                                    }
                                }
                                """
                ));
    }

    @Test
    void testChronoLocalDateTimeIsAfterRecipe() {
        Recipe recipe = RewriteUtils.loadRecipe("TimeRulesRecipes.ChronoLocalDateTimeIsAfterRecipe");
        rewriteRun(
                spec -> spec.recipe(recipe)
                        .expectedCyclesThatMakeChanges(1).cycles(1)
                        .parser(JavaParser.fromJavaVersion().classpath(JavaParser.runtimeClasspath())),
                java(
                        """
                                import com.google.common.collect.ImmutableSet;
                                
                                import java.time.LocalDateTime;
                                
                                class Test {
                                    ImmutableSet<Boolean> test() {
                                        return ImmutableSet.of(LocalDateTime.MIN.compareTo(LocalDateTime.MAX) > 0, LocalDateTime.MIN.compareTo(LocalDateTime.MAX) <= 0);
                                    }
                                }
                                """,
                        """
                                import com.google.common.collect.ImmutableSet;
                                
                                import java.time.LocalDateTime;
                                
                                class Test {
                                    ImmutableSet<Boolean> test() {
                                        return ImmutableSet.of(LocalDateTime.MIN.isAfter(LocalDateTime.MAX), !LocalDateTime.MIN.isAfter(LocalDateTime.MAX));
                                    }
                                }
                                """
                ));
    }

    @Test
    void testChronoLocalDateTimeIsBeforeRecipe() {
        Recipe recipe = RewriteUtils.loadRecipe("TimeRulesRecipes.ChronoLocalDateTimeIsBeforeRecipe");
        rewriteRun(
                spec -> spec.recipe(recipe)
                        .expectedCyclesThatMakeChanges(1).cycles(1)
                        .parser(JavaParser.fromJavaVersion().classpath(JavaParser.runtimeClasspath())),
                java(
                        """
                                import com.google.common.collect.ImmutableSet;
                                
                                import java.time.LocalDateTime;
                                
                                class Test {
                                    ImmutableSet<Boolean> test() {
                                        return ImmutableSet.of(LocalDateTime.MIN.compareTo(LocalDateTime.MAX) < 0, LocalDateTime.MIN.compareTo(LocalDateTime.MAX) >= 0);
                                    }
                                }
                                """,
                        """
                                import com.google.common.collect.ImmutableSet;
                                
                                import java.time.LocalDateTime;
                                
                                class Test {
                                    ImmutableSet<Boolean> test() {
                                        return ImmutableSet.of(LocalDateTime.MIN.isBefore(LocalDateTime.MAX), !LocalDateTime.MIN.isBefore(LocalDateTime.MAX));
                                    }
                                }
                                """
                ));
    }

    @Test
    void testChronoZonedDateTimeIsAfterRecipe() {
        Recipe recipe = RewriteUtils.loadRecipe("TimeRulesRecipes.ChronoZonedDateTimeIsAfterRecipe");
        rewriteRun(
                spec -> spec.recipe(recipe)
                        .expectedCyclesThatMakeChanges(1).cycles(1)
                        .parser(JavaParser.fromJavaVersion().classpath(JavaParser.runtimeClasspath())),
                java(
                        """
                                import com.google.common.collect.ImmutableSet;
                                
                                import java.time.ZonedDateTime;
                                
                                class Test {
                                    ImmutableSet<Boolean> test() {
                                        return ImmutableSet.of(ZonedDateTime.now().compareTo(ZonedDateTime.now()) > 0, ZonedDateTime.now().compareTo(ZonedDateTime.now()) <= 0);
                                    }
                                }
                                """,
                        """
                                import com.google.common.collect.ImmutableSet;
                                
                                import java.time.ZonedDateTime;
                                
                                class Test {
                                    ImmutableSet<Boolean> test() {
                                        return ImmutableSet.of(ZonedDateTime.now().isAfter(ZonedDateTime.now()), !ZonedDateTime.now().isAfter(ZonedDateTime.now()));
                                    }
                                }
                                """
                ));
    }

    @Test
    void testChronoZonedDateTimeIsBeforeRecipe() {
        Recipe recipe = RewriteUtils.loadRecipe("TimeRulesRecipes.ChronoZonedDateTimeIsBeforeRecipe");
        rewriteRun(
                spec -> spec.recipe(recipe)
                        .expectedCyclesThatMakeChanges(1).cycles(1)
                        .parser(JavaParser.fromJavaVersion().classpath(JavaParser.runtimeClasspath())),
                java(
                        """
                                import com.google.common.collect.ImmutableSet;
                                
                                import java.time.ZonedDateTime;
                                
                                class Test {
                                    ImmutableSet<Boolean> test() {
                                        return ImmutableSet.of(ZonedDateTime.now().compareTo(ZonedDateTime.now()) < 0, ZonedDateTime.now().compareTo(ZonedDateTime.now()) >= 0);
                                    }
                                }
                                """,
                        """
                                import com.google.common.collect.ImmutableSet;
                                
                                import java.time.ZonedDateTime;
                                
                                class Test {
                                    ImmutableSet<Boolean> test() {
                                        return ImmutableSet.of(ZonedDateTime.now().isBefore(ZonedDateTime.now()), !ZonedDateTime.now().isBefore(ZonedDateTime.now()));
                                    }
                                }
                                """
                ));
    }

    @Test
    void testClockInstantRecipe() {
        Recipe recipe = RewriteUtils.loadRecipe("TimeRulesRecipes.ClockInstantRecipe");
        rewriteRun(
                spec -> spec.recipe(recipe)
                        .expectedCyclesThatMakeChanges(1).cycles(1)
                        .parser(JavaParser.fromJavaVersion().classpath(JavaParser.runtimeClasspath())),
                java(
                        """
                                import java.time.Clock;
                                import java.time.Instant;
                                
                                class Test {
                                    Instant test() {
                                        return Instant.now(Clock.systemUTC());
                                    }
                                }
                                """,
                        """
                                import java.time.Clock;
                                import java.time.Instant;
                                
                                class Test {
                                    Instant test() {
                                        return Clock.systemUTC().instant();
                                    }
                                }
                                """
                ));
    }

    @Test
    void testDurationBetweenInstantsRecipe() {
        Recipe recipe = RewriteUtils.loadRecipe("TimeRulesRecipes.DurationBetweenInstantsRecipe");
        rewriteRun(
                spec -> spec.recipe(recipe)
                        .expectedCyclesThatMakeChanges(1).cycles(1)
                        .parser(JavaParser.fromJavaVersion().classpath(JavaParser.runtimeClasspath())),
                java(
                        """
                                import java.time.Duration;
                                import java.time.Instant;
                                
                                class Test {
                                    Duration test() {
                                        return Duration.ofMillis(Instant.MAX.toEpochMilli() - Instant.MIN.toEpochMilli());
                                    }
                                }
                                """,
                        """
                                import java.time.Duration;
                                import java.time.Instant;
                                
                                class Test {
                                    Duration test() {
                                        return Duration.between(Instant.MIN, Instant.MAX);
                                    }
                                }
                                """
                ));
    }

    @Test
    void testDurationBetweenOffsetDateTimesRecipe() {
        Recipe recipe = RewriteUtils.loadRecipe("TimeRulesRecipes.DurationBetweenOffsetDateTimesRecipe");
        rewriteRun(
                spec -> spec.recipe(recipe)
                        .expectedCyclesThatMakeChanges(1).cycles(1)
                        .parser(JavaParser.fromJavaVersion().classpath(JavaParser.runtimeClasspath())),
                java(
                        """
                                import java.time.Duration;
                                import java.time.OffsetDateTime;
                                
                                class Test {
                                    Duration test() {
                                        return Duration.between(OffsetDateTime.MIN.toInstant(), OffsetDateTime.MAX.toInstant()).plus(Duration.ofSeconds(OffsetDateTime.MAX.toEpochSecond() - OffsetDateTime.MIN.toEpochSecond()));
                                    }
                                }
                                """,
                        """
                                import java.time.Duration;
                                import java.time.OffsetDateTime;
                                
                                class Test {
                                    Duration test() {
                                        return Duration.between(OffsetDateTime.MIN, OffsetDateTime.MAX).plus(Duration.between(OffsetDateTime.MIN, OffsetDateTime.MAX));
                                    }
                                }
                                """
                ));
    }

    @Test
    void testDurationIsZeroRecipe() {
        Recipe recipe = RewriteUtils.loadRecipe("TimeRulesRecipes.DurationIsZeroRecipe");
        rewriteRun(
                spec -> spec.recipe(recipe)
                        .expectedCyclesThatMakeChanges(1).cycles(1)
                        .parser(JavaParser.fromJavaVersion().classpath(JavaParser.runtimeClasspath())),
                java(
                        """
                                import com.google.common.collect.ImmutableSet;
                                
                                import java.time.Duration;
                                
                                class Test {
                                    ImmutableSet<Boolean> test() {
                                        return ImmutableSet.of(Duration.ofDays(1).equals(Duration.ZERO), Duration.ZERO.equals(Duration.ofDays(2)));
                                    }
                                }
                                """,
                        """
                                import com.google.common.collect.ImmutableSet;
                                
                                import java.time.Duration;
                                
                                class Test {
                                    ImmutableSet<Boolean> test() {
                                        return ImmutableSet.of(Duration.ofDays(1).isZero(), Duration.ofDays(2).isZero());
                                    }
                                }
                                """
                ));
    }

    @Test
    void testDurationOfDaysRecipe() {
        Recipe recipe = RewriteUtils.loadRecipe("TimeRulesRecipes.DurationOfDaysRecipe");
        rewriteRun(
                spec -> spec.recipe(recipe)
                        .expectedCyclesThatMakeChanges(1).cycles(1)
                        .parser(JavaParser.fromJavaVersion().classpath(JavaParser.runtimeClasspath())),
                java(
                        """
                                import java.time.Duration;
                                import java.time.temporal.ChronoUnit;
                                
                                class Test {
                                    Duration test() {
                                        return Duration.of(1, ChronoUnit.DAYS);
                                    }
                                }
                                """,
                        """
                                import java.time.Duration;
                                
                                class Test {
                                    Duration test() {
                                        return Duration.ofDays(1);
                                    }
                                }
                                """
                ));
    }

    @Test
    void testDurationOfHoursRecipe() {
        Recipe recipe = RewriteUtils.loadRecipe("TimeRulesRecipes.DurationOfHoursRecipe");
        rewriteRun(
                spec -> spec.recipe(recipe)
                        .expectedCyclesThatMakeChanges(1).cycles(1)
                        .parser(JavaParser.fromJavaVersion().classpath(JavaParser.runtimeClasspath())),
                java(
                        """
                                import java.time.Duration;
                                import java.time.temporal.ChronoUnit;
                                
                                class Test {
                                    Duration test() {
                                        return Duration.of(1, ChronoUnit.HOURS);
                                    }
                                }
                                """,
                        """
                                import java.time.Duration;
                                
                                class Test {
                                    Duration test() {
                                        return Duration.ofHours(1);
                                    }
                                }
                                """
                ));
    }

    @Test
    void testDurationOfMillisRecipe() {
        Recipe recipe = RewriteUtils.loadRecipe("TimeRulesRecipes.DurationOfMillisRecipe");
        rewriteRun(
                spec -> spec.recipe(recipe)
                        .expectedCyclesThatMakeChanges(1).cycles(1)
                        .parser(JavaParser.fromJavaVersion().classpath(JavaParser.runtimeClasspath())),
                java(
                        """
                                import java.time.Duration;
                                import java.time.temporal.ChronoUnit;
                                
                                class Test {
                                    Duration test() {
                                        return Duration.of(1, ChronoUnit.MILLIS);
                                    }
                                }
                                """,
                        """
                                import java.time.Duration;
                                
                                class Test {
                                    Duration test() {
                                        return Duration.ofMillis(1);
                                    }
                                }
                                """
                ));
    }

    @Test
    void testDurationOfMinutesRecipe() {
        Recipe recipe = RewriteUtils.loadRecipe("TimeRulesRecipes.DurationOfMinutesRecipe");
        rewriteRun(
                spec -> spec.recipe(recipe)
                        .expectedCyclesThatMakeChanges(1).cycles(1)
                        .parser(JavaParser.fromJavaVersion().classpath(JavaParser.runtimeClasspath())),
                java(
                        """
                                import java.time.Duration;
                                import java.time.temporal.ChronoUnit;
                                
                                class Test {
                                    Duration test() {
                                        return Duration.of(1, ChronoUnit.MINUTES);
                                    }
                                }
                                """,
                        """
                                import java.time.Duration;
                                
                                class Test {
                                    Duration test() {
                                        return Duration.ofMinutes(1);
                                    }
                                }
                                """
                ));
    }

    @Test
    void testDurationOfNanosRecipe() {
        Recipe recipe = RewriteUtils.loadRecipe("TimeRulesRecipes.DurationOfNanosRecipe");
        rewriteRun(
                spec -> spec.recipe(recipe)
                        .expectedCyclesThatMakeChanges(1).cycles(1)
                        .parser(JavaParser.fromJavaVersion().classpath(JavaParser.runtimeClasspath())),
                java(
                        """
                                import java.time.Duration;
                                import java.time.temporal.ChronoUnit;
                                
                                class Test {
                                    Duration test() {
                                        return Duration.of(1, ChronoUnit.NANOS);
                                    }
                                }
                                """,
                        """
                                import java.time.Duration;
                                
                                class Test {
                                    Duration test() {
                                        return Duration.ofNanos(1);
                                    }
                                }
                                """
                ));
    }

    @Test
    void testDurationOfSecondsRecipe() {
        Recipe recipe = RewriteUtils.loadRecipe("TimeRulesRecipes.DurationOfSecondsRecipe");
        rewriteRun(
                spec -> spec.recipe(recipe)
                        .expectedCyclesThatMakeChanges(1).cycles(1)
                        .parser(JavaParser.fromJavaVersion().classpath(JavaParser.runtimeClasspath())),
                java(
                        """
                                import java.time.Duration;
                                import java.time.temporal.ChronoUnit;
                                
                                class Test {
                                    Duration test() {
                                        return Duration.of(1, ChronoUnit.SECONDS);
                                    }
                                }
                                """,
                        """
                                import java.time.Duration;
                                
                                class Test {
                                    Duration test() {
                                        return Duration.ofSeconds(1);
                                    }
                                }
                                """
                ));
    }

    @Test
    void testEpochInstantRecipe() {
        Recipe recipe = RewriteUtils.loadRecipe("TimeRulesRecipes.EpochInstantRecipe");
        rewriteRun(
                spec -> spec.recipe(recipe)
                        .expectedCyclesThatMakeChanges(1).cycles(1)
                        .parser(JavaParser.fromJavaVersion().classpath(JavaParser.runtimeClasspath())),
                java(
                        """
                                import com.google.common.collect.ImmutableSet;
                                
                                import java.time.Instant;
                                
                                class Test {
                                    ImmutableSet<Instant> test() {
                                        return ImmutableSet.of(Instant.ofEpochMilli(0), Instant.ofEpochMilli(0L), Instant.ofEpochSecond(0), Instant.ofEpochSecond(0, 0));
                                    }
                                }
                                """,
                        """
                                import com.google.common.collect.ImmutableSet;
                                
                                import java.time.Instant;
                                
                                class Test {
                                    ImmutableSet<Instant> test() {
                                        return ImmutableSet.of(Instant.EPOCH, Instant.EPOCH, Instant.EPOCH, Instant.EPOCH);
                                    }
                                }
                                """
                ));
    }

    @Test
    void testInstantAtOffsetRecipe() {
        Recipe recipe = RewriteUtils.loadRecipe("TimeRulesRecipes.InstantAtOffsetRecipe");
        rewriteRun(
                spec -> spec.recipe(recipe)
                        .expectedCyclesThatMakeChanges(1).cycles(1)
                        .parser(JavaParser.fromJavaVersion().classpath(JavaParser.runtimeClasspath())),
                java(
                        """
                                import java.time.Instant;
                                import java.time.OffsetDateTime;
                                import java.time.ZoneOffset;
                                
                                class Test {
                                    OffsetDateTime test() {
                                        return OffsetDateTime.ofInstant(Instant.EPOCH, ZoneOffset.UTC);
                                    }
                                }
                                """,
                        """
                                import java.time.Instant;
                                import java.time.OffsetDateTime;
                                import java.time.ZoneOffset;
                                
                                class Test {
                                    OffsetDateTime test() {
                                        return Instant.EPOCH.atOffset(ZoneOffset.UTC);
                                    }
                                }
                                """
                ));
    }

    @Test
    void testInstantAtZoneRecipe() {
        Recipe recipe = RewriteUtils.loadRecipe("TimeRulesRecipes.InstantAtZoneRecipe");
        rewriteRun(
                spec -> spec.recipe(recipe)
                        .expectedCyclesThatMakeChanges(1).cycles(1)
                        .parser(JavaParser.fromJavaVersion().classpath(JavaParser.runtimeClasspath())),
                java(
                        """
                                import java.time.Instant;
                                import java.time.ZoneOffset;
                                import java.time.ZonedDateTime;
                                
                                class Test {
                                    ZonedDateTime test() {
                                        return ZonedDateTime.ofInstant(Instant.EPOCH, ZoneOffset.UTC);
                                    }
                                }
                                """,
                        """
                                import java.time.Instant;
                                import java.time.ZoneOffset;
                                import java.time.ZonedDateTime;
                                
                                class Test {
                                    ZonedDateTime test() {
                                        return Instant.EPOCH.atZone(ZoneOffset.UTC);
                                    }
                                }
                                """
                ));
    }

    @Test
    void testInstantIdentityRecipe() {
        Recipe recipe = RewriteUtils.loadRecipe("TimeRulesRecipes.InstantIdentityRecipe");
        rewriteRun(
                spec -> spec.recipe(recipe)
                        .expectedCyclesThatMakeChanges(1).cycles(1)
                        .parser(JavaParser.fromJavaVersion().classpath(JavaParser.runtimeClasspath())),
                java(
                        """
                                import com.google.common.collect.ImmutableSet;
                                
                                import java.time.Duration;
                                import java.time.Instant;
                                import java.time.temporal.ChronoUnit;
                                
                                class Test {
                                    ImmutableSet<Instant> test() {
                                        return ImmutableSet.of(Instant.EPOCH.plusMillis(1).plus(Duration.ZERO), Instant.EPOCH.plusMillis(2).plus(0, ChronoUnit.MILLIS), Instant.EPOCH.plusMillis(3).plusNanos(0L), Instant.EPOCH.plusMillis(4).plusMillis(0), Instant.EPOCH.plusMillis(5).plusSeconds(0L), Instant.EPOCH.plusMillis(6).minus(Duration.ZERO), Instant.EPOCH.plusMillis(7).minus(0, ChronoUnit.SECONDS), Instant.EPOCH.plusMillis(8).minusNanos(0L), Instant.EPOCH.plusMillis(9).minusMillis(0), Instant.EPOCH.plusMillis(10).minusSeconds(0L), Instant.parse(Instant.EPOCH.plusMillis(11).toString()), Instant.EPOCH.plusMillis(12).truncatedTo(ChronoUnit.NANOS), Instant.ofEpochSecond(Instant.EPOCH.plusMillis(13).getEpochSecond(), Instant.EPOCH.plusMillis(13).getNano()));
                                    }
                                }
                                """,
                        """
                                import com.google.common.collect.ImmutableSet;
                                
                                import java.time.Instant;
                                
                                class Test {
                                    ImmutableSet<Instant> test() {
                                        return ImmutableSet.of(Instant.EPOCH.plusMillis(1), Instant.EPOCH.plusMillis(2), Instant.EPOCH.plusMillis(3), Instant.EPOCH.plusMillis(4), Instant.EPOCH.plusMillis(5), Instant.EPOCH.plusMillis(6), Instant.EPOCH.plusMillis(7), Instant.EPOCH.plusMillis(8), Instant.EPOCH.plusMillis(9), Instant.EPOCH.plusMillis(10), Instant.EPOCH.plusMillis(11), Instant.EPOCH.plusMillis(12), Instant.EPOCH.plusMillis(13));
                                    }
                                }
                                """
                ));
    }

    @Test
    void testInstantIsAfterRecipe() {
        Recipe recipe = RewriteUtils.loadRecipe("TimeRulesRecipes.InstantIsAfterRecipe");
        rewriteRun(
                spec -> spec.recipe(recipe)
                        .expectedCyclesThatMakeChanges(1).cycles(1)
                        .parser(JavaParser.fromJavaVersion().classpath(JavaParser.runtimeClasspath())),
                java(
                        """
                                import com.google.common.collect.ImmutableSet;
                                
                                import java.time.Instant;
                                
                                class Test {
                                    ImmutableSet<Boolean> test() {
                                        return ImmutableSet.of(Instant.MIN.compareTo(Instant.MAX) > 0, Instant.MIN.compareTo(Instant.MAX) <= 0);
                                    }
                                }
                                """,
                        """
                                import com.google.common.collect.ImmutableSet;
                                
                                import java.time.Instant;
                                
                                class Test {
                                    ImmutableSet<Boolean> test() {
                                        return ImmutableSet.of(Instant.MIN.isAfter(Instant.MAX), !Instant.MIN.isAfter(Instant.MAX));
                                    }
                                }
                                """
                ));
    }

    @Test
    void testInstantIsBeforeRecipe() {
        Recipe recipe = RewriteUtils.loadRecipe("TimeRulesRecipes.InstantIsBeforeRecipe");
        rewriteRun(
                spec -> spec.recipe(recipe)
                        .expectedCyclesThatMakeChanges(1).cycles(1)
                        .parser(JavaParser.fromJavaVersion().classpath(JavaParser.runtimeClasspath())),
                java(
                        """
                                import com.google.common.collect.ImmutableSet;
                                
                                import java.time.Instant;
                                
                                class Test {
                                    ImmutableSet<Boolean> test() {
                                        return ImmutableSet.of(Instant.MIN.compareTo(Instant.MAX) < 0, Instant.MIN.compareTo(Instant.MAX) >= 0);
                                    }
                                }
                                """,
                        """
                                import com.google.common.collect.ImmutableSet;
                                
                                import java.time.Instant;
                                
                                class Test {
                                    ImmutableSet<Boolean> test() {
                                        return ImmutableSet.of(Instant.MIN.isBefore(Instant.MAX), !Instant.MIN.isBefore(Instant.MAX));
                                    }
                                }
                                """
                ));
    }

    @Test
    void testInstantTruncatedToMillisecondsRecipe() {
        Recipe recipe = RewriteUtils.loadRecipe("TimeRulesRecipes.InstantTruncatedToMillisecondsRecipe");
        rewriteRun(
                spec -> spec.recipe(recipe)
                        .expectedCyclesThatMakeChanges(1).cycles(1)
                        .parser(JavaParser.fromJavaVersion().classpath(JavaParser.runtimeClasspath())),
                java(
                        """
                                import java.time.Instant;
                                
                                class Test {
                                    Instant test() {
                                        return Instant.ofEpochMilli(Instant.EPOCH.toEpochMilli());
                                    }
                                }
                                """,
                        """
                                import java.time.Instant;
                                import java.time.temporal.ChronoUnit;
                                
                                class Test {
                                    Instant test() {
                                        return Instant.EPOCH.truncatedTo(ChronoUnit.MILLIS);
                                    }
                                }
                                """
                ));
    }

    @Test
    void testInstantTruncatedToSecondsRecipe() {
        Recipe recipe = RewriteUtils.loadRecipe("TimeRulesRecipes.InstantTruncatedToSecondsRecipe");
        rewriteRun(
                spec -> spec.recipe(recipe)
                        .expectedCyclesThatMakeChanges(1).cycles(1)
                        .parser(JavaParser.fromJavaVersion().classpath(JavaParser.runtimeClasspath())),
                java(
                        """
                                import java.time.Instant;
                                
                                class Test {
                                    Instant test() {
                                        return Instant.ofEpochSecond(Instant.EPOCH.getEpochSecond());
                                    }
                                }
                                """,
                        """
                                import java.time.Instant;
                                import java.time.temporal.ChronoUnit;
                                
                                class Test {
                                    Instant test() {
                                        return Instant.EPOCH.truncatedTo(ChronoUnit.SECONDS);
                                    }
                                }
                                """
                ));
    }

    @Test
    void testLocalDateAtStartOfDayRecipe() {
        Recipe recipe = RewriteUtils.loadRecipe("TimeRulesRecipes.LocalDateAtStartOfDayRecipe");
        rewriteRun(
                spec -> spec.recipe(recipe)
                        .expectedCyclesThatMakeChanges(1).cycles(1)
                        .parser(JavaParser.fromJavaVersion().classpath(JavaParser.runtimeClasspath())),
                java(
                        """
                                import java.time.LocalDate;
                                import java.time.LocalDateTime;
                                import java.time.LocalTime;
                                
                                class Test {
                                    LocalDateTime test() {
                                        return LocalDate.EPOCH.atTime(LocalTime.MIN);
                                    }
                                }
                                """,
                        """
                                import java.time.LocalDate;
                                import java.time.LocalDateTime;
                                
                                class Test {
                                    LocalDateTime test() {
                                        return LocalDate.EPOCH.atStartOfDay();
                                    }
                                }
                                """
                ));
    }

    @Test
    void testLocalDateMinusDaysRecipe() {
        Recipe recipe = RewriteUtils.loadRecipe("TimeRulesRecipes.LocalDateMinusDaysRecipe");
        rewriteRun(
                spec -> spec.recipe(recipe)
                        .expectedCyclesThatMakeChanges(1).cycles(1)
                        .parser(JavaParser.fromJavaVersion().classpath(JavaParser.runtimeClasspath())),
                java(
                        """
                                import com.google.common.collect.ImmutableSet;
                                
                                import java.time.LocalDate;
                                import java.time.Period;
                                import java.time.temporal.ChronoUnit;
                                
                                class Test {
                                    ImmutableSet<LocalDate> test() {
                                        return ImmutableSet.of(LocalDate.EPOCH.minus(1L, ChronoUnit.DAYS), LocalDate.EPOCH.minus(Period.ofDays(1)));
                                    }
                                }
                                """,
                        """
                                import com.google.common.collect.ImmutableSet;
                                
                                import java.time.LocalDate;
                                
                                class Test {
                                    ImmutableSet<LocalDate> test() {
                                        return ImmutableSet.of(LocalDate.EPOCH.minusDays(1L), LocalDate.EPOCH.minusDays(1));
                                    }
                                }
                                """
                ));
    }

    @Test
    void testLocalDateMinusMonthsRecipe() {
        Recipe recipe = RewriteUtils.loadRecipe("TimeRulesRecipes.LocalDateMinusMonthsRecipe");
        rewriteRun(
                spec -> spec.recipe(recipe)
                        .expectedCyclesThatMakeChanges(1).cycles(1)
                        .parser(JavaParser.fromJavaVersion().classpath(JavaParser.runtimeClasspath())),
                java(
                        """
                                import com.google.common.collect.ImmutableSet;
                                
                                import java.time.LocalDate;
                                import java.time.Period;
                                import java.time.temporal.ChronoUnit;
                                
                                class Test {
                                    ImmutableSet<LocalDate> test() {
                                        return ImmutableSet.of(LocalDate.EPOCH.minus(1L, ChronoUnit.MONTHS), LocalDate.EPOCH.minus(Period.ofMonths(1)));
                                    }
                                }
                                """,
                        """
                                import com.google.common.collect.ImmutableSet;
                                
                                import java.time.LocalDate;
                                
                                class Test {
                                    ImmutableSet<LocalDate> test() {
                                        return ImmutableSet.of(LocalDate.EPOCH.minusMonths(1L), LocalDate.EPOCH.minusMonths(1));
                                    }
                                }
                                """
                ));
    }

    @Test
    void testLocalDateMinusWeeksRecipe() {
        Recipe recipe = RewriteUtils.loadRecipe("TimeRulesRecipes.LocalDateMinusWeeksRecipe");
        rewriteRun(
                spec -> spec.recipe(recipe)
                        .expectedCyclesThatMakeChanges(1).cycles(1)
                        .parser(JavaParser.fromJavaVersion().classpath(JavaParser.runtimeClasspath())),
                java(
                        """
                                import com.google.common.collect.ImmutableSet;
                                
                                import java.time.LocalDate;
                                import java.time.Period;
                                import java.time.temporal.ChronoUnit;
                                
                                class Test {
                                    ImmutableSet<LocalDate> test() {
                                        return ImmutableSet.of(LocalDate.EPOCH.minus(1L, ChronoUnit.WEEKS), LocalDate.EPOCH.minus(Period.ofWeeks(1)));
                                    }
                                }
                                """,
                        """
                                import com.google.common.collect.ImmutableSet;
                                
                                import java.time.LocalDate;
                                
                                class Test {
                                    ImmutableSet<LocalDate> test() {
                                        return ImmutableSet.of(LocalDate.EPOCH.minusWeeks(1L), LocalDate.EPOCH.minusWeeks(1));
                                    }
                                }
                                """
                ));
    }

    @Test
    void testLocalDateMinusYearsRecipe() {
        Recipe recipe = RewriteUtils.loadRecipe("TimeRulesRecipes.LocalDateMinusYearsRecipe");
        rewriteRun(
                spec -> spec.recipe(recipe)
                        .expectedCyclesThatMakeChanges(1).cycles(1)
                        .parser(JavaParser.fromJavaVersion().classpath(JavaParser.runtimeClasspath())),
                java(
                        """
                                import com.google.common.collect.ImmutableSet;
                                
                                import java.time.LocalDate;
                                import java.time.Period;
                                import java.time.temporal.ChronoUnit;
                                
                                class Test {
                                    ImmutableSet<LocalDate> test() {
                                        return ImmutableSet.of(LocalDate.EPOCH.minus(1L, ChronoUnit.YEARS), LocalDate.EPOCH.minus(Period.ofYears(1)));
                                    }
                                }
                                """,
                        """
                                import com.google.common.collect.ImmutableSet;
                                
                                import java.time.LocalDate;
                                
                                class Test {
                                    ImmutableSet<LocalDate> test() {
                                        return ImmutableSet.of(LocalDate.EPOCH.minusYears(1L), LocalDate.EPOCH.minusYears(1));
                                    }
                                }
                                """
                ));
    }

    @Test
    void testLocalDateOfInstantRecipe() {
        Recipe recipe = RewriteUtils.loadRecipe("TimeRulesRecipes.LocalDateOfInstantRecipe");
        rewriteRun(
                spec -> spec.recipe(recipe)
                        .expectedCyclesThatMakeChanges(1).cycles(1)
                        .parser(JavaParser.fromJavaVersion().classpath(JavaParser.runtimeClasspath())),
                java(
                        """
                                import com.google.common.collect.ImmutableSet;
                                
                                import java.time.*;
                                
                                class Test {
                                    ImmutableSet<LocalDate> test() {
                                        return ImmutableSet.of(Instant.EPOCH.atZone(ZoneId.of("Europe/Amsterdam")).toLocalDate(), Instant.EPOCH.atOffset(ZoneOffset.UTC).toLocalDate(), LocalDateTime.ofInstant(Instant.EPOCH, ZoneId.of("Europe/Berlin")).toLocalDate(), OffsetDateTime.ofInstant(Instant.EPOCH, ZoneOffset.MIN).toLocalDate());
                                    }
                                }
                                """,
                        """
                                import com.google.common.collect.ImmutableSet;
                                
                                import java.time.Instant;
                                import java.time.LocalDate;
                                import java.time.ZoneId;
                                import java.time.ZoneOffset;
                                
                                class Test {
                                    ImmutableSet<LocalDate> test() {
                                        return ImmutableSet.of(LocalDate.ofInstant(Instant.EPOCH, ZoneId.of("Europe/Amsterdam")), LocalDate.ofInstant(Instant.EPOCH, ZoneOffset.UTC), LocalDate.ofInstant(Instant.EPOCH, ZoneId.of("Europe/Berlin")), LocalDate.ofInstant(Instant.EPOCH, ZoneOffset.MIN));
                                    }
                                }
                                """
                ));
    }

    @Test
    void testLocalDatePlusDaysRecipe() {
        Recipe recipe = RewriteUtils.loadRecipe("TimeRulesRecipes.LocalDatePlusDaysRecipe");
        rewriteRun(
                spec -> spec.recipe(recipe)
                        .expectedCyclesThatMakeChanges(1).cycles(1)
                        .parser(JavaParser.fromJavaVersion().classpath(JavaParser.runtimeClasspath())),
                java(
                        """
                                import com.google.common.collect.ImmutableSet;
                                
                                import java.time.LocalDate;
                                import java.time.Period;
                                import java.time.temporal.ChronoUnit;
                                
                                class Test {
                                    ImmutableSet<LocalDate> test() {
                                        return ImmutableSet.of(LocalDate.EPOCH.plus(1L, ChronoUnit.DAYS), LocalDate.EPOCH.plus(Period.ofDays(1)));
                                    }
                                }
                                """,
                        """
                                import com.google.common.collect.ImmutableSet;
                                
                                import java.time.LocalDate;
                                
                                class Test {
                                    ImmutableSet<LocalDate> test() {
                                        return ImmutableSet.of(LocalDate.EPOCH.plusDays(1L), LocalDate.EPOCH.plusDays(1));
                                    }
                                }
                                """
                ));
    }

    @Test
    void testLocalDatePlusMonthsRecipe() {
        Recipe recipe = RewriteUtils.loadRecipe("TimeRulesRecipes.LocalDatePlusMonthsRecipe");
        rewriteRun(
                spec -> spec.recipe(recipe)
                        .expectedCyclesThatMakeChanges(1).cycles(1)
                        .parser(JavaParser.fromJavaVersion().classpath(JavaParser.runtimeClasspath())),
                java(
                        """
                                import com.google.common.collect.ImmutableSet;
                                
                                import java.time.LocalDate;
                                import java.time.Period;
                                import java.time.temporal.ChronoUnit;
                                
                                class Test {
                                    ImmutableSet<LocalDate> test() {
                                        return ImmutableSet.of(LocalDate.EPOCH.plus(1L, ChronoUnit.MONTHS), LocalDate.EPOCH.plus(Period.ofMonths(1)));
                                    }
                                }
                                """,
                        """
                                import com.google.common.collect.ImmutableSet;
                                
                                import java.time.LocalDate;
                                
                                class Test {
                                    ImmutableSet<LocalDate> test() {
                                        return ImmutableSet.of(LocalDate.EPOCH.plusMonths(1L), LocalDate.EPOCH.plusMonths(1));
                                    }
                                }
                                """
                ));
    }

    @Test
    void testLocalDatePlusWeeksRecipe() {
        Recipe recipe = RewriteUtils.loadRecipe("TimeRulesRecipes.LocalDatePlusWeeksRecipe");
        rewriteRun(
                spec -> spec.recipe(recipe)
                        .expectedCyclesThatMakeChanges(1).cycles(1)
                        .parser(JavaParser.fromJavaVersion().classpath(JavaParser.runtimeClasspath())),
                java(
                        """
                                import com.google.common.collect.ImmutableSet;
                                
                                import java.time.LocalDate;
                                import java.time.Period;
                                import java.time.temporal.ChronoUnit;
                                
                                class Test {
                                    ImmutableSet<LocalDate> test() {
                                        return ImmutableSet.of(LocalDate.EPOCH.plus(1L, ChronoUnit.WEEKS), LocalDate.EPOCH.plus(Period.ofWeeks(1)));
                                    }
                                }
                                """,
                        """
                                import com.google.common.collect.ImmutableSet;
                                
                                import java.time.LocalDate;
                                
                                class Test {
                                    ImmutableSet<LocalDate> test() {
                                        return ImmutableSet.of(LocalDate.EPOCH.plusWeeks(1L), LocalDate.EPOCH.plusWeeks(1));
                                    }
                                }
                                """
                ));
    }

    @Test
    void testLocalDatePlusYearsRecipe() {
        Recipe recipe = RewriteUtils.loadRecipe("TimeRulesRecipes.LocalDatePlusYearsRecipe");
        rewriteRun(
                spec -> spec.recipe(recipe)
                        .expectedCyclesThatMakeChanges(1).cycles(1)
                        .parser(JavaParser.fromJavaVersion().classpath(JavaParser.runtimeClasspath())),
                java(
                        """
                                import com.google.common.collect.ImmutableSet;
                                
                                import java.time.LocalDate;
                                import java.time.Period;
                                import java.time.temporal.ChronoUnit;
                                
                                class Test {
                                    ImmutableSet<LocalDate> test() {
                                        return ImmutableSet.of(LocalDate.EPOCH.plus(1L, ChronoUnit.YEARS), LocalDate.EPOCH.plus(Period.ofYears(1)));
                                    }
                                }
                                """,
                        """
                                import com.google.common.collect.ImmutableSet;
                                
                                import java.time.LocalDate;
                                
                                class Test {
                                    ImmutableSet<LocalDate> test() {
                                        return ImmutableSet.of(LocalDate.EPOCH.plusYears(1L), LocalDate.EPOCH.plusYears(1));
                                    }
                                }
                                """
                ));
    }

    @Test
    void testLocalDateTimeMinusDaysRecipe() {
        Recipe recipe = RewriteUtils.loadRecipe("TimeRulesRecipes.LocalDateTimeMinusDaysRecipe");
        rewriteRun(
                spec -> spec.recipe(recipe)
                        .expectedCyclesThatMakeChanges(1).cycles(1)
                        .parser(JavaParser.fromJavaVersion().classpath(JavaParser.runtimeClasspath())),
                java(
                        """
                                import com.google.common.collect.ImmutableSet;
                                
                                import java.time.LocalDateTime;
                                import java.time.Period;
                                import java.time.temporal.ChronoUnit;
                                
                                class Test {
                                    ImmutableSet<LocalDateTime> test() {
                                        return ImmutableSet.of(LocalDateTime.MAX.minus(1L, ChronoUnit.DAYS), LocalDateTime.MAX.minus(Period.ofDays(1)));
                                    }
                                }
                                """,
                        """
                                import com.google.common.collect.ImmutableSet;
                                
                                import java.time.LocalDateTime;
                                
                                class Test {
                                    ImmutableSet<LocalDateTime> test() {
                                        return ImmutableSet.of(LocalDateTime.MAX.minusDays(1L), LocalDateTime.MAX.minusDays(1));
                                    }
                                }
                                """
                ));
    }

    @Test
    void testLocalDateTimeMinusHoursRecipe() {
        Recipe recipe = RewriteUtils.loadRecipe("TimeRulesRecipes.LocalDateTimeMinusHoursRecipe");
        rewriteRun(
                spec -> spec.recipe(recipe)
                        .expectedCyclesThatMakeChanges(1).cycles(1)
                        .parser(JavaParser.fromJavaVersion().classpath(JavaParser.runtimeClasspath())),
                java(
                        """
                                import com.google.common.collect.ImmutableSet;
                                
                                import java.time.Duration;
                                import java.time.LocalDateTime;
                                import java.time.temporal.ChronoUnit;
                                
                                class Test {
                                    ImmutableSet<LocalDateTime> test() {
                                        return ImmutableSet.of(LocalDateTime.MAX.minus(1L, ChronoUnit.HOURS), LocalDateTime.MAX.minus(Duration.ofHours(1)));
                                    }
                                }
                                """,
                        """
                                import com.google.common.collect.ImmutableSet;
                                
                                import java.time.LocalDateTime;
                                
                                class Test {
                                    ImmutableSet<LocalDateTime> test() {
                                        return ImmutableSet.of(LocalDateTime.MAX.minusHours(1L), LocalDateTime.MAX.minusHours(1));
                                    }
                                }
                                """
                ));
    }

    @Test
    void testLocalDateTimeMinusMinutesRecipe() {
        Recipe recipe = RewriteUtils.loadRecipe("TimeRulesRecipes.LocalDateTimeMinusMinutesRecipe");
        rewriteRun(
                spec -> spec.recipe(recipe)
                        .expectedCyclesThatMakeChanges(1).cycles(1)
                        .parser(JavaParser.fromJavaVersion().classpath(JavaParser.runtimeClasspath())),
                java(
                        """
                                import com.google.common.collect.ImmutableSet;
                                
                                import java.time.Duration;
                                import java.time.LocalDateTime;
                                import java.time.temporal.ChronoUnit;
                                
                                class Test {
                                    ImmutableSet<LocalDateTime> test() {
                                        return ImmutableSet.of(LocalDateTime.MAX.minus(1L, ChronoUnit.MINUTES), LocalDateTime.MAX.minus(Duration.ofMinutes(1)));
                                    }
                                }
                                """,
                        """
                                import com.google.common.collect.ImmutableSet;
                                
                                import java.time.LocalDateTime;
                                
                                class Test {
                                    ImmutableSet<LocalDateTime> test() {
                                        return ImmutableSet.of(LocalDateTime.MAX.minusMinutes(1L), LocalDateTime.MAX.minusMinutes(1));
                                    }
                                }
                                """
                ));
    }

    @Test
    void testLocalDateTimeMinusMonthsRecipe() {
        Recipe recipe = RewriteUtils.loadRecipe("TimeRulesRecipes.LocalDateTimeMinusMonthsRecipe");
        rewriteRun(
                spec -> spec.recipe(recipe)
                        .expectedCyclesThatMakeChanges(1).cycles(1)
                        .parser(JavaParser.fromJavaVersion().classpath(JavaParser.runtimeClasspath())),
                java(
                        """
                                import com.google.common.collect.ImmutableSet;
                                
                                import java.time.LocalDateTime;
                                import java.time.Period;
                                import java.time.temporal.ChronoUnit;
                                
                                class Test {
                                    ImmutableSet<LocalDateTime> test() {
                                        return ImmutableSet.of(LocalDateTime.MAX.minus(1L, ChronoUnit.MONTHS), LocalDateTime.MAX.minus(Period.ofMonths(1)));
                                    }
                                }
                                """,
                        """
                                import com.google.common.collect.ImmutableSet;
                                
                                import java.time.LocalDateTime;
                                
                                class Test {
                                    ImmutableSet<LocalDateTime> test() {
                                        return ImmutableSet.of(LocalDateTime.MAX.minusMonths(1L), LocalDateTime.MAX.minusMonths(1));
                                    }
                                }
                                """
                ));
    }

    @Test
    void testLocalDateTimeMinusNanosRecipe() {
        Recipe recipe = RewriteUtils.loadRecipe("TimeRulesRecipes.LocalDateTimeMinusNanosRecipe");
        rewriteRun(
                spec -> spec.recipe(recipe)
                        .expectedCyclesThatMakeChanges(1).cycles(1)
                        .parser(JavaParser.fromJavaVersion().classpath(JavaParser.runtimeClasspath())),
                java(
                        """
                                import com.google.common.collect.ImmutableSet;
                                
                                import java.time.Duration;
                                import java.time.LocalDateTime;
                                import java.time.temporal.ChronoUnit;
                                
                                class Test {
                                    ImmutableSet<LocalDateTime> test() {
                                        return ImmutableSet.of(LocalDateTime.MAX.minus(1L, ChronoUnit.NANOS), LocalDateTime.MAX.minus(Duration.ofNanos(1)));
                                    }
                                }
                                """,
                        """
                                import com.google.common.collect.ImmutableSet;
                                
                                import java.time.LocalDateTime;
                                
                                class Test {
                                    ImmutableSet<LocalDateTime> test() {
                                        return ImmutableSet.of(LocalDateTime.MAX.minusNanos(1L), LocalDateTime.MAX.minusNanos(1));
                                    }
                                }
                                """
                ));
    }

    @Test
    void testLocalDateTimeMinusSecondsRecipe() {
        Recipe recipe = RewriteUtils.loadRecipe("TimeRulesRecipes.LocalDateTimeMinusSecondsRecipe");
        rewriteRun(
                spec -> spec.recipe(recipe)
                        .expectedCyclesThatMakeChanges(1).cycles(1)
                        .parser(JavaParser.fromJavaVersion().classpath(JavaParser.runtimeClasspath())),
                java(
                        """
                                import com.google.common.collect.ImmutableSet;
                                
                                import java.time.Duration;
                                import java.time.LocalDateTime;
                                import java.time.temporal.ChronoUnit;
                                
                                class Test {
                                    ImmutableSet<LocalDateTime> test() {
                                        return ImmutableSet.of(LocalDateTime.MAX.minus(1L, ChronoUnit.SECONDS), LocalDateTime.MAX.minus(Duration.ofSeconds(1)));
                                    }
                                }
                                """,
                        """
                                import com.google.common.collect.ImmutableSet;
                                
                                import java.time.LocalDateTime;
                                
                                class Test {
                                    ImmutableSet<LocalDateTime> test() {
                                        return ImmutableSet.of(LocalDateTime.MAX.minusSeconds(1L), LocalDateTime.MAX.minusSeconds(1));
                                    }
                                }
                                """
                ));
    }

    @Test
    void testLocalDateTimeMinusWeeksRecipe() {
        Recipe recipe = RewriteUtils.loadRecipe("TimeRulesRecipes.LocalDateTimeMinusWeeksRecipe");
        rewriteRun(
                spec -> spec.recipe(recipe)
                        .expectedCyclesThatMakeChanges(1).cycles(1)
                        .parser(JavaParser.fromJavaVersion().classpath(JavaParser.runtimeClasspath())),
                java(
                        """
                                import com.google.common.collect.ImmutableSet;
                                
                                import java.time.LocalDateTime;
                                import java.time.Period;
                                import java.time.temporal.ChronoUnit;
                                
                                class Test {
                                    ImmutableSet<LocalDateTime> test() {
                                        return ImmutableSet.of(LocalDateTime.MAX.minus(1L, ChronoUnit.WEEKS), LocalDateTime.MAX.minus(Period.ofWeeks(1)));
                                    }
                                }
                                """,
                        """
                                import com.google.common.collect.ImmutableSet;
                                
                                import java.time.LocalDateTime;
                                
                                class Test {
                                    ImmutableSet<LocalDateTime> test() {
                                        return ImmutableSet.of(LocalDateTime.MAX.minusWeeks(1L), LocalDateTime.MAX.minusWeeks(1));
                                    }
                                }
                                """
                ));
    }

    @Test
    void testLocalDateTimeMinusYearsRecipe() {
        Recipe recipe = RewriteUtils.loadRecipe("TimeRulesRecipes.LocalDateTimeMinusYearsRecipe");
        rewriteRun(
                spec -> spec.recipe(recipe)
                        .expectedCyclesThatMakeChanges(1).cycles(1)
                        .parser(JavaParser.fromJavaVersion().classpath(JavaParser.runtimeClasspath())),
                java(
                        """
                                import com.google.common.collect.ImmutableSet;
                                
                                import java.time.LocalDateTime;
                                import java.time.Period;
                                import java.time.temporal.ChronoUnit;
                                
                                class Test {
                                    ImmutableSet<LocalDateTime> test() {
                                        return ImmutableSet.of(LocalDateTime.MAX.minus(1L, ChronoUnit.YEARS), LocalDateTime.MAX.minus(Period.ofYears(1)));
                                    }
                                }
                                """,
                        """
                                import com.google.common.collect.ImmutableSet;
                                
                                import java.time.LocalDateTime;
                                
                                class Test {
                                    ImmutableSet<LocalDateTime> test() {
                                        return ImmutableSet.of(LocalDateTime.MAX.minusYears(1L), LocalDateTime.MAX.minusYears(1));
                                    }
                                }
                                """
                ));
    }

    @Test
    void testLocalDateTimeOfInstantRecipe() {
        Recipe recipe = RewriteUtils.loadRecipe("TimeRulesRecipes.LocalDateTimeOfInstantRecipe");
        rewriteRun(
                spec -> spec.recipe(recipe)
                        .expectedCyclesThatMakeChanges(1).cycles(1)
                        .parser(JavaParser.fromJavaVersion().classpath(JavaParser.runtimeClasspath())),
                java(
                        """
                                import com.google.common.collect.ImmutableSet;
                                
                                import java.time.*;
                                
                                class Test {
                                    ImmutableSet<LocalDateTime> test() {
                                        return ImmutableSet.of(Instant.EPOCH.atZone(ZoneId.of("Europe/Amsterdam")).toLocalDateTime(), Instant.EPOCH.atOffset(ZoneOffset.UTC).toLocalDateTime(), OffsetDateTime.ofInstant(Instant.EPOCH, ZoneId.of("Europe/Berlin")).toLocalDateTime());
                                    }
                                }
                                """,
                        """
                                import com.google.common.collect.ImmutableSet;
                                
                                import java.time.Instant;
                                import java.time.LocalDateTime;
                                import java.time.ZoneId;
                                import java.time.ZoneOffset;
                                
                                class Test {
                                    ImmutableSet<LocalDateTime> test() {
                                        return ImmutableSet.of(LocalDateTime.ofInstant(Instant.EPOCH, ZoneId.of("Europe/Amsterdam")), LocalDateTime.ofInstant(Instant.EPOCH, ZoneOffset.UTC), LocalDateTime.ofInstant(Instant.EPOCH, ZoneId.of("Europe/Berlin")));
                                    }
                                }
                                """
                ));
    }

    @Test
    void testLocalDateTimePlusDaysRecipe() {
        Recipe recipe = RewriteUtils.loadRecipe("TimeRulesRecipes.LocalDateTimePlusDaysRecipe");
        rewriteRun(
                spec -> spec.recipe(recipe)
                        .expectedCyclesThatMakeChanges(1).cycles(1)
                        .parser(JavaParser.fromJavaVersion().classpath(JavaParser.runtimeClasspath())),
                java(
                        """
                                import com.google.common.collect.ImmutableSet;
                                
                                import java.time.LocalDateTime;
                                import java.time.Period;
                                import java.time.temporal.ChronoUnit;
                                
                                class Test {
                                    ImmutableSet<LocalDateTime> test() {
                                        return ImmutableSet.of(LocalDateTime.MIN.plus(1L, ChronoUnit.DAYS), LocalDateTime.MIN.plus(Period.ofDays(1)));
                                    }
                                }
                                """,
                        """
                                import com.google.common.collect.ImmutableSet;
                                
                                import java.time.LocalDateTime;
                                
                                class Test {
                                    ImmutableSet<LocalDateTime> test() {
                                        return ImmutableSet.of(LocalDateTime.MIN.plusDays(1L), LocalDateTime.MIN.plusDays(1));
                                    }
                                }
                                """
                ));
    }

    @Test
    void testLocalDateTimePlusHoursRecipe() {
        Recipe recipe = RewriteUtils.loadRecipe("TimeRulesRecipes.LocalDateTimePlusHoursRecipe");
        rewriteRun(
                spec -> spec.recipe(recipe)
                        .expectedCyclesThatMakeChanges(1).cycles(1)
                        .parser(JavaParser.fromJavaVersion().classpath(JavaParser.runtimeClasspath())),
                java(
                        """
                                import com.google.common.collect.ImmutableSet;
                                
                                import java.time.Duration;
                                import java.time.LocalDateTime;
                                import java.time.temporal.ChronoUnit;
                                
                                class Test {
                                    ImmutableSet<LocalDateTime> test() {
                                        return ImmutableSet.of(LocalDateTime.MIN.plus(1L, ChronoUnit.HOURS), LocalDateTime.MIN.plus(Duration.ofHours(1)));
                                    }
                                }
                                """,
                        """
                                import com.google.common.collect.ImmutableSet;
                                
                                import java.time.LocalDateTime;
                                
                                class Test {
                                    ImmutableSet<LocalDateTime> test() {
                                        return ImmutableSet.of(LocalDateTime.MIN.plusHours(1L), LocalDateTime.MIN.plusHours(1));
                                    }
                                }
                                """
                ));
    }

    @Test
    void testLocalDateTimePlusMinutesRecipe() {
        Recipe recipe = RewriteUtils.loadRecipe("TimeRulesRecipes.LocalDateTimePlusMinutesRecipe");
        rewriteRun(
                spec -> spec.recipe(recipe)
                        .expectedCyclesThatMakeChanges(1).cycles(1)
                        .parser(JavaParser.fromJavaVersion().classpath(JavaParser.runtimeClasspath())),
                java(
                        """
                                import com.google.common.collect.ImmutableSet;
                                
                                import java.time.Duration;
                                import java.time.LocalDateTime;
                                import java.time.temporal.ChronoUnit;
                                
                                class Test {
                                    ImmutableSet<LocalDateTime> test() {
                                        return ImmutableSet.of(LocalDateTime.MIN.plus(1L, ChronoUnit.MINUTES), LocalDateTime.MIN.plus(Duration.ofMinutes(1)));
                                    }
                                }
                                """,
                        """
                                import com.google.common.collect.ImmutableSet;
                                
                                import java.time.LocalDateTime;
                                
                                class Test {
                                    ImmutableSet<LocalDateTime> test() {
                                        return ImmutableSet.of(LocalDateTime.MIN.plusMinutes(1L), LocalDateTime.MIN.plusMinutes(1));
                                    }
                                }
                                """
                ));
    }

    @Test
    void testLocalDateTimePlusMonthsRecipe() {
        Recipe recipe = RewriteUtils.loadRecipe("TimeRulesRecipes.LocalDateTimePlusMonthsRecipe");
        rewriteRun(
                spec -> spec.recipe(recipe)
                        .expectedCyclesThatMakeChanges(1).cycles(1)
                        .parser(JavaParser.fromJavaVersion().classpath(JavaParser.runtimeClasspath())),
                java(
                        """
                                import com.google.common.collect.ImmutableSet;
                                
                                import java.time.LocalDateTime;
                                import java.time.Period;
                                import java.time.temporal.ChronoUnit;
                                
                                class Test {
                                    ImmutableSet<LocalDateTime> test() {
                                        return ImmutableSet.of(LocalDateTime.MIN.plus(1L, ChronoUnit.MONTHS), LocalDateTime.MIN.plus(Period.ofMonths(1)));
                                    }
                                }
                                """,
                        """
                                import com.google.common.collect.ImmutableSet;
                                
                                import java.time.LocalDateTime;
                                
                                class Test {
                                    ImmutableSet<LocalDateTime> test() {
                                        return ImmutableSet.of(LocalDateTime.MIN.plusMonths(1L), LocalDateTime.MIN.plusMonths(1));
                                    }
                                }
                                """
                ));
    }

    @Test
    void testLocalDateTimePlusNanosRecipe() {
        Recipe recipe = RewriteUtils.loadRecipe("TimeRulesRecipes.LocalDateTimePlusNanosRecipe");
        rewriteRun(
                spec -> spec.recipe(recipe)
                        .expectedCyclesThatMakeChanges(1).cycles(1)
                        .parser(JavaParser.fromJavaVersion().classpath(JavaParser.runtimeClasspath())),
                java(
                        """
                                import com.google.common.collect.ImmutableSet;
                                
                                import java.time.Duration;
                                import java.time.LocalDateTime;
                                import java.time.temporal.ChronoUnit;
                                
                                class Test {
                                    ImmutableSet<LocalDateTime> test() {
                                        return ImmutableSet.of(LocalDateTime.MIN.plus(1L, ChronoUnit.NANOS), LocalDateTime.MIN.plus(Duration.ofNanos(1)));
                                    }
                                }
                                """,
                        """
                                import com.google.common.collect.ImmutableSet;
                                
                                import java.time.LocalDateTime;
                                
                                class Test {
                                    ImmutableSet<LocalDateTime> test() {
                                        return ImmutableSet.of(LocalDateTime.MIN.plusNanos(1L), LocalDateTime.MIN.plusNanos(1));
                                    }
                                }
                                """
                ));
    }

    @Test
    void testLocalDateTimePlusSecondsRecipe() {
        Recipe recipe = RewriteUtils.loadRecipe("TimeRulesRecipes.LocalDateTimePlusSecondsRecipe");
        rewriteRun(
                spec -> spec.recipe(recipe)
                        .expectedCyclesThatMakeChanges(1).cycles(1)
                        .parser(JavaParser.fromJavaVersion().classpath(JavaParser.runtimeClasspath())),
                java(
                        """
                                import com.google.common.collect.ImmutableSet;
                                
                                import java.time.Duration;
                                import java.time.LocalDateTime;
                                import java.time.temporal.ChronoUnit;
                                
                                class Test {
                                    ImmutableSet<LocalDateTime> test() {
                                        return ImmutableSet.of(LocalDateTime.MIN.plus(1L, ChronoUnit.SECONDS), LocalDateTime.MIN.plus(Duration.ofSeconds(1)));
                                    }
                                }
                                """,
                        """
                                import com.google.common.collect.ImmutableSet;
                                
                                import java.time.LocalDateTime;
                                
                                class Test {
                                    ImmutableSet<LocalDateTime> test() {
                                        return ImmutableSet.of(LocalDateTime.MIN.plusSeconds(1L), LocalDateTime.MIN.plusSeconds(1));
                                    }
                                }
                                """
                ));
    }

    @Test
    void testLocalDateTimePlusWeeksRecipe() {
        Recipe recipe = RewriteUtils.loadRecipe("TimeRulesRecipes.LocalDateTimePlusWeeksRecipe");
        rewriteRun(
                spec -> spec.recipe(recipe)
                        .expectedCyclesThatMakeChanges(1).cycles(1)
                        .parser(JavaParser.fromJavaVersion().classpath(JavaParser.runtimeClasspath())),
                java(
                        """
                                import com.google.common.collect.ImmutableSet;
                                
                                import java.time.LocalDateTime;
                                import java.time.Period;
                                import java.time.temporal.ChronoUnit;
                                
                                class Test {
                                    ImmutableSet<LocalDateTime> test() {
                                        return ImmutableSet.of(LocalDateTime.MIN.plus(1L, ChronoUnit.WEEKS), LocalDateTime.MIN.plus(Period.ofWeeks(1)));
                                    }
                                }
                                """,
                        """
                                import com.google.common.collect.ImmutableSet;
                                
                                import java.time.LocalDateTime;
                                
                                class Test {
                                    ImmutableSet<LocalDateTime> test() {
                                        return ImmutableSet.of(LocalDateTime.MIN.plusWeeks(1L), LocalDateTime.MIN.plusWeeks(1));
                                    }
                                }
                                """
                ));
    }

    @Test
    void testLocalDateTimePlusYearsRecipe() {
        Recipe recipe = RewriteUtils.loadRecipe("TimeRulesRecipes.LocalDateTimePlusYearsRecipe");
        rewriteRun(
                spec -> spec.recipe(recipe)
                        .expectedCyclesThatMakeChanges(1).cycles(1)
                        .parser(JavaParser.fromJavaVersion().classpath(JavaParser.runtimeClasspath())),
                java(
                        """
                                import com.google.common.collect.ImmutableSet;
                                
                                import java.time.LocalDateTime;
                                import java.time.Period;
                                import java.time.temporal.ChronoUnit;
                                
                                class Test {
                                    ImmutableSet<LocalDateTime> test() {
                                        return ImmutableSet.of(LocalDateTime.MIN.plus(1L, ChronoUnit.YEARS), LocalDateTime.MIN.plus(Period.ofYears(1)));
                                    }
                                }
                                """,
                        """
                                import com.google.common.collect.ImmutableSet;
                                
                                import java.time.LocalDateTime;
                                
                                class Test {
                                    ImmutableSet<LocalDateTime> test() {
                                        return ImmutableSet.of(LocalDateTime.MIN.plusYears(1L), LocalDateTime.MIN.plusYears(1));
                                    }
                                }
                                """
                ));
    }

    @Test
    void testLocalTimeMinRecipe() {
        Recipe recipe = RewriteUtils.loadRecipe("TimeRulesRecipes.LocalTimeMinRecipe");
        rewriteRun(
                spec -> spec.recipe(recipe)
                        .expectedCyclesThatMakeChanges(1).cycles(1)
                        .parser(JavaParser.fromJavaVersion().classpath(JavaParser.runtimeClasspath())),
                java(
                        """
                                import com.google.common.collect.ImmutableSet;
                                
                                import java.time.LocalTime;
                                
                                class Test {
                                    ImmutableSet<LocalTime> test() {
                                        return ImmutableSet.of(LocalTime.MIDNIGHT, LocalTime.of(0, 0), LocalTime.of(0, 0, 0), LocalTime.of(0, 0, 0, 0), LocalTime.ofNanoOfDay(0), LocalTime.ofSecondOfDay(0));
                                    }
                                }
                                """,
                        """
                                import com.google.common.collect.ImmutableSet;
                                
                                import java.time.LocalTime;
                                
                                class Test {
                                    ImmutableSet<LocalTime> test() {
                                        return ImmutableSet.of(LocalTime.MIN, LocalTime.MIN, LocalTime.MIN, LocalTime.MIN, LocalTime.MIN, LocalTime.MIN);
                                    }
                                }
                                """
                ));
    }

    @Test
    void testLocalTimeMinusHoursRecipe() {
        Recipe recipe = RewriteUtils.loadRecipe("TimeRulesRecipes.LocalTimeMinusHoursRecipe");
        rewriteRun(
                spec -> spec.recipe(recipe)
                        .expectedCyclesThatMakeChanges(1).cycles(1)
                        .parser(JavaParser.fromJavaVersion().classpath(JavaParser.runtimeClasspath())),
                java(
                        """
                                import com.google.common.collect.ImmutableSet;
                                
                                import java.time.Duration;
                                import java.time.LocalTime;
                                import java.time.temporal.ChronoUnit;
                                
                                class Test {
                                    ImmutableSet<LocalTime> test() {
                                        return ImmutableSet.of(LocalTime.NOON.minus(1L, ChronoUnit.HOURS), LocalTime.NOON.minus(Duration.ofHours(1)));
                                    }
                                }
                                """,
                        """
                                import com.google.common.collect.ImmutableSet;
                                
                                import java.time.LocalTime;
                                
                                class Test {
                                    ImmutableSet<LocalTime> test() {
                                        return ImmutableSet.of(LocalTime.NOON.minusHours(1L), LocalTime.NOON.minusHours(1));
                                    }
                                }
                                """
                ));
    }

    @Test
    void testLocalTimeMinusMinutesRecipe() {
        Recipe recipe = RewriteUtils.loadRecipe("TimeRulesRecipes.LocalTimeMinusMinutesRecipe");
        rewriteRun(
                spec -> spec.recipe(recipe)
                        .expectedCyclesThatMakeChanges(1).cycles(1)
                        .parser(JavaParser.fromJavaVersion().classpath(JavaParser.runtimeClasspath())),
                java(
                        """
                                import com.google.common.collect.ImmutableSet;
                                
                                import java.time.Duration;
                                import java.time.LocalTime;
                                import java.time.temporal.ChronoUnit;
                                
                                class Test {
                                    ImmutableSet<LocalTime> test() {
                                        return ImmutableSet.of(LocalTime.NOON.minus(1L, ChronoUnit.MINUTES), LocalTime.NOON.minus(Duration.ofMinutes(1)));
                                    }
                                }
                                """,
                        """
                                import com.google.common.collect.ImmutableSet;
                                
                                import java.time.LocalTime;
                                
                                class Test {
                                    ImmutableSet<LocalTime> test() {
                                        return ImmutableSet.of(LocalTime.NOON.minusMinutes(1L), LocalTime.NOON.minusMinutes(1));
                                    }
                                }
                                """
                ));
    }

    @Test
    void testLocalTimeMinusNanosRecipe() {
        Recipe recipe = RewriteUtils.loadRecipe("TimeRulesRecipes.LocalTimeMinusNanosRecipe");
        rewriteRun(
                spec -> spec.recipe(recipe)
                        .expectedCyclesThatMakeChanges(1).cycles(1)
                        .parser(JavaParser.fromJavaVersion().classpath(JavaParser.runtimeClasspath())),
                java(
                        """
                                import com.google.common.collect.ImmutableSet;
                                
                                import java.time.Duration;
                                import java.time.LocalTime;
                                import java.time.temporal.ChronoUnit;
                                
                                class Test {
                                    ImmutableSet<LocalTime> test() {
                                        return ImmutableSet.of(LocalTime.NOON.minus(1L, ChronoUnit.NANOS), LocalTime.NOON.minus(Duration.ofNanos(1)));
                                    }
                                }
                                """,
                        """
                                import com.google.common.collect.ImmutableSet;
                                
                                import java.time.LocalTime;
                                
                                class Test {
                                    ImmutableSet<LocalTime> test() {
                                        return ImmutableSet.of(LocalTime.NOON.minusNanos(1L), LocalTime.NOON.minusNanos(1));
                                    }
                                }
                                """
                ));
    }

    @Test
    void testLocalTimeMinusSecondsRecipe() {
        Recipe recipe = RewriteUtils.loadRecipe("TimeRulesRecipes.LocalTimeMinusSecondsRecipe");
        rewriteRun(
                spec -> spec.recipe(recipe)
                        .expectedCyclesThatMakeChanges(1).cycles(1)
                        .parser(JavaParser.fromJavaVersion().classpath(JavaParser.runtimeClasspath())),
                java(
                        """
                                import com.google.common.collect.ImmutableSet;
                                
                                import java.time.Duration;
                                import java.time.LocalTime;
                                import java.time.temporal.ChronoUnit;
                                
                                class Test {
                                    ImmutableSet<LocalTime> test() {
                                        return ImmutableSet.of(LocalTime.NOON.minus(1L, ChronoUnit.SECONDS), LocalTime.NOON.minus(Duration.ofSeconds(1)));
                                    }
                                }
                                """,
                        """
                                import com.google.common.collect.ImmutableSet;
                                
                                import java.time.LocalTime;
                                
                                class Test {
                                    ImmutableSet<LocalTime> test() {
                                        return ImmutableSet.of(LocalTime.NOON.minusSeconds(1L), LocalTime.NOON.minusSeconds(1));
                                    }
                                }
                                """
                ));
    }

    @Test
    void testLocalTimeOfInstantRecipe() {
        Recipe recipe = RewriteUtils.loadRecipe("TimeRulesRecipes.LocalTimeOfInstantRecipe");
        rewriteRun(
                spec -> spec.recipe(recipe)
                        .expectedCyclesThatMakeChanges(1).cycles(1)
                        .parser(JavaParser.fromJavaVersion().classpath(JavaParser.runtimeClasspath())),
                java(
                        """
                                import com.google.common.collect.ImmutableSet;
                                
                                import java.time.*;
                                
                                class Test {
                                    ImmutableSet<LocalTime> test() {
                                        return ImmutableSet.of(Instant.EPOCH.atZone(ZoneId.of("Europe/Amsterdam")).toLocalTime(), Instant.EPOCH.atOffset(ZoneOffset.UTC).toLocalTime(), LocalDateTime.ofInstant(Instant.EPOCH, ZoneId.of("Europe/Berlin")).toLocalTime(), OffsetDateTime.ofInstant(Instant.EPOCH, ZoneOffset.MIN).toLocalTime(), OffsetTime.ofInstant(Instant.EPOCH, ZoneOffset.MAX).toLocalTime());
                                    }
                                }
                                """,
                        """
                                import com.google.common.collect.ImmutableSet;
                                
                                import java.time.Instant;
                                import java.time.LocalTime;
                                import java.time.ZoneId;
                                import java.time.ZoneOffset;
                                
                                class Test {
                                    ImmutableSet<LocalTime> test() {
                                        return ImmutableSet.of(LocalTime.ofInstant(Instant.EPOCH, ZoneId.of("Europe/Amsterdam")), LocalTime.ofInstant(Instant.EPOCH, ZoneOffset.UTC), LocalTime.ofInstant(Instant.EPOCH, ZoneId.of("Europe/Berlin")), LocalTime.ofInstant(Instant.EPOCH, ZoneOffset.MIN), LocalTime.ofInstant(Instant.EPOCH, ZoneOffset.MAX));
                                    }
                                }
                                """
                ));
    }

    @Test
    void testLocalTimePlusHoursRecipe() {
        Recipe recipe = RewriteUtils.loadRecipe("TimeRulesRecipes.LocalTimePlusHoursRecipe");
        rewriteRun(
                spec -> spec.recipe(recipe)
                        .expectedCyclesThatMakeChanges(1).cycles(1)
                        .parser(JavaParser.fromJavaVersion().classpath(JavaParser.runtimeClasspath())),
                java(
                        """
                                import com.google.common.collect.ImmutableSet;
                                
                                import java.time.Duration;
                                import java.time.LocalTime;
                                import java.time.temporal.ChronoUnit;
                                
                                class Test {
                                    ImmutableSet<LocalTime> test() {
                                        return ImmutableSet.of(LocalTime.NOON.plus(1L, ChronoUnit.HOURS), LocalTime.NOON.plus(Duration.ofHours(1)));
                                    }
                                }
                                """,
                        """
                                import com.google.common.collect.ImmutableSet;
                                
                                import java.time.LocalTime;
                                
                                class Test {
                                    ImmutableSet<LocalTime> test() {
                                        return ImmutableSet.of(LocalTime.NOON.plusHours(1L), LocalTime.NOON.plusHours(1));
                                    }
                                }
                                """
                ));
    }

    @Test
    void testLocalTimePlusMinutesRecipe() {
        Recipe recipe = RewriteUtils.loadRecipe("TimeRulesRecipes.LocalTimePlusMinutesRecipe");
        rewriteRun(
                spec -> spec.recipe(recipe)
                        .expectedCyclesThatMakeChanges(1).cycles(1)
                        .parser(JavaParser.fromJavaVersion().classpath(JavaParser.runtimeClasspath())),
                java(
                        """
                                import com.google.common.collect.ImmutableSet;
                                
                                import java.time.Duration;
                                import java.time.LocalTime;
                                import java.time.temporal.ChronoUnit;
                                
                                class Test {
                                    ImmutableSet<LocalTime> test() {
                                        return ImmutableSet.of(LocalTime.NOON.plus(1L, ChronoUnit.MINUTES), LocalTime.NOON.plus(Duration.ofMinutes(1)));
                                    }
                                }
                                """,
                        """
                                import com.google.common.collect.ImmutableSet;
                                
                                import java.time.LocalTime;
                                
                                class Test {
                                    ImmutableSet<LocalTime> test() {
                                        return ImmutableSet.of(LocalTime.NOON.plusMinutes(1L), LocalTime.NOON.plusMinutes(1));
                                    }
                                }
                                """
                ));
    }

    @Test
    void testLocalTimePlusNanosRecipe() {
        Recipe recipe = RewriteUtils.loadRecipe("TimeRulesRecipes.LocalTimePlusNanosRecipe");
        rewriteRun(
                spec -> spec.recipe(recipe)
                        .expectedCyclesThatMakeChanges(1).cycles(1)
                        .parser(JavaParser.fromJavaVersion().classpath(JavaParser.runtimeClasspath())),
                java(
                        """
                                import com.google.common.collect.ImmutableSet;
                                
                                import java.time.Duration;
                                import java.time.LocalTime;
                                import java.time.temporal.ChronoUnit;
                                
                                class Test {
                                    ImmutableSet<LocalTime> test() {
                                        return ImmutableSet.of(LocalTime.NOON.plus(1L, ChronoUnit.NANOS), LocalTime.NOON.plus(Duration.ofNanos(1)));
                                    }
                                }
                                """,
                        """
                                import com.google.common.collect.ImmutableSet;
                                
                                import java.time.LocalTime;
                                
                                class Test {
                                    ImmutableSet<LocalTime> test() {
                                        return ImmutableSet.of(LocalTime.NOON.plusNanos(1L), LocalTime.NOON.plusNanos(1));
                                    }
                                }
                                """
                ));
    }

    @Test
    void testLocalTimePlusSecondsRecipe() {
        Recipe recipe = RewriteUtils.loadRecipe("TimeRulesRecipes.LocalTimePlusSecondsRecipe");
        rewriteRun(
                spec -> spec.recipe(recipe)
                        .expectedCyclesThatMakeChanges(1).cycles(1)
                        .parser(JavaParser.fromJavaVersion().classpath(JavaParser.runtimeClasspath())),
                java(
                        """
                                import com.google.common.collect.ImmutableSet;
                                
                                import java.time.Duration;
                                import java.time.LocalTime;
                                import java.time.temporal.ChronoUnit;
                                
                                class Test {
                                    ImmutableSet<LocalTime> test() {
                                        return ImmutableSet.of(LocalTime.NOON.plus(1L, ChronoUnit.SECONDS), LocalTime.NOON.plus(Duration.ofSeconds(1)));
                                    }
                                }
                                """,
                        """
                                import com.google.common.collect.ImmutableSet;
                                
                                import java.time.LocalTime;
                                
                                class Test {
                                    ImmutableSet<LocalTime> test() {
                                        return ImmutableSet.of(LocalTime.NOON.plusSeconds(1L), LocalTime.NOON.plusSeconds(1));
                                    }
                                }
                                """
                ));
    }

    @Test
    void testOffsetDateTimeIsAfterRecipe() {
        Recipe recipe = RewriteUtils.loadRecipe("TimeRulesRecipes.OffsetDateTimeIsAfterRecipe");
        rewriteRun(
                spec -> spec.recipe(recipe)
                        .expectedCyclesThatMakeChanges(1).cycles(1)
                        .parser(JavaParser.fromJavaVersion().classpath(JavaParser.runtimeClasspath())),
                java(
                        """
                                import com.google.common.collect.ImmutableSet;
                                
                                import java.time.OffsetDateTime;
                                
                                class Test {
                                    ImmutableSet<Boolean> test() {
                                        return ImmutableSet.of(OffsetDateTime.MIN.compareTo(OffsetDateTime.MAX) > 0, OffsetDateTime.MIN.compareTo(OffsetDateTime.MAX) <= 0);
                                    }
                                }
                                """,
                        """
                                import com.google.common.collect.ImmutableSet;
                                
                                import java.time.OffsetDateTime;
                                
                                class Test {
                                    ImmutableSet<Boolean> test() {
                                        return ImmutableSet.of(OffsetDateTime.MIN.isAfter(OffsetDateTime.MAX), !OffsetDateTime.MIN.isAfter(OffsetDateTime.MAX));
                                    }
                                }
                                """
                ));
    }

    @Test
    void testOffsetDateTimeIsBeforeRecipe() {
        Recipe recipe = RewriteUtils.loadRecipe("TimeRulesRecipes.OffsetDateTimeIsBeforeRecipe");
        rewriteRun(
                spec -> spec.recipe(recipe)
                        .expectedCyclesThatMakeChanges(1).cycles(1)
                        .parser(JavaParser.fromJavaVersion().classpath(JavaParser.runtimeClasspath())),
                java(
                        """
                                import com.google.common.collect.ImmutableSet;
                                
                                import java.time.OffsetDateTime;
                                
                                class Test {
                                    ImmutableSet<Boolean> test() {
                                        return ImmutableSet.of(OffsetDateTime.MIN.compareTo(OffsetDateTime.MAX) < 0, OffsetDateTime.MIN.compareTo(OffsetDateTime.MAX) >= 0);
                                    }
                                }
                                """,
                        """
                                import com.google.common.collect.ImmutableSet;
                                
                                import java.time.OffsetDateTime;
                                
                                class Test {
                                    ImmutableSet<Boolean> test() {
                                        return ImmutableSet.of(OffsetDateTime.MIN.isBefore(OffsetDateTime.MAX), !OffsetDateTime.MIN.isBefore(OffsetDateTime.MAX));
                                    }
                                }
                                """
                ));
    }

    @Test
    void testOffsetDateTimeMinusDaysRecipe() {
        Recipe recipe = RewriteUtils.loadRecipe("TimeRulesRecipes.OffsetDateTimeMinusDaysRecipe");
        rewriteRun(
                spec -> spec.recipe(recipe)
                        .expectedCyclesThatMakeChanges(1).cycles(1)
                        .parser(JavaParser.fromJavaVersion().classpath(JavaParser.runtimeClasspath())),
                java(
                        """
                                import com.google.common.collect.ImmutableSet;
                                
                                import java.time.OffsetDateTime;
                                import java.time.Period;
                                import java.time.temporal.ChronoUnit;
                                
                                class Test {
                                    ImmutableSet<OffsetDateTime> test() {
                                        return ImmutableSet.of(OffsetDateTime.MAX.minus(1L, ChronoUnit.DAYS), OffsetDateTime.MAX.minus(Period.ofDays(1)));
                                    }
                                }
                                """,
                        """
                                import com.google.common.collect.ImmutableSet;
                                
                                import java.time.OffsetDateTime;
                                
                                class Test {
                                    ImmutableSet<OffsetDateTime> test() {
                                        return ImmutableSet.of(OffsetDateTime.MAX.minusDays(1L), OffsetDateTime.MAX.minusDays(1));
                                    }
                                }
                                """
                ));
    }

    @Test
    void testOffsetDateTimeMinusHoursRecipe() {
        Recipe recipe = RewriteUtils.loadRecipe("TimeRulesRecipes.OffsetDateTimeMinusHoursRecipe");
        rewriteRun(
                spec -> spec.recipe(recipe)
                        .expectedCyclesThatMakeChanges(1).cycles(1)
                        .parser(JavaParser.fromJavaVersion().classpath(JavaParser.runtimeClasspath())),
                java(
                        """
                                import com.google.common.collect.ImmutableSet;
                                
                                import java.time.Duration;
                                import java.time.OffsetDateTime;
                                import java.time.temporal.ChronoUnit;
                                
                                class Test {
                                    ImmutableSet<OffsetDateTime> test() {
                                        return ImmutableSet.of(OffsetDateTime.MAX.minus(1L, ChronoUnit.HOURS), OffsetDateTime.MAX.minus(Duration.ofHours(1)));
                                    }
                                }
                                """,
                        """
                                import com.google.common.collect.ImmutableSet;
                                
                                import java.time.OffsetDateTime;
                                
                                class Test {
                                    ImmutableSet<OffsetDateTime> test() {
                                        return ImmutableSet.of(OffsetDateTime.MAX.minusHours(1L), OffsetDateTime.MAX.minusHours(1));
                                    }
                                }
                                """
                ));
    }

    @Test
    void testOffsetDateTimeMinusMinutesRecipe() {
        Recipe recipe = RewriteUtils.loadRecipe("TimeRulesRecipes.OffsetDateTimeMinusMinutesRecipe");
        rewriteRun(
                spec -> spec.recipe(recipe)
                        .expectedCyclesThatMakeChanges(1).cycles(1)
                        .parser(JavaParser.fromJavaVersion().classpath(JavaParser.runtimeClasspath())),
                java(
                        """
                                import com.google.common.collect.ImmutableSet;
                                
                                import java.time.Duration;
                                import java.time.OffsetDateTime;
                                import java.time.temporal.ChronoUnit;
                                
                                class Test {
                                    ImmutableSet<OffsetDateTime> test() {
                                        return ImmutableSet.of(OffsetDateTime.MAX.minus(1L, ChronoUnit.MINUTES), OffsetDateTime.MAX.minus(Duration.ofMinutes(1)));
                                    }
                                }
                                """,
                        """
                                import com.google.common.collect.ImmutableSet;
                                
                                import java.time.OffsetDateTime;
                                
                                class Test {
                                    ImmutableSet<OffsetDateTime> test() {
                                        return ImmutableSet.of(OffsetDateTime.MAX.minusMinutes(1L), OffsetDateTime.MAX.minusMinutes(1));
                                    }
                                }
                                """
                ));
    }

    @Test
    void testOffsetDateTimeMinusMonthsRecipe() {
        Recipe recipe = RewriteUtils.loadRecipe("TimeRulesRecipes.OffsetDateTimeMinusMonthsRecipe");
        rewriteRun(
                spec -> spec.recipe(recipe)
                        .expectedCyclesThatMakeChanges(1).cycles(1)
                        .parser(JavaParser.fromJavaVersion().classpath(JavaParser.runtimeClasspath())),
                java(
                        """
                                import com.google.common.collect.ImmutableSet;
                                
                                import java.time.OffsetDateTime;
                                import java.time.Period;
                                import java.time.temporal.ChronoUnit;
                                
                                class Test {
                                    ImmutableSet<OffsetDateTime> test() {
                                        return ImmutableSet.of(OffsetDateTime.MAX.minus(1L, ChronoUnit.MONTHS), OffsetDateTime.MAX.minus(Period.ofMonths(1)));
                                    }
                                }
                                """,
                        """
                                import com.google.common.collect.ImmutableSet;
                                
                                import java.time.OffsetDateTime;
                                
                                class Test {
                                    ImmutableSet<OffsetDateTime> test() {
                                        return ImmutableSet.of(OffsetDateTime.MAX.minusMonths(1L), OffsetDateTime.MAX.minusMonths(1));
                                    }
                                }
                                """
                ));
    }

    @Test
    void testOffsetDateTimeMinusNanosRecipe() {
        Recipe recipe = RewriteUtils.loadRecipe("TimeRulesRecipes.OffsetDateTimeMinusNanosRecipe");
        rewriteRun(
                spec -> spec.recipe(recipe)
                        .expectedCyclesThatMakeChanges(1).cycles(1)
                        .parser(JavaParser.fromJavaVersion().classpath(JavaParser.runtimeClasspath())),
                java(
                        """
                                import com.google.common.collect.ImmutableSet;
                                
                                import java.time.Duration;
                                import java.time.OffsetDateTime;
                                import java.time.temporal.ChronoUnit;
                                
                                class Test {
                                    ImmutableSet<OffsetDateTime> test() {
                                        return ImmutableSet.of(OffsetDateTime.MAX.minus(1L, ChronoUnit.NANOS), OffsetDateTime.MAX.minus(Duration.ofNanos(1)));
                                    }
                                }
                                """,
                        """
                                import com.google.common.collect.ImmutableSet;
                                
                                import java.time.OffsetDateTime;
                                
                                class Test {
                                    ImmutableSet<OffsetDateTime> test() {
                                        return ImmutableSet.of(OffsetDateTime.MAX.minusNanos(1L), OffsetDateTime.MAX.minusNanos(1));
                                    }
                                }
                                """
                ));
    }

    @Test
    void testOffsetDateTimeMinusSecondsRecipe() {
        Recipe recipe = RewriteUtils.loadRecipe("TimeRulesRecipes.OffsetDateTimeMinusSecondsRecipe");
        rewriteRun(
                spec -> spec.recipe(recipe)
                        .expectedCyclesThatMakeChanges(1).cycles(1)
                        .parser(JavaParser.fromJavaVersion().classpath(JavaParser.runtimeClasspath())),
                java(
                        """
                                import com.google.common.collect.ImmutableSet;
                                
                                import java.time.Duration;
                                import java.time.OffsetDateTime;
                                import java.time.temporal.ChronoUnit;
                                
                                class Test {
                                    ImmutableSet<OffsetDateTime> test() {
                                        return ImmutableSet.of(OffsetDateTime.MAX.minus(1L, ChronoUnit.SECONDS), OffsetDateTime.MAX.minus(Duration.ofSeconds(1)));
                                    }
                                }
                                """,
                        """
                                import com.google.common.collect.ImmutableSet;
                                
                                import java.time.OffsetDateTime;
                                
                                class Test {
                                    ImmutableSet<OffsetDateTime> test() {
                                        return ImmutableSet.of(OffsetDateTime.MAX.minusSeconds(1L), OffsetDateTime.MAX.minusSeconds(1));
                                    }
                                }
                                """
                ));
    }

    @Test
    void testOffsetDateTimeMinusWeeksRecipe() {
        Recipe recipe = RewriteUtils.loadRecipe("TimeRulesRecipes.OffsetDateTimeMinusWeeksRecipe");
        rewriteRun(
                spec -> spec.recipe(recipe)
                        .expectedCyclesThatMakeChanges(1).cycles(1)
                        .parser(JavaParser.fromJavaVersion().classpath(JavaParser.runtimeClasspath())),
                java(
                        """
                                import com.google.common.collect.ImmutableSet;
                                
                                import java.time.OffsetDateTime;
                                import java.time.Period;
                                import java.time.temporal.ChronoUnit;
                                
                                class Test {
                                    ImmutableSet<OffsetDateTime> test() {
                                        return ImmutableSet.of(OffsetDateTime.MAX.minus(1L, ChronoUnit.WEEKS), OffsetDateTime.MAX.minus(Period.ofWeeks(1)));
                                    }
                                }
                                """,
                        """
                                import com.google.common.collect.ImmutableSet;
                                
                                import java.time.OffsetDateTime;
                                
                                class Test {
                                    ImmutableSet<OffsetDateTime> test() {
                                        return ImmutableSet.of(OffsetDateTime.MAX.minusWeeks(1L), OffsetDateTime.MAX.minusWeeks(1));
                                    }
                                }
                                """
                ));
    }

    @Test
    void testOffsetDateTimeMinusYearsRecipe() {
        Recipe recipe = RewriteUtils.loadRecipe("TimeRulesRecipes.OffsetDateTimeMinusYearsRecipe");
        rewriteRun(
                spec -> spec.recipe(recipe)
                        .expectedCyclesThatMakeChanges(1).cycles(1)
                        .parser(JavaParser.fromJavaVersion().classpath(JavaParser.runtimeClasspath())),
                java(
                        """
                                import com.google.common.collect.ImmutableSet;
                                
                                import java.time.OffsetDateTime;
                                import java.time.Period;
                                import java.time.temporal.ChronoUnit;
                                
                                class Test {
                                    ImmutableSet<OffsetDateTime> test() {
                                        return ImmutableSet.of(OffsetDateTime.MAX.minus(1L, ChronoUnit.YEARS), OffsetDateTime.MAX.minus(Period.ofYears(1)));
                                    }
                                }
                                """,
                        """
                                import com.google.common.collect.ImmutableSet;
                                
                                import java.time.OffsetDateTime;
                                
                                class Test {
                                    ImmutableSet<OffsetDateTime> test() {
                                        return ImmutableSet.of(OffsetDateTime.MAX.minusYears(1L), OffsetDateTime.MAX.minusYears(1));
                                    }
                                }
                                """
                ));
    }

    @Test
    void testOffsetDateTimeOfInstantRecipe() {
        Recipe recipe = RewriteUtils.loadRecipe("TimeRulesRecipes.OffsetDateTimeOfInstantRecipe");
        rewriteRun(
                spec -> spec.recipe(recipe)
                        .expectedCyclesThatMakeChanges(1).cycles(1)
                        .parser(JavaParser.fromJavaVersion().classpath(JavaParser.runtimeClasspath())),
                java(
                        """
                                import java.time.Instant;
                                import java.time.OffsetDateTime;
                                import java.time.ZoneOffset;
                                
                                class Test {
                                    OffsetDateTime test() {
                                        return Instant.EPOCH.atZone(ZoneOffset.UTC).toOffsetDateTime();
                                    }
                                }
                                """,
                        """
                                import java.time.Instant;
                                import java.time.OffsetDateTime;
                                import java.time.ZoneOffset;
                                
                                class Test {
                                    OffsetDateTime test() {
                                        return OffsetDateTime.ofInstant(Instant.EPOCH, ZoneOffset.UTC);
                                    }
                                }
                                """
                ));
    }

    @Test
    void testOffsetDateTimePlusDaysRecipe() {
        Recipe recipe = RewriteUtils.loadRecipe("TimeRulesRecipes.OffsetDateTimePlusDaysRecipe");
        rewriteRun(
                spec -> spec.recipe(recipe)
                        .expectedCyclesThatMakeChanges(1).cycles(1)
                        .parser(JavaParser.fromJavaVersion().classpath(JavaParser.runtimeClasspath())),
                java(
                        """
                                import com.google.common.collect.ImmutableSet;
                                
                                import java.time.OffsetDateTime;
                                import java.time.Period;
                                import java.time.temporal.ChronoUnit;
                                
                                class Test {
                                    ImmutableSet<OffsetDateTime> test() {
                                        return ImmutableSet.of(OffsetDateTime.MIN.plus(1L, ChronoUnit.DAYS), OffsetDateTime.MIN.plus(Period.ofDays(1)));
                                    }
                                }
                                """,
                        """
                                import com.google.common.collect.ImmutableSet;
                                
                                import java.time.OffsetDateTime;
                                
                                class Test {
                                    ImmutableSet<OffsetDateTime> test() {
                                        return ImmutableSet.of(OffsetDateTime.MIN.plusDays(1L), OffsetDateTime.MIN.plusDays(1));
                                    }
                                }
                                """
                ));
    }

    @Test
    void testOffsetDateTimePlusHoursRecipe() {
        Recipe recipe = RewriteUtils.loadRecipe("TimeRulesRecipes.OffsetDateTimePlusHoursRecipe");
        rewriteRun(
                spec -> spec.recipe(recipe)
                        .expectedCyclesThatMakeChanges(1).cycles(1)
                        .parser(JavaParser.fromJavaVersion().classpath(JavaParser.runtimeClasspath())),
                java(
                        """
                                import com.google.common.collect.ImmutableSet;
                                
                                import java.time.Duration;
                                import java.time.OffsetDateTime;
                                import java.time.temporal.ChronoUnit;
                                
                                class Test {
                                    ImmutableSet<OffsetDateTime> test() {
                                        return ImmutableSet.of(OffsetDateTime.MIN.plus(1L, ChronoUnit.HOURS), OffsetDateTime.MIN.plus(Duration.ofHours(1)));
                                    }
                                }
                                """,
                        """
                                import com.google.common.collect.ImmutableSet;
                                
                                import java.time.OffsetDateTime;
                                
                                class Test {
                                    ImmutableSet<OffsetDateTime> test() {
                                        return ImmutableSet.of(OffsetDateTime.MIN.plusHours(1L), OffsetDateTime.MIN.plusHours(1));
                                    }
                                }
                                """
                ));
    }

    @Test
    void testOffsetDateTimePlusMinutesRecipe() {
        Recipe recipe = RewriteUtils.loadRecipe("TimeRulesRecipes.OffsetDateTimePlusMinutesRecipe");
        rewriteRun(
                spec -> spec.recipe(recipe)
                        .expectedCyclesThatMakeChanges(1).cycles(1)
                        .parser(JavaParser.fromJavaVersion().classpath(JavaParser.runtimeClasspath())),
                java(
                        """
                                import com.google.common.collect.ImmutableSet;
                                
                                import java.time.Duration;
                                import java.time.OffsetDateTime;
                                import java.time.temporal.ChronoUnit;
                                
                                class Test {
                                    ImmutableSet<OffsetDateTime> test() {
                                        return ImmutableSet.of(OffsetDateTime.MIN.plus(1L, ChronoUnit.MINUTES), OffsetDateTime.MIN.plus(Duration.ofMinutes(1)));
                                    }
                                }
                                """,
                        """
                                import com.google.common.collect.ImmutableSet;
                                
                                import java.time.OffsetDateTime;
                                
                                class Test {
                                    ImmutableSet<OffsetDateTime> test() {
                                        return ImmutableSet.of(OffsetDateTime.MIN.plusMinutes(1L), OffsetDateTime.MIN.plusMinutes(1));
                                    }
                                }
                                """
                ));
    }

    @Test
    void testOffsetDateTimePlusMonthsRecipe() {
        Recipe recipe = RewriteUtils.loadRecipe("TimeRulesRecipes.OffsetDateTimePlusMonthsRecipe");
        rewriteRun(
                spec -> spec.recipe(recipe)
                        .expectedCyclesThatMakeChanges(1).cycles(1)
                        .parser(JavaParser.fromJavaVersion().classpath(JavaParser.runtimeClasspath())),
                java(
                        """
                                import com.google.common.collect.ImmutableSet;
                                
                                import java.time.OffsetDateTime;
                                import java.time.Period;
                                import java.time.temporal.ChronoUnit;
                                
                                class Test {
                                    ImmutableSet<OffsetDateTime> test() {
                                        return ImmutableSet.of(OffsetDateTime.MIN.plus(1L, ChronoUnit.MONTHS), OffsetDateTime.MIN.plus(Period.ofMonths(1)));
                                    }
                                }
                                """,
                        """
                                import com.google.common.collect.ImmutableSet;
                                
                                import java.time.OffsetDateTime;
                                
                                class Test {
                                    ImmutableSet<OffsetDateTime> test() {
                                        return ImmutableSet.of(OffsetDateTime.MIN.plusMonths(1L), OffsetDateTime.MIN.plusMonths(1));
                                    }
                                }
                                """
                ));
    }

    @Test
    void testOffsetDateTimePlusNanosRecipe() {
        Recipe recipe = RewriteUtils.loadRecipe("TimeRulesRecipes.OffsetDateTimePlusNanosRecipe");
        rewriteRun(
                spec -> spec.recipe(recipe)
                        .expectedCyclesThatMakeChanges(1).cycles(1)
                        .parser(JavaParser.fromJavaVersion().classpath(JavaParser.runtimeClasspath())),
                java(
                        """
                                import com.google.common.collect.ImmutableSet;
                                
                                import java.time.Duration;
                                import java.time.OffsetDateTime;
                                import java.time.temporal.ChronoUnit;
                                
                                class Test {
                                    ImmutableSet<OffsetDateTime> test() {
                                        return ImmutableSet.of(OffsetDateTime.MIN.plus(1L, ChronoUnit.NANOS), OffsetDateTime.MIN.plus(Duration.ofNanos(1)));
                                    }
                                }
                                """,
                        """
                                import com.google.common.collect.ImmutableSet;
                                
                                import java.time.OffsetDateTime;
                                
                                class Test {
                                    ImmutableSet<OffsetDateTime> test() {
                                        return ImmutableSet.of(OffsetDateTime.MIN.plusNanos(1L), OffsetDateTime.MIN.plusNanos(1));
                                    }
                                }
                                """
                ));
    }

    @Test
    void testOffsetDateTimePlusSecondsRecipe() {
        Recipe recipe = RewriteUtils.loadRecipe("TimeRulesRecipes.OffsetDateTimePlusSecondsRecipe");
        rewriteRun(
                spec -> spec.recipe(recipe)
                        .expectedCyclesThatMakeChanges(1).cycles(1)
                        .parser(JavaParser.fromJavaVersion().classpath(JavaParser.runtimeClasspath())),
                java(
                        """
                                import com.google.common.collect.ImmutableSet;
                                
                                import java.time.Duration;
                                import java.time.OffsetDateTime;
                                import java.time.temporal.ChronoUnit;
                                
                                class Test {
                                    ImmutableSet<OffsetDateTime> test() {
                                        return ImmutableSet.of(OffsetDateTime.MIN.plus(1L, ChronoUnit.SECONDS), OffsetDateTime.MIN.plus(Duration.ofSeconds(1)));
                                    }
                                }
                                """,
                        """
                                import com.google.common.collect.ImmutableSet;
                                
                                import java.time.OffsetDateTime;
                                
                                class Test {
                                    ImmutableSet<OffsetDateTime> test() {
                                        return ImmutableSet.of(OffsetDateTime.MIN.plusSeconds(1L), OffsetDateTime.MIN.plusSeconds(1));
                                    }
                                }
                                """
                ));
    }

    @Test
    void testOffsetDateTimePlusWeeksRecipe() {
        Recipe recipe = RewriteUtils.loadRecipe("TimeRulesRecipes.OffsetDateTimePlusWeeksRecipe");
        rewriteRun(
                spec -> spec.recipe(recipe)
                        .expectedCyclesThatMakeChanges(1).cycles(1)
                        .parser(JavaParser.fromJavaVersion().classpath(JavaParser.runtimeClasspath())),
                java(
                        """
                                import com.google.common.collect.ImmutableSet;
                                
                                import java.time.OffsetDateTime;
                                import java.time.Period;
                                import java.time.temporal.ChronoUnit;
                                
                                class Test {
                                    ImmutableSet<OffsetDateTime> test() {
                                        return ImmutableSet.of(OffsetDateTime.MIN.plus(1L, ChronoUnit.WEEKS), OffsetDateTime.MIN.plus(Period.ofWeeks(1)));
                                    }
                                }
                                """,
                        """
                                import com.google.common.collect.ImmutableSet;
                                
                                import java.time.OffsetDateTime;
                                
                                class Test {
                                    ImmutableSet<OffsetDateTime> test() {
                                        return ImmutableSet.of(OffsetDateTime.MIN.plusWeeks(1L), OffsetDateTime.MIN.plusWeeks(1));
                                    }
                                }
                                """
                ));
    }

    @Test
    void testOffsetDateTimePlusYearsRecipe() {
        Recipe recipe = RewriteUtils.loadRecipe("TimeRulesRecipes.OffsetDateTimePlusYearsRecipe");
        rewriteRun(
                spec -> spec.recipe(recipe)
                        .expectedCyclesThatMakeChanges(1).cycles(1)
                        .parser(JavaParser.fromJavaVersion().classpath(JavaParser.runtimeClasspath())),
                java(
                        """
                                import com.google.common.collect.ImmutableSet;
                                
                                import java.time.OffsetDateTime;
                                import java.time.Period;
                                import java.time.temporal.ChronoUnit;
                                
                                class Test {
                                    ImmutableSet<OffsetDateTime> test() {
                                        return ImmutableSet.of(OffsetDateTime.MIN.plus(1L, ChronoUnit.YEARS), OffsetDateTime.MIN.plus(Period.ofYears(1)));
                                    }
                                }
                                """,
                        """
                                import com.google.common.collect.ImmutableSet;
                                
                                import java.time.OffsetDateTime;
                                
                                class Test {
                                    ImmutableSet<OffsetDateTime> test() {
                                        return ImmutableSet.of(OffsetDateTime.MIN.plusYears(1L), OffsetDateTime.MIN.plusYears(1));
                                    }
                                }
                                """
                ));
    }

    @Test
    void testOffsetTimeMinusHoursRecipe() {
        Recipe recipe = RewriteUtils.loadRecipe("TimeRulesRecipes.OffsetTimeMinusHoursRecipe");
        rewriteRun(
                spec -> spec.recipe(recipe)
                        .expectedCyclesThatMakeChanges(1).cycles(1)
                        .parser(JavaParser.fromJavaVersion().classpath(JavaParser.runtimeClasspath())),
                java(
                        """
                                import com.google.common.collect.ImmutableSet;
                                
                                import java.time.Duration;
                                import java.time.OffsetTime;
                                import java.time.temporal.ChronoUnit;
                                
                                class Test {
                                    ImmutableSet<OffsetTime> test() {
                                        return ImmutableSet.of(OffsetTime.MAX.minus(1L, ChronoUnit.HOURS), OffsetTime.MAX.minus(Duration.ofHours(1)));
                                    }
                                }
                                """,
                        """
                                import com.google.common.collect.ImmutableSet;
                                
                                import java.time.OffsetTime;
                                
                                class Test {
                                    ImmutableSet<OffsetTime> test() {
                                        return ImmutableSet.of(OffsetTime.MAX.minusHours(1L), OffsetTime.MAX.minusHours(1));
                                    }
                                }
                                """
                ));
    }

    @Test
    void testOffsetTimeMinusMinutesRecipe() {
        Recipe recipe = RewriteUtils.loadRecipe("TimeRulesRecipes.OffsetTimeMinusMinutesRecipe");
        rewriteRun(
                spec -> spec.recipe(recipe)
                        .expectedCyclesThatMakeChanges(1).cycles(1)
                        .parser(JavaParser.fromJavaVersion().classpath(JavaParser.runtimeClasspath())),
                java(
                        """
                                import com.google.common.collect.ImmutableSet;
                                
                                import java.time.Duration;
                                import java.time.OffsetTime;
                                import java.time.temporal.ChronoUnit;
                                
                                class Test {
                                    ImmutableSet<OffsetTime> test() {
                                        return ImmutableSet.of(OffsetTime.MAX.minus(1L, ChronoUnit.MINUTES), OffsetTime.MAX.minus(Duration.ofMinutes(1)));
                                    }
                                }
                                """,
                        """
                                import com.google.common.collect.ImmutableSet;
                                
                                import java.time.OffsetTime;
                                
                                class Test {
                                    ImmutableSet<OffsetTime> test() {
                                        return ImmutableSet.of(OffsetTime.MAX.minusMinutes(1L), OffsetTime.MAX.minusMinutes(1));
                                    }
                                }
                                """
                ));
    }

    @Test
    void testOffsetTimeMinusNanosRecipe() {
        Recipe recipe = RewriteUtils.loadRecipe("TimeRulesRecipes.OffsetTimeMinusNanosRecipe");
        rewriteRun(
                spec -> spec.recipe(recipe)
                        .expectedCyclesThatMakeChanges(1).cycles(1)
                        .parser(JavaParser.fromJavaVersion().classpath(JavaParser.runtimeClasspath())),
                java(
                        """
                                import com.google.common.collect.ImmutableSet;
                                
                                import java.time.Duration;
                                import java.time.OffsetTime;
                                import java.time.temporal.ChronoUnit;
                                
                                class Test {
                                    ImmutableSet<OffsetTime> test() {
                                        return ImmutableSet.of(OffsetTime.MAX.minus(1L, ChronoUnit.NANOS), OffsetTime.MAX.minus(Duration.ofNanos(1)));
                                    }
                                }
                                """,
                        """
                                import com.google.common.collect.ImmutableSet;
                                
                                import java.time.OffsetTime;
                                
                                class Test {
                                    ImmutableSet<OffsetTime> test() {
                                        return ImmutableSet.of(OffsetTime.MAX.minusNanos(1L), OffsetTime.MAX.minusNanos(1));
                                    }
                                }
                                """
                ));
    }

    @Test
    void testOffsetTimeMinusSecondsRecipe() {
        Recipe recipe = RewriteUtils.loadRecipe("TimeRulesRecipes.OffsetTimeMinusSecondsRecipe");
        rewriteRun(
                spec -> spec.recipe(recipe)
                        .expectedCyclesThatMakeChanges(1).cycles(1)
                        .parser(JavaParser.fromJavaVersion().classpath(JavaParser.runtimeClasspath())),
                java(
                        """
                                import com.google.common.collect.ImmutableSet;
                                
                                import java.time.Duration;
                                import java.time.OffsetTime;
                                import java.time.temporal.ChronoUnit;
                                
                                class Test {
                                    ImmutableSet<OffsetTime> test() {
                                        return ImmutableSet.of(OffsetTime.MAX.minus(1L, ChronoUnit.SECONDS), OffsetTime.MAX.minus(Duration.ofSeconds(1)));
                                    }
                                }
                                """,
                        """
                                import com.google.common.collect.ImmutableSet;
                                
                                import java.time.OffsetTime;
                                
                                class Test {
                                    ImmutableSet<OffsetTime> test() {
                                        return ImmutableSet.of(OffsetTime.MAX.minusSeconds(1L), OffsetTime.MAX.minusSeconds(1));
                                    }
                                }
                                """
                ));
    }

    @Test
    void testOffsetTimeOfInstantRecipe() {
        Recipe recipe = RewriteUtils.loadRecipe("TimeRulesRecipes.OffsetTimeOfInstantRecipe");
        rewriteRun(
                spec -> spec.recipe(recipe)
                        .expectedCyclesThatMakeChanges(1).cycles(1)
                        .parser(JavaParser.fromJavaVersion().classpath(JavaParser.runtimeClasspath())),
                java(
                        """
                                import com.google.common.collect.ImmutableSet;
                                
                                import java.time.*;
                                
                                class Test {
                                    ImmutableSet<OffsetTime> test() {
                                        return ImmutableSet.of(OffsetDateTime.ofInstant(Instant.EPOCH, ZoneId.of("Europe/Amsterdam")).toOffsetTime(), Instant.EPOCH.atOffset(ZoneOffset.UTC).toOffsetTime());
                                    }
                                }
                                """,
                        """
                                import com.google.common.collect.ImmutableSet;
                                
                                import java.time.Instant;
                                import java.time.OffsetTime;
                                import java.time.ZoneId;
                                import java.time.ZoneOffset;
                                
                                class Test {
                                    ImmutableSet<OffsetTime> test() {
                                        return ImmutableSet.of(OffsetTime.ofInstant(Instant.EPOCH, ZoneId.of("Europe/Amsterdam")), OffsetTime.ofInstant(Instant.EPOCH, ZoneOffset.UTC));
                                    }
                                }
                                """
                ));
    }

    @Test
    void testOffsetTimePlusHoursRecipe() {
        Recipe recipe = RewriteUtils.loadRecipe("TimeRulesRecipes.OffsetTimePlusHoursRecipe");
        rewriteRun(
                spec -> spec.recipe(recipe)
                        .expectedCyclesThatMakeChanges(1).cycles(1)
                        .parser(JavaParser.fromJavaVersion().classpath(JavaParser.runtimeClasspath())),
                java(
                        """
                                import com.google.common.collect.ImmutableSet;
                                
                                import java.time.Duration;
                                import java.time.OffsetTime;
                                import java.time.temporal.ChronoUnit;
                                
                                class Test {
                                    ImmutableSet<OffsetTime> test() {
                                        return ImmutableSet.of(OffsetTime.MIN.plus(1L, ChronoUnit.HOURS), OffsetTime.MIN.plus(Duration.ofHours(1)));
                                    }
                                }
                                """,
                        """
                                import com.google.common.collect.ImmutableSet;
                                
                                import java.time.OffsetTime;
                                
                                class Test {
                                    ImmutableSet<OffsetTime> test() {
                                        return ImmutableSet.of(OffsetTime.MIN.plusHours(1L), OffsetTime.MIN.plusHours(1));
                                    }
                                }
                                """
                ));
    }

    @Test
    void testOffsetTimePlusMinutesRecipe() {
        Recipe recipe = RewriteUtils.loadRecipe("TimeRulesRecipes.OffsetTimePlusMinutesRecipe");
        rewriteRun(
                spec -> spec.recipe(recipe)
                        .expectedCyclesThatMakeChanges(1).cycles(1)
                        .parser(JavaParser.fromJavaVersion().classpath(JavaParser.runtimeClasspath())),
                java(
                        """
                                import com.google.common.collect.ImmutableSet;
                                
                                import java.time.Duration;
                                import java.time.OffsetTime;
                                import java.time.temporal.ChronoUnit;
                                
                                class Test {
                                    ImmutableSet<OffsetTime> test() {
                                        return ImmutableSet.of(OffsetTime.MIN.plus(1L, ChronoUnit.MINUTES), OffsetTime.MIN.plus(Duration.ofMinutes(1)));
                                    }
                                }
                                """,
                        """
                                import com.google.common.collect.ImmutableSet;
                                
                                import java.time.OffsetTime;
                                
                                class Test {
                                    ImmutableSet<OffsetTime> test() {
                                        return ImmutableSet.of(OffsetTime.MIN.plusMinutes(1L), OffsetTime.MIN.plusMinutes(1));
                                    }
                                }
                                """
                ));
    }

    @Test
    void testOffsetTimePlusNanosRecipe() {
        Recipe recipe = RewriteUtils.loadRecipe("TimeRulesRecipes.OffsetTimePlusNanosRecipe");
        rewriteRun(
                spec -> spec.recipe(recipe)
                        .expectedCyclesThatMakeChanges(1).cycles(1)
                        .parser(JavaParser.fromJavaVersion().classpath(JavaParser.runtimeClasspath())),
                java(
                        """
                                import com.google.common.collect.ImmutableSet;
                                
                                import java.time.Duration;
                                import java.time.OffsetTime;
                                import java.time.temporal.ChronoUnit;
                                
                                class Test {
                                    ImmutableSet<OffsetTime> test() {
                                        return ImmutableSet.of(OffsetTime.MIN.plus(1L, ChronoUnit.NANOS), OffsetTime.MIN.plus(Duration.ofNanos(1)));
                                    }
                                }
                                """,
                        """
                                import com.google.common.collect.ImmutableSet;
                                
                                import java.time.OffsetTime;
                                
                                class Test {
                                    ImmutableSet<OffsetTime> test() {
                                        return ImmutableSet.of(OffsetTime.MIN.plusNanos(1L), OffsetTime.MIN.plusNanos(1));
                                    }
                                }
                                """
                ));
    }

    @Test
    void testOffsetTimePlusSecondsRecipe() {
        Recipe recipe = RewriteUtils.loadRecipe("TimeRulesRecipes.OffsetTimePlusSecondsRecipe");
        rewriteRun(
                spec -> spec.recipe(recipe)
                        .expectedCyclesThatMakeChanges(1).cycles(1)
                        .parser(JavaParser.fromJavaVersion().classpath(JavaParser.runtimeClasspath())),
                java(
                        """
                                import com.google.common.collect.ImmutableSet;
                                
                                import java.time.Duration;
                                import java.time.OffsetTime;
                                import java.time.temporal.ChronoUnit;
                                
                                class Test {
                                    ImmutableSet<OffsetTime> test() {
                                        return ImmutableSet.of(OffsetTime.MIN.plus(1L, ChronoUnit.SECONDS), OffsetTime.MIN.plus(Duration.ofSeconds(1)));
                                    }
                                }
                                """,
                        """
                                import com.google.common.collect.ImmutableSet;
                                
                                import java.time.OffsetTime;
                                
                                class Test {
                                    ImmutableSet<OffsetTime> test() {
                                        return ImmutableSet.of(OffsetTime.MIN.plusSeconds(1L), OffsetTime.MIN.plusSeconds(1));
                                    }
                                }
                                """
                ));
    }

    @Test
    void testUtcClockRecipe() {
        Recipe recipe = RewriteUtils.loadRecipe("TimeRulesRecipes.UtcClockRecipe");
        rewriteRun(
                spec -> spec.recipe(recipe)
                        .expectedCyclesThatMakeChanges(1).cycles(1)
                        .parser(JavaParser.fromJavaVersion().classpath(JavaParser.runtimeClasspath())),
                java(
                        """
                                import java.time.Clock;
                                import java.time.ZoneOffset;
                                
                                class Test {
                                    Clock test() {
                                        return Clock.system(ZoneOffset.UTC);
                                    }
                                }
                                """,
                        """
                                import java.time.Clock;
                                
                                class Test {
                                    Clock test() {
                                        return Clock.systemUTC();
                                    }
                                }
                                """
                ));
    }

    @Test
    void testUtcConstantRecipe() {
        Recipe recipe = RewriteUtils.loadRecipe("TimeRulesRecipes.UtcConstantRecipe");
        rewriteRun(
                spec -> spec.recipe(recipe)
                        .expectedCyclesThatMakeChanges(1).cycles(1)
                        .parser(JavaParser.fromJavaVersion().classpath(JavaParser.runtimeClasspath())),
                java(
                        """
                                import com.google.common.collect.ImmutableSet;
                                
                                import java.time.ZoneId;
                                import java.time.ZoneOffset;
                                
                                class Test {
                                    ImmutableSet<ZoneId> test() {
                                        return ImmutableSet.of(ZoneId.of("GMT"), ZoneId.of("UTC"), ZoneId.of("+0"), ZoneId.of("-0"), ZoneOffset.UTC, ZoneId.from(ZoneOffset.UTC));
                                    }
                                }
                                """,
                        """
                                import com.google.common.collect.ImmutableSet;
                                
                                import java.time.ZoneId;
                                import java.time.ZoneOffset;
                                
                                class Test {
                                    ImmutableSet<ZoneId> test() {
                                        return ImmutableSet.of(ZoneOffset.UTC, ZoneOffset.UTC, ZoneOffset.UTC, ZoneOffset.UTC, ZoneOffset.UTC, ZoneOffset.UTC);
                                    }
                                }
                                """
                ));
    }

    @Test
    void testZeroDurationRecipe() {
        Recipe recipe = RewriteUtils.loadRecipe("TimeRulesRecipes.ZeroDurationRecipe");
        rewriteRun(
                spec -> spec.recipe(recipe)
                        .expectedCyclesThatMakeChanges(1).cycles(1)
                        .parser(JavaParser.fromJavaVersion().classpath(JavaParser.runtimeClasspath())),
                java(
                        """
                                import com.google.common.collect.ImmutableSet;
                                
                                import java.time.Duration;
                                import java.time.temporal.ChronoUnit;
                                
                                class Test {
                                    ImmutableSet<Duration> test() {
                                        return ImmutableSet.of(Duration.ofNanos(0), Duration.ofMillis(0), Duration.ofSeconds(0), Duration.ofSeconds(0, 0), Duration.ofMinutes(0), Duration.ofHours(0), Duration.ofDays(0), Duration.of(0, ChronoUnit.MILLIS));
                                    }
                                }
                                """,
                        """
                                import com.google.common.collect.ImmutableSet;
                                
                                import java.time.Duration;
                                
                                class Test {
                                    ImmutableSet<Duration> test() {
                                        return ImmutableSet.of(Duration.ZERO, Duration.ZERO, Duration.ZERO, Duration.ZERO, Duration.ZERO, Duration.ZERO, Duration.ZERO, Duration.ZERO);
                                    }
                                }
                                """
                ));
    }

    @Test
    void testZeroPeriodRecipe() {
        Recipe recipe = RewriteUtils.loadRecipe("TimeRulesRecipes.ZeroPeriodRecipe");
        rewriteRun(
                spec -> spec.recipe(recipe)
                        .expectedCyclesThatMakeChanges(1).cycles(1)
                        .parser(JavaParser.fromJavaVersion().classpath(JavaParser.runtimeClasspath())),
                java(
                        """
                                import com.google.common.collect.ImmutableSet;
                                
                                import java.time.Period;
                                
                                class Test {
                                    ImmutableSet<Period> test() {
                                        return ImmutableSet.of(Period.ofDays(0), Period.ofWeeks(0), Period.ofMonths(0), Period.ofYears(0), Period.of(0, 0, 0));
                                    }
                                }
                                """,
                        """
                                import com.google.common.collect.ImmutableSet;
                                
                                import java.time.Period;
                                
                                class Test {
                                    ImmutableSet<Period> test() {
                                        return ImmutableSet.of(Period.ZERO, Period.ZERO, Period.ZERO, Period.ZERO, Period.ZERO);
                                    }
                                }
                                """
                ));
    }

    @Test
    void testZonedDateTimeMinusDaysRecipe() {
        Recipe recipe = RewriteUtils.loadRecipe("TimeRulesRecipes.ZonedDateTimeMinusDaysRecipe");
        rewriteRun(
                spec -> spec.recipe(recipe)
                        .expectedCyclesThatMakeChanges(1).cycles(1)
                        .parser(JavaParser.fromJavaVersion().classpath(JavaParser.runtimeClasspath())),
                java(
                        """
                                import com.google.common.collect.ImmutableSet;
                                
                                import java.time.Instant;
                                import java.time.Period;
                                import java.time.ZoneOffset;
                                import java.time.ZonedDateTime;
                                import java.time.temporal.ChronoUnit;
                                
                                class Test {
                                    static final ZonedDateTime ZONED_DATE_TIME = Instant.EPOCH.atZone(ZoneOffset.UTC);
                                
                                    ImmutableSet<ZonedDateTime> test() {
                                        return ImmutableSet.of(ZONED_DATE_TIME.minus(1L, ChronoUnit.DAYS), ZONED_DATE_TIME.minus(Period.ofDays(1)));
                                    }
                                }
                                """,
                        """
                                import com.google.common.collect.ImmutableSet;
                                
                                import java.time.Instant;
                                import java.time.ZoneOffset;
                                import java.time.ZonedDateTime;
                                
                                class Test {
                                    static final ZonedDateTime ZONED_DATE_TIME = Instant.EPOCH.atZone(ZoneOffset.UTC);
                                
                                    ImmutableSet<ZonedDateTime> test() {
                                        return ImmutableSet.of(ZONED_DATE_TIME.minusDays(1L), ZONED_DATE_TIME.minusDays(1));
                                    }
                                }
                                """
                ));
    }

    @Test
    void testZonedDateTimeMinusHoursRecipe() {
        Recipe recipe = RewriteUtils.loadRecipe("TimeRulesRecipes.ZonedDateTimeMinusHoursRecipe");
        rewriteRun(
                spec -> spec.recipe(recipe)
                        .expectedCyclesThatMakeChanges(1).cycles(1)
                        .parser(JavaParser.fromJavaVersion().classpath(JavaParser.runtimeClasspath())),
                java(
                        """
                                import com.google.common.collect.ImmutableSet;
                                
                                import java.time.Duration;
                                import java.time.Instant;
                                import java.time.ZoneOffset;
                                import java.time.ZonedDateTime;
                                import java.time.temporal.ChronoUnit;
                                
                                class Test {
                                    static final ZonedDateTime ZONED_DATE_TIME = Instant.EPOCH.atZone(ZoneOffset.UTC);
                                
                                    ImmutableSet<ZonedDateTime> test() {
                                        return ImmutableSet.of(ZONED_DATE_TIME.minus(1L, ChronoUnit.HOURS), ZONED_DATE_TIME.minus(Duration.ofHours(1)));
                                    }
                                }
                                """,
                        """
                                import com.google.common.collect.ImmutableSet;
                                
                                import java.time.Instant;
                                import java.time.ZoneOffset;
                                import java.time.ZonedDateTime;
                                
                                class Test {
                                    static final ZonedDateTime ZONED_DATE_TIME = Instant.EPOCH.atZone(ZoneOffset.UTC);
                                
                                    ImmutableSet<ZonedDateTime> test() {
                                        return ImmutableSet.of(ZONED_DATE_TIME.minusHours(1L), ZONED_DATE_TIME.minusHours(1));
                                    }
                                }
                                """
                ));
    }

    @Test
    void testZonedDateTimeMinusMinutesRecipe() {
        Recipe recipe = RewriteUtils.loadRecipe("TimeRulesRecipes.ZonedDateTimeMinusMinutesRecipe");
        rewriteRun(
                spec -> spec.recipe(recipe)
                        .expectedCyclesThatMakeChanges(1).cycles(1)
                        .parser(JavaParser.fromJavaVersion().classpath(JavaParser.runtimeClasspath())),
                java(
                        """
                                import com.google.common.collect.ImmutableSet;
                                
                                import java.time.Duration;
                                import java.time.Instant;
                                import java.time.ZoneOffset;
                                import java.time.ZonedDateTime;
                                import java.time.temporal.ChronoUnit;
                                
                                class Test {
                                    static final ZonedDateTime ZONED_DATE_TIME = Instant.EPOCH.atZone(ZoneOffset.UTC);
                                
                                    ImmutableSet<ZonedDateTime> test() {
                                        return ImmutableSet.of(ZONED_DATE_TIME.minus(1L, ChronoUnit.MINUTES), ZONED_DATE_TIME.minus(Duration.ofMinutes(1)));
                                    }
                                }
                                """,
                        """
                                import com.google.common.collect.ImmutableSet;
                                
                                import java.time.Instant;
                                import java.time.ZoneOffset;
                                import java.time.ZonedDateTime;
                                
                                class Test {
                                    static final ZonedDateTime ZONED_DATE_TIME = Instant.EPOCH.atZone(ZoneOffset.UTC);
                                
                                    ImmutableSet<ZonedDateTime> test() {
                                        return ImmutableSet.of(ZONED_DATE_TIME.minusMinutes(1L), ZONED_DATE_TIME.minusMinutes(1));
                                    }
                                }
                                """
                ));
    }

    @Test
    void testZonedDateTimeMinusMonthsRecipe() {
        Recipe recipe = RewriteUtils.loadRecipe("TimeRulesRecipes.ZonedDateTimeMinusMonthsRecipe");
        rewriteRun(
                spec -> spec.recipe(recipe)
                        .expectedCyclesThatMakeChanges(1).cycles(1)
                        .parser(JavaParser.fromJavaVersion().classpath(JavaParser.runtimeClasspath())),
                java(
                        """
                                import com.google.common.collect.ImmutableSet;
                                
                                import java.time.Instant;
                                import java.time.Period;
                                import java.time.ZoneOffset;
                                import java.time.ZonedDateTime;
                                import java.time.temporal.ChronoUnit;
                                
                                class Test {
                                    static final ZonedDateTime ZONED_DATE_TIME = Instant.EPOCH.atZone(ZoneOffset.UTC);
                                
                                    ImmutableSet<ZonedDateTime> test() {
                                        return ImmutableSet.of(ZONED_DATE_TIME.minus(1L, ChronoUnit.MONTHS), ZONED_DATE_TIME.minus(Period.ofMonths(1)));
                                    }
                                }
                                """,
                        """
                                import com.google.common.collect.ImmutableSet;
                                
                                import java.time.Instant;
                                import java.time.ZoneOffset;
                                import java.time.ZonedDateTime;
                                
                                class Test {
                                    static final ZonedDateTime ZONED_DATE_TIME = Instant.EPOCH.atZone(ZoneOffset.UTC);
                                
                                    ImmutableSet<ZonedDateTime> test() {
                                        return ImmutableSet.of(ZONED_DATE_TIME.minusMonths(1L), ZONED_DATE_TIME.minusMonths(1));
                                    }
                                }
                                """
                ));
    }

    @Test
    void testZonedDateTimeMinusNanosRecipe() {
        Recipe recipe = RewriteUtils.loadRecipe("TimeRulesRecipes.ZonedDateTimeMinusNanosRecipe");
        rewriteRun(
                spec -> spec.recipe(recipe)
                        .expectedCyclesThatMakeChanges(1).cycles(1)
                        .parser(JavaParser.fromJavaVersion().classpath(JavaParser.runtimeClasspath())),
                java(
                        """
                                import com.google.common.collect.ImmutableSet;
                                
                                import java.time.Duration;
                                import java.time.Instant;
                                import java.time.ZoneOffset;
                                import java.time.ZonedDateTime;
                                import java.time.temporal.ChronoUnit;
                                
                                class Test {
                                    static final ZonedDateTime ZONED_DATE_TIME = Instant.EPOCH.atZone(ZoneOffset.UTC);
                                
                                    ImmutableSet<ZonedDateTime> test() {
                                        return ImmutableSet.of(ZONED_DATE_TIME.minus(1L, ChronoUnit.NANOS), ZONED_DATE_TIME.minus(Duration.ofNanos(1)));
                                    }
                                }
                                """,
                        """
                                import com.google.common.collect.ImmutableSet;
                                
                                import java.time.Instant;
                                import java.time.ZoneOffset;
                                import java.time.ZonedDateTime;
                                
                                class Test {
                                    static final ZonedDateTime ZONED_DATE_TIME = Instant.EPOCH.atZone(ZoneOffset.UTC);
                                
                                    ImmutableSet<ZonedDateTime> test() {
                                        return ImmutableSet.of(ZONED_DATE_TIME.minusNanos(1L), ZONED_DATE_TIME.minusNanos(1));
                                    }
                                }
                                """
                ));
    }

    @Test
    void testZonedDateTimeMinusSecondsRecipe() {
        Recipe recipe = RewriteUtils.loadRecipe("TimeRulesRecipes.ZonedDateTimeMinusSecondsRecipe");
        rewriteRun(
                spec -> spec.recipe(recipe)
                        .expectedCyclesThatMakeChanges(1).cycles(1)
                        .parser(JavaParser.fromJavaVersion().classpath(JavaParser.runtimeClasspath())),
                java(
                        """
                                import com.google.common.collect.ImmutableSet;
                                
                                import java.time.Duration;
                                import java.time.Instant;
                                import java.time.ZoneOffset;
                                import java.time.ZonedDateTime;
                                import java.time.temporal.ChronoUnit;
                                
                                class Test {
                                    static final ZonedDateTime ZONED_DATE_TIME = Instant.EPOCH.atZone(ZoneOffset.UTC);
                                
                                    ImmutableSet<ZonedDateTime> test() {
                                        return ImmutableSet.of(ZONED_DATE_TIME.minus(1L, ChronoUnit.SECONDS), ZONED_DATE_TIME.minus(Duration.ofSeconds(1)));
                                    }
                                }
                                """,
                        """
                                import com.google.common.collect.ImmutableSet;
                                
                                import java.time.Instant;
                                import java.time.ZoneOffset;
                                import java.time.ZonedDateTime;
                                
                                class Test {
                                    static final ZonedDateTime ZONED_DATE_TIME = Instant.EPOCH.atZone(ZoneOffset.UTC);
                                
                                    ImmutableSet<ZonedDateTime> test() {
                                        return ImmutableSet.of(ZONED_DATE_TIME.minusSeconds(1L), ZONED_DATE_TIME.minusSeconds(1));
                                    }
                                }
                                """
                ));
    }

    @Test
    void testZonedDateTimeMinusWeeksRecipe() {
        Recipe recipe = RewriteUtils.loadRecipe("TimeRulesRecipes.ZonedDateTimeMinusWeeksRecipe");
        rewriteRun(
                spec -> spec.recipe(recipe)
                        .expectedCyclesThatMakeChanges(1).cycles(1)
                        .parser(JavaParser.fromJavaVersion().classpath(JavaParser.runtimeClasspath())),
                java(
                        """
                                import com.google.common.collect.ImmutableSet;
                                
                                import java.time.Instant;
                                import java.time.Period;
                                import java.time.ZoneOffset;
                                import java.time.ZonedDateTime;
                                import java.time.temporal.ChronoUnit;
                                
                                class Test {
                                    static final ZonedDateTime ZONED_DATE_TIME = Instant.EPOCH.atZone(ZoneOffset.UTC);
                                
                                    ImmutableSet<ZonedDateTime> test() {
                                        return ImmutableSet.of(ZONED_DATE_TIME.minus(1L, ChronoUnit.WEEKS), ZONED_DATE_TIME.minus(Period.ofWeeks(1)));
                                    }
                                }
                                """,
                        """
                                import com.google.common.collect.ImmutableSet;
                                
                                import java.time.Instant;
                                import java.time.ZoneOffset;
                                import java.time.ZonedDateTime;
                                
                                class Test {
                                    static final ZonedDateTime ZONED_DATE_TIME = Instant.EPOCH.atZone(ZoneOffset.UTC);
                                
                                    ImmutableSet<ZonedDateTime> test() {
                                        return ImmutableSet.of(ZONED_DATE_TIME.minusWeeks(1L), ZONED_DATE_TIME.minusWeeks(1));
                                    }
                                }
                                """
                ));
    }

    @Test
    void testZonedDateTimeMinusYearsRecipe() {
        Recipe recipe = RewriteUtils.loadRecipe("TimeRulesRecipes.ZonedDateTimeMinusYearsRecipe");
        rewriteRun(
                spec -> spec.recipe(recipe)
                        .expectedCyclesThatMakeChanges(1).cycles(1)
                        .parser(JavaParser.fromJavaVersion().classpath(JavaParser.runtimeClasspath())),
                java(
                        """
                                import com.google.common.collect.ImmutableSet;
                                
                                import java.time.Instant;
                                import java.time.Period;
                                import java.time.ZoneOffset;
                                import java.time.ZonedDateTime;
                                import java.time.temporal.ChronoUnit;
                                
                                class Test {
                                    static final ZonedDateTime ZONED_DATE_TIME = Instant.EPOCH.atZone(ZoneOffset.UTC);
                                
                                    ImmutableSet<ZonedDateTime> test() {
                                        return ImmutableSet.of(ZONED_DATE_TIME.minus(1L, ChronoUnit.YEARS), ZONED_DATE_TIME.minus(Period.ofYears(1)));
                                    }
                                }
                                """,
                        """
                                import com.google.common.collect.ImmutableSet;
                                
                                import java.time.Instant;
                                import java.time.ZoneOffset;
                                import java.time.ZonedDateTime;
                                
                                class Test {
                                    static final ZonedDateTime ZONED_DATE_TIME = Instant.EPOCH.atZone(ZoneOffset.UTC);
                                
                                    ImmutableSet<ZonedDateTime> test() {
                                        return ImmutableSet.of(ZONED_DATE_TIME.minusYears(1L), ZONED_DATE_TIME.minusYears(1));
                                    }
                                }
                                """
                ));
    }

    @Test
    void testZonedDateTimePlusDaysRecipe() {
        Recipe recipe = RewriteUtils.loadRecipe("TimeRulesRecipes.ZonedDateTimePlusDaysRecipe");
        rewriteRun(
                spec -> spec.recipe(recipe)
                        .expectedCyclesThatMakeChanges(1).cycles(1)
                        .parser(JavaParser.fromJavaVersion().classpath(JavaParser.runtimeClasspath())),
                java(
                        """
                                import com.google.common.collect.ImmutableSet;
                                
                                import java.time.Instant;
                                import java.time.Period;
                                import java.time.ZoneOffset;
                                import java.time.ZonedDateTime;
                                import java.time.temporal.ChronoUnit;
                                
                                class Test {
                                    static final ZonedDateTime ZONED_DATE_TIME = Instant.EPOCH.atZone(ZoneOffset.UTC);
                                
                                    ImmutableSet<ZonedDateTime> test() {
                                        return ImmutableSet.of(ZONED_DATE_TIME.plus(1L, ChronoUnit.DAYS), ZONED_DATE_TIME.plus(Period.ofDays(1)));
                                    }
                                }
                                """,
                        """
                                import com.google.common.collect.ImmutableSet;
                                
                                import java.time.Instant;
                                import java.time.ZoneOffset;
                                import java.time.ZonedDateTime;
                                
                                class Test {
                                    static final ZonedDateTime ZONED_DATE_TIME = Instant.EPOCH.atZone(ZoneOffset.UTC);
                                
                                    ImmutableSet<ZonedDateTime> test() {
                                        return ImmutableSet.of(ZONED_DATE_TIME.plusDays(1L), ZONED_DATE_TIME.plusDays(1));
                                    }
                                }
                                """
                ));
    }

    @Test
    void testZonedDateTimePlusHoursRecipe() {
        Recipe recipe = RewriteUtils.loadRecipe("TimeRulesRecipes.ZonedDateTimePlusHoursRecipe");
        rewriteRun(
                spec -> spec.recipe(recipe)
                        .expectedCyclesThatMakeChanges(1).cycles(1)
                        .parser(JavaParser.fromJavaVersion().classpath(JavaParser.runtimeClasspath())),
                java(
                        """
                                import com.google.common.collect.ImmutableSet;
                                
                                import java.time.Duration;
                                import java.time.Instant;
                                import java.time.ZoneOffset;
                                import java.time.ZonedDateTime;
                                import java.time.temporal.ChronoUnit;
                                
                                class Test {
                                    static final ZonedDateTime ZONED_DATE_TIME = Instant.EPOCH.atZone(ZoneOffset.UTC);
                                
                                    ImmutableSet<ZonedDateTime> test() {
                                        return ImmutableSet.of(ZONED_DATE_TIME.plus(1L, ChronoUnit.HOURS), ZONED_DATE_TIME.plus(Duration.ofHours(1)));
                                    }
                                }
                                """,
                        """
                                import com.google.common.collect.ImmutableSet;
                                
                                import java.time.Instant;
                                import java.time.ZoneOffset;
                                import java.time.ZonedDateTime;
                                
                                class Test {
                                    static final ZonedDateTime ZONED_DATE_TIME = Instant.EPOCH.atZone(ZoneOffset.UTC);
                                
                                    ImmutableSet<ZonedDateTime> test() {
                                        return ImmutableSet.of(ZONED_DATE_TIME.plusHours(1L), ZONED_DATE_TIME.plusHours(1));
                                    }
                                }
                                """
                ));
    }

    @Test
    void testZonedDateTimePlusMinutesRecipe() {
        Recipe recipe = RewriteUtils.loadRecipe("TimeRulesRecipes.ZonedDateTimePlusMinutesRecipe");
        rewriteRun(
                spec -> spec.recipe(recipe)
                        .expectedCyclesThatMakeChanges(1).cycles(1)
                        .parser(JavaParser.fromJavaVersion().classpath(JavaParser.runtimeClasspath())),
                java(
                        """
                                import com.google.common.collect.ImmutableSet;
                                
                                import java.time.Duration;
                                import java.time.Instant;
                                import java.time.ZoneOffset;
                                import java.time.ZonedDateTime;
                                import java.time.temporal.ChronoUnit;
                                
                                class Test {
                                    static final ZonedDateTime ZONED_DATE_TIME = Instant.EPOCH.atZone(ZoneOffset.UTC);
                                
                                    ImmutableSet<ZonedDateTime> test() {
                                        return ImmutableSet.of(ZONED_DATE_TIME.plus(1L, ChronoUnit.MINUTES), ZONED_DATE_TIME.plus(Duration.ofMinutes(1)));
                                    }
                                }
                                """,
                        """
                                import com.google.common.collect.ImmutableSet;
                                
                                import java.time.Instant;
                                import java.time.ZoneOffset;
                                import java.time.ZonedDateTime;
                                
                                class Test {
                                    static final ZonedDateTime ZONED_DATE_TIME = Instant.EPOCH.atZone(ZoneOffset.UTC);
                                
                                    ImmutableSet<ZonedDateTime> test() {
                                        return ImmutableSet.of(ZONED_DATE_TIME.plusMinutes(1L), ZONED_DATE_TIME.plusMinutes(1));
                                    }
                                }
                                """
                ));
    }

    @Test
    void testZonedDateTimePlusMonthsRecipe() {
        Recipe recipe = RewriteUtils.loadRecipe("TimeRulesRecipes.ZonedDateTimePlusMonthsRecipe");
        rewriteRun(
                spec -> spec.recipe(recipe)
                        .expectedCyclesThatMakeChanges(1).cycles(1)
                        .parser(JavaParser.fromJavaVersion().classpath(JavaParser.runtimeClasspath())),
                java(
                        """
                                import com.google.common.collect.ImmutableSet;
                                
                                import java.time.Instant;
                                import java.time.Period;
                                import java.time.ZoneOffset;
                                import java.time.ZonedDateTime;
                                import java.time.temporal.ChronoUnit;
                                
                                class Test {
                                    static final ZonedDateTime ZONED_DATE_TIME = Instant.EPOCH.atZone(ZoneOffset.UTC);
                                
                                    ImmutableSet<ZonedDateTime> test() {
                                        return ImmutableSet.of(ZONED_DATE_TIME.plus(1L, ChronoUnit.MONTHS), ZONED_DATE_TIME.plus(Period.ofMonths(1)));
                                    }
                                }
                                """,
                        """
                                import com.google.common.collect.ImmutableSet;
                                
                                import java.time.Instant;
                                import java.time.ZoneOffset;
                                import java.time.ZonedDateTime;
                                
                                class Test {
                                    static final ZonedDateTime ZONED_DATE_TIME = Instant.EPOCH.atZone(ZoneOffset.UTC);
                                
                                    ImmutableSet<ZonedDateTime> test() {
                                        return ImmutableSet.of(ZONED_DATE_TIME.plusMonths(1L), ZONED_DATE_TIME.plusMonths(1));
                                    }
                                }
                                """
                ));
    }

    @Test
    void testZonedDateTimePlusNanosRecipe() {
        Recipe recipe = RewriteUtils.loadRecipe("TimeRulesRecipes.ZonedDateTimePlusNanosRecipe");
        rewriteRun(
                spec -> spec.recipe(recipe)
                        .expectedCyclesThatMakeChanges(1).cycles(1)
                        .parser(JavaParser.fromJavaVersion().classpath(JavaParser.runtimeClasspath())),
                java(
                        """
                                import com.google.common.collect.ImmutableSet;
                                
                                import java.time.Duration;
                                import java.time.Instant;
                                import java.time.ZoneOffset;
                                import java.time.ZonedDateTime;
                                import java.time.temporal.ChronoUnit;
                                
                                class Test {
                                    static final ZonedDateTime ZONED_DATE_TIME = Instant.EPOCH.atZone(ZoneOffset.UTC);
                                
                                    ImmutableSet<ZonedDateTime> test() {
                                        return ImmutableSet.of(ZONED_DATE_TIME.plus(1L, ChronoUnit.NANOS), ZONED_DATE_TIME.plus(Duration.ofNanos(1)));
                                    }
                                }
                                """,
                        """
                                import com.google.common.collect.ImmutableSet;
                                
                                import java.time.Instant;
                                import java.time.ZoneOffset;
                                import java.time.ZonedDateTime;
                                
                                class Test {
                                    static final ZonedDateTime ZONED_DATE_TIME = Instant.EPOCH.atZone(ZoneOffset.UTC);
                                
                                    ImmutableSet<ZonedDateTime> test() {
                                        return ImmutableSet.of(ZONED_DATE_TIME.plusNanos(1L), ZONED_DATE_TIME.plusNanos(1));
                                    }
                                }
                                """
                ));
    }

    @Test
    void testZonedDateTimePlusSecondsRecipe() {
        Recipe recipe = RewriteUtils.loadRecipe("TimeRulesRecipes.ZonedDateTimePlusSecondsRecipe");
        rewriteRun(
                spec -> spec.recipe(recipe)
                        .expectedCyclesThatMakeChanges(1).cycles(1)
                        .parser(JavaParser.fromJavaVersion().classpath(JavaParser.runtimeClasspath())),
                java(
                        """
                                import com.google.common.collect.ImmutableSet;
                                
                                import java.time.Duration;
                                import java.time.Instant;
                                import java.time.ZoneOffset;
                                import java.time.ZonedDateTime;
                                import java.time.temporal.ChronoUnit;
                                
                                class Test {
                                    static final ZonedDateTime ZONED_DATE_TIME = Instant.EPOCH.atZone(ZoneOffset.UTC);
                                
                                    ImmutableSet<ZonedDateTime> test() {
                                        return ImmutableSet.of(ZONED_DATE_TIME.plus(1L, ChronoUnit.SECONDS), ZONED_DATE_TIME.plus(Duration.ofSeconds(1)));
                                    }
                                }
                                """,
                        """
                                import com.google.common.collect.ImmutableSet;
                                
                                import java.time.Instant;
                                import java.time.ZoneOffset;
                                import java.time.ZonedDateTime;
                                
                                class Test {
                                    static final ZonedDateTime ZONED_DATE_TIME = Instant.EPOCH.atZone(ZoneOffset.UTC);
                                
                                    ImmutableSet<ZonedDateTime> test() {
                                        return ImmutableSet.of(ZONED_DATE_TIME.plusSeconds(1L), ZONED_DATE_TIME.plusSeconds(1));
                                    }
                                }
                                """
                ));
    }

    @Test
    void testZonedDateTimePlusWeeksRecipe() {
        Recipe recipe = RewriteUtils.loadRecipe("TimeRulesRecipes.ZonedDateTimePlusWeeksRecipe");
        rewriteRun(
                spec -> spec.recipe(recipe)
                        .expectedCyclesThatMakeChanges(1).cycles(1)
                        .parser(JavaParser.fromJavaVersion().classpath(JavaParser.runtimeClasspath())),
                java(
                        """
                                import com.google.common.collect.ImmutableSet;
                                
                                import java.time.Instant;
                                import java.time.Period;
                                import java.time.ZoneOffset;
                                import java.time.ZonedDateTime;
                                import java.time.temporal.ChronoUnit;
                                
                                class Test {
                                    static final ZonedDateTime ZONED_DATE_TIME = Instant.EPOCH.atZone(ZoneOffset.UTC);
                                
                                    ImmutableSet<ZonedDateTime> test() {
                                        return ImmutableSet.of(ZONED_DATE_TIME.plus(1L, ChronoUnit.WEEKS), ZONED_DATE_TIME.plus(Period.ofWeeks(1)));
                                    }
                                }
                                """,
                        """
                                import com.google.common.collect.ImmutableSet;
                                
                                import java.time.Instant;
                                import java.time.ZoneOffset;
                                import java.time.ZonedDateTime;
                                
                                class Test {
                                    static final ZonedDateTime ZONED_DATE_TIME = Instant.EPOCH.atZone(ZoneOffset.UTC);
                                
                                    ImmutableSet<ZonedDateTime> test() {
                                        return ImmutableSet.of(ZONED_DATE_TIME.plusWeeks(1L), ZONED_DATE_TIME.plusWeeks(1));
                                    }
                                }
                                """
                ));
    }

    @Test
    void testZonedDateTimePlusYearsRecipe() {
        Recipe recipe = RewriteUtils.loadRecipe("TimeRulesRecipes.ZonedDateTimePlusYearsRecipe");
        rewriteRun(
                spec -> spec.recipe(recipe)
                        .expectedCyclesThatMakeChanges(1).cycles(1)
                        .parser(JavaParser.fromJavaVersion().classpath(JavaParser.runtimeClasspath())),
                java(
                        """
                                import com.google.common.collect.ImmutableSet;
                                
                                import java.time.Instant;
                                import java.time.Period;
                                import java.time.ZoneOffset;
                                import java.time.ZonedDateTime;
                                import java.time.temporal.ChronoUnit;
                                
                                class Test {
                                    static final ZonedDateTime ZONED_DATE_TIME = Instant.EPOCH.atZone(ZoneOffset.UTC);
                                
                                    ImmutableSet<ZonedDateTime> test() {
                                        return ImmutableSet.of(ZONED_DATE_TIME.plus(1L, ChronoUnit.YEARS), ZONED_DATE_TIME.plus(Period.ofYears(1)));
                                    }
                                }
                                """,
                        """
                                import com.google.common.collect.ImmutableSet;
                                
                                import java.time.Instant;
                                import java.time.ZoneOffset;
                                import java.time.ZonedDateTime;
                                
                                class Test {
                                    static final ZonedDateTime ZONED_DATE_TIME = Instant.EPOCH.atZone(ZoneOffset.UTC);
                                
                                    ImmutableSet<ZonedDateTime> test() {
                                        return ImmutableSet.of(ZONED_DATE_TIME.plusYears(1L), ZONED_DATE_TIME.plusYears(1));
                                    }
                                }
                                """
                ));
    }
}
