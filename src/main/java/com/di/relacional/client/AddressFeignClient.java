package com.di.relacional.client;

import com.di.relacional.client.response.AddressResponse;
import com.di.relacional.config.FeignConfig;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@FeignClient(
        name = "geminiClient",
        url = "https://viacep.com.br/ws",
        configuration = FeignConfig.class
)
public interface AddressFeignClient {
    @PostMapping("/{cep}/json")
    ResponseEntity<AddressResponse> findCep(
            @RequestAttribute("cep") String cep
    );
}

