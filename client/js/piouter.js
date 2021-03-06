
var app = angular.module('app', ['ngResource','angular-md5','ui.bootstrap','angularMoment']);
app.constant('apiRoot', 'http://localhost:8080');
app.constant('userId', 'thebignet@gmail.com');

app.controller('TestCtrl', function ($scope, $resource, $log, apiRoot, userId) {
    
	$scope.message = '';

	var User = $resource(apiRoot + '/user/:userId/:action/:actionId', {userId:'@userId',action:'@action',actionId:'@actionId'},{
        matching: {method:'GET',params:{action:'filter'},isArray:true},
        follow: {method:'PUT',params:{action:'follow'}},
        unfollow: {method:'DELETE',params:{action:'follow'}}
    });

	var Piou = $resource(apiRoot + '/piou/:userId', {userId:'@userId'});

	var Follower = $resource(apiRoot + '/follower/:userId', {userId:'@userId'});

    $scope.selectUser = function(id){
        $scope.user = User.get({userId:id});
    };

    $scope.selectUser(userId);

    $scope.$watch('user',function(user){
        $scope.pious = Piou.query({userId:user.id});
        $scope.followers = Follower.query({userId:user.id});
    },true);

    $scope.send = function () {
        var params = {userId:$scope.user.id,message:$scope.message,date:Date.now()};
        var monPiou = new Piou(params);
        monPiou.$save(function(ret, putResponseHeaders){
            if(ret.code==0){
                $scope.pious.push(params);
                $scope.message='';
            } else {
                alert(ret.message);
            }
        });
    };

    $scope.getUsersMatching = function(val){
        return User.matching({userId:$scope.user.id,actionId:val}).$promise.then(function(res){
            return res;
        });
    };

    $scope.follow = function(userToFollow){
        User.follow({userId:$scope.user.id,actionId:userToFollow.id},function(){
            $scope.user.following.push(userToFollow);
            $scope.userToFollow='';
        },function(res){
            alert(res.statusText);
        });
    };

    $scope.unfollow = function(userToUnfollow){
        User.unfollow({userId:$scope.user.id,actionId:userToUnfollow.id},function(){
            var newFollowers = _.without($scope.user.following,userToUnfollow);
            $scope.user.following=angular.copy(newFollowers);
        });
    };

    $scope.changeUser = function(user){
        $scope.selectUser(user.id);
        $scope.newUser='';
    };

    $scope.createUser = function(){
        $scope.selectUser($scope.newUser);
        $scope.newUser='';
    };

});

