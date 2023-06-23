import java.util.ArrayList;
import java.util.Scanner;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.StringReader;
import java.util.Arrays;
import java.time.LocalDate;
import java.util.HashMap;

class App extends CalendarioGlobal{
    private static final ArrayList<String> actionNames
        = new ArrayList<String>(Arrays.asList(
        "Cadastrar imóvel",
        "Cadastrar proprietário",
        "Listar imóveis",
        "Listar proprietários",
        "Calcular aluguel de um dado imóvel em uma dada sazonalidade",
        "Calcular aluguel de um dado imóvel em um dado período",
        "Verificar disponibilidade de um dado imóvel",
        "Sair"
    ));

    private ArrayList<Imovel> imoveis;
    private ArrayList<Proprietario> proprietarios;
    private InputStream source;

    App(InputStream source){
        this.source = source;
        this.imoveis = new ArrayList<Imovel>();
        this.proprietarios = new ArrayList<Proprietario>();
    }

    public ArrayList<Imovel> getImoveis(){
        return this.imoveis;
    }

    public ArrayList<Proprietario> getProprietarios(){
        return this.proprietarios;
    }

    public void prettyPrintImoveis(){
        System.out.print("[");
        for (int i = 0; i < this.getImoveis().size(); i++){
            System.out.println();
            Imovel im = this.getImoveis().get(i);
            String content = this.getImoveis().get(i).toString(); // base case (Imovel)
            if (im instanceof UnidadeAutonoma){
                content = ((UnidadeAutonoma)this.getImoveis().get(i)).toString();
            } else if (im instanceof UnidadeCompartilhada){
                content = ((UnidadeCompartilhada)this.getImoveis().get(i)).toString();
            }
            Scanner input = new Scanner(content).useDelimiter("\n");
            // print which line with a tab
            while (input.hasNext()){
                System.out.println("    " + input.next());
            }
            input.close(); // avoid resource leak
        }
        System.out.println("]");
    }

    public void prettyPrintProprietarios(){
        System.out.println("[");
        for (int i = 0; i < this.getProprietarios().size(); i++){
            String content = this.getProprietarios().get(i).toString();
            content = "    " + content.replaceAll("\n", "\n    "); // print all lines idented by 4 spaces
            System.out.println(content);
        }
        System.out.println("]");
    }

    public Imovel procuraImovelNumIPTU(Long numeroIPTU){
        for (Imovel im: this.getImoveis()){
            if (im.getNumeroIPTU().equals(numeroIPTU)){
                return im;
            }
        }
        return null;
    }

    public void run(){
        int action;
        Scanner actionInput = new Scanner(this.source);
        while (true){
            System.out.println("-----------------------------------");
            System.out.println("Digite a ação desejada: ");
            for (int i = 0; i < App.actionNames.size(); i++){
                System.out.println(Integer.toString(i + 1) + " - " + App.actionNames.get(i));
            }
            System.out.println("-----------------------------------");
            action = actionInput.nextInt();
            actionInput.nextLine(); // NOTE flush newline after reading int

            if (action == 1){
                // "Cadastrar imóvel",
                Imovel imovel = this.readImovel(actionInput);
                this.getImoveis().add(imovel);
            } else if (action == 2){
                // "Cadastrar proprietário"
                Proprietario propr = this.readProprietario(actionInput);
                this.getProprietarios().add(propr);
            } else if (action == 3){
                // "Listar imóveis"
                System.out.println("Imóveis cadastrados:");
                this.prettyPrintImoveis();
            } else if (action == 4){
                // "Listar proprietários"
                System.out.println("Proprietários cadastrados:");
                this.prettyPrintProprietarios();
            } else if (action == 5){
                // "Calcular aluguel de um dado imóvel em uma dada sazonalidade"
                this.displayAluguel(actionInput);
            } else if (action == 6) {
                // "Calcular aluguel de um dado imóvel em um dado período"
                this.displayAluguelPeriodo(actionInput);
            } else if (action == 7){
                // "Verificar disponibilidade de um dado imóvel"
                this.verificaDisponibilidade(actionInput);
            } else if (action == 8){
                // "Sair"
                System.out.println("Saindo...");
                break;
            } else {
                //
                System.out.println("Ação inválida.");
            }
        }
        actionInput.close(); // avoid resource leaks
    }

