/**
 * UserController
 */
app.controller('UserController',function($scope,UserService,$rootScope,$location,$cookieStore){
	
	// Statement to fetch user details for update [select * from user_s180233 where email=?
	if($rootScope.loggedInUser!=undefined){
	UserService.getUserDetails().then(function(response){//only for loggedInUser - only if user is loggedin
		$scope.user=response.data
	},function(response){
		console.log(response.status)
		$scope.error=response.data
		$location.path('/login')
	})
	}
	
	$scope.register=function(){
		UserService.register($scope.user).then(
		 function(response){
			alert('Registered successfully.. please login..')
			$location.path('/login')
		 }
		,function(response){
			$scope.error=response.data  //ErrorClazz object
		})
	}
	
	$scope.login=function(){
		UserService.login($scope.user).then(function(response){
			$rootScope.loggedInUser=response.data
			$cookieStore.put('loggedInUser',response.data)
			$location.path('/home') //valid credentials
		},function(response){
			$scope.error=response.data //ErrorClazz
			$location.path('/login')
		})
	}
    
	$scope.update=function(){
		UserService.update($scope.user).then(function(response){
			alert("Details updated successfully.")
			$rootScope.loggedInUser=response.data//updated user object
			$cookieStore.put('loggedInUser',response.data)
			$location.path('/home')
		},function(response){
			console.log(response.status)
			if(response.status==401)
				$location.path('/login')
		})
	}
})



