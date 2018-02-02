<%--
  Created by IntelliJ IDEA.
  User: ycm
  Date: 2018/1/29
  Time: 16:33
  模板
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="utf-8" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Hello Quartzy</title>
    <link rel="stylesheet" type="text/css" href="/plugin/themes/default/easyui.css">
    <link rel="stylesheet" type="text/css" href="/plugin/themes/icon.css">
    <script type="text/javascript" src="/plugin/jquery.min.js"></script>
    <script type="text/javascript" src="/plugin/jquery.easyui.min.js"></script>
    <script type="text/javascript" src="/plugin/locale/easyui-lang-zh_CN.js"></script>
    <script type="text/javascript" src="/plugin/commons.js"></script>
</head>
<body class="easyui-layout" style="overflow-y: hidden">
    <!-- header -->
    <div data-options="region: 'north', border: false" style="overflow: hidden; height: 30px; line-height: 30px;">
        <span style="float:right; padding-right:20px;" class="head">
                <a href="#" id="editpass">Password</a> |
                <a href="#" id="loginOut">Sign Out</a>
            </span>
        <span style="padding-left:10px; font-size: 16px;">Hello Quarty</span>
    </div>

    <!-- 左边菜单导航  -->
    <div data-options="region:'west',title:'Navigation',iconCls:'icon-tip'" style="width:220px;">
        <div class="easyui-accordion" data-options="fit:true,border:false,animate:true,plain:true">
            <div title="Vegetables" style="padding:10px;">
                <ul class="easyui-tree">
                    <li>Tomato</li>
                    <li>Carrot</li>
                    <li>Cabbage</li>
                    <li>Potato</li>
                    <li>Lettuce</li>
                </ul>
            </div>
        </div>
    </div>
    <!-- 中间内容页面  -->
    <div id="mainPanle" data-options="region:'center'" style="overflow-y:hidden">
        <div id="centerTab" class="easyui-tabs" data-options="fit:true,border:false">
            <div title="Home" style="padding:0px;" data-options="iconCls: 'icon-home'">
                <!-- Grid -->
                <table id="taskinfo-grid"></table>
                <!-- ToolBar -->
                <div id="taskinfo-tbar">
                    <table cellpadding="0" cellspacing="0" style="width:100%">
                        <tr>
                            <td>
                                <a href="#" class="easyui-linkbutton" iconCls="grid-add" plain="true" onclick="TaskInfo.addTaskInfo()">增加</a>
                                <div class="datagrid-btn-separator" style="vertical-align: middle; height: 15px;display:inline-block;float:none"></div>
                                <a href="#" class="easyui-linkbutton" iconCls="grid-edit" plain="true" onclick="TaskInfo.updateTaskInfo()">修改</a>
                                <div class="datagrid-btn-separator" style="vertical-align: middle; height: 15px;display:inline-block;float:none"></div>
                                <a href="#" class="easyui-linkbutton" iconCls="grid-del" plain="true" onclick="TaskInfo.deleteTaskInfo()">删除</a>
                            </td>
                        </tr>
                    </table>
                </div>
                <!-- 窗口Window -->
                <div id="taskinfoDialog" style="padding:10px 0px; display: none;">
                    <form id="taskinfoForm" class="easyui-form" method="post" data-options="novalidate:true">
                        <table style="width: 450px;margin: 0 auto;">
                            <tr>
                                <td>JobName</td>
                                <td><input class="easyui-textbox" name="jobName" style="width:100%" data-options="required:true"></td>
                                <td width="100"></td>
                            </tr>
                            <tr>
                                <td>JobGroup</td>
                                <td>
                                    <select class="easyui-combobox" name="jobGroup" style="width:100%;" data-options="required:true">
                                        <option value="Job_group">JobGroup</option>
                                    </select>
                                </td>
                                <td width="100"></td>
                            </tr>
                            <tr>
                                <td>CronExpression</td>
                                <td>
                                    <input class="easyui-textbox" name="cronExpression" data-options="required:true" style="width:100%">
                                </td>
                                <td width="100"></td>
                            </tr>
                            <tr>
                                <td>Description</td>
                                <td colspan="2">
                                    <input class="easyui-textbox" name="jobDescription" style="width:100%;height:60px" data-options="multiline:true,required:true">
                                    <input type="hidden" name="id" value="0">
                                </td>
                            </tr>
                        </table>
                    </form>
                </div>
            </div>
        </div>
    </div>
    <script type="text/javascript">
        (function ($) {
            TaskInfo.init();
        })(JQuery)
    </script>
</body>
</html>
