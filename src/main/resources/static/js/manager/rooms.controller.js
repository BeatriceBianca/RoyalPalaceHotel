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

        _self.closeTables = closeTables;
        
        var allRooms = [];
        var selectedRoom;

        function getRoomDetails(event) {
            selectedRoom = allRooms.find(function (room) {
                if (room.roomNumber.toString() === event.target.className.substring(1,3))
                    return room;
            });
            $('.modal-title').append('Room number ' + selectedRoom.roomNumber);
            $('.modal-body p:first-child').append('Room Type: ' + selectedRoom.roomType.roomName);
            $('.modal-body p:nth-child(2)').append('Number of single bed: ' + selectedRoom.roomType.nrSingleBed);
            $('.modal-body p:nth-child(3)').append('Number of double bed: ' + selectedRoom.roomType.nrDoubleBed);
            $('#myModal').modal();
        }

        function closeTables() {
            _self.isFirstFloorOpen = false;
            _self.isSecondFloorOpen = false;
            _self.isThirdFloorOpen = false;
            _self.isFourthFloorOpen = false;
        }

        function init() {

            RoomsService
                .getAllRooms()
                .then(function (response) {
                    allRooms = response.data;
                    response.data.forEach(function (room) {
                        $('.r'+room.roomNumber).addClass('type'+room.roomType.roomName.substring(0,2).toUpperCase());
                        $('.r'+room.roomNumber).on('click', getRoomDetails);
                        $('.r'+room.roomNumber).css('cursor', 'pointer');
                    })
                });

            $(".modal").on("hidden.bs.modal", function(){
                $(".modal-body p").html("");
                $(".modal-title").html("");
            });
        }
        
        init();
    }

})();