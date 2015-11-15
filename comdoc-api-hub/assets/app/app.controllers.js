/* Structure Controllers  */
app.controller('NavigationController', function($scope, $http, ROOT, ngProgressFactory, $window) {
	$scope.Root = ROOT;
	$scope.brand = 'ComDoc';
    $scope.isCollapsed = true;
    $scope.progressbar = ngProgressFactory.createInstance();
	$scope.progressbar.setColor('#02bbff');
    $scope.progressbar.start();
    $scope.$on('$routeChangeStart', function(next, current) {
		$scope.isCollapsed = true;
		$scope.progressbar.start();
	});
  $scope.user = localStorage.user;
  $scope.company = localStorage.company;
  $scope.mobile = localStorage.mobile;
  $scope.mobilehome = localStorage.mobilehome;
  var username = "";
  if(localStorage.user) {
  	username = JSON.parse(localStorage.user).user.username;
  }
  else {
  	username = "";
  }
  var companyname = "";
  if(localStorage.company) {
  	companyname = JSON.parse(localStorage.company).user.companyname;
  }
  else {
  	companyname = "";
  }
	$scope.menu = {
		items: [
			{
			    link: ROOT+"/",
			    name: "Home"
			},
			{
			    link: ROOT+"/about",
			    name: "About"
			},
			{
			    link: ROOT+"/contact",
			    name: "Contact"
			},
			{
					link: ROOT+"/repaircase",
					name: "Repair Case"
			},
			{
			    link: ROOT+"/login",
			    name: "Log In",
			    BeforeLogIn: true
			},
			{
			    link: ROOT+"/register",
			    name: "Register",
			    BeforeLogIn: true
			},
			{
					name: username,
					needAuth: true,
					userPage: true,
					items: [
							{
									link: ROOT+"/mypage/user",
									name: "My Page"
							},
							{
									link: ROOT+"/mypage/user/profile",
									name: "My Profile"
							}
					]
			},
			{
					name: companyname,
					needAuth: true,
					companyPage: true,
					items: [
							{
									link: ROOT+"/mypage/company",
									name: "My Page"
							},
							{
									link: ROOT+"/mypage/company/profile",
									name: "My Profile"
							}
					]
			},
			{
				name: "MyPage",
				mobile: true,
				link: ROOT+"/mobile/mypage"
			},
			{
				name: "Profile",
				mobile: true,
				link: ROOT+"/mypage/company/profile"
			},
			{
				link: ROOT+"/logout",
				name: "Log Out",
				needAuth: true,
				click: function () {
					localStorage.removeItem('user');
					localStorage.removeItem('company');
					$http.get('/logout').then(function onSuccess(sailsResponse){
						window.location = '/';
					})
					.catch(function onError(sailsResponse) {
						window.location = '/';
					});
				}
			},
			{
				link: ROOT+"/logout",
				name: "Log Out",
				needAuth: true,
				mobile: true,
				click: function () {
					localStorage.removeItem('company');
					localStorage.removeItem('mobile');
					$http.get('/logout').then(function onSuccess(sailsResponse){
						localStorage.mobilehome =  true;
						window.location = '/mobile/home';
					})
					.catch(function onError(sailsResponse) {
						localStorage.mobilehome =  true;
						window.location = '/mobile/home';
					});
				}
			}
		]
	}
	$scope.scroll = 0;
	$scope.$on('$routeChangeSuccess', function () {
		$scope.progressbar.complete();
	});
	$scope.top = function() {
		if(document.querySelector(".main")) return document.querySelector(".main").getBoundingClientRect().top;
	};
});
app.controller('FooterController', function($scope, ROOT) {
	$scope.brand = 'ComDoc';
	$scope.mobile = localStorage.mobile;
	$scope.mobilehome = localStorage.mobilehome;
});

