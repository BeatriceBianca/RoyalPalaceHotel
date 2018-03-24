(function() {

    'use strict';

    angular
        .module('RoyalPalaceHotel')
        .controller('reservationsController', Controller);

    Controller.$inject = ['$scope', '$state', 'ReservationsService', 'RoomsService', 'ManagerService'];

    function Controller($scope, $state, ReservationsService, RoomsService, ManagerService) {
        var _self = this;

        _self.isFirstFloorOpen = true;
        _self.isSecondFloorOpen = false;
        _self.isThirdFloorOpen = false;
        _self.isFourthFloorOpen = false;

        _self.closeTables = closeTables;
        _self.chooseRoom = chooseRoom;
        _self.submitRequest = submitRequest;

        _self.selectedRooms = [];

        var allRooms = [];
        _self.selectedRoom = null;

        var guestAlreadyExist = false;



        function init() {
            if ($state.current.name === 'viewReservations') {
                _self.currentState = 'newReservation';
            } else {
                _self.currentState = $state.current.name;
            }

            $('.menu-div a button').removeClass('active');
            $('#'+_self.currentState).addClass('active');

            _self.viewGuest = true;
            _self.viewRequest = false;
            _self.viewRoom = false;

            $('#datetimepicker1').datetimepicker({
                format: 'YYYY/MM/DD'
            });

            $('#datetimepicker2').datetimepicker({
                format: 'YYYY/MM/DD'
            });

            RoomsService
                .getAllRoomTypes()
                .then(function (value) {
                    _self.roomTypes = value.data;
                });

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




        _self.saveRequestedDate = function () {
            _self.request = {
                arrivalDate: $('#arrivalDate').val(),
                departureDate: $('#departureDate').val()
            };

            ReservationsService
                .getAllReservationsBetweenDates(_self.request.arrivalDate, _self.request.departureDate)
                .then(function (response) {
                    console.log(response);
                });
        };
        
        _self.searchByCnp = function () {
            if (_self.cnp.length === 13) {
                ReservationsService
                    .searchByCnp(_self.cnp)
                    .then(function (value) {
                        if (value.data !== "") {
                            guestAlreadyExist = true;

                            _self.guest = value.data;
                        }
                    })
            }
        };
        
        _self.validateCnp = function () {
            if (_self.guest.cnp.length === 13) {
                ReservationsService
                    .searchByCnp(_self.guest.cnp)
                    .then(function (value) {
                        if (value.data !== "") {
                            guestAlreadyExist = true;
                        }
                    })
            }
        };

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
            $('.modal-footer .chooseButton').html("Choose");
            $('#myModal').modal();
        }

        function closeTables() {
            _self.isFirstFloorOpen = false;
            _self.isSecondFloorOpen = false;
            _self.isThirdFloorOpen = false;
            _self.isFourthFloorOpen = false;
        }

        function chooseRoom() {
            _self.selectedRooms.push(_self.selectedRoom);
            $('.modal').modal('toggle');
        }
        
        function submitRequest() {

            if (!guestAlreadyExist) {
                ReservationsService
                    .saveGuest(_self.guest)
                    .then(function () {

                        ReservationsService
                            .searchByCnp(_self.guest.cnp)
                            .then(function (response) {

                                _self.request.customer = response.data;

                                var today = new Date();
                                var dd = today.getDate();
                                var mm = today.getMonth()+1;
                                var yyyy = today.getFullYear();

                                if (mm.toString().length === 1) {
                                    mm = "0" + mm;
                                }

                                _self.request.requestDate = yyyy + "/" + mm + "/" + dd;

                                ManagerService
                                    .getCurrentUser()
                                    .then(function (currentUser) {
                                        _self.request.user = currentUser.data;

                                        _self.request.rooms = _self.selectedRooms;
                                        ReservationsService
                                            .saveRequest(_self.request)
                                            .then(function (value) {
                                                console.log(value);
                                            })
                                    });
                            });
                    });
            } else {
                ReservationsService
                    .searchByCnp(_self.guest.cnp)
                    .then(function (response) {

                        _self.request.customer = response.data;

                        var today = new Date();
                        var dd = today.getDate();
                        var mm = today.getMonth()+1;
                        var yyyy = today.getFullYear();

                        if (mm.toString().length === 1) {
                            mm = "0" + mm;
                        }

                        _self.request.requestDate = yyyy + "/" + mm + "/" + dd;

                        ManagerService
                            .getCurrentUser()
                            .then(function (currentUser) {
                                _self.request.user = currentUser.data;

                                _self.request.rooms = _self.selectedRooms;
                                ReservationsService
                                    .saveRequest(_self.request)
                                    .then(function (value) {
                                        console.log(value);
                                    })
                            });
                    });
            }
        }
    }

})();