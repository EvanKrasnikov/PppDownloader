package ru.geographer29.pppdownloader.containers;

import org.testcontainers.containers.GenericContainer;
import org.testcontainers.containers.output.OutputFrame;
import org.testcontainers.containers.output.ToStringConsumer;
import org.testcontainers.images.builder.ImageFromDockerfile;
import org.testcontainers.utility.MountableFile;

import java.nio.charset.Charset;
import java.nio.file.Paths;

public class PppDownloaderContainer extends GenericContainer<PppDownloaderContainer> {

    //private static final String context = System.getProperty("user.dir");;
    private static final String dockerfile = System.getProperty("user.dir")+"/PppDownloader.dockerfile";


    private static ImageFromDockerfile image = new ImageFromDockerfile()
            .withDockerfile(Paths.get(dockerfile));

    //private String artifact = System.getProperty("user.dir") + "target/PppDownloader-0.0.1-SNAPSHOT.war";

    public PppDownloaderContainer() {
        super(image);

        withLogConsumer(new ToStringConsumer() {
            @Override
            public void accept(OutputFrame outputFrame) {
                if (outputFrame != null && outputFrame.getBytes() != null)
                    System.out.println(new String(outputFrame.getBytes(), Charset.forName("UTF-8")));
            }
        });
    }

}
