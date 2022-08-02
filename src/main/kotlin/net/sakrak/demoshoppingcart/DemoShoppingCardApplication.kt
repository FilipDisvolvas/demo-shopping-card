package net.sakrak.demoshoppingcart

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class DemoShoppingCardApplication

fun main(args: Array<String>) {
    val foo = runApplication<DemoShoppingCardApplication>(*args)
}
