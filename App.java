import java.util.ArrayList;
import java.util.Scanner;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.StringReader;

class App{
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
        System.out.print("[");
        for (int i = 0; i < this.getProprietarios().size(); i++){
            System.out.println();
            String content = this.getProprietarios().get(i).toString();
            Scanner input = new Scanner(content).useDelimiter("\n");
            // print which line with a tab
            while (input.hasNext()){
                System.out.println("    " + input.next());
            }
            input.close(); // avoid resource leak
        }
        System.out.println("]");
    }

    public void run(){
        int action;
        Scanner actionInput = new Scanner(this.source);
        while (true){
            System.out.println("Digite a ação desejada: ");
            System.out.println("1 - Cadastrar imóvel");
            System.out.println("2 - Cadastrar proprietário");
            System.out.println("3 - Listar imóveis");
            System.out.println("4 - Listar proprietários");
            System.out.println("5 - Calcular aluguel de um dado imóvel");
            System.out.println("6 - Sair");
            System.out.println("-------------------------");
            action = actionInput.nextInt();
            actionInput.nextLine(); // NOTE flush newline after reading int

            if (action == 1){
                Imovel imovel = this.readImovel(actionInput);
                this.getImoveis().add(imovel);
            } else if (action == 2){
                try{
                    Proprietario propr = this.readProprietario(actionInput);
                    this.getProprietarios().add(propr);
                } catch (UsuarioExistenteException e){
                    System.out.println(String.format("Erro: %s", e.getMessage()));
                    System.out.println("Cancelando ação...");
                    System.out.println("-------------------------");
                    continue; // skip action
                }
            } else if (action == 3){
                System.out.println("Imóveis cadastrados:");
                this.prettyPrintImoveis();
            } else if (action == 4){
                System.out.println("Proprietários cadastrados:");
                this.prettyPrintProprietarios();
            } else if (action == 5){
                this.displayAluguel(actionInput);
            }
            else if (action == 6){
                System.out.println("Saindo...");
                break;
            } else {
                System.out.println("Ação inválida.");
            }
        }
        actionInput.close(); // avoid resource leaks
    }

    public Endereco readEndereco(Scanner input){
        String rua, cep, estado, cidade;
        Long numero;

        //Scanner input = new Scanner(this.source); //FIXME REMOVE MULTIPLE SCANNER DECL
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

        //Scanner input = new Scanner(this.source); //FIXME REMOVE MULTIPLE SCANNER DECL
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

    public Proprietario readProprietario(Scanner input) throws UsuarioExistenteException{
        String nome, cpf, rg;
        Endereco endereco;

        //Scanner input = new Scanner(this.source); //FIXME REMOVE MULTIPLE SCANNER DECL
        System.out.println("Digite o nome do proprietário: ");   
        nome = input.nextLine();
        System.out.println("Digite o CPF do proprietário (apenas dígitos): ");
        cpf = input.nextLine();
        // check for same cpf
        for (Proprietario p : this.getProprietarios()){
            if (p.getCpf().equals(cpf)){
                throw new UsuarioExistenteException("Tentativa de adicionar proprietário existente.");
            }
        }

        System.out.println("Digite o RG do proprietário (apenas dígitos): ");
        rg = input.nextLine();
        // check for same rg
        for (Proprietario p : this.getProprietarios()){
            if (p.getRg().equals(rg)){
                throw new UsuarioExistenteException("Tentativa de adicionar proprietário existente.");
            }
        }
        // ok

        System.out.println("Entre com as informações de endereço do proprietário abaixo:");
        endereco = this.readEndereco(input);

        return new Proprietario(nome, cpf, rg, endereco);
    }

    public void displayAluguel(Scanner input){
        //Scanner input = new Scanner(this.source); //FIXME REMOVE MULTIPLE SCANNER DECL
        System.out.println("Digite o número do IPTU do imóvel: ");

        Imovel im = null;
        Long numeroIPTU = input.nextLong();
        boolean foundImovel = false;
        for (Imovel i: this.getImoveis()){
            if (i.getNumeroIPTU().equals(numeroIPTU)){
                im = i;
                foundImovel = true;
                break;
            }
        }

        if (!foundImovel){
            throw new IllegalArgumentException("Imóvel não encontrado. Saindo...");
        }

        System.out.println("Digite a sazonalidade do aluguel (0-4): ");
        System.out.println("0 - Comum");
        System.out.println("1 - Reveillon");
        System.out.println("2 - Carnaval");
        System.out.println("3 - Feriado Alta Estação");
        System.out.println("4 - Feriado Baixa Estação");
        int sazonalidade = input.nextInt();
        if (sazonalidade < 0 || sazonalidade > 4){
            throw new IllegalArgumentException("Sazonalidade inválida. Saindo...");
        }

        if (im instanceof UnidadeAutonoma){
            System.out.println("Imóvel do tipo Unidade Autônoma.");
            System.out.println("O valor do aluguel é: " + ((UnidadeAutonoma)im).calculaAluguel(sazonalidade));
        } else if (im instanceof UnidadeCompartilhada){
            System.out.println("Imóvel do tipo Unidade Compartilhada.");
            System.out.println("O valor do aluguel é: " + ((UnidadeCompartilhada)im).calculaAluguel(sazonalidade));
        } else {
            throw new IllegalArgumentException("Tipo de imóvel inválido. Saindo...");
        }
    }
}
