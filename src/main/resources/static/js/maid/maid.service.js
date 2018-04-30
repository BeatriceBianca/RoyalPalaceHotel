(function() {

    'use strict';

    angular
        .module('RoyalPalaceHotel')
        .factory('MaidService', ['$http', function($http) {

            if (location.protocol === 'http:') {
                var baseTestContext = "http://" + location.host + '/maid';
            } else {
                var baseTestContext = "https://" + location.host + '/maid';
            }

            function getCurrentUser() {

                if (location.protocol === 'http:') {
                    var URL = "http://" + location.host + "/getCurrentUser";
                } else {
                    var URL = "https://" + location.host + "/getCurrentUser";
                }

                return $http.get(URL, null);
            }

            return {
                getCurrentUser: getCurrentUser
            };

        }]);

})();