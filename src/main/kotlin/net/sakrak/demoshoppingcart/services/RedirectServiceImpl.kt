package net.sakrak.demoshoppingcart.services

import org.springframework.stereotype.Component
import org.springframework.web.servlet.ModelAndView
import org.springframework.web.servlet.view.RedirectView

@Component
class RedirectServiceImpl : RedirectService {
    override fun buildCleanRedirect(path: String): ModelAndView {
        val redirect = RedirectView(path)
        redirect.setExposeModelAttributes(false)
        return ModelAndView(redirect)
    }
}