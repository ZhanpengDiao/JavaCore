import java.util.regex.*;

public class Groups {
    public static final String POEM =
            "Twas brillig, and the slithy toves\n" +
            "Did gyre and gimble in the wabe.\n" +
            "All mimsy were the borogoves,\n" +
            "And the mome raths outgrabe.\n\n" +
            "Beware the jabberwock, my son,\n" +
            "The jaws that bite, the claws that catch.\n" +
            "Beware the Jbujub bird, and shun\n" +
            "The frumious Bandersnatch.";

    public static void main(String[] args) {
        Pattern p = Pattern.compile("(?m)(\\S+)\\s+((\\S+)\\s+(\\S+))$"); // (?m) Pattern.MULTILINE } }
        Matcher m = p.matcher(POEM);
        while(m.find()) {
            for(int i = 0; i <= m.groupCount(); i ++) {
                System.out.print("[" + m.group(i) + "]");
            }
            System.out.println();
        }
    }
}