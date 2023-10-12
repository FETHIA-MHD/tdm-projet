// Declaration de variables
const pool = require("../config/db.js");
const log = require("../config/Logger.js");

// Recuperer la liste des parkings
const getParkings = async (request, response) => {
  pool.query(`SELECT * from public."Parking";`, (error, results) => {
    if (error) {
      log.loggerConsole.error(error);
      log.loggerFile.error(error);
      response.sendStatus(500);
    } else {
      response.status(200).json(results.rows);
    }
  });
};

// Recuperer un parking par son id
const getParkingById = async (request, response) => {
  pool.query(
    `SELECT * from public."Parking" where id_parking=$1;`,
    [request.params.id],
    (error, results) => {
      if (error) {
        log.loggerConsole.error(error);
        log.loggerFile.error(error);
        response.sendStatus(500);
      } else {
        response.status(200).json(results.rows);
      }
    }
  );
};
// get parking by prix maximal
const getParkingByTarif = async (request, response) => {
  pool.query(
    `SELECT * from public."Parking" where tarif<=$1 Order By tarif ASC;`,
    [request.params.tarif],
    (error, results) => {
      if (error) {
        log.loggerConsole.error(error);
        log.loggerFile.error(error);
        response.sendStatus(500);
      } else {
        response.status(200).json(results.rows);
      }
    }
  );
};
// get paring by commune
const getParkingByCommune= async (request, response) => {
  pool.query(
    `SELECT * from public."Parking" where commune=$1;`,
    [request.params.commune],
    (error, results) => {
      if (error) {
        log.loggerConsole.error(error);
        log.loggerFile.error(error);
        response.sendStatus(500);
      } else {
        response.status(200).json(results.rows);
      }
    }
  );
};

// Ajouter un parking
const addParking = async (request, response) => {
  pool.query(
    `INSERT INTO public."Parking"(
      photo, nom, commune, etat, capacite, nb_places_libres, latitude, longitude, horaires, tarif)
      VALUES ($1, $2, $3, $4, $5, $6, $7, $8, $9, $10);`,
    [
      request.body.photo,
      request.body.nom,
      request.body.commune,
      request.body.etat,
      request.body.capacite,
      request.body.nb_places_libres,
      request.body.latitude,
      request.body.longitude,
      request.body.horaires,
      request.body.tarif,
    ],
    (error, results) => {
      if (error) {
        log.loggerConsole.error(error);
        log.loggerFile.error(error);
        response.sendStatus(500);
      } else {
        response.sendStatus(200);
      }
    }
  );
};

// Supprimer un parking
const deleteParking = async (request, response) => {
  pool.query(
    `DELETE from public."Parking" where id_parking=$1;`,
    [request.params.id],
    (error, results) => {
      if (error) {
        log.loggerConsole.error(error);
        log.loggerFile.error(error);
        response.sendStatus(500);
      } else {
        response.sendStatus(200);
      }
    }
  );
};
//Exporter les fonctions CRUD de la table parking
module.exports = {
  getParkings,
  getParkingById,
  addParking,
  deleteParking,
  getParkingByCommune,
  getParkingByTarif
};
