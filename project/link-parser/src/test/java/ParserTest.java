import org.junit.Assert;
import org.junit.Test;
import ru.tinkoff.edu.java.linkParser.Parser;


public class ParserTest {

    @Test
    public void testCorrectLinkGitHub() {
        Assert.assertEquals("sanyarnd : tinkoff-java-course-2022", new Parser().parseLink("https://github.com/sanyarnd/tinkoff-java-course-2022/\n"));
    }

    @Test
    public void testCorrectLinkStackOverflow() {
        Assert.assertEquals("1642028", new Parser().parseLink("https://stackoverflow.com/questions/1642028/what-is-the-operator-in-c\n"));
    }

    @Test
    public void testWrongLink() {
        Assert.assertNull(new Parser().parseLink("https://stackoverflow.com/search?q=unsupported%20link\n"));
    }
}
