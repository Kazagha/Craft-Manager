import java.io.File;
import java.io.IOException;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import javax.xml.bind.Marshaller;

public class JAXBController {
	
	private File file;
	
	private JAXBContext jaxbContext; 
	private Unmarshaller unmarshaller = null;
	private Marshaller marshaller = null;

	public JAXBController(File file) {
		this.file = file;
		this.setUp();
	}
	
	private void setUp() {
		try {
			jaxbContext = JAXBContext.newInstance(
					//  Model
					Model.class,
					// Items
					Item.class,
					ItemMundane.class,
					ItemMagic.class,
					ItemMagicBasic.class,
					// Effects
					Effect.class,
					EffectSpell.class,
					EffectBonus.class,
					EffectStatic.class);		
			unmarshaller = jaxbContext.createUnmarshaller();
			marshaller = jaxbContext.createMarshaller();
			marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
		} catch (JAXBException e) {
			System.out.format("Error configuring JAXB / Marshaller%n");
			e.printStackTrace();
		}
	}
	
	private boolean checkfile(File file) {
		if(file.exists()) {
			return true;
		}
		
		try {
			file.createNewFile();
		} catch (IOException e) {
			System.out.format("Cannot create file in the following location%n%s%n",
					file.toPath().toString());
			e.printStackTrace();
		}
		
		return false;
	}
	
	public void setfile(File file) {
		this.file = file;
	}
	
	public File getFile() 
	{
		return this.file;
	}
	
	public void save(Model model) {
		checkfile(file);
		
		try {
			marshaller.marshal(model, file);
		} catch (JAXBException e) {
			System.out.format("Error saving to file: %s", file.toString());
			e.printStackTrace();
		}
	}
	
	public Model load() {
		if(checkfile(file)) {
			try {
				return (Model) unmarshaller.unmarshal(file);
			} catch (JAXBException e) {
				System.out.format("Error loading file: %s", file.toString());
				e.printStackTrace();
			}
		}
		
		return new Model();
	}	
}