public class ValorHistorico {
    //atributos
    private int mes;
    private double acumuladoMedio;

    //constructor por defecto
    public ValorHistorico() {
    }

    //constructor parametrizado 
    public ValorHistorico(int mes, double acumuladoMedio) {
        this.mes = mes;
        this.acumuladoMedio = acumuladoMedio;
    }

    //getters
    public int getMes() {
        return mes;
    }

    public double getAcumuladoMedio() {
        return acumuladoMedio;
    }

    //setters
    public void setMes(int mes) {
        this.mes = mes;
    }

    public void setAcumuladoMedio(double acumuladoMedio) {
        this.acumuladoMedio = acumuladoMedio;
    }

    //toString
    @Override
    public String toString() {
        return "ValorHistorico{" + "mes=" + mes + ", acumuladoMedio=" + acumuladoMedio + '}';
    }
}