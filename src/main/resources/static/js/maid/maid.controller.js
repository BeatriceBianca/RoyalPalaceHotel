(function() {

    'use strict';

    angular
        .module('RoyalPalaceHotel')
        .controller('maidController', Controller);

    Controller.$inject = ['$rootScope', '$scope', '$state', '$window', '$filter', 'MaidService'];

    function Controller($rootScope, $scope, $state, $window, $filter, MaidService) {
        var _self = this;

        _self.username = "";
        _self.currentUserPassword = "";
        _self.currentState = 'homeMaid';

        _self.encryptMd5 = encryptMd5;

        _self.logout = function () {
            $window.location.href = "/logout";
        };

        $rootScope.user = null;

        function init() {



        }



        init();

    }
})();