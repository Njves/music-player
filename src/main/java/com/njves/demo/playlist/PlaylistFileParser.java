package com.njves.demo.playlist;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.njves.demo.list.LinkedList;
import com.njves.demo.model.Track;

import java.io.*;
import java.util.Arrays;
import java.util.List;

public class PlaylistFileParser {
    Gson gson = new GsonBuilder().setPrettyPrinting().create();

    public void toJson(PlaylistFileContainer playlistFileContainer) {
        System.out.println(Arrays.toString(playlistFileContainer.trackList.toArray()));
        String json = gson.toJson(playlistFileContainer.trackList.toArray());
        writeJson(json);
        System.out.println(readJson());
//        System.out.println(Arrays.toString(gson.fromJson(json, Track[].class)));
    }

    public void writeJson(String json) {
        try(FileWriter writer = new FileWriter("json.txt", false)) {
            writer.write(json);
            writer.append("\n");
            writer.flush();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public String readJson() {
        StringBuilder builder = new StringBuilder();
        try(FileReader reader = new FileReader("json.txt"))
        {
            char[] buf = new char[256];
            int c;
            while((c = reader.read(buf))>0){
                if(c < 256){
                    buf = Arrays.copyOf(buf, c);
                }
                builder.append(buf);
            }
        }
        catch(IOException ex){
            System.out.println(ex.getMessage());
        }
        return builder.toString();
    }
}
