import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Objects;
import java.util.Scanner;

class Atendimento {
    private final LocalDate data;
    private final String descricao;

    public Atendimento(LocalDate data, String descricao) {
        this.data = data;
        this.descricao = descricao;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Atendimento that = (Atendimento) obj;
        return Objects.equals(data, that.data) && Objects.equals(descricao, that.descricao);
    }

    @Override
    public String toString() {
        String formattedDate = (data != null) ? data.format(DateTimeFormatter.ofPattern("dd-MM-yyyy")) : "\nData indisponível";
        return "Data: " + formattedDate + ", Descrição: " + descricao;
    }
}

class Paciente {
    private String nome;
    private LocalDate dataNascimento;
    private ArrayList<Atendimento> consultas;

    public Paciente(String nome, LocalDate dataNascimento) {
        this.nome = nome;
        this.dataNascimento = dataNascimento;
        this.consultas = new ArrayList<>();
    }

    public void alterarDados(String nome, LocalDate dataNascimento) {
        this.nome = nome;
        this.dataNascimento = dataNascimento;
    }

    public String getNome() {
        return nome;
    }

    public void adicionarConsulta(Atendimento consulta) {
        consultas.add(consulta);
    }

    public void mostrarInformacoes() {
        System.out.println("- Nome: " + nome);
        System.out.println("- Data de Nascimento: " + dataNascimento);
        System.out.println("- Consultas Realizadas: ");

        for (Atendimento consulta : consultas) {
            System.out.println(" - " + consulta);
        }
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Paciente paciente = (Paciente) obj;
        return Objects.equals(nome, paciente.nome) && Objects.equals(dataNascimento, paciente.dataNascimento);
    }

    @Override
    public String toString() {
        return "Nome: " + nome + ", Data de Nascimento: " + dataNascimento;
    }
}

class SistemaConsultaMedica {
    private ArrayList<Paciente> pacientes;

    public SistemaConsultaMedica() {
        this.pacientes = new ArrayList<>();
    }

    public void incluirPaciente(Paciente paciente) {
        pacientes.add(paciente);
        System.out.println("\n----> Paciente incluído com sucesso.");
    }

    public Paciente buscarPacientePorNome(String nome) {
        for (Paciente paciente : pacientes) {
            if (paciente.getNome().equalsIgnoreCase(nome)) {
                return paciente;
            }
        }
        return null;
    }

    public void alterarDadosPaciente(String nome, String novoNome, LocalDate novaDataNascimento) {
        Paciente paciente = buscarPacientePorNome(nome);

        if (paciente != null) {
            paciente.alterarDados(novoNome, novaDataNascimento);
            System.out.println("\nDados do paciente alterados com sucesso.\n");
        } else {
            System.out.println("\nPaciente não encontrado.\n");
        }
    }

    public void realizarConsulta(String nome, Atendimento consulta) {
        Paciente paciente = buscarPacientePorNome(nome);

        if (paciente != null) {
            paciente.adicionarConsulta(consulta);
            System.out.println("\nConsulta realizada com sucesso.\n");
        } else {
            System.out.println("\nPaciente não encontrado.\n");
        }
    }

    public void listarPacientes() {
        if (pacientes.isEmpty()) {
            System.out.println("\nNão há pacientes cadastrados.\n");
        } else {
            System.out.println("\n===Lista de Pacientes===");
            for (Paciente paciente : pacientes) {
                System.out.println("\n - " + paciente);
            }
        }
    }

    public void mostrarInformacoesPaciente(String nome) {
        Paciente paciente = buscarPacientePorNome(nome);

        if (paciente != null) {
            System.out.println("\n===Informações do Paciente===\n");
            paciente.mostrarInformacoes();
        } else {
            System.out.println("\nPaciente não encontrado.\n");
        }
    }

    public void apagarPaciente(String nome) {
        Iterator<Paciente> iterator = pacientes.iterator();

        while (iterator.hasNext()) {
            Paciente paciente = iterator.next();

            if (paciente.getNome().equalsIgnoreCase(nome)) {
                iterator.remove();
                System.out.println("\nPaciente removido com sucesso.\n");
                return;
            }
        }
        System.out.println("\nPaciente não encontrado.\n");
    }
}

