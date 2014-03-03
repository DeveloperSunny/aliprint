package com.elivoa.aliprint.entities.constants;

/**
 * UploadfileNamedQueries Constants Class
 */
public class UploadfileNamedQueries {

	public static final String LIST = "Uploadfile.List";
	public static final String SQL_LIST = "SELECT ent FROM Uploadfile ent ORDER BY :orderby";

	public static final String TOTAL = "Uploadfile.Total";
	public static final String SQL_TOTAL = "SELECT COUNT(ent) FROM Uploadfile ent";

	public static final String LIST_BY_TYPE = "Uploadfile.ListByType";
	public static final String SQL_LIST_BY_TYPE = "SELECT ent FROM Uploadfile ent WHERE ent.fileType=:type ORDER BY :orderby";

	public static final String TOTAL_BY_TYPE = "Uploadfile.TotalByType";
	public static final String SQL_TOTAL_BY_TYPE = "SELECT COUNT(ent) FROM Uploadfile ent WHERE ent.fileType=:type";

	public static final String LIST_BY_OWNER = "Uploadfile.ListByOwner";
	public static final String SQL_LIST_BY_OWNER = "SELECT ent FROM Uploadfile ent WHERE ent.owner=:user ORDER BY :orderby";

	public static final String TOTAL_BY_OWNER = "Uploadfile.TotalByOwner";
	public static final String SQL_TOTAL_BY_OWNER = "SELECT COUNT(ent) FROM Uploadfile ent WHERE ent.owner=:user";

	public static final String LIST_BY_OWNER_AND_TYPE = "Uploadfile.ListByOwnerAndType";
	public static final String SQL_LIST_BY_OWNER_AND_TYPE = "SELECT ent FROM Uploadfile ent WHERE ent.owner=:owner AND ent.fileType=:type ORDER By :orderby";

	public static final String TOTAL_BY_OWNER_AND_TYPE = "Uploadfile.TotalByOwnerAndType";
	public static final String SQL_TOTAL_BY_OWNER_AND_TYPE = "SELECT COUNT(ent) FROM Uploadfile ent WHERE ent.owner=:user AND ent.fileType=:type";

}
