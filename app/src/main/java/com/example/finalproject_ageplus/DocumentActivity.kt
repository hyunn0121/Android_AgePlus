package com.example.finalproject_ageplus

import android.content.Intent
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.graphics.BitmapFactory
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.PersistableBundle
import android.provider.MediaStore
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.finalproject_ageplus.databinding.ActivityDocumentBinding
import java.io.BufferedReader
import java.io.File
import java.io.OutputStreamWriter

class DocumentActivity : AppCompatActivity() {

    lateinit var binding: ActivityDocumentBinding
    lateinit var toolbar : ActionBarDrawerToggle
    lateinit var adapter: MyAdapter
    var datas: MutableList<String>? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityDocumentBinding.inflate(layoutInflater)
        setContentView(binding.root)

        /*
        // 툴바 붙이기
        setSupportActionBar(binding.toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true) // supportActionBar는 null일 수도 있기 때문에 ? 붙임
        toolbar.syncState()
         */

        // setSupportActionBar(binding.toolbar)

        val requestlauncher : ActivityResultLauncher<Intent>
                = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
            it.data!!.getStringExtra("result")?.let{
                datas?.add(it) // recyclerview에 들어가는 리스트만 변경
                adapter.notifyDataSetChanged() // recyclerview에게 값이 변경되었다는 것을 알려줘야함 -> recyclerview가 다시 값을 만드는 등의 동작

                var db: SQLiteDatabase = DBHelper(this).writableDatabase
                db.execSQL("insert into document_tbl (document) values (?)", arrayOf<String>(it))
                db.close()
            }
        }
        // ActivityResultLauncher를 사용하는 방법 : 변수에 담고, registerForActivityResult() 함수 호출

        binding.documentFab.setOnClickListener {
            var intent = Intent(this, DocActivity::class.java)
            intent.putExtra("data", "이력서 등록")

            requestlauncher.launch(intent)
        }

        // DB 읽기
        datas = mutableListOf<String>()
        val db:SQLiteDatabase = DBHelper(this).readableDatabase
        var cursor: Cursor = db.rawQuery("select * from document_tbl", null)

        while (cursor.moveToNext()) {
            datas?.add(cursor.getString(1))
        }
        db.close()

        val layoutManager = LinearLayoutManager(this)
        binding.mainRecyclerView.layoutManager=layoutManager
        adapter=MyAdapter(datas)
        binding.mainRecyclerView.adapter=adapter
        binding.mainRecyclerView.addItemDecoration(
            DividerItemDecoration(this, LinearLayoutManager.VERTICAL)
        )
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_setting, menu)

        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val file: File = File(filesDir, "todo_list.txt")
        when (item.itemId) {
            R.id.setting -> {
                val intent = Intent(this, SettingActivity::class.java)
                startActivity(intent)
            }
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onSaveInstanceState(outState: Bundle, outPersistentState: PersistableBundle) {
        super.onSaveInstanceState(outState, outPersistentState)
        outState.putStringArrayList("datas", ArrayList(datas))
    }
}

    /*
    binding.saveDocument.setOnClickListener {
        //seq는 어차피 DB에서 추가
        val mem = Member(0,
            binding.documentTitle.text.toString().trim(),
            binding.documentYear.text.toString().toInt(),
            binding.documentEdit.text.toString().trim())

        val dbHelper = DBHelper.getInstance(this,"member.db",)//.insert(mem)해도 됨
        dbHelper.insert(mem)

        binding.documentText.text = "제목 : " + binding.documentTitle.text +"\n" + "경력 : " + binding.documentEdit.text + "\n내용 :" + binding.documentEdit.text
    }

    binding.allDocument.setOnClickListener {
        startActivity(Intent(this, AlldocActivity::class.java))
    }

     */

        /*
        binding.saveDocument.setOnClickListener {

            /*
            val document = binding.documentEdit.text.toString()
            binding.documentText.text = document.toString()
             */

            // DB에 값 넣기
            val inputData = binding.documentEdit.text.toString()
            var db : SQLiteDatabase = DBHelper(this).writableDatabase
            Log.d("mobileApp", "잘되나?")
            db.execSQL(
                "insert into document_tbl (document) values (?)", arrayOf<String>(inputData)
            )

            // DB 읽기
            val db1:SQLiteDatabase = DBHelper(this).readableDatabase
            var cursor: Cursor = db1.rawQuery("select * from document_tbl", null)

            binding.documentText.text = cursor.toString()
            db.close()
        }
         */


    /*
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

     */
