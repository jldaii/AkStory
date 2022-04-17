package com.jldaii.akstory.view.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.jldaii.akstory.R
import com.jldaii.akstory.databinding.FragmentProfileBinding


class ProfileFragment : Fragment() {

    private lateinit var binding: FragmentProfileBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentProfileBinding.inflate(inflater, container, false)

        requireActivity().apply {
            title = getString(R.string.title_profile)
        }

        return binding.root
    }

    companion object {
        val TAG: String = ProfileFragment::class.java.simpleName
        fun newInstance() = ProfileFragment()
    }

}