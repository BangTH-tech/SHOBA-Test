
var app = angular.module('sampleApp', ['ngRoute', 'ui.bootstrap', 'ui.select', 'ngSanitize', 'ngAnimate', 'angular-carousel', 'ngTouch']);

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

app.config(function ($routeProvider, $locationProvider, $compileProvider) {
  $locationProvider.hashPrefix('');
  $compileProvider.imgSrcSanitizationWhitelist(/^\s*(https?|ftp|file|ms-appx|ms-appx-web|x-wmapp0):|data:image\//);

  $routeProvider
    .when('/home', {
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
    .when('/product-list', {
      templateUrl: 'partials/product-list.html',
      controller: 'ProductListCtrl'
    })
    .when('/404', {
      templateUrl: 'partials/404.html',
      controller: 'P404Ctrl'
    })
    .when('/403', {
      templateUrl: 'partials/403.html',
      controller: '403Ctrl'
    })
    .when('/news-list', {
      templateUrl: 'partials/news-list.html',
      controller: 'NewsListCtrl'
    })
    .when('/detail-news/:id', {
      templateUrl: 'partials/detail-news.html',
      controller: 'DetailNewsCtrl'
    })
    .when('/detail-product/:id', {
      templateUrl: 'partials/detail-product.html',
      controller: 'DetailProductCtrl'
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