app.controller('HomeController', function($scope, $http, ROOT) {
	$scope.Root = ROOT;
	$scope.top = {
		slider: [
			{
				heading: "믿을 수 있는 컴퓨터수리, ComDoc",
				description: "이제는 믿고 맡길 수 있는 컴퓨터 수리 비교견적 서비스입니다.",
				//image: ROOT+"/assets/img/parallax_slider/2.png"
			},
			{
				heading: "합리적인 컴퓨터수리, ComDoc",
				description: "저희 ComDoc을 통해서라면 합리적인 컴퓨터 수리가 가능합니다.",
				//image: ROOT+"/assets/img/parallax_slider/3.png"
			},
			{
				heading: "쉽고 빠른 컴퓨터수리, ComDoc",
				description: "쉽고 빠르게 컴퓨터 수리 비교견적을 해보세요.",
				//image: ROOT+"/assets/img/parallax_slider/1.png"
			},
		],
		backstretch: [
			ROOT+'/assets/img/big/test.jpg',
			// ROOT+'/assets/img/big/big-1.jpg',
			// ROOT+'/assets/img/big/big-2.jpg',
			// ROOT+'/assets/img/big/big-4.jpg'
		],
		// backstretch_xs: [
		// 	ROOT+'/assets/img/big/test_xs.jpg',
		// 	// ROOT+'/assets/img/big/big-1.jpg',
		// 	// ROOT+'/assets/img/big/big-2.jpg',
		// 	// ROOT+'/assets/img/big/big-4.jpg'
		// ]
	};

	if(localStorage.mobile || localStorage.mobilehome) {
		localStorage.removeItem('mobilehome');
		localStorage.removeItem('mobile');
		window.location = '/';
	}
	

	$http.get('/auth/isAuthenticated').then(function onSuccess(res) {
		if(res.status === 200) {
			//alert("authenticated");
			if(!(localStorage.user || localStorage.company)) {
				//alert("something wrong");
			}
		}
	}).catch(function onError(sailsResponse) {
		if(sailsResponse.status === 401) {
			//alert("not authenticated");
			if(localStorage.user || localStorage.company) {
				localStorage.removeItem('user');
				localStorage.removeItem('company');
				window.location = '/';
			}
		}
	})
});

app.controller('AboutController', function($scope, ROOT) {
	$scope.top = {
		title: "About",
		backstretch: [
			// ROOT+'/assets/img/big/big-1.jpg',
			// ROOT+'/assets/img/big/big-2.jpg',
			// ROOT+'/assets/img/big/big-4.jpg'
			ROOT+'/assets/img/big/test.jpg'
		]
	};
	$scope.slider = [
		// ROOT+'/assets/img/slider/slider-1.jpg',
		// ROOT+'/assets/img/slider/slider-2.jpg',
		// ROOT+'/assets/img/slider/slider-3.jpg',
		// ROOT+'/assets/img/slider/slider-4.jpg'
	];
});

app.controller('ContactController', function($scope, $http, ROOT) {
	$scope.top = {
		title: "Contact",
		//backstretch: [ ROOT+'/assets/img/big/big-5.jpg' ]
		backstretch: [ ROOT+'/assets/img/big/test.jpg' ]
	};
	
	$scope.submitEmailForm = function() {

		$http.post('/contact-form', {
			contactName: $scope.emailForm.contactName,
			contactCompany: $scope.emailForm.contactCompany,
			contactEmail: $scope.emailForm.contactEmail,
			contactMsg: $scope.emailForm.contactMsg
		})
		.then(function onSuccess(res) {
			alert("메시지가 성공적으로 전달되었습니다.");

			if($scope.emailForm.contactCompany) {
				window.location = '/contact';
			}
			else if (localStorage.mobile || localStorage.mobilehome){
				window.location = '/mobile/home';
			}
			else {
				window.location = '/';
			}
		})
		.catch(function onError(sailsResponse) {
			//alert("메시지 송신 오류");
		})
	};
});

app.controller('LoginController', function($scope, $http, toastr, ROOT) {
	$scope.Root = ROOT;
	$scope.top = {
		login: true,
		//backstretch: [ ROOT+'/assets/img/big/big-1.jpg' ]
		backstretch: [ ROOT+'/assets/img/big/test.jpg' ]
	};
	$scope.loginForm = {
		type: 'user'
	};

	$scope.submitLoginForm = function (){

    // Set the loading state (i.e. show loading spinner)
    $scope.loginForm.loading = true;

    if ($scope.loginForm.type == "user") {
    	// Submit request to Sails.
	    $http.post('/login/user', {
	      email: $scope.loginForm.email,
	      password: $scope.loginForm.password
	    })
	    .then(function onSuccess (res){
	    	localStorage.user = JSON.stringify(res.data);  // 불러올때: JSON.parse(localStorage.user)

	      // Refresh the page now that we've been logged in.
	      window.location = '/';

	      // var bu = JSON.parse(localStorage.user);
	      // window.alert(localStorage.user);
	      // window.alert(bu.user.id);

	    })
	    .catch(function onError(sailsResponse) {

	      // Handle known error type(s).
	      // Invalid username / password combination.
	      if (sailsResponse.status === 400 || 404) {
	        // $scope.loginForm.topLevelErrorMessage = 'Invalid email/password combination.';
	        //
	        toastr.error('Invalid email/password combination.', 'Error', {
	          closeButton: true
	        });
	        return;
	      }

	        toastr.error('An unexpected error occurred, please try again.', 'Error', {
	          closeButton: true
	        });
	        return;

	    })
	    .finally(function eitherWay(){
	      $scope.loginForm.loading = false;
	    });
    }

    else {
    	// Submit request to Sails.
	    $http.post('/login/company', {
	      email: $scope.loginForm.email,
	      password: $scope.loginForm.password
	    })
	    .then(function onSuccess (res){
	    	localStorage.company = JSON.stringify(res.data);  // 불러올때: JSON.parse(localStorage).company

	      // Refresh the page now that we've been logged in.
	      window.location = '/';
	    })
	    .catch(function onError(sailsResponse) {

	      // Handle known error type(s).
	      // Invalid username / password combination.
	      if (sailsResponse.status === 400 || 404) {
	        // $scope.loginForm.topLevelErrorMessage = 'Invalid email/password combination.';
	        //
	        toastr.error('Invalid email/password combination.', 'Error', {
	          closeButton: true
	        });
	        return;
	      }

	        toastr.error('An unexpected error occurred, please try again.', 'Error', {
	          closeButton: true
	        });
	        return;

	    })
	    .finally(function eitherWay(){
	      $scope.loginForm.loading = false;
	    });
    }
  };
});

