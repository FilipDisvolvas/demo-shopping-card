package net.sakrak.demoshoppingcard.domain

import javax.persistence.Column
import javax.persistence.ManyToOne

class OrderEntry : BaseEntity() {
    @ManyToOne
    lateinit var order: Order

    @ManyToOne
    lateinit var product: Product
}