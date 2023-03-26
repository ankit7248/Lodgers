package com.example.lodgers

import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.profile_page.*

class PorfilePage: AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.profile_page)

        Spinner_Gender.onItemClickListener = object : AdapterView.OnItemClickListener
        {
            override fun onNothingSelected(p0: AdapterView<*>?) {
            }
            override fun onItemClick(adapterView: AdapterView<*>?, view: View?, position: Int, id: Long) {

                Toast.makeText(this@PorfilePage,"You Selected ${adapterView?.getItemAtPosition(position).toString()}",
                    Toast.LENGTH_SHORT).show()
            }

        }

    }
}