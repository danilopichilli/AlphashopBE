package com.xantrix.webapp.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.xantrix.webapp.entity.Promo;

@Repository
public interface PromoRepository extends JpaRepository<Promo, String>
{
	Promo findByIdPromo(String idPromo);
	
	Promo findByAnnoAndCodice(int anno, String codice);

	@Query("SELECT DISTINCT p " +
			"FROM Promo p " +
			"JOIN DettPromo d " +
			"ON p.idPromo = d.promo.idPromo " +
			"WHERE CURRENT_DATE " +
			"BETWEEN d.inizio AND d.fine")
	List<Promo> selPromoActive();
}
