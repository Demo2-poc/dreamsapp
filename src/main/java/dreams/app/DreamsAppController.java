package dreams.app;

import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import dreams.app.entity.ReservationPayload;
import dreams.app.service.DreamsPersistService;
import dreams.app.util.XmlParser;

@RestController
public class DreamsAppController {
	
	@Value("${rabbit.nge.queue.DREAMS}")
	private String queueKey;
	
	@Autowired
	private DreamsPersistService dreamsPersistService;
	
	public static final String OBJECT_KEY = "RESERVATION.PAYLOAD";
	
	@Autowired
	@Qualifier("redisTemplate")
	private RedisTemplate<String, String> redisTemplate;
	
	@RequestMapping(value="/storereservationpayload",method = RequestMethod.POST,consumes = "application/xml")
	public ResponseEntity<String> processPayloadAsXML(@RequestBody String payLoad) {
		
		ResponseEntity<String> respEntity = null;
		try{
			ReservationPayload reservationPayload =  XmlParser.xmlStringToPojoObject(payLoad);
			
			getAmqpTemplate().convertAndSend(queueKey, reservationPayload.getAccommodationDetailsid());
			
			dreamsPersistService.persisPayLoad(payLoad);
			String cacheData=(String) this.redisTemplate.opsForHash().get(OBJECT_KEY, reservationPayload.getAccommodationDetailsid());
			
			if(cacheData!=null){
				this.redisTemplate.opsForHash().put(OBJECT_KEY, reservationPayload.getAccommodationDetailsid(), reservationPayload);
			}
			
			respEntity = new ResponseEntity<String>(" Published Successfully ",HttpStatus.OK);
			
		}catch (Exception e) {
			respEntity = new ResponseEntity<String>(" Failed to publish ",HttpStatus.INTERNAL_SERVER_ERROR);
			e.printStackTrace();
		}
		
		return respEntity;
	}

	private AmqpTemplate getAmqpTemplate() {

		final ApplicationContext context = new ClassPathXmlApplicationContext("dreamsapp-context.xml");
		return context.getBean(AmqpTemplate.class);

	}
	
	@RequestMapping(value="/providereservationpayload",method = RequestMethod.GET)
	public ResponseEntity<String> providePayloadContent(@RequestParam("canonicalId") String cannonicalId) {
		
		ResponseEntity<String> respEntity = null;
		try{
			String payload = dreamsPersistService.providePayLoad(cannonicalId);
			respEntity = new ResponseEntity<String>(payload,HttpStatus.OK);
		}catch (Exception e) {
			respEntity = new ResponseEntity<String>("",HttpStatus.INTERNAL_SERVER_ERROR);
			e.printStackTrace();
		}
		
		return respEntity;
	}

}