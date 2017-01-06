package dreams.app.service;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import dreams.app.dao.DreamsPersistDAO;
import dreams.app.entity.ReservationPayload;
import dreams.app.model.DreamsModel;
import dreams.app.util.XmlParser;

@Service
public class DreamsPersistServiceImpl implements DreamsPersistService {

	@Autowired
	DreamsPersistDAO dreamsPersistDAO;
	final static Logger logger = Logger.getLogger(DreamsPersistServiceImpl.class);

	@Override
	@Transactional(value = "transactionManager")
	public void persisPayLoad(String payLoad) {
		long startTime = System.currentTimeMillis();
		logger.debug("started persisPayLoad service is invoked start time in ms =" + startTime);
		ReservationPayload reservationPayload = null;
		try {
			if (payLoad != null) {
				reservationPayload = (ReservationPayload) XmlParser.xmlStringToPojoObject(payLoad);
				DreamsModel bm = new DreamsModel();
				bm.setCanonnicalData(payLoad);
				bm.setCanonnicalId(reservationPayload.getAccommodationDetailsid());

				dreamsPersistDAO.persistPayLoad(bm);

			}
		} catch (Exception e) {
			logger.error(e.getMessage());
			e.printStackTrace();
		}
		long durationTaken = System.currentTimeMillis() - startTime;
		logger.debug("total time taken to process request ms =" + durationTaken);
	}

	@Override
	@Transactional(value = "transactionManager")
	public String providePayLoad(String cannonicalId) {
		long startTime = System.currentTimeMillis();
		logger.debug("started providePayLoad service is invoked start time in ms =" + startTime);
		try {
			return dreamsPersistDAO.providePayLoad(cannonicalId);
		} catch (Exception e) {
			logger.error(e.getMessage());
			e.printStackTrace();
		}
		long durationTaken = System.currentTimeMillis() - startTime;
		logger.debug("total time taken to process request ms =" + durationTaken);
		return "";
	}

}
