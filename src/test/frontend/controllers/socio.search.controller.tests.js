describe('SearchSocioController', () => {
  beforeEach(module('App'));

  let $scope; let $location; let mockSocioService; let mockFlash; let
    $httpBackend;

  beforeEach(
    inject(($controller, $rootScope, _$http_, _$filter_, _$httpBackend_, _SocioResource_) => {
      $scope = $rootScope.$new();
      $http = _$http_;
      $filter = _$filter_;
      $httpBackend = _$httpBackend_;
      mockSocioService = _SocioResource_;
		    createController = function () {
		    	return $controller('SearchSocioController', {
          $scope, $http, $filter, SocioResource: mockSocioService,
        });
		    };
    }),
  );

  describe('Búsqueda de Socios', () => {
    it('Página anterior', () => {
      createController();
      $scope.currentPage = 2;

      $scope.previous();
      expect($scope.currentPage).toBe(1);
    });

    it('Página siguiente', () => {
      createController();
      $scope.currentPage = 2;
      spyOn($scope, 'numberOfPages').and.returnValue(4);

      $scope.next();
      expect($scope.currentPage).toBe(3);
    });

    it('Setear página', () => {
      createController();
      $scope.currentPage = 2;

      $scope.setPage(1);
      expect($scope.currentPage).toBe(1);
    });

    it('Número de páginas = 1', () => {
      createController();

      expect($scope.numberOfPages()).toBe(1);
    });

    it('Número de páginas = 2', () => {
      createController();
      $scope.filteredResults = [{}, {}, {}, {}, {}, {}, {}, {}, {}, {}, {}];
      $scope.pageSize = 10;

      expect($scope.numberOfPages()).toBe(2);
    });

    // TODO
    it('Busqueda sin filtros', () => {
      createController();
      $scope.search.documento = false;
      spyOn(mockSocioService, 'queryAll').and.callThrough();
      $httpBackend.when('GET', 'rest/socios').respond([]);

      $scope.performSearch();
      $httpBackend.flush();

      expect(mockSocioService.queryAll).toHaveBeenCalled();
      expect($scope.currentPage).toBe(0);
      expect($scope.filteredResults).toHaveLength(0);
    });

    it('Busqueda por documento', () => {
      $httpBackend.when('GET', 'rest/socios').respond([]);
      createController();
      $scope.search.documento = '12345678';
      spyOn(mockSocioService, 'queryByDocumento').and.callThrough();
      $httpBackend.when('GET', `rest/socios/documento/${$scope.search.documento}`).respond([]);

      $scope.performSearch();
      $httpBackend.flush();

      expect(mockSocioService.queryByDocumento).toHaveBeenCalled();
      expect($scope.currentPage).toBe(0);
      expect($scope.filteredResults).toHaveLength(0);
    });
  });
});
