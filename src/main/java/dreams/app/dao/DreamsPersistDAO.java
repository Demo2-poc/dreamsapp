package dreams.app.dao;

import dreams.app.model.DreamsModel;

public interface DreamsPersistDAO {
	
	public void persistPayLoad(DreamsModel bm);

	public String providePayLoad(String cannonicalId);

}
