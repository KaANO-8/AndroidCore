package com.kaano8.androidcore.com.kaano8.androidcore.designpattern.creational.builder

/**
 * Example of builder pattern
 */
class Laptop(builder: Builder) {
    private val processor: String = builder.processor
    private val ram: String
    private val ssd: String
    private val screenSize: String

    init {
        ssd = builder.ssd
        ram = builder.ram
        screenSize = builder.screenSize
    }

    class Builder(val processor: String) {
        var ram: String = "16"
        var ssd: String = "512"
        var screenSize: String = "15.6"


        fun setCustomRam(ram: String): Builder {
            this.ram = ram
            return this
        }

        fun setCustomSSD(ssd: String): Builder {
            this.ssd = ssd
            return this
        }

        fun setCustomScreenSize(screenSize: String): Builder {
            this.screenSize = screenSize
            return this
        }

        fun build(): Laptop {
            return Laptop(this)
        }
    }
}


fun main() {
    val laptop = Laptop.Builder("i7").setCustomRam("32").build()
}