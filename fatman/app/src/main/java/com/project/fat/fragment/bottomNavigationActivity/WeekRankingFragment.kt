package com.project.fat.fragment.bottomNavigationActivity

import android.content.ContentValues.TAG
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.gms.common.util.WorkSourceUtil.add
import com.project.fat.adapter.RecyclerviewAdapter
import com.project.fat.data.dto.SignInResponse

import com.project.fat.data.dto.WeekRankResponseModel

import com.project.fat.databinding.FragmentWeekRankingBinding
import com.project.fat.retrofit.client.RankObject
import com.project.fat.retrofit.api_interface.RankService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [WeekRankingFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class WeekRankingFragment : Fragment() {
    lateinit var binding: FragmentWeekRankingBinding
    private var RankingApiService: RankService = RankObject.getApiService()

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
        binding = FragmentWeekRankingBinding.inflate(layoutInflater)

        getTopWeekRank("2023","7")
        //getWeekRank()

        return binding.root
    }
    fun getWeekRank(){
        RankingApiService.getWeekRank().enqueue(object : Callback<ArrayList<WeekRankResponseModel>>{
            override fun onResponse(
                call: Call<ArrayList<WeekRankResponseModel>>,
                response: Response<ArrayList<WeekRankResponseModel>>
            ) {
                if(response.isSuccessful){
                    val list = response.body()!!

                    binding.recyclerview.adapter = RecyclerviewAdapter(list)
                    binding.recyclerview.layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)

                    val id = response.body()!![0].id
                    val monsterNum = response.body()!![0].monsterNum
                    val user = response.body()!![0].user
                    val distance = response.body()!![0].distance
                    val yearNum = response.body()!![0].yearNum
                    val weekNum = response.body()!![0].weekNum
                    Log.d(
                        TAG, "Id: $id" +
                                "\nMonsterNum: $monsterNum" +
                                "\nDistance: $distance" +
                                "\nUser Name: ${user.name}" +
                                "\nYear: $yearNum" +
                                "\nWeek: $weekNum"
                    )
                }
                else
                    Log.d(TAG, response.code().toString())
            }

            override fun onFailure(call: Call<ArrayList<WeekRankResponseModel>>, t: Throwable) {
                Log.e(TAG, "getOnFailure: ",t.fillInStackTrace() )
            }
        })
    }

    fun getTopWeekRank(year: String, week: String){
        RankingApiService.getTopWeekRank(year, week).enqueue(object : Callback<ArrayList<WeekRankResponseModel>>{
            override fun onResponse(
                call: Call<ArrayList<WeekRankResponseModel>>,
                response: Response<ArrayList<WeekRankResponseModel>>
            ) {
                if(response.isSuccessful){
                    val list = response.body()!!
                    val id = response.body()!![1].id
                    val monsterNum = response.body()!![1].monsterNum
                    val user = response.body()!![1].user
                    val distance = response.body()!![1].distance
                    val yearNum = response.body()!![1].yearNum
                    val weekNum = response.body()!![1].weekNum

                    binding.recyclerview.adapter = RecyclerviewAdapter(list)
                    binding.recyclerview.layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)

                    Log.d(
                        TAG, "Id: $id" +
                                "\nMonsterNum: $monsterNum" +
                                "\nDistance: $distance" +
                                "\nUser Name: ${user.name}" +
                                "\nYear: $yearNum" +
                                "\nWeek: $weekNum"
                    )
                }
            }

            override fun onFailure(call: Call<ArrayList<WeekRankResponseModel>>, t: Throwable) {
                Log.e(TAG, "getOnFailure: ",t.fillInStackTrace() )
            }

        })
    }
    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment WeekRankingFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            WeekRankingFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}