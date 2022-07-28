package net.sakrak.demoshoppingcard.domain

import javax.persistence.*

@Entity
@Table(name = "baskets", /*indexes = [Index(columnList = "customer_id")] */)
class Basket constructor() : BaseEntity() {
    constructor(customer: Customer) : this() {
        this.customer = customer
    }

    @OneToOne(optional = false)
    @JoinColumn(name = "customer_id", referencedColumnName = "id")
    lateinit var customer: Customer

    @OneToMany(cascade = [CascadeType.ALL], mappedBy = "basket", orphanRemoval=true)
    var entries = mutableListOf<BasketEntry>()
}
