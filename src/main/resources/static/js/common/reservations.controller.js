(function() {

    'use strict';

    angular
        .module('RoyalPalaceHotel')
        .controller('reservationsController', Controller);

    Controller.$inject = ['$rootScope', '$scope', '$cookies','$state', 'ReservationsService', 'CommonService', 'MaidService'];

    function Controller($rootScope, $scope, $cookies, $state, ReservationsService, CommonService, MaidService) {
        var _self = this;

        _self.isFirstFloorOpen = true;
        _self.isSecondFloorOpen = false;
        _self.isThirdFloorOpen = false;
        _self.isFourthFloorOpen = false;

        _self.closeTables = closeTables;
        _self.chooseRoom = chooseRoom;
        _self.submitRequest = submitRequest;

        _self.user = $rootScope.user;

        _self.selectedRooms = [];

        var allRooms = [];
        _self.selectedRoom = null;

        var guestAlreadyExist = false;
        _self.loading = false;

        _self.isRequestedSet = function() {
            if ($('#arrivalDate').val() &&  $('#departureDate').val() && _self.nrOfPerson)
                return false;
            return true;
        };

        var refreshRooms;
        
        _self.closeInterval = function () {
            clearInterval(refreshRooms);
        };

        _self.allReservations = [];
        _self.hasRole = false;

        function init() {

            _self.hasRole = ($rootScope.user && ($rootScope.user.userRole == 'MANAGER' || $rootScope.user.userRole == 'RECEPTIONIST'));

            $('.menu-div a button').prop('disabled', true);
            $('.menu-div a button').css('cursor', 'context-menu');
            $('.menu-div a button').css('opacity', '0.5');

            $('#viewRes').prop('disabled', true);
            $('#viewRes').css('cursor', 'context-menu');
            $('#viewRes').css('opacity', '0.5');

            $('.menu-div a button#newReservation').prop('disabled', false);
            $('.menu-div a button#newReservation').css('cursor', 'pointer');
            $('.menu-div a button#newReservation').css('opacity', '1');

            $('.menu-div a button#viewReservations').prop('disabled', false);
            $('.menu-div a button#viewReservations').css('cursor', 'pointer');
            $('.menu-div a button#viewReservations').css('opacity', '1');

            if ($state.current.name === 'newReservation' && $rootScope.user && ($rootScope.user.userRole === 'MANAGER' || $rootScope.user.userRole === 'RECEPTIONIST')) {
                _self.currentState = 'viewReservations';
            } else {
                _self.currentState = $state.current.name;
            }

            $('.menu-div a button').removeClass('active');
            $('#'+_self.currentState).addClass('active');

            _self.viewGuest = true;
            _self.viewRequest = false;
            _self.viewRoom = false;
            _self.viewCustomReq = false;

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

            CommonService
                .getAllRoomTypes()
                .then(function (value) {
                    _self.roomTypes = value.data;
                });

            CommonService
                .getAllRooms()
                .then(function (response) {
                    allRooms = response.data;
                    response.data.forEach(function (room) {
                        $('.r'+room.roomNumber).addClass('type'+room.roomType.roomName.substring(0,2).toUpperCase());
                        $('.r'+room.roomNumber).on('click', getRoomDetails);
                        $('.r'+room.roomNumber).css('cursor', 'pointer');
                    })
                });

            $("#myModal").on("hidden.bs.modal", function(){
                $("#myModal .modal-body p").html("");
                $("#myModal .modal-title").html("");
                _self.selectedRoom = null;
            });

            $("#successModal").on("hidden.bs.modal", function(){
                if ($rootScope.user) {
                    if($rootScope.user.userRole === 'MANAGER')
                        $state.go('viewReservations');
                    else if ($rootScope.user.userRole === 'RECEPTIONIST')
                        $state.go('viewReservations');
                } else
                    $state.go('homeCommon');
            });

            _self.selectedRooms.forEach(function (value2) {
                ReservationsService
                    .removeChosenRoom(value2);
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
                                        $('#myModal .modal').modal('toggle');
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
                                    //
                                    // if (!_self.selectedRooms.find(r => r.roomNumber === chosenRooms.room.roomNumber)) {
                                    $('.r'+chosenRooms.room.roomNumber).addClass('chosenRooms');
                                    $('.r'+chosenRooms.room.roomNumber).off('click', getRoomDetails);
                                    $('.r'+chosenRooms.room.roomNumber).css('cursor', 'context-menu');
                                    // }
                                });
                                _self.loading = false;
                            });
                    })
            },5000);
        };
        
        _self.searchByEmail = function (email) {

            if (email) {
                ReservationsService
                    .searchByEmail(email)
                    .then(function (value) {
                        if (value.data !== "") {
                            guestAlreadyExist = true;

                            if (_self.user) {
                                _self.guest = value.data;
                            }
                        }
                    })
            }
        };
        
        // _self.validateIdNumber = function () {
        //     if (_self.guest.idNumber.length === 13) {
        //         ReservationsService
        //             .searchByIdNumber(_self.guest.idNumber)
        //             .then(function (value) {
        //                 if (value.data !== "") {
        //                     guestAlreadyExist = true;
        //                 }
        //             })
        //     }
        // };

        function getRoomDetails(event) {
            _self.selectedRoom = allRooms.find(function (room) {
                if (room.roomNumber.toString() === event.target.className.substring(1,3))
                    return room;
            });
            $('#myModal .modal-title').html("");
            $('#myModal .modal-title').append('Room number ' + _self.selectedRoom.roomNumber);

            $('#myModal .modal-body p:first-child').html("");
            $('#myModal .modal-body p:first-child').append('Room Type: ' + _self.selectedRoom.roomType.roomName);

            $('#myModal .modal-body p:nth-child(2)').html("");
            if (_self.selectedRoom.roomType.nrSingleBed !== 0) {
                $('#myModal .modal-body p:nth-child(2)').append('Number of single bed: ' + _self.selectedRoom.roomType.nrSingleBed);
            }

            $('#myModal .modal-body p:nth-child(3)').html("");
            if (_self.selectedRoom.roomType.nrDoubleBed !== 0) {
                $('#myModal .modal-body p:nth-child(3)').append('Number of double bed: ' + _self.selectedRoom.roomType.nrDoubleBed);
            }

            $('#myModal .modal-body p:nth-child(4)').html("");
            $('#myModal .modal-body p:nth-child(4)').append('Price: ' + _self.selectedRoom.roomType.price + " &euro;");

            if ($('.r'+_self.selectedRoom.roomNumber).hasClass('chosenRoom')) {
                $('#myModal .modal-footer .chooseButton').html("Discard");
            } else {
                $('#myModal .modal-footer .chooseButton').html("Choose");
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
                $('.r'+_self.selectedRoom.roomNumber).html("");
            } else {

                var chosenRoom = {
                    room: _self.selectedRoom
                };
                ReservationsService
                    .saveChosenRoom(chosenRoom);

                $('.r'+_self.selectedRoom.roomNumber).addClass('chosenRoom');
                $('.r'+_self.selectedRoom.roomNumber).html("X");
                // $('.r'+_self.selectedRoom.roomNumber).removeClass('type'+_self.selectedRoom.roomType.roomName.substring(0,2).toUpperCase());
                _self.selectedRooms.push(_self.selectedRoom);
            }
            $('#myModal').modal('toggle');
        }
        
        function submitRequest() {

            _self.closeInterval();
            _self.loading = true;

            if (!guestAlreadyExist) {
                ReservationsService
                    .saveGuest(_self.guest)
                    .then(function () {

                        ReservationsService
                            .searchByEmail(_self.guest.guestEmail)
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

                                _self.request.user = $rootScope.user;

                                _self.request.rooms = _self.selectedRooms;
                                _self.request.lateCheckout = !!_self.lateCheckout;
                                _self.request.lunch = !!_self.lunch;
                                _self.request.dinner = !!_self.dinner;
                                ReservationsService
                                    .saveRequest(_self.request)
                                    .then(function () {
                                        _self.loading = false;

                                        _self.removeChosenRooms();
                                        $('#successModal').modal();
                                    })
                            });
                    });
            } else {
                ReservationsService
                    .searchByEmail(_self.guest.guestEmail)
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

                        MaidService
                            .getCurrentUser()
                            .then(function (currentUser) {
                                if(currentUser.data) {
                                    _self.request.user = currentUser.data;
                                } else {
                                    _self.request.user = null;
                                }

                                _self.request.rooms = _self.selectedRooms;
                                _self.request.lateCheckout = !!_self.lateCheckout;
                                _self.request.lunch = !!_self.lunch;
                                _self.request.dinner = !!_self.dinner;
                                ReservationsService
                                    .saveRequest(_self.request)
                                    .then(function () {
                                        _self.loading = false;

                                        _self.removeChosenRooms();
                                        $('#successModal').modal();
                                    })
                            });
                    });
            }
        }

        _self.downloadInvoice = function () {
            ReservationsService
                .getPdf(_self.request)
                .then(function (response) {
                    var fileName = "test.pdf";
                    var a = document.createElement("a");
                    document.body.appendChild(a);

                    var file = new Blob([response.data], {type: 'application/pdf'});
                    var fileURL = window.URL.createObjectURL(file);
                    a.href = fileURL;
                    a.download = fileName;
                    a.click();
                })
        };

        _self.discardRes = function () {
            _self.removeChosenRooms();
            if ($rootScope.user) {
                if($rootScope.user.userRole === 'MANAGER')
                    $state.go('viewReservations');
                else if ($rootScope.user.userRole === 'RECEPTIONIST')
                    $state.go('viewReservations');
            } else
                $state.go('homeCommon');
        };

        _self.removeChosenRooms = function () {
            _self.selectedRooms.forEach(function (value2) {
                ReservationsService
                    .removeChosenRoom(value2);
            });

            $('.menu-div a button').prop('disabled', false);
            $('.menu-div a button').css('cursor', 'pointer');
            $('.menu-div a button').css('opacity', '1');
        }

    }

})();