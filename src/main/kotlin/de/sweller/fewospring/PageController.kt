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

    @GetMapping("/booking-form")
    fun getBookingForm(@RequestParam property: String?, model: Model): String {
        model.addAttribute(if (property == "bungalow") Property.BUNGALOW else Property.APARTMENT)
        return "booking-form"
    }

    @GetMapping("/booking-confirmation")
    fun getBookingConfirmation() = "booking-confirmation"

    @GetMapping("/directions")
    fun getDirections(model: Model): String {
        model.addAttribute("mapboxKey", mapboxKey)
        return "directions"
    }

    @GetMapping("/apartment")
    fun getApartmentDetail() = "apartment"

    @GetMapping("/bungalow")
    fun getBungalowDetail() = "bungalow"

    @GetMapping("/imprint")
    fun getImprint() = "imprint"

    @GetMapping("/privacy")
    fun getPrivacy() = "privacy"
}
