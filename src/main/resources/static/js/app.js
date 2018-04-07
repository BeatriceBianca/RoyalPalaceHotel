(function () {
    'use strict';

    angular.module('RoyalPalaceHotel', ['ui.router'])
            .config(function($stateProvider, $locationProvider) {

        $stateProvider
            //COMMON
            .state('contact', {
                // url: '/contact',
                templateUrl: 'common/contact.html'
            })
            .state('homeCommon', {
                // url: '/',
                templateUrl: 'common/home.html'
            })
            .state('offers', {
                // url: '/offers',
                templateUrl: 'common/offers.html'
            })
            .state('rooms', {
                // url: '/rooms',
                templateUrl: 'common/rooms.html'
                // controller: 'managerController as managerCtrl'
            })
            .state('newReservation', {
                // url: '/guest/newReservation',
                templateUrl: 'common/newReservation.html'
                // controller: 'managerController as managerCtrl'
            })


            //MAID
            .state('homeMaid', {
                // url: '/maid/home',
                templateUrl: 'maid/homeMaid.html'
            })
            .state('changePassword', {
                // url: '/maid/changePassword',
                templateUrl: 'maid/changePassword.html'
            })
            .state('myProfile', {
                // url: '/maid/myProfile',
                templateUrl: 'maid/myProfile.html'
            })


            //MANAGER
            .state('homeManager', {
                // url: '/manager/home',
                templateUrl: 'manager/home.html'
            })
            .state('newAccount', {
                // url: '/manager/newAccount',
                templateUrl: 'manager/newAccount.html'
            })
            .state('reports', {
                // url: '/manager/reports',
                templateUrl: 'manager/reports.html'
            })


            //RECEPTIONIST
            .state('homeReceptionist', {
                // url: '/receptionist/home',
                templateUrl: 'receptionist/homeReceptionist.html'
            })
            .state('guests', {
                // url: '/receptionist/guests',
                templateUrl: 'receptionist/guests.html'
            })
            .state('viewReservations', {
                // url: '/receptionist/viewReservations',
                templateUrl: 'receptionist/viewReservations.html'
                // controller: 'managerController'
            });

            $locationProvider.html5Mode(true);

    });
})();
