package de.sweller.fewospring

import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping

@Controller
@RequestMapping("/bookings")
class BookingsController {

    @GetMapping
    fun getBookingForm() = "bookings"

    @PostMapping
    fun createBooking() {
        println("create booking")
    }
}
