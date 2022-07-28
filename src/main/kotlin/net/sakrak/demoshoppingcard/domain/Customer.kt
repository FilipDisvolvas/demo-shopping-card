package net.sakrak.demoshoppingcard.domain

import javax.persistence.*
import javax.validation.constraints.NotBlank

@Entity
@Table(indexes = [Index(columnList = "email")])
class Customer() : BaseEntity() {
    @Column
    @NotBlank
    lateinit var firstName : String

    @Column
    var middleName : String? = null

    @Column
    @NotBlank
    lateinit var lastName : String

    @Column(unique = true)
    @NotBlank
    lateinit var email : String

    @Column
    @NotBlank
    lateinit var addressFirstLine : String

    @Column
    @NotBlank
    lateinit var addressSecondLine : String

    @Column
    var addressThirdLine : String? = null

    @Column
    @NotBlank
    var hashedPassword: String? = null

    @OneToOne(optional = true, mappedBy = "customer", fetch = FetchType.LAZY)
    var currentBasket: Basket? = null
}