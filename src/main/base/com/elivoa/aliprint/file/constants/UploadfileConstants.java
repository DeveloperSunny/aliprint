package com.elivoa.aliprint.file.constants;

/**
 * FileConstants
 * 
 * <ol>
 * <li>Add named queries for list all files and list type's files</li>
 * </ol>
 * 
 * @author Bo Gao elivoa[AT]gamil.com, [Nov 19, 2011]
 * 
 */
public interface UploadfileConstants {

	/*
	 * Named Queries
	 */
	final String NQ_LIST_BY_USER = "Uploadfile.listByUser";
	final String SQL_LIST_BY_USER = "SELECT f FROM Uploadfile f WHERE f.user=:user ORDER BY :orderby DESC";

	final String NQ_LIST_BY_USERANDTYPE = "Uploadfile.ListByUserAndType";
	final String SQL_LIST_BY_USERANDTYPE = "SELECT f FROM Uploadfile f WHERE f.user=:user and f.fileType=:fileType ORDER BY :orderby DESC";

	final String NQ_LIST = "Uploadfile.List";
	final String SQL_LIST = "SELECT f FROM Uploadfile f ORDER BY :orderby DESC";

	final String NQ_TOTAL = "Uploadfile.Total";
	final String SQL_TOTAL = "SELECT COUNT(f) FROM Uploadfile f";

	final String NQ_LIST_BYTYPE = "Uploadfile.ListByType";
	final String SQL_LIST_BYTYPE = "SELECT f FROM Uploadfile f WHERE f.fileType=:fileType ORDER BY :orderby DESC";

	final String NQ_TOTAL_BYTYPE = "Uploadfile.TotalByType";
	final String SQL_TOTAL_BYTYPE = "SELECT COUNT(f) FROM Uploadfile f WHERE f.fileType=:fileType";

	/*
	 * Orderby
	 */
	final String ORDERBY_CREATETIME = "create_time";
}