app.controller('LogoutController', function($scope) {

	$scope.logout = function () {
		localStorage.removeItem('user');
		localStorage.removeItem('company');
		$http.get('/logout').then(function onSuccess(sailsResponse){
		window.location = '/';
		})
	};

});

app.controller('RegisterController', function($scope, $http, toastr, ROOT) {
	$scope.top = {
		register: true,
		//backstretch: [ ROOT+'/assets/img/big/big-1.jpg' ]
		backstretch: [ ROOT+'/assets/img/big/test.jpg' ]
	};
	// set-up loading state
  $scope.registerForm = {
    loading: false
  }

  $scope.submitRegisterForm = function(){

    // Set the loading state (i.e. show loading spinner)
    $scope.registerForm.loading = true;

    if($scope.registerForm.username) {
    	$http.post('/register/user', {
    		username: $scope.registerForm.username,
	      email: $scope.registerForm.email,
	      phone_number: $scope.registerForm.phone_number,
	      location: $scope.registerForm.location,
	      password: $scope.registerForm.password
    	})
    	.then(function onSuccess(res){
    		localStorage.user = JSON.stringify(res.data);  // 불러올때: JSON.parse(localStorage).user

      	window.location = '/';
	    })
	    .catch(function onError(sailsResponse){

		    // Handle known error type(s).
		    // If using sails-disk adpater -- Handle Duplicate Key
		    var emailAddressAlreadyInUse = sailsResponse.status === 409;

		    if (emailAddressAlreadyInUse) {
		      toastr.error('That email address has already been taken, please try again.', 'Error');
		      return;
		    }
		    else if (sailsResponse.status === 400 || 404 || 403) {
		    	toastr.error('Something wrong, try again.',sailsResponse.status);

					return;
		    }

	    })
	    .finally(function eitherWay(){
	      $scope.registerForm.loading = false;
	    })
    }

    else if ($scope.registerForm.companyname) {
    	$http.post('/register/company', {
    		email: $scope.registerForm.email,
    		password: $scope.registerForm.password,
    		companyname: $scope.registerForm.companyname,
    		location: $scope.registerForm.location,
    		address: $scope.registerForm.address,
    		adminname: $scope.registerForm.adminname,
    		phone_number: $scope.registerForm.phone_number,
    		description: $scope.registerForm.description
    	})
    	.then(function onSuccess(res){
    		localStorage.company = JSON.stringify(res.data);  // 불러올때: JSON.parse(localStorage).company

      	window.location = '/';
	    })
	    .catch(function onError(sailsResponse){

		    // Handle known error type(s).
		    // If using sails-disk adpater -- Handle Duplicate Key
		    var emailAddressAlreadyInUse = sailsResponse.status === 409;

		    if (emailAddressAlreadyInUse) {
		      toastr.error('That email address has already been taken, please try again.', 'Error');
		      return;
		    }
		    else if (sailsResponse.status === 400 || 404 || 403) {
		    	toastr.error('Somthing wrong, try again.',sailsResponse.status);

					return;
		    }

	    })
	    .finally(function eitherWay(){
	      $scope.registerForm.loading = false;
	    })
    }
  }
});

app.controller('MypageUserController', function($scope, $http, ROOT) {
	$scope.Root = ROOT;
	$scope.top = {
		title: "My Page - User",
		backstretch: [
			// ROOT+'/assets/img/big/big-3.jpg',
			// ROOT+'/assets/img/big/big-5.jpg'
			ROOT+'/assets/img/big/test.jpg'
		]
	};

	var bu = JSON.parse(localStorage.user);
	var uid = bu.user.id;


	$http.get('/sheets/user/' + uid).success(function(data) {
	      for (var i = 0; i < data.length; i++) {
	        data[i].index = i;
	        data[i].click = function(item) {
	        	//alert(item.matching_status);
	        	// alert(JSON.stringify(the_data));
	        	if (item.matching_status === 0) {
	        		//$http.get('/mypage/user/sheet/detail/' + item.id).success(function() {});
	        		window.location = '/mypage/user/sheet/detail/'+item.id;
	        	}
	        	else if (item.matching_status === 1 || item.matching_status === 2) {
	        		//$http.get('/mypage/user/sheet/detail/after_adopted/' + item.id).success(function() {});
	        		window.location = '/mypage/user/sheet/detail/after_adopted/'+item.id;
	        	}
	        }
	      }
	      $scope.items = data;
	 });


	// $scope.submitUserSheetDetailForm = function() {
	// 	var sheet_id = $scope.userSheetForm;

	// 	window.alert(sheet_id);

	// 	$http.get('/sheet/find/'+sheet_id).success(function(data) {
	//   	$scope.the_item = data;
	//  	});
	// }


});

