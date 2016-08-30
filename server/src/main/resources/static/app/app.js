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

<<<<<<< HEAD

	

=======
>>>>>>> f3d59103363d5401cdb44021934ca2834ed792f6
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
            var providerId = data[0].id;
            $http.get('/provider/' + providerId + '/client').
                success(function (data) {
                    $scope.clients = data;
            });
        });
    $scope.setSelectedPolicy = function (policy) {
        $scope.selectedPolicy = policy;
        $http.get("/policy/"+policy.id)
        .then(function (response) {$scope.policy = response.data.records;});
    };
});


