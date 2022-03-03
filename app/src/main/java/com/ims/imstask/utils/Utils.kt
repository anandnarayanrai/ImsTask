package com.nt.cryothai.utils

import android.app.Activity
import android.content.Context
import android.content.res.Resources
import android.os.Build
import android.text.Html
import android.text.Spanned
import android.text.TextUtils
import android.util.Patterns
import android.view.View
import android.view.inputmethod.InputMethodManager
import java.io.File
import java.util.regex.Pattern


object Utils {
    fun hideKeyboard(activity: Activity) {
        val imm =
            activity.getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
        var view = activity.currentFocus
        if (view == null) {
            view = View(activity)
        }
        imm.hideSoftInputFromWindow(view.windowToken, 0)
    }

    fun deleteCache(context: Context) {
        try {
            val dir = context.cacheDir
            deleteDir(dir)
        } catch (e: Exception) {
            e.printStackTrace()
        }

    }

    fun isValidMobileNumber(mobileNumber: String): Boolean {

        // 1) Begins with 0 or +91
        // 2) Then contains 7 or 8 or 9.
        // 3) Then contains 9 digits
        /*return Pattern.compile(
            "(0/91)?[7-9][0-9]{9}"
        ).matcher(mobileNumber).matches()*/

        return when (mobileNumber) {
            "+916000000000" -> false
            "+917000000000" -> false
            "+918000000000" -> false
            "+919000000000" -> false
            // else -> Pattern.compile("((\\+|)91)?[6-9][0-9]{9}").matcher(mobileNumber).matches()
            else -> Pattern.compile("^(\\+91[\\-\\s]?)?[0]?(91)?[6789]\\d{9}\$")
                .matcher(mobileNumber)
                .matches()
        }

        //return Pattern.compile("((\\+|)91)?[6-9][0-9]{9}").matcher(mobileNumber).matches()

    }


    fun deleteDir(dir: File?): Boolean {
        if (dir != null && dir.isDirectory) {
            val children = dir.list()
            for (i in children.indices) {
                val success = deleteDir(File(dir, children[i]))
                if (!success) {
                    return false
                }
            }
            return dir.delete()
        } else return if (dir != null && dir.isFile) {
            dir.delete()
        } else {
            false
        }
    }

    fun isValidEmail(target: CharSequence?): Boolean {
        return !TextUtils.isEmpty(target) && Patterns.EMAIL_ADDRESS.matcher(target)
            .matches()
    }

    fun wordcount(string: String): Int {
        var count = 0
        val ch = CharArray(string.length)
        for (i in string.indices) {
            ch[i] = string[i]
            if (i > 0 && ch[i] != ' ' && ch[i - 1] == ' ' || ch[0] != ' ' && i == 0) count++
        }
        return count
    }

    fun checkString(string: String): String {
        try {
            if (!string.isNullOrEmpty()) {
                return string
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return ""
    }

    fun capitalizeWord(str: String): String {
        val words = str.split("\\s".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()
        var capitalizeWord = ""

        /*for (w in words) {
            val first = w.substring(0, 1)
            val afterfirst = w.substring(1)
            capitalizeWord += first.toUpperCase()*//*+ afterfirst + " "*//*
    }*/
        if (str != "") {
            if (words.size == 1) {
                val first = words[0].substring(0, 1)
                capitalizeWord += first.toUpperCase()
            } else {
                for (i in 0..1) {
                    val first = words[i].substring(0, 1)
                    capitalizeWord += first.toUpperCase()/*+ afterfirst + " "*/
                }
            }
        }
        return capitalizeWord.trim { it <= ' ' }
    }

    fun addStar(string: String): Spanned {

        var addColorStar = "${removeStar(string)}<font color='#FF0000'>*</font>"

        return if (string.contains("*")) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                Html.fromHtml(addColorStar, Html.FROM_HTML_MODE_COMPACT)
            } else {
                Html.fromHtml(addColorStar)
            }
        } else {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                Html.fromHtml(string, Html.FROM_HTML_MODE_COMPACT)
            } else {
                Html.fromHtml(string)
            }
        }
    }

    fun addStarWithValid(string: String, isValid: Boolean): Spanned {

        val addColorStar = "${removeStar(string)}<font color='#FF0000'>*</font>"

        return if (isValid) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                Html.fromHtml(addColorStar, Html.FROM_HTML_MODE_COMPACT)
            } else {
                Html.fromHtml(addColorStar)
            }
        } else {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                Html.fromHtml(removeStar(string), Html.FROM_HTML_MODE_COMPACT)
            } else {
                Html.fromHtml(removeStar(string))
            }
        }
    }

    fun removeStar(string: String): String {
        return string.replace("*", "")
    }

    fun createBoldText(mainString: String, changeString: String): String {
        var returnString = mainString
        if (mainString.contains(changeString, ignoreCase = true)) {
            returnString =
                mainString.replace(changeString, "<b>$changeString</b>", ignoreCase = true)
        }
        return returnString;
    }

    fun getScreenWidth(): Float {
        var width = 360.0.toFloat()
        val displayMetrics =
            Resources.getSystem().displayMetrics
        width = displayMetrics.widthPixels / displayMetrics.density
        return width
    }

    fun getScreenHeight(): Float {
        var height = 360.0.toFloat()
        val displayMetrics =
            Resources.getSystem().displayMetrics
        height = displayMetrics.heightPixels / displayMetrics.density
        return height
    }


}