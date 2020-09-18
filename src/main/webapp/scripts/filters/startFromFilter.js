

angular.module('App').filter('startFrom', () => function (input, start) {
  start = +start; // parse to int
  return input.slice(start);
});
