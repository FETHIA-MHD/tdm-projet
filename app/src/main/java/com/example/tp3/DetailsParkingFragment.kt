package com.example.tp3

import android.app.AlertDialog
import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.content.SharedPreferences
import android.graphics.Color
import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.core.os.bundleOf
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.RequestOptions
import com.example.tp3.databinding.FragmentDetailsParkingBinding
import retrofit2.Response.error


class DetailsParkingFragment : Fragment() {
    lateinit var Binding:FragmentDetailsParkingBinding
    lateinit var parkingViewModel:ParkingViewModel
    lateinit var pref: SharedPreferences
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        Binding =FragmentDetailsParkingBinding.inflate(inflater,container,false);
        return Binding.getRoot();
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        pref = requireActivity().getSharedPreferences("db_privee", Context.MODE_PRIVATE)
        val position= arguments?.getInt("position")
        val connected:Boolean=pref.getBoolean("connected", false)
        Binding.buttonPaiement.setOnClickListener{
            val bundle = bundleOf(
                "position" to position,
            )
            view.findNavController()
                .navigate(R.id.action_detailsParkingFragment_to_ajouterReservationFragment,bundle)
        }
        if(!connected){
            Binding.buttonPaiement.visibility=View.INVISIBLE

        }else{
            Binding.buttonPaiement.visibility=View.VISIBLE

        }
        val remplissageParking= arguments?.getString("remplissageParking")
        val dureeParking= arguments?.getString("dureeParking")
        val distanceParking=arguments?.getString("distanceParking")
        parkingViewModel = ViewModelProvider(requireActivity()).get(ParkingViewModel::class.java)
        if(position!=null){
            val parking= parkingViewModel.parkings.value?.get(position)
            if (parking != null) {
                Binding.nom.text=parking.nom
                Binding.position.text=parking.commune
                if (!parking.etat) {
                    Binding.statut.text="Fermé"
                    Binding.statut.setTextColor(Color.parseColor("#f00020"))
                } else {
                    Binding.statut.text="Ouvert"
                    Binding.statut.setTextColor(Color.parseColor("#008000"))
                }

                Binding.capacite.text=remplissageParking
                Binding.distance.text=distanceParking
                Binding.distance.setTextColor(Color.parseColor("#4287f5"))
                Binding.duree.text=dureeParking
                Binding.ouvertureFermeture.text=parking.horaire
                Binding.tarif.text=parking.tarif.toString().plus("DZD")
                Binding.tarif.setTextColor(Color.parseColor("#3CADA4"))
                Glide.with(requireContext()).load(parking.photo).apply(RequestOptions().error(R.drawable.ic_baseline_error_outline_24)).into(Binding.image)

                Binding.map.setOnClickListener{
                    val latitude = parking.latitude
                    val longitude = parking.longitude
                    val intent = Uri.parse("geo:$latitude,$longitude").let {
                        Intent(Intent.ACTION_VIEW,it)
                    }
                    startActivity(intent)
                }

                Binding.note.setOnClickListener{
                    val builder = AlertDialog.Builder(requireContext())
                    // Get the layout inflater
                    val inflater = requireActivity().layoutInflater;

                    // Inflate and set the layout for the dialog
                    // Pass null as the parent view because its going in the dialog layout
                    builder.setView(inflater.inflate(R.layout.evaluation_dialog, null))
                        // Add action buttons
                        .setPositiveButton(R.string.valider,
                            DialogInterface.OnClickListener { dialog, id ->
                                // valider la note  ...
                            })
                        .setNegativeButton(R.string.cancel,
                            DialogInterface.OnClickListener { dialog, id ->
                                // cancel la note  ...

                            })
                    builder.create()
                } ?: throw IllegalStateException("Activity cannot be null")



                Binding.share.setOnClickListener{
                    val intent= Intent().apply {
                        var chaine:String=""
                        chaine="Nom : "+parking.nom
                        action = Intent.ACTION_SEND
                        putExtra(Intent.EXTRA_TEXT,
                            chaine)
                        type = "text/plain"
                    }
                    startActivity(intent)
                }

            }
        }
    }

}