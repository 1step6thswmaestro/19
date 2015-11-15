/**
* Review.js
*
* @description :: TODO: You might write a short summary of how this model works and what it represents here.
* @docs        :: http://sailsjs.org/#!documentation/models
*/

module.exports = {
  // Enforce model schema in the case of schemaless databases
  schema: true,
  connection: 'someMysqlServer',
  tableName: 'Reviews',

  attributes: {
    review_comment: {
      model: 'Sheet'
    },

    point: {
      type: 'integer',
      defaultsTo: 0
    },

    comment: {
      type: 'text'
    }
  }
};

