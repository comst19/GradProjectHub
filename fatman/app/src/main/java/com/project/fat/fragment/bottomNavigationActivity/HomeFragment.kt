package com.project.fat.fragment.bottomNavigationActivity

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import com.bumptech.glide.Glide
import com.project.fat.LoadingActivity
import com.project.fat.R
import com.project.fat.data.dto.GetHistoryResponse
import com.project.fat.databinding.FragmentHomeBinding
import com.project.fat.manager.SelectedFatmanManager
import com.project.fat.retrofit.api_interface.HistoryService
import com.project.fat.retrofit.client.HistoryRetrofit
import com.project.fat.manager.TokenManager
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.time.LocalDate

class HomeFragment : Fragment() {
    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        var nickname = arguments?.getString("nickname")
        var money = arguments?.getInt("money")

        binding.nickname.text = nickname
        binding.money.text = money.toString()

        // 밑의 주석으로 처리한 부분이 무한 루프를 도는 버그가 존재합니다. 아무래도 retrofit이 비동기 방식이라서 view가 정의되기 전에 view에 접근하다보니 생긴 버그 같습니다.
        /* **오류 전문**
        FATAL EXCEPTION: main
                                                                                                    Process: com.project.fat, PID: 27905
                                                                                                    java.lang.NullPointerException
                                                                                                    	at com.project.fat.fragment.bottomNavigationActivity.HomeFragment.onViewCreated(HomeFragment.kt:50)
                                                                                                    	at androidx.fragment.app.Fragment.performViewCreated(Fragment.java:3019)
                                                                                                    	at androidx.fragment.app.FragmentStateManager.createView(FragmentStateManager.java:551)
                                                                                                    	at androidx.fragment.app.FragmentStateManager.moveToExpectedState(FragmentStateManager.java:261)
                                                                                                    	at androidx.fragment.app.FragmentManager.executeOpsTogether(FragmentManager.java:1840)
                                                                                                    	at androidx.fragment.app.FragmentManager.removeRedundantOperationsAndExecute(FragmentManager.java:1758)
                                                                                                    	at androidx.fragment.app.FragmentManager.execPendingActions(FragmentManager.java:1701)
                                                                                                    	at androidx.fragment.app.FragmentManager.dispatchStateChange(FragmentManager.java:2849)
                                                                                                    	at androidx.fragment.app.FragmentManager.dispatchActivityCreated(FragmentManager.java:2784)
                                                                                                    	at androidx.fragment.app.FragmentController.dispatchActivityCreated(FragmentController.java:262)
                                                                                                    	at androidx.fragment.app.FragmentActivity.onStart(FragmentActivity.java:478)
                                                                                                    	at androidx.appcompat.app.AppCompatActivity.onStart(AppCompatActivity.java:251)
                                                                                                    	at android.app.Instrumentation.callActivityOnStart(Instrumentation.java:1435)
                                                                                                    	at android.app.Activity.performStart(Activity.java:8024)
                                                                                                    	at android.app.ActivityThread.handleStartActivity(ActivityThread.java:3475)
                                                                                                    	at android.app.servertransaction.TransactionExecutor.performLifecycleSequence(TransactionExecutor.java:221)
                                                                                                    	at android.app.servertransaction.TransactionExecutor.cycleToPath(TransactionExecutor.java:201)
                                                                                                    	at android.app.servertransaction.TransactionExecutor.executeLifecycleState(TransactionExecutor.java:173)
                                                                                                    	at android.app.servertransaction.TransactionExecutor.execute(TransactionExecutor.java:97)
                                                                                                    	at android.app.ActivityThread$H.handleMessage(ActivityThread.java:2066)
                                                                                                    	at android.os.Handler.dispatchMessage(Handler.java:106)
                                                                                                    	at android.os.Looper.loop(Looper.java:223)
                                                                                                    	at android.app.ActivityThread.main(ActivityThread.java:7656)
                                                                                                    	at java.lang.reflect.Method.invoke(Native Method)
                                                                                                    	at com.android.internal.os.RuntimeInit$MethodAndArgsCaller.run(RuntimeInit.java:592)
                                                                                                    	at com.android.internal.os.ZygoteInit.main(ZygoteInit.java:947)
        * */
//        HistoryRetrofit.getApiService()!!.getHistory(
//            accessToken = resources.getString(R.string.prefix_of_refresh_token) + TokenManager.getAccessToken()!!,
//            LocalDate.now()
//        ).enqueue(object : Callback<GetHistoryResponse> {
//            override fun onResponse(
//                call: Call<GetHistoryResponse>,
//                response: Response<GetHistoryResponse>
//            ) {
//                if (response.isSuccessful) {
//                    val responseData = response.body()
//                    val mn = responseData?.get(0)?.monsterNum
//                    binding.monsterRecordText.text = mn.toString()
//                } else {
//                    Log.w("Retrofit", "Response Not Successful ${response.code()}")
//                }
//            }
//
//            override fun onFailure(call: Call<GetHistoryResponse>, t: Throwable) {
//                Log.e("Retrofit", "Error")
//            }
//        })

        binding.move.setOnClickListener {
            val intent = Intent(activity, LoadingActivity::class.java)
            startActivity(intent)
        }

        lifecycleScope.launch {
            Log.d(
                "set avata to SelectedFatman",
                "binding = ${binding}\nbinding.root = ${binding.root}\nbinding.fatman = ${binding.fatman}"
            )
            SelectedFatmanManager.initSelectedFatmanManager(requireContext())
            Log.d(
                "set avata to SelectedFatman",
                "selectedFatmanImage = ${SelectedFatmanManager.getSelectedFatmanImageUrl()}"
            )
        }
    }

    override fun onStart() {
        super.onStart()
        setFatmanImage()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun setFatmanImage() {
        Log.d("setFatmanImage", "start")
        if(!requireActivity().isDestroyed){
            Glide
                .with(requireContext())
                .load(SelectedFatmanManager.getSelectedFatmanImageUrl())
                .into(binding.fatman)
        }
    }
}
