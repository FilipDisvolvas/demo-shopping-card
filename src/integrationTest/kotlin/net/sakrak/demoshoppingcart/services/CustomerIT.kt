package net.sakrak.demoshoppingcart.services

import net.sakrak.demoshoppingcart.bootstrap.DatabaseBootstrap
import net.sakrak.demoshoppingcart.test.AbstractIntegrationTest
import org.hamcrest.Matchers.containsString
import org.hamcrest.Matchers.not
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.mockito.Mockito.mock
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.context.event.ContextRefreshedEvent
import org.springframework.test.context.ActiveProfiles
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.*


@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles(value = ["test"])
class CustomerIT : AbstractIntegrationTest() {
    @Autowired
    private lateinit var mockMvc: SessionAwareMockMvc

    @Autowired
    private lateinit var productService: ProductService

    @Autowired
    private lateinit var customerService: CustomerService

    @BeforeEach
    fun setup() {
        DatabaseBootstrap(productService, customerService).onApplicationEvent(mock(ContextRefreshedEvent::class.java))
    }

    @Test
    fun createAndLogin() {
        mockMvc.perform(get("/")).andExpect(status().isOk)
            .andExpect(xpath("//form[@action='/customers/login']").exists())

        mockMvc.perform(get("/customers/registration")).andExpect(status().isOk)

        val invalidCreateCustomerRequest = customerFormDataWithoutFirstName(post("/customers/registration"))
        mockMvc
            .perform(invalidCreateCustomerRequest)
            .andExpect(renderedErrorFlashMessage().exists())
            .andExpect(status().isOk)

        val createCustomerRequest = customerFormDataWithoutFirstName(post("/customers/registration"))
            .param("firstName", "First")
        mockMvc
            .perform(createCustomerRequest)
            .andExpect(flash().attributeExists("successFlash"))
            .andExpect(status().is3xxRedirection)

        val invalidLoginRequest1 = post("/customers/login")
            .param("email", "test@invalid.foo")
            .param("password", "")
        mockMvc
            .perform(invalidLoginRequest1)
            .andExpect(flash().attributeExists("errorFlash"))
            .andExpect(status().is3xxRedirection)

        val invalidLoginRequest2 = post("/customers/login")
            .param("email", "test@invalid.foo")
            .param("password", "dsflkhsdflkdhsf")
        mockMvc
            .perform(invalidLoginRequest2)
            .andExpect(flash().attributeExists("errorFlash"))
            .andExpect(status().is3xxRedirection)

        val validLoginRequest = post("/customers/login")
            .param("email", "test@invalid.foo")
            .param("password", "123456")
        mockMvc.perform(validLoginRequest).andExpect(status().is3xxRedirection)

        mockMvc.perform(get("/"))
            .andExpect(xpath("//form[@action='/customers/login']").doesNotExist())
            .andExpect(content().string(containsString("Hallo, <span>First</span>!")))

        mockMvc.perform(get("/customers/edit"))
            .andExpect(xpath("//form[@action='/customers/edit']").exists())

        mockMvc
            .perform(
                customerFormDataWithoutFirstName(put("/customers/edit"))
                    .param("firstName", "Foobar")
            )
            .andExpect(flash().attributeExists("successFlash"))
            .andExpect(status().is3xxRedirection)

        mockMvc.perform(get("/"))
            .andExpect(content().string(containsString("Hallo, <span>Foobar</span>!")))

        mockMvc.perform(get("/customers/logout"))
            .andExpect(flash().attributeExists("successFlash"))
            .andExpect(status().is3xxRedirection)

        mockMvc.perform(get("/"))
            .andExpect(xpath("//form[@action='/customers/login']").exists())
            .andExpect(content().string(not(containsString("Hallo, <span>Foobar</span>!"))))

    }

    private fun customerFormDataWithoutFirstName(mock: MockHttpServletRequestBuilder): MockHttpServletRequestBuilder {
        return mock
            .param("middleName", "")
            .param("lastName", "Last")
            .param("email", "test@invalid.foo")
            .param("addressFirstLine", "Example Avenue 42")
            .param("addressSecondLine", "42420 Entenhausen")
            .param("addressThirdLine", "")
            .param("password", "123456")
            .param("passwordRepitition", "123456")
    }
}