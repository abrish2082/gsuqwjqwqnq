package et.gov.eep.fcms.mapper.Ifrs;

import et.gov.eep.commonApplications.entity.ColumnNameResolver;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import et.gov.eep.commonApplications.mapper.AbstractFacade;
import et.gov.eep.fcms.entity.Ifrs.FinancialInstrumentRegister;
import et.gov.eep.fcms.entity.admin.FmsSubsidiaryLedger;

/**
 *
 * @author mz
 */
@Stateless
public class FinancialInstrumentRegisterFacade extends AbstractFacade<FinancialInstrumentRegister> {

    @PersistenceContext(unitName = "ERP-ejbPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public FinancialInstrumentRegisterFacade() {
        super(FinancialInstrumentRegister.class);
    }
    /*named query to select FmsSubsidiaryLedger value from FmsSubsidiaryLedger table using subsidiaryCode */

    public List<FmsSubsidiaryLedger> findbysubLedger(String subsidiaryLedger) {
        Query query = em.createNamedQuery("FmsSubsidiaryLedger.findBySubsidiaryCode");
        query.setParameter("subsidiaryCode", subsidiaryLedger);
        List<FmsSubsidiaryLedger> subsidiaryLedgerList = new ArrayList<>();
        if (query.getResultList().size() > 0) {
            subsidiaryLedgerList = query.getResultList();

        }
        return subsidiaryLedgerList;

    }

    public List<ColumnNameResolver> findColumns() {
        Query query = em.createNativeQuery("SELECT column_name  FROM user_tab_columns\n"
                + "WHERE table_name = UPPER('FINANCIAL_INSTRUMENT_REGISTER')\n"
                + "and column_name NOT IN('REGISTER_ID','ASSET_DESCRIPTION','ASSET_STATUS','BOND_TYPE','INTEREST_RATE','FMS_LU_FINANCIAL_ASSET_TYPE',\n"
                + "'FMS_LU_FINA_INSTRUMENT_MEASURE','FMS_LU_FINAN_INSTRUMENT_TYPE','GENERAL_LEDGER','SUBSIDIARY_LEDGER')\n "
                + "ORDER BY column_name ASC");
        try {
            List<String> RetrivedColumns = new ArrayList<>();
            RetrivedColumns = query.getResultList();
            List<ColumnNameResolver> ResolvedCol_list = new ArrayList<>();
            if (RetrivedColumns.size() > 0) {
                for (int i = 0; i < RetrivedColumns.size(); i++) {
                    ColumnNameResolver obj = new ColumnNameResolver();
                    obj.setCol_Name_FromTable(RetrivedColumns.get(i));
                    obj.setParsed_Col_Name(ColumnParser(RetrivedColumns.get(i)).toLowerCase());
                    ResolvedCol_list.add(obj);
                }
            }
            return ResolvedCol_list;
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }

    public String ColumnParser(String col_name) {
        String col = col_name.replace("_", " ");
        return col;
    }
     public ArrayList<FinancialInstrumentRegister> searchByCol_NameAndCol_Value(ColumnNameResolver columnNameResolver, String columnValue) {
        if (columnNameResolver.getCol_Name_FromTable() != null && columnValue != null) {
            Query query = em.createNativeQuery("SELECT * FROM FINANCIAL_INSTRUMENT_REGISTER\n"
                    + "where " + columnNameResolver.getCol_Name_FromTable().toLowerCase() + " LIKE'" + columnValue + "%'  ", FinancialInstrumentRegister.class);
            try {
                ArrayList<FinancialInstrumentRegister> financialInst = new ArrayList(query.getResultList());
                return financialInst;
            } catch (Exception ex) {
                ex.printStackTrace();
                return null;
            }
        } else {
            return null;
        }
    }
}
