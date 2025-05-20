import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class Evento implements Comparable<Evento> {
    private String nome;
    private String endereco;
    private String categoria;
    private LocalDateTime horario;
    private String descricao;
    private List<String> participantes = new ArrayList<>();

    public Evento(String nome, String endereco, String categoria, LocalDateTime horario, String descricao) {
        this.nome = nome;
        this.endereco = endereco;
        this.categoria = categoria;
        this.horario = horario;
        this.descricao = descricao;
    }

    public String getNome() {
        return nome;
    }

    public String getEndereco() {
        return endereco;
    }

    public String getCategoria() {
        return categoria;
    }

    public LocalDateTime getHorario() {
        return horario;
    }

    public String getDescricao() {
        return descricao;
    }

    public List<String> getParticipantes() {
        return participantes;
    }

    public void adicionarParticipante(String nomeUsuario) {
        if (!participantes.contains(nomeUsuario)) {
            participantes.add(nomeUsuario);
        }
    }

    public void removerParticipante(String nomeUsuario) {
        participantes.remove(nomeUsuario);
    }

    @Override
    public int compareTo(Evento outro) {
        return this.horario.compareTo(outro.horario);
    }

    @Override
    public String toString() {
        DateTimeFormatter fmt = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
        return String.format("[%s] %s - %s (%s)
Endereço: %s
Descrição: %s
Participantes: %d",
                categoria, nome, horario.format(fmt), horario.isBefore(LocalDateTime.now()) ? "Já ocorreu" :
                (horario.isBefore(LocalDateTime.now().plusMinutes(1)) ? "Acontecendo agora" : "Futuro"),
                endereco, descricao, participantes.size());
    }

    public String toDataFormat() {
        return nome + ";" + endereco + ";" + categoria + ";" + horario.toString() + ";" + descricao + ";" + String.join(",", participantes);
    }

    public static Evento fromDataFormat(String linha) {
        String[] partes = linha.split(";");
        Evento evento = new Evento(partes[0], partes[1], partes[2], LocalDateTime.parse(partes[3]), partes[4]);
        if (partes.length > 5 && !partes[5].isEmpty()) {
            for (String nome : partes[5].split(",")) {
                evento.adicionarParticipante(nome);
            }
        }
        return evento;
    }
}