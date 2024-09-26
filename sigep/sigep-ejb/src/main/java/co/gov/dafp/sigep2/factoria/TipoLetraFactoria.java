package co.gov.dafp.sigep2.factoria;

import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.NoResultException;

import co.gov.dafp.sigep2.constante.SQLNames;
import co.gov.dafp.sigep2.entity.view.TipoLetraDTO;
import co.gov.dafp.sigep2.view.TipoLetra;

@Stateless
public class TipoLetraFactoria extends AbstractFactory<TipoLetra> {
	private static final long serialVersionUID = -1218090837550198381L;

	public TipoLetraFactoria() {
		super(TipoLetra.class);
	}

	@SuppressWarnings("unchecked")
	public List<TipoLetraDTO> buscarTipoLetra() {
		try {
			String query = SQLNames.getSQL(SQLNames.TIPO_LETRA_SQL);
			return (List<TipoLetraDTO>)createNativeQuery(query, TipoLetra.TIPO_LETRA_MAPPING).getResultList();
		} catch (NoResultException e) {
			logger.error("", e);
			return null;
		}
	}
}
