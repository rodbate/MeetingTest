

require.config({

    paths: {
        'jquery': 'lib/jquery.min',
        'angular': 'lib/angular.min',
        'angular-route': 'lib/angular-route.min',
        'angular-resource': 'lib/angular-resource.min',
        'domReady': 'lib/domReady',
        'bootstrapJs': 'lib/bootstrap.min'
    },


    shim: {
        'angular': {
            exports: 'angular'
        },
        'angular-route': {
            deps: ['angular']
        },
        'angular-resource': {
            deps: ['angular']
        }
    },

    deps: [
        './bootstrap'
    ]
});


