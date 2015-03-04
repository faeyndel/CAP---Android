package common;

public class User {
		private String username;
		private String firstname;
		private String lastname;
		private String email;
		private String password;
		private String picture;
		private String key;
		
		/* SETTERS */
		public void setUsername(String username){
			this.username = username;
		}
		public void setFirstname(String firstname){
			this.firstname = firstname;
		}
		public void setLastname(String lastname){
			this.lastname = lastname;
		}
		public void setEmail(String email){
			this.email = email;
		}
		public void setPicture(String picture){
			this.picture = picture;
		}
		public void setPassword(String password){
			this.password = password;
		}
		public void setKey(String key){
			this.key = key;
		}

		/* GETTERS */
		public String getUsername(){
			return this.username;
		}
		public String getFirstname(){
			return this.firstname;
		}
		public String getLastname(){
			return this.lastname;
		}
		public String getEmail(){
			return this.email;
		}
		public String getPicture(){
			return this.picture;
		}
		public String getPassword(){
			return this.password;
		}
		public String getKey(){
			return this.key;
		}
}
