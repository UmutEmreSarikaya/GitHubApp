package com.umutemregithub.core.base

import android.os.Bundle
import android.view.View
import androidx.annotation.CallSuper
import androidx.fragment.app.Fragment

abstract class BaseFragment : Fragment() {

    @CallSuper
    open fun initClickListener(){}

    @CallSuper
    open fun initObservers(){}

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initClickListener()
        initObservers()
    }
}