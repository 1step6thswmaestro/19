/**
* Company.js
*
* @description :: TODO: You might write a short summary of how this model works and what it represents here.
* @docs        :: http://sailsjs.org/#!documentation/models
*/
var uuid = require('node-uuid');

module.exports = {
  // Enforce model schema in the case of schemaless databases
  schema: true,
  connection: 'someMysqlServer',
  tableName: 'Company',

  attributes: {
    email: {
      type: 'string',
      unique: true,
      required: true
    },

    encryptedPassword: {
      type: 'string',
      required: true
    },

    phone_number: {
      type: 'string',
      required: true
    },

    location: {
      type: 'string',
      enum: ['서울시 강남구', '서울시 강동구',
              '서울시 강북구', '서울시 강서구',
              '서울시 관악구', '서울시 광진구',
              '서울시 구로구', '서울시 금천구',
              '서울시 노원구', '서울시 도봉구',
              '서울시 동대문구', '서울시 동작구',
              '서울시 마포구', '서울시 서대문구',
              '서울시 서초구', '서울시 성동구',
              '서울시 성북구', '서울시 송파구',
              '서울시 양천구', '서울시 영등포구',
              '서울시 용산구', '서울시 은평구',
              '서울시 종로구', '서울시 중구',
              '서울시 중랑구'],
      required: true
    },

    address: {
      type: 'string',
      required: true
    },

    description: {
      type: 'text'
    },

    avg_point: {
      type: 'float',
      defaultsTo: 0
    },

    gravatarUrl: {
      type: 'string'
    },

    companyname: {
      type: 'string',
      required: true
    },

    adminname: {
      type: 'string',
      required: true
    },

    suggestion_sheets: {
      collection: 'Suggestion_sheet',
      via: 'suggester'
    },

    NaN: {
      type: 'boolean',
      defaultsTo: false
    },

    id: {
      type: 'string',
      unique: true,
      primaryKey: true
    },

    attempts: {
      collection: 'attempt',
      via: 'company'
    },

    jsonWebTokens: {
      collection: 'jwt',
      via: 'owner_company'
    },

    auth: {
      model: 'auth'
    }
  },

  beforeCreate: function(values, next) {
    values.id = uuid.v4();

    next();
  }
};

