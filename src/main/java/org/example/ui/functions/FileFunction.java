package org.example.ui.functions;

import org.example.entitys.CommandsData;
import org.json.simple.JSONObject;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;
import java.util.Queue;

public class FileFunction {

//static class to write the json file based in the input of the user

    public static void writeJsonFile(Queue<CommandsData> pojoData, String path) throws IOException {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(path + ".json"))) {
            bw.write("[\n");
            int size = pojoData.size();
            for (int i = 0; i < size; i++) {
                CommandsData data = pojoData.poll();
                if (data != null) {
                    JSONObject jsonObject = new JSONObject();
                    jsonObject.put("type", data.getType());
                    jsonObject.put("coordinateY", data.getCoordinateY());
                    jsonObject.put("coordinateX", data.getCoordinateX());
                    jsonObject.put("mouseButton", data.getMouseButton());
                    jsonObject.put("keyTyped", data.getKeyTyped());
                    bw.write(jsonObject.toJSONString());
                    if (i < size - 1) {
                        bw.write(",\n");
                    }
                }
            }
            bw.write("\n]");
            bw.flush();
        }
    }
}
