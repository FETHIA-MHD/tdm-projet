package com.example.tp3.Adapters

import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.os.bundleOf
import androidx.fragment.app.FragmentActivity
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.RequestOptions
import com.example.tp3.Entites.Parking
import com.example.tp3.R
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.SphericalUtil

class ParkingAdapter(var context: FragmentActivity) :
    RecyclerView.Adapter<ParkingAdapter.ParkingHolder>() {
    var data = mutableListOf<Parking>()
    var latitude_actuelle: Double = 0.0
    var longitude_actuelle: Double = 0.0
    var vitesse: Double = 1.0
    fun setParkings(parkings: List<Parking>) {
        this.data = parkings.toMutableList()

        notifyDataSetChanged()

    }

    fun getTauxoccupation(parking: Parking): Int {
        return ((parking.capacite - parking.nb_places_libres)*100/ parking.capacite).toInt()
    }
    fun setPostion(latitude_actuelle: Double, longitude_actuelle: Double, vitesse: Double) {
        this.latitude_actuelle = latitude_actuelle
        this.longitude_actuelle = longitude_actuelle
        this.vitesse = vitesse
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ParkingHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.layout_parking, parent, false)

        return ParkingHolder(view)
    }

    override fun getItemCount(): Int {
        return data.size
    }

    override fun onBindViewHolder(holder: ParkingHolder, position: Int) {
        bind(holder, data[position])
    }

    private fun bind(holder: ParkingHolder, parking: Parking) {
        holder.apply {
            holder.nomParking.text = parking.nom
            if (!parking.etat) {
                holder.etatParking.text = "Ferme"
                holder.etatParking.setTextColor(Color.parseColor("#f00020"))
            } else {
                holder.etatParking.text = "Ouvert"
                holder.etatParking.setTextColor(Color.parseColor("#008000"))
            }
            holder.remplissageParking.text = getTauxoccupation(parking).toString()
            holder.communeParking.text = parking.commune
            //===================================================================
            val pos1 = LatLng(parking.latitude, parking.longitude)
            val pos2 = LatLng(latitude_actuelle, longitude_actuelle)
            var distance = SphericalUtil.computeDistanceBetween(pos1, pos2);
            if (latitude_actuelle == 0.0 || longitude_actuelle == 0.0) {
                holder.distanceParking.text = "Calcul en cours ..."
                holder.dureeParking.text = "Calcul en cours ..."
            } else {
                if (vitesse == 0.0) {
                    holder.dureeParking.text = String.format("%.2f", (distance) / 1000).plus(" h")
                } else {
                    holder.dureeParking.text =
                        String.format("%.2f", ((distance / vitesse) / 60) / 60/60).plus(" h")
                }
                holder.distanceParking.text = String.format("%.2f", distance / 1000).plus(" Km")
                holder.distanceParking.setTextColor(Color.parseColor("#4287f5"))
            }
            //===================================================================
            Glide.with(context).load(parking.photo)
                .apply(RequestOptions().error(R.drawable.ic_baseline_error_outline_24))
                .diskCacheStrategy(
                    DiskCacheStrategy.ALL
                ).into(holder.imageParking)
            holder.itemView.setOnClickListener(object : View.OnClickListener {
                override fun onClick(view: View?) {
                    if (view != null) {
                        val bundle = bundleOf(
                            "position" to position,
                            "remplissageParking" to holder.remplissageParking.text,
                            "dureeParking" to holder.dureeParking.text,
                            "distanceParking" to holder.distanceParking.text
                        )
                        view.findNavController()
                            .navigate(R.id.action_homeFragment_to_detailsParkingFragment, bundle)

                    }
                }
            })
        }
    }

    class ParkingHolder(ItemView: View) : RecyclerView.ViewHolder(ItemView) {
        val nomParking: TextView = itemView.findViewById(R.id.nom)
        val etatParking:TextView=itemView.findViewById(R.id.Etat)
        val remplissageParking:TextView=itemView.findViewById(R.id.taux)
        val communeParking:TextView=itemView.findViewById(R.id.commune)
        val distanceParking:TextView=itemView.findViewById(R.id.distance)
        val dureeParking:TextView=itemView.findViewById(R.id.temps)
        val imageParking: ImageView = itemView.findViewById(R.id.imageView)
    }

}