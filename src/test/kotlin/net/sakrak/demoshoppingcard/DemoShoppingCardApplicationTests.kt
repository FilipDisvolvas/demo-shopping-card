package net.sakrak.demoshoppingcard

import net.sakrak.demoshoppingcard.services.RandomService
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest

@SpringBootTest
class DemoShoppingCardApplicationTests {
    @Autowired lateinit var randomService: RandomService
    @Test
    fun contextLoads() {
        println()
    }

}
