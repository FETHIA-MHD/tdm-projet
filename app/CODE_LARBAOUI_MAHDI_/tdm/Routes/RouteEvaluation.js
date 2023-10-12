const express = require("express");
const routerEvalaution = express.Router();
const serviceEvaluation = require("../Services/ServiceEvaluation.js");

//Declaration des routes du service Evaluation
routerEvalaution.get(
  "/evaluation/liste_evaluation/:id",
  serviceEvaluation.getEvaluationsByIdParking
);
routerEvalaution.post(
  "/evaluation/ajouter_evaluation/",
  serviceEvaluation.addEvaluation
);
routerEvalaution.delete(
  "/evaluation/supprimer_evaluation/:id",
  serviceEvaluation.deleteEvaluation
);
module.exports = routerEvalaution;
