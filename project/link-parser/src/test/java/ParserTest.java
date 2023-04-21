import org.junit.Assert;
import org.junit.Test;
import ru.tinkoff.edu.java.linkParser.Parser;

import java.util.Map;


public class ParserTest {

    @Test
    public void testCorrectLinkGitHub() {
        Assert.assertEquals(Map.of(
                "domain", "github.com",
                "owner", "sanyarnd",
                "repository", "tinkoff-java-course-2022"),
                Parser.parseLink("https://github.com/sanyarnd/tinkoff-java-course-2022/\n"));
    }

    @Test
    public void testCorrectLinkStackOverflow() {
        Assert.assertEquals(Map.of(
                "domain", "stackoverflow.com",
                "category", "questions",
                "questionId", "1642028"),
                Parser.parseLink("https://stackoverflow.com/questions/1642028/what-is-the-operator-in-c\n"));
    }

    @Test
    public void testWrongLink() {
        Assert.assertNull(Parser.parseLink("https://stackoverflow.com/search?q=unsupported%20link\n"));
    }
}
