(function () {
    'use strict';

    angular.module('RoyalPalaceHotel', ['ui.router'])
            .config(function($stateProvider, $urlRouterProvider) {
        //
        // $urlRouterProvider.when('/manager', 'home');
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
                templateUrl: 'manager/rooms.html'
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
