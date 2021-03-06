package gui;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.LinkedList;
import java.util.Random;
import java.util.Scanner;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.border.EmptyBorder;

import gui.models.RangListaTableModel;
import kviz.GrupaPitanja;
import kviz.Pitanje;
import ucesnici.GrupaUcesnika;
import ucesnici.Ucesnik;


public class GUIKontroler extends JFrame {

	private JPanel contentPane;
	private static Pocetna glavniProzor;
	public static GrupaPitanja grupa;
	public static GrupaUcesnika grupaUcesnika;
	public static RangLista prikaz;
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) throws IOException {
		
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					grupa = new GrupaPitanja();
					grupaUcesnika = new GrupaUcesnika();
					prikaz = new RangLista();
					glavniProzor = new Pocetna();
					glavniProzor.setVisible(true);
					glavniProzor.setLocationRelativeTo(null);
					
					
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
		ucitajTXTFajl();
		
	}

	private static void ucitajTXTFajl() throws IOException {
		BufferedReader reader = null;
        reader = new BufferedReader(new FileReader("pitanja.txt"));

        String line;
       

        Scanner scan = new Scanner(System.in);

        String questionNum = "";
        String question = "", choiceA = "", choiceB = "", choiceC = "", choiceD = "", answer = "";
        while((line = reader.readLine()) != null){

            if(line.contains("?")){
                question = line;
                continue;
            }

            if(line.contains("a)")){
                choiceA = line;
                continue;
            }

            if(line.contains("b)")){
                choiceB = line;
                continue;
            }

            if(line.contains("c)")){
                choiceC = line;
                continue;
            }

            if(line.contains("d)")){
                choiceD = line;
                continue;
            }
                
                answer = line;
                String [] ponudjeniOdgovori = {choiceA, choiceB, choiceC, choiceD};
                Pitanje pitanje = new Pitanje(question, ponudjeniOdgovori, answer);
                grupa.unesiPitanje(pitanje);
        }
		
	}

	/**
	 * Create the frame.
	 */
	public GUIKontroler() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);
	}

	public static void ugasiAplikaciju() {
		int opcija = JOptionPane.showConfirmDialog(null, "Da li zelite da zatvorite program?",
				"Zatvaranje aplikacije", JOptionPane.YES_NO_OPTION);
		
		if (opcija == JOptionPane.YES_OPTION) {
			System.exit(0);
		
		}
	}

	public static LinkedList<Pitanje> vratiPitanja(){
		return grupa.getPitanja();
	}

	public static int nasumicniRedniBrojPitanja() {
	    Random rand = new Random();
	    int randomNum = rand.nextInt((grupa.getPitanja().size()-1 - 0) + 1) + 0;
	    return randomNum;
	}
	
	public static void osveziTabelu(JTable table){
		RangListaTableModel model = (RangListaTableModel) table.getModel();
		model.ucitajUcesnike(GUIKontroler.vratiSveUcesnike());
	}

	public static LinkedList<Ucesnik> vratiSveUcesnike() {
		return grupaUcesnika.vratiSveUcesnike();
	}

	public static void unesiUcesnika(String ime, int brojPoena){
		try{
			Ucesnik u = new Ucesnik(ime, brojPoena);
			grupaUcesnika.unesiUcesnika(u);
			osveziTabelu(prikaz.getTable());
		}catch(Exception e1){
			JOptionPane.showMessageDialog(null, "Proveri formu "+ e1.getMessage(), "Greska", JOptionPane.ERROR_MESSAGE);
		}
		
	}
	public static void ucitajIzFajla(){
		try {
			grupaUcesnika.ucitajIzFajlaDeserijalizacijom();
			osveziTabelu(prikaz.getTable());
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, e.getMessage(), "Greskicica", JOptionPane.ERROR_MESSAGE);
		}
	}
	public static void otvoriRangListu() {
		RangLista prikaz = new RangLista();
		prikaz.setVisible(true);
		prikaz.setLocationRelativeTo(null);
		osveziTabelu(prikaz.getTable());
		prikaz.setTitle("Prikaz ucesnika");
	}
}
