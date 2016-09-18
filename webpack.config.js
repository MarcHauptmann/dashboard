module.exports = {
    entry: "./src/main/js/main.js",
    output: {
        path: "./target/classes/js",
        filename: "main.js"
    },
    module: {
        loaders: [
            {
                test: /src\/main\/js\/.*\.js$/,
                loader: 'babel-loader'
            }
        ]
    },
};