

define([], function () {
    return {
        defaultRoutePath: '/web/welcome',
        routes: {
            '/web/welcome': {
                templateUrl: 'views/welcome.html',
                dependencies: [
                    'controllers/WelcomeController'
                ]
            },

            '/web/meeting': {
                templateUrl: 'views/meeting-list.html',
                dependencies: [
                    'controllers/MeetingController'
                ]
            }

        }
    };
});