var app = angular.module('sampleApp', ['ngRoute', 'ui.bootstrap', 'ui.select', 'ngSanitize']);
function waitForGrecaptchaAndRender(containerId, siteKey) {
  var checkInterval = setInterval(function () {
    if (window.grecaptcha && window.grecaptcha.render) {
      clearInterval(checkInterval);
      grecaptcha.render(containerId, {
        sitekey: siteKey
      });
    }
  }, 100);
}
waitForGrecaptchaAndRender('recaptcha-container', '6LeGjD8rAAAAAL-j6zh2KwdnaZHmpdt0COBRXcYO');

app.config(function($routeProvider, $locationProvider) {
    $locationProvider.hashPrefix('');
    $routeProvider
        .when('/', {
            templateUrl: 'partials/home.html',
            controller: 'HomeCtrl'
        })
        .when('/users', {
            templateUrl: 'partials/users.html',
            controller: 'UsersCtrl'
        })
        .when('/login', {
            templateUrl: 'partials/login.html',
            controller: 'LoginCtrl'
        })
        .when('/register', {
            templateUrl: 'partials/register.html',
            controller: 'RegisterCtrl'
        })
        .when('/employee-list', {
          templateUrl: 'partials/employee-list.html',
            controller: 'EmployeeListCtrl'
        })
        .when('/news-list', {
          templateUrl: 'partials/news-list.html',
            controller: 'NewsListCtrl'
        })
        .when('/category-list', {
          templateUrl: 'partials/category-list.html',
            controller: 'CategoryListCtrl'
        })
        .when('/log-list', {
          templateUrl: 'partials/log-list.html',
            controller: 'LogListCtrl'
        })
        .otherwise({
            redirectTo: '/'
        });
});
