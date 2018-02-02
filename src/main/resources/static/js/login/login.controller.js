(function() {

    'use strict';

    angular
        .module('RoyalPalaceHotel')
        .controller('loginController', Controller);

    Controller.$inject = ['$scope', 'LoginService', '$location'];

    function Controller($scope, LoginService, $location) {
        var _self = this;

        $scope.forgotPass = forgotPass;

        function forgotPass() {

        }
    }

})();