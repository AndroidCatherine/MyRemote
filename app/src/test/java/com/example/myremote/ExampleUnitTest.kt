package com.example.myremote

import org.junit.Test

import org.junit.Assert.*

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class ExampleUnitTest {
    @Test
    fun addition_isCorrect() {
        assertEquals(4, 2 + 2)
        println("Hello world=========================")

        login( callback = {
            val result = "it: + $it Start"
            println( result )
        } )
    }

    fun login( callback:( token:String ) -> Unit  ){
        println("taking token ..ing....")
        val token = "token_ABC"
        callback.invoke( token )
    }

    fun showToken(token:((String)->Unit)?){
        println("Token")
    }
}