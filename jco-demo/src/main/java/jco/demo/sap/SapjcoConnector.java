package jco.demo.sap;

import com.sap.conn.jco.JCo;
import com.sap.conn.jco.JCoDestination;
import com.sap.conn.jco.JCoDestinationManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.util.Hashtable;
import java.util.Properties;

/**
 * Created by Admin on 2018/7/24.
 */
public class SapjcoConnector {
    private static Logger logger = LoggerFactory.getLogger(SapjcoConnector.class);

    static Hashtable<String, JCoDestination> destinations = new Hashtable<>();
    public static String SERVER_NAME = "SANPOWER_SERVER";
    public static String DESTINATION_NAME = "ABAP_AS_WITH_POOL";

    static {
        try{
            doInitialize();
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    /**
     * 初始化SAP连接
     * @throws Exception
     */
    private static void doInitialize() throws Exception{
        JCo.setTrace(4, null);
        Properties connProperties = new Properties();

        InputStream is = SapjcoConnector.class.getResourceAsStream("/properties/sap-config.properties");
        connProperties.load(is);
        createDataFile(DESTINATION_NAME, "jcoDestination", connProperties);
        //*******JCo server
        Properties serverProperties = new Properties();
        InputStream isServer = SapjcoConnector.class.getResourceAsStream("/properties/sap-config-server.properties");
        serverProperties.load(isServer);
        /*
            servertProperties.setProperty(ServerDataProvider.JCO_GWHOST, "xxx.xxx.xxx.xxx");
            // TCP服务sapgw是固定的，后面的00就是SAP实例系统编号，也可直接是端口号（端口号可以在
            // etc/server文件中找sapgw00所对应的端口号）
            servertProperties.setProperty(ServerDataProvider.JCO_GWSERV, "sapgw30");
            // 这里的程序ID来自于SM59中设置的Program ID，必须相同
            servertProperties.setProperty(ServerDataProvider.JCO_PROGID, "JCO_SANPOWER_LQ");
            servertProperties.setProperty(ServerDataProvider.JCO_REP_DEST, DESTINATION_NAME);
            servertProperties.setProperty(ServerDataProvider.JCO_CONNECTION_COUNT, "2");
        */
        createDataFile(SERVER_NAME, "jcoServer", serverProperties);
    }


    /**
     * 创建连接文件(必须)
     * @param name
     * @param suffix
     * @param properties
     */
    private static void createDataFile(String name, String suffix, Properties properties) {
        File cfg = new File(name + "."+ suffix);
        if(cfg.exists()){
            cfg.deleteOnExit();
        }
        try {
            FileOutputStream fos = new FileOutputStream(cfg, false);
            properties.store(fos, "for connection");
            fos.close();
            logger.info("createDataFile: "+ name + "."+ suffix);
        }catch (Exception e){
            throw new RuntimeException("Unable to create the destination file " + cfg.getName(), e);
        }
    }

    /**
     * 获取连接池, 利用JCoDestinationManager创建连接池放在表中
     * @param fdPoolName
     * @return
     */
    public static JCoDestination getDestionation(String fdPoolName) throws Exception{
        JCoDestination destination = null;
        if(destinations.containsKey(fdPoolName)){
            destination = destinations.get(fdPoolName);
        }else {
            destination = JCoDestinationManager.getDestination(fdPoolName);
            destinations.put(fdPoolName, destination);
        }
        return destination;
    }
}
