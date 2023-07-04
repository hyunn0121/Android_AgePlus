package com.example.finalproject_ageplus

import android.app.Activity
import android.content.Intent
import android.graphics.BitmapFactory
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import androidx.activity.result.contract.ActivityResultContracts
import com.example.finalproject_ageplus.databinding.ActivityDocBinding

lateinit var binding : ActivityDocBinding

class DocActivity : AppCompatActivity() {
    lateinit var binding : ActivityDocBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDocBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val mystr = intent.getStringExtra("data")
        binding.myData.text = mystr

        binding.saveDocument.setOnClickListener {
            intent.putExtra("result", binding.documentEdit.text.toString())
            // .toString()을 호출해서 코틀린에서 사용할 수 있는 자료 타입으로 변경
            setResult(Activity.RESULT_OK, intent) // 결과와 화면 되돌리기
            // 올바르게 이 엑티비티가 실행되었다고 값을 저장하고 호출했던(start())를 했던 곳으로 돌아가는 것
            finish() // 기존에 있던 Activity를 단순히 종료하고 싶을 땐 finish()
        }

        // 파일에 대한 입출력 관련 코드
        val requestGalleryLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()){
            try{
                val calRatio = calculateInSampleSize(it.data!!.data!!,
                    resources.getDimensionPixelSize(R.dimen.imgSize),
                    resources.getDimensionPixelSize(R.dimen.imgSize) )
                val option = BitmapFactory.Options()
                option.inSampleSize = calRatio
                var inputStream = contentResolver.openInputStream(it.data!!.data!!)
                val bitmap = BitmapFactory.decodeStream(inputStream, null, option)
                inputStream!!.close() // .close()로 파일을 종료(해주는 습관 필요)
                inputStream = null
                // 여기까지가 읽어오는 작업

                bitmap?.let {
                    binding.documentImg.setImageBitmap(bitmap)
                } ?: let{ Log.d("mobileApp", "bitmap NULL")}
            } catch (e:Exception) { e.printStackTrace() }
        }

        binding.buttonEdit.setOnClickListener {
            val intent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI) // intent 변수에 Intent() 호출해서 사진 하나를 가져오기 위해서 ACTION_PICK 사용, MediaStore를 통해서 uri를 가져오기
            // startActivity(intent) -> 사진을 가져와야하기 때문에 startActivity()를 사용하면 안됨
            intent.type = "image/*" // 가져오기 전에 가져올 타입을 image만 가져오도록 타입을 지정
            requestGalleryLauncher.launch(intent) //위에서 파일 입출력에 대해서 만든 변수에 시작하겠다는 .launch()하면 됨
        }
    }

    // 갤러리에서 사진을 불러오는 것 (+ 불러온 이미지 크기 조정)
    private fun calculateInSampleSize(fileUri: Uri, reqWidth: Int, reqHeight: Int): Int {
        val options = BitmapFactory.Options()
        options.inJustDecodeBounds = true
        try {
            var inputStream = contentResolver.openInputStream(fileUri)

            //inJustDecodeBounds 값을 true 로 설정한 상태에서 decodeXXX() 를 호출.
            //로딩 하고자 하는 이미지의 각종 정보가 options 에 설정 된다.
            BitmapFactory.decodeStream(inputStream, null, options)
            inputStream!!.close()
            inputStream = null
        } catch (e: Exception) {
            e.printStackTrace()
        }
        //비율 계산........................
        val (height: Int, width: Int) = options.run { outHeight to outWidth }
        var inSampleSize = 1
        //inSampleSize 비율 계산
        if (height > reqHeight || width > reqWidth) {

            val halfHeight: Int = height / 2
            val halfWidth: Int = width / 2

            while (halfHeight / inSampleSize >= reqHeight && halfWidth / inSampleSize >= reqWidth) {
                inSampleSize *= 2
            }
        }
        return inSampleSize
    }
}