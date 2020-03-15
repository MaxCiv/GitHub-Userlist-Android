package com.maxciv.githubuserlist.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.maxciv.githubuserlist.R
import com.maxciv.githubuserlist.adapters.UserListAdapter
import com.maxciv.githubuserlist.databinding.FragmentUserListBinding
import com.maxciv.githubuserlist.model.LoadingStatus
import com.maxciv.githubuserlist.model.UserShortInfo
import com.maxciv.githubuserlist.viewmodels.UserListViewModel
import dagger.android.support.DaggerFragment
import javax.inject.Inject

/**
 * @author maxim.oleynik
 * @since 13.03.2020
 */
class UserListFragment : DaggerFragment(), OnNavigateToUserDetailsListener {

    private lateinit var binding: FragmentUserListBinding

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory
    private val viewModel: UserListViewModel by viewModels { viewModelFactory }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_user_list, container, false)
        binding.lifecycleOwner = this

        val adapter = UserListAdapter(this)

        binding.recyclerView.apply {
            layoutManager = LinearLayoutManager(requireContext())
            this.adapter = adapter
        }

        binding.retryButton.setOnClickListener {
            viewModel.retryLoadPagedList()
        }

        viewModel.pagedList.observe(viewLifecycleOwner, Observer {
            it?.let {
                adapter.submitList(it)
            }
        })

        viewModel.navigateToUserDetailsEvent.observe(viewLifecycleOwner, Observer {
            it?.let { userShortInfo ->
                this.findNavController().navigate(UserListFragmentDirections.toUserDetails(userShortInfo))
                viewModel.navigateToUserDetailsEnded()
            }
        })

        viewModel.pagedListLoadingStatus.observe(viewLifecycleOwner, Observer {
            it?.let { status ->
                when (status) {
                    LoadingStatus.LOADING -> {
                        binding.retryLoadingBar.visibility = View.VISIBLE
                    }
                    LoadingStatus.LOADED -> {
                        hideMissingNetworkLayout()
                    }
                    LoadingStatus.ERROR -> {
                        showMissingNetworkLayout()
                    }
                }
            }
        })

        return binding.root
    }

    private fun showMissingNetworkLayout() {
        binding.initialLoadingBar.visibility = View.GONE
        binding.retryLoadingBar.visibility = View.INVISIBLE
        binding.retryLayout.visibility = View.VISIBLE
    }

    private fun hideMissingNetworkLayout() {
        binding.initialLoadingBar.visibility = View.GONE
        binding.retryLoadingBar.visibility = View.INVISIBLE
        binding.retryLayout.visibility = View.GONE
    }

    override fun onNavigateToUserDetails(userShortInfo: UserShortInfo) {
        viewModel.navigateToUserDetails(userShortInfo)
    }
}

interface OnNavigateToUserDetailsListener {
    fun onNavigateToUserDetails(userShortInfo: UserShortInfo)
}
