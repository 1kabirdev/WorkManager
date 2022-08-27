package com.example.work

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.example.work.databinding.ActivityMainDataBindingBinding

class MainActivityDataBinding : AppCompatActivity() {

    private lateinit var bindingBinding: ActivityMainDataBindingBinding

    private val employee = Models(1, "Kabir Agamirzaev", "89627751997")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        bindingBinding = DataBindingUtil.setContentView(
            this@MainActivityDataBinding,
            R.layout.activity_main_data_binding
        )

        bindingBinding.models = employee

    }

    override fun onResume() {
        super.onResume()
        bindingBinding.models = employee
    }
}