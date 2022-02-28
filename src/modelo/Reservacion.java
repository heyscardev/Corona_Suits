
package modelo;

import java.time.LocalDate;
import modelo.CRUDS.CRUDCliente;
import modelo.CRUDS.CRUDContratoHabitacion;
import modelo.CRUDS.CRUDContratoServicio;
import modelo.CRUDS.CRUDHabitacion;
import modelo.CRUDS.CRUDServicio;
import modelo.CRUDS.CRUDTipoHabitacion;


public class Reservacion {
    private String  codigoReservacion;
    private int id_cliente;
    private LocalDate fechaEntrada;
    private String sFechaEntrada;
    private int horaEntrada;
    private LocalDate fechaSalida;
    String sFechaSalida;
    private int horaSalida;
    private double costoTotal;
    Cliente cli = null;
    

    public Reservacion(String codigoReservacion, int id_cliente, LocalDate fechaEntrada, int horaEntrada, LocalDate fechaSalida, int horaSalida, double costoTotal) {
        this.codigoReservacion = codigoReservacion;
        this.id_cliente = id_cliente;
        this.fechaEntrada = fechaEntrada;
        this.sFechaEntrada = fechaEntrada.toString();
        this.horaEntrada = horaEntrada;
        this.fechaSalida = fechaSalida;
        this.sFechaSalida = fechaSalida.toString();
        this.horaSalida = horaSalida;
        this.costoTotal = costoTotal;
    }

    public String getCodigoReservacion() {
        return codigoReservacion;
    }

    private void setCodigoReservacion(String codigoReservacion) {
        this.codigoReservacion = codigoReservacion;
    }

    public int getId_cliente() {
        return id_cliente;
    }
    public String getNombre_cliente() {
        if(cli == null)
            cli = CRUDCliente.getInstance().findOneId(id_cliente);
        return cli.getNombre();
    }
    public String getCedula_cliente() {
        if(cli == null)
            cli = CRUDCliente.getInstance().findOneId(id_cliente);
        return cli.getCedula();
    }
    public Cliente getCliente(){
        if(cli == null)
            cli = CRUDCliente.getInstance().findOneId(id_cliente);
        return cli;
    }

    private void setId_cliente(int id_cliente) {
        this.id_cliente = id_cliente;
    }

    public LocalDate getFechaEntrada() {
        return fechaEntrada;
    }

    private void setFechaEntrada(LocalDate fechaEntrada) {
        this.fechaEntrada = fechaEntrada;
    }

    public int getHoraEntrada() {
        return horaEntrada;
    }

    private void setHoraEntrada(int horaEntrada) {
        this.horaEntrada = horaEntrada;
    }

    public LocalDate getFechaSalida() {
        return fechaSalida;
    }

    private void setFechaSalida(LocalDate fechaSalida) {
        this.fechaSalida = fechaSalida;
    }

    public int getHoraSalida() {
        return horaSalida;
    }

    private void setHoraSalida(int horaSalida) {
        this.horaSalida = horaSalida;
    }

    public double getCostoTotal() {
        return costoTotal;
    }

    private void setCostoTotal(double costoTotal) {
        this.costoTotal = costoTotal;
    }

    public String getSFechaEntrada() {
        return sFechaEntrada;
    }

    public String getSFechaSalida() {
        return sFechaSalida;
    }
    public String getStringHoraEntradaformat12(){
        if(horaEntrada >12)return Integer.toString(horaEntrada-12) + " PM";
        else if(horaEntrada<1)return Integer.toString(12) + " AM";
        else if (horaEntrada == 12) return Integer.toString(12) + " PM";
        else return Integer.toString(horaEntrada) + " AM";
    }
    public String getStringHoraSalidaformat12(){
        if(horaSalida >12)return Integer.toString(horaSalida-12) + " PM";
        else if(horaSalida<1)return Integer.toString(12) + " AM";
        else if (horaSalida == 12) return Integer.toString(12) + " PM";
        else return Integer.toString(horaSalida) + " AM";
    }
    public double getPreioTotal(){
        double preciotota = 0;
        for (ContratoHabitacion h :  CRUDContratoHabitacion.getInstance().findOneContratoHabitacion(codigoReservacion)) {
          Habitacion Habit =CRUDHabitacion.getInstance().findOneCodigo_reservacion(h.getId_habitacion());
            preciotota+= CRUDTipoHabitacion.getInstance().findOneid_tipoHabitacion(Habit.getId_tipo_habitaciones()).getCosto();
        }
        for (ContratoServicio c : CRUDContratoServicio.getInstance().findOneServicios(codigoReservacion)){
                preciotota+= CRUDServicio.getInstance().findOneid_servicio(c.getId_servicio()).getPrecio();
        }
       return preciotota;
    }
    
}
