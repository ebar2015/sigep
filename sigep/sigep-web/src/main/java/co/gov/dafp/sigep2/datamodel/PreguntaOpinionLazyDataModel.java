package co.gov.dafp.sigep2.datamodel;

import java.util.List;
import java.util.Map;

import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortOrder;

import co.gov.dafp.sigep2.mbean.preguntaopinion.PreguntaOpinion;
import co.gov.dafp.sigep2.servicios.ComunicacionServiciosSis;

public class PreguntaOpinionLazyDataModel extends LazyDataModel<PreguntaOpinion> {

	private static final long serialVersionUID = 1624191316405729408L;
	
	PreguntaOpinion preguntaOp;


	public PreguntaOpinion getPreguntaOp() {
		return preguntaOp;
	}


	public void setPreguntaOp(PreguntaOpinion preguntaOp) {
		this.preguntaOp = preguntaOp;
	}

	public PreguntaOpinionLazyDataModel(PreguntaOpinion preguntaOpinion) {
		this.preguntaOp = preguntaOpinion;
		
	}
	
	@Override
	public List<PreguntaOpinion> load(int first, int pageSize, String sortField, SortOrder sortOrder, Map<String,Object> filters) {
		try {
			preguntaOp.setLimitInit(first);
			preguntaOp.setLimitEnd(pageSize);
			List<PreguntaOpinion> listaPreguntaOpinion = ComunicacionServiciosSis
					.getPreguntaOpinion(preguntaOp);
				if (listaPreguntaOpinion != null && listaPreguntaOpinion.size() > 0) {
					PreguntaOpinion r = (PreguntaOpinion) listaPreguntaOpinion.get(0);
					this.setRowCount(r.getTotal().intValue());
				} else
					this.setRowCount(0);

			return listaPreguntaOpinion;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
    }
}
