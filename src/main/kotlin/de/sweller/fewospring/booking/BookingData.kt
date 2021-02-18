package de.sweller.fewospring.booking

import org.springframework.format.annotation.DateTimeFormat
import java.time.LocalDate
import java.util.*

const val DATE_INPUT_FORMAT = "yyyy-MM-dd"

data class BookingData(
        val id: Long? = null,
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
        val numberOfAdults: Int,
        val numberOfChildren: Int,
        val secondBedroom: Boolean?,
        val comments: String,
        val locale: Locale,
) {
    fun toBooking() = Booking(
            id = this.id,
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
            numberOfAdults = this.numberOfAdults,
            numberOfChildren = this.numberOfChildren,
            secondBedroom = this.secondBedroom ?: false,
            comments = this.comments,
            locale = this.locale,
    )
}
