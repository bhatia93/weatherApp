package com.example.weather.view

import WeatherData
import WeatherInfoViewModel
import android.content.Context
import android.os.Bundle
import android.view.KeyEvent
import android.view.View
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import com.bumptech.glide.Glide
import com.example.weather.model.WeatherInfoModel
import com.example.weather.model.WeatherInfoModelImpl
import com.example.weather.utils.AppPreference
import com.example.weatherapp.R
import com.example.weatherapp.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity(), SearchCityAdapter.SearchNameHistoryClickListener {
    private lateinit var model: WeatherInfoModel
    private lateinit var viewModel: WeatherInfoViewModel
    private lateinit var binding: ActivityMainBinding
    private var searchNamesArrayReverse: ArrayList<String> = ArrayList()
    private var searchNamesArray: ArrayList<String> = ArrayList()
    private var searchCity: String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        model = WeatherInfoModelImpl(applicationContext)
        // initialize ViewModel
        viewModel = ViewModelProvider(this).get(WeatherInfoViewModel::class.java)

        setLiveDataListeners()
        AppPreference.getArrayPreference(AppPreference.SAVE_NAMES_SEARCH_NAME, this)?.let {
            searchNamesArray = it
        }
        if (searchNamesArray.isNotEmpty()) {
            binding.rvRecentSearch.visibility = View.VISIBLE
            searchNamesArrayReverse.addAll(searchNamesArray)
            searchNamesArrayReverse.reverse()
            val adapter =
                SearchCityAdapter(
                    this,
                    searchNamesArrayReverse,
                    this
                )
            binding.rvRecentSearch.adapter = adapter
        } else {
            binding.rvRecentSearch.visibility = View.GONE
        }

        binding.edtSearch.setOnEditorActionListener { _, actionId, event ->
            if (event != null && event.keyCode == KeyEvent.KEYCODE_ENTER ||
                actionId == EditorInfo.IME_ACTION_NEXT
            ) {
                let {
                    binding.edtSearch.clearFocus()
                    callWeatherApi()
                }
                binding.edtSearch.hideKeyboard()
            }
            binding.edtSearch.hideKeyboard()
            false
        }
        binding.edtSearch.setOnClickListener {
            callWeatherApi()
        }

    }

    private fun callWeatherApi() {
        // fetch weather data
        viewModel.getWeather(binding.edtSearch.text.toString(), model)
        binding.edtSearch.hideKeyboard()
    }

    private fun setLiveDataListeners() {
        /**
         * ProgressBar visibility will be handle by LiveData. ViewModel will decide
         * visibility of progressbar
         *
         */
        viewModel.progressBarLiveData.observe(this) { isShowLoader ->
            if (isShowLoader)
                binding.progressBar.visibility = View.VISIBLE
            else
                binding.progressBar.visibility = View.GONE
        }

        viewModel.weatherInfoLiveData.observe(this) { weatherData ->
            setWeatherInfo(weatherData)
            if (searchCity.length > 1) {
                if (searchNamesArray.size > 4 && !searchNamesArray.contains(searchCity)) {
                    searchNamesArray.removeAt(0)
                }
                if (!searchNamesArray.contains(searchCity))
                    searchNamesArray.add(searchCity)
                AppPreference.seArrayPreference(
                    AppPreference.SAVE_NAMES_SEARCH_NAME,
                    searchNamesArray, this
                )
            }
        }

        viewModel.weatherInfoFailureLiveData.observe(this) { errorMessage ->
            binding.outputGroup.visibility = View.GONE
            binding.tvErrorMessage.visibility = View.VISIBLE
            binding.tvErrorMessage.text = errorMessage
        }
    }

    private fun setWeatherInfo(weatherData: WeatherData) {
        binding.outputGroup.visibility = View.VISIBLE
        binding.tvErrorMessage.visibility = View.GONE

        binding.layoutWeatherBasic.tvDateTime.text = weatherData.dateTime
        binding.layoutWeatherBasic.tvTemperature.text = weatherData.temperature
        binding.layoutWeatherBasic.tvCityCountry.text = weatherData.cityAndCountry
        Glide.with(this).load(weatherData.weatherConditionIconUrl)
            .into(binding.layoutWeatherBasic.ivWeatherCondition)
        binding.layoutWeatherBasic.tvWeatherCondition.text =
            weatherData.weatherConditionIconDescription

        binding.layoutWeatherAdditional.tvHumidityValue.text = weatherData.humidity
        binding.layoutWeatherAdditional.tvPressureValue.text = weatherData.pressure
        binding.layoutWeatherAdditional.tvVisibilityValue.text = weatherData.visibility

        binding.layoutSunsetSunrise.tvSunriseTime.text = weatherData.sunrise
        binding.layoutSunsetSunrise.tvSunsetTime.text = weatherData.sunset
    }

    private fun View.hideKeyboard() {
        val inputManager =
            context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        inputManager.hideSoftInputFromWindow(windowToken, 0)
    }
    override fun onNameSelected(name: String, selectedPosition: Int) {
        if (name != "") {
            binding.edtSearch.setText(name, true)
        }
    }

}