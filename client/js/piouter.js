
var app = angular.module('app', []);

app.constant('userId', 'madbrain');

app.controller('TestCtrl', function ($scope, $http, $log, userId) {
    
	$scope.text = 'Test text';
	$scope.piouts = [];
	$scope.user = {
		id: userId,
		following: [],
	};
	$scope.filteredUsers = [ ];
	$scope.newFolloweeName = '';

    $scope.send = function () {
		var data = {
			'message': $scope.text,
		};
		$http({
			method: 'POST',
			url: 'http://localhost:8080/piou/' + userId,
			data: "message=" + $scope.text,
			headers: {'Content-Type': 'application/x-www-form-urlencoded'}
		}).then(function (response) {
			$log.info(response.data);
			$scope.getPiouts();
		});
    };

    $scope.getPiouts = function () {
        $http.get('http://localhost:8080/piou/' + userId)
			.then(function (response) {
            	$scope.piouts = response.data;
            });
    };

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

	$scope.getPiouts();
	$scope.getFollowees();
});

