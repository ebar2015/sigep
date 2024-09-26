package co.gov.dafp.sigep2.factoria;

import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.NoResultException;

import co.gov.dafp.sigep2.constante.SQLNames;
import co.gov.dafp.sigep2.entity.PaisDTO;
import co.gov.dafp.sigep2.entity.jpa.comun.Pais;

@Stateless
public class PaisFactoria extends AbstractFactory<Pais> {
   private static final long serialVersionUID = 8633963329914798217L;

   public PaisFactoria() {
      super(Pais.class);
   }

   @SuppressWarnings("unchecked")
   public List<PaisDTO> findPais() {
      try {
         String query = SQLNames.getSQL(SQLNames.PAIS_SQL);
         return (List<PaisDTO>) createNativeQuery(query, Pais.PAIS_MAPPING).getResultList();
      } catch (NoResultException e) {
         return null;
      }
   }

   public Pais convertirPaisDTO(PaisDTO paisDTO) {
      if (paisDTO != null) {
         return new Pais(paisDTO.getId(), paisDTO.getNombrePais(), paisDTO.isFlgActivo());
      } else {
         return null;
      }
   }

   public PaisDTO encontrarPaisPorId(long id) {
      String query = "SELECT COD_PAIS, NOMBRE_PAIS, FLG_ACTIVO FROM PAIS WHERE COD_PAIS = :codPais";
      return (PaisDTO) createNativeQuery(query, Pais.PAIS_MAPPING).setParameter("codPais", id).getSingleResult();
   }
}