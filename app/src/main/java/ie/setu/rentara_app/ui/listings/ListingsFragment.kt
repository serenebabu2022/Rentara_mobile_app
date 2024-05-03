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
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.NavigationUI
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import ie.setu.rentara.ui.list.ListFragment
import ie.setu.rentara_app.R
import ie.setu.rentara_app.adapters.RentaraClickListener
import ie.setu.rentara_app.adapters.RentaraXAdapter
import ie.setu.rentara_app.databinding.FragmentListingsBinding
import ie.setu.rentara_app.main.RentaraXApp
import ie.setu.rentara_app.models.RentaraModel
import ie.setu.rentara_app.ui.auth.LoggedInViewModel
import ie.setu.rentara_app.utils.SwipeToDeleteCallback
import ie.setu.rentara_app.utils.SwipeToEditCallback
import ie.setu.rentara_app.utils.createLoader
import ie.setu.rentara_app.utils.hideLoader
import ie.setu.rentara_app.utils.showLoader


class ListingsFragment : Fragment(), RentaraClickListener {

    private var _fragBinding: FragmentListingsBinding? = null
    private val fragBinding get() = _fragBinding!!
    lateinit var loader : AlertDialog
    private val listingsViewModel: ListingsViewModel by activityViewModels()
    private val loggedInViewModel : LoggedInViewModel by activityViewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?
    ): View? {
        _fragBinding = FragmentListingsBinding.inflate(inflater, container, false)
        val root = fragBinding.root
        setupMenu()
        loader = createLoader(requireActivity())

        fragBinding.recyclerView.layoutManager = LinearLayoutManager(activity)
        fragBinding.fab.setOnClickListener {
            val action = ListingsFragmentDirections.actionListingsFragmentToListFragment()
            findNavController().navigate(action)
        }
        showLoader(loader,"Downloading Listings")
        listingsViewModel.observableRentalsList.observe(viewLifecycleOwner, Observer {
                listings ->
            listings?.let {
                render(listings as ArrayList<RentaraModel>)
                hideLoader(loader)
                checkSwipeRefresh()
            }
        })

        setSwipeRefresh()

        val swipeDeleteHandler = object : SwipeToDeleteCallback(requireContext()) {
            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                showLoader(loader,"Deleting Listing")
                val adapter = fragBinding.recyclerView.adapter as RentaraXAdapter
                adapter.removeAt(viewHolder.adapterPosition)
                listingsViewModel.delete(listingsViewModel.liveFirebaseUser.value?.uid!!,
                    (viewHolder.itemView.tag as RentaraModel).uid!!)

                hideLoader(loader)
            }
        }
        val itemTouchDeleteHelper = ItemTouchHelper(swipeDeleteHandler)
        itemTouchDeleteHelper.attachToRecyclerView(fragBinding.recyclerView)

        val swipeEditHandler = object : SwipeToEditCallback(requireContext()) {
            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                onListingClick(viewHolder.itemView.tag as RentaraModel)
            }
        }
        val itemTouchEditHelper = ItemTouchHelper(swipeEditHandler)
        itemTouchEditHelper.attachToRecyclerView(fragBinding.recyclerView)

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

    private fun render(rentalsList: ArrayList<RentaraModel>) {
        fragBinding.recyclerView.adapter = RentaraXAdapter(rentalsList,this)
        if (rentalsList.isEmpty()) {
            fragBinding.recyclerView.visibility = View.GONE
            fragBinding.rentalsNotFound.visibility = View.VISIBLE
        } else {
            fragBinding.recyclerView.visibility = View.VISIBLE
            fragBinding.rentalsNotFound.visibility = View.GONE
        }
    }

    override fun onListingClick(listing: RentaraModel) {
        val action = ListingsFragmentDirections.actionListingsFragmentToListingDetailFragment(listing.uid!!)
        findNavController().navigate(action)
    }

    private fun setSwipeRefresh() {
        fragBinding.swiperefresh.setOnRefreshListener {
            fragBinding.swiperefresh.isRefreshing = true
            showLoader(loader,"Downloading Listings")
            listingsViewModel.load()
        }
    }

    private fun checkSwipeRefresh() {
        if (fragBinding.swiperefresh.isRefreshing)
            fragBinding.swiperefresh.isRefreshing = false
    }

    override fun onResume() {
        super.onResume()
        showLoader(loader,"Downloading Listings")
        loggedInViewModel.liveFirebaseUser.observe(viewLifecycleOwner, Observer { firebaseUser ->
            if (firebaseUser != null) {
                listingsViewModel.liveFirebaseUser.value = firebaseUser
                listingsViewModel.load()
            }
        })
        //hideLoader(loader)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _fragBinding = null
    }
}