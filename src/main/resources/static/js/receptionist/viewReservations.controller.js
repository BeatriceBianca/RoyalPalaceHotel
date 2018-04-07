(function() {

    'use strict';

    angular
        .module('RoyalPalaceHotel')
        .controller('viewReservationsController', Controller);

    Controller.$inject = ['$scope', '$state', 'ReservationsService'];

    function Controller($scope, $state, ReservationsService) {
        var _self = this;

        _self.loading = true;

        _self.currentState = 'viewReservations';

        function init() {

            $('.menu-div a button').prop('disabled', false);
            $('.menu-div a button').css('cursor', 'pointer');
            $('.menu-div a button').css('opacity', '1');

            ReservationsService
                .getAllReservations()
                .then(function (response) {

                    var datasets = [];
                    response.data.forEach(function (r) {
                        var options = { year: 'numeric', month: 'long', day: 'numeric' };
                        var arr = [];
                        arr.push(r.customer.lastName + " " + r.customer.firstName);
                        arr.push((new Date(r.arrivalDate)).toLocaleDateString('en-EN', options));
                        arr.push((new Date(r.departureDate)).toLocaleDateString('en-EN', options));
                        var rms = "";
                        r.rooms.forEach(function (rm) {
                            rms += rm.roomNumber + "&nbsp;&nbsp;&nbsp;";
                        });
                        arr.push(rms);

                        datasets.push(arr);
                    });
                    $('#allReservationsTable').DataTable( {
                        pageLength: 5,
                        bLengthChange: false,
                        data: datasets,
                        columns: [
                            { title: "Name" },
                            { title: "ArrivalDate" },
                            { title: "DepartureDate"},
                            { title: "Rooms." }
                        ]
                    } );
                    _self.loading = false;
                });
            if ($state.current.name !== 'viewReservations') {
                _self.currentState = $state.current.name;
            }

            $('.menu-div a button').removeClass('active');
            $('#'+_self.currentState).addClass('active');
        }
        init();
    }

})();