package de.sweller.fewospring.booking

import org.springframework.data.repository.CrudRepository
import java.time.LocalDate
import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.Id

interface BookingRepository: CrudRepository<Booking, Long>

@Entity
class Booking(
        var firstName: String,
        var lastName: String,
        var arrivalDate: LocalDate,
        var departureDate: LocalDate,
        @Id @GeneratedValue var id: Long? = null,
)
