LoginApp.controller('LoginController', ['$scope', 'LoginService',
    function($scope, LoginService) {

        $('#errorContainer').css('display','none');
        $('#errorCircle1').css('display','none');
        $('#errorCircle2').css('display','none');
        $('#errorContainer span').css('display','none')
                                 .text("");
        // $scope.loginFct = function() {
        //     var UserData = {
        //         email: $scope.email,
        //         password: $scope.password
        //     };
        //     LoginService.LoginValidate(UserData);
        // }
}]);