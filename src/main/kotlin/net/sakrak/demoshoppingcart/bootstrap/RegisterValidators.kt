package net.sakrak.demoshoppingcart.bootstrap

import net.sakrak.demoshoppingcart.validations.MatchField
import net.sakrak.demoshoppingcart.validations.MatchFieldValidator
import org.hibernate.validator.HibernateValidator
import org.hibernate.validator.HibernateValidatorConfiguration
import org.springframework.context.ApplicationListener
import org.springframework.context.event.ContextRefreshedEvent
import org.springframework.stereotype.Component
import javax.validation.Validation


@Component
class RegisterValidators : ApplicationListener<ContextRefreshedEvent> {
    override fun onApplicationEvent(event: ContextRefreshedEvent) {
        val configuration: HibernateValidatorConfiguration = Validation
            .byProvider(HibernateValidator::class.java)
            .configure()

        val constraintMapping = configuration.createConstraintMapping()

        constraintMapping
            .constraintDefinition(MatchField::class.java)
            .includeExistingValidators(true)
            .validatedBy(MatchFieldValidator::class.java)

        configuration.addMapping(constraintMapping)
    }
}