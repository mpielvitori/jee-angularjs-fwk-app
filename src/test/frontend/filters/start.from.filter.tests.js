describe('StartFrom filter', () => {
  let $filter;

  beforeEach(() => {
    module('App');

	    inject((_$filter_) => {
	      $filter = _$filter_;
	    });
  });

  it('Filtra una lista desde cierto elemento', () => {
	    const lista = [
	    	'uno', 'dos', 'tres',
	    ];

	    const listaEsperada = [
	    	'tres',
	    ];

	    const result = $filter('startFrom')(lista, 2);
	    expect(result).toEqual(listaEsperada);
  });
});
