(function() {

    'use strict';

    angular
        .module('RoyalPalaceHotel')
        .controller('managerController', Controller);

    Controller.$inject = ['$rootScope', '$scope', '$window', '$state', '$filter', 'ManagerService', 'MaidService', 'ReservationsService'];

    function Controller($rootScope, $scope, $window, $state, $filter, ManagerService, MaidService, ReservationsService) {
        var _self = this;

        _self.username = "";
        _self.currentUserPassword = "";
        _self.currentState = 'homeManager';

        _self.newOffer = null;

        $rootScope.user = null;

        _self.loading = false;

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

        _self.getReports = function () {
            _self.loading = true;

            ReservationsService
                .getAllReservations()
                .then(function (reservations) {
                    _self.reservations = reservations;

                    _self.changeChart('rooms', "0");
                });
        };

        _self.changeChart = function(type, month) {

            if (!_self.loading) {
                _self.loading = true;
            }

            $('#addChart p').css('display', 'none');

            $('#myChart').remove();
            $('#addChart').append('<canvas id="myChart"></canvas>');

            if (!type) {
                type = _self.chartOption;
                month = _self.monthOption;
            }

            var ctx = document.getElementById("myChart");

            if (type === 'rooms') {
                var roomsType = {
                    Single: 0,
                    Double: 0,
                    Triple: 0,
                    Twin: 0,
                    Apartment: 0
                };

                var tot = 0;
                _self.reservations.data.forEach(function (reservation) {

                    if ((month !== "0" && (((new Date(reservation.arrivalDate)).getMonth() + 1).toString() === month ||
                            ((new Date(reservation.departureDate)).getMonth() + 1).toString() === month)) || month === "0") {
                        reservation.rooms.forEach(function (room) {
                            var roomName = room.roomType.roomName;

                            if (roomName.includes('Apartment')) {
                                roomsType.Apartment++;
                                tot++;
                            } else {
                                roomsType[roomName]++;
                                tot++;
                            }
                        });
                    }
                });

                if (tot) {

                    $('#addChart p').css('display', 'none');
                    var data = {
                        datasets: [{
                            backgroundColor: ['#7165b2', '#8c9de2', '#bea4d2', '#bdccff', '#f0e2f8'],
                            data: [roomsType.Single, roomsType.Double, roomsType.Triple, roomsType.Twin, roomsType.Apartment]
                        }],

                        // These labels appear in the legend and in the tooltips when hovering different arcs
                        labels: [
                            'Single',
                            'Double',
                            'Triple',
                            'Twin',
                            'Apartment'
                        ]
                    };
                } else {
                    data = null;

                    $('#addChart p').css('display', 'block');
                }

                var myChart = new Chart(ctx, {
                    type: 'doughnut',
                    data: data
                });

            } else if (type === 'resByMonth') {
                if (month === "0") {
                    var reservationsByMonth = [];
                    for (var i = 1; i <= 12; i++) {
                        reservationsByMonth[i] = 0;
                    }

                    _self.reservations.data.forEach(function (reservation) {
                        reservationsByMonth[((new Date(reservation.arrivalDate)).getMonth() + 1)]++;

                        if (((new Date(reservation.arrivalDate)).getMonth() + 1) !==
                            ((new Date(reservation.departureDate)).getMonth() + 1)) {
                            reservationsByMonth[((new Date(reservation.departureDate)).getMonth() + 1)]++;
                        }

                        $('#addChart p').css('display', 'none');
                        var data = {
                            datasets: [{
                                backgroundColor: ['#36213E', '#7165b2', '#734B5E', '#8c9de2', '#bea4d2', '#9E7682',
                                    '#4D4861', '#bdccff', '#BAABBD', '#f0e2f8', '#554971', '#B486AB'],
                                data: reservationsByMonth
                            }],

                            // These labels appear in the legend and in the tooltips when hovering different arcs
                            labels: [
                                'January',
                                'February',
                                'March',
                                'April',
                                'May',
                                'June',
                                'July',
                                'August',
                                'September',
                                'October',
                                'November',
                                'December'
                            ]
                        };

                        var myChart = new Chart(ctx, {
                            type: 'bar',
                            data: data,
                            options: {
                                legend: { display: false },
                                title: {
                                    display: false
                                },
                                scales: {
                                    yAxes: [{
                                        ticks: {
                                            fontColor: "black",
                                            fontSize: 16
                                        }
                                    }],
                                    xAxes: [{
                                        ticks: {
                                            fontColor: "black",
                                            fontSize: 16
                                        }
                                    }]
                                }
                            }
                        });
                    });
                } else {
                    console.log("else");
                }
            }

            $('#myChart').css('background-color', 'rgba(256, 256, 256, 0.6)');
            _self.loading = false;
        }
    }

})();