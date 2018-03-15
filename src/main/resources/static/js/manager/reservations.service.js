(function() {

    'use strict';

    angular
        .module('RoyalPalaceHotel')
        .factory('ReservationsService', ['$http', function($http) {

            var baseTestContext = 'http://localhost:8090/receptionist';

            function searchByCnp(cnp) {
                var URL = baseTestContext + "/findByCnp";
                return $http({
                    method: 'GET',
                    url: URL,
                    params: {cnp: cnp},
                    headers: {
                        'Content-Type': 'application/json'
                    }});
            }
            
            function getAllReservationsBetweenDates(arrivalDate, departureDate) {
                var URL = baseTestContext + "/getReservationsBetweenDates";
                return $http({
                    method: 'GET',
                    url: URL,
                    params: {arrivalDate: arrivalDate,
                             departureDate: departureDate},
                    headers: {
                        'Content-Type': 'application/json'
                    }});
            }

            function saveGuest(guest) {
                var URL = baseTestContext + "/saveGuest";
                return $http({
                    method: 'POST',
                    url: URL,
                    data: guest,
                    headers: {
                        'Content-Type': 'application/json'
                    }});
            }

            function saveRequest(request) {
                var URL = baseTestContext + "/saveRequest";
                return $http({
                    method: 'POST',
                    url: URL,
                    data: request,
                    headers: {
                        'Content-Type': 'application/json'
                    }});
            }

            function saveRooms(room, request) {
                var URL = baseTestContext + "/saveRoom";
                return $http({
                    method: 'POST',
                    url: URL,
                    params: {
                        room: room,
                        currentRequest: request
                    },
                    headers: {
                        'Content-Type': 'application/json'
                    }});
            }

            return {
                searchByCnp: searchByCnp,
                getAllReservationsBetweenDates: getAllReservationsBetweenDates,
                saveGuest: saveGuest,
                saveRequest: saveRequest,
                saveRooms: saveRooms
            };

        }]);

})();