package de.sweller.fewospring.booking

import java.time.DayOfWeek
import java.time.YearMonth
import java.time.temporal.TemporalAdjusters

data class AvailabilityMonth(val month: Int, val year: Int, val weeks: List<List<AvailabilityDate>>)
data class AvailabilityDate(val dayOfMonth: Int, val isInCurrentMonth: Boolean, val isBooked: Boolean = false)

fun availabilityFor(month: Int, year: Int): AvailabilityMonth {
    val monthStart = YearMonth.of(year, month).atDay(1)
    val monthEnd = YearMonth.of(year, month).atEndOfMonth()
    val calendarStart = monthStart.with(TemporalAdjusters.previousOrSame(DayOfWeek.MONDAY))
    val calendarEnd = monthEnd.with(TemporalAdjusters.nextOrSame(DayOfWeek.SUNDAY))
    val weeks = generateSequence(calendarStart) { it.plusDays(1) }
            .takeWhile { !it.isAfter(calendarEnd) }.toList()
            .map { AvailabilityDate(it.dayOfMonth, it.monthValue == month) }
            .chunked(7)
    return AvailabilityMonth(month, year, weeks)
}
