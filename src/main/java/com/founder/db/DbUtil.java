package com.founder.db;

import com.founder.util.connection.ConnectionFactory;
import com.founder.util.connection.DbType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;

public enum DbUtil {

	INSTANCE;

	private Logger log = LoggerFactory.getLogger(DbUtil.class);

	private Connection con ;

	private ConnSqlTemplate connSqlTemplate ;

	DbUtil() {
		try {
			con = ConnectionFactory.INSTANCE.getDatabaseConnection();

			if (ConnectionFactory.INSTANCE.getDriverName().equals(DbType.Mysql)){
				connSqlTemplate = new Mysql_ConnSqlTemplate();
			}else if(ConnectionFactory.INSTANCE.getDriverName().equals(DbType.Oracle)){
				connSqlTemplate = new Oracle_ConnSqlTemplate();
			}else if(ConnectionFactory.INSTANCE.getDriverName().equals(DbType.Undefined)){
				connSqlTemplate = null;
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public List<ColumesObject> exeute(String sql){

		List<ColumesObject> list=new LinkedList<>();

		try {
			ResultSet rs = con.createStatement().executeQuery(sql);
//			ResultSetMetaData rsmd = rs.getMetaData();
//			int columnCount = rsmd.getColumnCount();

			while(rs.next()){

				ResultSetMetaData rsmd = rs.getMetaData();
				int columnCount = rsmd.getColumnCount();
				Class<?> clazz =  ColumesObject.class;
				ColumesObject o = (ColumesObject) clazz.newInstance();

				for (int i=1; i<=columnCount; i++){

					String titleName = rsmd.getColumnLabel(i).toLowerCase();
					String setMethodName = "set" + titleName.substring(0,1).toUpperCase() + titleName.substring(1);
					String setValue = rs.getObject(i)==null?"":rs.getObject(i).toString();

					Method setter = clazz.getDeclaredMethod(setMethodName,String.class);
					setter.invoke(o,setValue);

				}

				list.add(o);
			}
			rs.close();
		} catch (SQLException e) {
			log.error(e.getMessage());
			e.printStackTrace();
		} catch (InstantiationException | InvocationTargetException | IllegalAccessException | NoSuchMethodException e) {
			e.printStackTrace();
		}

		return list;

	}

	public ColumesObject getPKColumn(String tableName, List<ColumesObject> columes){
		String sql = connSqlTemplate.getKeyColumnSql(tableName);
		log.debug("\n{}",sql);
		List<ColumesObject> list = exeute(sql);
		String pk = list.get(0).getColumn_name();

		for(ColumesObject colume : columes){
			if(pk.equals(colume.getColumn_name())){
				colume.setPk(true);
				return colume;
			}
		}
		return null;
	}
	
	public List<ColumesObject> queryColumes(String TableName){
		String sql= connSqlTemplate.queryColumesSql(TableName);

		log.debug("\n{}",sql);
		try{
			List<ColumesObject> list = exeute(sql);
			return list;

		}catch(Exception e){
			e.printStackTrace();
		}


		return null;
	}

	public void closeConn(){
		try {
			con.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

}
