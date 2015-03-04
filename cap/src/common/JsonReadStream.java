package common;

import java.util.ArrayList;
import java.util.HashMap;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.util.Log;

public class JsonReadStream {

	public static String[] blockTableJSON = new String[6];
	public static String[] tmp = new String[2];
	public static String[] userChallengerMarker = {"void", "data", "count", "caps", "challenges"};
	public static String LocalMarker;
	public static final int MARKER_DATA = 1;
	public static final int MARKER_COUNT = 2;
	public static final int MARKER_CAPS = 3;
	public static final int MARKER_CHALLENGE = 4;
	private static final String IMGURI = "http://delmasromain.com/Ecoles/ESGI/Projets/cap7/uploads/participation/";
	
	public static String JSONStringRemoveMarker(String string, String Marker){
		String jsonString = "";
		
		String[] separatedStart = string.split(",  \""+Marker+"\"");
		jsonString = separatedStart[0];
		jsonString += "}]}";
		
		return jsonString;
	}
	
	public static String stringToJSONString(String string, String Marker){
		String jsonString = "";
		String[] separatedStart = string.split("\\{");
		Boolean first = true;
		for (int i = 1; i < separatedStart.length; i++) {
			String[] separatedEnd = separatedStart[i].split("\\}");
			if(first == true){
				jsonString += "{"+Marker+":[{"+separatedEnd[0]+"}";
				first = false;
			}else{
				jsonString += ",{"+separatedEnd[0]+"}";
			}
			
		}
		jsonString += "]}";
		return jsonString; 
	}

