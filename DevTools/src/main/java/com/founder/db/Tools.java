package com.founder.db;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.FileOutputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Tools {

	private static Logger log = LoggerFactory.getLogger(Tools.class);

	public String tableName="SYFW_FWZPB";//数据库表名
	public String entityName="SYFW_FWZPB";//生成的业务实体（java bean）名
	public String paramName="SYFW_FWZPB";//生成的业务实体（java bean）参数名
	public String packageName="com.founder";//包名:model,sqlmap及dao的上级包名
	public String filePath = "D:/db2file";//生成的文件目录

	private DbCon conn = new DbCon();
	private String pk = null;
	
	
	private StringBuffer javaParam=new StringBuffer();
	private StringBuffer javaMethod=new StringBuffer();
	private StringBuffer insertParam=new StringBuffer();
	private StringBuffer insertValue=new StringBuffer();
	private StringBuffer updateParam=new StringBuffer();
	private StringBuffer queryParam=new StringBuffer();
	private StringBuffer queryPageParam=new StringBuffer();
	public Map<String, String> db2JavaMap;
	
	
	public Tools(){
		db2JavaMap=new HashMap<>();
		db2JavaMap.put("VARCHAR2", "String");
		db2JavaMap.put("DATE", "java.sql.Date");
		db2JavaMap.put("NUMBER", "int");
		db2JavaMap.put("BLOB", "byte[]");
		pk = conn.getOracleKeyColumn(tableName);
	}

	public void create(){

		List<Map<String, Object>> list=conn.queryColumes(tableName);
		doList(list);
		db2Java();
		db2SqlMap(list);
		createDao();
	}

	public static void main(String[] args) {
		Tools tool=new Tools();
		tool.create();
	}

	/**
	 * 
	 * @Title: doList
	 * @Description: TODO(处理列表)
	 * @param @param list    设定文件
	 * @return void    返回类型
	 * @throw
	 */
	public void doList(List<Map<String, Object>> list){
		Map<String, Object> map;
		String colName;
		
		for(int i=0;i<list.size();i++){
			map=list.get(i);
			colName=map.get("COLUMN_NAME").toString().toLowerCase();//列名
			//java
			doJava(colName,(String)map.get("DATA_TYPE"),map.get("COMMENTS"));

			//sql insert
			doInsert(colName);

			doUpdate(colName);
//
//			doQuery(colName);
//
//			doQueryPage(colName);
		}

	}
	
	private void doJava(String colName,String dataType,Object comments){
		if(colName.startsWith("xt_")) return;
		
		String colName2=colName.substring(0, 1).toUpperCase()+colName.substring(1);
		javaParam.append("\t@FieldDesc(\""+comments+"\") private "+db2JavaMap.get(dataType)+" "+colName+";\r\n");
		javaMethod.append("\tpublic "+db2JavaMap.get(dataType)+" get"+colName2+"(){\r\n\t\treturn "+colName+";\r\n\t}\r\n");
		javaMethod.append("\tpublic void set"+colName2+"("+db2JavaMap.get(dataType)+" "+colName+") {\r\n\t\tthis."+colName+" = "+colName+";\r\n\t}\r\n");
	}
	
	private void doInsert(String colName){
		if(colName.startsWith("xt_")) return;
		if(colName.equals("id")) return;
		
		insertParam.append("\t\t\t<isNotEmpty prepend=\",\" property=\""+colName+"\"><![CDATA[ "+colName.toUpperCase()+"]]></isNotEmpty>\r\n");
		insertValue.append("\t\t\t<isNotEmpty prepend=\",\" property=\""+colName+"\"><![CDATA[#"+colName+"#]]></isNotEmpty>\r\n");
	}
	
	private void doUpdate(String colName){
		if(colName.startsWith("xt_")) return;
		if(colName.equals(pk.toLowerCase())) return;
		updateParam.append("\t\t\t<isNotNull prepend=\",\" property=\""+colName+"\"><![CDATA[ "+colName.toUpperCase()+" = #"+colName+"#]]></isNotNull>\r\n");
	}
	
	private void doQuery(String colName){
		if(colName.startsWith("xt_")) return;
		
		queryParam.append("\t\t\t<isNotEmpty prepend=\"AND\" property=\""+colName+"\"> "+colName.toUpperCase()+" = #"+colName+"#</isNotEmpty>\r\n");
	}
	
	private void doQueryPage(String colName){
		if(colName.startsWith("xt_")) return;
		
		queryPageParam.append("\t\t\t<isNotEmpty prepend=\"AND\" property=\"entity."+colName+"\"> "+colName.toUpperCase()+" = #entity."+colName+"#</isNotEmpty>\r\n");
	}
	
	
	/**
	 * 
	 * @Title: db2Java
	 * @Description: TODO(生成entity)
	 * @param @param list    设定文件
	 * @return void    返回类型
	 * @throw
	 */
	public void db2Java(){
		StringBuffer content=new StringBuffer("package "+packageName+".model;\r\n\r\n");
		content.append("import java.io.Serializable;\r\n\r\n");
		
		content.append("import com.founder.framework.annotation.DBInfoAnnotation;\r\n");
		content.append("import com.founder.framework.annotation.FieldDesc;\r\n");
		content.append("import com.founder.framework.base.entity.BaseEntity;\r\n\r\n");
		
		content.append("@DBInfoAnnotation(tableName = \""+tableName+"\" , pk = \""+pk+"\")\r\n");
		content.append("public class "+entityName+" extends BaseEntity implements Serializable {\r\n");
		content.append("\tprivate static final long serialVersionUID = 1L;\r\n\r\n");
		
		content.append(javaParam);
		
		content.append(javaMethod);
		
		content.append("}");
		this.writeFile(this.packageName+".model",this.entityName+".java", content.toString());
	}
	
	public void db2SqlMap(List<Map<String, Object>> list){
		StringBuffer content = new StringBuffer();
		appendHeader(content);
		appendInsert(content);
		appendUpdate(content);
		appendDel(content);
		appendQuery(content);
		appendQueryPage(content);
		content.append("</sqlMap>");
		this.writeFile(this.packageName+".sqlmap",this.entityName+".xml", content.toString());
	}
	
	
	private void appendHeader(StringBuffer content){
		content.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>\r\n<!DOCTYPE sqlMap PUBLIC \"-//ibatis.apache.org//DTD SQL Map 2.0//EN\" \"http://ibatis.apache.org/dtd/sql-map-2.dtd\">\r\n");		
		content.append("<sqlMap namespace=\"" + entityName + "\">\r\n");	//添加namespace
		content.append("\t<typeAlias alias=\""+ paramName +"\" type=\""+ packageName +".model."+ entityName +"\" />\r\n\r\n");	//添加参数变量
	}
	
	
	
	/**
	 * 对获取到sql中的条件进行处理   insert  语句
	 */
	public void appendInsert(StringBuffer content){
		
		content.append("\t<!-- 新增 -->\r\n");
		content.append("\t<insert id=\"save\" parameterClass=\""+ paramName +"\" >\r\n");
		content.append("\t\t<![CDATA[ INSERT INTO " + tableName + " ("+pk+"]]>\r\n");
		
		content.append(this.insertParam);
		
		content.append("\t\t\t,<include refid=\"insertXtzd\" />\r\n\t\t<![CDATA[ )VALUES (#"+pk.toLowerCase()+"# ]]>\r\n");
		
		content.append(this.insertValue);
		
		content.append("\t\t\t,<include refid=\"insertXtzdVal\" />\r\n\t\t)\r\n");
		content.append("\t</insert>\r\n\r\n");
	}
	
	
	/**
	 * 对获取到sql中的条件进行处理  update语句
	 */
	public void appendUpdate(StringBuffer content){
		content.append("\t<!-- 修改 -->\r\n");
		content.append("\t<update id=\"update\" parameterClass=\""+ paramName +"\" >\r\n");
		content.append("\t\t<![CDATA[ UPDATE " + tableName + "]]>\r\n");
		content.append("\t\t<dynamic prepend=\"SET\">\r\n");
		
		content.append(this.updateParam);
		
		content.append("\t\t\t,<include refid=\"updateXtzd\"/>\r\n");
		content.append("\t\t</dynamic>\r\n");
		content.append("\t\t<![CDATA[ WHERE "+pk+" = #"+pk.toLowerCase()+"#]]>\r\n");
		content.append("\t</update>\r\n\r\n");
	}
	
	
	/**
	 * 对获取到sql中的条件进行处理  del语句
	 */
	public void appendDel(StringBuffer content){
		content.append("\t<!-- 注销 -->\r\n");
		content.append("\t<update id=\"delete\" parameterClass=\"" + paramName + "\">\r\n");
		content.append("\t\t<![CDATA[ UPDATE " + tableName+ " SET]]>\r\n");
		content.append("\t\t\t<include refid=\"deleteXtzd\" />\r\n");
		content.append("\t\t<![CDATA[ WHERE \"+pk+\" = #"+pk.toLowerCase()+"#]]>\r\n");
		content.append("\t</update>\r\n\r\n");
	}
	
	
	/**
	 * 
	 * @Title: appendQuery
	 * @Description: TODO(查询sql拼装)
	 * @param @param content    设定文件
	 * @return void    返回类型
	 * @throw
	 */
	public void appendQuery(StringBuffer content){
		content.append("\t<!-- 通过  ID 查询单条数据 -->\r\n");
		content.append("\t<select id=\"queryById\" parameterClass=\"String\" resultClass=\""+ paramName +"\">\r\n");
		content.append("\t\t<![CDATA[ SELECT * FROM "+ tableName +" WHERE XT_ZXBZ='0' AND "+pk+" = #"+pk.toLowerCase()+"# AND ROWNUM = 1]]>\r\n");
		content.append("\t</select>\r\n\r\n");
		
		content.append("\t<!-- 通过  entity 查询列表 -->\r\n");
		content.append("\t<select id=\"queryByEntity\" parameterClass=\""+ paramName +"\" resultClass=\""+ paramName +"\">\r\n");
		content.append("\t\t<![CDATA[ SELECT * FROM "+ tableName +" WHERE XT_ZXBZ='0' ]]>\r\n");
		
		content.append(this.queryParam);
		
		content.append("\t</select>\r\n\r\n");
	}
	
	/**
	 * 
	 * @Title: appendQueryPage
	 * @Description: TODO(分页sql拼装)
	 * @param @param content    设定文件
	 * @return void    返回类型
	 * @throw
	 */
	public void appendQueryPage(StringBuffer content){
		content.append("\t<!-- 查询分页总条数 -->\r\n");
		content.append("\t<select id=\"queryPageCount\" parameterClass=\"Map\" resultClass=\"Integer\">\r\n");
		content.append("\t\t<![CDATA[ SELECT count(*) FROM "+tableName+" WHERE XT_ZXBZ = '0' ]]>\r\n");
		
		content.append(this.queryPageParam);
		content.append("\t</select>\r\n\r\n");
		
		content.append("\t<!-- 查询分页列表 -->\r\n");
		content.append("\t<select id=\"queryPageList\" parameterClass=\"Map\" resultClass=\""+ paramName +"\">\r\n");
		content.append("\t\t<![CDATA[SELECT t.* FROM (\r\n\t\t\tSELECT a.* , rownum r FROM (\r\n\t\t\t\tSELECT *  FROM "+tableName+" WHERE XT_ZXBZ = '0']]>\r\n");
		
		content.append(this.queryPageParam);
		
		content.append("\t\t<![CDATA[ order by $sort$ $order$  ) a \r\n\t\t\tWHERE ROWNUM <= #end# ) t \r\n\t\t\t\tWHERE r > #begin#]]>\r\n");
		content.append("\t</select>\r\n");
	}
	
	public void createDao(){
		StringBuffer content=new StringBuffer("package "+packageName+".dao;\r\n\r\n");
		
		content.append("import java.util.List;\r\n");
		content.append("import java.util.Map;\r\n\r\n");
		content.append("import org.springframework.stereotype.Repository;\r\n\r\n");
		content.append("import com.founder.framework.base.dao.BaseDaoImpl;\r\n");
		content.append("import com.founder.framework.utils.EasyUIPage;\r\n");
		content.append("import "+packageName+".model."+entityName+";\r\n\r\n");
		
		content.append("@Repository\r\n");
		content.append("public class "+entityName+"Dao extends BaseDaoImpl{\r\n\r\n");
		
		//insert
		content.append("\t/**\r\n\t * 新增业务对象\r\n\t */\r\n");
		content.append("\tpublic void insert("+entityName+" entity) {\r\n");
		content.append("\t\tsuper.insert(\""+entityName+".save\",entity);\r\n");
		content.append("\t}\r\n\r\n");
		
		//update
		content.append("\t/**\r\n\t * 根据主键ID修改对象\r\n\t */\r\n");
		content.append("\tpublic void update("+entityName+" entity) {\r\n");
		content.append("\t\tsuper.update(\""+entityName+".update\",entity);\r\n");
		content.append("\t}\r\n\r\n");
		
		//delete
		content.append("\t/**\r\n\t * 通过主键ID注销对象，只是修改注销状态为（已注销）\r\n\t */\r\n");
		content.append("\tpublic void delete("+entityName+" entity) {\r\n");
		content.append("\t\tsuper.delete(\""+entityName+".delete\",entity);\r\n");
		content.append("\t}\r\n\r\n");
		
		//queryById
		content.append("\t/**\r\n\t * 通过主键ID查询单个业务实体对象\r\n\t */\r\n");
		content.append("\tpublic "+entityName+" queryById(String "+pk.toLowerCase()+") {\r\n");
		content.append("\t\treturn ("+entityName+")super.queryForObject(\""+entityName+".queryById\","+pk.toLowerCase()+");\r\n");
		content.append("\t}\r\n\r\n");
		
		//queryByEntity
		content.append("\t/**\r\n\t * 根据业务实体查询业务实体列表\r\n\t */\r\n");
		content.append("\tpublic List<"+entityName+"> queryByEntity("+entityName+" entity) {\r\n");
		content.append("\t\treturn super.queryForList(\""+entityName+".queryByEntity\",entity);\r\n");
		content.append("\t}\r\n\r\n");
		
		//queryPageList
		content.append("\t/**\r\n\t * 查询分页对象\r\n\t */\r\n");
		content.append("\tpublic EasyUIPage queryPageList(Map<String, Object> map, EasyUIPage page) {\r\n");
		content.append("\t\tInteger count=(Integer)queryForObject(\""+entityName+".queryPageCount\", map);\r\n");
		content.append("\t\tpage.setTotal(count==null?0:count);\r\n");
		content.append("\t\tpage.setRows(queryForList(\""+entityName+".queryPageList\", map));\r\n");
		content.append("\t\treturn page;\r\n");
		content.append("\t}\r\n");
		
		content.append("}\r\n");
		
		this.writeFile(this.packageName+".dao",this.entityName+"Dao.java", content.toString());
	}
	
	/**
	 * 
	 * @Title: writeFile
	 * @Description: TODO(文件生成)
	 * @param @param fileName 文件名
	 * @param @param content  文件内容
	 * @return void    返回类型
	 * @throw
	 */
	public void writeFile(String packageName,String fileName,String content){
		try{
			String path = this.filePath+"/"+packageName.replace(".","/");
			File dir = new File(path);
			dir.mkdirs();
			log.info(path);
			File file=new File(path+"/"+fileName);
//			if(!file.exists())
			file.createNewFile();
			FileOutputStream fileOut=new FileOutputStream(file);
			fileOut.write(content.getBytes("UTF-8"));
			fileOut.flush();
			fileOut.close();
		}catch(Exception e){
			e.printStackTrace();
		}
	}
}
