<?xml version="1.0" encoding="UTF-8"?>
<!DOCTYPE sqlMap PUBLIC "-//ibatis.apache.org//DTD SQL Map 2.0//EN" "http://ibatis.apache.org/dtd/sql-map-2.dtd">
<sqlMap namespace="${paramName}">
    <typeAlias alias="${paramName}" type="com.founder.model.${entityName}" />

    <!-- 新增 -->
    <insert id="save" parameterClass="${paramName}" >
        <![CDATA[ INSERT INTO ${tableName} (${pk}]]>
    <#list columus as colume>
        <isNotEmpty prepend="," property="${colume["COLUMN_NAME"]?lower_case}"><![CDATA[ ${colume["COLUMN_NAME"]?lower_case}}]]></isNotEmpty>
    </#list>
        ,<include refid="insertXtzd" />
        <![CDATA[ )VALUES (#${pk?lower_case}# ]]>
    <#list columus as colume>
        <isNotEmpty prepend="," property="${colume["COLUMN_NAME"]?lower_case}"><![CDATA[ #${colume["COLUMN_NAME"]?lower_case}#]]></isNotEmpty>
    </#list>
        ,<include refid="insertXtzdVal" />
        )
    </insert>

    <!-- 修改 -->
    <update id="update" parameterClass="${paramName}" >
        <![CDATA[ UPDATE ${tableName}]]>
        <dynamic prepend="SET">
        <#list columus as colume>
            <isNotNull prepend="," property="${colume["COLUMN_NAME"]?lower_case}"><![CDATA[ ${colume["COLUMN_NAME"]} = #${colume["COLUMN_NAME"]?lower_case}#]]></isNotNull>
        </#list>
            ,<include refid="updateXtzd"/>
        </dynamic>
        <![CDATA[ WHERE ${pk} = #${pk?lower_case}#]]>
    </update>

    <!-- 注销 -->
    <update id="delete" parameterClass="${paramName}">
        <![CDATA[ UPDATE ${tableName} SET]]>
        <include refid="deleteXtzd" />
        <![CDATA[ WHERE ${pk} = #${pk?lower_case}#]]>
    </update>

    <!-- 通过  ID 查询单条数据 -->
    <select id="queryById" parameterClass="String" resultClass="${paramName}">
        <![CDATA[ SELECT * FROM ${tableName} WHERE XT_ZXBZ='0' AND ${pk} = #${pk?lower_case}# AND ROWNUM = 1]]>
    </select>

    <!-- 通过  entity 查询列表 -->
    <select id="queryByEntity" parameterClass="${paramName}" resultClass="${paramName}">
        <![CDATA[ SELECT * FROM ${tableName} WHERE XT_ZXBZ='0' ]]>
    <#list columus as colume>
        <isNotEmpty prepend="AND" property="${colume["COLUMN_NAME"]?lower_case}"> ${colume["COLUMN_NAME"]} = #${colume["COLUMN_NAME"]?lower_case}#</isNotEmpty>
    </#list>
    </select>

    <!-- 查询分页总条数 -->
    <select id="queryPageCount" parameterClass="Map" resultClass="Integer">
        <![CDATA[ SELECT count(*) FROM ${tableName} WHERE XT_ZXBZ = '0' ]]>
    <#list columus as colume>
        <isNotEmpty prepend="AND" property="entity.${colume["COLUMN_NAME"]?lower_case}"> ${colume["COLUMN_NAME"]} = #entity.${colume["COLUMN_NAME"]?lower_case}#</isNotEmpty>
    </#list>
    </select>

    <!-- 查询分页列表 -->
    <select id="queryPageList" parameterClass="Map" resultClass="${paramName}">
        <![CDATA[SELECT t.* FROM (
        SELECT a.* , rownum r FROM (
        SELECT *  FROM ${tableName} WHERE XT_ZXBZ = '0']]>
    <#list columus as colume>
        <isNotEmpty prepend="AND" property="entity.${colume["COLUMN_NAME"]?lower_case}"> ${colume["COLUMN_NAME"]} = #entity.${colume["COLUMN_NAME"]?lower_case}#</isNotEmpty>
    </#list>
        <![CDATA[ order by $sort$ $order$  ) a
        WHERE ROWNUM <= #end# ) t
        WHERE r > #begin#]]>
    </select>
</sqlMap>