package de.sweller.fewospring

import de.sweller.fewospring.booking.Property
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestParam

@Controller
class PageController {

    @Value("\${MAPBOX_KEY}")
    lateinit var mapboxKey: String

    @GetMapping("/")
    fun getHome() = "index"

    @GetMapping("/buchen")
    fun getBookingForm(@RequestParam unterkunft: String?, model: Model): String {
        model.addAttribute(if (unterkunft == "ferienhaus") Property.BUNGALOW else Property.APARTMENT)
        return "booking-form"
    }

    @GetMapping("/buchungsbestaetigung")
    fun getBookingConfirmation() = "booking-confirmation"

    @GetMapping("/anfahrt")
    fun getDirections(model: Model): String {
        model.addAttribute("mapboxKey", mapboxKey)
        return "directions"
    }

    @GetMapping("/ferienwohnung")
    fun getApartmentDetail() = "apartment"

    @GetMapping("/ferienhaus")
    fun getBungalowDetail() = "bungalow"

    @GetMapping("/impressum")
    fun getImprint() = "imprint"

    @GetMapping("/datenschutz")
    fun getPrivacy() = "privacy"
}
