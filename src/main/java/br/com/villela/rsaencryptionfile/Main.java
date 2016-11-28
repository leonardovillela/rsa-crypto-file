package br.com.villela.rsaencryptionfile;

import br.com.villela.rsacore.config.KeysConfigLoader;
import br.com.villela.rsacore.cypher.Decrypter;
import br.com.villela.rsacore.cypher.Encrypter;
import br.com.villela.rsacore.key.Key;
import br.com.villela.rsacore.key.KeyPair;
import br.com.villela.rsacore.key.reader.KeysPairFileReader;
import br.com.villela.rsaencryptionfile.target.FileTargetOperations;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;

public class Main {

    public static void main(String[] args) throws IOException {
        List<String> argsList = Arrays.asList(args);

        Operations operations = Operations.getEnumByString(argsList.get(0));
        Path fileTargetPath = Paths.get(argsList.get(1));
        String fileTargetText = FileTargetOperations.read(fileTargetPath);

        KeyPair keyPair = KeysPairFileReader.readKeysFromFiles(new KeysConfigLoader());

        switch (operations) {
            case CRYPT:
                Key publicKey = keyPair.getPublicKey();
                String encryptedText = Encrypter.encrypt(publicKey, fileTargetText);
                FileTargetOperations.write(fileTargetPath, encryptedText);
                break;
            case DECRYPT:
                Key privateKey = keyPair.getPrivateKey();
                String decryptedText = Decrypter.decrypt(privateKey, fileTargetText);
                FileTargetOperations.write(fileTargetPath, decryptedText);
                break;
            default:
                System.out.println("Operação não existente");
        }

        System.out.println("Operação realizada com sucesso!");
    }

}
