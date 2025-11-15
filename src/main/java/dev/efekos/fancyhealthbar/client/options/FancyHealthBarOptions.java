package dev.efekos.fancyhealthbar.client.options;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import dev.efekos.fancyhealthbar.client.Try;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;

public class FancyHealthBarOptions {

    public static final int VERSION = 1;

    private static final Gson GSON = new GsonBuilder()
            .disableJdkUnsafe().disableHtmlEscaping().setPrettyPrinting().create();
    private static final Logger log = LoggerFactory.getLogger(FancyHealthBarOptions.class);

    private final File optionsDirectory;
    private final File selectedFile;

    private String selectedRendering;
    private HealthBarRenderingOptions renderingOptions;

    public FancyHealthBarOptions(File optionsDirectory) {
        this.optionsDirectory = optionsDirectory;
        selectedFile = new File(optionsDirectory, "selected");
        defaultConfig();
        load();
    }

    public void defaultConfig(){
        selectedRendering = "vanilla";
        VanillaRenderingOptions options = (VanillaRenderingOptions) HealthBarRenderingOptions.TYPES.get("vanilla");
        options.reset();
        renderingOptions = options;
    }

    public void write(){
        if(!optionsDirectory.exists())optionsDirectory.mkdir();
        if(!selectedFile.exists()) Try.error(selectedFile::createNewFile,log,"Could not create selected file!");
        Try.error(() -> {
            FileWriter writer = new FileWriter(selectedFile);
            writer.write(selectedRendering);
            writer.flush();
            writer.close();
            return 0;
        },log,"Could not save selected rendering!");

        File file = getRenderingOptionsFile(selectedRendering);
        if(!file.exists()) Try.error(file::createNewFile,log,"Could not create rendering options file!");
        try (FileWriter writer = new FileWriter(file)) {
            JsonObject object = new JsonObject();
            renderingOptions.write(object);
            GSON.toJson(object, writer);
            writer.flush();
        } catch (IOException e) {
            log.error("Could not save rendering options file!",e);
        };
    }

    public void load(){
        if(!optionsDirectory.exists())return;
        if(!selectedFile.exists())return;
        Try.error(() -> {
            String s = Files.readString(selectedFile.toPath());
            HealthBarRenderingOptions options = HealthBarRenderingOptions.TYPES.get(s);
            if(options==null){
                log.error("Invalid selected rendering!");
                return 0;
            }
            File file = getRenderingOptionsFile(s);
            if(!file.exists()){
                options.reset();
                return 0;
            }

            FileReader reader = new FileReader(file);
            JsonObject object = GSON.fromJson(reader, JsonObject.class);
            reader.close();
            options.read(object);
            renderingOptions = options;
            selectedRendering = s;
            return 0;
        },log,"Couldn't read FHB options.");
    }

    private File getRenderingOptionsFile(String s) {
        return  new File(optionsDirectory, s+".json");
    }

    public String getSelectedRendering() {
        return selectedRendering;
    }

    public HealthBarRenderingOptions getRenderingOptions() {
        return renderingOptions;
    }

    public void selectRendering(String rendering) {
        Try.error(() -> {
            HealthBarRenderingOptions options = HealthBarRenderingOptions.TYPES.get(rendering);
            if(options==null){
                log.error("Invalid selected rendering!");
                return 0;
            }
            File file = getRenderingOptionsFile(rendering);
            if(!file.exists()){
                options.reset();
            } else {
                FileReader reader = new FileReader(file);
                JsonObject object = GSON.fromJson(reader, JsonObject.class);
                reader.close();
                options.read(object);
            }

            renderingOptions = options;
            selectedRendering = rendering;
            return 0;
        },log,"Couldn't change FHB rendering.");
    }

}
