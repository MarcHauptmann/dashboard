require("jquery");

function pollJSON(url, handler, interval) {
    $.ajax({
        url: url,
        type: "GET",
        dataType: "json",
        timeout: 3000,
        success: handler,
        complete: setTimeout(function () {
            pollJSON(url, handler, interval);
        }, interval)
    });
}

module.exports = pollJSON;