package com.sime.save;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import com.sime.Simulation;

import tools.jackson.databind.ObjectMapper;

public class SaveUtility {

    private ObjectMapper mapper;
    private Path directory;

    public SaveUtility(String directoryName) throws IOException {
        this.mapper = mapper();
        this.directory = directory(directoryName);
    }

    public void save(Simulation sim) throws IOException {
        var output = directory.resolve("saveFile.json");
        output.toFile().createNewFile();
        mapper.writeValue(output, sim);
    }


    private Path directory(String directoryName) throws IOException {
        var newDir = Path.of("output", directoryName, "saves");
        Files.createDirectories(newDir);
        return newDir;
    }

    private ObjectMapper mapper() {
        var mapper = new ObjectMapper();
        mapper.registeredModules();
        mapper.writerWithDefaultPrettyPrinter();
        return mapper;
    }
}
