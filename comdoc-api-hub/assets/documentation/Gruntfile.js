module.exports = function(grunt) {

grunt.initConfig({
	
    paths: {
      assets: {
        css: 'assets/less/',
        vendor: 'bower_components/',
        app: 'app/'
      },
      css: 'assets/build/',
      js: 'assets/build/'

    },

    // Task configuration
   concat: {
      options: {
        separator: ';'
      },
      vision: {
        src: [
			'<%= paths.assets.vendor %>jquery/dist/jquery.js',
			'<%= paths.assets.vendor %>flexslider/jquery.flexslider.js',
			'<%= paths.assets.vendor %>masonry/dist/masonry.pkgd.js',
			'<%= paths.assets.vendor %>imagesloaded/imagesloaded.pkgd.js',
			'<%= paths.assets.vendor %>angular/angular.js',
			'<%= paths.assets.vendor %>angular-route/angular-route.js',
			'<%= paths.assets.vendor %>angular-sanitize/angular-sanitize.js',
			'<%= paths.assets.vendor %>angular-bootstrap/ui-bootstrap-tpls.js',
			'<%= paths.assets.vendor %>angular-flexslider/angular-flexslider.js',
			'<%= paths.assets.vendor %>ng-backstretch/dist/ng-backstretch.js',
			'<%= paths.assets.vendor %>angular-parallax/scripts/angular-parallax.js',
			'<%= paths.assets.vendor %>angular-fitvids/angular-fitvids.js',
			'<%= paths.assets.vendor %>angular-masonry/angular-masonry.js',
			'<%= paths.assets.vendor %>momentjs/moment.js',
			'<%= paths.assets.vendor %>humanize-duration/humanize-duration.js',
			'<%= paths.assets.vendor %>angular-timer/dist/angular-timer.js',
			'<%= paths.assets.vendor %>ngprogress/build/ngprogress.min.js',
			'<%= paths.assets.vendor %>angular-google-maps/dist/angular-google-maps.js',
        ],
        dest: '<%= paths.js %>scripts.js',
      }
    }, 
    
    less: {
        production: {
            options: {
              compress: true,
            },
            files: {
              //compiling styles.less into styles.min.css
              "<%= paths.css %>styles.min.css":"<%= paths.assets.css %>styles.less"

            }
        }
    }, 
    uglify: {
		options: {
			mangle: false  // Use if you want the names of your functions and variables unchanged
		},
		vision: {
			files: {
				'<%= paths.js %>scripts.min.js': '<%= paths.js %>scripts.js',
			}
		},
    },
    watch: {
        vision: {
          files: [
	           	'<%= paths.assets.vendor %>jquery/dist/jquery.js',
				'<%= paths.assets.vendor %>flexslider/jquery.flexslider.js',
				'<%= paths.assets.vendor %>masonry/dist/masonry.pkgd.js',
				'<%= paths.assets.vendor %>imagesloaded/imagesloaded.pkgd.js',
				'<%= paths.assets.vendor %>angular/angular.js',
				'<%= paths.assets.vendor %>angular-route/angular-route.js',
				'<%= paths.assets.vendor %>angular-sanitize/angular-sanitize.js',
				'<%= paths.assets.vendor %>angular-bootstrap/ui-bootstrap-tpls.js',
				'<%= paths.assets.vendor %>angular-flexslider/angular-flexslider.js',
				'<%= paths.assets.vendor %>ng-backstretch/dist/ng-backstretch.js',
				'<%= paths.assets.vendor %>angular-parallax/scripts/angular-parallax.js',
				'<%= paths.assets.vendor %>angular-fitvids/angular-fitvids.js',
				'<%= paths.assets.vendor %>angular-masonry/angular-masonry.js',
				'<%= paths.assets.vendor %>momentjs/moment.js',
				'<%= paths.assets.vendor %>humanize-duration/humanize-duration.js',
				'<%= paths.assets.vendor %>angular-timer/dist/angular-timer.js',
				'<%= paths.assets.vendor %>ngprogress/build/ngprogress.min.js',
				'<%= paths.assets.vendor %>angular-google-maps/dist/angular-google-maps.js',
            ],   
          tasks: ['concat:vision','uglify:vision'],     //tasks to run
          options: {
            livereload: true                        //reloads the browser
          }
        },
        less: {
          files: ['<%= paths.assets.css %>*.less'],  //watched files
          tasks: ['less'],                          //tasks to run
          options: {
            livereload: true                        //reloads the browser
          }
        },
      }
});

 // Plugin loading
grunt.loadNpmTasks('grunt-contrib-concat');
grunt.loadNpmTasks('grunt-contrib-watch');
grunt.loadNpmTasks('grunt-contrib-less');
grunt.loadNpmTasks('grunt-contrib-uglify');

// Task definition
grunt.registerTask('default', ['watch']);
};