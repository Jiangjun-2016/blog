package comfanlingjun.core.mybatis.page;

/**
 * Mysql 方言
 */
public class MysqlDialect implements Dialect {

	public static final String SQL_END_DELIMITER = ";";

	public boolean supportsLimit() {
		return true;
	}

	/**
	 * 以传入SQL为基础组装分页查询的SQL语句，传递给myBatis调用
	 *
	 * @param sql    原始SQL
	 * @param offset 分页查询的记录的偏移量
	 * @param limit  每页限定记录数
	 * @return 拼装好的SQL
	 */
	public String getLimitSqlString(String sql, int offset, int limit) {
		sql = sql.trim();
		boolean isForUpdate = false;
		if (sql.toLowerCase().endsWith(" for update")) {
			sql = sql.substring(0, sql.length() - 11);
			isForUpdate = true;
		}
		if (offset < 0) {
			offset = 0;
		}
		StringBuffer pagingSelect = new StringBuffer();
		pagingSelect.append(sql + " limit " + offset + "," + limit);
		if (isForUpdate) {
			pagingSelect.append(" for update");
		}
		return pagingSelect.toString();
	}

	/**
	 * 以传入SQL为基础组装总记录数查询的SQL语句
	 *
	 * @param sql 原始SQL
	 * @return 拼装好的SQL
	 */
	public String getCountSqlString(String sql) {
		sql = trim(sql);
		StringBuffer sb = new StringBuffer(sql.length() + 10);
		sb.append("SELECT COUNT(1) AS " + RS_COLUMN + " FROM  ( ");
		sb.append(sql);
		sb.append(")a");
		return sb.toString();
	}

	//预检测sql
	private static String trim(String sql) {
		sql = sql.trim();
		if (sql.endsWith(SQL_END_DELIMITER)) {
			sql = sql.substring(0, sql.length() - 1
					- SQL_END_DELIMITER.length());
		}
		return sql;
	}
}