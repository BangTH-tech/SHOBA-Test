app.controller('HomeCtrl', function ($scope, $http, $location) {
    $scope.news = [];
    $scope.categories = [];
    $scope.currentPage = 0;
    $scope.totalPages = 0;
    $scope.totalElements = 0;
    $scope.numberOfElements = 0;
    $scope.filter = {
        page: 0,
        size: 6,
        sortBy: '',
        ascending: true,
        search: '',
        categoryId: 0
    }
    $scope.fetchCategories = function () {
        $http.get('http://localhost:8080/api/v1/news/category-short-response', {
            withCredentials: true
        })
            .then(function (response) {
                console.log(response)

                $scope.categories.push({
                    id: 0,
                    name: 'All Category'
                })
                $scope.categories = $scope.categories.concat(response.data);
            })
            .catch(function (error) {
                console.error("Lỗi khi lấy dữ liệu:", error);
                if (error.status == 401) {
                    toastr.error("Session expired! Please login again");
                    $location.path('/login');
                }
            });
    }
    $scope.fetchNewsByPage = function () {

        $scope.newsFilter = angular.copy($scope.filter);
        if ($scope.newsFilter.categoryId == 0) {
            $scope.newsFilter.categoryId = null;
        }
        $http.post('http://localhost:8080/api/v1/news/news-list', $scope.newsFilter, {
            withCredentials: true
        })
            .then(function (response) {
                console.log(response)
                if (response.data != '') {
                    $scope.news = response.data;
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
    $scope.fetchCategories();
    $scope.fetchNewsByPage();
    $scope.getPageRange = function () {
        var total = $scope.news.totalPages;
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

    $scope.goToDetail = function (id) {
        $location.path('/detail-news/' + id);
    }

    $scope.goToPage = function (page) {
        if (page === '...') return;
        console.log(page);
        $scope.currentPage = page;
        $scope.filter.page = page - 1;
        // Gọi API để lấy dữ liệu trang mới nếu cần
        $scope.fetchNewsByPage();
    };

    $scope.sortBy = function (column) {
        if ($scope.filter.sortBy == column) {
            $scope.filter.ascending = !$scope.filter.ascending;
        }
        else {
            $scope.filter.sortBy = column;
            $scope.filter.ascending = true;
        }
        $scope.fetchNewsByPage();
    };
});

app.controller('DetailNewsCtrl', function ($scope, $http, $location, $routeParams) {
    $scope.news = {};
    const newsId = $routeParams.id;
    $scope.fetchNewsDetail = function () {
        $http.get('http://localhost:8080/api/v1/news/news-detail/' + newsId,
            {
                withCredentials: true
            }
        )
            .then(function (response) {
                console.log(response);
                $scope.news = response.data;
            })
            .catch(function (error) {
                console.error("Lỗi khi lấy dữ liệu:", error);
                if (error.status == 401) {
                    toastr.error("Session expired! Please login again");
                    $location.path('/login');
                }
            });
    }
    $scope.fetchNewsDetail();

    $scope.goToHome = function () {
        $location.path('/home');
    }
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
                console.log(response);
                $scope.error = false;
                if (response.data.role !== 'GUEST') {
                    $location.path('/employee-list');
                }
                else {
                    $location.path('/home');
                }
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

app.controller('EmployeeListCtrl', function ($scope, $http, $location, $uibModal) {
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
    $scope.formData = {
        username: '',
        fullName: '',
        email: '',
        role: 'EMPLOYEE'
    }

    $scope.editData = {
        username: '',
        fullName: '',
        email: '',
        role: 'EMPLOYEE'
    }

    $scope.openModal = function () {
        var modalInstance = $uibModal.open({
            templateUrl: 'myModal.html',
            windowClass: 'modal-vertical-centered',
            controller: function ($scope, $uibModalInstance, onLoad) {

                $scope.addEmployee = function () {
                    if ($scope.formData.role != null) {
                        $http.post('http://localhost:8080/api/v1/admin/add-user', $scope.formData,
                            {
                                withCredentials: true
                            }
                        )
                            .then(function (response) {
                                toastr.success("Add employee successfully!");
                                $uibModalInstance.close();
                                onLoad();

                            })
                            .catch(function (error) {
                                console.log(error);

                                toastr.error(error.data.message);
                                $uibModalInstance.close();
                            });
                    }
                }

                $scope.cancel = function () {
                    console.log("Người dùng hủy");
                    $uibModalInstance.dismiss('cancel');
                };


            },
            resolve: {
                onLoad: function () {
                    return $scope.fetchEmpsByPage;
                }
            }
        });
    };


    $scope.openModalEdit = function (emp) {
        var modalInstance = $uibModal.open({
            templateUrl: 'editModal.html',
            windowClass: 'modal-vertical-centered',
            controller: function ($scope, $uibModalInstance, editData, onLoad) {
                $scope.editData = angular.copy(editData);
                $scope.getEmployeeDetail = function () {
                    $http.get('http://localhost:8080/api/v1/admin/employee-detail/' + emp.id,
                        {
                            withCredentials: true
                        }
                    )
                        .then(function (response) {
                            editData = response.data;
                            console.log(editData);
                        })
                        .catch(function (error) {
                            console.log(error);

                            toastr.error(error.data.message);
                            $uibModalInstance.close();
                        });

                }
                $scope.getEmployeeDetail();
                $scope.editEmployee = function () {
                    if ($scope.editData.role != null) {
                        $http.put('http://localhost:8080/api/v1/admin/edit-user', $scope.editData,
                            {
                                withCredentials: true
                            }
                        )
                            .then(function (response) {
                                toastr.success("Edit employee successfully!");
                                $uibModalInstance.close();
                                onLoad();

                            })
                            .catch(function (error) {
                                console.log(error);

                                toastr.error(error.data.message);
                                $uibModalInstance.close();
                            });
                    }
                }

                $scope.cancel = function () {
                    console.log("Người dùng hủy");
                    $uibModalInstance.dismiss('cancel');
                };


            },
            resolve: {
                editData: function () {
                    return emp;
                },
                onLoad: function () {
                    return $scope.fetchEmpsByPage;
                }
            }
        });
    };

});

app.controller('NewsListCtrl', function ($scope, $http, $location, $uibModal) {
    $scope.news = [];
    $scope.categories = [];
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
        categoryId: 0
    }

    $scope.fetchCategories = function () {
        $http.get('http://localhost:8080/api/v1/admin/category-short-response', {
            withCredentials: true
        })
            .then(function (response) {
                console.log(response)

                $scope.categories.push({
                    id: 0,
                    name: 'All Category'
                })
                $scope.categories = $scope.categories.concat(response.data);
            })
            .catch(function (error) {
                console.error("Lỗi khi lấy dữ liệu:", error);
                if (error.status == 401) {
                    toastr.error("Session expired! Please login again");
                    $location.path('/login');
                }
            });
    }

    $scope.fetchNewsByPage = function () {

        $scope.newsFilter = angular.copy($scope.filter);
        if ($scope.newsFilter.categoryId == 0) {
            $scope.newsFilter.categoryId = null;
        }
        $http.post('http://localhost:8080/api/v1/admin/news-list', $scope.newsFilter, {
            withCredentials: true
        })
            .then(function (response) {
                console.log(response)
                if (response.data != '') {
                    $scope.news = response.data;
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
    $scope.fetchCategories();
    $scope.fetchNewsByPage();
    $scope.getPageRange = function () {
        var total = $scope.news.totalPages;
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
        $scope.fetchNewsByPage();
    };

    $scope.sortBy = function (column) {
        if ($scope.filter.sortBy == column) {
            $scope.filter.ascending = !$scope.filter.ascending;
        }
        else {
            $scope.filter.sortBy = column;
            $scope.filter.ascending = true;
        }
        $scope.fetchNewsByPage();
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

    $scope.editData = {
        title: '',
        content: '',
        categoryId: 1
    }

    $scope.openModal = function () {
        var modalInstance = $uibModal.open({
            templateUrl: 'myModal.html',
            windowClass: 'modal-vertical-centered',
            controller: function ($scope, $uibModalInstance, onLoad, categories) {
                $scope.formData = {
                    title: '',
                    content: '',
                    categoryId: 1
                }
                $scope.categories = angular.copy(categories);
                $scope.categories.shift();
                $scope.addNews = function () {
                    $http.post('http://localhost:8080/api/v1/admin/add-news', $scope.formData,
                        {
                            withCredentials: true
                        }
                    )
                        .then(function (response) {
                            toastr.success("Add news successfully!");
                            $uibModalInstance.close();
                            onLoad();

                        })
                        .catch(function (error) {
                            console.log(error);

                            toastr.error(error.data.message);
                            $uibModalInstance.close();
                        });
                }


                $scope.cancel = function () {
                    $uibModalInstance.dismiss('cancel');
                };


            },
            resolve: {
                onLoad: function () {
                    return $scope.fetchNewsByPage;
                },
                categories: function () {
                    return $scope.categories;
                }
            }
        });
    };


    $scope.openModalEdit = function (news) {
        var modalInstance = $uibModal.open({
            templateUrl: 'editModal.html',
            windowClass: 'modal-vertical-centered',
            controller: function ($scope, $uibModalInstance, editData, onLoad, categories) {
                $scope.editData = angular.copy(editData);
                $scope.categories = angular.copy(categories);
                $scope.categories.shift();
                $scope.getNewsDetail = function () {
                    $http.get('http://localhost:8080/api/v1/admin/news-detail/' + news.id,
                        {
                            withCredentials: true
                        }
                    )
                        .then(function (response) {
                            $scope.editData = response.data;
                            console.log(editData);
                        })
                        .catch(function (error) {
                            console.log(error);

                            toastr.error(error.data.message);
                            $uibModalInstance.close();
                        });

                }
                $scope.getNewsDetail();
                $scope.editNews = function () {
                    $http.put('http://localhost:8080/api/v1/admin/edit-news', $scope.editData,
                        {
                            withCredentials: true
                        }
                    )
                        .then(function (response) {
                            toastr.success("Edit news successfully!");
                            $uibModalInstance.close();
                            onLoad();

                        })
                        .catch(function (error) {
                            console.log(error);

                            toastr.error(error.data.message);
                            $uibModalInstance.close();
                        });

                }

                $scope.cancel = function () {
                    console.log("Người dùng hủy");
                    $uibModalInstance.dismiss('cancel');
                };


            },
            resolve: {
                editData: function () {
                    return news;
                },
                onLoad: function () {
                    return $scope.fetchNewsByPage;
                },
                categories: function () {
                    return $scope.categories;
                }
            }
        });
    };

    $scope.openModalDelete = function (news) {
        var modalInstance = $uibModal.open({
            templateUrl: 'deleteModal.html',
            windowClass: 'modal-vertical-centered',
            controller: function ($scope, $uibModalInstance, onLoad, filter, currentPage) {

                $scope.deleteNews = function () {
                    $http.delete('http://localhost:8080/api/v1/admin/delete-news/' + news.id,
                        {
                            withCredentials: true
                        }
                    )
                        .then(function (response) {
                            toastr.success("Delete news successfully!");
                            $uibModalInstance.close();
                            filter.page = 0;
                            currentPage = 1;
                            onLoad();

                        })
                        .catch(function (error) {
                            console.log(error);

                            toastr.error(error.data.message);
                            $uibModalInstance.close();
                        });

                }

                $scope.cancel = function () {
                    console.log("Người dùng hủy");
                    $uibModalInstance.dismiss('cancel');
                };


            },
            resolve: {
                filter: function () {
                    return $scope.filter;
                },
                currentPage: function () {
                    return $scope.currentPage;
                },
                onLoad: function () {
                    return $scope.fetchNewsByPage;
                }
            }
        });
    };

});

app.controller('CategoryListCtrl', function ($scope, $http, $location, $uibModal) {
    $scope.categories = [];
    $scope.currentPage = 0;
    $scope.totalPages = 0;
    $scope.totalElements = 0;
    $scope.numberOfElements = 0;

    $scope.fetchCategoriesByPage = function () {

        $http.get('http://localhost:8080/api/v1/admin/new-category-list', {
            withCredentials: true
        })
            .then(function (response) {
                console.log(response)
                if (response.data != '') {
                    $scope.categories = response.data;
                    $scope.totalElements = response.data.length;
                    console.log($scope.categories)
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
    $scope.fetchCategoriesByPage();
    $scope.getPageRange = function () {
        var total = $scope.categories.totalPages;
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
        $scope.fetchCategoriesByPage();
    };


    $scope.editData = {
        name: ''
    }

    $scope.openModal = function () {
        var modalInstance = $uibModal.open({
            templateUrl: 'myModal.html',
            windowClass: 'modal-vertical-centered',
            controller: function ($scope, $uibModalInstance, onLoad) {
                $scope.formData = {
                    name: ''
                }
                $scope.addCategory = function () {
                    $http.post('http://localhost:8080/api/v1/admin/add-new-category', $scope.formData,
                        {
                            withCredentials: true
                        }
                    )
                        .then(function (response) {
                            toastr.success("Add category successfully!");
                            $uibModalInstance.close();
                            onLoad();

                        })
                        .catch(function (error) {
                            console.log(error);

                            toastr.error(error.data.message);
                            $uibModalInstance.close();
                        });
                }


                $scope.cancel = function () {
                    $uibModalInstance.dismiss('cancel');
                };


            },
            resolve: {
                onLoad: function () {
                    return $scope.fetchCategoriesByPage;
                }
            }
        });
    };


    $scope.openModalEdit = function (category) {
        var modalInstance = $uibModal.open({
            templateUrl: 'editModal.html',
            windowClass: 'modal-vertical-centered',
            controller: function ($scope, $uibModalInstance, editData, onLoad) {
                $scope.editData = angular.copy(editData);
                $scope.getCategoryDetail = function () {
                    $http.get('http://localhost:8080/api/v1/admin/new-category-detail/' + category.id,
                        {
                            withCredentials: true
                        }
                    )
                        .then(function (response) {
                            $scope.editData = response.data;
                            console.log(editData);
                        })
                        .catch(function (error) {
                            console.log(error);

                            toastr.error(error.data.message);
                            $uibModalInstance.close();
                        });

                }
                $scope.getCategoryDetail();
                $scope.editCategory = function () {
                    $http.put('http://localhost:8080/api/v1/admin/edit-new-category', $scope.editData,
                        {
                            withCredentials: true
                        }
                    )
                        .then(function (response) {
                            toastr.success("Edit category successfully!");
                            $uibModalInstance.close();
                            onLoad();

                        })
                        .catch(function (error) {
                            console.log(error);

                            toastr.error(error.data.message);
                            $uibModalInstance.close();
                        });

                }

                $scope.cancel = function () {
                    console.log("Người dùng hủy");
                    $uibModalInstance.dismiss('cancel');
                };


            },
            resolve: {
                editData: function () {
                    return category;
                },
                onLoad: function () {
                    return $scope.fetchCategoriesByPage;
                }
            }
        });
    };

    $scope.openModalDelete = function (category) {
        var modalInstance = $uibModal.open({
            templateUrl: 'deleteModal.html',
            windowClass: 'modal-vertical-centered',
            controller: function ($scope, $uibModalInstance, onLoad) {

                $scope.deleteNews = function () {
                    $http.delete('http://localhost:8080/api/v1/admin/delete-new-category/' + category.id,
                        {
                            withCredentials: true
                        }
                    )
                        .then(function (response) {
                            toastr.success("Delete category successfully!");
                            $uibModalInstance.close();
                            onLoad();

                        })
                        .catch(function (error) {
                            console.log(error);

                            toastr.error(error.data.message);
                            $uibModalInstance.close();
                        });

                }

                $scope.cancel = function () {
                    console.log("Người dùng hủy");
                    $uibModalInstance.dismiss('cancel');
                };


            },
            resolve: {
                onLoad: function () {
                    return $scope.fetchCategoriesByPage;
                }
            }
        });
    };

});

app.controller('LogListCtrl', function ($scope, $http, $location, $uibModal) {
    $scope.logs = [];
    $scope.functions = [];
    $scope.currentPage = 0;
    $scope.totalPages = 0;
    $scope.totalElements = 0;
    $scope.numberOfElements = 0;
    $scope.filter = {
        page: 0,
        size: 5,
        sortBy: '',
        ascending: true,
        method: '',
        function: 'All Function',
        status: '',
        createdBy: ''
    }

    $scope.fetchFunctions = function () {
        $http.get('http://localhost:8080/api/v1/admin/get-all-functions', {
            withCredentials: true
        })
            .then(function (response) {
                console.log(response)

                $scope.functions.push(
                    'All Function'
                )
                $scope.functions = $scope.functions.concat(response.data);
            })
            .catch(function (error) {
                console.error("Lỗi khi lấy dữ liệu:", error);
                if (error.status == 401) {
                    toastr.error("Session expired! Please login again");
                    $location.path('/login');
                }
            });
    }

    $scope.fetchLogsByPage = function () {

        $scope.newsFilter = angular.copy($scope.filter);
        if ($scope.newsFilter.method == '') {
            $scope.newsFilter.method = null;
        }
        if ($scope.newsFilter.function == 'All Function') {
            $scope.newsFilter.function = null;
        }
        if ($scope.newsFilter.status == '') {
            $scope.newsFilter.status = null;
        }
        if ($scope.newsFilter.createdBy == '') {
            $scope.newsFilter.createdBy = null;
        }
        $http.post('http://localhost:8080/api/v1/admin/log-list', $scope.newsFilter, {
            withCredentials: true
        })
            .then(function (response) {
                console.log(response)
                if (response.data != '') {
                    $scope.logs = response.data;
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
    $scope.fetchFunctions();
    $scope.fetchLogsByPage();
    $scope.getPageRange = function () {
        var total = $scope.logs.totalPages;
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
        $scope.fetchLogsByPage();
    };

    $scope.sortBy = function (column) {
        if ($scope.filter.sortBy == column) {
            $scope.filter.ascending = !$scope.filter.ascending;
        }
        else {
            $scope.filter.sortBy = column;
            $scope.filter.ascending = true;
        }
        $scope.fetchLogsByPage();
    };

    $scope.openModalDetail = function (response) {
        console.log(response);
        var modalInstance = $uibModal.open({
            templateUrl: 'modal.html',
            windowClass: 'modal-vertical-centered',
            controller: function ($scope, $uibModalInstance) {

                $scope.formatJson = function () {
                    try {
                        console.log(response);

                        const obj = JSON.parse(response);
                        return JSON.stringify(obj, null, 2); // indent 2 spaces
                    } catch (e) {
                        if (response == '') {
                            return 'Empty'
                        }
                        return response; // nếu không phải JSON hợp lệ, hiển thị nguyên văn
                    }
                };


                $scope.cancel = function () {
                    console.log("Người dùng hủy");
                    $uibModalInstance.dismiss('cancel');
                };


            }
        });
    };


});
