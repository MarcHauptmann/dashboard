package marc.dashboard.todo;

import biweekly.Biweekly;
import biweekly.ICalendar;
import biweekly.component.VEvent;
import biweekly.property.RecurrenceRule;
import biweekly.util.ICalDate;
import com.google.ical.compat.javautil.DateIterator;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.IOException;
import java.net.URL;
import java.sql.Date;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.util.List;
import java.util.Optional;
import java.util.Spliterator;
import java.util.Spliterators;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

@Controller
@RequestMapping("/todo")
public class TodoController {
    public static final int MAX_DATE_STEPS = 10000;

    @Value("${calendar.url}")
    private String calendarUrl;

    @RequestMapping(value = "today", method = RequestMethod.GET)
    @ResponseBody
    public List<TodoItem> getTodaysTodos() {
        try {
            URL url = new URL(calendarUrl);

            ICalendar calendar = Biweekly.parse(url.openStream()).first();

            return calendar.getEvents().stream()
                    .flatMap(event -> Optional.ofNullable(event.getRecurrenceRule())
                            .map(rule -> toTodoStream(event))
                            .orElseGet(() -> toTodoItem(event)))
                    .sorted()
                    .filter(this::today)
                    .collect(Collectors.toList());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private boolean today(TodoItem item) {
        LocalDateTime tomorrow = LocalDateTime.of(LocalDate.now(), LocalTime.MIDNIGHT).plusDays(1);

        LocalDateTime eventTime = LocalDateTime.ofInstant(item.getDate().toInstant(), ZoneId.systemDefault());

        return eventTime.isBefore(tomorrow);
    }

    private Stream<TodoItem> toTodoStream(VEvent event) {
        RecurrenceRule rule = event.getRecurrenceRule();

        ICalDate value = event.getDateStart().getValue();
        DateIterator dateIterator = rule.getDateIterator(value);

        return StreamSupport.stream(Spliterators.spliteratorUnknownSize(dateIterator, Spliterator.ORDERED), false)
                .limit(MAX_DATE_STEPS)
                .map(date -> new TodoItem(date, event.getSummary().getValue()));

    }

    private Stream<TodoItem> toTodoItem(VEvent event) {
        java.util.Date date = Date.from(event.getDateStart().getValue().toInstant());
        String value = event.getSummary().getValue();

        return Stream.of(new TodoItem(date, value));
    }
}
