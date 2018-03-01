import java.util.regex.*;

public class ReFlags {
    public static final String JAVA =
            "java has regex\nJava has regex\n" +
                    "JAVA has pretty good regular expression\n" +
                    "Regular expressions are in Java";

    public static void main(String[] args) {
        Pattern p1 = Pattern.compile("^java", Pattern.CASE_INSENSITIVE|Pattern.MULTILINE);
        Matcher m1 = p1.matcher(JAVA);

        while(m1.find()) {
            System.out.println(m1.group());
        }

        // another way of using flags
        Pattern p2 = Pattern.compile("(?m)(?i)^java");
        Matcher m2 = p2.matcher(JAVA);

        while(m2.find()) {
            System.out.println(m2.group());
        }


    }
}