    public Endereco readEndereco(Scanner input){
        String rua, cep, estado, cidade;
        Long numero;

        System.out.println("Digite o nome da rua: ");
        rua = input.nextLine();
        System.out.println("Digite o número do imóvel: ");
        numero = input.nextLong();
        // nextLong() does not consume the newline character, so we need to do it manually
        input.nextLine();

        System.out.println("Digite o CEP do imóvel: ");
        cep = input.nextLine();
        System.out.println("Digite o estado do imóvel (sigla): ");
        estado = input.nextLine();
        System.out.println("Digite a cidade do imóvel: ");
        cidade = input.nextLine();
        return new Endereco(rua, numero, cep, estado, cidade);
    }

    public Imovel readImovel(Scanner input){
        Long numeroIPTU;
        String tipo, uso;
        Endereco endereco;

        System.out.println("Digite o número do IPTU do imóvel: ");
        numeroIPTU = input.nextLong();
        // nextLong() does not consume the newline character, so we need to do it manually
        // input.nextLine();

        System.out.println("Digite o valor do IPTU (anual) do imóvel: ");
        double valorIPTU = input.nextDouble();
        // nextDouble() does not consume the newline character, so we need to do it manually
        input.nextLine();

        System.out.println("Entre com as informações de endereço do imóvel abaixo:");
        endereco = this.readEndereco(input);
        
        System.out.println("Digite o tipo do imóvel: ");
        tipo = input.nextLine();
        System.out.println("Digite o uso do imóvel: ");
        uso = input.nextLine();
        
        System.out.println("Digite a classe de imóvel:");
        System.out.println("1 - Unidade autônoma");
        System.out.println("2 - Unidade compartilhada");

        int autonomia = input.nextInt();
        // flush the newline character
        input.nextLine();
        if (autonomia == 1){
            double areaUtil, areaConstruida;
            System.out.println("Digite a área útil do imóvel: ");
            areaUtil = input.nextDouble();
            System.out.println("Digite a área construída do imóvel: ");
            areaConstruida = input.nextDouble();
            return new UnidadeAutonoma(numeroIPTU, endereco, tipo, uso, valorIPTU, areaUtil, areaConstruida);
        } else if (autonomia == 2){
            String idCondominio;
            Endereco enderecoCondominio;
            System.out.println("Digite o ID do condomínio: ");
            idCondominio = input.nextLine();
            System.out.println("Digite as áreas de lazer da residência, separadas por vírgula:");
            String[] areasLazer = input.nextLine().split(",");
            System.out.println("Entre com as informações de endereço do condomínio abaixo:");
            enderecoCondominio = this.readEndereco(input);
            UnidadeCompartilhada imovel = new UnidadeCompartilhada(numeroIPTU, endereco, tipo, uso, valorIPTU, idCondominio, enderecoCondominio);
            for (String area: areasLazer){
                imovel.addAreaLazer(area);
            }
            return imovel;
        } else {
            throw new IllegalArgumentException("Classe de imóvel inválida.");
        }
    }

    public Proprietario readProprietario(Scanner input){
        String nome, cpf, rg;
        Endereco endereco;

        System.out.println("Digite o nome do proprietário: ");   
        nome = input.nextLine();
        System.out.println("Digite o CPF do proprietário: ");
        cpf = input.nextLine();
        System.out.println("Digite o RG do proprietário: ");
        rg = input.nextLine();

        System.out.println("Entre com as informações de endereço do proprietário abaixo:");
        endereco = this.readEndereco(input);

        Proprietario p = new Proprietario(nome, cpf, rg, endereco);

        while (true){
            System.out.println("Ações:");
            System.out.println("1 - Adicionar imóvel");
            System.out.println("2 - Sair");
            int action = input.nextInt();
            if (action == 1){
                Imovel im = null;
                System.out.println("Deseja vincular proprietário a Imóvel já existente? [Y/n]");
                String answer = input.next();
                input.nextLine(); // flush newline
                if (answer.equals("y")){
                    System.out.println("Digite o número do IPTU do imóvel: ");
                    Long numeroIPTU = input.nextLong();
                    im = this.procuraImovelNumIPTU(numeroIPTU);
                    if (im == null){
                        System.out.println("Imóvel não encontrado. Saindo...");
                        break; // early return from while loop
                    } 
                } else if (answer.equals("n")){
                    im = this.readImovel(input);
                    if (im == null){
                        System.out.println("Imóvel inválido, dexando de cadastrar.");
                        break; // early return from while loop
                    } 
                }
                p.cadastraImovel(im);
            } else if (action == 2){
                break;
            } else {
                System.out.println("Ação inválida.");
            }            
        }
        return p;
    }

