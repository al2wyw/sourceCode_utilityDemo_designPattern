package demo_test;

import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;
import org.joda.time.Period;
import org.joda.time.PeriodType;

public class joda_time {

	public static void main(String[] args) {
		DateTime now = new DateTime();
		System.out.println(now);
		DateTime then = now.plusDays(10);
		System.out.println(then);
		DateTime t = now.dayOfWeek().withMinimumValue();
		System.out.println(t);
		System.out.println(t.getDayOfMonth());
		DateTime nt = t.withZone(DateTimeZone.forOffsetHours(-8));
		System.out.println(nt.getDayOfMonth());
		//Period p = new Period(now, then) p.getDays returns 3???
		Period p = new Period(now, then, PeriodType.days());
		System.out.println(p.getDays());
	}

}
