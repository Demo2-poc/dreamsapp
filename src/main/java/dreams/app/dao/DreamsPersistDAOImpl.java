package dreams.app.dao;

import org.apache.log4j.Logger;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import dreams.app.model.DreamsModel;

@Repository
public class DreamsPersistDAOImpl implements DreamsPersistDAO {

	@Autowired
	@Qualifier(value = "hibernate4AnnotatedSessionFactory")
	private SessionFactory sessionFactory;
	final static Logger logger = Logger.getLogger(DreamsPersistDAOImpl.class);

	public SessionFactory getSessionFactory() {
		return sessionFactory;
	}

	public void setSessionFactory(SessionFactory sf) {
		this.sessionFactory = sf;
	}

	@Override
	public void persistPayLoad(DreamsModel dreamsModel) {
		long startTime = System.currentTimeMillis();
		logger.debug("started persistPayLoad service is invoked  start time in ms =" + startTime);
		try {
			Session session = this.sessionFactory.getCurrentSession();
			session.persist(dreamsModel);
		} catch (Exception e) {
			logger.error(e.getMessage());
			e.printStackTrace();
		}
		long durationTaken = System.currentTimeMillis() - startTime;
		logger.debug("total time taken to process request ms =" + durationTaken);
	}

	@Override
	public String providePayLoad(String cannonicalId) {
		long startTime = System.currentTimeMillis();
		logger.debug("started providePayLoad service is invoked  start time in ms =" + startTime);
		String payload = "";

		try {
			Session session = this.sessionFactory.getCurrentSession();
			Criteria cr = session.createCriteria(DreamsModel.class);
			cr.add(Restrictions.eq("canonnicalId", cannonicalId));
			int size = cr.list().size();
			if (size > 0) {
				payload = ((DreamsModel) cr.list().get(0)).getCanonnicalData();
			}
		} catch (Exception e) {
			logger.error(e.getMessage());
			e.printStackTrace();
		}
		long durationTaken = System.currentTimeMillis() - startTime;
		logger.debug("total time taken to process request ms =" + durationTaken);
		return payload;
	}

}
