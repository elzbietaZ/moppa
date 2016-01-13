/**
 * 
 */
angular.module('Home')
    .controller('HomeController', function ($rootScope,$scope, $http, $interval){

    	
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
                                                          "userId": $rootScope.globals.currentUser.userId
                                                      },
                                                   		  "nValue": $scope.nValue
                                                 }

                            $scope.createTaskData = "";
                            $scope.createTaskStatus = "Creating the task...";

                            $http({
                                method: 'POST',
                                url: 'http://localhost:8080/MoppaCustomerAPI/v1/tasks/create',
                                data: jsonTask
                            }).
                            success(function(data, status, headers, config) {
                                $scope.createTaskData = data;
                                $scope.createTaskStatus = "Successfully created the task!";
                            }).
                            error(function(data, status, headers, config) {
                                $scope.createTaskData = status;
                                $scope.createTaskStatus = "Error";
                            });
                        }

        $scope.getTasksForUser = function() {
                            $scope.allTasks = {array: []};
                            $scope.openSessionStatus = "Getting the task...";

                            $http({
                                method: 'GET',
                                url: 'http://localhost:8080/MoppaCustomerAPI/v1/tasks/user/'+$rootScope.globals.currentUser.userId
                            }).
                            success(function(data, status, headers, config) {
                                $scope.allTasks.array = data;
                                $scope.openSessionStatus = "Ok, tasks retreived";
                            }).
                            error(function(data, status, headers, config) {
                                $scope.allTasks = status;
                                $scope.openSessionStatus = "Error";
                            });

                        }


    })