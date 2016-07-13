

require.config({

    paths: {
        'jquery': 'lib/jquery.min',
        'angular': 'lib/angular.min',
        'angularRoute': 'lib/angular-route.min',
        'angularResource': 'lib/angular-resource.min',
        'domReady': 'lib/domReady',
        'bootstrap': 'lib/bootstrap.min',
        'datetimePicker': 'lib/bootstrap-datetimepicker.min',
        'datetimePickerCN': 'lib/bootstrap-datetimepicker.zh-CN'
    },


    shim: {
        'jquery' : { exports: '$'},
        'angular': {
            exports: 'angular',
            deps: ['jquery']
        },
        'bootstrap': ['jquery'],
        'datetimePicker': ['bootstrap'],
        'datetimePickerCN': ['datetimePicker'],
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


