package com.unitalegio.usrmng.data.entity

import jakarta.persistence.*

@MappedSuperclass
abstract class BaseNumberIdentityEntity<T: Number>(
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    open val id: T
)