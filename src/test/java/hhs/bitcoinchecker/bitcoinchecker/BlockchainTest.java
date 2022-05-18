package hhs.bitcoinchecker.bitcoinchecker;

import org.testng.annotations.Test;

import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class BlockchainTest {
    @Test
    public void Test() throws IOException, ParseException {
        // Arrange
        Tracker.initialiseer();
        TrackedBitcoinAdres bitcoinAdres = new TrackedBitcoinAdres("Main","bc1qzxq78ncvglf6n2fugkq2ru8x4w53mw6h7zsakx",0);
        Tracker.voegAdresToe(bitcoinAdres);

        // Act

        ArrayList<TrackedBitcoinAdres> opgehaaldeAdressen = JsonHandler.haalTrackedBitcoinAdressenOp();
        // ArrayList<BitcoinTransactie> geschiedenis = Blockchain.getAdresGeschiedenis(bitcoinAdres);

        // Assert

        System.out.println(opgehaaldeAdressen);
        System.out.println(opgehaaldeAdressen.get(1));
        System.out.println(opgehaaldeAdressen.get(1).getNaam());

        assertEquals(bitcoinAdres.getNaam(), opgehaaldeAdressen.get(1).getNaam());
        assertEquals("bc1qzxq78ncvglf6n2fugkq2ru8x4w53mw6h7zsakx", opgehaaldeAdressen.get(0).getHash());

        /*
        for (BitcoinTransactie tx : geschiedenis) {

            System.out.println(tx.getHash());
            System.out.println(tx.getBitcoinAdres());
            System.out.println(tx.getTijd());
        }
        */
    }
}
