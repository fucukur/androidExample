package com.fucukur.big_project.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.fucukur.big_project.model.DogBreed

class ListViewModel : ViewModel() {

    val dogs = MutableLiveData<List<DogBreed>>()
    val dogsLoadError = MutableLiveData<Boolean>()
    val loading = MutableLiveData<Boolean>()

    fun  refresh() {
        val dog1 = DogBreed("1","Corgi","15 yıl","breadGroup","bredFor","temprement","")
        val dog2 = DogBreed("2","Rot","16 yıl","breadGroup","bredFor","temprement","")
        val dog3 = DogBreed("3","Pit","17 yıl","breadGroup","bredFor","temprement","")

        val dogList:ArrayList<DogBreed> = arrayListOf<DogBreed>(dog1,dog2,dog3)

        dogs.value = dogList
        dogsLoadError.value = false
        loading.value = false
    }
}