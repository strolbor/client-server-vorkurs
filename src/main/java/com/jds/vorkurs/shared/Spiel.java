package com.jds.vorkurs.shared;

import java.util.Random;
import java.util.Scanner;


public class Spiel {
	Player pPC,pMensch, Niemand, Error;
	Random r;
	int Mensch;
	String name="Namenlos";
	int auswahlID;
	public void initPlayer() {
		//Computer initalizieren
				pPC = new Player();
				pPC.setName("Computer");
				//Unentschieden initalisieren
				Niemand = new Player();
				Niemand.setName("Niemand");
				//Mensch Initalisieren
				pMensch = new Player();
				//Error Init
				Error = new Player();
				Error.setName("Error");
	}
	public void WelcomeMessage() {
		System.out.println();
		System.out.println("Willkommen bei Schere-Stein-Papier!");
		System.out.println("Bitte waehlen Sie zwischen:");
		System.out.println("1 - Papier");
		System.out.println("2 - Schere");
		System.out.println("3 - Stein");
	}
	public void HumanInput() {
		 Mensch = 0;
		@SuppressWarnings("resource")
		Scanner sc = new Scanner(System.in);
		try {
			Mensch = sc.nextInt();
		}catch (Exception e) {
			System.err.println("Bitte eine Zahl eintippen");
		}
	}
	public void pickWinner() {
		entscheide(Mensch, pMensch);

		
		Player Winner = Schiedsrichter();
		System.out.println("Der Gewinner ist: "+Winner.getName());
		System.out.println();System.out.println();System.out.println();
	}
	public void inputName() {
		System.out.println("Bitte geben Sie Ihren Namen ein");
		@SuppressWarnings("resource")
		Scanner sc = new Scanner(System.in);
		try {
			name = sc.nextLine();
		}catch (Exception e) {
			System.err.println("Bitte einen Namen eintippen");
		}
		pMensch.setName(name);
	}
	
	public Spiel(int i) {
		initPlayer();
		WelcomeMessage();
		HumanInput();
		pickWinner();
		
	}
	public Spiel() {

	}
	
	public void enemy(Player gegner) {
		pPC = gegner;
		Schiedsrichter();
	}
	private void entscheide(int PC, Player p) {
		auswahlID=PC;
		switch (PC) {
		case 1:
			p.setAuswahl(Dinge.PAPER);
			break;
		case 2:
			p.setAuswahl(Dinge.SCHERE);
			break;
		case 3:
			p.setAuswahl(Dinge.STONE);
			break;
	
		default:
			p.setAuswahl(Dinge.ERROR);
			System.err.println("Fehler! Neue Zahl auswaehlen");
			return;
		}
	}
	
	private Player Schiedsrichter() {
		System.out.println("Mensch = "+pMensch.getAuswahl());
		System.out.println("PC = "+pPC.getAuswahl());
		
		
			if(pMensch.getAuswahl() == Dinge.SCHERE) {
				if(pPC.getAuswahl() == Dinge.SCHERE) {
					//Gleich
					return Niemand;
				}
				if(pPC.getAuswahl() == Dinge.STONE) {
					//PC - Gewonnen | Spieler verloren
					return pPC;
				}
				if(pPC.getAuswahl() == Dinge.PAPER) {
					//PC - Verloren | Spieler Gewonnen
					return pMensch;
				}
			}
			if(pMensch.getAuswahl() == Dinge.STONE) {
				if(pPC.getAuswahl() == Dinge.SCHERE) {
					//PC - Verloren | Spieler Gewonnen
					return pMensch;
				}
				if(pPC.getAuswahl() == Dinge.STONE) {
					//Gleich
					return Niemand;
				}
				if(pPC.getAuswahl() == Dinge.PAPER) {
					//PC - Gewonnen | Spieler verloren
					return pPC;
				}
			}
			if(pMensch.getAuswahl() == Dinge.PAPER) {
				if(pPC.getAuswahl() == Dinge.SCHERE) {
					//PC - Gewonnen | Spieler verloren
					return pPC;
				}
				if(pPC.getAuswahl() == Dinge.STONE) {
					//PC - Verloren | Spieler Gewonnen
					return pMensch;
				}
				if(pPC.getAuswahl() == Dinge.PAPER) {
					//Gleich
					return Niemand;
				}
			}

			System.err.println("Bitte eine andere Zahl eingeben: " + pPC.getAuswahl() + " " + pMensch.getAuswahl());

		return null;
	}
	
	public static void main(String[] args) {
		new Spiel(0);
	}
	public Player getpPC() {
		return pPC;
	}
	public void setpPC(Player pPC) {
		this.pPC = pPC;
	}
	public Player getpMensch() {
		return pMensch;
	}
	public void setpMensch(Player pMensch) {
		this.pMensch = pMensch;
	}
	/*
	public void setpMenschName(String name) {
		this.pMensch.setName(name);
	}*/
	public Player getNiemand() {
		return Niemand;
	}
	public void setNiemand(Player niemand) {
		Niemand = niemand;
	}
	public Player getError() {
		return Error;
	}
	public void setError(Player error) {
		Error = error;
	}
	public Random getR() {
		return r;
	}
	public void setR(Random r) {
		this.r = r;
	}
	public int getMensch() {
		return Mensch;
	}
	public void setMensch(int mensch) {
		Mensch = mensch;
	}

}
