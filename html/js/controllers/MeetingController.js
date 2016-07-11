

define(['../meeting','jquery'],function(module,$){

    module.controller('MeetingController', function ($scope, $http) {

        $scope.meetings = [];

        $scope.getMeetingList = function(){

            $http.get('/api/meeting').success(function(data, status, headers){

                $scope.meetings = data;

            }).error(function(err){

                console.log(err);
            });
        }

        $scope.getMeetingList();

    });

});