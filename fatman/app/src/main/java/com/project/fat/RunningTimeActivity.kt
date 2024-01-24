package com.project.fat

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Looper
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.lifecycle.lifecycleScope
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationCallback
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationResult
import com.google.android.gms.location.LocationServices
import com.google.android.gms.location.Priority
import com.project.fat.MapsActivity.Companion.toLocation
import com.project.fat.data.runningData.ResultDistanceTime
import com.project.fat.databinding.ActivityRunningTimeBinding
import com.project.fat.location.Distance
import com.project.fat.location.LocationProvider
import kotlinx.coroutines.Job
import kotlinx.coroutines.cancel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class RunningTimeActivity : AppCompatActivity() {
    private lateinit var binding : ActivityRunningTimeBinding
    private lateinit var timeJob : Job
    private lateinit var distanceJob : Job

    private lateinit var fusedLocationClient: FusedLocationProviderClient
    private lateinit var locationCallback: LocationCallback
    private lateinit var locationRequest : LocationRequest

    private var toLocationArray : DoubleArray? = null

    private var time = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRunningTimeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        toLocationArray = toLocation
        Log.d("toLocationArray", "toLocationArray : ${toLocationArray?.get(0)}, ${toLocationArray?.get(1)}")

        timeCoroutine(time)
        distanceCoroutine()

        binding.imageView.setOnClickListener {
            saveRunningFinalData()
            //임시
            fusedLocationClient.removeLocationUpdates(locationCallback)
            startActivity(Intent(this, ArActivity::class.java))
        }

        binding.pauseBtn.setOnClickListener {
            timeJob.cancel()
            distanceJob.cancel()
            LocationProvider.stopLocationUpdates()
            binding.pauseBtn.visibility = View.GONE
            binding.startBtn.visibility = View.VISIBLE
            binding.stopBtn.visibility = View.VISIBLE
        }

        binding.startBtn.setOnClickListener{
            timeCoroutine(time)
            distanceCoroutine()
            binding.stopBtn.visibility = View.GONE
            binding.startBtn.visibility = View.GONE
            binding.pauseBtn.visibility = View.VISIBLE
        }

        binding.stopBtn.setOnClickListener {
            time = 0
            binding.kilometer.text = "0.000"
            timeCoroutine(time)
            distanceCoroutine()
            binding.stopBtn.visibility = View.GONE
            binding.startBtn.visibility = View.GONE
            binding.pauseBtn.visibility = View.VISIBLE
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        LocationProvider.stopLocationUpdates()
    }

    private fun timeCalculate(time : Int) : String? {
        var timeString : String? = null

        if(time >= 0){
            timeString = (time/60).toString() + ":" + if((time%60) < 10) {
                "0" + (time%60).toString()
            } else {
                (time%60).toString()
            }
        }else{
            Log.d("time", "time < 0")
        }

        return timeString
    }

    private fun timeCoroutine(t : Int) {
        timeJob = lifecycleScope.launchWhenStarted {
            var tm = t
            while(true){
                val check = timeCalculate(tm)
                if(check.isNullOrBlank())
                {
                    time = tm
                    this.cancel()
                }else{
                    binding.time.text = check
                    delay(1000)
                    tm++
                    time = tm
                }
            }
        }
    }

    @SuppressLint("MissingPermission")
    private fun distanceCoroutine() {
        distanceJob = lifecycleScope.launch {
            //LocationProvider를 사용할 땐 정상 작동을 하지 않아 해결방안을 찾을 동안 개별적으로 둡니다.
            fusedLocationClient = LocationServices.getFusedLocationProviderClient(this@RunningTimeActivity)
            locationRequest = LocationRequest.Builder(Priority.PRIORITY_HIGH_ACCURACY, 1000).build()

            locationCallback = object : LocationCallback(){
                override fun onLocationResult(locationResult: LocationResult) {
                    for(location in locationResult.locations){
                        Log.d("onLocationResult in RunningTimeActivity", "${location.latitude}, ${location.longitude}")
                        val result = Distance.getDistance(location.latitude, location.longitude, toLocationArray?.get(0), toLocationArray?.get(1))
                        if(result == Distance.NOT_SIGNAL) {
                            LocationProvider.stopLocationUpdates()
                            Toast.makeText(this@RunningTimeActivity,
                                getString(R.string.error_lost_destination_latlng), Toast.LENGTH_SHORT).show()
                            startActivity(Intent(this@RunningTimeActivity, MapsActivity::class.java))
                            finish()
                            return
                        }
                        if(result.toDouble() < 0.005){
                            fusedLocationClient.removeLocationUpdates(locationCallback)
                            saveRunningFinalData()
                            startActivity(Intent(this@RunningTimeActivity, ArActivity::class.java))
                            finish()
                            return
                        }

                        //binding.test.text = "Latitude: ${location.latitude}\nLongitude: ${location.longitude}"
                        binding.kilometer.text = result
                    }
                }
            }

            fusedLocationClient.requestLocationUpdates(locationRequest, locationCallback, Looper.myLooper())
        }
    }

    private fun saveRunningFinalData() {
        runningFinalData = ResultDistanceTime(binding.time.text.toString(), binding.kilometer.text.toString())
    }

    companion object{
        var runningFinalData : ResultDistanceTime? = null
    }
}