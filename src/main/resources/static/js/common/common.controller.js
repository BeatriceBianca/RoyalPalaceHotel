(function() {

    'use strict';

    angular
        .module('RoyalPalaceHotel')
        .controller('commonController', Controller);

    Controller.$inject = ['$scope', '$state', '$http', '$location', '$window', 'CommonService'];

    function Controller($scope, $state, $http, $location, $window, CommonService) {
        var _self = this;

        _self.username = "";
        _self.dataLoading = false;
        _self.currentUserPassword = "";
        _self.currentState = 'homeCommon';

        function init() {

            if ($state.current.name !== 'default' && $state.current.name !== '') {
                _self.currentState = $state.current.name;
            } else {
                $state.go(_self.currentState);
            }

            $('.menu-div a button').removeClass('active');
            $('#'+_self.currentState).addClass('active');
        }

        init();

        _self.login = function () {
            $window.location.href = '/login';
        }
    }
})();