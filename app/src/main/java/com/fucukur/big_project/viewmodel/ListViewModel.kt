package com.fucukur.big_project.viewmodel

import android.app.Application
import android.widget.Toast
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.fucukur.big_project.model.DogBreed
import com.fucukur.big_project.model.DogDao
import com.fucukur.big_project.model.DogDatabase
import com.fucukur.big_project.model.DogsApiServices
import com.fucukur.big_project.util.SharedPrefrencesHelper
import io.reactivex.Scheduler
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import io.reactivex.observers.DisposableSingleObserver
import io.reactivex.schedulers.Schedulers
import kotlinx.coroutines.launch

class ListViewModel(application: Application) : BaseViewModel(application) {

    private var prefHelper = SharedPrefrencesHelper(getApplication())
    private var refreshTime = 5 * 60 * 1000 * 1000 * 1000L
    private val dogsService = DogsApiServices()
    private val disposable = CompositeDisposable()


    val dogs = MutableLiveData<List<DogBreed>>()
    val dogsLoadError = MutableLiveData<Boolean>()
    val loading = MutableLiveData<Boolean>()

    fun  refresh() {
        val updateTime = prefHelper.getUpdateTime()
        if(updateTime != null && updateTime != 0L && System.nanoTime() - updateTime < refreshTime) {
            fetchFromDatabase()
        }
        else {
            fetchFromRemote()
        }
    }

    private fun fetchFromDatabase() {
        loading.value = true
        launch {
            val dogs = DogDatabase(getApplication()).dogDao().getAllDogs()
            dogRetrieved(dogs)
            Toast.makeText(getApplication(),"Database den geliyor ",Toast.LENGTH_SHORT).show()
        }
    }

    private fun fetchFromRemote(){
        loading.value = true
        disposable.add(
            dogsService.getDogs()
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(object : DisposableSingleObserver<List<DogBreed>>(){

                    override fun onSuccess(dogList: List<DogBreed>) {
                        storeDogsLocally(dogList)
                        Toast.makeText(getApplication(),"Servisten geliyor ",Toast.LENGTH_SHORT).show()
                    }

                    override fun onError(e: Throwable) {
                        dogsLoadError.value = true
                        loading.value = false
                        e.printStackTrace()
                    }

                })
        )
    }
    private fun dogRetrieved(dogList: List<DogBreed>){
        dogs.value = dogList
        dogsLoadError.value = false
        loading.value = false
    }

    private fun storeDogsLocally(list: List<DogBreed>){
        launch {
            val dao : DogDao = DogDatabase(getApplication()).dogDao()
            dao.deleteAllDogs()
            val result = dao.insertAll(*list.toTypedArray())
            var i = 0
            while (i < list.size) {
                list[i].uuid = result[i].toInt()
                ++i
            }
            dogRetrieved(list)
        }
        prefHelper.saveUpdateTime(System.nanoTime())
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