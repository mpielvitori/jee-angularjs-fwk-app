describe('EditSocioController', () => {
  beforeEach(module('App'));

  let $scope; let $location; let mockSocioService; let
    mockFlash;

  describe('Controller context', () => {
    beforeEach(
      inject(($controller, $rootScope, _$location_, _flash_, _SocioResource_) => {
        $scope = $rootScope.$new();
        $location = _$location_;
        mockSocioService = _SocioResource_;
        mockFlash = _flash_;
			    createController = function () {
			    	return $controller('EditSocioController', {
            $scope, $location, flash: mockFlash, SocioResource: mockSocioService,
          });
			    };
      }),
    );

    it('Redirect a la pantalla del listado de socios al cancelar', () => {
      createController();
      $location.path('/');
      $scope.cancel();
      expect($location.path()).toBe('/Socios');
    });

    it('Comprueba si el socio fué modificado', () => {
      const controller = createController();
		    $scope.socio = {
        id: 1,
        alta: '2016-11-29T03:00:00.000Z',
        documento: '12345678',
        filial: 'METROPOLITANA',
	   				nacimiento: '1947-07-06T07:30:00.000Z',
        nombre: 'John James Rambo',
        plan: '210',
        tipo: 'Directo',
      };
		    controller.original = {
        id: 1,
        alta: '2016-11-29T03:00:00.000Z',
        documento: '11111111',
        filial: 'METROPOLITANA',
	   				nacimiento: '1947-07-06T07:30:00.000Z',
        nombre: 'John James Rambo',
        plan: '210',
        tipo: 'Directo',
      };
      expect($scope.isClean()).toBe(false);
    });

    it('Comprueba si el socio NO fué modificado', () => {
      const controller = createController();
		    $scope.socio = {
        id: 1,
        alta: '2016-11-29T03:00:00.000Z',
        documento: '12345678',
        filial: 'METROPOLITANA',
	   				nacimiento: '1947-07-06T07:30:00.000Z',
        nombre: 'John James Rambo',
        plan: '210',
        tipo: 'Directo',
      };
		    controller.original = 	{
        id: 1,
        alta: '2016-11-29T03:00:00.000Z',
        documento: '12345678',
        filial: 'METROPOLITANA',
        nacimiento: '1947-07-06T07:30:00.000Z',
        nombre: 'John James Rambo',
        plan: '210',
        tipo: 'Directo',
      };
      expect($scope.isClean()).toBe(true);
    });
  });

  describe('Edición de Socio', () => {
    let $httpBackend; let
      $routeParams;

    beforeEach(
      inject(($controller, $rootScope, _$httpBackend_, _$location_, _flash_, _SocioResource_, _$routeParams_) => {
        $scope = $rootScope.$new();
        $location = _$location_;
        $httpBackend = _$httpBackend_;
        mockSocioService = _SocioResource_;
        mockFlash = _flash_;
        $routeParams = _$routeParams_;

				    createController = function () {
				    	return $controller('EditSocioController', {
            $scope, $routeParams, $location, flash: mockFlash, SocioResource: mockSocioService,
          });
				    };
      }),
    );

	    afterEach(() => {
	    	$httpBackend.verifyNoOutstandingExpectation();
	    	$httpBackend.verifyNoOutstandingRequest();
	    });

    it('Editar Socio con errores', () => {
      // Given
		    $scope.socioSinDNI = {
        id: 1,
        alta: '2016-11-29T03:00:00.000Z',
        filial: 'METROPOLITANA',
				   				nacimiento: '1947-07-06T07:30:00.000Z',
        nombre: 'John James Rambo',
        plan: '210',
        tipo: 'Directo',
				   			};
		    spyOn(mockSocioService, 'update').and.callThrough();
		    spyOn(mockFlash, 'setMessage').and.stub();
		    // Para revisar: se devería llamar a PUT rest/socios/1 pero se llama a PUT rest/socios
		    $httpBackend.when('PUT', 'rest/socios', $scope.socioSinDNI).respond(500);
		    $httpBackend.when('GET', 'rest/socios').respond($scope.socioSinDNI);

		    createController();

		    // When
      $scope.save();
      $httpBackend.flush();

      // Then
      expect(mockSocioService.update).toHaveBeenCalled();
      expect(mockFlash.setMessage).toHaveBeenCalledWith({ type: 'error', text: expect.any(String) }, true);
    });

    it('Editar Socio', () => {
      // Given
		    $scope.socio = {
        id: 1,
        alta: '2016-11-29T03:00:00.000Z',
        documento: '12345678',
        filial: 'METROPOLITANA',
				   				nacimiento: '1947-07-06T07:30:00.000Z',
        nombre: 'John James Rambo',
        plan: '210',
        tipo: 'Directo',
				   			};
		    spyOn(mockSocioService, 'update').and.callThrough();
		    spyOn(mockFlash, 'setMessage').and.stub();
		    $httpBackend.when('PUT', `rest/socios/${$scope.socio.id}`, $scope.socio).respond(204);
		    $httpBackend.when('GET', 'rest/socios').respond($scope.socio);

		    createController();

		    // When
      $scope.save();
      $httpBackend.flush();

      // Then
      expect(mockSocioService.update).toHaveBeenCalled();
      expect(mockFlash.setMessage).toHaveBeenCalledWith({ type: 'success', text: expect.any(String) }, true);
    });

    it('Eliminar Socio con errores', () => {
      // Given
      $scope.socio = {
        id: 1,
        alta: '2016-11-29T03:00:00.000Z',
        documento: '12345678',
        filial: 'METROPOLITANA',
				   				nacimiento: '1947-07-06T07:30:00.000Z',
        nombre: 'John James Rambo',
        plan: '210',
        tipo: 'Directo',
      };
		    spyOn(mockFlash, 'setMessage').and.stub();
		    $httpBackend.when('DELETE', `rest/socios/${$scope.socio.id}`).respond(500);
		    spyOn(mockSocioService, 'remove').and.callThrough();
		    $httpBackend.when('GET', 'rest/socios').respond({});
		    $location.path('/Socios/edit/1');
		    createController();

		    // When
      $scope.remove();
      $httpBackend.flush();

      // Then
      expect(mockSocioService.remove).toHaveBeenCalled();
      expect(mockFlash.setMessage).toHaveBeenCalledWith({ type: 'error', text: expect.any(String) }, true);
      expect($location.path()).toBe('/Socios/edit/1');
    });

    it('Eliminar Socio', () => {
      // Given
      $scope.socio = {
        id: 1,
        alta: '2016-11-29T03:00:00.000Z',
        documento: '12345678',
        filial: 'METROPOLITANA',
				   				nacimiento: '1947-07-06T07:30:00.000Z',
        nombre: 'John James Rambo',
        plan: '210',
        tipo: 'Directo',
      };
		    spyOn(mockFlash, 'setMessage').and.stub();
		    $httpBackend.when('DELETE', `rest/socios/${$scope.socio.id}`).respond(204);
		    spyOn(mockSocioService, 'remove').and.callThrough();
		    $httpBackend.when('GET', 'rest/socios').respond({});
		    $location.path('/Socios/edit/1');
		    createController();

		    // When
      $scope.remove();
      $httpBackend.flush();

      // Then
      expect(mockSocioService.remove).toHaveBeenCalled();
      expect(mockFlash.setMessage).toHaveBeenCalledWith({ type: 'success', text: expect.any(String) });
      expect($location.path()).toBe('/Socios');
    });

    it('Recuperar Socio con errores', () => {
      // Given
      $routeParams.SocioId = 8;
		    spyOn(mockFlash, 'setMessage').and.stub();
		    spyOn(mockSocioService, 'get').and.callThrough();
		    $httpBackend.when('GET', `rest/socios/${$routeParams.SocioId}`).respond(404);
		    $location.path(`/Socios/edit/${$routeParams.SocioId}`);
		    createController();

		    // When
      $scope.get();
      $httpBackend.flush();

      // Then
      expect(mockSocioService.get).toHaveBeenCalled();
      expect(mockFlash.setMessage).toHaveBeenCalledWith({ type: 'error', text: expect.any(String) });
      expect($location.path()).toBe('/Socios');
    });

    it('Recuperar Socio', () => {
      // Given
      $scope.socio = {
        id: 1,
        alta: '2016-11-29T03:00:00.000Z',
        baja: '2016-11-30T03:00:00.000Z',
        documento: '12345678',
        filial: 'METROPOLITANA',
				   				nacimiento: '1947-07-06T07:30:00.000Z',
        nombre: 'John James Rambo',
        plan: '210',
        tipo: 'Directo',
      };
      $routeParams.SocioId = 1;
		    spyOn(mockSocioService, 'get').and.callThrough();
		    $httpBackend.when('GET', `rest/socios/${$routeParams.SocioId}`).respond(200, $scope.socio);
		    const controller = createController();

		    // When
      $scope.get();
      $httpBackend.flush();

      // Then
      expect(mockSocioService.get).toHaveBeenCalled();
      expect($scope.isClean()).toBe(true);
      expect(controller.original.id).toBe($routeParams.SocioId);
    });
  });
});
