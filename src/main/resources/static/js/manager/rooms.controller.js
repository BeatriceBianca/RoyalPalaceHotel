(function() {

    'use strict';

    angular
        .module('RoyalPalaceHotel')
        .controller('roomsController', Controller);

    Controller.$inject = ['$scope', 'RoomsService'];

    function Controller($scope, RoomsService) {
        var _self = this;

        _self.isFirstFloorOpen = true;
        _self.isSecondFloorOpen = false;
        _self.isThirdFloorOpen = false;
        _self.isFourthFloorOpen = false;

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