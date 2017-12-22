import org.apache.shiro.crypto.hash.Md5Hash;

public class Test {

    @org.junit.Test
    public void test() {

        Md5Hash md5Hash = new Md5Hash("123");
        System.out.println(md5Hash.toString());
    }
}
