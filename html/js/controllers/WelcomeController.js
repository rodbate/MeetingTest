

define(['../meeting','jquery'],function(module,$){

    module.controller('WelcomeController', function($scope,$http){

        $scope.initDatetimePicker = function(){
            $(".form_date").datetimepicker({
                language: 'zh-CN',
                format: "yyyy-mm-dd",
                autoclose: true,
                todayBtn: true,
                //startDate: "today",
                //minuteStep: 5,
                todayHighlight: 1,
                minView: 2,
                forceParse: 0
            })
            .on('changeDate', function(ev){
                console.log(ev.date.valueOf());
                $scope.getMeetingInfo(ev.date.valueOf());
            });

        }

        $scope.initDatetimePicker();

        $scope.meetingInfo = [];

        $scope.getMeetingInfo = function(timestamp){

                $http.get('/api/meetingInfo/' + timestamp).then(function(response){

                    $scope.meetingInfo = response.data;

                },function(response){
                    console.log(response.data);
                });
        }

        $scope.test = function(str){
            console.log(str);
        }
    });
});
