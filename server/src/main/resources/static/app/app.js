'use strict';

angular.module('myApp', [
    'ngRoute',
    'myApp.version',
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

angular.module('myApp').directive('clientList', function () {
    return {
        restrict: 'E',
        templateUrl: "clientList.html"
    };
});

angular.module('myApp').directive('clientDetails', function () {
    return {
        restrict: 'E',
        templateUrl: "clientDetails.html"
    };
});

angular.module('myApp').controller('memoryLeaksCtrl', function ($scope, $http) {
    $scope.selectedTab = 'clients';
    
    $http.get('/policies').
        success(function (data) {
            $scope.policies = data;
            console.log('received: ' + $scope.policies.id);
            $scope.selectedPolicy = data[0];
        });

    var me = this;
    $http.get('/provider').
        success(function (data) {
            $scope.providers = data;
            var idx = data[0].name.indexOf(':');
            if (idx != -1) {
                $scope.provider = data[0].name.substring(idx + 1);
            } else {
                $scope.provider = data[0].name;
            }
            $scope.selectedProvider = data[0];
            console.log('received: ' + $scope.provider);
            me.providerId = data[0].id;
            $http.get('/provider/' + me.providerId + '/client').
                success(function (data) {
                    $scope.clients = data;
                });
        });
    $scope.setSelectedPolicy = function (policy) {
        $scope.selectedPolicy = policy;
        $http.get("/policy/"+policy.id)
        .then(function (response) {$scope.policy = response.data.records;});
    };
        
    $scope.setSelectedClient = function (client) {
        $scope.selectedClient = client;
        $http.get("/client/"+client.id)
        .then(function (response) {$scope.client = response.data.records;});
        //get backups for the client
        $scope.backups = [];
        $http.get('/provider/' + me.providerId + '/client/' + client.id + '/backup').
        success(function (data) {
            $scope.backups = data;
        }); 
    };

    $scope.backup = function () {
        $http.get('/provider/' + me.providerId + '/client/' + $scope.selectedClient.id + '/adhock').
            success(function (data) {
                alert(data);
            });
    };
});
