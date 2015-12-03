/**
 * 
 */
angular.module('SQA2014.controllers', [])
    .controller('controller', function ($scope, $http, $interval){

    	
    	$scope.testClick = function() {
            $scope.testClickData = "clicked!";
            
        }

        $scope.getTask = function() {
                    $scope.openSessionData = "";
                    $scope.openSessionStatus = "Getting the task...";

                    $http({
                        method: 'GET',
                        url: 'http://localhost:8080/MoppaCustomerAPI/v1/tasks/1'
                    }).
                    success(function(data, status, headers, config) {
                        $scope.openSessionData = data;
                        $scope.openSessionStatus = "Ok";
                    }).
                    error(function(data, status, headers, config) {
                        $scope.openSessionData = status;
                        $scope.openSessionStatus = "Error";
                    });
                }

        $scope.createTask = function() {
                             var jsonTask =
                                                 {
                                                      "user" : {
                                                          "userId": 1
                                                      },
                                                   		  "nValue": $scope.nValue
                                                 }

                            $scope.openSessionData = "";
                            $scope.openSessionStatus = "Creating the task...";

                            $http({
                                method: 'POST',
                                url: 'http://localhost:8080/MoppaCustomerAPI/v1/tasks/create',
                                data: jsonTask
                            }).
                            success(function(data, status, headers, config) {
                                $scope.openSessionData = data;
                                $scope.openSessionStatus = "Ok";
                            }).
                            error(function(data, status, headers, config) {
                                $scope.openSessionData = status;
                                $scope.openSessionStatus = "Error";
                            });
                        }

        $scope.getTasksForUser = function() {
                            $scope.openSessionData = "";
                            $scope.allTasks = "";
                            $scope.openSessionStatus = "Getting the task...";

                            $http({
                                method: 'GET',
                                url: 'http://localhost:8080/MoppaCustomerAPI/v1/tasks/user/1'
                            }).
                            success(function(data, status, headers, config) {
                                $scope.allTasks = data;
                                $scope.openSessionStatus = "Ok, tasks retreived";
                            }).
                            error(function(data, status, headers, config) {
                                $scope.openSessionData = status;
                                $scope.openSessionStatus = "Error";
                            });
                        }


    })