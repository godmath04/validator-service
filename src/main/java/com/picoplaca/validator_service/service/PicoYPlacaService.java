package com.picoplaca.validator_service.service;
import com.picoplaca.validator_service.dto.PicoYPlacaRequest;
import com.picoplaca.validator_service.dto.PicoYPlacaResponse;
import com.picoplaca.validator_service.exception.InvalidDateTimeException;
import com.picoplaca.validator_service.restrictionpolicy.PicoYPlacaRestriction;
import org.springframework.stereotype.Service;

import java.time.*;

@Service
public class PicoYPlacaService {

    //Resctriction policy
    private final PicoYPlacaRestriction restriction;

    public PicoYPlacaService(PicoYPlacaRestriction restriction) {
        this.restriction = restriction;
    }

    public PicoYPlacaResponse validar(PicoYPlacaRequest request) {
        String placa = request.getPlaca().trim().toUpperCase();
        LocalDate fecha = LocalDate.parse(request.getFecha());
        LocalTime hora = LocalTime.parse(request.getHora());
        LocalDateTime consulta = LocalDateTime.of(fecha, hora);

        if (consulta.isBefore(LocalDateTime.now())) {
            throw new InvalidDateTimeException("No se puede consultar en el pasado");
        }

        int ultimoDigito = Character.getNumericValue(placa.charAt(placa.length() - 1));
        boolean puedeCircular = restriction.puedeCircular(fecha, ultimoDigito, hora);

        String mensaje = puedeCircular
                ? "El carro puede circular el " + fecha + " a las " + hora
                : "El carro no puede circular el " + fecha + " a las " + hora;

        return new PicoYPlacaResponse(placa, puedeCircular, mensaje);
    }

}
