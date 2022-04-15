package projekt.przystanki;

import projekt.linie.Linia;
import projekt.czas.BrakGodziny;
import projekt.czas.Godzina;

import java.util.ArrayList;

public class TabliczkaPrzystankowa {
    ArrayList<Godzina>[] godziny_odjazdow;

    public TabliczkaPrzystankowa() {
        godziny_odjazdow = new ArrayList[1000];
    }

    public void dodajNowaLinie(Linia nowa_linia) {
        godziny_odjazdow[nowa_linia.numer] = new ArrayList<>();
    }

    public void dodajKurs(int numer_linii, Godzina g) {
        godziny_odjazdow[numer_linii].add(g);
    }

    public Godzina kiedyNastepnyKurs(int numer_linii, Godzina po_ktorej) {
        for (int i = 0; i < godziny_odjazdow[numer_linii].size(); i++) {
            Godzina rozwazana_godz = (godziny_odjazdow[numer_linii]).get(i);
            if (rozwazana_godz.czyPozniejOd(po_ktorej)) return rozwazana_godz;
        }
        return new BrakGodziny();
    }
}
