package net.sakrak.demoshoppingcart.services

import org.springframework.test.web.servlet.ResultActions
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder

interface SessionAwareMockMvc {
    fun perform(requestBuilder: MockHttpServletRequestBuilder): ResultActions
}