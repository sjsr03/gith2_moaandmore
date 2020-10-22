package budget.model.dto;

public class NoBudgetDetailDTO {
	private int nobudget_no;
	private String content;
	private String memo;
	private String img;
	
	public int getNobudget_no() {
		return nobudget_no;
	}
	public void setNobudget_no(int nobudget_no) {
		this.nobudget_no = nobudget_no;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public String getMemo() {
		return memo;
	}
	public void setMemo(String memo) {
		this.memo = memo;
	}
	public String getImg() {
		return img;
	}
	public void setImg(String img) {
		this.img = img;
	}
	@Override
	public String toString() {
		return "NoBudgetDetailDTO [nobudget_no=" + nobudget_no + ", content=" + content + ", memo=" + memo + ", img="
				+ img + "]";
	}
	
	
}
