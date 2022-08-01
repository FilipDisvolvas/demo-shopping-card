package net.sakrak.demoshoppingcart.test

import org.springframework.test.web.servlet.result.MockMvcResultMatchers
import org.springframework.test.web.servlet.result.XpathResultMatchers

abstract class AbstractIntegrationTest {
    protected fun renderedErrorFlashMessage() : XpathResultMatchers =
        MockMvcResultMatchers.xpath("//div[contains(@class,'alert-danger')]")
}
