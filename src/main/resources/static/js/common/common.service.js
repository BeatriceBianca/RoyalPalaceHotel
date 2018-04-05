(function() {

    'use strict';

    angular
        .module('RoyalPalaceHotel')
        .factory('CommonService', ['$http', '$location', function($http, $location) {

            var baseTestContext = "http://" + location.host + '/common';

            // function getCurrentUser() {
            //
            //     var URL = baseTestContext + "/getCurrentUser";
            //     return $http.get(URL, null);
            // }
            //
            return {
                // getCurrentUser: getCurrentUser
            };

        }]);

})();