package co.gov.dafp.sigep2.converter;

import java.util.List;

import javax.faces.component.UIComponent;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

import co.gov.dafp.sigep2.deledago.HojaDeVidaDelegate;
import co.gov.dafp.sigep2.entity.view.TipoLetraDTO;

@FacesConverter("tipoLetraConverter")
public class TipoLetraConverter implements Converter {

	@Override
	@SuppressWarnings("unchecked")
	public Object getAsObject(FacesContext facesContext, UIComponent uiComponent, String value) {
		if (value == null || value.isEmpty()) {
			return null;
		}
		ExternalContext contexto = FacesContext.getCurrentInstance().getExternalContext();
		List<TipoLetraDTO> tipoLetra = (List<TipoLetraDTO>) contexto.getSessionMap()
				.get("tipoLetra");
		try {
			if (tipoLetra == null) {
				tipoLetra = HojaDeVidaDelegate.buscarTipoLetra();
				contexto.getSessionMap().put("tipoLetra", tipoLetra);
			}
		} catch (Exception e) {
			return null;
		}

		TipoLetraDTO tipoLetraDTO = new TipoLetraDTO();
		tipoLetraDTO.setId(Long.valueOf(value));
		return tipoLetra.get(tipoLetra.indexOf(tipoLetraDTO));
	}

	@Override
	public String getAsString(FacesContext facesContext, UIComponent uiComponent, Object value) {
		if (value == null) {
			return "";
		}

		if (value instanceof String) {
			return value.toString();
		}

		Object id = String.valueOf(((TipoLetraDTO) value).getId());
		if (id == null || id.toString().isEmpty()) {
			return "";
		}
		return id.toString();
	}
}
