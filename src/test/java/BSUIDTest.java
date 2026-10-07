import io.github.federkone.bsuid.BSUID;
import io.github.federkone.bsuid.InvalidBSUIDException;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class BSUIDTest {

    @Test
    public void invalid_BSUID_from_String() {
        assertThrows(InvalidBSUIDException.class, () -> {
            BSUID.fromString("");
        });

        assertThrows(InvalidBSUIDException.class, () -> {
            BSUID.fromString("123145124155");
        });

        assertThrows(InvalidBSUIDException.class, () -> {
            BSUID.fromString("ARG.123145124155");
        });

        assertThrows(InvalidBSUIDException.class, () -> {
            BSUID.fromString("AR123145124155");
        });

        assertThrows(InvalidBSUIDException.class, () -> {
            BSUID.fromString("AR.123145124155A141551451515154246525246t2341351351351235131344635879245789245782578923457892347345236337352452356347345234134134162463245234");
        });

        assertThrows(NullPointerException.class, () -> {
            BSUID.fromString(null);
        });


    }

    @Test
    public void valid_BSUID_from_String() {
        assertDoesNotThrow(() -> {
            BSUID.fromString("US.13491208655302741918");
        });

        assertTrue(BSUID.itIsAnBSUID("US.13491208655302741918"));

        assertEquals("US", BSUID.fromString("US.13491208655302741918").region());
    }


    @Test
    public void valid_BSUID_parent_version_from_String() {
        assertDoesNotThrow(() -> {
            BSUID.fromString("US.ENT.13491208655302741918");
        });

        BSUID bsuid = BSUID.fromString("US.ENT.13491208655302741918");
        assertTrue(bsuid.isParent());
    }
}
