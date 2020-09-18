describe('FlashController', () => {
  beforeEach(module('App'));

  let $scope; let
    mockFlash;

  beforeEach(inject(
    ($controller, $rootScope, _flash_) => {
      $scope = $rootScope.$new();
      mockFlash = _flash_;
      $controller('FlashController', { $scope, flash: mockFlash });
    },
  ));

  it('Hide Alert cambia propiedad showAlert a false', () => {
    $scope.showAlert = true;
    $scope.hideAlert();
    expect($scope.showAlert).toEqual(false);
  });

  it('Obtener mensaje de error seteado', () => {
    spyOn(mockFlash, 'getMessage').and.callThrough();

    mockFlash.setMessage({ type: 'error', text: 'Test message' }, true);
    const message = mockFlash.getMessage();

    expect(message.text).toBe('Test message');
    expect(message.type).toBe('error');
  });

  it('Obtener mensaje de success seteado', () => {
    spyOn(mockFlash, 'getMessage').and.callThrough();

    mockFlash.setMessage({ type: 'success', text: 'Test message' }, true);
    const message = mockFlash.getMessage();

    expect(message.text).toBe('Test message');
    expect(message.type).toBe('success');
  });

  it('Obtener mensaje de info seteado', () => {
    spyOn(mockFlash, 'getMessage').and.callThrough();

    mockFlash.setMessage({ type: 'info', text: 'Test message' }, true);
    const message = mockFlash.getMessage();

    expect(message.text).toBe('Test message');
    expect(message.type).toBe('info');
  });

  it('Obtener mensaje de warning seteado', () => {
    spyOn(mockFlash, 'getMessage').and.callThrough();

    mockFlash.setMessage({ type: 'warning', text: 'Test message' }, true);
    const message = mockFlash.getMessage();

    expect(message.text).toBe('Test message');
    expect(message.type).toBe('warning');
  });
});
