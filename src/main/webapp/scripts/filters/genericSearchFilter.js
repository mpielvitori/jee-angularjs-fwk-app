

angular.module('App').filter('searchFilter', () => {
  function matchObjectProperties(expectedObject, actualObject) {
    let flag = true;
    for (const key in expectedObject) {
      if (expectedObject.hasOwnProperty(key)) {
        const expectedProperty = expectedObject[key];
        if (expectedProperty == null || expectedProperty === '') {
          continue;
        }
        const actualProperty = actualObject[key];
        if (angular.isUndefined(actualProperty)) {
          continue;
        }
        if (actualProperty == null) {
          flag = false;
        } else if (angular.isObject(expectedProperty)) {
          flag = flag && matchObjectProperties(expectedProperty, actualProperty);
        } else {
          flag = flag && (actualProperty.toString().indexOf(expectedProperty.toString()) != -1);
        }
      }
    }
    return flag;
  }

  return function (results, scope) {
    scope.filteredResults = [];
    for (let ctr = 0; ctr < results.length; ctr++) {
      let flag = true;
      const searchCriteria = scope.search;
      const result = results[ctr];
      for (const key in searchCriteria) {
        if (searchCriteria.hasOwnProperty(key)) {
          const expected = searchCriteria[key];
          if (expected == null || expected === '') {
            continue;
          }
          const actual = result[key];
          if (actual == null) {
            flag = false;
          } else if (angular.isObject(expected)) {
            flag = flag && matchObjectProperties(expected, actual);
          } else {
            flag = flag && (actual.toString().indexOf(expected.toString()) != -1);
          }
        }
      }
      if (flag == true) {
        scope.filteredResults.push(result);
      }
    }
    scope.numberOfPages();
    return scope.filteredResults;
  };
});
