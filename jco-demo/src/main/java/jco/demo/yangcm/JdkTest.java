package jco.demo.yangcm;

/**
 * Created by ycm on 2018/7/24.
 */
public class JdkTest {


    public static void main(String[] args) {
        System.out.println("JVM Bit size:" + System.getProperty("sun.arch.data.model"));
        System.out.println("JVM Bit size: " + System.getProperty("os.arch"));
    }
}
