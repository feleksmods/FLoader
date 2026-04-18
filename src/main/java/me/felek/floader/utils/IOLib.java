package me.felek.floader.utils;

import jdk.jfr.internal.LogLevel;
import jdk.jfr.internal.Logger;
import me.felek.floader.FLoader;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

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
}
