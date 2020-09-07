package de.sweller.fewospring.booking

import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping

@Controller
@RequestMapping
class BookingController(
        val bookingService: BookingService,
) {

    @GetMapping("/booking-form")
    fun getBookingForm() = "booking-form"

    @GetMapping("/bookings")
    fun getBookings(model: Model): String {
        model.addAttribute("bookings", bookingService.getBookings())
        return "bookings"
    }

    @PostMapping("/bookings")
    fun createBooking(bookingData: BookingData): String {
        bookingService.addBooking(bookingData)
        return "redirect:/bookings"
    }
}