app.controller('MypageCompanyController', function($scope, $http, ROOT) {
	$scope.Root = ROOT;
	$scope.top = {
		title: "My Page - Company",
		mobile: localStorage.mobile,
		backstretch: [
			// ROOT+'/assets/img/big/big-3.jpg',
			// ROOT+'/assets/img/big/big-5.jpg'
			ROOT+'/assets/img/big/test.jpg'
		],
		backstretch_xs: [ ROOT+'/assets/img/big/test_xs.jpg' ]
	};

	var bu = JSON.parse(localStorage.company);
	var uid = bu.user.id;

	$http.get('/suggestion_sheets/company/'+ uid).success(function(data) {
	      for (var i = 0; i < data.length; i++) {
	        data[i].index = i;
	      }
	      $scope.items = data;
	      //console.log(data);
	 });



});

app.controller('MypageUserSheetController', function($scope, $http, toastr, ROOT, $routeParams) {
	$scope.Root = ROOT;
	$scope.top = {
		title: "My Page - User",
		backstretch: [
			// ROOT+'/assets/img/big/big-3.jpg',
			// ROOT+'/assets/img/big/big-5.jpg'
			ROOT+'/assets/img/big/test.jpg'
		]
	};

	$scope.user_phone_number = JSON.parse(localStorage.user).user.phone_number;
	$scope.user_location = JSON.parse(localStorage.user).user.location;

	$scope.submitUserSheetForm = function(){

    // Set the loading state (i.e. show loading spinner)
    $scope.userSheetForm.loading = true;

    var uid = JSON.parse(localStorage.user);

    if(true) {
    	$http.post('/insert/sheet', {
    		requester: uid.user.id,
    	  location: $scope.userSheetForm.location,
	      address: $scope.userSheetForm.address,
	      requester_phone: $scope.userSheetForm.requester_phone,
	      computer_type: $scope.userSheetForm.computer_type,
	      brand: $scope.userSheetForm.brand,
	      used_year: $scope.userSheetForm.used_year,
	      trouble_type: $scope.userSheetForm.trouble_type,
	      trouble_detail: $scope.userSheetForm.trouble_detail,
	      available_time: $scope.userSheetForm.available_time,
	      include: $routeParams.id
    	})
    	.then(function onSuccess(sailsResponse){
    		if($routeParams.id) {
    			$http.get('/company/find/'+$routeParams.id)
  				.success(function(data) {
	  				//alert(data.phone_number);
						$http.get('/push_notification?to='+data.phone_number+'&from=010-4561-8243&text=새로운 컴퓨터 출장수리 고객 요청이 들어왔습니다. -ComDoc')
	  				.success(function() { 
	  					//alert("메시지전송 성공");
	  					window.location = '/mypage/user';
	  				});
					});
    		}
    		else {
					window.location = '/mypage/user';
    		}
	    })
	    .catch(function onError(sailsResponse){

	    })
	    .finally(function eitherWay(){
	      $scope.userSheetForm.loading = false;
	    })
    }
	}
});

app.controller('MypageCompanySheetController', function($scope, $http, toastr, ROOT, $routeParams) {
	$scope.Root = ROOT;
	$scope.top = {
		title: "My Page - Company",
		mobile: localStorage.mobile,
		backstretch: [
			// ROOT+'/assets/img/big/big-3.jpg',
			// ROOT+'/assets/img/big/big-5.jpg'
			ROOT+'/assets/img/big/test.jpg'
		],
		backstretch_xs: [ ROOT+'/assets/img/big/test_xs.jpg' ]
	};

	$scope.submitCompanySheetForm = function(){

    // Set the loading state (i.e. show loading spinner)
    $scope.companySheetForm.loading = true;

    var uid = JSON.parse(localStorage.company);

    if(true) {
    	$http.post('/insert/suggestion_sheet', {
    		request_sheet: $routeParams.id,
    		suggester: uid.user.id,
    	  expect_price: $scope.companySheetForm.expect_price,
    	  expect_period: $scope.companySheetForm.expect_period,
    	  comment: $scope.companySheetForm.comment,
    	  visit_time: $scope.companySheetForm.visit_time,
    	  engineer: $scope.companySheetForm.engineer,
    	  engineer_phone: $scope.companySheetForm.engineer_phone
    	})
    	.then(function onSuccess(sailsResponse){
    		$http.get('/sheet/find/'+$routeParams.id)
  			.success(function(data) {
  				//alert(data.requester_phone);
					$http.get('/push_notification?to='+data.requester_phone+'&from=010-4561-8243&text=새로운 컴퓨터 수리 견적 제안서가 도착했습니다. -ComDoc')
  				.success(function() { 
  					//alert("메시지전송 성공");
  					window.location = '/mypage/company';
  				});
				});
	    })
	    .catch(function onError(sailsResponse){

	    })
	    .finally(function eitherWay(){
	      $scope.companySheetForm.loading = false;
	    })
    }
	}
});

