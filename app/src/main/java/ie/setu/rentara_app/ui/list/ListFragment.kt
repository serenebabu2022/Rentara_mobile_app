package ie.setu.rentara.ui.list

import android.os.Bundle
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.MenuHost
import androidx.core.view.MenuProvider
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.navigation.ui.NavigationUI
import ie.setu.rentara.ui.listings.ListingsViewModel
import ie.setu.rentara_app.R
import ie.setu.rentara_app.databinding.FragmentListBinding
import ie.setu.rentara_app.main.RentaraXApp
import ie.setu.rentara_app.models.RentaraModel
import ie.setu.rentara_app.ui.auth.LoggedInViewModel

class ListFragment : Fragment() {
    var totalListings = 0
    private var _fragBinding: FragmentListBinding? = null
    // This property is only valid between onCreateView and onDestroyView.
    private val fragBinding get() = _fragBinding!!
    private lateinit var listViewModel: ListViewModel

    private val listingsViewModel: ListingsViewModel by activityViewModels()
    private val loggedInViewModel : LoggedInViewModel by activityViewModels()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        _fragBinding = FragmentListBinding.inflate(inflater, container, false)
        val root = fragBinding.root
        activity?.title = getString(R.string.action_list)
        setupMenu()
        listViewModel = ViewModelProvider(this).get(ListViewModel::class.java)
        listViewModel.observableStatus.observe(viewLifecycleOwner, Observer {
                status -> status?.let { render(status) }
        })

//        fragBinding.progressBar.max = 10000
        fragBinding.amountPicker.minValue = 1
        fragBinding.amountPicker.maxValue = 1000

        fragBinding.amountPicker.setOnValueChangedListener { _, _, newVal ->
            //Display the newly selected number to paymentAmount
            fragBinding.paymentAmount.setText("$newVal")
        }
        setButtonListener(fragBinding)
        return root;
    }

    private fun setupMenu() {
        (requireActivity() as MenuHost).addMenuProvider(object : MenuProvider {
            override fun onPrepareMenu(menu: Menu) {
                // Handle for example visibility of menu items
            }

            override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
                menuInflater.inflate(R.menu.menu_list, menu)
            }
            override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
                // Validate and handle the selected menu item
                println("there")
                return NavigationUI.onNavDestinationSelected(menuItem,
                    requireView().findNavController())
            }       }, viewLifecycleOwner, Lifecycle.State.RESUMED)
    }

    private fun render(status: Boolean) {
        when (status) {
            true -> {
                view?.let {
                    //Uncomment this if you want to immediately return to Report
                    //findNavController().popBackStack()
                }
            }
            false -> Toast.makeText(context,getString(R.string.listingError),Toast.LENGTH_LONG).show()
        }
    }

    fun setButtonListener(layout: FragmentListBinding) {
        layout.listButton.setOnClickListener {
            val amount = if (layout.paymentAmount.text.isNotEmpty())
                layout.paymentAmount.text.toString().toInt()
            else layout.amountPicker.value
            if(amount!==0){
                val rentalType = if(fragBinding.rentalPeriodType.checkedRadioButtonId == R.id.Daily)
                    "Daily" else "Weekly"
//                app.listStore.create(RentaraModel(rentalPeriodType = rentalType, price = amount))
//                layout.rentalPeriodType.tex=rentalType
                listViewModel.addListing(RentaraModel(rentalPeriodType = rentalType , price = amount,email = loggedInViewModel.liveFirebaseUser.value?.email!!))
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _fragBinding = null
    }
    override fun onResume() {
        super.onResume()
        //try implementing updation of listings count
//        listingsViewModel.rentalsList.observe(viewLifecycleOwner, { rentals ->
//            // Filter rentals by user email (replace with your logic to get user email)
//            val userRentals = rentals.filter { it.email == userEmail }
//
//            // Display the number of listings
//            val numListings = userRentals.count()
//            val listingsText = "You have $numListings listings."
//            // Update UI with listingsText
//        })
//        totalListings = listingsViewModel.observableRentalsList.value!!.sumOf { it.email==loggedInViewModel.liveFirebaseUser.value?.email!! }
//
////        val listingsViewModel = ViewModelProvider(this).get(ListingsViewModel::class.java)
//        listingsViewModel.observableRentalsList.observe(viewLifecycleOwner, Observer {
////            totalDonated = reportViewModel.observableDonationsList.value!!.sumOf { it.amount }
////            fragBinding.
////            fragBinding.totalSoFar.text = getString(R.string.totalSoFar,totalDonated)
//        })
    }
}
