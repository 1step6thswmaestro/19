/**
 * Use
 *
 * @module      :: Model
 * @description :: Tracks the usage of a given Jwt
 * @docs        :: http://waterlock.ninja/documentation
 */

module.exports = {
  schema: true,

  attributes: require('waterlock').models.use.attributes({
    
    /* e.g.
    nickname: 'string'
    */
    
  })
};
