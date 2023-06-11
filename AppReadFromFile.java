import java.util.ArrayList;
import java.util.Scanner;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

class AppReadFromFile {
    private ArrayList<Imovel> imoveis;
    private ArrayList<Proprietario> proprietarios;

    AppReadFromFile() {
        this.imoveis = new ArrayList<Imovel>();
        this.proprietarios = new ArrayList<Proprietario>();
    }

    public ArrayList<Imovel> getImoveis() {
        return this.imoveis;
    }

    public ArrayList<Proprietario> getProprietarios() {
        return this.proprietarios;
    }

    public void prettyPrintImoveis() {
        System.out.print("[");
        for (int i = 0; i < this.getImoveis().size(); i++) {
            System.out.println();
            String content = this.getImoveis().get(i).toString();
            Scanner input = new Scanner(content).useDelimiter("\n");
            // print each line with a tab
            while (input.hasNext()) {
                System.out.println("    " + input.next());
            }
        }
        System.out.println("]");
    }

    public void prettyPrintProprietarios() {
        System.out.print("[");
        for (int i = 0; i < this.getProprietarios().size(); i++) {
            System.out.println();
            String content = this.getProprietarios().get(i).toString();
            Scanner input = new Scanner(content).useDelimiter("\n");
            // print each line with a tab
            while (input.hasNext()) {
                System.out.println("    " + input.next());
            }
        }
        System.out.println("]");
    }

    public void run(String inputFile) {
        try (BufferedReader reader = new BufferedReader(new FileReader(inputFile))) {
            int action;
            String line;

            while ((line = reader.readLine()) != null) {
                action = Integer.parseInt(line);

                if (action == 1) {
                    Imovel imovel = readImovel(reader);
                    this.getImoveis().add(imovel);
                } else if (action == 2) {
                    Proprietario propr = readProprietario(reader);
                    this.getProprietarios().add(propr);
                } else if (action == 3) {
                    System.out.println("Imóveis cadastrados:");
                    this.prettyPrintImoveis();
                } else if (action == 4) {
                    System.out.println("Proprietários cadastrados:");
                    this.prettyPrintProprietarios();
                } else if (action == 5) {
                    System.out.println("Saindo...");
                    break;
                } else {
                    System.out.println("Ação inválida.");
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public Endereco readEndereco(BufferedReader reader) throws IOException {
        String rua, cep, estado, cidade;
        Long numero;

        rua = reader.readLine();
        numero = Long.parseLong(reader.readLine());
        cep = reader.readLine();
        estado = reader.readLine();
        cidade = reader.readLine();
        return new Endereco(rua, numero, cep, estado, cidade);
    }

    public Imovel readImovel(BufferedReader reader) throws IOException {
        Long numeroIPTU;
        String tipo, uso;
        Endereco endereco;

        numeroIPTU = Long.parseLong(reader.readLine());
        endereco = readEndereco(reader);
        tipo = reader.readLine();
        uso = reader.readLine();

        return new Imovel(numeroIPTU, endereco, tipo, uso);
    }

    public Proprietario readProprietario(BufferedReader reader) throws IOException {
        String nome, cpf, rg;
        Endereco endereco;

        nome = reader.readLine();
        cpf = reader.readLine();
        rg = reader.readLine();
        endereco = readEndereco(reader);

        return new Proprietario(nome, cpf, rg, endereco);
    }
}
