

angular.module('App').factory('flash', ['$rootScope', function ($rootScope) {
  const messages = [];
  let currentMessage = {};

  $rootScope.$on('$routeChangeSuccess', () => {
    currentMessage = messages.shift() || {};
  });

  return {
    getMessage() {
      return currentMessage;
    },
    setMessage(message, pop) {
      switch (message.type) {
        case 'error': message.cssClass = 'danger'; break;
        case 'success': message.cssClass = 'success'; break;
        case 'info': message.cssClass = 'info'; break;
        case 'warning': message.cssClass = 'warning'; break;
      }
      messages.push(message);
      if (pop) {
        currentMessage = messages.shift() || {};
      }
    },
  };
}]);
