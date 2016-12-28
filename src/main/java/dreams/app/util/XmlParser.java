package dreams.app.util;

import org.apache.commons.lang.StringUtils;

import dreams.app.entity.ReservationPayload;


public class XmlParser {
	
	/*public static Object xmlStringToObject(final String canonicalXml) throws JAXBException {
		final JAXBContext jContext = JAXBContext.newInstance(DreamsPayloadEvent.class);
		final Unmarshaller jaxbUnmarshaller = jContext.createUnmarshaller();
		final StringReader reader = new StringReader(canonicalXml);
		return jaxbUnmarshaller.unmarshal(reader);

	}*/
	
	public static ReservationPayload xmlStringToPojoObject(final String canonicalXml){
		
		ReservationPayload obj = new ReservationPayload();
		
		try{
			if (!canonicalXml.equals("")) {
                String aer = StringUtils.substringBetween(canonicalXml, "<ns5: externalReferenceIdsid=\"{","}\"type=\"swid\"/>").trim();
                obj.setExternalReferenceIdsid(aer.replaceAll("[{}]", ""));
                
                String accommodationId = StringUtils.substringBetween(canonicalXml, "<accommodationDetailsid=","enterpriseFacilityId=").trim();
                obj.setAccommodationDetailsid(accommodationId.replaceAll("[\"\"]", "").trim());
          }
		}catch (Exception e) {
			e.printStackTrace();
		}
		
		return obj;
	}

}