    public void displayAluguel(Scanner input){
        System.out.println("Digite o número do IPTU do imóvel: ");

        Long numeroIPTU = input.nextLong();
        Imovel im = this.procuraImovelNumIPTU(numeroIPTU);
        if (im == null){
            System.out.println("Imóvel não encontrado. Saindo...");
            return;
        }

        System.out.println("Digite a sazonalidade do aluguel (0-4): ");
        this.printOpcoesSazonalidades();
        int sazonalidade = input.nextInt();

        if (sazonalidade < 0 || sazonalidade > App.MAXSAZONALIDADES){
            System.out.println("Sazonalidade inválida. Saindo...");
            return;
        }

        if (im instanceof UnidadeAutonoma){
            System.out.println("Imóvel do tipo Unidade Autônoma.");
            System.out.println("O valor do aluguel é: " + ((UnidadeAutonoma)im).calculaAluguel(sazonalidade));
        } else if (im instanceof UnidadeCompartilhada){
            System.out.println("Imóvel do tipo Unidade Compartilhada.");
            System.out.println("O valor do aluguel é: " + ((UnidadeCompartilhada)im).calculaAluguel(sazonalidade));
        } else {
            System.out.println("Tipo de imóvel inválido. Saindo...");
            return;
        }
    }

    public void displayAluguelPeriodo(Scanner input){
        System.out.println("Digite o número do IPTU do imóvel: ");
        Long numeroIPTU = input.nextLong();

        Imovel im = this.procuraImovelNumIPTU(numeroIPTU);

        if (im == null){
            System.out.println("Imóvel não encontrado. Saindo...");
            return;
        }

        // ler data inicial
        System.out.println("Entrando com a data inicial do período de aluguel:");
        LocalDate dataInicial = this.readLocalDate(input);
        System.out.println("Entrando com a data final do período de aluguel:");
        LocalDate dataFinal = this.readLocalDate(input);
        if (dataInicial == null || dataFinal == null){
            System.out.println("Datas inválidas");
            return;
        }

        double valorAluguel = im.calculaAluguelPeriodo(dataInicial, dataFinal, this.getFeriados());

        System.out.println("O valor do aluguel durante esse período é: " + Double.toString(valorAluguel));
    }

    public void verificaDisponibilidade(Scanner input){
        System.out.println("Digite o número do IPTU do imóvel: ");
        Long numeroIPTU = input.nextLong();

        Imovel im = this.procuraImovelNumIPTU(numeroIPTU);
        if (im == null){
            System.out.println("Imóvel não encontrado. Saindo...");
            return;
        }
        
        // ler data inicial
        System.out.println("Entrando com a data inicial do período de aluguel:");
        LocalDate dataInicial = this.readLocalDate(input);
        System.out.println("Entrando com a data final do período de aluguel:");
        LocalDate dataFinal = this.readLocalDate(input);
        if (dataInicial == null || dataFinal == null){
            System.out.println("Datas inválidas. Saindo...");
            return;
        }

        if (im.getAgenda().checkDisponibilidade(dataInicial, dataFinal)){
            System.out.println("Imóvel indisponível no período especificado.");
            System.out.println("Primeira data indisponível:" + im.getAgenda().findIndisponibilidade(dataInicial, dataFinal).toString());
        } else {
            if (im.getAgenda().checkInformacaoAusente(dataInicial, dataFinal)){
                System.out.println("Imóvel com disponibilidade desconhecida no período especificado.");
                System.out.println("Primeira data com status desconhecido:" + im.getAgenda().findInformacaoAusente(dataInicial, dataFinal).toString());
            } else {
                System.out.println("Imóvel disponível no período especificado.");
            }
        }
    }
}
