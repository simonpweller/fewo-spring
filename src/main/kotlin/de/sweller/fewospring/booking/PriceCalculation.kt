package de.sweller.fewospring.booking

import java.time.temporal.ChronoUnit
import kotlin.math.max

fun calculatePrice(booking: Booking): CalculatedPrice {
    val numberOfNights = ChronoUnit.DAYS.between(booking.arrivalDate, booking.departureDate).toInt()
    val totalPrice = calculatePrice(
        numberOfNights,
        booking.numberOfAdults,
        booking.numberOfChildren,
        booking.secondBedroom,
        booking.property
    )
    return CalculatedPrice(totalPrice, totalPrice / numberOfNights)
}

class CalculatedPrice(val totalPrice: Int, val pricePerNight: Int)

fun calculatePrice(numberOfNights: Int, adults: Int, children: Int, extraBedroom: Boolean, property: Property): Int {
    val effectiveNights = max(numberOfNights, 2)
    val pricePerNight = getPricePerNight(numberOfNights, adults, children, property, extraBedroom)
    return effectiveNights * pricePerNight
}

private fun getPricePerNight(
    numberOfNights: Int,
    adults: Int,
    children: Int,
    property: Property,
    extraBedroom: Boolean
): Int {
    val people = adults + children
    val baseChargePerNight = getBaseChargePerNight(numberOfNights, people, property)
    val additionalAdults = max(adults - 2, 0)
    val additionalChildren = if (adults == 1) max(children - 1, 0) else children
    val chargePerAdditionalAdult = 15
    val extraBedroomCharge = if (extraBedroom && people <= 3) 15 else 0
    return baseChargePerNight + (additionalAdults * chargePerAdditionalAdult) + (additionalChildren * 8) + extraBedroomCharge
}

private fun getBaseChargePerNight(numberOfNights: Int, people: Int, property: Property): Int {
    if (people == 1) return getSinglePersonChargePerNight(numberOfNights)
    return when (property) {
        Property.APARTMENT -> if (numberOfNights > 2) 55 else 65
        Property.BUNGALOW -> if (numberOfNights > 2) 65 else 75
    }
}

private fun getSinglePersonChargePerNight(numberOfNights: Int): Int =
    when (numberOfNights) {
        1 -> 55
        2 -> 55
        3 -> 45
        4 -> 45
        else -> 40
    }
