(function () {
    angular.module('app')
        .controller('TaskController', function($scope, $http) {
            $scope.tasks = [];

            function create() {
                $http.get('/ng-md-todo/create').then(
                    function(response) {
                        console.log('Dados criados. Status ' + response.status);
                    },
                    function(error) {
                        console.log(error);
                    }
                );
            }
            create();
            $scope.list = function () {
                $http.get('/ng-md-todo/tasks').then(
                    function(response) {
                        console.log('Dados retornados', response.data);
                        $scope.tasks = response.data;
                    },
                    function(error) {
                        console.log(error);
                    }
                );
            };
        });
})();