app.controller('MypageCompanyRequestSheetController', function($scope, $http, ROOT) {
	$scope.Root = ROOT;
	$scope.top = {
		title: "My Page - Company",
		mobile: localStorage.mobile,
		backstretch: [
			// ROOT+'/assets/img/big/big-3.jpg',
			// ROOT+'/assets/img/big/big-5.jpg'
			ROOT+'/assets/img/big/test.jpg'
		],
		backstretch_xs: [ ROOT+'/assets/img/big/test_xs.jpg' ]
	};


	var bu = JSON.parse(localStorage.company);
	var location = bu.user.location;
	var id = bu.user.id;
	$scope.company_id = id;
	//alert($scope.company_id);

	$http.get('/sheets/location/'+location+'/'+id).success(function(data) {
	      for (var i = 0; i < data.length; i++) {
	      	if (data[i].index === i) {
	      		continue;
	      	}
	        data[i].index = 1;

	        // 이미 견적제안한것들 제외처리
	        if(data[i].suggestion_sheets) {
	        	for (var j = 0; j < data[i].suggestion_sheets.length; j++) {
		        	if (data[i].suggestion_sheets[j].suggester === id) {
		        		data[i].suggested = true;
		        		continue;
	        	}
	        		else if (data[i].suggestion_sheets[j].suggester === undefined) {
		        		continue;
		        	}
	        	}
	        }

	      }

	      $scope.items = data;

	 }).then(function removeSuggested() {
	 	for (var i = 0; i < $scope.items.length; i++) {
	 		// 이미 견적제안한것들 제외처리
    	if($scope.items[i].suggested === true) {
    		$scope.items.splice(i, 1);
    		i = i-1;
    	}
	  }
	 });
});

app.controller('MypageUserSheetDetailController', function($scope, $http, ROOT, $routeParams) {
	$scope.Root = ROOT;
	$scope.top = {
		title: "My Page - User",
		backstretch: [
			// ROOT+'/assets/img/big/big-3.jpg',
			// ROOT+'/assets/img/big/big-5.jpg'
			ROOT+'/assets/img/big/test.jpg'
		]
	};

	$http.get('/sheet/find/'+$routeParams.id).success(function(data) {
		data.click = function() {
	  	$http.delete('/sheet/destroy/'+$routeParams.id).success(function(){
	  		window.location = '/mypage/user';
	  	});
	  }
		$scope.the_item = data;

	});

	// /suggestion_sheets/request_sheet/:id
	$http.get('/suggestion_sheets/request_sheet/'+$routeParams.id).success(function(data) {
		for (var i = 0; i < data.length; i++) {
	        data[i].index = i;
	      }
	      $scope.suggestion_sheets = data;
	});




});

app.controller('MypageCompanySheetDetailController', function($scope, $http, ROOT, $routeParams) {
	$scope.Root = ROOT;
	$scope.top = {
		title: "My Page - Company",
		mobile: localStorage.mobile,
		backstretch: [
			// ROOT+'/assets/img/big/big-3.jpg',
			// ROOT+'/assets/img/big/big-5.jpg'
			ROOT+'/assets/img/big/test.jpg'
		],
		backstretch_xs: [ ROOT+'/assets/img/big/test_xs.jpg' ]
	};

	$http.get('/suggestion_sheet/find/'+$routeParams.id).success(function(data) {
		data.click = function() {
	  	$http.delete('/suggestion_sheet/destroy/'+$routeParams.id).success(function(){
	  		window.location = '/mypage/company';
	  	});
	  }

		$scope.the_item = data;
	});

	$http.get('/request_sheet/suggestion_sheet/'+$routeParams.id).success(function(the_data) {
		$scope.request_sheet = the_data[0];
		console.log(the_data[0]);
		$http.get('/review/sheet/'+the_data[0].id).success(function(review_data) {
		$scope.review = review_data[0];
		});
	});




});

app.controller('MypageCompanyRequestSheetDetailController', function($scope, $http, ROOT, $routeParams) {
	$scope.Root = ROOT;
	$scope.top = {
		title: "My Page - Company",
		mobile: localStorage.mobile,
		backstretch: [
			// ROOT+'/assets/img/big/big-3.jpg',
			// ROOT+'/assets/img/big/big-5.jpg'
			ROOT+'/assets/img/big/test.jpg'
		],
		backstretch_xs: [ ROOT+'/assets/img/big/test_xs.jpg' ]
	};

	$http.get('/sheet/find/'+$routeParams.id).success(function(data) {
				$scope.the_item = data;
	 });

});

