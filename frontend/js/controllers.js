app.controller('HomeCtrl', function ($scope) {
    $scope.message = "Welcome to the Home Page!";
});

app.controller('UsersCtrl', function ($scope) {
    $scope.users = [
        { id: 1, name: "Alice" },
        { id: 2, name: "Bob" },
        { id: 3, name: "Charlie" }
    ];
});

app.controller('LoginCtrl', function ($scope, $http) {
    waitForGrecaptchaAndRender('recaptcha-container', '6LeGjD8rAAAAAL-j6zh2KwdnaZHmpdt0COBRXcYO');
    $scope.error = false;
    $scope.errorMessage = '';


    $scope.formData = {}
    $scope.submitForm = function () {
        var recaptchaResponse = grecaptcha.getResponse();

        if (recaptchaResponse.length === 0) {
            alert("Please verify reCAPTCHA.");
            return;
        }

        var dataToSend = angular.copy($scope.formData);
        dataToSend.recaptchaToken = recaptchaResponse;
        $http.post('http://localhost:8080/api/v1/auth/login', dataToSend,
            {
                withCredentials: true
            }
        )
            .then(function (response) {
                $scope.error = false;
            })
            .catch(function (error) {
                $scope.error = true;
                $scope.errorMessage = error.data.message;
            });
    }
});

app.controller('RegisterCtrl', function ($scope, $http, $location) {
    waitForGrecaptchaAndRender('recaptcha-container-register', '6LeGjD8rAAAAAL-j6zh2KwdnaZHmpdt0COBRXcYO');
    $scope.error = false;
    $scope.errorMessage = '';

    $scope.formData = {}
    $scope.submitForm = function () {
        var recaptchaResponse = grecaptcha.getResponse();

        if (recaptchaResponse.length === 0) {
            toastr.error("Please verify reCAPTCHA.");
            return;
        }

        var dataToSend = angular.copy($scope.formData);
        dataToSend.recaptchaToken = recaptchaResponse;
        $http.post('http://localhost:8080/api/v1/auth/register', dataToSend,
            {
                withCredentials: true
            }
        )
            .then(function (response) {
                toastr.success("Register successfully! Now you can login to our system");
                $location.path('/login');
                $scope.error = false;
                
            })
            .catch(function (error) {
                console.log(error);
      
                toastr.error(error.data.message);
            });
    }
});

