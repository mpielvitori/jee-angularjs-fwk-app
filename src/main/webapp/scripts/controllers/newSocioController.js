angular.module('App').controller('NewSocioController', ['$scope', '$location', 'flash', 'SocioResource', '$http', function ($scope, $location, flash, SocioResource) {
  $scope.disabled = false;
  $scope.$location = $location;
  $scope.socio = $scope.socio || {};

  $scope.planes = ['210', '310', '410'];
  $scope.filiales = ['METROPOLITANA', 'CATAMARCA', 'SALTA', 'JUJUY'];
  $scope.tipos = ['Directo', 'Obligatorio', 'Convenio'];

  $scope.dateOptions = {
    	    maxDate: new Date(),
    	    startingDay: 0,
    	    showWeeks: false,
  };

  $scope.save = function () {
    const successCallback = function (data, headers) {
      flash.setMessage({ type: 'success', text: 'El socio ha sido creado exitosamente.' });
      $location.path('/Socios');
    };
    const errorCallback = function (response) {
      flash.setMessage({ type: 'error', text: 'Ha ocurrido un problema. Por favor, revise la información e intente nuevamente.' }, true);
    };
    SocioResource.save($scope.socio, successCallback, errorCallback);
  };

  $scope.cancel = function () {
    $location.path('/Socios');
  };
}]);
