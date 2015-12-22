module.exports = function (grunt) {
    grunt.initConfig({
        uglify: {
            core: {
                files: [{
                    expand: true,
                    cwd: 'src/main/js',
                    src: '**/*.js',
                    dest: "target/classes/js/",
                    ext: '.js'
                }]
            }
        },
        bowercopy: {
            options: {
                clean: true
            },
            libs: {
                options: {
                    destPrefix: "target/generated-sources/js"
                },
                files: {
                    "jquery.js": "jquery/dist/jquery.min.js",
                    "highcharts.js": "highcharts/highcharts.js"
                }
            }
        },
        copy: {
            libs: {
                files: [{
                    expand: true,
                    cwd: "target/generated-sources/js/",
                    src: "**",
                    dest: "target/classes/js/"
                }]
            }
        }
    });

    grunt.loadNpmTasks('grunt-contrib-uglify');
    grunt.loadNpmTasks('grunt-bowercopy');
    grunt.loadNpmTasks('grunt-contrib-copy');

    grunt.registerTask('default', ['uglify', 'bowercopy', 'copy']);
};
