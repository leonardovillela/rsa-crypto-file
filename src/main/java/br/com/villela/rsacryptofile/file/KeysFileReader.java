package br.com.villela.rsacryptofile.file;

import br.com.villela.rsacoreapi.KeyPair;
import br.com.villela.rsacoreapi.PrivateKey;
import br.com.villela.rsacoreapi.PublicKey;

import java.io.*;
import java.math.BigInteger;

public class KeysFileReader {

    private static final String dirKeysName = ".simple-rsa";
    private static final String publicKeyFileName = "id_rsa.pub";
    private static final String privateKeyFileName = "id_rsa";

    // TODO(LV) resolver esse "* 2"
    public static KeyPair readKeysFromFiles() {
        PublicKey publicKey = readPublicKeyFile();
        PrivateKey privateKey = readPrivateKeyFile();

        return new KeyPair(publicKey.getExponent(), privateKey.getExponent(), privateKey.getProductPrimesOfKeys());
    }

    private static PublicKey readPublicKeyFile() {
        PublicKey publicKey = null;

        try (ObjectInputStream inputStream = new ObjectInputStream(new FileInputStream(getPublicKeyFilePath()))) {
            String key = inputStream.readUTF();
            int indexOfToken = key.indexOf("|");
            String exponent = key.substring(0, indexOfToken);
            String productOfPrimes = key.substring(indexOfToken + 1, key.length());
            publicKey = new PublicKey(new BigInteger(exponent), new BigInteger(productOfPrimes));
        } catch (Exception ex) {
            System.err.println("Não foi possivel ler a chave publica.");
        }

        return publicKey;
    }

    private static PrivateKey readPrivateKeyFile() {
        PrivateKey privateKey = null;

        try (ObjectInputStream inputStream = new ObjectInputStream(new FileInputStream(getPrivateKeyFilePath()))) {
            String key = inputStream.readUTF();
            int indexOfToken = key.indexOf("|");
            String exponent = key.substring(0, indexOfToken);
            String productOfPrimes = key.substring(indexOfToken + 1, key.length());
            privateKey = new PrivateKey(new BigInteger(exponent), new BigInteger(productOfPrimes));
        } catch (Exception ex) {
            System.err.println("Não foi possivel ler a chave privada.");
        }

        return privateKey;
    }

    private static String getDirKeyLocation() {
        String homeFolderPath = System.getenv().get("HOME");
        return homeFolderPath + "/" + dirKeysName + "/";
    }

    private static String getPublicKeyFilePath() {
        return getDirKeyLocation() + publicKeyFileName;
    }

    private static String getPrivateKeyFilePath() {
        return getDirKeyLocation() + privateKeyFileName;
    }
}
