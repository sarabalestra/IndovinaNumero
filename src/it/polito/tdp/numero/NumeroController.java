package it.polito.tdp.numero;

import java.net.URL;
import java.util.ResourceBundle;

import it.polito.tdp.numero.model.NumeroModel;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;

public class NumeroController {
	
	private NumeroModel model;

	@FXML
	private ResourceBundle resources;

	@FXML
	private URL location;

	@FXML
	private HBox boxControllopartita;

	@FXML
	private TextField txtRimasti;
	// numero di tentativi rimasti ancora da provare

	@FXML
	private HBox boxControlloTentativi;

	@FXML
	private TextField txtTentativo;
	// tentativo inserito dall'utente

	@FXML
	private TextArea txtMessaggi;

	
	public void setModel(NumeroModel model) {
		this.model = model;
	}
	
	@FXML
	void handleNuovaPartita(ActionEvent event) {
		//Gestisce l'inizio di una nuova partita
		
		// Gestione dell'interfaccia
		boxControllopartita.setDisable(true);
		boxControlloTentativi.setDisable(false);
		txtMessaggi.clear();
		txtTentativo.clear();
		txtRimasti.setText(Integer.toString(model.getTMAX()));
		
		//Comunico al modello di iniziare una nuova partita
		model.newGame();
		

	}

	@FXML
	void handleProvaTentativo(ActionEvent event) {

		// Leggi il valore del tentativo
		String ts = txtTentativo.getText();

		// Controlla se è valido il tipo di dato
		/*Il controllo su tipo di dato rimane nel Controller,
		 *infatti il Model richiede un dato di tipo intero.*/
		int tentativo ;
		try {
			tentativo = Integer.parseInt(ts);
		} catch (NumberFormatException e) {
			// la stringa inserita non è un numero valido
			txtMessaggi.appendText("Non è un numero valido\n");
			return ;
		}
		
		//controllo se il tentativo è nel range corretto
		if(!model.tentativoValido(tentativo)) {
			txtMessaggi.appendText("Range non valido!\n");
			return ;
		}
		
		int risultato = model.tentativo(tentativo);
		
		txtTentativo.clear();
		
		if(risultato == 0) {
			txtMessaggi.appendText("Complimenti, hai indovinato in "+model.getTentativiFatti()+" tentativi\n");
			boxControllopartita.setDisable(false);
			boxControlloTentativi.setDisable(true);
			
			//non serve una endGame(), il modello l'ha già fatto in tentativo.
		}else if(risultato < 0){
			txtMessaggi.appendText("Tentativo troppo BASSO\n");
		}else {
			txtMessaggi.appendText("Tentativo troppo ALTO\n");
		}

		// Aggiornare interfaccia con n. tentativi rimasti
		txtRimasti.setText(Integer.toString(model.getTMAX()- model.getTentativiFatti()));

		//Devo vedere se ho finito i tentativi, dallo stato di inGioco
		if(!model.isInGioco()) {
			//la partita è finita
			//questo può capitare se ho indovinato(caso già gestito con una stampa), o se ho terminato i tentativi
		
			if(risultato != 0) {
				txtMessaggi.appendText("Hai perso!");
				txtMessaggi.appendText(String.format("\n Il numero segreto era: %d", model.getSegreto()));
				boxControllopartita.setDisable(false);
				boxControlloTentativi.setDisable(true);
			}
		}
	}

	@FXML
	void initialize() {
		assert boxControllopartita != null : "fx:id=\"boxControllopartita\" was not injected: check your FXML file 'Numero.fxml'.";
		assert txtRimasti != null : "fx:id=\"txtRimasti\" was not injected: check your FXML file 'Numero.fxml'.";
		assert boxControlloTentativi != null : "fx:id=\"boxControlloTentativi\" was not injected: check your FXML file 'Numero.fxml'.";
		assert txtTentativo != null : "fx:id=\"txtTentativo\" was not injected: check your FXML file 'Numero.fxml'.";
		assert txtMessaggi != null : "fx:id=\"txtMessaggi\" was not injected: check your FXML file 'Numero.fxml'.";

	}
}
