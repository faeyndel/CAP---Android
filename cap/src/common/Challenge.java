package common;

public class Challenge {
	private String fileName;
	private String filePath;
	private String challengeName;
	private String challengeCathegory;
	private String challengeId;
	private String token;
	
	/* SETTERS */
	public void setFileName(String fileName){
		this.fileName = fileName;
	}
	public void setFilePath(String filePath){
		this.filePath = filePath;
	}
	public void setChallengeName(String challengeName){
		this.challengeName = challengeName;
	}
	public void setChallengeCathegory(String challengeCathegory){
		this.challengeCathegory = challengeCathegory;
	}
	public void setToken(String token){
		this.token = token;
	}
	public void setChallengeId(String challengeId){
		this.challengeId = challengeId;
	}
	
	/* GETTERS */
	public String getChallengeId(){
		return this.challengeId;
	}
	public String getFileName(){
		return this.fileName;
	}
	public String getFilePath(){
		return this.filePath;
	}
	public String getChallengeName(){
		return this.challengeName;
	}
	public String getChallengeCathegory(){
		return this.challengeCathegory;
	}
	public String getToken(){
		return this.token;
	}
}
