package et.gov.eep.fcms.mapper.bank;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import et.gov.eep.commonApplications.mapper.AbstractFacade;
import et.gov.eep.fcms.entity.bank.FmsBankBranchAccounts;

/**
 *
 * @author Musie
 */
@Stateless
public class Bank_Reconciliation_Facade extends AbstractFacade<FmsBankBranchAccounts> {

    @PersistenceContext(unitName = "ERP-ejbPU")
    EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public Bank_Reconciliation_Facade() {
        super(FmsBankBranchAccounts.class);
    }

}
