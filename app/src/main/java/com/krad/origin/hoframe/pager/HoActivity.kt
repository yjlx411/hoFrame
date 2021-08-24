package com.krad.origin.hoframe.pager

import android.os.Bundle
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import com.krad.origin.databinding.ActivityHoBinding
import com.krad.origin.hoframe.view.viewset.StatusBarInvasionUtil
import android.content.Intent

import android.R
import com.krad.origin.SplashActivity


class HoActivity : AppCompatActivity() {

    private var mPager: Pager<*>? = null

    private var binding: ActivityHoBinding? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        var restart = false
        if (savedInstanceState != null) {
            restart = true
        }
        super.onCreate(null)
        if (restart) {
            val intent = Intent(this, SplashActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            startActivity(intent)
        } else {
            StatusBarInvasionUtil().run(this)
            val intent = intent
            val coreKey = intent.getIntExtra(PagerLoader.KEY_ID, 0)
            mPager = PagerStore.take(coreKey)
            if (mPager == null) finish()
            binding = ActivityHoBinding.inflate(layoutInflater)
            setContentView(binding!!.viewRoot)
            mPager?.fragmentManager = supportFragmentManager
            mPager?.inject(binding!!.viewRoot!!)
        }
    }


    override fun onStart() {
        super.onStart()
        mPager?.onStart()
    }

    override fun onResume() {
        super.onResume()
        mPager?.onResume()
    }

    override fun onPause() {
        super.onPause()
        mPager?.onPause()
    }

    override fun onStop() {
        super.onStop()
        mPager?.onStop()
    }

    override fun onDestroy() {
        super.onDestroy()
        binding?.unbind()
    }
}