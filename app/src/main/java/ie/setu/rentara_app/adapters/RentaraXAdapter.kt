package ie.setu.rentara_app.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.net.toUri
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import ie.setu.rentara_app.R
import ie.setu.rentara_app.databinding.CardListingBinding
import ie.setu.rentara_app.databinding.ProductItemBinding
import ie.setu.rentara_app.models.RentaraModel

interface RentaraClickListener {
    fun onListingClick(rental: RentaraModel)
}
class RentaraXAdapter(private var listings: ArrayList<RentaraModel>,
                      private val listener: RentaraClickListener,
                      private val readOnly: Boolean)
    : RecyclerView.Adapter<RentaraXAdapter.MainHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainHolder {
        val binding = CardListingBinding
            .inflate(LayoutInflater.from(parent.context), parent, false)

        return MainHolder(binding,readOnly)
    }

    override fun onBindViewHolder(holder: MainHolder, position: Int) {
        val listing = listings[holder.adapterPosition]
        holder.bind(listing,listener)
    }

    fun removeAt(position: Int) {
        listings.removeAt(position)
        notifyItemRemoved(position)
    }

    override fun getItemCount(): Int = listings.size

    inner class MainHolder(val binding : CardListingBinding, private val readOnly : Boolean) :
        RecyclerView.ViewHolder(binding.root) {

        val readOnlyRow = readOnly

        fun bind(listing: RentaraModel, listener: RentaraClickListener) {
            binding.root.tag = listing
            binding.rentals = listing
            Picasso.get().load(listing.productPic.toUri())
                .resize(200, 200)
//                .transform(customTransformation())
                .centerCrop()
                .into(binding.imageIcon)
            binding.root.setOnClickListener { listener.onListingClick(listing) }
            binding.executePendingBindings()
        }
    }
}