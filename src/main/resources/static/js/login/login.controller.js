(function() {

    'use strict';

    angular
        .module('RoyalPalaceHotel')
        .controller('loginController', Controller);

    Controller.$inject = ['$scope', 'LoginService'];

    function Controller($scope, LoginService) {
        var _self = this;

        _self.forgotPass = forgotPass;

        function forgotPass() {
            console.log("Forgot password");
        }

    }

})();