(function() {

    'use strict';

    angular
        .module('RoyalPalaceHotel')
        .controller('loginController', Controller);

    Controller.$inject = ['$scope', 'LoginService', '$location'];

    function Controller($scope, LoginService, $location) {
        var _self = this;

        _self.forgotPass = forgotPass;

        function forgotPass() {

        }
    }

})();