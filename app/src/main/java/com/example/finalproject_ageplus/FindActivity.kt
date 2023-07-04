package com.example.finalproject_ageplus

import android.app.AlertDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.core.content.ContextCompat.startActivity
import com.example.finalproject_ageplus.databinding.ActivityFindBinding
import com.example.finalproject_ageplus.databinding.ActivityMainBinding
import com.example.finalproject_ageplus.databinding.NavigationHeaderBinding
import com.google.android.material.navigation.NavigationView

// 로그인된 후 무슨 일을 할 지 선택하는 화면 (일자리 찾기 등 ..)
class FindActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {
    lateinit var binding: ActivityFindBinding
    lateinit var toolbar : ActionBarDrawerToggle
    lateinit var navHeader : NavigationHeaderBinding
    var authMenuItem : MenuItem? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivityFindBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.toolbar)
        toolbar = ActionBarDrawerToggle(this, binding.drawer, R.string.drawer_opened, R.string.drawer_closed)
        supportActionBar?.setDisplayHomeAsUpEnabled(true) // supportActionBar는 null일 수도 있기 때문에 ? 붙임
        toolbar.syncState()

        binding.mainDrawer.setNavigationItemSelectedListener(this)

        navHeader = NavigationHeaderBinding.inflate(layoutInflater)

        binding.FindButton1.setOnClickListener {
            // 일자리 목록 페이지로 이동
            startActivity(Intent(this, ListActivity::class.java))
        }

        binding.FindButton2.setOnClickListener {
            startActivity(Intent(this, BoardActivity::class.java))
        }

        navHeader.logoutButton.setOnClickListener {
            // 로그아웃 처리하기
            //로그아웃...........
            MyApplication.auth.signOut()
            MyApplication.email = null // 마지막 로그인했던 주소를 지워줘야함
            Log.d("mobileApp", "로그아웃 확인")
            // changeVisibility("logout")
        }

        /*
        if(authMenuItem != null) {
            if (MyApplication.checkAuth()) {
                authMenuItem!!.title = "${MyApplication.email}님"
            }
            else {
                authMenuItem!!.title = "인증" // 그대로 인증을 쓰겠다는 것
            }
        }
        */
    }

    // 드로어 레이아웃 화면 메뉴 설정
    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        when(item.itemId) { // when을 이용해서 각 MenuItem을 구분
            R.id.MyPage -> {
                startActivity(Intent(this, MypageActivity::class.java)) // 이름, 나이, 성별 등 기본 정보 설정 페이지
            }
            R.id.manage_CV -> {
                startActivity(Intent(this, DocumentActivity::class.java)) //이력서 페이지
            }
            R.id.setting -> {
                startActivity(Intent(this, SettingActivity::class.java)) // sharedPreference (글씨 크기, 글꼴, 배경색 등 지정 가능)
            }
        }
        return true
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_auth, menu) // menuInflater를 통해 해당되는 menu_action.xml의 리소스 값을 불러오는 것

        // 현재 로그인 상태인지 아닌지 확인
        authMenuItem = menu!!.findItem(R.id.menu_user)
        if (MyApplication.checkAuth()) {
            authMenuItem!!.title = "${MyApplication.email}님"
        }
        else {
            authMenuItem!!.title = "인증" // 그대로 인증을 쓰겠다는 것
        }

        return super.onCreateOptionsMenu(menu)
    }

    // 3) 만들어놓은 툴바에 대해서 클릭 이벤트 연결해두기 / onOptionsItemSelected : 메뉴 아이템이 클릭되었을 때 실행될 함수 / ex. menu1이라는 아이템이 클릭되었을 때 실행할 함수 등을 정의
    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        if (toolbar.onOptionsItemSelected(item)) {
            return true
        }

        if (item.itemId === R.id.menu_user) {
            val intent = Intent(this, AuthActivity::class.java)

            if (authMenuItem!!.title!!.equals("인증")) {
                // 상단에 "인증"으로 되어있다면 로그인으로 들어가기
                intent.putExtra("data", "logout")
            }
            else {
                MyApplication.email = null
                intent.putExtra("data", "login")
            }
            startActivity(intent)
        }
        return super.onOptionsItemSelected(item)
    }
}