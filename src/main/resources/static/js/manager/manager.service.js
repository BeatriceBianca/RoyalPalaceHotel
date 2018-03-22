(function() {

    'use strict';

    angular
        .module('RoyalPalaceHotel')
        .factory('ManagerService', ['$http', '$location', function($http, $location) {

            var baseTestContext = "http://" + location.host + '/manager';

            function getCurrentUser() {

                var URL = baseTestContext + "/getCurrentUser";
                return $http.get(URL, null);
            }

            return {
                getCurrentUser: getCurrentUser
            };

        }]);

})();