package marc.dashboard.efa.api;

import org.apache.commons.lang3.builder.ToStringBuilder;

import javax.xml.bind.annotation.*;
import java.util.Calendar;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType
public class DateTime {
    @XmlElementRef
    Date date;

    @XmlElementRef
    Time time;

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }

    public Date getDateComponent() {
        return date;
    }

    public Time getTimeComponent() {
        return time;
    }

    public java.util.Date getDate() {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.YEAR, getDateComponent().getYear());
        calendar.set(Calendar.MONTH, getDateComponent().getMonth() - 1);
        calendar.set(Calendar.DAY_OF_MONTH, getDateComponent().getDay());
        calendar.set(Calendar.HOUR_OF_DAY, getTimeComponent().getHour());
        calendar.set(Calendar.MINUTE, getTimeComponent().getMinute());
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);

        return calendar.getTime();
    }
}
