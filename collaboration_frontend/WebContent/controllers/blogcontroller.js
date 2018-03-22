/**
 * BlogController
 */
app.controller('BlogController',function($scope,$location,BlogService,$rootScope,$sce){

	$scope.addBlog=function(blog){
		
		BlogService.addBlog(blog).then(function(response){
			alert('Blog is added successfully and waiting for approval from ADMIN')
			$location.path('/home')
		},function(response){
			$rootScope.error=response.data//ErrorClazz
			if(response.status==401)//Not Authenticated
				$location.path('/login')					
		})
	}
	//List of blogs waiting for approval
	if($rootScope.loggedInUser.role=='ADMIN'){
	BlogService.getBlogsWaitingForApproval().then(
			function(response){
				$scope.blogsWaitingForApproval=response.data //select * from blogpost where approved=false
				alert('Blogs waiting for approval size ' + $scope.blogsWaitingForApproval.length)
			},
			function(response){
				$rootScope.error=response.data
				if(response.status==401)
					$location.path('/login')
			})
	}
	//List of blogs approved
	BlogService.getApprovedBlogs().then(function(response){
		$scope.blogsApproved=response.data //select * from blogpost where approved=true
		alert('Blogs approved size ' + $scope.blogsApproved.length)
	},function(response){
		$rootScope.error=response.data
		if(response.status==401)
			$location.path('/login')
	})
	
})