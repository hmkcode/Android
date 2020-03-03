package sample.contentprovider.com.content_provider;

class CopyrightModel {

	public String holder;
	public String licence;
	public String logo;
	public int year;
	public String licence_url;
	public String holder_url;

	public CopyrightModel(String holder, String licence, String logo, int year, String licence_url, String holder_url) {

		this.holder = holder;
		this.licence = licence;
		this.logo = logo;
		this.year = year;
		this.licence_url = licence_url;
		this.holder_url = holder_url;

	}

	public String getHolder() {
		return holder;
	}

	public void setHolder(String holder) {
		this.holder = holder;
	}

	public String getLicence() {
		return licence;
	}

	public void setLicence(String licence) {
		this.licence = licence;
	}

	public String getLogo() {
		return logo;
	}

	public void setLogo(String logo) {
		this.logo = logo;
	}

	public int getYear() {
		return year;
	}

	public void setYear(int year) {
		this.year = year;
	}

	public String getLicence_url() {
		return licence_url;
	}

	public void setLicence_url(String licence_url) {
		this.licence_url = licence_url;
	}

	public String getHolder_url() {
		return holder_url;
	}

	public void setHolder_url(String holder_url) {
		this.holder_url = holder_url;
	}
}