public class Main {
    public static void main(String[] args) {
        SistemaConsultaMedica sistema = new SistemaConsultaMedica();
        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println("\n=== Sistema de Gerenciamento de Consultas ===\n");
            System.out.println("1. Realizar Consulta");
            System.out.println("2. Cadastrar Paciente");
            System.out.println("3. Listar Pacientes");
            System.out.println("4. Alterar Dados do Paciente");
            System.out.println("5. Mostrar Informações do Paciente");
            System.out.println("6. Apagar Paciente");
            System.out.println("0. Sair");
            System.out.println("\n=============================================\n");

            int escolha = -1;

            while (escolha < 0 || escolha > 6) {
                System.out.print("Escolha uma opção (0 a 6): ");

                if (scanner.hasNextInt()) {
                    escolha = scanner.nextInt();
                    scanner.nextLine();
                    if (escolha < 0 || escolha > 6) {
                        System.out.println("Opção inválida. Por favor, digite um número entre 0 e 6.");
                    }
                } else {
                    System.out.println("Opção inválida. Por favor, digite um número.");
                    scanner.nextLine();
                }
            }

            switch (escolha) {
                case 1:
                    System.out.print("\nDigite o nome do paciente para realizar a consulta: ");
                    String nomeConsulta = scanner.nextLine();

                    LocalDate dataConsulta = null;
                    boolean dataValida = false;

                    while (!dataValida) {
                        System.out.print("Digite a data da consulta (DD-MM-AAAA): ");
                        String dataConsultaStr = scanner.nextLine();

                        try {
                            dataConsulta = LocalDate.parse(dataConsultaStr, DateTimeFormatter.ofPattern("dd-MM-yyyy"));
                            dataValida = true;
                        } catch (DateTimeParseException e) {
                            System.out.println("Data inválida. Por favor, digite uma data no formato correto.");
                        }
                    }

                    System.out.print("Digite a descrição da consulta: ");
                    String descricaoConsulta = scanner.nextLine();
                    Atendimento novaConsulta = new Atendimento(dataConsulta, descricaoConsulta);
                    sistema.realizarConsulta(nomeConsulta, novaConsulta);
                    break;
                case 2:
                    System.out.print("\nDigite o nome do paciente: ");
                    String nome = scanner.nextLine();

                    LocalDate dataNascimento = null;
                    boolean dataNascimentoValida = false;

                    while (!dataNascimentoValida) {
                        System.out.print("Digite a data de nascimento (DD-MM-AAAA): ");
                        String dataNascimentoStr = scanner.nextLine();

                        try {
                            dataNascimento = LocalDate.parse(dataNascimentoStr, DateTimeFormatter.ofPattern("dd-MM-yyyy"));
                            dataNascimentoValida = true;
                        } catch (DateTimeParseException e) {
                            System.out.println("\n ERRO: Data inválida. Por favor, digite uma data no formato correto.");
                        }
                    }

                    Paciente novoPaciente = new Paciente(nome, dataNascimento);
                    sistema.incluirPaciente(novoPaciente);
                    break;
                case 3:
                    sistema.listarPacientes();
                    break;
                case 4:
                    System.out.print("\nDigite o nome do paciente a ser alterado: ");
                    String nomeAlterar = scanner.nextLine();
                    System.out.print("Digite o novo nome: ");
                    String novoNome = scanner.nextLine();

                    LocalDate novaDataNascimento = null;
                    boolean novaDataNascimentoValida = false;

                    while (!novaDataNascimentoValida) {
                        System.out.print("Digite a nova data de nascimento (DD-MM-AAAA): ");
                        String novaDataNascimentoStr = scanner.nextLine();

                        try {
                            novaDataNascimento = LocalDate.parse(novaDataNascimentoStr, DateTimeFormatter.ofPattern("dd-MM-yyyy"));
                            novaDataNascimentoValida = true;
                        } catch (DateTimeParseException e) {
                            System.out.println("\n ERRO: Data inválida. Por favor, digite uma data no formato correto.\n");
                        }
                    }

                    sistema.alterarDadosPaciente(nomeAlterar, novoNome, novaDataNascimento);
                    break;
                case 5:
                    System.out.print("\nDigite o nome do paciente a ser mostrado: ");
                    String nomeMostrar = scanner.nextLine();
                    sistema.mostrarInformacoesPaciente(nomeMostrar);
                    break;
                case 6:
                    System.out.print("\nDigite o nome do paciente a ser apagado: ");
                    String nomeApagar = scanner.nextLine();
                    sistema.apagarPaciente(nomeApagar);
                    break;
                case 0:
                    System.out.println("\nSaindo do programa. Até logo!");
                    scanner.close();
                    System.exit(0);
                default:
                    System.out.println("\nOpção inválida. Tente novamente.\n");
            }
        }
    }
}
