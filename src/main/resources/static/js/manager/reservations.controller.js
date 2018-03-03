(function() {

    'use strict';

    angular
        .module('RoyalPalaceHotel')
        .controller('reservationsController', Controller);

    Controller.$inject = ['$scope', '$state'];

    function Controller($scope, $state) {
        var _self = this;


        function init() {
            if ($state.current.name === 'viewReservations') {
                _self.currentState = 'newReservation';
            } else {
                _self.currentState = $state.current.name;
            }

            $('.menu-div a button').removeClass('active');
            $('#'+_self.currentState).addClass('active');
        }

        init();
    }

})();