package com.xantrix.webapp.repository;

 
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.xantrix.webapp.entity.DettPromo;

public interface PrezziPromoRepository extends JpaRepository<DettPromo, Long>
{
	
	//Query JPQL - Selezione promo per codice articolo
	@Query(value = "SELECT d.* " +
					"FROM dettpromo d " +
					"JOIN promo p " +
					"ON p.idpromo = d.idpromo " +
					"WHERE d.codart = ?1 " +
					"AND d.isfid = 'No' " +
					"AND CURRENT_DATE " +
					"BETWEEN d.inizio AND d.fine", nativeQuery = true)
	DettPromo selByCodArt(@Param("codart") String codArt);
	
	
	//Query JPQL - Selezione promo fidelity per codice articolo
	@Query(value = "SELECT d.* " +
					"FROM dettpromo d " +
					"JOIN promo p " +
					"ON d.idpromo = p.idpromo " +
					"WHERE d.codart = ?1" +
					" AND d.isfid = 'Si' " +
					"AND CURRENT_DATE " +
					"BETWEEN d.inizio AND d.fine", nativeQuery = true)
	DettPromo selByCodArtAndFid(@Param("codart") String codArt);
	
		
	//Query JPQL - Selezione promo per codice fidelity e codice
	@Query(value = "SELECT d.* " +
			"FROM dettpromo d " +
			"JOIN promo p " +
			"ON d.idpromo = p.idpromo " +
			"WHERE d.codart = :codart " +
			"AND d.codfid = :codfid " +
			"AND CURRENT_DATE " +
			"BETWEEN d.inizio AND d.fine", nativeQuery = true)
	DettPromo selByCodArtAndCodFid(@Param("codart") String codArt, @Param("codfid") String codFid);
	
	/*
	@Query("SELECT DISTINCT b FROM Promo a JOIN a.dettPromo b WHERE CURRENT_DATE BETWEEN b.inizio AND b.fine")
	List<DettPromo> SelPromoActive();
	
	//Query SQL - Modifica oggetto promozione (prezzo)
	@Modifying
	@Query(value = "UPDATE dettpromo SET OGGETTO = :oggetto WHERE ID = :id", nativeQuery = true)
	void UpdOggettoPromo(@Param("oggetto") String Oggetto, @Param("id") Long Id);
	*/

		
}
