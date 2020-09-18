module.exports = function(config) {
    config.set({
    	frameworks: ['jasmine'],
        files: [
        	//***************** Frontend libs *****************
        	'${project.build.directory}/${project.build.finalName}/bower_components/angular/angular.js',      	
        	'${project.build.directory}/${project.build.finalName}/bower_components/angular-aria/angular-aria.js',
        	'${project.build.directory}/${project.build.finalName}/bower_components/angular-cookies/angular-cookies.js',
        	'${project.build.directory}/${project.build.finalName}/bower_components/angular-messages/angular-messages.js',
        	'${project.build.directory}/${project.build.finalName}/bower_components/angular-resource/angular-resource.js',
        	'${project.build.directory}/${project.build.finalName}/bower_components/angular-sanitize/angular-sanitize.js',    	
        	'${project.build.directory}/${project.build.finalName}/bower_components/angular-bootstrap/ui-bootstrap-tpls.js',  
        	'${project.build.directory}/${project.build.finalName}/bower_components/angular-route/angular-route.js',      
        	'${project.build.directory}/node_modules/angular-mocks/angular-mocks.js',
        	//*************** Fin Frontend libs ***************
        	'${basedir}/src/main/webapp/scripts/app.js', //app.js        	
        	'${basedir}/src/main/webapp/scripts/**/*.js', //frontend scripts       	
            '${basedir}/src/test/frontend/**/*.js' //frontend tests
        ],
        reporters: ['progress', 'coverage', 'verbose', 'spec'],
        coverageReporter: {
            type : 'html',
            dir : '${project.build.directory}/karma-coverage/'
          },
        port: 9876,
        logLevel: config.LOG_DEBUG,
        loggers: [
        		{type: 'console'},
        		{type: 'file', filename: 'karma-tests.log'}
        	],
        browsers: ['PhantomJS'],
        singleRun: false,
        autoWatch: true,
        plugins: [
            'karma-jasmine',
            'karma-phantomjs-launcher',
            'karma-coverage',
            'karma-verbose-reporter',
            'karma-spec-reporter'
        ],
        preprocessors: {
	      	'${basedir}/src/main/webapp/scripts/**/*.js': ['coverage']      
        }
    });
};