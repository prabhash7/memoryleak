var app = angular.module('policy_details', []);
app.controller('policyDetails', function($scope, $http) {
	$http.get("http://www.w3schools.com/angular/customers.php")
        .then(function (response) {$scope.names = response.data.records;});
});
//$http.get("http://localhost:8080/policies")

