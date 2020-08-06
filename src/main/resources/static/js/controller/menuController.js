(function () {
    'use strict';
    angular.module('app')
        .controller('MenuController', function($scope) {
            $scope.sidenavClosed = true;
            $scope.toggleSidenav = function() {
                const sidenav = document.getElementById('sidenav');
                if (sidenav.style.width == "50px") {
                    sidenav.style.width = "290px";
                    $scope.sidenavClosed = false;
                } else {
                    sidenav.style.width = "50px";
                    $scope.sidenavClosed = true;
                }
            };
        });
})();