package com.example.finalproject_ageplus

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils.replace
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.ActionBarDrawerToggle
import com.example.finalproject_ageplus.databinding.ActivityListBinding
import com.example.finalproject_ageplus.databinding.ActivityMainBinding
import com.example.finalproject_ageplus.databinding.ItemMainBinding
import com.example.finalproject_ageplus.databinding.NavigationHeaderBinding

class ListActivity : AppCompatActivity() {

    lateinit var binding: ActivityListBinding
    lateinit var toolbar : ActionBarDrawerToggle

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivityListBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // setSupportActionBar(binding.toolbar)

        // retrofitFragment= RetrofitFragment()
        val xmlfragment = XmlFragment()

        supportFragmentManager.beginTransaction()
            .replace(R.id.activity_content, xmlfragment)
            .commit()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        if (toolbar.onOptionsItemSelected(item)) {
            return true
        }

        when(item.itemId) {
            R.id.menu_home -> {
                startActivity(Intent(this, FindActivity::class.java))
            }
        }

        return super.onOptionsItemSelected(item)
    }

}