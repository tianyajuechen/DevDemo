package com.tzy.demo.utils;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;

/**
 * @Author tangzhaoyang
 * @Date 2021/3/24
 */
public class DatePickUtil {

    public static final long ONE_HOUR = 60 * 60 * 1000;//一小时毫秒值
    public static final long TEN_MINUTE = 60 * 60 * 1000;//10分钟毫秒值

    /**
     * @param weeks    周一到周日 0,1,2,3,4,5,6
     * @param hours    22~2 6~6 19~23
     * @param interval 每条数据时间间隔
     * @param delay    从delay之后开始算
     * @return
     */
    public static List<DateDto> getList(List<Integer> weeks, List<Integer> hours, long interval, long delay) {
        List<DateDto> dateList = new ArrayList<>();
        Calendar calendar = Calendar.getInstance();
        long currMillis = calendar.getTimeInMillis() + delay;//下单时间至少要比当前时间晚delay秒
        /**
         * 周一到周日
         * Calendar格式：     2,3,4,5,6,7,1
         *              ↓ +5 % 7
         * 后台数据格式：      0,1,2,3,4,5,6
         * 后台前一天数据格式： 6,0,1,2,3,4,5
         *              ↑ +4 % 7
         * Calendar格式：     2,3,4,5,6,7,1
         */
        for (int i = 0; i < 7; i++) {
            List<ChildDto> childList = new ArrayList<>();
            int startHour = hours.get(0);
            int endHour = hours.get(1);
            if (startHour >= endHour) {
                //跨天 比如22:00~3:00，分隔成两部分 今天的22:00~24:00和明天的0:00~3:00
                if (weeks.contains((calendar.get(Calendar.DAY_OF_WEEK) + 4) % 7)) {
                    //昨天接单，今天需要加上昨天的 24:00 ~ endHour（即今天的0:00~3:00）
                    Calendar zeroCalendar = createHourCalendar(calendar, 0);
                    Calendar endHourCalendar = createHourCalendar(calendar, endHour);
                    for (long temp = zeroCalendar.getTimeInMillis(); temp < endHourCalendar.getTimeInMillis(); temp += interval) {
                        if (temp >= currMillis) {
                            childList.add(new ChildDto(temp));
                        }
                    }
                }
                if (weeks.contains((calendar.get(Calendar.DAY_OF_WEEK) + 5) % 7)) {
                    //今天接单，添加今天的start ~ 24:00 (22:00~24:00)
                    Calendar startHourCalendar = createHourCalendar(calendar, startHour);
                    Calendar Calendar24 = createHourCalendar(calendar, 24);
                    for (long temp = startHourCalendar.getTimeInMillis(); temp < Calendar24.getTimeInMillis(); temp += interval) {
                        if (temp >= currMillis) {
                            childList.add(new ChildDto(temp));
                        }
                    }
                }
            } else {
                //不跨天，只加今天开始到结束的时间，比如18:00~22:00
                if (weeks.contains((calendar.get(Calendar.DAY_OF_WEEK) + 5) % 7)) {
                    Calendar startHourCalendar = createHourCalendar(calendar, startHour);
                    Calendar endHourCalendar = createHourCalendar(calendar, endHour);
                    for (long temp = startHourCalendar.getTimeInMillis(); temp < endHourCalendar.getTimeInMillis(); temp += interval) {
                        if (temp >= currMillis) {
                            childList.add(new ChildDto(temp));
                        }
                    }
                }
            }
            if (!childList.isEmpty()) {
                DateDto dateDto = new DateDto();
                dateDto.setChild(childList);
                dateDto.setAvailable(true);
                dateDto.setDayMs(createHourCalendar(calendar, 0).getTimeInMillis());
                dateList.add(dateDto);
            }
            calendar.add(Calendar.DATE, 1);
        }
        printDateList(dateList);
        return dateList;
    }

    public static Calendar createHourCalendar(Calendar calendar, int hour) {
        return new GregorianCalendar(calendar.get(Calendar.YEAR)
                , calendar.get(Calendar.MONTH)
                , calendar.get(Calendar.DAY_OF_MONTH)
                , hour
                , 0
                , 0);
    }

    public static void printDateList(List<DateDto> list) {
        for (DateDto dateDto : list) {
            System.out.println("-------------------------" + DateUtils.formatDate(dateDto.dayMs, "yyyy-MM-dd") + "-------------------------");
            for (ChildDto childDto : dateDto.getChild()) {
                System.out.println(DateUtils.formatDate(childDto.ms, "MM月dd日 HH:mm"));
            }
        }
    }

    public static class DateDto {
        private long dayMs;//当天0点毫秒值
        private boolean available;
        List<ChildDto> child;

        public long getDayMs() {
            return dayMs;
        }

        public void setDayMs(long dayMs) {
            this.dayMs = dayMs;
        }

        public boolean isAvailable() {
            return available;
        }

        public void setAvailable(boolean available) {
            this.available = available;
        }

        public List<ChildDto> getChild() {
            return child;
        }

        public void setChild(List<ChildDto> child) {
            this.child = child;
        }
    }

    public static class ChildDto {
        private long ms;//毫秒值
        private boolean available;

        public ChildDto(long ms) {
            this(ms, true);
        }

        public ChildDto(long ms, boolean available) {
            this.ms = ms;
            this.available = available;
        }

        public long getMs() {
            return ms;
        }

        public void setMs(long ms) {
            this.ms = ms;
        }

        public boolean isAvailable() {
            return available;
        }

        public void setAvailable(boolean available) {
            this.available = available;
        }
    }
}
