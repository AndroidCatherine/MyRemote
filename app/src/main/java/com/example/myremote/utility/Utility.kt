package com.example.myremote.utility

import android.app.Activity
import android.content.Context
import android.content.Intent
import java.util.regex.Matcher
import java.util.regex.Pattern
/**
 * Created by Catherine Tsai on 01/03/2024
 */
class Utility {
    companion object{

        fun checkEmailStyle( inputEmail: String? ):Boolean{
            var pass = false
            val pattern: Pattern = Pattern
                .compile("^([a-zA-Z0-9_-])+@([a-zA-Z0-9_-])+(\\.([a-zA-Z0-9_-])+)+$");
            val matcher: Matcher = pattern.matcher( inputEmail )
            if (matcher.matches()) {
                pass = true
            }
            return pass
        }

        fun <T:Activity> goToPage(context: Context, className:Class<T> ){
            val intent = Intent()
            intent.setClass( context , className )
            context.startActivity(intent)
        }

    }
}