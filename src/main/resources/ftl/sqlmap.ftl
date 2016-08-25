<?xml version="1.0" encoding="UTF-8" ?>
<!DOCTYPE mapper PUBLIC "-//mybatis.org//DTD Mapper 3.0//EN" "http://mybatis.org/dtd/mybatis-3-mapper.dtd" >
<mapper namespace="${parentPackageName}.dao.${entityName}Dao">
    <resultMap id="${paramName?uncap_first}" type="${parentPackageName}.model.${entityName}">
            <id column="${pk.getColumn_name()}" property="${pk.getProperty_name()}" jdbcType="${pk.getJdbcType()}" />
    <#list columus as colume>
        <#if colume.getColumn_name() != pk.getColumn_name() >
            <result column="${colume.getColumn_name()}" property="${colume.getProperty_name()}" jdbcType="${pk.getJdbcType()}"/>
        </#if>
    </#list>
    </resultMap>

    <sql id="Base_Column_List">
    <#list columus as colume>
        <#if colume_index==0>
        ${colume.getColumn_name()}
        <#else>
        ,${colume.getColumn_name()}
        </#if>
    </#list>
    </sql>

    <select id="selectByPrimaryKey" resultMap="${paramName?uncap_first}" parameterType="java.lang.String">
        select
        <include refid="Base_Column_List"/>
        from ${tableName}
        where ${pk.getColumn_name()} = ${"#{"} ${pk.getProperty_name()},jdbcType=${pk.getJdbcType()} ${"}"}
    </select>

    <delete id="deleteByPrimaryKey" parameterType="java.lang.Integer" >
        delete from ${tableName}
        where ${pk.getColumn_name()} = ${"#{"} ${pk.getProperty_name()},jdbcType=${pk.getJdbcType()} ${"}"}
     </delete>
  <insert id="insert" parameterType="${parentPackageName}.model.${entityName}" >
    insert into ${tableName} (
        <include refid="Base_Column_List"/>
    )
    values (

    <#list columus as colume>
        <#if colume_index==0>
        ${"#{"}  ${colume.getProperty_name()} , jdbcType=${colume.getJdbcType()} ${"}"}
        <#else>
        , ${"#{"} ${colume.getProperty_name()} , jdbcType=${colume.getJdbcType()} ${"}"}
        </#if>
    </#list>

    )
  </insert>

  <insert id="insertSelective" parameterType="${parentPackageName}.model.${entityName}" >
    insert into ${tableName}
    <trim prefix="(" suffix=")" suffixOverrides="," >
    <#list columus as colume>
        <if test="${colume.getProperty_name()} != null" >
        ${colume.getColumn_name()},
       </if>
    </#list>
    </trim>
    <trim prefix="values (" suffix=")" suffixOverrides="," >
    <#list columus as colume>
        <if test="${colume.getProperty_name()} != null" >
            ${"#{"} ${colume.getProperty_name()} , jdbcType=${colume.getJdbcType()} ${"}"},
        </if>
    </#list>
    </trim>
  </insert>
  <update id="updateByPrimaryKeySelective" parameterType="${parentPackageName}.model.${entityName}" >
    update ${tableName}
    <set>
    <#list columus as colume>
        <#if colume.getColumn_name() != pk.getColumn_name() >
        <if test="${colume.getProperty_name()} != null">
            ${colume.getColumn_name()} =  ${"#{"} ${colume.getProperty_name()} , jdbcType=${colume.getJdbcType()} ${"}"},
        </if>
        </#if>
    </#list>
    </set>
    where ${pk.getColumn_name()} = ${"#{"} ${pk.getProperty_name()},jdbcType=${pk.getJdbcType()} ${"}"}
  </update>
  <update id="updateByPrimaryKey" parameterType="${parentPackageName}.model.${entityName}" >
    update ${tableName}
    set
    <#list columus as colume>
        <#if colume.getColumn_name() != pk.getColumn_name() >
            ${colume.getColumn_name()} =  ${"#{"} ${colume.getProperty_name()} , jdbcType=${colume.getJdbcType()} ${"}"},
        </#if>
    </#list>
    where ${pk.getColumn_name()} = ${"#{"} ${pk.getProperty_name()},jdbcType=${pk.getJdbcType()} ${"}"}
  </update>

</mapper>