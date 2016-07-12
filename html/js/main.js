

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
            exports: 'angular',
            deps: ['jquery']
        },
        'bootstrap': ['jquery'],
        'angularRoute': ['angular'],
        'angularResource': ['angular']
    }

});



require
(
    [
        'angular','meeting', './controllers/IndexController'
    ],
    function(angular)
    {
        angular.bootstrap(document, ['meeting']);
    }
);


