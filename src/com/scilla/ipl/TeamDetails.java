package com.scilla.ipl;

public class TeamDetails {
	String country;
	String playerid;
	String playername;
	String type;

	public TeamDetails() {
	}

	public TeamDetails(String paramString1, String paramString2,
			String paramString3, String paramString4) {
		this.playername = paramString1;
		this.type = paramString2;
		this.country = paramString3;
		this.playerid = paramString4;
	}

	public String getCountry() {
		return this.country;
	}

	public String getPlayerid() {
		return this.playerid;
	}

	public String getPlayername() {
		return this.playername;
	}

	public String getType() {
		return this.type;
	}

	public void setCountry(String paramString) {
		this.country = paramString;
	}

	public void setPlayerid(String paramString) {
		this.playerid = paramString;
	}

	public void setPlayername(String paramString) {
		this.playername = paramString;
	}

	public void setType(String paramString) {
		this.type = paramString;
	}

	public String toString() {
		return this.playername;
	}
}