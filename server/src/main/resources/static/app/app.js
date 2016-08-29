'use strict';

angular.module('myApp', [
    'ngRoute',
    'myApp.version'
]).config(['$locationProvider', '$routeProvider', '$httpProvider', function ($locationProvider, $routeProvider, $httpProvider) {
            $routeProvider.otherwise({redirectTo: 'index.html'});
            $httpProvider.defaults.useXDomain = true;
            delete $httpProvider.defaults.headers.common["X-Requested-With"];
            $httpProvider.defaults.headers.common["Accept"] = "application/json";
            $httpProvider.defaults.headers.common["Content-Type"] = "application/json";
        }]);

angular.module('myApp').directive('globalHeader', function () {
    return {
        restrict: 'E',
        templateUrl: "header.html"
    };
});

angular.module('myApp').controller('memoryLeaksCtrl', function ($scope, $http) {
    $http.get('http://10.168.246.11:8080/policy').
        success(function (data) {
            $scope.records = data;
            console.log('received: ' + $scope.records.id);
        });
});