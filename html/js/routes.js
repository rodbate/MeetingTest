



define(['./app'], function (app) {
    'use strict';
    return app.config(['$routeProvider', '$locationProvider', function ($routeProvider, $locationProvider) {
        $locationProvider.html5Mode(true);

        $routeProvider.when('/meeting', {
            templateUrl: 'views/meeting-list.html',
            controller: 'MeetingController'
        });

        $routeProvider.when('/welcome', {
            templateUrl: 'views/welcome.html'
        });

        $routeProvider.otherwise({
            redirectTo: '/'
        });
    }]);
});