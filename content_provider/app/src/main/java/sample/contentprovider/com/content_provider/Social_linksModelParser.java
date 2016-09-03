package sample.contentprovider.com.content_provider;

import org.json.JSONException;
import org.json.JSONObject;

class Social_linksModelParser {


		public Social_linksModelParser() {
		}

		public Social_linksModel parseSocial_linksModel(String json_object) {

			Social_linksModel local_model = null;
			try {
					JSONObject jsobj = new JSONObject(json_object);

					local_model = new Social_linksModel(jsobj.getInt("id") , jsobj.getString("link") , jsobj.getString("name") , );
 			} 
			catch (JSONException e){

 				 e.printStackTrace();
			}

			return local_model;
		}
			
}