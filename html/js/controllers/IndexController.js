

define(['../meeting','jquery'],function(module,$){

    module.controller('IndexController', function(){

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
    });
});
