package net.sakrak.demoshoppingcard.bootstrap

import org.slf4j.LoggerFactory
import org.springframework.context.ApplicationListener
import org.springframework.context.event.ContextRefreshedEvent
import org.springframework.core.env.ConfigurableEnvironment
import org.springframework.stereotype.Component

@Component
class ProfileCheck(private val env: ConfigurableEnvironment) : ApplicationListener<ContextRefreshedEvent> {
    override fun onApplicationEvent(event: ContextRefreshedEvent) {
        if (env.activeProfiles.isEmpty()) {
            LoggerFactory.getLogger(this::class.java).error("\u001B[31mEs sind keine Konfigurations-Profile gesetzt!\u001B[0m")
        }
    }
}