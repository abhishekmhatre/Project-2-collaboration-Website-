/**
 * FriendCtrl
 */
app.controller('FriendCtrl',function($scope,$location,FriendService,$rootScope){
	function getAllSuggestedUsers(){
	FriendService.getSuggestedUsers().then(
			function(response){
				$scope.suggestedUsers=response.data
				
			},function(response){
				$rootScope.error=response.data
				if(response.status==401)
				$location.path('/login')
			})
	}
	function getPendingRequests(){
		FriendService.getPendingRequests().then(function(response){
			$scope.friendRequests=response.data;
		},
		function(response){
			$rootScope.error=response.data
			if(response.status==401)
			$location.path('/login')
		})
	}
	$scope.addFriend=function(user){
		FriendService.addFriend(user).then(function(response){
			getAllSuggestedUsers()
		},function(response){
			$rootScope.error=response.data
				if(response.status==401)
				$location.path('/login')
		})
	}
	
	//onclick Accept , status='A' or Delete,  status='D'
		$scope.updateFriendRequest=function(friendRequest,status){
		friendRequest.status=status //status is updated in the JSON object [A/D]
		//friendRequest object status is updated to either A or D
		FriendService.updateFriendRequest(friendRequest).then(function(response){
			getPendingRequests()
		},function(response){
			$rootScope.error=response.data
				if(response.status==401)
				$location.path('/login')
		})
		
	}
	
	FriendService.listOfFriends().then(function(response){
		$scope.friends=response.data//List of User objects
	},
	function(response){
		$rootScope.error=response.data
		if(response.status==401)
		$location.path('/login')
	})
	
	
	getAllSuggestedUsers();
	getPendingRequests();
})