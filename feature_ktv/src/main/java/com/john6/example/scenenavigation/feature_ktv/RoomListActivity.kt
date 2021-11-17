package com.john6.example.scenenavigation.feature_ktv

import android.content.Intent
import android.os.Bundle
import com.john6.example.base.BaseActivity
import com.john6.example.scenenavigation.feature_ktv.databinding.ActivityRoomListBinding

class RoomListActivity :BaseActivity<ActivityRoomListBinding>(){
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initView()
    }

    private fun initView() {
        mBinding.btn.setOnClickListener {
            startActivity(Intent(this, RoomActivity::class.java))
        }
        mBinding.fab.setOnClickListener {
            startActivity(Intent(this, RoomCreateActivity::class.java))
        }
    }
}