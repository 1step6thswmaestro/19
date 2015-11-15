/**
* User.js
*
* @description :: TODO: You might write a short summary of how this model works and what it represents here.
* @docs        :: http://sailsjs.org/#!documentation/models
*/
var uuid = require('node-uuid');

module.exports = {
  // Enforce model schema in the case of schemaless databases
  schema: true,
  connection: 'someMysqlServer',
  tableName: 'Users',

  attributes: require('waterlock').models.user.attributes({
    isAdmin: {
      type: 'boolean',
      defaultsTo: false,
    },

    // The user's username
    // e.g. Genius
    username: {
      type: 'string',
      required: true
    },

    // The user's phone number
    // e.g. 010-9077-1639
    phone_number: {
      type: 'string',
      required: true
    },

    // The user's location
    // e.g. 서울시 동대문구
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

    // The user's email address
    // And it is User Table's primary key
    // e.g. nikola@tesla.com
    email: {
      type: 'string',
      required: true,
      unique: true
    },

    // The encrypted password for the user
    // e.g. asdgh8a249321e9dhgaslcbqn2913051#T(@GHASDGA
    encryptedPassword: {
      type: 'string',
      required: true
    },

    // url for gravatar
    // it is for user's thumbnail
    gravatarUrl: {
      type: 'string'
    },

    // The timestamp when the the user last logged in
    // (i.e. sent a username and password to the server)
    lastLoggedIn: {
      type: 'date',
      required: true,
      defaultsTo: new Date()
    },

    request_sheets: {
      collection: 'Sheet',
      via: 'requester'
    },

    id: {
      type: 'string',
      unique: true,
      primaryKey: true
    },

    NaN: {
      type: 'boolean',
      defaultsTo: false
    }

  }),

  beforeCreate: function(values, next) {
    values.id = uuid.v4();

    next();
  }
};
