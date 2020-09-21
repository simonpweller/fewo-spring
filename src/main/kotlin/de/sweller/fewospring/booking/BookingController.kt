package de.sweller.fewospring.booking

import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.*
import org.springframework.web.servlet.mvc.support.RedirectAttributes
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.*

@Controller
@RequestMapping("/bookings")
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
        return "redirect:/booking-confirmation"
    }

    @GetMapping
    @ResponseBody
    fun getBookedDates(
            @RequestParam month: Int?,
            @RequestParam year: Int?,
    ): AvailabilityMonth = availabilityFor(month ?: LocalDate.now().monthValue, year ?: LocalDate.now().year)
}
