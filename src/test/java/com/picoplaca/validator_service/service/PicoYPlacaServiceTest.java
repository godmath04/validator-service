package com.picoplaca.validator_service.service;

import com.picoplaca.validator_service.dto.PicoYPlacaRequest;
import com.picoplaca.validator_service.dto.PicoYPlacaResponse;
import com.picoplaca.validator_service.exception.InvalidDateTimeException;
import com.picoplaca.validator_service.restrictionpolicy.PicoYPlacaRestriction;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

class PicoYPlacaServiceTest {

    private PicoYPlacaService service;

    @BeforeEach
    void setUp() {
        service = new PicoYPlacaService(new PicoYPlacaRestriction());
    }

    @Test
    void testPuedeCircularFueraDeHoraPico() {
        // Placa termina en 1 (lunes restringido), pero fuera de hora pico
        PicoYPlacaRequest req = new PicoYPlacaRequest();
        req.setPlaca("ABC1231");
        req.setFecha(nextWeekday("MONDAY").toString());
        req.setHora("10:00");

        PicoYPlacaResponse resp = service.validar(req);
        assertTrue(resp.isPuedeCircular());
        assertTrue(resp.getMensaje().contains("puede circular"));
    }

    @Test
    void testNoPuedeCircularEnHoraPico() {
        // Placa termina en 1 (lunes restringido), y en hora pico
        PicoYPlacaRequest req = new PicoYPlacaRequest();
        req.setPlaca("ABC1231");
        req.setFecha(nextWeekday("MONDAY").toString());
        req.setHora("08:00");

        PicoYPlacaResponse resp = service.validar(req);
        assertFalse(resp.isPuedeCircular());
        assertTrue(resp.getMensaje().contains("no puede circular"));
    }

    @Test
    void testPuedeCircularFinDeSemana() {
        // Placa termina en cualquier número, día sábado
        PicoYPlacaRequest req = new PicoYPlacaRequest();
        req.setPlaca("XYZ9999");
        req.setFecha(nextWeekday("SATURDAY").toString());
        req.setHora("08:00");

        PicoYPlacaResponse resp = service.validar(req);
        assertTrue(resp.isPuedeCircular());
    }

    @Test
    void testErrorFechaPasada() {
        PicoYPlacaRequest req = new PicoYPlacaRequest();
        req.setPlaca("XYZ9999");
        req.setFecha(LocalDate.now().minusDays(1).toString());
        req.setHora("08:00");

        assertThrows(InvalidDateTimeException.class, () -> service.validar(req));
    }

    // Helper para obtener próxima fecha específica (ej: próximo lunes)
    private LocalDate nextWeekday(String dayOfWeek) {
        LocalDate date = LocalDate.now().plusDays(1);
        while (!date.getDayOfWeek().name().equalsIgnoreCase(dayOfWeek)) {
            date = date.plusDays(1);
        }
        return date;
    }
}
