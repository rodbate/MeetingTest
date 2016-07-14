

define(['../meeting','jquery'],function(module,$){

    module.controller('WelcomeController', function($scope,$http){


        var dateFormat = function(date){
            var year = date.getFullYear();
            var month = date.getMonth() + 1;
            var day = date.getDate();
            return year + "-" + month + "-" + day;
        }

        /**  yyyy-MM-dd */
        $scope.currentDate = '';

        /**  yyyy-MM-dd HH:mm */
        $scope.currentDateTime = '';

        $scope.timestamp = '';

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
                console.log(dateFormat(ev.date));
                $scope.currentDate = dateFormat(ev.date);
                $scope.timestamp = ev.date.valueOf();
                $scope.getMeetingInfo(ev.date.valueOf());
            });

            $("[data-toggle='popover']").popover();

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


        $scope.meetingDetailInfo = {};

        $scope.getMeetingDetailInfoById = function(meetingId){

            $http.get('/api/meeting/' + meetingId).then(function(response){

                $scope.meetingDetailInfo = response.data;

            },function(response){

                console.log(response.status);

            });
        };

        $scope.createMeetingData = {
            meetingRoomId: 0,
            meetingRoomName: '',
            meetingName: '',
            startDate: '',
            endDate: ''
        };

        $scope.showDetailOrCreate = function(meetingId, roomId, roomName, time){

            if(meetingId === undefined || meetingId === 0){
                //创建会议
                $scope.createMeetingData.meetingRoomId = roomId;
                $scope.createMeetingData.meetingRoomName = roomName;
                $scope.currentDateTime = $scope.currentDate + " " + time;
                $scope.createMeetingData.startDate = $scope.currentDateTime;
                $scope.getValidEndDates(roomId, new Date($scope.currentDateTime).valueOf());
                $scope.showModal("#createMeetingModal");
            }else{
                //显示会议详情
                $scope.getMeetingDetailInfoById(meetingId);
                $scope.showModal("#showDetailInfoModal");
            }
        }

        $scope.validEndDates = [];

        $scope.getValidEndDates = function(roomId, timestamp){

            $http.get('/api/meeting/validEndDate/' + roomId + "/" + timestamp).then(function(response){

                $scope.validEndDates = response.data;

            },function(response){

                console.log(response.status);

            });
        }

        $scope.test = function(str){
            console.log(str);
        }

        $scope.cancel = function(id){
            $(id).modal('hide');
            $scope.createMeetingData.meetingName='';
        }

        $scope.showModal = function(id){
            $(id).modal('show');
        }
    });
});
