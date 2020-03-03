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


	public int getSessions_ver() {
		return sessions_ver;
	}

	public void setSessions_ver(int sessions_ver) {
		this.sessions_ver = sessions_ver;
	}

	public int getMicrolocations_ver() {
		return microlocations_ver;
	}

	public void setMicrolocations_ver(int microlocations_ver) {
		this.microlocations_ver = microlocations_ver;
	}

	public int getSpeakers_ver() {
		return speakers_ver;
	}

	public void setSpeakers_ver(int speakers_ver) {
		this.speakers_ver = speakers_ver;
	}

	public int getTracks_ver() {
		return tracks_ver;
	}

	public void setTracks_ver(int tracks_ver) {
		this.tracks_ver = tracks_ver;
	}

	public int getEvent_ver() {
		return event_ver;
	}

	public void setEvent_ver(int event_ver) {
		this.event_ver = event_ver;
	}

	public int getSponsors_ver() {
		return sponsors_ver;
	}

	public void setSponsors_ver(int sponsors_ver) {
		this.sponsors_ver = sponsors_ver;
	}
}