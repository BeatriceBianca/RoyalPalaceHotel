(function() {

    'use strict';

    angular
        .module('RoyalPalaceHotel')
        .controller('commonController', Controller);

    Controller.$inject = ['$rootScope', '$scope', '$state', '$filter', '$window', 'MaidService', 'CommonService'];

    function Controller($rootScope, $scope, $state, $filter, $window, MaidService, CommonService) {
        var _self = this;

        $rootScope.user = null;

        _self.username = "";
        _self.currentUserPassword = "";

        _self.newUser = null;

        _self.loading = false;
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

                    _self.hasRole = ($rootScope.user && ($rootScope.user.userRole == 'MANAGER'));

                    if ($rootScope.user) {
                        switch ($rootScope.user.userRole) {
                            case 'MANAGER': {_self.currentState = 'homeManager';break;}
                            case 'RECEPTIONIST': {_self.currentState = 'homeReceptionist';break;}
                            case 'MAID': {_self.currentState = 'homeMaid';break;}
                        }
                    } else {
                        _self.currentState = 'homeCommon';
                    }

                    if ($state.current.name !== 'default' && $state.current.name !== '') {
                        _self.currentState = $state.current.name;
                    } else {
                        $state.go(_self.currentState);
                    }

                    $('.menu-div a button').removeClass('active');
                    $('#'+_self.currentState).addClass('active');

                    $('#datetimepicker1').datetimepicker({
                        format: 'YYYY/MM/DD'
                    });

                    $('#datetimepicker2').datetimepicker({
                        format: 'YYYY/MM/DD'
                    });
                });

            _self.offers = [];
            var nr = 0;
            CommonService
                .getAllOffers()
                .then(function (response) {

                    response.data.forEach(function (o) {
                        o.nr = nr;
                        nr++;
                       _self.offers.push(o);
                    });
                });

        }

        init();

        _self.login = function () {
            $window.location.href = '/login';
        };

        _self.logout = function () {
            $window.location.href = "/logout";
        };

        _self.encryptMd5 = function (pass) {
            return $.md5(pass);
        }

        _self.disableNewAccount = function() {
            if ($('#birthDate').val() && $('#hireDate').val() &&
                _self.newUser.lastName && _self.newUser.firstName && _self.newUser.userEmail && _self.newUser.phone)
                return false;
            return true;
        }

        _self.goTo = function (state) {
            $state.go(state);
        }
    }
})();