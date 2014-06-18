
var app = angular.module('app', []);

//app.config(['$httpProvider', function($httpProvider) {
//        $httpProvider.defaults.useXDomain = true;
//        delete $httpProvider.defaults.headers.common['X-Requested-With'];
//    }]);

app.constant('userId', 'thebignet');

app.controller('TestCtrl', function ($scope, $http, $log, userId) {
    
	$scope.text = 'Test text';
	$scope.piouts = [];

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
			$scope.list();
		});
    };

    $scope.list = function () {
        $http.get('http://localhost:8080/piou/' + userId)
			.then(function (response) {
            	$scope.piouts = response.data;
            });
    };

	$scope.list();
});
