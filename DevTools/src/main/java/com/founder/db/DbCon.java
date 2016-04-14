package com.founder.db;

import com.alibaba.druid.sql.SQLUtils;
import com.founder.util.connection.ConnectionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.*;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class DbCon {

	private static Logger log = LoggerFactory.getLogger(DbCon.class);

	private static Connection con = getCon();

	public static Connection getCon(){
		try{
			if (con==null) {
				con = ConnectionFactory.getDatabaseConnection();
			}
			return con;
		}catch(Exception e){
			e.printStackTrace();
		}
		return null;
	}

	public List<Map<String, Object>> exeute(String sql){
		List<Map<String, Object>> list=new LinkedList<>();
		try {
			ResultSet rs = DbCon.getCon().createStatement().executeQuery(sql);
			ResultSetMetaData rsmd = rs.getMetaData();
			int columnCount = rsmd.getColumnCount();

			while(rs.next()){
				Map<String, Object> map=new HashMap<>();
				for (int i=1; i<=columnCount; i++){
					map.put(rsmd.getColumnName(i),rs.getObject(i));
				}
				list.add(map);

			}
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			return list;
		}
	}

	public String getKeyColumn(String tableName){
		String sql = "SELECT " +
				" cu.column_name " +
				" FROM " +
				" user_cons_columns cu," +
				" user_constraints au" +
				" WHERE" +
				" cu.constraint_name = au.constraint_name" +
				" AND au.constraint_type = 'P'" +
				" AND au.table_name = 'SYFW_FWZPB';";
		sql = SQLUtils.formatOracle(sql, SQLUtils.DEFAULT_FORMAT_OPTION);
		log.debug("\n{}",sql);
		List<Map<String, Object>> list = exeute(sql);
		log.debug("\n{}",list.get(0).get("COLUMN_NAME"));
		return null;
	}
	
	public List<Map<String, Object>> queryColume(String TableName){
		String sql="select a.COLUMN_NAME ,a.DATA_TYPE,b.COMMENTS from user_tab_columns a left join user_col_comments b on( "
				+ "b.TABLE_NAME=a.TABLE_NAME and b.COLUMN_NAME=a.COLUMN_NAME) where a.TABLE_NAME='"+TableName+"'";
		try{
			Statement stmt = con.createStatement() ;
			ResultSet rs = stmt.executeQuery(sql) ;
			List<Map<String, Object>> list = exeute(sql);
			stmt.close();
			con.close();
			return list;

		}catch(Exception e){
			e.printStackTrace();
		}
		
		
		return null;
	}

}
