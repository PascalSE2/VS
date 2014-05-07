package server;

import bank.Konto;
import bank.KontoPOA;
import bank.KontoPackage.EInvalidAmount;
import bank.KontoPackage.ENotEnoughMoney;

public class KontoImpl extends KontoPOA {

    
    
    double kontostand;
    String kontonr;

    public KontoImpl(String kontonr) {
        this.kontostand = 0;
        this.kontonr = kontonr;
    }

    public double kontostand() {
        return this.kontostand;
    }

    public String kontonr() {
        return this.kontonr;
    }

    public synchronized void einzahlen(double betrag) throws EInvalidAmount {
        if (betrag <= 0) {
            throw new EInvalidAmount("falsche uebergabe Parameter");
        } else {
            this.kontostand += betrag;
            Server.getBankRef().addSendeQueue("Einzahlung Betrag: " + betrag + ", Konto: " + this.kontonr + ", neuer Kontostand: " + this.kontostand);
//            Server.sendMessage("Einzahlung Betrag: " + betrag + ", Konto: " + this.kontonr + ", neuer Kontostand: " + this.kontostand);
        }
    }

    public synchronized void auszahlen(double betrag) throws EInvalidAmount, ENotEnoughMoney {
        if (betrag <= 0) {
            throw new EInvalidAmount("falsche uebergabe Parameter");
        } else if (this.kontostand < betrag) {
            throw new ENotEnoughMoney("Konto ist nicht gedeckt. Kontostand: " + this.kontostand);
        } else {
            this.kontostand -= betrag;
            Server.getBankRef().addSendeQueue("Auszahlung Betrag: " + betrag + ", Konto: " + this.kontonr + ", neuer Kontostand: " + this.kontostand);
//            Server.sendMessage("Auszahlung Betrag: " + betrag + ", Konto: " + this.kontonr + ", neuer Kontostand: " + this.kontostand);
        }
    }

    public synchronized void transfer(double betrag, Konto toKonto) throws EInvalidAmount {
        if (betrag <= 0 || toKonto == null) {
            throw new EInvalidAmount();
        }else{
            try {
                this.auszahlen(betrag);
                toKonto.einzahlen(betrag);
            } catch (ENotEnoughMoney e) {
                System.err.println(e.s);
                e.printStackTrace();
            }
        }
    }

    @Override
    public int hashCode() {
        return this.kontonr.hashCode();
    }

    @Override
    public String toString() {
        // TODO Auto-generated method stub
        return "Kontonummer: " + this.kontonr + " Kontostand: " + this.kontostand;
    }
}
