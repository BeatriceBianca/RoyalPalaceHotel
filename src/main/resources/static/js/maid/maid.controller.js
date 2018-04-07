(function() {

    'use strict';

    angular
        .module('RoyalPalaceHotel')
        .controller('maidController', Controller);

    Controller.$inject = ['$rootScope', '$scope', '$state', '$window', '$filter', 'MaidService'];

    function Controller($rootScope, $scope, $state, $window, $filter, MaidService) {
        var _self = this;

        _self.username = "";
        _self.dataLoading = false;
        _self.currentUserPassword = "";
        _self.currentState = 'homeMaid';

        _self.encryptMd5 = encryptMd5;

        _self.logout = function () {
            $window.location.href = "/logout";
        };

        $rootScope.user = null;

        function init() {

            MaidService
                .getCurrentUser()
                .then(function (response) {
                    _self.username = response.data.firstName;
                    if (response.data !== "") {
                        _self.user = response.data;
                        _self.user.birthDate = $filter('date')(response.data.birthDate, "yyyy/MM/dd");
                        _self.user.hireDate = $filter('date')(response.data.hireDate, "yyyy/MM/dd");
                        _self.currentUserPassword = response.data.userPassword;
                    }

                    $rootScope.user = _self.user;
                });
        }

        function encryptMd5(pass) {
            return $.md5(pass);
        }

        init();

    }
})();