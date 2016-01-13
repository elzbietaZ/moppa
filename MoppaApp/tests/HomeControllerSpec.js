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




});
