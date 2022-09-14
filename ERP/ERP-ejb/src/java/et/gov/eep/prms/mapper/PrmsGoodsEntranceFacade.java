/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package et.gov.eep.prms.mapper;

import et.gov.eep.commonApplications.entity.ColumnNameResolver;
import et.gov.eep.commonApplications.mapper.AbstractFacade;
import et.gov.eep.hrms.entity.organization.HrDepartments;
import et.gov.eep.mms.entity.MmsDisposal;
import et.gov.eep.prms.entity.PrmsContract;
import et.gov.eep.prms.entity.PrmsContractAmendment;
import et.gov.eep.prms.entity.PrmsGoodsEntrance;
import et.gov.eep.prms.entity.PrmsLcRigistration;
import et.gov.eep.prms.entity.PrmsLcRigistrationAmend;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

@Stateless
public class PrmsGoodsEntranceFacade extends AbstractFacade<PrmsGoodsEntrance> {

    @PersistenceContext(unitName = "ERP-ejbPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public PrmsGoodsEntranceFacade() {
        super(PrmsGoodsEntrance.class);
    }

//    public List<PrmsGoodsEntrance> getRegistNo(PrmsGoodsEntrance prmsGoodsEntrance) {
//        Query query = em.createNamedQuery("PrmsGoodsEntrance.findByRegistrationNumLikes", PrmsGoodsEntrance.class);
//        query.setParameter("registrationNo", prmsGoodsEntrance.getRegistrationNo()+ '%');
//
//        try {
//            List<PrmsGoodsEntrance> GoodsEntrances = new ArrayList<>(query.getResultList());
//            return GoodsEntrances;
//        } catch (Exception ex) {
//            ex.printStackTrace();
//            return null;
//        }
//    }
//    
    public ArrayList<PrmsGoodsEntrance> getRegistNo(PrmsGoodsEntrance prmsGoodsEntrance) {
        Query query = em.createNamedQuery("PrmsGoodsEntrance.findByRegistrationNos", PrmsGoodsEntrance.class);
        query.setParameter("registrationNo", prmsGoodsEntrance.getRegistrationNo() + '%');
        //    query.setParameter("prepatedBy", prmsGoodsEntrance.getPrepatedBy() + '%');
        try {
            ArrayList<PrmsGoodsEntrance> goodsEntrances = new ArrayList<>(query.getResultList());
            return goodsEntrances;
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }

    public PrmsGoodsEntrance getSelectedId(String id) {
        Query query = em.createNamedQuery("PrmsGoodsEntrance.findById");
        query.setParameter("id", id);
        try {
            PrmsGoodsEntrance idlst = (PrmsGoodsEntrance) query.getResultList().get(0);
            return idlst;
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }

    }

    public PrmsGoodsEntrance getLastRegNo() {
        Query query = em.createNamedQuery("PrmsGoodsEntrance.findByMaxId");
        PrmsGoodsEntrance result = null;
        try {
            if (query.getResultList().size() > 0) {
                result = (PrmsGoodsEntrance) query.getResultList().get(0);
            }

            return result;
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }

    public HrDepartments getSelectDepartement(int key) {
        Query query = em.createNamedQuery("HrDepartments.findByDepId");
        query.setParameter("depId", key);
        try {
            HrDepartments selectdepartment = (HrDepartments) query.getResultList().get(0);
            return selectdepartment;
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }

    public List<PrmsGoodsEntrance> getGoodsLst() {
        Query query = em.createNamedQuery("PrmsGoodsEntrance.findByReqForApproval");
        ArrayList<PrmsGoodsEntrance> Goodslst = new ArrayList<>(query.getResultList());
        return Goodslst;
    }

    public List<PrmsGoodsEntrance> findGoodsNosss(PrmsGoodsEntrance prmsGoodsEntrance) {
        Query query = em.createNamedQuery("PrmsGoodsEntrance.findByGoodsNoos", PrmsGoodsEntrance.class);
        query.setParameter("registrationNo", prmsGoodsEntrance.getRegistrationNo() + '%');

        try {
            List<PrmsGoodsEntrance> marketAssessmentLst = new ArrayList(query.getResultList());
            return marketAssessmentLst;
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }

    public List<PrmsLcRigistration> findApprovedLCNo() {
        Query query = em.createNamedQuery("PrmsLcRigistration.findAlls");
        try {
            List<PrmsLcRigistration> lc = new ArrayList<>();
            if (query.getResultList().size() > 0) {
                lc = query.getResultList();
            }
            return lc;
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }

    public List<PrmsContractAmendment> checkAmendOrNot(PrmsContract prmsContract) {
        System.out.println("cont id --" + prmsContract.getContractId());
//        Query query = em.createNamedQuery("PrmsContractAmendment.findByContractId");
//        query.setParameter("contractId", prmsContract.getContractId());
        Query query = em.createNativeQuery("SELECT * FROM prms_contract_amendment contamd\n"
                + "inner join(select contract_id, max (contract_amend_id) as maxContAmendId \n"
                + "from prms_contract_amendment \n"
                + "group by contract_id)contamd2\n"
                + "on contamd.contract_amend_id=contamd2.maxContAmendId\n"
                + "inner join prms_contract cont\n"
                + "on cont.contract_id=contamd.contract_id\n"
                + "where cont.contract_id='" + prmsContract.getContractId() + "'");
        try {
            List<PrmsContractAmendment> amendedContractList = new ArrayList<>();
            if (query.getResultList().size() > 0) {
                amendedContractList = query.getResultList();
                System.out.println("size -- " + amendedContractList.size());
            }
            return amendedContractList;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public PrmsContractAmendment getContractAmendment(PrmsContract prmsContract) {
        System.out.println("when cont id --- " + prmsContract.getContractId());
        Query q = em.createNativeQuery("SELECT * FROM prms_contract_amendment contamd\n"
                + "inner join(select contract_id, max(contract_amend_id) as maxContAmendId\n"
                + "from prms_contract_amendment \n"
                + "group by contract_id) contamd2\n"
                + "on contamd.contract_amend_id=contamd2.maxContAmendId\n"
                + "where contamd.contract_id = '" + prmsContract.getContractId() + "'", PrmsContractAmendment.class);
        try {
            PrmsContractAmendment contractAmendedInfo = new PrmsContractAmendment();
            if (q.getResultList().size() > 0) {
                contractAmendedInfo = (PrmsContractAmendment) q.getResultList().get(0);
                System.out.println("Supplier Id from Amended Cntract " + contractAmendedInfo.getSuppId());
            }
            return contractAmendedInfo;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public List<PrmsLcRigistrationAmend> getCheckLcAmended(PrmsLcRigistration prmsLcRigistration) {
        System.out.println("id here " + prmsLcRigistration.getLcId());
        Query query = em.createNativeQuery("SELECT lcamd.* FROM PRMS_LC_RIGISTRATION_AMEND lcamd\n"
                + "INNER JOIN(SELECT MAX(id) as maxAmendedId\n"
                + "FROM PRMS_LC_RIGISTRATION_AMEND) lcamd2\n"
                + "on lcamd.ID=lcamd2.maxAmendedId\n"
                + "WHERE lcamd.LC_ID in (SELECT lc.LC_ID FROM PRMS_LC_RIGISTRATION lc)");
        try {
            List<PrmsLcRigistrationAmend> lcAmendedList = new ArrayList<>();
            if (query.getResultList().size() > 0) {
                lcAmendedList = query.getResultList();
                System.out.println("size " + lcAmendedList.size());
            }
            return lcAmendedList;
        } catch (NullPointerException es) {
            es.printStackTrace();
            return null;
        }
    }

    public PrmsLcRigistrationAmend getLcAmendedInfo(PrmsLcRigistration prmsLcRigistration) {
        System.out.println("id here " + prmsLcRigistration.getLcId());
        Query query = em.createNativeQuery("SELECT lcamd.* FROM PRMS_LC_RIGISTRATION_AMEND lcamd\n"
                + "INNER JOIN(SELECT MAX(id) as maxAmendedId\n"
                + "FROM PRMS_LC_RIGISTRATION_AMEND) lcamd2\n"
                + "on lcamd.ID=lcamd2.maxAmendedId\n"
                + "WHERE '"+prmsLcRigistration.getLcId()+"' in (SELECT lc.LC_ID FROM PRMS_LC_RIGISTRATION lc)",PrmsLcRigistrationAmend.class);
//                + "WHERE lcamd.LC_ID in (SELECT lc.LC_ID FROM PRMS_LC_RIGISTRATION lc)",PrmsLcRigistrationAmend.class);
        try {
            PrmsLcRigistrationAmend lcAmendedInfo = new PrmsLcRigistrationAmend();
            if (query.getResultList().size() > 0) {
                lcAmendedInfo = (PrmsLcRigistrationAmend) query.getResultList().get(0);
                System.out.println(" Latest Amended Id " + lcAmendedInfo.getId());
            }
            return lcAmendedInfo;
        } catch (NullPointerException es) {
            es.printStackTrace();
            return null;
        }
    }
    public List<PrmsGoodsEntrance> getGoodsListsByParameter(PrmsGoodsEntrance prmsGoodsEntrance) {
         List<PrmsGoodsEntrance> colValueLists = new ArrayList<>();
        if (prmsGoodsEntrance.getColumnName() != null  && prmsGoodsEntrance.getColumnValue() != null ) {
            System.out.println("when if");
            Query query = em.createNativeQuery("SELECT * FROM PRMS_GOODS_ENTRANCE\n"
                  + "   WHERE " + prmsGoodsEntrance.getColumnName().toLowerCase() + "='" + prmsGoodsEntrance.getColumnValue() + "'"
                     
                    + " ", PrmsGoodsEntrance.class);
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
            Query query = em.createNamedQuery("PrmsGoodsEntrance.findByPrepatedBy");
            query.setParameter("PrepatedBy", prmsGoodsEntrance.getPrepatedBy());
            colValueLists = query.getResultList();
            System.out.println("list of size " + colValueLists.size());
            return colValueLists;
        }
    }

    public List<ColumnNameResolver> getColumnNameList() {
        Query query = em.createNativeQuery("SELECT column_name\n"
                + "   FROM USER_TAB_COLUMNS\n"
                + "   WHERE table_name = UPPER('PRMS_GOODS_ENTRANCE')\n"
                + "   and COLUMN_NAME NOT IN ('DESCRIPTION','COUNTRY_ID','GOODS_AMOUNT','UNIT_MEASURE','PORT_OF_DISCHARGE','BANK_PERMIT','SUPPLIER','CURRENCY_ID','CONTRACT_AMOUNT','BILL_NO','OPERATION_NO','ESLSE_NO','ICAS_NO','AIRPORT','RAILWAY','CUSTOM_AUTHORITY','CUSTOM_COST','SERVICE_COST','MISCELLANEOUS_COST','TOTAL_COST','REMARK','ID','COTRACT_ID','DEPOSITE_COST','CASH_RECEIVABLE','PROJECT','DEP_ID','LC_ID','COMMERCIAL_INVOICE','DECLARATION_NO','INITIAL_DEPOSITE','ACTUAL_PAID','REMAINING_AMOUNT','DOCUMENT_ID','STATUS','CASH_PAYABLE')\n"
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

}


