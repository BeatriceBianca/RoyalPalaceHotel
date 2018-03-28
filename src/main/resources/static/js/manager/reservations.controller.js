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
        _self.loading = false;
        var refreshRooms;
        
        _self.closeInterval = function () {
            clearInterval(refreshRooms);
        };

        _self.allReservations = [];

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
            _self.loading = true;
            _self.request = {
                arrivalDate: $('#arrivalDate').val(),
                departureDate: $('#departureDate').val()
            };

            ReservationsService
                .getAllReservationsBetweenDates(_self.request.arrivalDate, _self.request.departureDate)
                .then(function (response) {
                    response.data.forEach(function (request) {
                        request.rooms.forEach(function (room) {
                            $('.r'+room.roomNumber).addClass('occupiedRoom');
                            $('.r'+room.roomNumber).off('click', getRoomDetails);
                            $('.r'+room.roomNumber).css('cursor', 'context-menu');
                        });
                    });

                    ReservationsService
                        .getAllChosenRooms()
                        .then(function (response) {
                            response.data.forEach(function (chosenRooms) {

                                if (!_self.selectedRooms.find(r => r.roomNumber === chosenRooms.room.roomNumber)) {
                                    $('.r'+chosenRooms.room.roomNumber).addClass('chosenRooms');
                                    $('.r'+chosenRooms.room.roomNumber).off('click', getRoomDetails);
                                    $('.r'+chosenRooms.room.roomNumber).css('cursor', 'context-menu');
                                }
                            });
                            _self.loading = false;
                        });
                });

            refreshRooms = setInterval(function() {
                ReservationsService
                    .getAllReservationsBetweenDates(_self.request.arrivalDate, _self.request.departureDate)
                    .then(function (response) {

                        $('.occupiedRoom').on('click', getRoomDetails);
                        $('.occupiedRoom').css('cursor', 'pointer');
                        $('.occupiedRoom').removeClass('occupiedRoom');

                        response.data.forEach(function (request) {
                            request.rooms.forEach(function (room) {

                                if(_self.selectedRoom) {
                                    if(_self.selectedRoom.id === room.id) {
                                        $('.modal').modal('toggle');
                                    }
                                }

                                $('.r'+room.roomNumber).addClass('occupiedRoom');
                                $('.r'+room.roomNumber).off('click', getRoomDetails);
                                $('.r'+room.roomNumber).css('cursor', 'context-menu');
                            });
                        });

                        ReservationsService
                            .getAllChosenRooms()
                            .then(function (response) {
                                response.data.forEach(function (chosenRooms) {

                                    $('.chosenRooms').on('click', getRoomDetails);
                                    $('.chosenRooms').css('cursor', 'pointer');
                                    $('.chosenRooms').removeClass('chosenRooms');

                                    if (!_self.selectedRooms.find(r => r.roomNumber === chosenRooms.room.roomNumber)) {
                                        $('.r'+chosenRooms.room.roomNumber).addClass('chosenRooms');
                                        $('.r'+chosenRooms.room.roomNumber).off('click', getRoomDetails);
                                        $('.r'+chosenRooms.room.roomNumber).css('cursor', 'context-menu');
                                    }
                                });
                                _self.loading = false;
                            });
                    })
            },5000);
        };
        
        _self.searchByIdNumber = function () {
            if (_self.idNumber.length === 13) {
                ReservationsService
                    .searchByIdNumber(_self.idNumber)
                    .then(function (value) {
                        if (value.data !== "") {
                            guestAlreadyExist = true;

                            _self.guest = value.data;
                        } else {
                            _self.guest.idNumber = _self.idNumber;
                        }
                    })
            }
        };
        
        _self.validateIdNumber = function () {
            if (_self.guest.idNumber.length === 13) {
                ReservationsService
                    .searchByIdNumber(_self.guest.idNumber)
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
            if ($('.r'+_self.selectedRoom.roomNumber).hasClass('chosenRoom')) {
                $('.modal-footer .chooseButton').html("Discard");
            } else {
                $('.modal-footer .chooseButton').html("Choose");
            }
            $('#myModal').modal();
        }

        function closeTables() {
            _self.isFirstFloorOpen = false;
            _self.isSecondFloorOpen = false;
            _self.isThirdFloorOpen = false;
            _self.isFourthFloorOpen = false;
        }

        function chooseRoom() {

            if ($('.r'+_self.selectedRoom.roomNumber).hasClass('chosenRoom')) {

                ReservationsService
                    .removeChosenRoom(_self.selectedRoom);

                $('.r'+_self.selectedRoom.roomNumber).addClass('type'+_self.selectedRoom.roomType.roomName.substring(0,2).toUpperCase());
                const index = _self.selectedRooms.indexOf(_self.selectedRoom);
                _self.selectedRooms.splice(index, 1);
                $('.r'+_self.selectedRoom.roomNumber).removeClass('chosenRoom');
            } else {

                ReservationsService
                    .saveChosenRoom(_self.selectedRoom);

                $('.r'+_self.selectedRoom.roomNumber).addClass('chosenRoom');
                $('.r'+_self.selectedRoom.roomNumber).removeClass('type'+_self.selectedRoom.roomType.roomName.substring(0,2).toUpperCase());
                _self.selectedRooms.push(_self.selectedRoom);
            }
            $('.modal').modal('toggle');
        }
        
        function submitRequest() {

            _self.closeInterval();

            if (!guestAlreadyExist) {
                ReservationsService
                    .saveGuest(_self.guest)
                    .then(function () {

                        ReservationsService
                            .searchByIdNumber(_self.guest.idNumber)
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
                                            .then(function () {
                                                _self.selectedRooms.forEach(function (value2) {
                                                    ReservationsService
                                                        .removeChosenRoom(value2);
                                                });
                                                $state.go('home');
                                            })
                                    });
                            });
                    });
            } else {
                ReservationsService
                    .searchByIdNumber(_self.guest.idNumber)
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
                                    .then(function () {
                                        _self.selectedRooms.forEach(function (value2) {
                                            ReservationsService
                                                .removeChosenRoom(value2);
                                        });
                                        $state.go('home');
                                    })
                            });
                    });
            }
        }

        window.onbeforeunload = function () {
            _self.closeInterval();
        }
    }

})();