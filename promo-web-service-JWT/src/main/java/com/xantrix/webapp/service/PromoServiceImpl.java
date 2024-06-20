package com.xantrix.webapp.service;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.stereotype.Service;

import com.xantrix.webapp.entity.Promo;
import com.xantrix.webapp.repository.PromoRepository;

@Service
@Transactional
public class PromoServiceImpl implements PromoService {

	private final PromoRepository promoRepository;

    public PromoServiceImpl(PromoRepository promoRepository) {
        this.promoRepository = promoRepository;
    }

    @Override
	public List<Promo> selTutti()
	{
		return promoRepository.findAll();
	}

	@Override
	public Promo selByIdPromo(String idPromo)
	{
		return promoRepository.findByIdPromo(idPromo);
	}

	@Override
	public Promo selByAnnoCodice(String anno, String codice)
	{
		return promoRepository.findByAnnoAndCodice(Integer.parseInt(anno), codice);
	}

	@Override
	public List<Promo> selPromoActive()
	{
		return promoRepository.selPromoActive();
	}

	@Override
	public void insPromo(Promo promo)
	{
		promoRepository.saveAndFlush(promo);
	}

	@Override
	public void delPromo(Promo promo)
	{
		promoRepository.delete(promo);
	}

}