app.controller('MypageUserAdoptedController', function($scope, $http, ROOT, $routeParams) {
	$scope.Root = ROOT;
	$scope.top = {
		title: "My Page - Company",
		backstretch: [
			// ROOT+'/assets/img/big/big-3.jpg',
			// ROOT+'/assets/img/big/big-5.jpg'
			ROOT+'/assets/img/big/test.jpg'
		]
	};

	$http.get('/suggestion_sheets/adopted/'+$routeParams.sheet_id+'/'+$routeParams.suggestion_sheet_id).success(function() {
		$http.get('/sheet/find/'+$routeParams.sheet_id)
		.success(function(data1) {
			//alert(data1.requester_phone);
			$http.get('/push_notification?to='+data1.requester_phone+'&from=010-4561-8243&text=견적서 채택이 완료되었습니다. 해당 수리업체가 고객님의 집에 방문할 예정입니다. -ComDoc')
			.success(function() { 
				$http.get('/suggestion_sheet/find/'+$routeParams.suggestion_sheet_id)
				.success(function(data2) {
					//alert(data2.engineer_phone);
					$http.get('/push_notification?to='+data2.engineer_phone+'&from=010-4561-8243&text=제안서가 채택되었습니다. 방문예정시각은 '+data2.visit_time+' 입니다. -ComDoc')
					.success(function() {
						//alert("메시지전송 성공");
					})
				})
				// alert("메시지전송 성공");
				// window.location = '/mypage/company';
			});
		});
	});

	$http.get('/sheet/find/'+$routeParams.sheet_id).success(function(data) {
		$scope.the_item = data;
	 });

	$http.get('/suggestion_sheet/find?id='+$routeParams.suggestion_sheet_id).success(function(data) {
	  $scope.suggestion_sheet = data;
	})

});


app.controller('MypageUserSheetDetailAfterAdoptedController', function($scope, $http, ROOT, $routeParams) {
	$scope.Root = ROOT;
	$scope.top = {
		title: "My Page - User",
		backstretch: [
			// ROOT+'/assets/img/big/big-3.jpg',
			// ROOT+'/assets/img/big/big-5.jpg'
			ROOT+'/assets/img/big/test.jpg'
		]
	};



	$http.get('/sheet/find/'+$routeParams.id).success(function(data) {
		$scope.the_item = data;
		console.log(data);

		// /suggestion_sheets/request_sheet/:id
		$http.get('/suggestion_sheets/request_sheet/'+data.id).success(function(the_data) {
			for (var i = 0; i < the_data.length; i++) {
		        the_data[i].index = i;

		        if(the_data[i].status === 2) {
		        	$scope.suggestion_sheet = the_data[i];

		        	break;
		        }
		      }
		});
	});

	$http.get('/review/sheet/'+$routeParams.id).success(function(review_data) {
		$scope.review = review_data[0];
	});

});


app.controller('MypageUserRepairCompletedController', function($scope, $http, ROOT, $routeParams) {
	$scope.Root = ROOT;
	$scope.top = {
		title: "My Page - Company",
		backstretch: [
			// ROOT+'/assets/img/big/big-3.jpg',
			// ROOT+'/assets/img/big/big-5.jpg'
			ROOT+'/assets/img/big/test.jpg'
		]
	};

	$http.get('/suggestion_sheets/request_sheet/'+$routeParams.id).success(function(the_data) {
		for (var i = 0; i < the_data.length; i++) {
		  the_data[i].index = i;

	    if(the_data[i].status === 2) {
	    	$scope.suggestion_sheet = the_data[i];

	    	break;
	    }
  	}
	});



	$scope.submitUserSheetForm = function(){

    // Set the loading state (i.e. show loading spinner)
    $scope.userSheetForm.loading = true;

    if(true) {
    	$http.post('/insert/review', {
				review_comment: $routeParams.id,
				point: $scope.userSheetForm.point,
				comment: $scope.userSheetForm.comment
    	})
    	.then(function onSuccess(sailsResponse){
    		$http.put('/sheets/repairCompleted', {
    			id: $routeParams.id,
					final_start_date: $scope.userSheetForm.final_start_date,
					final_end_date: $scope.userSheetForm.final_end_date,
					final_price: $scope.userSheetForm.final_price
				}).then(function onSuccess(sailsResponse) {
					
      		//alert($scope.suggestion_sheet.engineer_phone);
      		$http.get('/push_notification?to='+$scope.suggestion_sheet.engineer_phone+'&from=010-4561-8243&text=고객님께서 귀사의 출장수리 서비스에 리뷰를 등록하셨습니다. -ComDoc')
					.success(function() {
						//alert("메시지전송 성공");
					})

				});

      	window.location = '/mypage/user/sheet/detail/after_adopted/'+$routeParams.id;
	    })
	    .catch(function onError(sailsResponse){

	    })
	    .finally(function eitherWay(){
	      $scope.userSheetForm.loading = false;
	    })
	  }
	}

});

