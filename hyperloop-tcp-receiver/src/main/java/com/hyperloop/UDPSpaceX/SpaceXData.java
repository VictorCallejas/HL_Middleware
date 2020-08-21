package com.hyperloop.UDPSpaceX;

class SpaceXData {

	private int team_id;
	private int status;
	private int acceleration;
	private int position;
	private int velocity;
	private int battery_voltage;
	private int battery_current;
	private int battery_temperature;
	private int pod_temperature;
	private int stripe_count;
	
	public SpaceXData() {
		super();
	}
	
	/**
	 * @param team_id
	 * @param status
	 * @param accel
	 * @param position
	 * @param velocity
	 * @param voltage
	 * @param current
	 * @param bat_temp
	 * @param pod_temp
	 * @param stripe_count
	 */
	public SpaceXData(int team_id, int status, int acceleration, int position, int velocity) {
		super();
		this.team_id = team_id;
		this.status = status;
		this.acceleration = acceleration;
		this.position = position;
		this.velocity = velocity;
		this.battery_voltage = 0;
		this.battery_current = 0;
		this.battery_temperature = 0;
		this.pod_temperature = 0;
		this.stripe_count = 0;
	}
	public int getTeam_id() {
		return team_id;
	}
	public void setTeam_id(int team_id) {
		this.team_id = team_id;
	}
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	public int getAcceleration() {
		return acceleration;
	}
	public void setAcceleration(int accel) {
		this.acceleration = accel;
	}
	public int getPosition() {
		return position;
	}
	public void setPosition(int position) {
		this.position = position;
	}
	public int getVelocity() {
		return velocity;
	}
	public void setVelocity(int velocity) {
		this.velocity = velocity;
	}
	public int getVoltage() {
		return battery_voltage;
	}
	public void setVoltage(int voltage) {
		this.battery_voltage = voltage;
	}
	public int getCurrent() {
		return battery_current;
	}
	public void setCurrent(int current) {
		this.battery_current = current;
	}
	public int getBat_temp() {
		return battery_temperature;
	}
	public void setBat_temp(int bat_temp) {
		this.battery_temperature = bat_temp;
	}
	public int getPod_temp() {
		return pod_temperature;
	}
	public void setPod_temp(int pod_temp) {
		this.pod_temperature = pod_temp;
	}
	public int getStripe_count() {
		return stripe_count;
	}
	public void setStripe_count(int stripe_count) {
		this.stripe_count = stripe_count;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + acceleration;
		result = prime * result + battery_temperature;
		result = prime * result + battery_current;
		result = prime * result + pod_temperature;
		result = prime * result + position;
		result = prime * result + status;
		result = prime * result + stripe_count;
		result = prime * result + team_id;
		result = prime * result + velocity;
		result = prime * result + battery_voltage;
		return result;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		SpaceXData other = (SpaceXData) obj;
		if (acceleration != other.acceleration)
			return false;
		if (battery_temperature != other.battery_temperature)
			return false;
		if (battery_current != other.battery_current)
			return false;
		if (pod_temperature != other.pod_temperature)
			return false;
		if (position != other.position)
			return false;
		if (status != other.status)
			return false;
		if (stripe_count != other.stripe_count)
			return false;
		if (team_id != other.team_id)
			return false;
		if (velocity != other.velocity)
			return false;
		if (battery_voltage != other.battery_voltage)
			return false;
		return true;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "SpaceXData [team_id=" + team_id + ", status=" + status + ", acceleration=" + acceleration + ", position=" + position
				+ ", velocity=" + velocity + ", voltage=" + battery_voltage + ", current=" + battery_current + ", bat_temp=" + battery_temperature
				+ ", pod_temp=" + pod_temperature + ", stripe_count=" + stripe_count + "]";
	}
}