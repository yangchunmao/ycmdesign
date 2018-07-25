package jco.demo.yangcm;


import com.sap.conn.jco.*;
import com.sap.conn.jco.ext.DestinationDataProvider;
import com.sap.conn.jco.ext.Environment;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

public final class RfcManager {

    private static Logger logger = LoggerFactory.getLogger(RfcManager.class);

    private static String ABAP_AS_POOLED = "XXX";

    private static JCOProvider provider = null;

    private static JCoDestination destination = null;

    static {
        Properties properties = loadProperties();

        provider = new JCOProvider();

        // catch IllegalStateException if an instance is already registered
        try {
            Environment.registerDestinationDataProvider(provider);
        } catch (IllegalStateException e) {
            logger.debug(e.getMessage());
        }

        provider.changePropertiesForABAP_AS(ABAP_AS_POOLED, properties);
    }

    public static Properties loadProperties() {
        Properties prop = new Properties();
        prop.setProperty(DestinationDataProvider.JCO_ASHOST, "10.135.2.229");
        prop.setProperty(DestinationDataProvider.JCO_SYSNR, "10");
        prop.setProperty(DestinationDataProvider.JCO_USER, "01000044");
        prop.setProperty(DestinationDataProvider.JCO_PASSWD, "MIMA,1234");
        prop.setProperty(DestinationDataProvider.JCO_CLIENT, "300");
        prop.setProperty(DestinationDataProvider.JCO_LANG, "zh");
        return prop;
    }

    public static JCoDestination getDestination() throws JCoException {
        if (destination == null) {
            destination = JCoDestinationManager.getDestination(ABAP_AS_POOLED);
        }
        return destination;
    }

    public static JCoFunction getFunction(String functionName) {
        JCoFunction function = null;
        try {
            function = getDestination().getRepository()
                    .getFunctionTemplate(functionName).getFunction();
        } catch (JCoException e) {
            logger.error(e.getMessage());
        } catch (NullPointerException e) {
            logger.error(e.getMessage());
        }
        return function;
    }

    public static void execute(JCoFunction function) {
        logger.debug("SAP Function Name : " + function.getName());
        JCoParameterList paramList = function.getImportParameterList();

        if (paramList != null) {
            logger.debug("Function Import Structure : " + paramList.toString());
        }

        try {
            function.execute(getDestination());
        } catch (JCoException e) {
            logger.error(e.getMessage());
        }
        paramList = function.getExportParameterList();

        if (paramList != null) {
            logger.debug("Function Export Structure : " + paramList.toString());
        }
    }

    /*
     *
     */
    public static String ping() {
        String msg = null;
        try {
            getDestination().ping();
            msg = "Destination " + ABAP_AS_POOLED + " is ok";
            System.out.println(msg);
        } catch (JCoException ex) {
            System.out.print(ex.getMessage());
        }
        logger.debug(msg);
        return msg;
    }

    public static void main(String[] args) {
        RfcManager.ping();
//        RfcManager.callRfc4Wsc();
    }

    // 示例
    public static Map<String, Object> callRfc4Wsc() {
        Map<String, Object> map = new HashMap();
        // 获取RFC 对象
        JCoFunction function = RfcManager.getFunction("ZFI_INT_KX_POST");
        JCoTable importParam = function.getTableParameterList().getTable(0);
        JCoUtils.printJCoTable(importParam);
        importParam.appendRow();
//        JCoParameterList importParam = function.getImportParameterList();
//        JCoStructure importParam=function.getImportParameterList().getStructure("ZFI_INT_KX_IN");
        //业务单据号
        importParam.setValue("ROW_ID", "K01111111112");
        //业务模式
        importParam.setValue("YWMS", "K01");
        //交易类型
        importParam.setValue("JYLX", "");
        //公司代码
        importParam.setValue("BUKRS", "3710");
        //凭证中的过帐日期
        importParam.setValue("BUDAT", new Date());
        //凭证抬头文本
        importParam.setValue("BKTXT", "AAAAAAAAA");
        //参考凭证编号
        importParam.setValue("XBLNR", "BBBBBBB");
        //货币码
        importParam.setValue("WAERS", "CNY");
        //客户编号
        importParam.setValue("KUNNR", "8800043802");
        //供应商编码
        importParam.setValue("LIFNR", "8800043802");
        //一次客户名称
        importParam.setValue("NAME", "");
        //一次客户城市
        importParam.setValue("CITY", "");
        //应收/应付总额
        importParam.setValue("DMBTR", 100.00);
        //手续费金额
        importParam.setValue("DMBTR1", 0.00);
        //税金
        importParam.setValue("DMBTR2", 0.00);
        //收款金额
        importParam.setValue("DMBTR3", 0.00);
        //行项目的参考码3
        importParam.setValue("XREF3", "GGGGYCM");
        //项目文本
        importParam.setValue("SGTXT", "微商城发货记账");
        //分配编号
        importParam.setValue("ZUONR", "");
        //用户名
        importParam.setValue("USERNAME", "");
        //客户编号1
        importParam.setValue("KUNNR1", "");
        //供应商编码1
        importParam.setValue("LIFNR1", "");

        // 执行RFC
        RfcManager.execute(function);

        // 获取RFC返回的字段值
//        JCoParameterList exportParam = function.getExportParameterList();
//        String exParamA = exportParam.getString("field_A");
//        String exParamB = exportParam.getString("field_B");
        // 遍历RFC返回的表对象
        JCoTable returnStructure = function.getTableParameterList().getTable(1);
        for (int i = 0; i < returnStructure.getNumRows(); i++) {
            returnStructure.setRow(i);
            map.put("ROW_ID", returnStructure.getString("ROW_ID"));
            map.put("BUKRS", returnStructure.getString("BUKRS"));
            map.put("GJAHR", returnStructure.getString("GJAHR"));
            map.put("BELNR", returnStructure.getString("BELNR"));
            map.put("FLAG", returnStructure.getString("FLAG"));
            map.put("MESSAGE", returnStructure.getString("MESSAGE"));
            System.out.println(returnStructure.getString("ROW_ID"));
            System.out.println(returnStructure.getString("BUKRS"));
            System.out.println(returnStructure.getString("GJAHR"));
            System.out.println(returnStructure.getString("BELNR"));
            System.out.println(returnStructure.getString("FLAG"));
            System.out.println(returnStructure.getString("MESSAGE"));
        }
        return map;
    }

    // 示例
    public void callRfcExample() {
        // 获取RFC 对象
        JCoFunction function = RfcManager.getFunction("function_name");
        // 设置import 参数
        JCoParameterList importParam = function.getImportParameterList();
        importParam.setValue("field_name", "val");
        // 执行RFC
        RfcManager.execute(function);

        // 获取RFC返回的字段值
        JCoParameterList exportParam = function.getExportParameterList();
        String exParamA = exportParam.getString("field_A");
        String exParamB = exportParam.getString("field_B");
        // 遍历RFC返回的表对象
        JCoTable tb = function.getTableParameterList().getTable("table_name");
        for (int i = 0; i < tb.getNumRows(); i++) {
            tb.setRow(i);
            System.out.println(tb.getString("field01"));
            System.out.println(tb.getString("field02"));
        }
    }

}
