package com.di.relacional.client;

import com.di.relacional.client.response.AddressResponse;
import com.di.relacional.config.FeignConfig;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(
        name = "addressClient",
        url = "https://viacep.com.br/ws",
        configuration = FeignConfig.class
)
public interface AddressClient {
    @GetMapping("/{cep}/json")
    ResponseEntity<AddressResponse> findCep(
            @PathVariable("cep") String cep
    );
}

