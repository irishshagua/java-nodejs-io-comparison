var mysql = require('mysql');

var connectionPool = mysql.createPool({
	host     : 'io-compare-db.cix2zti3tbhx.eu-west-1.rds.amazonaws.com',
	user     : 'aws_db_user',
	password : 'Pa$$word121',
	database : 'pubs'
});

module.exports = {
    getAllPubs: function (callback) {
		connectionPool.getConnection(function(err, connection) {
			if (err) throw err;			
			connection.query('SELECT * FROM pub', function(err, rows) {				
				connection.release();
				if (err) throw err;
				callback(rows);
			});
		});
    },

    getPubById: function (pubId, callback) {
		connectionPool.getConnection(function(err, connection) {
			if (err) throw err;			
			connection.query('SELECT * FROM pub WHERE id = ?', [pubId], function(err, rows) {				
				connection.release();
				if (err) throw err;
				callback(rows);
			});
		});
    }
}