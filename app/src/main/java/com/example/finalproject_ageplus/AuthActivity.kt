package com.example.finalproject_ageplus

import android.app.AlertDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.ActionBarDrawerToggle
import com.example.finalproject_ageplus.databinding.ActivityAuthBinding
import com.example.finalproject_ageplus.databinding.NavigationHeaderBinding
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.android.material.navigation.NavigationView
import com.google.firebase.auth.GoogleAuthProvider

class AuthActivity : AppCompatActivity() {
    lateinit var binding: ActivityAuthBinding
    lateinit var navHeader : NavigationHeaderBinding
    lateinit var toolvar : ActionBarDrawerToggle

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivityAuthBinding.inflate(layoutInflater)
        navHeader = NavigationHeaderBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.toolbar)

        changeVisibility("logout") // 로그아웃되어져 있는 상태를 먼저 보여줌

        binding.goSignInBtn.setOnClickListener{
            // 회원가입
            changeVisibility("signin")
        }

        binding.signBtn.setOnClickListener {
            //이메일,비밀번호 회원가입........................
            val email:String = binding.authEmailEditView.text.toString()
            val password:String = binding.authPasswordEditView.text.toString()
            MyApplication.auth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this) { task ->
                    if (task.isSuccessful) {
                        MyApplication.auth.currentUser?.sendEmailVerification()?.addOnCompleteListener {
                                sendTask ->
                            if (sendTask.isSuccessful) {
                                Toast.makeText(baseContext, "회원 가입을 위해 입력하신 이메일에 전송된 메일을 확인해주세요", Toast.LENGTH_LONG).show()
                                changeVisibility("logout")
                            }
                            else  {
                                Toast.makeText(baseContext, "메일 전송 실패 - 이메일 주소를 확인해주세요", Toast.LENGTH_LONG).show()
                                changeVisibility("logout")
                            }
                        }
                    }
                    else {
                        Toast.makeText(baseContext, "회원 가입 실패 - 이미 회원 가입된 이메일이거나 이메일을 잘못 입력하셨습니다.", Toast.LENGTH_LONG).show()
                        changeVisibility("logout")
                    }
                    binding.authEmailEditView.text.clear()
                    binding.authPasswordEditView.text.clear()
                }
        }

        binding.loginBtn.setOnClickListener {
            //이메일, 비밀번호 로그인.......................
            val email:String = binding.authEmailEditView.text.toString()
            val password:String = binding.authPasswordEditView.text.toString()

            MyApplication.auth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this) {task ->
                    if (task.isSuccessful) {
                        if (MyApplication.checkAuth()) {
                            MyApplication.email = email
                            changeVisibility("login")
                            Log.d("mobile App", "로그인 성공")
                            // Toast.makeText(baseContext, "로그인을 성공하였습니다", Toast.LENGTH_LONG)

                            // 네비게이션 헤더에 로그인한 이메일로 변경하기
                            navHeader.username.text = "${MyApplication.email}"

                            // 원하는 일 선택 페이지로 넘어가기
                            startActivity(Intent(this, FindActivity::class.java))
                        }
                        else {
                            Toast.makeText(baseContext, "이메일 입력이 잘못되었습니다.", Toast.LENGTH_LONG).show()
                        }
                    }
                    else {
                        Toast.makeText(baseContext, "로그인 실패 - 확인 후 다시 입력해주세요", Toast.LENGTH_LONG).show()
                        changeVisibility("logout")
                    }

                    // 다음 입력이 가능하도록 입력창 지우기
                    binding.authEmailEditView.text.clear()
                    binding.authPasswordEditView.text.clear()
                }
        }

        binding.logoutBtn.setOnClickListener {
            //로그아웃...........
            MyApplication.auth.signOut()
            MyApplication.email = null // 마지막 로그인했던 주소를 지워줘야함
            changeVisibility("logout")
        }

        val requestLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
            val task = GoogleSignIn.getSignedInAccountFromIntent(it.data)

            // ApiException 오류 : Google Play 서비스 호출이 실패했을 때 테스크에서 반환할 예외
            try {
                val account = task.getResult(ApiException::class.java)
                val credential = GoogleAuthProvider.getCredential(account.idToken, null)
                MyApplication.auth.signInWithCredential(credential)
                    .addOnCompleteListener(this) { task ->
                        if(task.isSuccessful) {
                            MyApplication.email = account.email
                            // changeVisibility("login")
                            finish() // 원래 프로그램으로 돌아오기
                            Log.d("mobileApp", "GoogleSignIn - Successful")
                        } else {
                            changeVisibility("logout")
                            Log.d("mobileApp",  "GoogleSignIn - NOT Successful")
                        }
                    }
            } catch (e: ApiException) {
                changeVisibility("logout")
                Log.d("mobileApp",  "GoogleSignIn - ${e.message}")
            }
        }
        binding.googleLoginBtn.setOnClickListener {
            //구글 로그인....................
            val gso : GoogleSignInOptions = GoogleSignInOptions
                .Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build()

            val signInIntent : Intent = GoogleSignIn.getClient(this, gso).signInIntent
            requestLauncher.launch(signInIntent)

            // 네비게이션 헤더에 로그인한 이메일로 변경하기
            Log.d("login", "${MyApplication.email}")
            navHeader.username.text = "${MyApplication.email} 회원님"

            // FindActivty로 넘어가기
            startActivity(Intent(this, FindActivity::class.java))
        }
    }

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
                    setTitle("로그인/회원가입 도움말")
                    setIcon(android.R.drawable.ic_dialog_info)
                    setMessage("회원가입 및 로그인에 어려움을 겪고 계시다면" + "hyunn0121@duksung.ac.kr로 연락주세요.")
                    setPositiveButton("확인", null)
                    show()
                }
            }
        }

        return super.onOptionsItemSelected(item)
    }

    fun changeVisibility(mode: String){
        if(mode === "signin"){
            binding.run {
                logoutBtn.visibility = View.GONE // GONE은 표시하지 않겠다는 의미
                goSignInBtn.visibility = View.GONE
                googleLoginBtn.visibility = View.GONE
                authEmailEditView.visibility = View.VISIBLE // VISIBLE은 표시를 의미
                authPasswordEditView.visibility = View.VISIBLE
                signBtn.visibility = View.VISIBLE
                loginBtn.visibility = View.GONE
            }
        }else if(mode === "login"){
            binding.run {
                authMainTextView.text = "${MyApplication.email} 님 반갑습니다."
                logoutBtn.visibility= View.VISIBLE // 로그인되어있을 땐 로그아웃만 보이도록 함
                goSignInBtn.visibility= View.GONE
                googleLoginBtn.visibility= View.GONE
                authEmailEditView.visibility= View.GONE
                authPasswordEditView.visibility= View.GONE
                signBtn.visibility= View.GONE
                loginBtn.visibility= View.GONE
            }

        }else if(mode === "logout"){
            binding.run {
                authMainTextView.text = "로그인 하거나 회원가입 해주세요."
                logoutBtn.visibility = View.GONE
                goSignInBtn.visibility = View.VISIBLE
                googleLoginBtn.visibility = View.VISIBLE
                authEmailEditView.visibility = View.VISIBLE
                authPasswordEditView.visibility = View.VISIBLE
                signBtn.visibility = View.GONE
                loginBtn.visibility = View.VISIBLE
            }
        }
    }
}

