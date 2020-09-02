package de.sweller.fewospring.admin

import de.sweller.fewospring.booking.BookingService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.server.ResponseStatusException

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

    @DeleteMapping("/{id}")
    fun deleteBooking(@PathVariable id: Long): ResponseEntity<Void> {
        return if (bookingService.delete(id)) {
            ResponseEntity.noContent().build()
        } else {
            throw ResponseStatusException(HttpStatus.NOT_FOUND)
        }
    }
}
