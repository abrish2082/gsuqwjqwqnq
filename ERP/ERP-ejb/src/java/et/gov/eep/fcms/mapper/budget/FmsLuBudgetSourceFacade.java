
package et.gov.eep.fcms.mapper.budget;
//<editor-fold defaultstate="collapsed" desc="import ">
import et.gov.eep.commonApplications.mapper.AbstractFacade;
import et.gov.eep.fcms.entity.budget.FmsLuBudgetSource;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
    //</editor-fold>

@Stateless
public class FmsLuBudgetSourceFacade extends AbstractFacade<FmsLuBudgetSource> {
    @PersistenceContext(unitName = "ERP-ejbPU")
    private EntityManager em;
//<editor-fold defaultstate="collapsed" desc="other methods ">
    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public FmsLuBudgetSourceFacade() {
        super(FmsLuBudgetSource.class);
    }
    //</editor-fold>
    
    //<editor-fold defaultstate="collapsed" desc="NamedQuery">
     public FmsLuBudgetSource findByBudgetSourceId(FmsLuBudgetSource budgetSource) {
        Query query = em.createNamedQuery("FmsLuBudgetSource.findByBudgetSourceId");
        query.setParameter("budgetSourceId", budgetSource.getBudgetSourceId());
        try {
            return (FmsLuBudgetSource) query.getResultList().get(0);
        } catch (Exception ex) {
            return null;
        }
    }
    //</editor-fold>
     
    
    
    
   
}
