package com.antoniocostadossantos.githubapirepositories.util

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.antoniocostadossantos.githubapirepositories.R

fun AppCompatActivity.startFragment(fragment: Fragment, bundle: Bundle? = null): Boolean {
    fragment.arguments = bundle
    val transaction = supportFragmentManager.beginTransaction()
    transaction.replace(R.id.main_fragment_container_view, fragment)
    transaction.addToBackStack(null)
    transaction.commit()
    return true
}

fun Fragment.startFragment(fragment: Fragment, bundle: Bundle? = null): Boolean {
    fragment.arguments = bundle
    val transaction = activity?.supportFragmentManager?.beginTransaction()!!
    transaction.replace(R.id.main_fragment_container_view, fragment)
    transaction.addToBackStack(null)
    transaction.commit()
    return true
}