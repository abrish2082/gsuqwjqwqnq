/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package et.gov.eep.fcms.mapper.loan;

import et.gov.eep.commonApplications.mapper.AbstractFacade;
import et.gov.eep.fcms.entity.loan.FmsLoan;
import et.gov.eep.fcms.entity.loan.FmsLoanRepayment;
import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 *
 * @author PO
 */
@Stateless
public class FmsLoanRepaymentFacade extends AbstractFacade<FmsLoanRepayment> {

    @PersistenceContext(unitName = "ERP-ejbPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public FmsLoanRepaymentFacade() {
        super(FmsLoanRepayment.class);
    }

    public ArrayList<FmsLoanRepayment> searchLoanRepayment(FmsLoan fmsLoan) {
        Query query = em.createNativeQuery("SELECT LR.SCHEDULE_DATE, SUM(LR.CAPITAL_REPAYMENT), "
                + "SUM(LR.INTEREST_REPAYMENT) from FMS_LOAN_REPAYMENT LR "
                + "WHERE LR.DISBURSEMENT_FK IN (SELECT LD.DISBURSEMENT_ID FROM FMS_LOAN_DISBURSEMENT LD WHERE LD.LOAN_ID = '" + fmsLoan.getLoanId() + "') "
                + "GROUP BY LR.SCHEDULE_DATE ORDER BY LR.SCHEDULE_DATE");

        List<Object[]> results = query.getResultList();
        FmsLoanRepayment fmsLoanRepayment;
        ArrayList<FmsLoanRepayment> loanRepaymentList = new ArrayList();

        for (Object[] result : results) {

            fmsLoanRepayment = new FmsLoanRepayment();
            String target = result[0].toString();
            DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
            try {
                Date scheduleDate = df.parse(target);
                fmsLoanRepayment.setScheduleDate(scheduleDate);
            } catch (Exception e) {
            }

            if (result[1] == null) {
                fmsLoanRepayment.setCapitalRepayment(BigDecimal.ZERO);
            } else {
                fmsLoanRepayment.setCapitalRepayment(new BigDecimal(result[1].toString()));
            }
            if (result[2] == null) {
                fmsLoanRepayment.setInterestRepayment(BigDecimal.ZERO);
            } else {
                fmsLoanRepayment.setInterestRepayment(new BigDecimal(result[2].toString()));
            }
            loanRepaymentList.add(fmsLoanRepayment);
        }
        return loanRepaymentList;
    }
}
