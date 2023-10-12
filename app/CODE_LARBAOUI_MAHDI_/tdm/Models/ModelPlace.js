const pool = require("../config/db.js");
const log = require("../config/Logger.js");

// Trouver une place libre
const getPlaceLibre = async (request, response) => {
  pool.query(`SELECT * from public."Place" where id_parking=$1 and etat='t' limit 1 ;`,
  [
    request.params.id
  ] ,
  (error, results) => {
    if (error) {
      log.loggerConsole.error(error);
      log.loggerFile.error(error);
      response.sendStatus(500);
    } else {
      response.status(200).json(results.rows);
    }
  });
};

// Changer l'etat d'occupation d'une place de parking
const updateEtatPlace = async (request, response) => {
  pool.query(
    `UPDATE public."Place" SET etat=$1 where id_parking=$2 and numero_place=$3;`,
    [
        request.body.etat,
        request.body.id_parking,
        request.body.numero_place
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


//Exporter les fonctions CRUD de la table parking
module.exports = {
  getPlaceLibre,
  updateEtatPlace
};