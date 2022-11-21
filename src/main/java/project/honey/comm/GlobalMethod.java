package project.honey.comm;

import java.util.ArrayList;
import java.util.List;

public abstract class GlobalMethod {

    public static List<String> makeTitle(String ... titles){
        List<String> result  = new ArrayList<>();
        for(String title : titles) result.add(title);
        return result;
    }
}
