const ModelUtilisateur = require("../Models/ModelUtilisateur.js");
const log = require("../config/Logger.js");

// Fonctions du service Utilisateur
const getUtilisateurByEmail = async (request, response) => {
  try {
    await ModelUtilisateur.getUtilisateurByEmail(request, response);
  } catch (error) {
    log.loggerConsole.error(error);
    log.loggerFile.error(error);
    response.sendStatus(500);
  }
};

const getUtilisateurByNumeroTelephone = async (request, response) => {
  try {
    await ModelUtilisateur.getUtilisateurByNumeroTelephone(request, response);
  } catch (error) {
    log.loggerConsole.error(error);
    log.loggerFile.error(error);
    response.sendStatus(500);
  }
};
const getUtilisateurs = async (request, response) => {
  try {
    await ModelUtilisateur.getUtilisateurs(request, response);
  } catch (error) {
    log.loggerConsole.error(error);
    log.loggerFile.error(error);
    response.sendStatus(500);
  }
};
const getUtilisateurByEmailWithoutPassword = async (request, response) => {
  await ModelUtilisateur.getUtilisateurByEmailWithoutPassword(request, response);
};
const getUsersByEmail = async (request, response) => {
  try {
    await ModelUtilisateur.getUsersByEmail(request, response);
  } catch (error) {
    log.loggerConsole.error(error);
    log.loggerFile.error(error);
    response.sendStatus(500);
  }
};
const addUtilisateur = async (request, response) => {
  try {
    await ModelUtilisateur.addUtilisateur(request, response);
  } catch (error) {
    log.loggerConsole.error(error);
    log.loggerFile.error(error);
    response.sendStatus(500);
  }
};

const deleteUtilisateurByEmail = async (request, response) => {
  try {
    await ModelUtilisateur.deleteUtilisateurByEmail(request, response);
  } catch (error) {
    log.loggerConsole.error(error);
    log.loggerFile.error(error);
    response.sendStatus(500);
  }
};

//Exporter les fonctions du service Parking
module.exports = {
  getUtilisateurByEmail,
  getUtilisateurByNumeroTelephone,
  addUtilisateur,
  deleteUtilisateurByEmail,
  getUsersByEmail,
 getUtilisateurs ,
 getUtilisateurByEmailWithoutPassword

};
