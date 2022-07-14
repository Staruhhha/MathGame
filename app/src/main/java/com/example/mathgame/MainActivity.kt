package com.example.mathgame

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.widget.Button
import android.widget.PopupMenu
import android.widget.PopupWindow
import androidx.appcompat.app.AlertDialog
import androidx.databinding.DataBindingUtil
import com.example.mathgame.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    lateinit var binding : ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)

        binding.aboutButton.setOnClickListener {
            showAlertDialog()
        }

        binding.startGameButton.setOnClickListener {
            val intent = Intent(this, GameActivity::class.java)
            startActivity(intent)
        }

    }

    fun showAlertDialog(){
        val mDialogView = LayoutInflater.from(this).inflate(R.layout.popup_window_layout, null)
        val mBuilder = AlertDialog.Builder(this).setView(mDialogView)
        val mAlertDialog = mBuilder.show()
        val closeButton = mDialogView.findViewById<Button>(R.id.close_window_button)
        closeButton.setOnClickListener {
            mAlertDialog.dismiss()
        }
    }

}