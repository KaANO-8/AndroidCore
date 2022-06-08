package com.kaano8.androidcore.com.kaano8.androidcore.designpattern.structural.decorator

//1
interface Salad {
    fun getIngredient(): String
}

//2
class PlainSalad : Salad {
    override fun getIngredient(): String {
        return "Arugula & Lettuce"
    }
}

//3
open class SaladDecorator(protected var salad: Salad) : Salad {
    override fun getIngredient(): String {
        return salad.getIngredient()
    }
}

//4
class Cucumber(salad: Salad) : SaladDecorator(salad) {
    override fun getIngredient(): String {
        return salad.getIngredient() + ", Cucumber"
    }
}

//5
class Carrot(salad: Salad) : SaladDecorator(salad) {
    override fun getIngredient(): String {
        return salad.getIngredient() + ", Carrot"
    }
}

fun main() {
    val cucumberSalad = Cucumber(Carrot(PlainSalad()))
    print(cucumberSalad.getIngredient()) // Arugula & Lettuce, Carrot, Cucumber
    val carrotSalad = Carrot(PlainSalad())
    print(carrotSalad.getIngredient()) // Arugula & Lettuce, Carrot
}