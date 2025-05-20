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

app.controller('LoginCtrl', function ($scope, $http, $location) {
    waitForGrecaptchaAndRender('recaptcha-container', '6LeGjD8rAAAAAL-j6zh2KwdnaZHmpdt0COBRXcYO');
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
        $http.post('http://localhost:8080/api/v1/auth/login', dataToSend,
            {
                withCredentials: true
            }
        )
            .then(function (response) {
                $scope.error = false;
                $location.path('/employee-list');
            })
            .catch(function (error) {
                toastr.error(error.data.message);
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

app.controller('EmployeeListCtrl', function ($scope, $http, $location) {
    $scope.emps = [];
    $scope.currentPage = 0;
    $scope.totalPages = 0;
    $scope.totalElements = 0;
    $scope.numberOfElements = 0;
    $scope.filter = {
        page: 0,
        size: 5,
        sortBy: '',
        ascending: true,
        search: '',
        role: '',
        status: ''
    }
    $scope.fetchEmpsByPage = function () {
        console.log($scope.filter)
        if ($scope.filter.role == '') {
            $scope.filter.role = null;
        }
        if ($scope.filter.status == '') {
            $scope.filter.status = null;
        }
        $http.post('http://localhost:8080/api/v1/admin/employee-list', $scope.filter, {
            withCredentials: true
        })
            .then(function (response) {
                console.log(response)
                if (response.data != '') {
                    $scope.emps = response.data;
                    $scope.currentPage = response.data.number + 1;
                    $scope.totalPages = response.data.totalPages;
                    $scope.totalElements = response.data.totalElements;
                    $scope.numberOfElements = response.data.numberOfElements;
                }
                else {
                    $scope.totalElements = 0;
                    $scope.numberOfElements = 0;
                }
            })
            .catch(function (error) {
                console.error("Lỗi khi lấy dữ liệu:", error);
                if (error.status == 401) {
                    toastr.error("Session expired! Please login again");
                    $location.path('/login');
                }
            });
    }
    $scope.fetchEmpsByPage();
    $scope.getPageRange = function () {
        var total = $scope.emps.totalPages;
        var current = $scope.currentPage;
        var delta = 2;
        var range = [];
        var left = current - delta;
        var right = current + delta;

        for (var i = 1; i <= total; i++) {
            if (i == 1 || i == total || (i >= left && i <= right)) {
                range.push(i);
            }
        }

        // Thêm dấu "..." khi cần
        var pagination = [];
        var last;

        range.forEach(function (i) {
            if (last) {
                if (i - last === 2) {
                    pagination.push(last + 1);
                } else if (i - last > 2) {
                    pagination.push('...');
                }
            }
            pagination.push(i);
            last = i;
        });

        return pagination;
    };

    $scope.goToPage = function (page) {
        if (page === '...') return;
        console.log(page);
        $scope.currentPage = page;
        $scope.filter.page = page - 1;
        // Gọi API để lấy dữ liệu trang mới nếu cần
        $scope.fetchEmpsByPage();
    };

    $scope.sortBy = function (column) {
        if ($scope.filter.sortBy == column) {
            $scope.filter.ascending = !$scope.filter.ascending;
        }
        else {
            $scope.filter.sortBy = column;
            $scope.filter.ascending = true;
        }
        $scope.fetchEmpsByPage();
    };

    $scope.changeStatus = function (emp) {
        if (emp.status == 'ACTIVE') {
            emp.status = 'INACTIVE';
        }
        else {
            emp.status = 'ACTIVE'
        }
        $http.put('http://localhost:8080/api/v1/admin/change-status/' + emp.id, {}, {
            withCredentials: true
        })
            .then(function (response) {
                toastr.success("Change employee status successfully")
            })
            .catch(function (error) {
                console.error("Lỗi khi lấy dữ liệu:", error);
                if (error.status == 401) {
                    toastr.error("Session expired! Please login again");
                    $location.path('/login');
                }
            });
    };
});
