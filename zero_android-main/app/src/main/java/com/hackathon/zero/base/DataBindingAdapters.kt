package com.hackathon.zero.base

import android.widget.TextView
import androidx.databinding.BindingAdapter
import org.w3c.dom.Text

@BindingAdapter("bind:lengthText", "bind:maxLength")
fun setLengthText(view: TextView, length: Int, max: Int) {
    view.text = "$length/$max"
}

@BindingAdapter("bind:heightFormat")
fun setHeightFormat(view: TextView, height: Int) {
    view.text = "${height}cm"
}

@BindingAdapter("bind:weightKgFormat")
fun setWeightFormat(view: TextView, weight: Int) {
    view.text = "${weight}kg"
}

@BindingAdapter("bind:weightGFormat")
fun setWeightGFormat(view: TextView, weight: Int) {
    view.text = "${weight}g"
}

@BindingAdapter("bind:capacityFormat")
fun setCapacity(view:TextView, capacity: Int) {
    view.text = "${capacity}ml"
}

@BindingAdapter("bind:calorieFormat")
fun setCalorie(view:TextView, calorie: Int){
    view.text = "${calorie}Kcal"
}