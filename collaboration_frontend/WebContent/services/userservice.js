/**
 *UserService 
 */

app.factory('UserService',function($http){
	var userService={}
	
	var BASE_URL = "http://localhost:8085/collaboration_middleware"
	
	userService.register=function(user){
	 return $http.post(BASE_URL + "/register",user) //insert
	}
	
	userService.login=function(user){ //email n password
		 return $http.post(BASE_URL + "/login",user) //select 
	}
	
	userService.logout=function(){
		 return $http.put(BASE_URL + "/logout")
	}
	
	userService.getUserDetails=function(){
		return $http.get(BASE_URL + "/getuser")
	}
	
	userService.update=function(user){
		return $http.put(BASE_URL + "/update",user)
	}
	
	return userService;
})
