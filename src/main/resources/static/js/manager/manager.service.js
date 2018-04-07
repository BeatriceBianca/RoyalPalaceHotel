(function() {

    'use strict';

    angular
        .module('RoyalPalaceHotel')
        .factory('ManagerService', ['$http', function($http) {

            var baseTestContext = "http://" + location.host + '/manager';

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
                editRoomType: editRoomType,
                editPrice: editPrice
            };

        }]);

})();