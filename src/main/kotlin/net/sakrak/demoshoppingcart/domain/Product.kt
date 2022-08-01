package net.sakrak.demoshoppingcart.domain

import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.Table

@Entity
@Table(name = "products")
class Product() : BaseEntity() {
    constructor(id: Long?, name: String, description: String) : this() {
        this.id = id
        this.name = name
        this.description = description
    }

    @Column
    lateinit var name : String

    @Column
    lateinit var description : String
}