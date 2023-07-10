package pe.gob.migraciones.sgv.videollamadas.bean;

public class WebexCorrelativoBean {
    private Integer nIdSimVideGenTic;
    private String sTipTicket;
    private String sFormato;
    private Integer nCorrelativo;

    public Integer getnIdSimVideGenTic() {
        return nIdSimVideGenTic;
    }

    public void setnIdSimVideGenTic(Integer nIdSimVideGenTic) {
        this.nIdSimVideGenTic = nIdSimVideGenTic;
    }

    public String getsTipTicket() {
        return sTipTicket;
    }

    public void setsTipTicket(String sTipTicket) {
        this.sTipTicket = sTipTicket;
    }

    public String getsFormato() {
        return sFormato;
    }

    public void setsFormato(String sFormato) {
        this.sFormato = sFormato;
    }

    public Integer getnCorrelativo() {
        return nCorrelativo;
    }

    public void setnCorrelativo(Integer nCorrelativo) {
        this.nCorrelativo = nCorrelativo;
    }
}
