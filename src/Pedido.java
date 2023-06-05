import java.util.Date;

public class Pedido {
    private int id;
    private Date data;
    private double valor;
    private String status;

    public Pedido(int id, Date data, double valor, String status) {
        this.id = id;
        this.data = data;
        this.valor = valor;
        this.status = status;
    }

    public int getId() {
        return id;
    }

    public Date getData() {
        return data;
    }

    public double getValor() {
        return valor;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "Pedido #" + id + " | Data: " + data + " | Valor: " + valor + " | Status: " + status;
    }
}
