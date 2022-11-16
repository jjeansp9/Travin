package com.jspstudio.travin

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.jspstudio.travin.databinding.ActivityAccountFriendListBinding

class AccountFriendListActivity : AppCompatActivity() {

    val binding: ActivityAccountFriendListBinding by lazy { ActivityAccountFriendListBinding.inflate(layoutInflater) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        setSupportActionBar(binding.toolbar)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)

    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return super.onSupportNavigateUp()
    }
}