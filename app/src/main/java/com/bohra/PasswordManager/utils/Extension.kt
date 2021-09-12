package com.bohra.PasswordManager.utils

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.widget.EditText
import androidx.core.content.ContextCompat.startActivity


fun showToast(context: Context, message: String, visible: Boolean) {
    if (visible) {
        android.widget.Toast.makeText(context, message, android.widget.Toast.LENGTH_SHORT).show()
    }

}

fun showLog(tag: String, message: String) {
    Log.d(tag, message)
}

fun emptyCheck(value: String): Boolean {
    return value.isEmpty()
}

fun chekLengthForSix(stringToCheck: String): Boolean {
    return stringToCheck.length != 6
}

fun checkLengthForTen(stringToCheck: String): Boolean {
    return stringToCheck.length != 10
}

fun focusNext(
    fromEditText: EditText? = null,
    nextEditText: EditText? = null,
    previousEditText: EditText? = null
) {
    fromEditText?.addTextChangedListener(object : TextWatcher {
        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
        }

        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            if (fromEditText.text.trim().toString().length == 1) {
                nextEditText?.requestFocus()
            }
            if (fromEditText.text.trim().toString().isEmpty()) {
                previousEditText?.requestFocus()
            }

        }

        override fun afterTextChanged(s: Editable?) {
        }
    })


}

fun <T> navigationIntent(context: Context, packageContext: Context, cls: Class<T>) {
    val intent = Intent(packageContext, cls)
    context.startActivity(intent)
}