package business_logic.working_days;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

public abstract class WorkingDays implements IWorkingDays {
    protected Map<DayOfWeek, Boolean> isWorkingDayMap = new HashMap<>();

    protected abstract void setupWorkingDays();

    public WorkingDays() {
        setupWorkingDays();
    }

    public LocalDate findFirstWorkingDate(LocalDate date) {

         if (isWorkingDayMap.values().stream().noneMatch(b -> b)) {
            return null;
        }

         return findFirstWorkingDateRec(date);
    }

    private LocalDate findFirstWorkingDateRec(LocalDate date) {
        final DayOfWeek inputDay = date.getDayOfWeek();

        if (isWorkingDayMap.get(inputDay)) {
            return date;
        } else {
            // otherwise look for the next working date
            return findFirstWorkingDateRec(date.plusDays(1));
        }
    }
}
