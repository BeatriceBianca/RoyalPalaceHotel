(function() {

    'use strict';

    angular
        .module('RoyalPalaceHotel')
        .controller('managerController', Controller);

    Controller.$inject = ['$scope', 'ManagerService', '$window', '$state', '$filter', '$location'];

    function Controller($scope, ManagerService, $window, $state, $filter, $location) {
        var _self = this;

        _self.username = "";
        _self.dataLoading = false;
        _self.currentUserPassword = "";
        _self.currentState = 'home';

        _self.encryptMd5 = encryptMd5;

        var baseTestContext = "http://" + location.host + '/manager';
        
        function init() {

            ManagerService
                .getCurrentUser()
                .then(function (response) {
                    _self.username = response.data.firstName;
                    _self.user = response.data;
                    _self.user.birthDate = $filter('date')(response.data.birthDate, "yyyy/MM/dd");
                    _self.user.hireDate = $filter('date')(response.data.hireDate, "yyyy/MM/dd");
                    _self.currentUserPassword = response.data.userPassword;
                });

            $('#datetimepicker1').datetimepicker({
                format: 'YYYY/MM/DD'
            });
            $('#datetimepicker2').datetimepicker({
                format: 'YYYY/MM/DD'
            });

            if ($state.current.name !== 'default' && $state.current.name !== '') {
                _self.currentState = $state.current.name;
            }

            $('.menu-div a button').removeClass('active');
            $('#'+_self.currentState).addClass('active');
        }

        function encryptMd5(pass) {
            return $.md5(pass);
        }

        init();
    }

})();