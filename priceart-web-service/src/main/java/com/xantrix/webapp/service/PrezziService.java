package com.xantrix.webapp.service;

import com.xantrix.webapp.entity.DettListini;

public interface PrezziService {

    DettListini findByCodArtAndIdList(String codArt, String idList);

    void deleteByCodArtAndIdList(String codArt, String idList);
}
