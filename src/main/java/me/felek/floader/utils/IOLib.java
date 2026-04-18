package me.felek.floader.utils;

import me.felek.floader.FLoader;

import java.io.*;
import java.util.Scanner;

public class IOLib {
    public static String readAllText(File file) {
        StringBuilder sb = new StringBuilder();

        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            int ch = 0;
            while ((ch = br.read()) != -1) {
                sb.append((char) ch);
            }
        }catch (IOException exc) {
            throw new RuntimeException(exc);
        }

        return sb.toString();
    }

    public static String readResourceText(String path) {
        try (InputStream is = IOLib.class.getResourceAsStream(path)) {
            if (is == null) return null;
            try (Scanner s = new Scanner(is).useDelimiter("\\A")) {
                return s.hasNext() ? s.next() : "";
            }
        } catch (Exception e) {
            return null;
        }
    }
}
