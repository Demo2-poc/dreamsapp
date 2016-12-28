package dreams.app.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import dreams.app.dao.DreamsPersistDAO;
import dreams.app.entity.ReservationPayload;
import dreams.app.model.DreamsModel;
import dreams.app.util.XmlParser;


@Service
public class DreamsPersistServiceImpl implements DreamsPersistService{
	
	@Autowired
	DreamsPersistDAO dreamsPersistDAO;
	
	@Override
    @Transactional(value="transactionManager")
	public void persisPayLoad(String payLoad) {
		
		ReservationPayload reservationPayload = null;
		try{
			if(payLoad != null){
				reservationPayload = (ReservationPayload) XmlParser.xmlStringToPojoObject(payLoad);
				DreamsModel bm = new DreamsModel();
				bm.setCanonnicalData(payLoad);
				bm.setCanonnicalId(reservationPayload.getAccommodationDetailsid());

				dreamsPersistDAO.persistPayLoad(bm);
				
			}
		}catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	@Transactional(value="transactionManager")
	public String providePayLoad(String cannonicalId) {
		try{
			return dreamsPersistDAO.providePayLoad(cannonicalId);
		}catch (Exception e) {
			e.printStackTrace();
		}
		return "";
	}

}
