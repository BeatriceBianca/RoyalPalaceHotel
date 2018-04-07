(function() {

    'use strict';

    angular
        .module('RoyalPalaceHotel')
        .controller('managerController', Controller);

    Controller.$inject = ['$rootScope', '$scope', '$window', '$state', '$filter', 'ManagerService', 'MaidService'];

    function Controller($rootScope, $scope, $window, $state, $filter, ManagerService, MaidService) {
        var _self = this;

        _self.username = "";
        _self.currentUserPassword = "";
        _self.currentState = 'homeManager';

        $rootScope.user = null;

        _self.logout = function () {
            $window.location.href = "/logout";
        };
        
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

            if ($state.current.name !== 'default' && $state.current.name !== '') {
                _self.currentState = $state.current.name;
            } else {
                $state.go(_self.currentState);
            }

            $('.menu-div a button').removeClass('active');
            $('#'+_self.currentState).addClass('active');
        }

        init();

    }

})();