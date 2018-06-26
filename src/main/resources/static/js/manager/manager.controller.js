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

            if($window.location.href.includes('sendContactEmail')) {
                $window.location.href = '/manager';
            }

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
            var ctx2D = ctx.getContext('2d');

            if (type === 'rooms') {

                $('#monthSelection').css('background-color', 'white');
                $('#monthSelection').prop('disabled', false);

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

                _self.monthOption = "0";
                month = "0";
                $('#monthSelection').css('background-color', 'lightgrey');
                $('#monthSelection').prop('disabled', 'disabled');

                if (month === "0") {
                    var reservationsByMonth = [];
                    for (var i = 0; i <= 11; i++) {
                        reservationsByMonth[i] = 0;
                    }

                    _self.reservations.data.forEach(function (reservation) {
                        reservationsByMonth[((new Date(reservation.arrivalDate)).getMonth())]++;

                        if (((new Date(reservation.arrivalDate)).getMonth()) !==
                            ((new Date(reservation.departureDate)).getMonth())) {
                            reservationsByMonth[((new Date(reservation.departureDate)).getMonth())]++;
                        }
                    });

                    var gradientStroke = ctx2D.createLinearGradient(0, 0, 700, 0);
                    gradientStroke.addColorStop(0, "#f0e2f8");
                    gradientStroke.addColorStop(0.25, "#e2d0ef");
                    gradientStroke.addColorStop(0.5, "#bea4d2");
                    gradientStroke.addColorStop(0.75, "#9f7abc");
                    gradientStroke.addColorStop(1, "#2e0f2b");

                    $('#addChart p').css('display', 'none');
                    data = {
                        datasets: [{
                            backgroundColor: gradientStroke,
                            hoverBackgroundColor: gradientStroke,
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

                    myChart = new Chart(ctx, {
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

                }
                // else {
                //     var monthlyReservations = [];
                //     var daysLabel = [];
                //
                //     for (i = 0; i <= (new Date((new Date()).getFullYear(), parseInt(month) + 1, 0)).getDate() - 1; i++) {
                //         monthlyReservations[i] = 0;
                //         daysLabel[i] = i+1;
                //     }
                //
                //     _self.reservations.data.forEach(function (reservation) {
                //
                //         if (((new Date(reservation.arrivalDate)).getMonth()).toString() === month ||
                //                 ((new Date(reservation.departureDate)).getMonth()).toString() === month) {
                //
                //             monthlyReservations[((new Date(reservation.arrivalDate)).getMonth())]++;
                //
                //             if (((new Date(reservation.arrivalDate)).getMonth()) !==
                //                 ((new Date(reservation.departureDate)).getMonth())) {
                //                 monthlyReservations[((new Date(reservation.departureDate)).getMonth())]++;
                //             }
                //
                //         }
                //     });
                //
                //     gradientStroke = ctx2D.createLinearGradient(0, 0, 700, 0);
                //     gradientStroke.addColorStop(0, "#f0e2f8");
                //     gradientStroke.addColorStop(0.25, "#e2d0ef");
                //     gradientStroke.addColorStop(0.5, "#bea4d2");
                //     gradientStroke.addColorStop(0.75, "#9f7abc");
                //     gradientStroke.addColorStop(1, "#2e0f2b");
                //
                //     $('#addChart p').css('display', 'none');
                //     data = {
                //         datasets: [{
                //             backgroundColor: gradientStroke,
                //             hoverBackgroundColor: gradientStroke,
                //             data: monthlyReservations
                //         }],
                //
                //         // These labels appear in the legend and in the tooltips when hovering different arcs
                //         labels: daysLabel
                //     };
                //
                //     myChart = new Chart(ctx, {
                //         type: 'bar',
                //         data: data,
                //         options: {
                //             legend: { display: false },
                //             title: {
                //                 display: false
                //             },
                //             scales: {
                //                 yAxes: [{
                //                     ticks: {
                //                         fontColor: "black",
                //                         fontSize: 16
                //                     }
                //                 }],
                //                 xAxes: [{
                //                     ticks: {
                //                         fontColor: "black",
                //                         fontSize: 16
                //                     }
                //                 }]
                //             }
                //         }
                //     });
                // }
            }

            $('#myChart').css('background-color', 'rgba(256, 256, 256, 0.6)');
            _self.loading = false;
        }
    }

})();