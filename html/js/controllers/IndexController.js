

define(['../meeting','jquery'],function(module,$){

    module.controller('IndexController', function($scope, $location){

        console.log($location.url());

        var url = $location.url();


        $scope.activeMenu = [true, false, false];

        $scope.active = function(index) {
            $scope.activeMenu[index] = true;
            if(index === 0) {
                $scope.activeMenu[1] = false;
                $scope.activeMenu[2] = false;
            }
            if(index === 1) {
                $scope.activeMenu[0] = false;
                $scope.activeMenu[2] = false;
            }
            if(index === 2) {
                $scope.activeMenu[0] = false;
                $scope.activeMenu[1] = false;
            }


        }

        if(url == '/web/welcome') {
            $scope.active(0);
        } else if (url == '/web/meeting') {
            $scope.active(1);
        }
    });
});
