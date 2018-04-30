(function() {

    'use strict';

    angular
        .module('RoyalPalaceHotel')
        .factory('LoginService', ['$http', '$location', function($http, $location) {

            if (location.protocol === 'http:') {
                var baseTestContext = "http://" + location.host;
            } else {
                var baseTestContext = "https://" + location.host;
            }

            return {

            };

        }]);

})();