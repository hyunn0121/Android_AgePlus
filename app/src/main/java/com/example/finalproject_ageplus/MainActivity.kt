package com.example.finalproject_ageplus

import android.app.AlertDialog
import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.widget.SearchView
import com.example.finalproject_ageplus.databinding.ActivityMainBinding
import com.google.android.material.navigation.NavigationView

class MainActivity : AppCompatActivity() {
    lateinit var binding: ActivityMainBinding
    lateinit var toolbar : ActionBarDrawerToggle


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        /*
        setSupportActionBar(binding.toolbar)
        toolbar = ActionBarDrawerToggle(this, binding.drawer, R.string.drawer_opened, R.string.drawer_closed)
        supportActionBar?.setDisplayHomeAsUpEnabled(true) // supportActionBar는 null일 수도 있기 때문에 ? 붙임
        toolbar.syncState()
         */

        setSupportActionBar(binding.toolbar)

        /*
        binding.mainDrawer.setNavigationItemSelectedListener(this)
         */

        // 로그인/회원가입 화면 이동
       binding.buttonAuth.setOnClickListener {
            startActivity(Intent(this, AuthActivity::class.java))
        }
    }

    /*
    // 뭔지 모르겠음 -> 추후 수정
    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        when(item.itemId) { // when을 이용해서 각 MenuItem을 구분
            R.id.MyPage -> {
                startActivity(Intent(this, MypageActivity::class.java)) // 이름, 나이, 성별 등 기본 정보 설정 페이지
            }
            R.id.manage_CV -> {
                startActivity(Intent(this, DocumentActivity::class.java)) //이력서 페이지
            }
            R.id.setting -> {
                // sharedPreference (글씨 크기, 글꼴, 배경색 등 지정 가능)
            }
        }
        return true
    }
     */

    override fun onCreateOptionsMenu(menu: Menu?): Boolean { // 변수 menu에 들어갈 Menu? 타입이 null이 가능해서 ?를 붙임 => 그 후로 이용하는 menu 변수에는 모두 다 ?를 꼭 붙여줘야함
        // menu 변수에 사용할 R.menu.menu_actionbar의 내용을 담아서 액티비티와 xml을 서로 연결하는 듯?
        menuInflater.inflate(R.menu.menu_actionbar, menu) // menuInflater를 통해 해당되는 menu_action.xml의 리소스 값을 불러오는 것

        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        /*
        if (toolbar.onOptionsItemSelected(item)) {
            return true
        }
         */

        when(item.itemId) { // item이 가지고 있는 itemId에 따라서 적절하게 처리
            R.id.menu_help -> {
                Log.d("mobileApp", "되는건가?")
                AlertDialog.Builder(this).run {
                    setTitle("도움말")
                    setIcon(android.R.drawable.ic_dialog_info)
                    setMessage("시니어를 위한 구인 구직 앱입니다.\n 시작하기를 원하신다면 로그인 또는 회원가입을 진행해주세요.\n" +
                                "다른 문의 사항이 있으시다면 " + "hyunn0121@duksung.ac.kr로 연락주세요.")
                    setPositiveButton("확인", null)
                    show()
                }
            }
        }

        return super.onOptionsItemSelected(item)
}
}