package com.jldaii.akstory.view.fragment

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.Fragment
import com.custom.ui.activity.ShowCustomViewActivity
import com.custom.ui.activity.ShowMaterialEditActivity
import com.jldaii.akstory.R
import com.jldaii.akstory.databinding.FragmentDashboardBinding

class DashboardFragment : Fragment() {

    private lateinit var binding: FragmentDashboardBinding

    lateinit var btnToShowView: Button
    lateinit var btnToShowPoint: Button

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = FragmentDashboardBinding.inflate(inflater, container, false)

        requireActivity().apply {
            title = getString(R.string.title_dashboard)
        }
        btnToShowView = binding.btnToshowView
        btnToShowPoint = binding.btnToShowPoint

        initView()
        return binding.root
    }

    private fun initView() {
        btnToShowView.setOnClickListener {
            val intent = Intent(activity, ShowCustomViewActivity::class.java)
            startActivity(intent)
        }

        btnToShowPoint.setOnClickListener {
            // Intent intent = new Intent(getActivity(), ShowProvinceViewActivity.class);
            val intent = Intent(activity, ShowMaterialEditActivity::class.java)
            startActivity(intent)
        }
    }

    companion object {
        val TAG: String = DashboardFragment::class.java.simpleName
        fun newInstance() = DashboardFragment()
    }

}