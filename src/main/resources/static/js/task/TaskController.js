(function () {
    angular.module('app')
        .controller('TaskController', function($scope, $http) {
            $scope.tasks = [{id: 1, checked: false, description: 'teste', steps: [{id: 1, checked: false, description: 'teste'}]}];
            var nextId = $scope.tasks.length + 1;
            $scope.tasksNew = [
                {id: nextId, icon: 'add', checked: false, description: undefined}
            ];
            $scope.task = {};
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
            function list() {
                $http.get('/ng-md-todo/tasks').then(
                    function(response) {
                        console.log('Dados retornados', response.data);
                        //$scope.tasks = response.data;
                    },
                    function(error) {
                        console.log(error);
                    }
                );
            }
            list();
            $scope.reset = function (task) {
                task.icon = '';
                task.description = undefined;
                window.setTimeout(() => {
                    document.getElementById('txtDescription').focus();
                }, 500);
            };
            $scope.insert = function (task) {
                $scope.tasks.push({id: task.id, checked: task.checked, description: task.description, steps: []});
                nextId = $scope.tasks.length + 1;
                $scope.tasksNew = [];
                $scope.tasksNew.push({id: nextId, icon: 'add', checked: false, description: undefined});
            };
        });
})();