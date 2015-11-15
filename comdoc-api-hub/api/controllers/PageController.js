/**
 * PageController
 *
 * @description :: Server-side logic for managing pages
 * @help        :: See http://links.sailsjs.org/docs/controllers
 */

module.exports = {

  showHomePage: function (req, res) {

    return res.view('homepage');
  },

  showMyPage: function (req, res) {
    
  }

};
