package tr.edu.gsu.sine.out;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.Formatter;
import java.util.logging.LogRecord;

/**
 * Provides support for formatting log records with time-stamp and log level.
 * 
 * @see tr.edu.gsu.sine.Sine#initLogHandler()
 */
public class SineLogFormatter extends Formatter {

	/**
	 * Time-stamp's format.
	 */
	private static final DateFormat dateFormat = new SimpleDateFormat(
			"yyyy/MM/dd HH:mm:ss");
	
	/**
	 * Format the given log record and return the formatted string.
	 * 
	 * @see java.util.logging.Formatter#format(java.util.logging.LogRecord)
	 */
	@Override
	public String format(LogRecord record) {
		// Create a StringBuffer to contain the formatted record
		// start with the date.
		StringBuffer sb = new StringBuffer();

		// Get the date from the LogRecord and add it to the buffer
		Date date = new Date(record.getMillis());
		sb.append(dateFormat.format(date));
		sb.append(" ");

		// Get the level name and add it to the buffer
		sb.append(record.getLevel().getName());
		sb.append(" ");

		// Get the formatted message (includes localization
		// and substitution of parameters) and add it to the buffer
		sb.append(formatMessage(record));
		sb.append(System.getProperty("line.separator"));

		return sb.toString();
	}
}
