
var app = angular.module('app', ['ngResource','angular-md5']);
app.constant('userId', 'thebignet@gmail.com');

app.controller('TestCtrl', function ($scope, $resource, $log, userId) {
    
	$scope.message = '';

	var Piou = $resource('http://localhost:8080/piou/:userId', {userId:'@userId'});
    $scope.pious = Piou.query({userId:userId});

	var User = $resource('http://localhost:8080/user/:userId', {userId:'@userId'});
    $scope.user = User.get({userId:userId});

	var Follower = $resource('http://localhost:8080/follower/:userId', {userId:'@userId'});
    $scope.followers = Follower.query({userId:userId});

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

});

