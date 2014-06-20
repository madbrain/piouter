var app = angular.module('app', ['ngResource']);

app.constant('apiRoot', 'http://localhost:8080');

app.controller('TestCtrl', function ($scope, $resource, $log, $http, apiRoot) {
    
	$scope.user = {
		id: null,
		following: [],
	};
	$scope.filteredUsers = [ ];
	$scope.newFolloweeName = '';
	$scope.pious = [];

	var Piou = $resource(apiRoot + '/piou/:userId', {userId:'@userId'});

    $scope.send = function () {
        var params = {userId:$scope.user.id,message:$scope.message,date:Date.now()};
        var monPiou = new Piou(params);
        monPiou.$save(function(ret, putResponseHeaders){
            if(ret.code==0){
                $scope.pious.push(params);
            } else {
                alert(ret.message);
            }
        });
    };

	function getPiouts() {
    	$scope.pious = Piou.query({userId: $scope.user.id});
	}

	$scope.doLogin = function() {
		$log.info("burp");
		$http.get(apiRoot + '/user/' + $scope.login)
			.then(function (response) {
				$scope.user = response.data;
				getPiouts();
           	});
	};

	$scope.doLogout = function() {
		$scope.user = {id: null, following: []};
		$scope.pious = [];
	};

    $scope.$watch('newFolloweeName', function(newValue, oldValue) {
		if (newValue.length >= 2) {
			$http.get(apiRoot + '/users/' + newValue)
				.then(function (response) {
            		$scope.filteredUsers = response.data;
            	});
		} else {
            $scope.filteredUsers = [];
		}
    });

    $scope.addFollowee = function (user) {
		$http.put(apiRoot + '/user/' + $scope.user.id + '/follow/' + user.id)
			.then(function (response) {
           		$scope.user = response.data;
				$scope.newFolloweeName = '';
				getPiouts();
           	});
    };

    $scope.removeFollowee = function (user) {
		$http.delete(apiRoot + '/user/' + $scope.user.id + '/follow/' + user.id)
			.then(function (response) {
           		$scope.user = response.data;
				getPiouts();
           	});
    };

});

