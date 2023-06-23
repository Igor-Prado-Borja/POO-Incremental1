import java.util.Scanner;
import java.util.Map;
import java.util.HashMap;
import java.time.LocalDate;

class CalendarioGlobal{
    public static final int MAXSAZONALIDADES = 4;
    public static final String sazonalidades[] = {
        "Comum", 
        "Reveillon",
        "Carnaval",
        "Feriado Alta Estação",
        "Feriado Baixa Estação"
    };
    private Map<LocalDate, Integer> feriados;

    public CalendarioGlobal(){
        this.feriados = new HashMap<LocalDate, Integer>();
    }

    public Map<LocalDate, Integer> getFeriados(){
        return this.feriados;
    }

    public void printOpcoesSazonalidades(){
        for (int i = 0; i <= CalendarioGlobal.MAXSAZONALIDADES; i++){
            System.out.println(i + " - " + CalendarioGlobal.sazonalidades[i]);
        }
    }

    public void registraFeriado(Scanner input){
        System.out.println("Digite o dia do feriado: ");
        int dia = input.nextInt();
        System.out.println("Digite o mês do feriado: ");
        int mes = input.nextInt();
        System.out.println("Digite o ano do feriado: ");
        int ano = input.nextInt();
        System.out.println("Digite a sazonalidade ");
        this.printOpcoesSazonalidades();
        int sazonalidade = input.nextInt();
        System.out.println("Feriado registrado.");

        LocalDate data = LocalDate.of(ano, mes, dia);
        this.feriados.put(data, sazonalidade);
    }

    public LocalDate readLocalDate(Scanner input){
        System.out.println("Digite o dia: ");
        int dia = input.nextInt();
        System.out.println("Digite o mês: ");
        int mes = input.nextInt();
        System.out.println("Digite o ano: ");
        int ano = input.nextInt();
        // sanitize the input

        try {
            return LocalDate.of(ano, mes, dia);
        } catch (Exception e){
            System.out.println("Data inválida.");
            return null; // NOTE: propagate error further
        }
    }
}