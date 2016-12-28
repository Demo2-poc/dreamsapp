package dreams.app.service;

public interface DreamsPersistService {
	
	public void persisPayLoad(String payLoad);

	public String providePayLoad(String cannonicalId);

}