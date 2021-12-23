package com.rickrip.andersen6

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity(), AFragment.ButtonClickListener {

    companion object {
        var isTablet = false
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // isTablet = resources.displayMetrics.densityDpi <= 288
        isTablet = resources.getBoolean(R.bool.isTablet);

        Log.d("'", "${resources.displayMetrics.densityDpi}")
        Log.d("'", "$isTablet")

        if (supportFragmentManager.findFragmentByTag(AFragment.FRAGMENT_A_TAG) == null) { //для сохр сост
            supportFragmentManager.beginTransaction().run {
                val fragment = AFragment.newInstance()

                if (isTablet) {
                    replace(R.id.frameLayoutL, fragment, AFragment.FRAGMENT_A_TAG)
                    replace(R.id.frameLayoutR, BFragment.newInstance(), BFragment.FRAGMENT_B_TAG)
                } else {
                    replace(R.id.frameLayout, fragment, AFragment.FRAGMENT_A_TAG)
                }
                commit()
            }
        }

    }

    override fun onButtonClicked(view: Int, text: String) {

        if (!isTablet) {
            supportFragmentManager.beginTransaction().run {
                if (supportFragmentManager.findFragmentByTag(AFragment.FRAGMENT_A_TAG) != null) {
                    hide(supportFragmentManager.findFragmentByTag(AFragment.FRAGMENT_A_TAG)!!)
                }
                val fragment = BFragment.newInstance(view, text)
                add(R.id.frameLayout, fragment, BFragment.FRAGMENT_B_TAG)
                addToBackStack(BFragment.FRAGMENT_B_TAG)
                commit()
            }
        } else {
            supportFragmentManager.beginTransaction().run {
                val fragment = BFragment.newInstance(view, text)
                replace(R.id.frameLayoutR, fragment, BFragment.FRAGMENT_B_TAG)
                //addToBackStack(BFragment.FRAGMENT_B_TAG)
                commit()
            }
        }

    }

}