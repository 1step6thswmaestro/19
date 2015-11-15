/**
 * CompanyController
 *
 * @description :: Server-side logic for managing companys
 * @help        :: See http://links.sailsjs.org/docs/controllers
 */
function registerSuccess(req, res, user) {
  waterlock.logger.debug('user registration success');
  if (!user) {
    waterlock.logger.debug('registerSuccess requires a valid user object');
    return res.serverError();
  }

  var address = _addressFromRequest(req);

  var attempt = {
    user: user.id,
    successful: true
  };

  _.merge(attempt, address);

  waterlock.Attempt.create(attempt).exec(function(err) {
    if (err) {
      waterlock.logger.debug(err);
    }
  });

  // store user in && authenticate the session
  req.session.user = user;
  req.session.authenticated = true;

  // now respond or redirect
  var postResponse = _resolvePostAction(waterlock.config.postActions.register.success, user);

  if (postResponse === 'jwt') {
    //Returns the token immediately
    var jwtData = waterlock._utils.createJwt(req, res, user);

    Jwt.create({token: jwtData.token, uses: 0, owner_company: user.id}).exec(function(err){
      if(err){
        return res.serverError('JSON web token could not be created');
      }

      var result = {};

      result[waterlock.config.jsonWebTokens.tokenProperty] = jwtData.token;
      result[waterlock.config.jsonWebTokens.expiresProperty] = jwtData.expires;

      if (waterlock.config.jsonWebTokens.includeUserInJwtResponse) {
        result['user'] = user;
      }

      res.json(result);
    });
  }else if (typeof postResponse === 'string' && _isURI(postResponse)) {
    res.redirect(postResponse);
  } else {
    res.ok(postResponse);
  }
}

/**
 * handles registration failures
 *
 * @param  {Object} req  express request object
 * @param  {Object} res  expresss response object
 * @param  {Object} user the user instance
 * @param  {Object|String} error the error that caused the failure
 * @api public
 */
function registerFailure(req, res, user, error) {
  waterlock.logger.debug('user register failure');

  if (user) {
    var address = _addressFromRequest(req);

    var attempt = {
      company: user.id,
      successful: false
    };

    _.merge(attempt, address);

    waterlock.Attempt.create(attempt).exec(function(err) {
      if (err) {
        waterlock.logger.debug(err);
      }
    });
  }

  if (req.session.authenticated) {
    req.session.authenticated = false;
  }

  delete(req.session.user);

  // now respond or redirect
  var postResponse = _resolvePostAction(waterlock.config.postActions.register.failure,
    error);

  if (typeof postResponse === 'string' && _isURI(postResponse)) {
    res.redirect(postResponse);
  } else {
    res.forbidden(postResponse);
  }
}


/**
 * handles successful logins
 *
 * @param  {Object} req  express request object
 * @param  {Object} res  expresss response object
 * @param  {Object} user the user instance
 * @api public
 */
function loginSuccess(req, res, user) {
  waterlock.logger.debug('user login success');
  if (!user) {
    waterlock.logger.debug('loginSuccess requires a valid user object');
    return res.serverError();
  }

  var address = _addressFromRequest(req);

  var attempt = {
    company: user.id,
    successful: true
  };

  _.merge(attempt, address);

  waterlock.Attempt.create(attempt).exec(function(err) {
    if (err) {
      waterlock.logger.debug(err);
    }
  });

  // store user in && authenticate the session
  req.session.user = user;
  req.session.authenticated = true;
  // now respond or redirect
  var postResponse = _resolvePostAction(waterlock.config.postActions.login.success,
    user);
  if (postResponse === 'jwt') {
    //Returns the token immediately
    var jwtData = waterlock._utils.createJwt(req, res, user);

    Jwt.create({token: jwtData.token, uses: 0, owner_company: user.id}).exec(function(err){
      if(err){
        return res.serverError('JSON web token could not be created');
      }

      var result = {};

      result[waterlock.config.jsonWebTokens.tokenProperty] = jwtData.token || 'token';
      result[waterlock.config.jsonWebTokens.expiresProperty] = jwtData.expires || 'expires';

      if (waterlock.config.jsonWebTokens.includeUserInJwtResponse) {
        result['user'] = user;
      }

      res.json(result);
    });
  } else if(typeof postResponse === 'string' && _isURI(postResponse)){
    res.redirect(postResponse);
  } else {
    res.ok(postResponse);
  }
}

