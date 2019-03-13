package it.polito.tdp.numero.model;

import java.security.InvalidParameterException;

public class NumeroModel {
	
	private final int NMAX = 100;
	private final int TMAX = 8;

	private int segreto;
	private int tentativiFatti;
	private boolean inGioco = false;
	
	public NumeroModel() {
		inGioco = false;
	}
	
	//METODI PUBBLICI perchè saranno usati dal controllore
	
	/***
	 * Avvia nuova partita
	 */
	public void newGame() {
		this.segreto = (int) (Math.random() * NMAX) + 1;
		this.tentativiFatti = 0;
		this.inGioco = true;
		
	}
	
	/***
	 * Metodo per effettuare un tentativo
	 * @param t: il tentativo
	 * @return: 1 se il tentativo è troppo alto, -1 se è troppo basso, 0 se l'utente ha indovinato.
	 */
	
	public int tentativo(int t) {
		
		//Controllo se la partita è in corso:
		if(!inGioco) {
			throw new IllegalStateException("La partita è terminata");
		}
		
		//controllo se l'input è corretto:
		/* NOTA: il controllo se il tentativo è del tipo corretto va fatto del Controlloer, 
		 * infatti a questo metodo del Controller deve venire passato un dato di tipo intero, 
		 * il controllo sarà quindi fatto in precedenza.
		 * */
		
		//controllo che il tentativo inserito sia nel range corretto:
		if(!tentativoValido(t)) {
			throw new InvalidParameterException(String.format("Devi inserire un numero tra %d e %d", 1, NMAX));
		}
		
		//gestisco il tentativo:
		
		this.tentativiFatti++;
		
		//se esaurisco i tentativi la partita finisce
		if(this.tentativiFatti == this.TMAX) {
			this.inGioco = false;
		}
		
		//HO INDOVINATO:
		if(t == this.segreto) {
			this.inGioco = false;
			return 0;
		}
		
		//NON HO INDOVINATO:
		//tentativo troop alto
		if(t > this.segreto) {
			return 1;
		}
		//tentativo troppo basso
		return -1;
	}
	
	public boolean tentativoValido(int t) {
		if(t<1 || t> NMAX) {
			return false;
		} else {
			return true;
		}
	}

	/*la set di inGioco non mi serve, infatti è il modello che deve decidere se si è in gioco o no,
	 *non il controller.*/
	public boolean isInGioco() {
		return inGioco;
	}

	public int getSegreto() {
		return segreto;
	}

	public int getTentativiFatti() {
		return tentativiFatti;
	}
	
	public int getTMAX() {
		return TMAX;
	}
	
	
	
	
	
	
	

}
