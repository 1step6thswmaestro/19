/**
 * AuthController
 *
 * @module      :: Controller
 * @description	:: Provides the base authentication
 *                 actions used to make waterlock work.
 *
 * @docs        :: http://waterlock.ninja/documentation
 */

module.exports = require('waterlock').waterlocked({
  isAuthenticated: function(req, res) {
    if(req.session.authenticated) {
      res.ok();
    }
    else {
      res.authNotFound();
    }
  },

  showHomePage: function (req, res) {

    return res.view('homepage');
  },

});