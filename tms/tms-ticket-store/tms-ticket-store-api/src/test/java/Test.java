import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class Test {

    public static void main(String[] args) {

        Date date = new Date();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");

        System.out.println(simpleDateFormat.format(date));

//        String id = "372922199612105414";
//        System.out.println(id.substring(6,10));
//
//        Calendar calendar = Calendar.getInstance();
//        Integer now = calendar.get(Calendar.YEAR);
//        String str = "1996";
//        Integer in = Integer.parseInt(str);
//
//        System.out.println(now - in);

    }
}
