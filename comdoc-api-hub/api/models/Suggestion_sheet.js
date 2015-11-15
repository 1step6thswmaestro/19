/**
* Suggestion_sheet.js
*
* @description :: TODO: You might write a short summary of how this model works and what it represents here.
* @docs        :: http://sailsjs.org/#!documentation/models
*/
var formatDate = require('format-date');

module.exports = {
  // Enforce model schema in the case of schemaless databases
  schema: true,
  connection: 'someMysqlServer',
  tableName: 'Suggestion_Sheets',

  attributes: {
    request_sheet: {
      model: 'Sheet'
    },

    suggester: {
      model: 'Company'
    },

    expect_price: {
      type: 'integer',
      required: true
    },

    expect_period: {
      type: 'string',
      required: true
    },

    comment: {
      type: 'text'
    },

    // 0: 초기값, 1: 거절, 2: 승인
    status: {
      type: 'integer',
      defaultsTo: 0
    },

    visit_time: {
      type: 'string',
      required: true
    },

    engineer: {
      type: 'string',
      required: true
    },

    engineer_phone: {
      type: 'string',
      required: true
    }
  },

  beforeCreate: function(values, next) {
    values.visit_time = formatDate('{year}-{month}-{day} {hours}:{minutes}:{seconds}', new Date(Date.parse(values.visit_time)));

    next();
  }
};