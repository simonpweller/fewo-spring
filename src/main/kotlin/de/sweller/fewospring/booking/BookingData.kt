package de.sweller.fewospring.booking

import org.springframework.format.annotation.DateTimeFormat
import java.time.LocalDate

const val DATE_INPUT_FORMAT = "yyyy-MM-dd"

data class BookingData(
        val firstName: String,
        val lastName: String,
        val streetLine: String,
        val city: String,
        val zipCode: String,
        val email: String,
        val phoneNumber: String,
        @DateTimeFormat(pattern = DATE_INPUT_FORMAT)
        val arrivalDate: LocalDate,
        @DateTimeFormat(pattern = DATE_INPUT_FORMAT)
        val departureDate: LocalDate,
        val property: Property,
        val comments: String,
) {
    fun toBooking() = Booking(
            firstName = this.firstName,
            lastName = this.lastName,
            streetLine = this.streetLine,
            city = this.city,
            zipCode = this.zipCode,
            email = this.email,
            phoneNumber = this.phoneNumber,
            arrivalDate = this.arrivalDate,
            departureDate = this.departureDate,
            property = this.property,
            comments = this.comments,
    )
}
