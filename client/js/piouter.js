
var app = angular.module('app', []);

//app.config(['$httpProvider', function($httpProvider) {
//        $httpProvider.defaults.useXDomain = true;
//        delete $httpProvider.defaults.headers.common['X-Requested-With'];
//    }]);

app.constant('userId', 'thebignet');

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
			url: 'http://localhost:8080/piou/' + 'devoxxFr',
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

    $scope.fetchMatchingUsers = function () {
		if ($scope.newFolloweeName.length >= 2) {
			$http.get('http://localhost:8080/users/' + $scope.newFolloweeName)
				.then(function (response) {
            		$scope.filteredUsers = response.data;
            	});
		} else {
            $scope.filteredUsers = [];
		}
    };

	$scope.getPiouts();
	$scope.getFollowees();
});

