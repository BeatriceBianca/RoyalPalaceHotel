(function() {

    'use strict';

    angular
        .module('RoyalPalaceHotel')
        .factory('ReceptionistService', ['$http', function($http) {

            if (location.protocol === 'http:') {
                var baseTestContext = "http://" + location.host + '/receptionist';
            } else {
                var baseTestContext = "https://" + location.host + '/receptionist';
            }

            function getAllGuests() {
                var URL = baseTestContext + "/getAllGuests";
                return $http({
                    method: 'GET',
                    url: URL,
                    headers: {
                        'Content-Type': 'application/json'
                    }});
            }

            return {
                getAllGuests: getAllGuests
            }

        }]);

})();