/**
 * handles login failures
 *
 * @param  {Object} req  express request object
 * @param  {Object} res  expresss response object
 * @param  {Object} user the user instance
 * @param  {Object|String} error the error that caused the failure
 * @api public
 */
function loginFailure(req, res, user, error) {
  waterlock.logger.debug('user login failure');

  if (user) {
    var address = _addressFromRequest(req);

    var attempt = {
      company: user.id,
      successful: false
    };

    _.merge(attempt, address);

    waterlock.Attempt.create(attempt).exec(function(err) {
      if (err) {
        waterlock.logger.debug(err);
      }
    });
  }

  if (req.session.authenticated) {
    req.session.authenticated = false;
  }

  delete(req.session.user);

  // now respond or redirect
  var postResponse = _resolvePostAction(waterlock.config.postActions.login.failure,
    error);

  if (typeof postResponse === 'string' && _isURI(postResponse)) {
    res.redirect(postResponse);
  } else {
    res.forbidden(postResponse);
  }
}

/**
 * handles logout events
 *
 * @param  {Object} req  express request object
 * @param  {Object} res  expresss response object
 * @api public
 */
function logout(req, res) {
  waterlock.logger.debug('user logout');
  delete(req.session.user);

  if (req.session.authenticated) {
    logoutSuccess(req, res);
  } else {
    logoutFailure(req, res);
  }
}

/**
 * the logout 'success' event
 *
 * @param  {Object} req express request object
 * @param  {Object} res express response object
 * @api public
 */
function logoutSuccess(req, res) {

  req.session.authenticated = false;

  var defaultString = 'You have successfully logged out.';

  // now respond or redirect
  var postResponse = _resolvePostAction(waterlock.config.postActions.logout.success,
    defaultString);

  if (typeof postResponse === 'string' && _isURI(postResponse)) {
    res.redirect(postResponse);
  } else {
    res.ok(postResponse);
  }
}

/**
 * the logout 'failure' event
 *
 * @param  {Object} req express request object
 * @param  {Object} res express response object
 * @api public
 */
function logoutFailure(req, res) {
  var defaultString = 'You have successfully logged out.';

  // now respond or redirect
  var postResponse = _resolvePostAction(waterlock.config.postActions.logout.failure,
    defaultString);

  if (typeof postResponse === 'string' && _isURI(postResponse)) {
    res.redirect(postResponse);
  } else {
    res.ok(postResponse);
  }
}

/**
 * Tries to check if the given string is a URI
 *
 * @param  {String}  str the string to check
 * @return {Boolean}     true if string is a URI
 * @api private
 */
function _isURI(str) {
  if (str.indexOf('/') === 0) { /* assume relative path */
    return true;
  } else if (str.indexOf('http') >= 0) { /* assume url */
    return true;
  }

  return false;
}

/**
 * returns an ip address and port from the express request object, or the
 * sails.io socket which is attached to the req object.
 *
 * @param  {Object} req express request
 * @return {Object}     the transport address object
 * @api private
 */
function _addressFromRequest(req) {
  if (req.connection && req.connection.remoteAddress) {
    return {
      ip: req.connection.remoteAddress,
      port: req.connection.remotePort
    };
  }

  if (req.socket && req.socket.remoteAddress) {
    return {
      ip: req.socket.remoteAddress,
      port: req.socket.remotePort
    };
  }

  return {
    ip: '0.0.0.0',
    port: 'n/a'
  };
}

/**
 * translates the mix postAction to a string
 *
 * @param  {String|Object} mix the postAction object|string
 * @param  {String|Object} def the default value to use if mix cannot be
 *                         translated or is 'default'
 * @return {String|Object} the translated postAction or default value
 * @api private
 */
function _resolvePostAction(mix, def){
  //If postAction is not defined fall back to default
  if(mix === 'default' || typeof mix === 'undefined') {
    return def;
  }

  if (typeof mix === 'object') {
    return _relativePathFromObj(mix);
  }

  return mix;
}

/**
 * returns the relative path from an object, the object is
 * expected to look like the following
 *
 * example:
 * {
 *   controller: 'foo',
 *   action: 'bar'
 * }
 *
 * @param  {Object} obj the redirect object
 * @return {String}     the relative path
 * @api private
 */
