package sample.contentprovider.com.content_provider;

import java.util.ArrayList;

class RootModel {

	public CopyrightModel _copyright;
	public String privacy;
	public String description;
	public String state;
	public ArrayList<Social_linksModel> social_links;
	public String background_image;
	public String timezone;
	public String start_time;
	public String location_name;
	public String schedule_published_on;
	public VersionModel _version;
	public int id;
	public String end_time;
	public String organizer_name;
	public String type;
	public String logo;
	public Call_for_papersModel _call_for_papers;
	public String organizer_description;
	public String name;
	public CreatorModel _creator;
	public String topic;
	public String code_of_conduct;
	public String email;

	public RootModel(CopyrightModel copyright, String privacy, String description, String state, ArrayList<Social_linksModel> social_links, String background_image, String timezone, String start_time, String location_name, String schedule_published_on, VersionModel version, int id, String end_time, String organizer_name, String type, String logo, Call_for_papersModel call_for_papers, String organizer_description, String name, CreatorModel creator, String topic, String code_of_conduct, String email) {

		this._copyright = copyright;
		this.privacy = privacy;
		this.description = description;
		this.state = state;
		this.social_links = social_links;
		this.background_image = background_image;
		this.timezone = timezone;
		this.start_time = start_time;
		this.location_name = location_name;
		this.schedule_published_on = schedule_published_on;
		this._version = version;
		this.id = id;
		this.end_time = end_time;
		this.organizer_name = organizer_name;
		this.type = type;
		this.logo = logo;
		this._call_for_papers = call_for_papers;
		this.organizer_description = organizer_description;
		this.name = name;
		this._creator = creator;
		this.topic = topic;
		this.code_of_conduct = code_of_conduct;
		this.email = email;

	}

}