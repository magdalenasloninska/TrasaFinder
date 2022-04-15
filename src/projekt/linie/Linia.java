package projekt.linie;

import projekt.czas.Godzina;
import projekt.przystanki.Przystanek;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class Linia {
    public int numer;
    public boolean czy_klimatyzowany;
    public boolean czy_niskopodlogowy;
    public ArrayList<Przystanek> lista_przystankow;
    protected ArrayList<Integer> jak_dlugo_jedzie;
    protected ArrayList<Godzina> wyjazd_z_zajezdni;

    public Linia() {
        this.numer = 0;
        this.czy_klimatyzowany = false;
        this.czy_niskopodlogowy = false;
        this.lista_przystankow = new ArrayList<>();
        this.jak_dlugo_jedzie = new ArrayList<>();
        this.wyjazd_z_zajezdni = new ArrayList<>();
    }

    public Linia(int numer, boolean czy_klimatyzowany, boolean czy_niskopodlogowy, ArrayList<Przystanek> lista_przystankow, ArrayList<Integer> jak_dlugo_jedzie) {
        this.numer = numer;
        this.czy_klimatyzowany = czy_klimatyzowany;
        this.czy_niskopodlogowy = czy_niskopodlogowy;
        this.lista_przystankow = lista_przystankow;
        this.jak_dlugo_jedzie = jak_dlugo_jedzie;
    }

    public Linia(String nazwa_pliku, ArrayList<Przystanek> wszystkie_przystanki) {
        File plik = new File("C:\\Users\\magda\\Documents\\UWr\\PO\\TrasaFinder\\test\\" + nazwa_pliku);
        BufferedReader czytnik;

        try {
            czytnik = new BufferedReader(new FileReader(plik));
            this.numer = Integer.parseInt(czytnik.readLine());
            this.czy_klimatyzowany = Boolean.parseBoolean(czytnik.readLine());
            this.czy_niskopodlogowy = Boolean.parseBoolean(czytnik.readLine());
            this.lista_przystankow = new ArrayList<>();
            this.jak_dlugo_jedzie = new ArrayList<>();
            this.wyjazd_z_zajezdni = new ArrayList<>();

            int ile_przystankow = Integer.parseInt(czytnik.readLine());

            for (int i = 0; i < ile_przystankow; i++) {
                String nowa_nazwa = czytnik.readLine();
                boolean czy_dodany = false;
                for (Przystanek p : wszystkie_przystanki) {
                    if (nowa_nazwa.equals(p.nazwa)) {
                        this.lista_przystankow.add(p);
                        p.dodajLinie(this);
                        czy_dodany = true;
                    }
                }
                if (!czy_dodany) {
                    Przystanek nowy_p = new Przystanek(nowa_nazwa);
                    this.lista_przystankow.add(nowy_p);
                    nowy_p.dodajLinie(this);
                    wszystkie_przystanki.add(nowy_p);
                }
            }

            for (int i = 0; i < ile_przystankow; i++) {
                int ile_minut = Integer.parseInt(czytnik.readLine());
                this.jak_dlugo_jedzie.add(ile_minut);
            }

            String obecny_wiersz = czytnik.readLine();
            while (obecny_wiersz != null) {
                Godzina nowa_godz = new Godzina(obecny_wiersz);
                this.wyjazd_z_zajezdni.add(nowa_godz);
                obecny_wiersz = czytnik.readLine();
            }
        }
        catch (IOException e) {
            e.printStackTrace();
        }

        dodajDaneDoPrzystankow();
    }

    void dodajDaneDoPrzystankow() {
        for (int ktory_przystanek = 0; ktory_przystanek < lista_przystankow.size(); ktory_przystanek++) {
            for (int ktory_kurs = 0; ktory_kurs < wyjazd_z_zajezdni.size(); ktory_kurs++) {
                Godzina godz_kursu = (wyjazd_z_zajezdni.get(ktory_kurs)).dodaj(jak_dlugo_jedzie.get(ktory_przystanek));
                (lista_przystankow.get(ktory_przystanek)).dodajKurs(this.numer, godz_kursu);
            }
        }
    }

    public boolean czyPrzejezdzaPrzez(String nazwa_przystanku) {
        for (Przystanek p : lista_przystankow) {
            if (p.nazwa.equals(nazwa_przystanku)) return true;
        }
        return false;
    }

    public int ileSieJedzie(Przystanek pocz, Przystanek kon) {
        int pocz_id = lista_przystankow.indexOf(pocz), kon_id = lista_przystankow.indexOf(kon);
        return Math.abs(jak_dlugo_jedzie.get(pocz_id) - jak_dlugo_jedzie.get(kon_id));
    }
}
