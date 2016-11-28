package br.com.villela.rsaencryptionfile.target;

public class FileTargetReadException extends RuntimeException {

    public FileTargetReadException() {
        super("Não foi possivel ler o arquivo para realizar as operação de criptografia.");
    }
}
