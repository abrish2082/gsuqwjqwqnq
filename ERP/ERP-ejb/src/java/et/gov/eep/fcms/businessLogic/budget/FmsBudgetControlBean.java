package et.gov.eep.fcms.businessLogic.budget;
//<editor-fold defaultstate="collapsed" desc="import ">

import et.gov.eep.fcms.entity.FmsLuBudgetYear;
import et.gov.eep.fcms.entity.admin.FmsCostcSystemJunction;
import et.gov.eep.fcms.entity.budget.FmsBudgetControl;
import et.gov.eep.fcms.mapper.budget.FmsBudgetControlFacade;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Stateless;
    //</editor-fold>

@Stateless
public class FmsBudgetControlBean implements FmsBudgetControlBeanLocal {
//<editor-fold defaultstate="collapsed" desc="EJB ">

    @EJB
    FmsBudgetControlFacade fmsBudgetControlFacade;

    //</editor-fold>
    //<editor-fold defaultstate="collapsed" desc="other methods ">

    @Override
    public void create(FmsBudgetControl fmsBudgetControl) {
        fmsBudgetControlFacade.create(fmsBudgetControl);
    }

    @Override
    public List<FmsBudgetControl> fetchOBComparison(FmsLuBudgetYear fmsLuBudgetYear, FmsCostcSystemJunction fmsCostcSystemJunction) {
        return fmsBudgetControlFacade.fetchOBComparison(fmsLuBudgetYear, fmsCostcSystemJunction);
    }

    @Override
    public List<FmsBudgetControl> fetchCBComparison(FmsLuBudgetYear fmsLuBudgetYear, FmsCostcSystemJunction fmsCostcSystemJunction) {
        return fmsBudgetControlFacade.fetchCBComparison(fmsLuBudgetYear, fmsCostcSystemJunction);
    }
    //</editor-fold>

}
