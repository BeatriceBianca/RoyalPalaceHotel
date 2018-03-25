(function() {

    'use strict';

    angular
        .module('RoyalPalaceHotel')
        .factory('ReservationsService', ['$http', '$location', function($http, $location) {

            var baseTestContext = "http://" + location.host + '/receptionist';

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

            function getAllChosenRooms() {
                var URL = baseTestContext + "/getAllChosenRooms";
                return $http({
                    method: 'GET',
                    url: URL,
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
            
            function saveChosenRoom(room) {
                var URL = baseTestContext + "/saveChosenRoom";
                return $http({
                    method: 'POST',
                    url: URL,
                    data: room,
                    headers: {
                        'Content-Type': 'application/json'
                    }});
            }

            function removeChosenRoom(room) {
                var URL = baseTestContext + "/removeChosenRoom";
                return $http({
                    method: 'POST',
                    url: URL,
                    data: room,
                    headers: {
                        'Content-Type': 'application/json'
                    }});
            }

            function getAllReservations() {
                var URL = baseTestContext + "/getAllReservations";
                return $http({
                    method: 'GET',
                    url: URL,
                    headers: {
                        'Content-Type': 'application/json'
                    }});
            }

            return {
                searchByCnp: searchByCnp,
                getAllReservationsBetweenDates: getAllReservationsBetweenDates,
                getAllChosenRooms: getAllChosenRooms,
                saveGuest: saveGuest,
                saveRequest: saveRequest,
                saveChosenRoom: saveChosenRoom,
                removeChosenRoom: removeChosenRoom,
                getAllReservations: getAllReservations
            };

        }]);

})();