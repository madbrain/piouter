
var app = angular.module('app', ['ngResource']);
//app.config(['$httpProvider', function($httpProvider) {
//        $httpProvider.defaults.useXDomain = true;
//        delete $httpProvider.defaults.headers.common['X-Requested-With'];
//    }]);

app.constant('userId', 'thebignet');
$http.defaults.headers.post["Content-Type"] = "application/x-www-form-urlencoded";


app.controller('TestCtrl', function ($scope, $resource, $log, userId) {
    
	$scope.text = 'Test text';
	var Piou = $resource('http://localhost:8080/piou/:userId', {userId:'@id'});

    $scope.send = function () {
        $log.info("avant");
        Piou.save({id:'devoxxFR',message:$scope.text});
        $log.info("après");
    };

    $scope.piouts = Piou.query({userId:userId});

});
