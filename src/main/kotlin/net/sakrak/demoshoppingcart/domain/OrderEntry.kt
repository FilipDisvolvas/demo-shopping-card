package net.sakrak.demoshoppingcart.domain

import javax.persistence.ManyToOne

class OrderEntry : BaseEntity() {
    @ManyToOne
    lateinit var order: Order

    @ManyToOne
    lateinit var product: Product
}