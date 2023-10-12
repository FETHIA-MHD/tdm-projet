const pool = require("../config/db.js");
const log = require("../config/Logger.js");


// Ajouter un paiement
// Ajouter un paiement
const addPaiement = async (request, response) => {
  pool.query(
    `INSERT INTO public."Paiement"(id_paiement, montant, date_paiement)
	VALUES ($1, $2, $3);`,
    [request.body.id_paiement, request.body.montant, request.body.date_paiement],
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
  addPaiement,
};