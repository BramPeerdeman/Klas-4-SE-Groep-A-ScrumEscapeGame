package org.ScrumEscapeGame.Items;
import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;
import org.ScrumEscapeGame.Providers.HintProvider;

public class StubHintService implements HintProvider {
    @Override
    public String getHint(){

        return "[Stub]: Geeft altijd hint terug";
    }
    @Test
    public void testHintServiceWithStub() {
        HintProvider stub = new StubHintService();
        String hint = stub.getHint();
        assertEquals("[Stub]: Geeft altijd hint terug", hint);
    }
}
