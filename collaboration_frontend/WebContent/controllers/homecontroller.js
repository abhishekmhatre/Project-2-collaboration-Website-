/**
 * HomeController 
 */

app.controller('HomeController',function($scope,$rootScope,$location,NotificationService){
	NotificationService.getAllNotification().then(function(response){
		$rootScope.notifications=response.data;
		$rootScope.notificationsCount=$rootScope.notifications.length;
	},function(response){
		$rootScope.error=response.data//ErrorClazz
		if(response.status==401)//Not Authenticated
			$location.path('/login')
		
	})
	
	
})