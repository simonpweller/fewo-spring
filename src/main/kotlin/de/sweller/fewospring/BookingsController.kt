package de.sweller.fewospring

import org.springframework.format.annotation.DateTimeFormat
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import java.time.LocalDate

const val DATE_INPUT_FORMAT = "yyyy-MM-dd"

@Controller
@RequestMapping
class BookingsController {

    private var bookings = listOf<Booking>()

    @GetMapping("/booking-form")
    fun getBookingForm() = "booking-form"

    @GetMapping("/bookings")
    fun getBookings(model: Model): String {
        model.addAttribute("bookings", bookings)
        return "bookings"
    }

    @PostMapping("/bookings")
    fun createBooking(booking: Booking): String {
        bookings = bookings + booking
        return "redirect:/bookings"
    }
}

data class Booking(
        val firstName: String,
        val lastName: String,
        @DateTimeFormat(pattern = DATE_INPUT_FORMAT)
        val arrivalDate: LocalDate,
        @DateTimeFormat(pattern = DATE_INPUT_FORMAT)
        val departureDate: LocalDate,
)
