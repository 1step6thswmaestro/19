/**
 * SheetController
 *
 * @description :: Server-side logic for managing sheets
 * @help        :: See http://sailsjs.org/#!/documentation/concepts/Controllers
 */
var async = require('async');
var formatDate = require('format-date');

module.exports = {

	/**
	* Sheet RESTful API GET
	* API Route : GET /sheets?skip=0&limit=30&where={}&sort=id ASC
	*/
	// find: find,
	/**
	* Sheet RESTful API GET One
	* API Route : GET /sheets/:sheetId
	*/
	// findOne : findOne,

	/**
	* Insert Sheet Data
	*/
	insertSheet: function(req, res) {
		Sheet.create({
						requester: req.param('requester'),
	          location: req.param('location'),
	          address: req.param('address'),
	          requester_phone: req.param('requester_phone'),
	          computer_type: req.param('computer_type'),
	          brand: req.param('brand'),
	          used_year: req.param('used_year'),
	          trouble_type: req.param('trouble_type'),
	          available_time: req.param('available_time'),
	          trouble_detail : req.param('trouble_detail'),
	          include: req.param('include'),
	          matching_status : 0,
	          created_date : new Date()
	        }, function sheetCreated(err, newSheet) {
	          if (err) {

	            console.log("err: ", err);
	            console.log("err.invalidAttributes: ", err.invalidAttributes)

	            // Otherwise, send back something reasonable as our error response.
	            return res.negotiate(err);
	          }

	          // Send back the id of the new user
	          return res.json({
	            id: newSheet.id
	          });
	        });
	},


	findFromRequester: function (req, res) {
		//console.log(req.param('id'));
		Sheet
			.find()
			.populateAll()
			.then(function (sheets) {
				var result = [];

				sheets.forEach(function (sheet, idx) {
					console.log(sheet);
					if (sheet.requester && sheet.requester.id === req.param('id')) {
						result.push(sheet);
					}
				});
				return res.ok(result);
			})
			.catch(function (error) {
				return res.badRequest(error);
			});
	},



	findFromSuggestionSheet: function (req, res) {
		// console.log(req.param('id'));
		var query = 'SELECT * from Sheets where id = (SELECT request_sheet from Suggestion_Sheets WHERE id = '+req.param('id')+')';

		console.log(query);

		Sheet.query(query,
			function(err, result) {
				if(err) {
					console.log(err);
					return;
				}
				console.log(result);
				
				return res.ok(result);
		});
	},

	findFromLocation: function(req, res) {
		// var query = 'SELECT * from Sheets where location = "'+req.param('location')+'" or include = "'+req.param('company_id')+'"';

		// console.log(query);

		// Sheet.query(query,
		// 	function(err, result) {
		// 		if(err) {
		// 			console.log(err);
		// 			return;
		// 		}
		// 		console.log(result);
				
		// 		return res.ok(result);
		// });

		Sheet
			.find()
			.populateAll()
			.then(function (sheets) {
				var result = [];

				sheets.forEach(function (sheet, idx) {
					console.log(sheet);
					if ((sheet.include === req.param('company_id') || sheet.location === req.param('location')) && sheet.matching_status === 0) {
						result.push(sheet);
					}
				});
				return res.ok(result);
			})
			.catch(function (error) {
				return res.badRequest(error);
			});
	},


	repairCompleted: function(req, res) {
		var final_start_date = formatDate('{year}-{month}-{day} {hours}:{minutes}:{seconds}', new Date(Date.parse(req.param('final_start_date'))));
    var final_end_date = formatDate('{year}-{month}-{day} {hours}:{minutes}:{seconds}', new Date(Date.parse(req.param('final_end_date'))));

		var query = 'UPDATE Sheets set matching_status =2, final_start_date = "'+final_start_date+'", final_end_date = "'+final_end_date+'", final_price = '+req.param('final_price')+' where id='+req.param('id');

		console.log(query);

		Sheet.query(query,
			function(err) {
				if(err) {
					console.log(err);
					return;
				}
		});
		
		return res.ok();
	},

	// insertReview: function(req, res) {
	// 	var query = 'INSERT into Reviews (review_comment,point,comment) values ('+req.param('review_comment')+','+req.param('point')+','+req.param('comment')+')';

	// 	Review.query(query,
	// 		function(err) {
	// 			if(err) {
	// 				console.log(err);
	// 				return;
	// 			}
	// 	});
		
	// 	return res.ok();
	// },
};
