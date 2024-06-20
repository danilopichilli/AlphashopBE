package com.xantrix.webapp.service;

import com.xantrix.webapp.entity.DettListini;
import com.xantrix.webapp.repository.PrezziRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
public class PrezziServiceImpl implements PrezziService{

    private final PrezziRepository repository;

    public PrezziServiceImpl(PrezziRepository repository) {
        this.repository = repository;
    }

    @Override
    public DettListini findByCodArtAndIdList(String codArt, String idList) {
        if(codArt != null && idList != null) {
            DettListini d = repository.findByCodArtAndIdList(codArt, idList);
            if(d != null) {
                return d;
            }
        }
        return null;
    }

    @Override
    @Transactional
    public void deleteByCodArtAndIdList(String codArt, String idList) {
        repository.deleteByCodArtAndIdList(codArt, idList);
    }
}

