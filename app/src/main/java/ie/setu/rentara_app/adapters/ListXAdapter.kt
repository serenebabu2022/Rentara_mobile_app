package ie.setu.rentara_app.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import ie.setu.rentara_app.R
import ie.setu.rentara_app.databinding.CardListingBinding
import ie.setu.rentara_app.models.ListModel

class ListXAdapter constructor(private var rentals: List<ListModel>)
    : RecyclerView.Adapter<ListXAdapter.MainHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainHolder {
        val binding = CardListingBinding
            .inflate(LayoutInflater.from(parent.context), parent, false)

        return MainHolder(binding)
    }
    override fun onBindViewHolder(holder: MainHolder, position: Int) {
        val donation = rentals[holder.adapterPosition]
        holder.bind(donation)
    }
    override fun getItemCount(): Int = rentals.size
    inner class MainHolder(val binding : CardListingBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bind(rental: ListModel) {
            binding.paymentamount.text = rental.price.toString()
            binding.rentalPeriodType.text = rental.rentalPeriodType
            binding.imageIcon.setImageResource(R.mipmap.ic_launcher_round)
        }
    }
}