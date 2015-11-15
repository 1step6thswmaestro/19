/**
* Sheet.js
*
* @description :: TODO: You might write a short summary of how this model works and what it represents here.
* @docs        :: http://sailsjs.org/#!documentation/models
*/
var formatDate = require('format-date');

module.exports = {
  // Enforce model schema in the case of schemaless databases
  schema: true,
  connection: 'someMysqlServer',
  tableName: 'Sheets',

  attributes: {
    suggestion_sheets: {
      collection: 'Suggestion_sheet',
      via: 'request_sheet'
    },

    requester: {
      model: 'User'
    },

    review_comment: {
      model: 'Review'
    },

    location: {
      type: 'string',
      required: true
    },

    address: {
      type: 'text',
      required: true
    },

    requester_phone: {
      type: 'string',
      required: true
    },

    computer_type: {
      type: 'string',
      enum: ['데스크탑', '노트북'],
      required: true
    },

    available_time: {
      type: 'string',
      required: true
    },

    trouble_type: {
      type: 'string',
      required: true
    },

    used_year: {
      type: 'string'
    },

    brand: {
      type: 'string'
    },

    // 0: 연결중, 1: 연결완료, 2: 수리완료
    matching_status: {
      type: 'integer',
      defaultsTo: 0
    },

    final_price: {
      type: 'integer'
      //required: true
    },

    created_date: {
      type: 'string'
    },

    trouble_detail: {
      type: 'text'
    },

    final_start_date: {
      type: 'string'
      //required: true
    },

    final_end_date: {
      type: 'string'
      //required: true
    },

    include: {
      type: 'string'
    }

  },

  beforeCreate: function(values, next) {
    values.created_date = formatDate('{year}-{month}-{day}', new Date);

    next();
  }
};

