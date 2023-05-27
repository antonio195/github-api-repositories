package com.antoniocostadossantos.githubapirepositories.util

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction.TRANSIT_FRAGMENT_FADE
import com.antoniocostadossantos.githubapirepositories.R
import com.antoniocostadossantos.githubapirepositories.ui.activitys.WebViewActivity
import retrofit2.Response

fun AppCompatActivity.startFragment(fragment: Fragment, bundle: Bundle? = null): Boolean {
    fragment.arguments = bundle
    val transaction = supportFragmentManager.beginTransaction()
    transaction.replace(R.id.main_fragment_container_view, fragment)
    transaction.setTransition(TRANSIT_FRAGMENT_FADE)
    transaction.addToBackStack(null)
    transaction.commit()
    return true
}

fun Fragment.startFragment(fragment: Fragment, bundle: Bundle? = null): Boolean {
    fragment.arguments = bundle
    val transaction = activity?.supportFragmentManager?.beginTransaction()!!
    transaction.replace(R.id.main_fragment_container_view, fragment)
    transaction.setTransition(TRANSIT_FRAGMENT_FADE)
    transaction.addToBackStack(null)
    transaction.commit()
    return true
}

fun Fragment.startLink(url: String) {
    Intent(requireContext(), WebViewActivity::class.java).apply {
        putExtra("URL", url)
        startActivity(this)
    }
}

fun <T> handleRespons(response: Response<T>): StateResource<T> {
    if (response.isSuccessful) {
        response.body()?.let { values ->
            return StateResource.Success(values)
        }
    }
    return StateResource.Error(response.message())
}