package com.founder.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.Statement;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class DbCon {
	private String dbUrl;
	private String dbUser;
	private String dbPwd;
	
	public DbCon(String dbUrl,String dbUser,String dbPwd){
		this.dbUrl=dbUrl;
		this.dbUser=dbUser;
		this.dbPwd=dbPwd;
	}
	
	private Connection getCon(){
		try{
			Class.forName("oracle.jdbc.OracleDriver");
			Connection con = DriverManager.getConnection(dbUrl , dbUser , dbPwd ) ; 
			return con;
		}catch(Exception e){
			e.printStackTrace();
		}
		return null;
	}
	
	public List<Map<String, Object>> query(String TableName){
		String sql="select a.COLUMN_NAME ,a.DATA_TYPE,b.COMMENTS from user_tab_columns a left join user_col_comments b on( "
				+ "b.TABLE_NAME=a.TABLE_NAME and b.COLUMN_NAME=a.COLUMN_NAME) where a.TABLE_NAME='"+TableName+"'";
		try{
			Connection con=this.getCon();
			Statement stmt = con.createStatement() ;
			ResultSet rs = stmt.executeQuery(sql) ;   
			ResultSetMetaData rsmd = rs.getMetaData();
			int columnCount = rsmd.getColumnCount();   
			List<Map<String, Object>> list=new LinkedList<Map<String, Object>>();			
			while(rs.next()){   
				Map<String, Object> map=new HashMap<String, Object>();
				for (int i=1; i<=columnCount; i++){   					
					map.put(rsmd.getColumnName(i),rs.getObject(i));								       
			    }   		
				list.add(map);
				
		     }
			stmt.close();
			con.close();
			return list;

		}catch(Exception e){
			e.printStackTrace();
		}
		
		
		return null;
	}

}
