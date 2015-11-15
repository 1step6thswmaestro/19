var nodemailer = require('nodemailer');
var transporter = nodemailer.createTransport({
  service: 'gmail',
  auth: {
    user: 'comdoccontact@gmail.com',
    pass: 'comdoc123'
  }
});

module.exports = {
  sendMail: function(req, res) {
    var data = req.body;

    transporter.sendMail({
      from: data.contactEmail,
      to: 'east928@gmail.com',
      subject: 'Message from ' + data.contactName + ', ' + data.contactEmail + ', Companyname: '+ data.contactCompany,
      text: data.contactMsg
    }, function(err, res) {
      if(err) {
        console.log(err);
      }
      else {
        console.log("Mesage sent from " + data.contactEmail);
      }

      transporter.close();
    });

    res.json(data);
  }
};