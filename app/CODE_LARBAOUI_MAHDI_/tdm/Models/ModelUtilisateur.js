// Declaration de variables
const pool = require("../config/db.js");
const log = require("../config/Logger.js");


// Recuperer la liste des utilisateurs
const getUtilisateurs = async (request, response) => {
  pool.query(`SELECT * from public."Utilisateur";`, (error, results) => {
    if (error) {
      log.loggerConsole.error(error);
      log.loggerFile.error(error);
      response.sendStatus(500);
    } else {
      response.status(200).json(results.rows);
    }
  });
};
const getUtilisateurByEmailWithoutPassword = async (request, response) => {
  pool.query(
    `SELECT * from public."Utilisateur" where email=$1;`,
    [request.body.email],
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
// Recuperer un utilisateur par son email et mot de passe
const getUtilisateurByEmail = async (request, response) => {
  pool.query(
    `SELECT * from public."Utilisateur" where email=$1 and mot_de_passe=$2;`,
    [request.body.email, request.body.mot_de_passe],
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

// Recuperer un utilisateur par son numero de telephone et mot de passe
const getUtilisateurByNumeroTelephone = async (request, response) => {
  pool.query(
    `SELECT * from public."Utilisateur" where numero_telephone=$1 and mot_de_passe=$2;`,
    [request.body.numero_telephone, request.body.mot_de_passe],
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
// get utilisateur by email
const getUsersByEmail = async (request, response) => {
  pool.query(
    `SELECT * from public."Utilisateur" where email=$1;`,
    [request.params.email],
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
// Ajouter un utilisateur
const addUtilisateur = async (request, response) => {
  pool.query(
    `INSERT INTO public."Utilisateur"(
      email, numero_telephone, nom, prenom, mot_de_passe)
      VALUES ($1, $2, $3, $4, $5);`,
    [
      request.body.email,
      request.body.numero_telephone,
      request.body.nom,
      request.body.prenom,
      request.body.mot_de_passe,
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

// Supprimer un utilisateur
const deleteUtilisateurByEmail = async (request, response) => {
  pool.query(
    `DELETE from public."Utilisateur" where email=$1;`,
    [request.params.email],
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
  getUtilisateurByEmail,
  getUtilisateurByNumeroTelephone,
  addUtilisateur,
  deleteUtilisateurByEmail,
  getUtilisateurs,
  getUsersByEmail,
  getUtilisateurByEmailWithoutPassword
};
