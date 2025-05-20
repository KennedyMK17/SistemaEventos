import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

public class EventoManager {
    private List<Evento> eventos = new ArrayList<>();
    private Usuario usuario;
    private final Scanner scanner = new Scanner(System.in);
    private final String[] categorias = {"Festa", "Show", "Esporte", "Cultura"};

    public void menu() {
        System.out.println("=== Cadastro de Usuário ===");
        System.out.print("Nome: ");
        String nome = scanner.nextLine();
        System.out.print("Email: ");
        String email = scanner.nextLine();
        System.out.print("Telefone: ");
        String telefone = scanner.nextLine();
        usuario = new Usuario(nome, email, telefone);

        int opcao;
        do {
            System.out.println("\n=== Menu Principal ===");
            System.out.println("1. Cadastrar evento");
            System.out.println("2. Listar eventos");
            System.out.println("3. Confirmar participação");
            System.out.println("4. Cancelar participação");
            System.out.println("5. Ver eventos confirmados");
            System.out.println("0. Sair");
            System.out.print("Escolha: ");
            opcao = Integer.parseInt(scanner.nextLine());

            switch (opcao) {
                case 1 -> cadastrarEvento();
                case 2 -> listarEventos();
                case 3 -> confirmarParticipacao();
                case 4 -> cancelarParticipacao();
                case 5 -> listarEventosConfirmados();
                case 0 -> System.out.println("Saindo...");
                default -> System.out.println("Opção inválida!");
            }
        } while (opcao != 0);
    }

    private void cadastrarEvento() {
        System.out.println("== Novo Evento ==");
        System.out.print("Nome: ");
        String nome = scanner.nextLine();
        System.out.print("Endereço: ");
        String endereco = scanner.nextLine();
        System.out.println("Categoria (Festa, Show, Esporte, Cultura): ");
        String categoria = scanner.nextLine();
        System.out.print("Data e hora (dd/MM/yyyy HH:mm): ");
        LocalDateTime horario = LocalDateTime.parse(scanner.nextLine(), DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm"));
        System.out.print("Descrição: ");
        String descricao = scanner.nextLine();
        eventos.add(new Evento(nome, endereco, categoria, horario, descricao));
        System.out.println("Evento cadastrado com sucesso!");
    }

    private void listarEventos() {
        System.out.println("== Lista de Eventos ==");
        eventos.stream().sorted().forEach(System.out::println);
    }

    private void confirmarParticipacao() {
        listarEventos();
        System.out.print("Digite o nome do evento que deseja participar: ");
        String nome = scanner.nextLine();
        eventos.stream()
                .filter(e -> e.getNome().equalsIgnoreCase(nome))
                .findFirst()
                .ifPresentOrElse(e -> {
                    e.adicionarParticipante(usuario.getNome());
                    System.out.println("Participação confirmada!");
                }, () -> System.out.println("Evento não encontrado."));
    }

    private void cancelarParticipacao() {
        listarEventosConfirmados();
        System.out.print("Digite o nome do evento que deseja sair: ");
        String nome = scanner.nextLine();
        eventos.stream()
                .filter(e -> e.getNome().equalsIgnoreCase(nome))
                .findFirst()
                .ifPresentOrElse(e -> {
                    e.removerParticipante(usuario.getNome());
                    System.out.println("Participação cancelada.");
                }, () -> System.out.println("Evento não encontrado."));
    }

    private void listarEventosConfirmados() {
        System.out.println("== Eventos Confirmados ==");
        eventos.stream()
                .filter(e -> e.getParticipantes().contains(usuario.getNome()))
                .forEach(System.out::println);
    }

    public void salvarEventos() {
        List<String> linhas = new ArrayList<>();
        for (Evento e : eventos) {
            linhas.add(e.toDataFormat());
        }
        ArquivoUtil.salvarArquivo("events.data", linhas);
    }

    public void carregarEventos() {
        List<String> linhas = ArquivoUtil.lerArquivo("events.data");
        for (String l : linhas) {
            eventos.add(Evento.fromDataFormat(l));
        }
    }
}