describe('MainCtrl', () => {
  beforeEach(module('App'));

  let $scope; let
    controller;

  beforeEach(inject(
    ($controller, $rootScope) => {
      $scope = $rootScope.$new();
      controller = $controller('MainCtrl', { $scope });
    },
  ));

  it('Comprueba propiedades awesomeThings', () => {
    expect(controller.awesomeThings).toHaveLength(3);
  });
});
