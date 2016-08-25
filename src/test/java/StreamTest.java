import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Pattern;
import java.util.stream.Stream;

/**
 * Package: PACKAGE_NAME
 * ClassName: StreamTest
 * Author: he_hu@founder.com.cn
 * Description:
 * CreateDate: 2016/8/15
 * Version: 1.0
 */
public class StreamTest {

    private static Logger log = LoggerFactory.getLogger(StreamTest.class);

    public List list = new ArrayList<>();

    @Before
    public void before(){
        list.clear();
    }

    //From Arrays
    @Test
    public void arraysStream() {
        String[] arr = {"program", "creek", "program", "creek", "java", "web", "program"};
        Stream.of(arr).forEach(System.out::println);
    }

    //From Collections
    @Test
    public void collectionStream() {
        List<String> list = new ArrayList<>();
        list.add("Java");
        list.add("python");
        list.add("c++");
        list.add("c");
        list.add("lisp");

        Stream<String> stream = list.stream().filter(p -> p.length() > 3);
        String[] arr = stream.toArray(String[]::new);
        System.out.println(Arrays.toString(arr));
    }

    //Use Stream.generate()
    @Test
    public void generate() {
        Stream<String> stream = Stream.generate(() -> "test").limit(10);
        String[] strArr = stream.toArray(String[]::new);
        System.out.println(Arrays.toString(strArr));
    }

    //Use Stream.iterate()
    @Test
    public void iterateStream() {
        Stream<BigInteger> bigIntStream = Stream.iterate(BigInteger.ZERO, n -> n.add(BigInteger.TEN)).limit(10);
        BigInteger[] bigIntArr = bigIntStream.toArray(BigInteger[]::new);
        System.out.println(Arrays.toString(bigIntArr));
    }

    // From Popular APIs
    @Test
    public void populaStream() {
        String sentence = "Program creek is a Java site.";
        Stream<String> wordStream = Pattern.compile("\\W").splitAsStream(sentence);
        String[] wordArr = wordStream.toArray(String[]::new);
        System.out.println(Arrays.toString(wordArr));
    }

}
