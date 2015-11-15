/**
 * SheetController
 *
 * @description :: Server-side logic for managing sheets
 * @help        :: See http://sailsjs.org/#!/documentation/concepts/Controllers
 */
var async = require('async');

module.exports = {
  /**
   * 수리 사례 조회 - 사례를 넣으면 현재 수리 완료된 사례를 조회할 수 있다.
   * -parameter
   *   trouble_type
   *     1. 컴퓨터 전원이 들어오지 않음
   *     2. 컴퓨터 성능이 현저히 저하됌
   *     3. 인터넷이 안됌
   *     4. 알 수 없는 화면이 뜸
   *     5. 기타
   */

  findRepairCase: function (req, res) {

    var trouble_type = '';

    if (req.param('trouble_type') === '1') {
      trouble_type = '컴퓨터 전원이 들어오지 않음';
    }
    else if (req.param('trouble_type') === '2') {
      trouble_type = '컴퓨터 성능이 현저히 저하됌';
    }
    else if (req.param('trouble_type') === '3') {
      trouble_type = '인터넷이 안됌';
    }
    else if (req.param('trouble_type') === '4') {
      trouble_type = '알 수 없는 화면이 뜸';
    }
    else if (req.param('trouble_type') === '5') {
      trouble_type = '기타';
    }

    var query = 'SELECT Company.id as company_id,Company.email as company_email,Company.phone_number as company_phone,Company.location as company_location,Company.address as company_address,Company.description as company_description,Company.avg_point as company_avgpoint,Company.companyname as company_name,Company.adminname as company_admin_name,Sheets.location as sheet_location,Sheets.address as sheet_address,Sheets.requester_phone as requester_phone,Sheets.computer_type as computer_type,Sheets.trouble_type as trouble_type,Sheets.trouble_detail as trouble_detail,Sheets.used_year as used_year,Sheets.brand as brand,Sheets.final_price as final_price,Sheets.final_start_date as final_start_date,Sheets.final_end_date as final_end_date,Sheets.id as sheet_id,Suggestion_Sheets.comment as suggestion_comment, Suggestion_Sheets.expect_price as suggestion_price, Suggestion_Sheets.visit_time as suggestion_visit_time,Suggestion_Sheets.engineer as suggestion_sheet_id,Suggestion_Sheets.id as suggestion_sheet_id,Reviews.point as review_point,Reviews.comment as review_comment FROM Company, Sheets, Suggestion_Sheets, Reviews WHERE Suggestion_Sheets.suggester = Company.id AND Suggestion_Sheets.request_sheet = Sheets.id AND Reviews.review_comment = Sheets.id AND Sheets.matching_status = 2 AND Suggestion_Sheets.status = 2 AND Sheets.trouble_type="'+trouble_type+'"';

    console.log(query);

    Sheet.query(query,
      function(err, result) {
        if(err) {
          console.log(err);
          return;
        }
        console.log(result);
        
        return res.ok(result);
    });
  },


  

  findOneRepairCaseFromId: function(req, res) {
    var trouble_type = '';

    if (req.param('trouble_type') === '1') {
      trouble_type = '컴퓨터 전원이 들어오지 않음';
    }
    else if (req.param('trouble_type') === '2') {
      trouble_type = '컴퓨터 성능이 현저히 저하됌';
    }
    else if (req.param('trouble_type') === '3') {
      trouble_type = '인터넷이 안됌';
    }
    else if (req.param('trouble_type') === '4') {
      trouble_type = '알 수 없는 화면이 뜸';
    }
    else if (req.param('trouble_type') === '5') {
      trouble_type = '기타';
    }

    var query = 'SELECT Company.id as company_id,Company.email as company_email,Company.phone_number as company_phone,Company.location as company_location,Company.address as company_address,Company.description as company_description,Company.avg_point as company_avgpoint,Company.companyname as company_name,Company.adminname as company_admin_name,Sheets.location as sheet_location,Sheets.address as sheet_address,Sheets.requester_phone as requester_phone,Sheets.computer_type as computer_type,Sheets.trouble_type as trouble_type,Sheets.trouble_detail as trouble_detail,Sheets.used_year as used_year,Sheets.brand as brand,Sheets.final_price as final_price,Sheets.final_start_date as final_start_date,Sheets.final_end_date as final_end_date,Sheets.id as sheet_id,Suggestion_Sheets.comment as suggestion_comment, Suggestion_Sheets.expect_price as suggestion_price, Suggestion_Sheets.visit_time as suggestion_visit_time,Suggestion_Sheets.engineer as suggestion_sheet_id,Suggestion_Sheets.id as suggestion_sheet_id,Reviews.point as review_point,Reviews.comment as review_comment FROM Company, Sheets, Suggestion_Sheets, Reviews WHERE Suggestion_Sheets.suggester = Company.id AND Suggestion_Sheets.request_sheet = Sheets.id AND Reviews.review_comment = Sheets.id AND Sheets.matching_status = 2 AND Suggestion_Sheets.status = 2 AND Sheets.trouble_type="'+trouble_type+'" AND Sheets.id='+req.param('id');

    console.log(query);

    Sheet.query(query,
      function(err, result) {
        if(err) {
          console.log(err);
          return;
        }
        console.log(result);
        
        return res.ok(result);
    });
  }

};
