package dreams.app.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.Table;

@Entity
@Table(name="RESERVATION_PAYLOAD")
public class DreamsModel {

	@Id
	@Column(name="CANONNICALID")
	private String canonnicalId;
	
	@Lob
	@Column(name="CANONNICALDATA")
	private String canonnicalData;
	
	public DreamsModel(){
		
	}
	public String getCanonnicalId() {
		return canonnicalId;
	}
	public void setCanonnicalId(String canonnicalId) {
		this.canonnicalId = canonnicalId;
	}
	
	public String getCanonnicalData() {
		return canonnicalData;
	}
	public void setCanonnicalData(String canonnicalData) {
		this.canonnicalData = canonnicalData;
	}
}
