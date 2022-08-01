package net.sakrak.demoshoppingcart.services

import org.junit.jupiter.api.Test


class RandomServiceTest {

    @Test
    fun testNextString() {
        val service = RandomServiceImpl()
        println(service.nextString(15))
    }
}