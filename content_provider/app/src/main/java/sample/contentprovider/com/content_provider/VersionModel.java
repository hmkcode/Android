package sample.contentprovider.com.content_provider;

class VersionModel {

	public int sessions_ver;
	public int microlocations_ver;
	public int speakers_ver;
	public int tracks_ver;
	public int event_ver;
	public int sponsors_ver;

	public VersionModel(int sessions_ver, int microlocations_ver, int speakers_ver, int tracks_ver, int event_ver, int sponsors_ver) {

		this.sessions_ver = sessions_ver;
		this.microlocations_ver = microlocations_ver;
		this.speakers_ver = speakers_ver;
		this.tracks_ver = tracks_ver;
		this.event_ver = event_ver;
		this.sponsors_ver = sponsors_ver;

	}

}