

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

        $scope.participants = [];

        $scope.getParticipants = function(){
            $http.get('/api/employee').then(function(response){

                $scope.participants = response.data;
                $scope.initSelectize();
            },function(response){
                //console.log(response.data);
            });
        }

        $scope.initSelectize = function(){

            $('#select-participant').selectize({
                maxItems: null,
                valueField: 'id',
                labelField: 'name',
                searchField: 'name',
                options: $scope.participants,
                create: false,
                onChange: function(values){
                    console.log(values);
                    $scope.createMeetingData.participants = values;
                }
            });
        }

        //$scope.getParticipants();

        $scope.initDatetimePicker();



        $scope.meetingInfo = [];

        $scope.getMeetingInfo = function(timestamp){

                $http.get('/api/meetingInfo/' + timestamp).then(function(response){

                    $scope.meetingInfo = response.data;

                },function(response){
                    //console.log(response.data);
                });
        }


        $scope.meetingDetailInfo = {};

        $scope.getMeetingDetailInfoById = function(meetingId){

            $http.get('/api/meeting/' + meetingId).then(function(response){

                $scope.meetingDetailInfo = response.data;

            },function(response){

                //console.log(response.status);

            });
        };

        $scope.createMeetingData = {
            meetingRoomId: 0,
            meetingRoomName: '',
            meetingName: '',
            startDate: '',
            endDate: '',
            hostId:'',
            participants: []
        };

        $scope.createMeeting = function(flag){

            $scope.meetingNameNullHide = true;
            $scope.endTimeNullHide = true;
            $scope.hostNameNullHide = true;
            $scope.participantNullHide = true;

            if(!flag) return;

            $http.post('/api/meeting', $scope.createMeetingData).then(function(response){

                $("#createMeetingModal").modal('hide');

                console.log(response.data);
                $scope.getMeetingInfo($scope.timestamp);

                $scope.createMeetingData = {};
                $scope.participants = [];

            },function(response){

            });
        }

        $scope.showDetailOrCreate = function(meetingId, roomId, roomName, time){

            if(meetingId === undefined || meetingId === 0){
                //创建会议
                $scope.createMeetingData.meetingRoomId = roomId;
                $scope.createMeetingData.meetingRoomName = roomName;
                $scope.currentDateTime = $scope.currentDate + " " + time;
                $scope.createMeetingData.startDate = $scope.currentDateTime;
                $scope.getValidEndDates(roomId, new Date($scope.currentDateTime).valueOf());
                $scope.getParticipants();

                console.log($scope.participants)
                $scope.meetingNameNullHide = false;
                $scope.endTimeNullHide = false;
                $scope.hostNameNullHide = false;
                $scope.participantNullHide = false;
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

                //console.log(response.status);

            });
        }

        $scope.validHosts = [];

        $scope.getValidHosts = function(){
            console.log(new Date($scope.createMeetingData.startDate).valueOf());
            $scope.hostParams = {
                params: {
                    startTime: new Date($scope.createMeetingData.startDate).valueOf(),
                    endTime: new Date($scope.createMeetingData.endDate).valueOf()
                }
            };

            $http.get('/api/host/valid', $scope.hostParams).then(function(response){

                $scope.validHosts = response.data;

            },function(response){
                //console.log(response.data)
            });
        }

        $scope.test = function(str){
            console.log(str);
        }

        $scope.cancel = function(id){
            $(id).modal('hide');
            $scope.createMeetingData={};
            $scope.participants = [];
        }

        $scope.showModal = function(id){
            $(id).modal('show');
        }

        $scope.myDisplay = null;
    });
});
