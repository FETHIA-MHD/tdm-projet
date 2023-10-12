const ModelParking = require("../Models/ModelParking.js");
const log = require("../config/Logger.js");

// Fonctions du service Parking
const getParkings = async (request, response) => {
  try {
    await ModelParking.getParkings(request, response);
  } catch (error) {
    log.loggerConsole.error(error);
    log.loggerFile.error(error);
    response.sendStatus(500);
  }
};

const getParkingById = async (request, response) => {
  try {
    await ModelParking.getParkingById(request, response);
  } catch (error) {
    log.loggerConsole.error(error);
    log.loggerFile.error(error);
    response.sendStatus(500);
  }
};
const getParkingByCommune = async (request, response) => {
  try {
    await ModelParking.getParkingByCommune(request, response);
  } catch (error) {
    log.loggerConsole.error(error);
    log.loggerFile.error(error);
    response.sendStatus(500);
  }
};
const getParkingByTarif = async (request, response) => {
  try {
    await ModelParking.getParkingByTarif(request, response);
  } catch (error) {
    log.loggerConsole.error(error);
    log.loggerFile.error(error);
    response.sendStatus(500);
  }
};
const addParking = async (request, response) => {
  try {
    await ModelParking.addParking(request, response);
  } catch (error) {
    log.loggerConsole.error(error);
    log.loggerFile.error(error);
    response.sendStatus(500);
  }
};

const deleteParking = async (request, response) => {
  try {
    await ModelParking.deleteParking(request, response);
  } catch (error) {
    log.loggerConsole.error(error);
    log.loggerFile.error(error);
    response.sendStatus(500);
  }
};

//Exporter les fonctions du service Parking
module.exports = {
  getParkings,
  getParkingById,
  addParking,
  deleteParking,
  getParkingByCommune,
  getParkingByTarif
};
