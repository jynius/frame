/*
 * 
 */
package us.jyni.frame.util;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.ResolverStyle;

import javax.persistence.AttributeConverter;

/**
 * @author jynius
 * @Since 2020-08-05
 */
public class LocalDateTimeConverter implements AttributeConverter<String, LocalDateTime> {
	
	private final DateTimeFormatter dateFormat;

	public LocalDateTimeConverter() {
		this.dateFormat = DateTimeFormatter
				.ofPattern("yyyy-MM-dd")
				.withResolverStyle(ResolverStyle.SMART);
	}
	
	@Override
	public LocalDateTime convertToDatabaseColumn(String attribute) {
		return LocalDateTime.parse(attribute, this.dateFormat);
	}

	@Override
	public String convertToEntityAttribute(LocalDateTime value) {
		return this.dateFormat.format(value);
	}
}
