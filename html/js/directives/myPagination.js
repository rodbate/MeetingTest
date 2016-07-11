define(['../meeting','jquery'],function(module,$){

    module.factory('myPaginationService',function(){

            //get page data function
            var getPageData = function(pageObject,page){
                pageObject.currentPage = page;
                if(pageObject.totalPage==0||pageObject.totalPage==null){
                         pageObject.totalPage=1;
                }
                if (pageObject.currentPage > 1 && pageObject.currentPage < pageObject.totalPage) {
                     pageObject.pages = [
                         pageObject.currentPage - 1,
                         pageObject.currentPage,
                         pageObject.currentPage + 1
                     ];
                } else if (pageObject.currentPage == 1 && pageObject.totalPage == 1) {
                    pageObject.pages = [
                        1
                    ];
                } else if (pageObject.currentPage == 1 && pageObject.totalPage == 2) {
                    pageObject.pages = [
                        1,2
                    ];
                } else if (pageObject.currentPage == 1 && pageObject.totalPage > 2) {
                    pageObject.pages = [
                        pageObject.currentPage,
                        pageObject.currentPage + 1,
                        pageObject.currentPage + 2
                    ];
                } else if (pageObject.currentPage == pageObject.totalPage && pageObject.totalPage == 1) {
                    pageObject.pages = [
                        1
                    ];
                } else if (pageObject.currentPage == pageObject.totalPage && pageObject.totalPage == 2) {
                    pageObject.pages = [
                        1,2
                    ];
                } else if (pageObject.currentPage == pageObject.totalPage && pageObject.totalPage > 2) {
                    pageObject.pages = [
                        pageObject.currentPage - 2,
                        pageObject.currentPage - 1,
                        pageObject.currentPage
                    ];
                }
            };

            var service = {
                //click to the last page
                upPageClick: function(pageObject,page){
                    if(pageObject.currentPage == 1){
                        return;
                    };
                    pageObject.currentPage --;
                    getPageData(pageObject,page);
                },
                //click to the next page
                downPageClick: function(pageObject,page){
                    if(pageObject.currentPage >= pageObject.totalPage){
                        return;
                    };
                    pageObject.currentPage ++;
                    getPageData(pageObject,page);
                },
                //show the first page content
                showFirstPageContent: function(pageObject,page){
                    pageObject.currentPage = 1;
                    getPageData(pageObject,page);
                },
                //show the last page content
                showLastPageContent: function(pageObject,page){
                    pageObject.currentPage = pageObject.totalPage;
                    getPageData(pageObject,page);
                },
                //show the current page content
                showCurrentPageContent: function(pageObject,page){
                    pageObject.currentPage = page;
                    getPageData(pageObject,page);
                }
            };
            return service;
    });


    module.directive('myPagination',function(myPaginationService){
        return {
            restrict: 'A',
            replace: true,
            scope: {
                pageObject:'='
            },
            templateUrl: '../../views/myPagination.html',
            link: function(scope,element,attrs){
                scope.upPageClick = function(page){
                    myPaginationService.upPageClick(scope.pageObject,page);
                };
                scope.downPageClick = function(page){
                    myPaginationService.downPageClick(scope.pageObject,page);
                };
                scope.showFirstPage = function(page){
                    myPaginationService.showFirstPageContent(scope.pageObject,page);
                };
                scope.showLastPage = function(page){
                    myPaginationService.showLastPageContent(scope.pageObject,page);
                };
                scope.showCurrentPage = function(page){
                    myPaginationService.showCurrentPageContent(scope.pageObject,page);
                };
            }
        };
    });

});