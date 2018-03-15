(function() {

    'use strict';

    angular
        .module('RoyalPalaceHotel')
        .controller('roomsController', Controller);

    Controller.$inject = ['$scope', '$state', 'RoomsService'];

    function Controller($scope, $state, RoomsService) {
        var _self = this;

        _self.isFirstFloorOpen = true;
        _self.isSecondFloorOpen = false;
        _self.isThirdFloorOpen = false;
        _self.isFourthFloorOpen = false;

        _self.closeTables = closeTables;
        _self.editRoom = editRoom;
        
        var allRooms = [];
        _self.selectedRoom = null;

        function updatePrice() {
            var selectedValue = _self.roomTypes.find(function (type) {
                if (type.id.toString() === $('.modal-body select').val()) {
                    return type;
                }
            });
            $('.modal-body p:last-child input').val(selectedValue.price);
        }

        function editRoom() {
            RoomsService
                .getAllRoomTypes()
                .then(function (roomTypes) {

                    if ($('.modal-footer .editButton').text() === 'Edit') {
                        _self.roomTypes = roomTypes.data;
                        var options = "";

                        _self.roomTypes.forEach(function (roomType) {
                            options += "<option value='" + roomType.id + "'>" + roomType.roomName + "</option>";
                        });

                        $('.modal-body p:first-child').html("");
                        $('.modal-body p:first-child').append("Room Type: " + "<select id='typesList'>" + options + "</select>");

                        $('.modal-body p:last-child').html("");
                        $('.modal-body p:last-child').append("Price: " + "<input type='text' id='price' value=" + _self.selectedRoom.roomType.price +
                            " /> <br/> <span> * You will change the price of all " + _self.selectedRoom.roomType.roomName+ " rooms </span>");


                        $('.modal-body select').val(_self.selectedRoom.roomType.id);

                        $('.modal-footer .editButton').html("");
                        $('.modal-footer .editButton').append("Save");

                        $('#typesList').on('change', updatePrice);
                    } else {
                        _self.selectedRoom.roomType = _self.roomTypes.find(function (r) {
                            if (r.id.toString() === $('.modal-body select').val()) {
                                return r;
                            }
                        });

                        if ($('.modal-body p:last-child input').val() !== _self.selectedRoom.roomType.price.toString()) {

                            _self.selectedRoom.roomType.price = $('.modal-body p:last-child input').val();

                            RoomsService
                                .editPrice(_self.selectedRoom.roomType)
                                .then(function () {

                                    RoomsService
                                        .editRoomType(_self.selectedRoom)
                                        .then(function () {
                                            $('.modal').modal('toggle');
                                            $state.reload();
                                        });
                                });
                        } else {
                            RoomsService
                                .editRoomType(_self.selectedRoom)
                                .then(function () {
                                    $('.modal').modal('toggle');
                                    $state.reload();
                                });
                        }
                    }
                });
        }

        function getRoomDetails(event) {
            _self.selectedRoom = allRooms.find(function (room) {
                if (room.roomNumber.toString() === event.target.className.substring(1,3))
                    return room;
            });
            $('.modal-title').append('Room number ' + _self.selectedRoom.roomNumber);
            $('.modal-body p:first-child').append('Room Type: ' + _self.selectedRoom.roomType.roomName);
            $('.modal-body p:nth-child(2)').append('Number of single bed: ' + _self.selectedRoom.roomType.nrSingleBed);
            $('.modal-body p:nth-child(3)').append('Number of double bed: ' + _self.selectedRoom.roomType.nrDoubleBed);
            $('.modal-body p:nth-child(4)').append('Price: ' + _self.selectedRoom.roomType.price + " &euro;");
            $('.modal-footer .editButton').html("Edit");
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
                _self.selectedRoom = null;
            });
        }
        
        init();
    }

})();