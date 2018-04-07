(function() {

    'use strict';

    angular
        .module('RoyalPalaceHotel')
        .controller('receptionistController', Controller);

    Controller.$inject = ['$rootScope', '$scope', '$state', '$window', '$filter', 'ReceptionistService', 'MaidService'];

    function Controller($rootScope, $scope, $state, $window, $filter, ReceptionistService, MaidService) {
        var _self = this;

        _self.username = "";
        _self.currentUserPassword = "";
        _self.currentState = 'homeReceptionist';

        _self.loading = true;

        $rootScope.user = null;

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

            ReceptionistService
                .getAllGuests()
                .then(function (response) {

                    var datasets = [];
                    response.data.forEach(function (r) {
                        var options = { year: 'numeric', month: 'long', day: 'numeric' };
                        var arr = [];
                        arr.push(r.lastName + " " + r.firstName);
                        arr.push(r.guestEmail);
                        arr.push(r.guestPhone);
                        arr.push(r.address);
                        if (r.registerDate)
                            arr.push((new Date(r.registerDate)).toLocaleDateString('en-EN', options));
                        else
                            arr.push("");

                        datasets.push(arr);
                    });
                    $('#allReservationsTable').DataTable( {
                        pageLength: 5,
                        bLengthChange: false,
                        data: datasets,
                        columns: [
                            { title: "Name" },
                            { title: "Email" },
                            { title: "Phone"},
                            { title: "Address"},
                            { title: "Register Date" }
                        ]
                    } );
                    _self.loading = false;
                });

            if ($state.current.name !== 'default' && $state.current.name !== '') {
                _self.currentState = $state.current.name;
            } else {
                $state.go(_self.currentState);
            }

            $('.menu-div a button').removeClass('active');
            $('#'+_self.currentState).addClass('active');
        }

        init();

    }
})();