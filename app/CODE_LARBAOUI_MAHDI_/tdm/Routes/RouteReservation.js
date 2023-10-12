const express = require("express");
const routerReservation = express.Router();
const serviceReservation = require("../Services/ServiceReservation.js");

//Declaration des routes du service Reservation
routerReservation.post(
  "/reservation/ajouter_reservation/",
  serviceReservation.addReservation
);
routerReservation.put(
  "/reservation/update_reservation_numero_place/",
  serviceReservation.addPlaceParkingReservation
);
routerReservation.post(
  "/reservation/nombre_reservations/",
  serviceReservation.getReservationBetweenHeureDebutHeureFin
);
routerReservation.put(
  "/reservation/update_reservation_etat/",
  serviceReservation.updateEtatReservation
);
module.exports = routerReservation;