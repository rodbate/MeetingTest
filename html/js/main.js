

require.config({

    paths: {
        'jquery': 'lib/jquery.min',
        'angular': 'lib/angular.min',
        'angular-route': 'lib/angular-route.min',
        'angular-resource': 'lib/angular-resource.min',
        'domReady': 'lib/domReady',
        'bootstrap': 'lib/bootstrap.min'
    },


    shim: {
        'jquery' : { exports: '$'},
        'angular': {
            deps: ['jquery'],
            exports: 'angular'
        },
        'angular-route': {
            deps: ['angular']
        },
        'angular-resource': {
            deps: ['angular']
        }
    }

});


require(['jquery', 'angular', 'angular-route', 'angular-resource'], function($, angular){

    $(document).ready(function(angular){

        angular.bootstrap(document, ['app']);
    });

});


