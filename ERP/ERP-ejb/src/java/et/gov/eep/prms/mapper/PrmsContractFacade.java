/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package et.gov.eep.prms.mapper;

import et.gov.eep.commonApplications.mapper.AbstractFacade;
import et.gov.eep.hrms.entity.organization.HrDepartments;
import et.gov.eep.pms.entity.PmsCreateProjects;
import et.gov.eep.prms.entity.PrmsAward;
import et.gov.eep.prms.entity.PrmsBid;
import et.gov.eep.prms.entity.PrmsContract;
import et.gov.eep.prms.entity.PrmsQuotation;
import et.gov.eep.fcms.entity.FmsLuCurrency;
import et.gov.eep.prms.entity.PrmsBidAmend;
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
public class PrmsContractFacade extends AbstractFacade<PrmsContract> {

    @PersistenceContext(unitName = "ERP-ejbPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public PrmsContractFacade() {
        super(PrmsContract.class);
    }

    // <editor-fold defaultstate="collapsed" desc="Named Queries">
    public ArrayList<PrmsContract> searchContractByContractNo(PrmsContract papmsContract) {
        Query query = em.createNamedQuery("PrmsContract.searchfindByContractNo");
        query.setParameter("contractNo", papmsContract.getContractNo() + '%');
        try {
            ArrayList<PrmsContract> CotractList = new ArrayList(query.getResultList());
            return CotractList;
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }

    public PrmsContract getContractNoinfo(PrmsContract papmsContract) {
        Query query = em.createNamedQuery("PrmsContract.findByContractNo");
        query.setParameter("contractNo", papmsContract.getContractNo());
        try {
            PrmsContract selectedobj = (PrmsContract) query.getResultList().get(0);
            return selectedobj;
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }

    public List<PrmsContract> findAmend() {
        Query query = em.createNamedQuery("PrmsContract.findAlls");
        try {
            List<PrmsContract> cont = new ArrayList<>();
            if (query.getResultList().size() > 0) {
                cont = query.getResultList();
            }
            return cont;
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }

    public List<PrmsContract> contractNumLists(int approvedStatus) {
        Query query = em.createNamedQuery("PrmsContract.findByStatus");
        query.setParameter("status", approvedStatus);
        try {
            ArrayList<PrmsContract> contNum = new ArrayList(query.getResultList());
            return contNum;
        } catch (Exception e) {
            return null;
        }
    }

    public ArrayList<HrDepartments> searchdeptName() {
        Query query = em.createNamedQuery("HrDepartments.findAll", HrDepartments.class);
        try {
            ArrayList<HrDepartments> departmentName = new ArrayList(query.getResultList());
            return departmentName;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public ArrayList<PrmsBid> bidNumberList() {
        Query query = em.createNamedQuery("PrmsBid.findAll");
        try {
            ArrayList<PrmsBid> supplier = new ArrayList(query.getResultList());
            return supplier;
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }

    public ArrayList<PrmsBid> quotationNumberList() {
        Query query = em.createNamedQuery("PRMS_QUOTATION.findAll");
        try {
            ArrayList<PrmsBid> supplier = new ArrayList(query.getResultList());
            return supplier;
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }

    public List<PrmsContract> findApprovedContractNumbers(int status) {
        ArrayList<PrmsContract> result = null;
        Query query = em.createNamedQuery("PrmsContract.findAll", PrmsContract.class);
        try {
            if (query.getResultList().size() > 0) {
                result = new ArrayList(query.getResultList());
            }
            return result;
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }

    public PrmsContract getLastContractNo() {
        Query query = em.createNamedQuery("PrmsContract.findByMaxcontractId");
        PrmsContract result = null;
        try {
            if (query.getResultList().size() > 0) {
                result = (PrmsContract) query.getResultList().get(0);
            }
            return result;
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }

    public PrmsContract getSelectedRequest(BigDecimal id) {
        Query query = em.createNamedQuery("PrmsContract.findByContractId");
        query.setParameter("contractId", id);
        try {
            PrmsContract selectrequest = (PrmsContract) query.getResultList().get(0);
            return selectrequest;
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }

    public List<PrmsContract> ContractList() {
        Query query = em.createNamedQuery("PrmsContract.findAll");
        try {
            ArrayList<PrmsContract> supplier = new ArrayList(query.getResultList());
            return supplier;
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }

    public List<PrmsContract> ContractListStatus() {

        Query query = em.createNamedQuery("PrmsContract.findAllbyStatus");

        try {
            ArrayList<PrmsContract> supplier = new ArrayList(query.getResultList());

            return supplier;

        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }

    public List<PrmsQuotation> getProforma() {
        Query query = em.createNamedQuery("PrmsQuotation.findAll");
        try {
            ArrayList<PrmsQuotation> quotationNum = new ArrayList<>(query.getResultList());
            return quotationNum;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public List<PmsCreateProjects> getListProjects() {
        Query query = em.createNamedQuery("PmsCreateProjects.findAll");
        try {
            List<PmsCreateProjects> criteria = new ArrayList(query.getResultList());
            return criteria;
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return null;
    }

    public List<PrmsContract> searchContract(PrmsContract papmsContract) {
         List<PrmsContract> prmsContractLst = new ArrayList();
        if (papmsContract.getColumnName() != null && !papmsContract.getColumnName().equals("")
                && papmsContract.getColumnValue() != null && !papmsContract.getColumnValue().equals("")) {

            Query query = em.createNativeQuery("SELECT * FROM PRMS_CONTRACT\n"
                    + "where " + papmsContract.getColumnName().toLowerCase() + " = '" + papmsContract.getColumnValue() + "'"
                    + "and " + papmsContract.getPreparedBy() + "='" + papmsContract.getPreparedBy() + "'", PrmsContract.class);
            try {
                if (query.getResultList().size() > 0) {
                    prmsContractLst = query.getResultList();
                }
                return prmsContractLst;
            } catch (NumberFormatException nfe) {
                nfe.getMessage();
                return null;
            }
        } else {
            Query query = em.createNamedQuery("PrmsContract.findByPreparedBy");
            query.setParameter("preparedBy", papmsContract.getPreparedBy());
            prmsContractLst = query.getResultList();
            return prmsContractLst;
        }
    }

    public List<PrmsContract> findContractsListByWfStatus(int status) {
        Query query = em.createNamedQuery("PrmsContract.findByStatus", PrmsContract.class);
        query.setParameter("status", status);
        try {
            ArrayList<PrmsContract> listofContracts = new ArrayList(query.getResultList());
            return listofContracts;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public List<PrmsContract> listOfContractNO() {
        Query query = em.createNamedQuery("PrmsContract.findAlls");
        try {
            List<PrmsContract> cont = new ArrayList<>();
            if (query.getResultList().size() > 0) {
                cont = query.getResultList();
            }
            return cont;
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }

    public ArrayList<FmsLuCurrency> getCurrencylist() {
        Query query = em.createNativeQuery(" select * from Fms_Lu_Currency", FmsLuCurrency.class);
        try {
            ArrayList<FmsLuCurrency> curr = new ArrayList<>();
            curr = new ArrayList<>(query.getResultList());
            return curr;
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }

    public PrmsContract getByContId(PrmsContract prmsContract) {
        Query query = em.createNamedQuery("PrmsContract.findByContractId");
        query.setParameter("contractId", prmsContract.getContractId());
        try {
            PrmsContract operatingBudgetInfo = null;
            if (query.getResultList().size() > 0) {
                operatingBudgetInfo = (PrmsContract) query.getResultList().get(0);
            }
            return operatingBudgetInfo;
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }
    // </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="Native Queries">
    public ArrayList<PrmsBid> BidNoForCheck() {
        Query query = em.createNativeQuery("SELECT distinct PRMS_BID.*\n"
                + " FROM PRMS_BID\n"
                + " INNER JOIN PRMS_AWARD\n"
                + " ON PRMS_BID.ID = PRMS_AWARD.BID_ID "
                + " WHERE PRMS_BID.STATUS = 3 ORDER BY PRMS_BID.ref_no", PrmsBid.class);
        try {
            ArrayList<PrmsBid> supplier = new ArrayList<>();
            supplier = new ArrayList<>(query.getResultList());
            return supplier;

        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }

    public ArrayList<PrmsBid> vendorName() {

        Query query = em.createNativeQuery("SELECT PRMS_BID.ID,\n"
                + "  PRMS_BID.REF_NO,\n"
                + "  PRMS_AWARD.BID_ID,\n"
                + "  PRMS_AWARD.AWARD_ID,\n"
                + "  PRMS_AWARD.SUPP_ID,\n"
                + "  PRMS_SUPPLY_PROFILE.VENDOR_NAME\n"
                + "FROM PRMS_BID\n"
                + "INNER JOIN PRMS_AWARD\n"
                + "ON PRMS_BID.ID = PRMS_AWARD.BID_ID\n"
                + "INNER JOIN PRMS_SUPPLY_PROFILE\n"
                + "ON PRMS_SUPPLY_PROFILE.ID = PRMS_AWARD.SUPP_ID", PrmsBid.class);
        try {
            ArrayList<PrmsBid> supplier = new ArrayList<>();
            supplier = new ArrayList<>(query.getResultList());
            return supplier;

        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }

    public ArrayList<PrmsAward> getsupplierlist(PrmsBid bidNo) {
        Query query = em.createNativeQuery(" SELECT PRMS_BID.ID,\n"
                + "  PRMS_BID.REF_NO,\n"
                + "  PRMS_AWARD.*\n"
                + "FROM PRMS_BID\n"
                + "INNER JOIN PRMS_AWARD\n"
                + "ON PRMS_BID.ID = PRMS_AWARD.BID_ID\n"
                + "WHERE PRMS_AWARD.BID_ID = '" + bidNo.getId() + "'", PrmsAward.class);
        try {
            ArrayList<PrmsAward> supplier = new ArrayList<>();
            supplier = new ArrayList<>(query.getResultList());
            return supplier;
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }

    public List<PrmsContract> searchContract(int status, int UserId) {
        Query query = em.createNativeQuery("select * from PRMS_CONTRACT dr "
                + "INNER JOIN WF_PRMS_PROCESSED wf on dr.CONTRACT_ID=wf.CONTRACT_ID "
                + "where dr.status='" + status + "' "
                + "and wf.PROCESSED_BY='" + UserId + "' ", PrmsContract.class);
        ArrayList<PrmsContract> contLst = new ArrayList<>(query.getResultList());

        return contLst;
    }

    public List<PrmsBidAmend> checkBidFromAmended(PrmsBid prmsBid) {
        Query query = em.createNativeQuery("SELECT * FROM prms_bid_amend bdamd\n"
                + "inner join(select bid_id , max(id) as maxAmendId \n"
                + "FROM prms_bid_amend \n"
                + "GROUP BY bid_id) bdamd2\n"
                + "on bdamd.id=bdamd2.maxAmendId\n"
                + "inner join prms_bid bd\n"
                + "on bd.id=bdamd.bid_id\n"
                + "where bd.id ='" + prmsBid.getId() + "'", PrmsBidAmend.class);
        try {
            List<PrmsBidAmend> bidAmend = new ArrayList<>();
            if (query.getResultList().size() > 0) {
                bidAmend = query.getResultList();
            }
            return bidAmend;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public PrmsBidAmend getBidAmendInfoByBidId(PrmsBidAmend prmsBidAmend) {
        System.out.println("query to get bidAmendement where Bid Id==== " + prmsBidAmend.getBidId());
        Query query = em.createNativeQuery("SELECT * FROM prms_bid_amend bdamd\n"
                + "inner join(SELECT bdamd2.bid_id, max(bdamd2.id) as maxAmendId FROM prms_bid_amend bdamd2\n"
                + "group by bdamd2.bid_id) maxId\n"
                + "on bdamd.id=maxId.maxAmendId\n"
                + "where bdamd.bid_id='" + prmsBidAmend.getBidId().getId() + "'", PrmsBidAmend.class);
        try {
            System.out.println("here try by Bid Id " + prmsBidAmend.getBidId().getId());
            PrmsBidAmend bidAmendInfo = new PrmsBidAmend();
            if (query.getResultList().size() > 0) {
                bidAmendInfo = (PrmsBidAmend) query.getResultList().get(0);
                System.out.println("bid Type " + bidAmendInfo.getBidType() + " at " + bidAmendInfo.getId() + " bidAmendment MaxID");
            }
            return bidAmendInfo;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
    // </editor-fold>

    public List<PrmsContract> getParamNameList() {
        Query query = em.createNativeQuery("SELECT column_name FROM user_tab_columns \n"
                + "where table_name = UPPER('PRMS_CONTRACT') \n"
                + "and column_name not in ('contractId','DOCUMENT_ID','REMARK','CURRENCY','BID_ID','SUPP_ID','AWARD_ID','PROJECT_ID','QUOTATION_ID','FILENAME','STATUS','DOCUMENTUP_ID')");
        try {
            List<PrmsContract> columnNameList = new ArrayList<>();
            columnNameList = query.getResultList();
            return columnNameList;
        } catch (Exception e) {
            e.getMessage();
            return null;
        }
    }
}
