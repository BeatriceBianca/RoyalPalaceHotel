(function() {

    'use strict';

    angular
        .module('RoyalPalaceHotel')
        .factory('ManagerService', ['$http', function($http) {

            var baseTestContext = 'http://localhost:8090/manager';

            function getCurrentUser() {

                var URL = baseTestContext + "/getCurrentUser";
                return $http.get(URL, null);
            }

            return {
                getCurrentUser: getCurrentUser
            };

        }]);

})();