package introsde.rest.ehealth.model;

import introsde.rest.ehealth.model.MeasureDefinition;
import java.util.List;
import java.util.ArrayList;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlElement;

@XmlRootElement(name = "measureTypes")
public class MeasureDefinitionSimpleList {
	

	@XmlElement
	List<String> measureType = new ArrayList<String>();


	public MeasureDefinitionSimpleList(){
		List<MeasureDefinition> md = MeasureDefinition.getAll();

		for(int i=0; i<md.size(); i++){
			MeasureDefinition m = md.get(i);
			measureType.add(m.getMeasureName());
		}
	}
}