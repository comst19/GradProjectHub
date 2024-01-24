package com.project.fat.fragment.bottomNavigationActivity

import android.content.ContentValues.TAG
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.project.fat.adapter.RecyclerviewAdapter2
import com.project.fat.data.dto.TotalRankResponseModel
import com.project.fat.databinding.FragmentTotalRankingBinding
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
 * Use the [TotalRankingFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class TotalRankingFragment : Fragment() {
    lateinit var binding: FragmentTotalRankingBinding
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
        binding = FragmentTotalRankingBinding.inflate(layoutInflater)

        //getTopTotalRank()
        totalRank()

        return binding.root
    }
    fun totalRank(){
        RankingApiService!!.totalRank().enqueue(object : Callback<TotalRankResponseModel> {
            override fun onResponse (
                call: Call<TotalRankResponseModel>,
                response: Response<TotalRankResponseModel>
            ) {
                if (response.isSuccessful) {
                    val list = response.body()!!
                    val id = response.body()!![1].id
                    val monsterNum = response.body()!![1].monsterNum
                    val user = response.body()!![1].user
                    val distance = response.body()!![1].distance

                    binding.recyclerview.adapter = RecyclerviewAdapter2(list)
                    binding.recyclerview.layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)


                    Log.d(
                        TAG, "Id: $id" +
                                "\nMonsterNum: $monsterNum" +
                                "\nDistance: $distance" +
                                "\nlist: $list"
                    )

                }
            }

            override fun onFailure(call: Call<TotalRankResponseModel>, t: Throwable) {
                Log.e(TAG, "getOnFailure: ",t.fillInStackTrace() )
            }

        })
    }
    fun getTopTotalRank(){

        RankingApiService.getTopTotalRank().enqueue(object : Callback<TotalRankResponseModel>{
            override fun onResponse(
                call: Call<TotalRankResponseModel>,
                response: Response<TotalRankResponseModel>
            ) {
                if(response.isSuccessful){
                    val list = response.body()!!
                    val id = response.body()!![1].id
                    val monsterNum = response.body()!![1].monsterNum
                    val user = response.body()!![1].user
                    val distance = response.body()!![1].distance

                    binding.recyclerview.adapter = RecyclerviewAdapter2(list)
                    binding.recyclerview.layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)

                    Log.d(
                        TAG, "Id: $id" +
                                "\nMonsterNum: $monsterNum" +
                                "\nDistance: $distance" +
                                "\nUser Name: ${user.name}" +
                                "\n$list"
                    )
                }
            }

            override fun onFailure(call: Call<TotalRankResponseModel>, t: Throwable) {
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
         * @return A new instance of fragment TotalRankingFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            TotalRankingFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}