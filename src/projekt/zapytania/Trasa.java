package projekt.zapytania;

import projekt.linie.Linia;
import projekt.czas.Godzina;
import projekt.przystanki.Przystanek;

import java.util.ArrayList;

public class Trasa {
    ArrayList<Linia> kolejne_linie;
    ArrayList<Przystanek> lista_przystankow;
    ArrayList<Godzina> odpowiadajace_godziny;

    public Trasa() {
        this.lista_przystankow = new ArrayList<>();
        this.odpowiadajace_godziny = new ArrayList<>();
    }

    public Trasa(Godzina g, Przystanek p, ArrayList<Linia> zarys_trasy) {
        this.lista_przystankow = new ArrayList<>();
        this.odpowiadajace_godziny = new ArrayList<>();

        this.kolejne_linie = zarys_trasy;
        this.dodajDoTrasy(p, p.rozklad_jazdy.kiedyNastepnyKurs(zarys_trasy.get(0).numer, g));

        for (int i = 1; i < kolejne_linie.size(); i++) {
            Linia poprzednia_linia = kolejne_linie.get(i - 1);
            Linia obecna_linia = kolejne_linie.get(i);
            for (Przystanek sprawdzany_p : poprzednia_linia.lista_przystankow) {
                if (sprawdzany_p.kursujace_linie.contains(obecna_linia)) {
                    Przystanek poprzedni_p = lista_przystankow.get(i - 1);
                    Godzina wyjazd_z_poprzedniego = this.odpowiadajace_godziny.get(i - 1);
                    Godzina o_ktorej_mozemy_ruszyc = wyjazd_z_poprzedniego.dodaj(poprzednia_linia.ileSieJedzie(poprzedni_p, sprawdzany_p));
                    this.dodajDoTrasy(sprawdzany_p, sprawdzany_p.rozklad_jazdy.kiedyNastepnyKurs(obecna_linia.numer, o_ktorej_mozemy_ruszyc));
                }
            }
        }
    }

    void dodajDoTrasy(Przystanek nowy_p, Godzina nowa_g) {
        lista_przystankow.add(nowy_p);
        odpowiadajace_godziny.add(nowa_g);
    }

    public void wypiszTrase() {
        System.out.println("Zaczynamy podróż!");
        for (int i = 0; i < kolejne_linie.size(); i++) {
            System.out.println("O godzinie " + odpowiadajace_godziny.get(i).wypisz() +
                               " z przystanku " + lista_przystankow.get(i).nazwa +
                               " odjeżdża pojazd linii " + kolejne_linie.get(i).numer);
        }
        System.out.println("Dotarłeś do celu!");
    }
}
