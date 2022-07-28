package net.sakrak.demoshoppingcard.config

import net.sakrak.demoshoppingcard.controllers.CustomerLoginInterceptor
import net.sakrak.demoshoppingcard.controllers.CustomerLoginInterceptorImpl
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