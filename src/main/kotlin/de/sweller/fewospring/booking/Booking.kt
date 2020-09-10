package de.sweller.fewospring.booking

import org.springframework.data.repository.CrudRepository
import java.time.LocalDate
import java.util.*
import javax.persistence.*

interface BookingRepository: CrudRepository<Booking, Long>

@Entity
class Booking(
        var firstName: String,
        var lastName: String,
        var streetLine: String,
        var city: String,
        var zipCode: String,
        var email: String,
        var phoneNumber: String,
        var arrivalDate: LocalDate,
        var departureDate: LocalDate,
        @Enumerated(EnumType.STRING) var property: Property,
        @Lob var comments: String,
        var confirmed: Boolean = false,
        var locale: Locale,
        @Id @GeneratedValue var id: Long? = null,
)
