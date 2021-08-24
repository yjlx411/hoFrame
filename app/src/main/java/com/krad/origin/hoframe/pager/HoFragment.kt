package com.krad.origin.hoframe.pager

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.krad.origin.R
import java.util.*

class HoFragment : Fragment() {
    private var mPager: Pager<*>? = null
    fun setCoreUnit(pager: Pager<*>?) {
        mPager = pager
    }

    fun <T : Pager<*>?> getCoreUnit(): T? {
        return mPager as T?
    }

    private fun loadCore() {
        val bundle = arguments ?: return
        try {
            val coreClass = bundle.getSerializable("core") as? Class<*>
            if (coreClass != null) {
                val instance = coreClass.newInstance()
                if (instance is Pager<*>) {
                    mPager = instance as Pager<*>
                    val dataExtra = bundle.getSerializable("coreData") as? HashMap<String, Any>
                    mPager!!.dataExtra = dataExtra
                    mPager?.fragmentManager = childFragmentManager
                }
            }
            mPager?.initData()
        } catch (e: Exception) {
            //
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        if (mPager == null) {
            loadCore()
        } else {
            mPager?.fragmentManager = childFragmentManager
        }
        return inflater.inflate(R.layout.fragment_ho, container, false)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        if (mPager != null) mPager!!.inject(view as ViewGroup)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        if (mPager != null) {
            mPager!!.onDestroy()
            mPager = null
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (mPager != null) {
            mPager!!.onActivityResult(requestCode, resultCode, data)
        }
    }
}