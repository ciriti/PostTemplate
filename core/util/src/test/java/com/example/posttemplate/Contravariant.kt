package com.example.posttemplate

import org.junit.Test

class Contravariant {

    //    Contravariant (in) classes or functions are designed to consume values of the generic type.
    //    Supertypes can be assigned to subtypes (Basket<Fruit> → Basket<Apple>).

    @Test
    fun t1() {
        var tfBox = TBox<TropicalFruit>()
        var ananasBox = TBox<Ananas>()
        var mangoBox = TBox<Mango>()

        ananasBox = tfBox

    }

    interface TropicalFruit
    class Ananas : TropicalFruit
    class Mango : TropicalFruit

    class TBox<in T : TropicalFruit> {
        fun addFruit(tf : T){
            println(tf)
        }
    }

}
