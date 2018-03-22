/**
 * ChatCtrl
 */

app.controller('ChatCtrl',  function($rootScope ,$scope, ChatService) {
   
    $scope.chats = [];
    $scope.stompClient = ChatService.stompClient;
    $scope.users=[]
    $scope.$on('sockConnected', function(event, frame) {
    	
    	alert('sockconnected')
    	$scope.userName=$rootScope.loggedInUser.email;
        $scope.stompClient.subscribe("/topic/join", function(message) {
        	
            user = JSON.parse(message.body);
            console.log(user)
         
            if(user != $scope.userName && $.inArray(user, $scope.users) == -1) {
            	alert(user+" joined chat..");
                $scope.addUser(user);
                $scope.latestUser = user;
                $scope.$apply();
                $('#joinedChat').fadeIn(500).delay(2000).fadeOut(500);
            }
            
        });
    	
  
        $scope.stompClient.subscribe('/app/join/'+$scope.userName, function(message) {
        
            $scope.users = JSON.parse(message.body);
        	
            $scope.$apply();
        });
        
    });

    $scope.sendMessage = function(chat) {
        chat.from = $scope.userName;
      console.log("sending message...."+chat.to);
        $scope.stompClient.send("/app/chat", {}, JSON.stringify(chat));
        $rootScope.$broadcast('sendingChat', chat);
        $scope.chat.message = '';

    };

    $scope.capitalize = function(str) {
        return str.charAt(0).toUpperCase() + str.slice(1);
    };
 
    $scope.addUser = function(user) {
        $scope.users.push(user);
        $scope.$apply();
    };
 
    
    
    
    
    
    
    $scope.$on('sockConnected', function(event, frame) {
        $scope.userName=$rootScope.loggedInUser.email;
  
        $scope.user=$rootScope.loggedInUser.firstname;
       
        $scope.stompClient.subscribe( "/queue/chats/"+$scope.userName, function(message) {
        	console.log("In chat with "+$scope.userName);
        	
            $scope.processIncomingMessage(message, false);
        });
        
        
        $scope.stompClient.subscribe("/queue/chats", function(message) {
        	
            $scope.processIncomingMessage(message, true);
        });
        
        
    });

    $scope.$on('sendingChat', function(event, sentChat) {
    	console.log("chat sending....");
        chat = angular.copy(sentChat);
        chat.from = 'Me';
        chat.direction = 'outgoing';
        $scope.addChat(chat);
    });

    $scope.processIncomingMessage = function(message, isBroadcast) {
    	console.log("recieving msg");
        message = JSON.parse(message.body);
        message.direction = 'incoming';
	message.broadcast=isBroadcast;
	console.log("is Broadcast: "+message.broadcast);
        if(message.from != $scope.userName) {
        	$scope.addChat(message);
            $scope.$apply(); // since inside subscribe closure
        }
    };

 
    $scope.addChat = function(chat) {
        $scope.chats.push(chat);
    };
 
 
});