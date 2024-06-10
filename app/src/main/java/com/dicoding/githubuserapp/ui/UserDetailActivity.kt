package com.dicoding.githubuserapp.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ImageButton
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.dicoding.githubuserapp.R
import com.dicoding.githubuserapp.vm.UserDetailVM
import com.dicoding.githubuserapp.databinding.ActivityUserDetailBinding

class UserDetailActivity : AppCompatActivity(), View.OnClickListener {

    companion object {
        const val EXTRA_UNAME = "extra_uname"
    }

    private lateinit var bind: ActivityUserDetailBinding
    private lateinit var vm: UserDetailVM

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        bind = ActivityUserDetailBinding.inflate(layoutInflater)
        setContentView(bind.root)

        bind.progressBar.visibility = View.VISIBLE

        val uname = intent.getStringExtra(EXTRA_UNAME)
        val bundle = Bundle()
        bundle.putString(EXTRA_UNAME, uname)

        val btnPrev: ImageButton = findViewById(R.id.btn_prev)
        btnPrev.setOnClickListener(this)

        vm = ViewModelProvider(this, ViewModelProvider.NewInstanceFactory()).get(
            UserDetailVM::class.java
        )

        vm.setUserDetails(uname.toString())
        vm.getUserDetails().observe(this, {
            if (it != null) {
                bind.apply {
                    tvName.text = it.name
                    tvLogin.text = it.login
                    tvFollowers.text = "${it.followers} Followers"
                    tvFollowing.text = "${it.following} Following"

                    Glide.with(this@UserDetailActivity).load(it.avatarUrl).centerCrop()
                        .into(imgUserAvt)
                }

                bind.progressBar.visibility = View.GONE
            }
        })

        val pagerAdapt = PagerAdapt(this, supportFragmentManager, bundle)
        bind.apply {
            vpFollow.adapter = pagerAdapt
            tlFollow.setupWithViewPager(vpFollow)
        }
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.btn_prev -> {
                val toPrev = Intent(this@UserDetailActivity, MainActivity::class.java)
                startActivity(toPrev)
            }
        }
    }
}