function _relativePathFromObj(obj) {
  if (typeof obj.controller === 'undefined' || typeof obj.action === 'undefined') {
    var error = new Error('You must define a controller and action to redirect to.').stack;
    throw error;
  }

  return '/' + obj.controller + '/' + obj.action;
}

/**
 * Simple wrapper for Auth find/populate method
 * 
 * @param  {Object}   criteria should be id to find the auth by
 * @param  {Function} cb         function to be called when the auth has been
 *                               found or an error has occurred 
 * @api public                          
 */
function findAuth(criteria, cb){
  waterlock.Auth.findOne(criteria).populate('company')
  .exec(function(err, auth){
    cb(err, _invertAuth(auth));
  });
}

/**
 * This will create a user and auth object if one is not found
 * 
 * @param  {Object}   criteria   should be id to find the auth by
 * @param  {Object}   attributes auth attributes
 * @param  {Function} cb         function to be called when the auth has been
 *                               found or an error has occurred 
 * @api private
 */
function _attachAuthToUser(criteria, auth, cb){

  // create the user
  if(!auth.company){
    Company.update(criteria, {auth:auth.id}).exec(function(err, company){
      if(err){
        waterlock.logger.debug(err);
        return cb(err);
      }

      // update the auth object
      waterlock.Auth.update(auth.id, {company:company.id}).exec(function(err, auth){
        if(err){
          waterlock.logger.debug(err);
          return cb(err);
        }

        company.auth = auth.shift();
        cb(err, company);  
      });
    });
  }else{
    // just fire off update to user object so we can get the 
    // backwards association going.
    if(!auth.company.auth){
      Company.update(auth.company.id, {auth:auth.id}).exec(function(){});
    }
    
    cb(null, _invertAuth(auth));
  }
}

/**
 * Find or create the auth then pass the results to _attachAuthToUser 
 * 
 * @param  {Object}   criteria   should be id to find the auth by
 * @param  {Object}   attributes auth attributes
 * @param  {Function} cb         function to be called when the auth has been
 *                               found or an error has occurred 
 *
 * @api public
 */
function findOrCreateAuth(criteria, attributes, cb){
  waterlock.Auth.findOrCreate(criteria, attributes)
  .exec(function(err, newAuth){
    if(err){
      waterlock.logger.debug(err);
      return cb(err);
    }

    Auth.findOne(newAuth.id).populate('company')
    .exec(function(err, auth){
      if(err){
        waterlock.logger.debug(err);
        return cb(err);
      }

      _attachAuthToUser(criteria, auth, cb);
    });
  });
}

/**
 * Attach given auth attributes to user 
 * 
 * @param  {Object}   attributes auth attributes
 * @param  {Object}   user       user instance
 * @param  {Function} cb         function to be called when the auth has been
 *                               attached or an error has occurred 
 * @api public
 */
function attachAuthToUser(attributes, company, cb){
  attributes.company = company.id;

  Company.findOne(company.id).exec(function(err, company){
    if(err){
      waterlock.logger.debug(err);
      return cb(err);
    }

    if(company.auth){
      delete(attributes.auth); 
      //update existing auth
      Auth.findOne(company.auth).exec(function(err, auth){
        if(err){
          waterlock.logger.debug(err); 
          return cb(err); 
        }

        // Check if any attribtues have changed if so update them
        if(_updateAuth(auth, attributes)){
           auth.save(function(err){
            if(err){
              waterlock.logger.debug(err); 
              return cb(err); 
            }
            company.auth = auth; 
            cb(err, company); 
          });           
        }else{
          company.auth = auth; 
          cb(err, company); 
        }

      });
    }else{
      // force create by pass of user id
      findOrCreateAuth(company.id, attributes, cb);
    }
  });
}

/**
 * Inverts the auth object so we don't need to run another query
 * 
 * @param  {Object} auth Auth object
 * @return {Object}      User object
 * @api private
 */
function _invertAuth(auth){
  // nothing to invert
  if(!auth || !auth.company){
    return auth; 
  }

  var u = auth.company;
  delete(auth.company);
  u.auth = auth;
  return u;
}

/**
 * Decorates the auth object with values of the attributes object
 * where the attributes differ from the auth
 * 
 * @param  {Object} auth       waterline Auth instance
 * @param  {Object} attributes used to update auth with
 * @return {Boolean}           true if any values were updated
 */
