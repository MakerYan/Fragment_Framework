package com.makeryan.lib.record.audio;

public class Bell {

	private String bellName;

	private String bellPath;

	public String getBellName() {

		return bellName;
	}

	public void setBellName(String bellName) {

		this.bellName = bellName;
	}

	public String getBellPath() {

		return bellPath;
	}

	public void setBellPath(String bellPath) {

		this.bellPath = bellPath;
	}

	@Override
	public int hashCode() {

		final int prime  = 31;
		int       result = 1;
		result = prime * result + ((bellName == null) ?
				0 :
				bellName.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {

		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (getClass() != obj.getClass()) {
			return false;
		}
		Bell other = (Bell) obj;
		if (bellName == null) {
			if (other.bellName != null) {
				return false;
			}
		} else if (!bellName.equals(other.bellName)) {
			return false;
		}
		return true;
	}

}
