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

        _self.newOffer = null;

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

            $('#datetimepicker1').datetimepicker({
                format: 'YYYY/MM/DD',
                minDate: moment()
            });

            $('#datetimepicker2').datetimepicker({
                format: 'YYYY/MM/DD',
                minDate: moment()
            });

            $('#datetimepicker1').datetimepicker().on('dp.change', function (e) {
                var incrementDay = moment(new Date(e.date));
                incrementDay.add(1, 'days');
                $('#datetimepicker2').data('DateTimePicker').minDate(incrementDay);
                $(this).data("DateTimePicker").hide();

            });

            $('#datetimepicker2').datetimepicker().on('dp.change', function (e) {
                var decrementDay = moment(new Date(e.date));
                decrementDay.subtract(1, 'days');
                $('#datetimepicker1').data('DateTimePicker').maxDate(decrementDay);
                $(this).data("DateTimePicker").hide();
            });
        }

        init();

        _self.disableNewOffer = function() {
            if (($('#startDate').val() !== "") && ($('#endDate').val() !== "") &&
                _self.newOffer.description && _self.newOffer.quantity)
                return false;
            else return true;
        };

        _self.disableNewPromotion = function () {
            if (_self.newPromotion.description && _self.newPromotion.name && _self.newPromotion.quantity)
                return false;
            else return true;
        }
    }

})();