package com.maxciv.githubuserlist.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.maxciv.githubuserlist.R
import com.maxciv.githubuserlist.databinding.FragmentUserDetailsBinding
import com.maxciv.githubuserlist.viewmodels.UserDetailsViewModel

/**
 * @author maxim.oleynik
 * @since 13.03.2020
 */
class UserDetailsFragment : Fragment() {

    private lateinit var binding: FragmentUserDetailsBinding
//    private val args: UserDetailsFragmentArgs by navArgs()
    private val viewModel: UserDetailsViewModel by viewModels()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_user_details, container, false)
        binding.lifecycleOwner = this

        return binding.root
    }
}
