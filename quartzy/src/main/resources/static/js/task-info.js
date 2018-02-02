
var TaskInfo = {

    grid: '#taskinfo-grid',
    form: '#taskinfoForm',

    init: function () {
        this.initGrid();
    },
    // 初始化表格
    initGrid: function () {
        $(this.grid).datagrid({
            toolbar: '#taskinfo-tbar',
            url: '/list',
            method: 'post',
            fitColumns: true,
            striped: true,
            fit: true,
            pagination: false,
            rownumbers: true,
            columns:[[
                {field:'id',title:'',checkbox:'true', width:20},
                {field:'jobName',title:'JobName',width:120},
                {field:'jobGroup',title:'JobGroup',width:50},
                {field:'jobDescription',title:'JobDescription',width:120},
                {field:'jobStatus',title:'JobStatus',width:50},
                {field:'cronExpression',title:'CronExpression',width:60},
                {field:'createTime',title:'CreateTime',width:70}
            ]]
        })
    }

}