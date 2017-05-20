package javaBackend;

import java.util.ArrayList;

public class Region {
	private ArrayList<String> provinces;
	private String regionName;
	public Region(ArrayList<String> provinces, String regionName) {
		this.provinces = provinces;
		this.regionName = regionName;
	}
	public ArrayList<String> getProvinces() {
		return provinces;
	}
	public void setProvinces(ArrayList<String> provinces) {
		this.provinces = provinces;
	}
	public String getRegionName() {
		return regionName;
	}
	public void setRegionName(String regionName) {
		this.regionName = regionName;
	}
	
}
