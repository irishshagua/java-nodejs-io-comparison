var express = require('express');
var app = express();
var dao = require('./dao');

app.get('/', function (req, res) {
	res.send('Answered by JavaScript');
});

app.get('/pubs', function (req, res) {
	dao.getAllPubs(function(dbResult) {
		res.json(dbResult);
	});
});

app.get('/pubs/:id', function (req, res) {
	var pubId = req.params.id;
	dao.getPubById(pubId, function(dbResult) {
		res.json(dbResult);
	});
});

var server = app.listen(8080, function () {
  console.log("App started. Listening on http://localhost:8080");
});