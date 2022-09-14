package et.gov.eep.fcms.mapper.Bond;

import java.math.BigDecimal;
import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.concurrent.TimeUnit;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import et.gov.eep.commonApplications.mapper.AbstractFacade;
import et.gov.eep.fcms.entity.Bond.FmsBondApplication;
import et.gov.eep.fcms.entity.Bond.FmsBondRepaymentSchedule;
import et.gov.eep.fcms.entity.Bond.FmsBondType;

/**
 *
 * @author mora
 */
@Stateless
public class FmsBondApplicationFacade extends AbstractFacade<FmsBondApplication> {

    @PersistenceContext(unitName = "ERP-ejbPU")
    private EntityManager em;
    @Inject
    FmsBondRepaymentSchedule repaymentSchedule;
    @EJB
    FmsBondRepaymentScheduleFacade repaymentScheduleFacade;

    Double am;

    int days;
    Double inerest = 0.0;
    Double inerest_B = 0.0;

    Double rate_b = 1.5;
    Double rate = 1.3;
    Double amount = 1.1;
    BigDecimal days1;
    String R_date1 = "";
    java.util.Date R_date, no_days, end_day;
    Date Carent_date;
    java.util.Date today = Calendar.getInstance().getTime();
    SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public FmsBondApplicationFacade() {
        super(FmsBondApplication.class);

    }

    public long Days(java.util.Date d1, java.util.Date d2) {
        return TimeUnit.MILLISECONDS.toDays(d1.getTime() - d2.getTime());
    }

    public FmsBondRepaymentSchedule getRepaymentSchedule() {
        if (repaymentSchedule == null) {
            repaymentSchedule = new FmsBondRepaymentSchedule();
        }
        return repaymentSchedule;
    }

    public void setRepaymentSchedule(FmsBondRepaymentSchedule repaymentSchedule) {
        this.repaymentSchedule = repaymentSchedule;
    }

