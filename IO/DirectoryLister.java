// showing the current directory list filtered by regex

import java.util.regex.*;
import java.io.*;
import java.util.*;

public class DirectoryLister {
    public static void main(String[] args) {
        System.out.println(args[0]);
        File path = new File(".");
        String[] dirList;
        if(args.length == 0) {
            dirList = path.list();
        } else {
            dirList = path.list(new FilenameFilter() {
                private Pattern p = Pattern.compile(args[0]);
                @Override
                public boolean accept(File dir, String name) {
                    return p.matcher(name).matches();
                }
            });
        }

        Arrays.sort(dirList, String.CASE_INSENSITIVE_ORDER);
        for(String dName: dirList) {
            System.out.println(dName);
        }
    }
}
