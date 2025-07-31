package application;

import exceptions.ProdutoNaoEncontradoException;

import DAO.ProdutoEletronicoDAO;
import entities.ProdutoEletronico;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.Scanner;


public class Main {
    public static void main(String[] args) {

        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        Locale.setDefault(Locale.US);
        Scanner sc = new Scanner(System.in);

        char repeticao;
        do {
            System.out.println("menu de interações");
            System.out.println("Selecione o tipo de produto para interagir: ");
            System.out.println("1 - Para produto Eletrônico");
            System.out.println("2 - Para produto Perecível");
            System.out.println("3 - Para produto de Limpeza");
            System.out.print("Opcão: ");
            int opcao = sc.nextInt();
            sc.nextLine();

            switch (opcao) {
                case 1: {
                    System.out.println("Produto Eletrônico selecionado, selecione o que deseja ser feito: ");
                    System.out.println("1 - Inserir um produto Eletrônico no sistema");
                    System.out.println("2 - Deletar um produto Eletrônico do sistema");
                    System.out.println("3 - Buscar informações de um produto Eletrônico no sistema");
                    int opProdEletronico = sc.nextInt();
                    sc.nextLine();

                    switch (opProdEletronico) {
                        case 1: {
                                try {
                                    System.out.println("Informe os dados para inserir no sistema: ");
                                    System.out.print("Nome: ");
                                    String nome = sc.nextLine();
                                    System.out.print("Preço: ");
                                    double preco = sc.nextDouble();
                                    sc.nextLine();
                                    System.out.print("Quantidade: ");
                                    int quantidade = sc.nextInt();
                                    sc.nextLine();
                                    System.out.print("Data de fabricação (dd/MM/yyyy): ");
                                    String dataString = sc.nextLine();
                                    Date utilDate = sdf.parse(dataString); //Convertendo ums String para o formato de data utilizando o sdf (SimpleDateFormat)
                                    java.sql.Date dataFabricacao = new java.sql.Date(utilDate.getTime()); //Prepara a data inserida acima para o Banco
                                    System.out.print("Fabricante: ");
                                    String fabricante = sc.nextLine();
                                    System.out.print("Garantia em meses: ");
                                    int garantiaMeses = sc.nextInt();
                                    sc.nextLine();
                                    System.out.print("Voltagem do produto Eletrônico: ");
                                    String voltagem = sc.nextLine();

                                    //Chamada do método de inseção de dados da classe ProdutoEletronico
                                    ProdutoEletronico pe = new ProdutoEletronico(nome, preco, quantidade, dataFabricacao, fabricante, garantiaMeses, voltagem);

                                    ProdutoEletronicoDAO prodEletroDAO = new ProdutoEletronicoDAO(); //Chamada da classe DAO
                                    prodEletroDAO.cadastrarProdutoEletronico(pe); //Inserção dos dados acima na classe DAO do banco
                                }
                                catch (ParseException e) {
                                    System.out.println("Erro ao converter a data. " + e.getMessage());
                                    }
                                }
                                break;
                        case 2: {
                            try {
                                System.out.println("Informe o ID (Identificador) do produto Eletrônico para excluí-lo");
                                System.out.print("ID: ");
                                int idProdEletronico = sc.nextInt();
                                System.out.println("Tem certeza que deseja remover o produto do sistema? (S/N)");
                                char opExcluirProd = sc.next().charAt(0);
                                if (opExcluirProd == 's' || opExcluirProd == 'S') {
                                    System.out.println("Excluindo produto...");

                                    ProdutoEletronicoDAO prodEletroDAO = new ProdutoEletronicoDAO();
                                    prodEletroDAO.excluirProdutoEletronico(idProdEletronico);
                                } else {
                                    System.out.println("Cancelando a remoção do produto no sistema...");
                                }
                            }
                            catch (ProdutoNaoEncontradoException e) {
                                System.out.println("Erro: " + e.getMessage());
                            }
                        }
                        break;
                        case 3: {
                            try {
                                System.out.println("Informe o ID (Identificador) do produto Eletrônico para buscar os dados: ");
                                System.out.println("ID: ");
                                int idProdELetronico = sc.nextInt();

                                ProdutoEletronicoDAO prodEletroDAO = new ProdutoEletronicoDAO();
                                prodEletroDAO.buscarProdutoEletronicoPorID(idProdELetronico);
                            }
                            catch (ProdutoNaoEncontradoException e) {
                                System.out.println("Erro: " + e.getMessage());
                            }
                        }
                        break;
                    }
                }
                case 2:

            }
            System.out.println("Deseja continuar no menu de interação? (S/N): ");
            repeticao = sc.next().charAt(0);
        }
        while (repeticao == 'S' || repeticao == 's');
        System.out.println("Obrigado!");

        sc.close();
    }
}