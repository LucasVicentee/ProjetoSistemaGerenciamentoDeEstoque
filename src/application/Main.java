package application;

import DAO.ProdutoLimpezaDAO;
import DAO.ProdutoPerecivelDAO;
import entities.ProdutoLimpeza;
import entities.ProdutoPerecivel;
import exceptions.DataFormatoIncorretoException;
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
            System.out.println("2 - Para produto de Limpeza");
            System.out.println("3 - Para produto Perecível");
            System.out.print("Opcão: ");
            int opcao = sc.nextInt();
            sc.nextLine();

            switch (opcao) {
                case 1: {
                    System.out.println("Produto Eletrônico selecionado, selecione o que deseja ser feito: ");
                    System.out.println("1 - Inserir um produto Eletrônico no sistema");
                    System.out.println("2 - Deletar um produto Eletrônico do sistema");
                    System.out.println("3 - Buscar informações de um produto Eletrônico no sistema");
                    System.out.println("4 - Para alterar as informações de um produto Eletrônico no sistema");
                    System.out.print("Opção: ");
                    int opProdEletronico = Integer.parseInt(sc.nextLine());

                    switch (opProdEletronico) {
                        case 1: {
                            try {
                                System.out.println("Informe os dados para inserir no sistema: ");
                                System.out.print("Nome: ");
                                String nome = sc.nextLine();
                                System.out.print("Preço: ");
                                double preco = Double.parseDouble(sc.nextLine());

                                System.out.print("Quantidade: ");
                                int quantidade = Integer.parseInt(sc.nextLine());

                                System.out.print("Data de fabricação (dd/MM/yyyy): ");
                                String dataString = sc.nextLine();
                                Date utilDate = sdf.parse(dataString); //Convertendo ums String para o formato de data utilizando o sdf (SimpleDateFormat)
                                java.sql.Date dataFabricacao = new java.sql.Date(utilDate.getTime()); //Prepara a data inserida acima para o Banco

                                System.out.print("Fabricante: ");
                                String fabricante = sc.nextLine();

                                System.out.print("Garantia em meses: ");
                                int garantiaMeses = Integer.parseInt(sc.nextLine());

                                System.out.print("Voltagem do produto Eletrônico: ");
                                String voltagem = sc.nextLine();

                                //Chamada do método de inseção de dados da classe ProdutoEletronico
                                ProdutoEletronico pe = new ProdutoEletronico(nome, preco, quantidade, dataFabricacao, fabricante, garantiaMeses, voltagem);

                                ProdutoEletronicoDAO prodEletroDAO = new ProdutoEletronicoDAO(); //Chamada da classe DAO
                                prodEletroDAO.cadastrarProdutoEletronico(pe); //Inserção dos dados acima na classe DAO do banco
                            } catch (ParseException e) {
                                throw new DataFormatoIncorretoException();
                            }
                        }
                        break;
                        case 2: {
                            try {
                                System.out.println("Informe o ID (Identificador) do produto Eletrônico para excluí-lo");
                                System.out.print("ID: ");
                                int idProdEletronico = Integer.parseInt(sc.nextLine());

                                System.out.println("Tem certeza que deseja remover o produto do sistema? (S/N)");
                                char opExcluirProd = sc.next().charAt(0);
                                if (opExcluirProd == 's' || opExcluirProd == 'S') {
                                    System.out.println("Excluindo produto...");

                                    ProdutoEletronicoDAO prodEletroDAO = new ProdutoEletronicoDAO();
                                    prodEletroDAO.excluirProdutoEletronico(idProdEletronico);
                                } else {
                                    System.out.println("Cancelando a remoção do produto eletrônico...");
                                }
                            } catch (ProdutoNaoEncontradoException e) {
                                System.out.println("Erro: " + e.getMessage());
                            }
                        }
                        break;
                        case 3: {
                            try {
                                System.out.println("Informe o ID (Identificador) do produto Eletrônico para buscar os dados: ");
                                System.out.println("ID: ");
                                int idProdEletronico = Integer.parseInt(sc.nextLine());

                                ProdutoEletronicoDAO prodEletroDAO = new ProdutoEletronicoDAO();
                                prodEletroDAO.buscarProdutoEletronicoPorID(idProdEletronico);
                            }
                            catch (ProdutoNaoEncontradoException e) {
                                System.out.println("Erro" + e.getMessage());
                            }
                        }
                        break;
                        case 4: {
                            char repeticaoAlterarDadosProdELetronico = 'S';
                            try {
                                do {
                                    System.out.println("Alterar as informações selecionada!");
                                    System.out.println("Informe o ID do produto que deseja modificar");
                                    System.out.print("ID: ");
                                    int idProdEletronico = sc.nextInt();
                                    sc.nextLine();

                                    System.out.print("Você deseja alterar todas as informações ou apenas uma em especifíca?: (T/E): ");
                                    char opcaoAlterarInfo = sc.next().charAt(0);
                                    sc.nextLine();

                                    if (opcaoAlterarInfo == 'T' || opcaoAlterarInfo == 't') {
                                        System.out.println("opção de alterar todos os dados selecionado!");
                                        System.out.println("Informe os dados abaixo para que sejam alterados no sistema");

                                        System.out.print("Nome: ");
                                        String novoNome = sc.nextLine();

                                        System.out.print("Preço: ");
                                        double novoPreco = Double.parseDouble(sc.nextLine());

                                        System.out.print("Quantidade: ");
                                        int novaQuantidade = Integer.parseInt(sc.nextLine());

                                        System.out.print("Data de fabricação (dd/MM/yyyy): ");
                                        String novaDataString = sc.nextLine();
                                        Date utilDate = sdf.parse(novaDataString);
                                        java.sql.Date novaDataFabricacao = new java.sql.Date(utilDate.getTime());

                                        System.out.print("Fabricante: ");
                                        String novoFabricante = sc.nextLine();

                                        System.out.print("Garantia em meses: ");
                                        int novaGarantiaMeses = Integer.parseInt(sc.nextLine());

                                        System.out.print("Voltagem: ");
                                        String novaVoltagem = sc.nextLine();

                                        ProdutoEletronicoDAO prodEletroDAO = new ProdutoEletronicoDAO();
                                        prodEletroDAO.alterarTodosDadosProdutoEletronico(idProdEletronico, novoNome, novoPreco, novaQuantidade, novaDataFabricacao, novoFabricante, novaGarantiaMeses, novaVoltagem);
                                    } else if (opcaoAlterarInfo == 'E' || opcaoAlterarInfo == 'e') {
                                        System.out.println("Alteração de um dado específico selecionado!");

                                        System.out.println("Informe qual campo deseja alterar o dado já existente");
                                        System.out.println("1 - Para alterar o nome");
                                        System.out.println("2 - Para alterar o preço");
                                        System.out.println("3 - Para alterar a quantidade");
                                        System.out.println("4 - Para alterar a data de fabricação");
                                        System.out.println("5 - Para alterar o fabricante");
                                        System.out.println("6 - Para alterar a garantia em meses");
                                        System.out.println("7 - Para alterar a voltagem");
                                        System.out.print("Opção: ");
                                        int opEscolhaAlterarDado = Integer.parseInt(sc.nextLine());

                                        String campo = "";
                                        Object novoValor = null;

                                        switch (opEscolhaAlterarDado) {
                                            case 1: {
                                                campo = "nome";

                                                System.out.println("Opção de alterar o nome selecionada!");
                                                System.out.println("Informe o novo nome");

                                                System.out.print("Nome: ");
                                                novoValor = sc.nextLine();
                                            }
                                            break;
                                            case 2: {
                                                campo = "preco";

                                                System.out.println("Opção de alterar o preço selecionada!");
                                                System.out.println("Informe o novo preço");

                                                System.out.print("Preço: ");
                                                novoValor = Double.parseDouble(sc.nextLine());
                                            }
                                            break;
                                            case 3: {
                                                campo = "quantidade";

                                                System.out.println("Opção de alterar a quantidade selecionada!");
                                                System.out.println("Informe a nova quantidade");

                                                System.out.print("Quantidade: ");
                                                novoValor = Integer.parseInt(sc.nextLine());
                                            }
                                            break;
                                            case 4: {
                                                campo = "data_fabricacao";

                                                System.out.println("Opção de alterar a data de fabricação selecionada!");
                                                System.out.println("Informe a nova data de fabricação");

                                                System.out.print("Data de fabricação (dd/MM/yyyy): ");
                                                String novaData = sc.nextLine();
                                                Date utilDate = sdf.parse(novaData);
                                                java.sql.Date novaDataFormatada = new java.sql.Date(utilDate.getTime());
                                                novoValor = novaDataFormatada;
                                            }
                                            break;
                                            case 5: {
                                                campo = "fabricante";

                                                System.out.println("Opção de alterar o fabricante selecionada!");
                                                System.out.println("Informe o novo fabricante");

                                                System.out.print("Fabricante: ");
                                                novoValor = sc.nextLine();
                                            }
                                            break;
                                            case 6: {
                                                campo = "garantia_meses";

                                                System.out.println("Opção de alterar a garantia em meses selecionada!");
                                                System.out.println("Informe a nova garantia em meses: ");

                                                System.out.print("Garantia em meses: ");
                                                novoValor = Integer.parseInt(sc.nextLine());
                                            }
                                            break;
                                            case 7: {
                                                campo = "voltagem";

                                                System.out.println("Opção de alterar a voltagem selecionada!");
                                                System.out.println("Informe a nova voltagem");

                                                System.out.print("Voltagem: ");
                                                novoValor = Integer.parseInt(sc.nextLine());
                                            }
                                            break;
                                        }
                                        ProdutoEletronicoDAO prodEletroDAO = new ProdutoEletronicoDAO();
                                        prodEletroDAO.alterarDadosEspecificosProdutoEletronico(idProdEletronico, campo, novoValor);
                                    }
                                    System.out.println("Deseja alterar mais dados de um produto eletrônico? (S/N): ");
                                    char repeticaoAlterarDadosProdEletronico = sc.next().charAt(0);
                                }
                                while (repeticaoAlterarDadosProdELetronico == 'S' || repeticaoAlterarDadosProdELetronico == 's');
                            }
                            catch (ParseException e) {
                                throw new DataFormatoIncorretoException();
                            }
                        }
                        break;
                    }
                }
                break;
                case 2: {
                    System.out.println("Produto de Limpeza selecionado, selecione o que deseja ser feito: ");
                    System.out.println("1 - Inserir um produto de Limpeza no sistema");
                    System.out.println("2 - Deletar um produto de Limpeza do sistema");
                    System.out.println("3 - Buscar informações de um produto de Limpeza no sistema");
                    System.out.println("4 - Para alterar as informações de um produto de Limpeza no sistema");
                    System.out.print("Opção: ");
                    int opProdLimpeza = Integer.parseInt(sc.nextLine());

                    switch (opProdLimpeza) {
                        case 1: {
                            try {
                                System.out.println("Informe os dados para inserir no sistema: ");
                                System.out.print("Nome: ");
                                String nome = sc.nextLine();

                                System.out.print("Preço: ");
                                double preco = Double.parseDouble(sc.nextLine());

                                System.out.print("Quantidade: ");
                                int quantidade = Integer.parseInt(sc.nextLine());

                                System.out.print("Data de fabricação (dd/MM/yyyy): ");
                                String dataString = sc.nextLine();
                                Date utilDate = sdf.parse(dataString);
                                java.sql.Date dataFabricacao = new java.sql.Date(utilDate.getTime());

                                System.out.println("Fabricante: ");
                                String fabricante = sc.nextLine();

                                System.out.println("Fragrância: ");
                                String fragrancia = sc.nextLine();

                                System.out.println("Volume em ML: ");
                                int volume_ml = Integer.parseInt(sc.nextLine());

                                System.out.println("Uso recomendado: ");
                                String uso = sc.nextLine();

                                ProdutoLimpeza pl = new ProdutoLimpeza(nome, preco, quantidade, dataFabricacao, fabricante, fragrancia, volume_ml, uso);

                                ProdutoLimpezaDAO prodLimpDAO = new ProdutoLimpezaDAO();
                                prodLimpDAO.cadastrarProdutoLimpeza(pl);
                            } catch (ParseException e) {
                                throw  new DataFormatoIncorretoException();
                            }
                        }
                        break;
                        case 2: {
                            System.out.println("Insira o ID (identificador) do produto de limpeza para excluí-lo");
                            System.out.print("ID: ");
                            int idProdLimpeza = Integer.parseInt(sc.nextLine());

                            System.out.println("Tem certeza que deseja excluir o produto do sistema? (S/N): ");
                            char opExcluirProdLimpeza = sc.next().charAt(0);
                            if (opExcluirProdLimpeza == 'S' || opExcluirProdLimpeza == 's') {
                                System.out.println("Excluindo produto do sistema...");

                                ProdutoLimpezaDAO prodLimpezaDAO = new ProdutoLimpezaDAO();
                                prodLimpezaDAO.excluirProdutoLimpeza(idProdLimpeza);
                            } else {
                                System.out.println("Cancelando a exclusão do produto de limpeza...");
                            }
                        }
                        break;
                        case 3: {
                            try {
                                System.out.println("Informe o ID (identificador) do produto de limpeza: ");
                                System.out.print("ID: ");
                                int idProdLimpeza = Integer.parseInt(sc.nextLine());

                                ProdutoLimpezaDAO prodLimpeDAO = new ProdutoLimpezaDAO();
                                prodLimpeDAO.buscarProdutoLimpezaPorID(idProdLimpeza);
                            } catch (ProdutoNaoEncontradoException e) {
                                System.out.println("Erro: " + e.getMessage());
                            }
                        }
                        break;
                        case 4: {
                            char repeticaoAlterarDadosProdLimpeza = 'S';
                            try {
                                do {
                                    System.out.println("Alterar dados de um produto selecionado!");
                                    System.out.println("Informe o ID do produto que deseja ser alterado os dados");
                                    System.out.print("ID: ");
                                    int idProdLimpeza = sc.nextInt();
                                    sc.nextLine();

                                    System.out.print("Você deseja alterar todas as informações ou apenas uma em especifíca?: (T/E): ");
                                    char opcaoAlterarInfo = sc.next().charAt(0);
                                    sc.nextLine();

                                    if (opcaoAlterarInfo == 'T' || opcaoAlterarInfo == 't') {
                                        System.out.println("Opção de alterar todos os dados selecionado!");
                                        System.out.println("Informe os dados abaixo para que sejam alterados no sistema");
                                        System.out.print("Nome: ");
                                        String novoNome = sc.nextLine();

                                        System.out.print("Preço: ");
                                        double novoPreco = Double.parseDouble(sc.nextLine());

                                        System.out.print("Quantidade: ");
                                        int novaQuantidade = Integer.parseInt(sc.nextLine());

                                        System.out.print("Data de fabricação (dd/MM/yyyy): ");
                                        String dataString = sc.nextLine();
                                        Date utilDate = sdf.parse(dataString);
                                        java.sql.Date novaDataFabricacao = new java.sql.Date(utilDate.getTime());

                                        System.out.print("Fabricante: ");
                                        String novoFabricante = sc.nextLine();

                                        System.out.print("Fragrância: ");
                                        String novaFragrancia = sc.nextLine();

                                        System.out.print("Volume em ML: ");
                                        int novoVolumeMl = Integer.parseInt(sc.nextLine());

                                        System.out.print("Uso recomendado:");
                                        String novoUso = sc.nextLine();

                                        ProdutoLimpezaDAO prodLimpDAO = new ProdutoLimpezaDAO();
                                        prodLimpDAO.alterarTodosDadosProdutoLimpeza(idProdLimpeza, novoNome, novoPreco, novaQuantidade, novaDataFabricacao, novoFabricante, novaFragrancia, novoVolumeMl, novoUso);
                                    } else if (opcaoAlterarInfo == 'E' || opcaoAlterarInfo == 'e') {
                                        System.out.println("Alteração de um dado específico selecionado!");

                                        System.out.println("Informe qual campo deseja alterar o dado já existente");
                                        System.out.println("1 - Para alterar o nome");
                                        System.out.println("2 - Para alterar o preço");
                                        System.out.println("3 - Para alterar a quantidade");
                                        System.out.println("4 - Para alterar a data de fabricação");
                                        System.out.println("5 - Para alterar o fabricante");
                                        System.out.println("6 - Para alterar a fragrância");
                                        System.out.println("7 - Para alterar o volume em ML");
                                        System.out.println("8 - Para alterar o uso recomendado");

                                        System.out.print("Opção: ");
                                        int opEscolhaAlterarDado = sc.nextInt();
                                        sc.nextLine();

                                        String campo = "";
                                        Object novoValor = null;

                                        switch (opEscolhaAlterarDado) {
                                            case 1: {
                                                campo = "nome";

                                                System.out.println("Insira o novo  nome");
                                                System.out.print("Nome: ");
                                                novoValor = sc.nextLine();
                                            }
                                            break;
                                            case 2: {
                                                campo = "preco";

                                                System.out.println("Insira o novo preço");
                                                System.out.print("Preço: ");
                                                novoValor = Double.parseDouble(sc.nextLine());
                                            }
                                            break;

                                            case 3: {
                                                campo = "quantidade";

                                                System.out.println("Informe a nova quantidade:");
                                                System.out.print("QUantidade: ");
                                                novoValor = Integer.parseInt(sc.nextLine());
                                            }
                                            break;
                                            case 4: {
                                                campo = "data_fabricacao";

                                                System.out.println("Informe a nova data de fabricação");
                                                System.out.print("Data de fabricação: (dd/MM/yyyy): ");
                                                String dataFabricacao = sc.nextLine();
                                                Date utilDate = sdf.parse(dataFabricacao);
                                                java.sql.Date novaDataFabricacao = new java.sql.Date(utilDate.getTime());
                                                novoValor = novaDataFabricacao;
                                            }
                                            break;
                                            case 5: {
                                                campo = "fabricante";

                                                System.out.println("Informe a nova fabricante: ");
                                                System.out.print("Fabricante: ");
                                                novoValor = sc.nextLine();
                                            }
                                            break;
                                            case 6: {
                                                campo = "fragrancia";

                                                System.out.println("Informe a nova fragância");
                                                System.out.print("Fragrância: ");
                                                novoValor = sc.nextLine();
                                            }
                                            break;
                                            case 7: {
                                                campo = "volume_ml";

                                                System.out.println("Informe o novo volume em ML");
                                                System.out.print("Volume em ML: ");
                                                novoValor = Integer.parseInt(sc.nextLine());
                                            }
                                            break;
                                            case 8: {
                                                campo = "uso";

                                                System.out.println("Informe o novo tipo de uso");
                                                System.out.print("Uso: ");
                                                novoValor = sc.nextLine();
                                            }
                                            break;
                                        }
                                        ProdutoLimpezaDAO prodLimpDAO = new ProdutoLimpezaDAO();
                                        prodLimpDAO.alterarDadosEspecificosProdutoLimpeza(idProdLimpeza, campo, novoValor);
                                    }
                                    System.out.println("Deseja alterar mais dados de um produto de limpeza? (S/N): ");
                                    repeticaoAlterarDadosProdLimpeza = sc.next().charAt(0);
                                    System.out.print("Opção: ");
                                }
                                while (repeticaoAlterarDadosProdLimpeza == 'S' || repeticaoAlterarDadosProdLimpeza == 's');
                            }
                            catch (ParseException e) {
                                throw new DataFormatoIncorretoException(e.getMessage());
                            }
                        }
                        break;
                    }
                }
                break;
                case 3: {
                    System.out.println("Produto perecível selecionado, selecione o que deseja ser feito: ");
                    System.out.println("1 - Inserir um produto perecível no sistema");
                    System.out.println("2 - Deletar um produto perecível do sistema");
                    System.out.println("3 - Buscar informações de um produto perecível no sistema");
                    System.out.println("4 - Para alterar os dados de um produto perecível");
                    System.out.print("Opção: ");
                    int opProdPerecivel = Integer.parseInt(sc.nextLine());

                    switch (opProdPerecivel) {
                        case 1: {
                            try {
                                System.out.println("Informe os dados do produto perecível para inseri-lo no sistema");

                                System.out.print("Nome: ");
                                String nome = sc.nextLine();
                                System.out.print("Preço: ");
                                double preco = Double.parseDouble(sc.nextLine());

                                System.out.print("Quantidade: ");
                                int quantidade = Integer.parseInt(sc.nextLine());

                                System.out.print("Data de fabricação (dd/MM/yyyy): ");
                                String dataFabricacaoString = sc.nextLine();
                                Date utilFabricacaoDate = sdf.parse(dataFabricacaoString);
                                java.sql.Date dataFabricacao = new java.sql.Date(utilFabricacaoDate.getTime());

                                System.out.print("Fabricante: ");
                                String fabricante = sc.nextLine();

                                System.out.print("Data de vencimento (dd/MM/yyyy): ");
                                String dataVencimentoString = sc.nextLine();
                                Date utilVencimentoDate = sdf.parse(dataVencimentoString);
                                java.sql.Date dataVencimento = new java.sql.Date(utilVencimentoDate.getTime());

                                System.out.print("Tipo do produto: ");
                                String tipoProduto = sc.nextLine();

                                System.out.print("Peso em gramas: ");
                                double pesoGramas = Double.parseDouble(sc.nextLine());

                                System.out.print("Temperatura para armazenamento: ");
                                String temperaturaArmazenamento = sc.nextLine();

                                ProdutoPerecivel pp = new ProdutoPerecivel(nome, preco, quantidade, dataFabricacao, fabricante, dataVencimento, tipoProduto, pesoGramas, temperaturaArmazenamento);
                                ProdutoPerecivelDAO prodPereciDAO = new ProdutoPerecivelDAO();
                                prodPereciDAO.cadastrarProdutoPerecivel(pp);
                            }
                            catch (ParseException e) {
                                throw new DataFormatoIncorretoException();
                            }
                        }
                        break;
                        case 2: {
                            System.out.println("Informe o ID (identificador) do produto perecível que deseja excluír");
                            System.out.print("ID: ");
                            int idProdPerecivel = Integer.parseInt(sc.nextLine());

                            System.out.println("Tem certeza que deseja excluír o produto perecível? (S/N)");
                            char opExcluirProd = sc.next().charAt(0);

                            if (opExcluirProd == 'S' || opExcluirProd == 's') {
                                System.out.println("Excluindo produto perecível do sistema...");

                                ProdutoPerecivelDAO prodPereciDAO = new ProdutoPerecivelDAO();
                                prodPereciDAO.excluirProdutoPerecivel(idProdPerecivel);
                            }
                            else {
                                System.out.println("Cancelando a exclusão do produto perecível no sistema....");
                            }
                        }
                        break;
                        case 3: {
                            try {
                                System.out.println("Informe o ID (identificador) do produto perecível que deseja buscar informações");
                                System.out.print("ID: ");
                                int idProdPerecivel = Integer.parseInt(sc.nextLine());

                                ProdutoPerecivelDAO prodPereciDAO = new ProdutoPerecivelDAO();
                                prodPereciDAO.listarProdutoPerecivel(idProdPerecivel);
                            }
                            catch (ProdutoNaoEncontradoException e) {
                                throw new ProdutoNaoEncontradoException(e.getMessage());
                            }
                        }
                        break;
                        case 4: {
                            try {
                                System.out.println("Alteração dos dados de um produto selecionado!");
                                System.out.println("Informe o ID do produto que deseja alterar os dados");
                                System.out.print("ID: ");
                                int idProdPerecivel = Integer.parseInt(sc.nextLine());

                                System.out.print("Você deseja alterar todos os dados ou um em específico? (T/E): ");
                                char opcaoAlterarInfo = sc.next().charAt(0);
                                sc.nextLine();

                                if (opcaoAlterarInfo == 'T' || opcaoAlterarInfo == 't') {
                                    System.out.println("Opção de alterar todos os dados selecionado!");
                                    System.out.println("Informe os dados abaixo para que sejam alterados no sistema");

                                    System.out.print("Nome: ");
                                    String novoNome = sc.nextLine();

                                    System.out.print("Preço: ");
                                    double novoPreco = Double.parseDouble(sc.nextLine());

                                    System.out.print("Quantidade: ");
                                    int novaQuantidade = Integer.parseInt(sc.nextLine());

                                    System.out.print("Data de fabricação (dd/MM/yyyy): ");
                                    String novaDataFabri = sc.nextLine();
                                    Date utilDateFabri = sdf.parse(novaDataFabri);
                                    java.sql.Date novaDataFabricacao = new java.sql.Date(utilDateFabri.getTime());

                                    System.out.print("Fabricante: ");
                                    String novoFabricante = sc.nextLine();

                                    System.out.print("Data de vencimento (dd/MM/yyyy): ");
                                    String novaDataVenci = sc.nextLine();
                                    Date utilDateVenci = sdf.parse(novaDataVenci);
                                    java.sql.Date novaDataVencimento = new java.sql.Date(utilDateVenci.getTime());

                                    System.out.print("Tipo do produto: ");
                                    String novoTipoProduto = sc.nextLine();

                                    System.out.print("Peso em gramas: ");
                                    double novoPesoGramas = Double.parseDouble(sc.nextLine());

                                    System.out.print("Temperatura de armazenamento: ");
                                    String novaTemperaturaArmazenamento = sc.nextLine();

                                    ProdutoPerecivelDAO prodPereciDAO = new ProdutoPerecivelDAO();
                                    prodPereciDAO.alterarTodosDadosProdutoPerecivel(idProdPerecivel, novoNome, novoPreco, novaQuantidade, novaDataFabricacao, novoFabricante,novaDataVencimento, novoTipoProduto, novoPesoGramas, novaTemperaturaArmazenamento);
                                }
                                else if (opcaoAlterarInfo == 'E' || opcaoAlterarInfo == 'e') {
                                    System.out.println("Alteração de um dado específico selecionado!");
                                    System.out.println("Informe o ID do produto que deseja alterar os dados");
                                    System.out.print("ID: ");
                                }
                            }
                            catch (ParseException e) {
                                throw new DataFormatoIncorretoException(e.getMessage());
                            }
                        }
                    }
                }
            }
            System.out.println("Deseja continuar no menu de interação? (S/N): ");
            System.out.print("Opção: ");
            repeticao = sc.next().charAt(0);
        }
        while (repeticao == 'S' || repeticao == 's');
        System.out.println("Obrigado!");

        sc.close();
    }
}