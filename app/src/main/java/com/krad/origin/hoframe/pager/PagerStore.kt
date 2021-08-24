package com.krad.origin.hoframe.pager

import android.util.SparseArray
import com.krad.origin.hoframe.index.PagerIndex

object PagerStore {
    private val pagerArray = SparseArray<Pager<*>>()

    fun take(key: Int): Pager<*>? {
        val pager = pagerArray.get(key)
        pagerArray.remove(key)
        return pager
    }

    fun put(key: Int, pager: Pager<*>) {
        pagerArray.put(key, pager)
    }

}