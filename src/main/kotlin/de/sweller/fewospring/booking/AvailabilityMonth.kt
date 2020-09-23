package de.sweller.fewospring.booking

import java.time.DayOfWeek
import java.time.LocalDate
import java.time.YearMonth
import java.time.temporal.TemporalAdjusters

data class AvailabilityMonth(val yearMonth: YearMonth, val weeks: List<List<AvailabilityDate>>)
data class AvailabilityDate(val dayOfMonth: Int, val isInCurrentMonth: Boolean, val isBooked: Boolean = false)

fun availabilityFor(yearMonth: YearMonth, bookedDates: Set<LocalDate>): AvailabilityMonth {
    val monthStart = yearMonth.atDay(1)
    val monthEnd = yearMonth.atEndOfMonth()
    val calendarStart = monthStart.with(TemporalAdjusters.previousOrSame(DayOfWeek.MONDAY))
    val calendarEnd = monthEnd.with(TemporalAdjusters.nextOrSame(DayOfWeek.SUNDAY))
    val weeks = generateSequence(calendarStart) { it.plusDays(1) }
            .takeWhile { !it.isAfter(calendarEnd) }.toList()
            .map { AvailabilityDate(it.dayOfMonth, YearMonth.from(it) == yearMonth, isBooked = bookedDates.contains(it)) }
            .chunked(7)
    return AvailabilityMonth(yearMonth, weeks)
}
