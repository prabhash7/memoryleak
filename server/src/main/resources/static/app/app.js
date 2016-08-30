'use strict';

angular.module('myApp', [
    'ngRoute',
    'myApp.version',
    'ui.tree'
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

angular.module('myApp').directive('sideNav', function () {
    return {
        restrict: 'E',
        templateUrl: "sideNav.html"
    };
});

angular.module('myApp').directive('policyList', function () {
    return {
        restrict: 'E',
        templateUrl: "policyList.html"
    };
});

angular.module('myApp').directive('policyDetails', function () {
    return {
        restrict: 'E',
        templateUrl: "policyDetails.html"
    };
});

angular.module('myApp').controller('memoryLeaksCtrl', function ($scope, $http) {
    $http.get('/policies').
        success(function (data) {
            $scope.policies = data;
            console.log('received: ' + $scope.policies.id);
        });
    $http.get('/provider').
        success(function (data) {
            $scope.providers = data;
            var idx = data[0].name.indexOf(':');
            if (idx != -1) {
                $scope.provider = data[0].name.substring(idx + 1);
            } else {
                $scope.provider = data[0].name;
            }
            console.log('received: ' + $scope.provider);
        });
    $scope.setSelectedPolicy = function (policy) {
        $scope.selectedPolicy = policy;
    };
});

angular.module('myApp').config(function (treeConfig) {
    treeConfig.defaultCollapsed = true;
});
