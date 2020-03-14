package com.maxciv.githubuserlist.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.maxciv.githubuserlist.R
import com.maxciv.githubuserlist.adapters.UserListAdapter
import com.maxciv.githubuserlist.databinding.FragmentUserListBinding
import com.maxciv.githubuserlist.model.UserShortInfo
import com.maxciv.githubuserlist.viewmodels.UserListViewModel

/**
 * @author maxim.oleynik
 * @since 13.03.2020
 */
class UserListFragment : Fragment(), OnNavigateToUserDetailsListener {

    private lateinit var binding: FragmentUserListBinding
    private val viewModel: UserListViewModel by viewModels()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_user_list, container, false)
        binding.lifecycleOwner = this

        val adapter = UserListAdapter(this)

        binding.recyclerView.apply {
            layoutManager = LinearLayoutManager(requireContext())
            this.adapter = adapter
        }

        viewModel.pagedList.observe(viewLifecycleOwner, Observer {
            adapter.submitList(it)
        })

        viewModel.navigateToUserDetailsEvent.observe(viewLifecycleOwner, Observer {
            it?.let { userShortInfo ->
                this.findNavController().navigate(UserListFragmentDirections.toUserDetails(userShortInfo))
                viewModel.navigateToUserDetailsEnded()
            }
        })

        return binding.root
    }

    override fun onNavigateToUserDetails(userShortInfo: UserShortInfo) {
        viewModel.navigateToUserDetails(userShortInfo)
    }
}

interface OnNavigateToUserDetailsListener {
    fun onNavigateToUserDetails(userShortInfo: UserShortInfo)
}
