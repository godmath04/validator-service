package com.picoplaca.validator_service.restrictionpolicy;

import org.springframework.stereotype.Component;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Set;

@Component
public class PicoYPlacaRestriction {
    private static final LocalTime MANANA_INICIO = LocalTime.of(6, 0);
    private static final LocalTime MANANA_FIN = LocalTime.of(9, 30);
    private static final LocalTime TARDE_INICIO = LocalTime.of(16, 0);
    private static final LocalTime TARDE_FIN = LocalTime.of(20, 0);

    public boolean puedeCircular(LocalDate fecha, int ultimoDigito, LocalTime hora) {
        DayOfWeek dia = fecha.getDayOfWeek();

        boolean restringido = switch (dia) {
            case MONDAY -> Set.of(1, 2).contains(ultimoDigito);
            case TUESDAY -> Set.of(3, 4).contains(ultimoDigito);
            case WEDNESDAY -> Set.of(5, 6).contains(ultimoDigito);
            case THURSDAY -> Set.of(7, 8).contains(ultimoDigito);
            case FRIDAY -> Set.of(9, 0).contains(ultimoDigito);
            default -> false;
        };

        boolean horaPico = (hora.isAfter(MANANA_INICIO.minusSeconds(1)) && hora.isBefore(MANANA_FIN.plusSeconds(1))) ||
                (hora.isAfter(TARDE_INICIO.minusSeconds(1)) && hora.isBefore(TARDE_FIN.plusSeconds(1)));

        return !(restringido && horaPico);
    }
}
