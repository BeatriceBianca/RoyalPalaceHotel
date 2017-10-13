angular.module('RoyalPalaceHotel').service('LoginService',
    function($http, $location) {

    this.LoginValidate = function(UserData) {
        var httpConfig = {
            transformResponse : function(data) {
                return data;
            }
        };
        var url = "login";
        $http.post(url, UserData, httpConfig).then(function (response) {
            window.location.href += response.data;
            console.log(window.location.href);
            });
    }
});