(function () {
    'use strict';
    angular.module('app')
        .controller('StepController', function($scope, $http) {
            var testData = [{id: 1, checked: false, description: undefined}];
            $scope.steps = [];
            $scope.stepTemp = [{id: 0, checked: false, description: undefined}];
            function create() {
                $http.get('step')
                    .then(function() {
                        console.log('Dados criados.');
                        list();
                    }, function(response) {
                        console.log(response.status, response.statusText);
                    }
                );
            }
            create();
            function list() {
                $http.get('steps')
                    .then(function(response) {
                        $scope.steps = response.data;
                    }, function(response) {
                        console.log(response.status, response.statusText);
                    }
                );
            }
            $scope.reset = function(step) {
                step.description = undefined;
                window.setTimeout(() => {
                    document.getElementById('txtDescription').focus();
                }, 500);
            };
            $scope.resetIfEmpty = function(step) {
                if (angular.isUndefined(step.description) || step.description.length === 0) {
                }
            };
            $scope.insert = function(checked, description) {
                $http.post('steps', {checked: checked, description: description})
                    .then(function(response) {
                        console.log('Etapa ' + response.data + ' inserida');
                        list();
                    }, function(response) {
                        console.log(response.status, response.statusText);
                    }
                );
                $scope.stepTemp = [];
                $scope.stepTemp.push({id: 0, checked: false, description: undefined});
            };
            $scope.update = function(id, checked) {
                $http.put('steps', {id: id, checked: checked})
                    .then(function() {
                        console.log('Etapa ' + id + ' atualizada');
                        list();
                    }, function(response) {
                        console.log(response.status, response.statusText);
                    }
                );
            };
            $scope.remove = function(id) {
                $http.delete('steps/' + id)
                    .then(function() {
                        console.log('Etapa ' + id + ' removida');
                        list();
                    }, function(response) {
                        console.log(response.status, response.statusText);
                    }
                );
            };
        });
})();