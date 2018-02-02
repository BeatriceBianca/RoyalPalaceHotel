(function() {

    'use strict';

    angular
        .module('RoyalPalaceHotel')
        .controller('managerController', Controller);

    Controller.$inject = ['$scope', 'ManagerService', '$window'];

    function Controller($scope, ManagerService, $window) {
        var _self = this;


        _self.getCurrentContent = getCurrentContent;
        
        _self.username = "";
        _self.currentPage = "home";
        _self.dataLoading = false;
        _self.user = [];

        var baseTestContext = 'http://localhost:8090/manager';

        function getCurrentContent() {

            return "manager/" + _self.currentPage + ".html";
        }
        
        function init() {
            ManagerService
                .getCurrentUser()
                .then(function (response) {
                    _self.username = response.data.firstName;
                });

            $('#datetimepicker1').datetimepicker({
                format: 'DD/MM/YYYY'
            });
            $('#datetimepicker2').datetimepicker({
                format: 'DD/MM/YYYY'
            });
        }

        init();
    }

})();