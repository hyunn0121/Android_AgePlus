package com.example.finalproject_ageplus

import android.app.Application
import androidx.multidex.MultiDexApplication
import com.bumptech.glide.Glide.init
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.ktx.storage
import com.tickaroo.tikxml.TikXml
import com.tickaroo.tikxml.retrofit.TikXmlConverterFactory
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

// 로그인, retrofit 통신을 위한 클래스
class MyApplication: MultiDexApplication() {
    companion object { // 전역 변수 선언
        lateinit var db : FirebaseFirestore
        lateinit var storage : FirebaseStorage

        lateinit var auth : FirebaseAuth
        var email : String?  = null

        // 사용자가 로그인을 시도했던 정보가 firebase에 등록된 사용자인지 확인하기 위한 함수
        fun checkAuth() : Boolean {
            var currentuser = auth.currentUser
            return currentuser?.let {
                email = currentuser.email
                if (currentuser.isEmailVerified) true
                else false
            } ?: false
        }


        // var networkService : NetworkService
        var networkServiceXml : NetworkService

        val parser = TikXml.Builder().exceptionOnUnreadXml(false).build()
        val retrofitXml : Retrofit
            get() = Retrofit.Builder()
                .baseUrl("http://apis.data.go.kr/")
                // .addConverterFactory(GsonConverterFactory.create())
                .addConverterFactory(TikXmlConverterFactory.create(parser))
                .build()

        init{
            networkServiceXml = retrofitXml.create(NetworkService::class.java)
        }

        /* 2023 수업 예제
        var networkService : NetworkService
        val retrofit : Retrofit
            get() = Retrofit.Builder()
                .baseUrl("http://apis.data.go.kr/B553748/CertImgListService/")
                .addConverterFactory(GsonConverterFactory.create())
                .build()

        init {
            networkService = retrofit.create(NetworkService::class.java) // 초기화
        }
         */
    }

    override fun onCreate() {
        super.onCreate()
        auth = Firebase.auth

        db = FirebaseFirestore.getInstance()
        storage = Firebase.storage
    }
}