package projekt.przystanki;

import projekt.linie.Linia;
import projekt.czas.Godzina;

import java.util.ArrayList;

public class Przystanek {
    public String nazwa;
    public ArrayList<Linia> kursujace_linie;
    public TabliczkaPrzystankowa rozklad_jazdy;

    public Przystanek(String nazwa) {
        this.nazwa = nazwa;
        this.kursujace_linie = new ArrayList<>();
        this.rozklad_jazdy = new TabliczkaPrzystankowa();
    }

    public void dodajLinie(Linia nowa_linia) {
        kursujace_linie.add(nowa_linia);
        rozklad_jazdy.dodajNowaLinie(nowa_linia);
    }

    public void dodajKurs(int numer_linii, Godzina g) {
        rozklad_jazdy.dodajKurs(numer_linii, g);
    }
}