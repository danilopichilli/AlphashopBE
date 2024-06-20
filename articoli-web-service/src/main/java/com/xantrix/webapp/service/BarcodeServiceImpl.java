package com.xantrix.webapp.service;

import com.xantrix.webapp.entity.Barcode;
import com.xantrix.webapp.repository.BarcodeRepository;
import org.springframework.stereotype.Service;

import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
public class BarcodeServiceImpl implements BarcodeService{

    private BarcodeRepository barcodeRepository;

    public BarcodeServiceImpl(BarcodeRepository barcodeRepository) {
        this.barcodeRepository = barcodeRepository;
    }

    @Override
    public Barcode SelByBarcode(String barcode) {
        return barcodeRepository.findByBarcode(barcode);
    }
}
