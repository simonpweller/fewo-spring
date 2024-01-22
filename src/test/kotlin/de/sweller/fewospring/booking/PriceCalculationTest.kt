package de.sweller.fewospring.booking

import de.sweller.fewospring.booking.Property.APARTMENT
import de.sweller.fewospring.booking.Property.BUNGALOW
import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Nested

internal class PriceCalculationTest {

    @Nested
    @DisplayName("Apartment")
    inner class Apartment {

        @Test
        fun `should use a base price of 45 EUR per night for 1 person`() {
            assertEquals(135, calculatePrice(3, 1, 0, false, APARTMENT))
            assertEquals(180, calculatePrice(4, 1, 0, false, APARTMENT))
        }

        @Test
        fun `should use a base price of 40 EUR per night for 1 person, staying 5 nights or more`() {
            assertEquals(200, calculatePrice(5, 1, 0, false, APARTMENT))
            assertEquals(240, calculatePrice(6, 1, 0, false, APARTMENT))
        }

        @Test
        fun `should use a base price of 55 EUR per night for 1 person, staying 2 nights only`() {
            assertEquals(110, calculatePrice(2, 1, 0, false, APARTMENT))
        }

        @Test
        fun `should use a base price of 65 EUR per night for 2 people`() {
            assertEquals(130, calculatePrice(2, 2, 0, false, APARTMENT))
        }

        @Test
        fun `should use a base price of 55 EUR per night for 2 people staying 3 nights or more`() {
            assertEquals(165, calculatePrice(3, 2, 0, false, APARTMENT))
            assertEquals(220, calculatePrice(4, 2, 0, false, APARTMENT))
        }

        @Test
        fun `should add 15 EUR per night for an additional person`() {
            assertEquals(210, calculatePrice(3, 3, 0, false, APARTMENT))
            assertEquals(280, calculatePrice(4, 3, 0, false, APARTMENT))
        }

        @Test
        fun `should add 8 EUR per night for a child`() {
            assertEquals(189, calculatePrice(3, 2, 1, false, APARTMENT))
            assertEquals(252, calculatePrice(4, 2, 1, false, APARTMENT))
        }

        @Test
        fun `should treat 1 adult and 1 child like 2 adults`() {
            assertEquals(130, calculatePrice(2, 1, 1, false, APARTMENT))
            assertEquals(165, calculatePrice(3, 1, 1, false, APARTMENT))
        }

        @Test
        fun `should correctly enforce the minimum number of nights`() {
            assertEquals(110, calculatePrice(1, 1, 0, false, APARTMENT))

            assertEquals(130, calculatePrice(1, 2, 0, false, APARTMENT))
            assertEquals(130, calculatePrice(1, 1, 1, false, APARTMENT))
            assertEquals(146, calculatePrice(1, 1, 2, false, APARTMENT))
            assertEquals(160, calculatePrice(1, 3, 0, false, APARTMENT))
        }
    }

    @Nested
    @DisplayName("Bungalow")
    inner class Bungalow {

        @Test
        fun `should use a base price of 75 EUR per night for 2 people`() {
            assertEquals(150, calculatePrice(2, 2, 0, false, BUNGALOW))
        }

        @Test
        fun `should use a base price of 65 EUR per night for 2 people staying 3 nights or more`() {
            assertEquals(195, calculatePrice(3, 2, 0, false, BUNGALOW))
            assertEquals(260, calculatePrice(4, 2, 0, false, BUNGALOW))
        }

        @Test
        fun `should add 15 EUR per night for an additional person`() {
            assertEquals(240, calculatePrice(3, 3, 0, false, BUNGALOW))
            assertEquals(320, calculatePrice(4, 3, 0, false, BUNGALOW))

            assertEquals(285, calculatePrice(3, 4, 0, false, BUNGALOW))
            assertEquals(380, calculatePrice(4, 4, 0, false, BUNGALOW))

            assertEquals(330, calculatePrice(3, 5, 0, false, BUNGALOW))
            assertEquals(440, calculatePrice(4, 5, 0, false, BUNGALOW))
        }

        @Test
        fun `should add 8 EUR per night for a child`() {
            assertEquals(219, calculatePrice(3, 2, 1, false, BUNGALOW))
            assertEquals(292, calculatePrice(4, 2, 1, false, BUNGALOW))
        }

        @Test
        fun `should treat 1 adult and 1 child like 2 adults`() {
            assertEquals(150, calculatePrice(2, 1, 1, false, BUNGALOW))
            assertEquals(195, calculatePrice(3, 1, 1, false, BUNGALOW))
        }

        @Test
        fun `should correctly enforce the minimum number of nights`() {
            assertEquals(150, calculatePrice(1, 2, 0, false, BUNGALOW))
            assertEquals(150, calculatePrice(1, 1, 1, false, BUNGALOW))
        }

        @Test
        fun `should add 15 EUR per night for the extra bedroom for up to 3 people`() {
            assertEquals(180, calculatePrice(2, 2, 0, true, BUNGALOW))
            assertEquals(285, calculatePrice(3, 3, 0, true, BUNGALOW))
        }

        @Test
        fun `should not add an extra charge for the extra bedroom for more than 3 people`() {
            assertEquals(182, calculatePrice(2, 2, 2, true, BUNGALOW))
            assertEquals(285, calculatePrice(3, 4, 0, true, BUNGALOW))
        }
    }
}
