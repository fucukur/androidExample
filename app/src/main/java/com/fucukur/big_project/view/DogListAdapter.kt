package com.fucukur.big_project.view

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import com.fucukur.big_project.R
import com.fucukur.big_project.model.DogBreed
import com.fucukur.big_project.util.getProgressDrawable
import com.fucukur.big_project.util.loadImage
import kotlinx.android.synthetic.main.item_dog.view.*

class DogListAdapter(val dogList: ArrayList<DogBreed>) : RecyclerView.Adapter<DogListAdapter.DogViewHolder>() {

    fun updateDogList(newDogList: List<DogBreed>){
        dogList.clear()
        dogList.addAll(newDogList)
        notifyDataSetChanged()
    }

    class DogViewHolder(var view: View) : RecyclerView.ViewHolder(view)

    override fun onBindViewHolder(holder: DogViewHolder, position: Int) {
        holder.view.name.text = dogList[position].dogBreed
        holder.view.lifespan.text = dogList[position].lifeSpan
        holder.view.setOnClickListener{
            Navigation.findNavController(it).navigate(ListFragmentDirections.actionDetailFragment())
        }
        holder.view.imageView.loadImage(dogList[position].imageUrl, getProgressDrawable(holder.view.imageView.context))
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DogViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view = inflater.inflate(R.layout.item_dog,parent,false)
        return DogViewHolder(view)
    }

    override fun getItemCount() = dogList.size

}