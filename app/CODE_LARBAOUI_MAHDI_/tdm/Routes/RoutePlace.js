const express = require("express");
const routerPlace = express.Router();
const servicePlace = require("../Services/ServicePlace.js");

//Declaration des routes du service Parking
routerPlace.get("/place/place_libre/:id", servicePlace.getPlaceLibre);
routerPlace.put("/parking/update_etat_place/", servicePlace.updateEtatPlace);

module.exports = routerPlace;