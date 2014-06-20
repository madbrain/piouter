var app = angular.module('app', ['ngResource']);
app.constant('userId', 'madbrain');

app.controller('TestCtrl', function ($scope, $resource, $log, $http, userId) {
    
	$scope.text = 'Test text';
	$scope.user = {
		id: userId,
		following: [],
	};
	$scope.filteredUsers = [ ];
	$scope.newFolloweeName = '';

	var Piou = $resource('http://localhost:8080/piou/:userId', {userId:'@userId'});

    $scope.send = function () {
        var params = {userId:$scope.author,message:$scope.message,date:Date.now()};
        var monPiou = new Piou(params);
        monPiou.$save(function(ret, putResponseHeaders){
            if(ret.code==0){
                $scope.pious.push(params);
            } else {
                alert(ret.message);
            }
        });
    };

    $scope.pious = Piou.query({userId:userId});

    $scope.getFollowees = function () {
        $http.get('http://localhost:8080/user/' + userId)
			.then(function (response) {
            	$scope.user = response.data;
            });
    };

    $scope.$watch('newFolloweeName', function(newValue, oldValue) {
		if (newValue.length >= 2) {
			$http.get('http://localhost:8080/users/' + newValue)
				.then(function (response) {
            		$scope.filteredUsers = response.data;
            	});
		} else {
            $scope.filteredUsers = [];
		}
    });

    $scope.addFollowee = function (user) {
		$http.put('http://localhost:8080/user/' + userId + '/follow/' + user.id)
			.then(function (response) {
           		$scope.user = response.data;
				$scope.newFolloweeName = '';
				$scope.getPiouts();
           	});
    };

    $scope.removeFollowee = function (user) {
		$http.delete('http://localhost:8080/user/' + userId + '/follow/' + user.id)
			.then(function (response) {
           		$scope.user = response.data;
				$scope.getPiouts();
           	});
    };

});

