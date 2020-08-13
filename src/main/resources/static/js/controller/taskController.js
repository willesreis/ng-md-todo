(function () {
    'use strict';
    angular.module('app')
        .controller('TaskController', function($scope, $http, $filter) {
            // var testData = [{id: 1, checked: false, description: 'teste', steps: [{id: 1, checked: false, description: 'teste'}]}];
            const initialTask = {id: 0, icon: 'add', checked: false, toDay: false, toImportant: false, description: undefined};
            $scope.tasks = [];
            $scope.tasksTemp = [angular.copy(initialTask)];
            $scope.taskSelected = undefined;
            $scope.title = undefined;
            $scope.subTitle = undefined;
            function list() {
                $http.get('tasks')
                    .then(function(response) {
                        $scope.tasks = response.data;
                    }, function(response) {
                        console.log(response.status, response.statusText);
                    }
                );
            }
            list();
            $scope.menuMyDay = function(array) {
                $scope.title = 'Meu dia';
                const options = {weekday: 'long', day: 'numeric', month: 'long'}
                $scope.subTitle = new Date().toLocaleString('pt-BR', options);
                // $scope.tasks = $filter('filter')(array, {toDay: true});
            };
            $scope.menuImportant = function(array) {
                $scope.title = 'Importante';
                $scope.subTitle = undefined;
                // $scope.tasks = $filter('filter')(array, {toImportant: true});
            };
            $scope.menuPlanned = function() {
                $scope.title = 'Planejado';
                $scope.subTitle = undefined;
            };
            $scope.menuIssuesToYou = function() {
                $scope.title = 'Atribuída a você';
                $scope.subTitle = undefined;
            };
            $scope.menuMailMarked = function() {
                $scope.title = 'Email sinalizado';
                $scope.subTitle = undefined;
            };
            $scope.menuTasks = function() {
                $scope.title = 'Tarefas';
                $scope.subTitle = undefined;
            };
            $scope.menuTasks();
            $scope.reset = function(task) {
                task.icon = '';
                task.description = undefined;
                window.setTimeout(() => {
                    document.getElementsByClassName('insert')[0].focus();
                }, 500);
            };
            $scope.resetIfEmpty = function(task) {
                if (angular.isUndefined(task.description) || task.description.length === 0) {
                    task.icon = 'add';
                }
            };
            $scope.insert = function(task) {
                $http.post('tasks', {checked: task.checked, toDay: task.toDay, toImportant: task.toImportant, description: task.description})
                    .then(function(response) {
                        console.log(response.data);
                        list();
                    }, function(response) {
                        console.log(response.status, response.statusText);
                    }
                );
                $scope.tasksTemp = [];
                $scope.tasksTemp.push(angular.copy(initialTask));
            };
            $scope.update = function(task) {
                $http.put('tasks', {id: task.id, checked: task.checked, toDay: task.toDay, toImportant: task.toImportant})
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
            $scope.toggleSidenav = function() {
                const sidenav = document.getElementById('sidenav-left');
                if (sidenav.style.width == "50px") {
                    sidenav.style.width = "290px";
                } else {
                    sidenav.style.width = "50px";
                }
            };
            $scope.showSteps = function(task) {
                const sidenav = document.getElementById('sidenav-right');
                sidenav.style.width = "360px";
                $scope.taskSelected = task;
            };
            $scope.hideSteps = function() {
                const sidenav = document.getElementById('sidenav-right');
                sidenav.style.width = "0px";
                $scope.taskSelected = undefined;
            };
            $scope.addAllowed = function(task) {
                if (task.icon  === 'add') {
                    return true;
                }
                return false;
            };
            $scope.addNotAllowed = function(task) {
                if (task.icon  === '') {
                    return true;
                }
                return false;
            };
            $scope.addNotAllowedAndHasDescription = function(task) {
                if (task.icon  === '' && angular.isDefined(task.description)) {
                    return true;
                }
                return false;
            };
        });
})();