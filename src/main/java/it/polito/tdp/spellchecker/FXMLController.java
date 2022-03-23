package it.polito.tdp.spellchecker;

import java.net.URL;
import java.util.*;

import it.polito.tdp.spellchecker.model.Dictionary;
import it.polito.tdp.spellchecker.model.RichWord;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;

public class FXMLController {

	private Dictionary model;
	
    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private ComboBox<String> cmbLanguage;

    @FXML
    private Label errorsLabel;

    @FXML
    private Label timeLabel;

    @FXML
    private TextArea txtText;

    @FXML
    private TextArea txtWrongWords;

    @FXML
    void doChangeLanguage(ActionEvent event) {
    	String language = cmbLanguage.getValue();
    	model.loadDictionary(language);
    }

    @FXML
    void doClearText(ActionEvent event) {
    	txtText.clear();
    	txtWrongWords.clear();
    	errorsLabel.setText("");
    	timeLabel.setText("");
    	errorsLabel.setText("");
    }

    @FXML
    void doSpellCheck(ActionEvent event) {
    	txtWrongWords.clear();
    	String text = txtText.getText().replaceAll("[?.,\\/#!$%\\^&\\*;:{}=\\-_`~()\\[\\]\"]", "").toLowerCase();

    	List<String> l = new ArrayList<String>();
    	
    	String[] s = text.split(" ");
    	for(String ss: s)
    		l.add(ss);
    	
    	List<RichWord> result = new ArrayList<RichWord>();
    	double startTime = System.nanoTime();
    	result = model.spellCheckTextDichotomic(l);
    	double endTime = System.nanoTime();
    	int errors = 0;
    	
    	for(RichWord r: result) {
    		if(!r.isRight()) {
    			txtWrongWords.appendText(r.getWord()+"\n");	
    			errors++;
    		}
    	}
    	
    	timeLabel.setText("Spell check completed in "+ ((endTime-startTime)/1000000000) + " seconds.");
    	errorsLabel.setText("The text contains " + errors + " errori.");
    }

    public void setModel(Dictionary model) {
		this.model = model;
	}

	@FXML
    void initialize() {
        assert cmbLanguage != null : "fx:id=\"cmbLanguage\" was not injected: check your FXML file 'Scene.fxml'.";
        assert errorsLabel != null : "fx:id=\"errorsLabel\" was not injected: check your FXML file 'Scene.fxml'.";
        assert timeLabel != null : "fx:id=\"timeLabel\" was not injected: check your FXML file 'Scene.fxml'.";
        assert txtText != null : "fx:id=\"txtText\" was not injected: check your FXML file 'Scene.fxml'.";
        assert txtWrongWords != null : "fx:id=\"txtWrongWords\" was not injected: check your FXML file 'Scene.fxml'.";

        cmbLanguage.getItems().clear();
        cmbLanguage.getItems().addAll("Italiano", "English");
    }

}



