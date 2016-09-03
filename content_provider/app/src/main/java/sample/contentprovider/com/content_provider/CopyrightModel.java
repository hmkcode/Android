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

}