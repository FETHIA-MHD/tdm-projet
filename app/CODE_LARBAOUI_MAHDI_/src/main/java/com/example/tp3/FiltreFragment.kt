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
import androidx.lifecycle.MutableLiveData
import com.bumptech.glide.manager.SupportRequestManagerFragment
import com.example.tp3.Entites.Parking
import com.example.tp3.databinding.ActivityMainBinding.inflate
import com.example.tp3.databinding.FragmentFiltreBinding
import com.example.tp3.databinding.FragmentMyCustomDialogBinding
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.google.maps.android.SphericalUtil
import kotlinx.android.synthetic.main.dialog_input.*
import kotlinx.android.synthetic.main.dialog_input.view.*
import kotlinx.android.synthetic.main.fragment_details_parking.*
import kotlinx.android.synthetic.main.fragment_home.*
import kotlinx.android.synthetic.main.fragment_my_custom_dialog.*


class FiltreFragment : Fragment() {
    var items = arrayOf("Bab Ezzouar", "Oued Smar", "Dar el beida", "Ouled Fayet")
    var autoCompleteTxt: AutoCompleteTextView? = null
    var refresh :Boolean=false
    var adapterItems: ArrayAdapter<String>? = null
    var destination :String=""
    var prix_double :Double =0.0
    var prix :String =""
    var distance :String = ""
    var distance_double =0.0
    var latitude =0.0
    var longitude =0.0

    lateinit var Binding: FragmentFiltreBinding


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


        Binding = FragmentFiltreBinding.inflate(inflater, container, false);


        return Binding.getRoot();
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)






        val pr = requireActivity().getSharedPreferences("db_privee", Context.MODE_PRIVATE)
        prix=pr.getString("prix","")!!
        prix_double= prix.toDouble()
        distance=pr.getString("distance","")!!
        distance_double=distance.toDouble()
        destination=pr.getString("destination","")!!.trim()


        if (destination.equals("Bab Ezzouar".trim())){
            latitude= Commune.Bab_Ezzouar.lat
            longitude=Commune.Bab_Ezzouar.long
        }
        if (destination.equals("Oued Smar".trim()))
        {
            latitude= Commune.Oued_Smar.lat
            longitude=Commune.Oued_Smar.long
        }
        if (destination.equals("Dar el beida".trim()))
        {
            latitude= Commune.Dar_el_beida.lat
            longitude=Commune.Dar_el_beida.long
        }
        if (destination.equals("Ouled Fayet".trim()))
        {
            latitude= Commune.Olued_Fayet.lat
            longitude=Commune.Olued_Fayet.long

        }
        val pos2 = LatLng(latitude, longitude)

        Toast.makeText(requireContext(), "${destination}", Toast.LENGTH_SHORT).show()






        adapterItems = ArrayAdapter<String>(requireActivity(), R.layout.list_item, items)
        parkingViewModel = ViewModelProvider(requireActivity()).get(ParkingViewModel::class.java)
        parkingViewModel.errorMessage.value = null
        val layoutManager = LinearLayoutManager(requireActivity())
        recyclerView = Binding.recycleParking1
        recyclerView.layoutManager = layoutManager
        adapter = ParkingAdapter(requireActivity())
        recyclerView.adapter = adapter
        parkingViewModel.getParkingsPrix(prix_double)
        // add Observers
        // loading observer
        parkingViewModel.loading.observe(viewLifecycleOwner, Observer { loading ->
            if (loading) {
                Binding.progressBarHome1.visibility = View.VISIBLE
            } else {
                Binding.progressBarHome1.visibility = View.GONE
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
        parkingViewModel.parkingPrix.observe(viewLifecycleOwner, Observer { parkings ->
            var  parkingsFiltres = parkingViewModel.parkingPrix.value
            var parkingListFiltre=ArrayList<Parking>()

            if ( parkingsFiltres != null) {
                for (i in parkingsFiltres.indices) {
                    if (parkingsFiltres[i].nom.isNotEmpty()) {
                        val pos1 = LatLng(parkingsFiltres[i].latitude, parkingsFiltres[i].longitude)
                        var distance = SphericalUtil.computeDistanceBetween(pos1, pos2)/1000;

                        if (distance<=distance_double){
                            Toast.makeText(requireContext(), "${distance}", Toast.LENGTH_SHORT).show()
                            parkingListFiltre.add(parkingsFiltres[i])
                        }



                    }

                }
            }





            adapter.setParkings(parkingListFiltre)

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
    enum class Commune(val lat: Double,val long:Double ) {
        Bab_Ezzouar(36.7206251,3.1854975),
        Olued_Fayet(36.7355249,2.9509064),
        Oued_Smar(36.7044199,3.1681561),
        Dar_el_beida(36.7141667,3.2125000000000004)
    }

}