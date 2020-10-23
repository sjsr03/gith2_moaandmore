package budget.model.dto;

import java.sql.Timestamp;

public class SearchForRecordDTO {
	private String pageNum;
	private String searchDate;
	private String id;
	private String type;
	private Timestamp timeStampDate;
	private int startRow;
	private int endRow;
	private String keyword; 
	//keyword는 검색용
	
	public Timestamp getTimeStampDate() {
		return timeStampDate;
	}
	public void setTimeStampDate(Timestamp timeStampDate) {
		this.timeStampDate = timeStampDate;
	}
	public String getPageNum() {
		return pageNum;
	}
	public void setPageNum(String pageNum) {
		this.pageNum = pageNum;
	}
	
	public String getSearchDate() {
		return searchDate;
	}
	public void setSearchDate(String serachDate) {
		this.searchDate = serachDate;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public int getStartRow() {
		return startRow;
	}
	public void setStartRow(int startRow) {
		this.startRow = startRow;
	}
	public int getEndRow() {
		return endRow;
	}
	public void setEndRow(int endRow) {
		this.endRow = endRow;
	}
	public String getKeyword() {
		return keyword;
	}
	public void setKeyword(String keyword) {
		this.keyword = keyword;
	}
	
	
	
}
