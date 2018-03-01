import java.util.regex.*;

public class TheReplacements {
    public static final String TEXT_INPUT =
            "/*! Here's a block of test to use as input to \n" +
            "the regular expression matcher. Note that we'll \n" +
            "first extract the block of text by looking for \n" +
            "the special delimiters, then process the extracted block. !*/";

    public static void main(String[] args) {
        Matcher mInput = Pattern.compile("(?s)/\\*!(.*)!\\*/").matcher(TEXT_INPUT);
        String s = "";

        if(mInput.find()) s = mInput.group(1);
        s = s.replaceAll(" {2,}", " ");
        s = s.replaceAll("(?m)^ +", "");
        System.out.println(s);

        s = s.replaceFirst("[aeiou]", "(VOWEL1)");
        StringBuffer sbuf = new StringBuffer();
        Matcher m = Pattern.compile("[aeiou]").matcher(s);
        while(m.find()) m.appendReplacement(sbuf, m.group().toUpperCase()); // append each section to the buffer

        m.appendTail(sbuf); // add the remainder of the text
        System.out.println(sbuf);
    }
}
