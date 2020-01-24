package com.fucukur.big_project.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.fucukur.big_project.model.DogBreed
import com.fucukur.big_project.model.DogsApiServices
import io.reactivex.Scheduler
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import io.reactivex.observers.DisposableSingleObserver
import io.reactivex.schedulers.Schedulers

class ListViewModel : ViewModel() {

    private val dogsService = DogsApiServices()
    private val disposable = CompositeDisposable()


    val dogs = MutableLiveData<List<DogBreed>>()
    val dogsLoadError = MutableLiveData<Boolean>()
    val loading = MutableLiveData<Boolean>()

    fun  refresh() {
        fetchFromRemote()
    }

    private fun fetchFromRemote(){
        loading.value = true
        disposable.add(
            dogsService.getDogs()
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(object : DisposableSingleObserver<List<DogBreed>>(){
                    override fun onSuccess(dogList: List<DogBreed>) {
                        dogs.value = dogList
                        dogsLoadError.value = false
                        loading.value = false
                    }

                    override fun onError(e: Throwable) {
                        dogsLoadError.value = true
                        loading.value = false
                        e.printStackTrace()
                    }

                })
        )
    }

    override fun onCleared() {
        super.onCleared()
        disposable.clear()
    }
}

//DummyData() {
//    val dog1 = DogBreed("1","Corgi","15 yıl","breadGroup","bredFor","temprement","")
//    val dog2 = DogBreed("2","Rot","16 yıl","breadGroup","bredFor","temprement","")
//    val dog3 = DogBreed("3","Pit","17 yıl","breadGroup","bredFor","temprement","")

//    val dogList:ArrayList<DogBreed> = arrayListOf<DogBreed>(dog1,dog2,dog3)

//    dogs.value = dogList
//    dogsLoadError.value = false
//    loading.value = false
//}