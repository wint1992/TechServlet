package ru.chuchalin.tech.model;

import static ru.chuchalin.tech.model.TransformData.transformTimestamp;

import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.util.Collection;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.xml.datatype.XMLGregorianCalendar;

public class Event {
	protected Integer eventID;
	protected String eventName;
	protected EventAddress eventAddress;
	protected Date beginDateTime;
	protected Date endDateTime;
	protected String description;
	protected String comment;
	protected String uri;
	protected String backgroundPhoto;
	protected BigDecimal cost;
	protected Integer priority;

	protected Set<EventMusicStyle> eventMusicStyles;
	protected boolean fake;

	public Integer getEventID() {
		return eventID;
	}

	public void setEventID(Integer eventID) {
		this.eventID = eventID;
	}

	public String getEventName() {
		return eventName;
	}

	public void setEventName(String eventName) {
		this.eventName = eventName;
	}

	public EventAddress getEventAddress() {
		return eventAddress;
	}

	public void setEventAddress(EventAddress eventAddress) {
		this.eventAddress = eventAddress;
	}

	public String getBeginDateTime() {
		XMLGregorianCalendar _beginDateTime = transformTimestamp(beginDateTime);
		if (_beginDateTime != null)
			return _beginDateTime.toString();
		else
			return null;
	}

	public Date getBeginDateTimeAsDate() {
		return beginDateTime;
	}

	public void setBeginDateTime(Date beginDateTime) {
		this.beginDateTime = beginDateTime;
	}

	public String getEndDateTime() {
		XMLGregorianCalendar _endDateTime = transformTimestamp(endDateTime);
		if (_endDateTime != null)
			return _endDateTime.toString();
		else
			return null;
	}

	public Date getEndDateTimeAsDate() {
		return endDateTime;
	}

	public void setEndDateTime(Date endDateTime) {
		this.endDateTime = endDateTime;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

	public String getUri() {
		return uri;
	}

	public void setUri(String uri) {
		this.uri = uri;
	}

	public String getBackgroundPhoto() {
		return backgroundPhoto;
	}

	public void setBackgroundPhoto(String backgroundPhoto) {
		this.backgroundPhoto = backgroundPhoto;
	}

	public BigDecimal getCost() {
		return cost;
	}

	public void setCost(BigDecimal cost) {
		this.cost = cost;
	}

	public Integer getPriority() {
		return priority;
	}

	public void setPriority(Integer priority) {
		this.priority = priority;
	}

	public Set<EventMusicStyle> getEventMusicStyles() {
		if (eventMusicStyles == null)
			eventMusicStyles = new HashSet<>();
		return eventMusicStyles;
	}

	public void setEventMusicStyles(Set<EventMusicStyle> eventMusicStyles) {
		this.eventMusicStyles = eventMusicStyles;
	}

	public String toString() {
		StringBuilder sb = new StringBuilder("{");
		try {
			boolean hasFirstProperty = false;
			Field[] fields = this.getClass().getDeclaredFields();
			for (int i = 0; i < fields.length - 1; i++) {
				if (fields[i].get(this) != null) {
					if (fields[i].getType().equals(String.class)) {
						if (hasFirstProperty)
							sb.append(",");
						sb.append("\"").append(fields[i].getName()).append("\"").append(": \"")
								.append(fields[i].get(this).toString()).append("\"");
						hasFirstProperty = true;
					} else if (fields[i].getType().equals(Date.class)) {
						if (hasFirstProperty)
							sb.append(",");
						sb.append("\"").append(fields[i].getName()).append("\"").append(": \"")
								.append(transformTimestamp((Date) fields[i].get(this)).toString()).append("\"");
						hasFirstProperty = true;
					} else if (fields[i].getType().equals(Set.class) || fields[i].getType().equals(List.class)) {
						if (((Collection) fields[i].get(this)).size() > 0) {
							if (hasFirstProperty)
								sb.append(",");
							sb.append("\"").append(fields[i].getName()).append("\"").append(": ")
									.append(fields[i].get(this).toString());
							hasFirstProperty = true;
						}
					} else {
						if (hasFirstProperty)
							sb.append(",");
						sb.append("\"").append(fields[i].getName()).append("\"").append(": ")
								.append(fields[i].get(this).toString());
						hasFirstProperty = true;
					}
				}
			}
		} catch (Exception e) {
			sb.append("null");
		}
		return sb.append("}").toString();
	}
}
