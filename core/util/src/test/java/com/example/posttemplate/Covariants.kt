package com.example.posttemplate

import org.junit.Test

class Covariants {

    @Test
    fun t1() {
        var fruitBox = Basket<Fruit>(Apple())
        var bananaBox = Basket<Banana>(Banana())

        fruitBox = bananaBox
    }


    interface Fruit

    class Apple : Fruit
    class Banana : Fruit

    class Basket<out T : Fruit>(val item : T){
        fun getFruit() : T{
            return item
        }
    }

}
