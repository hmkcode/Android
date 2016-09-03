package sample.contentprovider.com.content_provider;

import org.json.JSONException;
import org.json.JSONObject;

class VersionModelParser {


		public VersionModelParser() {
		}

		public VersionModel parseVersionModel(String json_object) {

			VersionModel local_model = null;
			try {
					JSONObject jsobj = new JSONObject(json_object);

					local_model = new VersionModel(jsobj.getInt("sessions_ver") , jsobj.getInt("microlocations_ver") , jsobj.getInt("speakers_ver") , jsobj.getInt("tracks_ver") , jsobj.getInt("event_ver") , jsobj.getInt("sponsors_ver") , );
 			} 
			catch (JSONException e){

 				 e.printStackTrace();
			}

			return local_model;
		}
			
}