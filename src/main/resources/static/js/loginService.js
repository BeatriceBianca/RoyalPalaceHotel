LoginApp.service('LoginService',
    function($http) {

    var LoginValidate = function(UserData) {
        var httpConfig = {
            transformResponse : function(data) {
                return data;
            }
        };
        var url = "login";
        $http.post(url, UserData, httpConfig)
            .then(function (response) {

                if (response.data === "User not found") {
                    $('#errorContainer').css('display','inline');
                    $('#errorContainer span').text("Email not found! Please ask the manager to register you.")
                                             .css({'display':'inline', 'top': '27%'});
                    $('#errorCircle1').css('display','inline');
                    $('#errorCircle2').css('display','inline');
                } else if (response.data === "Incorrect password") {
                    $('#errorContainer').css('display','inline');
                    $('#errorContainer span').text("Incorrect password! Please try again.")
                                             .css({'display':'inline', 'top': '31%'});
                    $('#errorCircle1').css('display','inline');
                    $('#errorCircle2').css('display','inline');
                } else {
                    window.location.href += response.data;
                }
            });
    };

    return {
        LoginValidate: LoginValidate
    };
});