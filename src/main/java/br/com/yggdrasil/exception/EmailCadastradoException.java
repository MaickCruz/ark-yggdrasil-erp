package br.com.yggdrasil.exception;

@SuppressWarnings("serial")
public class EmailCadastradoException extends RuntimeException {

    public EmailCadastradoException(String e) {
        super("Email: " + e + " já cadastrado.");
    }
}
