package net.sakrak.demoshoppingcard.services

import org.springframework.web.servlet.ModelAndView

interface RedirectService {
    fun buildCleanRedirect(path: String) : ModelAndView
}