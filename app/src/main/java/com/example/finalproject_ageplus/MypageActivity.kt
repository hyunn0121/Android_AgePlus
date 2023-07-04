package com.example.finalproject_ageplus

import android.app.AlertDialog
import android.app.DatePickerDialog
import android.app.ProgressDialog.show
import android.content.DialogInterface
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.DatePicker
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.core.content.ContextCompat.startActivity
import com.example.finalproject_ageplus.databinding.ActivityFindBinding
import com.example.finalproject_ageplus.databinding.ActivityMypageBinding

class MypageActivity : AppCompatActivity() {
    lateinit var binding: ActivityMypageBinding
    lateinit var toolbar : ActionBarDrawerToggle

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivityMypageBinding.inflate(layoutInflater)
        setContentView(binding.root)

        /*
        setSupportActionBar(binding.toolbar)
        toolbar = ActionBarDrawerToggle(this, binding.drawer, R.string.drawer_opened, R.string.drawer_closed)
        supportActionBar?.setDisplayHomeAsUpEnabled(true) // supportActionBar는 null일 수도 있기 때문에 ? 붙임
        toolbar.syncState()
         */

        setSupportActionBar(binding.toolbar)

        // 이벤트 핸들러 : 각 setPositiveButton, setNagativeButton 등의 버튼이 눌렸을 때 처리할 이벤트 핸들러를 alertHandler 변수에 담아서 정의
        val alertHandler = object : DialogInterface.OnClickListener { // OnClickListener는 꼭 onClick()을 재정의
            override fun onClick(dialog: DialogInterface?, which: Int) {
                when (which) { // when()을 이용해서 각 버튼을 구분
                    DialogInterface.BUTTON_POSITIVE -> {
                        Log.d("mobileApp", "DialogInterface.BUTTON_POSITIVE")
                    }
                    DialogInterface.BUTTON_NEGATIVE -> {
                        Log.d("mobileApp", "DialogInterface.BUTTON_NEGATIVE")
                    }
                    DialogInterface.BUTTON_NEUTRAL -> {
                        Log.d("mobileApp", "DialogInterface.BUTTON_NEUTRAL")
                    }
                }
            }
        }

        var myage = ""
        var ageItems = arrayOf<String>("40세 미만", "40-50세", "50-60세", "60-70세", "80-90세", "100세 이상")

        binding.buttonAge.setOnClickListener {
            AlertDialog.Builder(this).run {
                setTitle("나이를 선택하세요")
                // RadioButton에 해당하는setSingleChoiceItems는 setItems와 다르게 두번째에 기본값(ex. 1)을 설정해주어야함
                setSingleChoiceItems(ageItems, 0, object : DialogInterface.OnClickListener {
                    override fun onClick(dialog: DialogInterface?, which: Int) {
                        Log.d("mobileApp", "${ageItems[which]} 나이입니다.") // 현재 사용자가 선택한 값에 대한 정보가 which에 있음
                        myage = ageItems[which]
                        binding.textAge.text = "나이 : ${myage}"
                    }
                })
                setPositiveButton("Yes", alertHandler)
                show()
            }
        }

        var mygender = ""
        var genderItem = arrayOf<String>("남자", "여자")

        binding.buttonGender.setOnClickListener {
            AlertDialog.Builder(this).run {
                setTitle("성별을 선택하세요")
                setSingleChoiceItems(genderItem, 0, object : DialogInterface.OnClickListener {
                    override fun onClick(dialog: DialogInterface?, which: Int) {
                        Log.d("mobileApp", "${genderItem[which]} 나이입니다.") // 현재 사용자가 선택한 값에 대한 정보가 which에 있음
                        mygender = genderItem[which]
                        binding.textGender.text = "성별 : ${mygender}"
                    }
                })
                setPositiveButton("Yes", alertHandler)
                show()
            }
        }

        // 날짜 설정
        binding.buttonBirthday.setOnClickListener {
            DatePickerDialog(this, object : DatePickerDialog.OnDateSetListener {
                override fun onDateSet(view: DatePicker?, year: Int, month: Int, dayOfMonth: Int) {
                    binding.textBirth.text = "생년월일 : ${year}년 ${month + 1}월 ${dayOfMonth}일"
                    // showToast("${year}년 ${month + 1}월 ${dayOfMonth}일")
                }
            }, 2023, 3, 3).show()
        }

        var myPlace = ""
        var placeItem = arrayOf<String>("강원도", "경기도", "경상남도", "경상북도", "광주광역시", "대구광역시", "부산광역시",
            "서울특별시", "세종특별자치시", "울산광역시", "인천광역시", "전라남도", "전라북도", "제주특별자치도", "충청남도", "충청북도")

        binding.buttonPlace.setOnClickListener {
            AlertDialog.Builder(this).run {
                setTitle("거주하는 도시를 선택하세요")
                setSingleChoiceItems(placeItem, 0, object : DialogInterface.OnClickListener {
                    override fun onClick(dialog: DialogInterface?, which: Int) {
                        Log.d("mobileApp", "${placeItem[which]} 나이입니다.") // 현재 사용자가 선택한 값에 대한 정보가 which에 있음
                        myPlace = placeItem[which]
                        binding.textPlace.text = "거주지 : ${myPlace}"
                    }
                })
                setPositiveButton("Yes", alertHandler)
                show()
            }
        }

        var myCareer = ""
        var careerItem = arrayOf<String>("건축", "소방", "IT", "기타")

        binding.buttonCareer.setOnClickListener {
            AlertDialog.Builder(this).run {
                setTitle("선호하는 직업을 고르세요")
                setSingleChoiceItems(careerItem, 0, object : DialogInterface.OnClickListener {
                    override fun onClick(dialog: DialogInterface?, which: Int) {
                        Log.d("mobileApp", "${careerItem[which]} 나이입니다.") // 현재 사용자가 선택한 값에 대한 정보가 which에 있음
                        myCareer = careerItem[which]
                        binding.textCareer.text = "선호 직업 : ${myCareer}"
                    }
                })
                setPositiveButton("Yes", alertHandler)
                show()
            }
        }
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