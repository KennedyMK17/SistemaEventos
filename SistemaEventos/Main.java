
public class Main {
    public static void main(String[] args) {
        EventoManager manager = new EventoManager();
        manager.carregarEventos();
        manager.menu();
        manager.salvarEventos();
    }
}