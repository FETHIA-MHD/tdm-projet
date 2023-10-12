const ModelReservation = require("../Models/ModelReservation.js");
const log = require("../config/Logger.js");

// Fonctions du service Reservation
const addReservation = async (request, response) => {
  await ModelReservation.addReservation(request, response);
};

const addPlaceParkingReservation = async (request, response) => {
  await ModelReservation.addPlaceParkingReservation(request, response);
};

const updateEtatReservation = async (request, response) => {
  await ModelReservation.updateEtatReservation(request, response);
};

const getReservationBetweenHeureDebutHeureFin = async (request, response) => {
  await ModelReservation.getReservationBetweenHeureDebutHeureFin(
    request,
    response
  );
};

//Exporter les fonctions du service Parking
module.exports = {
  addReservation,
  getReservationBetweenHeureDebutHeureFin,
  updateEtatReservation,
  addPlaceParkingReservation,
};