package projekt.czas;

public class Godzina {
    int h;
    int min;

    public Godzina(int h, int min) {
        this.h = h;
        this.min = min;
    }

    public Godzina(String s) {
        this.h = Character.getNumericValue(s.charAt(0))*10 + Character.getNumericValue(s.charAt(1));
        this.min = Character.getNumericValue(s.charAt(3))*10 + Character.getNumericValue(s.charAt(4));
    }

    public String wypisz() {
        String wypisz_h, wypisz_min;
        if (this.h == 0) wypisz_h = "00";
        else wypisz_h = String.valueOf(this.h);

        if (this.min < 10) wypisz_min = "0" + String.valueOf(this.min);
        else wypisz_min = String.valueOf(this.min);

        return wypisz_h + ":" + wypisz_min;
    }

    public boolean czyPozniejOd(Godzina g) {
        return this.h > g.h || (this.h == g.h && this.min > g.min);
    }

    public Godzina dodaj(int ile_minut) {
        int minuty = this.h*60 + this.min + ile_minut;
        return new Godzina(minuty / 60, minuty % 60);
    }
}