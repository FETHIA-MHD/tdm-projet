package com.example.tp3

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.ProgressBar
import android.widget.Toast
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.tp3.Adapters.ParkingAdapter
import com.example.tp3.databinding.DialogInputBinding
import com.example.tp3.databinding.FragmentHomeBinding
import kotlinx.android.synthetic.main.fragment_details_parking.*
import kotlinx.android.synthetic.main.fragment_home.view.*


//private val onSubmitClickListener: (String) -> Unit
class MyCustomDialog( ) : DialogFragment() {
    private lateinit var binding : DialogInputBinding
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        var rootView :View =inflater.inflate(R.layout.dialog_input, container, false)
        var rootView2 =inflater.inflate(R.layout.layout_parking, container, false)
        var dest:String
        var prix:String
        var distance:String
        var button = rootView.findViewById(R.id.btn_filtrer) as Button
        var adapter =ParkingAdapter(requireActivity())


        button.setOnClickListener {
            var destination=rootView.findViewById(R.id.destination) as EditText
            dest=destination.text.toString()
            var prix_max = rootView.findViewById(R.id.prix_max) as EditText
            prix =prix_max.text.toString()
            var distance_max =rootView.findViewById(R.id.distance_max) as EditText
            distance=distance_max.text.toString()


            val pr = requireActivity().getSharedPreferences("db_privee", Context.MODE_PRIVATE)?.edit()
            pr?.putString("destination", dest)
            pr?.putString("prix",prix)
            pr?.putString("distance",distance)
            pr?.apply()

            activity?.findNavController(R.id.navHost)?.navigateUp()
            activity?.findNavController(R.id.navHost)
                ?.navigate(R.id.action_homeFragment_to_filtreFragment)
            dismiss()






        }
        getDialog()!!.getWindow()?.setBackgroundDrawableResource(R.drawable.round_corner);
        return rootView







    }

    override fun onStart() {
        super.onStart()
        val width = (resources.displayMetrics.widthPixels * 0.85).toInt()
        val height = (resources.displayMetrics.heightPixels * 0.40).toInt()
        dialog!!.window?.setLayout(width, ViewGroup.LayoutParams.WRAP_CONTENT)
    }
}