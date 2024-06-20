package com.xantrix.webapp.controller;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;

import java.math.BigDecimal;

@FeignClient(name = "PriceArtService", url = "http://localhost:5071")
public interface PriceClient {

    @GetMapping("/api/prezzi/{codArt}")
    public BigDecimal getDefPriceArt(@RequestHeader("Authorization") String authHeader, @PathVariable String codArt);

    @GetMapping("/api/prezzi/{codArt}/{idList}")
    public BigDecimal getPriceArt(@RequestHeader("Authorization") String authHeader, @PathVariable String codArt, @PathVariable String idList);

    @GetMapping("/api/prezzi/noauth/{codArt}/{idList}")
    public BigDecimal getPriceArt(@PathVariable String codArt, @PathVariable String idList);

    @GetMapping("/api/prezzi/noauth/{codArt}")
    public BigDecimal getDefPriceArt(@PathVariable String codArt);
}
