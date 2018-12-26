public class Test {

    public static void main(String[] args){
        try {
            foo();
        }
        catch (Exception ex){
            System.out.println( "exception caught by outer class.");
        }
    }

    public static void foo () {
        try {
            bar();
        }
        catch (Exception ex){
            System.out.println("exception caught by inner try-catch");
        }
    }

    private static void bar()  {
        try {
            TestInner.foobar();
        }
        catch (Exception ex){
            System.out.println("Exception caught by inner-inner try-catch");
        }
    }

    static class TestInner {
        private static void foobar() throws Exception {
            throw new Exception();
        }
    }

}
