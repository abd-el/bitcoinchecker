package hhs.bitcoinchecker.bitcoinchecker;

import org.testng.annotations.Test;

import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class BlockchainTest {
    @Test
    public void Test() {
        // Arrange
        Tracker.initialiseer();
        TrackedBitcoinAdres bitcoinAdres = new TrackedBitcoinAdres("Main","bc1qzxq78ncvglf6n2fugkq2ru8x4w53mw6h7zsakx",0);
        Tracker.voegAdresToe(bitcoinAdres);

        // Act

        ArrayList<TrackedBitcoinAdres> opgehaaldeAdressen = JsonHandler.haalTrackedBitcoinAdressenOp();
        // ArrayList<BitcoinTransactie> geschiedenis = bitcoinAdres.getGeschiedenisVanBlockchain();

        // Assert

        assertEquals(bitcoinAdres.getNaam(), opgehaaldeAdressen.get(0).getNaam());
        assertEquals("bc1qzxq78ncvglf6n2fugkq2ru8x4w53mw6h7zsakx", opgehaaldeAdressen.get(0).getHash());
    }
}