function _updateAuth(auth, attributes){
  var changed = false; 
  for(var key in attributes){
    if(attributes.hasOwnProperty(key)){
      if(auth[key] !== attributes[key]){
        auth[key] = attributes[key]; 
        changed = true; 
      }
    }
  }
  return changed;
}

module.exports = require('waterlock').actions.user({
  

  /**
   * Check the provided email address and password, and if they
   * match a real company in the database, sign in to Activity Overlord.
   */
  login: function (req, res) {

    // Try to look up company using the provided email address
    Company.findOne({
      email: req.param('email')
    }, function foundCompany(err, company) {
      if (err) return res.negotiate(err);
      if (!company) return res.notFound();

      // Compare password attempt from the form params to the encrypted password
      // from the database (`company.password`)
      require('machinepack-passwords').checkPassword({
        passwordAttempt: req.param('password'),
        encryptedPassword: company.encryptedPassword
      }).exec({

        error: function (err){
          return loginFailure(req, res, company, err);
        },

        // If the password from the form params doesn't checkout w/ the encrypted
        // password from the database...
        incorrect: function (){
          return res.notFound();
        },

        success: function (){

          // Store company id in the company session
          req.session.me = company.id;
          req.session.user = company;

          // All done- let the client know that everything worked.
          return loginSuccess(req, res, company);
        }
      });
    });

  },

  /**
   * Register for a company account.
   */
  register: function(req, res) {

    var Passwords = require('machinepack-passwords');

    // Encrypt a string using the BCrypt algorithm.
    Passwords.encryptPassword({
      password: req.param('password'),
      difficulty: 10,
    }).exec({
      // An unexpected error occurred.
      error: function(err) {
        return res.negotiate(err);
      },
      // OK.
      success: function(encryptedPassword) {
        require('machinepack-gravatar').getImageUrl({
          emailAddress: req.param('email')
        }).exec({
          error: function(err) {
            return res.negotiate(err);
          },
          success: function(gravatarUrl) {
          // Create a Company with the params sent from
          // the sign-up form --> register.ejs
            var params = waterlock._utils.allParams(req);
            var auth = {
              email: params.email,
              password: encryptedPassword
            }
            Company.create({
              companyname: req.param('companyname'),
              email: req.param('email'),
              phone_number: req.param('phone_number'),
              location: req.param('location'),
              address: req.param('address'),
              description: req.param('description'),
              adminname: req.param('adminname'),
              encryptedPassword: encryptedPassword,
              lastLoggedIn: new Date(),
              gravatarUrl: gravatarUrl
            }).exec({
              error: function(err) {

                console.log("err: ", err);
                console.log("err.invalidAttributes: ", err.invalidAttributes)

                // If this is a uniqueness error about the email attribute,
                // send back an easily parseable status code.
                if (err.invalidAttributes && err.invalidAttributes.email && err.invalidAttributes.email[0]
                  && err.invalidAttributes.email[0].rule === 'unique') {
                  return res.emailAddressInUse();
                }

                // Otherwise, send back something reasonable as our error response.
                return res.negotiate(err);
              },

              success: function(newCompany) {
                attachAuthToUser(auth, newCompany, function(err, ua) {
                  if (err) {
                    console.log(err);
                    return registerFailure(req, res, newCompany, err);
                  }
                  else {
                    // Log company in
                    req.session.me = newCompany.id;
                    req.session.user = newCompany;

                    return registerSuccess(req, res, newCompany);
                  }
                });
              }
            });
          }
        });
      }
    });
  },

  me: function (req, res) {
    return res.ok(req.session.user);
  },

  /**
   * Log out of Activity Overlord.
   * (wipes `me` from the sesion)
   */
  logout: function (req, res) {

    // Look up the company record from the database which is
    // referenced by the id in the company session (req.session.me)
    Company.find(req.session.me, function foundCompany(err, company) {
      if (err) return res.negotiate(err);

      // If session refers to a company who no longer exists, still allow logout.
      if (!company) {
        sails.log.verbose('Session refers to a company who no longer exists.');
        return res.backToHomePage();
      }

      // Wipe out the session (log out)
      delete(req.session.me);
      delete(req.session.user);

      // Either send a 200 OK or redirect to the home page
      return logoutSuccess(req, res);

    });
  },
});
