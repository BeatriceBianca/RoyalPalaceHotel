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

            return {
                getAllRooms: getAllRooms
            };

        }]);

})();