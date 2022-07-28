package net.sakrak.demoshoppingcard.domain

import javax.persistence.*

@Entity
@Table(name = "basket_entries")
class BasketEntry constructor() : BaseEntity() {
    constructor(basket: Basket, product: Product) : this() {
        this.basket = basket
        this.product = product
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "basket_id", nullable = true)
    lateinit var basket: Basket

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id", nullable = false)
    lateinit var product: Product

    @Column
    var quantity: Int = 0
}