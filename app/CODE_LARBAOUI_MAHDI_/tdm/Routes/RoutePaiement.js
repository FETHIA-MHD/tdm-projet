const express = require("express");
const routerPaiement = express.Router();
const servicePaiement = require("../Services/ServicePaiement.js");

//Declaration des routes du service Paiement
routerPaiement.post("/paiement/ajouter_paiement/", servicePaiement.addPaiement);

module.exports = routerPaiement;