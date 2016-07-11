

require.config({

    paths: {
        'jquery': 'lib/jquery.min',
        'angular': 'lib/angular.min',
        'angularRoute': 'lib/angular-route.min',
        'angularResource': 'lib/angular-resource.min',
        'domReady': 'lib/domReady',
        'bootstrap': 'lib/bootstrap.min'
    },


    shim: {
        'jquery' : { exports: '$'},
        'angular': {
            deps: ['jquery'],
            exports: 'angular'
        },
        'bootstrap': ['jquery'],
        'angularRoute': ['angular'],
        'angularResource': ['angular']
    }

});


/*require(['jquery', 'angular', 'angular-route', 'angular-resource'], function($, angular){

    $(document).ready(function(angular){

        angular.bootstrap(document, ['app']);
    });

});*/


require
(
    [
        'angular','briair', './controllers/IndexController'
    ],
    function(angular)
    {
        angular.bootstrap(document, ['meeting']);
    }
);


