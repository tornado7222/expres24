package ui;


import context.Context;

public class MainUI {
    public void star(){
        boolean isExited = false;

        while (!isExited){
            System.out.println("""
                    1. Kirish
                    2. Ro'yhatdan o'tish
                    3. chiqish""");
            switch (Context.intScanner.nextInt()) {
                case 1 -> {
                    signIn();
                }
                case 2 -> {
                    signUp();
                }
                case 3 -> isExited = true;
                default -> {
                    System.out.println("No");
                }
            }
        }
    }

    private void signUp() {
        System.out.print("Telefon raqam kiriting : ");
        String poneNumber = Context.stringScanner.nextLine();
        System.out.print("Ismingizni kiriting : ");
        String name = Context.stringScanner.nextLine();
        System.out.print("Familiyangizni kiriting : ");
        String surname = Context.stringScanner.nextLine();
        System.out.print("Parolinginz kiriting : ");
        String password = Context.stringScanner.nextLine();
        System.out.print("balance ni kiriting : ");
        double balance = Context.intScanner.nextInt();
    }

    private void signIn() {

    }
}
