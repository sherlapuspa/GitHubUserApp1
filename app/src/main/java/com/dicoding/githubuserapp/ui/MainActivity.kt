package com.dicoding.githubuserapp.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.KeyEvent
import android.view.View
import android.view.inputmethod.EditorInfo
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.dicoding.githubuserapp.data.response.UserData
import com.dicoding.githubuserapp.data.response.UserGitResponse
import com.dicoding.githubuserapp.data.retrofit.ApiClient
import com.dicoding.githubuserapp.databinding.ActivityMainBinding
import com.dicoding.githubuserapp.vm.UserDataVM
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : AppCompatActivity(), View.OnClickListener {

    private lateinit var bind: ActivityMainBinding
    private lateinit var vm: UserDataVM
    private lateinit var adapt: UserDataAdapt
    private var UID: String? = null
    private var findQuery: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        bind = ActivityMainBinding.inflate(layoutInflater)
        setContentView(bind.root)

        adapt = UserDataAdapt()
        adapt.notifyDataSetChanged()

        adapt.setOnItemClickCallback(object : UserDataAdapt.OnItemClickCallback {
            override fun onItemClicked(data: UserData) {
                Intent(this@MainActivity, UserDetailActivity::class.java).also {
                    it.putExtra(UserDetailActivity.EXTRA_UNAME, data.login)
                    startActivity(it)
                }
            }
        })

        vm = ViewModelProvider(this, ViewModelProvider.NewInstanceFactory()).get(
            UserDataVM::class.java
        )

        bind.apply {
            rvFindUsers.layoutManager = LinearLayoutManager(this@MainActivity)
            rvFindUsers.setHasFixedSize(true)
            rvFindUsers.adapter = adapt

            btnFind.setOnClickListener {
                findUserData()
            }

            findUserData.setOnKeyListener { v, keyCode, event ->
                if (event.action == KeyEvent.ACTION_DOWN && keyCode == KeyEvent.KEYCODE_ENTER) {
                    findUserData()
                    return@setOnKeyListener true
                }
                return@setOnKeyListener false
            }

            vm.getFindUserData().observe(this@MainActivity, {
                if (it != null) {
                    adapt.setUserList(it)
                    loadingBar(false)
                }
            })
        }

        findQuery = savedInstanceState?.getString("findQuery")
        if (findQuery != null) {
            findUserData(findQuery!!)
        } else {
            findUserData(" ")
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putString("findQuery", findQuery)
    }

    private fun findUserData() {
        bind.apply {
            val queries = findUserData.text.toString()
            if (queries.isEmpty()) return
            loadingBar(true)
            vm.setFindUser(queries)
            findQuery = queries
        }

        bind.findUserData.setOnEditorActionListener { _, actionId, _ ->
            if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                UID = bind.findUserData.text.toString().trim()
                findUserData()
                true
            } else {
                false
            }
        }
    }

    private fun findUserData(usernames: String) {
        loadingBar(true)
        UserDataList()
    }

    private fun UserDataList() {
        val username = bind.findUserData.text.toString().takeIf { it.isNotBlank() } ?: "Sherla"
        val client = ApiClient.apiInstance.findUserData(username)
        client.enqueue(object : Callback<UserGitResponse> {
            override fun onResponse(
                call: Call<UserGitResponse>, response: Response<UserGitResponse>
            ) {
                loadingBar(false)
                if (response.isSuccessful) {
                    val responseBody = response.body()
                    if (responseBody != null) {
                        adapt.setUserList(responseBody.items)
                    }
                } else {
                    Log.e("TAG", "onFailure: ${response.message()}")
                    // Show error message to user
                    showToast("Failed to find users.")
                }
                loadingBar(false)
            }

            override fun onFailure(call: Call<UserGitResponse>, t: Throwable) {
                loadingBar(false)
                Log.e("TAG", "onFailure: ${t.message}")
                // Show error message to user
                showToast("Failed to find users. Please check your internet connection.")
            }
        })
    }

    private fun loadingBar(isLoadingBar: Boolean) {
        if (isLoadingBar) {
            bind.progressBar.visibility = View.VISIBLE
        } else {
            bind.progressBar.visibility = View.GONE
        }
    }

    private fun showToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }

    override fun onClick(v: View?) {
    }
}
