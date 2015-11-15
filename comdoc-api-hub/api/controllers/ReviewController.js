/**
 * ReviewController
 *
 * @description :: Server-side logic for managing reviews
 * @help        :: See http://sailsjs.org/#!/documentation/concepts/Controllers
 */

var async = require('async');

module.exports = {
	/**
	* Review RESTful API GET
	* API Route : GET /review?skip=0&limit=30&where={}&sort=id ASC
	*/
	// find: find,
	/**
	* Review RESTful API GET One
	* API Route : GET /review/:reviewId
	*/
	// findOne : findOne,

	/**
	* Insert Review Data
	*/
	insertReview: function(req, res) {
		Review.create({
			  review_comment: req.param('review_comment'),
			  point: req.param('point'),
			  comment: req.param('comment')
	        }, function ReviewCreated(err, newReview) {
	          if (err) {

	            console.log("err: ", err);
	            console.log("err.invalidAttributes: ", err.invalidAttributes)

	            // Otherwise, send back something reasonable as our error response.
	            return res.negotiate(err);
	          }
	          Sheet.update({id: req.param('review_comment')},{
	          	review_comment: newReview.id
	          }).exec(function afterwards(err){
	          	if (err) {
	          		console.log(err);
	          		return;
	          	}
	          })

	          // var query = 'UPDATE Company set avg_point = (select avg(point) from Reviews,Suggestion_Sheets where review_comment = '+req.param('review_comment')+' or review_comment in (select id from Suggestion_Sheets where suggester = (select suggester from Suggestion_Sheets where request_sheet = '+ req.param('review_comment')+')))';
	          
	          var query = 'update Company set avg_point = (select avg(point) as avg_point from Sheets, Suggestion_Sheets, Reviews where Suggestion_Sheets.suggester = (select distinct Suggestion_Sheets.suggester from Sheets, Suggestion_Sheets where Suggestion_Sheets.status = 2 and Suggestion_Sheets.request_sheet = '+req.param('review_comment')+') and Suggestion_Sheets.request_sheet = Sheets.id and Reviews.review_comment = Sheets.id) where Company.id = (select distinct Suggestion_Sheets.suggester from Sheets, Suggestion_Sheets where Suggestion_Sheets.status = 2 and Suggestion_Sheets.request_sheet = '+req.param('review_comment')+')';

	          console.log(query);

						Company.query(query,
							function(err, result) {
								if(err) {
									console.log(err);
									return;
								}
						});

	          // Send back the id of the new user
	          return res.json({
	            id: newReview.id
	          });
	        });
	},

	findFromRequestSheet: function(req, res) {
		var query = 'SELECT * from Reviews where review_comment='+req.param('id');
		console.log(query);

		Review.query(query,
			function(err, result) {
				if(err) {
					console.log(err);
					return;
				}

				return res.ok(result);
		});
	}
};