    /*named query to select value from FmsBondApplication table using BondTypeId*/
    public FmsBondApplication fmsBondApplicationinfo(FmsBondApplication application) {
        Query query = em.createNamedQuery("FmsBondType.findByBondTypeId");
        query.setParameter("BondTypeId", application.getBondApplicationId());
        try {
            FmsBondApplication BondApplicationInfo = (FmsBondApplication) query.getResultList().get(0);
            return BondApplicationInfo;

        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }
    /*select BondTypeId from FmsBondApplication table using BondApplicationId's first letter or number*/

    public ArrayList<FmsBondApplication> searchFmsBondTypeId(FmsBondApplication application) {
        Query query = em.createNamedQuery("FmsBondApplication.findByBondApplicationIdlike");
        query.setParameter("BondApplicationId", application.getBondApplicationId() + '%');
        try {
            ArrayList<FmsBondApplication> applicationsList = new ArrayList(query.getResultList());
            return applicationsList;
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }

    public Double calculat__inerest(FmsBondApplication BondApplication) throws ParseException {

        amount = BondApplication.getAmount();
        if (days >= 180) {
            inerest = ((amount * rate) * days / 360);
        }

        return inerest;
    }

    /*native query to select all value from FmsBondApplication table*/
    public ArrayList<FmsBondApplication> searchFmsBonddate() {
        Query query = em.createNativeQuery("SELECT * FROM FMS_BOND_APPLICATION,FMS_BOND_TYPE,FMS_BOND_LIBOR WHERE ((FMS_BOND_APPLICATION.application_date <= TRUNC(SYSDATE) - 180\n"
                + "                AND FMS_BOND_APPLICATION.BOND_TYPE_ID =FMS_BOND_TYPE.BOND_TYPE_ID)"
                + "                AND (fms_Bond_application.application_date between fms_Bond_libor.start_date and fms_Bond_libor.end_date))", FmsBondApplication.class);

        try {
            List<Object[]> results = query.getResultList();
            ArrayList<FmsBondApplication> applicationsList = new ArrayList();
            FmsBondType BondType = null;
            int i = 0;

            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");

            FmsBondApplication application = null;
            for (Object[] result : results) {
                application = new FmsBondApplication();
                application.setBondApplicationId(String.valueOf(result[0]));
                String BondId = String.valueOf(result[0]);
                application.setBuyerFullName(String.valueOf(result[1]));
                application.setApplicationDate((java.util.Date) result[2]);
                R_date = (java.util.Date) (result[2]);
                end_day = (java.util.Date) (result[17]);
                days = (int) Days(today, R_date);
                application.setCountryBondIssued(String.valueOf(result[3]));

                BondType = new FmsBondType();
                rate_b = BondType.getBondInterst();
                rate = (Double.parseDouble(result[13].toString()));
                BondType.setBondTypeId(String.valueOf(result[4]));
                application.setBondTypeId(BondType);
                application.setAmount((Double.parseDouble(result[5].toString())));
                Double rate_lobrat = (Double.parseDouble(result[19].toString()));
                amount = (Double.parseDouble(result[5].toString()));
                if (days >= 180) {
                    inerest = ((amount * (rate + rate_lobrat)) * days / 360);
                    int x = 1;
                    x = x + 1;
                    Calendar c = Calendar.getInstance();
                    c.setTime(end_day); // Now use today date.
                    c.add(Calendar.DATE, 1); // Adding 5 days
                    end_day = (c.getTime());
//                     
                    repaymentSchedule = new FmsBondRepaymentSchedule();
                    repaymentSchedule.setPrincipalPayment(inerest);
                    repaymentSchedule.setPreparedBy("mora");
                    repaymentSchedule.setPreparedByDate(today);
                    repaymentSchedule.setBondId(application);
                    repaymentSchedule.setInstallmentDate(today);
                    if (repaymentSchedule != null) {
                        repaymentScheduleFacade.create(repaymentSchedule);

                    }
                    repaymentSchedule = null;
                }
                application.setModeOfPayment(String.valueOf(result[6]));
                application.setGracePeriod(String.valueOf(result[7]));
                applicationsList.add(application);
                i++;

            }
            return applicationsList;
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }

    /*native query to select all value from FmsBondApplication table*/
    public ArrayList<FmsBondApplication> july_7(Date D) {
        Calendar c = Calendar.getInstance();
        c.setTime(D); // Now use today date.
        if (c.get(Calendar.MONTH) == 07 && c.get(Calendar.MONTH) == 07) {
            Query query = em.createNativeQuery("SELECT * FROM FMS_BOND_APPLICATION,FMS_BOND_TYPE,FMS_BOND_LIBOR WHERE ("
                    + "(FMS_BOND_APPLICATION.application_date=fms_Bond_application.last_repayment_schedule) and"
                    + "         AND FMS_BOND_APPLICATION.BOND_TYPE_ID =FMS_BOND_TYPE.BOND_TYPE_ID)"
                    + "     AND (fms_Bond_application.application_date between fms_Bond_libor.start_date and fms_Bond_libor.end_date))");

            try {
                List<Object[]> results = query.getResultList();
                ArrayList<FmsBondApplication> applicationsList = new ArrayList();
                FmsBondType BondType = null;
                int i = 0;

                SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");

                FmsBondApplication application = null;
                for (Object[] result : results) {
                    application = new FmsBondApplication();
                    R_date = (java.util.Date) (result[2]);
                    end_day = (java.util.Date) (result[17]);
                    days = (int) Days(today, R_date);
                    application.setCountryBondIssued(String.valueOf(result[3]));

                    BondType = new FmsBondType();

                    rate = (Double.parseDouble(result[13].toString()));
                    BondType.setBondTypeId(String.valueOf(result[4]));
                    application.setBondTypeId(BondType);
                    application.setAmount((Double.parseDouble(result[5].toString())));
                    Double rate_lobrat = (Double.parseDouble(result[19].toString()));
                    amount = (Double.parseDouble(result[5].toString()));

                    inerest = ((amount * (rate + rate_lobrat)) * days / 360);
                    int x = 1;
                    x = x + 1;

                    c.setTime(end_day); // Now use today date.
                    c.add(Calendar.DATE, 1); // Adding 5 days
                    end_day = (c.getTime());
//                     
                    repaymentSchedule = new FmsBondRepaymentSchedule();
                    repaymentSchedule.setPrincipalPayment(inerest);
                    repaymentSchedule.setPreparedBy("mora");
                    repaymentSchedule.setPreparedByDate(today);
                    repaymentSchedule.setNoOfInstallmen(days);
                    repaymentSchedule.setInstallmentDate(R_date);
                    repaymentSchedule.setBondId(application);
                    if (repaymentSchedule != null) {
                        repaymentScheduleFacade.create(repaymentSchedule);

                    }
                    repaymentSchedule = null;

                    i++;

                }
                return applicationsList;
            } catch (Exception ex) {
                ex.printStackTrace();
                return null;
            }
        }
        if (c.get(Calendar.MONTH) == 07 && c.get(Calendar.MONTH) == 07) {
            Query query = em.createNativeQuery("SELECT * FROM FMS_BOND_APPLICATION,FMS_BOND_TYPE,FMS_BOND_LIBOR WHERE ("
                    + "(FMS_BOND_APPLICATION.application_date!=fms_Bond_application.last_repayment_schedule) and"
                    + "         AND FMS_BOND_APPLICATION.BOND_TYPE_ID =FMS_BOND_TYPE.BOND_TYPE_ID)"
                    + "     AND (fms_Bond_application.application_date between fms_Bond_libor.start_date and fms_Bond_libor.end_date))");
            try {
                List<Object[]> results = query.getResultList();
                ArrayList<FmsBondApplication> applicationsList = new ArrayList();
                FmsBondType BondType = null;
                int i = 0;

                SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");

                FmsBondApplication application = null;
                for (Object[] result : results) {
                    application = new FmsBondApplication();
                    R_date = (java.util.Date) (result[2]);
                    end_day = (java.util.Date) (result[8]);
                    days = (int) Days(today, end_day);
                    application.setCountryBondIssued(String.valueOf(result[3]));

                    BondType = new FmsBondType();

                    rate = (Double.parseDouble(result[13].toString()));
                    BondType.setBondTypeId(String.valueOf(result[4]));
                    application.setBondTypeId(BondType);
                    application.setAmount((Double.parseDouble(result[5].toString())));
                    Double rate_lobrat = (Double.parseDouble(result[19].toString()));
                    amount = (Double.parseDouble(result[5].toString()));
                    inerest = ((amount * (rate + rate_lobrat)) * days / 360);
                    repaymentSchedule = new FmsBondRepaymentSchedule();
                    repaymentSchedule.setPrincipalPayment(inerest);
                    repaymentSchedule.setPreparedBy("mora");
                    repaymentSchedule.setPreparedByDate(today);
                    repaymentSchedule.setNoOfInstallmen(days);
                    repaymentSchedule.setInstallmentDate(end_day);
                    repaymentSchedule.setBondId(application);
                    if (repaymentSchedule != null) {
                        repaymentScheduleFacade.create(repaymentSchedule);

                    }
                    repaymentSchedule = null;

                    i++;

                }
                return applicationsList;
            } catch (Exception ex) {
                ex.printStackTrace();
                return null;
            }
        }
        if (c.get(Calendar.MONTH) != 07 || c.get(Calendar.MONTH) != 07) {
            Query query = em.createNativeQuery("SELECT * FROM FMS_BOND_APPLICATION,FMS_BOND_TYPE,FMS_BOND_LIBOR WHERE ("
                    + "(FMS_BOND_APPLICATION.application_date=fms_Bond_application.last_repayment_schedule) and"
                    + "(FMS_BOND_APPLICATION.application_date <= SYSDATE - INTERVAL '6' MONTH"
                    + "         AND FMS_BOND_APPLICATION.BOND_TYPE_ID =FMS_BOND_TYPE.BOND_TYPE_ID)"
                    + "     AND (fms_Bond_application.application_date between fms_Bond_libor.start_date and fms_Bond_libor.end_date))");

            try {
                List<Object[]> results = query.getResultList();
                ArrayList<FmsBondApplication> applicationsList = new ArrayList();
                FmsBondType BondType = null;
                int i = 0;

                SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");

                FmsBondApplication application = null;
                for (Object[] result : results) {
                    application = new FmsBondApplication();
                    R_date = (java.util.Date) (result[2]);
                    end_day = (java.util.Date) (result[17]);
                    days = (int) Days(today, R_date);
                    application.setCountryBondIssued(String.valueOf(result[3]));

                    BondType = new FmsBondType();

                    rate = (Double.parseDouble(result[13].toString()));
                    BondType.setBondTypeId(String.valueOf(result[4]));
                    application.setBondTypeId(BondType);
                    application.setAmount((Double.parseDouble(result[5].toString())));
                    Double rate_lobrat = (Double.parseDouble(result[19].toString()));
                    amount = (Double.parseDouble(result[5].toString()));

                    inerest = ((amount * (rate + rate_lobrat)) * days / 360);
                    int x = 1;
                    x = x + 1;

                    c.setTime(end_day); // Now use today date.
                    c.add(Calendar.DATE, 1); // Adding 5 days
                    end_day = (c.getTime());
//                     
                    repaymentSchedule = new FmsBondRepaymentSchedule();
                    repaymentSchedule.setPrincipalPayment(inerest);
                    repaymentSchedule.setPreparedBy("mora");
                    repaymentSchedule.setPreparedByDate(today);
                    repaymentSchedule.setNoOfInstallmen(days);
                    repaymentSchedule.setInstallmentDate(R_date);
                    repaymentSchedule.setBondId(application);
                    if (repaymentSchedule != null) {
                        repaymentScheduleFacade.create(repaymentSchedule);

                    }
                    repaymentSchedule = null;

                    i++;

                }
                return applicationsList;
            } catch (Exception ex) {
                ex.printStackTrace();
                return null;
            }
        }
        if (c.get(Calendar.MONTH) != 07 || c.get(Calendar.MONTH) != 07) {
            Query query = em.createNativeQuery("SELECT * FROM FMS_BOND_APPLICATION,FMS_BOND_TYPE,FMS_BOND_LIBOR WHERE ("
                    + "(FMS_BOND_APPLICATION.application_date!=fms_Bond_application.last_repayment_schedule) and"
                    + "(FMS_BOND_APPLICATION.application_date <= SYSDATE - INTERVAL '6' MONTH"
                    + "         AND FMS_BOND_APPLICATION.BOND_TYPE_ID =FMS_BOND_TYPE.BOND_TYPE_ID)"
                    + "     AND (fms_Bond_application.application_date between fms_Bond_libor.start_date and fms_Bond_libor.end_date))");
            try {
                List<Object[]> results = query.getResultList();
                ArrayList<FmsBondApplication> applicationsList = new ArrayList();
                FmsBondType BondType = null;
                int i = 0;

                SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");

                FmsBondApplication application = null;
                for (Object[] result : results) {
                    application = new FmsBondApplication();
                    R_date = (java.util.Date) (result[2]);
                    end_day = (java.util.Date) (result[8]);
                    days = (int) Days(today, end_day);
                    application.setCountryBondIssued(String.valueOf(result[3]));

                    BondType = new FmsBondType();

                    rate = (Double.parseDouble(result[13].toString()));
                    BondType.setBondTypeId(String.valueOf(result[4]));
                    application.setBondTypeId(BondType);
                    application.setAmount((Double.parseDouble(result[5].toString())));
                    Double rate_lobrat = (Double.parseDouble(result[19].toString()));
                    amount = (Double.parseDouble(result[5].toString()));
                    inerest = ((amount * (rate + rate_lobrat)) * days / 360);
                    repaymentSchedule = new FmsBondRepaymentSchedule();
                    repaymentSchedule.setPrincipalPayment(inerest);
                    repaymentSchedule.setPreparedBy("mora");
                    repaymentSchedule.setPreparedByDate(today);
                    repaymentSchedule.setNoOfInstallmen(days);
                    repaymentSchedule.setInstallmentDate(end_day);
                    repaymentSchedule.setBondId(application);
                    if (repaymentSchedule != null) {
                        repaymentScheduleFacade.create(repaymentSchedule);

                    }
                    repaymentSchedule = null;

                    i++;

                }
                return applicationsList;
            } catch (Exception ex) {
                ex.printStackTrace();
                return null;
            }
        }

        return null;
    }

    /*native query to select all value from FmsBondApplication table with requirement*/
    public FmsBondApplication fmsBondmaturedinfo(FmsBondApplication application) {
        Query query = em.createNativeQuery("SELECT * FROM FMS_BOND_APPLICATION f WHERE f.application_date <= SYSDATE - INTERVAL '6' MONTH");
        query.setParameter("BondTypeId", application.getBondApplicationId());
        try {
            FmsBondApplication BondApplicationInfo = (FmsBondApplication) query.getResultList().get(0);
            return BondApplicationInfo;

        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }

    }

}
