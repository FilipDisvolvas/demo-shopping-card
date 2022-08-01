package net.sakrak.demoshoppingcart.config

import net.sakrak.demoshoppingcart.controllers.CustomerLoginInterceptor
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Configuration
import org.springframework.web.servlet.config.annotation.InterceptorRegistry
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter


@Configuration
class InterceptorConfig : WebMvcConfigurerAdapter() {
    @Autowired
    lateinit var customerLoginInterceptor: CustomerLoginInterceptor

    override fun addInterceptors(registry: InterceptorRegistry) {
        registry.addInterceptor(customerLoginInterceptor)
    }
}