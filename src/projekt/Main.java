package projekt;

import projekt.czas.Godzina;
import projekt.linie.Linia;
import projekt.linie.LiniaAutobusowa;
import projekt.linie.LiniaTramwajowa;
import projekt.przystanki.Przystanek;
import projekt.zapytania.Trasa;
import projekt.zapytania.Zapytanie;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class Main {
    public static void main(String[] args) {
        String nazwy = "nazwy.txt";
        ArrayList<Linia> wszystkie_linie = new ArrayList<>();
        ArrayList<Przystanek> wszystkie_przystanki = new ArrayList<>();
        File plik = new File("C:\\Users\\magda\\Documents\\UWr\\PO\\TrasaFinder\\test\\" + nazwy);
        BufferedReader czytnik;

        try {
            czytnik = new BufferedReader(new FileReader(plik));
            String t_czy_a = czytnik.readLine();
            String plik_nowej_linii = czytnik.readLine();
            while (plik_nowej_linii != null) {
                if (t_czy_a.equals("T")) wszystkie_linie.add(new LiniaTramwajowa(plik_nowej_linii, wszystkie_przystanki));
                else wszystkie_linie.add(new LiniaAutobusowa(plik_nowej_linii, wszystkie_przystanki));
                t_czy_a = czytnik.readLine();
                plik_nowej_linii = czytnik.readLine();
            }
        }
        catch (IOException e) {
            e.printStackTrace();
        }

        Zapytanie z1 = new Zapytanie("Kowale", "Uniwersytet", new Godzina("12:00"), false, false, false, false);
        Trasa t1 = z1.znajdzTrase(wszystkie_przystanki);
        t1.wypiszTrase();

        Zapytanie z2 = new Zapytanie("Kowale", "Uniwersytet", new Godzina("12:00"), true, false, false, false);
        Trasa t2 = z2.znajdzTrase(wszystkie_przystanki);
        t2.wypiszTrase();

        Zapytanie z3 = new Zapytanie("Kliniki", "Uniwersytet", new Godzina("11:04"), false, false, false, false);
        Trasa t3 = z3.znajdzTrase(wszystkie_przystanki);
        t3.wypiszTrase();

        Zapytanie z4 = new Zapytanie("Kliniki", "Uniwersytet", new Godzina("11:04"), false, false, false, true);
        Trasa t4 = z4.znajdzTrase(wszystkie_przystanki);
        t4.wypiszTrase();
    }
}