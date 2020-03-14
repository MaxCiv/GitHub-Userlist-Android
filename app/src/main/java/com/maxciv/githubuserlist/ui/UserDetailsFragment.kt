package com.maxciv.githubuserlist.ui

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.maxciv.githubuserlist.R
import com.maxciv.githubuserlist.databinding.FragmentUserDetailsBinding
import com.maxciv.githubuserlist.viewmodels.UserDetailsViewModel

/**
 * @author maxim.oleynik
 * @since 13.03.2020
 */
class UserDetailsFragment : Fragment() {

    private lateinit var binding: FragmentUserDetailsBinding
    private val args: UserDetailsFragmentArgs by navArgs()
    private val viewModel: UserDetailsViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (viewModel.userShortInfo == null) {
            viewModel.userShortInfo = args.userShortInfo
            viewModel.load()
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_user_details, container, false)
        binding.lifecycleOwner = this

        Glide.with(binding.avatarImageView)
                .load(viewModel.userShortInfo?.avatarUrl)
                .transform(CenterCrop())
                .transition(DrawableTransitionOptions.withCrossFade())
                .into(binding.avatarImageView)

        binding.openBrowserButton.setOnClickListener {
            startActivity(Intent(Intent.ACTION_VIEW).apply {
                data = Uri.parse(viewModel.userShortInfo?.link)
            })
        }

        viewModel.user.observe(viewLifecycleOwner, Observer {
            it?.let { user ->
                binding.user = user
            }
        })

        return binding.root
    }
}