app.controller('MypageUserProfileController', function($scope, $http, ROOT) {
	$scope.Root = ROOT;
	$scope.top = {
		title: "My Page - User",
		backstretch: [
			// ROOT+'/assets/img/big/big-3.jpg',
			// ROOT+'/assets/img/big/big-5.jpg'
			ROOT+'/assets/img/big/test.jpg'
		]
	};

	var bu = JSON.parse(localStorage.user);
	var uid = bu.user.id;

	$http.get('/user/find?id='+uid).success(function(data) {
		$scope.user = data;
	});

});

app.controller('MypageUserProfileEditController', function($scope, $http, ROOT) {
	$scope.Root = ROOT;
	$scope.top = {
		title: "My Page - User",
		backstretch: [
			// ROOT+'/assets/img/big/big-3.jpg',
			// ROOT+'/assets/img/big/big-5.jpg'
			ROOT+'/assets/img/big/test.jpg'
		]
	};

	var bu = JSON.parse(localStorage.user);
	var uid = bu.user.id;

	$scope.user_username = bu.user.username;
	$scope.user_email = bu.user.email;
	$scope.user_location = bu.user.location;
	$scope.user_phone_number = bu.user.phone_number;

	$http.get('/user/find?id='+uid).success(function(data) {
		$scope.user = data;
	});

});

app.controller('MypageCompanyProfileController', function($scope, $http, ROOT) {
	$scope.Root = ROOT;
	$scope.top = {
		title: "My Page - Company",
		mobile: localStorage.mobile,
		backstretch: [
			// ROOT+'/assets/img/big/big-3.jpg',
			// ROOT+'/assets/img/big/big-5.jpg'
			ROOT+'/assets/img/big/test.jpg'
		],
		backstretch_xs: [ ROOT+'/assets/img/big/test_xs.jpg' ]
	};

	var bu = JSON.parse(localStorage.company);
	var uid = bu.user.id;

	$http.get('/company/find?id='+uid).success(function(data) {
		$scope.company = data;
	});

});

app.controller('MypageCompanyProfileEditController', function($scope, $http, ROOT) {
	$scope.Root = ROOT;
	$scope.top = {
		title: "My Page - Company",
		mobile: localStorage.mobile,
		backstretch: [
			// ROOT+'/assets/img/big/big-3.jpg',
			// ROOT+'/assets/img/big/big-5.jpg'
			ROOT+'/assets/img/big/test.jpg'
		],
		backstretch_xs: [ ROOT+'/assets/img/big/test_xs.jpg' ]
	};

	var bu = JSON.parse(localStorage.company);
	var uid = bu.user.id;

	$scope.company_companyname = bu.user.companyname;
	$scope.company_adminname = bu.user.adminname;
	$scope.company_email = bu.user.email;
	$scope.company_phone_number = bu.user.phone_number;
	$scope.company_location = bu.user.location;
	$scope.company_address = bu.user.address;
	$scope.company_description = bu.user.description;

	$http.get('/company/find?id='+uid).success(function(data) {
		$scope.company = data;
	});

});

app.controller('RepairCaseController', function($scope, $http, ROOT) {
	$scope.Root = ROOT;
	$scope.top = {
		title: "Repair Case",
		backstretch: [
			// ROOT+'/assets/img/big/big-3.jpg',
			// ROOT+'/assets/img/big/big-5.jpg'
			ROOT+'/assets/img/big/test.jpg'
		]
	};

	$scope.route = function() {
		window.location = '/mypage/user/sheet';
	}

});

app.controller('RepairCaseListController', function($scope, $http, ROOT, $routeParams) {
	$scope.Root = ROOT;
	$scope.top = {
		title: "Repair Case",
		backstretch: [
			// ROOT+'/assets/img/big/big-3.jpg',
			// ROOT+'/assets/img/big/big-5.jpg'
			ROOT+'/assets/img/big/test.jpg'
		]
	};

	$scope.route = function() {
		window.location = '/mypage/user/sheet';
	}

	$scope.trouble_type = $routeParams.trouble_type;

	$http.get('/repaircase/trouble_type/'+$routeParams.trouble_type).success(function(data) {
		for (var i = 0; i < data.length; i++) {
	        data[i].index = i;
	      }
	  $scope.items = data;
	})

});


