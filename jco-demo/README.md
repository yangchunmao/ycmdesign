java 通过JCO直连的方式连通SAP, 调用相应的方法.
    -  对应64位,32位系统, 不同的Window, Linux系统都有相应的jar等.
    -  把具体的`sapjco3.jar`, `sapjco3.dll`或`libsapjco3.so`放在`lib`下,通过`maven`打包可以成功
    -  `pom`文件中添加

            <dependency>
            <groupId>com.yangcm.sapjco3</groupId>
            <artifactId>sapjco3</artifactId>
            <version>0.0.1</version>
            <scope>system</scope>
            <systemPath>${project.basedir}/src/main/resources/lib/sapjco3.jar</systemPath>
            </dependency>
