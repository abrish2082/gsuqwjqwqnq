package et.gov.eep.mms.mapper;

import et.gov.eep.commonApplications.mapper.AbstractFacade;
import et.gov.eep.commonApplications.entity.ColumnNameResolver;
import et.gov.eep.mms.entity.MmsDisposal;
import et.gov.eep.mms.entity.MmsDisposalItems;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 *
 * @author w_station
 */
@Stateless
public class MmsDisposalFacade extends AbstractFacade<MmsDisposal> {

    @PersistenceContext(unitName = "ERP-ejbPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public MmsDisposalFacade() {
        super(MmsDisposal.class);
    }

    public MmsDisposal getLastDisposalId() {

        Query query1 = em.createNamedQuery("MmsDisposal.findByNfaIdMaximum");

        MmsDisposal result = null;

        try {
            if (query1.getResultList().size() > 0) {
                result = (MmsDisposal) query1.getResultList().get(0);
            } else {
                return result;
            }

            return result;
        } catch (Exception ex) {

            ex.printStackTrace();
            return null;
        }
    }

    public List<MmsDisposal> searchDisposalByParameterPrefix(MmsDisposal dispEntity) {
        Query query = em.createNamedQuery("MmsDisposal.findByAllParameters");
        query.setParameter("dispNo", '%' + dispEntity.getDispNo() + '%');
        try {
            ArrayList<MmsDisposal> dispList = new ArrayList(query.getResultList());
            return dispList;
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }

    public MmsDisposal getSelectedRequest2(BigDecimal dispId) {
        Query query = em.createNamedQuery("MmsDisposal.findByDispId");
        query.setParameter("dispId", dispId);
        System.err.println("===" + query.getResultList().size());
        try {
            MmsDisposal selectrequest = (MmsDisposal) query.getResultList().get(0);
            return selectrequest;
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }

    public List<MmsDisposal> searchDisposalByParameterPrefixAndDispPrep(MmsDisposal dispEntity) {
        Query query = em.createNamedQuery("MmsDisposal.findByAllParametersAndDispPrep");
        query.setParameter("dispNo", dispEntity.getDispNo());
        query.setParameter("proposedBy", dispEntity.getProposedBy());
        System.out.println("====facade==" + dispEntity.getDispNo());
        try {
            ArrayList<MmsDisposal> dispList = new ArrayList(query.getResultList());
            return dispList;
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }

    public List<MmsDisposal> findDisposalListByWfStatus(int PREPARE_VALUE) {
        Query query = em.createNamedQuery("MmsDisposal.findDisposalListByWfStatus", MmsDisposal.class);
        query.setParameter("dpStatus", PREPARE_VALUE);
        try {
            ArrayList<MmsDisposal> listofinspection = new ArrayList(query.getResultList());
            return listofinspection;
        } catch (Exception e) {
            return null;
        }
    }

    public List<MmsDisposal> searchAllDisposalInfoByPreparerId(Integer proposedBy) {
        Query query = em.createNamedQuery("MmsDisposal.findAllByPreparerId", MmsDisposal.class);

        query.setParameter("proposedBy", proposedBy);
        System.out.println("======@facade====" + proposedBy);
        try {
            ArrayList<MmsDisposal> LocList = new ArrayList(query.getResultList());
            return LocList;
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }

    public List<MmsDisposal> getMmsDisposeColumnNameList() {
        Query query = em.createNativeQuery("SELECT column_name\n"
                + "   FROM USER_TAB_COLUMNS\n"
                + "   WHERE table_name = UPPER('MMS_DISPOSAL')\n"
                + "   and COLUMN_NAME NOT IN ('PROPOSED_DATE','DISP_ID','STORE_ID','DISPOSAL_ITEM_ID','DP_STATUS','PROPOSAL_REMARK','DISPOSAL_STATUS','APPROVER_REMARK')\n"
                + "   ORDER BY column_name ASC");
        try {
            List<MmsDisposal> colNameLists = new ArrayList<>();
            colNameLists = query.getResultList();
            return colNameLists;
        } catch (Exception e) {
            e.getMessage();
            return null;
        }
    }

    public List<MmsDisposal> getDisposeListsByParameter(MmsDisposal dispEntity) {
          List<MmsDisposal> colValueLists = new ArrayList<>();
        if (dispEntity.getColumnName() != null  && dispEntity.getColumnValue() != null ) {
            System.out.println("when if");
            Query query = em.createNativeQuery("SELECT * FROM MMS_DISPOSAL\n"
                    + "   WHERE " + dispEntity.getColumnName().toLowerCase() + "='" + dispEntity.getColumnValue() + "'"
                     
                    + " ", MmsDisposal.class);
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
            Query query = em.createNamedQuery("MmsDisposal.findByProposedBy");
            query.setParameter("proposedBy", dispEntity.getProposedBy());
            colValueLists = query.getResultList();
            System.out.println("list of size " + colValueLists.size());
            return colValueLists;
        }
    }

    public List<ColumnNameResolver> getColumnNameList() {
        Query query = em.createNativeQuery("SELECT column_name\n"
                + "   FROM USER_TAB_COLUMNS\n"
                + "   WHERE table_name = UPPER('MMS_DISPOSAL')\n"
                + "   and COLUMN_NAME NOT IN ('PROPOSED_DATE','DISP_ID','STORE_ID','DISPOSAL_ITEM_ID','DP_STATUS','PROPOSAL_REMARK','DISPOSAL_STATUS','APPROVER_REMARK','APP_DATE','PROP_DATE','DISP_COLLECTION','REFERENCE_NUMBER')\n"
                + "   ORDER BY column_name ASC");
        try {
            List<String> RetrivedColumns = new ArrayList<>();
            RetrivedColumns = query.getResultList();
            List<ColumnNameResolver> ResolvedCol_list = new ArrayList<>();
            if (RetrivedColumns.size() > 0) {
                for (int i = 0; i < RetrivedColumns.size(); i++) {
                    ColumnNameResolver obj = new ColumnNameResolver();
                    System.out.println("RetrivedColumns.get(i)===" + RetrivedColumns.get(i));
                    obj.setCol_Name_FromTable(RetrivedColumns.get(i));
                    obj.setParsed_Col_Name(ColumnParser(RetrivedColumns.get(i)).toLowerCase());
                    ResolvedCol_list.add(obj);
                }
            }
            return ResolvedCol_list;
        } catch (Exception e) {
            e.getMessage();
            return null;
        }
        
    }
     public String ColumnParser(String col_name) {
        String col = col_name.replace("_", " ");
        System.out.println("col==" + col);
        return col;
    }

    public List<MmsDisposal> getdisposalListsByParameter(MmsDisposal dispEntity) {
        List<MmsDisposal> colValueLists = new ArrayList<>();
        if (dispEntity.getColumnName() != null && dispEntity.getColumnValue() != null ) {
            System.out.println("when if");
            Query query = em.createNativeQuery("SELECT * FROM MMS_DISPOSAL\n"
                 + "   WHERE " + dispEntity.getColumnName().toLowerCase() + "='" + dispEntity.getColumnValue() + "'"
                    + "", MmsDisposal.class);
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
            Query query = em.createNamedQuery("MmsDisposal.findByProposedBy");
            query.setParameter("ProposedBy", dispEntity.getProposedBy());
            colValueLists = query.getResultList();
            System.out.println("list of size " + colValueLists.size());
            return colValueLists;
        }
    }
    }


