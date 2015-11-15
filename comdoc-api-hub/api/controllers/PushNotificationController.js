var request = require('request');
var Hashes = require('jshashes');

module.exports = {
  sendMsg: function(req, res) {
    var _api_key = 'NCS563C92E91CA2B';
    var _timestamp = Math.floor(new Date() / 1000);
    var _salt = (Math.random().toString(16)+"000000000").substr(2,8);
    var _signature = new Hashes.MD5().hex_hmac("4E323ED8AC6E6143611A64ECA6FB0626", _timestamp+_salt);

    console.log(_signature);
    console.log(_timestamp+_salt);

    console.log("to:"+req.param('to')+","+typeof req.param('to'));
    console.log("from:"+req.param('from')+","+typeof req.param('from'));
    console.log("text:"+req.param('text')+","+typeof req.param('text'));

    request({
      url: 'https://api.coolsms.co.kr/sms/1.5/send', //URL to hit
      method: 'POST',
      form: {
        api_key: _api_key,
        timestamp: _timestamp,
        salt: _salt,
        signature: _signature,
        to: req.param('to'),
        from: req.param('from'),
        text: req.param('text')
      }
    }, function(err, res){
      if(err) {
        console.log(err);
      } else {
        console.log(res.statusCode);
      }
    });

    return res.ok("메시지가 성공적으로 전송되었습니다.");
  }
}