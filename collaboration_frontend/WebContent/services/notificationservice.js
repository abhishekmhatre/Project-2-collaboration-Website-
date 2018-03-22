/**
 * NotificationService
 */
app.factory('NotificationService',function($http){
	var notificationService={}
	var BASE_URL="http://localhost:8085/collaboration_middleware"
	notificationService.getAllNotification=function(){
		//response.data = Array of notification objects
		return $http.get(BASE_URL + "/getallnotification")
	}
	
	notificationService.getNotification=function(id){
		return $http.get(BASE_URL + "/getnotification/"+id)
	}
	notificationService.updateNotification=function(id){
		return $http.put(BASE_URL + "/updatenotification/"+id)
	}
	
	return notificationService;
})
