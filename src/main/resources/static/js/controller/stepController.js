(function () {
    'use strict';
    angular.module('app')
        .controller('StepController', function($scope, $http) {
            var testData = [{id: 1, checked: false, description: undefined}];
            $scope.steps = [];
            $scope.stepTemp = [{id: 0, checked: false, description: undefined}];
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
            };
            $scope.resetIfEmpty = function(step) {
                if (angular.isUndefined(step.description) || step.description.length === 0) {
                    step.icon = 'add';
                }
            };
            $scope.insertStep = function(checked, description) {
                $http.post('steps', {checked: checked, description: description})
                    .then(function(response) {
                        console.log('Etapa ' + response.data + ' inserida');
                        list();
                    }, function(response) {
                        console.log(response.status, response.statusText);
                    }
                );
            };
            $scope.updateStep = function(id, checked) {
                $http.put('steps', {id: id, checked: checked})
                    .then(function() {
                        console.log('Etapa ' + id + ' atualizada');
                        list();
                    }, function(response) {
                        console.log(response.status, response.statusText);
                    }
                );
            };
            $scope.removeStep = function(id) {
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