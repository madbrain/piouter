
var app = angular.module('app', ['ngResource']);
app.constant('userId', 'thebignet');

app.controller('TestCtrl', function ($scope, $resource, $log, userId) {
    
	$scope.text = 'Test text';
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

});
