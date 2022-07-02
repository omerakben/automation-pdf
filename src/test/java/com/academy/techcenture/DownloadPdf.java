package com.academy.techcenture;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import java.io.*;
import java.net.URL;
import java.nio.channels.Channels;
import java.nio.channels.FileChannel;
import java.nio.channels.ReadableByteChannel;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.time.Duration;
import java.util.HashMap;
import java.util.Map;

public class DownloadPdf {

    WebDriver driver = null;

    @BeforeTest
    public void setUp() {
        WebDriverManager.chromedriver().setup();

        ChromeOptions options = new ChromeOptions();
        Map<String, Object> prefs = new HashMap<String, Object>();
        prefs.put("download.prompt_for_download", false);
        prefs.put("download.default_directory", System.getProperty("user.dir") + "/src/main/resources/invoices");
        options.setExperimentalOption("prefs", prefs);
        driver = new ChromeDriver(options);
    }


    @Test (priority = 1)
    public void downloadPdf() throws IOException, InterruptedException {
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
        driver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(10));
        driver.get("http://automationpractice.com/index.php");
        Thread.sleep(2000);
        driver.findElement(By.xpath("//a[@class='login']")).click();
        Thread.sleep(2000);
        driver.findElement(By.xpath("//input[@id='email']")).sendKeys("kevinlee1234@gmail.com");
        driver.findElement(By.xpath("//input[@id='passwd']")).sendKeys("Kevin123");
        driver.findElement(By.xpath("//button[contains(.,'Sign in')]")).click();
        driver.findElement(By.xpath("//a[.='Order history and details']")).click();

        //locator to click the pdf download link
        String pdfUrlLink = "http://automationpractice.com/index.php?controller=pdf-invoice&id_order=450844.pdf";
        driver.get(pdfUrlLink);



//        String content  = "";
//        PDDocument document = PDDocument.load(new File("src/main/resources/invoices/IN220027.pdf"));
//        if (!document.isEncrypted()) {
//            PDFTextStripper stripper = new PDFTextStripper();
//            content = stripper.getText(document);
//            System.out.println("Text:" + content);
//        }
//        document.close();
//
//        System.out.println(content);


    }

    private String verifyPdfInvoice(String urlTxt) throws IOException {

        URL pdfUrl = new URL(urlTxt);
        InputStream in = pdfUrl.openStream();
        BufferedInputStream bf = new BufferedInputStream(in);

        byte[] arrayFromInputStream = getArrayFromInputStream(bf);
        writeContent(arrayFromInputStream,  "src/main/resources/invoices/sample.pdf");

        String content  = "";
//        PDDocument document = PDDocument.load(new File("src/main/resources/invoices/IN220027.pdf"));
//        if (!document.isEncrypted()) {
//            PDFTextStripper stripper = new PDFTextStripper();
//            content = stripper.getText(document);
//            System.out.println("Text:" + content);
//        }
//        document.close();
//

        return content;
    }

    private static byte[] getArrayFromInputStream(BufferedInputStream inputStream) throws IOException {
        byte[] bytes;
        byte[] buffer = new byte[1024];
        try(BufferedInputStream is = new BufferedInputStream(inputStream)){
            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            int length;
            while ((length = is.read(buffer)) > -1 ) {
                System.out.println(new String(buffer));
                bos.write(buffer, 0, length);
            }
            bos.flush();
            bytes = bos.toByteArray();
        }
        return bytes;
    }

    private void writeContent(byte[] content, String fileToWriteTo) throws IOException {
        File file = new File(fileToWriteTo);
        try(BufferedOutputStream salida = new BufferedOutputStream(new FileOutputStream(file))) {
            salida.write(content);
            salida.flush();
        }
    }



}
