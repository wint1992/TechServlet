package ru.chuchalin.tech.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;

public class TransformData implements Serializable {
	private static final long serialVersionUID = -4852071941436382433L;

	public static XMLGregorianCalendar transformDate(Date date) {
		if (date == null) {
			return null;
		}
		try {
			return DatatypeFactory.newInstance()
					.newXMLGregorianCalendar(new SimpleDateFormat("yyyy-MM-dd").format(date));
		} catch (DatatypeConfigurationException e) {
			return null;
		}
	}

	public static XMLGregorianCalendar transformTimestamp(Date date) {
		if (date == null) {
			return null;
		}
		try {
			return DatatypeFactory.newInstance()
					.newXMLGregorianCalendar(new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss").format(date));
		} catch (DatatypeConfigurationException e) {
			return null;
		}
	}

	public static Integer bigDecimalToInteger(BigDecimal value) {
		return value == null ? null : value.intValue();
	}

}
