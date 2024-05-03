package ie.setu.rentara_app.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import ie.setu.rentara_app.R
import ie.setu.rentara_app.databinding.CardListingBinding
import ie.setu.rentara_app.models.RentaraModel

interface RentaraClickListener {
    fun onListingClick(rental: RentaraModel)
}
class RentaraXAdapter(private var rentals: ArrayList<RentaraModel>, private val listener: RentaraClickListener)
    : RecyclerView.Adapter<RentaraXAdapter.MainHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainHolder {
        val binding = CardListingBinding
            .inflate(LayoutInflater.from(parent.context), parent, false)

        return MainHolder(binding)
    }
    override fun onBindViewHolder(holder: MainHolder, position: Int) {
        val rental = rentals[holder.adapterPosition]
        holder.bind(rental,listener)
    }
    fun removeAt(position: Int) {
        rentals.removeAt(position)
        notifyItemRemoved(position)
    }
    override fun getItemCount(): Int = rentals.size
    inner class MainHolder(val binding : CardListingBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bind(rentals: RentaraModel,listener: RentaraClickListener) {
            //binding.paymentamount.text = donation.amount.toString()
            //binding.paymentmethod.text = donation.paymentmethod
            binding.root.tag=rentals
            binding.rentals = rentals
            binding.imageIcon.setImageResource(R.mipmap.ic_launcher_round)
            binding.root.setOnClickListener { listener.onListingClick(rentals) }
            binding.executePendingBindings()
        }
    }
}