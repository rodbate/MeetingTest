

define(['../meeting','jquery','../directives/myPagination'],function(module,$){

    module.controller('MeetingController', function ($scope, $http, myPaginationService) {

        $scope.meetingPageObject={
              currentPageList: [], //当前页面显示的数据列表
              currentPage: 1, //当前页，初始化为1
              totalPage: 0, //总页数
              pageSize: 5, //页面大小
              pages:[] //前台页面按钮显示内容
        };

        $scope.selectablePageSize = [2, 5, 10 ,15];

        $scope.meetings = [];

        $scope.fuzzySearch = {
            meetingName: '',
            hostName: '',
            order: 0,
            orderBy: ''
        };

        $scope.sortByTHead = function(orderBy){
            if($scope.fuzzySearch.order == 0) {
                $scope.fuzzySearch.order = 1;
            }else if($scope.fuzzySearch.order == 1) {
                $scope.fuzzySearch.order = 0;
            }

           $scope.fuzzySearch.orderBy = orderBy;
           $scope.getMeetingList();
        }

        $scope.getMeetingList = function(){

            $scope.meetingSearch = {
                params:{
                    meetingName: $scope.fuzzySearch.meetingName,
                    hostName: $scope.fuzzySearch.hostName,
                    pageSize:$scope.meetingPageObject.pageSize,
                    pageNo:$scope.meetingPageObject.currentPage,
                    order: $scope.fuzzySearch.order,
                    orderBy: $scope.fuzzySearch.orderBy
                }
            };

            $http.get('/api/meeting', $scope.meetingSearch).then(function(response){

                $scope.meetingPageObject.totalPage = response.data.totalPage;
                $scope.meetingPageObject.currentPageList=response.data.content;
                $scope.meetings = response.data.content;
            }, function(response){
                //console.log(response.data);
            });
        }

        $scope.getMeetingList();


        $scope.init = function(){
            $scope.getMeetingList();
            $scope.$watch('meetingPageObject.totalPage',function(){myPaginationService.showFirstPageContent($scope.meetingPageObject,1);});
        };
        $scope.init();
        $scope.$watch('meetingPageObject.currentPage',function(){$scope.getMeetingList();});


        $scope.deleteMeeting = function(id){

            $http.delete('/api/meeting/' + id).then(function(response){

                $('#deleteMeetingModal').modal('hide');
                $scope.getMeetingList();

            },function(response){

            });

        }


        $scope.saveSomeMeetingId = function(meetingId) {
            $scope.meetingId = meetingId;
        }

        $scope.cancel = function(id){
            $(id).modal('hide');
        }

    });

});