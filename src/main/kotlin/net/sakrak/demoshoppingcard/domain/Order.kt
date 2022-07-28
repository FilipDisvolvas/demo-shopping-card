package net.sakrak.demoshoppingcard.domain

import javax.persistence.Column

class Order : BaseEntity() {
    @Column
    lateinit var orderNumber: String

}