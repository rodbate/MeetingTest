

require.config({

    paths: {
        'jquery': 'http://apps.bdimg.com/libs/jquery/2.1.4/jquery.min',
        'angular': 'http://apps.bdimg.com/libs/angular.js/1.4.6/angular.min',
        'angularRoute': 'http://apps.bdimg.com/libs/angular.js/1.4.6/angular-route.min',
        'angularResource': 'http://apps.bdimg.com/libs/angular.js/1.4.6/angular-resource.min',
        'domReady': 'lib/domReady',
        'bootstrap': 'lib/bootstrap.min'
    },


    shim: {
        //'jquery' : { exports: '$'},
        'angular': {
            exports: 'angular'
            //deps: ['jquery']
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
        'angular','meeting', './controllers/IndexController'
    ],
    function(angular)
    {
        angular.bootstrap(document, ['meeting']);
    }
);


