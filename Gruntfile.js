module.exports = function (grunt) {
    var targetDir = "target/classes/js/";
    var generatedSourcesDir = "target/generated-sources/js/";

    grunt.initConfig({
        uglify: {
            core: {
                files: [{
                    expand: true,
                    cwd: 'src/main/js',
                    src: '**/*.js',
                    dest: targetDir,
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
                    destPrefix: generatedSourcesDir
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
                    cwd: generatedSourcesDir,
                    src: "**",
                    dest: targetDir
                }]
            }
        },
        watch: {
            scripts: {
                files: ['**/*.js'],
                tasks: ['uglify'],
                options: {
                    spawn: false
                }
            }
        }
    });

    grunt.loadNpmTasks('grunt-contrib-uglify');
    grunt.loadNpmTasks('grunt-bowercopy');
    grunt.loadNpmTasks('grunt-contrib-copy');
    grunt.loadNpmTasks('grunt-contrib-watch');

    grunt.registerTask('default', ['uglify', 'bowercopy', 'copy']);
};
