import formatNumber from "./string.js";

export default function updateTodo(response) {
    var todoList = $("#todoList");

    if (response.length > 0) {
        var table = $("<table>").addClass("table").addClass("table-condensed");

        $.each(response, function (index, item) {
            var date = new Date(item.date);

            var dateString = formatNumber(date.getHours()) + ":" + formatNumber(date.getMinutes());

            table.append($("<row>")
                .append($("<td>").addClass("time").text(dateString))
                .append($("<td>").addClass("title").text(item.title)));
        });

        todoList.empty();
        todoList.append(table);
    } else {
        todoList.empty();

        todoList.append($("<div>").addClass("emptyList")
            .append($("<span>").text("☺").addClass("smiley"))
            .append($("<span>").text("nichts …").addClass("text")));
    }
}