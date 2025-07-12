package com.picoplaca.validator_service.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;


public class PicoYPlacaResponse {
    private String placa;
    private boolean puedeCircular;
    private String mensaje;

    public PicoYPlacaResponse(String placa, boolean puedeCircular, String mensaje) {
        this.placa = placa;
        this.puedeCircular = puedeCircular;
        this.mensaje = mensaje;
    }

    public String getPlaca() {
        return placa;
    }

    public void setPlaca(String placa) {
        this.placa = placa;
    }

    public boolean isPuedeCircular() {
        return puedeCircular;
    }

    public void setPuedeCircular(boolean puedeCircular) {
        this.puedeCircular = puedeCircular;
    }

    public String getMensaje() {
        return mensaje;
    }

    public void setMensaje(String mensaje) {
        this.mensaje = mensaje;
    }
}