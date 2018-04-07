(function() {

    'use strict';

    angular
        .module('RoyalPalaceHotel')
        .factory('MaidService', ['$http', function($http) {

            var baseTestContext = "http://" + location.host + '/maid';

            function getCurrentUser() {

                var URL = "http://" + location.host + "/getCurrentUser";
                return $http.get(URL, null);
            }

            return {
                getCurrentUser: getCurrentUser
            };

        }]);

})();