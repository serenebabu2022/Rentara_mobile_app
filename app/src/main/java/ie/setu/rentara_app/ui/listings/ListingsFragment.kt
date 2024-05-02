package ie.setu.rentara.ui.listings

import android.app.AlertDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.core.view.MenuHost
import androidx.core.view.MenuProvider
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.NavigationUI
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.floatingactionbutton.FloatingActionButton
import ie.setu.rentara_app.R
import ie.setu.rentara_app.adapters.RentaraClickListener
import ie.setu.rentara_app.adapters.RentaraXAdapter
import ie.setu.rentara_app.databinding.FragmentListingsBinding
import ie.setu.rentara_app.main.RentaraXApp
import ie.setu.rentara_app.models.RentaraModel
import ie.setu.rentara_app.utils.createLoader
import ie.setu.rentara_app.utils.hideLoader
import ie.setu.rentara_app.utils.showLoader


class ListingsFragment : Fragment(),RentaraClickListener {
    lateinit var app: RentaraXApp
    private var _fragBinding: FragmentListingsBinding? = null
    lateinit var loader : AlertDialog
    private val fragBinding get() = _fragBinding!!
    private lateinit var listingsViewModel: ListingsViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _fragBinding = FragmentListingsBinding.inflate(inflater, container, false)
        val root = fragBinding.root
        loader = createLoader(requireActivity())
//        activity?.title = getString(R.string.myListingsTitle)
setupMenu()
        fragBinding.recyclerView.layoutManager = LinearLayoutManager(activity)
        listingsViewModel = ViewModelProvider(this).get(ListingsViewModel::class.java)
        showLoader(loader,"Downloading Donations")
        listingsViewModel.observableRentalsList.observe(viewLifecycleOwner, Observer {
                rentals ->
            rentals?.let {
                render(rentals)
                hideLoader(loader)
                checkSwipeRefresh()
            }
        })

        fragBinding.fab.setOnClickListener {
            val action = ListingsFragmentDirections.actionListingsFragmentToListFragment()
            findNavController().navigate(action)
        }
        return root
    }
    private fun setupMenu() {
        (requireActivity() as MenuHost).addMenuProvider(object : MenuProvider {
            override fun onPrepareMenu(menu: Menu) {
                // Handle for example visibility of menu items
            }

            override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
                menuInflater.inflate(R.menu.menu_my_listings, menu)
            }

            override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
                // Validate and handle the selected menu item
                return NavigationUI.onNavDestinationSelected(menuItem,
                    requireView().findNavController())
            }
        }, viewLifecycleOwner, Lifecycle.State.RESUMED)
    }
    private fun render(rentalsList: List<RentaraModel>) {
        fragBinding.recyclerView.adapter = RentaraXAdapter(rentalsList,this)
        if (rentalsList.isEmpty()) {
            fragBinding.recyclerView.visibility = View.GONE
            fragBinding.rentalsNotFound.visibility = View.VISIBLE
        } else {
            fragBinding.recyclerView.visibility = View.VISIBLE
            fragBinding.rentalsNotFound.visibility = View.GONE
        }
    }
    private fun checkSwipeRefresh() {
        if (fragBinding.swiperefresh.isRefreshing)
            fragBinding.swiperefresh.isRefreshing = false
    }

    override fun onResume() {
        super.onResume()
        listingsViewModel.load()
    }
    override fun onDestroyView() {
        super.onDestroyView()
        _fragBinding = null
    }

    override fun onListingClick(rental: RentaraModel) {
        val action = ListingsFragmentDirections.actionListingsFragmentToListingDetailFragment(rental.id)
        findNavController().navigate(action)
    }
}
