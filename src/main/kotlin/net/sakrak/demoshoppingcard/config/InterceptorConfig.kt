package net.sakrak.demoshoppingcard.config

import net.sakrak.demoshoppingcard.controllers.CustomerLoginInterceptor
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Configuration
import org.springframework.web.servlet.config.annotation.InterceptorRegistry
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter


@Configuration
class InterceptorConfig : WebMvcConfigurerAdapter() {
    override fun addInterceptors(registry: InterceptorRegistry) {
        registry.addInterceptor(CustomerLoginInterceptor())
    }
}