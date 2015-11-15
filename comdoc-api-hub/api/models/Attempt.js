/**
 * Attempt
 *
 * @module      :: Model
 * @description :: Tracks login attempts of users on your app.
 * @docs        :: http://waterlock.ninja/documentation
 */

module.exports = {
  schema: true,

  attributes: require('waterlock').models.attempt.attributes({
    
   company: {
    model: 'company'
   }
    
  })
};