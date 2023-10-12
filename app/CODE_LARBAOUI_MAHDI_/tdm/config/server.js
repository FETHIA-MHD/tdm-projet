const express = require("express");
const bodyParser = require("body-parser");
const fs = require("fs");
const path = require("path");
const morgan = require("morgan");
const app = express();
const port = process.env.PORT || 3000;
const cors = require("cors");
app.use(bodyParser.json());
app.use(bodyParser.urlencoded({ extended: true }));
app.use(function (req, res, next) {
  res.header("Access-Control-Allow-Origin", "*");
  res.header("Access-Control-Allow-Credentials", true);
  res.header("Access-Control-Allow-Methods", "GET,PUT,POST,DELETE,OPTIONS");
  res.header(
    "Access-Control-Allow-Headers",
    "Origin,X-Requested-With,Content-Type,Accept,content-type,application/json"
  );
  next();
});
app.use(
  cors({
    origin: "*",
  })
);
const routeParking= require("../Routes/RouteParking");
const routeUtilisateur=require("../Routes/RouteUtilisateur")
const routeEvaluation=require("../Routes/RouteEvaluation")
const routePlace=require("../Routes/RoutePlace")
const routePaiement=require("../Routes/RoutePaiement");
const routerReservation = require("../Routes/RouteReservation");

// Configurer le serveur pour utiliser toutes les routes
app.use(bodyParser.json());
app.use(bodyParser.urlencoded({ extended: true }));
app.use(
  morgan("combined", {
    stream: fs.createWriteStream(
      path.join(__dirname, "../LogFiles/logTrafic.log"),
      { flags: "a" }
    ),
  })
);
app.use("/", routeParking);
app.use("/",routeUtilisateur)
app.use("/",routeEvaluation)
app.use("/",routePlace)
app.use("/",routePaiement)
app.use("/",routerReservation)

app.get("/", (req, res) => {
  res.send("Serveur projet TDM");
});

// Demarrer le serveur
app.listen(port, () => console.log("Server running on port 3000 ..."));

module.exports = {
  app,
};