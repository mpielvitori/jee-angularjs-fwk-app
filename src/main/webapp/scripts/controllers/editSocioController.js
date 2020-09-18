

angular.module('App').controller('EditSocioController', function ($scope, $routeParams, $location, flash, SocioResource) {
  const self = this;
  $scope.disabled = false;
  $scope.$location = $location;

  $scope.planes = ['210', '310', '410'];
  $scope.filiales = ['METROPOLITANA', 'CATAMARCA', 'SALTA', 'JUJUY'];
  $scope.tipos = ['Directo', 'Obligatorio', 'Convenio'];

  $scope.dateOptions = {
    	    maxDate: new Date(),
    	    startingDay: 0,
    	    showWeeks: false,
    	    format: 'dd-MM-yyyy',
  };

  $scope.get = function () {
    const successCallback = function (data) {
      self.original = data;
      $scope.socio = new SocioResource(self.original);
        	const nacimiento = new Date($scope.socio.nacimiento);
      $scope.socio.nacimiento = nacimiento;
      self.original.nacimiento = nacimiento;
      const fechaAlta = new Date($scope.socio.alta);
      $scope.socio.alta = fechaAlta;
      self.original.alta = fechaAlta;
      if ($scope.socio.baja != undefined) {
            	const fechaBaja = new Date($scope.socio.baja);
            	$scope.socio.baja = fechaBaja;
            	self.original.baja = fechaBaja;
        	}
    };
    const errorCallback = function () {
      flash.setMessage({ type: 'error', text: 'El socio no pudo ser encontrado.' });
      $location.path('/Socios');
    };
    SocioResource.get({ Param: $routeParams.SocioId }, successCallback, errorCallback);
  };

  $scope.isClean = function () {
    return angular.equals(self.original, $scope.socio);
  };

  $scope.save = function () {
    const successCallback = function () {
      flash.setMessage({ type: 'success', text: 'El socio ha sido actualizado exitosamente.' }, true);
      self.original = $scope.socio;
      $scope.get();
    };
    const errorCallback = function (response) {
      flash.setMessage({ type: 'error', text: 'Ha ocurrido un problema. Por favor, revise la información e intente nuevamente.' }, true);
    };
    SocioResource.update($scope.socio, successCallback, errorCallback);
  };

  $scope.cancel = function () {
    $location.path('/Socios');
  };

  $scope.remove = function () {
    const successCallback = function () {
      flash.setMessage({ type: 'success', text: 'El socio ha sido eliminado.' });
      $location.path('/Socios');
    };
    const errorCallback = function (response) {
      flash.setMessage({ type: 'error', text: 'Ha ocurrido un problema. Por favor, revise la información e intente nuevamente.' }, true);
    };
    SocioResource.remove({ Param: $scope.socio.id }, successCallback, errorCallback);
  };


  $scope.get();
});
