package com.example.herokuspringmqexample

import java.io.Serializable

class BigOperation : Serializable {
    var name: String? = null

    constructor() {}
    constructor(name: String?) {
        this.name = name
    }

    override fun toString(): String {
        return "BigOperation{" +
            "name='" + name + '\'' +
            '}'
    }
}