/*
class AuthActivity : AppCompatActivity() {
    lateinit var binding: ActivityAuthBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivityAuthBinding.inflate(layoutInflater)
        setContentView(binding.root)

        changeVisibility(intent.getStringExtra("data").toString()) // 로그아웃되어져 있는 상태를 먼저 보여줌

        binding.goSignInBtn.setOnClickListener{
            // 회원가입
            changeVisibility("signin")
        }

        binding.signBtn.setOnClickListener {
            //이메일,비밀번호 회원가입........................
            val email:String = binding.authEmailEditView.text.toString()
            val password:String = binding.authPasswordEditView.text.toString()
            MyApplication.auth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this) { task ->
                    if (task.isSuccessful) {
                        MyApplication.auth.currentUser?.sendEmailVerification()?.addOnCompleteListener {
                                sendTask ->
                            if (sendTask.isSuccessful) {
                                Toast.makeText(baseContext, "회원가입 성공! 이메일 확인", Toast.LENGTH_LONG).show()
                                changeVisibility("logout")
                            }
                            else  {
                                Toast.makeText(baseContext, "메일 전송 실패 ...", Toast.LENGTH_LONG).show()
                                changeVisibility("logout")
                            }
                        }
                    }
                    else {
                        Toast.makeText(baseContext, "회원가입 실패 ...", Toast.LENGTH_LONG).show()
                        changeVisibility("logout")
                    }
                    binding.authEmailEditView.text.clear()
                    binding.authPasswordEditView.text.clear()
                }
        }

        binding.loginBtn.setOnClickListener {
            //이메일, 비밀번호 로그인.......................
            val email:String = binding.authEmailEditView.text.toString()
            val password:String = binding.authPasswordEditView.text.toString()

            MyApplication.auth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this) {task ->
                    if (task.isSuccessful) {
                        if (MyApplication.checkAuth()) {
                            MyApplication.email = email
                            // changeVisibility("login")
                            finish()
                        }
                        else {
                            Toast.makeText(baseContext, "이메일 인증 실패 ...", Toast.LENGTH_LONG).show()
                        }
                    }
                    else {
                        Toast.makeText(baseContext, "로그인 실패 ...", Toast.LENGTH_LONG).show()
                        changeVisibility("logout")
                    }

                    // 다음 입력이 가능하도록 입력창 지우기
                    binding.authEmailEditView.text.clear()
                    binding.authPasswordEditView.text.clear()
                }
        }

        binding.logoutBtn.setOnClickListener {
            //로그아웃...........
            MyApplication.auth.signOut()
            MyApplication.email = null // 마지막 로그인했던 주소를 지워줘야함
            // changeVisibility("logout")
            finish() // 원래 프로그램으로 돌아오기
        }

        val requestLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
            val task = GoogleSignIn.getSignedInAccountFromIntent(it.data)

            // ApiException 오류 : Google Play 서비스 호출이 실패했을 때 테스크에서 반환할 예외
            try {
                val account = task.getResult(ApiException::class.java)
                val credential = GoogleAuthProvider.getCredential(account.idToken, null)
                MyApplication.auth.signInWithCredential(credential)
                    .addOnCompleteListener(this) { task ->
                        if(task.isSuccessful) {
                            MyApplication.email = account.email
                            // changeVisibility("login")
                            finish() // 원래 프로그램으로 돌아오기
                            Log.d("mobileApp", "GoogleSignIn - Successful")
                        } else {
                            changeVisibility("logout")
                            Log.d("mobileApp",  "GoogleSignIn - NOT Successful")
                        }
                    }
            } catch (e: ApiException) {
                changeVisibility("logout")
                Log.d("mobileApp",  "GoogleSignIn - ${e.message}")
            }
        }
        binding.googleLoginBtn.setOnClickListener {
            //구글 로그인....................
            val gso : GoogleSignInOptions = GoogleSignInOptions
                .Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build()
            val signInIntent : Intent = GoogleSignIn.getClient(this, gso).signInIntent
            requestLauncher.launch(signInIntent)
        }
    }

    fun changeVisibility(mode: String){
        if(mode.equals("signin")){
            binding.run {
                logoutBtn.visibility = View.GONE // GONE은 표시하지 않겠다는 의미
                goSignInBtn.visibility = View.GONE
                googleLoginBtn.visibility = View.GONE
                authEmailEditView.visibility = View.VISIBLE // VISIBLE은 표시를 의미
                authPasswordEditView.visibility = View.VISIBLE
                signBtn.visibility = View.VISIBLE
                loginBtn.visibility = View.GONE
            }
        }else if(mode.equals("login")){
            binding.run {
                authMainTextView.text = "${MyApplication.email} 님 반갑습니다."
                logoutBtn.visibility= View.VISIBLE // 로그인되어있을 땐 로그아웃만 보이도록 함
                goSignInBtn.visibility= View.GONE
                googleLoginBtn.visibility= View.GONE
                authEmailEditView.visibility= View.GONE
                authPasswordEditView.visibility= View.GONE
                signBtn.visibility= View.GONE
                loginBtn.visibility= View.GONE
            }

        }else if(mode.equals("logout")){
            binding.run {
                authMainTextView.text = "로그인 하거나 회원가입 해주세요."
                logoutBtn.visibility = View.GONE
                goSignInBtn.visibility = View.VISIBLE
                googleLoginBtn.visibility = View.VISIBLE
                authEmailEditView.visibility = View.VISIBLE
                authPasswordEditView.visibility = View.VISIBLE
                signBtn.visibility = View.GONE
                loginBtn.visibility = View.VISIBLE
            }
        }
    }
}
 */