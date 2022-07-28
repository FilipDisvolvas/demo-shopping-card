package net.sakrak.demoshoppingcard.services

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.mock.web.MockHttpSession
import org.springframework.stereotype.Component
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.ResultActions
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder

@Component
class SessionAwareMockMvcImpl(@Autowired private var mockMvc: MockMvc?) : SessionAwareMockMvc {
    private var previousResultActions: ResultActions? = null

    override fun perform(requestBuilder : MockHttpServletRequestBuilder) : ResultActions
    {
        val session = previousResultActions?.andReturn()?.request?.session as MockHttpSession?

        previousResultActions = mockMvc!!.perform(if (session != null) {
            requestBuilder.session(session)
        } else {
            requestBuilder
        })

        return previousResultActions!!
    }
}