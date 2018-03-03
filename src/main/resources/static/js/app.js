(function () {
    'use strict';

    angular.module('RoyalPalaceHotel', ['ui.router'])
            .config(function($stateProvider, $urlRouterProvider) {

        $stateProvider

            .state('home', {
                templateUrl: 'manager/home.html'
            })

            .state('newAccount', {
                templateUrl: 'manager/newAccount.html'
            })

            .state('guests', {
                templateUrl: 'manager/guests.html'
            })

            .state('reports', {
                templateUrl: 'manager/reports.html'
            })

            .state('rooms', {
                templateUrl: 'manager/rooms.html',
                controller: 'managerController'
            })

            .state('myProfile', {
                templateUrl: 'manager/myProfile.html'
            })

            .state('changePassword', {
                templateUrl: 'manager/changePassword.html'
            })

            .state('default', {
                url: '',
                templateUrl : 'manager/home.html'
            })

    });
})();
