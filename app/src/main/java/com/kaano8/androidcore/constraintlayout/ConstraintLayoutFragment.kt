package com.kaano8.androidcore.constraintlayout

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.kaano8.androidcore.R

class ConstraintLayoutFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_constraint_layout, container, false)
    }

    companion object {
        @JvmStatic
        fun newInstance() =
            ConstraintLayoutFragment()
    }
}