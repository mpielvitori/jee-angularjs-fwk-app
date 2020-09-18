angular.module('App').controller('SearchSocioController', ($scope, $http, $filter, SocioResource) => {
  $scope.search = {};
  $scope.currentPage = 0;
  $scope.pageSize = 10;
  $scope.searchResults = [];
  $scope.filteredResults = [];
  $scope.pageRange = [];
  $scope.planes = ['210', '310', '410'];
  $scope.filiales = ['METROPOLITANA', 'CATAMARCA', 'SALTA', 'JUJUY'];
  $scope.tipos = ['Directo', 'Obligatorio', 'Convenio'];

  $scope.numberOfPages = function () {
    const result = Math.ceil($scope.filteredResults.length / $scope.pageSize);
    const max = (result == 0) ? 1 : result;
    $scope.pageRange = [];
    for (let ctr = 0; ctr < max; ctr++) {
      $scope.pageRange.push(ctr);
    }
    return max;
  };

  $scope.performSearch = function () {
    if (!$scope.search.documento) {
      $scope.searchResults = SocioResource.queryAll(() => {
        $scope.filteredResults = $filter('searchFilter')($scope.searchResults, $scope);
        $scope.currentPage = 0;
      });
    } else {
      $scope.searchResults = SocioResource.queryByDocumento({ Param: $scope.search.documento }, () => {
        $scope.filteredResults = $filter('searchFilter')($scope.searchResults, $scope);
        $scope.currentPage = 0;
      });
    }
  };

  $scope.previous = function () {
    if ($scope.currentPage > 0) {
      $scope.currentPage--;
    }
  };

  $scope.next = function () {
    if ($scope.currentPage < ($scope.numberOfPages() - 1)) {
      $scope.currentPage++;
    }
  };

  $scope.setPage = function (n) {
    $scope.currentPage = n;
  };

  $scope.performSearch();
});
