describe('NewSocioController', () => {
  beforeEach(module('App'));

  let $scope; let $location; let mockSocioService; let
    mockFlash;

  describe('Crear nuevo Socio', () => {
    let $httpBackend;

    beforeEach(
      inject(($controller, $rootScope, _$httpBackend_, _$location_, _flash_, _SocioResource_) => {
        $scope = $rootScope.$new();
        $location = _$location_;
        $httpBackend = _$httpBackend_;
        mockSocioService = _SocioResource_;
        mockFlash = _flash_;

				    createController = function () {
				    	return $controller('NewSocioController', {
            $scope, $location, flash: mockFlash, SocioResource: mockSocioService,
          });
				    };
      }),
    );

	    afterEach(() => {
	    	$httpBackend.verifyNoOutstandingExpectation();
	    	$httpBackend.verifyNoOutstandingRequest();
	    });

    it('Redirect a la pantalla del listado de socios al cancelar', () => {
      createController();
      $location.path('/');
      $scope.cancel();
      expect($location.path()).toBe('/Socios');
    });

    it('Creación con errores', () => {
      // Given
		    $scope.socio = {
        alta: '2016-11-29T03:00:00.000Z',
        documento: '12345678',
        filial: 'METROPOLITANA',
				   				nacimiento: '1947-07-06T07:30:00.000Z',
        nombre: 'John James Rambo',
        plan: '210',
        tipo: 'Directo',
				   			};
      // callThrough track all calls to it but in addition it will delegate to the actual implementation.
      // Esto prueba que el servicio realmente llame al servicio que es mockeado con httpbackend.when
		    spyOn(mockSocioService, 'save').and.callThrough();
		    // Returns the original stubbing behavior
		    spyOn(mockFlash, 'setMessage').and.stub();
      // Mocks de servicio y su respuesta
		    $httpBackend.when('POST', 'rest/socios', $scope.socio).respond(404);

		    $location.path('/Socios/new');
		    createController();

		    // When
      $scope.save();
      $httpBackend.flush();// flush pending requests. This preserves the async api of the backend, while allowing the test to execute synchronously.

      // Then
      expect(mockSocioService.save).toHaveBeenCalledWith($scope.socio, expect.any(Function), expect.any(Function));
      expect(mockFlash.setMessage).toHaveBeenCalledWith({ type: 'error', text: expect.any(String) }, true);
      expect($location.path()).toBe('/Socios/new');
    });

    it('Creación exitosa', () => {
      // Given
		    $scope.socio = {
        alta: '2016-11-29T03:00:00.000Z',
        documento: '12345678',
        filial: 'METROPOLITANA',
				   				nacimiento: '1947-07-06T07:30:00.000Z',
        nombre: 'John James Rambo',
        plan: '210',
        tipo: 'Directo',
				   			};
		    spyOn(mockSocioService, 'save').and.callThrough();
		    spyOn(mockFlash, 'setMessage').and.stub();
		    $httpBackend.when('POST', 'rest/socios', $scope.socio).respond(201);

		    $location.path('/Socios/new');
		    createController();

		    // When
      $scope.save();
      $httpBackend.flush();

      // Then
      expect(mockSocioService.save).toHaveBeenCalledWith($scope.socio, expect.any(Function), expect.any(Function));
      expect(mockFlash.setMessage).toHaveBeenCalledWith({ type: 'success', text: expect.any(String) });
      expect($location.path()).toBe('/Socios');
    });
  });
});
