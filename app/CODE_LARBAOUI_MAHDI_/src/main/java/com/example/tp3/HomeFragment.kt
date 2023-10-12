package com.example.tp3

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView.OnItemClickListener
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import android.widget.Button
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.tp3.Adapters.ParkingAdapter
import com.example.tp3.databinding.FragmentHomeBinding
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.DialogFragment
import com.bumptech.glide.manager.SupportRequestManagerFragment
import com.example.tp3.databinding.ActivityMainBinding.inflate
import com.example.tp3.databinding.FragmentMyCustomDialogBinding
import kotlinx.android.synthetic.main.dialog_input.*
import kotlinx.android.synthetic.main.dialog_input.view.*
import kotlinx.android.synthetic.main.fragment_details_parking.*
import kotlinx.android.synthetic.main.fragment_home.*
import kotlinx.android.synthetic.main.fragment_my_custom_dialog.*


class HomeFragment : Fragment() {
    var items = arrayOf("Bab Ezzouar", "Oued Smar", "Dar el beida", "Ouled Fayet")
    var autoCompleteTxt: AutoCompleteTextView? = null
    var refresh :Boolean=false
    var adapterItems: ArrayAdapter<String>? = null
    var destination :String=""
    var prix_double :Double =0.0
    var prix :String =""
    var distance :String = ""
    lateinit var Binding: FragmentHomeBinding

    lateinit var adapter: ParkingAdapter
    lateinit var recyclerView: RecyclerView
    lateinit var parkingViewModel: ParkingViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        /* (Binding as MainActivity).supportFragmentManager
             .beginTransaction()
             .replace(MainActivity.re, MyCustomDialog())
             .commit()*/
        var rootView :View =inflater.inflate(R.layout.dialog_input, container, false)

        Binding = FragmentHomeBinding.inflate(inflater, container, false);


        return Binding.getRoot();
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)



        Binding.filtrer.setOnClickListener{
            onFiltre(requireView())



        }
        val fragment =MyCustomDialog()

        /* fragment.view?.btn_filtrer?.setOnClickListener {

             val pr = requireActivity().getSharedPreferences("db_privee", Context.MODE_PRIVATE)?.edit()
             pr?.putString("destination", destination)
             pr?.putString("prix",prix)
             pr?.putString("distance",distance)

             pr?.apply()
             Toast.makeText(requireContext(), "imene", Toast.LENGTH_SHORT).show()
             fragment.dismiss()


         }*/

        autoCompleteTxt = Binding.autoCompleteTxt


        adapterItems = ArrayAdapter<String>(requireActivity(), R.layout.list_item, items)
        autoCompleteTxt!!.setAdapter(adapterItems)

        autoCompleteTxt!!.onItemClickListener =
            OnItemClickListener { parent, view, position, id ->


                val item = parent.getItemAtPosition(position).toString()
                //Toast.makeText(getApplicationContext(), "Item: $item", Toast.LENGTH_SHORT).show()
                parkingViewModel = ViewModelProvider(requireActivity()).get(ParkingViewModel::class.java)
                parkingViewModel.errorMessage.value = null
                val layoutManager = LinearLayoutManager(requireActivity())
                recyclerView = Binding.recycleParking
                recyclerView.layoutManager = layoutManager
                adapter = ParkingAdapter(requireActivity())
                recyclerView.adapter = adapter
                parkingViewModel.getParkingsCommune(item)
                // add Observers
                // loading observer
                parkingViewModel.loading.observe(viewLifecycleOwner, Observer { loading ->
                    if (loading) {
                        Binding.progressBarHome.visibility = View.VISIBLE
                    } else {
                        Binding.progressBarHome.visibility = View.GONE
                    }

                })
                // Error message observer
                parkingViewModel.errorMessage.observe(viewLifecycleOwner, Observer { message ->
                    if (message != null) {
                        Toast.makeText(requireContext(), "Une erreur s'est produite", Toast.LENGTH_SHORT)
                            .show()
                        Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show()
                    }
                })
                // List movies observer
                parkingViewModel.parkingsCommune.observe(viewLifecycleOwner, Observer { parkings ->
                    adapter.setParkings(parkings)
                })
                parkingViewModel.postion.observe(viewLifecycleOwner, Observer { position ->
                    if (position != null) {
                        adapter.setPostion(position["latitude"]!!, position["longitude"]!!,position["vitesse"]!!)
                    }
                })
            }
        parkingViewModel = ViewModelProvider(requireActivity()).get(ParkingViewModel::class.java)
        parkingViewModel.errorMessage.value = null
        val layoutManager = LinearLayoutManager(requireActivity())
        recyclerView = Binding.recycleParking
        recyclerView.layoutManager = layoutManager
        adapter = ParkingAdapter(requireActivity())
        recyclerView.adapter = adapter
        parkingViewModel.getParkings()
        // add Observers
        // loading observer
        parkingViewModel.loading.observe(viewLifecycleOwner, Observer { loading ->
            if (loading) {
                Binding.progressBarHome.visibility = View.VISIBLE
            } else {
                Binding.progressBarHome.visibility = View.GONE
            }

        })
        // Error message observer
        parkingViewModel.errorMessage.observe(viewLifecycleOwner, Observer { message ->
            if (message != null) {
                Toast.makeText(requireContext(), "Une erreur s'est produite", Toast.LENGTH_SHORT)
                    .show()
                Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show()
            }
        })
        // List movies observer
        parkingViewModel.parkings.observe(viewLifecycleOwner, Observer { parkings ->
            adapter.setParkings(parkings)
        })
        parkingViewModel.postion.observe(viewLifecycleOwner, Observer { position ->
            if (position != null) {
                adapter.setPostion(position["latitude"]!!, position["longitude"]!!,position["vitesse"]!!)
            }
        })
    }
    fun onFiltre(view: View) {
        MyCustomDialog().show(getParentFragmentManager(), "MyCustomDialog")
    }

    override fun onResume() {
        super.onResume()


    }

}