package hhs.bitcoinchecker.bitcoinchecker;

import org.testng.Assert;
import org.testng.annotations.Test;

import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

public class TrackerTest {
    @Test
    public void TestVoegAdresToe_ConditionCoverage() throws IOException, ParseException {
        // BESTAAT, NIEUW, VERANDERING, RESULTAAT
        // 1        0      0            0
        // 0        1      0            0
        // 0        0      1            0

        // ======== BESTAAT, NIEUW, VERANDERING, RESULTAAT
        // ======== 1        0      0            0

        // Arrange
        double bitcoinPrijs = Blockchain.getBitcoinPrijs();

        TrackedBitcoinAdres bitcoinAdres = new TrackedBitcoinAdres("NrEen", "bc1qzxq78ncvglf6n2fugkq2ru8x4w53mw6h7zsakx");
        BitcoinTransactie laatsteTransactie = bitcoinAdres.getGeschiedenis().get(0);

        Tracker.voegAdresToe(bitcoinAdres); // BESTAAT wordt true
        laatsteTransactie.setTijd(0); // NIEUW wordt false
        laatsteTransactie.setVerandering(0.0); // VERANDERING wordt false

        // Act
        boolean gestuurdEen = Tracker.stuurMelding(laatsteTransactie, bitcoinPrijs);

        // Assert
        assertFalse(gestuurdEen);

        // ======== BESTAAT, NIEUW, VERANDERING, RESULTAAT
        // ======== 0        1      0            0

        // Arrange
        TrackedBitcoinAdres bitcoinAdresTwee = new TrackedBitcoinAdres("NrTwee", "34xp4vRoCGJym3xR7yCVPFHoCNxv4Twseo");
        BitcoinTransactie laatsteTransactieTwee = bitcoinAdresTwee.getGeschiedenis().get(0);

        // Tracker.voegAdresToe(bitcoinAdres); // BESTAAT wordt false
        // laatsteTransactieTwee.setTijd(0); // NIEUW wordt true
        laatsteTransactieTwee.setVerandering(0.0); // VERANDERING wordt false

        // Act
        boolean gestuurdTwee = Tracker.stuurMelding(laatsteTransactieTwee, bitcoinPrijs);

        // Assert
        assertFalse(gestuurdTwee);

        // ======== BESTAAT, NIEUW, VERANDERING, RESULTAAT
        // ======== 0        0      1            0

        // Arrange
        TrackedBitcoinAdres bitcoinAdresDrie = new TrackedBitcoinAdres("NrDrie", "3KnQF7LRv1BjAAXhmCYpRwwpB7shtK5wag");
        BitcoinTransactie laatsteTransactieDrie = bitcoinAdresDrie.getGeschiedenis().get(0);

        // Tracker.voegAdresToe(bitcoinAdres); // BESTAAT wordt false
        laatsteTransactieDrie.setTijd(0); // NIEUW wordt false
        // laatsteTransactie.setVerandering(0.0); // VERANDERING wordt true

        // Act
        boolean gestuurdDrie = Tracker.stuurMelding(laatsteTransactieDrie, bitcoinPrijs);

        // Assert
        assertFalse(gestuurdDrie);
    }

    @Test
    public void TestVoegAdresToe_DecisionCoverage() {
        // Tracker.stuurMelding(...)
    }

    @Test
    public void TestVoegAdresToe_ConditionDecisionCoverage() {
        // Tracker.stuurMelding(...)
    }

    @Test
    public void TestVoegAdresToe_ModifiedConditionDecisionCoverage() {
        // Tracker.stuurMelding(...)
    }

    @Test
    public void TestVoegAdresToe_MultipleConditionsCoverage() {
        // Tracker.stuurMelding(...)
    }

    // Test_EquivalantieKlassen()

    /*
    @Test
    public void Test() {
        // Arrange
        Tracker.initialiseer();
        TrackedBitcoinAdres bitcoinAdres = new TrackedBitcoinAdres("Main","bc1qzxq78ncvglf6n2fugkq2ru8x4w53mw6h7zsakx");
        Tracker.voegAdresToe(bitcoinAdres);

        // Act

        ArrayList<TrackedBitcoinAdres> opgehaaldeAdressen = JsonHandler.haalTrackedBitcoinAdressenOp();
        // ArrayList<BitcoinTransactie> geschiedenis = Blockchain.getAdresGeschiedenis(bitcoinAdres);

        // Assert

        System.out.println(opgehaaldeAdressen);
        System.out.println(opgehaaldeAdressen.get(1));
        System.out.println(opgehaaldeAdressen.get(1).getNaam());

        assertEquals(bitcoinAdres.getNaam(), opgehaaldeAdressen.get(1).getNaam());
        assertEquals("bc1qzxq78ncvglf6n2fugkq2ru8x4w53mw6h7zsakx", opgehaaldeAdressen.get(0).getAdres());
    }
    */
}
