package de.sweller.fewospring.admin

import de.sweller.fewospring.booking.BookingService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.*
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

    @PostMapping("/{id}/confirm")
    fun confirmBooking(@PathVariable id: Long): ResponseEntity<Void> {
        return if (bookingService.confirm(id)) {
            ResponseEntity.noContent().build()
        } else {
            throw ResponseStatusException(HttpStatus.NOT_FOUND)
        }
    }
}
