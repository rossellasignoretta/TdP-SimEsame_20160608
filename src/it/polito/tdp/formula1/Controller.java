package it.polito.tdp.formula1;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.net.URL;
import java.util.ResourceBundle;

import it.polito.tdp.formula1.model.Circuit;
import it.polito.tdp.formula1.model.Driver;
import it.polito.tdp.formula1.model.FantaDriver;
import it.polito.tdp.formula1.model.Model;
import it.polito.tdp.formula1.model.Race;
import it.polito.tdp.formula1.model.Season;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;

public class Controller {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private ComboBox<Circuit> boxCircuit;

    @FXML
    private ComboBox<Driver> boxDriver;

    @FXML
    private ComboBox<Season> boxSeason;

    @FXML
    private TextArea txtResult;

	private Model model;

    @FXML
    void doFantaGara(ActionEvent event) {
    	txtResult.clear();
    	
    	Circuit c=boxCircuit.getValue();
    	Driver d=boxDriver.getValue();
    	
    	if(c==null){
    		txtResult.setText("Scegli un circuito!");
    		return;
    	}
    	
    	if(d==null){
    		txtResult.setText("Scegli un pilota!");
    		return;
    	}
    	
    	List<FantaDriver> classifica= model.doFantaGara(d, c);
    	
    	Collections.sort(classifica, new Comparator<FantaDriver>(){

			@Override
			public int compare(FantaDriver f1, FantaDriver f2) {
				return f2.getPunteggio()-f1.getPunteggio();
			}
    		
    	});
    	
    	txtResult.appendText("RISULTATO FANTA GARA: \n");
    	for(FantaDriver fd: classifica){
    		txtResult.appendText(fd.getYear().getValue()+": "+fd.getPunteggio()+"\n");
    	}
    }

    @FXML
    void doInfoGara(ActionEvent event) {
    	txtResult.clear();

    	Circuit c=boxCircuit.getValue();
    	Season s=boxSeason.getValue();
    	
    	if(c==null){
    		txtResult.setText("Scegli un circuito!");
    		return;
    	}
    	
    	if(s==null){
    		txtResult.setText("Scegli una stagione!");
    		return;
    	}
    	
    	Race r= model.getGara(s, c);
    	List<Driver> d= model.getPiloti(r);
    	
    	txtResult.appendText(r.toString()+"\nElenco Piloti: "+d.toString());
    	
    	boxDriver.getItems().clear();
    	boxDriver.getItems().addAll(d);
    }
    
    @FXML
    void caricaCircuiti(ActionEvent event) {
    	Season season= boxSeason.getValue();
    	
    	boxCircuit.getItems().clear();
    	boxCircuit.getItems().addAll(model.getCircuitiForSeason(season));

    }

    @FXML
    void initialize() {
        assert boxCircuit != null : "fx:id=\"boxCircuit\" was not injected: check your FXML file 'Formula1.fxml'.";
        assert boxDriver != null : "fx:id=\"boxDriver\" was not injected: check your FXML file 'Formula1.fxml'.";
        assert boxSeason != null : "fx:id=\"boxSeason\" was not injected: check your FXML file 'Formula1.fxml'.";
        assert txtResult != null : "fx:id=\"txtResult\" was not injected: check your FXML file 'Formula1.fxml'.";

    }

	public void setModel(Model model) {
		this.model = model ;
		
		
		boxSeason.getItems().addAll(this.model.getAllSeasons());
	}
}
