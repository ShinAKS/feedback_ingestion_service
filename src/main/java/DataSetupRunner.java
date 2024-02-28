import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;

@Component
public class DataSetupRunner implements ApplicationRunner {

    @Value("classpath:scripts/tweetdata_setup.sh")
    private Resource twitterData;

    @Value("classpath:scripts/zendesk_setup.sh")
    private Resource zendeskData;


    @Override
    public void run(ApplicationArguments args) throws Exception {
        // Get the absolute path to the script files
        File script1File = File.createTempFile("script1", ".sh");
        copyResourceToFile(twitterData, script1File);

        File script2File = File.createTempFile("script2", ".sh");
        copyResourceToFile(zendeskData, script2File);

        // Run the scripts
        runScript(script1File.getAbsolutePath());
        runScript(script2File.getAbsolutePath());

        // Clean up temporary files
        script1File.delete();
        script2File.delete();
    }

    private void copyResourceToFile(Resource resource, File file) throws IOException {
        try (InputStream inputStream = resource.getInputStream();
             FileOutputStream outputStream = new FileOutputStream(file)) {
            byte[] buffer = new byte[1024];
            int bytesRead;
            while ((bytesRead = inputStream.read(buffer)) != -1) {
                outputStream.write(buffer, 0, bytesRead);
            }
        }
    }

    private void runScript(String scriptPath) throws IOException, InterruptedException {
        ProcessBuilder pb = new ProcessBuilder("sh", scriptPath);
        pb.inheritIO(); // Redirect output to console
        Process process = pb.start();
        int exitCode = process.waitFor();
        if (exitCode != 0) {
            throw new RuntimeException("Script execution failed with exit code " + exitCode);
        }
    }
}