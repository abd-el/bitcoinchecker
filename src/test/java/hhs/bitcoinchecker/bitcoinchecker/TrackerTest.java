package hhs.bitcoinchecker.bitcoinchecker;

import org.testng.annotations.Test;

import java.io.IOException;
import java.text.ParseException;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class TrackerTest {
    double bitcoinPrijs;
    {
        try { bitcoinPrijs = Blockchain.getBitcoinPrijs();
        } catch (IOException e) { e.printStackTrace(); }
    }

    @Test
    public void TestStuurMelding_ModifiedConditionDecisionCoverage_Een() throws IOException, ParseException {
        // Tracker.stuurMelding(...)

        // BESTAAT, NIEUW, VERANDERING, RESULTAAT
        // 0        1      1            0
        // 1        1      1            1
        // 1        0      1            0
        // 1        1      0            0

        // ======== BESTAAT, NIEUW, VERANDERING, RESULTAAT
        // ======== 0        1      1            0

        // Arrange
        BitcoinChecker.initialiseer();

        TrackedBitcoinAdres bitcoinAdres = new TrackedBitcoinAdres("NrEen", "bc1qzxq78ncvglf6n2fugkq2ru8x4w53mw6h7zsakx");
        BitcoinTransactie laatsteTransactie = bitcoinAdres.getGeschiedenis().get(0);

        // Tracker.voegAdresToe(bitcoinAdres); // BESTAAT wordt false
        // laatsteTransactie.setTijd(0); // NIEUW wordt true
        // laatsteTransactie.setVerandering(0.0); // VERANDERING wordt true

        // Act
        boolean gestuurd = Tracker.stuurMelding(laatsteTransactie, bitcoinPrijs);

        // Assert
        assertFalse(gestuurd);
        Tracker.verwijderAdres(bitcoinAdres);
    }

    @Test
    public void TestStuurMelding_ModifiedConditionDecisionCoverage_Twee() throws IOException, ParseException {
        // ======== BESTAAT, NIEUW, VERANDERING, RESULTAAT
        // ======== 1        1      1            1

        // Arrange
        TrackedBitcoinAdres bitcoinAdres = new TrackedBitcoinAdres("NrTwee", "bc1qzxq78ncvglf6n2fugkq2ru8x4w53mw6h7zsakx");
        BitcoinTransactie laatsteTransactie = bitcoinAdres.getGeschiedenis().get(0);

        Tracker.voegAdresToe(bitcoinAdres); // BESTAAT wordt true
        // laatsteTransactie.setTijd(0); // NIEUW wordt true
        // laatsteTransactie.setVerandering(0.0); // VERANDERING wordt true

        // Act
        boolean gestuurd = Tracker.stuurMelding(laatsteTransactie, bitcoinPrijs);

        // Assert
        assertTrue(gestuurd);
        Tracker.verwijderAdres(bitcoinAdres);
    }

    @Test
    public void TestStuurMelding_ModifiedConditionDecisionCoverage_Drie() throws IOException, ParseException {
        // ======== BESTAAT, NIEUW, VERANDERING, RESULTAAT
        // ======== 1        0      1            0

        // Arrange
        TrackedBitcoinAdres bitcoinAdres = new TrackedBitcoinAdres("NrDrie", "bc1qzxq78ncvglf6n2fugkq2ru8x4w53mw6h7zsakx");
        BitcoinTransactie laatsteTransactie = bitcoinAdres.getGeschiedenis().get(0);

        Tracker.voegAdresToe(bitcoinAdres); // BESTAAT wordt true
        laatsteTransactie.setTijd(0); // NIEUW wordt false
        // laatsteTransactie.setVerandering(0.0); // VERANDERING wordt true

        // Act
        boolean gestuurd = Tracker.stuurMelding(laatsteTransactie, bitcoinPrijs);

        // Assert
        assertFalse(gestuurd);
        Tracker.verwijderAdres(bitcoinAdres);
    }

    @Test
    public void TestStuurMelding_ModifiedConditionDecisionCoverage_Vier() throws IOException, ParseException {
        // ======== BESTAAT, NIEUW, VERANDERING, RESULTAAT
        // ======== 1        1      0            0

        // Arrange
        TrackedBitcoinAdres bitcoinAdres = new TrackedBitcoinAdres("NrVier", "bc1qzxq78ncvglf6n2fugkq2ru8x4w53mw6h7zsakx");
        BitcoinTransactie laatsteTransactie = bitcoinAdres.getGeschiedenis().get(0);

        Tracker.voegAdresToe(bitcoinAdres); // BESTAAT wordt true
        // laatsteTransactie.setTijd(0); // NIEUW wordt true
        laatsteTransactie.setVerandering(0.0); // VERANDERING wordt false

        // Act
        boolean gestuurd = Tracker.stuurMelding(laatsteTransactie, bitcoinPrijs);

        // Assert
        assertFalse(gestuurd);
        Tracker.verwijderAdres(bitcoinAdres);
    }

    // Test_EquivalantieKlassen()
}
