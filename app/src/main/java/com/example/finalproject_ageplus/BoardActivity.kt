package com.example.finalproject_ageplus

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Environment
import android.provider.Settings
import android.util.Log
import android.provider.ContactsContract
import android.view.LayoutInflater
import android.widget.Toast
import androidx.fragment.app.Fragment
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.finalproject_ageplus.databinding.ActivityBoardBinding
import com.google.firebase.firestore.Query


class BoardActivity : AppCompatActivity() {
    lateinit var binding : ActivityBoardBinding

    // 데이터를 등록하는 부분
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityBoardBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // AppCompatActitivy() 맞는지 확인 필요
        myCheckPermission()
        // myCheckPermission(requireActivity as AppCompatActivity())

        binding.mainFab.setOnClickListener {
            if (MyApplication.checkAuth()) {
                val intent = Intent(this, AddActivity::class.java)
                startActivity(intent)
            }
            else {
                Toast.makeText(this, "인증을 진행해 주세요", Toast.LENGTH_SHORT).show()
            }
        }
    }

    override fun onStart() {
        super.onStart()
        if (MyApplication.checkAuth()) {
            MyApplication.db.collection("news")
                .orderBy("date", Query.Direction.DESCENDING)
                .get()
                .addOnSuccessListener {result ->
                    val itemList = mutableListOf<ItemBoardModel>()

                    for (document in result) {
                        val item = document.toObject(ItemBoardModel::class.java)
                        item.docId = document.id
                        itemList.add(item)
                    }
                    binding.boardRecyclerView.layoutManager = LinearLayoutManager(this) // Fragment이기 때문에 this가 아니라 requireContext()를 넣어줘야함
                    binding.boardRecyclerView.adapter = MyBoardAdapter(this, itemList)

                }
                .addOnFailureListener {
                    Toast.makeText(this, "데이터 획득 실패", Toast.LENGTH_SHORT).show()
                }
        }
    }

    fun myCheckPermission() {
        val requestPermissionLauncher = registerForActivityResult(
            ActivityResultContracts.RequestPermission()
        ) {
            if (it) {
                Toast.makeText(this,"권한 승인", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(this, "권한 거부", Toast.LENGTH_SHORT).show()
            }
        }

        if (ContextCompat.checkSelfPermission(
                this, Manifest.permission.READ_EXTERNAL_STORAGE
            ) !== PackageManager.PERMISSION_GRANTED
        ) {
            requestPermissionLauncher.launch(Manifest.permission.READ_EXTERNAL_STORAGE)
        }
        if (ContextCompat.checkSelfPermission(
                this, Manifest.permission.WRITE_EXTERNAL_STORAGE
            ) !== PackageManager.PERMISSION_GRANTED
        ) {
            requestPermissionLauncher.launch(Manifest.permission.WRITE_EXTERNAL_STORAGE)
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            if (!Environment.isExternalStorageManager()) {
                val intent = Intent(Settings.ACTION_MANAGE_ALL_FILES_ACCESS_PERMISSION)
                startActivity(intent)
            }
        }
    }

}