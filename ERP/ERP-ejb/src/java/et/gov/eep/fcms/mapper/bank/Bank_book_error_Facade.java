package et.gov.eep.fcms.mapper.bank;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import et.gov.eep.commonApplications.mapper.AbstractFacade;
import et.gov.eep.fcms.entity.bank.FmsBankBookError;

/**
 *
 * @author sura
 */
@Stateless
public class Bank_book_error_Facade extends AbstractFacade<FmsBankBookError> {

    @PersistenceContext(unitName = "ERP-ejbPU")
    EntityManager em;

    public Bank_book_error_Facade() {
        super(FmsBankBookError.class);
    }

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }
}
