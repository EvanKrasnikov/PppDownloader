package ru.geographer29.pppdownloader.containers;

import org.testcontainers.containers.GenericContainer;
import org.testcontainers.containers.output.OutputFrame;
import org.testcontainers.containers.output.ToStringConsumer;
import org.testcontainers.images.builder.ImageFromDockerfile;
import org.testcontainers.utility.MountableFile;

import java.nio.charset.Charset;
import java.nio.file.Paths;

public class ConsulContainer extends GenericContainer<ConsulContainer> {

    //private final static String context = System.getProperty("user.dir");
    private final static String dockerfile = System.getProperty("user.dir") + "/Consul.dockerfile";
    static ImageFromDockerfile image = new ImageFromDockerfile()
            .withDockerfile(Paths.get(dockerfile));
    //private String consulSettings = System.getProperty("user.dir") + "PppDownloader-consul-config.json";

    public ConsulContainer() {
        super(image);
        //super("consul:latest");

        withLogConsumer(new ToStringConsumer() {
            @Override
            public void accept(OutputFrame outputFrame) {
                if (outputFrame != null && outputFrame.getBytes() != null)
                    System.out.println(new String(outputFrame.getBytes(), Charset.forName("UTF-8")));
            }
        });
    }

}
