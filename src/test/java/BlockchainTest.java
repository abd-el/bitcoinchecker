import org.testng.annotations.Test;

import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;

public class BlockchainTest {
    @Test
    public static void Test() throws ParseException, IOException {
        TrackedBitcoinAdres bitcoinAdres = new TrackedBitcoinAdres("Main","bc1qzxq78ncvglf6n2fugkq2ru8x4w53mw6h7zsakx",0);

        ArrayList<BitcoinTransactie> geschiedenis = Blockchain.getAdresGeschiedenis(bitcoinAdres);

        for(int i = 0; i < geschiedenis.size(); i++){
            BitcoinTransactie tx = geschiedenis.get(i);
            System.out.println(tx.getHash());
            System.out.println(tx.getBitcoinAdres());
            System.out.println(tx.getTijd());
        }
    }
}
