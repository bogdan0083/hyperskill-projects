package io.bogdan0083.encryptdecrypt;

import io.bogdan0083.encryptdecrypt.cirpher.Cipher;
import io.bogdan0083.encryptdecrypt.cirpher.CipherMode;
import io.bogdan0083.encryptdecrypt.exception.ArgumentParsingException;
import io.bogdan0083.encryptdecrypt.exception.NoArgumentsPassedException;
import io.bogdan0083.encryptdecrypt.cirpher.ShiftCipher;
import io.bogdan0083.encryptdecrypt.cirpher.UnicodeCipher;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;

/**
 * A simple utility application which provides a simple Encryption and Decryption
 * functionalities based on {@link UnicodeCipher}
 * and {@link ShiftCipher} algorithms
 */
public class Main {
    public static void main(String[] args) {
        HashMap<String, String> argsMap;

        try {
            argsMap = parseArgs(args);
        } catch (NoArgumentsPassedException | ArgumentParsingException e) {
            System.out.println(e.getMessage());
            return;
        }

        CipherMode mode = getMode(argsMap);
        int key = getKey(argsMap);

        Cipher cipher = Cipher.getInstance(argsMap.get("-alg"));

        try (InputStream input = getInput(argsMap); PrintWriter writer = getWriter(argsMap)) {
            String inputString = new String(input.readAllBytes(), StandardCharsets.UTF_8);
            String outputString = cipher.runAction(mode, inputString, key);
            writer.print(outputString);
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    public static PrintWriter getWriter(HashMap<String, String> argsMap) throws IOException {
        String outputFilePath = argsMap.get("-out");

        if (outputFilePath == null) {
            return new PrintWriter(System.out);
        }

        return new PrintWriter(outputFilePath);
    }

    /*
     * Parses arguments into a hashmap. Arguments with no values
     * are filled with nulls
     */
    public static HashMap<String, String> parseArgs(String[] args) throws NoArgumentsPassedException, ArgumentParsingException {
        HashMap<String, String> argsMap = new HashMap<>();

        if (args.length == 0) {
            throw new NoArgumentsPassedException("Error: no arguments passed to the program");
        }

        for (int i = 0; i < args.length; i++) {
            String arg = args[i];
            if (isArgumentKey(arg)) {
                String nextArg = args[Math.min(i + 1, args.length - 1)];
                if (isArgumentKey(nextArg) || arg.equals(nextArg)) { // argument WITHOUT value
                    String msg = "Error: argument " + arg + " passed without value";
                    throw new ArgumentParsingException(msg);
                } else { // argument WITH value
                    argsMap.put(arg, nextArg);
                }
            }
        }

        return argsMap;
    }

    public static CipherMode getMode(HashMap<String, String> argsMap) {
        String modeStringValue = argsMap.get("-mode");

        if (modeStringValue == null) {
            return CipherMode.ENCODE;
        }

        switch (modeStringValue) {
            case "enc":
                return CipherMode.ENCODE;
            case "dec":
                return CipherMode.DECODE;
            default:
                return CipherMode.UNKNOWN;
        }
    }

    public static int getKey(HashMap<String, String> argsMap) {
        String keyStringValue = argsMap.get("-key");

        if (keyStringValue == null) {
            return 0;
        }

        return Integer.parseInt(keyStringValue);
    }

    public static InputStream getInput(HashMap<String, String> argsMap) throws IOException {
        String dataStringValue = argsMap.get("-data");
        String inputFilePath = argsMap.get("-in");

        if (dataStringValue == null && inputFilePath == null) {
            return new ByteArrayInputStream("".getBytes());
        }

        boolean inputFileExists = inputFilePath != null && Files.exists(Paths.get(inputFilePath));

        if (dataStringValue == null && !inputFileExists) {
            throw new FileNotFoundException("Error: file " + inputFilePath + "does not exist!");
        }

        if (dataStringValue == null) {
            return new ByteArrayInputStream(Files.readAllBytes(Path.of(inputFilePath)));
        }

        return new ByteArrayInputStream(dataStringValue.getBytes());
    }

    /*
     * Return true if argument is key, meaning that it contains
     * the "-" sign in the beginning (e.g. -mode)
     */
    public static boolean isArgumentKey(String arg) {
        return arg.startsWith("-");
    }

}
