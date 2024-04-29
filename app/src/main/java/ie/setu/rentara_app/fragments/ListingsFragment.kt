package ie.setu.rentara_app.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.navigation.ui.NavigationUI
import androidx.recyclerview.widget.LinearLayoutManager
import ie.setu.rentara_app.R
import ie.setu.rentara_app.adapters.ListXAdapter
import ie.setu.rentara_app.databinding.FragmentListingsBinding
import ie.setu.rentara_app.main.ListXApp

class ListingsFragment : Fragment() {
    lateinit var app: ListXApp
    private var _fragBinding: FragmentListingsBinding? = null
    private val fragBinding get() = _fragBinding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        app = activity?.application as ListXApp
        setHasOptionsMenu(true)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _fragBinding = FragmentListingsBinding.inflate(inflater, container, false)
        val root = fragBinding.root
        activity?.title = getString(R.string.myListingsTitle)
        fragBinding.recyclerView.setLayoutManager(LinearLayoutManager(activity))
        fragBinding.recyclerView.adapter = ListXAdapter(app.listStore.findAll())
        return root
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
    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu_my_listings, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return NavigationUI.onNavDestinationSelected(item,
            requireView().findNavController()) || super.onOptionsItemSelected(item)
    }
}
