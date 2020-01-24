package com.fucukur.big_project.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.fucukur.big_project.model.DogBreed

class DetailViewModel: ViewModel()
{
    val dogLiveData = MutableLiveData<DogBreed>()

    fun fetch() {
        val dog = DogBreed("1","Corgi","15 yıl","breadGroup","bredFor","temprement","")
        dogLiveData.value = dog
    }
}