/**
 * UserController
 *
 * @description :: Server-side logic for managing users
 * @help        :: See http://links.sailsjs.org/docs/controllers
 */

module.exports = require('waterlock').actions.user({
  

  /**
   * Check the provided email address and password, and if they
   * match a real user in the database, sign in to Activity Overlord.
   */
  login: function (req, res) {

    // Try to look up user using the provided email address
    User.findOne({
      email: req.param('email')
    }, function foundUser(err, user) {
      if (err) return res.negotiate(err);
      if (!user) return res.notFound();

      // Compare password attempt from the form params to the encrypted password
      // from the database (`user.password`)
      require('machinepack-passwords').checkPassword({
        passwordAttempt: req.param('password'),
        encryptedPassword: user.encryptedPassword
      }).exec({

        error: function (err){
          return waterlock.cycle.loginFailure(req, res, user, err);
        },

        // If the password from the form params doesn't checkout w/ the encrypted
        // password from the database...
        incorrect: function (){
          return res.notFound();
        },

        success: function (){       

          // Store user id in the user session
          req.session.me = user.id;
          req.session.user = user;

          // All done- let the client know that everything worked.
          return waterlock.cycle.loginSuccess(req, res, user);
        }
      });
    });

  },

  /**
   * Register for a user account.
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
          // Create a User with the params sent from
          // the sign-up form --> register.ejs
            var params = waterlock._utils.allParams(req);
            var auth = {
              email: params.email,
              password: encryptedPassword
            }
            User.create({
              username: req.param('username'),
              email: req.param('email'),
              phone_number: req.param('phone_number'),
              location: req.param('location'),
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

              success: function(newUser) {
                waterlock.engine.attachAuthToUser(auth, newUser, function(err, ua) {
                  if(err) {
                    console.log(err);   
                    return waterlock.cycle.registerFailure(req, res, newUser, err);
                  }
                  else {
                    // Log user in
                    req.session.me = newUser.id;
                    req.session.user = newUser;

                    return waterlock.cycle.registerSuccess(req, res, newUser);
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

    // Look up the user record from the database which is
    // referenced by the id in the user session (req.session.me)
    User.find(req.session.me, function foundUser(err, user) {
      if (err) return res.negotiate(err);

      // If session refers to a user who no longer exists, still allow logout.
      if (!user) {
        sails.log.verbose('Session refers to a user who no longer exists.');
        return res.backToHomePage();
      }

      // Wipe out the session (log out)
      delete(req.session.me);
      delete(req.session.user);


      // Either send a 200 OK or redirect to the home page
      return waterlock.cycle.logoutSuccess(req, res);

    });
  },
  
});
