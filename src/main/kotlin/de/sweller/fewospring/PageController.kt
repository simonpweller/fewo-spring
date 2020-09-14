package de.sweller.fewospring

import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.GetMapping

@Controller
class PageController {

    @Value("\${MAPBOX_KEY}")
    lateinit var mapboxKey: String

    @GetMapping("/")
    fun getHome() = "index"

    @GetMapping("/booking-form")
    fun getBookingForm() = "booking-form"

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
}