	public static ArrayList<HashMap<String, String>> jsonUserDataParsing(String jsonStr, String Marker){
		try {
			String[] tmp = new String[2];
			
			String[] dataBlocks = jsonStr.split("\\{");
			String userData = "{data:[";
			for (int i = 0; i < 3; i++) {
				if(i == 0 || i == 1) { continue; }
				tmp = dataBlocks[i].split("\\}");
				userData += "{"+tmp[0]+"}]}";
			}

			String userCount = "{count:[";
			for (int i = 0; i < 4; i++) {
				if(i == 0 || i == 1 || i == 2) { continue; }
				tmp = dataBlocks[i].split("\\}");
				userCount += "{"+tmp[0].replaceAll("\"challenge\"\\:", "")+"}]}";
			}

			ArrayList<HashMap<String, String>> dataList = new ArrayList<HashMap<String, String>>();
			JSONObject jsonObj = new JSONObject(userData);
			JSONArray Datas = jsonObj.getJSONArray("data");

			for (int i = 0; i < Datas.length(); i++) {
				JSONObject c = Datas.getJSONObject(i);

				String id = c.getString("id");
				String username = c.getString("username");
				String firstname = c.getString("firstname");
				String lastname = c.getString("lastname");
				String email = c.getString("email");

				HashMap<String, String> dataMap = new HashMap<String, String>();

				dataMap.put("id", id);
				dataMap.put("username", username);
				dataMap.put("firstname", firstname);
				dataMap.put("lastname", lastname);
				dataMap.put("email", email);

				dataList.add(dataMap);
			}

			jsonObj = new JSONObject(userCount);
			Datas = jsonObj.getJSONArray("count");

			for (int i = 0; i < Datas.length(); i++) {
				JSONObject c = Datas.getJSONObject(i);

				String totalCount = c.getString("totalCount");

				HashMap<String, String> dataMap = new HashMap<String, String>();

				dataMap.put("totalCount", totalCount);

				dataList.add(dataMap);
			}
			
			return dataList;
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public static ArrayList<HashMap<String, String>> jsonUserDataChallengeParsing(String jsonStr, String Marker){
		try {
			String[] tmp = new String[2];
			
			Boolean switcher = false;
			String[] dataBlocks = jsonStr.split("\\{");
			String nJSONstr = "{data:[";
			for (int i = 0; i < dataBlocks.length; i++) {
				if(i == 0 || i == 1 || i == 2 || i == 3) { continue; }
				tmp = dataBlocks[i].split("\\}");
				if (switcher == false) {
					nJSONstr += "{"+tmp[0].replaceAll("\"challenge\"\\:", "");
					switcher = true;
				}else{
					if(i < dataBlocks.length - 1){
						nJSONstr += tmp[0].replaceAll("\"challenge\"\\:", "")+"},";
					}else{
						nJSONstr += tmp[0].replaceAll("\"challenge\"\\:", "")+"}";
					}
					switcher = false;
				}
			}
			nJSONstr += "]}";

			ArrayList<HashMap<String, String>> dataList = new ArrayList<HashMap<String, String>>();
			JSONObject jsonObj = new JSONObject(nJSONstr);

			JSONArray Datas = jsonObj.getJSONArray("data");

			for (int i = 0; i < Datas.length(); i++) {
				JSONObject c = Datas.getJSONObject(i);

				String id = c.getString("id");
				String author = c.getString("author");
				String description = c.getString("description");
				String image = IMGURI+""+id+"_"+author+".jpg";
				String countCap = c.getString("countCap");
				String countPasCap = c.getString("countPasCap");
				String createdAt = c.getString("createdAt");

				if(author.equals(MyApp.getCurUserId())){
					author = MyApp.getUserName();
				}
				
				HashMap<String, String> dataMap = new HashMap<String, String>();

				dataMap.put("id", id);
				dataMap.put("author", author);
				dataMap.put("description", description);
				dataMap.put("image", image);
				dataMap.put("countCap", countCap);
				dataMap.put("countPasCap", countPasCap);
				dataMap.put("createdAt", createdAt);

				dataList.add(dataMap);
			}
			
			return dataList;
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	
	public static ArrayList<HashMap<String, String>> jsonCathegoriesChallengeParsing(String jsonStr, String Marker){
		try {
			String[] tmp = new String[10];
			String[] dataBlocks2;
			
			String[] dataBlocks = jsonStr.split("username");
			ArrayList<String> userBlocks = new ArrayList<String>();
			for (int i = 0; i < dataBlocks.length; i++) {
				if(i == 0) { continue; }
				tmp = dataBlocks[i].split(",");
				userBlocks.add(tmp[0].toString().replaceAll("\"", "").replaceAll("\\:", "").replaceAll(" ", ""));
			}
			
			dataBlocks = jsonStr.split("id");
			ArrayList<String> ucidBlocks = new ArrayList<String>();
			Boolean pair = true;
			for (int i = 0; i < dataBlocks.length; i++) {
				if(i == 0) { continue; }
				if (pair == true) {
					for (int z = 0; z < dataBlocks.length; z++) {
						if(z == 0) { continue; }
						tmp = dataBlocks[z].split(",");
						ucidBlocks.add(tmp[0].toString().replaceAll("\"", "").replaceAll("\\:", "").replaceAll(" ", ""));
						break ;
					}
					pair = false;
				}else{ pair = true; continue; }
			}
			
			dataBlocks = jsonStr.split("description");
			ArrayList<String> descriptionBlocks = new ArrayList<String>();
			for (int i = 0; i < dataBlocks.length; i++) {
				if(i == 0) { continue; }
				tmp = dataBlocks[i].split(",");
				//descriptionBlocks.add(tmp[0].toString().replaceAll("\"", "").replaceAll("\\:", "").replaceAll(" ", ""));
				tmp[0] = tmp[0].toString().replaceAll("\"", "").replaceAll("\\:", "");
				descriptionBlocks.add("\""+tmp[0]+"\"");
			}
			
			dataBlocks = jsonStr.split("challenge");
			ArrayList<String> challengeIdBlocks = new ArrayList<String>();
			for (int j = 0; j < dataBlocks.length; j++) {
				if(j == 0) { continue; }
				dataBlocks2 = dataBlocks[j].split("id");
				for (int z = 0; z < dataBlocks2.length; z++) {
					if(z == 0) { continue; }
					tmp = dataBlocks2[z].split("\\}");
					challengeIdBlocks.add(tmp[0].toString().replaceAll("\"", "").replaceAll("\\:", "").replaceAll(" ", ""));
					break ;
				}
			}
			
			dataBlocks = jsonStr.split("media");
			ArrayList<String> mediaBlocks = new ArrayList<String>();
			for (int i = 0; i < dataBlocks.length; i++) {
				if(i == 0) { continue; }
				tmp = dataBlocks[i].split(",");
				mediaBlocks.add(tmp[0].toString().replaceAll("\"", "").replaceAll("\\:", "").replaceAll(" ", ""));
			}
			
			dataBlocks = jsonStr.split("username");
			ArrayList<String> userIdBlocks = new ArrayList<String>();
			for (int i = 0; i < dataBlocks.length; i++) {
				if(i == 0) { continue; }
				dataBlocks2 = dataBlocks[i].split("id");
				for (int j = 0; j < dataBlocks2.length; j++) {
					if(j == 0) { continue; }
					tmp = dataBlocks2[j].split("\\}");
					userIdBlocks.add(tmp[0].toString().replaceAll("\"", "").replaceAll("\\:", "").replaceAll(" ", ""));
					break;
				}
			}
			
			dataBlocks = jsonStr.split("countCap");
			ArrayList<String> countCapBlocks = new ArrayList<String>();
			for (int i = 0; i < dataBlocks.length; i++) {
				if(i == 0) { continue; }
				tmp = dataBlocks[i].split(",");
				countCapBlocks.add(tmp[0].toString().replaceAll("\"", "").replaceAll("\\:", "").replaceAll(" ", ""));
			}
			
			dataBlocks = jsonStr.split("countPasCap");
			ArrayList<String> countPasCapBlocks = new ArrayList<String>();
			for (int i = 0; i < dataBlocks.length; i++) {
				if(i == 0) { continue; }
				tmp = dataBlocks[i].split(",");
				countPasCapBlocks.add(tmp[0].toString().replaceAll("\"", "").replaceAll("\\:", "").replaceAll(" ", ""));
			}
			
			dataBlocks = jsonStr.split("createdAt");
			ArrayList<String> createdAtBlocks = new ArrayList<String>();
			for (int i = 0; i < dataBlocks.length; i++) {
				if(i == 0) { continue; }
				tmp = dataBlocks[i].split(",");
				createdAtBlocks.add(tmp[0].toString().replaceAll("\"", "").replaceAll("\\:", "").replaceAll(" ", ""));
			}
			
			String datas = "{data:[";
			for (int j = 0; j < userBlocks.size(); j++) {
				datas += "{username:"+userBlocks.get(j)+", description:"+descriptionBlocks.get(j)+", ucid:"+ucidBlocks.get(j);
				datas += ", image:"+mediaBlocks.get(j)+", uid:"+userIdBlocks.get(j)+", cid:"+challengeIdBlocks.get(j);
				datas += ", countCap:"+countCapBlocks.get(j)+", countPasCap:"+countPasCapBlocks.get(j)+", createdAt:"+createdAtBlocks.get(j);
				if(j == userBlocks.size() - 1)
					datas += "}]}";
				else
					datas += "},";
			}
			
			ArrayList<HashMap<String, String>> dataList = new ArrayList<HashMap<String, String>>();
			JSONObject jsonObj = new JSONObject(datas);

			JSONArray Datas = jsonObj.getJSONArray("data");

			for (int i = 0; i < Datas.length(); i++) {
				JSONObject c = Datas.getJSONObject(i);

				String username = c.getString("username");
				String description = c.getString("description");
				String uid = c.getString("uid");
				String cid = c.getString("cid");
				String image = IMGURI+c.getString("image");
				String countCap = c.getString("countCap");
				String countPasCap = c.getString("countPasCap");
				String createdAt = c.getString("createdAt");
				String ucid = c.getString("ucid");
				
				HashMap<String, String> dataMap = new HashMap<String, String>();

				dataMap.put("username", username);
				dataMap.put("description", description);
				dataMap.put("image", image);
				dataMap.put("uid", uid);
				dataMap.put("cid", cid);
				dataMap.put("countCap", countCap);
				dataMap.put("countPasCap", countPasCap);
				dataMap.put("createdAt", createdAt);
				dataMap.put("ucid", ucid);

				dataList.add(dataMap);
			}
			
			return dataList;
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	
	public static ArrayList<HashMap<String, String>> jsonLastChallengeParsing(String jsonStr, String Marker){
		try {
			String[] tmp = new String[10];
			String[] dataBlocks2;
			
			String[] dataBlocks = jsonStr.split("username");
			ArrayList<String> userBlocks = new ArrayList<String>();
			for (int i = 0; i < dataBlocks.length; i++) {
				if(i == 0) { continue; }
				tmp = dataBlocks[i].split(",");
				userBlocks.add(tmp[0].toString().replaceAll("\"", "").replaceAll("\\:", "").replaceAll(" ", ""));
			}
			
			dataBlocks = jsonStr.split("id");
			ArrayList<String> ucidBlocks = new ArrayList<String>();
			Boolean pair = true;
			for (int i = 0; i < dataBlocks.length; i++) {
				if(i == 0) { continue; }
				if (pair == true) {
					for (int z = 0; z < dataBlocks.length; z++) {
						if(z == 0) { continue; }
						tmp = dataBlocks[z].split(",");
						ucidBlocks.add(tmp[0].toString().replaceAll("\"", "").replaceAll("\\:", "").replaceAll(" ", ""));
						break ;
					}
					pair = false;
				}else{ pair = true; continue; }
			}

			dataBlocks = jsonStr.split("description");
			ArrayList<String> descriptionBlocks = new ArrayList<String>();
			for (int i = 0; i < dataBlocks.length; i++) {
				if(i == 0) { continue; }
				tmp = dataBlocks[i].split("\\}");
				tmp[0] = tmp[0].toString().replaceAll("\"", "").replaceAll("\\:", "");
				descriptionBlocks.add("\""+tmp[0]+"\"");
				
			}
			
			dataBlocks = jsonStr.split("challenge");
			ArrayList<String> challengeIdBlocks = new ArrayList<String>();
			for (int j = 0; j < dataBlocks.length; j++) {
				if(j == 0) { continue; }
				dataBlocks2 = dataBlocks[j].split("id");
				for (int z = 0; z < dataBlocks2.length; z++) {
					if(z == 0) { continue; }
					tmp = dataBlocks2[z].split(",");
					challengeIdBlocks.add(tmp[0].toString().replaceAll("\"", "").replaceAll("\\:", "").replaceAll(" ", ""));
					break ;
				}
			}
			
			dataBlocks = jsonStr.split("media");
			ArrayList<String> mediaBlocks = new ArrayList<String>();
			for (int i = 0; i < dataBlocks.length; i++) {
				if(i == 0) { continue; }
				tmp = dataBlocks[i].split(",");
				mediaBlocks.add(tmp[0].toString().replaceAll("\"", "").replaceAll("\\:", "").replaceAll(" ", ""));
			}
			
			dataBlocks = jsonStr.split("countCap");
			ArrayList<String> countCapBlocks = new ArrayList<String>();
			for (int i = 0; i < dataBlocks.length; i++) {
				if(i == 0) { continue; }
				tmp = dataBlocks[i].split(",");
				countCapBlocks.add(tmp[0].toString().replaceAll("\"", "").replaceAll("\\:", "").replaceAll(" ", ""));
			}
			
			dataBlocks = jsonStr.split("countPasCap");
			ArrayList<String> countPasCapBlocks = new ArrayList<String>();
			for (int i = 0; i < dataBlocks.length; i++) {
				if(i == 0) { continue; }
				tmp = dataBlocks[i].split(",");
				countPasCapBlocks.add(tmp[0].toString().replaceAll("\"", "").replaceAll("\\:", "").replaceAll(" ", ""));
			}
			
			dataBlocks = jsonStr.split("createdAt");
			ArrayList<String> createdAtBlocks = new ArrayList<String>();
			for (int i = 0; i < dataBlocks.length; i++) {
				if(i == 0) { continue; }
				tmp = dataBlocks[i].split(",");
				createdAtBlocks.add(tmp[0].toString().replaceAll("\"", "").replaceAll("\\:", "").replaceAll(" ", ""));
			}
			
			dataBlocks = jsonStr.split("username");
			ArrayList<String> userIdBlocks = new ArrayList<String>();
			for (int i = 0; i < dataBlocks.length; i++) {
				if(i == 0) { continue; }
				dataBlocks2 = dataBlocks[i].split("id");
				for (int j = 0; j < dataBlocks2.length; j++) {
					if(j == 0) { continue; }
					tmp = dataBlocks2[j].split("\\}");
					userIdBlocks.add(tmp[0].toString().replaceAll("\"", "").replaceAll("\\:", "").replaceAll(" ", ""));
					break;
				}
			}
			
			String datas = "{data:[";
			for (int j = 0; j < userBlocks.size(); j++) {
				datas += "{username:"+userBlocks.get(j)+", description:"+descriptionBlocks.get(j)+", ucid:"+ucidBlocks.get(j);
				datas += ", image:"+mediaBlocks.get(j)+", uid:"+userIdBlocks.get(j)+", cid:"+challengeIdBlocks.get(j);
				datas += ", countCap:"+countCapBlocks.get(j)+", countPasCap:"+countPasCapBlocks.get(j)+", createdAt:"+createdAtBlocks.get(j);
				if(j == userBlocks.size() - 1)
					datas += "}]}";
				else
					datas += "},";
					
				
			}
			
			ArrayList<HashMap<String, String>> dataList = new ArrayList<HashMap<String, String>>();
			JSONObject jsonObj = new JSONObject(datas);

			JSONArray Datas = jsonObj.getJSONArray("data");

			for (int i = 0; i < Datas.length(); i++) {
				JSONObject c = Datas.getJSONObject(i);

				String username = c.getString("username");
				String description = c.getString("description");
				String uid = c.getString("uid");
				String cid = c.getString("cid");
				String image = IMGURI+c.getString("image");
				String countCap = c.getString("countCap");
				String countPasCap = c.getString("countPasCap");
				String createdAt = c.getString("createdAt");
				String ucid = c.getString("ucid");
				
				HashMap<String, String> dataMap = new HashMap<String, String>();

				dataMap.put("username", username);
				dataMap.put("description", description);
				dataMap.put("image", image);
				dataMap.put("uid", uid);
				dataMap.put("cid", cid);
				dataMap.put("countCap", countCap);
				dataMap.put("countPasCap", countPasCap);
				dataMap.put("createdAt", createdAt);
				dataMap.put("ucid", ucid);

				dataList.add(dataMap);
			}
			
			return dataList;
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	
	public static ArrayList<HashMap<String, String>> jsonCathegoryParsing(String jsonStr, String Marker){
		try {
			ArrayList<HashMap<String, String>> dataList = new ArrayList<HashMap<String, String>>();
			JSONObject jsonObj = new JSONObject(jsonStr);

			JSONArray Datas = jsonObj.getJSONArray(Marker);

			for (int i = 0; i < Datas.length(); i++) {
				JSONObject c = Datas.getJSONObject(i);

				String id = c.getString("id");
				String name = c.getString("name");

				HashMap<String, String> dataMap = new HashMap<String, String>();

				dataMap.put("id", id);
				dataMap.put("name", name);

				dataList.add(dataMap);
			}
			return dataList;
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public static ArrayList<HashMap<String, String>> jsonChallengeParsing(String jsonStr, String Marker){
		try {
			
			ArrayList<HashMap<String, String>> dataList = new ArrayList<HashMap<String, String>>();
			JSONObject jsonObj = new JSONObject(jsonStr);

			JSONArray Datas = jsonObj.getJSONArray(Marker);

			for (int i = 0; i < Datas.length(); i++) {
				JSONObject c = Datas.getJSONObject(i);

				String id = c.getString("id");
				String author = c.getString("author");
				String description = c.getString("description");

				HashMap<String, String> dataMap = new HashMap<String, String>();

				dataMap.put("id", id);
				dataMap.put("author", author);
				dataMap.put("description", description);

				dataList.add(dataMap);
			}
			return dataList;
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public static ArrayList<HashMap<String, String>> jsonCathegoryChallengeParsing(String jsonStr, String Marker){
		try {
			ArrayList<HashMap<String, String>> dataList = new ArrayList<HashMap<String, String>>();
			JSONObject jsonObj = new JSONObject(jsonStr);

			JSONArray Datas = jsonObj.getJSONArray(Marker);

			for (int i = 0; i < Datas.length(); i++) {
				JSONObject c = Datas.getJSONObject(i);

				String id = c.getString("id");
				String author = c.getString("author");
				String description = c.getString("description");

				HashMap<String, String> dataMap = new HashMap<String, String>();

				dataMap.put("id", id);
				dataMap.put("author", author);
				dataMap.put("description", description);

				dataList.add(dataMap);
			}
			return dataList;
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return null;
	}
}
