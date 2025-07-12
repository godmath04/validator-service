package com.picoplaca.validator_service.controller;
import com.picoplaca.validator_service.dto.PicoYPlacaRequest;
import com.picoplaca.validator_service.dto.PicoYPlacaResponse;
import com.picoplaca.validator_service.service.PicoYPlacaService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/validar")
//Para limitar despues en producción
@CrossOrigin(origins = "*")

public class PicoYPlacaController {
    private final PicoYPlacaService service;

    public PicoYPlacaController(PicoYPlacaService service) {
        this.service = service;
    }

    @PostMapping
    public PicoYPlacaResponse validar(@RequestBody @Valid PicoYPlacaRequest request) {
        return service.validar(request);
    }


}
