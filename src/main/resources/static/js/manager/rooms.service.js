(function() {

    'use strict';

    angular
        .module('RoyalPalaceHotel')
        .factory('RoomsService', ['$http', function($http) {

            var baseTestContext = 'http://localhost:8090/manager';

            function getAllRooms() {

                var URL = baseTestContext + "/getAllRooms";
                return $http.get(URL, null);
            }

            function getAllRoomTypes() {

                var URL = baseTestContext + "/getAllRoomTypes";
                return $http.get(URL, null);
            }
            
            function editRoomType(room) {
                var URL = baseTestContext + "/editRoomType";
                return $http({
                    method: 'POST',
                    url: URL,
                    data: room,
                    headers: {
                        'Content-Type': 'application/json'
                    }});
            }

            function editPrice(roomType) {
                var URL = baseTestContext + "/editPrice";
                return $http({
                    method: 'POST',
                    url: URL,
                    data: roomType,
                    headers: {
                        'Content-Type': 'application/json'
                    }});
            }

            return {
                getAllRooms: getAllRooms,
                getAllRoomTypes: getAllRoomTypes,
                editRoomType: editRoomType,
                editPrice: editPrice
            };

        }]);

})();