app.controller('RepairCaseListDetailController', function($scope, $http, ROOT, $routeParams) {
	$scope.Root = ROOT;
	$scope.top = {
		title: "Repair Case",
		backstretch: [
			// ROOT+'/assets/img/big/big-3.jpg',
			// ROOT+'/assets/img/big/big-5.jpg'
			ROOT+'/assets/img/big/test.jpg'
		]
	};

	$scope.user = localStorage.user;
  $scope.company = localStorage.company;

	$scope.route = function(id) {
		window.location = '/mypage/user/sheet/'+id;
	}

	$http.get('/repaircase/trouble_type/detail/'+$routeParams.trouble_type+'/'+$routeParams.id).success(function(data) {
		$scope.the_item = data[0];
	});

});


app.controller('MobileHomeController', function($scope, $http, toastr, ROOT) {
	$scope.Root = ROOT;
	$scope.top = {
		mobilehome: true,
		//backstretch: [ ROOT+'/assets/img/big/big-1.jpg' ]
		backstretch_xs: [ ROOT+'/assets/img/big/test_xs.jpg' ]
	};

	if(!localStorage.mobilehome) {
		localStorage.mobilehome =  true;
		window.location = '/mobile/home';
	}

	if(localStorage.company) {
		localStorage.mobile =  true;
    localStorage.removeItem('mobilehome');
		window.location = '/mobile/mypage';
	};
	

	$scope.submitLoginForm = function (){
		// Set the loading state (i.e. show loading spinner)
    $scope.loginForm.loading = true;

  	// Submit request to Sails.
    $http.post('/login/company', {
      email: $scope.loginForm.email,
      password: $scope.loginForm.password
    })
    .then(function onSuccess (res){
    	localStorage.company = JSON.stringify(res.data);  // 불러올때: JSON.parse(localStorage).company
    	localStorage.mobile =  true;
    	localStorage.removeItem('mobilehome');

      // Refresh the page now that we've been logged in.
      window.location = '/mobile/mypage';
    })
    .catch(function onError(sailsResponse) {

      // Handle known error type(s).
      // Invalid username / password combination.
      if (sailsResponse.status === 400 || 404) {
        // $scope.loginForm.topLevelErrorMessage = 'Invalid email/password combination.';
        //
        toastr.error('Invalid email/password combination.', 'Error', {
          closeButton: true
        });
        return;
      }

        toastr.error('An unexpected error occurred, please try again.', 'Error', {
          closeButton: true
        });
        return;

    })
    .finally(function eitherWay(){
      $scope.loginForm.loading = false;
    });
  };
});



app.controller('MobileMyPageController', function($scope, $http, ROOT) {
	$scope.Root = ROOT;
	$scope.top = {
		title: "My Page - Company",
		mobile: true,
		backstretch_xs: [ ROOT+'/assets/img/big/test_xs.jpg' ]
	};

	if(!localStorage.company) {
		localStorage.mobilehome =  true;
		window.location = '/mobile/home';
	};


	var bu = JSON.parse(localStorage.company);
	var uid = bu.user.id;

	$http.get('/suggestion_sheets/company/'+ uid).success(function(data) {
	      for (var i = 0; i < data.length; i++) {
	        data[i].index = i;
	      }
	      $scope.items = data;
	      //console.log(data);
	 });
});



app.controller('MobileRegisterController', function($scope, $http, toastr, ROOT) {
	$scope.top = {
		register: true,
		mobile: true,
		//backstretch: [ ROOT+'/assets/img/big/big-1.jpg' ]
		backstretch_xs: [ ROOT+'/assets/img/big/test_xs.jpg' ]
	};
	// set-up loading state
  $scope.registerForm = {
    loading: false
  }

  $scope.submitRegisterForm = function(){

    // Set the loading state (i.e. show loading spinner)
    $scope.registerForm.loading = true;

  	$http.post('/register/company', {
  		email: $scope.registerForm.email,
  		password: $scope.registerForm.password,
  		companyname: $scope.registerForm.companyname,
  		location: $scope.registerForm.location,
  		address: $scope.registerForm.address,
  		adminname: $scope.registerForm.adminname,
  		phone_number: $scope.registerForm.phone_number,
  		description: $scope.registerForm.description
  	})
  	.then(function onSuccess(res){
  		localStorage.company = JSON.stringify(res.data);  // 불러올때: JSON.parse(localStorage).company
  		localStorage.mobile =  true;
    	localStorage.removeItem('mobilehome');

    	window.location = '/mobile/mypage';
    })
    .catch(function onError(sailsResponse){

	    // Handle known error type(s).
	    // If using sails-disk adpater -- Handle Duplicate Key
	    var emailAddressAlreadyInUse = sailsResponse.status === 409;

	    if (emailAddressAlreadyInUse) {
	      toastr.error('That email address has already been taken, please try again.', 'Error');
	      return;
	    }
	    else if (sailsResponse.status === 400 || 404 || 403) {
	    	toastr.error('Somthing wrong, try again.',sailsResponse.status);

				return;
	    }

    })
    .finally(function eitherWay(){
      $scope.registerForm.loading = false;
    })
  }
});