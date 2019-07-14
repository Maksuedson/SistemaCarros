
package br.max;

import dao.CarroDAO;
import exception.ErroSistema;
import java.util.ArrayList;
import java.util.List;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;

@ManagedBean
@SessionScoped
public class CarroBean {
    private Carro carro = new Carro();
    private CarroDAO carroDao = new CarroDAO();
    private List<Carro> carros = new ArrayList();
    
    public void adicionar(){
        try {
            carroDao.salvar(carro);
            adicionarMensagem("Salvo", "Carro salvo com sucesso!", FacesMessage.SEVERITY_INFO);
        } catch (ErroSistema ex) {
            adicionarMensagem(ex.getMessage(), ex.getCause().getMessage(), FacesMessage.SEVERITY_ERROR);
        }
        carro = new Carro();
        listar();
    }
    public void deletar(Carro c){
        try {
            carroDao.deletar(c.getId());
            adicionarMensagem("Deletado", "Carro deletado com sucesso!", FacesMessage.SEVERITY_INFO);
        } catch (ErroSistema ex) {
            adicionarMensagem(ex.getMessage(), ex.getCause().getMessage(), FacesMessage.SEVERITY_ERROR);            
        }
       // carro = new Carro();
        //listar();
    }
    
    
    public void listar(){
        try {
            carros = carroDao.buscar();
            if (carros == null || carros.size() == 0){
                adicionarMensagem("Nenhum dado encontrado", "Sua busca não retornou nenhum carro!", FacesMessage.SEVERITY_WARN);
            }
        } catch (ErroSistema ex) {
           adicionarMensagem(ex.getMessage(), ex.getCause().getMessage(), FacesMessage.SEVERITY_ERROR);
        }
    }
    
    public void editar(Carro c){
        carro = c;
    }

    public void adicionarMensagem(String sumario, String detalhe, FacesMessage.Severity tipoErro){
        FacesContext context = FacesContext.getCurrentInstance();
        FacesMessage message  = new FacesMessage(tipoErro, sumario, detalhe);       
        context.addMessage(null, message);
    }
    public Carro getCarro() {
        return carro;
    }

    public void setCarro(Carro carro) {
        this.carro = carro;
    }

    public List<Carro> getCarros() {
        return carros;
    }

    public void setCarros(List<Carro> carros) {
        this.carros = carros;
    }
}
