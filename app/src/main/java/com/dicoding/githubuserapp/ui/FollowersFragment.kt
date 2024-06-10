package com.dicoding.githubuserapp.ui


import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.dicoding.githubuserapp.R
import com.dicoding.githubuserapp.databinding.FragmentFollowBinding
import com.dicoding.githubuserapp.vm.FollowersVM

class FollowersFragment : Fragment(R.layout.fragment_follow) {

    private var _bind: FragmentFollowBinding? = null
    private val bind get() = _bind!!
    private lateinit var vm: FollowersVM
    private lateinit var adapt: UserDataAdapt
    private lateinit var username: String

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val args = arguments
        username = args?.getString(UserDetailActivity.EXTRA_UNAME).toString()

        _bind = FragmentFollowBinding.bind(view)

        adapt = UserDataAdapt()
        adapt.notifyDataSetChanged()

        bind.apply {
            rvFollows.setHasFixedSize(true)
            rvFollows.layoutManager = LinearLayoutManager(activity)
            rvFollows.adapter = adapt
        }

        loadingBar(true)
        vm = ViewModelProvider(this, ViewModelProvider.NewInstanceFactory()).get(
            FollowersVM::class.java
        )
        vm.setFollowersList(username)
        vm.getFollowersList().observe(viewLifecycleOwner, {
            if (it != null) {
                adapt.setUserList(it)
                loadingBar(false)
            }
        })
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _bind = null
    }

    private fun loadingBar(isLoadingBar: Boolean) {
        if (isLoadingBar) {
            bind.progressBar.visibility = View.VISIBLE
        } else {
            bind.progressBar.visibility = View.GONE
        }
    }
}