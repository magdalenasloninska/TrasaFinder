package projekt.zapytania;

import projekt.linie.Linia;
import projekt.czas.Godzina;
import projekt.przystanki.Przystanek;

import java.util.*;

public class Zapytanie {
    String przystanek_pocz;
    String przystanek_kon;
    Godzina godz;
    boolean z_wozkiem;
    boolean w_ciszy;
    boolean z_klimatyzacja;
    boolean bez_przesiadek;

    public Zapytanie(String przystanek_pocz,
                     String przystanek_kon,
                     Godzina godz,
                     boolean z_wozkiem,
                     boolean w_ciszy,
                     boolean z_klimatyzacja,
                     boolean bez_przesiadek) {
        this.przystanek_pocz = przystanek_pocz;
        this.przystanek_kon = przystanek_kon;
        this.godz = godz;
        this.z_wozkiem = z_wozkiem;
        this.w_ciszy = w_ciszy;
        this.z_klimatyzacja = z_klimatyzacja;
        this.bez_przesiadek = bez_przesiadek;
    }

    public Trasa znajdzTrase(ArrayList<Przystanek> wszystkie_przystanki) {

        Set<Linia> wykorzystane = new HashSet<>();

        Przystanek start = new Przystanek("");
        for (Przystanek p : wszystkie_przystanki) {
            if (p.nazwa.equals(przystanek_pocz)) {
                start = p;
                break;
            }
        }

        Queue<ArrayList<Linia>> zarys_trasy = new LinkedList<>();
        for (Linia obecna_linia : start.kursujace_linie) {
            wykorzystane.add(obecna_linia);
            if ((!z_klimatyzacja || obecna_linia.czy_klimatyzowany) && (!z_wozkiem || obecna_linia.czy_niskopodlogowy)) {
                ArrayList<Linia> element_startowy = new ArrayList<>();
                element_startowy.add(obecna_linia);
                zarys_trasy.add(element_startowy);
            }
        }

        while(!zarys_trasy.isEmpty()) {
            ArrayList<Linia> analizowana_trasa = zarys_trasy.poll();
            if (analizowana_trasa.get(analizowana_trasa.size() - 1).czyPrzejezdzaPrzez(przystanek_kon)) {
                if (bez_przesiadek && analizowana_trasa.size() > 1) return new BrakTrasy();
                else return new Trasa(godz, start, analizowana_trasa);
            }

            Linia ostatnia_linia = analizowana_trasa.get(analizowana_trasa.size() - 1);
            for (Przystanek p : ostatnia_linia.lista_przystankow) {
                for (Linia l : p.kursujace_linie) {
                    if (!wykorzystane.contains(l) && (!z_klimatyzacja || l.czy_klimatyzowany) && (!z_wozkiem || l.czy_niskopodlogowy)) {
                        ArrayList<Linia> nowa_trasa = analizowana_trasa;
                        nowa_trasa.add(l);
                        zarys_trasy.add(nowa_trasa);
                    }
                }
            }
        }

        return new BrakTrasy();
    }
}
