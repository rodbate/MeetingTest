

define(['../meeting','jquery','../directives/myPagination'],function(module,$){

    module.controller('MeetingController', function ($scope, $http, myPaginationService) {

        $scope.meetingPageObject={
              currentPageList: [], //当前页面显示的数据列表
              currentPage: 1, //当前页，初始化为1
              totalPage: 0, //总页数
              pageSize: 1, //页面大小
              pages:[] //前台页面按钮显示内容
        };


        $scope.meetings = [];

        $scope.getMeetingList = function(){

            $scope.meetingSearch = {
                params:{
                    pageSize:$scope.meetingPageObject.pageSize,
                    pageNo:$scope.meetingPageObject.currentPage,
                    order:0
                }
            };

            $http.get('/api/meeting', $scope.meetingSearch).success(function(data, status, headers){

                $scope.meetingPageObject.totalPage = data.totalPage;
                $scope.meetingPageObject.currentPageList=data.content;
                $scope.meetings = data.content;
            }).error(function(err){

                console.log(err);
            });
        }

        $scope.getMeetingList();


        $scope.init = function(){
            $scope.getMeetingList();
            $scope.$watch('meetingPageObject.totalPage',function(){myPaginationService.showFirstPageContent($scope.meetingPageObject,1);});
        };
        $scope.init();
        $scope.$watch('meetingPageObject.currentPage',function(){$scope.getMeetingList();});

    });

});