package de.sweller.fewospring.booking

import org.springframework.http.HttpStatus
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.*
import org.springframework.web.server.ResponseStatusException
import org.springframework.web.servlet.mvc.support.RedirectAttributes
import java.time.LocalDate
import java.time.YearMonth
import java.time.format.DateTimeFormatter
import java.util.*

@Controller
@RequestMapping("/buchungen")
class BookingController(
        val bookingService: BookingService,
) {
    @PostMapping
    fun createBooking(bookingData: BookingData, locale: Locale, redirectAttributes: RedirectAttributes): String {
        val booking = bookingService.requestBooking(bookingData)
        val dateTimeFormatter = DateTimeFormatter.ofPattern(if (booking.locale == Locale.ENGLISH) "dd/MM/yyyy" else "dd.MM.yyyy")
        redirectAttributes.addFlashAttribute("isBungalow", booking.property == Property.BUNGALOW)
        redirectAttributes.addFlashAttribute("arrivalDate", booking.arrivalDate.format(dateTimeFormatter))
        redirectAttributes.addFlashAttribute("departureDate", booking.departureDate.format(dateTimeFormatter))
        return "redirect:/buchungsbestaetigung"
    }

    @GetMapping
    fun getBookedDates(
            @RequestParam month: Int?,
            @RequestParam year: Int?,
            @RequestParam property: Property?,
            model: Model,
            locale: Locale
    ): String {
        val currentYearMonth = YearMonth.of(LocalDate.now().year, LocalDate.now().monthValue)
        val yearMonth = YearMonth.of(year ?: LocalDate.now().year, month ?: LocalDate.now().monthValue)
        if (yearMonth < currentYearMonth) throw ResponseStatusException(HttpStatus.UNAUTHORIZED)
        val bookedDates = bookingService.getBookedDates(yearMonth, property ?: Property.APARTMENT)
        val availability = availabilityFor(yearMonth, bookedDates)
        val dateTimeFormatter = DateTimeFormatter.ofPattern("MMMM yyyy", locale)
        model.addAttribute("availability", availability)
        model.addAttribute("currentYearMonth", yearMonth.format(dateTimeFormatter))
        model.addAttribute("previousYearMonth", yearMonth.minusMonths(1).format(dateTimeFormatter))
        model.addAttribute("nextYearMonth", yearMonth.plusMonths(1).format(dateTimeFormatter))
        model.addAttribute("showPreviousMonth", yearMonth > currentYearMonth)
        return "calendar"
    }
}
