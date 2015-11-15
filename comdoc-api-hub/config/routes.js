/**
 * Route Mappings
 * (sails.config.routes)
 *
 * Your routes map URLs to views and controllers.
 *
 * If Sails receives a URL that doesn't match any of the routes below,
 * it will check for matching files (images, scripts, stylesheets, etc.)
 * in your assets directory.  e.g. `http://localhost:1337/images/foo.jpg`
 * might match an image file: `/assets/images/foo.jpg`
 *
 * Finally, if those don't match either, the default 404 handler is triggered.
 * See `api/responses/notFound.js` to adjust your app's 404 logic.
 *
 * Note: Sails doesn't ACTUALLY serve stuff from `assets`-- the default Gruntfile in Sails copies
 * flat files from `assets` to `.tmp/public`.  This allows you to do things like compile LESS or
 * CoffeeScript for the front-end.
 *
 * For more information on configuring custom routes, check out:
 * http://sailsjs.org/#!/documentation/concepts/Routes/RouteTargetSyntax.html
 */

module.exports.routes = {

  /***************************************************************************
  *                                                                          *
  * Make the view located at `views/homepage.ejs` (or `views/homepage.jade`, *
  * etc. depending on your default view engine) your home page.              *
  *                                                                          *
  * (Alternatively, remove this and add an `index.html` file in your         *
  * `assets` directory)                                                      *
  *                                                                          *
  ***************************************************************************/

  '/': {
    view: 'homepage'
  },
  '/login': {
    view: 'homepage'
  },
  '/register': {
    view: 'homepage'
  },
  '/about': {
    view: 'homepage'
  },
  '/contact': {
    view: 'homepage'
  },
  '/repaircase': {
    view: 'homepage'
  },
  '/404': {
    view: 'homepage'
  },
  '/500': {
    view: 'homepage'
  },
  '/mobile/home': {
    view: 'homepage'
  },
  '/mobile/mypage': {
    view: 'homepage'
  },
  '/mobile/register': {
    view: 'homepage'
  },


  ////////////////////////////////////////////////////////////
  // Server-rendered HTML webpages
  ////////////////////////////////////////////////////////////

  'GET /mypage/user': 'AuthController.showHomePage',
  'GET /mypage/user/sheet': 'AuthController.showHomePage',
  'GET /mypage/user/sheet/:id': 'AuthController.showHomePage',
  'GET /mypage/user/sheet/detail/:id': 'AuthController.showHomePage',
  'GET /mypage/user/sheet/detail/after_adopted/:id': 'AuthController.showHomePage',
  'GET /mypage/user/adopted/:sheet_id/:suggestion_sheet_id': 'AuthController.showHomePage',
  'GET /mypage/company': 'AuthController.showHomePage',
  'GET /mypage/company/request_sheet': 'AuthController.showHomePage',
  'GET /mypage/company/sheet/detail/:id': 'AuthController.showHomePage',
  'GET /mypage/company/request_sheet/detail/:id': 'AuthController.showHomePage',
  'GET /mypage/company/sheet/:id': 'AuthController.showHomePage',
  'GET /mypage/user/profile': 'AuthController.showHomePage',
  'GET /mypage/user/profile/edit': 'AuthController.showHomePage',
  'GET /mypage/company/profile': 'AuthController.showHomePage',
  'GET /mypage/company/profile/edit': 'AuthController.showHomePage',
  'GET /repaircase/list/:trouble_type': 'PageController.showHomePage',
  'GET /repaircase/list/detail/:trouble_type/:id': 'PageController.showHomePage',

  ////////////////////////////////////////////////////////////
  // JSON API
  ////////////////////////////////////////////////////////////

  // User enrollment + authentication
  'POST /register/user': 'UserController.register',
  'POST /register/company': 'CompanyController.register',
  'POST /login/user': 'UserController.login', 
  'POST /login/company': 'CompanyController.login',
  'GET /logout': 'UserController.logout',

  //Sheet enrollment
  'POST /insert/sheet': 'SheetController.insertSheet',
  'POST /insert/suggestion_sheet': 'Suggestion_sheetController.insertSuggestionSheet',
  

  //Sheet finder & updater
  'GET /sheets/user/:id': 'SheetController.findFromRequester',
  'GET /suggestion_sheets/company/:id': 'Suggestion_sheetController.findFromSuggester',
  'GET /suggestion_sheets/request_sheet/:id': 'Suggestion_sheetController.findFromRequestSheet',
  'GET /request_sheet/suggestion_sheet/:id': 'SheetController.findFromSuggestionSheet',
  'GET /suggestion_sheets/adopted/:sheet_id/:suggestion_sheet_id': 'Suggestion_sheetController.adopted',
  'PUT /sheets/repairCompleted': 'SheetController.repairCompleted', //파라미터: id, final_start_date, final_end_date, final_price
  'GET /sheets/location/:location/:company_id': 'SheetController.findFromLocation',


  //Review finder
  'GET /review/sheet/:id': 'ReviewController.findFromRequestSheet',

  //Review enrollment
  'POST /insert/review': 'ReviewController.insertReview',

  //Repair Case finder
  'GET /repaircase/trouble_type/:trouble_type': 'RepairCaseController.findRepairCase',
  'GET /repaircase/trouble_type/detail/:trouble_type/:id': 'RepairCaseController.findOneRepairCaseFromId',


  //Sending Email
  'POST /contact-form': 'MailController.sendMail',


  //Push Notification
  'GET /push_notification': 'PushNotificationController.sendMsg'



  /***************************************************************************
  *                                                                          *
  * Custom routes here...                                                    *
  *                                                                          *
  * If a request to a URL doesn't match any of the custom routes above, it   *
  * is matched against Sails route blueprints. See `config/blueprints.js`    *
  * for configuration options and examples.                                  *
  *                                                                          *
  ***************************************************************************/

};
