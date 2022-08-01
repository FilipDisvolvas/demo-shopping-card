package net.sakrak.demoshoppingcart.domain

import javax.persistence.Column

class Order : BaseEntity() {
    @Column
    lateinit var orderNumber: String

}