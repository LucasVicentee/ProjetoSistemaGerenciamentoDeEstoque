package application;


import java.util.Locale;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {

        Locale.setDefault(Locale.US);
        Scanner sc = new Scanner(System.in);
        char repeticao = sc.next().charAt(0);

        do {
            System.out.println("menu de interações: ");
            System.out.println("Selecione o tipo de produto para interagir: ");
            int opcao = sc.nextInt();
            System.out.println("1 - Para produto Eletrônico");
            System.out.println("2 - Para produto Perecível");
            System.out.println("3 - Para produto de Limpeza");
        }
        while (repeticao == 'S' || repeticao == 's');

        sc.close();
    }
}