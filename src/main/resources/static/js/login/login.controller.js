(function() {

    'use strict';

    angular
        .module('RoyalPalaceHotel')
        .controller('loginController', Controller);

    Controller.$inject = ['$scope', '$window', 'LoginService'];

    function Controller($scope, $window, LoginService) {
        var _self = this;

        function init() {

            if(!$window.location.href.includes('login')) {
                $window.location.href = '/login';
            }

            $('#login-part').css('display', 'block');
            $('#login-part').css('height', '100%');
            $('#password-part').css('display', 'none');
        }

        init();

        _self.forgotPass = forgotPass;

        function forgotPass() {
            $('#login-part').css('display', 'none');
            $('#password-part').css('display', 'block');
            $('#password-part').css('height', '100%');
        }
        
        _self.changePass = function (email) {
            console.log(email);
        }

    }

})();