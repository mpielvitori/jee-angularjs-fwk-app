angular.module('App').factory('SocioResource', ($resource) => {
  const resource = $resource('rest/socios/:filtro/:Param', { Param: '@id' }, {
    queryAll: { method: 'GET', isArray: true }, query: { method: 'GET', isArray: false }, queryByDocumento: { method: 'GET', isArray: true, params: { filtro: 'documento' } }, update: { method: 'PUT' }, remove: { method: 'DELETE' },
  });
  return resource;
});
