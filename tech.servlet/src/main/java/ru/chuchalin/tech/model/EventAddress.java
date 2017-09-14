package ru.chuchalin.tech.model;

import static ru.chuchalin.tech.model.TransformData.transformTimestamp;

import java.lang.reflect.Field;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Set;

public class EventAddress {
	protected Integer eventAddressID;
	protected String place;
	protected City city;
	protected String street;
	protected String house;
	protected String building;
	protected String construction;
	protected String fullAddress;
	protected String gps;
	protected boolean fake;

	public Integer getEventAddressID() {
		return eventAddressID;
	}

	public void setEventAddressID(Integer eventAddressID) {
		this.eventAddressID = eventAddressID;
	}

	public String getPlace() {
		return place;
	}

	public void setPlace(String place) {
		this.place = place;
	}

	public City getCity() {
		return city;
	}

	public void setCity(City city) {
		this.city = city;
	}

	public String getStreet() {
		return street;
	}

	public void setStreet(String street) {
		this.street = street;
	}

	public String getHouse() {
		return house;
	}

	public void setHouse(String house) {
		this.house = house;
	}

	public String getBuilding() {
		return building;
	}

	public void setBuilding(String building) {
		this.building = building;
	}

	public String getConstruction() {
		return construction;
	}

	public void setConstruction(String construction) {
		this.construction = construction;
	}

	public String getFullAddress() {
		return fullAddress;
	}

	public void setFullAddress(String fullAddress) {
		this.fullAddress = fullAddress;
	}

	public String getGps() {
		return gps;
	}

	public void setGps(String gps) {
		this.gps = gps;
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
