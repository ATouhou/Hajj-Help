package com.labbayak.model;

public class RowObject {


	private String date;
	private boolean firstChecked;
	public RowObject(String date, boolean firstChecked) {
		super();
		this.date=date;
		this.firstChecked = firstChecked;
	}

	public boolean isFirstChecked() {
		return firstChecked;
	}

	public void setFirstChecked(boolean firstChecked) {
		this.firstChecked = firstChecked;
	}

}
