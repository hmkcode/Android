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

	public CopyrightModel get_copyright() {
		return _copyright;
	}

	public void set_copyright(CopyrightModel _copyright) {
		this._copyright = _copyright;
	}

	public String getPrivacy() {
		return privacy;
	}

	public void setPrivacy(String privacy) {
		this.privacy = privacy;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public ArrayList<Social_linksModel> getSocial_links() {
		return social_links;
	}

	public void setSocial_links(ArrayList<Social_linksModel> social_links) {
		this.social_links = social_links;
	}

	public String getBackground_image() {
		return background_image;
	}

	public void setBackground_image(String background_image) {
		this.background_image = background_image;
	}

	public String getTimezone() {
		return timezone;
	}

	public void setTimezone(String timezone) {
		this.timezone = timezone;
	}

	public String getStart_time() {
		return start_time;
	}

	public void setStart_time(String start_time) {
		this.start_time = start_time;
	}

	public String getLocation_name() {
		return location_name;
	}

	public void setLocation_name(String location_name) {
		this.location_name = location_name;
	}

	public String getSchedule_published_on() {
		return schedule_published_on;
	}

	public void setSchedule_published_on(String schedule_published_on) {
		this.schedule_published_on = schedule_published_on;
	}

	public VersionModel get_version() {
		return _version;
	}

	public void set_version(VersionModel _version) {
		this._version = _version;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getEnd_time() {
		return end_time;
	}

	public void setEnd_time(String end_time) {
		this.end_time = end_time;
	}

	public String getOrganizer_name() {
		return organizer_name;
	}

	public void setOrganizer_name(String organizer_name) {
		this.organizer_name = organizer_name;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getLogo() {
		return logo;
	}

	public void setLogo(String logo) {
		this.logo = logo;
	}

	public Call_for_papersModel get_call_for_papers() {
		return _call_for_papers;
	}

	public void set_call_for_papers(Call_for_papersModel _call_for_papers) {
		this._call_for_papers = _call_for_papers;
	}

	public String getOrganizer_description() {
		return organizer_description;
	}

	public void setOrganizer_description(String organizer_description) {
		this.organizer_description = organizer_description;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public CreatorModel get_creator() {
		return _creator;
	}

	public void set_creator(CreatorModel _creator) {
		this._creator = _creator;
	}

	public String getTopic() {
		return topic;
	}

	public void setTopic(String topic) {
		this.topic = topic;
	}

	public String getCode_of_conduct() {
		return code_of_conduct;
	}

	public void setCode_of_conduct(String code_of_conduct) {
		this.code_of_conduct = code_of_conduct;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}
}