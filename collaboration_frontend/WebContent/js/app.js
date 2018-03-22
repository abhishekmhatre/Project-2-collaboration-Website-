/**
 * Angular Module
 */
var app=angular.module("app",['ngRoute','ngCookies'])
app.config(function($routeProvider){
	$routeProvider
	.when('/register',{
		templateUrl:'views/registration.html',
		controller:'UserController'
	})
	.when('/login',{
		templateUrl:'views/login.html',
		controller:'UserController'
	})
	.when('/editprofile',{
		templateUrl:'views/updateform.html',
		controller:'UserController'
	})
	.when('/updateprofile',{
		templateUrl:'views/updateform2.html',
		controller:'UserController'
	})
	
	.when('/home',{
		templateUrl:'views/updateform.html',
		controller:'UserController'										
	})
	
	.when('/addjob',{ //view to controller
		templateUrl:'views/jobform.html',
		controller:'JobController' //$scope.job
	})
	.when('/getalljobs',{ //controller to view
		templateUrl:'views/joblist.html', //ng-repeat
		controller:'JobController'    //$scope.job
	})
	.when('/getjob/:id',{  //controller to view
		templateUrl:'views/jobdetails.html',
		controller:'JobController'
	})
	.when('/addblog',{  //view to controller
		templateUrl:'views/blogform.html',
		controller:'BlogController'
	})
	.when('/getblogs',{  //controller to view
		templateUrl:'views/blogslist.html',
		controller:'BlogController'
	})
	.when('/getblog/:id',{ //for approved blogs
		templateUrl:'views/blogpostdetails.html',
		controller:'BlogPostDetailController'
	})
	
	.when('/getapprovalform/:id',{ //blogs waiting for approval
		templateUrl:'views/approvalform.html',
		controller:'BlogPostDetailController'
	})
	
	.when('/getnotification/:id',{  
		templateUrl:'views/notificationdetails.html',
		controller:'NotificationController'
	})
	
	.when('/profilepic',{  
		templateUrl:'views/uploadprofilepic.html',
	})
	
	.when('/suggestedusers',{  
		templateUrl:'views/suggestedusers.html',
		controller:'FriendCtrl'
	})
	
	.when('/pendingrequests',{  
		templateUrl:'views/pendingrequests.html',
		controller:'FriendCtrl'
	})
	
	.when('/friends',{  
		templateUrl:'views/friendslist.html',
		controller:'FriendCtrl'
	})
	 
	.when('/chat',{
		templateUrl:'views/chat.html',
		controller:'ChatCtrl'
	})
	
	.otherwise({
		templateUrl:'views/login.html',
		controller:'UserController'
	})
})
app.run(function($rootScope,$cookieStore,UserService,$location){
	if($rootScope.loggedInUser==undefined)
		$rootScope.loggedInUser=$cookieStore.get('loggedInUser')
		
		$rootScope.logout=function(){
		UserService.logout().then(function(response){
			    $rootScope.successMessage="LoggedOut Successfully.."
			    delete $rootScope.loggedInUser
			    $cookieStore.remove("loggedInUser")
				$location.path('/login')
		},function(response){
			$rootScope.errorMessage="Please login.."
				$location.path('/login')
		})
	}
	
})
