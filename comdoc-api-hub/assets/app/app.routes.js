app.config(function($routeProvider, $locationProvider){
    $routeProvider.when('/',{
        templateUrl: "app/views/misc/home.tpl.html",
        controller: "HomeController"
    })
    .when('/about',{
        templateUrl: "app/views/misc/about.tpl.html",
        controller: "AboutController"
    })
	.when('/404',{
        templateUrl: "app/views/features/404.tpl.html",
        controller: "404Controller"
    })
    .when('/500',{
        templateUrl: "app/views/features/500.tpl.html",
        controller: "500Controller"
    })
    .when('/contact',{
        templateUrl: "app/views/misc/contact.tpl.html",
        controller: "ContactController"
    })
    .when('/login',{
        templateUrl: "app/views/misc/login.tpl.html",
        controller: "LoginController"
    })
    .when('/register',{
        templateUrl: "app/views/misc/register/register.tpl.html",
        controller: "RegisterController"
    })
    .when('/register/user',{
        templateUrl: "app/views/misc/register/register-user.tpl.html",
        controller: "RegisterController"
    })
    .when('/register/company',{
        templateUrl: "app/views/misc/register/register-company.tpl.html",
        controller: "RegisterController"
    })
    .when('/logout',{
        redirectTo: '/',
        controller: "LogoutController"
    })
    .when('/mypage/user',{
        templateUrl: "app/views/misc/mypage/user/mypage-user.tpl.html",
        controller: "MypageUserController"
    })
    .when('/mypage/company',{
        templateUrl: "app/views/misc/mypage/company/mypage-company.tpl.html",
        controller: "MypageCompanyController"
    })
    .when('/mypage/user/sheet',{
        templateUrl: "app/views/misc/mypage/user/mypage-user-sheet.tpl.html",
        controller: "MypageUserSheetController"
    })
    .when('/mypage/user/sheet/:id',{
        templateUrl: "app/views/misc/mypage/user/mypage-user-sheet.tpl.html",
        controller: "MypageUserSheetController"
    })
    .when('/mypage/company/request_sheet',{
        templateUrl: "app/views/misc/mypage/company/mypage-company-requestSheet.tpl.html",
        controller: "MypageCompanyRequestSheetController"
    })
    .when('/mypage/user/sheet/detail/:id',{
        templateUrl: "app/views/misc/mypage/user/mypage-user-sheet-detail.tpl.html",
        controller: "MypageUserSheetDetailController"
    })
    .when('/mypage/company/sheet/detail/:id',{
        templateUrl: "app/views/misc/mypage/company/mypage-company-sheet-detail.tpl.html",
        controller: "MypageCompanySheetDetailController"
    })
    .when('/mypage/company/request_sheet/detail/:id',{
        templateUrl: "app/views/misc/mypage/company/mypage-company-requestSheet-detail.tpl.html",
        controller: "MypageCompanyRequestSheetDetailController"
    })
    .when('/mypage/company/sheet/:id',{
        templateUrl: "app/views/misc/mypage/company/mypage-company-sheet.tpl.html",
        controller: "MypageCompanySheetController"
    })
    .when('/mypage/user/adopted/:sheet_id/:suggestion_sheet_id', {
        templateUrl: "app/views/misc/mypage/user/mypage-user-adopted.tpl.html",
        controller: "MypageUserAdoptedController"
    })
    .when('/mypage/user/sheet/detail/after_adopted/:id',{
        templateUrl: "app/views/misc/mypage/user/mypage-user-sheet-detail-afterAdopted.tpl.html",
        controller: "MypageUserSheetDetailAfterAdoptedController"
    })
    .when('/sheets/repairCompleted/:id',{
        templateUrl: "app/views/misc/mypage/user/mypage-user-repairCompleted.tpl.html",
        controller: "MypageUserRepairCompletedController"
    })
    .when('/mypage/user/profile',{
        templateUrl: "app/views/misc/mypage/user/mypage-user-profile.tpl.html",
        controller: "MypageUserProfileController"
    })
    .when('/mypage/user/profile/edit',{
        templateUrl: "app/views/misc/mypage/user/mypage-user-profile-edit.tpl.html",
        controller: "MypageUserProfileEditController"
    })
    .when('/mypage/company/profile',{
        templateUrl: "app/views/misc/mypage/company/mypage-company-profile.tpl.html",
        controller: "MypageCompanyProfileController"
    })
    .when('/mypage/company/profile/edit',{
        templateUrl: "app/views/misc/mypage/company/mypage-company-profile-edit.tpl.html",
        controller: "MypageCompanyProfileEditController"
    })
    .when('/repaircase',{
        templateUrl: "app/views/misc/repairCase/repairCase.tpl.html",
        controller: "RepairCaseController"
    })
    .when('/repaircase/list/:trouble_type',{
        templateUrl: "app/views/misc/repairCase/repairCase-list.tpl.html",
        controller: "RepairCaseListController"
    }).
    when('/repaircase/list/detail/:trouble_type/:id',{
        templateUrl: "app/views/misc/repairCase/repairCase-list-detail.tpl.html",
        controller: "RepairCaseListDetailController"
    })
    .when('/mobile/home',{
        templateUrl: "app/views/misc/home_mobile.tpl.html",
        controller: "MobileHomeController"
    })
    .when('/mobile/mypage',{
        templateUrl: "app/views/misc/mypage/company/mypage-company.tpl.html",
        controller: "MobileMyPageController"
    })
    .when('/mobile/register',{
        templateUrl: "app/views/misc/register/register_mobile.tpl.html",
        controller: "MobileRegisterController"
    })
    .otherwise({redirectTo:'/'});

    $locationProvider.html5Mode(true);
});
