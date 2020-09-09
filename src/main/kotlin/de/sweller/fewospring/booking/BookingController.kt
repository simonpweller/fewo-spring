package de.sweller.fewospring.booking

import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping

@Controller
@RequestMapping("/bookings")
class BookingController(
        val bookingService: BookingService,
) {
    @PostMapping
    fun createBooking(bookingData: BookingData): String {
        bookingService.addBooking(bookingData)
        return "redirect:/"
    }
}
