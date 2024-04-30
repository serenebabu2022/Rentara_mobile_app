package ie.setu.rentara.ui.listings

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
import androidx.navigation.ui.NavigationUI
import androidx.recyclerview.widget.LinearLayoutManager
import ie.setu.rentara_app.R
import ie.setu.rentara_app.adapters.RentaraXAdapter
import ie.setu.rentara_app.databinding.FragmentListingsBinding
import ie.setu.rentara_app.main.RentaraXApp

class ListingsFragment : Fragment() {
    lateinit var app: RentaraXApp
    private var _fragBinding: FragmentListingsBinding? = null
    private val fragBinding get() = _fragBinding!!
    private lateinit var listingsViewModel: ListingsViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        app = activity?.application as RentaraXApp
//        setHasOptionsMenu(true)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _fragBinding = FragmentListingsBinding.inflate(inflater, container, false)
        val root = fragBinding.root
        activity?.title = getString(R.string.myListingsTitle)
setupMenu()
        listingsViewModel =
            ViewModelProvider(this).get(ListingsViewModel::class.java)
        //val textView: TextView = root.findViewById(R.id.text_gallery)
        listingsViewModel.text.observe(viewLifecycleOwner, Observer {
            //textView.text = it
        })

        fragBinding.recyclerView.setLayoutManager(LinearLayoutManager(activity))
        fragBinding.recyclerView.adapter = RentaraXAdapter(app.rentaraStore.findAll())
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

    companion object {
        @JvmStatic
        fun newInstance() =
            ListingsFragment().apply {
                arguments = Bundle().apply { }
            }
    }
    override fun onDestroyView() {
        super.onDestroyView()
        _fragBinding = null
    }
//    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
//        inflater.inflate(R.menu.menu_my_listings, menu)
//        super.onCreateOptionsMenu(menu, inflater)
//    }
//    override fun onOptionsItemSelected(item: MenuItem): Boolean {
//        return NavigationUI.onNavDestinationSelected(item,
//            requireView().findNavController()) || super.onOptionsItemSelected(item)
//    }
}
