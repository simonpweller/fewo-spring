package de.sweller.fewospring.booking

import org.springframework.format.annotation.DateTimeFormat
import java.time.LocalDate

const val DATE_INPUT_FORMAT = "yyyy-MM-dd"

data class BookingData(
        val firstName: String,
        val lastName: String,
        @DateTimeFormat(pattern = DATE_INPUT_FORMAT)
        val arrivalDate: LocalDate,
        @DateTimeFormat(pattern = DATE_INPUT_FORMAT)
        val departureDate: LocalDate,
) {
    fun toBooking() = Booking(
            firstName = this.firstName,
            lastName = this.lastName,
            arrivalDate = this.arrivalDate,
            departureDate = this.departureDate,
    )
}
