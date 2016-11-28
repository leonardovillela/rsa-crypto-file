package br.com.villela.rsaencryptionfile;

import static java.util.Arrays.asList;

enum Operations {
    CRYPT("crypt"),
    DECRYPT("decrypt");

    private String name;

    private Operations(String name) {
        this.name = name;
    }

    public static Operations getEnumByString(String enumName) {
        return asList(values())
            .stream()
            .filter(it -> it.name.equals(enumName))
            .findFirst()
            .orElseThrow(IllegalArgumentException::new);
    }
}
