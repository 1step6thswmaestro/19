/**
 * Jwt
 *
 * @module      :: Model
 * @description :: Holds all distributed json web tokens
 * @docs        :: http://waterlock.ninja/documentation
 */

module.exports = {
  scheam: true,

  attributes: require('waterlock').models.jwt.attributes({
    
  owner_company: {
    model: 'company'
  }
    
  })
};
