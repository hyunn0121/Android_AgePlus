package com.example.finalproject_ageplus

import android.content.Context
import android.content.Intent
import android.os.Bundle
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.finalproject_ageplus.databinding.FragmentXmlBinding

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [XmlFragment.newInstance] factory method to
 * create an instance of this fragment.
 */

class XmlFragment : Fragment() {
    lateinit var binding : FragmentXmlBinding

    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentXmlBinding.inflate(inflater, container, false)
        var mutableList: MutableList<myItem>

        val call: Call<responseInfo> = MyApplication.networkServiceXml.getXmlList(
            "ON7INoUej/s14fgB2der7dkpkWyd8pctA3FtlGkaj3+55JyPUHlvUnMuu5IExHbFKyS8hIhKa2+WHiRu5ZjqsA==",
            1,
            20
            // 오류나면 다시 pageSize 10으로 바꾸기...
        )

        call?.enqueue(object : Callback<responseInfo> {
            override fun onResponse(call: Call<responseInfo>, response: Response<responseInfo>) {
                if(response.isSuccessful){
                    Log.d("mobileApp", "$response")
                    binding.xmlRecyclerView.layoutManager = LinearLayoutManager(activity)
                    binding.xmlRecyclerView.adapter = XmlAdapter(activity as Context, response.body()!!.body!!.items!!.item)
                }
            }

            override fun onFailure(call: Call<responseInfo>, t: Throwable) {
                Log.d("mobileApp", "onFailure ${call.request()}")
            }
        })

         mutableList = mutableListOf<myItem>()
         binding.xmlRecyclerView.layoutManager = LinearLayoutManager(activity)
         binding.xmlRecyclerView.adapter = XmlAdapter(activity as Context, mutableList)
         // binding.xmlRecyclerView.adapter = XmlAdapter(activity as Context, response.body()!!.body!!.items.item_)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        var mutableList: MutableList<myItem>
        mutableList = mutableListOf<myItem>()

        // 어댑터 생성
        val reportRecyclerAdapter = XmlAdapter(activity as Context, mutableList)
        binding.xmlRecyclerView.adapter = reportRecyclerAdapter
        binding.xmlRecyclerView.layoutManager = LinearLayoutManager(this.activity)
        
        val intent = Intent(this.context,DetailActivity::class.java)

        //메서드에 객체 전달
        reportRecyclerAdapter.setOnItemclickListener(object: XmlAdapter.OnItemClickListener {
            override fun onItemClick(view: View, position: Int) {
                intent.putExtra("clickedReport_doctor", mutableList[position].recrtTitle)
                intent.putExtra("clickedReport_hospital", mutableList[position].workPlcNm)
                intent.putExtra("clickedReport_reportTime", mutableList[position].toDd)
                intent.putExtra("clickedReport_symptom", mutableList[position].frDd)
                startActivity(intent)
            }
        })
    }

    /*
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val binding = FragmentXmlBinding.inflate(inflater, container, false)

        val returnType = arguments?.getString("returnType")
        val call: Call<responseInfo> = MyApplication.networkServiceXml.getXmlList(
            "ON7INoUej/s14fgB2der7dkpkWyd8pctA3FtlGkaj3+55JyPUHlvUnMuu5IExHbFKyS8hIhKa2+WHiRu5ZjqsA==",
            1,
            10
        )

        call?.enqueue(object : Callback<responseInfo> {
            override fun onResponse(call: Call<responseInfo>, response: Response<responseInfo>) {
                if (response.isSuccessful) {
                    Log.d("mobileApp", "$response")
                    binding.xmlRecyclerView.layoutManager = LinearLayoutManager(activity)
                    binding.xmlRecyclerView.adapter =
                        XmlAdapter(activity as Context, response.body()!!.body!!.items!!.item)
                }
            }

            override fun onFailure(call: Call<responseInfo>, t: Throwable) {
                Log.d("mobileApp", "onFailure ${call.request()}")
            }
        })
        return binding.root
    }
     */

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment XmlFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            XmlFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}