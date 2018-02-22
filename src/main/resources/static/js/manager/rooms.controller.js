(function() {

    'use strict';

    angular
        .module('RoyalPalaceHotel')
        .controller('roomsController', Controller);

    Controller.$inject = ['$scope', 'RoomsService', '$window', '$state', '$filter'];

    function Controller($scope, RoomsService, $window, $state, $filter) {

        function init() {

            RoomsService
                .getAllRooms()
                .then(function (response) {
                    response.data.forEach(function (room) {
                        console.log($('.r'+room.roomNumber).length);
                        $('.r'+room.roomNumber).addClass('type'+room.roomType.roomName.substring(0,2).toUpperCase());
                    })
                });
        }

        init();
    }

})();