package br.com.fiap.pos_tech_adj.tech_challenge_fase2.controller.exception;

public class ControllerNotFoundException extends RuntimeException {

    public ControllerNotFoundException (String message){
        super(message);
    }
}
