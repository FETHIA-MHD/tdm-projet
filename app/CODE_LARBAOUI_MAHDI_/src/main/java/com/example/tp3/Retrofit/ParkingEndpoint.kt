package com.example.tp3.Retrofit

import com.example.tp3.Entites.Parking
import com.example.tp3.url
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path

interface ParkingEndpoint {

    @GET("/parking/afficher_liste_parkings")
    suspend fun getParkings():Response<List<Parking>>
    @GET("/parking/get_parking_commune/{commune}")
    suspend fun getParkingsCommune(
        @Path("commune") commune: String
    ):Response<List<Parking>>
    @GET("/parking/get_parking_tarif/{tarif}")
    suspend fun  getParkingPrix(
        @Path("tarif") tarif: Double)
            :Response<List<Parking>>


    companion object{
        @Volatile
        var parkingEndpoint:ParkingEndpoint?=null
        fun createInstance():ParkingEndpoint{
            if(parkingEndpoint==null){
                synchronized(this){

                    parkingEndpoint=Retrofit.Builder().baseUrl(url).addConverterFactory(GsonConverterFactory.create()).build().create(ParkingEndpoint::class.java)
                }
            }
            return parkingEndpoint!!
        }

    }

}