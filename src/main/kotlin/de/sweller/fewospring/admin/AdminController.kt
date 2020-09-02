package de.sweller.fewospring.admin

import de.sweller.fewospring.booking.BookingService
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping

@RequestMapping("/admin/bookings")
@Controller
class AdminController(
        val bookingService: BookingService,
) {

    @GetMapping
    fun getBookings(model: Model): String {
        model.addAttribute("bookings", bookingService.getBookings())
        return "admin"
    }
}
