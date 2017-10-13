LoginApp.controller('LoginController', ['$scope', 'LoginService',
    function($scope, LoginService) {

    $scope.loginFct = function() {
        var UserData = {
            email: $scope.email,
            password: $scope.password
        };
        LoginService.LoginValidate(UserData);
    }
}]);