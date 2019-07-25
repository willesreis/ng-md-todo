(function () {
    'use strict';
    angular.module('app')
        .controller('TaskController', function($scope, $http) {
            var testData = [{id: 1, checked: false, description: 'teste', steps: [{id: 1, checked: false, description: 'teste'}]}];
            $scope.tasks = [];
            $scope.tasksTemp = [{id: 0, icon: 'add', checked: false, description: undefined}];
            function create() {
                $http.get('task')
                    .then(function(response) {
                        console.log(response.data);
                        list();
                    }, function(response) {
                        console.log(response.status, response.statusText);
                    }
                );
            }
            create();
            function list() {
                $http.get('tasks')
                    .then(function(response) {
                        $scope.tasks = response.data;
                    }, function(response) {
                        console.log(response.status, response.statusText);
                    }
                );
            }
            $scope.reset = function(task) {
                task.icon = '';
                task.description = undefined;
                window.setTimeout(() => {
                    document.getElementById('txtDescription').focus();
                }, 500);
            };
            $scope.resetIfEmpty = function(task) {
                if (angular.isUndefined(task.description) || task.description.length === 0) {
                    task.icon = 'add';
                }
            };
            $scope.insert = function(checked, description) {
                $http.post('tasks', {checked: checked, description: description})
                    .then(function(response) {
                        console.log(response.data);
                        list();
                    }, function(response) {
                        console.log(response.status, response.statusText);
                    }
                );
                $scope.tasksTemp = [];
                $scope.tasksTemp.push({id: 0, icon: 'add', checked: false, description: undefined});
            };
            $scope.update = function(id, checked) {
                $http.put('tasks', {id: id, checked: checked})
                    .then(function(response) {
                        console.log(response.data);
                        list();
                    }, function(response) {
                        console.log(response.status, response.statusText);
                    }
                );
            };
            $scope.remove = function(id) {
                $http.delete('tasks/' + id)
                    .then(function(response) {
                        console.log(response);
                        list();
                    }, function(response) {
                        console.log(response.status, response.statusText);
                    }
                );
            };
        });
})();