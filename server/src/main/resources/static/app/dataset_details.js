var app = angular.module('dataset_details', []);
app.controller('datasetDetails', function($scope, $http) {
    $http.get("http://www.w3schools.com/angular/customers.php")
    .then(function (response) {$scope.names = response.data.records;});
});
