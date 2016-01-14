'use strict';


 describe('Controller: HomeController', function() {

    var $rootScope, $scope, $http, $interval, $controller;

    beforeEach(module('Home'));

    beforeEach(inject(function(_$rootScope_, _$controller_){
        $rootScope = _$rootScope_;
        $scope = $rootScope.$new();
        $controller = _$controller_;

        $controller('HomeController', {'$rootScope' : $rootScope, '$scope': $scope});
    }));

    it('should pass', function() {
            expect(true).toBe(true);
    });

    it('should be undefined', function() {
                expect($scope.openSessionStatus).toBeUndefined();
                expect($scope.openSessionData).toBeUndefined();

    });

    describe("Creating new task", function() {

      beforeEach(function() {
        $scope.nValue = 5;
       //$scope.createTask();
      });
         it('should set nr', function() {
                expect($scope.nValue).toBe(5);
         });
    });

     describe("Getting new task", function() {
          beforeEach(function() {
            $scope.getTask();
          });
             it('should be getting the task', function() {
                    expect($scope.openSessionStatus).toBe("Getting the task...");
             });
        });


     it('Should get task correctly', inject(function($httpBackend,$http) {

       $scope.getTask();
       $httpBackend
         .when('GET', 'http://localhost:8080/MoppaCustomerAPI/v1/tasks/1')
         .respond(200);

       $httpBackend.flush();
       expect($scope.openSessionStatus).toBe("Ok");

     }));

     it('Should get task with error', inject(function($httpBackend,$http) {

            $scope.getTask();
            $httpBackend
              .when('GET', 'http://localhost:8080/MoppaCustomerAPI/v1/tasks/1')
              .respond(400);

            $httpBackend.flush();
            expect($scope.openSessionStatus).toBe("Error");

      }));



});
