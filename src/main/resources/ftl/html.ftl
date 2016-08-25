<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>${entityName}</title>
    <link rel="stylesheet" href="http://static.jwzh.com:7777/jwzh/common/easyuiDiy/easyui.css">
    <link rel="stylesheet" href="http://static.jwzh.com:7777/jwzh/common/easyuiDiy/icon.css">
    <link rel="stylesheet" href="http://static.jwzh.com:7777/jwzh/framework/default/css/font-awesome.min.css">
    <link rel="stylesheet" href="http://static.jwzh.com:7777/jwzh/common/datepicker/skin/christ/datepicker.css">
    <link rel="stylesheet" href="http://static.jwzh.com:7777/jwzh/stylesheets/common.css">
    <link rel="stylesheet" href="http://static.jwzh.com:7777/jwzh/stylesheets/newItemStyle.css">
    <link rel="stylesheet" href="../stylesheets/formModel.css">
</head>
<body>
<div class="form-main container">
    <form id="${entityName?uncap_first}Form" method='post'>
        <ul class="item-container">
        <#list columus as colume>
            <#if colume.isPk() >
            <#elseif colume.getTransformComment().isShow() >

                <#if !colume.IsCommentTransform() >
                    <#if colume.getJavaType() == "java.util.Date" >
                        <li class="item-two">
                            <p class="pro"><#if !colume.isNullable()><i class="fa fa-asterisk"></i></#if>${colume.getComments()}:</p>
                            <div class="ipt">
                                <input id="${colume.getProperty_name()}"
                                       name="${colume.getProperty_name()}"
                                       class="easyui-validatebox Wdate" style="height:24px;width:99.7%" placeholder="yyyy-MM-dd"
                                       onfocus="WdatePicker({skin: 'christ',dateFmt: 'yyyy-MM-dd',errDealMode:2,autoPickDate:true});"
                                       data-options="
                                       <#if !colume.isNullable()>
                                       required: true,
                                       </#if>
                                       validType:['date[\'yyyy-MM-dd\']']
                                   "
                                />
                            </div>
                        </li>
                    <#else >
                        <li class="item-two">
                            <p class="pro"><#if !colume.isNullable()><i class="fa fa-asterisk"></i></#if>${colume.getComments()}:</p>
                            <div class="ipt">
                                <input id="${colume.getProperty_name()}"
                                       name="${colume.getProperty_name()}"
                                       style="height: 25px;width: 407px"
                                       class="easyui-textbox" value=""
                                       data-options="
                                            <#if !colume.isNullable()>
                                            required: true,
                                            </#if>
                                            validType:['length[0,${colume.getRealDataLength()?c}]'],
                                            prompt:'${colume.getRealDataLengthComent()?string}'
                                       "
                                >
                            </div>
                        </li>
                    </#if>
                <#else >
                        <#if colume.getTransformComment().getShowType() == "DATE" >
                            <li class="item-two">
                                <p class="pro"><#if !colume.isNullable()><i class="fa fa-asterisk"></i></#if>${colume.getTransformComment().getComment()}:</p>
                                <div class="ipt">
                                    <input id="${colume.getProperty_name()}"
                                           name="${colume.getProperty_name()}"

                                           class="easyui-validatebox Wdate <#if !colume.getTransformComment().isEditable()>cannotEdit</#if>" style="height:24px;width:99.7%" placeholder="yyyy-MM-dd"
                                           onfocus="WdatePicker({skin: 'christ',dateFmt: 'yyyy-MM-dd',errDealMode:2,autoPickDate:true});"
                                           data-options="
                                           <#if !colume.isNullable()>
                                           required: true,
                                           </#if>
                                           validType:['date[\'yyyy-MM-dd\']']
                                           "
                                    />
                                </div>
                            </li>
                        <#elseif colume.getTransformComment().getShowType() == "DATETIME">
                            <li class="item-two">
                                <p class="pro"><#if !colume.isNullable()><i class="fa fa-asterisk"></i></#if>${colume.getTransformComment().getComment()}:</p>
                                <div class="ipt">
                                    <input id="${colume.getProperty_name()}"
                                           name="${colume.getProperty_name()}"

                                           class="easyui-validatebox Wdate <#if !colume.getTransformComment().isEditable()>cannotEdit</#if>" style="height:24px;width:99.7%" placeholder="yyyy-MM-dd HH:mm:ss"
                                           onfocus="WdatePicker({skin: 'christ',dateFmt: 'yyyy-MM-dd',errDealMode:2,autoPickDate:true});"
                                           data-options="
                                           <#if !colume.isNullable()>
                                           required: true,
                                           </#if>
                                           validType:['date[\'yyyy-MM-dd HH:mm:ss\']']
                                           "
                                    />
                                </div>
                            </li>
                        <#elseif colume.getTransformComment().getShowType() == "COBBOX">
                            <li class="item-two">
                                <p class="pro"><#if !colume.isNullable()><i class="fa fa-asterisk"></i></#if>${colume.getTransformComment().getComment()}:</p>
                                <div class="ipt">
                                    <input id="${colume.getProperty_name()}"
                                           name="${colume.getProperty_name()}"
                                           style="height: 25px;width: 407px"
                                           class="val easyui-combobox <#if !colume.getTransformComment().isEditable()>cannotEdit</#if>"
                                           data-options="
                                                    url: '${colume.getDictUrl()}',
                                                    method: 'get',
                                                    valueField: 'id',
                                                    textField: 'text',
                                                    panelWidth: 'auto',
                                                    panelMaxHeight: 200,
                                                    panelHeight: 'auto',
                                                    <#if !colume.isNullable()>
                                                    required: true,
                                                    </#if>
                                               "
                                    >
                                </div>
                            </li>
                        <#elseif colume.getTransformComment().getShowType() == "TEXTBOX">
                            <li class="item-two">
                                <p class="pro"><#if !colume.isNullable()><i class="fa fa-asterisk"></i></#if>${colume.getTransformComment().getComment()}:</p>
                                <div class="ipt">
                                    <input id="${colume.getProperty_name()}"
                                           name="${colume.getProperty_name()}"
                                           style="height: 25px;width: 407px"
                                           class="easyui-textbox <#if !colume.getTransformComment().isEditable()>cannotEdit</#if>" value=""
                                           data-options="
                                                <#if !colume.isNullable()>
                                                required: true,
                                                </#if>
                                                validType:['length[0,${colume.getRealDataLength()?c}]'],
                                                prompt:'${colume.getRealDataLengthComent()?string}'
                                           "
                                    >
                                </div>
                            </li>
                        <#elseif colume.getTransformComment().getShowType() == "TEXTAREA">
                            <li class="item-full">
                                <p class="pro-fullTwo"><#if !colume.isNullable()><i class="fa fa-asterisk"></i></#if>${colume.getTransformComment().getComment()}:</p>
                                <div class="ipt-fullTwo textarea">
                                <textarea id="${colume.getProperty_name()}"
                                          name="${colume.getProperty_name()}"

                                          class="easyui-validatebox <#if !colume.getTransformComment().isEditable()>cannotEdit</#if>"
                                          style="width: 99.333%;height: 100px;"
                                          placeholder="${colume.getRealDataLengthComent()?string}"
                                          data-options="
                                                <#if !colume.isNullable()>
                                                required: true,
                                                </#if>
                                                 validType:['length[0,${colume.getRealDataLength()?c}]'],
                                          "
                                ></textarea>
                                </div>
                            </li>
                        </#if >
                </#if >
            <#else >
                <input id="${colume.getProperty_name()}" name="${colume.getProperty_name()}" value="" type="hidden" />
            </#if>
        </#list>

        <#list xt_columes as colume>
            <#if colume.getTransformComment().isShow()>

                <#if !colume.IsCommentTransform() >
                    <#if colume.getJavaType() == "java.util.Date" >
                        <li class="item-two">
                            <p class="pro"><#if !colume.isNullable()><i class="fa fa-asterisk"></i></#if>${colume.getComments()}:</p>
                            <div class="ipt">
                                <input id="${colume.getProperty_name()}"
                                       name="${colume.getProperty_name()}"
                                       class="easyui-validatebox Wdate" style="height:24px;width:99.7%" placeholder="yyyy-MM-dd"
                                       onfocus="WdatePicker({skin: 'christ',dateFmt: 'yyyy-MM-dd',errDealMode:2,autoPickDate:true});"
                                       data-options="
                                       <#if !colume.isNullable()>
                                       required: true,
                                       </#if>
                                       validType:['date[\'yyyy-MM-dd\']']
                                   "
                                />
                            </div>
                        </li>
                    <#else >
                        <li class="item-two">
                            <p class="pro"><#if !colume.isNullable()><i class="fa fa-asterisk"></i></#if>${colume.getComments()}:</p>
                            <div class="ipt">
                                <input id="${colume.getProperty_name()}"
                                       name="${colume.getProperty_name()}"
                                       style="height: 25px;width: 407px"
                                       class="easyui-textbox" value=""
                                       data-options="
                                            <#if !colume.isNullable()>
                                            required: true,
                                            </#if>
                                            validType:['length[0,${colume.getRealDataLength()?c}]'],
                                            prompt:'${colume.getRealDataLengthComent()?string}'
                                       "
                                >
                            </div>
                        </li>
                    </#if>
                <#else >
                    <#if colume.getTransformComment().getShowType() == "DATE" >
                        <li class="item-two">
                            <p class="pro"><#if !colume.isNullable()><i class="fa fa-asterisk"></i></#if>${colume.getTransformComment().getComment()}:</p>
                            <div class="ipt">
                                <input id="${colume.getProperty_name()}"
                                       name="${colume.getProperty_name()}"

                                       class="easyui-validatebox Wdate <#if !colume.getTransformComment().isEditable()>cannotEdit</#if>" style="height:24px;width:99.7%" placeholder="yyyy-MM-dd"
                                       onfocus="WdatePicker({skin: 'christ',dateFmt: 'yyyy-MM-dd',errDealMode:2,autoPickDate:true});"
                                       data-options="
                                           <#if !colume.isNullable()>
                                           required: true,
                                           </#if>
                                           validType:['date[\'yyyy-MM-dd\']']
                                           "
                                />
                            </div>
                        </li>
                    <#elseif colume.getTransformComment().getShowType() == "DATETIME">
                        <li class="item-two">
                            <p class="pro"><#if !colume.isNullable()><i class="fa fa-asterisk"></i></#if>${colume.getTransformComment().getComment()}:</p>
                            <div class="ipt">
                                <input id="${colume.getProperty_name()}"
                                       name="${colume.getProperty_name()}"

                                       class="easyui-validatebox Wdate <#if !colume.getTransformComment().isEditable()>cannotEdit</#if>" style="height:24px;width:99.7%" placeholder="yyyy-MM-dd HH:mm:ss"
                                       onfocus="WdatePicker({skin: 'christ',dateFmt: 'yyyy-MM-dd',errDealMode:2,autoPickDate:true});"
                                       data-options="
                                           <#if !colume.isNullable()>
                                           required: true,
                                           </#if>
                                           validType:['date[\'yyyy-MM-dd HH:mm:ss\']']
                                           "
                                />
                            </div>
                        </li>
                    <#elseif colume.getTransformComment().getShowType() == "COBBOX">
                        <li class="item-two">
                            <p class="pro"><#if !colume.isNullable()><i class="fa fa-asterisk"></i></#if>${colume.getTransformComment().getComment()}:</p>
                            <div class="ipt">
                                <input id="${colume.getProperty_name()}"
                                       name="${colume.getProperty_name()}"
                                       style="height: 25px;width: 407px"
                                       class="val easyui-combobox <#if !colume.getTransformComment().isEditable()>cannotEdit</#if>"
                                       data-options="
                                                    url: '${colume.getDictUrl()}',
                                                    method: 'get',
                                                    valueField: 'id',
                                                    textField: 'text',
                                                    panelWidth: 'auto',
                                                    panelMaxHeight: 200,
                                                    panelHeight: 'auto',
                                                    <#if !colume.isNullable()>
                                                    required: true,
                                                    </#if>
                                               "
                                >
                            </div>
                        </li>
                    <#elseif colume.getTransformComment().getShowType() == "TEXTBOX">
                        <li class="item-two">
                            <p class="pro"><#if !colume.isNullable()><i class="fa fa-asterisk"></i></#if>${colume.getTransformComment().getComment()}:</p>
                            <div class="ipt">
                                <input id="${colume.getProperty_name()}"
                                       name="${colume.getProperty_name()}"
                                       style="height: 25px;width: 407px"
                                       class="easyui-textbox <#if !colume.getTransformComment().isEditable()>cannotEdit</#if>" value=""
                                       data-options="
                                                <#if !colume.isNullable()>
                                                required: true,
                                                </#if>
                                                validType:['length[0,${colume.getRealDataLength()?c}]'],
                                                prompt:'${colume.getRealDataLengthComent()?string}'
                                           "
                                >
                            </div>
                        </li>
                    <#elseif colume.getTransformComment().getShowType() == "TEXTAREA">
                        <li class="item-full">
                            <p class="pro-fullTwo"><#if !colume.isNullable()><i class="fa fa-asterisk"></i></#if>${colume.getTransformComment().getComment()}:</p>
                            <div class="ipt-fullTwo textarea">
                                <textarea id="${colume.getProperty_name()}"
                                          name="${colume.getProperty_name()}"

                                          class="easyui-validatebox <#if !colume.getTransformComment().isEditable()>cannotEdit</#if>"
                                          style="width: 99.333%;height: 100px;"
                                          placeholder="${colume.getRealDataLengthComent()?string}"
                                          data-options="
                                                <#if !colume.isNullable()>
                                                required: true,
                                                </#if>
                                                 validType:['length[0,${colume.getRealDataLength()?c}]'],
                                          "
                                ></textarea>
                            </div>
                        </li>
                    </#if >
                </#if >
            <#else >
                <input id="${colume.getProperty_name()}" name="${colume.getProperty_name()}" value="" type="hidden" />
            </#if>
        </#list>
        </ul>
    </form>
    <div class="btn-area">
        <a class="easyui-linkbutton c6" id="save_info"
           onclick="checkEnter('${entityName?uncap_first}Form','/autoEntry/${entityName?uncap_first}/insert');">保存</a>
    </div>
