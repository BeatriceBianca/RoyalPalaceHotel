(function() {

    'use strict';

    angular
        .module('RoyalPalaceHotel')
        .factory('CommonService', ['$http', function($http) {

            var baseTestContext = "http://" + location.host + '/common';

            function getAllRooms() {

                var URL = baseTestContext + "/getAllRooms";
                return $http.get(URL, null);
            }

            function getAllRoomTypes() {

                var URL = baseTestContext + "/getAllRoomTypes";
                return $http.get(URL, null);
            }

            return {
                getAllRooms: getAllRooms,
                getAllRoomTypes: getAllRoomTypes
            };

        }]);

})();