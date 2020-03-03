package sample.contentprovider.com.content_provider;

import org.json.JSONException;
import org.json.JSONObject;

class CreatorModelParser {


		public CreatorModelParser() {
		}

		public CreatorModel parseCreatorModel(String json_object) {

			CreatorModel local_model = null;
			try {
					JSONObject jsobj = new JSONObject(json_object);

					local_model = new CreatorModel(jsobj.getString("email") , );
 			} 
			catch (JSONException e){

 				 e.printStackTrace();
			}

			return local_model;
		}
			
}