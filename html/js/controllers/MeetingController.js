

define(['../meeting','jquery','../directives/myPagination'],function(module,$){

    module.controller('MeetingController', function ($scope, $http, myPaginationService) {

        $scope.meetingPageObject={
              currentPageList: [], //当前页面显示的数据列表
              currentPage: 1, //当前页，初始化为1
              totalPage: 0, //总页数
              pageSize: 3, //页面大小
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

    });

});