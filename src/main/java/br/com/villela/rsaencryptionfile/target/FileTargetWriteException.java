package br.com.villela.rsaencryptionfile.target;

public class FileTargetWriteException extends RuntimeException {

    public FileTargetWriteException() {
        super("Não foi possivel escrever no arquivo para realizar as operação de criptografia.");
    }
}
