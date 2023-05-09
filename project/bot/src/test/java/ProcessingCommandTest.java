import org.junit.Assert;
import org.junit.Test;
import org.mockito.Mockito;
import ru.tinkoff.edu.java.bot.handler.CommandProcessingUnit;
import ru.tinkoff.edu.java.bot.handler.ListCommandProcessingUnit;
import ru.tinkoff.edu.java.bot.service.TelegramBotService;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

public class ProcessingCommandTest {

    @Test
    public void testNotEmptyLinksList() {
        List<URI> linksList;
        try {
            linksList = new ArrayList<>(List.of(new URI("url1"), new URI("url2")));
        } catch (URISyntaxException e) {
            throw new RuntimeException(e);
        }
        Assert.assertEquals(linksList.toString(), new ListCommandProcessingUnit(linksList).processCommand("/list"));
    }

    @Test
    public void testEmptyLinksList() {
        Assert.assertEquals(
                "Список отслеживаемых ссылок пуст",
                new ListCommandProcessingUnit(new ArrayList<>()).processCommand("/list"));
    }

    @Test
    public void testUnknownCommand() {
        CommandProcessingUnit mock = Mockito.mock(CommandProcessingUnit.class);
        String unknownCommand = "/unknownCommand";
        Mockito.when(mock.processCommand("/start"))
               .thenReturn("sorry, feature \"user registration\" is under construction");
        Assert.assertEquals(
                "Извините, данная команда не поддерживается",
                new TelegramBotService(List.of(mock)).processMessage(unknownCommand));
    }

    @Test
    public void testCorrectCommands() {
        CommandProcessingUnit mock = Mockito.mock(CommandProcessingUnit.class);
        Mockito.when(mock.processCommand("/start"))
               .thenReturn("sorry, feature \"user registration\" is under construction");
        Mockito.when(mock.processCommand("/track")).thenReturn("sorry, feature \"track\" is under construction");

        Assert.assertEquals(
                "sorry, feature \"track\" is under construction",
                new TelegramBotService(List.of(mock)).processMessage("/track"));
    }
}
