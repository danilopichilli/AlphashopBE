package com.xantrix.webapp.controller;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;

import java.math.BigDecimal;

@FeignClient(name = "PromoWebService", url = "http://localhost:8091")
public interface PromoClient {

    @GetMapping("/api/promo/prezzo/{codArt}")
    public BigDecimal getPromoArt(@RequestHeader("Authorization") String authHeader, @PathVariable String codArt);

    @GetMapping("api/promo/prezzo/fidelity/{codArt}")
    public BigDecimal getPromoFidelityArt(@RequestHeader("Authorization") String authHeader, @PathVariable String codArt);

    @GetMapping("api/promo/prezzo/{codArt}/{codFid}")
    public BigDecimal getPromoOnlyFid(@RequestHeader("Authorization") String authHeader, @PathVariable String codArt, @PathVariable String codFid);
}
