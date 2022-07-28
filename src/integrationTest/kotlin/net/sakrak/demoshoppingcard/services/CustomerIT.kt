package net.sakrak.demoshoppingcard.services

import net.sakrak.demoshoppingcard.bootstrap.DatabaseBootstrap
import org.hamcrest.Matchers.containsString
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.mockito.Mockito.mock
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.context.event.ContextRefreshedEvent
import org.springframework.mock.web.MockHttpSession
import org.springframework.test.context.ActiveProfiles
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.*


@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles(value = ["test"])
class CustomerIT {
    @Autowired
    private lateinit var mockMvc: MockMvc

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
        val session = mockMvc.perform(get("/")).andExpect(status().isOk)
            .andExpect(xpath("//form[@action='/customers/login']").exists())
            .andReturn().request.session as MockHttpSession

        mockMvc.perform(get("/customers/registration")).andExpect(status().isOk)

        val createCustomerRequest = post("/customers/registration")
            .session(session)
            .param("firstName", "First")
            .param("middleName", "")
            .param("lastName", "Last")
            .param("email", "test@invalid.foo")
            .param("addressFirstLine", "Example Avenue 42")
            .param("addressSecondLine", "42420 Entenhausen")
            .param("addressThirdLine", "")
            .param("password", "123456")
            .param("passwordRepitition", "123456")

        mockMvc.perform(createCustomerRequest).andExpect(status().is3xxRedirection)

        val loginCustomerRequest = post("/customers/login")
            .session(session)
            .param("email", "test@invalid.foo")
            .param("password", "123456")

        mockMvc.perform(get("/").session(session))
            .andExpect(xpath("//form[@action='/customers/login']").doesNotExist())
            .andExpect(content().string(containsString("Hallo, <span>First</span>!")))
    }
}