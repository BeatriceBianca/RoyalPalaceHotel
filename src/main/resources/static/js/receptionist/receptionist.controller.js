(function() {

    'use strict';

    angular
        .module('RoyalPalaceHotel')
        .controller('receptionistController', Controller);

    Controller.$inject = ['$scope', '$state', '$window', '$filter', 'MaidService'];

    function Controller($scope, $state, $window, $filter, MaidService) {
        var _self = this;

        _self.username = "";
        _self.dataLoading = false;
        _self.currentUserPassword = "";
        _self.currentState = 'homeReceptionist';

        _self.logout = function () {
            $window.location.href = "/logout";
        };

        function init() {

            MaidService
                .getCurrentUser()
                .then(function (response) {
                    _self.username = response.data.firstName;
                    _self.user = response.data;
                    _self.user.birthDate = $filter('date')(response.data.birthDate, "yyyy/MM/dd");
                    _self.user.hireDate = $filter('date')(response.data.hireDate, "yyyy/MM/dd");
                    _self.currentUserPassword = response.data.userPassword;
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