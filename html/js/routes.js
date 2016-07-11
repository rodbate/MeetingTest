

define([], function () {
    return {
        defaultRoutePath: '/web/welcome',
        routes: {
            '/web/welcome': {
                templateUrl: '/views/welcome.html'
            },

            '/web/meeting': {
                templateUrl: '/view/meeting-list.html',
                dependencies: [
                    'controllers/MeetingController'
                ]
            }

        }
    };
});