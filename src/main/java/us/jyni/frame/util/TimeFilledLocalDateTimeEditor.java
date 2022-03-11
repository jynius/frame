/*
 * 
 */
package us.jyni.frame.util;

import java.beans.PropertyEditorSupport;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.lang.Nullable;

/**
 * @author jynius
 * @Since 2020-08-05
 */
public class TimeFilledLocalDateTimeEditor extends PropertyEditorSupport {

	private static final Logger LOG = LoggerFactory.getLogger(TimeFilledLocalDateTimeEditor.class);
	private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss");

	private String fixedTime;

	public TimeFilledLocalDateTimeEditor(String fixedTime) {
		this.fixedTime = "T".concat(fixedTime);
	}

	/**
	 * Format the Date as String, using the specified DateFormat.
	 */
	@Override
	public String getAsText() {
		LocalDateTime value = (LocalDateTime) getValue();
		return value == null ? null: FORMATTER.format(value).substring(0, 10);
	}

	/**
	 * Parse the Date from the given text, using the specified DateFormat.
	 */
	@Override
	public void setAsText(@Nullable String text) throws IllegalArgumentException {

		LocalDateTime value = null;
		if(text!=null && text.isEmpty()) {
			
			try {
				value = LocalDateTime.parse(text.concat(fixedTime), FORMATTER);
			}
			catch (DateTimeParseException e) {
				LOG.error("Illegal Format LocalDateTime: {}", text, e);
				throw new IllegalArgumentException("Illegal Format LocalDateTime: " + text, e);
			}
		}

		setValue(value);
	}
}
