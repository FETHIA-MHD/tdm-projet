package com.example.tp3

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.tp3.Adapters.ReservationAdapter
import com.example.tp3.BDD.AppBD
import com.example.tp3.Entites.Reservation
import com.example.tp3.Entites.Utilisateur
import com.example.tp3.ViewModels.UtilisateurViewModel
import com.example.tp3.databinding.FragmentMesReservationBinding
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.google.android.gms.tasks.OnCompleteListener
import kotlinx.android.synthetic.main.fragment_mes_reservation.*
import java.util.*


class MesReservationFragment : Fragment() {

    lateinit var Binding: FragmentMesReservationBinding
    lateinit var utilisateurViewModel: UtilisateurViewModel
    lateinit var parkingViewModel: ParkingViewModel
    lateinit var adapter: ReservationAdapter
    lateinit var recyclerView: RecyclerView
    lateinit var mGoogleSignInClient: GoogleSignInClient
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        Binding = FragmentMesReservationBinding.inflate(inflater, container, false);
        return Binding.root;
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestEmail()
            .build()
        mGoogleSignInClient = GoogleSignIn.getClient(requireActivity(), gso);

        utilisateurViewModel =
            ViewModelProvider(requireActivity()).get(UtilisateurViewModel::class.java)
        val pref = requireActivity().getSharedPreferences("db_privee", Context.MODE_PRIVATE)
        val id_utilisateur = pref.getString("id_utilisateur", "")!!.toInt()
      //  val bd: AppBD? = AppBD.buildDatabase(requireContext())
       //
        val bd: AppBD? = AppBD.buildDatabase(requireContext())

       // val bd: AppBD? = AppBD.buildDatabase(requireContext())
       /*bd?.getReservationDao()?.insert(
            Reservation(
                id_reservation="Res-97",
                date_reservation = Date(System.currentTimeMillis()),
                heure_entree = System.currentTimeMillis().toDouble(),
                heure_sortie = System.currentTimeMillis().toDouble(),
                etat = true,
                numero_place = 1,
                id_parking = 6,
                id_utilisateur = 51,
                id_paiement = "96",
                synchronise = false
            )
        )*/
        val reservations: List<Reservation>? =
          bd?.getReservationDao()?.getReservationsUtilisateur(id_utilisateur)
 val reservationsEnCours: MutableList<Reservation> = mutableListOf()
 if (reservations != null) {
     for (item in reservations) {
         if ((item.heure_sortie > System.currentTimeMillis() && item.date_reservation == Date()) || (item.date_reservation > Date())) {
             reservationsEnCours.add(item)
         }
     }
 }
 val layoutManager = LinearLayoutManager(requireActivity())
 recyclerView = Binding.recycleReservation
 recyclerView.layoutManager = layoutManager
 adapter = ReservationAdapter(requireActivity())
 recyclerView.adapter = adapter
 parkingViewModel = ViewModelProvider(requireActivity()).get(ParkingViewModel::class.java)
 if (parkingViewModel.parkings.value != null) {
     adapter.setParkings(parkingViewModel.parkings.value!!)
 }
 if (reservations != null) {
     adapter.setReservation(reservationsEnCours)
 }
 logout.setOnClickListener{

     val prefmodif = requireActivity().getSharedPreferences("db_privee", Context.MODE_PRIVATE)?.edit()
     prefmodif?.putBoolean("connected", false)
     prefmodif?.putString("email", "")
     prefmodif?.putString("mot_de_passe", "")
     prefmodif?.putString("nom", "")
     prefmodif?.putString("prenom", "")
     prefmodif?.putString("id_utilisateur", "")
     prefmodif?.putString("numero_telephone", "")
     prefmodif?.apply()

     utilisateurViewModel.utilisateurs.value = null

     try {
         mGoogleSignInClient.signOut()
             .addOnCompleteListener(requireActivity(), OnCompleteListener<Void?> {
             })
     }
     catch (e: Exception){
     }
     activity?.findNavController(R.id.navHost)?.navigate(R.id.action_mesReservationFragment_to_loginFragment2)
 }

}

}