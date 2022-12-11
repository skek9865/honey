package project.honey.comm;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public abstract class GlobalMethod {

    public static List<String> makeTitle(String ... titles){
        List<String> result  = new ArrayList<>();
        for(String title : titles) result.add(title);
        return result;
    }

    public static List<String> makeExcelType(String ... types){
        List<String> result  = new ArrayList<>();
        for(String type : types) result.add(type);
        return result;
    }

    public static List<String> makeFooter(String ... types){
        List<String> result  = new ArrayList<>();
        for(String type : types) result.add(type);
        return result;
    }

    public static String makeYmd(String ymd, String pattern) {
        SimpleDateFormat sdf = new SimpleDateFormat(pattern);
        SimpleDateFormat beforeSdf= new SimpleDateFormat("yyyyMMdd");
        Date date = null;
        try{
            date = beforeSdf.parse(ymd);
        } catch(ParseException e){
            e.printStackTrace();
        }
        return sdf.format(date);
    }

    public static String replaceYmd(String ymd, String pattern) {
        return ymd.replaceAll(pattern, "");
    }
}
