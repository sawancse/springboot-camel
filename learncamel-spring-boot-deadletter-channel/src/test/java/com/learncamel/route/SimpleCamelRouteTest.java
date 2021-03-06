package com.learncamel.route;

import javafx.scene.shape.Path;
import org.apache.camel.Exchange;
import org.apache.camel.ProducerTemplate;
import org.apache.camel.test.spring.CamelSpringBootRunner;
import org.apache.camel.util.FileUtil;
import org.apache.commons.io.FileUtils;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.env.Environment;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import static org.junit.Assert.*;

/**
 * Created by z001qgd on 1/29/18.
 */
@ActiveProfiles("dev")
@RunWith(CamelSpringBootRunner.class)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
@SpringBootTest
public class SimpleCamelRouteTest {

    @Autowired
    ProducerTemplate producerTemplate;

    @Autowired
    Environment environment;

    @BeforeClass
    public static void startCleanUp() throws IOException {

        FileUtils.cleanDirectory(new File("data/input"));
        FileUtils.deleteDirectory(new File("data/output"));
    }

    @Test
    public void testMoveFile() throws InterruptedException {

        String message = "type,sku#,itemdescription,price\n" +
                "ADD,100,Samsung TV,500\n" +
                "ADD,101,LG TV,500";
        String fileName="fileTest.txt";

        producerTemplate.sendBodyAndHeader(environment.getProperty("fromRoute")
                            ,message, Exchange.FILE_NAME,fileName);

        Thread.sleep(3000);

        File outFile = new File("data/output/"+fileName);
        assertTrue(outFile.exists());

    }

    @Test
    public void testMoveFile_ADD() throws InterruptedException, IOException {

        String message = "type,sku#,itemdescription,price\n" +
                "ADD,100,Samsung TV,500\n" +
                "ADD,101,LG TV,500";
        String fileName="fileTest.txt";

        producerTemplate.sendBodyAndHeader(environment.getProperty("fromRoute")
                ,message, Exchange.FILE_NAME,fileName);

        Thread.sleep(3000);

        File outFile = new File("data/output/"+fileName);
        assertTrue(outFile.exists());
        String outputMessage = "Data Updated SuccessFully";
        String output = new String(Files.readAllBytes(Paths.get("data/output/Success.txt")));
        assertEquals(outputMessage,output);
    }

    @Test
    public void testMoveFile_ADD_Exception() throws InterruptedException, IOException {

        String message = "type,sku#,itemdescription,price\n" +
                "ADD,,Samsung TV,500\n" +
                "ADD,101,LG TV,500";
        String fileName="fileTest.txt";

        producerTemplate.sendBodyAndHeader(environment.getProperty("fromRoute")
                ,message, Exchange.FILE_NAME,fileName);

        Thread.sleep(3000);

        File outFile = new File("data/output/"+fileName);
        assertTrue(outFile.exists());
        File successFile = new File("data/output/Success.txt");
        assertTrue(successFile.exists());
    }

    @Test
    public void testMoveFile_UPDATE() throws InterruptedException, IOException {

        String message = "type,sku#,itemdescription,price\n" +
                "UPDATE,100,Samsung TV,600";
        String fileName="fileUpdate.txt";

        producerTemplate.sendBodyAndHeader(environment.getProperty("fromRoute")
                ,message, Exchange.FILE_NAME,fileName);

        Thread.sleep(3000);

        File outFile = new File("data/output/"+fileName);
        assertTrue(outFile.exists());
        String outputMessage = "Data Updated SuccessFully";
        String output = new String(Files.readAllBytes(Paths.get("data/output/Success.txt")));
        assertEquals(outputMessage,output);


    }

    @Test
    public void testMoveFile_DELETE() throws InterruptedException, IOException {

        String message = "type,sku#,itemdescription,price\n" +
                "DELETE,100,Samsung TV,600";
        String fileName="fileDelete.txt";

        producerTemplate.sendBodyAndHeader(environment.getProperty("fromRoute")
                ,message, Exchange.FILE_NAME,fileName);

        Thread.sleep(3000);

        File outFile = new File("data/output/"+fileName);
        assertTrue(outFile.exists());
        String outputMessage = "Data Updated SuccessFully";
        String output = new String(Files.readAllBytes(Paths.get("data/output/Success.txt")));
        assertEquals(outputMessage,output);


    }
}
