package de.sweller.fewospring.booking

import org.springframework.stereotype.Service

@Service
class BookingService(
    val bookingRepository: BookingRepository,
) {
    fun getBookings(): MutableIterable<Booking> = bookingRepository.findAll()

    fun addBooking(bookingData: BookingData) {
        bookingRepository.save(bookingData.toBooking())
    }
}
