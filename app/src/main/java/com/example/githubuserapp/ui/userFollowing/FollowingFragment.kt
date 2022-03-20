package com.example.githubuserapp.ui.userFollowing

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.githubuserapp.R
import com.example.githubuserapp.databinding.FragmentFollowingBinding
import com.example.githubuserapp.model.UserSocialResponse
import com.example.githubuserapp.ui.detailUser.DetailUserActivity
import com.example.githubuserapp.ui.adapter.DetailUserAdapter
import com.google.android.material.snackbar.Snackbar

class FollowingFragment : Fragment() {

    private var _binding: FragmentFollowingBinding? = null
    private val followingViewModel: FollowingViewModel by activityViewModels()

    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentFollowingBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val args = arguments
        val username = args?.getString(DetailUserActivity.USERNAME).toString()

        followingViewModel.itemFollowing.observe(viewLifecycleOwner) {
            setFollowing(it)
        }

        val layoutManager = LinearLayoutManager(activity)
        binding.rvUser.layoutManager = layoutManager

        val itemDecoration = DividerItemDecoration(activity, layoutManager.orientation)
        binding.rvUser.addItemDecoration(itemDecoration)

        followingViewModel.isLoading.observe(viewLifecycleOwner) {
            showLoading(it)
        }

        followingViewModel.getUserFollowing(username)

        followingViewModel.snackbarText.observe(viewLifecycleOwner) {
            it.getContentIfNotHandled()?.let { snackBarText ->
                Snackbar.make(
                    requireActivity().findViewById(R.id.tv_user_following),
                    snackBarText,
                    Snackbar.LENGTH_LONG
                ).show()
            }
        }
    }

    private fun setFollowing(itemUser: List<UserSocialResponse>) {
        val listUser = ArrayList<UserSocialResponse>()
        for (user in itemUser) {
            val dataUser = UserSocialResponse(
                user.login,
                user.avatarUrl,
                user.type
            )
            listUser.add(dataUser)
        }

        val adapter =
            DetailUserAdapter(listUser)
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