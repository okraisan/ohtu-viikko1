package ohtu.ohtuvarasto;

import org.junit.*;
import static org.junit.Assert.*;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

public class VarastoTest {

    Varasto varasto;
    Varasto virheellinen_varasto;
    Varasto varasto_alkusaldolla;
    Varasto virheellinen_varasto_alkusaldolla;
    Varasto varasto_virheellisella_alkusaldolla;

    double vertailuTarkkuus = 0.0001;

    @Before
    public void setUp() {
        varasto = new Varasto(10);
        virheellinen_varasto = new Varasto(-1);
        varasto_alkusaldolla = new Varasto(10, 5);
        varasto_virheellisella_alkusaldolla = new Varasto(10, -1);
        virheellinen_varasto_alkusaldolla = new Varasto(-1, 5);
    }

    @Test
    public void konstruktoriLuoTyhjanVaraston() {
        assertEquals(0, varasto.getSaldo(), vertailuTarkkuus);
    }

    @Test
    public void uudellaVarastollaOikeaTilavuus() {
        assertEquals(10, varasto.getTilavuus(), vertailuTarkkuus);
    }

    @Test
    public void alkusaldollaOikeaSaldo() {
        assertEquals(4, varasto_alkusaldolla.getSaldo(), vertailuTarkkuus);
        assertEquals(0, varasto_virheellisella_alkusaldolla.getSaldo(), vertailuTarkkuus);
    }

    @Test
    public void alkusaldollaOikeaTilavuus() {
        assertEquals(10, varasto_alkusaldolla.getTilavuus(), vertailuTarkkuus);
        assertEquals(0, virheellinen_varasto_alkusaldolla.getTilavuus(), vertailuTarkkuus);
    }

    @Test
    public void lisaysLisaaSaldoa() {
        varasto.lisaaVarastoon(8);

        // saldon pitäisi olla sama kun lisätty määrä
        assertEquals(8, varasto.getSaldo(), vertailuTarkkuus);
    }

    @Test
    public void lisaysLisaaPienentaaVapaataTilaa() {
        varasto.lisaaVarastoon(8);

        // vapaata tilaa pitäisi vielä olla tilavuus-lisättävä määrä eli 2
        assertEquals(2, varasto.paljonkoMahtuu(), vertailuTarkkuus);
    }

    @Test
    public void ottaminenPalauttaaOikeanMaaran() {
        varasto.lisaaVarastoon(8);

        double saatuMaara = varasto.otaVarastosta(2);

        assertEquals(2, saatuMaara, vertailuTarkkuus);
    }

    @Test
    public void ottaminenLisääTilaa() {
        varasto.lisaaVarastoon(8);

        varasto.otaVarastosta(2);

        // varastossa pitäisi olla tilaa 10 - 8 + 2 eli 4
        assertEquals(4, varasto.paljonkoMahtuu(), vertailuTarkkuus);
    }

    @Test
    public void negatiivistaEiVoiOttaa() {
        double saldo_ennen = varasto.getSaldo();
        varasto.otaVarastosta(-2);
        assertEquals(saldo_ennen, varasto.getSaldo(), vertailuTarkkuus);
    }

    @Test
    public void negatiivistaEiVoiLisätä() {
        double saldo_ennen = varasto.getSaldo();
        varasto.lisaaVarastoon(-1);
        assertEquals(saldo_ennen, varasto.getSaldo(), vertailuTarkkuus);
    }

    @Test
    public void eiVoiOttaaLiikaa() {
        varasto.lisaaVarastoon(20);
        double saldo_ennen = varasto.getSaldo();
        double saatiin = varasto.otaVarastosta(saldo_ennen + 10);
        assertEquals(0, varasto.getSaldo(), vertailuTarkkuus);
        assertEquals(saldo_ennen, saatiin, vertailuTarkkuus);
    }

    @Test
    public void oikeaMerkkijonoesitys() {
        System.out.println(varasto.toString());
        assertEquals("saldo = 0.0, vielä tilaa 10.0", varasto.toString());
    }
}