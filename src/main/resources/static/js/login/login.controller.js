(function() {

    'use strict';

    angular
        .module('RoyalPalaceHotel')
        .controller('loginController', Controller);

    Controller.$inject = ['$scope', 'LoginService'];

    function Controller($scope, LoginService) {
        var _self = this;

        _self.forgotPass = forgotPass;
        _self.newGuest = newGuest;
        _self.guestAccount = 'guest account';

        _self.login = true;
        _self.newAccount = false;

        function forgotPass() {
            console.log("Forgot password");
        }

         function newGuest() {
            console.log("New Account");
        }
    }

})();