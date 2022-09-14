/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package et.gov.eep.prms.mapper;

import et.gov.eep.commonApplications.mapper.AbstractFacade;
import et.gov.eep.prms.entity.PrmsContract;
import et.gov.eep.prms.entity.PrmsContractAmendment;
import et.gov.eep.prms.entity.PrmsContractCurrencyDetail;
import et.gov.eep.prms.entity.PrmsContractDetail;
import et.gov.eep.prms.entity.PrmsContractamendCurrency;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 *
 * @author user
 */
@Stateless
public class PrmsContractAmendmentFacade extends AbstractFacade<PrmsContractAmendment> {

    @PersistenceContext(unitName = "ERP-ejbPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public PrmsContractAmendmentFacade() {
        super(PrmsContractAmendment.class);
    }

    public PrmsContract getContractNumber(String string) {
        System.out.println("gggp" + string);
        PrmsContract bidNoLst = null;
        Query query = em.createNamedQuery("PrmsContract.findByContractNo", PrmsContract.class);
        query.setParameter("contractNo", string);
        try {
            bidNoLst = (PrmsContract) query.getResultList().get(0);
            System.out.println("gggp========" + bidNoLst.getContractNo());
            return bidNoLst;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return bidNoLst;
    }

    public List<PrmsContractDetail> getBidNo(String bidNo) {
        System.out.println("ggg" + bidNo);
        List<PrmsContractDetail> bidNoLst = null;
        Query query = em.createNamedQuery("PrmsContractDetail.findByContractNo", PrmsContractDetail.class);
        query.setParameter("contractNo", bidNo);
        try {
            bidNoLst = (List<PrmsContractDetail>) query.getResultList();
            System.out.println("ggg========" + bidNoLst.size());
            return bidNoLst;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return bidNoLst;
    }

    /**
     *
     * @param toString
     * @return
     */
    public List<PrmsContractAmendment> getContractAmend(String toString) {

        System.out.println("g=====" + toString);
        Query query = em.createNamedQuery("PrmsContractAmendment.findByContractAmendNo");
        List<PrmsContractAmendment> directPurcObj = null;
        query.setParameter("contractAmendNo", toString);
//        query.setParameter("preparedBy", papmsContract.getPreparedBy());

        try {
            directPurcObj = (List<PrmsContractAmendment>) query.getResultList();
            System.out.println("k=====" + directPurcObj.size());
            return directPurcObj;
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }

    /**
     * *************************************************************************
     *
     * @param prmsContractAmendment
     * @return
     * ***********************************************************************
     */
    public List<PrmsContractAmendment> getContractAmendmentNo(
            PrmsContractAmendment prmsContractAmendment) {

        List<PrmsContractAmendment> contractAmendmentLst = new ArrayList();
        if (prmsContractAmendment.getColumnName() != null && !prmsContractAmendment.getColumnName().equals("")
                && prmsContractAmendment.getColumnValue() != null && !prmsContractAmendment.getColumnValue().equals("")) {

            Query query = em.createNativeQuery("SELECT * FROM PRMS_CONTRACT_AMENDMENT\n"
                    + "where " + prmsContractAmendment.getColumnName().toLowerCase() + " = '" + prmsContractAmendment.getColumnValue() + "'"
                    + "and " + prmsContractAmendment.getPreparedBy() + "='" + prmsContractAmendment.getPreparedBy() + "'", PrmsContractAmendment.class);
            try {
                if (query.getResultList().size() > 0) {
                    contractAmendmentLst = query.getResultList();
                }
                return contractAmendmentLst;
            } catch (NumberFormatException nfe) {
                nfe.getMessage();
                return null;
            }
        } else {
            Query query = em.createNamedQuery("PrmsContractAmendment.findByPreparedBy");
            query.setParameter("preparedBy", prmsContractAmendment.getPreparedBy());
            contractAmendmentLst = query.getResultList();
            return contractAmendmentLst;
        }
    }

    public ArrayList<PrmsContractAmendment> getContractAmendmentNo(int status, int UserId) {
        Query query = em.createNativeQuery("select * from PRMS_CONTRACT_AMENDMENT dr INNER JOIN WF_PRMS_PROCESSED wf on dr.CONTRACT_AMEND_ID=wf.CONTRACT_AMEND_ID "
                + "where dr.status='" + status + "' " + "and wf.PROCESSED_BY='" + UserId + "' ", PrmsContractAmendment.class);
        ArrayList<PrmsContractAmendment> contAmLst = new ArrayList<>(query.getResultList());

        return contAmLst;
    }

    /**
     * *************************************************************************
     *
     * @return
     * ************************************************************************
     */
    public List<PrmsContract> getContractList() {

        List<PrmsContract> ContractList = null;

        try {
            Query query = em.createNamedQuery("PrmsContract.findAll",
                    PrmsContract.class);
            ContractList = (List<PrmsContract>) query.getResultList();

            return ContractList;

        } catch (Exception e) {
            e.printStackTrace();
        }

        return ContractList;
    }

    public List<PrmsContractamendCurrency> getContractAmeList() {

        List<PrmsContractamendCurrency> ContractList = null;

        try {
            Query query = em.createNamedQuery("PrmsContractCurrencyDetail.findAll",
                    PrmsContractamendCurrency.class);
            ContractList = (List<PrmsContractamendCurrency>) query.getResultList();

            return ContractList;

        } catch (Exception e) {
            e.printStackTrace();
        }

        return ContractList;
    }

    /**
     * *************************************************************************
     *
     * @param bidNumer
     * @return
     * ************************************************************************
     */
    public PrmsContractAmendment getBidNumber(String bidNumer) {

        Query query = em.createNamedQuery("PrmsContractAmendment.findByContractAmendNo",
                PrmsContractAmendment.class);
        PrmsContractAmendment directPurcObj = null;
        query.setParameter("contractAmendNo", bidNumer);

        try {
            directPurcObj = (PrmsContractAmendment) query.getResultList().get(0);

            return directPurcObj;
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }

    public PrmsContractAmendment LastContractNo() {

        Query query = em.createNamedQuery("PrmsContractAmendment.findByMaxCheckListNum");

        try {
            PrmsContractAmendment CheckListNo = null;
            if (query.getResultList().size() > 0) {
                CheckListNo = (PrmsContractAmendment) query.getResultList().get(0);

            }
            return CheckListNo;
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }

    public List<PrmsContractDetail> getContractNo(String contractNo) {
        System.out.println("---contract No---" + contractNo);
        List<PrmsContractDetail> ContractList = null;
        try {
            Query query = em.createNamedQuery("PrmsContractDetail.findByContractNo", PrmsContractDetail.class);
            query.setParameter("contractNo", contractNo);
            ContractList = (List<PrmsContractDetail>) query.getResultList();
            System.out.println("---contract after in---" + ContractList.size());
            return ContractList;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ContractList;
    }

    public PrmsContract getContractNum(String toString) {
        PrmsContract prmsContractObj = null;
        Query query = em.createNamedQuery("PrmsContract.findByContractNo", PrmsContract.class);
        query.setParameter("contractNo", toString);
        try {
            if (query.getResultList().size() > 0) {
                prmsContractObj = (PrmsContract) query.getResultList().get(0);

                return prmsContractObj;
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return prmsContractObj;
    }

    public List<PrmsContractAmendment> getBidAmend(String toString) {
        System.out.println("--------demissie---" + toString);

        Query query = em.createNamedQuery("PrmsContractAmendment.findByContractNo");
        List<PrmsContractAmendment> directPurcObj = null;
        query.setParameter("contractNo", toString);
        try {
            directPurcObj = (List<PrmsContractAmendment>) query.getResultList();
            System.out.println("---------amare----" + directPurcObj.size());

            return directPurcObj;
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }

    /**
     *
     * @param id
     * @return
     */
    public PrmsContractAmendment getSelectedRequest(BigDecimal id) {

        Query query = em.createNamedQuery("PrmsContractAmendment.findByContractAmendId");
        query.setParameter("contractAmendId", id);

        try {
            PrmsContractAmendment selectrequest = (PrmsContractAmendment) query.getResultList().get(0);

            return selectrequest;

        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }

    /**
     *
     */
    public List<PrmsContractAmendment> getContAmendedNoByContId(PrmsContract prmsContract) {

        System.out.println("----I'm rush to get Amend No Lists By Contract Id ");
        Query ques = em.createNamedQuery("PrmsContractAmendment.listByContId");
        ques.setParameter("contractId", prmsContract);
        // System.out.println("selected===" + prmsContract.getContractId());
        try {
            System.out.println("22");
            ArrayList<PrmsContractAmendment> amendNoList = null;

            if (ques.getResultList().size() > 0) {
                System.out.println("23");
                amendNoList = new ArrayList<>(ques.getResultList());
                System.out.println("--- Here is We Listed for You-----" + amendNoList);
            }

            return amendNoList;

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * *************************************************************************
     *
     * @return
     * ************************************************************************
     */
    public List<PrmsContractAmendment> getContAmendedNoByContNoStatus(PrmsContract prmsContract) {

        System.out.println("----I'm rush to get Amend No Lists By Contract Id ");
        Query ques = em.createNamedQuery("PrmsContractAmendment.listByContIdStatus");
        ques.setParameter("contractId", prmsContract);
        // System.out.println("selected===" + prmsContract.getContractId());
        try {
            System.out.println("22");
            ArrayList<PrmsContractAmendment> amendNoList = null;

            if (ques.getResultList().size() > 0) {
                System.out.println("23");
                amendNoList = new ArrayList<>(ques.getResultList());
                System.out.println("--- Here is We Listed for You-----" + amendNoList);
            }

            return amendNoList;

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * *************************************************************************
     *
     * @return
     * ************************************************************************
     */
    public List<PrmsContractAmendment> getContAmendedNoByContNoStatus() {

        System.out.println("----I'm rush to get Amend No Lists By Contract Id ");
        Query ques = em.createNamedQuery("PrmsContractAmendment.listBytatus");

        try {
            System.out.println("22");
            ArrayList<PrmsContractAmendment> amendNoList = null;

            if (ques.getResultList().size() > 0) {
                System.out.println("23");
                amendNoList = new ArrayList<>(ques.getResultList());
                System.out.println("--- Here is We Listed for You-----" + amendNoList);
            }

            return amendNoList;

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    // <editor-fold defaultstate="collapsed" desc="Native Queries">
    // to check whether it's amended Contract or not
    public List<PrmsContractAmendment> checkAmendOrNot(PrmsContract prmsContract) {
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
            }
            return amendedContractList;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

//get contract Amendment info By Contract Id
    public PrmsContractAmendment getContractAmendment(PrmsContract prmsContract) {
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
            }
            return contractAmendedInfo;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    
    // </editor-fold>

    public List<PrmsContractAmendment> getParamNameList() {
        Query query = em.createNativeQuery("SELECT column_name FROM user_tab_columns \n"
                + "where table_name = UPPER('PRMS_CONTRACT_AMENDMENT') \n"
                + "and column_name not in ('CONTRACT_AMEND_ID','DOCUMENT_ID','STATUS','REMARK','CURRENCY','BID_ID','ID','SUPP_ID','AWARD_ID','CONTRACT_ID','PROJECT_ID','DOCUMENTUP_ID')");
        try {
            List<PrmsContractAmendment> columnNameList = new ArrayList<>();
            columnNameList = query.getResultList();
            return columnNameList;
        } catch (Exception e) {
            e.getMessage();
            return null;
        }
    }
}
