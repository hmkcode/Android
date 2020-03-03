package sample.contentprovider.com.content_provider;

class Social_linksModel {

	public int id;
	public String link;
	public String name;

	public Social_linksModel(int id, String link, String name) {

		this.id = id;
		this.link = link;
		this.name = name;

	}


	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getLink() {
		return link;
	}

	public void setLink(String link) {
		this.link = link;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
}