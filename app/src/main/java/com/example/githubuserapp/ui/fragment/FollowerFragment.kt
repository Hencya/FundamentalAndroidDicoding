package com.example.githubuserapp.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.githubuserapp.R
import com.example.githubuserapp.databinding.FragmentFollowerBinding
import com.example.githubuserapp.model.UserSocialResponse
import com.example.githubuserapp.ui.activity.DetailUserActivity
import com.example.githubuserapp.ui.adapter.DetailUserAdapter
import com.example.githubuserapp.ui.viewmodel.FollowerViewModel
import com.google.android.material.snackbar.Snackbar


class FollowerFragment : Fragment() {

    private var _binding: FragmentFollowerBinding? = null
    private val followersViewModel: FollowerViewModel by activityViewModels()

    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentFollowerBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val args = arguments
        val username = args?.getString(DetailUserActivity.USERNAME).toString()

        followersViewModel.itemFollowers.observe(viewLifecycleOwner) {
            setFollowers(it)
        }

        val layoutManager = LinearLayoutManager(activity)
        binding.rvUser.layoutManager = layoutManager

        val itemDecoration = DividerItemDecoration(activity, layoutManager.orientation)
        binding.rvUser.addItemDecoration(itemDecoration)

        followersViewModel.isLoading.observe(viewLifecycleOwner) {
            showLoading(it)
        }

        followersViewModel.getUserFollowers(username)

        followersViewModel.snackbarText.observe(viewLifecycleOwner) {
            it.getContentIfNotHandled()?.let { snackBarText ->
                Snackbar.make(
                    requireActivity().findViewById(R.id.tv_user_follower),
                    snackBarText,
                    Snackbar.LENGTH_LONG
                ).show()
            }
        }
    }

    private fun setFollowers(itemUser: List<UserSocialResponse>) {
        val listUser = ArrayList<UserSocialResponse>()
        for (user in itemUser) {
            val dataUser = UserSocialResponse(
                user.login,
                user.avatarUrl,
                user.type
            )
            listUser.add(dataUser)
        }

        val adapter = DetailUserAdapter(listUser)
        binding.rvUser.adapter = adapter
    }


    private fun showLoading(isLoading: Boolean) {
        if (isLoading) {
            binding.progressBar.visibility = View.VISIBLE
            binding.rvUser.visibility = View.INVISIBLE
        } else {
            binding.progressBar.visibility = View.GONE
            binding.rvUser.visibility = View.VISIBLE
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
