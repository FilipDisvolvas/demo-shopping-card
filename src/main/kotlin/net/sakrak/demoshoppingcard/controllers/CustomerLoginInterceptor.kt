package net.sakrak.demoshoppingcard.controllers

import net.sakrak.demoshoppingcard.commands.LoginCommand
import net.sakrak.demoshoppingcard.services.BasketService
import net.sakrak.demoshoppingcard.services.CustomerService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.lang.Nullable
import org.springframework.stereotype.Component
import org.springframework.web.servlet.HandlerInterceptor
import org.springframework.web.servlet.ModelAndView
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse


@Component
class CustomerLoginInterceptor : HandlerInterceptor {
    @Autowired
    lateinit var customerService: CustomerService

    @Autowired
    lateinit var basketService: BasketService

    override fun postHandle(
        request: HttpServletRequest,
        response: HttpServletResponse,
        handler: Any,
        @Nullable modelAndView: ModelAndView?
    ) {
        if (modelAndView == null || modelAndView.model == null) {
            return
        }

        val customerId : Long?  = request.session.getAttribute("customerId") as Long?

        if (customerId != null) {
            val customer = customerService.findById(customerId)
            val basketEntries = basketService.getBasketEntries(customerId)
            modelAndView!!.model["greetingCustomerName"] = customer!!.firstName
            modelAndView.model["greetingBasketEntries"] = basketEntries.sumOf { it.quantity }
        } else {
            modelAndView!!.model["loginCommand"] = LoginCommand(email = null, password = null)
        }
    }
}