</div>
<!--<input type="button" value="提交" class="btn2" onclick = "checkEnter();" />-->
</body>
<script src="http://static.jwzh.com:7777/jwzh/common/easyui/jquery.min.js"></script>
<script src="http://static.jwzh.com:7777/jwzh/common/easyui/jquery.dragsort-0.5.2.js"></script>
<script src="http://static.jwzh.com:7777/jwzh/common/easyui/jquery.easyui.min.js"></script>
<script src="http://static.jwzh.com:7777/jwzh/common/easyui/locale/easyui-lang-zh_CN.js"></script>
<script src="http://static.jwzh.com:7777/jwzh/common/easyui/jquery.easyui.extend.js"></script>
<script src="http://static.jwzh.com:7777/jwzh/common/easyui/jquery.easyui.extend.validatebox.js"></script>
<script src="http://static.jwzh.com:7777/jwzh/common/easyui/jquery.easyui.extend.tabs.js"></script>
<script src="http://static.jwzh.com:7777/jwzh/common/easyui/jquery.form.js"></script>
<script src="http://static.jwzh.com:7777/jwzh/common/js/tools.js"></script>
<script src="http://static.jwzh.com:7777/jwzh/common/js/business.tools.js"></script>
<script src="http://static.jwzh.com:7777/jwzh/common/js/windowTopPage.js"></script>
<script src="http://static.jwzh.com:7777/jwzh/common/js/messenger.js"></script>
<script src="http://static.jwzh.com:7777/jwzh/common/js/common.js"></script>
<script src="http://static.jwzh.com:7777/jwzh/common/js/ajax.js"></script>
<script src="http://static.jwzh.com:7777/jwzh/common/datepicker/WdatePicker.js"></script>
<script src="../js/getPath.js"></script>
<script src="../js/globalVar.js"></script>
<script src="../js/formStyle.js"></script>
</html>