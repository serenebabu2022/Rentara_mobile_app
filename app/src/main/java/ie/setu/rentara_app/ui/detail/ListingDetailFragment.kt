package ie.setu.rentara_app.ui.detail

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.navigation.fragment.navArgs
import ie.setu.rentara.ui.list.ListViewModel
import ie.setu.rentara_app.R
import ie.setu.rentara_app.adapters.RentaraXAdapter
import ie.setu.rentara_app.databinding.FragmentListBinding
import ie.setu.rentara_app.databinding.FragmentListingDetailBinding
import ie.setu.rentara_app.models.RentaraModel

class ListingDetailFragment : Fragment() {
    private val args by navArgs<ListingDetailFragmentArgs>()
    private var _fragBinding: FragmentListingDetailBinding? = null
    private val fragBinding get() = _fragBinding!!
    private lateinit var listingDetailViewModel: ListingDetailViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _fragBinding = FragmentListingDetailBinding.inflate(inflater, container, false)
        val root = fragBinding.root
        listingDetailViewModel = ViewModelProvider(this).get(ListingDetailViewModel::class.java)
        listingDetailViewModel.observableListing.observe(viewLifecycleOwner, Observer { render()
        })
        return root
    }
    private fun render() {
        fragBinding.editTitle.setText("Title")
        fragBinding.editDescription.setText("desc")
        fragBinding.editPrice.setText("10")
        fragBinding.rentaravm = listingDetailViewModel
    }
    override fun onResume() {
        super.onResume()
        listingDetailViewModel.getListing(args.listingid)
    }
    override fun onDestroyView() {
        super.onDestroyView()
        _fragBinding = null
    }
}