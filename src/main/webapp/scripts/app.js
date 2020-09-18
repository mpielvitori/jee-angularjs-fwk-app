

/**
 * @ngdoc overview
 * @name App
 * @description
 * # App
 *
 * Main module of the application.
 */
angular
  .module('App', [
    'ngAria',
    'ngCookies',
    'ngMessages',
    'ngResource',
    'ngRoute',
    'ngSanitize',
    'ui.bootstrap',
  ])
  .config(($routeProvider) => {
    $routeProvider
      .when('/', {
        templateUrl: 'views/main.html',
        controller: 'MainCtrl',
        controllerAs: 'main',
      })
      .when('/about', {
        templateUrl: 'views/about.html',
        controller: 'AboutCtrl',
        controllerAs: 'about',
      })
      .when('/Socios', { templateUrl: 'views/Socio/search.html', controller: 'SearchSocioController' })
      .when('/Socios/new', { templateUrl: 'views/Socio/detail.html', controller: 'NewSocioController' })
      .when('/Socios/edit/:SocioId', { templateUrl: 'views/Socio/detail.html', controller: 'EditSocioController' })
      .otherwise({
        redirectTo: '/',
      });
  });
