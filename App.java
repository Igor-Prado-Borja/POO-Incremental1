import java.util.ArrayList;
import java.util.Scanner;
import java.io.BufferedReader;
import java.io.StringReader;


class App{
    private ArrayList<Imovel> imoveis;
    private ArrayList<Proprietario> proprietarios;

    App(){
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
            String content = this.getImoveis().get(i).toString();
            Scanner input = new Scanner(content).useDelimiter("\n");
            // print which line with a tab
            while (input.hasNext()){
                System.out.println("    " + input.next());
            }
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
        }
        System.out.println("]");
    }

    public void run(){
        int action;
        Scanner actionInput = new Scanner(System.in);
        while (true){
            System.out.println("Digite a ação desejada: ");
            System.out.println("1 - Cadastrar imóvel");
            System.out.println("2 - Cadastrar proprietário");
            System.out.println("3 - Listar imóveis");
            System.out.println("4 - Listar proprietários");
            System.out.println("5 - Sair");
            System.out.println("-------------------------");
            action = actionInput.nextInt();
            
            if (action == 1){
                Imovel imovel = this.readImovel();
                this.getImoveis().add(imovel);
            } else if (action == 2){
                Proprietario propr = this.readProprietario();
                this.getProprietarios().add(propr);
            } else if (action == 3){
                System.out.println("Imóveis cadastrados:");
                this.prettyPrintImoveis();
            } else if (action == 4){
                System.out.println("Proprietários cadastrados:");
                this.prettyPrintProprietarios();
            }
            else if (action == 5){
                break;
            } else {
                System.out.println("Ação inválida.");
            }
        }
    }

    public Endereco readEndereco(){
        String rua, cep, estado, cidade;
        Long numero;

        Scanner input = new Scanner(System.in);
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

    public Imovel readImovel(){
        Long numeroIPTU;
        String tipo, uso;
        Endereco endereco;

        Scanner input = new Scanner(System.in);
        System.out.println("Digite o número do IPTU do imóvel: ");
        numeroIPTU = input.nextLong();
        // nextLong() does not consume the newline character, so we need to do it manually
        input.nextLine();        

        System.out.println("Entre com as informações de endereço do imóvel abaixo:\n");
        endereco = this.readEndereco(); 
        
        System.out.println("Digite o tipo do imóvel: ");
        tipo = input.nextLine();
        System.out.println("Digite o uso do imóvel: ");
        uso = input.nextLine();
        
        return new Imovel(numeroIPTU, endereco, tipo, uso);      
    }

    public Proprietario readProprietario(){
        String nome, cpf, rg;
        Endereco endereco;

        Scanner input = new Scanner(System.in);
        System.out.println("Digite o nome do proprietário: ");   
        nome = input.nextLine();
        System.out.println("Digite o CPF do proprietário: ");
        cpf = input.nextLine();
        System.out.println("Digite o RG do proprietário: ");
        rg = input.nextLine();

        System.out.println("Entre com as informações de endereço do proprietário:\n");
        endereco = this.readEndereco(); 
        
        return new Proprietario(nome, cpf, rg, endereco);
    }
}