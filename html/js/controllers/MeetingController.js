


define(['./module'], function (controllers) {
    'use strict';
    controllers.controller('MeetingController', ['$scope', '$http', function ($scope, $http) {

        $scope.meetings = [];

        $scope.getMeetingList = function(){

            $http.get('/api/meeting').success(function(data, status, headers){

                $scope.meetings = data;

            }).error(function(err){

                console.log(err);
            });
        }

        $scope.getMeetingList();

    }]);
});