// Declaration de variables
const pool = require("../config/db.js");
const log = require("../config/Logger.js");

// Recuperer la liste des evaluations d'un parking
const getEvaluationsByIdParking = async (request, response) => {
  pool.query(
    `SELECT * FROM public."Evaluation" where id_parking=$1;`,
    [request.params.id_parking],
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

// Ajouter une evaluation
const addEvaluation = async (request, response) => {
  let body = [];
  body = request.body;
  body.forEach((element) => {
    pool.query(
      `INSERT INTO public."Evaluation"(
          note, commentaire, id_utilisateur, id_parking)
          VALUES ($1, $2, $3, $4);`,
      [
        element.note,
        element.commentaire,
        element.id_utilisateur,
        element.id_parking,
      ],
      (error, results) => {
        if (error) {
          log.loggerConsole.error(error);
          log.loggerFile.error(error);
          response.sendStatus(500);
        }
      }
    );
  });
  if (response.statusCode != 500) {
    response.sendStatus(200);
  }
};

// Supprimer une evaluation
const deleteEvaluation = async (request, response) => {
  pool.query(
    `DELETE from public."Evaluation" where id_evaluation=$1;`,
    [request.params.id_evaluation],
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

//Exporter les fonctions CRUD de la table evaluation
module.exports = {
  getEvaluationsByIdParking,
  addEvaluation,
  deleteEvaluation,
};