package br.com.villela.rsacryptofile;

import java.io.*;
import java.math.BigInteger;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;

import br.com.villela.rsacoreapi.KeyPair;
import br.com.villela.rsacoreapi.PrivateKey;
import br.com.villela.rsacoreapi.PublicKey;
import br.com.villela.rsacryptofile.file.KeysFileReader;

// TODO(LV) refatorar todo esse projeto, to com preguiça, ta tarde... hahahha
public class Main {

    public static void main(String[] args) {
        List<String> argsList = Arrays.asList(args);
        String fileToOperateName = argsList.get(1);
        String text = readFileToOperate(fileToOperateName);
        KeyPair keyPair = KeysFileReader.readKeysFromFiles();

        if (argsList.get(0).equals("crypt")){
            PublicKey publicKey = keyPair.getPublicKey();
            String encryptedText = new BigInteger(text.getBytes()).modPow(publicKey.getExponent(), publicKey.getProductPrimesOfKeys()).toString();
            writeInFileToOperate(fileToOperateName, encryptedText);
        } else if (argsList.get(0).equals("decrypt")){
            PrivateKey privateKey = keyPair.getPrivateKey();
            String decryptedText = new String(new BigInteger(text).modPow(privateKey.getExponent(), privateKey.getProductPrimesOfKeys()).toByteArray());
            writeInFileToOperate(fileToOperateName, decryptedText);
        } else {
            System.out.println("Operação não existente");
        }
    }

    private static String readFileToOperate(String fileName) {
        String contentFile = null;

        try {
            contentFile = Files.lines(Paths.get(fileName), Charset.forName("UTF-8")).reduce(String::concat).orElse("");
        } catch (Exception ex) {
//            System.err.println("Não foi possivel ler o arquivo para realizar as operação.");
            ex.printStackTrace();
        }

        return contentFile;
    }

    private static void writeInFileToOperate(String fileName, String text) {
        try(BufferedWriter w = new BufferedWriter(new FileWriter(fileName))) {
            w.write(text);
        } catch(IOException e) {
            e.printStackTrace();
        }
    }
}
