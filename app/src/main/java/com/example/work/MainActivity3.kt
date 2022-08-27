package com.example.work

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.example.work.databinding.ActivityMain3Binding

class MainActivity3 : AppCompatActivity() {

    private lateinit var bindingBinding: ActivityMain3Binding

    private val models = Models(1, "Kabir Agamirzaev", "89627751997")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        bindingBinding = DataBindingUtil.setContentView(
            this@MainActivity3,
            R.layout.activity_main_3
        )

        bindingBinding.models = models

    }

    override fun onResume() {
        super.onResume()
        bindingBinding.models = models
    }
}