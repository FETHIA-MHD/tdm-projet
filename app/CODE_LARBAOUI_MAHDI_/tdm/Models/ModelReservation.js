// Declaration de variables
const pool = require("../config/db.js");
const log = require("../config/Logger.js");

// Ajouter une reservation
const addReservation = async (request, response) => {
  let body = [];
  body = request.body;
  body.forEach((element) => {
    pool.query(
      `INSERT INTO public."Reservation"(id_reservation,date_reservation, heure_entree, heure_sortie, etat, numero_place, id_parking, id_utilisateur, id_paiement)
	VALUES ($1, $2, $3, $4, $5, $6, $7,$8,$9);`,
      [ element.id_reservation,
        element.date_reservation,
        element.heure_entree,
        element.heure_sortie,
        element.etat,
        element.numero_place,
        element.id_parking,
        element.id_utilisateur,
        element.id_paiement,
      ],
      (error, results) => {
        if (error) {
          log.loggerConsole.error(error);
          log.loggerFile.error(error);
          response.sendStatus(500);
          return ;
        }
      }
    );
  });
  response.sendStatus(200);
};

// Associer une place de parking a une reservation
const addPlaceParkingReservation = async (request, response) => {
  pool.query(
    `UPDATE public."Reservation"
	SET  numero_place=$1 WHERE id_reservation=$2;`,
    [request.body.numero_place, request.body.id_reservation],
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

// Maj le champs etat d'une reservation
const updateEtatReservation = async (request, response) => {
  pool.query(
    `UPDATE public."Reservation"
      SET  etat=$1 WHERE id_reservation=$2;`,
    [request.body.etat, request.body.id_reservation],
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

// Recuperer le nombre de de reservation entre une heure de debut et heure fin
const getReservationBetweenHeureDebutHeureFin = async (request, response) => {
  pool.query(
    `select * from public."Reservation" where date_reservation=$1 and id_parking=$4 and (((heure_entree < $2) and (heure_sortie<$3 or heure_sortie>$3)) or ((heure_entree>$2) and (heure_sortie<$3)) );`,
    [
      request.body.date_reservation,
      request.body.heure_entree,
      request.body.heure_sortie,
      request.body.id_parking,
    ],
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
//Exporter les fonctions CRUD de la table parking
module.exports = {
  addReservation,
  addPlaceParkingReservation,
  updateEtatReservation,
  getReservationBetweenHeureDebutHeureFin,
};
