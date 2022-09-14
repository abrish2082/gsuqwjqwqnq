package et.gov.eep.fcms.mapper.bank;

import et.gov.eep.commonApplications.entity.ColumnNameResolver;
import java.util.ArrayList;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import et.gov.eep.commonApplications.mapper.AbstractFacade;
import et.gov.eep.fcms.entity.bank.FmsBank;
import java.util.List;

/**
 *
 * @author mubejbl
 */
@Stateless
public class FmsBankFacade extends AbstractFacade<FmsBank> {

    @PersistenceContext(unitName = "ERP-ejbPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public FmsBankFacade() {
        super(FmsBank.class);
    }

      /*named query to select bank list from FmsBank table using bankName's first letter match
    returen bank List*/
    public ArrayList<FmsBank> searchBankByName(FmsBank bankName) {
        Query query = em.createNamedQuery("FmsBank.findByBankNameLike");
        query.setParameter("bankName", bankName.getBankName().toUpperCase() + '%');
        try {
            ArrayList<FmsBank> bankList = new ArrayList(query.getResultList());
            return bankList;

        } catch (Exception ex) {

            return null;
        }

    }

      /*named query to select bank information from FmsBank table using bankName
    returen bank information*/
    public FmsBank getBankInfo(FmsBank bankName) {
        Query query = em.createNamedQuery("FmsBank.findByBankName");
        query.setParameter("bankName", bankName.getBankName());
        try {
            FmsBank bankInfo = (FmsBank) query.getResultList().get(0);
            return bankInfo;
        } catch (Exception ex) {

            return null;
        }
    }

       /*named query to select bank information from FmsBank table using bankName
    returen boolean value*/
    public boolean findDupByBankName(FmsBank fmsBank) {
        boolean dup;
        try {
            Query query = em.createNamedQuery("FmsBank.findByBankName");
            query.setParameter("bankName", fmsBank.getBankName());
            if (query.getResultList().size() > 0) {
                dup = true;
            } else {
                dup = false;
            }
            return dup;
        } catch (Exception ex) {
            return false;
        }
    }

     /*named query to select bank information from FmsBank table using bankCode
    returen boolean value*/
    public boolean findDupByBankCode(FmsBank fmsBank) {
        boolean dup;
        try {
            Query query = em.createNamedQuery("FmsBank.findByBankCode");
            query.setParameter("bankCode", fmsBank.getBankCode());
            if (query.getResultList().size() > 0) {
                dup = true;
            } else {
                dup = false;
            }
            return dup;
        } catch (Exception ex) {
            return false;
        }
    }

    public List<String> getMmsBankColumnNameList() {
        Query query = em.createNativeQuery("SELECT column_name\n"
                + "   FROM USER_TAB_COLUMNS\n"
                + "   WHERE table_name = UPPER('FMS_BANK')\n"
                + "   and COLUMN_NAME NOT IN ('BANK_ID')\n"
                + "   ORDER BY column_name ASC");
        try {
            List<String> colNameLists = new ArrayList<>();
            colNameLists = query.getResultList();
            return colNameLists;
        } catch (Exception e) {
            e.getMessage();
            return null;
        }
    }

    public List<FmsBank> getBankListsByParameter(ColumnNameResolver columnNameResolver, FmsBank fmsBank, String columnValue) {
        System.out.println("columnNameResolver.getCol_Name_FromTable()==" + columnNameResolver.getCol_Name_FromTable());
        System.out.println("Col_Value==" + columnValue);
        List<FmsBank> colValueLists = new ArrayList<>();
        if (fmsBank.getColumnName() != null && !fmsBank.getColumnName().equals("")
                && fmsBank.getColumnValue() != null && !fmsBank.getColumnValue().equals("")) {
            System.out.println("when if");

            Query query = em.createNativeQuery("SELECT * FROM FMS_BANK\n"
                     + "   WHERE " + fmsBank.getColumnName().toLowerCase() + "='" + fmsBank.getColumnValue() + "'" , FmsBank.class);
            try {
                if (query.getResultList().size() > 0) {
                    colValueLists = query.getResultList();
                }
                return colValueLists;
            } catch (Exception e) {
                e.getMessage();
                return null;
            }
        } else {
            System.out.println("In else " + colValueLists.size());
            Query query = em.createNamedQuery("FmsBank.findAll");
            
            colValueLists = query.getResultList();
            System.out.println("list of size " + colValueLists.size());
            return colValueLists;
        }
    }
}
