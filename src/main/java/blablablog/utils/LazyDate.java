package blablablog.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author max
 */
public class LazyDate {
    public static final String DATE_FORMAT = "dd.MM.yyyy HH:mm";

    public static String getNewDate(String fomat) {
        return new SimpleDateFormat(fomat).format(new Date());
    }

    public static String formatDate(Date date, String format) {
        return new SimpleDateFormat(format).format(date);
    }

    public static String formatDate(String date, String fomatFrom, String formatTo) throws ParseException {
        if (date == null) {
            return null;
        }
        Date sdf = new SimpleDateFormat(fomatFrom).parse(date);
        SimpleDateFormat dateFormat = new SimpleDateFormat(formatTo);

        return dateFormat.format(sdf);
    }

    public static Date getDateFromUnixTimestamp(int date) {
        long yourmilliseconds = ((long) date) * 1000;
        return new Date(yourmilliseconds);
    }

    public static String getDate(String format) {
        SimpleDateFormat dateFormat = new SimpleDateFormat(format);
        return dateFormat.format(new Date());
    }

    public static int getUnixTimestamp() {
        return (int) (System.currentTimeMillis() / 1000);
    }

    public static long getUnixTimestampLong() {
        return System.currentTimeMillis();
    }

    public static long getUnixTimestamp(final String date, final String format) throws ParseException {
        final SimpleDateFormat formatter = new SimpleDateFormat(format);
        return formatter.parse(date).getTime() / 1000;
    }

    public static int getIntUnixTimestamp(final String date, final String format) throws ParseException {
        final SimpleDateFormat formatter = new SimpleDateFormat(format);
        return (int) (formatter.parse(date).getTime() / 1000);
    }

    /**
     * Get date by timestamp
     *
     * @param timestamp long unix timestamp
     * @return string date
     */
    public static String getDateByTimeStamp(long timestamp) {
        return LazyDate.formatDate(new Date(timestamp), DATE_FORMAT);
    }

    /**
     * Get date by timestamp
     *
     * @param timestamp int unix timestamp
     * @return string date
     */
    public static String getDateByTimeStamp(int timestamp) {
        return LazyDate.formatDate(new Date((long) timestamp * 1000), DATE_FORMAT);
    }

    /**
     * Convert date to unix timestamp to date string
     *
     * @param timestamp  timestamp
     * @param dateFormat date format
     * @return string date by unix timestamp
     */
    public static String getDateByTimeStamp(long timestamp, String dateFormat) {
        return LazyDate.formatDate(new Date(timestamp), dateFormat);
    }

    /**
     * Convert date to unix timestamp to date string
     *
     * @param timestamp  timestamp
     * @param dateFormat date format
     * @return string date by unix timestamp
     */
    public static String getDateByTimeStamp(int timestamp, String dateFormat) {
        return LazyDate.formatDate(new Date((long) timestamp * 1000), dateFormat);
    }

    /**
     * Get date from string by format
     *
     * @param format date format
     * @param date   string date
     * @return date
     * @throws ParseException
     */
    public static Date getDateFromString(String format, String date) throws ParseException {
        SimpleDateFormat formatter = new SimpleDateFormat(format);
        return formatter.parse(date);
    }

    /**
     * Calc time ago from create timestamp
     *
     * @param createTimeStamp create timestamp
     * @return time ago
     */
    public static String calcTimeAgo(int createTimeStamp) {
        createTimeStamp = getUnixTimestamp() - createTimeStamp;
        if (createTimeStamp <= 0) {
            return "NOW";
        }
        if (createTimeStamp < 60) {
            return createTimeStamp + " seconds ago";
        }
        if (createTimeStamp < 3600) {
            return (createTimeStamp / 60) + " minutes ago";
        }
        if (createTimeStamp < 86400 * 2) {
            return (createTimeStamp / 3600) + " hours ago";
        }
        if (createTimeStamp >= 86400 * 2) {
            return (createTimeStamp / 86400) + " days ago";
        }
        return "long time ago";
    }

    /**
     * Calc time ago from create timestamp
     *
     * @param createTimeStamp create timestamp
     * @return time ago
     */
    public static String calcTimeAgo(long createTimeStamp) {
        createTimeStamp = getUnixTimestamp() - ((int) (createTimeStamp / 1000));
        if (createTimeStamp <= 0) {
            return "NOW";
        }
        if (createTimeStamp < 60) {
            return createTimeStamp + " seconds ago";
        }
        if (createTimeStamp < 3600) {
            return (createTimeStamp / 60) + " minutes ago";
        }
        if (createTimeStamp < 86400 * 2) {
            return (createTimeStamp / 3600) + " hours ago";
        }
        if (createTimeStamp >= 86400 * 2) {
            return (createTimeStamp / 86400) + " days ago";
        }
        return "long time ago";
    }

    /**
     * Calc days from create timestamp
     *
     * @param createTimeStamp unix create timestamp
     * @return amount days
     */
    public static int getDaysLostFromDate(int createTimeStamp) {
        createTimeStamp = getUnixTimestamp() - createTimeStamp;
        return createTimeStamp / 86400;
    }
}
