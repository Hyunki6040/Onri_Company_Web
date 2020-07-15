package poly.dto;

public class AdminDTO {

	private String user; //사용자 권한 확인
	private String id; //아이디
	private String pw; //비밀번호
	
	public String getUser() {
		return user;
	}
	public void setUser(String user) {
		this.user = user;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getPw() {
		return pw;
	}
	public void setPw(String pw) {
		this.pw = pw;
	}

}
